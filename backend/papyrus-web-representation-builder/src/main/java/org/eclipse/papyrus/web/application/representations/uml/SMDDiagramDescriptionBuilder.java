/*******************************************************************************
 * Copyright (c) 2022, 2023 CEA, Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.application.representations.uml;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
import org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
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

    public static final String SMD_REP_NAME = "State Machine Diagram"; //$NON-NLS-1$

    public static final String SMD_PREFIX = "SMD_"; //$NON-NLS-1$

    public static final int STATEMACHINE_NODE_BORDER_RADIUS = 10;

    private static final String ROUND_ICON_NODE_DEFAULT_DIAMETER = "30";

    private static final String FORK_NODE_DEFAULT_WIDTH = "50";

    private static final String FORK_NODE_DEFAULT_HEIGHT = "150";

    private final UMLPackage umlPackage = UMLPackage.eINSTANCE;

    public SMDDiagramDescriptionBuilder() {
        super(SMD_PREFIX, SMD_REP_NAME, UMLPackage.eINSTANCE.getStateMachine()); // $NON-NLS-1$
    }

    @Override
    protected void fillDescription(DiagramDescription diagramDescription) {

        createStateMachineNodeDescription(diagramDescription);
        createTransitionEdgeDescription(diagramDescription);

        createCommentDescription(diagramDescription);

        // There is a unique DropTool for the DiagramDescription
        diagramDescription.getPalette().setDropTool(getViewBuilder().createGenericDropTool(getIdBuilder().getDropToolId()));
    }

    private NodeDescription createStateMachineNodeDescription(DiagramDescription diagramDescription) {
        RectangularNodeStyleDescription rectangularNodeStyle = getViewBuilder().createRectangularNodeStyle(false, false);
        rectangularNodeStyle.setBorderRadius(STATEMACHINE_NODE_BORDER_RADIUS);
        NodeDescription smdStateMachineNodeDesc = newNodeBuilder(umlPackage.getStateMachine(), rectangularNodeStyle)//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription())//
                .semanticCandidateExpression(getQueryBuilder().querySelf())//
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)//
                .labelEditTool(getViewBuilder().createDirectEditTool())//
                .build();
        diagramDescription.getNodeDescriptions().add(smdStateMachineNodeDesc);

        // workaround to overcome missing enhancement https://github.com/PapyrusSirius/papyrus-web/issues/121
        // It is not possible to define that there is no delete tool.
        // The only way is to define a delete tool that does nothing
        smdStateMachineNodeDesc.getPalette().setDeleteTool(DiagramFactory.eINSTANCE.createDeleteTool());

        String specializedDomainNodeName = getIdBuilder().getSpecializedDomainNodeName(umlPackage.getPseudostate(), "BorderNode_InStateMachine"); //$NON-NLS-1$
        createPseudostateBorderNodeDescription(smdStateMachineNodeDesc, umlPackage.getStateMachine_ConnectionPoint(), specializedDomainNodeName);

        NodeDescription regionNodeDescription = createRegionNodeDescription(smdStateMachineNodeDesc, diagramDescription);

        registerNodeAsCommentOwner(regionNodeDescription, diagramDescription);

        Supplier<List<NodeDescription>> stateMachineDescs = () -> collectNodesWithDomain(diagramDescription, umlPackage.getStateMachine());
        registerCallback(smdStateMachineNodeDesc, () -> {
            CreationToolsUtil.addNodeCreationTool(stateMachineDescs, getViewBuilder().createCreationTool(UMLPackage.eINSTANCE.getStateMachine_Region(), UMLPackage.eINSTANCE.getRegion()));
        });
        return smdStateMachineNodeDesc;
    }

    private NodeDescription createRegionNodeDescription(NodeDescription stateMachineNodeDescription, DiagramDescription diagramDescription) {

        NodeDescription regionNodeDesc = newNodeBuilder(umlPackage.getRegion(), getViewBuilder().createRectangularNodeStyle(false, false))//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .semanticCandidateExpression(CallQuery.queryAttributeOnSelf(umlPackage.getStateMachine_Region()))//
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)//
                .deleteTool(getViewBuilder().createNodeDeleteTool(umlPackage.getRegion().getName()))//
                .labelEditTool(getViewBuilder().createDirectEditTool())//
                .build();

        stateMachineNodeDescription.getChildrenDescriptions().add(regionNodeDesc);

        createStateNodeDescription(regionNodeDesc);
        createFinalStateNodeDescription(regionNodeDesc);
        createPseudostateInRegionNodeDescription(regionNodeDesc);
        
        Supplier<List<NodeDescription>> regionDescs = () -> collectNodesWithDomain(diagramDescription, umlPackage.getRegion());
        registerCallback(regionNodeDesc, () -> {
            CreationToolsUtil.addNodeCreationTool(regionDescs, getViewBuilder().createCreationTool(umlPackage.getRegion_Subvertex(), umlPackage.getState()));
            CreationToolsUtil.addNodeCreationTool(regionDescs, getViewBuilder().createCreationTool(umlPackage.getRegion_Subvertex(), umlPackage.getFinalState()));
        });
        return regionNodeDesc;
    }

    private NodeDescription createStateNodeDescription(NodeDescription regionNodeDescription) {
        RectangularNodeStyleDescription rectangularNodeStyle = getViewBuilder().createRectangularNodeStyle(false, false);
        rectangularNodeStyle.setBorderRadius(STATEMACHINE_NODE_BORDER_RADIUS);

        NodeDescription stateNodeDesc = newNodeBuilder(umlPackage.getState(), rectangularNodeStyle)//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription())//
                .semanticCandidateExpression(CallQuery.queryAttributeOnSelf(umlPackage.getRegion_Subvertex()))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .deleteTool(getViewBuilder().createNodeDeleteTool(umlPackage.getState().getName()))//
                .reusedNodeDescriptions(List.of(regionNodeDescription))//
                .labelEditTool(getViewBuilder().createDirectEditTool())//
                .build();

        regionNodeDescription.getChildrenDescriptions().add(stateNodeDesc);

        String specializedDomainNodeName = getIdBuilder().getSpecializedDomainNodeName(umlPackage.getPseudostate(), "BorderNode_InState"); //$NON-NLS-1$
        createPseudostateBorderNodeDescription(stateNodeDesc, umlPackage.getState_ConnectionPoint(), specializedDomainNodeName);

        return stateNodeDesc;
    }

    private void createFinalStateNodeDescription(NodeDescription regionNodeDescription) {
        ImageNodeStyleDescription imageNodeStyle = getViewBuilder().createImageNodeStyle(UUID.nameUUIDFromBytes("FinalState_24dp.svg".getBytes()).toString(), false); //$NON-NLS-1$
        imageNodeStyle.setBorderSize(0);
        imageNodeStyle.setWidthComputationExpression(ROUND_ICON_NODE_DEFAULT_DIAMETER);
        imageNodeStyle.setHeightComputationExpression(ROUND_ICON_NODE_DEFAULT_DIAMETER);

        NodeDescription finalStateNodeDesc = newNodeBuilder(umlPackage.getFinalState(), imageNodeStyle)//
                .semanticCandidateExpression(CallQuery.queryAttributeOnSelf(umlPackage.getRegion_Subvertex()))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .deleteTool(getViewBuilder().createNodeDeleteTool(umlPackage.getFinalState().getName()))//
                .labelEditTool(getViewBuilder().createDirectEditTool())//
                .build();

        regionNodeDescription.getChildrenDescriptions().add(finalStateNodeDesc);
    }

    private void createPseudostateInRegionNodeDescription(NodeDescription regionNodeDescription) {
        List<PseudostateKind> pseudostateKinds = new ArrayList<>(List.of(PseudostateKind.values()));
        pseudostateKinds.removeAll(List.of(PseudostateKind.ENTRY_POINT_LITERAL, PseudostateKind.EXIT_POINT_LITERAL));

        String specializedDomainNodeName = getIdBuilder().getSpecializedDomainNodeName(umlPackage.getPseudostate(), "InRegion"); //$NON-NLS-1$
        NodeDescription pseudostateNodeDesc = createPseudoStateNodeDescription(regionNodeDescription, umlPackage.getRegion_Subvertex(), pseudostateKinds, specializedDomainNodeName);

        regionNodeDescription.getChildrenDescriptions().add(pseudostateNodeDesc);
    }

    private void createPseudostateBorderNodeDescription(NodeDescription parentNodeDescription, EReference containmentFeature, String name) {
        List<PseudostateKind> pseudostateKinds = List.of(PseudostateKind.ENTRY_POINT_LITERAL, PseudostateKind.EXIT_POINT_LITERAL);

        NodeDescription pseudostateBorderNodeDesc = createPseudoStateNodeDescription(parentNodeDescription, containmentFeature, pseudostateKinds, name);

        parentNodeDescription.getBorderNodesDescriptions().add(pseudostateBorderNodeDesc);
    }

    private NodeDescription createPseudoStateNodeDescription(NodeDescription parentDesc, EReference containmentFeature, List<PseudostateKind> pseudostateKinds, String name) {
        List<ConditionalNodeStyle> conditionalNodeStyles = new ArrayList<>();
        List<NodeTool> creationTools = new ArrayList<>();
        for (PseudostateKind pseudostateKind : pseudostateKinds) {
            String condition = "aql:self.kind = uml::PseudostateKind::" + pseudostateKind.getLiteral(); //$NON-NLS-1$
            String literal = pseudostateKind.getLiteral();
            String literalName = literal.substring(0, 1).toUpperCase() + literal.substring(1);
            String imageName = literalName + ".svg"; //$NON-NLS-1$
            NodeStyleDescription imageNodeStyle = getViewBuilder().createImageNodeStyle(UUID.nameUUIDFromBytes(imageName.getBytes()).toString(), false);
            imageNodeStyle.setBorderSize(0);
            if (pseudostateKind.equals(PseudostateKind.FORK_LITERAL) || pseudostateKind.equals(PseudostateKind.JOIN_LITERAL)) {
                imageNodeStyle.setWidthComputationExpression(FORK_NODE_DEFAULT_WIDTH);
                imageNodeStyle.setHeightComputationExpression(FORK_NODE_DEFAULT_HEIGHT);
            } else {
                imageNodeStyle.setWidthComputationExpression(ROUND_ICON_NODE_DEFAULT_DIAMETER);
                imageNodeStyle.setHeightComputationExpression(ROUND_ICON_NODE_DEFAULT_DIAMETER);
            }

            ConditionalNodeStyle conditionalNodeStyle = getViewBuilder().createConditionalNodeStyle(condition, imageNodeStyle);
            conditionalNodeStyles.add(conditionalNodeStyle);

            // Node creation tool
            NodeTool creationTool = DiagramFactory.eINSTANCE.createNodeTool();
            creationTool.setName("New " + literalName);

            // Create instance and init
            ChangeContext createElement = getViewBuilder().createChangeContextOperation(CallQuery.queryServiceOnSelf("createPseudoState", //
                    "'uml::Pseudostate'", //
                    String.format("'%s'", containmentFeature.getName()), //
                    "selectedNode", //
                    "diagramContext", //
                    "convertedNodes", //
                    String.format("'%s'", pseudostateKind)));

            creationTool.getBody().add(createElement);

            creationTools.add(creationTool);
        }

        NodeDescription pseudostateBorderNodeDesc = newNodeBuilder(umlPackage.getPseudostate(), null)//
                .name(name)//
                .semanticCandidateExpression(CallQuery.queryAttributeOnSelf(containmentFeature))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .deleteTool(getViewBuilder().createNodeDeleteTool(umlPackage.getPseudostate().getName()))//
                .labelEditTool(getViewBuilder().createDirectEditTool())//
                .conditionalStyles(conditionalNodeStyles)//
                .build();
        registerCallback(pseudostateBorderNodeDesc, () -> {
            parentDesc.getPalette().getNodeTools().addAll(creationTools);
        });

        return pseudostateBorderNodeDesc;
    }

    private void createTransitionEdgeDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> vertexNodeDescriptions = () -> collectNodesWithDomain(diagramDescription, umlPackage.getVertex());
        EdgeDescription transitionEdgeDescription = getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(umlPackage.getTransition(),
                getQueryBuilder().queryAllReachableExactType(umlPackage.getTransition()), vertexNodeDescriptions, vertexNodeDescriptions);
        transitionEdgeDescription.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        transitionEdgeDescription.getPalette().setCenterLabelEditTool(null);
        registerCallback(transitionEdgeDescription, () -> {
            CreationToolsUtil.addEdgeCreationTool(vertexNodeDescriptions, getViewBuilder().createDefaultDomainBasedEdgeTool(transitionEdgeDescription, umlPackage.getRegion_Transition()));
        });

        diagramDescription.getEdgeDescriptions().add(transitionEdgeDescription);

        getViewBuilder().addDefaultReconnectionTools(transitionEdgeDescription);
    }
}
