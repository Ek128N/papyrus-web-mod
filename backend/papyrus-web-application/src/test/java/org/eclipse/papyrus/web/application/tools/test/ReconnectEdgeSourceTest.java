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
package org.eclipse.papyrus.web.application.tools.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.web.application.tools.checker.Checker;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.IDiagramElement;

/**
 * Utility class to help the definition of reconnect edge source tool tests.
 * <p>
 * Concrete reconnect edge source tool tests can extend this class and reuse
 * {@link #reconnectEdgeSource(String, String, Checker)} to invoke the reconnect edge source tool and check the result.
 * </p>
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class ReconnectEdgeSourceTest extends AbstractPapyrusWebTest {

    /**
     * Initializes the test with the provided {@code representationName} and {@code rootElementEClass}.
     *
     * @param documentName
     *            the name of the document to create
     * @param representationName
     *            the name of the representation to create
     * @param rootElementEClass
     *            the type of the root semantic element to create
     *
     * @see AbstractPapyrusWebTest#AbstractPapyrusWebTest(String, String, EClass)
     */
    public ReconnectEdgeSourceTest(String documentName, String representationName, EClass rootElementEClass) {
        super(documentName, representationName, rootElementEClass);
    }

    /**
     * Reconnects the source of the provided {@code edgeId} to {@code newSourceLabel}.
     *
     * @param edgeId
     *            the graphical identifier of the edge to reconnect the source of
     * @param newSourceLabel
     *            the label of the graphical element to reconnect the source to
     * @param checker
     *            the {@link Checker} to use to validate the operation
     */
    protected void reconnectEdgeSource(String edgeId, String newSourceLabel, Checker checker) {
        assertThat(checker).as("checker cannot be null").isNotNull();
        IDiagramElement newSourceElement = this.findGraphicalElementByLabel(newSourceLabel);
        this.applyReconnectEdgeSourceTool(edgeId, newSourceElement.getId());
        Edge edge = this.getDiagram().getEdges().get(0);
        checker.validateRepresentationElement(edge);
    }
}
