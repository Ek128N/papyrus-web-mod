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

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.web.application.tools.checker.Checker;
import org.eclipse.sirius.components.diagrams.Node;

/**
 * Utility class to help the definition of semantic drop tool tests.
 * <p>
 * Concrete semantic drop tool tests can extend this class and reuse
 * {@link #semanticDropOnContainer(String, String, Checker)} to invoke the semantic drop tool and check the result.
 * </p>
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class SemanticDropTest extends AbstractPapyrusWebTest {

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
    public SemanticDropTest(String documentName, String representationName, EClass rootElementEClass) {
        super(documentName, representationName, rootElementEClass);
    }

    /**
     * Drops the provided {@code droppedElementId} on the diagram.
     *
     * @param droppedElementId
     *            the semantic identifier of the element to drop on the diagram
     * @param checker
     *            the {@link Checker} to use to validate the operation
     */
    protected void semanticDropOnDiagram(String droppedElementId, Checker checker) {
        assertThat(droppedElementId).as("droppedElementId cannot be null").isNotNull();
        assertThat(checker).as("checker cannot be null").isNotNull();
        String targetElementId = this.getDiagram().getId();
        List<String> droppedElementUUIDs = List.of(droppedElementId);
        int diagramChildCount = this.getDiagram().getNodes().size();
        this.applyDropOnDiagramTool(targetElementId, droppedElementUUIDs);
        checker.validateRepresentationElement(this.getDiagram().getNodes().get(diagramChildCount));
    }

    /**
     * Drops the provided {@code droppedElementId} on the provided {@code parentElementLabel}.
     *
     * @param parentElementLabel
     *            the label of the graphical element to drop onto
     * @param droppedElementId
     *            the semantic identifier of the element to drop
     * @param checker
     *            the {@link Checker} to use to validate the operation
     */
    protected void semanticDropOnContainer(String parentElementLabel, String droppedElementId, Checker checker) {
        assertThat(parentElementLabel).as("parentElementLabel cannot be null").isNotNull();
        assertThat(droppedElementId).as("droppedElementId cannot be null").isNotNull();
        assertThat(checker).as("checker cannot be null").isNotNull();
        Node parentGraphicalElement = (Node) this.findGraphicalElementByLabel(parentElementLabel);
        String targetElementId = parentGraphicalElement.getId();
        int parentChildCount = parentGraphicalElement.getChildNodes().size();
        List<String> droppedElementUUIDs = List.of(droppedElementId);
        this.applyDropOnDiagramTool(targetElementId, droppedElementUUIDs);
        Node parentNode = (Node) this.findGraphicalElementByLabel(parentElementLabel);
        checker.validateRepresentationElement(parentNode.getChildNodes().get(parentChildCount));
    }
}
