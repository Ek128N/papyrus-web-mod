/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
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
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.uml.domain.services.UMLHelper;
import org.eclipse.papyrus.web.application.representations.view.CreationToolsUtil;
import org.eclipse.papyrus.web.application.representations.view.aql.CallQuery;
import org.eclipse.papyrus.web.application.representations.view.builders.NodeSemanticCandidateExpressionTransformer;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Builder of the "Profile Diagram" diagram representation.
 * 
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class PRDDiagramDescriptionBuilder extends AbstractRepresentationDescriptionBuilder {

    /**
     * The suffix used to identify <i>operations</i> compartments.
     */
    public static final String OPERATIONS_COMPARTMENT_SUFFIX = "Operations";

    /**
     * The suffix used to identify <i>attributes</i> compartments.
     */
    public static final String ATTRIBUTES_COMPARTMENT_SUFFIX = "Attributes";

    /**
     * The suffix used to identify <i>literals</i> compartments.
     */
    public static final String LITERALS_COMPARTMENT_SUFFIX = "Literals";

    /**
     * The name of the representation handled by this builder.
     */
    public static final String PRD_REP_NAME = "Profile Diagram";

    /**
     * The prefix of the representation handled by this builder.
     */
    public static final String PRD_PREFIX = "PRD_";

    private static final Predicate<NodeDescription> PACKAGE_CHILDREN_FILTER = n -> n.getName().endsWith(PACKAGE_CHILD);

    private UMLPackage umlPackage = UMLPackage.eINSTANCE;

    /**
     * Initializes the builder.
     */
    public PRDDiagramDescriptionBuilder() {
        super(PRD_PREFIX, PRD_REP_NAME, UMLPackage.eINSTANCE.getPackage());
    }

    @Override
    protected void fillDescription(DiagramDescription diagramDescription) {

        NodeDescription prdSharedDescription = this.createSharedDescription(diagramDescription);
        this.createCommentDescriptionInNodeDescription(diagramDescription, prdSharedDescription, List.of(this.umlPackage.getPackage()));
        this.createConstraintDescriptionInNodeDescription(diagramDescription, prdSharedDescription, List.of(this.umlPackage.getPackage()));

        this.createPackageDescription(diagramDescription);

        this.createProfileDescription(diagramDescription);

        this.createClassDescription(diagramDescription);
        this.createDataTypeDescription(diagramDescription);
        this.createStereotypeDescription(diagramDescription);

        this.createPrimitiveTypeDescription(diagramDescription);
        this.createEnumerationDescription(diagramDescription);

        this.createAssociationDescription(diagramDescription);
        this.createExtensionDescription(diagramDescription);
        this.createGeneralizationDescription(diagramDescription);

        this.createCommentDescriptionInDiagramDescription(diagramDescription);
        this.createConstraintDescriptionInDiagramDescription(diagramDescription);

        diagramDescription.getPalette().setDropTool(this.getViewBuilder().createGenericDropTool(this.getIdBuilder().getDropToolId()));

    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Package}.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    @Override
    protected void createPackageDescription(DiagramDescription diagramDescription) {
        EClass packageEClass = this.umlPackage.getPackage();
        NodeDescription prdPackageDescription = this.getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(packageEClass, this.getQueryBuilder().queryAllReachable(packageEClass));
        diagramDescription.getNodeDescriptions().add(prdPackageDescription);

        diagramDescription.getPalette().getNodeTools().add(this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), packageEClass));

        this.registerCallback(prdPackageDescription, () -> {
            List<NodeDescription> packages = this.collectNodesWithDomain(diagramDescription, packageEClass);
            packages.forEach(p -> {
                p.getPalette().getNodeTools().add(this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), packageEClass));
                p.getPalette().getNodeTools().add(this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), this.umlPackage.getProfile()));
            });
            String childrenCandidateExpression = CallQuery.queryAttributeOnSelf(UMLPackage.eINSTANCE.getPackage_PackagedElement());
            List<NodeDescription> copiedClassifier = diagramDescription.getNodeDescriptions().stream().filter(
                    n -> this.isValidNodeDescription(n, false, false, this.umlPackage.getPackageableElement()) && !this.isValidNodeDescription(n, false, false, this.umlPackage.getConstraint()))
                    .map(n -> this.transformIntoPackageChildNode(n, childrenCandidateExpression, diagramDescription)).toList();
            prdPackageDescription.getChildrenDescriptions().addAll(copiedClassifier);
        });
    }

    /**
     * Creates the {@link NodeDescription} representing an UML input at {@link Package} level.
     * 
     * @param input
     *            UML element to create,
     * @param semanticCandidateExpression
     *            aql expression to retrieve list of input to represent
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     * @return the {@link NodeDescription} representing an UML input at {@link Package} level.
     */
    private NodeDescription transformIntoPackageChildNode(NodeDescription input, String semanticCandidateExpression, DiagramDescription diagramDescription) {
        EClass eClass = UMLHelper.toEClass(input.getDomainType());
        String id = this.getIdBuilder().getSpecializedDomainNodeName(eClass, PACKAGE_CHILD);
        NodeDescription n = new NodeSemanticCandidateExpressionTransformer().intoNewCanidateExpression(id, input, semanticCandidateExpression);

        if (UMLPackage.eINSTANCE.getPackage().isSuperTypeOf(eClass)) {
            this.collectAndReusedChildNodes(n, this.umlPackage.getPackageableElement(), diagramDescription, PACKAGE_CHILDREN_FILTER);
        }
        return n;
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Profile}.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createProfileDescription(DiagramDescription diagramDescription) {
        EClass profileEClass = this.umlPackage.getProfile();
        NodeDescription prdProfileDescription = this.getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(profileEClass,
                this.getQueryBuilder().queryAllReachable(this.umlPackage.getProfile()));
        diagramDescription.getNodeDescriptions().add(prdProfileDescription);
        diagramDescription.getPalette().getNodeTools().add(this.getViewBuilder().createCreationTool(UMLPackage.eINSTANCE.getPackage_PackagedElement(), profileEClass));
        // TODO uncomment next line when setColor is restored possibly in 2023.10.0
        // prdProfileDescription.getStyle().setColor(this.styleProvider.getModelColor());
        this.collectAndReusedChildNodes(prdProfileDescription, this.umlPackage.getPackageableElement(), diagramDescription, PACKAGE_CHILDREN_FILTER);
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Class}.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     * 
     * @see #createNodeDescriptionForClassifier(DiagramDescription, EClass)
     * @see #createAttributesCompartment(NodeDescription)
     * @see #createOperationsCompartment(NodeDescription)
     */
    private void createClassDescription(DiagramDescription diagramDescription) {
        NodeDescription prdClassDescription = this.createNodeDescriptionForClassifier(diagramDescription, this.umlPackage.getClass_());
        this.createAttributesCompartment(prdClassDescription);
        this.createOperationsCompartment(prdClassDescription);
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link DataType}.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     * 
     * @see #createNodeDescriptionForClassifier(DiagramDescription, EClass)
     * @see #createAttributesCompartment(NodeDescription)
     * @see #createOperationsCompartment(NodeDescription)
     */
    private void createDataTypeDescription(DiagramDescription diagramDescription) {
        NodeDescription prdDataTypeDescription = this.createNodeDescriptionForClassifier(diagramDescription, this.umlPackage.getDataType());
        this.createAttributesCompartment(prdDataTypeDescription);
        this.createOperationsCompartment(prdDataTypeDescription);
    }

    /**
     * Create the {@link NodeDescription} representing an UML {@link Stereotype}.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     * 
     * @see #createNodeDescriptionForClassifier(DiagramDescription, EClass)
     * @see #createAttributesCompartment(NodeDescription)
     * @see #createOperationsCompartment(NodeDescription)
     */
    private void createStereotypeDescription(DiagramDescription diagramDescription) {
        NodeDescription prdStereotypeDescription = this.createNodeDescriptionForClassifier(diagramDescription, this.umlPackage.getStereotype());
        this.createAttributesCompartment(prdStereotypeDescription);
        this.createOperationsCompartment(prdStereotypeDescription);
    }

    /**
     * Creates a list {@link NodeDescription} representing {@link Classifier} sub-classes.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     * @param classifierEClass
     *            the classifier sub-type to represent
     * @return the created {@link NodeDescription}
     */
    private NodeDescription createNodeDescriptionForClassifier(DiagramDescription diagramDescription, EClass classifierEClass) {
        NodeDescription prdClassifierNodeDescription = this.newNodeBuilder(classifierEClass, this.getViewBuilder().createRectangularNodeStyle(true, false))//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription())//
                .semanticCandidateExpression(this.getQueryBuilder().queryAllReachable(classifierEClass))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool())//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(classifierEClass.getName())) //
                .build();
        NodeTool creationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), classifierEClass);
        Supplier<List<NodeDescription>> packageOwners = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getPackage());
        this.registerCallback(prdClassifierNodeDescription, () -> {
            CreationToolsUtil.addNodeCreationTool(packageOwners, creationTool);
        });
        diagramDescription.getPalette().getNodeTools().add(creationTool);
        diagramDescription.getNodeDescriptions().add(prdClassifierNodeDescription);
        return prdClassifierNodeDescription;
    }

    /**
     * Creates an <i>attributes</i> compartment in the provided {@code nodeDescription}.
     * 
     * @param nodeDescription
     *            the {@link NodeDescription} containing the compartment
     */
    private void createAttributesCompartment(NodeDescription nodeDescription) {
        this.newListCompartmentBuilder().withChildrenType(this.umlPackage.getProperty())//
                .withCompartmentNameSuffix(ATTRIBUTES_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryOperationOnSelf(this.umlPackage.getClassifier__GetAllAttributes()))//
                .addCreationTools(this.umlPackage.getStructuredClassifier_OwnedAttribute(), this.umlPackage.getProperty())//
                .buildIn(nodeDescription);
    }

    /**
     * Creates an <i>operations</i> compartment in the provided {@code nodeDescription}.
     * 
     * @param nodeDescription
     *            the {@link NodeDescription} containing the compartment
     */
    private void createOperationsCompartment(NodeDescription nodeDescription) {
        this.newListCompartmentBuilder().withChildrenType(this.umlPackage.getOperation())//
                .withCompartmentNameSuffix(OPERATIONS_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryOperationOnSelf(this.umlPackage.getClassifier__GetAllOperations()))//
                .addCreationTools(this.umlPackage.getClass_OwnedOperation(), this.umlPackage.getOperation())//
                .buildIn(nodeDescription);
    }

    /**
     * Create the {@link NodeDescription} representing an UML {@link PrimitiveType}.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createPrimitiveTypeDescription(DiagramDescription diagramDescription) {
        EClass primitiveTypeEClass = this.umlPackage.getPrimitiveType();
        NodeDescription prdPrimitiveTypeDescription = this.newNodeBuilder(primitiveTypeEClass, this.getViewBuilder().createRectangularNodeStyle(true, false)) //
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())
                .semanticCandidateExpression(this.getQueryBuilder().queryAllReachable(primitiveTypeEClass)).synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED) //
                .labelEditTool(this.getViewBuilder().createDirectEditTool()) //
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(primitiveTypeEClass.getName())) //
                .build();
        NodeTool creationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), primitiveTypeEClass);
        Supplier<List<NodeDescription>> packageOwners = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getPackage());
        this.registerCallback(prdPrimitiveTypeDescription, () -> {
            CreationToolsUtil.addNodeCreationTool(packageOwners, creationTool);
        });
        diagramDescription.getPalette().getNodeTools().add(creationTool);
        diagramDescription.getNodeDescriptions().add(prdPrimitiveTypeDescription);
    }

    /**
     * Create the {@link NodeDescription} representing an UML {@link Enumeration}.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createEnumerationDescription(DiagramDescription diagramDescription) {
        EClass enumerationEClass = this.umlPackage.getEnumeration();
        NodeDescription prdEnumerationDescription = this.newNodeBuilder(enumerationEClass, this.getViewBuilder().createRectangularNodeStyle(true, false))//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription())//
                .semanticCandidateExpression(this.getQueryBuilder().queryAllReachable(enumerationEClass))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool())//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(enumerationEClass.getName())) //
                .build();
        NodeTool creationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), enumerationEClass);
        Supplier<List<NodeDescription>> packageOwners = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getPackage());
        this.registerCallback(prdEnumerationDescription, () -> {
            CreationToolsUtil.addNodeCreationTool(packageOwners, creationTool);
        });
        diagramDescription.getPalette().getNodeTools().add(creationTool);
        diagramDescription.getNodeDescriptions().add(prdEnumerationDescription);

        // Create Literals Compartment
        this.newListCompartmentBuilder().withChildrenType(this.umlPackage.getEnumerationLiteral())//
                .withCompartmentNameSuffix(LITERALS_COMPARTMENT_SUFFIX)//
                .withSemanticCandidateExpression(CallQuery.queryAttributeOnSelf(this.umlPackage.getEnumeration_OwnedLiteral()))//
                .addCreationTools(this.umlPackage.getEnumeration_OwnedLiteral(), this.umlPackage.getEnumerationLiteral())//
                .buildIn(prdEnumerationDescription);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Association}.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createAssociationDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> sourceAndTargetDescriptionSupplier = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getClassifier());

        EClass association = this.umlPackage.getAssociation();
        EdgeDescription prdAssociationDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(association, this.getQueryBuilder().queryAllReachableExactType(association),
                sourceAndTargetDescriptionSupplier, sourceAndTargetDescriptionSupplier);
        prdAssociationDescription.getStyle().setLineStyle(LineStyle.SOLID);
        prdAssociationDescription.getStyle().setTargetArrowStyle(ArrowStyle.NONE);
        prdAssociationDescription.getStyle().setSourceArrowStyle(ArrowStyle.NONE);

        EdgeTool associationCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(prdAssociationDescription, this.umlPackage.getPackage_PackagedElement());
        this.registerCallback(prdAssociationDescription, () -> {
            CreationToolsUtil.addEdgeCreationTool(sourceAndTargetDescriptionSupplier, associationCreationTool);
        });

        prdAssociationDescription.setBeginLabelExpression(this.getQueryBuilder().createDomainBaseEdgeSourceLabelExpression());
        prdAssociationDescription.getPalette().setBeginLabelEditTool(this.getViewBuilder().createDirectEditTool(CallQuery.queryServiceOnSelf(ClassDiagramServices.GET_ASSOCIATION_TARGET)));

        prdAssociationDescription.setEndLabelExpression(this.getQueryBuilder().createDomainBaseEdgeTargetLabelExpression());
        prdAssociationDescription.getPalette().setEndLabelEditTool(this.getViewBuilder().createDirectEditTool(CallQuery.queryServiceOnSelf(ClassDiagramServices.GET_ASSOCIATION_SOURCE)));

        diagramDescription.getEdgeDescriptions().add(prdAssociationDescription);
        this.getViewBuilder().addDefaultReconnectionTools(prdAssociationDescription);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Extension}.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createExtensionDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> sourceDescriptionSupplier = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getStereotype());
        Supplier<List<NodeDescription>> targetDescriptionSupplier = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getClass_());

        EClass extension = this.umlPackage.getExtension();
        EdgeDescription prdExtensionDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(extension, this.getQueryBuilder().queryAllReachableExactType(extension),
                sourceDescriptionSupplier, targetDescriptionSupplier);
        prdExtensionDescription.getStyle().setLineStyle(LineStyle.SOLID);
        prdExtensionDescription.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_FILL_CLOSED_ARROW);
        prdExtensionDescription.getStyle().setSourceArrowStyle(ArrowStyle.NONE);
        EdgeTool creationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(prdExtensionDescription, this.umlPackage.getPackage_PackagedElement());
        this.registerCallback(prdExtensionDescription, () -> {
            CreationToolsUtil.addEdgeCreationTool(sourceDescriptionSupplier, creationTool);
        });

        diagramDescription.getEdgeDescriptions().add(prdExtensionDescription);
        this.getViewBuilder().addDefaultReconnectionTools(prdExtensionDescription);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Generalization}.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createGeneralizationDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> sourceAndTargetDescriptionSupplier = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getClassifier());

        EClass generalization = this.umlPackage.getGeneralization();
        EdgeDescription prdGeneralizationDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(generalization,
                this.getQueryBuilder().queryAllReachableExactType(generalization), sourceAndTargetDescriptionSupplier, sourceAndTargetDescriptionSupplier);
        prdGeneralizationDescription.getStyle().setLineStyle(LineStyle.SOLID);
        prdGeneralizationDescription.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);
        prdGeneralizationDescription.getStyle().setSourceArrowStyle(ArrowStyle.NONE);
        EdgeTool creationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(prdGeneralizationDescription, this.umlPackage.getClassifier_Generalization());
        this.registerCallback(prdGeneralizationDescription, () -> {
            CreationToolsUtil.addEdgeCreationTool(sourceAndTargetDescriptionSupplier, creationTool);
        });

        diagramDescription.getEdgeDescriptions().add(prdGeneralizationDescription);
        this.getViewBuilder().addDefaultReconnectionTools(prdGeneralizationDescription);

    }

}
