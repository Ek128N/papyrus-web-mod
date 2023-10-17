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
import org.eclipse.papyrus.web.application.tools.utils.CreationTool;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.Node;

/**
 * Utility class to help the definition of node creation tool tests.
 * <p>
 * Concrete node creation tool tests can extend this class and reuse {@link #createNode(String, String, Checker)} to
 * invoke the node creation tool and check the result.
 * </p>
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class NodeCreationTest extends AbstractPapyrusWebTest {

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
    public NodeCreationTest(String documentName, String representationName, EClass rootElementEClass) {
        super(documentName, representationName, rootElementEClass);
    }

    /**
     * Creates a node on the diagram with the provided {@code nodeCreationTool}.
     *
     * @param nodeCreationTool
     *            the {@link CreationTool} specifying the tool section and name in the palette
     * @param checker
     *            the {@link Checker} to use to validate the operation
     */
    protected void createNodeOnDiagram(CreationTool nodeCreationTool, Checker checker) {
        assertThat(checker).as("checker cannot be null").isNotNull();
        this.applyNodeCreationTool(this.representationId, nodeCreationTool);
        // Reload the diagram to ensure it contains the create element
        Diagram diagram = this.getDiagram();
        Node createdNode = diagram.getNodes().get(0);
        checker.validateRepresentationElement(createdNode);
    }

    /**
     * Creates a node in {@code parentName} with the provided {@nodeCreationTool}.
     *
     * @param parentName
     *            the label of the graphical container of the node to create
     * @param edgeCreationTool
     *            the {@link CreationTool} specifying the tool section and name in the palette
     * @param checker
     *            the {@link Checker} to use to validate the operation
     */
    protected void createNodeOnContainer(String parentName, CreationTool nodeCreationTool, Checker checker) {
        assertThat(checker).as("checker cannot be null").isNotNull();
        IDiagramElement diagramElement = this.findGraphicalElementByLabel(parentName);
        assertThat(diagramElement).as("Node container should be a Node").isInstanceOf(Node.class);
        assertThat(diagramElement).as("Cannot find Node container with label " + parentName).isNotNull();
        Node parentNode = (Node) diagramElement;
        int initialNumberOfChild = parentNode.getChildNodes().size();
        this.applyNodeCreationTool(parentNode.getId(), nodeCreationTool);
        // Reload the parent element to ensure it contains the created element
        IDiagramElement updatedDiagramElement = this.findGraphicalElementByLabel(parentName);
        assertThat(updatedDiagramElement).as("Node container should be a Node").isInstanceOf(Node.class);
        assertThat(updatedDiagramElement).as("Cannot find Node container with label " + parentName).isNotNull();
        // We assume the created element is always added at the end of the getChildNodes list
        Node createdNode = ((Node) updatedDiagramElement).getChildNodes().get(initialNumberOfChild);
        checker.validateRepresentationElement(createdNode);
    }
}
