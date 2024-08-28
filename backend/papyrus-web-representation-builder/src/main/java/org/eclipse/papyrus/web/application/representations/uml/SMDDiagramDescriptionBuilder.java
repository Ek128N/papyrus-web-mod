/*****************************************************************************
 * Copyright (c) 2022, 2024 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *  Aurelien Didier (Artal Technologies) - Issue 199
 *****************************************************************************/
package org.eclipse.papyrus.web.application.representations.uml;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.web.application.representations.view.CreationToolsUtil;
import org.eclipse.papyrus.web.application.representations.view.aql.CallQuery;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.InsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.OutsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Builder of the "State machine" diagram representation description.
 *
 * @author Laurent Fasani
 */
public class SMDDiagramDescriptionBuilder extends AbstractRepresentationDescriptionBuilder {

    public static final String SMD_REP_NAME = "State Machine Diagram";

    public static final String SMD_PREFIX = "SMD_";

    public static final int STATEMACHINE_NODE_BORDER_RADIUS = 10;

    private static final String PSEUDO_STATE = "Pseudostate_";

    private static final String ROUND_ICON_NODE_DEFAULT_DIAMETER = "30";

    private static final String ICON_PATH = "/icons-override/full/obj16/";

    private static final String ICON_SVG_EXTENSION = ".svg";

    private final UMLPackage umlPackage = UMLPackage.eINSTANCE;

    public SMDDiagramDescriptionBuilder() {
        super(SMD_PREFIX, SMD_REP_NAME, UMLPackage.eINSTANCE.getStateMachine());
    }

    @Override
    protected void fillDescription(DiagramDescription diagramDescription) {

        this.createStateMachineNodeDescription(diagramDescription);
        this.createTransitionEdgeDescription(diagramDescription);

        this.createCommentDescription(diagramDescription);

        // There is a unique DropTool for the DiagramDescription
        diagramDescription.getPalette().setDropTool(this.getViewBuilder().createGenericSemanticDropTool(this.getIdBuilder().getDiagramSemanticDropToolName()));
    }

    private NodeDescription createStateMachineNodeDescription(DiagramDescription diagramDescription) {
        RectangularNodeStyleDescription rectangularNodeStyle = this.getViewBuilder().createRectangularNodeStyle();
        rectangularNodeStyle.setBorderRadius(STATEMACHINE_NODE_BORDER_RADIUS);
        ListLayoutStrategyDescription listLayoutStrategyDescription = DiagramFactory.eINSTANCE.createListLayoutStrategyDescription();
        NodeDescription smdStateMachineNodeDesc = this.newNodeBuilder(this.umlPackage.getStateMachine(), rectangularNodeStyle)//
                .layoutStrategyDescription(listLayoutStrategyDescription)//
                .semanticCandidateExpression(this.getQueryBuilder().querySelf())//
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(this.umlPackage.getStateMachine().getName()))//
                .insideLabelDescription(this.getQueryBuilder().queryRenderLabel(), this.getViewBuilder().createDefaultInsideLabelStyle(false, true))
                .build();
        diagramDescription.getNodeDescriptions().add(smdStateMachineNodeDesc);

        // workaround to overcome missing enhancement https://github.com/PapyrusSirius/papyrus-web/issues/121
        // It is not possible to define that there is no delete tool.
        // The only way is to define a delete tool that does nothing
        smdStateMachineNodeDesc.getPalette().setDeleteTool(DiagramFactory.eINSTANCE.createDeleteTool());

        String specializedDomainNodeName = this.getIdBuilder().getSpecializedDomainNodeName(this.umlPackage.getPseudostate(), "BorderNode_InStateMachine");
        this.createPseudostateBorderNodeDescription(smdStateMachineNodeDesc, this.umlPackage.getStateMachine_ConnectionPoint(), specializedDomainNodeName);

        NodeDescription regionNodeDescription = this.createRegionNodeDescription(smdStateMachineNodeDesc, diagramDescription);
        listLayoutStrategyDescription.getGrowableNodes().add(regionNodeDescription);
        this.registerNodeAsCommentOwner(regionNodeDescription, diagramDescription);

        Supplier<List<NodeDescription>> stateMachineDescs = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getStateMachine());
        this.registerCallback(smdStateMachineNodeDesc, () -> {
            CreationToolsUtil.addNodeCreationTool(stateMachineDescs, this.getViewBuilder().createCreationTool(UMLPackage.eINSTANCE.getStateMachine_Region(), UMLPackage.eINSTANCE.getRegion()));
        });
        return smdStateMachineNodeDesc;
    }

    private NodeDescription createRegionNodeDescription(NodeDescription stateMachineNodeDescription, DiagramDescription diagramDescription) {
        RectangularNodeStyleDescription rectangularNodeStyleDescription = this.getViewBuilder().createRectangularNodeStyle();
        rectangularNodeStyleDescription.setBackground(this.styleProvider.getTransparentColor());

        // We need here to add a non empty label so the renderer displays a label and the label separator
        // This is the only way we can have compartment with no border but still display a line between each compartment
        InsideLabelDescription labelDesc = this.getViewBuilder().createDefaultInsideLabelDescription(false, true);
        labelDesc.getStyle().setWithHeader(false);
        labelDesc.setLabelExpression("aql:' '");
        NodeDescription regionNodeDesc = this.newNodeBuilder(this.umlPackage.getRegion(), rectangularNodeStyleDescription)//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .semanticCandidateExpression(CallQuery.queryAttributeOnSelf(this.umlPackage.getStateMachine_Region()))//
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(this.umlPackage.getRegion().getName()))//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(this.umlPackage.getRegion().getName()))//
                .insideLabelDescription(labelDesc)
                .build();

        stateMachineNodeDescription.getChildrenDescriptions().add(regionNodeDesc);

        this.createStateNodeDescription(regionNodeDesc, diagramDescription);
        this.createFinalStateNodeDescription(regionNodeDesc);
        this.createPseudostateInRegionNodeDescription(regionNodeDesc);

        Supplier<List<NodeDescription>> regionDescs = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getRegion());
        this.registerCallback(regionNodeDesc, () -> {
            CreationToolsUtil.addNodeCreationTool(regionDescs, this.getViewBuilder().createCreationTool(this.umlPackage.getRegion_Subvertex(), this.umlPackage.getState()));
            CreationToolsUtil.addNodeCreationTool(regionDescs, this.getViewBuilder().createCreationTool(this.umlPackage.getRegion_Subvertex(), this.umlPackage.getFinalState()));
        });
        return regionNodeDesc;
    }

    private NodeDescription createStateNodeDescription(NodeDescription regionNodeDescription, DiagramDescription diagramDescription) {
        RectangularNodeStyleDescription rectangularNodeStyle = this.getViewBuilder().createRectangularNodeStyle();
        rectangularNodeStyle.setBorderRadius(STATEMACHINE_NODE_BORDER_RADIUS);
        InsideLabelDescription labelDescription = this.getViewBuilder().createDefaultInsideLabelDescription(false, false);

        // Display the header separator only if there is a region in the state
        String condition = "aql:self.region->size() > 0";
        labelDescription.getConditionalStyles()
                .add(this.getViewBuilder().createConditionalInsideLabelStyle(condition, this.getViewBuilder().createDefaultInsideLabelStyle(false, true)));

        RectangularNodeStyleDescription headerStyle = this.getViewBuilder().createRectangularNodeStyle();
        headerStyle.setBorderRadius(STATEMACHINE_NODE_BORDER_RADIUS);

        ConditionalNodeStyle conditionalHeaderStyle = this.getViewBuilder().createConditionalNodeStyle(condition, headerStyle);

        NodeDescription stateNodeDesc = this.newNodeBuilder(this.umlPackage.getState(), rectangularNodeStyle)//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription())//
                .semanticCandidateExpression(CallQuery.queryAttributeOnSelf(this.umlPackage.getRegion_Subvertex()))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .conditionalStyles(List.of(conditionalHeaderStyle)) //
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(this.umlPackage.getState().getName()))//
                .reusedNodeDescriptions(List.of(regionNodeDescription))//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(this.umlPackage.getState().getName()))//
                .insideLabelDescription(labelDescription)
                .build();

        regionNodeDescription.getChildrenDescriptions().add(stateNodeDesc);

        String specializedDomainNodeName = this.getIdBuilder().getSpecializedDomainNodeName(this.umlPackage.getPseudostate(), "BorderNode_InState");
        this.createPseudostateBorderNodeDescription(stateNodeDesc, this.umlPackage.getState_ConnectionPoint(), specializedDomainNodeName);

        Supplier<List<NodeDescription>> stateDesc = () -> this.collectNodesWithDomainAndFilter(diagramDescription, List.of(this.umlPackage.getState()), List.of(this.umlPackage.getFinalState()));
        this.registerCallback(stateNodeDesc, () -> {
            CreationToolsUtil.addNodeCreationTool(stateDesc, this.getViewBuilder().createCreationTool(this.umlPackage.getState_Region(), this.umlPackage.getRegion()));
        });

        return stateNodeDesc;
    }

    private void createFinalStateNodeDescription(NodeDescription regionNodeDescription) {
        ImageNodeStyleDescription imageNodeStyle = this.getViewBuilder().createImageNodeStyle("view/images/FinalState_24dp.svg");

        NodeDescription finalStateNodeDesc = this.newNodeBuilder(this.umlPackage.getFinalState(), imageNodeStyle)//
                .semanticCandidateExpression(CallQuery.queryAttributeOnSelf(this.umlPackage.getRegion_Subvertex()))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(this.umlPackage.getFinalState().getName()))//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(this.umlPackage.getFinalState().getName()))//
                .addOutsideLabelDescription(this.getViewBuilder().createDefaultOutsideLabelDescription(true))
                .build();
        finalStateNodeDesc.setDefaultWidthExpression(ROUND_ICON_NODE_DEFAULT_DIAMETER);
        finalStateNodeDesc.setDefaultHeightExpression(ROUND_ICON_NODE_DEFAULT_DIAMETER);

        regionNodeDescription.getChildrenDescriptions().add(finalStateNodeDesc);
    }

    private void createPseudostateInRegionNodeDescription(NodeDescription regionNodeDescription) {
        List<PseudostateKind> pseudostateKinds = new ArrayList<>(List.of(PseudostateKind.values()));
        pseudostateKinds.removeAll(List.of(PseudostateKind.ENTRY_POINT_LITERAL, PseudostateKind.EXIT_POINT_LITERAL));

        String specializedDomainNodeName = this.getIdBuilder().getSpecializedDomainNodeName(this.umlPackage.getPseudostate(), "InRegion");
        NodeDescription pseudostateNodeDesc = this.createPseudoStateNodeDescription(regionNodeDescription, this.umlPackage.getRegion_Subvertex(), pseudostateKinds, specializedDomainNodeName);

        regionNodeDescription.getChildrenDescriptions().add(pseudostateNodeDesc);
    }

    private void createPseudostateBorderNodeDescription(NodeDescription parentNodeDescription, EReference containmentFeature, String name) {
        List<PseudostateKind> pseudostateKinds = List.of(PseudostateKind.ENTRY_POINT_LITERAL, PseudostateKind.EXIT_POINT_LITERAL);

        NodeDescription pseudostateBorderNodeDesc = this.createPseudoStateNodeDescription(parentNodeDescription, containmentFeature, pseudostateKinds, name);

        parentNodeDescription.getBorderNodesDescriptions().add(pseudostateBorderNodeDesc);
    }

    private NodeDescription createPseudoStateNodeDescription(NodeDescription parentDesc, EReference containmentFeature, List<PseudostateKind> pseudostateKinds, String name) {
        List<ConditionalNodeStyle> conditionalNodeStyles = new ArrayList<>();
        List<NodeTool> creationTools = new ArrayList<>();

        final OutsideLabelDescription labelStyle = this.getViewBuilder().createDefaultOutsideLabelDescription(true);
        for (PseudostateKind pseudostateKind : pseudostateKinds) {
            String condition = "aql:self.kind = uml::PseudostateKind::" + pseudostateKind.getLiteral();
            String literal = pseudostateKind.getLiteral();
            String literalName = literal.substring(0, 1).toUpperCase() + literal.substring(1);
            String imageName = literalName + ".svg";

            NodeStyleDescription nodeStyle;
            if (pseudostateKind.equals(PseudostateKind.FORK_LITERAL) || pseudostateKind.equals(PseudostateKind.JOIN_LITERAL)) {
                nodeStyle = this.getViewBuilder().createRectangularNodeStyle();
            } else {
                nodeStyle = this.getViewBuilder().createImageNodeStyle("view/images/" + imageName);
            }

            ConditionalNodeStyle conditionalNodeStyle = this.getViewBuilder().createConditionalNodeStyle(condition,
                    nodeStyle);
            conditionalNodeStyles.add(conditionalNodeStyle);

            // Node creation tool
            NodeTool creationTool = DiagramFactory.eINSTANCE.createNodeTool();
            creationTool.setName("New " + literalName);
            creationTool.setIconURLsExpression(ICON_PATH + PSEUDO_STATE + literal + ICON_SVG_EXTENSION);

            // Create instance and init
            ChangeContext createElement = this.getViewBuilder().createChangeContextOperation(CallQuery.queryServiceOnSelf(StateMachineDiagramServices.CREATE_PSEUDO_STATE, //
                    "'uml::Pseudostate'", //
                    String.format("'%s'", containmentFeature.getName()), //
                    "selectedNode", //
                    "diagramContext", //
                    "convertedNodes", //
                    String.format("'%s'", pseudostateKind)));

            creationTool.getBody().add(createElement);

            creationTools.add(creationTool);
        }

        NodeDescription pseudostateBorderNodeDesc = this.newNodeBuilder(this.umlPackage.getPseudostate(), null)//
                .name(name)//
                .semanticCandidateExpression(CallQuery.queryAttributeOnSelf(containmentFeature))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(this.umlPackage.getPseudostate().getName()))//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(this.umlPackage.getPseudostate().getName()))//
                .addOutsideLabelDescription(labelStyle) //
                .conditionalStyles(conditionalNodeStyles)//
                .build();

        pseudostateBorderNodeDesc.setDefaultWidthExpression(CallQuery.queryServiceOnSelf(StateMachineDiagramServices.COMPUTE_PSEUDO_STATE_WITDTH));
        pseudostateBorderNodeDesc.setDefaultHeightExpression(CallQuery.queryServiceOnSelf(StateMachineDiagramServices.COMPUTE_PSEUDO_STATE_HEIGHT));
        pseudostateBorderNodeDesc.setKeepAspectRatio(true);

        this.registerCallback(pseudostateBorderNodeDesc, () -> {
            parentDesc.getPalette().getNodeTools().addAll(creationTools);
        });

        return pseudostateBorderNodeDesc;
    }

    private void createTransitionEdgeDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> vertexNodeDescriptions = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getVertex());
        EdgeDescription transitionEdgeDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.umlPackage.getTransition(),
                this.getQueryBuilder().queryAllReachableExactType(this.umlPackage.getTransition()), vertexNodeDescriptions, vertexNodeDescriptions);
        transitionEdgeDescription.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        diagramDescription.getEdgeDescriptions().add(transitionEdgeDescription);
        transitionEdgeDescription.getPalette().setCenterLabelEditTool(null);
        EdgeTool edgeTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(transitionEdgeDescription, this.umlPackage.getRegion_Transition());
        this.registerCallback(transitionEdgeDescription, () -> {
            CreationToolsUtil.addEdgeCreationTool(vertexNodeDescriptions, edgeTool);
        });
        this.getViewBuilder().addDefaultReconnectionTools(transitionEdgeDescription);
    }
}
