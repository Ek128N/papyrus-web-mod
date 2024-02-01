/*****************************************************************************
 * Copyright (c) 2024 CEA LIST, Obeo.
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
 *****************************************************************************/
package org.eclipse.papyrus.web.application.representations.uml;

import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.CONVERTED_NODES;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.DIAGRAM_CONTEXT;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.SELECTED_NODE;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.uml.domain.services.create.ElementCreator;
import org.eclipse.papyrus.web.application.representations.view.aql.CallQuery;
import org.eclipse.papyrus.web.application.representations.view.aql.Variables;
import org.eclipse.papyrus.web.application.representations.view.builders.NodeDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.view.builders.NoteStyleDescriptionBuilder;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.PapyrusCustomNodesFactory;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.RectangleWithExternalLabelNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DropNodeTool;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;
import org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.uml2.uml.AcceptCallAction;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.ActionInputPin;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityFinalNode;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ActivityParameterNode;
import org.eclipse.uml2.uml.ActivityPartition;
import org.eclipse.uml2.uml.AddStructuralFeatureValueAction;
import org.eclipse.uml2.uml.CallBehaviorAction;
import org.eclipse.uml2.uml.CallOperationAction;
import org.eclipse.uml2.uml.ClearAssociationAction;
import org.eclipse.uml2.uml.ClearStructuralFeatureAction;
import org.eclipse.uml2.uml.ConditionalNode;
import org.eclipse.uml2.uml.ControlFlow;
import org.eclipse.uml2.uml.CreateObjectAction;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.DestroyObjectAction;
import org.eclipse.uml2.uml.ExpansionNode;
import org.eclipse.uml2.uml.ExpansionRegion;
import org.eclipse.uml2.uml.FlowFinalNode;
import org.eclipse.uml2.uml.ForkNode;
import org.eclipse.uml2.uml.InitialNode;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.InterruptibleActivityRegion;
import org.eclipse.uml2.uml.JoinNode;
import org.eclipse.uml2.uml.LoopNode;
import org.eclipse.uml2.uml.MergeNode;
import org.eclipse.uml2.uml.ObjectFlow;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.ReadExtentAction;
import org.eclipse.uml2.uml.ReadIsClassifiedObjectAction;
import org.eclipse.uml2.uml.ReadSelfAction;
import org.eclipse.uml2.uml.ReadStructuralFeatureAction;
import org.eclipse.uml2.uml.ReclassifyObjectAction;
import org.eclipse.uml2.uml.ReduceAction;
import org.eclipse.uml2.uml.SendObjectAction;
import org.eclipse.uml2.uml.SendSignalAction;
import org.eclipse.uml2.uml.StartClassifierBehaviorAction;
import org.eclipse.uml2.uml.StartObjectBehaviorAction;
import org.eclipse.uml2.uml.StructuredActivityNode;
import org.eclipse.uml2.uml.TestIdentityAction;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValuePin;
import org.eclipse.uml2.uml.ValueSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Builder of the "Activity Diagram" diagram representation.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class ADDiagramDescriptionBuilder extends AbstractRepresentationDescriptionBuilder {

    /**
     * The prefix of the representation handled by this builder.
     */
    public static final String AD_PREFIX = "AD_";

    /**
     * The name of the representation handled by this builder.
     */
    public static final String AD_REP_NAME = "Activity Diagram";

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ElementCreator.class);

    /**
     * The width/height to use for border nodes.
     */
    private static final String BORDER_NODE_SIZE = "10";

    /**
     * The size of the border radius to use for rounded nodes.
     *
     * @see NodeStyleDescription#setBorderRadius(int)
     */
    private static final int BORDER_RADIUS_SIZE = 10;

    /**
     * The width/height to use for container nodes.
     */
    private static final String CONTAINER_NODE_SIZE = "100";

    /**
     * The condition to use in input/output pin conditional styles.
     */
    private static final String PIN_CONDITIONAL_STYLE_CONDITION = "aql:not(self.incoming->isEmpty()) or not(self.outgoing->isEmpty())";

    /**
     * 50px size Node.
     */
    private static final String SIZE_50 = "50";

    /**
     * 30px size Node.
     */
    private static final String SIZE_30 = "30";

    /**
     * Activity Group tool section name.
     */
    private static final String ACTIVITY_GROUP = "Activity Group";

    /**
     * Activity Node tool section name.
     */
    private static final String ACTIVITY_NODE = "Activity Node";

    /**
     * Pin tool section name.
     */
    private static final String PIN = "Pin";

    /**
     * Expansion Region tool section name.
     */
    private static final String EXPANSION_REGION = "Expansion Region";

    /**
     * Invocation Action tool section name.
     */
    private static final String INVOCATION_ACTION = "Invocation Action";

    /**
     * Create Object Action tool section name.
     */
    private static final String CREATE_OBJECT_ACTION = "Create Object Action";

    /**
     * Structured Activity Node tool section name.
     */
    private static final String STRUCTURED_ACTIVITY_NODE = "Structured Activity Node";

    /**
     * Structural Feature tool section name.
     */
    private static final String STRUCTURAL_FEATURE = "Structural Feature";

    /**
     * Executable node tool section name.
     */
    private static final String EXECUTABLE_NODE = "Executable Node";

    /**
     * Accept Event Action tool section name.
     */
    private static final String ACCEPT_EVENT_ACTION = "Accept Event Action";

    /**
     * The image to use for connected pins.
     */
    private static final String CONNECTED_PIN_IMAGE = "ConnectedPin.svg";

    /**
     * The {@link UMLPackage} used to access the UML metamodel.
     */
    private UMLPackage umlPackage = UMLPackage.eINSTANCE;

    /**
     * The <i>shared</i> {@link NodeDescription} for the diagram.
     */
    private NodeDescription adSharedDescription;

    public ADDiagramDescriptionBuilder() {
        super(AD_PREFIX, AD_REP_NAME, UMLPackage.eINSTANCE.getNamedElement());
    }

    @Override
    protected void fillDescription(DiagramDescription diagramDescription) {
        diagramDescription.setPreconditionExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.CAN_CREATE_DIAGRAM));

        this.createDiagramActivityNodeDescription(diagramDescription);
        this.createObjectFlowDescription(diagramDescription);
        this.createControlFlowDescription(diagramDescription);
        this.createSharedNodeDescriptions(diagramDescription);
        diagramDescription.getPalette().setDropTool(this.getViewBuilder().createGenericSemanticDropTool(this.getIdBuilder().getDiagramSemanticDropToolName()));
    }

    private void createSharedNodeDescriptions(DiagramDescription diagramDescription) {
        List<EClass> commentOwners = List.of(this.umlPackage.getActivity(), //
                this.umlPackage.getActivityPartition(), //
                this.umlPackage.getConditionalNode(), //
                this.umlPackage.getExpansionRegion(), //
                this.umlPackage.getInterruptibleActivityRegion(), //
                this.umlPackage.getLoopNode(), //
                this.umlPackage.getSequenceNode(), //
                this.umlPackage.getStructuredActivityNode());
        List<EClass> constraintOwners = List.of(this.umlPackage.getActivity(), //
                this.umlPackage.getConditionalNode(), //
                this.umlPackage.getExpansionRegion(), //
                this.umlPackage.getLoopNode(), //
                this.umlPackage.getSequenceNode(), //
                this.umlPackage.getStructuredActivityNode());
        this.adSharedDescription = this.createSharedDescription(diagramDescription);
        this.createCommentDescriptionInNodeDescription(diagramDescription, this.adSharedDescription, NODES,
                this.getIdBuilder().getSpecializedDomainNodeName(this.umlPackage.getComment(), SHARED_SUFFIX), commentOwners);
        this.createConstraintDescriptionInNodeDescription(diagramDescription, this.adSharedDescription, NODES,
                this.getIdBuilder().getSpecializedDomainNodeName(this.umlPackage.getConstraint(), SHARED_SUFFIX), constraintOwners);
        this.createSharedAcceptCallActionNodeDescription(diagramDescription);
        this.createSharedAcceptEventActionNodeDescription(diagramDescription);
        this.createSharedActionInputPinNodeDescription(diagramDescription);
        this.createSharedActivityFinalNodeDescription(diagramDescription);
        this.createSharedActivityParameterNodeNodeDescription(diagramDescription);
        this.createSharedActivityPartitionDescription(diagramDescription);
        this.createSharedAddStructuralFeatureValueActionNodeDescription(diagramDescription);
        this.createSharedBroadcastSignalActionNodeDescription(diagramDescription);
        this.createSharedCallBehaviorActionNodeDescription(diagramDescription);
        this.createSharedCallOperationActionNodeDescription(diagramDescription);
        this.createSharedClearAssociationActionNodeDescription(diagramDescription);
        this.createSharedClearStructuralFeatureActionNodeDescription(diagramDescription);
        this.createSharedConditionalNodeDescription(diagramDescription);
        this.createSharedCreateObjectActionNodeDescription(diagramDescription);
        this.createSharedDecisionNodeDescription(diagramDescription);
        this.createSharedDestroyObjectActionNodeDescription(diagramDescription);
        this.createSharedExpansionRegionNodeDescription(diagramDescription);
        this.createSharedFlowFinalNodeDescription(diagramDescription);
        this.createSharedForkNodeDescription(diagramDescription);
        this.createSharedInitialNodeDescription(diagramDescription);
        this.createSharedInputPinNodeDescription(diagramDescription);
        this.createSharedInterruptibleActivityRegionDescription(diagramDescription);
        this.createSharedJoinNodeDescription(diagramDescription);
        this.createSharedLoopNodeDescription(diagramDescription);
        this.createSharedMergeNodeDescription(diagramDescription);
        this.createSharedOpaqueActionNodeDescription(diagramDescription);
        this.createSharedOutputPinNodeDescription(diagramDescription);
        this.createSharedReadExtentActionNodeDescription(diagramDescription);
        this.createSharedReadIsClassifiedObjectActionNodeDescription(diagramDescription);
        this.createSharedReadSelfActionNodeDescription(diagramDescription);
        this.createSharedReadStructuralFeatureActionNodeDescription(diagramDescription);
        this.createSharedReclassifyObjectActionNodeDescription(diagramDescription);
        this.createSharedReduceActionNodeDescription(diagramDescription);
        this.createSharedSendObjectActionNodeDescription(diagramDescription);
        this.createSharedSendSignalActionNodeDescription(diagramDescription);
        this.createSharedSequenceNodeDescription(diagramDescription);
        this.createSharedStartClassifierBehaviorActionNodeDescription(diagramDescription);
        this.createSharedStartObjectBehaviorActionNodeDescription(diagramDescription);
        this.createSharedStructuredActivityNodeDescription(diagramDescription);
        this.createSharedSubActivityDescription(diagramDescription);
        this.createSharedTestIdentityActionNodeDescription(diagramDescription);
        this.createSharedValuePinNodeDescription(diagramDescription);
        this.createSharedValueSpecificationActionNodeDescription(diagramDescription);
    }

    /**
     * Creates the {@link NodeDescription} representing the UML root {@link Activity}.
     * <p>
     * This method creates the {@link NodeDescription} that represents the root activity of the diagram. See
     * {@link #createSharedSubActivityDescription(NodeDescription, DiagramDescription)} to create the
     * {@link NodeDescription} representing sub-{@link Activity} elements.
     * </p>
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription} containing the created {@link NodeDescription}
     *
     * @see #createSharedSubActivityDescription(NodeDescription, DiagramDescription)
     */
    private NodeDescription createDiagramActivityNodeDescription(DiagramDescription diagramDescription) {
        RectangularNodeStyleDescription rectangularNodeStyle = this.getViewBuilder().createRectangularNodeStyle(true, true);
        rectangularNodeStyle.setBorderRadius(BORDER_RADIUS_SIZE);

        EClass activityEClass = this.umlPackage.getActivity();
        NodeDescription adActivityDescription = this.newNodeBuilder(activityEClass, rectangularNodeStyle) //
                .name(this.getIdBuilder().getDomainNodeName(activityEClass)) //
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription()) //
                .semanticCandidateExpression(this.getQueryBuilder().querySelf()) //
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED) //
                .labelEditTool(this.getViewBuilder().createDirectEditTool(activityEClass.getName())) //
                .build();
        adActivityDescription.setDefaultWidthExpression(ROOT_ELEMENT_WIDTH);
        adActivityDescription.setDefaultHeightExpression(ROOT_ELEMENT_HEIGHT);
        diagramDescription.getNodeDescriptions().add(adActivityDescription);

        this.createDefaultToolSectionsInNodeDescription(adActivityDescription);
        this.addToolSections(adActivityDescription, ACTIVITY_GROUP, ACTIVITY_NODE, EXPANSION_REGION, INVOCATION_ACTION, CREATE_OBJECT_ACTION, STRUCTURED_ACTIVITY_NODE, STRUCTURAL_FEATURE,
                EXECUTABLE_NODE, ACCEPT_EVENT_ACTION);

        DropNodeTool adActivityGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(adActivityDescription));
        List<EClass> children = List.of(this.umlPackage.getActivity(), this.umlPackage.getActivityNode(), this.umlPackage.getActivityPartition(), this.umlPackage.getComment(),
                this.umlPackage.getConstraint(), this.umlPackage.getInterruptibleActivityRegion());
        this.registerCallback(adActivityDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            adActivityGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        adActivityDescription.getPalette().setDropNodeTool(adActivityGraphicalDropTool);

        return adActivityDescription;
    }

    private void addToolSections(NodeDescription parentNodeDescription, String... toolSectionNames) {
        for (String toolSectionName : toolSectionNames) {
            NodeToolSection toolSection = this.getViewBuilder().createNodeToolSection(toolSectionName);
            parentNodeDescription.getPalette().getToolSections().add(toolSection);
        }
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link AcceptCallAction}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedAcceptCallActionNodeDescription(DiagramDescription diagramDescription) {
        NodeDescription adAcceptCallActionNodeDescription = this.createSharedRoundedRectangleActionNodeDescription(this.umlPackage.getAcceptCallAction());
        this.adSharedDescription.getChildrenDescriptions().add(adAcceptCallActionNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adAcceptCallActionNodeDescription);
        this.addToolSections(adAcceptCallActionNodeDescription, PIN);

        NodeTool adAcceptCallActionCreationTool = this.createActivityNodeCreationTool(this.umlPackage.getAcceptCallAction());
        this.reuseNodeAndCreateTool(adAcceptCallActionNodeDescription, diagramDescription, adAcceptCallActionCreationTool, ACCEPT_EVENT_ACTION, this.umlPackage.getActivity(),
                this.umlPackage.getActivityGroup());
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link AcceptEventAction}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedAcceptEventActionNodeDescription(DiagramDescription diagramDescription) {
        // create hourglass conditional style
        String imageFile = "AcceptTimeEventAction.svg";
        ImageNodeStyleDescription adHourglassNodeStyleDescription = this.getViewBuilder().createImageNodeStyle(UUID.nameUUIDFromBytes(imageFile.getBytes()).toString(), true);
        adHourglassNodeStyleDescription.setBorderSize(0);
        ConditionalNodeStyle hourglassConditionalStyle = this.getViewBuilder().createConditionalNodeStyle("aql:self.trigger->size()=1 and self.trigger->first().event.oclIsTypeOf(uml::TimeEvent)", //
                adHourglassNodeStyleDescription);

        // create AcceptEventAction node description
        EClass domainType = this.umlPackage.getAcceptEventAction();
        NodeStyleDescription adFlagNodeStyleDescription = PapyrusCustomNodesFactory.eINSTANCE.createInnerFlagNodeStyleDescription();
        adFlagNodeStyleDescription.setColor(this.styleProvider.getNodeColor());
        adFlagNodeStyleDescription.setBorderColor(this.styleProvider.getBorderNodeColor());
        adFlagNodeStyleDescription.setBorderRadius(this.styleProvider.getNodeBorderRadius());
        adFlagNodeStyleDescription.setLabelColor(this.styleProvider.getNodeLabelColor());
        adFlagNodeStyleDescription.setShowIcon(true);

        NodeDescription adAcceptEventActionNodeDescription = this.newNodeBuilder(domainType, adFlagNodeStyleDescription) //
                .name(this.getIdBuilder().getSpecializedDomainNodeName(domainType, SHARED_SUFFIX)) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.GET_ACTIVITY_NODE_CANDIDATES)) //
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED) //
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(domainType.getName())) //
                .labelEditTool(this.getViewBuilder().createDirectEditTool(domainType.getName())) //
                .conditionalStyles(List.of(hourglassConditionalStyle)) //
                .build();
        adAcceptEventActionNodeDescription.setDefaultWidthExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.COMPUTE_ACCEPT_EVENT_ACTION_WIDTH));
        adAcceptEventActionNodeDescription.setDefaultHeightExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.COMPUTE_ACCEPT_EVENT_ACTION_HEIGHT));

        this.adSharedDescription.getChildrenDescriptions().add(adAcceptEventActionNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adAcceptEventActionNodeDescription);
        this.addToolSections(adAcceptEventActionNodeDescription, PIN);

        NodeTool adAcceptEventActionCreationTool = this.createActivityNodeCreationTool(this.umlPackage.getAcceptEventAction());
        this.reuseNodeAndCreateTool(adAcceptEventActionNodeDescription, diagramDescription, adAcceptEventActionCreationTool, ACCEPT_EVENT_ACTION, this.umlPackage.getActivity(),
                this.umlPackage.getActivityGroup());
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link ActionInputPin}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     */
    private void createSharedActionInputPinNodeDescription(DiagramDescription diagramDescription) {
        ImageNodeStyleDescription actionInputPinStyle = this.getViewBuilder().createImageNodeStyle(this.getImageForDomainType(this.umlPackage.getActionInputPin()), true);
        actionInputPinStyle.setPositionDependentRotation(true);
        NodeStyleDescription incomingOutgoingNodeStyleDescription = this.getViewBuilder().createImageNodeStyle(UUID.nameUUIDFromBytes(CONNECTED_PIN_IMAGE.getBytes()).toString(), true);
        ConditionalNodeStyle incomingOutgoingConditionalStyle = this.getViewBuilder().createConditionalNodeStyle(PIN_CONDITIONAL_STYLE_CONDITION, incomingOutgoingNodeStyleDescription);

        NodeDescription adActionInputPinDescription = new NodeDescriptionBuilder(this.getIdBuilder(), this.getQueryBuilder(), this.umlPackage.getActionInputPin(), actionInputPinStyle,
                this.getUmlMetaModelHelper()).name(this.getIdBuilder().getSpecializedDomainNodeName(this.umlPackage.getActionInputPin(), SHARED_SUFFIX)) //
                        .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED) //
                        .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.GET_ACTION_INPUT_PIN_CANDIDATES)).conditionalStyles(List.of(incomingOutgoingConditionalStyle)) //
                        .build();
        adActionInputPinDescription.setDefaultWidthExpression(BORDER_NODE_SIZE);
        adActionInputPinDescription.setDefaultHeightExpression(BORDER_NODE_SIZE);
        this.getViewBuilder().addDirectEditTool(adActionInputPinDescription);
        this.getViewBuilder().addDefaultDeleteTool(adActionInputPinDescription);
        this.adSharedDescription.getBorderNodesDescriptions().add(adActionInputPinDescription);

        this.createDefaultToolSectionsInNodeDescription(adActionInputPinDescription);

        NodeTool nodeTool = this.getViewBuilder().createCreationTool(this.getIdBuilder().getCreationToolId(this.umlPackage.getActionInputPin()), ActivityDiagramServices.CREATE_ACTION_INPUT_PIN,
                List.of(SELECTED_NODE, DIAGRAM_CONTEXT, CONVERTED_NODES));
        nodeTool.setPreconditionExpression(
                CallQuery.queryServiceOnSelf(ActivityDiagramServices.CAN_CREATE_INTO_PARENT, this.getQueryBuilder().aqlString(this.umlPackage.getActionInputPin().getName())));
        List<EClass> owners = List.of(this.umlPackage.getAddStructuralFeatureValueAction(), //
                this.umlPackage.getAddVariableValueAction(), //
                this.umlPackage.getBroadcastSignalAction(), //
                this.umlPackage.getClearAssociationAction(), //
                this.umlPackage.getClearStructuralFeatureAction(), //
                this.umlPackage.getConditionalNode(), //
                this.umlPackage.getCreateLinkAction(), //
                this.umlPackage.getCreateLinkObjectAction(), //
                this.umlPackage.getDestroyLinkAction(), //
                this.umlPackage.getDestroyObjectAction(), //
                this.umlPackage.getExpansionRegion(), //
                this.umlPackage.getLoopNode(), //
                this.umlPackage.getOpaqueAction(), //
                this.umlPackage.getReadIsClassifiedObjectAction(), //
                this.umlPackage.getReadLinkAction(), //
                this.umlPackage.getReclassifyObjectAction(), //
                this.umlPackage.getReduceAction(), //
                this.umlPackage.getSequenceNode(), //
                this.umlPackage.getStartObjectBehaviorAction(), //
                this.umlPackage.getStructuredActivityNode(), //
                this.umlPackage.getUnmarshallAction(), //
                this.umlPackage.getCallBehaviorAction(), //
                this.umlPackage.getCallOperationAction(), //
                this.umlPackage.getReadStructuralFeatureAction(), //
                this.umlPackage.getSendObjectAction(), //
                this.umlPackage.getSendSignalAction(), //
                this.umlPackage.getStartClassifierBehaviorAction(), //
                this.umlPackage.getTestIdentityAction() //
        );
        this.reuseNodeAndCreateTool(adActionInputPinDescription, diagramDescription, nodeTool, PIN, owners.toArray(EClass[]::new));
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link ActivityFinalNode}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     *
     * @see #createSharedCustomImageNodeDescription(NodeDescription, EClass, EReference, DiagramDescription)
     */
    private void createSharedActivityFinalNodeDescription(DiagramDescription diagramDescription) {
        this.createSharedCustomImageActivityNodeDescription(this.umlPackage.getActivityFinalNode(), this.umlPackage.getActivity_OwnedNode(), diagramDescription);
    }

    /**
     * Utility method easing the definition of {@link ActivityNode} creation tools.
     * <p>
     * This method is a shortcut for {@link #createCreationTool(String, String, List)} with a preset service that can be
     * used for all the {@link ActivityNode} subclasses. Use {@link #createCreationTool(String, String, List)} to create
     * a creation tool with a custom creation service.
     * </p>
     *
     * @return the created {@link NodeTool}
     * @see #createCreationTool(String, String, List)
     */
    private NodeTool createActivityNodeCreationTool(EClass newType) {
        return this.getViewBuilder().createCreationTool(this.getIdBuilder().getCreationToolId(newType), ActivityDiagramServices.CREATE_ACTIVITY_NODE,
                List.of(this.getQueryBuilder().aqlString(newType.getName()), this.getQueryBuilder().aqlString(this.umlPackage.getActivity_OwnedNode().getName()), SELECTED_NODE, DIAGRAM_CONTEXT,
                        CONVERTED_NODES));
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link ActivityParameterNode}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     */
    private void createSharedActivityParameterNodeNodeDescription(DiagramDescription diagramDescription) {
        NodeStyleDescription adActivityParameterNodeNodeStyleDescription = this.getViewBuilder().createRectangularNodeStyle(true, false);
        EClass activityParameterNodeEClass = this.umlPackage.getActivityParameterNode();

        NodeDescription adActivityParameterNodeDescription = this.newNodeBuilder(activityParameterNodeEClass, adActivityParameterNodeNodeStyleDescription)
                .name(this.getIdBuilder().getSpecializedDomainNodeName(activityParameterNodeEClass, SHARED_SUFFIX))
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.GET_ACTIVITY_NODE_CANDIDATES))
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(activityParameterNodeEClass.getName()))
                .labelEditTool(this.getViewBuilder().createDirectEditTool(activityParameterNodeEClass.getName()))
                .build();

        adActivityParameterNodeDescription.setDefaultWidthExpression("80");
        adActivityParameterNodeDescription.setDefaultHeightExpression("20");

        this.adSharedDescription.getBorderNodesDescriptions().add(adActivityParameterNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adActivityParameterNodeDescription);

        NodeTool adActivityParameterNodeCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getActivity_OwnedNode(), this.umlPackage.getActivityParameterNode());
        List<EClass> owners = List.of(this.umlPackage.getActivity());
        this.reuseNodeAndCreateTool(adActivityParameterNodeDescription, diagramDescription, adActivityParameterNodeCreationTool, NODES, owners.toArray(EClass[]::new));
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link ActivityPartition}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     */
    private void createSharedActivityPartitionDescription(DiagramDescription diagramDescription) {
        RectangularNodeStyleDescription rectangularNodeStyle = this.getViewBuilder().createRectangularNodeStyle(true, true);

        EClass activityPartitionEClass = this.umlPackage.getActivityPartition();
        NodeDescription adActivityPartitionDescription = this.newNodeBuilder(activityPartitionEClass, rectangularNodeStyle) //
                .name(this.getIdBuilder().getSpecializedDomainNodeName(activityPartitionEClass, SHARED_SUFFIX)) //
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription()) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.GET_ACTIVITY_PARTITION_CANDIDATES)) //
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED) //
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(activityPartitionEClass.getName())) //
                .labelEditTool(this.getViewBuilder().createDirectEditTool(activityPartitionEClass.getName())) //
                .build();
        adActivityPartitionDescription.setDefaultWidthExpression(CONTAINER_NODE_SIZE);
        adActivityPartitionDescription.setDefaultHeightExpression(CONTAINER_NODE_SIZE);
        this.adSharedDescription.getChildrenDescriptions().add(adActivityPartitionDescription);

        this.createDefaultToolSectionsInNodeDescription(adActivityPartitionDescription);
        this.addToolSections(adActivityPartitionDescription, ACTIVITY_GROUP, ACTIVITY_NODE, EXPANSION_REGION, INVOCATION_ACTION, CREATE_OBJECT_ACTION, STRUCTURED_ACTIVITY_NODE, STRUCTURAL_FEATURE,
                EXECUTABLE_NODE, ACCEPT_EVENT_ACTION);

        NodeTool subPartitionNodeTool = this.getViewBuilder().createCreationTool(this.umlPackage.getActivityPartition_Subpartition(), activityPartitionEClass);
        this.reuseNodeAndCreateTool(adActivityPartitionDescription, diagramDescription, subPartitionNodeTool, ACTIVITY_GROUP, activityPartitionEClass);
        NodeTool activityPartitionNodeTool = this.getViewBuilder().createCreationTool(this.umlPackage.getActivity_Partition(), activityPartitionEClass);
        this.reuseNodeAndCreateTool(adActivityPartitionDescription, diagramDescription, activityPartitionNodeTool, ACTIVITY_GROUP, this.umlPackage.getActivity());

        DropNodeTool adActivityPartitionGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(adActivityPartitionDescription));
        List<EClass> children = List.of(this.umlPackage.getActivityNode(), this.umlPackage.getActivityPartition(), this.umlPackage.getComment());
        this.registerCallback(adActivityPartitionDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            adActivityPartitionGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        adActivityPartitionDescription.getPalette().setDropNodeTool(adActivityPartitionGraphicalDropTool);
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link AddStructuralFeatureValueAction}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedAddStructuralFeatureValueActionNodeDescription(DiagramDescription diagramDescription) {
        NodeDescription adAddStructuralFeatureValueActionNodeDescription = this.createSharedRoundedRectangleActionNodeDescription(this.umlPackage.getAddStructuralFeatureValueAction());
        this.adSharedDescription.getChildrenDescriptions().add(adAddStructuralFeatureValueActionNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adAddStructuralFeatureValueActionNodeDescription);
        this.addToolSections(adAddStructuralFeatureValueActionNodeDescription, PIN);

        NodeTool adAddStructuralFeatureValueActionCreationTool = this.createActivityNodeCreationTool(this.umlPackage.getAddStructuralFeatureValueAction());
        this.reuseNodeAndCreateTool(adAddStructuralFeatureValueActionNodeDescription, diagramDescription, adAddStructuralFeatureValueActionCreationTool, STRUCTURAL_FEATURE,
                this.umlPackage.getActivity(), this.umlPackage.getActivityGroup());
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link BroadCastSignalAction}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedBroadcastSignalActionNodeDescription(DiagramDescription diagramDescription) {
        NodeDescription adBroadcastSignalActionNodeDescription = this.createSharedRoundedRectangleActionNodeDescription(this.umlPackage.getBroadcastSignalAction());
        this.adSharedDescription.getChildrenDescriptions().add(adBroadcastSignalActionNodeDescription);
        this.createDefaultToolSectionsInNodeDescription(adBroadcastSignalActionNodeDescription);
        this.addToolSections(adBroadcastSignalActionNodeDescription, PIN);

        NodeTool adBroadcastSignalActionCreationTool = this.createActivityNodeCreationTool(this.umlPackage.getBroadcastSignalAction());
        List<EClass> owners = List.of(this.umlPackage.getActivity(), //
                this.umlPackage.getActivityGroup());
        this.reuseNodeAndCreateTool(adBroadcastSignalActionNodeDescription, diagramDescription, adBroadcastSignalActionCreationTool, INVOCATION_ACTION, owners,
                List.of(this.umlPackage.getConditionalNode()));
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link CallBehaviorAction}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedCallBehaviorActionNodeDescription(DiagramDescription diagramDescription) {
        NodeDescription adCallBehaviorActionNodeDescription = this.createSharedRoundedRectangleActionNodeDescription(this.umlPackage.getCallBehaviorAction());
        this.adSharedDescription.getChildrenDescriptions().add(adCallBehaviorActionNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adCallBehaviorActionNodeDescription);
        this.addToolSections(adCallBehaviorActionNodeDescription, PIN);

        NodeTool adCallBehaviorActionCreationTool = this.createActivityNodeCreationTool(this.umlPackage.getCallBehaviorAction());
        this.reuseNodeAndCreateTool(adCallBehaviorActionNodeDescription, diagramDescription, adCallBehaviorActionCreationTool, INVOCATION_ACTION, this.umlPackage.getActivity(),
                this.umlPackage.getActivityGroup());
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link CallOperationAction}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedCallOperationActionNodeDescription(DiagramDescription diagramDescription) {
        NodeDescription adCallOperationActionNodeDescription = this.createSharedRoundedRectangleActionNodeDescription(this.umlPackage.getCallOperationAction());
        this.adSharedDescription.getChildrenDescriptions().add(adCallOperationActionNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adCallOperationActionNodeDescription);
        this.addToolSections(adCallOperationActionNodeDescription, PIN);

        NodeTool adCallOperationActionCreationTool = this.createActivityNodeCreationTool(this.umlPackage.getCallOperationAction());
        this.reuseNodeAndCreateTool(adCallOperationActionNodeDescription, diagramDescription, adCallOperationActionCreationTool, INVOCATION_ACTION, this.umlPackage.getActivity(),
                this.umlPackage.getActivityGroup());
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link ClearAssociationAction}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedClearAssociationActionNodeDescription(DiagramDescription diagramDescription) {
        NodeDescription adClearAssociationActionNodeDescription = this.createSharedRoundedRectangleActionNodeDescription(this.umlPackage.getClearAssociationAction());
        this.adSharedDescription.getChildrenDescriptions().add(adClearAssociationActionNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adClearAssociationActionNodeDescription);
        this.addToolSections(adClearAssociationActionNodeDescription, PIN);

        NodeTool nodeTool = this.createActivityNodeCreationTool(this.umlPackage.getClearAssociationAction());
        this.reuseNodeAndCreateTool(adClearAssociationActionNodeDescription, diagramDescription, nodeTool, EXECUTABLE_NODE, List.of(this.umlPackage.getActivity(), this.umlPackage.getActivityGroup()),
                List.of(this.umlPackage.getConditionalNode()));
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link ClearStructuralFeatureAction}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedClearStructuralFeatureActionNodeDescription(DiagramDescription diagramDescription) {
        NodeDescription adClearStructuralFeatureActionNodeDescription = this.createSharedRoundedRectangleActionNodeDescription(this.umlPackage.getClearStructuralFeatureAction());
        this.adSharedDescription.getChildrenDescriptions().add(adClearStructuralFeatureActionNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adClearStructuralFeatureActionNodeDescription);
        this.addToolSections(adClearStructuralFeatureActionNodeDescription, PIN);

        NodeTool adClearStructuralFeatureActionCreationTool = this.createActivityNodeCreationTool(this.umlPackage.getClearStructuralFeatureAction());
        List<EClass> owners = List.of(this.umlPackage.getActivity(), //
                this.umlPackage.getActivityGroup());
        this.reuseNodeAndCreateTool(adClearStructuralFeatureActionNodeDescription, diagramDescription, adClearStructuralFeatureActionCreationTool, STRUCTURAL_FEATURE, owners,
                List.of());
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link ConditionalNode}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     */
    private void createSharedConditionalNodeDescription(DiagramDescription diagramDescription) {
        RectangularNodeStyleDescription rectangularNodeStyle = this.getViewBuilder().createRectangularNodeStyle(false, true);
        rectangularNodeStyle.setBorderRadius(BORDER_RADIUS_SIZE);
        rectangularNodeStyle.setBorderLineStyle(LineStyle.DASH);
        EClass conditionalNodeEClass = this.umlPackage.getConditionalNode();
        NodeDescription adConditionalNodeDescription = this.newNodeBuilder(conditionalNodeEClass, rectangularNodeStyle) //
                .name(this.getIdBuilder().getSpecializedDomainNodeName(conditionalNodeEClass, SHARED_SUFFIX)) //
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription()) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.GET_ACTIVITY_NODE_CANDIDATES)) //
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED) //
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(conditionalNodeEClass.getName())) //
                .labelEditTool(this.getViewBuilder().createDirectEditTool(conditionalNodeEClass.getName())) //
                .build();
        adConditionalNodeDescription.setDefaultWidthExpression(CONTAINER_NODE_SIZE);
        adConditionalNodeDescription.setDefaultHeightExpression(CONTAINER_NODE_SIZE);
        this.adSharedDescription.getChildrenDescriptions().add(adConditionalNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adConditionalNodeDescription);
        this.addToolSections(adConditionalNodeDescription, PIN, ACTIVITY_NODE, EXPANSION_REGION, INVOCATION_ACTION, CREATE_OBJECT_ACTION, STRUCTURED_ACTIVITY_NODE, STRUCTURAL_FEATURE, EXECUTABLE_NODE,
                ACCEPT_EVENT_ACTION);

        NodeTool adConditionalNodeCreationTool = this.createStructuredActivityNodeCreationTool(conditionalNodeEClass);
        List<EClass> owners = List.of(this.umlPackage.getActivity(), //
                this.umlPackage.getActivityGroup());
        this.reuseNodeAndCreateTool(adConditionalNodeDescription, diagramDescription, adConditionalNodeCreationTool, STRUCTURED_ACTIVITY_NODE, owners, List.of());

        DropNodeTool adConditionalNodeGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(adConditionalNodeDescription));
        List<EClass> children = List.of(this.umlPackage.getActivityNode(), this.umlPackage.getComment(), this.umlPackage.getConstraint());
        this.registerCallback(adConditionalNodeDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            adConditionalNodeGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        adConditionalNodeDescription.getPalette().setDropNodeTool(adConditionalNodeGraphicalDropTool);
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link CreateObjectAction}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedCreateObjectActionNodeDescription(DiagramDescription diagramDescription) {
        NodeDescription adCreateObjectActionNodeDescription = this.createSharedRoundedRectangleActionNodeDescription(this.umlPackage.getCreateObjectAction());
        this.adSharedDescription.getChildrenDescriptions().add(adCreateObjectActionNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adCreateObjectActionNodeDescription);
        this.addToolSections(adCreateObjectActionNodeDescription, PIN);

        NodeTool adCreateObjectActionCreationTool = this.createActivityNodeCreationTool(this.umlPackage.getCreateObjectAction());
        List<EClass> owners = List.of(this.umlPackage.getActivity(), //
                this.umlPackage.getActivityGroup());
        this.reuseNodeAndCreateTool(adCreateObjectActionNodeDescription, diagramDescription, adCreateObjectActionCreationTool, CREATE_OBJECT_ACTION, owners,
                List.of());
    }

    /**
     * Creates an image-based {@link NodeDescription} representing the provided {@code domainType} with the given
     * {@code containmentReference}.
     *
     * @param domainType
     *            the type of the element to represent
     * @param containmentReference
     *            the containment reference of the element to represent
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     */
    private void createSharedCustomImageActivityNodeDescription(EClass domainType, EReference containmentReference, DiagramDescription diagramDescription) {
        ImageNodeStyleDescription imageNodeStyle = this.getViewBuilder().createImageNodeStyle(this.getImageForDomainType(domainType), true);
        imageNodeStyle.setBorderSize(0);

        NodeDescription nodeDescription = this.newNodeBuilder(domainType, imageNodeStyle) //
                .name(this.getIdBuilder().getSpecializedDomainNodeName(domainType, SHARED_SUFFIX)) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.GET_ACTIVITY_NODE_CANDIDATES)) //
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED) //
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(domainType.getName())) //
                .labelEditTool(this.getViewBuilder().createDirectEditTool(domainType.getName())).build();
        nodeDescription.setDefaultWidthExpression(SIZE_30);
        nodeDescription.setDefaultHeightExpression(SIZE_30);
        this.adSharedDescription.getChildrenDescriptions().add(nodeDescription);

        this.createDefaultToolSectionsInNodeDescription(nodeDescription);

        NodeTool nodeTool = this.createActivityNodeCreationTool(domainType);
        this.reuseNodeAndCreateTool(nodeDescription, diagramDescription, nodeTool, ACTIVITY_NODE, List.of(this.umlPackage.getActivity(), this.umlPackage.getActivityGroup()),
                List.of(this.umlPackage.getSequenceNode()));
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link DecisionNode}.
     * <p>
     * This method also creates the {@link NodeDescription} and {@link EdgeDescription} for the note associated to
     * {@link DecisionNode}s with a decision input.
     * </p>
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     *
     * @see #createSharedCustomImageNodeDescription(NodeDescription, EClass, EReference, DiagramDescription)
     */
    private void createSharedDecisionNodeDescription(DiagramDescription diagramDescription) {
        this.createSharedCustomImageActivityNodeDescription(this.umlPackage.getDecisionNode(), this.umlPackage.getActivity_OwnedNode(), diagramDescription);

        NodeStyleDescription nodeStyleDescription = this.getViewBuilder().createNoteNodeStyle();
        nodeStyleDescription.setShowIcon(false);
        nodeStyleDescription.setColor(this.styleProvider.getNoteColor());
        NodeDescription adDecisionNodeNoteDescription = this.newNodeBuilder(this.umlPackage.getDecisionNode(), nodeStyleDescription) //
                .name(this.getIdBuilder().getDomainNodeName(this.umlPackage.getDecisionNode()) + "_Note_" + SHARED_SUFFIX) //
                .labelExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.GET_DECISION_INPUT_NOTE_LABEL))
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.GET_ACTIVITY_NODE_CANDIDATES)) //
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED) //
                .build();
        adDecisionNodeNoteDescription
                .setPreconditionExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.SHOW_DECISION_NODE_NOTE, Variables.DIAGRAM_CONTEXT, "previousDiagram", Variables.EDITING_CONTEXT));

        adDecisionNodeNoteDescription.setDefaultWidthExpression(NoteStyleDescriptionBuilder.DEFAULT_NOTE_WIDTH);
        adDecisionNodeNoteDescription.setDefaultHeightExpression(NoteStyleDescriptionBuilder.DEFAULT_NOTE_HEIGHT);

        this.adSharedDescription.getChildrenDescriptions().add(adDecisionNodeNoteDescription);

        this.registerCallback(adDecisionNodeNoteDescription, () -> {
            Supplier<List<NodeDescription>> ownerNodeDescriptions = () -> this.collectNodesWithDomainAndFilter(diagramDescription,
                    List.of(this.umlPackage.getActivity(), this.umlPackage.getActivityGroup()), List.of(this.umlPackage.getSequenceNode()));
            this.reusedNodeDescriptionInOwners(adDecisionNodeNoteDescription, ownerNodeDescriptions.get());
        });

        Predicate<NodeDescription> isDecisionNodeNoteDescription = nodeDescription -> Objects.equals(nodeDescription.getName(), adDecisionNodeNoteDescription.getName());

        EdgeDescription adDecisionNodeNoteEdgeDescription = this.getViewBuilder().createFeatureEdgeDescription(//
                this.getIdBuilder().getFeatureBaseEdgeId(this.umlPackage.getDecisionNode_DecisionInput()), //
                this.getQueryBuilder().emptyString(), //
                this.getQueryBuilder().querySelf(),
                () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getDecisionNode()).stream().filter(isDecisionNodeNoteDescription.negate()).toList(), //
                () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getDecisionNode()).stream().filter(isDecisionNodeNoteDescription).toList());

        adDecisionNodeNoteEdgeDescription.getStyle().setTargetArrowStyle(ArrowStyle.NONE);
        adDecisionNodeNoteEdgeDescription.getStyle().setLineStyle(LineStyle.DASH);
        diagramDescription.getEdgeDescriptions().add(adDecisionNodeNoteEdgeDescription);
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link DestroyObjectAction}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedDestroyObjectActionNodeDescription(DiagramDescription diagramDescription) {
        NodeDescription adDestroyObjectActionNodeDescription = this.createSharedRoundedRectangleActionNodeDescription(this.umlPackage.getDestroyObjectAction());
        this.adSharedDescription.getChildrenDescriptions().add(adDestroyObjectActionNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adDestroyObjectActionNodeDescription);
        this.addToolSections(adDestroyObjectActionNodeDescription, PIN);

        NodeTool adDestroyObjectActionCreationTool = this.createActivityNodeCreationTool(this.umlPackage.getDestroyObjectAction());
        List<EClass> owners = List.of(this.umlPackage.getActivity(), //
                this.umlPackage.getActivityGroup());
        this.reuseNodeAndCreateTool(adDestroyObjectActionNodeDescription, diagramDescription, adDestroyObjectActionCreationTool, CREATE_OBJECT_ACTION, owners,
                List.of());
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link ExpansionRegion}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     */
    private void createSharedExpansionRegionNodeDescription(DiagramDescription diagramDescription) {
        RectangularNodeStyleDescription rectangularNodeStyle = this.getViewBuilder().createRectangularNodeStyle(false, true);
        rectangularNodeStyle.setBorderRadius(BORDER_RADIUS_SIZE);
        rectangularNodeStyle.setBorderLineStyle(LineStyle.DASH);
        EClass expansionRegionEClass = this.umlPackage.getExpansionRegion();
        NodeDescription adExpansionRegionDescription = this.newNodeBuilder(expansionRegionEClass, rectangularNodeStyle) //
                .name(this.getIdBuilder().getSpecializedDomainNodeName(expansionRegionEClass, SHARED_SUFFIX)) //
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription()) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.GET_ACTIVITY_NODE_CANDIDATES)) //
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED) //
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(expansionRegionEClass.getName())) //
                .labelEditTool(this.getViewBuilder().createDirectEditTool(expansionRegionEClass.getName())) //
                .build();
        adExpansionRegionDescription.setDefaultWidthExpression(CONTAINER_NODE_SIZE);
        adExpansionRegionDescription.setDefaultHeightExpression(CONTAINER_NODE_SIZE);
        this.adSharedDescription.getChildrenDescriptions().add(adExpansionRegionDescription);

        this.createDefaultToolSectionsInNodeDescription(adExpansionRegionDescription);
        this.addToolSections(adExpansionRegionDescription, ACTIVITY_NODE, PIN, EXPANSION_REGION, INVOCATION_ACTION, CREATE_OBJECT_ACTION, STRUCTURED_ACTIVITY_NODE, STRUCTURAL_FEATURE, EXECUTABLE_NODE,
                ACCEPT_EVENT_ACTION);

        this.createInputOutputExpansionNodeNodeDescription(adExpansionRegionDescription, diagramDescription);

        NodeTool nodeTool = this.createStructuredActivityNodeCreationTool(expansionRegionEClass);
        List<EClass> owners = List.of(this.umlPackage.getActivity(), this.umlPackage.getActivityGroup());
        this.reuseNodeAndCreateTool(adExpansionRegionDescription, diagramDescription, nodeTool, EXPANSION_REGION, owners, List.of());

        DropNodeTool adExpansionRegionGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(adExpansionRegionDescription));
        List<EClass> children = List.of(this.umlPackage.getActivityNode(), this.umlPackage.getComment(), this.umlPackage.getConstraint());
        this.registerCallback(adExpansionRegionDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            adExpansionRegionGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        adExpansionRegionDescription.getPalette().setDropNodeTool(adExpansionRegionGraphicalDropTool);
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link FlowFinalNode}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     *
     * @see #createSharedCustomImageNodeDescription(NodeDescription, EClass, EReference, DiagramDescription)
     */
    private void createSharedFlowFinalNodeDescription(DiagramDescription diagramDescription) {
        this.createSharedCustomImageActivityNodeDescription(this.umlPackage.getFlowFinalNode(), this.umlPackage.getActivity_OwnedNode(), diagramDescription);
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link ForkNode}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     */
    private void createSharedForkNodeDescription(DiagramDescription diagramDescription) {
        EClass forkNodeEClass = this.umlPackage.getForkNode();
        RectangleWithExternalLabelNodeStyleDescription nodeStyle = this.getViewBuilder().createRectangleWithExternalLabelNodeStyle();

        NodeDescription adForkNodeDescription = this.newNodeBuilder(forkNodeEClass, nodeStyle) //
                .name(this.getIdBuilder().getSpecializedDomainNodeName(forkNodeEClass, SHARED_SUFFIX)) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.GET_ACTIVITY_NODE_CANDIDATES)) //
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED) //
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(forkNodeEClass.getName())) //
                .labelEditTool(this.getViewBuilder().createDirectEditTool(forkNodeEClass.getName())).build();
        adForkNodeDescription.setDefaultWidthExpression(SIZE_50);
        adForkNodeDescription.setDefaultHeightExpression("150");
        this.adSharedDescription.getChildrenDescriptions().add(adForkNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adForkNodeDescription);

        NodeTool nodeTool = this.createActivityNodeCreationTool(forkNodeEClass);
        this.reuseNodeAndCreateTool(adForkNodeDescription, diagramDescription, nodeTool, ACTIVITY_NODE, List.of(this.umlPackage.getActivity(), this.umlPackage.getActivityGroup()),
                List.of(this.umlPackage.getSequenceNode()));
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link InitialNode}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     *
     * @see #createSharedCustomImageNodeDescription(NodeDescription, EClass, EReference, DiagramDescription)
     */
    private void createSharedInitialNodeDescription(DiagramDescription diagramDescription) {
        this.createSharedCustomImageActivityNodeDescription(this.umlPackage.getInitialNode(), this.umlPackage.getActivity_OwnedNode(), diagramDescription);
    }

    /**
     * Creates the {@link NodeDescription}s representing UML input/output {@link ExpansionNode}.
     * <p>
     * Input and output {@link ExpansionNode} are represented with the same EClass, but are stored using different
     * containment references. This method creates the two creation tools that allow to create input and output
     * {@link ExpansionNode}.
     * </p>
     *
     * @param parentDescription
     *            the {@link NodeDescription} containing the created {@link NodeDescription}
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     */
    private void createInputOutputExpansionNodeNodeDescription(NodeDescription parentDescription, DiagramDescription diagramDescription) {
        ImageNodeStyleDescription imageNodeStyle = this.getViewBuilder().createImageNodeStyle(this.getImageForDomainType(this.umlPackage.getExpansionNode()), true);
        EClass expansionNodeEClass = this.umlPackage.getExpansionNode();
        NodeDescription inputExpansionNodeDescription = this.newNodeBuilder(expansionNodeEClass, imageNodeStyle)
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.GET_EXPANSION_NODE_CANDIDATES))
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(expansionNodeEClass.getName()))
                .labelEditTool(this.getViewBuilder().createDirectEditTool(expansionNodeEClass.getName()))
                .build();

        inputExpansionNodeDescription.setDefaultWidthExpression(SIZE_30);
        inputExpansionNodeDescription.setDefaultHeightExpression("10");
        parentDescription.getBorderNodesDescriptions().add(inputExpansionNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(inputExpansionNodeDescription);

        NodeTool inputExpansionNodeTool = this.getViewBuilder().createCreationTool("New Input " + this.umlPackage.getExpansionNode().getName(), ActivityDiagramServices.CREATE_EXPANSION_NODE,
                List.of(SELECTED_NODE, DIAGRAM_CONTEXT, CONVERTED_NODES, String.valueOf(true)));
        this.reuseNodeAndCreateTool(inputExpansionNodeDescription, diagramDescription, inputExpansionNodeTool, EXPANSION_REGION, this.umlPackage.getExpansionRegion());
        NodeTool outputExpansionNodeTool = this.getViewBuilder().createCreationTool("New Output " + this.umlPackage.getExpansionNode().getName(), ActivityDiagramServices.CREATE_EXPANSION_NODE,
                List.of(SELECTED_NODE, DIAGRAM_CONTEXT, CONVERTED_NODES, String.valueOf(false)));
        this.reuseNodeAndCreateTool(inputExpansionNodeDescription, diagramDescription, outputExpansionNodeTool, EXPANSION_REGION, this.umlPackage.getExpansionRegion());
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link InputPin}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     */
    private void createSharedInputPinNodeDescription(DiagramDescription diagramDescription) {
        ImageNodeStyleDescription inputPinStyle = this.getViewBuilder().createImageNodeStyle(this.getImageForDomainType(this.umlPackage.getInputPin()), true);
        inputPinStyle.setPositionDependentRotation(true);
        NodeStyleDescription incomingOutgoingNodeStyleDescription = this.getViewBuilder().createImageNodeStyle(UUID.nameUUIDFromBytes(CONNECTED_PIN_IMAGE.getBytes()).toString(), true);
        ConditionalNodeStyle incomingOutgoingConditionalStyle = this.getViewBuilder().createConditionalNodeStyle(PIN_CONDITIONAL_STYLE_CONDITION, incomingOutgoingNodeStyleDescription);

        NodeDescription adInputPinDescription = new NodeDescriptionBuilder(this.getIdBuilder(), this.getQueryBuilder(), this.umlPackage.getInputPin(), inputPinStyle, this.getUmlMetaModelHelper())
                .name(this.getIdBuilder().getSpecializedDomainNodeName(this.umlPackage.getInputPin(), SHARED_SUFFIX)) //
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.GET_INPUT_PIN_CANDIDATES)).conditionalStyles(List.of(incomingOutgoingConditionalStyle)) //
                .build();
        adInputPinDescription.setDefaultWidthExpression(BORDER_NODE_SIZE);
        adInputPinDescription.setDefaultHeightExpression(BORDER_NODE_SIZE);
        this.getViewBuilder().addDirectEditTool(adInputPinDescription);
        this.getViewBuilder().addDefaultDeleteTool(adInputPinDescription);
        this.adSharedDescription.getBorderNodesDescriptions().add(adInputPinDescription);

        this.createDefaultToolSectionsInNodeDescription(adInputPinDescription);

        NodeTool nodeTool = this.getViewBuilder().createCreationTool(this.getIdBuilder().getCreationToolId(this.umlPackage.getInputPin()), ActivityDiagramServices.CREATE_INPUT_PIN,
                List.of(SELECTED_NODE, DIAGRAM_CONTEXT, CONVERTED_NODES));
        nodeTool.setPreconditionExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.CAN_CREATE_INTO_PARENT, this.getQueryBuilder().aqlString(this.umlPackage.getInputPin().getName())));
        List<EClass> owners = List.of(this.umlPackage.getBroadcastSignalAction(), //
                this.umlPackage.getConditionalNode(), //
                this.umlPackage.getCreateLinkAction(), //
                this.umlPackage.getCreateLinkObjectAction(), //
                this.umlPackage.getDestroyLinkAction(), //
                this.umlPackage.getExpansionRegion(), //
                this.umlPackage.getLoopNode(), //
                this.umlPackage.getOpaqueAction(), //
                this.umlPackage.getClearAssociationAction(), //
                this.umlPackage.getReduceAction(), //
                this.umlPackage.getStartClassifierBehaviorAction(), //
                this.umlPackage.getReadLinkAction(), //
                this.umlPackage.getSequenceNode(), //
                this.umlPackage.getStartObjectBehaviorAction(), //
                this.umlPackage.getStructuredActivityNode(), //
                this.umlPackage.getAddStructuralFeatureValueAction(), //
                this.umlPackage.getAddVariableValueAction(), //
                this.umlPackage.getCallBehaviorAction(), //
                this.umlPackage.getCallOperationAction(), //
                this.umlPackage.getClearStructuralFeatureAction(), //
                this.umlPackage.getDestroyObjectAction(), //
                this.umlPackage.getReadIsClassifiedObjectAction(), //
                this.umlPackage.getReadStructuralFeatureAction(), //
                this.umlPackage.getReclassifyObjectAction(), //
                this.umlPackage.getSendObjectAction(), //
                this.umlPackage.getSendSignalAction(), //
                this.umlPackage.getStartClassifierBehaviorAction(), //
                this.umlPackage.getTestIdentityAction(), //
                this.umlPackage.getUnmarshallAction()//
        );
        this.reuseNodeAndCreateTool(adInputPinDescription, diagramDescription, nodeTool, PIN, owners.toArray(EClass[]::new));
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link InterruptibleActivityRegion}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     */
    private void createSharedInterruptibleActivityRegionDescription(DiagramDescription diagramDescription) {
        RectangularNodeStyleDescription rectangularNodeStyle = this.getViewBuilder().createRectangularNodeStyle(false, false);
        rectangularNodeStyle.setBorderRadius(BORDER_RADIUS_SIZE);
        rectangularNodeStyle.setBorderLineStyle(LineStyle.DASH);
        EClass interruptibleActivityRegionEClass = this.umlPackage.getInterruptibleActivityRegion();
        NodeDescription adInterruptibleActivityRegionDescription = this.newNodeBuilder(interruptibleActivityRegionEClass, rectangularNodeStyle) //
                .name(this.getIdBuilder().getSpecializedDomainNodeName(interruptibleActivityRegionEClass, SHARED_SUFFIX)) //
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription()) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.GET_INTERRUPTIBLE_ACTIVITY_REGION_CANDIDATES)) //
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED) //
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(interruptibleActivityRegionEClass.getName())) //
                .labelEditTool(this.getViewBuilder().createDirectEditTool(interruptibleActivityRegionEClass.getName())) //
                .build();
        adInterruptibleActivityRegionDescription.setDefaultWidthExpression(CONTAINER_NODE_SIZE);
        adInterruptibleActivityRegionDescription.setDefaultHeightExpression(CONTAINER_NODE_SIZE);
        adInterruptibleActivityRegionDescription.setLabelExpression("");
        this.adSharedDescription.getChildrenDescriptions().add(adInterruptibleActivityRegionDescription);

        this.createDefaultToolSectionsInNodeDescription(adInterruptibleActivityRegionDescription);
        this.addToolSections(adInterruptibleActivityRegionDescription, ACTIVITY_NODE, EXPANSION_REGION, INVOCATION_ACTION, CREATE_OBJECT_ACTION, STRUCTURED_ACTIVITY_NODE, STRUCTURAL_FEATURE,
                EXECUTABLE_NODE, ACCEPT_EVENT_ACTION);

        NodeTool nodeTool = this.getViewBuilder().createCreationTool(this.umlPackage.getActivity_OwnedGroup(), interruptibleActivityRegionEClass);
        this.reuseNodeAndCreateTool(adInterruptibleActivityRegionDescription, diagramDescription, nodeTool, ACTIVITY_GROUP, this.umlPackage.getActivity());

        DropNodeTool adInterruptibleActivityRegionGraphicalDropTool = this.getViewBuilder()
                .createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(adInterruptibleActivityRegionDescription));
        List<EClass> children = List.of(this.umlPackage.getActivityNode(), this.umlPackage.getComment());
        this.registerCallback(adInterruptibleActivityRegionDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            adInterruptibleActivityRegionGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        adInterruptibleActivityRegionDescription.getPalette().setDropNodeTool(adInterruptibleActivityRegionGraphicalDropTool);
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link JoinNode}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     */
    private void createSharedJoinNodeDescription(DiagramDescription diagramDescription) {
        EClass joinNodeEClass = this.umlPackage.getJoinNode();
        RectangleWithExternalLabelNodeStyleDescription nodeStyle = this.getViewBuilder().createRectangleWithExternalLabelNodeStyle();

        NodeDescription adJoinNodeDescription = this.newNodeBuilder(joinNodeEClass, nodeStyle) //
                .name(this.getIdBuilder().getSpecializedDomainNodeName(joinNodeEClass, SHARED_SUFFIX)) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.GET_ACTIVITY_NODE_CANDIDATES)) //
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED) //
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(joinNodeEClass.getName())) //
                .labelEditTool(this.getViewBuilder().createDirectEditTool(joinNodeEClass.getName())).build();
        adJoinNodeDescription.setDefaultWidthExpression(SIZE_50);
        adJoinNodeDescription.setDefaultHeightExpression("150");
        this.adSharedDescription.getChildrenDescriptions().add(adJoinNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adJoinNodeDescription);

        NodeTool nodeTool = this.createActivityNodeCreationTool(joinNodeEClass);
        this.reuseNodeAndCreateTool(adJoinNodeDescription, diagramDescription, nodeTool, ACTIVITY_NODE, List.of(this.umlPackage.getActivity(), this.umlPackage.getActivityGroup()),
                List.of(this.umlPackage.getSequenceNode()));
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link LoopNode}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     */
    private void createSharedLoopNodeDescription(DiagramDescription diagramDescription) {
        RectangularNodeStyleDescription rectangularNodeStyle = this.getViewBuilder().createRectangularNodeStyle(false, true);
        rectangularNodeStyle.setBorderRadius(BORDER_RADIUS_SIZE);
        rectangularNodeStyle.setBorderLineStyle(LineStyle.DASH);
        EClass loopNodeEClass = this.umlPackage.getLoopNode();
        NodeDescription adLoopNodeDescription = this.newNodeBuilder(loopNodeEClass, rectangularNodeStyle) //
                .name(this.getIdBuilder().getSpecializedDomainNodeName(loopNodeEClass, SHARED_SUFFIX)) //
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription()) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.GET_ACTIVITY_NODE_CANDIDATES)) //
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED) //
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(loopNodeEClass.getName())) //
                .labelEditTool(this.getViewBuilder().createDirectEditTool(loopNodeEClass.getName())) //
                .build();
        adLoopNodeDescription.setDefaultWidthExpression(CONTAINER_NODE_SIZE);
        adLoopNodeDescription.setDefaultHeightExpression(CONTAINER_NODE_SIZE);
        this.adSharedDescription.getChildrenDescriptions().add(adLoopNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adLoopNodeDescription);
        this.addToolSections(adLoopNodeDescription, ACTIVITY_NODE, PIN, EXPANSION_REGION, INVOCATION_ACTION, CREATE_OBJECT_ACTION, STRUCTURED_ACTIVITY_NODE, STRUCTURAL_FEATURE, EXECUTABLE_NODE,
                ACCEPT_EVENT_ACTION);

        NodeTool adLoopNodeCreationTool = this.createStructuredActivityNodeCreationTool(loopNodeEClass);
        List<EClass> owners = List.of(this.umlPackage.getActivity(), //
                this.umlPackage.getActivityGroup());
        this.reuseNodeAndCreateTool(adLoopNodeDescription, diagramDescription, adLoopNodeCreationTool, STRUCTURED_ACTIVITY_NODE, owners, List.of());

        DropNodeTool adLoopNodeGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(adLoopNodeDescription));
        List<EClass> children = List.of(this.umlPackage.getActivityNode(), this.umlPackage.getComment(), this.umlPackage.getConstraint());
        this.registerCallback(adLoopNodeDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            adLoopNodeGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        adLoopNodeDescription.getPalette().setDropNodeTool(adLoopNodeGraphicalDropTool);
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link MergeNode}
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     *
     * @see #createSharedCustomImageNodeDescription(NodeDescription, EClass, EReference, DiagramDescription)
     */
    private void createSharedMergeNodeDescription(DiagramDescription diagramDescription) {
        this.createSharedCustomImageActivityNodeDescription(this.umlPackage.getMergeNode(), this.umlPackage.getActivity_OwnedNode(), diagramDescription);
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link OpaqueAction}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedOpaqueActionNodeDescription(DiagramDescription diagramDescription) {
        NodeDescription adOpaqueActionDescription = this.createSharedRoundedRectangleActionNodeDescription(this.umlPackage.getOpaqueAction());
        this.adSharedDescription.getChildrenDescriptions().add(adOpaqueActionDescription);

        this.createDefaultToolSectionsInNodeDescription(adOpaqueActionDescription);
        this.addToolSections(adOpaqueActionDescription, PIN);

        NodeTool nodeTool = this.createActivityNodeCreationTool(this.umlPackage.getOpaqueAction());
        this.reuseNodeAndCreateTool(adOpaqueActionDescription, diagramDescription, nodeTool, EXECUTABLE_NODE, List.of(this.umlPackage.getActivity(), this.umlPackage.getActivityGroup()),
                List.of());

    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link OutputPin}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     */
    private void createSharedOutputPinNodeDescription(DiagramDescription diagramDescription) {
        ImageNodeStyleDescription outputPinStyle = this.getViewBuilder().createImageNodeStyle(this.getImageForDomainType(this.umlPackage.getOutputPin()), true);
        outputPinStyle.setPositionDependentRotation(true);
        NodeStyleDescription incomingOutgoingNodeStyleDescription = this.getViewBuilder().createImageNodeStyle(UUID.nameUUIDFromBytes(CONNECTED_PIN_IMAGE.getBytes()).toString(), true);
        ConditionalNodeStyle incomingOutgoingConditionalStyle = this.getViewBuilder().createConditionalNodeStyle(PIN_CONDITIONAL_STYLE_CONDITION, incomingOutgoingNodeStyleDescription);

        NodeDescription adOutputPinDescription = new NodeDescriptionBuilder(this.getIdBuilder(), this.getQueryBuilder(), this.umlPackage.getOutputPin(), outputPinStyle, this.getUmlMetaModelHelper())
                .name(this.getIdBuilder().getSpecializedDomainNodeName(this.umlPackage.getOutputPin(), SHARED_SUFFIX)) //
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.GET_OUTPUT_PIN_CANDIDATES)) //
                .conditionalStyles(List.of(incomingOutgoingConditionalStyle)) //
                .build();
        adOutputPinDescription.setDefaultWidthExpression(BORDER_NODE_SIZE);
        adOutputPinDescription.setDefaultHeightExpression(BORDER_NODE_SIZE);
        this.getViewBuilder().addDirectEditTool(adOutputPinDescription);
        this.getViewBuilder().addDefaultDeleteTool(adOutputPinDescription);
        this.adSharedDescription.getBorderNodesDescriptions().add(adOutputPinDescription);

        this.createDefaultToolSectionsInNodeDescription(adOutputPinDescription);

        NodeTool nodeTool = this.getViewBuilder().createCreationTool(this.getIdBuilder().getCreationToolId(this.umlPackage.getOutputPin()), ActivityDiagramServices.CREATE_OUTPUT_PIN,
                List.of(SELECTED_NODE, DIAGRAM_CONTEXT, CONVERTED_NODES));
        nodeTool.setPreconditionExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.CAN_CREATE_INTO_PARENT, this.getQueryBuilder().aqlString(this.umlPackage.getOutputPin().getName())));
        List<EClass> owners = List.of(this.umlPackage.getAcceptEventAction(), //
                this.umlPackage.getAddStructuralFeatureValueAction(), //
                this.umlPackage.getClearStructuralFeatureAction(), //
                this.umlPackage.getConditionalNode(), //
                this.umlPackage.getCreateLinkObjectAction(), //
                this.umlPackage.getExpansionRegion(), //
                this.umlPackage.getLoopNode(), //
                this.umlPackage.getOpaqueAction(), //
                this.umlPackage.getValueSpecificationAction(), //
                this.umlPackage.getReadExtentAction(), //
                this.umlPackage.getReadIsClassifiedObjectAction(), //
                this.umlPackage.getReadLinkAction(), //
                this.umlPackage.getReduceAction(), //
                this.umlPackage.getSequenceNode(), //
                this.umlPackage.getStartObjectBehaviorAction(), //
                this.umlPackage.getStructuredActivityNode(), //
                this.umlPackage.getUnmarshallAction(), //
                this.umlPackage.getAcceptCallAction(), //
                this.umlPackage.getCallBehaviorAction(), //
                this.umlPackage.getCallOperationAction(), //
                this.umlPackage.getCreateObjectAction(), //
                this.umlPackage.getReadStructuralFeatureAction(), //
                this.umlPackage.getReadVariableAction(), //
                this.umlPackage.getReadSelfAction(), //
                this.umlPackage.getTestIdentityAction(), //
                this.umlPackage.getValueSpecificationAction());
        this.reuseNodeAndCreateTool(adOutputPinDescription, diagramDescription, nodeTool, PIN, owners.toArray(EClass[]::new));
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link ReadExtentAction}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedReadExtentActionNodeDescription(DiagramDescription diagramDescription) {
        NodeDescription adReadExtentActionNodeDescription = this.createSharedRoundedRectangleActionNodeDescription(this.umlPackage.getReadExtentAction());
        this.adSharedDescription.getChildrenDescriptions().add(adReadExtentActionNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adReadExtentActionNodeDescription);
        this.addToolSections(adReadExtentActionNodeDescription, PIN);

        NodeTool nodeTool = this.createActivityNodeCreationTool(this.umlPackage.getReadExtentAction());
        this.reuseNodeAndCreateTool(adReadExtentActionNodeDescription, diagramDescription, nodeTool, EXECUTABLE_NODE, List.of(this.umlPackage.getActivity(), this.umlPackage.getActivityGroup()),
                List.of());
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link ReadIsClassifiedObjectAction}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedReadIsClassifiedObjectActionNodeDescription(DiagramDescription diagramDescription) {
        NodeDescription adReadIsClassifiedObjectActionNodeDescription = this.createSharedRoundedRectangleActionNodeDescription(this.umlPackage.getReadIsClassifiedObjectAction());
        this.adSharedDescription.getChildrenDescriptions().add(adReadIsClassifiedObjectActionNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adReadIsClassifiedObjectActionNodeDescription);
        this.addToolSections(adReadIsClassifiedObjectActionNodeDescription, PIN);

        NodeTool adReadIsClassifiedObjectActionCreationTool = this.createActivityNodeCreationTool(this.umlPackage.getReadIsClassifiedObjectAction());
        List<EClass> owners = List.of(this.umlPackage.getActivity(), //
                this.umlPackage.getActivityGroup());
        this.reuseNodeAndCreateTool(adReadIsClassifiedObjectActionNodeDescription, diagramDescription, adReadIsClassifiedObjectActionCreationTool, CREATE_OBJECT_ACTION, owners,
                List.of());
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link ReadSelfAction}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedReadSelfActionNodeDescription(DiagramDescription diagramDescription) {
        NodeDescription adReadSelfActionNodeDescription = this.createSharedRoundedRectangleActionNodeDescription(this.umlPackage.getReadSelfAction());
        this.adSharedDescription.getChildrenDescriptions().add(adReadSelfActionNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adReadSelfActionNodeDescription);
        this.addToolSections(adReadSelfActionNodeDescription, PIN);

        NodeTool adReadSelfActionCreationTool = this.createActivityNodeCreationTool(this.umlPackage.getReadSelfAction());
        List<EClass> owners = List.of(this.umlPackage.getActivity(), //
                this.umlPackage.getActivityGroup());
        this.reuseNodeAndCreateTool(adReadSelfActionNodeDescription, diagramDescription, adReadSelfActionCreationTool, CREATE_OBJECT_ACTION, owners, List.of());
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link ReadStructuralFeatureAction}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedReadStructuralFeatureActionNodeDescription(DiagramDescription diagramDescription) {
        NodeDescription adReadStructuralFeatureActionNodeDescription = this.createSharedRoundedRectangleActionNodeDescription(this.umlPackage.getReadStructuralFeatureAction());
        this.adSharedDescription.getChildrenDescriptions().add(adReadStructuralFeatureActionNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adReadStructuralFeatureActionNodeDescription);
        this.addToolSections(adReadStructuralFeatureActionNodeDescription, PIN);

        NodeTool adReadStructuralFeatureActionCreationTool = this.createActivityNodeCreationTool(this.umlPackage.getReadStructuralFeatureAction());
        List<EClass> owners = List.of(this.umlPackage.getActivity(), //
                this.umlPackage.getActivityGroup());
        this.reuseNodeAndCreateTool(adReadStructuralFeatureActionNodeDescription, diagramDescription, adReadStructuralFeatureActionCreationTool, STRUCTURAL_FEATURE, owners,
                List.of());
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link ReclassifyObjectAction}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedReclassifyObjectActionNodeDescription(DiagramDescription diagramDescription) {
        NodeDescription adReclassifyObjectActionNodeDescription = this.createSharedRoundedRectangleActionNodeDescription(this.umlPackage.getReclassifyObjectAction());
        this.adSharedDescription.getChildrenDescriptions().add(adReclassifyObjectActionNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adReclassifyObjectActionNodeDescription);
        this.addToolSections(adReclassifyObjectActionNodeDescription, PIN);

        NodeTool adReclassifyObjectActionCreationTool = this.createActivityNodeCreationTool(this.umlPackage.getReclassifyObjectAction());
        List<EClass> owners = List.of(this.umlPackage.getActivity(), //
                this.umlPackage.getActivityGroup());
        this.reuseNodeAndCreateTool(adReclassifyObjectActionNodeDescription, diagramDescription, adReclassifyObjectActionCreationTool, CREATE_OBJECT_ACTION, owners,
                List.of());
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link ReduceAction}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedReduceActionNodeDescription(DiagramDescription diagramDescription) {
        NodeDescription adReduceActionNodeDescription = this.createSharedRoundedRectangleActionNodeDescription(this.umlPackage.getReduceAction());
        this.adSharedDescription.getChildrenDescriptions().add(adReduceActionNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adReduceActionNodeDescription);
        this.addToolSections(adReduceActionNodeDescription, PIN);

        NodeTool nodeTool = this.createActivityNodeCreationTool(this.umlPackage.getReduceAction());
        this.reuseNodeAndCreateTool(adReduceActionNodeDescription, diagramDescription, nodeTool, EXECUTABLE_NODE, List.of(this.umlPackage.getActivity(), this.umlPackage.getActivityGroup()),
                List.of());
    }

    /**
     * Creates a rounded-rectangle {@link NodeDescription} representing the {@code domainType} UML action.
     *
     * @param domainType
     *            the type of the UML action to represent
     * @return the created {@link NodeDescription}
     */
    private NodeDescription createSharedRoundedRectangleActionNodeDescription(EClass domainType) {
        NodeStyleDescription rectangularNodeStyle = this.getViewBuilder().createRectangularNodeStyle(true, false);
        rectangularNodeStyle.setBorderRadius(BORDER_RADIUS_SIZE);
        NodeDescription roundedRectangleNodeDescription = this.newNodeBuilder(domainType, rectangularNodeStyle) //
                .name(this.getIdBuilder().getSpecializedDomainNodeName(domainType, SHARED_SUFFIX)) //
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription()) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.GET_ACTIVITY_NODE_CANDIDATES)) //
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED) //
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(domainType.getName())) //
                .labelEditTool(this.getViewBuilder().createDirectEditTool(domainType.getName())) //
                .build();
        roundedRectangleNodeDescription.setDefaultHeightExpression(SIZE_50);
        roundedRectangleNodeDescription.setDefaultWidthExpression("200");
        return roundedRectangleNodeDescription;
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link SendObjectAction}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedSendObjectActionNodeDescription(DiagramDescription diagramDescription) {
        NodeDescription adSendObjectActionNodeDescription = this.createSharedRoundedRectangleActionNodeDescription(this.umlPackage.getSendObjectAction());
        this.adSharedDescription.getChildrenDescriptions().add(adSendObjectActionNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adSendObjectActionNodeDescription);
        this.addToolSections(adSendObjectActionNodeDescription, PIN);

        NodeTool adSendObjectActionCreationTool = this.createActivityNodeCreationTool(this.umlPackage.getSendObjectAction());
        List<EClass> owners = List.of(this.umlPackage.getActivity(), this.umlPackage.getActivityGroup());
        this.reuseNodeAndCreateTool(adSendObjectActionNodeDescription, diagramDescription, adSendObjectActionCreationTool, INVOCATION_ACTION, owners, List.of());
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link SendSignalAction}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedSendSignalActionNodeDescription(DiagramDescription diagramDescription) {
        EClass domainType = this.umlPackage.getSendSignalAction();
        NodeStyleDescription adSendSignalActionNodeStyleDescription = PapyrusCustomNodesFactory.eINSTANCE.createOuterFlagNodeStyleDescription();
        adSendSignalActionNodeStyleDescription.setColor(this.styleProvider.getNodeColor());
        adSendSignalActionNodeStyleDescription.setBorderColor(this.styleProvider.getBorderNodeColor());
        adSendSignalActionNodeStyleDescription.setBorderRadius(this.styleProvider.getNodeBorderRadius());
        adSendSignalActionNodeStyleDescription.setLabelColor(this.styleProvider.getNodeLabelColor());
        adSendSignalActionNodeStyleDescription.setShowIcon(true);

        NodeDescription adSendSignalActionNodeDescription = this.newNodeBuilder(domainType, adSendSignalActionNodeStyleDescription) //
                .name(this.getIdBuilder().getSpecializedDomainNodeName(domainType, SHARED_SUFFIX)) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.GET_ACTIVITY_NODE_CANDIDATES)) //
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED) //
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(domainType.getName())) //
                .labelEditTool(this.getViewBuilder().createDirectEditTool(domainType.getName())).build();
        adSendSignalActionNodeDescription.setDefaultWidthExpression("170");
        adSendSignalActionNodeDescription.setDefaultHeightExpression("70");
        this.adSharedDescription.getChildrenDescriptions().add(adSendSignalActionNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adSendSignalActionNodeDescription);
        this.addToolSections(adSendSignalActionNodeDescription, PIN);

        NodeTool adSendSignalActionCreationTool = this.createActivityNodeCreationTool(this.umlPackage.getSendSignalAction());
        this.reuseNodeAndCreateTool(adSendSignalActionNodeDescription, diagramDescription, adSendSignalActionCreationTool, INVOCATION_ACTION, this.umlPackage.getActivity(),
                this.umlPackage.getActivityGroup());
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link SequenceNode}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     */
    private void createSharedSequenceNodeDescription(DiagramDescription diagramDescription) {
        RectangularNodeStyleDescription rectangularNodeStyle = this.getViewBuilder().createRectangularNodeStyle(false, true);
        rectangularNodeStyle.setBorderRadius(BORDER_RADIUS_SIZE);
        rectangularNodeStyle.setBorderLineStyle(LineStyle.DASH);
        EClass sequenceNodeEClass = this.umlPackage.getSequenceNode();
        NodeDescription adSequenceNodeDescription = this.newNodeBuilder(sequenceNodeEClass, rectangularNodeStyle) //
                .name(this.getIdBuilder().getSpecializedDomainNodeName(sequenceNodeEClass, SHARED_SUFFIX)) //
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription()) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.GET_ACTIVITY_NODE_CANDIDATES)) //
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED) //
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(sequenceNodeEClass.getName())) //
                .labelEditTool(this.getViewBuilder().createDirectEditTool(sequenceNodeEClass.getName())) //
                .build();
        adSequenceNodeDescription.setDefaultWidthExpression(CONTAINER_NODE_SIZE);
        adSequenceNodeDescription.setDefaultHeightExpression(CONTAINER_NODE_SIZE);
        this.adSharedDescription.getChildrenDescriptions().add(adSequenceNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adSequenceNodeDescription);
        this.addToolSections(adSequenceNodeDescription, PIN, ACTIVITY_NODE, EXPANSION_REGION, INVOCATION_ACTION, CREATE_OBJECT_ACTION, STRUCTURED_ACTIVITY_NODE, STRUCTURAL_FEATURE, EXECUTABLE_NODE,
                ACCEPT_EVENT_ACTION);
        NodeTool adSequenceNodeCreationTool = this.createStructuredActivityNodeCreationTool(sequenceNodeEClass);
        List<EClass> owners = List.of(this.umlPackage.getActivity(), //
                this.umlPackage.getActivityGroup());
        this.reuseNodeAndCreateTool(adSequenceNodeDescription, diagramDescription, adSequenceNodeCreationTool, STRUCTURED_ACTIVITY_NODE, owners, List.of());

        DropNodeTool adSequenceNodeGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(adSequenceNodeDescription));
        List<EClass> children = List.of(this.umlPackage.getActivityNode(), this.umlPackage.getComment(), this.umlPackage.getConstraint());
        this.registerCallback(adSequenceNodeDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            adSequenceNodeGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        adSequenceNodeDescription.getPalette().setDropNodeTool(adSequenceNodeGraphicalDropTool);
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link StartClassifierBehaviorAction}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     *
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedStartClassifierBehaviorActionNodeDescription(DiagramDescription diagramDescription) {
        NodeDescription adStartClassifierBehaviorActionNodeDescription = this.createSharedRoundedRectangleActionNodeDescription(this.umlPackage.getStartClassifierBehaviorAction());
        this.adSharedDescription.getChildrenDescriptions().add(adStartClassifierBehaviorActionNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adStartClassifierBehaviorActionNodeDescription);
        this.addToolSections(adStartClassifierBehaviorActionNodeDescription, PIN);

        NodeTool nodeTool = this.createActivityNodeCreationTool(this.umlPackage.getStartClassifierBehaviorAction());
        this.reuseNodeAndCreateTool(adStartClassifierBehaviorActionNodeDescription, diagramDescription, nodeTool, EXECUTABLE_NODE,
                List.of(this.umlPackage.getActivity(), this.umlPackage.getActivityGroup()), List.of());
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link StartObjectBehaviorAction}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedStartObjectBehaviorActionNodeDescription(DiagramDescription diagramDescription) {
        NodeDescription adStartObjectBehaviorActionNodeDescription = this.createSharedRoundedRectangleActionNodeDescription(this.umlPackage.getStartObjectBehaviorAction());
        this.adSharedDescription.getChildrenDescriptions().add(adStartObjectBehaviorActionNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adStartObjectBehaviorActionNodeDescription);
        this.addToolSections(adStartObjectBehaviorActionNodeDescription, PIN);

        NodeTool adStartObjectBehaviorActionCreationTool = this.createActivityNodeCreationTool(this.umlPackage.getStartObjectBehaviorAction());
        List<EClass> owners = List.of(this.umlPackage.getActivity(), //
                this.umlPackage.getActivityGroup());
        this.reuseNodeAndCreateTool(adStartObjectBehaviorActionNodeDescription, diagramDescription, adStartObjectBehaviorActionCreationTool, INVOCATION_ACTION, owners,
                List.of());
    }

    /**
     * Utility method easing the definition of {@link StructuredActivityNode} creation tools.
     * <p>
     * This method is a shortcut for {@link #createCreationTool(String, String, List)} with a preset service that can be
     * used for all the {@link StructuredActivityNode} subclasses. Use {@link #createCreationTool(String, String, List)}
     * to create a creation tool with a custom creation service.
     * </p>
     *
     * @return the created {@link NodeTool}
     * @see #createCreationTool(String, String, List)
     */
    private NodeTool createStructuredActivityNodeCreationTool(EClass newType) {
        return this.getViewBuilder().createCreationTool(this.getIdBuilder().getCreationToolId(newType), ActivityDiagramServices.CREATE_ACTIVITY_NODE,
                List.of(this.getQueryBuilder().aqlString(newType.getName()), this.getQueryBuilder().aqlString(this.umlPackage.getActivity_StructuredNode().getName()), SELECTED_NODE, DIAGRAM_CONTEXT,
                        CONVERTED_NODES));
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link StructuredActivityNode}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     */
    private void createSharedStructuredActivityNodeDescription(DiagramDescription diagramDescription) {
        RectangularNodeStyleDescription rectangularNodeStyle = this.getViewBuilder().createRectangularNodeStyle(false, true);
        rectangularNodeStyle.setBorderRadius(BORDER_RADIUS_SIZE);
        rectangularNodeStyle.setBorderLineStyle(LineStyle.DASH);
        EClass structuredActivityNodeEClass = this.umlPackage.getStructuredActivityNode();
        NodeDescription adStructuredActivityNodeDescription = this.newNodeBuilder(structuredActivityNodeEClass, rectangularNodeStyle) //
                .name(this.getIdBuilder().getSpecializedDomainNodeName(structuredActivityNodeEClass, SHARED_SUFFIX)) //
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription()) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.GET_ACTIVITY_NODE_CANDIDATES)) //
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED) //
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(structuredActivityNodeEClass.getName())) //
                .labelEditTool(this.getViewBuilder().createDirectEditTool(structuredActivityNodeEClass.getName())) //
                .build();
        adStructuredActivityNodeDescription.setDefaultWidthExpression(CONTAINER_NODE_SIZE);
        adStructuredActivityNodeDescription.setDefaultHeightExpression(CONTAINER_NODE_SIZE);
        this.adSharedDescription.getChildrenDescriptions().add(adStructuredActivityNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adStructuredActivityNodeDescription);
        this.addToolSections(adStructuredActivityNodeDescription, ACTIVITY_NODE, PIN, EXPANSION_REGION, INVOCATION_ACTION, CREATE_OBJECT_ACTION, STRUCTURED_ACTIVITY_NODE, STRUCTURAL_FEATURE,
                EXECUTABLE_NODE, ACCEPT_EVENT_ACTION);

        NodeTool adStructuredActivityNodeCreationTool = this.createStructuredActivityNodeCreationTool(structuredActivityNodeEClass);
        List<EClass> owners = List.of(this.umlPackage.getActivity(), //
                this.umlPackage.getActivityGroup());
        this.reuseNodeAndCreateTool(adStructuredActivityNodeDescription, diagramDescription, adStructuredActivityNodeCreationTool, STRUCTURED_ACTIVITY_NODE, owners,
                List.of());

        DropNodeTool adStructuredActivityNodeGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(adStructuredActivityNodeDescription));
        List<EClass> children = List.of(this.umlPackage.getActivityNode(), this.umlPackage.getComment(), this.umlPackage.getConstraint());
        this.registerCallback(adStructuredActivityNodeDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            adStructuredActivityNodeGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        adStructuredActivityNodeDescription.getPalette().setDropNodeTool(adStructuredActivityNodeGraphicalDropTool);
    }

    /**
     * Creates the {@link NodeDescription} representing an {@link Activity} inside another {@link Activity}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     */
    private void createSharedSubActivityDescription(DiagramDescription diagramDescription) {
        // We need a custom NodeDescription for sub-activity, otherwise there is no way to differentiate the root
        // Activity from Sub Activities, and the semantic candidate expression doesn't work (self): it produces an
        // infinite loop.
        RectangularNodeStyleDescription rectangularNodeStyle = this.getViewBuilder().createRectangularNodeStyle(true, true);
        rectangularNodeStyle.setBorderRadius(BORDER_RADIUS_SIZE);
        EClass activityEClass = this.umlPackage.getActivity();
        NodeDescription adSubActivityDescription = this.newNodeBuilder(activityEClass, rectangularNodeStyle) //
                // Explicitly set the name because IdBuilder can't add a prefix before the domain type name.
                .name("AD_SubActivity_SHARED") //
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription()) //
                .semanticCandidateExpression("aql:self.oclAsType(uml::Activity).nestedClassifier")//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED) //
                .labelEditTool(this.getViewBuilder().createDirectEditTool(activityEClass.getName())) //
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(activityEClass.getName())) //
                .build();
        adSubActivityDescription.setDefaultWidthExpression(CONTAINER_NODE_SIZE);
        adSubActivityDescription.setDefaultHeightExpression(CONTAINER_NODE_SIZE);

        this.adSharedDescription.getChildrenDescriptions().add(adSubActivityDescription);

        this.createDefaultToolSectionsInNodeDescription(adSubActivityDescription);
        this.addToolSections(adSubActivityDescription, ACTIVITY_GROUP, ACTIVITY_NODE, EXPANSION_REGION, INVOCATION_ACTION, CREATE_OBJECT_ACTION, STRUCTURED_ACTIVITY_NODE, STRUCTURAL_FEATURE,
                EXECUTABLE_NODE, ACCEPT_EVENT_ACTION);

        NodeTool nodeTool = this.getViewBuilder().createCreationTool(this.umlPackage.getClass_NestedClassifier(), activityEClass);
        this.reuseNodeAndCreateTool(adSubActivityDescription, diagramDescription, nodeTool, ACTIVITY_GROUP, activityEClass);

        DropNodeTool adActivityGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(adSubActivityDescription));
        List<EClass> children = List.of(this.umlPackage.getActivity(), this.umlPackage.getActivityNode(), this.umlPackage.getActivityPartition(), this.umlPackage.getComment(),
                this.umlPackage.getConstraint(), this.umlPackage.getInterruptibleActivityRegion());
        this.registerCallback(adSubActivityDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            adActivityGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        adSubActivityDescription.getPalette().setDropNodeTool(adActivityGraphicalDropTool);
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link TestIdentityAction}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedTestIdentityActionNodeDescription(DiagramDescription diagramDescription) {
        NodeDescription adTestIdentityActionNodeDescription = this.createSharedRoundedRectangleActionNodeDescription(this.umlPackage.getTestIdentityAction());
        this.adSharedDescription.getChildrenDescriptions().add(adTestIdentityActionNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adTestIdentityActionNodeDescription);
        this.addToolSections(adTestIdentityActionNodeDescription, PIN);

        NodeTool adTestIdentityActionCreationTool = this.createActivityNodeCreationTool(this.umlPackage.getTestIdentityAction());
        List<EClass> owners = List.of(this.umlPackage.getActivity(), this.umlPackage.getActivityGroup());
        this.reuseNodeAndCreateTool(adTestIdentityActionNodeDescription, diagramDescription, adTestIdentityActionCreationTool, NODES, owners,
                List.of());
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link ValuePin}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     */
    private void createSharedValuePinNodeDescription(DiagramDescription diagramDescription) {
        ImageNodeStyleDescription valuePinStyle = this.getViewBuilder().createImageNodeStyle(this.getImageForDomainType(this.umlPackage.getValuePin()), true);
        valuePinStyle.setPositionDependentRotation(true);
        NodeStyleDescription incomingOutgoingNodeStyleDescription = this.getViewBuilder().createImageNodeStyle(UUID.nameUUIDFromBytes(CONNECTED_PIN_IMAGE.getBytes()).toString(), true);
        ConditionalNodeStyle incomingOutgoingConditionalStyle = this.getViewBuilder().createConditionalNodeStyle(PIN_CONDITIONAL_STYLE_CONDITION, incomingOutgoingNodeStyleDescription);

        NodeDescription adValuePinDescription = new NodeDescriptionBuilder(this.getIdBuilder(), this.getQueryBuilder(), this.umlPackage.getValuePin(), valuePinStyle, this.getUmlMetaModelHelper())
                .name(this.getIdBuilder().getSpecializedDomainNodeName(this.umlPackage.getValuePin(), SHARED_SUFFIX)) //
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.GET_VALUE_PIN_CANDIDATES)) //
                .conditionalStyles(List.of(incomingOutgoingConditionalStyle)) //
                .build();
        adValuePinDescription.setDefaultWidthExpression(BORDER_NODE_SIZE);
        adValuePinDescription.setDefaultHeightExpression(BORDER_NODE_SIZE);
        this.getViewBuilder().addDirectEditTool(adValuePinDescription);
        this.getViewBuilder().addDefaultDeleteTool(adValuePinDescription);
        this.adSharedDescription.getBorderNodesDescriptions().add(adValuePinDescription);

        this.createDefaultToolSectionsInNodeDescription(adValuePinDescription);

        NodeTool nodeTool = this.getViewBuilder().createCreationTool(this.getIdBuilder().getCreationToolId(this.umlPackage.getValuePin()), ActivityDiagramServices.CREATE_VALUE_PIN,
                List.of(SELECTED_NODE, DIAGRAM_CONTEXT, CONVERTED_NODES));
        nodeTool.setPreconditionExpression(CallQuery.queryServiceOnSelf(ActivityDiagramServices.CAN_CREATE_INTO_PARENT, this.getQueryBuilder().aqlString(this.umlPackage.getValuePin().getName())));
        List<EClass> owners = List.of(this.umlPackage.getBroadcastSignalAction(), //
                this.umlPackage.getConditionalNode(), //
                this.umlPackage.getCreateLinkAction(), //
                this.umlPackage.getCreateLinkObjectAction(), //
                this.umlPackage.getDestroyLinkAction(), //
                this.umlPackage.getExpansionRegion(), //
                this.umlPackage.getLoopNode(), //
                this.umlPackage.getOpaqueAction(), //
                this.umlPackage.getReadLinkAction(), //
                this.umlPackage.getSequenceNode(), //
                this.umlPackage.getStartObjectBehaviorAction(), //
                this.umlPackage.getStructuredActivityNode(), //
                this.umlPackage.getAddStructuralFeatureValueAction(), //
                this.umlPackage.getAddVariableValueAction(), //
                this.umlPackage.getCallBehaviorAction(), //
                this.umlPackage.getCallOperationAction(), //
                this.umlPackage.getClearAssociationAction(), //
                this.umlPackage.getClearStructuralFeatureAction(), //
                this.umlPackage.getDestroyObjectAction(), //
                this.umlPackage.getReadIsClassifiedObjectAction(), //
                this.umlPackage.getReadStructuralFeatureAction(), //
                this.umlPackage.getReclassifyObjectAction(), //
                this.umlPackage.getReduceAction(), //
                this.umlPackage.getSendObjectAction(), //
                this.umlPackage.getSendSignalAction(), //
                this.umlPackage.getStartClassifierBehaviorAction(), //
                this.umlPackage.getTestIdentityAction(), //
                this.umlPackage.getUnmarshallAction()//
        );
        this.reuseNodeAndCreateTool(adValuePinDescription, diagramDescription, nodeTool, PIN, owners.toArray(EClass[]::new));
    }

    /**
     * Creates a {@link NodeDescription} representing an UML {@link ValueSpecification}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription}
     * @see #createSharedRoundedRectangleActionNodeDescription(EClass)
     */
    private void createSharedValueSpecificationActionNodeDescription(DiagramDescription diagramDescription) {
        NodeDescription adValueSpecificationActionNodeDescription = this.createSharedRoundedRectangleActionNodeDescription(this.umlPackage.getValueSpecificationAction());
        this.adSharedDescription.getChildrenDescriptions().add(adValueSpecificationActionNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(adValueSpecificationActionNodeDescription);
        this.addToolSections(adValueSpecificationActionNodeDescription, PIN);

        NodeTool nodeTool = this.createActivityNodeCreationTool(this.umlPackage.getValueSpecificationAction());
        this.reuseNodeAndCreateTool(adValueSpecificationActionNodeDescription, diagramDescription, nodeTool, EXECUTABLE_NODE,
                List.of(this.umlPackage.getActivity(), this.umlPackage.getActivityGroup()), List.of());
    }

    /**
     * Creates an {@link EdgeDescription} representing an UML {@link ControlFlow}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createControlFlowDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> sourceDescriptionSupplier = () -> this.collectNodesWithDomainAndFilter(diagramDescription, List.of(this.umlPackage.getActivityNode()),
                List.of(this.umlPackage.getFinalNode()));
        Supplier<List<NodeDescription>> targetDescriptionSupplier = () -> this.collectNodesWithDomainAndFilter(diagramDescription, List.of(this.umlPackage.getActivityNode()),
                List.of(this.umlPackage.getInitialNode()));

        EdgeDescription adControlFlowDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.umlPackage.getControlFlow(),
                this.getQueryBuilder().queryAllReachableExactType(this.umlPackage.getControlFlow()), sourceDescriptionSupplier, targetDescriptionSupplier);
        adControlFlowDescription.getStyle().setLineStyle(LineStyle.SOLID);
        adControlFlowDescription.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        EdgeTool adControlFlowCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(adControlFlowDescription, this.umlPackage.getActivity_Edge());
        this.registerCallback(adControlFlowDescription, () -> {
            this.addEdgeToolInEdgesToolSection(sourceDescriptionSupplier.get(), adControlFlowCreationTool);
        });

        diagramDescription.getEdgeDescriptions().add(adControlFlowDescription);

        this.getViewBuilder().addDefaultReconnectionTools(adControlFlowDescription);
    }

    /**
     * Creates an {@link EdgeDescription} representing an UMLL {@link ObjectFlow}.
     *
     * @param diagramDescription
     *            the Activity {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createObjectFlowDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> sourceDescriptionSupplier = () -> this.collectNodesWithDomainAndFilter(diagramDescription, List.of(
                this.umlPackage.getActionInputPin(),
                this.umlPackage.getInputPin(),
                this.umlPackage.getOutputPin(),
                this.umlPackage.getValuePin(),
                this.umlPackage.getExpansionNode(),
                this.umlPackage.getDecisionNode(),
                this.umlPackage.getForkNode(),
                this.umlPackage.getJoinNode(),
                this.umlPackage.getMergeNode(),
                this.umlPackage.getOpaqueAction(),
                this.umlPackage.getActivityParameterNode()),
                List.of());
        Supplier<List<NodeDescription>> targetDescriptionSupplier = () -> this.collectNodesWithDomainAndFilter(diagramDescription, List.of(
                this.umlPackage.getActionInputPin(),
                this.umlPackage.getInputPin(),
                this.umlPackage.getOutputPin(),
                this.umlPackage.getValuePin(),
                this.umlPackage.getActivityFinalNode(),
                this.umlPackage.getDecisionNode(),
                this.umlPackage.getFlowFinalNode(),
                this.umlPackage.getForkNode(),
                this.umlPackage.getJoinNode(),
                this.umlPackage.getMergeNode(),
                this.umlPackage.getOpaqueAction(),
                this.umlPackage.getExpansionNode(),
                this.umlPackage.getActivityParameterNode()),
                List.of());

        EdgeDescription adObjectFlowDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.umlPackage.getObjectFlow(),
                this.getQueryBuilder().queryAllReachableExactType(this.umlPackage.getObjectFlow()), sourceDescriptionSupplier, targetDescriptionSupplier);
        adObjectFlowDescription.setEndLabelExpression(this.getQueryBuilder().createDomainBaseEdgeTargetLabelExpression());
        adObjectFlowDescription.getStyle().setLineStyle(LineStyle.SOLID);
        adObjectFlowDescription.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        EdgeTool adObjectFlowCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(adObjectFlowDescription, this.umlPackage.getActivity_Edge());
        this.registerCallback(adObjectFlowDescription, () -> {
            this.addEdgeToolInEdgesToolSection(sourceDescriptionSupplier.get(), adObjectFlowCreationTool);
        });

        diagramDescription.getEdgeDescriptions().add(adObjectFlowDescription);

        this.getViewBuilder().addDefaultReconnectionTools(adObjectFlowDescription);
    }

    /**
     * Utility method that computes the {@link UUID} of the image to use to represent the provided {@code domainType}.
     *
     * @param domainType
     *            the type to retrieve the image from
     * @return a {@link String} representing the image
     */
    private String getImageForDomainType(EClass domainType) {
        if (domainType == null) {
            LOGGER.warn("Cannot find the image for domain type null");
            return null;
        }
        String imageName = switch (domainType.getClassifierID()) {

            case UMLPackage.ACTION_INPUT_PIN, UMLPackage.INPUT_PIN, UMLPackage.VALUE_PIN -> "InputPin.svg";
            case UMLPackage.ACTIVITY_FINAL_NODE -> "ActivityFinalNode.svg";
            case UMLPackage.DECISION_NODE -> "DecisionNode.svg";
            case UMLPackage.EXPANSION_NODE -> "ExpansionNode.svg";
            case UMLPackage.FLOW_FINAL_NODE -> "FlowFinalNode.svg";
            // Use Fork.svg instead of ForkNode.svg because it has been defined in SMD.
            case UMLPackage.FORK_NODE -> "Fork.svg";
            case UMLPackage.INITIAL_NODE -> "InitialNode.svg";
            // Use Join.svg instead of ForkNode.svg because it has been defined in SMD.
            case UMLPackage.JOIN_NODE -> "Join.svg";
            case UMLPackage.MERGE_NODE -> "MergeNode.svg";
            case UMLPackage.OUTPUT_PIN -> "OutputPin.svg";
            default -> null;
        };
        String result = null;
        if (imageName != null) {
            result = UUID.nameUUIDFromBytes(imageName.getBytes()).toString();
        } else {
            LOGGER.warn("Cannot find the image for domain type {0}", domainType.getName());
        }
        return result;
    }

    /**
     * Collects all the {@link NodeDescription} matching the given {@code domains}, excluding the ones matching the
     * provided {@code forbiddenDomains}.
     * <p>
     * This method is typically used to collect a given domain class and exclude some of its sub-classes. It also
     * excludes {@code AD_DecisionNode_Note_SHARED}, which shouldn't be searchable with this method.
     * </p>
     *
     * @param description
     *            the diagram description
     * @param domains
     *            the list of matching domain types
     * @param forbiddenDomains
     *            the list of domain types to exclude
     * @return a list of matching {@link NodeDescription}
     */
    @Override
    protected List<NodeDescription> collectNodesWithDomainAndFilter(DiagramDescription description, List<EClass> domains, List<EClass> forbiddenDomains) {
        List<NodeDescription> forbiddenDescription = this.collectNodesWithDomain(description, forbiddenDomains.toArray(EClass[]::new));
        return this.collectNodesWithDomain(description, domains.toArray(EClass[]::new)).stream() //
                .filter(nd -> !SHARED_DESCRIPTIONS.equals(nd.getName())) //
                .filter(nd -> !Objects.equals(nd.getName(), "AD_DecisionNode_Note_SHARED")) //
                .filter(nd -> !forbiddenDescription.contains(nd)) //
                .toList();
    }
}
