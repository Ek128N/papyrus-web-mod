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
import org.eclipse.sirius.components.diagrams.IDiagramElement;

/**
 * Utility class to help the definition of edge deletion tool tests.
 * <p>
 * Concrete edge deletion tool tests can extend this class and reuse
 * {@link #deleteSemanticEdge(IDiagramElement, Checker)} to invoke the edge deletion tool and check the result.
 * </p>
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class EdgeDeletionTest extends AbstractPapyrusWebTest {

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
    public EdgeDeletionTest(String documentName, String representationName, EClass rootElementEClass) {
        super(documentName, representationName, rootElementEClass);
    }

    /**
     * Delete semantically an edge {@code elementToDelete} from the diagram or a node container.
     *
     * @param elementToDelete
     *            element to delete
     * @param checker
     *            the {@link Checker} to use to validate the operation
     */
    protected void deleteSemanticEdge(IDiagramElement elementToDelete, Checker checker) {
        assertThat(checker).as("checker cannot be null").isNotNull();
        this.applyEdgeSemanticDeletionTool(elementToDelete.getId());
        checker.validateRepresentationElement(elementToDelete);
    }
}
