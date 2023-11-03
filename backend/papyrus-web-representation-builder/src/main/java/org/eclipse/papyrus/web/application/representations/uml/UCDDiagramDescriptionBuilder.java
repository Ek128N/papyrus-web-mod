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

import static java.util.stream.Collectors.joining;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.CONVERTED_NODES;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.DIAGRAM_CONTEXT;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.SELECTED_NODE;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.web.application.representations.configuration.ParametricSVGImageRegistryCustomImpl;
import org.eclipse.papyrus.web.application.representations.view.IdBuilder;
import org.eclipse.papyrus.web.application.representations.view.aql.CallQuery;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DiagramToolSection;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeStyle;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.uml2.uml.Abstraction;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Actor;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Extend;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.PackageMerge;
import org.eclipse.uml2.uml.Realization;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Usage;
import org.eclipse.uml2.uml.UseCase;

/**
 * Builder of the "UseCase Diagram " diagram representation.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public final class UCDDiagramDescriptionBuilder extends AbstractRepresentationDescriptionBuilder {

    /**
     * The name of the representation handled by this builder.
     */
    public static final String UCD_REP_NAME = "Use Case Diagram"; //$NON-NLS-1$

    /**
     * The prefix of the representation handled by this builder.
     */
    public static final String UCD_PREFIX = "UCD_"; //$NON-NLS-1$

    /**
     * The suffix of "Classifier as Subject" creation tool
     */
    private static final String AS_SUBJECT = " as Subject"; //$NON-NLS-1$

    /**
     * Subject tool section name.
     */
    private static final String SUBJECT = "Subject"; //$NON-NLS-1$

    /**
     * Factory used to create UML elements.
     */
    private final UMLPackage umlPackage = UMLPackage.eINSTANCE;

    /**
     * The <i>shared</i> {@link NodeDescription} for the diagram.
     */
    private NodeDescription ucdSharedDescription;

    public UCDDiagramDescriptionBuilder() {
        super(UCD_PREFIX, UCD_REP_NAME, UMLPackage.eINSTANCE.getPackage());
    }

    @Override
    protected void fillDescription(DiagramDescription diagramDescription) {

        // create diagram tool sections
        this.createToolSectionsWithSubjectInDiagramDescription(diagramDescription);
        diagramDescription.setPreconditionExpression(CallQuery.queryServiceOnSelf(UseCaseDiagramServices.IS_NOT_PROFILE_MODEL));

        this.createDiagramActorDescription(diagramDescription);
        this.createDiagramActivityAsSubjectDescription(diagramDescription);
        this.createDiagramClassAsSubjectDescription(diagramDescription);
        this.createDiagramComponentAsSubjectDescription(diagramDescription);
        this.createDiagramInteractionAsSubjectDescription(diagramDescription);
        this.createDiagramStateMachineAsSubjectDescription(diagramDescription);
        this.createDiagramUseCaseDescription(diagramDescription);
        this.createDiagramPackageDescription(diagramDescription);
        this.createDiagramCommentDescription(diagramDescription, NODES);
        this.createDiagramConstraintDescription(diagramDescription, NODES);

        this.ucdSharedDescription = this.createSharedDescription(diagramDescription);
        this.createSharedUseCaseDescription(diagramDescription);
        this.createSharedActorDescription(diagramDescription);
        this.createSharedActivityAsSubjectDescription(diagramDescription);
        this.createSharedClassAsSubjectDescription(diagramDescription);
        this.createSharedComponentAsSubjectDescription(diagramDescription);
        this.createSharedInteractionAsSubjectDescription(diagramDescription);
        this.createSharedStateMachineAsSubjectDescription(diagramDescription);
        List<EClass> commentAndConstraintOwners = List.of(this.umlPackage.getPackage(), //
                this.umlPackage.getActivity(), //
                this.umlPackage.getClass_(), //
                this.umlPackage.getComponent(), //
                this.umlPackage.getInteraction(), //
                this.umlPackage.getStateMachine());
        this.createCommentDescriptionInNodeDescription(diagramDescription, this.ucdSharedDescription, NODES, commentAndConstraintOwners);
        this.createConstraintDescriptionInNodeDescription(diagramDescription, this.ucdSharedDescription, NODES, commentAndConstraintOwners);

        this.createAbstractionDescription(diagramDescription);
        this.createAssociationDescription(diagramDescription);
        this.createDependencyDescription(diagramDescription);
        this.createExtendDescription(diagramDescription);
        this.createGeneralizationDescription(diagramDescription);
        this.createIncludeDescription(diagramDescription);
        this.createPackageMergeDescription(diagramDescription);
        this.createPackageImportDescription(diagramDescription);
        this.createRealizationDescription(diagramDescription);
        this.createUsageDescription(diagramDescription);

        diagramDescription.getPalette().setDropTool(this.getViewBuilder().createGenericDropTool(this.getIdBuilder().getDropToolId()));
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Package} on the Diagram.
     * 
     * @param diagramDescription
     *            the UseCase {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramPackageDescription(DiagramDescription diagramDescription) {
        EClass packageEClass = this.umlPackage.getPackage();
        NodeDescription ucdDiagramPackageDescription = this.getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(packageEClass,
                this.getQueryBuilder().queryAllReachableExactType(packageEClass));
        diagramDescription.getNodeDescriptions().add(ucdDiagramPackageDescription);

        this.createToolSectionsWithSubjectInNodeDescription(ucdDiagramPackageDescription);

        NodeTool ucdDiagramPackageCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), packageEClass);
        this.addDiagramToolInToolSection(diagramDescription, ucdDiagramPackageCreationTool, NODES);

        this.createPackageDescriptionInNodeDescription(ucdDiagramPackageDescription);
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Package} in {@code parentNodeDescription}.
     * 
     * @param parentNodeDescription
     *            the parent {@link NodeDescription} which contain definition of the new {@link NodeDescription}
     */
    private void createPackageDescriptionInNodeDescription(NodeDescription parentNodeDescription) {
        EClass packageEClass = this.umlPackage.getPackage();
        NodeDescription ucdPackagePackageDescription = this.getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(packageEClass,
                CallQuery.queryAttributeOnSelf(this.umlPackage.getPackage_PackagedElement()));
        ucdPackagePackageDescription.setName(this.getIdBuilder().getSpecializedDomainNodeName(packageEClass, PACKAGE_CHILD));

        this.createToolSectionsWithSubjectInNodeDescription(ucdPackagePackageDescription);

        parentNodeDescription.getChildrenDescriptions().add(ucdPackagePackageDescription);
        NodeTool ucdPackagePackageCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), packageEClass);
        this.getNodeToolSection(parentNodeDescription, NODES).getNodeTools().add(ucdPackagePackageCreationTool);
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Class} as Subject on the Diagram.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramClassAsSubjectDescription(DiagramDescription diagramDescription) {
        this.createDiagramClassifierAsSubjectDescription(diagramDescription, this.umlPackage.getClass_());
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link Class} as Subject.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedClassAsSubjectDescription(DiagramDescription diagramDescription) {
        this.createSharedClassifierAsSubjectDescription(diagramDescription, this.umlPackage.getClass_());
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Activity} as Subject on the Diagram.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramActivityAsSubjectDescription(DiagramDescription diagramDescription) {
        this.createDiagramClassifierAsSubjectDescription(diagramDescription, this.umlPackage.getActivity());
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link Activity} as Subject.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedActivityAsSubjectDescription(DiagramDescription diagramDescription) {
        this.createSharedClassifierAsSubjectDescription(diagramDescription, this.umlPackage.getActivity());
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Component} as Subject on the Diagram.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramComponentAsSubjectDescription(DiagramDescription diagramDescription) {
        this.createDiagramClassifierAsSubjectDescription(diagramDescription, this.umlPackage.getComponent());
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link Component} as Subject.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedComponentAsSubjectDescription(DiagramDescription diagramDescription) {
        this.createSharedClassifierAsSubjectDescription(diagramDescription, this.umlPackage.getComponent());
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link StateMachine} as Subject on the Diagram.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramStateMachineAsSubjectDescription(DiagramDescription diagramDescription) {
        this.createDiagramClassifierAsSubjectDescription(diagramDescription, this.umlPackage.getStateMachine());
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link StateMachine} as Subject.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedStateMachineAsSubjectDescription(DiagramDescription diagramDescription) {
        this.createSharedClassifierAsSubjectDescription(diagramDescription, this.umlPackage.getStateMachine());
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Interaction} as Subject on the Diagram.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramInteractionAsSubjectDescription(DiagramDescription diagramDescription) {
        this.createDiagramClassifierAsSubjectDescription(diagramDescription, this.umlPackage.getInteraction());
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link Interaction} as Subject.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedInteractionAsSubjectDescription(DiagramDescription diagramDescription) {
        this.createSharedClassifierAsSubjectDescription(diagramDescription, this.umlPackage.getInteraction());
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Classifier} as Subject on the Diagram.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     * @param classifierAsSubject
     *            the type of {@link Classifier} to create
     */
    private void createDiagramClassifierAsSubjectDescription(DiagramDescription diagramDescription, EClass classifierAsSubject) {
        NodeDescription ucdDiagramClassifierDescription = this.newNodeBuilder(classifierAsSubject, this.getViewBuilder().createRectangularNodeStyle(true, true))//
                .name(this.getIdBuilder().getDomainNodeName(classifierAsSubject)) //
                .semanticCandidateExpression(this.getQueryBuilder().queryAllReachableExactType(classifierAsSubject))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .labelEditTool(this.getViewBuilder().createDirectEditTool())//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(classifierAsSubject.getName())) //
                .build();
        diagramDescription.getNodeDescriptions().add(ucdDiagramClassifierDescription);

        this.createDefaultToolSectionsInNodeDescription(ucdDiagramClassifierDescription);

        NodeTool ucdDiagramClassifierCreationTool = this.createAsSubjectCreationTool(this.umlPackage.getPackage_PackagedElement(), classifierAsSubject);
        this.addDiagramToolInToolSection(diagramDescription, ucdDiagramClassifierCreationTool, SUBJECT);
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link Classifier} as Subject.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     * @param classifierAsSubject
     *            the type of {@link Classifier} to create
     */
    private void createSharedClassifierAsSubjectDescription(DiagramDescription diagramDescription, EClass classifierAsSubject) {
        NodeDescription ucdPackageClassifierDescription = this.newNodeBuilder(classifierAsSubject, this.getViewBuilder().createRectangularNodeStyle(true, true))//
                .name(this.getIdBuilder().getSpecializedDomainNodeName(classifierAsSubject, SHARED_SUFFIX)) //
                .semanticCandidateExpression(CallQuery.queryAttributeOnSelf(this.umlPackage.getPackage_PackagedElement()))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .labelEditTool(this.getViewBuilder().createDirectEditTool())//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(classifierAsSubject.getName())) //
                .build();
        this.ucdSharedDescription.getChildrenDescriptions().add(ucdPackageClassifierDescription);

        this.createDefaultToolSectionsInNodeDescription(ucdPackageClassifierDescription);

        NodeTool ucdPackageClassifierCreationTool = this.createAsSubjectCreationTool(this.umlPackage.getPackage_PackagedElement(), classifierAsSubject);
        List<EClass> owners = List.of(this.umlPackage.getPackage());
        this.reuseNodeAndCreateTool(ucdPackageClassifierDescription, diagramDescription, ucdPackageClassifierCreationTool, SUBJECT, owners.toArray(EClass[]::new));
    }

    /**
     * Suffix creation tool with "as Subject".
     * 
     * @param containementRef
     *            the reference used to contain the new type
     * @param newType
     *            the new type to create,
     * @return the creation tool
     */
    private NodeTool createAsSubjectCreationTool(EReference containementRef, EClass newType) {
        String domainTypeName = this.getIdBuilder().findWordsInMixedCase(newType.getName()).stream().collect(joining(IdBuilder.SPACE));
        return this.getViewBuilder().createCreationTool(IdBuilder.NEW + domainTypeName + AS_SUBJECT, containementRef, newType);
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link UseCase} on the Diagram.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramUseCaseDescription(DiagramDescription diagramDescription) {
        EClass useCaseEClass = this.umlPackage.getUseCase();

        ImageNodeStyleDescription useCaseNodeStyle = this.getViewBuilder().createImageNodeStyle(ParametricSVGImageRegistryCustomImpl.PARAMETRIC_USE_CASE_IMAGE_ID.toString(), true);
        useCaseNodeStyle.setWidthComputationExpression("204"); //$NON-NLS-1$
        useCaseNodeStyle.setHeightComputationExpression("104"); //$NON-NLS-1$

        NodeDescription ucdDiagramUseCaseDescription = this.newNodeBuilder(useCaseEClass, useCaseNodeStyle)//
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(UseCaseDiagramServices.GET_USECASE_NODE_CANDIDATES))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool())//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(useCaseEClass.getName())) //
                .build();

        diagramDescription.getNodeDescriptions().add(ucdDiagramUseCaseDescription);

        this.createDefaultToolSectionsInNodeDescription(ucdDiagramUseCaseDescription);

        // create tools
        String domainTypeName = this.getIdBuilder().findWordsInMixedCase(useCaseEClass.getName()).stream().collect(joining(IdBuilder.SPACE));
        NodeTool ucdDiagramUseCaseCreationTool = this.getViewBuilder().createCreationTool(IdBuilder.NEW + domainTypeName, UseCaseDiagramServices.CREATE_USECASE,
                List.of(SELECTED_NODE, DIAGRAM_CONTEXT, CONVERTED_NODES));
        this.addDiagramToolInToolSection(diagramDescription, ucdDiagramUseCaseCreationTool, NODES);
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link UseCase}.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedUseCaseDescription(DiagramDescription diagramDescription) {
        EClass useCaseEClass = this.umlPackage.getUseCase();

        ImageNodeStyleDescription useCaseNodeStyle = this.getViewBuilder().createImageNodeStyle(ParametricSVGImageRegistryCustomImpl.PARAMETRIC_USE_CASE_IMAGE_ID.toString(), true);
        useCaseNodeStyle.setWidthComputationExpression("204"); //$NON-NLS-1$
        useCaseNodeStyle.setHeightComputationExpression("104"); //$NON-NLS-1$

        NodeDescription ucdSharedUseCaseDescription = this.newNodeBuilder(useCaseEClass, useCaseNodeStyle)//
                .name(this.getIdBuilder().getSpecializedDomainNodeName(useCaseEClass, SHARED_SUFFIX)) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(UseCaseDiagramServices.GET_USECASE_NODE_CANDIDATES))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool())//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(useCaseEClass.getName())) //
                .build();

        this.ucdSharedDescription.getChildrenDescriptions().add(ucdSharedUseCaseDescription);

        this.createDefaultToolSectionsInNodeDescription(ucdSharedUseCaseDescription);

        String domainTypeName = this.getIdBuilder().findWordsInMixedCase(useCaseEClass.getName()).stream().collect(joining(IdBuilder.SPACE));
        NodeTool ucdSharedUseCaseCreationTool = this.getViewBuilder().createCreationTool(IdBuilder.NEW + domainTypeName, UseCaseDiagramServices.CREATE_USECASE,
                List.of(SELECTED_NODE, DIAGRAM_CONTEXT, CONVERTED_NODES));

        List<EClass> owners = List.of(this.umlPackage.getClass_(), //
                this.umlPackage.getActivity(), //
                this.umlPackage.getComponent(), //
                this.umlPackage.getInteraction(), //
                this.umlPackage.getStateMachine(), //
                this.umlPackage.getPackage());
        this.reuseNodeAndCreateTool(ucdSharedUseCaseDescription, diagramDescription, ucdSharedUseCaseCreationTool, NODES, owners.toArray(EClass[]::new));
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Actor} on the Diagram.
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramActorDescription(DiagramDescription diagramDescription) {
        NodeStyleDescription actorNodeStyle = this.getViewBuilder().createImageNodeStyle(UUID.nameUUIDFromBytes("Actor.svg".getBytes()).toString(), false); //$NON-NLS-1$
        actorNodeStyle.setBorderSize(0);
        actorNodeStyle.setWidthComputationExpression("70"); //$NON-NLS-1$
        actorNodeStyle.setHeightComputationExpression("100"); //$NON-NLS-1$

        EClass actorEClass = this.umlPackage.getActor();
        NodeDescription ucdDiagramActorDescription = this.newNodeBuilder(actorEClass, actorNodeStyle)//
                .name(this.getIdBuilder().getDomainNodeName(actorEClass)) //
                .semanticCandidateExpression(this.getQueryBuilder().queryAllReachableExactType(actorEClass))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool())//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(actorEClass.getName())) //
                .build();

        diagramDescription.getNodeDescriptions().add(ucdDiagramActorDescription);

        this.createDefaultToolSectionsInNodeDescription(ucdDiagramActorDescription);

        NodeTool ucdDiagramActorCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), actorEClass);
        this.addDiagramToolInToolSection(diagramDescription, ucdDiagramActorCreationTool, NODES);
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link Actor}.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedActorDescription(DiagramDescription diagramDescription) {
        NodeStyleDescription actorNodeStyle = this.getViewBuilder().createImageNodeStyle(UUID.nameUUIDFromBytes("Actor.svg".getBytes()).toString(), false); //$NON-NLS-1$
        actorNodeStyle.setBorderSize(0);
        actorNodeStyle.setWidthComputationExpression("70"); //$NON-NLS-1$
        actorNodeStyle.setHeightComputationExpression("100"); //$NON-NLS-1$

        EClass actorEClass = this.umlPackage.getActor();
        NodeDescription ucdPackageActorDescription = this.newNodeBuilder(actorEClass, actorNodeStyle)//
                .name(this.getIdBuilder().getSpecializedDomainNodeName(actorEClass, SHARED_SUFFIX)) //
                .semanticCandidateExpression(CallQuery.queryAttributeOnSelf(this.umlPackage.getPackage_PackagedElement()))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool())//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(actorEClass.getName())) //
                .build();
        this.ucdSharedDescription.getChildrenDescriptions().add(ucdPackageActorDescription);

        this.createDefaultToolSectionsInNodeDescription(ucdPackageActorDescription);

        this.createDefaultToolSectionsInNodeDescription(ucdPackageActorDescription);

        NodeTool ucdPackageActorCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), actorEClass);
        List<EClass> owners = List.of(this.umlPackage.getPackage());
        this.reuseNodeAndCreateTool(ucdPackageActorDescription, diagramDescription, ucdPackageActorCreationTool, NODES, owners.toArray(EClass[]::new));
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Abstraction}.
     * 
     * @param diagramDescription
     *            the UseCase {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createAbstractionDescription(DiagramDescription diagramDescription) {
        this.createDependencyOrSubTypeDescription(diagramDescription, this.umlPackage.getAbstraction(), LineStyle.DASH, ArrowStyle.INPUT_ARROW);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Association}.
     * 
     * @param diagramDescription
     *            the UseCase {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createAssociationDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> sourceAndTargetDescriptionsSupplier = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getClassifier());

        EClass association = this.umlPackage.getAssociation();
        EdgeDescription ucdAssociationDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(association, this.getQueryBuilder().queryAllReachableExactType(association),
                sourceAndTargetDescriptionsSupplier, sourceAndTargetDescriptionsSupplier);
        ucdAssociationDescription.getStyle().setLineStyle(LineStyle.SOLID);
        ucdAssociationDescription.getStyle().setTargetArrowStyle(ArrowStyle.NONE);
        ucdAssociationDescription.getStyle().setSourceArrowStyle(ArrowStyle.NONE);

        EdgeTool ucdAssociationCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(ucdAssociationDescription, this.umlPackage.getPackage_PackagedElement());
        this.registerCallback(ucdAssociationDescription, () -> {
            this.addEdgeToolInEdgesToolSection(sourceAndTargetDescriptionsSupplier.get(), ucdAssociationCreationTool);
        });

        ucdAssociationDescription.setBeginLabelExpression(this.getQueryBuilder().createDomainBaseEdgeSourceLabelExpression());
        ucdAssociationDescription.getPalette().setBeginLabelEditTool(this.getViewBuilder().createDirectEditTool(CallQuery.queryServiceOnSelf(ClassDiagramServices.GET_ASSOCIATION_TARGET)));

        ucdAssociationDescription.setEndLabelExpression(this.getQueryBuilder().createDomainBaseEdgeTargetLabelExpression());
        ucdAssociationDescription.getPalette().setEndLabelEditTool(this.getViewBuilder().createDirectEditTool(CallQuery.queryServiceOnSelf(ClassDiagramServices.GET_ASSOCIATION_SOURCE)));

        diagramDescription.getEdgeDescriptions().add(ucdAssociationDescription);

        this.getViewBuilder().addDefaultReconnectionTools(ucdAssociationDescription);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Dependency}.
     * 
     * @param diagramDescription
     *            the UseCase {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createDependencyDescription(DiagramDescription diagramDescription) {
        this.createDependencyOrSubTypeDescription(diagramDescription, this.umlPackage.getDependency(), LineStyle.DASH, ArrowStyle.INPUT_ARROW);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Extend}.
     * 
     * @param diagramDescription
     *            the UseCase {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createExtendDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> useCaseCollector = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getUseCase());
        EClass extendClass = this.umlPackage.getExtend();
        EdgeDescription ucdExtendDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(extendClass, this.getQueryBuilder().queryAllReachableExactType(extendClass),
                useCaseCollector, useCaseCollector);
        EdgeStyle style = ucdExtendDescription.getStyle();
        style.setLineStyle(LineStyle.DASH);
        style.setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        diagramDescription.getEdgeDescriptions().add(ucdExtendDescription);

        EdgeTool ucdExtendCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(ucdExtendDescription, this.umlPackage.getUseCase_Extend());
        this.registerCallback(ucdExtendDescription, () -> {
            this.addEdgeToolInEdgesToolSection(useCaseCollector.get(), ucdExtendCreationTool);
        });
        this.getViewBuilder().addDefaultReconnectionTools(ucdExtendDescription);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Generalization}.
     * 
     * @param diagramDescription
     *            the UseCase {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createGeneralizationDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> sourceAndTargetDescriptionsSupplier = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getClassifier());

        EClass generalization = this.umlPackage.getGeneralization();
        EdgeDescription ucdGeneralizationDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(generalization,
                this.getQueryBuilder().queryAllReachableExactType(generalization), sourceAndTargetDescriptionsSupplier, sourceAndTargetDescriptionsSupplier);
        ucdGeneralizationDescription.getStyle().setLineStyle(LineStyle.SOLID);
        ucdGeneralizationDescription.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);
        EdgeTool ucdGeneralizationCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(ucdGeneralizationDescription, this.umlPackage.getClassifier_Generalization());
        this.registerCallback(ucdGeneralizationDescription, () -> {
            this.addEdgeToolInEdgesToolSection(sourceAndTargetDescriptionsSupplier.get(), ucdGeneralizationCreationTool);
        });

        diagramDescription.getEdgeDescriptions().add(ucdGeneralizationDescription);

        this.getViewBuilder().addDefaultReconnectionTools(ucdGeneralizationDescription);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Include}.
     * 
     * @param diagramDescription
     *            the UseCase {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createIncludeDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> useCaseCollector = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getUseCase());
        EdgeDescription ucdIncludeDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.umlPackage.getInclude(),
                this.getQueryBuilder().queryAllReachableExactType(this.umlPackage.getInclude()), useCaseCollector, useCaseCollector);
        EdgeStyle style = ucdIncludeDescription.getStyle();
        style.setLineStyle(LineStyle.DASH);
        style.setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        diagramDescription.getEdgeDescriptions().add(ucdIncludeDescription);

        EdgeTool ucdIncludeCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(ucdIncludeDescription, this.umlPackage.getUseCase_Include());
        this.registerCallback(ucdIncludeDescription, () -> {
            this.addEdgeToolInEdgesToolSection(useCaseCollector.get(), ucdIncludeCreationTool);
        });
        this.getViewBuilder().addDefaultReconnectionTools(ucdIncludeDescription);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link PackageImport}.
     * 
     * @param diagramDescription
     *            the UseCase {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createPackageImportDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> packageDescriptions = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getPackage());
        EdgeDescription ucdPackageImportDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.umlPackage.getPackageImport(),
                this.getQueryBuilder().queryAllReachableExactType(this.umlPackage.getPackageImport()), packageDescriptions, packageDescriptions);
        ucdPackageImportDescription.getStyle().setLineStyle(LineStyle.DASH);
        ucdPackageImportDescription.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);

        EdgeTool ucdPackageImportCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(ucdPackageImportDescription, this.umlPackage.getNamespace_PackageImport());
        this.registerCallback(ucdPackageImportDescription, () -> {
            this.addEdgeToolInEdgesToolSection(packageDescriptions.get(), ucdPackageImportCreationTool);
        });
        diagramDescription.getEdgeDescriptions().add(ucdPackageImportDescription);
        this.getViewBuilder().addDefaultReconnectionTools(ucdPackageImportDescription);

    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link PackageMerge}.
     * 
     * @param diagramDescription
     *            the UseCase {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createPackageMergeDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> packageDescriptions = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getPackage());
        EdgeDescription ucdPackageMergeDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.umlPackage.getPackageMerge(),
                this.getQueryBuilder().queryAllReachableExactType(this.umlPackage.getPackageMerge()), packageDescriptions, packageDescriptions);
        ucdPackageMergeDescription.getStyle().setLineStyle(LineStyle.DASH);
        ucdPackageMergeDescription.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        EdgeTool ucdPackageMergeCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(ucdPackageMergeDescription, this.umlPackage.getPackage_PackageMerge());
        this.registerCallback(ucdPackageMergeDescription, () -> {
            this.addEdgeToolInEdgesToolSection(packageDescriptions.get(), ucdPackageMergeCreationTool);
        });
        diagramDescription.getEdgeDescriptions().add(ucdPackageMergeDescription);
        this.getViewBuilder().addDefaultReconnectionTools(ucdPackageMergeDescription);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Realization}.
     * 
     * @param diagramDescription
     *            the UseCase {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createRealizationDescription(DiagramDescription diagramDescription) {
        this.createDependencyOrSubTypeDescription(diagramDescription, this.umlPackage.getRealization(), LineStyle.DASH, ArrowStyle.INPUT_CLOSED_ARROW);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Usage}.
     * 
     * @param diagramDescription
     *            the UseCase {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createUsageDescription(DiagramDescription diagramDescription) {
        this.createDependencyOrSubTypeDescription(diagramDescription, this.umlPackage.getUsage(), LineStyle.DASH, ArrowStyle.INPUT_ARROW);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Dependency} or subType.
     * 
     * @param diagramDescription
     *            the UseCase {@link DiagramDescription} containing the created {@link EdgeDescription}
     * @param edgeToCreate
     *            kind of edge to create which should be a Dependency or a subType
     * @param lineStyle
     *            the line style of the edge
     * @param arrowStyle
     *            the arrow style of the edge
     */
    private void createDependencyOrSubTypeDescription(DiagramDescription diagramDescription, EClass edgeToCreate, LineStyle lineStyle, ArrowStyle arrowStyle) {
        Supplier<List<NodeDescription>> namedElementCollector = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getNamedElement());
        EdgeDescription ucdDependencyDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(edgeToCreate,
                this.getQueryBuilder().queryAllReachableExactType(edgeToCreate), namedElementCollector, namedElementCollector);
        EdgeStyle style = ucdDependencyDescription.getStyle();
        style.setLineStyle(lineStyle);
        style.setTargetArrowStyle(arrowStyle);
        diagramDescription.getEdgeDescriptions().add(ucdDependencyDescription);
        EdgeTool ucdDependencyCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(ucdDependencyDescription, this.umlPackage.getPackage_PackagedElement());
        this.registerCallback(ucdDependencyDescription, () -> {
            this.addEdgeToolInEdgesToolSection(namedElementCollector.get(), ucdDependencyCreationTool);
        });
        this.getViewBuilder().addDefaultReconnectionTools(ucdDependencyDescription);
    }

    /**
     * Create tools sections "Nodes", "Edges" and "Subject" in the palette tool of a given {@link NodeDescription}.
     * 
     * @param nodeDescription
     *            the node description with the palette to complete with tool sections
     */
    private void createToolSectionsWithSubjectInNodeDescription(NodeDescription nodeDescription) {
        NodeToolSection subjectToolSection = this.getViewBuilder().createNodeToolSection(SUBJECT);
        NodeToolSection nodesToolSection = this.getViewBuilder().createNodeToolSection(NODES);
        NodeToolSection edgesToolSection = this.getViewBuilder().createNodeToolSection(EDGES);
        nodeDescription.getPalette().getToolSections().addAll(List.of(subjectToolSection, nodesToolSection, edgesToolSection));
    }

    /**
     * Create tools sections "Nodes", "Edges" and "Subject" in the palette tool of a given {@link DiagramDescription}.
     * 
     * @param nodeDescription
     *            the node description with the palette to complete with tool sections
     */
    protected void createToolSectionsWithSubjectInDiagramDescription(DiagramDescription diagramDescription) {
        DiagramToolSection subjectToolSection = this.getViewBuilder().createDiagramToolSection(SUBJECT);
        DiagramToolSection nodesToolSection = this.getViewBuilder().createDiagramToolSection(NODES);
        DiagramToolSection edgesToolSection = this.getViewBuilder().createDiagramToolSection(EDGES);
        diagramDescription.getPalette().getToolSections().addAll(List.of(subjectToolSection, nodesToolSection, edgesToolSection));

    }

}
