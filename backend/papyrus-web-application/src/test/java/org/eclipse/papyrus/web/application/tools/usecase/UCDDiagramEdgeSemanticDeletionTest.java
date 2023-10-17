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

import java.util.stream.Stream;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.web.application.representations.uml.UCDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.tools.checker.CombinedChecker;
import org.eclipse.papyrus.web.application.tools.checker.DeletionGraphicalChecker;
import org.eclipse.papyrus.web.application.tools.checker.NodeSemanticDeletionSemanticChecker;
import org.eclipse.papyrus.web.application.tools.test.EdgeDeletionTest;
import org.eclipse.papyrus.web.application.tools.usecase.utils.UCDCreationTool;
import org.eclipse.papyrus.web.application.tools.usecase.utils.UCDToolSections;
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

    private static final String ACTOR1 = "Actor1";

    private static final String USE_CASE1 = "UseCase1";

    public UCDDiagramEdgeSemanticDeletionTest() {
        super(DEFAULT_DOCUMENT, UCDDiagramDescriptionBuilder.UCD_REP_NAME, UML.getModel());
    }

    private static Stream<Arguments> parameterProvider() {
        return Stream.of(//
                Arguments.of(ACTOR1, UML.getPackage_PackagedElement()), //
                Arguments.of(USE_CASE1, UML.getPackage_PackagedElement())//
        );
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        // Complete the model with new element and create representations on Diagram
        this.applyNodeCreationTool(this.representationId, new UCDCreationTool(UCDToolSections.NODES, UML.getActor()));
        this.applyNodeCreationTool(this.representationId, new UCDCreationTool(UCDToolSections.NODES, UML.getUseCase()));
        IDiagramElement actor = this.findGraphicalElementByLabel("Actor1");
        IDiagramElement useCase = this.findGraphicalElementByLabel("UseCase1");
        this.applyEdgeCreationTool(actor.getId(), useCase.getId(), new UCDCreationTool(UCDToolSections.EDGES, UML.getAssociation()));
    }

    @Override
    @AfterEach
    public void tearDown() {
        super.tearDown();
    }

    @ParameterizedTest
    @MethodSource("parameterProvider")
    public void testDeleteSemanticNode(String elementName, EReference containmentReference) {
        Edge edge = this.getDiagram().getEdges().get(0);
        DeletionGraphicalChecker graphicalChecker = new DeletionGraphicalChecker(() -> this.getDiagram(), null);
        NodeSemanticDeletionSemanticChecker semanticChecker = new NodeSemanticDeletionSemanticChecker(this.getObjectService(), () -> this.getEditingContext(), () -> this.getRootSemanticElement(),
                containmentReference);
        this.deleteSemanticEdge(edge, new CombinedChecker(graphicalChecker, semanticChecker));
    }

}
