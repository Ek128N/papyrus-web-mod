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

import org.eclipse.papyrus.web.application.representations.uml.UCDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.tools.checker.Checker;
import org.eclipse.papyrus.web.application.tools.checker.EdgeSourceGraphicalChecker;
import org.eclipse.papyrus.web.application.tools.test.ReconnectEdgeSourceTest;
import org.eclipse.papyrus.web.application.tools.usecase.utils.UCDCreationTool;
import org.eclipse.papyrus.web.application.tools.usecase.utils.UCDToolSections;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests reconnect edge source tools in the Use Case Diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class UCDReconnectEdgeSourceTest extends ReconnectEdgeSourceTest {

    public UCDReconnectEdgeSourceTest() {
        super(DEFAULT_DOCUMENT, UCDDiagramDescriptionBuilder.UCD_REP_NAME, UML.getModel());
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        this.applyNodeCreationTool(this.representationId, new UCDCreationTool(UCDToolSections.NODES, UML.getActor()));
        this.applyNodeCreationTool(this.representationId, new UCDCreationTool(UCDToolSections.NODES, UML.getUseCase()));
        IDiagramElement actor = this.findGraphicalElementByLabel("Actor1");
        IDiagramElement useCase = this.findGraphicalElementByLabel("UseCase1");
        this.applyEdgeCreationTool(actor.getId(), useCase.getId(), new UCDCreationTool(UCDToolSections.EDGES, UML.getAssociation()));
        this.applyNodeCreationTool(this.representationId, new UCDCreationTool(UCDToolSections.NODES, UML.getActor()));
    }

    @Override
    @AfterEach
    public void tearDown() {
        super.tearDown();
    }

    // @ParameterizedTest
    // @MethodSource("parameterProvider")
    @Test
    public void testReconnectEdgeSource() {
        Edge edge = this.getDiagram().getEdges().get(0);
        Checker graphicalChecker = new EdgeSourceGraphicalChecker(() -> this.findGraphicalElementByLabel("Actor2"));
        this.reconnectEdgeSource(edge.getId(), "Actor2", graphicalChecker);

    }

}
