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

import org.eclipse.papyrus.web.application.representations.uml.UCDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.tools.checker.CombinedChecker;
import org.eclipse.papyrus.web.application.tools.checker.EdgeCreationGraphicalChecker;
import org.eclipse.papyrus.web.application.tools.checker.EdgeCreationSemanticChecker;
import org.eclipse.papyrus.web.application.tools.test.EdgeCreationTest;
import org.eclipse.papyrus.web.application.tools.usecase.utils.UCDCreationTool;
import org.eclipse.papyrus.web.application.tools.usecase.utils.UCDMappingTypes;
import org.eclipse.papyrus.web.application.tools.usecase.utils.UCDToolSections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests edge creation tools in the Use Case Diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class UCDEdgeCreationTest extends EdgeCreationTest {

    private static final String ACTOR1 = "Actor1";

    private static final String ACTOR2 = "Actor2";

    private static final String USE_CASE1 = "UseCase1";

    private static final String USE_CASE2 = "UseCase2";

    public UCDEdgeCreationTest() {
        super(DEFAULT_DOCUMENT, UCDDiagramDescriptionBuilder.UCD_REP_NAME, UML.getModel());
    }

    private static Stream<Arguments> associationParameterProvider() {
        return Stream.of(//
                Arguments.of(ACTOR1, ACTOR2), //
                Arguments.of(ACTOR1, USE_CASE2), //
                Arguments.of(USE_CASE1, USE_CASE2)//
        );
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        this.applyNodeCreationTool(this.representationId, new UCDCreationTool(UCDToolSections.NODES, UML.getActor()));
        this.applyNodeCreationTool(this.representationId, new UCDCreationTool(UCDToolSections.NODES, UML.getActor()));
        this.applyNodeCreationTool(this.representationId, new UCDCreationTool(UCDToolSections.NODES, UML.getUseCase()));
        this.applyNodeCreationTool(this.representationId, new UCDCreationTool(UCDToolSections.NODES, UML.getUseCase()));
    }

    @Override
    @AfterEach
    public void tearDown() {
        super.tearDown();
    }

    @ParameterizedTest
    @MethodSource("associationParameterProvider")
    public void testCreateAssociation(String sourceElementLabel, String targetElementLabel) {
        EdgeCreationGraphicalChecker graphicalChecker = new EdgeCreationGraphicalChecker(() -> this.getDiagram(), null, UCDMappingTypes.getMappingType(UML.getAssociation()), this.getCapturedEdges());
        EdgeCreationSemanticChecker semanticChecker = new EdgeCreationSemanticChecker(this.getObjectService(), () -> this.getEditingContext(), UML.getAssociation(),
                () -> this.getRootSemanticElement(), UML.getPackage_PackagedElement());
        this.createEdge(sourceElementLabel, targetElementLabel, new UCDCreationTool(UCDToolSections.EDGES, UML.getAssociation()), new CombinedChecker(graphicalChecker, semanticChecker));
    }

}
