/*****************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo.
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

import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.CACHE;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.DIAGRAM_CONTEXT;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.EDGE_SOURCE;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.EDGE_TARGET;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.EDITING_CONTEXT;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.GRAPHICAL_EDGE_SOURCE;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.GRAPHICAL_EDGE_TARGET;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.SEMANTIC_EDGE_SOURCE;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.SEMANTIC_EDGE_TARGET;

import java.util.List;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.web.application.representations.view.CreationToolsUtil;
import org.eclipse.papyrus.web.application.representations.view.aql.CallQuery;
import org.eclipse.papyrus.web.application.representations.view.aql.Services;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeStyle;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Builder of the "Class Diagram " diagram representation.
 *
 * @author Arthur Daussy
 */
public final class CDDiagramDescriptionBuilder extends AbstractRepresentationDescriptionBuilder {

    public static final String NESTED_CLASSIFIERS_COMPARTMENT_SUFFIX = "NestedClassifiers";

    public static final String OPERATIONS_COMPARTMENT_SUFFIX = "Operations";

    public static final String ATTRIBUTES_COMPARTMENT_SUFFIX = "Attributes";

    public static final String LITERAL_COMPARTMENT_SUFFIX = "Literals";

    public static final String CD_REP_NAME = "Class Diagram";

    public static final String CD_PREFIX = "CD_";

    private static final String NEW_CONTAINMENT_LINK_TOOL_LABEL = "New Containment Link";

    public static final String CLASSIFIER_CONTAINMENT_LINK_EDGE_ID = CD_PREFIX + "_ClassifierContainmentLink_FeatureEdge"; //$NON-NLS-1$

    public static final String PACKAGE_CONTAINMENT_LINK_EDGE_ID = CD_PREFIX + "_PackageContainmentLink_FeatureEdge"; //$NON-NLS-1$

    private final UMLPackage pack = UMLPackage.eINSTANCE;

    public CDDiagramDescriptionBuilder() {
        super(CD_PREFIX, CD_REP_NAME, UMLPackage.eINSTANCE.getPackage());
    }

    @Override
    protected void fillDescription(DiagramDescription diagramDescription) {

        this.createPackageDescription(diagramDescription);
        this.createModelDescription(diagramDescription);

        this.createClassDescription(diagramDescription);
        this.createInterfaceDescription(diagramDescription);
        this.createPrimitiveTypeDescription(diagramDescription);
        this.createDataTypeDescription(diagramDescription);
        this.createEnumerationDescription(diagramDescription);
        this.createPackageMergeDescription(diagramDescription);
        this.createPackageImportDescription(diagramDescription);
        this.createAbstractionDescription(diagramDescription);
        this.createDependencyDescription(diagramDescription);
        this.createInterfaceRealizationDescription(diagramDescription);
        this.createGeneralizationDescription(diagramDescription);
        this.createAssociationDescription(diagramDescription);
        this.createUsageDescription(diagramDescription);

        this.createClassifierContainmentLink(diagramDescription);
        this.createPackageContainmentLink(diagramDescription);

        this.createCommentDescription(diagramDescription);

        diagramDescription.getPalette().setDropTool(this.getViewBuilder().createGenericDropTool(this.getIdBuilder().getDropToolId()));
    }

    private void createUsageDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> classifierCollector = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getNamedElement());
        EdgeDescription usageDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.pack.getUsage(), this.getQueryBuilder().queryAllReachable(this.pack.getUsage()),
                classifierCollector, classifierCollector);
        EdgeStyle style = usageDescription.getStyle();
        style.setLineStyle(LineStyle.DASH);
        style.setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        diagramDescription.getEdgeDescriptions().add(usageDescription);

        EdgeTool creationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(usageDescription, this.pack.getPackage_PackagedElement());
        this.registerCallback(creationTool, () -> {
            CreationToolsUtil.addEdgeCreationTool(classifierCollector, creationTool);
        });
        this.getViewBuilder().addDefaultReconnectionTools(usageDescription);
    }

    private void createAssociationDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> sourceAndTargetDescriptionsSupplier = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getClassifier());

        EClass association = this.pack.getAssociation();
        EdgeDescription padAssociation = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(association, this.getQueryBuilder().queryAllReachableExactType(association),
                sourceAndTargetDescriptionsSupplier, sourceAndTargetDescriptionsSupplier);
        padAssociation.getStyle().setLineStyle(LineStyle.SOLID);
        padAssociation.getStyle().setTargetArrowStyle(ArrowStyle.NONE);
        padAssociation.getStyle().setSourceArrowStyle(ArrowStyle.NONE);

        this.registerCallback(padAssociation, () -> {
            CreationToolsUtil.addEdgeCreationTool(sourceAndTargetDescriptionsSupplier, this.getViewBuilder().createDefaultDomainBasedEdgeTool(padAssociation, this.pack.getPackage_PackagedElement()));
            CreationToolsUtil.addEdgeCreationTool(sourceAndTargetDescriptionsSupplier,
                    this.createSpecializedAssociationDomainBasedEdgeTool("New Composite Association", ClassDiagramServices.CREATION_COMPOSITE_ASSOCIATION));
            CreationToolsUtil.addEdgeCreationTool(sourceAndTargetDescriptionsSupplier,
                    this.createSpecializedAssociationDomainBasedEdgeTool("New Shared Association", ClassDiagramServices.CREATION_SHARED_ASSOCIATION));
        });

        padAssociation.setBeginLabelExpression(this.getQueryBuilder().createDomainBaseEdgeSourceLabelExpression());
        padAssociation.getPalette().setBeginLabelEditTool(this.getViewBuilder().createDirectEditTool(CallQuery.queryServiceOnSelf(ClassDiagramServices.GET_ASSOCIATION_TARGET)));

        padAssociation.setEndLabelExpression(this.getQueryBuilder().createDomainBaseEdgeTargetLabelExpression());
        padAssociation.getPalette().setEndLabelEditTool(this.getViewBuilder().createDirectEditTool(CallQuery.queryServiceOnSelf(ClassDiagramServices.GET_ASSOCIATION_SOURCE)));

        // Can be improve once https://github.com/PapyrusSirius/papyrus-web/issues/208 is closed
        new AssociationEdgeCustomStyleBuilder(padAssociation).addCustomArowStyles();

        diagramDescription.getEdgeDescriptions().add(padAssociation);

        this.getViewBuilder().addDefaultReconnectionTools(padAssociation);
    }

    private EdgeTool createSpecializedAssociationDomainBasedEdgeTool(String specializationName, String serviceName) {
        EdgeTool tool = DiagramFactory.eINSTANCE.createEdgeTool();
        tool.setName(specializationName);
        ChangeContext changeContext = ViewFactory.eINSTANCE.createChangeContext();

        String query = new CallQuery(SEMANTIC_EDGE_SOURCE)//
                .callService(serviceName, //
                        SEMANTIC_EDGE_TARGET, //
                        EDGE_SOURCE, //
                        EDGE_TARGET, //
                        EDITING_CONTEXT, //
                        DIAGRAM_CONTEXT);
        changeContext.setExpression(query);

        tool.getBody().add(changeContext);
        return tool;
    }

    private void createPackageContainmentLink(DiagramDescription diagramDescription) {

        Supplier<List<NodeDescription>> sourceProvider = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getPackage());
        Supplier<List<NodeDescription>> targetProvider = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getPackageableElement());

        EdgeDescription containmentLinkEdge = this.getViewBuilder().createFeatureEdgeDescription(//
                PACKAGE_CONTAINMENT_LINK_EDGE_ID, //
                this.getQueryBuilder().emptyString(), //
                CallQuery.queryAttributeOnSelf(this.pack.getPackage_PackagedElement()), //
                sourceProvider, //
                targetProvider);

        containmentLinkEdge.setPreconditionExpression(new CallQuery(GRAPHICAL_EDGE_SOURCE).callService(Services.IS_NOT_VISUAL_DESCENDANT, GRAPHICAL_EDGE_TARGET, CACHE)); // $NON-NLS-1$

        containmentLinkEdge.getStyle().setSourceArrowStyle(ArrowStyle.CROSSED_CIRCLE);

        diagramDescription.getEdgeDescriptions().add(containmentLinkEdge);

        // Create containment Link tool

        EdgeTool tool = DiagramFactory.eINSTANCE.createEdgeTool();
        tool.setName(NEW_CONTAINMENT_LINK_TOOL_LABEL); //

        String toolQuery = new CallQuery(SEMANTIC_EDGE_TARGET).callService(Services.MOVE_IN, SEMANTIC_EDGE_SOURCE, this.getQueryBuilder().aqlString(this.pack.getPackage_PackagedElement().getName())); // $NON-NLS-1$

        ChangeContext changeContext = this.getViewBuilder().createChangeContextOperation(toolQuery);
        tool.getBody().add(changeContext);
        this.registerCallback(containmentLinkEdge, () -> {
            CreationToolsUtil.addEdgeCreationTool(sourceProvider, tool);
        });
    }

    private void createClassifierContainmentLink(DiagramDescription diagramDescription) {

        Supplier<List<NodeDescription>> sourceProvider = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getClass_());
        Supplier<List<NodeDescription>> targetProvider = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getClassifier());

        EdgeDescription containmentLinkEdge = this.getViewBuilder().createFeatureEdgeDescription(//
                CLASSIFIER_CONTAINMENT_LINK_EDGE_ID, //
                this.getQueryBuilder().emptyString(), //
                CallQuery.queryAttributeOnSelf(this.pack.getClass_NestedClassifier()), //
                sourceProvider, //
                targetProvider);

        containmentLinkEdge.setPreconditionExpression(new CallQuery(GRAPHICAL_EDGE_SOURCE).callService(Services.IS_NOT_VISUAL_DESCENDANT, GRAPHICAL_EDGE_TARGET, CACHE)); // $NON-NLS-1$

        containmentLinkEdge.getStyle().setSourceArrowStyle(ArrowStyle.CROSSED_CIRCLE);

        diagramDescription.getEdgeDescriptions().add(containmentLinkEdge);

        // Create containment Link tool
        EdgeTool tool = DiagramFactory.eINSTANCE.createEdgeTool();
        tool.setName(NEW_CONTAINMENT_LINK_TOOL_LABEL); //

        String toolQuery = new CallQuery(SEMANTIC_EDGE_TARGET).callService(Services.MOVE_IN, SEMANTIC_EDGE_SOURCE, this.getQueryBuilder().aqlString(this.pack.getClass_NestedClassifier().getName())); // $NON-NLS-1$

        ChangeContext changeContext = this.getViewBuilder().createChangeContextOperation(toolQuery);
        tool.getBody().add(changeContext);
        this.registerCallback(containmentLinkEdge, () -> {
            CreationToolsUtil.addEdgeCreationTool(sourceProvider, tool);
        });
    }

    private void createGeneralizationDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> sourceAndTargetDescriptionsSupplier = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getClassifier());

        EClass generalization = this.pack.getGeneralization();
        EdgeDescription padGeneralization = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(generalization, this.getQueryBuilder().queryAllReachableExactType(generalization),
                sourceAndTargetDescriptionsSupplier, sourceAndTargetDescriptionsSupplier);
        padGeneralization.getStyle().setLineStyle(LineStyle.SOLID);
        padGeneralization.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);
        this.registerCallback(padGeneralization, () -> {
            CreationToolsUtil.addEdgeCreationTool(sourceAndTargetDescriptionsSupplier,
                    this.getViewBuilder().createDefaultDomainBasedEdgeTool(padGeneralization, this.pack.getClassifier_Generalization()));
        });

        diagramDescription.getEdgeDescriptions().add(padGeneralization);

        this.getViewBuilder().addDefaultReconnectionTools(padGeneralization);
    }

    private void createInterfaceRealizationDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> sourceDescriptionsSupplier = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getBehavioredClassifier());
        Supplier<List<NodeDescription>> targetDescriptionsSupplier = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getInterface());

        EdgeDescription padInterfaceRealization = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.pack.getInterfaceRealization(),
                this.getQueryBuilder().queryAllReachableExactType(this.pack.getInterfaceRealization()), sourceDescriptionsSupplier, targetDescriptionsSupplier);
        padInterfaceRealization.getStyle().setLineStyle(LineStyle.DASH);
        padInterfaceRealization.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);
        this.registerCallback(padInterfaceRealization, () -> {
            CreationToolsUtil.addEdgeCreationTool(sourceDescriptionsSupplier,
                    this.getViewBuilder().createDefaultDomainBasedEdgeTool(padInterfaceRealization, this.pack.getBehavioredClassifier_InterfaceRealization()));
        });
        diagramDescription.getEdgeDescriptions().add(padInterfaceRealization);

        this.getViewBuilder().addDefaultReconnectionTools(padInterfaceRealization);
    }

    private void createEnumerationDescription(DiagramDescription diagramDescription) {
        EClass enumerationEClass = this.pack.getEnumeration();
        NodeDescription enumerationLiterals = this.newNodeBuilder(enumerationEClass, this.getViewBuilder().createRectangularNodeStyle(true, true))//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription())//
                .semanticCandidateExpression(this.getQueryBuilder().queryAllReachable(enumerationEClass))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(enumerationEClass.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(enumerationEClass.getName())) //
                .build();
        NodeTool creationTool = this.getViewBuilder().createCreationTool(this.pack.getPackage_PackagedElement(), enumerationEClass);
        Supplier<List<NodeDescription>> packageOwners = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getPackage());
        Supplier<List<NodeDescription>> interfaceOwners = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getInterface());
        Supplier<List<NodeDescription>> compartmentOwners = () -> this.collectNodesWithDomain(diagramDescription, true, false, this.pack.getClass_()) //
                .stream() //
                .filter(desc -> desc.getName().endsWith(NESTED_CLASSIFIERS_COMPARTMENT_SUFFIX)) //
                .toList();
        this.registerCallback(enumerationLiterals, () -> {
            CreationToolsUtil.addNodeCreationTool(packageOwners, creationTool);
            CreationToolsUtil.addNodeCreationTool(compartmentOwners, creationTool);
            CreationToolsUtil.addNodeCreationTool(interfaceOwners, creationTool);
            diagramDescription.getPalette().getNodeTools().add(creationTool);
        });

        diagramDescription.getNodeDescriptions().add(enumerationLiterals);

        // Create Enumeration Literals Compartment
        this.newListCompartmentBuilder().withChildrenType(this.pack.getEnumerationLiteral())//
                .withCompartmentNameSuffix(LITERAL_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryAttributeOnSelf(this.pack.getEnumeration_OwnedLiteral()))//
                .addCreationTools(this.pack.getEnumeration_OwnedLiteral(), this.pack.getEnumerationLiteral())//
                .buildIn(enumerationLiterals);
    }

    private void createInterfaceDescription(DiagramDescription diagramDescription) {

        EClass interfaceEClass = this.pack.getInterface();
        NodeDescription interfaceDescription = this.newNodeBuilder(interfaceEClass, this.getViewBuilder().createRectangularNodeStyle(true, true))//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription())//
                .semanticCandidateExpression(this.getQueryBuilder().queryAllReachable(interfaceEClass))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(interfaceEClass.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(interfaceEClass.getName())) //
                .build();
        NodeTool creationTool = this.getViewBuilder().createCreationTool(this.pack.getPackage_PackagedElement(), interfaceEClass);
        Supplier<List<NodeDescription>> packageOwners = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getPackage());
        Supplier<List<NodeDescription>> interfaceOwners = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getInterface());
        Supplier<List<NodeDescription>> compartmentOwners = () -> this.collectNodesWithDomain(diagramDescription, true, false, this.pack.getClass_()) //
                .stream() //
                .filter(desc -> desc.getName().endsWith(NESTED_CLASSIFIERS_COMPARTMENT_SUFFIX)) //
                .toList();
        this.registerCallback(interfaceDescription, () -> {
            CreationToolsUtil.addNodeCreationTool(packageOwners, creationTool);
            CreationToolsUtil.addNodeCreationTool(compartmentOwners, creationTool);
            CreationToolsUtil.addNodeCreationTool(interfaceOwners, creationTool);
            diagramDescription.getPalette().getNodeTools().add(creationTool);
        });

        diagramDescription.getNodeDescriptions().add(interfaceDescription);

        // Create Attributes Compartment
        this.newListCompartmentBuilder()//
                .withChildrenType(this.pack.getProperty())//
                .withCompartmentNameSuffix(ATTRIBUTES_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryOperationOnSelf(this.pack.getClassifier__GetAllAttributes()))//
                .addCreationTools(this.pack.getInterface_OwnedAttribute(), this.pack.getProperty())//
                .buildIn(interfaceDescription);

        // Create Operation Compartment
        this.newListCompartmentBuilder()//
                .withChildrenType(this.pack.getOperation())//
                .withCompartmentNameSuffix(OPERATIONS_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryOperationOnSelf(this.pack.getClassifier__GetAllOperations()))//
                .addCreationTools(this.pack.getInterface_OwnedOperation(), this.pack.getOperation())//
                .buildIn(interfaceDescription);

        // Create Nested Classifier Compartment
        this.newListCompartmentBuilder()//
                .withChildrenType(this.pack.getClassifier())//
                .withCompartmentNameSuffix(NESTED_CLASSIFIERS_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryAttributeOnSelf(this.pack.getInterface_NestedClassifier()))//
                .addCreationTools(this.pack.getInterface_NestedClassifier(), this.pack.getClass_())//
                .addCreationTools(this.pack.getInterface_NestedClassifier(), this.pack.getDataType())//
                .addCreationTools(this.pack.getInterface_NestedClassifier(), this.pack.getEnumeration())//
                .addCreationTools(this.pack.getInterface_NestedClassifier(), this.pack.getPrimitiveType())//
                .addCreationTools(this.pack.getInterface_NestedClassifier(), this.pack.getInterface())//
                .buildIn(interfaceDescription);

    }

    private void createPrimitiveTypeDescription(DiagramDescription diagramDescription) {

        EClass primitiveTypeEClass = this.pack.getPrimitiveType();
        NodeDescription primitiveTypeDescription = this.newNodeBuilder(primitiveTypeEClass, this.getViewBuilder().createRectangularNodeStyle(true, true))//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription())//
                .semanticCandidateExpression(this.getQueryBuilder().queryAllReachable(primitiveTypeEClass))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(primitiveTypeEClass.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(primitiveTypeEClass.getName())) //
                .build();
        NodeTool creationTool = this.getViewBuilder().createCreationTool(this.pack.getPackage_PackagedElement(), primitiveTypeEClass);
        Supplier<List<NodeDescription>> packageOwners = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getPackage());
        Supplier<List<NodeDescription>> interfaceOwners = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getInterface());
        Supplier<List<NodeDescription>> compartmentOwners = () -> this.collectNodesWithDomain(diagramDescription, true, false, this.pack.getClass_()) //
                .stream() //
                .filter(desc -> desc.getName().endsWith(NESTED_CLASSIFIERS_COMPARTMENT_SUFFIX)) //
                .toList();
        this.registerCallback(primitiveTypeDescription, () -> {
            CreationToolsUtil.addNodeCreationTool(packageOwners, creationTool);
            CreationToolsUtil.addNodeCreationTool(compartmentOwners, creationTool);
            CreationToolsUtil.addNodeCreationTool(interfaceOwners, creationTool);
            diagramDescription.getPalette().getNodeTools().add(creationTool);
        });
        diagramDescription.getNodeDescriptions().add(primitiveTypeDescription);

        // Create Attributes Compartment
        this.newListCompartmentBuilder().withChildrenType(this.pack.getProperty())//
                .withCompartmentNameSuffix(ATTRIBUTES_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryOperationOnSelf(this.pack.getClassifier__GetAllAttributes()))//
                .addCreationTools(this.pack.getDataType_OwnedAttribute(), this.pack.getProperty())//
                .buildIn(primitiveTypeDescription);

        // Create Operation Compartment
        this.newListCompartmentBuilder().withChildrenType(this.pack.getOperation())//
                .withCompartmentNameSuffix(OPERATIONS_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryOperationOnSelf(this.pack.getClassifier__GetAllOperations()))//
                .addCreationTools(this.pack.getDataType_OwnedOperation(), this.pack.getOperation())//
                .buildIn(primitiveTypeDescription);
    }

    private void createDataTypeDescription(DiagramDescription diagramDescription) {

        EClass dataTypeEClass = this.pack.getDataType();
        NodeDescription classDescription = this.newNodeBuilder(dataTypeEClass, this.getViewBuilder().createRectangularNodeStyle(true, true))//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription())//
                .semanticCandidateExpression(this.getQueryBuilder().queryAllReachable(dataTypeEClass))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(dataTypeEClass.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(dataTypeEClass.getName())) //
                .build();

        NodeTool creationTool = this.getViewBuilder().createCreationTool(this.pack.getPackage_PackagedElement(), dataTypeEClass);
        Supplier<List<NodeDescription>> packageOwners = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getPackage());
        Supplier<List<NodeDescription>> interfaceOwners = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getInterface());
        Supplier<List<NodeDescription>> compartmentOwners = () -> this.collectNodesWithDomain(diagramDescription, true, false, this.pack.getClass_()) //
                .stream() //
                .filter(desc -> desc.getName().endsWith(NESTED_CLASSIFIERS_COMPARTMENT_SUFFIX)) //
                .toList();
        this.registerCallback(classDescription, () -> {
            CreationToolsUtil.addNodeCreationTool(packageOwners, creationTool);
            CreationToolsUtil.addNodeCreationTool(compartmentOwners, creationTool);
            CreationToolsUtil.addNodeCreationTool(interfaceOwners, creationTool);
            diagramDescription.getPalette().getNodeTools().add(creationTool);
        });

        diagramDescription.getNodeDescriptions().add(classDescription);

        // Create Attributes Compartment
        this.newListCompartmentBuilder().withChildrenType(this.pack.getProperty())//
                .withCompartmentNameSuffix(ATTRIBUTES_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryOperationOnSelf(this.pack.getClassifier__GetAllAttributes()))//
                .addCreationTools(this.pack.getDataType_OwnedAttribute(), this.pack.getProperty())//
                .buildIn(classDescription);

        // Create Operation Compartment
        this.newListCompartmentBuilder().withChildrenType(this.pack.getOperation())//
                .withCompartmentNameSuffix(OPERATIONS_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryOperationOnSelf(this.pack.getClassifier__GetAllOperations()))//
                .addCreationTools(this.pack.getDataType_OwnedOperation(), this.pack.getOperation())//
                .buildIn(classDescription);

    }

    private void createClassDescription(DiagramDescription diagramDescription) {
        NodeDescription classDescription = this.newNodeBuilder(this.pack.getClass_(), this.getViewBuilder().createRectangularNodeStyle(true, true))//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription())//
                .semanticCandidateExpression(this.getQueryBuilder().queryAllReachable(this.pack.getClass_()))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(this.pack.getClass_().getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(this.pack.getClass_().getName())) //
                .build();
        NodeTool creationTool = this.getViewBuilder().createCreationTool(this.pack.getPackage_PackagedElement(), this.pack.getClass_());
        Supplier<List<NodeDescription>> packageOwners = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getPackage());
        Supplier<List<NodeDescription>> interfaceOwners = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getInterface());
        Supplier<List<NodeDescription>> compartmentOwners = () -> this.collectNodesWithDomain(diagramDescription, true, false, this.pack.getClass_()) //
                .stream() //
                .filter(desc -> desc.getName().endsWith(NESTED_CLASSIFIERS_COMPARTMENT_SUFFIX)) //
                .toList();
        this.registerCallback(classDescription, () -> {
            CreationToolsUtil.addNodeCreationTool(packageOwners, creationTool);
            CreationToolsUtil.addNodeCreationTool(compartmentOwners, creationTool);
            CreationToolsUtil.addNodeCreationTool(interfaceOwners, creationTool);
        });
        diagramDescription.getPalette().getNodeTools().add(creationTool);
        diagramDescription.getNodeDescriptions().add(classDescription);

        // Create Attributes Compartment
        this.newListCompartmentBuilder().withChildrenType(this.pack.getProperty())//
                .withCompartmentNameSuffix(ATTRIBUTES_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryOperationOnSelf(this.pack.getClassifier__GetAllAttributes()))//
                .addCreationTools(this.pack.getStructuredClassifier_OwnedAttribute(), this.pack.getProperty())//
                .buildIn(classDescription);

        // Create Operation Compartment
        this.newListCompartmentBuilder().withChildrenType(this.pack.getOperation())//
                .withCompartmentNameSuffix(OPERATIONS_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryOperationOnSelf(this.pack.getClassifier__GetAllOperations()))//
                .addCreationTools(this.pack.getClass_OwnedOperation(), this.pack.getOperation())//
                .buildIn(classDescription);

        // Create Nested Classifier Compartment
        this.newListCompartmentBuilder().withChildrenType(this.pack.getClassifier())//
                .withCompartmentNameSuffix(NESTED_CLASSIFIERS_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryAttributeOnSelf(this.pack.getClass_NestedClassifier()))//
                .addCreationTools(this.pack.getClass_NestedClassifier(), this.pack.getClass_())//
                .addCreationTools(this.pack.getClass_NestedClassifier(), this.pack.getDataType())//
                .addCreationTools(this.pack.getClass_NestedClassifier(), this.pack.getEnumeration())//
                .addCreationTools(this.pack.getClass_NestedClassifier(), this.pack.getPrimitiveType())//
                .addCreationTools(this.pack.getClass_NestedClassifier(), this.pack.getInterface())//
                .buildIn(classDescription);

    }

    private void createDependencyDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> namedElementDescriptions = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getNamedElement());
        EdgeDescription padDependency = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.pack.getDependency(),
                this.getQueryBuilder().queryAllReachableExactType(this.pack.getDependency()), namedElementDescriptions, namedElementDescriptions);
        padDependency.getStyle().setLineStyle(LineStyle.DASH);
        padDependency.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        EdgeTool creationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(padDependency, this.pack.getPackage_PackagedElement());
        this.registerCallback(padDependency, () -> {
            CreationToolsUtil.addEdgeCreationTool(namedElementDescriptions, creationTool);
        });
        diagramDescription.getEdgeDescriptions().add(padDependency);

        this.getViewBuilder().addDefaultReconnectionTools(padDependency);
    }

    private void createAbstractionDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> namedElementDescriptions = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getNamedElement());
        EdgeDescription padAbstraction = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.pack.getAbstraction(),
                this.getQueryBuilder().queryAllReachableExactType(this.pack.getAbstraction()), namedElementDescriptions, namedElementDescriptions);
        padAbstraction.getStyle().setLineStyle(LineStyle.DASH);
        padAbstraction.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        EdgeTool creationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(padAbstraction, this.pack.getPackage_PackagedElement());
        this.registerCallback(padAbstraction, () -> {
            CreationToolsUtil.addEdgeCreationTool(namedElementDescriptions, creationTool);
        });
        diagramDescription.getEdgeDescriptions().add(padAbstraction);

        this.getViewBuilder().addDefaultReconnectionTools(padAbstraction);
    }

    private void createPackageMergeDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> packageDescriptions = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getPackage());
        EdgeDescription padPackageMerge = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.pack.getPackageMerge(),
                this.getQueryBuilder().queryAllReachable(this.pack.getPackageMerge()), packageDescriptions, packageDescriptions);
        padPackageMerge.getStyle().setLineStyle(LineStyle.DASH);
        padPackageMerge.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        EdgeTool creationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(padPackageMerge, this.pack.getPackage_PackageMerge());
        this.registerCallback(padPackageMerge, () -> {
            CreationToolsUtil.addEdgeCreationTool(packageDescriptions, creationTool);
        });
        diagramDescription.getEdgeDescriptions().add(padPackageMerge);
        this.getViewBuilder().addDefaultReconnectionTools(padPackageMerge);
    }

    private void createPackageImportDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> packageDescriptions = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getPackage());
        EdgeDescription padPackageImport = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.pack.getPackageImport(),
                this.getQueryBuilder().queryAllReachable(this.pack.getPackageImport()), packageDescriptions, packageDescriptions);
        padPackageImport.getStyle().setLineStyle(LineStyle.DASH);
        padPackageImport.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);

        EdgeTool creationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(padPackageImport, this.pack.getNamespace_PackageImport());
        this.registerCallback(padPackageImport, () -> {
            CreationToolsUtil.addEdgeCreationTool(packageDescriptions, creationTool);
        });
        diagramDescription.getEdgeDescriptions().add(padPackageImport);
        this.getViewBuilder().addDefaultReconnectionTools(padPackageImport);

    }

}
