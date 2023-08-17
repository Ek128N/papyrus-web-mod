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

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.web.application.representations.view.aql.CallQuery;
import org.eclipse.papyrus.web.application.representations.view.builders.CallbackAdapter;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.uml2.uml.DurationObservation;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.TimeObservation;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Builder of the "Communication" diagram representation.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public final class CODDiagramDescriptionBuilder extends AbstractRepresentationDescriptionBuilder {

    /**
     * Size of TimeObservation and DurationObservation nodes.
     */
    public static final String OBSERVATION_SIZE = "25";

    /**
     * The name of the representation handled by this builder.
     */
    public static final String COD_REP_NAME = "Communication Diagram";

    /**
     * The prefix of the representation handled by this builder.
     */
    public static final String COD_PREFIX = "COD_";

    /**
     * Size of the radius corner of the Interaction Node initialized on the diagram at its creation.
     */
    public static final int INTERACTION_NODE_BORDER_RADIUS = 10;

    /**
     * Factory used to create UML elements.
     */
    private final UMLPackage umlPackage = UMLPackage.eINSTANCE;

    /**
     * Initializes the builder.
     */
    public CODDiagramDescriptionBuilder() {
        super(COD_PREFIX, COD_REP_NAME, UMLPackage.eINSTANCE.getNamedElement());
    }

    @Override
    protected void fillDescription(DiagramDescription diagramDescription) {
        diagramDescription.setPreconditionExpression(CallQuery.queryServiceOnSelf(CommunicationDiagramServices.CAN_CREATE_DIAGRAM));

        NodeDescription codInteractionDescription = this.createDiagramInteractionDescription(diagramDescription);
        this.createLifelineDescriptionInNodeDescription(codInteractionDescription);
        this.createDurationObservationDescriptionInNodeDescription(codInteractionDescription);
        this.createTimeObservationDescriptionInNodeDescription(codInteractionDescription);
        this.createMessageDescriptionInNodeDescription(diagramDescription);

        this.createCommentDescriptionInNodeDescription(diagramDescription, codInteractionDescription, NODES, this.getIdBuilder().getDomainNodeName(this.umlPackage.getComment()),
                List.of(this.umlPackage.getInteraction()));
        this.createConstraintDescriptionInNodeDescription(diagramDescription, codInteractionDescription, NODES, this.getIdBuilder().getDomainNodeName(this.umlPackage.getConstraint()),
                List.of(this.umlPackage.getInteraction()));

        diagramDescription.getPalette().setDropTool(this.getViewBuilder().createGenericSemanticDropTool(this.getIdBuilder().getDiagramSemanticDropToolName()));
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Interaction}.
     *
     * @param diagramDescription
     *            the Communication {@link DiagramDescription} containing the created {@link NodeDescription}
     * @return the {@link NodeDescription} representing an UML {@link Interaction}.
     */
    protected NodeDescription createDiagramInteractionDescription(DiagramDescription diagramDescription) {
        RectangularNodeStyleDescription rectangularNodeStyle = this.getViewBuilder().createRectangularNodeStyle(true, true);
        rectangularNodeStyle.setBorderRadius(INTERACTION_NODE_BORDER_RADIUS);

        EClass interactionEClass = this.umlPackage.getInteraction();
        NodeDescription codInteractionDescription = this.newNodeBuilder(interactionEClass, rectangularNodeStyle)//
                .name(this.getIdBuilder().getDomainNodeName(interactionEClass)) //
                .semanticCandidateExpression(this.getQueryBuilder().querySelf())//
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(interactionEClass.getName()))//
                .build();
        codInteractionDescription.setDefaultWidthExpression(ROOT_ELEMENT_WIDTH);
        codInteractionDescription.setDefaultHeightExpression(ROOT_ELEMENT_HEIGHT);
        diagramDescription.getNodeDescriptions().add(codInteractionDescription);

        // create Interaction tool sections
        this.createDefaultToolSectionsInNodeDescription(codInteractionDescription);

        return codInteractionDescription;
    }

    /**
     * Create the {@link NodeDescription} and creation tool representing an UML {@link Lifeline}.
     *
     * @param parentNodeDescription
     *            the {@link NodeDescription} containing the {@link Lifeline} {@link NodeDescription}
     */
    private void createLifelineDescriptionInNodeDescription(NodeDescription parentNodeDescription) {
        RectangularNodeStyleDescription rectangularNodeStyle = this.getViewBuilder().createRectangularNodeStyle(true, false);
        EClass lifelineEClass = this.umlPackage.getLifeline();
        NodeDescription codLifelineDescription = this.newNodeBuilder(lifelineEClass, rectangularNodeStyle) //
                .semanticCandidateExpression(CallQuery.queryAttributeOnSelf(UMLPackage.eINSTANCE.getInteraction_Lifeline())).synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED) //
                .labelEditTool(this.getViewBuilder().createDirectEditTool(lifelineEClass.getName())) //
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(lifelineEClass.getName())) //
                .build();
        parentNodeDescription.getChildrenDescriptions().add(codLifelineDescription);

        // create Lifeline tool sections
        this.createDefaultToolSectionsInNodeDescription(codLifelineDescription);

        NodeTool codLifelineCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getInteraction_Lifeline(), lifelineEClass);
        this.addNodeToolInToolSection(List.of(parentNodeDescription), codLifelineCreationTool, NODES);
    }

    /**
     * Create the {@link NodeDescription} and creation tool representing an UML {@link DurationObservation}.
     *
     * @param parentNodeDescription
     *            the {@link NodeDescription} containing the {@link DurationObservation} {@link NodeDescription}
     */
    private void createDurationObservationDescriptionInNodeDescription(NodeDescription parentNodeDescription) {
        NodeStyleDescription durationObservationNodeStyle = this.getViewBuilder().createImageNodeStyle(UUID.nameUUIDFromBytes("DurationObservation.svg".getBytes()).toString(), true);

        EClass durationObservationEClass = this.umlPackage.getDurationObservation();
        NodeDescription codDurationObservationDescription = this.newNodeBuilder(durationObservationEClass, durationObservationNodeStyle)//
                .name(this.getIdBuilder().getDomainNodeName(durationObservationEClass)) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(CommunicationDiagramServices.GET_DURATION_OBSERVATION_CANDIDATES))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(durationObservationEClass.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(durationObservationEClass.getName())) //
                .build();
        codDurationObservationDescription.setDefaultWidthExpression(OBSERVATION_SIZE);
        codDurationObservationDescription.setDefaultHeightExpression(OBSERVATION_SIZE);
        parentNodeDescription.getChildrenDescriptions().add(codDurationObservationDescription);

        // create durationObservation tool sections
        this.createDefaultToolSectionsInNodeDescription(codDurationObservationDescription);

        NodeTool codDurationObservationCreationTool = this.getViewBuilder().createCreationTool(this.getIdBuilder().getCreationToolId(durationObservationEClass),
                CallQuery.queryServiceOnSelf(CommunicationDiagramServices.GET_PACKAGE_CONTAINER), this.umlPackage.getPackage_PackagedElement(), durationObservationEClass);
        this.addNodeToolInToolSection(List.of(parentNodeDescription), codDurationObservationCreationTool, NODES);
    }

    /**
     * Create the {@link NodeDescription} and creation tool representing an UML {@link TimeObservation}.
     *
     * @param parentNodeDescription
     *            the {@link NodeDescription} containing the {@link TimeObservation} {@link NodeDescription}
     */
    private void createTimeObservationDescriptionInNodeDescription(NodeDescription parentNodeDescription) {
        NodeStyleDescription timeObservationNodeStyle = this.getViewBuilder().createImageNodeStyle(UUID.nameUUIDFromBytes("TimeObservation.svg".getBytes()).toString(), true);

        EClass timeObservationEClass = this.umlPackage.getTimeObservation();
        NodeDescription codTimeObservationDescription = this.newNodeBuilder(timeObservationEClass, timeObservationNodeStyle)//
                .name(this.getIdBuilder().getDomainNodeName(timeObservationEClass)) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(CommunicationDiagramServices.GET_TIME_OBSERVATION_CANDIDATES))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(timeObservationEClass.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(timeObservationEClass.getName())) //
                .build();
        codTimeObservationDescription.setDefaultWidthExpression(OBSERVATION_SIZE);
        codTimeObservationDescription.setDefaultHeightExpression(OBSERVATION_SIZE);

        parentNodeDescription.getChildrenDescriptions().add(codTimeObservationDescription);

        // create timeObservation tool sections
        this.createDefaultToolSectionsInNodeDescription(codTimeObservationDescription);

        NodeTool codTimeObservationCreationTool = this.getViewBuilder().createCreationTool(this.getIdBuilder().getCreationToolId(timeObservationEClass),
                CallQuery.queryServiceOnSelf(CommunicationDiagramServices.GET_PACKAGE_CONTAINER), this.umlPackage.getPackage_PackagedElement(), timeObservationEClass);
        this.addNodeToolInToolSection(List.of(parentNodeDescription), codTimeObservationCreationTool, NODES);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Message}.
     *
     * @param diagramDescription
     *            the Communication {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createMessageDescriptionInNodeDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> sourceAndTargetDescriptionsSupplier = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getLifeline());

        EClass messageEClass = this.umlPackage.getMessage();
        EdgeDescription codMessageDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(messageEClass, this.getQueryBuilder().queryAllReachable(messageEClass),
                sourceAndTargetDescriptionsSupplier, sourceAndTargetDescriptionsSupplier);
        codMessageDescription.getStyle().setLineStyle(LineStyle.SOLID);
        codMessageDescription.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);

        EdgeTool codMessageCreationTool = this.getViewBuilder().createDomainBasedEdgeToolWithService("New Message", CommunicationDiagramServices.CREATE_MESSAGE);

        codMessageDescription.eAdapters().add(new CallbackAdapter(() -> {
            List<NodeDescription> targetNodeDescriptions = codMessageDescription.getTargetNodeDescriptions();
            codMessageCreationTool.getTargetElementDescriptions().addAll(targetNodeDescriptions);
        }));

        this.registerCallback(codMessageDescription, () -> {
            this.addEdgeToolInEdgesToolSection(sourceAndTargetDescriptionsSupplier.get(), codMessageCreationTool);
        });

        diagramDescription.getEdgeDescriptions().add(codMessageDescription);

        this.getViewBuilder().addDefaultReconnectionTools(codMessageDescription);
    }

}
