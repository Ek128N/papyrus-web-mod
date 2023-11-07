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
import org.eclipse.papyrus.web.application.representations.view.aql.CallQuery;
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
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
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

    /**
     * The name of the {@link NodeDescription} representing a metaclass on the diagram.
     */
    public static final String PRD_METACLASS = PRD_PREFIX + "Metaclass";

    /**
     * The name of the {@link NodeDescription} representing a metaclass inside another element.
     */
    public static final String PRD_SHARED_METACLASS = PRD_METACLASS + "_" + SHARED_SUFFIX;

    private UMLPackage umlPackage = UMLPackage.eINSTANCE;

    /**
     * The <i>shared</i> {@link NodeDescription} for the diagram.
     */
    private NodeDescription prdSharedDescription;

    /**
     * Predicate used to exclude Metaclass Node Description.
     */
    private Predicate<NodeDescription> excludeMetaclassNodeDescription = nodeDescription -> !nodeDescription.getName().equals(PRD_METACLASS) && !nodeDescription.getName().equals(PRD_SHARED_METACLASS);

    /**
     * Initializes the builder.
     */
    public PRDDiagramDescriptionBuilder() {
        super(PRD_PREFIX, PRD_REP_NAME, UMLPackage.eINSTANCE.getPackage());
    }

    @Override
    protected void fillDescription(DiagramDescription diagramDescription) {

        // create diagram tool sections
        this.createDefaultToolSectionInDiagramDescription(diagramDescription);
        diagramDescription.setPreconditionExpression(CallQuery.queryServiceOnSelf(ProfileDiagramServices.IS_PROFILE_MODEL));

        this.createDiagramPackageDescription(diagramDescription);
        this.createDiagramProfileDescription(diagramDescription);
        this.createDiagramClassDescription(diagramDescription);
        /*
         * This call needs to be below createDiagramClassDescription: PRD_Class and PRD_Metaclass are defined at the
         * same level, so the method that selects the best mapping candidate will return the first found. Ensuring
         * PRD_Class is found first makes view creation/DnD easier to define: we just have to handle the metaclass case,
         * which is way less common than the class case.
         */
        this.createDiagramMetaclassDescription(diagramDescription);
        this.createDiagramDataTypeDescription(diagramDescription);
        this.createDiagramStereotypeDescription(diagramDescription);
        this.createDiagramPrimitiveTypeDescription(diagramDescription);
        this.createDiagramEnumerationDescription(diagramDescription);
        this.createDiagramCommentDescription(diagramDescription, NODES);
        this.createDiagramConstraintDescription(diagramDescription, NODES);

        this.prdSharedDescription = this.createSharedDescription(diagramDescription);
        this.createCommentDescriptionInNodeDescription(diagramDescription, this.prdSharedDescription, NODES, List.of(this.umlPackage.getPackage()));
        this.createConstraintDescriptionInNodeDescription(diagramDescription, this.prdSharedDescription, NODES, List.of(this.umlPackage.getPackage()));
        this.createSharedPackageDescription(diagramDescription);
        this.createSharedProfileDescription(diagramDescription);
        this.createSharedClassDescription(diagramDescription);
        this.createSharedAttributeDescription(diagramDescription);
        this.createSharedOperationDescription(diagramDescription);
        /*
         * This call needs to be below createClassDescriptionInNodeDescription: shared PRD_Class and PRD_Metaclass are
         * defined at the same level, so the method that selects the best mapping candidate will return the first found.
         * Ensuring PRD_Class is found first makes view creation/DnD easier to define: we just have to handle the
         * metaclass case, which is way less common than the class case. Note that we have to define PRD_Metaclass as a
         * shared element even if it is only reused in PRD_Profile, otherwise it takes precedence over PRD_Class in
         * profile.
         */
        this.createSharedMetaclassDescription(diagramDescription);
        this.createSharedDataTypeDescription(diagramDescription);
        this.createSharedStereotypeDescription(diagramDescription);
        this.createSharedPrimitiveTypeDescription(diagramDescription);
        this.createSharedEnumerationDescription(diagramDescription);

        // create shared compartments
        this.createSharedLiteralsCompartmentForEnumerationDescription(diagramDescription);
        this.createSharedCompartmentForDataTypeDescription(diagramDescription, ATTRIBUTES_COMPARTMENT_SUFFIX);
        this.createSharedCompartmentDescriptionForClassDescription(diagramDescription, ATTRIBUTES_COMPARTMENT_SUFFIX);
        this.createSharedCompartmentForStereotypeDescription(diagramDescription, ATTRIBUTES_COMPARTMENT_SUFFIX);
        this.createSharedCompartmentForDataTypeDescription(diagramDescription, OPERATIONS_COMPARTMENT_SUFFIX);
        this.createSharedCompartmentDescriptionForClassDescription(diagramDescription, OPERATIONS_COMPARTMENT_SUFFIX);
        this.createSharedCompartmentForStereotypeDescription(diagramDescription, OPERATIONS_COMPARTMENT_SUFFIX);

        this.createAssociationDescription(diagramDescription);
        this.createExtensionDescription(diagramDescription);
        this.createGeneralizationDescription(diagramDescription);

        diagramDescription.getPalette().setDropTool(this.getViewBuilder().createGenericDropTool(this.getIdBuilder().getDropToolId()));

    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Package} on the Diagram.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramPackageDescription(DiagramDescription diagramDescription) {
        EClass packageEClass = this.umlPackage.getPackage();
        NodeDescription prdDiagramPackageDescription = this.getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(packageEClass,
                this.getQueryBuilder().queryAllReachableExactType(this.umlPackage.getPackage()));
        diagramDescription.getNodeDescriptions().add(prdDiagramPackageDescription);

        // create Package tool sections
        this.createDefaultToolSectionsInNodeDescription(prdDiagramPackageDescription);

        NodeTool prdDiagramPackageCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), packageEClass);
        this.addDiagramToolInToolSection(diagramDescription, prdDiagramPackageCreationTool, NODES);

        // No direct children for Package: the NodeDescriptions it can contain are all defined as shared descriptions.
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
        NodeDescription prdSharedPackageDescription = this.getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(packageEClass,
                CallQuery.queryAttributeOnSelf(this.umlPackage.getPackage_PackagedElement()));
        prdSharedPackageDescription.setName(this.getIdBuilder().getSpecializedDomainNodeName(packageEClass, SHARED_SUFFIX));
        this.prdSharedDescription.getChildrenDescriptions().add(prdSharedPackageDescription);

        this.createDefaultToolSectionsInNodeDescription(prdSharedPackageDescription);

        NodeTool prdSharedPackageCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), packageEClass);
        List<EClass> owners = List.of(this.umlPackage.getPackage());
        this.reuseNodeAndCreateTool(prdSharedPackageDescription, diagramDescription, prdSharedPackageCreationTool, NODES, owners.toArray(EClass[]::new));
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Profile} on the Diagram.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramProfileDescription(DiagramDescription diagramDescription) {
        EClass profileEClass = this.umlPackage.getProfile();
        NodeDescription prdDiagramProfileDescription = this.getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(profileEClass,
                this.getQueryBuilder().queryAllReachableExactType(profileEClass));
        diagramDescription.getNodeDescriptions().add(prdDiagramProfileDescription);

        this.createDefaultToolSectionsInNodeDescription(prdDiagramProfileDescription);

        NodeTool prdDiagramProfileCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), profileEClass);
        this.addDiagramToolInToolSection(diagramDescription, prdDiagramProfileCreationTool, NODES);

    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link Profile}.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedProfileDescription(DiagramDescription diagramDescription) {
        EClass profileEClass = this.umlPackage.getProfile();
        NodeDescription prdSharedProfileDescription = this.getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(profileEClass,
                CallQuery.queryAttributeOnSelf(this.umlPackage.getPackage_PackagedElement()));
        prdSharedProfileDescription.setName(this.getIdBuilder().getSpecializedDomainNodeName(profileEClass, SHARED_SUFFIX));
        this.prdSharedDescription.getChildrenDescriptions().add(prdSharedProfileDescription);

        this.createDefaultToolSectionsInNodeDescription(prdSharedProfileDescription);

        NodeTool prdSharedProfileCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), profileEClass);
        List<EClass> owners = List.of(this.umlPackage.getPackage());
        this.reuseNodeAndCreateTool(prdSharedProfileDescription, diagramDescription, prdSharedProfileCreationTool, NODES, owners.toArray(EClass[]::new));
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Class} on the Diagram.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramClassDescription(DiagramDescription diagramDescription) {
        this.createDiagramNodeDescriptionForClassifier(diagramDescription, this.umlPackage.getClass_());
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link Class}.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedClassDescription(DiagramDescription diagramDescription) {
        this.createSharedNodeDescriptionForClassifier(diagramDescription, this.umlPackage.getClass_());
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link DataType} on the Diagram.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramDataTypeDescription(DiagramDescription diagramDescription) {
        this.createDiagramNodeDescriptionForClassifier(diagramDescription, this.umlPackage.getDataType());
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link DataType}.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     * @param parentNodeDescription
     *            the parent {@link NodeDescription} which contain definition of the new {@link NodeDescription}
     */
    private void createSharedDataTypeDescription(DiagramDescription diagramDescription) {
        this.createSharedNodeDescriptionForClassifier(diagramDescription, this.umlPackage.getDataType());
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Stereotype} on the Diagram.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramStereotypeDescription(DiagramDescription diagramDescription) {
        this.createDiagramNodeDescriptionForClassifier(diagramDescription, this.umlPackage.getStereotype());
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link Stereotype}.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedStereotypeDescription(DiagramDescription diagramDescription) {
        this.createSharedNodeDescriptionForClassifier(diagramDescription, this.umlPackage.getStereotype());
    }

    /**
     * Creates a list {@link NodeDescription} representing {@link Classifier} sub-classes on the Diagram.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     * 
     * @param classifierEClass
     *            the classifier sub-type to represent
     * @return the created {@link NodeDescription}
     */
    private NodeDescription createDiagramNodeDescriptionForClassifier(DiagramDescription diagramDescription, EClass classifierEClass) {
        NodeDescription prdDiagramClassifierDescription = this.newNodeBuilder(classifierEClass, this.getViewBuilder().createRectangularNodeStyle(true, false))//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription())//
                .semanticCandidateExpression(this.getQueryBuilder().queryAllReachableExactType(classifierEClass))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(classifierEClass.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(classifierEClass.getName())) //
                .build();
        diagramDescription.getNodeDescriptions().add(prdDiagramClassifierDescription);

        this.createDefaultToolSectionsInNodeDescription(prdDiagramClassifierDescription);

        NodeTool prdDiagramClassifierCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), classifierEClass);
        this.addDiagramToolInToolSection(diagramDescription, prdDiagramClassifierCreationTool, NODES);

        return prdDiagramClassifierDescription;
    }

    /**
     * Creates a shared list {@link NodeDescription} representing {@link Classifier} sub-classes.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     * @param classifierEClass
     *            the classifier sub-type to represent
     * @return the created {@link NodeDescription}
     */
    private NodeDescription createSharedNodeDescriptionForClassifier(DiagramDescription diagramDescription, EClass classifierEClass) {
        NodeDescription prdSharedClassifierDescription = this.newNodeBuilder(classifierEClass, this.getViewBuilder().createRectangularNodeStyle(true, false))//
                .name(this.getIdBuilder().getSpecializedDomainNodeName(classifierEClass, SHARED_SUFFIX)) //
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription())//
                .semanticCandidateExpression(CallQuery.queryAttributeOnSelf(this.umlPackage.getPackage_PackagedElement()))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(classifierEClass.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(classifierEClass.getName())) //
                .build();
        this.prdSharedDescription.getChildrenDescriptions().add(prdSharedClassifierDescription);

        this.createDefaultToolSectionsInNodeDescription(prdSharedClassifierDescription);

        NodeTool prdSharedClassifierCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), classifierEClass);
        List<EClass> owners = List.of(this.umlPackage.getPackage());
        this.reuseNodeAndCreateTool(prdSharedClassifierDescription, diagramDescription, prdSharedClassifierCreationTool, NODES, owners.toArray(EClass[]::new));
        return prdSharedClassifierDescription;
    }

    /**
     * Creates a shared <i>Literals</i> compartment reused by <i>Enumeration</i> {@link NodeDescription}.
     * <p>
     * The created {@link NodeDescription} compartment is added to the <i>shared</i> {@link NodeDescription} of the
     * diagram.
     * <p>
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedLiteralsCompartmentForEnumerationDescription(DiagramDescription diagramDescription) {
        EClass enumerationEClass = this.umlPackage.getEnumeration();
        List<EClass> owners = List.of(enumerationEClass);
        NodeDescription prdSharedLiteralsCompartmentDescription = this.createSharedCompartmentsDescription(diagramDescription, this.prdSharedDescription, enumerationEClass,
                LITERALS_COMPARTMENT_SUFFIX, owners, List.of(), this.excludeMetaclassNodeDescription);

        this.createEnumerationLiteralsDescription(diagramDescription, prdSharedLiteralsCompartmentDescription);
    }

    /**
     * Creates a shared compartment reused by <i>DataType</i> {@link NodeDescription}.
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
    private void createSharedCompartmentForDataTypeDescription(DiagramDescription diagramDescription, String compartmentName) {
        EClass dataTypeEClass = this.umlPackage.getDataType();
        List<EClass> owners = List.of(dataTypeEClass);
        List<EClass> forbiddenOwners = List.of(this.umlPackage.getEnumeration(), this.umlPackage.getPrimitiveType());
        this.createSharedCompartmentsDescription(diagramDescription, this.prdSharedDescription, dataTypeEClass, compartmentName, owners, forbiddenOwners, this.excludeMetaclassNodeDescription);
    }

    /**
     * Creates a shared compartment reused by <i>Stereotype</i> {@link NodeDescription}.
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
    private void createSharedCompartmentForStereotypeDescription(DiagramDescription diagramDescription, String compartmentName) {
        EClass stereotypeEClass = this.umlPackage.getStereotype();
        List<EClass> owners = List.of(stereotypeEClass);
        this.createSharedCompartmentsDescription(diagramDescription, this.prdSharedDescription, stereotypeEClass, compartmentName, owners, List.of(), this.excludeMetaclassNodeDescription);
    }

    /**
     * Creates a shared compartment reused by <i>Class</i> {@link NodeDescription}.
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
    private void createSharedCompartmentDescriptionForClassDescription(DiagramDescription diagramDescription, String compartmentName) {
        EClass classEClass = this.umlPackage.getClass_();
        List<EClass> owners = List.of(classEClass);
        List<EClass> forbiddenOwners = List.of(this.umlPackage.getStereotype());
        this.createSharedCompartmentsDescription(diagramDescription, this.prdSharedDescription, classEClass, compartmentName, owners, forbiddenOwners, this.excludeMetaclassNodeDescription);
    }

    /**
     * Creates a <i>Property</i> child reused by <i>Attributes</i> compartments.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedAttributeDescription(DiagramDescription diagramDescription) {
        List<EClass> owners = List.of(this.umlPackage.getClass_(), this.umlPackage.getDataType(), this.umlPackage.getStereotype());
        List<EClass> forbiddenOwners = List.of(this.umlPackage.getPrimitiveType(), this.umlPackage.getEnumeration());
        this.createNodeDescriptionInCompartmentDescription(diagramDescription, this.prdSharedDescription, this.umlPackage.getProperty(), ATTRIBUTES_COMPARTMENT_SUFFIX,
                CallQuery.queryOperationOnSelf(this.umlPackage.getClassifier__GetAllAttributes()), this.umlPackage.getStructuredClassifier_OwnedAttribute(), owners, forbiddenOwners,
                this.excludeMetaclassNodeDescription);
    }

    /**
     * Creates a <i>Operation</i> child reused by <i>Operations</i> compartments.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedOperationDescription(DiagramDescription diagramDescription) {
        List<EClass> owners = List.of(this.umlPackage.getClass_(), this.umlPackage.getDataType(), this.umlPackage.getStereotype());
        List<EClass> forbiddenOwners = List.of(this.umlPackage.getPrimitiveType(), this.umlPackage.getEnumeration());
        this.createNodeDescriptionInCompartmentDescription(diagramDescription, this.prdSharedDescription, this.umlPackage.getOperation(), OPERATIONS_COMPARTMENT_SUFFIX,
                CallQuery.queryOperationOnSelf(this.umlPackage.getClassifier__GetAllOperations()), this.umlPackage.getClass_OwnedOperation(), owners, forbiddenOwners,
                this.excludeMetaclassNodeDescription);
    }

    /**
     * Creates a <i>Literals</i> child reused by <i>Literals</i> compartments.
     *
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createEnumerationLiteralsDescription(DiagramDescription diagramDescription, NodeDescription parent) {
        List<EClass> owners = List.of(this.umlPackage.getEnumeration());
        this.createNodeDescriptionInCompartmentDescription(diagramDescription, parent, this.umlPackage.getEnumerationLiteral(), LITERALS_COMPARTMENT_SUFFIX,
                CallQuery.queryAttributeOnSelf(this.umlPackage.getEnumeration_OwnedLiteral()), this.umlPackage.getEnumeration_OwnedLiteral(), owners, List.of(), this.excludeMetaclassNodeDescription);
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link PrimitiveType} on the Diagram.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramPrimitiveTypeDescription(DiagramDescription diagramDescription) {
        EClass primitiveTypeEClass = this.umlPackage.getPrimitiveType();
        NodeDescription prdDiagramPrimitiveTypeDescription = this.newNodeBuilder(primitiveTypeEClass, this.getViewBuilder().createRectangularNodeStyle(true, false)) //
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription()) //
                .semanticCandidateExpression(this.getQueryBuilder().queryAllReachableExactType(primitiveTypeEClass)) //
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED) //
                .labelEditTool(this.getViewBuilder().createDirectEditTool(primitiveTypeEClass.getName())) //
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(primitiveTypeEClass.getName())) //
                .build();
        diagramDescription.getNodeDescriptions().add(prdDiagramPrimitiveTypeDescription);

        this.createDefaultToolSectionsInNodeDescription(prdDiagramPrimitiveTypeDescription);

        NodeTool prdDiagramPrimitiveTypeCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), primitiveTypeEClass);
        this.addDiagramToolInToolSection(diagramDescription, prdDiagramPrimitiveTypeCreationTool, NODES);
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link PrimitiveType}.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedPrimitiveTypeDescription(DiagramDescription diagramDescription) {
        EClass primitiveTypeEClass = this.umlPackage.getPrimitiveType();
        NodeDescription prdPrimitiveTypeDescription = this.newNodeBuilder(primitiveTypeEClass, this.getViewBuilder().createRectangularNodeStyle(true, false)) //
                .name(this.getIdBuilder().getSpecializedDomainNodeName(primitiveTypeEClass, SHARED_SUFFIX)) //
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription()) //
                .semanticCandidateExpression(CallQuery.queryAttributeOnSelf(this.umlPackage.getPackage_PackagedElement())) //
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED) //
                .labelEditTool(this.getViewBuilder().createDirectEditTool(primitiveTypeEClass.getName())) //
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(primitiveTypeEClass.getName())) //
                .build();
        this.prdSharedDescription.getChildrenDescriptions().add(prdPrimitiveTypeDescription);

        this.createDefaultToolSectionsInNodeDescription(prdPrimitiveTypeDescription);

        NodeTool prdSharedPrimitiveTypeCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), primitiveTypeEClass);
        List<EClass> owners = List.of(this.umlPackage.getPackage());
        this.reuseNodeAndCreateTool(prdPrimitiveTypeDescription, diagramDescription, prdSharedPrimitiveTypeCreationTool, NODES, owners.toArray(EClass[]::new));
    }

    /**
     * Create the {@link NodeDescription} representing an UML Metaclass on the Diagram.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramMetaclassDescription(DiagramDescription diagramDescription) {
        EClass metaclassEClass = this.umlPackage.getClass_();
        NodeDescription prdDiagramMetaclassDescription = this.newNodeBuilder(metaclassEClass, this.getViewBuilder().createRectangularNodeStyle(true, false))//
                .name(PRD_METACLASS) //
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ProfileDiagramServices.GET_METACLASS_CANDIDATES)) //
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .build();
        diagramDescription.getNodeDescriptions().add(prdDiagramMetaclassDescription);
        this.createDefaultToolSectionsInNodeDescription(prdDiagramMetaclassDescription);

        // Custom tool is defined from Frontend nodules :
        // /frontend/src/views/edit-project/EditProjectView.tsx/diagramPaletteToolContributions
    }

    /**
     * Create the shared {@link NodeDescription} representing an UML Metaclass.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedMetaclassDescription(DiagramDescription diagramDescription) {
        EClass metaclassEClass = this.umlPackage.getClass_();
        NodeDescription prdSharedMetaclassDescription = this.newNodeBuilder(metaclassEClass, this.getViewBuilder().createRectangularNodeStyle(true, false))//
                .name(PRD_SHARED_METACLASS) //
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(ProfileDiagramServices.GET_METACLASS_CANDIDATES)) //
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .build();

        this.prdSharedDescription.getChildrenDescriptions().add(prdSharedMetaclassDescription);

        this.createDefaultToolSectionsInNodeDescription(prdSharedMetaclassDescription);

        // Use reuseNodeAndCreateTool once the tool to create a metaclass is available.
        this.registerCallback(prdSharedMetaclassDescription, () -> {
            List<NodeDescription> owerNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, List.of(this.umlPackage.getProfile()), List.of());
            for (NodeDescription ownerNodeDescription : owerNodeDescriptions) {
                if (ownerNodeDescription != prdSharedMetaclassDescription.eContainer()) {
                    ownerNodeDescription.getReusedChildNodeDescriptions().add(prdSharedMetaclassDescription);
                }
            }
        });
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Enumeration} on the Diagram.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramEnumerationDescription(DiagramDescription diagramDescription) {
        EClass enumerationEClass = this.umlPackage.getEnumeration();
        NodeDescription prdDiagramEnumerationDescription = this.newNodeBuilder(enumerationEClass, this.getViewBuilder().createRectangularNodeStyle(true, false))//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription())//
                .semanticCandidateExpression(this.getQueryBuilder().queryAllReachableExactType(enumerationEClass))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(enumerationEClass.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(enumerationEClass.getName())) //
                .build();
        diagramDescription.getNodeDescriptions().add(prdDiagramEnumerationDescription);

        this.createDefaultToolSectionsInNodeDescription(prdDiagramEnumerationDescription);

        NodeTool prdDiagramEnumerationCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), enumerationEClass);
        this.addDiagramToolInToolSection(diagramDescription, prdDiagramEnumerationCreationTool, NODES);
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link Enumeration}.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedEnumerationDescription(DiagramDescription diagramDescription) {
        EClass enumerationEClass = this.umlPackage.getEnumeration();
        NodeDescription prdSharedEnumerationDescription = this.newNodeBuilder(enumerationEClass, this.getViewBuilder().createRectangularNodeStyle(true, false))//
                .name(this.getIdBuilder().getSpecializedDomainNodeName(enumerationEClass, SHARED_SUFFIX)) //
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription())//
                .semanticCandidateExpression(CallQuery.queryAttributeOnSelf(this.umlPackage.getPackage_PackagedElement()))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(enumerationEClass.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(enumerationEClass.getName())) //
                .build();
        this.prdSharedDescription.getChildrenDescriptions().add(prdSharedEnumerationDescription);

        this.createDefaultToolSectionsInNodeDescription(prdSharedEnumerationDescription);

        NodeTool prdSharedEnumerationCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), enumerationEClass);
        List<EClass> owners = List.of(this.umlPackage.getPackage());
        this.reuseNodeAndCreateTool(prdSharedEnumerationDescription, diagramDescription, prdSharedEnumerationCreationTool, NODES, owners.toArray(EClass[]::new));
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Association}.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createAssociationDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> sourceAndTargetDescriptionSupplier = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getClassifier()).stream()
                .filter(nodeDescription -> !nodeDescription.getName().equals(PRD_METACLASS) && !nodeDescription.getName().equals(PRD_SHARED_METACLASS)).toList();

        EClass association = this.umlPackage.getAssociation();
        EdgeDescription prdAssociationDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(association, this.getQueryBuilder().queryAllReachableExactType(association),
                sourceAndTargetDescriptionSupplier, sourceAndTargetDescriptionSupplier);
        prdAssociationDescription.getStyle().setLineStyle(LineStyle.SOLID);
        prdAssociationDescription.getStyle().setTargetArrowStyle(ArrowStyle.NONE);
        prdAssociationDescription.getStyle().setSourceArrowStyle(ArrowStyle.NONE);

        EdgeTool prdAssociationCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(prdAssociationDescription, this.umlPackage.getPackage_PackagedElement());
        this.registerCallback(prdAssociationDescription, () -> {
            this.addEdgeToolInEdgesToolSection(sourceAndTargetDescriptionSupplier.get(), prdAssociationCreationTool);
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
        EdgeTool prdExtensionCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(prdExtensionDescription, this.umlPackage.getPackage_PackagedElement());
        this.registerCallback(prdExtensionDescription, () -> {
            this.addEdgeToolInEdgesToolSection(sourceDescriptionSupplier.get(), prdExtensionCreationTool);
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
        Supplier<List<NodeDescription>> sourceDescriptionSupplier = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getClassifier()).stream()
                .filter(nodeDescription -> !nodeDescription.getName().equals(PRD_METACLASS) && !nodeDescription.getName().equals(PRD_SHARED_METACLASS)).toList();
        Supplier<List<NodeDescription>> targetDescriptionSupplier = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getClassifier());

        EClass generalization = this.umlPackage.getGeneralization();
        EdgeDescription prdGeneralizationDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(generalization,
                this.getQueryBuilder().queryAllReachableExactType(generalization), sourceDescriptionSupplier, targetDescriptionSupplier);
        prdGeneralizationDescription.getStyle().setLineStyle(LineStyle.SOLID);
        prdGeneralizationDescription.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);
        prdGeneralizationDescription.getStyle().setSourceArrowStyle(ArrowStyle.NONE);
        EdgeTool prdGeneratlizationCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(prdGeneralizationDescription, this.umlPackage.getClassifier_Generalization());
        this.registerCallback(prdGeneralizationDescription, () -> {
            this.addEdgeToolInEdgesToolSection(sourceDescriptionSupplier.get(), prdGeneratlizationCreationTool);
        });

        diagramDescription.getEdgeDescriptions().add(prdGeneralizationDescription);
        this.getViewBuilder().addDefaultReconnectionTools(prdGeneralizationDescription);
    }

}
