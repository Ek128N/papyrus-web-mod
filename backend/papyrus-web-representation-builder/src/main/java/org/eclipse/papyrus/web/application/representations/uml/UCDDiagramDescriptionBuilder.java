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

import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.CONVERTED_NODES;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.DIAGRAM_CONTEXT;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.SELECTED_NODE;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.uml.domain.services.UMLHelper;
import org.eclipse.papyrus.web.application.representations.configuration.ParametricSVGImageRegistryCustomImpl;
import org.eclipse.papyrus.web.application.representations.view.CreationToolsUtil;
import org.eclipse.papyrus.web.application.representations.view.aql.CallQuery;
import org.eclipse.papyrus.web.application.representations.view.builders.NodeSemanticCandidateExpressionTransformer;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeStyle;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.uml2.uml.Abstraction;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Actor;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Extend;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interaction;
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
    public static final String UCD_REP_NAME = "Use Case Diagram";

    /**
     * The prefix of the representation handled by this builder.
     */
    public static final String UCD_PREFIX = "UCD_";

    /**
     * The prefix of creation tool.
     */
    private static final String NEW = "New "; //$NON-NLS-1$

    /**
     * Factory used to create UML elements.
     */
    private final UMLPackage umlPackage = UMLPackage.eINSTANCE;

    public UCDDiagramDescriptionBuilder() {
        super(UCD_PREFIX, UCD_REP_NAME, UMLPackage.eINSTANCE.getPackageableElement());
    }

    @Override
    protected void fillDescription(DiagramDescription diagramDescription) {

        this.createActorDescription(diagramDescription);
        this.createActivityAsSubjectDescription(diagramDescription);
        this.createClassAsSubjectDescription(diagramDescription);
        this.createComponentAsSubjectDescription(diagramDescription);
        this.createInteractionAsSubjectDescription(diagramDescription);
        this.createStateMachineAsSubjectDescription(diagramDescription);
        this.createUseCaseDescription(diagramDescription);
        this.createPackageDescription(diagramDescription);

        NodeDescription sharedDescription = this.createSharedDescription(diagramDescription);
        this.createSharedUseCaseDescription(diagramDescription, sharedDescription);
        List<EClass> commentAndConstraintOwners = List.of(this.umlPackage.getPackage(), //
                this.umlPackage.getActivity(), //
                this.umlPackage.getClass_(), //
                this.umlPackage.getComponent(), //
                this.umlPackage.getInteraction(), //
                this.umlPackage.getStateMachine());
        this.createCommentDescriptionInNodeDescription(diagramDescription, sharedDescription, commentAndConstraintOwners);
        this.createConstraintDescriptionInNodeDescription(diagramDescription, sharedDescription, commentAndConstraintOwners);

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

        this.createCommentDescriptionInDiagramDescription(diagramDescription);
        this.createConstraintDescriptionInDiagramDescription(diagramDescription);

        diagramDescription.getPalette().setDropTool(this.getViewBuilder().createGenericDropTool(this.getIdBuilder().getDropToolId()));
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Package}.
     * 
     * @param diagramDescription
     *            the UseCase {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    @Override
    protected void createPackageDescription(DiagramDescription diagramDescription) {
        EClass packageEClass = this.umlPackage.getPackage();
        NodeDescription ucdPackage = this.getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(packageEClass, this.getQueryBuilder().queryAllReachable(packageEClass));
        diagramDescription.getNodeDescriptions().add(ucdPackage);

        diagramDescription.getPalette().getNodeTools().add(this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), packageEClass));

        this.registerCallback(ucdPackage, () -> {
            List<NodeDescription> packages = this.collectNodesWithDomain(diagramDescription, packageEClass);
            packages.forEach(p -> {
                p.getPalette().getNodeTools().add(this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), packageEClass));
            });
            String childrenCandidateExpression = CallQuery.queryAttributeOnSelf(UMLPackage.eINSTANCE.getPackage_PackagedElement());
            List<NodeDescription> copiedClassifier = diagramDescription.getNodeDescriptions().stream()
                    .filter(n -> this.isValidNodeDescription(n, false, false, this.umlPackage.getPackageableElement())
                            && !this.isValidNodeDescription(n, false, false, this.umlPackage.getConstraint(), this.umlPackage.getUseCase()))
                    .map(n -> this.transformIntoPackageChildNode(n, childrenCandidateExpression, diagramDescription)).toList();
            ucdPackage.getChildrenDescriptions().addAll(copiedClassifier);
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
     *            the UseCase {@link DiagramDescription} containing the created {@link NodeDescription}
     * @return the {@link NodeDescription} representing an UML input at {@link Package} level.
     */
    private NodeDescription transformIntoPackageChildNode(NodeDescription input, String semanticCandidateExpression, DiagramDescription diagramDescription) {
        EClass eClass = UMLHelper.toEClass(input.getDomainType());
        String id = this.getIdBuilder().getSpecializedDomainNodeName(eClass, PACKAGE_CHILD);
        NodeDescription n = new NodeSemanticCandidateExpressionTransformer().intoNewCanidateExpression(id, input, semanticCandidateExpression);

        if (UMLPackage.eINSTANCE.getPackage().isSuperTypeOf(eClass)) {
            // Add Package children into Package_inPackage child
            this.collectAndReusedChildNodes(n, this.umlPackage.getPackageableElement(), diagramDescription, PACKAGE_CHILDREN_FILTER);
        }
        return n;
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Class} as Subject at diagram level.
     * 
     * @param diagramDescription
     *            the UseCase {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createClassAsSubjectDescription(DiagramDescription diagramDescription) {
        this.createClassifierAsSubjectDescription(diagramDescription, this.umlPackage.getClass_());
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Activity} as Subject at diagram level.
     * 
     * @param diagramDescription
     *            the UseCase {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createActivityAsSubjectDescription(DiagramDescription diagramDescription) {
        this.createClassifierAsSubjectDescription(diagramDescription, this.umlPackage.getActivity());
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Component} as Subject at diagram level.
     * 
     * @param diagramDescription
     *            the UseCase {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createComponentAsSubjectDescription(DiagramDescription diagramDescription) {
        this.createClassifierAsSubjectDescription(diagramDescription, this.umlPackage.getComponent());
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link StateMachine} as Subject at diagram level.
     * 
     * @param diagramDescription
     *            the UseCase {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createStateMachineAsSubjectDescription(DiagramDescription diagramDescription) {
        this.createClassifierAsSubjectDescription(diagramDescription, this.umlPackage.getStateMachine());
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Interaction} as Subject at diagram level.
     * 
     * @param diagramDescription
     *            the UseCase {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createInteractionAsSubjectDescription(DiagramDescription diagramDescription) {
        this.createClassifierAsSubjectDescription(diagramDescription, this.umlPackage.getInteraction());
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Classifier} as Subject at diagram level.
     * 
     * @param diagramDescription
     *            the UseCase {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createClassifierAsSubjectDescription(DiagramDescription diagramDescription, EClass classifierAsSubject) {
        NodeDescription subjectAsClassifierDescription = this.newNodeBuilder(classifierAsSubject, this.getViewBuilder().createRectangularNodeStyle(true, true))//
                .name(this.getIdBuilder().getDomainNodeName(classifierAsSubject)) //
                .semanticCandidateExpression(this.getQueryBuilder().queryAllReachable(classifierAsSubject))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .labelEditTool(this.getViewBuilder().createDirectEditTool())//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(classifierAsSubject.getName())) //
                .build();
        diagramDescription.getNodeDescriptions().add(subjectAsClassifierDescription);

        NodeTool createUcdClassTool = this.createAsSubjectCreationTool(this.umlPackage.getPackage_PackagedElement(), classifierAsSubject);
        Supplier<List<NodeDescription>> packageOwners = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getPackage());
        this.registerCallback(subjectAsClassifierDescription, () -> {
            CreationToolsUtil.addNodeCreationTool(packageOwners, createUcdClassTool);
            diagramDescription.getPalette().getNodeTools().add(createUcdClassTool);
        });
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
        return this.getViewBuilder().createCreationTool("New " + newType.getName() + " as Subject", containementRef, newType); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link UseCase} at diagram level.
     * 
     * @param diagramDescription
     *            the UseCase {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createUseCaseDescription(DiagramDescription diagramDescription) {
        EClass useCaseEClass = this.umlPackage.getUseCase();
        ImageNodeStyleDescription useCaseNodeStyle = this.getViewBuilder().createImageNodeStyle(ParametricSVGImageRegistryCustomImpl.PARAMETRIC_USE_CASE_IMAGE_ID.toString(), true);
        useCaseNodeStyle.setWidthComputationExpression("204"); //$NON-NLS-1$
        useCaseNodeStyle.setHeightComputationExpression("104"); //$NON-NLS-1$

        NodeDescription ucdUseCaseDescription = this.newNodeBuilder(useCaseEClass, useCaseNodeStyle)//
                .name(this.getIdBuilder().getDomainNodeName(useCaseEClass)) //
                .semanticCandidateExpression(this.getQueryBuilder().queryAllReachable(useCaseEClass))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool())//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(useCaseEClass.getName())) //
                .build();

        diagramDescription.getNodeDescriptions().add(ucdUseCaseDescription);

        // create tools
        NodeTool ucdUseCaseCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), useCaseEClass);
        diagramDescription.getPalette().getNodeTools().add(ucdUseCaseCreationTool);
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link UseCase} at sub-level.
     * 
     * @param diagramDescription
     *            the UseCase {@link DiagramDescription} containing the created {@link NodeDescription}
     * @param sharedOwnerDescription
     *            the parent {@link NodeDescription} which contain definition of the new {@link NodeDescription}
     */
    private void createSharedUseCaseDescription(DiagramDescription diagramDescription, NodeDescription sharedOwnerDescription) {
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

        sharedOwnerDescription.getChildrenDescriptions().add(ucdSharedUseCaseDescription);

        // create tools
        NodeTool ucdSharedUseCaseCreationTool = this.getViewBuilder().createCreationTool(NEW + useCaseEClass.getName(), UseCaseDiagramServices.CREATE_USECASE,
                List.of(SELECTED_NODE, DIAGRAM_CONTEXT, CONVERTED_NODES));

        List<EClass> owners = List.of(useCaseEClass, //
                this.umlPackage.getClass_(), //
                this.umlPackage.getActivity(), //
                this.umlPackage.getComponent(), //
                this.umlPackage.getInteraction(), //
                this.umlPackage.getStateMachine(), //
                this.umlPackage.getPackage());
        this.reuseNodeAndCreateTool(ucdSharedUseCaseDescription, diagramDescription, ucdSharedUseCaseCreationTool, owners.toArray(EClass[]::new));
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Actor} at diagram level.
     * 
     * @param diagramDescription
     *            the UseCase {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createActorDescription(DiagramDescription diagramDescription) {
        NodeStyleDescription actorNodeStyle = this.getViewBuilder().createImageNodeStyle(UUID.nameUUIDFromBytes("Actor.svg".getBytes()).toString(), false); //$NON-NLS-1$
        actorNodeStyle.setBorderSize(0);
        actorNodeStyle.setWidthComputationExpression("70"); //$NON-NLS-1$
        actorNodeStyle.setHeightComputationExpression("100"); //$NON-NLS-1$

        EClass actorEClass = this.umlPackage.getActor();
        NodeDescription ucdActorDescription = this.newNodeBuilder(actorEClass, actorNodeStyle)//
                .name(this.getIdBuilder().getDomainNodeName(actorEClass)) //
                .semanticCandidateExpression(this.getQueryBuilder().queryAllReachable(actorEClass))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(this.getViewBuilder().createDirectEditTool())//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(actorEClass.getName())) //
                .build();

        diagramDescription.getNodeDescriptions().add(ucdActorDescription);

        NodeTool createActorTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), actorEClass);
        Supplier<List<NodeDescription>> packageOwners = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getPackage());
        this.registerCallback(ucdActorDescription, () -> {
            CreationToolsUtil.addNodeCreationTool(packageOwners, createActorTool);
            diagramDescription.getPalette().getNodeTools().add(createActorTool);
        });
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

        EdgeTool associationCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(ucdAssociationDescription, this.umlPackage.getPackage_PackagedElement());
        this.registerCallback(ucdAssociationDescription, () -> {
            CreationToolsUtil.addEdgeCreationTool(sourceAndTargetDescriptionsSupplier, associationCreationTool);
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
        EdgeDescription ucdExtendDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(extendClass, this.getQueryBuilder().queryAllReachable(extendClass),
                useCaseCollector, useCaseCollector);
        EdgeStyle style = ucdExtendDescription.getStyle();
        style.setLineStyle(LineStyle.DASH);
        style.setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        diagramDescription.getEdgeDescriptions().add(ucdExtendDescription);

        EdgeTool createUcdExtendTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(ucdExtendDescription, this.umlPackage.getUseCase_Extend());
        this.registerCallback(ucdExtendDescription, () -> {
            CreationToolsUtil.addEdgeCreationTool(useCaseCollector, createUcdExtendTool);
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
        EdgeTool creationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(ucdGeneralizationDescription, this.umlPackage.getClassifier_Generalization());
        this.registerCallback(ucdGeneralizationDescription, () -> {
            CreationToolsUtil.addEdgeCreationTool(sourceAndTargetDescriptionsSupplier, creationTool);
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
                this.getQueryBuilder().queryAllReachable(this.umlPackage.getInclude()), useCaseCollector, useCaseCollector);
        EdgeStyle style = ucdIncludeDescription.getStyle();
        style.setLineStyle(LineStyle.DASH);
        style.setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        diagramDescription.getEdgeDescriptions().add(ucdIncludeDescription);

        EdgeTool createUcdIncludeTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(ucdIncludeDescription, this.umlPackage.getUseCase_Include());
        this.registerCallback(ucdIncludeDescription, () -> {
            CreationToolsUtil.addEdgeCreationTool(useCaseCollector, createUcdIncludeTool);
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
                this.getQueryBuilder().queryAllReachable(this.umlPackage.getPackageImport()), packageDescriptions, packageDescriptions);
        ucdPackageImportDescription.getStyle().setLineStyle(LineStyle.DASH);
        ucdPackageImportDescription.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);

        EdgeTool creationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(ucdPackageImportDescription, this.umlPackage.getNamespace_PackageImport());
        this.registerCallback(ucdPackageImportDescription, () -> {
            CreationToolsUtil.addEdgeCreationTool(packageDescriptions, creationTool);
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
                this.getQueryBuilder().queryAllReachable(this.umlPackage.getPackageMerge()), packageDescriptions, packageDescriptions);
        ucdPackageMergeDescription.getStyle().setLineStyle(LineStyle.DASH);
        ucdPackageMergeDescription.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        EdgeTool creationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(ucdPackageMergeDescription, this.umlPackage.getPackage_PackageMerge());
        this.registerCallback(ucdPackageMergeDescription, () -> {
            CreationToolsUtil.addEdgeCreationTool(packageDescriptions, creationTool);
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
        EdgeTool creationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(ucdDependencyDescription, this.umlPackage.getPackage_PackagedElement());
        this.registerCallback(ucdDependencyDescription, () -> {
            CreationToolsUtil.addEdgeCreationTool(namedElementCollector, creationTool);
        });
        this.getViewBuilder().addDefaultReconnectionTools(ucdDependencyDescription);
    }

}
