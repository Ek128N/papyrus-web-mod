/*******************************************************************************
 * Copyright (c) 2022 CEA, Obeo.
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

        createPackageDescription(diagramDescription);
        createModelDescription(diagramDescription);

        createClassDescription(diagramDescription);
        createInterfaceDescription(diagramDescription);
        createPrimitiveTypeDescription(diagramDescription);
        createDataTypeDescription(diagramDescription);
        createEnumerationDescription(diagramDescription);
        createPackageMergeDescription(diagramDescription);
        createPackageImportDescription(diagramDescription);
        createAbstractionDescription(diagramDescription);
        createDependencyDescription(diagramDescription);
        createInterfaceRealizationDescription(diagramDescription);
        createGeneralizationDescription(diagramDescription);
        createAssociationDescription(diagramDescription);
        createUsageDescription(diagramDescription);

        createClassifierContainmentLink(diagramDescription);
        createPackageContainmentLink(diagramDescription);

        createCommentDescription(diagramDescription);

        diagramDescription.getPalette().setDropTool(getViewBuilder().createGenericDropTool(getIdBuilder().getDropToolId()));
    }

    private void createUsageDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> classifierCollector = () -> collectNodesWithDomain(diagramDescription, pack.getNamedElement());
        EdgeDescription usageDescription = getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(pack.getUsage(), getQueryBuilder().queryAllReachable(pack.getUsage()),
                classifierCollector, classifierCollector);
        EdgeStyle style = usageDescription.getStyle();
        style.setLineStyle(LineStyle.DASH);
        style.setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        diagramDescription.getEdgeDescriptions().add(usageDescription);

        EdgeTool creationTool = getViewBuilder().createDefaultDomainBasedEdgeTool(usageDescription, pack.getPackage_PackagedElement());
        registerCallback(creationTool, () -> {
            CreationToolsUtil.addEdgeCreationTool(classifierCollector, creationTool);
        });
        getViewBuilder().addDefaultReconnectionTools(usageDescription);
    }

    private void createAssociationDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> sourceAndTargetDescriptionsSupplier = () -> collectNodesWithDomain(diagramDescription, pack.getClassifier());

        EClass association = pack.getAssociation();
        EdgeDescription padAssociation = getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(association, getQueryBuilder().queryAllReachableExactType(association),
                sourceAndTargetDescriptionsSupplier, sourceAndTargetDescriptionsSupplier);
        padAssociation.getStyle().setLineStyle(LineStyle.SOLID);
        padAssociation.getStyle().setTargetArrowStyle(ArrowStyle.NONE);
        padAssociation.getStyle().setSourceArrowStyle(ArrowStyle.NONE);

        registerCallback(padAssociation, () -> {
            CreationToolsUtil.addEdgeCreationTool(sourceAndTargetDescriptionsSupplier, getViewBuilder().createDefaultDomainBasedEdgeTool(padAssociation, pack.getPackage_PackagedElement()));
            CreationToolsUtil.addEdgeCreationTool(sourceAndTargetDescriptionsSupplier,
                    createSpecializedAssociationDomainBasedEdgeTool("New Composite Association", ClassDiagramServices.CREATION_COMPOSITE_ASSOCIATION));
            CreationToolsUtil.addEdgeCreationTool(sourceAndTargetDescriptionsSupplier,
                    createSpecializedAssociationDomainBasedEdgeTool("New Shared Association", ClassDiagramServices.CREATION_SHARED_ASSOCIATION));
        });

        padAssociation.setBeginLabelExpression(getQueryBuilder().createDomainBaseEdgeSourceLabelExpression());
        padAssociation.getPalette().setBeginLabelEditTool(getViewBuilder().createDirectEditTool(CallQuery.queryServiceOnSelf(ClassDiagramServices.GET_ASSOCIATION_TARGET)));

        padAssociation.setEndLabelExpression(getQueryBuilder().createDomainBaseEdgeTargetLabelExpression());
        padAssociation.getPalette().setEndLabelEditTool(getViewBuilder().createDirectEditTool(CallQuery.queryServiceOnSelf(ClassDiagramServices.GET_ASSOCIATION_SOURCE)));

        // Can be improve once https://github.com/PapyrusSirius/papyrus-web/issues/208 is closed
        new AssociationEdgeCustomStyleBuilder(padAssociation).addCustomArowStyles();

        diagramDescription.getEdgeDescriptions().add(padAssociation);

        getViewBuilder().addDefaultReconnectionTools(padAssociation);
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

        Supplier<List<NodeDescription>> sourceProvider = () -> collectNodesWithDomain(diagramDescription, pack.getPackage());
        Supplier<List<NodeDescription>> targetProvider = () -> collectNodesWithDomain(diagramDescription, pack.getPackageableElement());

        EdgeDescription containmentLinkEdge = getViewBuilder().createFeatureEdgeDescription(//
                PACKAGE_CONTAINMENT_LINK_EDGE_ID, //
                getQueryBuilder().emptyString(), //
                CallQuery.queryAttributeOnSelf(pack.getPackage_PackagedElement()), //
                sourceProvider, //
                targetProvider);

        containmentLinkEdge.setPreconditionExpression(new CallQuery(GRAPHICAL_EDGE_SOURCE).callService(Services.IS_NOT_VISUAL_DESCENDANT, GRAPHICAL_EDGE_TARGET, CACHE)); // $NON-NLS-1$

        containmentLinkEdge.getStyle().setSourceArrowStyle(ArrowStyle.CROSSED_CIRCLE);

        diagramDescription.getEdgeDescriptions().add(containmentLinkEdge);

        // Create containment Link tool

        EdgeTool tool = DiagramFactory.eINSTANCE.createEdgeTool();
        tool.setName(NEW_CONTAINMENT_LINK_TOOL_LABEL); //

        String toolQuery = new CallQuery(SEMANTIC_EDGE_TARGET).callService(Services.MOVE_IN, SEMANTIC_EDGE_SOURCE, getQueryBuilder().aqlString(pack.getPackage_PackagedElement().getName())); // $NON-NLS-1$

        ChangeContext changeContext = getViewBuilder().createChangeContextOperation(toolQuery);
        tool.getBody().add(changeContext);
        registerCallback(containmentLinkEdge, () -> {
            CreationToolsUtil.addEdgeCreationTool(sourceProvider, tool);
        });
    }

    private void createClassifierContainmentLink(DiagramDescription diagramDescription) {

        Supplier<List<NodeDescription>> sourceProvider = () -> collectNodesWithDomain(diagramDescription, pack.getClass_());
        Supplier<List<NodeDescription>> targetProvider = () -> collectNodesWithDomain(diagramDescription, pack.getClassifier());

        EdgeDescription containmentLinkEdge = getViewBuilder().createFeatureEdgeDescription(//
                CLASSIFIER_CONTAINMENT_LINK_EDGE_ID, //
                getQueryBuilder().emptyString(), //
                CallQuery.queryAttributeOnSelf(pack.getClass_NestedClassifier()), //
                sourceProvider, //
                targetProvider);

        containmentLinkEdge.setPreconditionExpression(new CallQuery(GRAPHICAL_EDGE_SOURCE).callService(Services.IS_NOT_VISUAL_DESCENDANT, GRAPHICAL_EDGE_TARGET, CACHE)); // $NON-NLS-1$

        containmentLinkEdge.getStyle().setSourceArrowStyle(ArrowStyle.CROSSED_CIRCLE);

        diagramDescription.getEdgeDescriptions().add(containmentLinkEdge);

        // Create containment Link tool
        EdgeTool tool = DiagramFactory.eINSTANCE.createEdgeTool();
        tool.setName(NEW_CONTAINMENT_LINK_TOOL_LABEL); //

        String toolQuery = new CallQuery(SEMANTIC_EDGE_TARGET).callService(Services.MOVE_IN, SEMANTIC_EDGE_SOURCE, getQueryBuilder().aqlString(pack.getClass_NestedClassifier().getName())); // $NON-NLS-1$

        ChangeContext changeContext = getViewBuilder().createChangeContextOperation(toolQuery);
        tool.getBody().add(changeContext);
        registerCallback(containmentLinkEdge, () -> {
            CreationToolsUtil.addEdgeCreationTool(sourceProvider, tool);
        });
    }

    private void createGeneralizationDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> sourceAndTargetDescriptionsSupplier = () -> collectNodesWithDomain(diagramDescription, pack.getClassifier());

        EClass generalization = pack.getGeneralization();
        EdgeDescription padGeneralization = getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(generalization, getQueryBuilder().queryAllReachableExactType(generalization),
                sourceAndTargetDescriptionsSupplier, sourceAndTargetDescriptionsSupplier);
        padGeneralization.getStyle().setLineStyle(LineStyle.SOLID);
        padGeneralization.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);
        registerCallback(padGeneralization, () -> {
            CreationToolsUtil.addEdgeCreationTool(sourceAndTargetDescriptionsSupplier, getViewBuilder().createDefaultDomainBasedEdgeTool(padGeneralization, pack.getClassifier_Generalization()));
        });

        diagramDescription.getEdgeDescriptions().add(padGeneralization);

        getViewBuilder().addDefaultReconnectionTools(padGeneralization);
    }

    private void createInterfaceRealizationDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> sourceDescriptionsSupplier = () -> collectNodesWithDomain(diagramDescription, pack.getBehavioredClassifier());
        Supplier<List<NodeDescription>> targetDescriptionsSupplier = () -> collectNodesWithDomain(diagramDescription, pack.getInterface());

        EdgeDescription padInterfaceRealization = getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(pack.getInterfaceRealization(),
                getQueryBuilder().queryAllReachableExactType(pack.getInterfaceRealization()), sourceDescriptionsSupplier, targetDescriptionsSupplier);
        padInterfaceRealization.getStyle().setLineStyle(LineStyle.DASH);
        padInterfaceRealization.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);
        registerCallback(padInterfaceRealization, () -> {
            CreationToolsUtil.addEdgeCreationTool(sourceDescriptionsSupplier,
                    getViewBuilder().createDefaultDomainBasedEdgeTool(padInterfaceRealization, pack.getBehavioredClassifier_InterfaceRealization()));
        });
        diagramDescription.getEdgeDescriptions().add(padInterfaceRealization);

        getViewBuilder().addDefaultReconnectionTools(padInterfaceRealization);
    }

    private void createEnumerationDescription(DiagramDescription diagramDescription) {
        EClass enumerationEClass = pack.getEnumeration();
        NodeDescription enumerationLiterals = newNodeBuilder(enumerationEClass, getViewBuilder().createRectangularNodeStyle(true, false))//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription())//
                .semanticCandidateExpression(getQueryBuilder().queryAllReachable(enumerationEClass))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(getViewBuilder().createDirectEditTool())//
                .deleteTool(getViewBuilder().createNodeDeleteTool(enumerationEClass.getName())) //
                .build();
        NodeTool creationTool = getViewBuilder().createCreationTool(pack.getPackage_PackagedElement(), enumerationEClass);
        Supplier<List<NodeDescription>> packageOwners = () -> collectNodesWithDomain(diagramDescription, pack.getPackage());
        Supplier<List<NodeDescription>> interfaceOwners = () -> collectNodesWithDomain(diagramDescription, pack.getInterface());
        Supplier<List<NodeDescription>> compartmentOwners = () -> collectNodesWithDomain(diagramDescription, true, false, pack.getClass_()) //
                .stream() //
                .filter(desc -> desc.getName().endsWith(NESTED_CLASSIFIERS_COMPARTMENT_SUFFIX)) //
                .toList();
        registerCallback(enumerationLiterals, () -> {
            CreationToolsUtil.addNodeCreationTool(packageOwners, creationTool);
            CreationToolsUtil.addNodeCreationTool(compartmentOwners, creationTool);
            CreationToolsUtil.addNodeCreationTool(interfaceOwners, creationTool);
            diagramDescription.getPalette().getNodeTools().add(creationTool);
        });

        diagramDescription.getNodeDescriptions().add(enumerationLiterals);

        // Create Enumeration Literals Compartment
        newListCompartmentBuilder().withChildrenType(pack.getEnumerationLiteral())//
                .withCompartmentNameSuffix(LITERAL_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryAttributeOnSelf(pack.getEnumeration_OwnedLiteral()))//
                .addCreationTools(pack.getEnumeration_OwnedLiteral(), pack.getEnumerationLiteral())//
                .buildIn(enumerationLiterals);
    }

    private void createInterfaceDescription(DiagramDescription diagramDescription) {

        EClass interfaceEClass = pack.getInterface();
        NodeDescription interfaceDescription = newNodeBuilder(interfaceEClass, getViewBuilder().createRectangularNodeStyle(true, false))//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription())//
                .semanticCandidateExpression(getQueryBuilder().queryAllReachable(interfaceEClass))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(getViewBuilder().createDirectEditTool())//
                .deleteTool(getViewBuilder().createNodeDeleteTool(interfaceEClass.getName())) //
                .build();
        NodeTool creationTool = getViewBuilder().createCreationTool(pack.getPackage_PackagedElement(), interfaceEClass);
        Supplier<List<NodeDescription>> packageOwners = () -> collectNodesWithDomain(diagramDescription, pack.getPackage());
        Supplier<List<NodeDescription>> interfaceOwners = () -> collectNodesWithDomain(diagramDescription, pack.getInterface());
        Supplier<List<NodeDescription>> compartmentOwners = () -> collectNodesWithDomain(diagramDescription, true, false, pack.getClass_()) //
                .stream() //
                .filter(desc -> desc.getName().endsWith(NESTED_CLASSIFIERS_COMPARTMENT_SUFFIX)) //
                .toList();
        registerCallback(interfaceDescription, () -> {
            CreationToolsUtil.addNodeCreationTool(packageOwners, creationTool);
            CreationToolsUtil.addNodeCreationTool(compartmentOwners, creationTool);
            CreationToolsUtil.addNodeCreationTool(interfaceOwners, creationTool);
            diagramDescription.getPalette().getNodeTools().add(creationTool);
        });

        diagramDescription.getNodeDescriptions().add(interfaceDescription);

        // Create Attributes Compartment
        newListCompartmentBuilder()//
                .withChildrenType(pack.getProperty())//
                .withCompartmentNameSuffix(ATTRIBUTES_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryOperationOnSelf(pack.getClassifier__GetAllAttributes()))//
                .addCreationTools(pack.getInterface_OwnedAttribute(), pack.getProperty())//
                .buildIn(interfaceDescription);

        // Create Operation Compartment
        newListCompartmentBuilder()//
                .withChildrenType(pack.getOperation())//
                .withCompartmentNameSuffix(OPERATIONS_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryOperationOnSelf(pack.getClassifier__GetAllOperations()))//
                .addCreationTools(pack.getInterface_OwnedOperation(), pack.getOperation())//
                .buildIn(interfaceDescription);

        // Create Nested Classifier Compartment
        newListCompartmentBuilder()//
                .withChildrenType(pack.getClassifier())//
                .withCompartmentNameSuffix(NESTED_CLASSIFIERS_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryAttributeOnSelf(pack.getInterface_NestedClassifier()))//
                .addCreationTools(pack.getInterface_NestedClassifier(), pack.getClass_())//
                .addCreationTools(pack.getInterface_NestedClassifier(), pack.getDataType())//
                .addCreationTools(pack.getInterface_NestedClassifier(), pack.getEnumeration())//
                .addCreationTools(pack.getInterface_NestedClassifier(), pack.getPrimitiveType())//
                .addCreationTools(pack.getInterface_NestedClassifier(), pack.getInterface())//
                .buildIn(interfaceDescription);

    }

    private void createPrimitiveTypeDescription(DiagramDescription diagramDescription) {

        EClass primitiveTypeEClass = pack.getPrimitiveType();
        NodeDescription primitiveTypeDescription = newNodeBuilder(primitiveTypeEClass, getViewBuilder().createRectangularNodeStyle(true, false))//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription())//
                .semanticCandidateExpression(getQueryBuilder().queryAllReachable(primitiveTypeEClass))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(getViewBuilder().createDirectEditTool())//
                .deleteTool(getViewBuilder().createNodeDeleteTool(primitiveTypeEClass.getName())) //
                .build();
        NodeTool creationTool = getViewBuilder().createCreationTool(pack.getPackage_PackagedElement(), primitiveTypeEClass);
        Supplier<List<NodeDescription>> packageOwners = () -> collectNodesWithDomain(diagramDescription, pack.getPackage());
        Supplier<List<NodeDescription>> interfaceOwners = () -> collectNodesWithDomain(diagramDescription, pack.getInterface());
        Supplier<List<NodeDescription>> compartmentOwners = () -> collectNodesWithDomain(diagramDescription, true, false, pack.getClass_()) //
                .stream() //
                .filter(desc -> desc.getName().endsWith(NESTED_CLASSIFIERS_COMPARTMENT_SUFFIX)) //
                .toList();
        registerCallback(primitiveTypeDescription, () -> {
            CreationToolsUtil.addNodeCreationTool(packageOwners, creationTool);
            CreationToolsUtil.addNodeCreationTool(compartmentOwners, creationTool);
            CreationToolsUtil.addNodeCreationTool(interfaceOwners, creationTool);
            diagramDescription.getPalette().getNodeTools().add(creationTool);
        });
        diagramDescription.getNodeDescriptions().add(primitiveTypeDescription);

        // Create Attributes Compartment
        newListCompartmentBuilder().withChildrenType(pack.getProperty())//
                .withCompartmentNameSuffix(ATTRIBUTES_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryOperationOnSelf(pack.getClassifier__GetAllAttributes()))//
                .addCreationTools(pack.getDataType_OwnedAttribute(), pack.getProperty())//
                .buildIn(primitiveTypeDescription);

        // Create Operation Compartment
        newListCompartmentBuilder().withChildrenType(pack.getOperation())//
                .withCompartmentNameSuffix(OPERATIONS_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryOperationOnSelf(pack.getClassifier__GetAllOperations()))//
                .addCreationTools(pack.getDataType_OwnedOperation(), pack.getOperation())//
                .buildIn(primitiveTypeDescription);
    }

    private void createDataTypeDescription(DiagramDescription diagramDescription) {

        EClass dataTypeEClass = pack.getDataType();
        NodeDescription classDescription = newNodeBuilder(dataTypeEClass, getViewBuilder().createRectangularNodeStyle(true, false))//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription())//
                .semanticCandidateExpression(getQueryBuilder().queryAllReachable(dataTypeEClass))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(getViewBuilder().createDirectEditTool())//
                .deleteTool(getViewBuilder().createNodeDeleteTool(dataTypeEClass.getName())) //
                .build();

        NodeTool creationTool = getViewBuilder().createCreationTool(pack.getPackage_PackagedElement(), dataTypeEClass);
        Supplier<List<NodeDescription>> packageOwners = () -> collectNodesWithDomain(diagramDescription, pack.getPackage());
        Supplier<List<NodeDescription>> interfaceOwners = () -> collectNodesWithDomain(diagramDescription, pack.getInterface());
        Supplier<List<NodeDescription>> compartmentOwners = () -> collectNodesWithDomain(diagramDescription, true, false, pack.getClass_()) //
                .stream() //
                .filter(desc -> desc.getName().endsWith(NESTED_CLASSIFIERS_COMPARTMENT_SUFFIX)) //
                .toList();
        registerCallback(classDescription, () -> {
            CreationToolsUtil.addNodeCreationTool(packageOwners, creationTool);
            CreationToolsUtil.addNodeCreationTool(compartmentOwners, creationTool);
            CreationToolsUtil.addNodeCreationTool(interfaceOwners, creationTool);
            diagramDescription.getPalette().getNodeTools().add(creationTool);
        });

        diagramDescription.getNodeDescriptions().add(classDescription);

        // Create Attributes Compartment
        newListCompartmentBuilder().withChildrenType(pack.getProperty())//
                .withCompartmentNameSuffix(ATTRIBUTES_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryOperationOnSelf(pack.getClassifier__GetAllAttributes()))//
                .addCreationTools(pack.getDataType_OwnedAttribute(), pack.getProperty())//
                .buildIn(classDescription);

        // Create Operation Compartment
        newListCompartmentBuilder().withChildrenType(pack.getOperation())//
                .withCompartmentNameSuffix(OPERATIONS_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryOperationOnSelf(pack.getClassifier__GetAllOperations()))//
                .addCreationTools(pack.getDataType_OwnedOperation(), pack.getOperation())//
                .buildIn(classDescription);

    }

    private void createClassDescription(DiagramDescription diagramDescription) {
        NodeDescription classDescription = newNodeBuilder(pack.getClass_(), getViewBuilder().createRectangularNodeStyle(true, false))//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription())//
                .semanticCandidateExpression(getQueryBuilder().queryAllReachable(pack.getClass_()))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(getViewBuilder().createDirectEditTool())//
                .deleteTool(getViewBuilder().createNodeDeleteTool(pack.getClass_().getName())) //
                .build();
        NodeTool creationTool = getViewBuilder().createCreationTool(pack.getPackage_PackagedElement(), pack.getClass_());
        Supplier<List<NodeDescription>> packageOwners = () -> collectNodesWithDomain(diagramDescription, pack.getPackage());
        Supplier<List<NodeDescription>> interfaceOwners = () -> collectNodesWithDomain(diagramDescription, pack.getInterface());
        Supplier<List<NodeDescription>> compartmentOwners = () -> collectNodesWithDomain(diagramDescription, true, false, pack.getClass_()) //
                .stream() //
                .filter(desc -> desc.getName().endsWith(NESTED_CLASSIFIERS_COMPARTMENT_SUFFIX)) //
                .toList();
        registerCallback(classDescription, () -> {
            CreationToolsUtil.addNodeCreationTool(packageOwners, creationTool);
            CreationToolsUtil.addNodeCreationTool(compartmentOwners, creationTool);
            CreationToolsUtil.addNodeCreationTool(interfaceOwners, creationTool);
        });
        diagramDescription.getPalette().getNodeTools().add(creationTool);
        diagramDescription.getNodeDescriptions().add(classDescription);

        // Create Attributes Compartment
        newListCompartmentBuilder().withChildrenType(pack.getProperty())//
                .withCompartmentNameSuffix(ATTRIBUTES_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryOperationOnSelf(pack.getClassifier__GetAllAttributes()))//
                .addCreationTools(pack.getStructuredClassifier_OwnedAttribute(), pack.getProperty())//
                .buildIn(classDescription);

        // Create Operation Compartment
        newListCompartmentBuilder().withChildrenType(pack.getOperation())//
                .withCompartmentNameSuffix(OPERATIONS_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryOperationOnSelf(pack.getClassifier__GetAllOperations()))//
                .addCreationTools(pack.getClass_OwnedOperation(), pack.getOperation())//
                .buildIn(classDescription);

        // Create Nested Classifier Compartment
        newListCompartmentBuilder().withChildrenType(pack.getClassifier())//
                .withCompartmentNameSuffix(NESTED_CLASSIFIERS_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryAttributeOnSelf(pack.getClass_NestedClassifier()))//
                .addCreationTools(pack.getClass_NestedClassifier(), pack.getClass_())//
                .addCreationTools(pack.getClass_NestedClassifier(), pack.getDataType())//
                .addCreationTools(pack.getClass_NestedClassifier(), pack.getEnumeration())//
                .addCreationTools(pack.getClass_NestedClassifier(), pack.getPrimitiveType())//
                .addCreationTools(pack.getClass_NestedClassifier(), pack.getInterface())//
                .buildIn(classDescription);

    }

    private void createDependencyDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> namedElementDescriptions = () -> collectNodesWithDomain(diagramDescription, pack.getNamedElement());
        EdgeDescription padDependency = getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(pack.getDependency(), getQueryBuilder().queryAllReachableExactType(pack.getDependency()),
                namedElementDescriptions, namedElementDescriptions);
        padDependency.getStyle().setLineStyle(LineStyle.DASH);
        padDependency.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        EdgeTool creationTool = getViewBuilder().createDefaultDomainBasedEdgeTool(padDependency, pack.getPackage_PackagedElement());
        registerCallback(padDependency, () -> {
            CreationToolsUtil.addEdgeCreationTool(namedElementDescriptions, creationTool);
        });
        diagramDescription.getEdgeDescriptions().add(padDependency);

        getViewBuilder().addDefaultReconnectionTools(padDependency);
    }

    private void createAbstractionDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> namedElementDescriptions = () -> collectNodesWithDomain(diagramDescription, pack.getNamedElement());
        EdgeDescription padAbstraction = getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(pack.getAbstraction(), getQueryBuilder().queryAllReachableExactType(pack.getAbstraction()),
                namedElementDescriptions, namedElementDescriptions);
        padAbstraction.getStyle().setLineStyle(LineStyle.DASH);
        padAbstraction.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        EdgeTool creationTool = getViewBuilder().createDefaultDomainBasedEdgeTool(padAbstraction, pack.getPackage_PackagedElement());
        registerCallback(padAbstraction, () -> {
            CreationToolsUtil.addEdgeCreationTool(namedElementDescriptions, creationTool);
        });
        diagramDescription.getEdgeDescriptions().add(padAbstraction);

        getViewBuilder().addDefaultReconnectionTools(padAbstraction);
    }

    private void createPackageMergeDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> packageDescriptions = () -> collectNodesWithDomain(diagramDescription, pack.getPackage());
        EdgeDescription padPackageMerge = getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(pack.getPackageMerge(), getQueryBuilder().queryAllReachable(pack.getPackageMerge()),
                packageDescriptions, packageDescriptions);
        padPackageMerge.getStyle().setLineStyle(LineStyle.DASH);
        padPackageMerge.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        EdgeTool creationTool = getViewBuilder().createDefaultDomainBasedEdgeTool(padPackageMerge, pack.getPackage_PackageMerge());
        registerCallback(padPackageMerge, () -> {
            CreationToolsUtil.addEdgeCreationTool(packageDescriptions, creationTool);
        });
        diagramDescription.getEdgeDescriptions().add(padPackageMerge);
        getViewBuilder().addDefaultReconnectionTools(padPackageMerge);
    }

    private void createPackageImportDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> packageDescriptions = () -> collectNodesWithDomain(diagramDescription, pack.getPackage());
        EdgeDescription padPackageImport = getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(pack.getPackageImport(), getQueryBuilder().queryAllReachable(pack.getPackageImport()),
                packageDescriptions, packageDescriptions);
        padPackageImport.getStyle().setLineStyle(LineStyle.DASH);
        padPackageImport.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);

        EdgeTool creationTool = getViewBuilder().createDefaultDomainBasedEdgeTool(padPackageImport, pack.getNamespace_PackageImport());
        registerCallback(padPackageImport, () -> {
            CreationToolsUtil.addEdgeCreationTool(packageDescriptions, creationTool);
        });
        diagramDescription.getEdgeDescriptions().add(padPackageImport);
        getViewBuilder().addDefaultReconnectionTools(padPackageImport);

    }

}
