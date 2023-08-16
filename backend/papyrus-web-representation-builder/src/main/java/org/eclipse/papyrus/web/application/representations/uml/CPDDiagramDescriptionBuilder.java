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
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.web.application.representations.view.IdBuilder;
import org.eclipse.papyrus.web.application.representations.view.aql.CallQuery;
import org.eclipse.papyrus.web.application.representations.view.aql.Services;
import org.eclipse.papyrus.web.application.representations.view.aql.Variables;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DropNodeTool;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeStyle;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.uml2.uml.Abstraction;
import org.eclipse.uml2.uml.ComponentRealization;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Manifestation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Usage;

/**
 * Builder of the "Component" diagram representation.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public final class CPDDiagramDescriptionBuilder extends AbstractRepresentationDescriptionBuilder {

    /**
     * The suffix used to identify <i>operations</i> compartments.
     */
    public static final String OPERATIONS_COMPARTMENT_SUFFIX = "Operations";

    /**
     * The suffix used to identify <i>attributes</i> compartments.
     */
    public static final String ATTRIBUTES_COMPARTMENT_SUFFIX = "Attributes";

    /**
     * The suffix used to identify <i>receptions</i> compartments.
     */
    public static final String RECEPTIONS_COMPARTMENT_SUFFIX = "Receptions";

    /**
     * The name of the representation handled by this builder.
     */
    public static final String CPD_REP_NAME = "Component Diagram";

    /**
     * The prefix of the representation handled by this builder.
     */
    public static final String CPD_PREFIX = "CPD_";

    /**
     * Suffix used for nodeDescriptions defined inside Component Description.
     */
    public static final String IN_COMPONENT = "inComponent";

    /**
     * AQL expression to set children not draggable from its container.
     */
    private static final String CHILD_NOT_DRAGGABLE_EXPRESSION = "aql:false";

    /**
     * Factory used to create UML elements.
     */
    private final UMLPackage umlPackage = UMLPackage.eINSTANCE;

    /**
     * The list of semantic types that are represented as border nodes.
     * <p>
     * This list is used to filter drag & drop targets and prevent border nodes from being droppable.
     * </p>
     */
    private List<EClass> borderNodeTypes = List.of(this.umlPackage.getPort());

    /**
     * The <i>shared</i> {@link NodeDescription} for the diagram.
     */
    private NodeDescription cpdSharedDescription;

    public CPDDiagramDescriptionBuilder() {
        super(CPD_PREFIX, CPD_REP_NAME, UMLPackage.eINSTANCE.getPackage());
    }

    @Override
    protected void fillDescription(DiagramDescription diagramDescription) {

        // create diagram tool sections
        this.createDefaultToolSectionInDiagramDescription(diagramDescription);
        diagramDescription.setPreconditionExpression(CallQuery.queryServiceOnSelf(Services.IS_NOT_PROFILE_MODEL));

        // create node descriptions with their tools
        this.createDiagramCommentDescription(diagramDescription, NODES);
        this.createDiagramConstraintDescription(diagramDescription, NODES);
        this.createDiagramComponentDescription(diagramDescription);
        this.createDiagramInterfaceDescription(diagramDescription);
        this.createDiagramModelDescription(diagramDescription);
        this.createDiagramPackageDescription(diagramDescription);

        // create shared node descriptions with their tools
        this.cpdSharedDescription = this.createSharedDescription(diagramDescription);
        this.createCommentDescriptionInNodeDescription(diagramDescription, this.cpdSharedDescription, NODES,
                this.getIdBuilder().getSpecializedDomainNodeName(this.umlPackage.getComment(), SHARED_SUFFIX), List.of(this.umlPackage.getPackage()));
        this.createConstraintDescriptionInNodeDescription(diagramDescription, this.cpdSharedDescription, NODES,
                this.getIdBuilder().getSpecializedDomainNodeName(this.umlPackage.getConstraint(), SHARED_SUFFIX), List.of(this.umlPackage.getPackage()));

        this.createSharedComponentDescription(diagramDescription);
        this.createSharedInterfaceDescription(diagramDescription);
        this.createSharedModelDescription(diagramDescription);
        this.createSharedPackageDescription(diagramDescription);
        this.createSharedPortDescription(diagramDescription);
        this.createSharedPropertyDescription(diagramDescription);

        // create shared compartments
        NodeDescription attributesCompartment = this.createSharedCompartmentForInterfaceDescription(diagramDescription, ATTRIBUTES_COMPARTMENT_SUFFIX);
        NodeDescription operationsCompartment = this.createSharedCompartmentForInterfaceDescription(diagramDescription, OPERATIONS_COMPARTMENT_SUFFIX);
        NodeDescription receptionsCompartment = this.createSharedCompartmentForInterfaceDescription(diagramDescription, RECEPTIONS_COMPARTMENT_SUFFIX);
        this.createAttributeListDescription(diagramDescription, attributesCompartment);
        this.createOperationListDescription(diagramDescription, operationsCompartment);
        this.createReceptionListDescription(diagramDescription, receptionsCompartment);

        // create edge descriptions with their tools
        this.createAbstractionDescription(diagramDescription);
        this.createComponentRealizationDescription(diagramDescription);
        this.createConnectorDescription(diagramDescription);
        this.createDependencyDescription(diagramDescription);
        this.createGeneralizationDescription(diagramDescription);
        this.createInterfaceRealizationDescription(diagramDescription);
        this.createManifestationDescription(diagramDescription);
        this.createSubstitutionDescription(diagramDescription);
        this.createUsageDescription(diagramDescription);

        diagramDescription.getPalette().setDropTool(this.getViewBuilder().createGenericSemanticDropTool(this.getIdBuilder().getDiagramSemanticDropToolName()));

        // Add dropped tool on diagram
        DropNodeTool cpdGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getDiagramGraphicalDropToolName());
        List<EClass> children = List.of(this.umlPackage.getComponent(), this.umlPackage.getComment(), this.umlPackage.getConstraint(), this.umlPackage.getInterface(), this.umlPackage.getModel(),
                this.umlPackage.getPackage());
        this.registerCallback(diagramDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            cpdGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        diagramDescription.getPalette().setDropNodeTool(cpdGraphicalDropTool);
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Component} at Diagram and {@link Component}
     * levels.
     *
     * @param diagramDescription
     *            the Component {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramComponentDescription(DiagramDescription diagramDescription) {
        EClass componentEClass = this.umlPackage.getComponent();
        NodeDescription cpdDiagramComponentDescription = this.newNodeBuilder(componentEClass, this.getViewBuilder().createRectangularNodeStyle(true, true))//
                .name(this.getIdBuilder().getDomainNodeName(componentEClass)) //
                .semanticCandidateExpression(this.getQueryBuilder().queryAllReachableExactType(componentEClass))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(componentEClass.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(componentEClass.getName())) //
                .build();
        diagramDescription.getNodeDescriptions().add(cpdDiagramComponentDescription);

        this.createDefaultToolSectionsInNodeDescription(cpdDiagramComponentDescription);

        NodeTool cpdDiagramComponentCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getComponent_PackagedElement(), componentEClass);
        this.addDiagramToolInToolSection(diagramDescription, cpdDiagramComponentCreationTool, NODES);

        // Add dropped tool on Component container
        DropNodeTool cpdComponentGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(cpdDiagramComponentDescription));
        List<EClass> children = List.of(this.umlPackage.getComponent(), this.umlPackage.getProperty());
        this.registerCallback(cpdDiagramComponentDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, this.borderNodeTypes);
            cpdComponentGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        cpdDiagramComponentDescription.getPalette().setDropNodeTool(cpdComponentGraphicalDropTool);
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Interface} at diagram level.
     *
     * @param diagramDescription
     *            the Component {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramInterfaceDescription(DiagramDescription diagramDescription) {
        ListLayoutStrategyDescription listLayoutStrategyDescription = DiagramFactory.eINSTANCE.createListLayoutStrategyDescription();
        listLayoutStrategyDescription.setAreChildNodesDraggableExpression(CHILD_NOT_DRAGGABLE_EXPRESSION);
        EClass interfaceEClass = this.umlPackage.getInterface();
        NodeDescription cpdDiagramInterfaceDescription = this.newNodeBuilder(interfaceEClass, this.getViewBuilder().createRectangularNodeStyle(true, true))//
                .layoutStrategyDescription(listLayoutStrategyDescription)//
                .semanticCandidateExpression(this.getQueryBuilder().queryAllReachableExactType(interfaceEClass))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(interfaceEClass.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(interfaceEClass.getName())) //
                .build();
        diagramDescription.getNodeDescriptions().add(cpdDiagramInterfaceDescription);

        this.createDefaultToolSectionsInNodeDescription(cpdDiagramInterfaceDescription);

        NodeTool cpdDiagramInterfaceCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), interfaceEClass);
        this.addDiagramToolInToolSection(diagramDescription, cpdDiagramInterfaceCreationTool, NODES);
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Model} on the Diagram.
     *
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramModelDescription(DiagramDescription diagramDescription) {
        EClass modelEClass = this.umlPackage.getModel();
        NodeDescription cpdDiagramModelDescription = this.getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(modelEClass, this.getQueryBuilder().queryAllReachableExactType(modelEClass));
        diagramDescription.getNodeDescriptions().add(cpdDiagramModelDescription);

        this.createDefaultToolSectionsInNodeDescription(cpdDiagramModelDescription);

        NodeTool cpdDiagramProfileCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), modelEClass);
        this.addDiagramToolInToolSection(diagramDescription, cpdDiagramProfileCreationTool, NODES);

        // Add dropped tool on Profile container
        DropNodeTool cpdModelGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(cpdDiagramModelDescription));
        List<EClass> children = List.of(this.umlPackage.getComponent(), this.umlPackage.getComment(), this.umlPackage.getConstraint(), this.umlPackage.getInterface(), this.umlPackage.getModel(),
                this.umlPackage.getPackage());
        this.registerCallback(cpdDiagramModelDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            cpdModelGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        cpdDiagramModelDescription.getPalette().setDropNodeTool(cpdModelGraphicalDropTool);
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Package} on the Diagram.
     *
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramPackageDescription(DiagramDescription diagramDescription) {
        EClass packageEClass = this.umlPackage.getPackage();
        NodeDescription cpdDiagramPackageDescription = this.getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(packageEClass,
                this.getQueryBuilder().queryAllReachableExactType(this.umlPackage.getPackage()));
        diagramDescription.getNodeDescriptions().add(cpdDiagramPackageDescription);

        cpdDiagramPackageDescription.setStyle(this.getViewBuilder().createPackageNodeStyle());

        // create Package tool sections
        this.createDefaultToolSectionsInNodeDescription(cpdDiagramPackageDescription);

        NodeTool cpdDiagramPackageCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), packageEClass);
        this.addDiagramToolInToolSection(diagramDescription, cpdDiagramPackageCreationTool, NODES);

        // No direct children for Package: the NodeDescriptions it can contain are all defined as shared descriptions.

        // Add dropped tool on Package container
        DropNodeTool cpdPackageGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(cpdDiagramPackageDescription));
        List<EClass> children = List.of(this.umlPackage.getComponent(), this.umlPackage.getComment(), this.umlPackage.getConstraint(), this.umlPackage.getInterface(), this.umlPackage.getModel(),
                this.umlPackage.getPackage());
        this.registerCallback(cpdDiagramPackageDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            cpdPackageGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        cpdDiagramPackageDescription.getPalette().setDropNodeTool(cpdPackageGraphicalDropTool);
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link Component}.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     *
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedComponentDescription(DiagramDescription diagramDescription) {
        EClass componentEClass = this.umlPackage.getComponent();
        NodeDescription cpdSharedComponentDescription = this.newNodeBuilder(componentEClass, this.getViewBuilder().createRectangularNodeStyle(true, true))//
                .name(this.getIdBuilder().getSpecializedDomainNodeName(componentEClass, SHARED_SUFFIX)) //
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .semanticCandidateExpression(CallQuery.queryAttributeOnSelf(this.umlPackage.getComponent_PackagedElement()))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(componentEClass.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(componentEClass.getName())) //
                .build();
        this.cpdSharedDescription.getChildrenDescriptions().add(cpdSharedComponentDescription);

        this.createDefaultToolSectionsInNodeDescription(cpdSharedComponentDescription);

        NodeTool cpdSharedClassifierCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getComponent_PackagedElement(), componentEClass);
        List<EClass> owners = List.of(this.umlPackage.getPackage(), this.umlPackage.getModel(), this.umlPackage.getComponent());
        this.reuseNodeAndCreateTool(cpdSharedComponentDescription, diagramDescription, cpdSharedClassifierCreationTool, NODES, owners.toArray(EClass[]::new));

        // Add dropped tool on Shared Component container
        DropNodeTool cpdComponentGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(cpdSharedComponentDescription));
        List<EClass> children = List.of(this.umlPackage.getComponent(), this.umlPackage.getProperty());
        this.registerCallback(cpdSharedComponentDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, this.borderNodeTypes);
            cpdComponentGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        cpdSharedComponentDescription.getPalette().setDropNodeTool(cpdComponentGraphicalDropTool);
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link Interface}.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     *
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedInterfaceDescription(DiagramDescription diagramDescription) {
        ListLayoutStrategyDescription listLayoutStrategyDescription = DiagramFactory.eINSTANCE.createListLayoutStrategyDescription();
        listLayoutStrategyDescription.setAreChildNodesDraggableExpression(CHILD_NOT_DRAGGABLE_EXPRESSION);
        EClass interfaceEClass = this.umlPackage.getInterface();
        NodeDescription cpdSharedInterfaceDescription = this.newNodeBuilder(interfaceEClass, this.getViewBuilder().createRectangularNodeStyle(true, true))//
                .name(this.getIdBuilder().getSpecializedDomainNodeName(interfaceEClass, SHARED_SUFFIX)) //
                .layoutStrategyDescription(listLayoutStrategyDescription)//
                .semanticCandidateExpression(CallQuery.queryAttributeOnSelf(this.umlPackage.getPackage_PackagedElement()))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(interfaceEClass.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(interfaceEClass.getName())) //
                .build();
        this.cpdSharedDescription.getChildrenDescriptions().add(cpdSharedInterfaceDescription);

        this.createDefaultToolSectionsInNodeDescription(cpdSharedInterfaceDescription);

        NodeTool cpdSharedClassifierCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), interfaceEClass);
        List<EClass> owners = List.of(this.umlPackage.getPackage());
        this.reuseNodeAndCreateTool(cpdSharedInterfaceDescription, diagramDescription, cpdSharedClassifierCreationTool, NODES, owners.toArray(EClass[]::new));
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link Model}.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     *
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedModelDescription(DiagramDescription diagramDescription) {
        EClass modelEClass = this.umlPackage.getModel();
        NodeDescription cpdSharedModelDescription = this.getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(modelEClass,
                CallQuery.queryAttributeOnSelf(this.umlPackage.getPackage_PackagedElement()));
        cpdSharedModelDescription.setName(this.getIdBuilder().getSpecializedDomainNodeName(modelEClass, SHARED_SUFFIX));
        cpdSharedModelDescription.setStyle(this.getViewBuilder().createPackageNodeStyle());

        this.cpdSharedDescription.getChildrenDescriptions().add(cpdSharedModelDescription);

        this.createDefaultToolSectionsInNodeDescription(cpdSharedModelDescription);

        NodeTool cpdSharedModelCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), modelEClass);
        List<EClass> owners = List.of(this.umlPackage.getPackage(), this.umlPackage.getModel());
        this.reuseNodeAndCreateTool(cpdSharedModelDescription, diagramDescription, cpdSharedModelCreationTool, NODES, owners.toArray(EClass[]::new));

        // Add dropped tool on Shared Package container
        DropNodeTool cpdModelGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(cpdSharedModelDescription));
        List<EClass> children = List.of(this.umlPackage.getComponent(), this.umlPackage.getComment(), this.umlPackage.getConstraint(), this.umlPackage.getInterface(), this.umlPackage.getModel(),
                this.umlPackage.getPackage());
        this.registerCallback(cpdSharedModelDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            cpdModelGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        cpdSharedModelDescription.getPalette().setDropNodeTool(cpdModelGraphicalDropTool);
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link Package}.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     *
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedPackageDescription(DiagramDescription diagramDescription) {
        EClass packageEClass = this.umlPackage.getPackage();
        NodeDescription cpdSharedPackageDescription = this.getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(packageEClass,
                CallQuery.queryAttributeOnSelf(this.umlPackage.getPackage_PackagedElement()));
        cpdSharedPackageDescription.setName(this.getIdBuilder().getSpecializedDomainNodeName(packageEClass, SHARED_SUFFIX));
        cpdSharedPackageDescription.setStyle(this.getViewBuilder().createPackageNodeStyle());

        this.cpdSharedDescription.getChildrenDescriptions().add(cpdSharedPackageDescription);

        this.createDefaultToolSectionsInNodeDescription(cpdSharedPackageDescription);

        NodeTool cpdSharedPackageCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), packageEClass);
        List<EClass> owners = List.of(this.umlPackage.getPackage(), this.umlPackage.getModel());
        this.reuseNodeAndCreateTool(cpdSharedPackageDescription, diagramDescription, cpdSharedPackageCreationTool, NODES, owners.toArray(EClass[]::new));

        // Add dropped tool on Shared Package container
        DropNodeTool cpdPackageGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(cpdSharedPackageDescription));
        List<EClass> children = List.of(this.umlPackage.getComponent(), this.umlPackage.getComment(), this.umlPackage.getConstraint(), this.umlPackage.getInterface(), this.umlPackage.getModel(),
                this.umlPackage.getPackage());
        this.registerCallback(cpdSharedPackageDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            cpdPackageGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        cpdSharedPackageDescription.getPalette().setDropNodeTool(cpdPackageGraphicalDropTool);
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Property} at sub-level.
     *
     * @param diagramDescription
     *            the Deployment {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedPortDescription(DiagramDescription diagramDescription) {
        EClass portEClass = this.umlPackage.getPort();

        NodeDescription cpdSharedPortDescription = this.getViewBuilder().createSpecializedPortUnsynchonizedNodeDescription(SHARED_SUFFIX, portEClass,
                CallQuery.queryServiceOnSelf(ComponentDiagramServices.GET_PORT_NODE_CANDIDATES));

        this.cpdSharedDescription.getBorderNodesDescriptions().add(cpdSharedPortDescription);

        this.createDefaultToolSectionsInNodeDescription(cpdSharedPortDescription);

        // create tools
        NodeTool cpdSharedPortCreationTool = this.getViewBuilder().createCreationTool(IdBuilder.NEW + portEClass.getName(), ComponentDiagramServices.CREATE_PORT,
                List.of(SELECTED_NODE, DIAGRAM_CONTEXT, CONVERTED_NODES));
        cpdSharedPortCreationTool
                .setPreconditionExpression(
                        CallQuery.queryServiceOnSelf(ComponentDiagramServices.CAN_CREATE_PROPERTY_INTO_PARENT));

        List<EClass> owners = List.of(this.umlPackage.getProperty(), //
                this.umlPackage.getStructuredClassifier());
        // Port should be exclude from owners because it is not possible to create BorderNode on BorderNode
        this.reuseNodeAndCreateTool(cpdSharedPortDescription, diagramDescription, cpdSharedPortCreationTool, NODES, owners, List.of(this.umlPackage.getPort()));
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Property} at sub-level.
     *
     * @param diagramDescription
     *            the Deployment {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedPropertyDescription(DiagramDescription diagramDescription) {
        EClass propertyEClass = this.umlPackage.getProperty();

        NodeDescription cpdSharedPropertyDescription = this.newNodeBuilder(propertyEClass, this.getViewBuilder().createRectangularNodeStyle(true, true))//
                .name(this.getIdBuilder().getSpecializedDomainNodeName(propertyEClass, CPDDiagramDescriptionBuilder.SHARED_SUFFIX)) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ComponentDiagramServices.GET_PROPERTY_NODE_CANDIDATES))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(propertyEClass.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(propertyEClass.getName())) //
                .build();
        this.cpdSharedDescription.getChildrenDescriptions().add(cpdSharedPropertyDescription);

        this.createDefaultToolSectionsInNodeDescription(cpdSharedPropertyDescription);

        // create tools
        NodeTool cpdSharedPropertyCreationTool = this.getViewBuilder().createCreationTool(IdBuilder.NEW + propertyEClass.getName(), ComponentDiagramServices.CREATE_PROPERTY,
                List.of(SELECTED_NODE, DIAGRAM_CONTEXT, CONVERTED_NODES));
        cpdSharedPropertyCreationTool
                .setPreconditionExpression(
                        CallQuery.queryServiceOnSelf(ComponentDiagramServices.CAN_CREATE_PROPERTY_INTO_PARENT));

        List<EClass> owners = List.of(this.umlPackage.getProperty(), //
                this.umlPackage.getStructuredClassifier());
        // Port should be exclude from owners because it is not possible to create Property in Port
        this.reuseNodeAndCreateTool(cpdSharedPropertyDescription, diagramDescription, cpdSharedPropertyCreationTool, NODES, owners, List.of(this.umlPackage.getPort()));

        // Add dropped tool on Component container
        DropNodeTool cpdSharedPropertyGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(cpdSharedPropertyDescription));
        List<EClass> children = List.of(this.umlPackage.getProperty());
        this.registerCallback(cpdSharedPropertyDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            cpdSharedPropertyGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        cpdSharedPropertyDescription.getPalette().setDropNodeTool(cpdSharedPropertyGraphicalDropTool);
    }

    /**
     * Creates a shared compartment reused by <i>Interface</i> {@link NodeDescription}.
     * <p>
     * The created {@link NodeDescription} compartment is added to the <i>shared</i> {@link NodeDescription} of the
     * diagram.
     * <p>
     *
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     * @param compartmentName
     *            the name of the compartment to create
     */
    private NodeDescription createSharedCompartmentForInterfaceDescription(DiagramDescription diagramDescription, String compartmentName) {
        EClass interfaceEClass = this.umlPackage.getInterface();
        List<EClass> owners = List.of(interfaceEClass);
        List<EClass> forbiddenOwners = List.of();
        NodeDescription cpdSharedCompartmentForInterfaceDescription = this.createSharedCompartmentsDescription(diagramDescription, this.cpdSharedDescription, interfaceEClass, compartmentName, owners,
                forbiddenOwners, nodeDescription -> nodeDescription != null);
        return cpdSharedCompartmentForInterfaceDescription;
    }

    /**
     * Creates a <i>Property</i> child reused by <i>Attributes</i> compartments.
     *
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createAttributeListDescription(DiagramDescription diagramDescription, NodeDescription parentNodeDescription) {
        List<EClass> owners = List.of(this.umlPackage.getInterface());
        List<EClass> forbiddenOwners = List.of();
        NodeDescription attributeNodeDescription = this.createNodeDescriptionInCompartmentDescription(diagramDescription, parentNodeDescription, this.umlPackage.getProperty(),
                ATTRIBUTES_COMPARTMENT_SUFFIX, CallQuery.queryOperationOnSelf(this.umlPackage.getClassifier__GetAllAttributes()), this.umlPackage.getStructuredClassifier_OwnedAttribute(), owners,
                forbiddenOwners, nodeDescription -> nodeDescription != null);

        // Add Attribute Graphical dropped tool on Shared Compartment for Interface
        DropNodeTool graphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(parentNodeDescription));
        graphicalDropTool.getAcceptedNodeTypes().addAll(List.of(attributeNodeDescription));
        parentNodeDescription.getPalette().setDropNodeTool(graphicalDropTool);
    }

    /**
     * Creates a <i>Operation</i> child reused by <i>Operations</i> compartments.
     *
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createOperationListDescription(DiagramDescription diagramDescription, NodeDescription parentNodeDescription) {
        List<EClass> owners = List.of(this.umlPackage.getInterface());
        List<EClass> forbiddenOwners = List.of();
        NodeDescription operationNodeDescription = this.createNodeDescriptionInCompartmentDescription(diagramDescription, parentNodeDescription, this.umlPackage.getOperation(),
                OPERATIONS_COMPARTMENT_SUFFIX, CallQuery.queryOperationOnSelf(this.umlPackage.getClassifier__GetAllOperations()), this.umlPackage.getClass_OwnedOperation(), owners, forbiddenOwners,
                nodeDescription -> nodeDescription != null);

        // Add Operation Graphical dropped tool on Shared Compartment for Interface
        DropNodeTool graphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(parentNodeDescription));
        graphicalDropTool.getAcceptedNodeTypes().addAll(List.of(operationNodeDescription));
        parentNodeDescription.getPalette().setDropNodeTool(graphicalDropTool);

    }

    /**
     * Creates a <i>Operation</i> child reused by <i>Receptions</i> compartments.
     *
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createReceptionListDescription(DiagramDescription diagramDescription, NodeDescription parentNodeDescription) {
        List<EClass> owners = List.of(this.umlPackage.getInterface());
        List<EClass> forbiddenOwners = List.of();
        NodeDescription receptionNodeDescription = this.createNodeDescriptionInCompartmentDescription(diagramDescription, parentNodeDescription, this.umlPackage.getReception(),
                RECEPTIONS_COMPARTMENT_SUFFIX, CallQuery.queryAttributeOnSelf(UMLPackage.eINSTANCE.getInterface_OwnedReception()), this.umlPackage.getInterface_OwnedReception(), owners,
                forbiddenOwners, nodeDescription -> nodeDescription != null);
        receptionNodeDescription.setLabelExpression(CallQuery.queryServiceOnSelf(Services.RENDER_LABEL_ONE_LINE, "true", "true"));

        // Add Reception Graphical dropped tool on Shared Compartment for Interface
        DropNodeTool graphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(parentNodeDescription));
        graphicalDropTool.getAcceptedNodeTypes().addAll(List.of(receptionNodeDescription));
        parentNodeDescription.getPalette().setDropNodeTool(graphicalDropTool);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Abstraction}.
     *
     * @param diagramDescription
     *            the Component {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createAbstractionDescription(DiagramDescription diagramDescription) {
        this.createDependencyOrSubTypeDescription(diagramDescription, this.umlPackage.getAbstraction(), LineStyle.DASH, ArrowStyle.INPUT_ARROW);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link ComponentRealization}.
     *
     * @param diagramDescription
     *            the Component {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createComponentRealizationDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> componentTargetCollector = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getComponent());
        Supplier<List<NodeDescription>> classifierSourceCollector = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getClassifier());
        EdgeDescription cpdComponentRealizationDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.umlPackage.getComponentRealization(),
                this.getQueryBuilder().queryAllReachableExactType(this.umlPackage.getComponentRealization()), classifierSourceCollector, componentTargetCollector);
        EdgeStyle style = cpdComponentRealizationDescription.getStyle();
        style.setLineStyle(LineStyle.DASH);
        style.setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);
        diagramDescription.getEdgeDescriptions().add(cpdComponentRealizationDescription);
        EdgeTool cpdComponentRealizationCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(cpdComponentRealizationDescription, this.umlPackage.getPackage_PackagedElement());
        this.registerCallback(cpdComponentRealizationDescription, () -> {
            this.addEdgeToolInEdgesToolSection(classifierSourceCollector.get(), cpdComponentRealizationCreationTool);
        });
        this.getViewBuilder().addDefaultReconnectionTools(cpdComponentRealizationDescription);
    }

    private void createConnectorDescription(DiagramDescription diagramDescription) {

        Supplier<List<NodeDescription>> sourceAndTargets = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getPort(), this.umlPackage.getProperty());

        EdgeDescription cpdConnectorDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.umlPackage.getConnector(),
                this.getQueryBuilder().queryAllReachable(this.umlPackage.getConnector()), sourceAndTargets, sourceAndTargets);
        cpdConnectorDescription.setBeginLabelExpression(this.getQueryBuilder().createDomainBaseEdgeSourceLabelExpression());
        cpdConnectorDescription.setEndLabelExpression(this.getQueryBuilder().createDomainBaseEdgeTargetLabelExpression());
        // Use ConnectorEnd#partWithPort to handle complex Connector edges
        cpdConnectorDescription.setPreconditionExpression(new CallQuery(Variables.SELF)//
                .callService(ComponentDiagramServices.SHOULD_DISPLAY_CONNECTOR, //
                        Variables.SEMANTIC_EDGE_SOURCE, //
                        Variables.SEMANTIC_EDGE_TARGET, //
                        Variables.GRAPHICAL_EDGE_SOURCE, //
                        Variables.GRAPHICAL_EDGE_TARGET, //
                        Variables.CACHE, //
                        Variables.EDITING_CONTEXT));
        diagramDescription.getEdgeDescriptions().add(cpdConnectorDescription);
        EdgeTool cpdConnectorCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(cpdConnectorDescription, this.umlPackage.getStructuredClassifier_OwnedConnector());
        this.registerCallback(cpdConnectorDescription, () -> {
            this.addEdgeToolInEdgesToolSection(sourceAndTargets.get(), cpdConnectorCreationTool);
        });
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Dependency}.
     *
     * @param diagramDescription
     *            the Component {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createDependencyDescription(DiagramDescription diagramDescription) {
        this.createDependencyOrSubTypeDescription(diagramDescription, this.umlPackage.getDependency(), LineStyle.DASH, ArrowStyle.INPUT_ARROW);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Generalization}.
     *
     * @param diagramDescription
     *            the Component {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createGeneralizationDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> sourceAndTargetDescriptionsSupplier = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getClassifier());

        EClass generalization = this.umlPackage.getGeneralization();
        EdgeDescription cpdGeneralizationDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(generalization,
                this.getQueryBuilder().queryAllReachableExactType(generalization), sourceAndTargetDescriptionsSupplier, sourceAndTargetDescriptionsSupplier, false);
        cpdGeneralizationDescription.getStyle().setLineStyle(LineStyle.SOLID);
        cpdGeneralizationDescription.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);
        EdgeTool cpdGeneralizationCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(cpdGeneralizationDescription, this.umlPackage.getClassifier_Generalization());
        this.registerCallback(cpdGeneralizationDescription, () -> {
            this.addEdgeToolInEdgesToolSection(sourceAndTargetDescriptionsSupplier.get(), cpdGeneralizationCreationTool);
        });

        diagramDescription.getEdgeDescriptions().add(cpdGeneralizationDescription);

        this.getViewBuilder().addDefaultReconnectionTools(cpdGeneralizationDescription);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link InterfaceRealization}.
     *
     * @param diagramDescription
     *            the Component {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createInterfaceRealizationDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> interfaceTargetCollector = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getInterface());
        Supplier<List<NodeDescription>> behavioredClassifierSourceCollector = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getBehavioredClassifier());
        EdgeDescription cpdInterfaceRealizationDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.umlPackage.getInterfaceRealization(),
                this.getQueryBuilder().queryAllReachableExactType(this.umlPackage.getInterfaceRealization()), behavioredClassifierSourceCollector, interfaceTargetCollector);
        EdgeStyle style = cpdInterfaceRealizationDescription.getStyle();
        style.setLineStyle(LineStyle.DASH);
        style.setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);
        diagramDescription.getEdgeDescriptions().add(cpdInterfaceRealizationDescription);
        EdgeTool cpdInterfaceRealizationCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(cpdInterfaceRealizationDescription, this.umlPackage.getPackage_PackagedElement());
        this.registerCallback(cpdInterfaceRealizationDescription, () -> {
            this.addEdgeToolInEdgesToolSection(behavioredClassifierSourceCollector.get(), cpdInterfaceRealizationCreationTool);
        });
        this.getViewBuilder().addDefaultReconnectionTools(cpdInterfaceRealizationDescription);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Manifestation}.
     *
     * @param diagramDescription
     *            the Component {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createManifestationDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> packageableELementTargetCollector = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getPackageableElement());
        Supplier<List<NodeDescription>> namedElementSourceCollector = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getNamedElement());
        EdgeDescription cpdManifestationDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.umlPackage.getManifestation(),
                this.getQueryBuilder().queryAllReachableExactType(this.umlPackage.getManifestation()), namedElementSourceCollector, packageableELementTargetCollector);
        EdgeStyle style = cpdManifestationDescription.getStyle();
        style.setLineStyle(LineStyle.DASH);
        style.setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        diagramDescription.getEdgeDescriptions().add(cpdManifestationDescription);
        EdgeTool cpdManifestationCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(cpdManifestationDescription, this.umlPackage.getPackage_PackagedElement());
        this.registerCallback(cpdManifestationDescription, () -> {
            this.addEdgeToolInEdgesToolSection(namedElementSourceCollector.get(), cpdManifestationCreationTool);
        });
        this.getViewBuilder().addDefaultReconnectionTools(cpdManifestationDescription);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Substitution}.
     *
     * @param diagramDescription
     *            the Component {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createSubstitutionDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> classifierSourceTargetCollector = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getClassifier());
        EdgeDescription cpdSubstitutionDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.umlPackage.getSubstitution(),
                this.getQueryBuilder().queryAllReachableExactType(this.umlPackage.getSubstitution()), classifierSourceTargetCollector, classifierSourceTargetCollector);
        EdgeStyle style = cpdSubstitutionDescription.getStyle();
        style.setLineStyle(LineStyle.DASH);
        style.setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);
        diagramDescription.getEdgeDescriptions().add(cpdSubstitutionDescription);
        EdgeTool cpdSubstitutionCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(cpdSubstitutionDescription, this.umlPackage.getClassifier_Substitution());
        this.registerCallback(cpdSubstitutionDescription, () -> {
            this.addEdgeToolInEdgesToolSection(classifierSourceTargetCollector.get(), cpdSubstitutionCreationTool);
        });
        this.getViewBuilder().addDefaultReconnectionTools(cpdSubstitutionDescription);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Usage}.
     *
     * @param diagramDescription
     *            the Component {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createUsageDescription(DiagramDescription diagramDescription) {
        this.createDependencyOrSubTypeDescription(diagramDescription, this.umlPackage.getUsage(), LineStyle.DASH, ArrowStyle.INPUT_ARROW);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Dependency} or subType.
     *
     * @param diagramDescription
     *            the Component {@link DiagramDescription} containing the created {@link EdgeDescription}
     * @param edgeToCreate
     *            kind of edge to create which should be a Dependency or a subType
     * @param lineStyle
     *            the line style of the edge
     * @param arrowStyle
     *            the arrow style of the edge
     */
    private void createDependencyOrSubTypeDescription(DiagramDescription diagramDescription, EClass edgeToCreate, LineStyle lineStyle, ArrowStyle arrowStyle) {
        Supplier<List<NodeDescription>> namedElementCollector = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getNamedElement());
        EdgeDescription cpdDependencyDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(edgeToCreate,
                this.getQueryBuilder().queryAllReachableExactType(edgeToCreate), namedElementCollector, namedElementCollector);
        EdgeStyle style = cpdDependencyDescription.getStyle();
        style.setLineStyle(lineStyle);
        style.setTargetArrowStyle(arrowStyle);
        diagramDescription.getEdgeDescriptions().add(cpdDependencyDescription);
        EdgeTool cpdDependencyCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(cpdDependencyDescription, this.umlPackage.getPackage_PackagedElement());
        this.registerCallback(cpdDependencyDescription, () -> {
            this.addEdgeToolInEdgesToolSection(namedElementCollector.get(), cpdDependencyCreationTool);
        });
        this.getViewBuilder().addDefaultReconnectionTools(cpdDependencyDescription);
    }

    @Override
    protected List<NodeDescription> collectNodesWithDomain(DiagramDescription description, EClass... domains) {
        return super.collectNodesWithDomain(description, domains).stream() //
                .filter(nd -> !SHARED_DESCRIPTIONS.equals(nd.getName())) //
                .toList();
    }

}
