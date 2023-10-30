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
package org.eclipse.papyrus.web.application.tools.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.web.application.representations.uml.UCDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.tools.checker.CombinedChecker;
import org.eclipse.papyrus.web.application.tools.checker.DeletionGraphicalChecker;
import org.eclipse.papyrus.web.application.tools.checker.NodeSemanticDeletionSemanticChecker;
import org.eclipse.papyrus.web.application.tools.test.EdgeDeletionTest;
import org.eclipse.papyrus.web.application.tools.usecase.utils.UCDCreationTool;
import org.eclipse.papyrus.web.application.tools.usecase.utils.UCDToolSections;
import org.eclipse.papyrus.web.application.tools.utils.CreationTool;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests semantic deletion edge tool at the root of the diagram in the Use Case Diagram.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class UCDDiagramEdgeSemanticDeletionTest extends EdgeDeletionTest {

    private static final String ACTIVITY_SOURCE = "ActivitySource";

    private static final String ACTIVITY_TARGET = "ActivityTarget";

    private static final String ACTOR_SOURCE = "ActorSource";

    private static final String ACTOR_TARGET = "ActorTarget";

    private static final String CLASS_SOURCE = "ClassSource";

    private static final String CLASS_TARGET = "ClassTarget";

    private static final String COMPONENT_SOURCE = "ComponentSource";

    private static final String COMPONENT_TARGET = "ComponentTarget";

    private static final String CONSTRAINT_SOURCE = "ConstraintSource";

    private static final String CONSTRAINT_TARGET = "ConstraintTarget";

    private static final String INTERACTION_SOURCE = "InteractionSource";

    private static final String INTERACTION_TARGET = "InteractionTarget";

    private static final String PACKAGE_SOURCE = "PackageSource";

    private static final String PACKAGE_TARGET = "PackageTarget";

    private static final String STATE_MACHINE_SOURCE = "StateMachineSource";

    private static final String STATE_MACHINE_TARGET = "StateMachineTarget";

    private static final String USE_CASE_SOURCE = "UseCaseSource";

    private static final String USE_CASE_TARGET = "UseCaseTarget";

    public UCDDiagramEdgeSemanticDeletionTest() {
        super(DEFAULT_DOCUMENT, UCDDiagramDescriptionBuilder.UCD_REP_NAME, UML.getModel());
    }

    private static Stream<Arguments> abstractionAndDependencyAndRealizationAndUsageParameters() {
        List<String> sources = List.of(ACTOR_SOURCE, PACKAGE_SOURCE, ACTIVITY_SOURCE, CLASS_SOURCE, COMPONENT_SOURCE, CONSTRAINT_SOURCE, INTERACTION_SOURCE, STATE_MACHINE_SOURCE, USE_CASE_SOURCE);
        List<String> targets = List.of(ACTOR_TARGET, PACKAGE_TARGET, ACTIVITY_TARGET, CLASS_TARGET, COMPONENT_TARGET, CONSTRAINT_TARGET, INTERACTION_TARGET, STATE_MACHINE_TARGET, USE_CASE_TARGET);
        return cartesianProduct(sources, targets);
    }

    private static Stream<Arguments> associationAndGeneralizationParameters() {
        List<String> sources = List.of(ACTOR_SOURCE, ACTIVITY_SOURCE, CLASS_SOURCE, COMPONENT_SOURCE, INTERACTION_SOURCE, STATE_MACHINE_SOURCE, USE_CASE_SOURCE);
        List<String> targets = List.of(ACTOR_TARGET, ACTIVITY_TARGET, CLASS_TARGET, COMPONENT_TARGET, INTERACTION_TARGET, STATE_MACHINE_TARGET, USE_CASE_TARGET);
        return cartesianProduct(sources, targets);
    }

    private static Stream<Arguments> extendAndIncludeParameters() {
        return Stream.of(Arguments.of(USE_CASE_SOURCE, USE_CASE_TARGET));
    }

    private static Stream<Arguments> packageImportAndPackageMergeParameters() {
        return Stream.of(Arguments.of(PACKAGE_SOURCE, PACKAGE_TARGET));
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        this.createDiagramSourceAndTargetNodes(new UCDCreationTool(UCDToolSections.SUBJECT, UML.getActivity()));
        this.createDiagramSourceAndTargetNodes(new UCDCreationTool(UCDToolSections.NODES, UML.getActor()));
        this.createDiagramSourceAndTargetNodes(new UCDCreationTool(UCDToolSections.SUBJECT, UML.getClass_()));
        this.createDiagramSourceAndTargetNodes(new UCDCreationTool(UCDToolSections.SUBJECT, UML.getComponent()));
        this.createDiagramSourceAndTargetNodes(new UCDCreationTool(UCDToolSections.NODES, UML.getConstraint()));
        this.createDiagramSourceAndTargetNodes(new UCDCreationTool(UCDToolSections.SUBJECT, UML.getInteraction()));
        this.createDiagramSourceAndTargetNodes(new UCDCreationTool(UCDToolSections.NODES, UML.getPackage()));
        this.createDiagramSourceAndTargetNodes(new UCDCreationTool(UCDToolSections.SUBJECT, UML.getStateMachine()));
        this.createDiagramSourceAndTargetNodes(new UCDCreationTool(UCDToolSections.NODES, UML.getUseCase()));
    }

    @Override
    @AfterEach
    public void tearDown() {
        super.tearDown();
    }

    @ParameterizedTest
    @MethodSource("abstractionAndDependencyAndRealizationAndUsageParameters")
    public void testDeleteAbstraction(String sourceElementLabel, String targetElementLabel) {
        // Abstraction is contained in its source if it is a package or component
        this.createEdge(sourceElementLabel, targetElementLabel, new UCDCreationTool(UCDToolSections.EDGES, UML.getAbstraction()));
        Edge edge = this.getDiagram().getEdges().get(0);
        if (sourceElementLabel.equals(COMPONENT_SOURCE)) {
            this.testDeleteEdge(edge, sourceElementLabel, UML.getComponent_PackagedElement());
        } else if (sourceElementLabel.equals(PACKAGE_SOURCE)) {
            this.testDeleteEdge(edge, sourceElementLabel, UML.getPackage_PackagedElement());
        } else {
            this.testDeleteEdge(edge);
        }
    }

    @ParameterizedTest
    @MethodSource("associationAndGeneralizationParameters")
    public void testDeleteAssociation(String sourceElementLabel, String targetElementLabel) {
        this.createEdge(sourceElementLabel, targetElementLabel, new UCDCreationTool(UCDToolSections.EDGES, UML.getAssociation()));
        Edge edge = this.getDiagram().getEdges().get(0);
        this.testDeleteEdge(edge);
    }

    @ParameterizedTest
    @MethodSource("abstractionAndDependencyAndRealizationAndUsageParameters")
    public void testDeleteDependency(String sourceElementLabel, String targetElementLabel) {
        // Dependency is contained in its source if it is a package or component
        this.createEdge(sourceElementLabel, targetElementLabel, new UCDCreationTool(UCDToolSections.EDGES, UML.getDependency()));
        Edge edge = this.getDiagram().getEdges().get(0);
        if (sourceElementLabel.equals(COMPONENT_SOURCE)) {
            this.testDeleteEdge(edge, sourceElementLabel, UML.getComponent_PackagedElement());
        } else if (sourceElementLabel.equals(PACKAGE_SOURCE)) {
            this.testDeleteEdge(edge, sourceElementLabel, UML.getPackage_PackagedElement());
        } else {
            this.testDeleteEdge(edge);
        }
    }

    @ParameterizedTest
    @MethodSource("extendAndIncludeParameters")
    public void testDeleteExtend(String sourceElementLabel, String targetElementLabel) {
        this.createEdge(sourceElementLabel, targetElementLabel, new UCDCreationTool(UCDToolSections.EDGES, UML.getExtend()));
        Edge edge = this.getDiagram().getEdges().get(0);
        this.testDeleteEdge(edge, sourceElementLabel, UML.getUseCase_Extend());
    }

    @ParameterizedTest
    @MethodSource("associationAndGeneralizationParameters")
    public void testDeleteGeneralization(String sourceElementLabel, String targetElementLabel) {
        // Dependency is contained in its source if it is a package or component
        this.createEdge(sourceElementLabel, targetElementLabel, new UCDCreationTool(UCDToolSections.EDGES, UML.getGeneralization()));
        Edge edge = this.getDiagram().getEdges().get(0);
        this.testDeleteEdge(edge, sourceElementLabel, UML.getClassifier_Generalization());
    }

    @ParameterizedTest
    @MethodSource("extendAndIncludeParameters")
    public void testDeleteInclude(String sourceElementLabel, String targetElementLabel) {
        this.createEdge(sourceElementLabel, targetElementLabel, new UCDCreationTool(UCDToolSections.EDGES, UML.getInclude()));
        Edge edge = this.getDiagram().getEdges().get(0);
        this.testDeleteEdge(edge, sourceElementLabel, UML.getUseCase_Include());
    }

    @ParameterizedTest
    @MethodSource("abstractionAndDependencyAndRealizationAndUsageParameters")
    public void testDeleteRealization(String sourceElementLabel, String targetElementLabel) {
        // Dependency is contained in its source if it is a package or component
        this.createEdge(sourceElementLabel, targetElementLabel, new UCDCreationTool(UCDToolSections.EDGES, UML.getRealization()));
        Edge edge = this.getDiagram().getEdges().get(0);
        if (sourceElementLabel.equals(COMPONENT_SOURCE)) {
            this.testDeleteEdge(edge, sourceElementLabel, UML.getComponent_PackagedElement());
        } else if (sourceElementLabel.equals(PACKAGE_SOURCE)) {
            this.testDeleteEdge(edge, sourceElementLabel, UML.getPackage_PackagedElement());
        } else {
            this.testDeleteEdge(edge);
        }
    }

    @ParameterizedTest
    @MethodSource("abstractionAndDependencyAndRealizationAndUsageParameters")
    public void testDeleteUsage(String sourceElementLabel, String targetElementLabel) {
        // Dependency is contained in its source if it is a package or component
        this.createEdge(sourceElementLabel, targetElementLabel, new UCDCreationTool(UCDToolSections.EDGES, UML.getUsage()));
        Edge edge = this.getDiagram().getEdges().get(0);
        if (sourceElementLabel.equals(COMPONENT_SOURCE)) {
            this.testDeleteEdge(edge, sourceElementLabel, UML.getComponent_PackagedElement());
        } else if (sourceElementLabel.equals(PACKAGE_SOURCE)) {
            this.testDeleteEdge(edge, sourceElementLabel, UML.getPackage_PackagedElement());
        } else {
            this.testDeleteEdge(edge);
        }
    }

    @ParameterizedTest
    @MethodSource("packageImportAndPackageMergeParameters")
    public void testDeletePackageImport(String sourceElementLabel, String targetElementLabel) {
        this.createEdge(sourceElementLabel, targetElementLabel, new UCDCreationTool(UCDToolSections.EDGES, UML.getPackageImport()));
        Edge edge = this.getDiagram().getEdges().get(0);
        this.testDeleteEdge(edge, sourceElementLabel, UML.getNamespace_PackageImport());
    }

    @ParameterizedTest
    @MethodSource("packageImportAndPackageMergeParameters")
    public void testDeletePackageMerge(String sourceElementLabel, String targetElementLabel) {
        this.createEdge(sourceElementLabel, targetElementLabel, new UCDCreationTool(UCDToolSections.EDGES, UML.getPackageMerge()));
        Edge edge = this.getDiagram().getEdges().get(0);
        this.testDeleteEdge(edge, sourceElementLabel, UML.getPackage_PackageMerge());
    }

    private void testDeleteEdge(Edge edge) {
        this.testDeleteEdge(edge, null, UML.getPackage_PackagedElement());
    }

    private void testDeleteEdge(Edge edge, String oldOwnerLabel, EReference oldContainmentReference) {
        final Supplier<EObject> oldOwnerSupplier;
        if (oldOwnerLabel == null) {
            oldOwnerSupplier = this::getRootSemanticElement;
        } else {
            oldOwnerSupplier = () -> this.findSemanticElementByName(oldOwnerLabel);
        }
        DeletionGraphicalChecker graphicalChecker = new DeletionGraphicalChecker(this::getDiagram, null);
        NodeSemanticDeletionSemanticChecker semanticChecker = new NodeSemanticDeletionSemanticChecker(this.getObjectService(), this::getEditingContext, oldOwnerSupplier, oldContainmentReference);
        this.deleteSemanticEdge(edge, new CombinedChecker(graphicalChecker, semanticChecker));
    }

    private void createEdge(String sourceLabel, String targetLabel, CreationTool creationTool) {
        IDiagramElement source = this.findGraphicalElementByLabel(sourceLabel);
        IDiagramElement target = this.findGraphicalElementByLabel(targetLabel);
        this.applyEdgeCreationTool(source.getId(), target.getId(), creationTool);
        assertThat(this.getDiagram().getEdges()).as("Diagram doesn't contain the created edge").hasSize(1);
    }
}
