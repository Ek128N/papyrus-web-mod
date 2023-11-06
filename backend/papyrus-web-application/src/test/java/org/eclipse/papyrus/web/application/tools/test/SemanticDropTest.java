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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.web.application.tools.checker.Checker;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.uml2.uml.NamedElement;
import org.springframework.beans.factory.annotation.Autowired;

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

    private static final String DROPPED_ELEMENT_IS_NULL_ERROR = "droppedElementId cannot be null";

    private static final String CHECKER_IS_NULL_ERROR = "checker cannot be null";

    @Autowired
    protected IEditingContextPersistenceService persistenceService;

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
        assertThat(droppedElementId).as(DROPPED_ELEMENT_IS_NULL_ERROR).isNotNull();
        assertThat(checker).as(CHECKER_IS_NULL_ERROR).isNotNull();
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
        assertThat(droppedElementId).as(DROPPED_ELEMENT_IS_NULL_ERROR).isNotNull();
        assertThat(checker).as(CHECKER_IS_NULL_ERROR).isNotNull();
        Node parentGraphicalElement = (Node) this.findGraphicalElementByLabel(parentElementLabel);
        String targetElementId = parentGraphicalElement.getId();
        int parentChildCount = parentGraphicalElement.getChildNodes().size();
        List<String> droppedElementUUIDs = List.of(droppedElementId);
        this.applyDropOnDiagramTool(targetElementId, droppedElementUUIDs);
        Node parentNode = (Node) this.findGraphicalElementByLabel(parentElementLabel);
        checker.validateRepresentationElement(parentNode.getChildNodes().get(parentChildCount));
    }

    /**
     * Drops the provided {@code droppedElementId} on the provided {@code parentName}'s {@code compartmentMapping}
     * compartment.
     *
     * @param parentElementLabel
     *            the label of the graphical element to drop onto
     * @param compartmentMapping
     *            the mapping of the compartment to drop the element into
     * @param droppedElementId
     *            the semantic identifier of the element to drop
     * @param checker
     *            the {@link Checker} to use to validate the operation
     */
    protected void semanticDropOnContainerCompartment(String parentElementLabel, String compartmentMapping, String droppedElementId, Checker checker) {
        assertThat(parentElementLabel).as("parentElementLabel cannot be null").isNotNull();
        assertThat(droppedElementId).as(DROPPED_ELEMENT_IS_NULL_ERROR).isNotNull();
        assertThat(checker).as(CHECKER_IS_NULL_ERROR).isNotNull();
        Node parentCompartmentNode = this.getSubNode(parentElementLabel, compartmentMapping);
        String targetCompartmentId = parentCompartmentNode.getId();
        int compartmentChildCount = parentCompartmentNode.getChildNodes().size();
        List<String> droppedElementUUIDs = List.of(droppedElementId);
        this.applyDropOnDiagramTool(targetCompartmentId, droppedElementUUIDs);
        Node updatedParentCompartmentNode = this.getSubNode(parentElementLabel, compartmentMapping);
        checker.validateRepresentationElement(updatedParentCompartmentNode.getChildNodes().get(compartmentChildCount));
    }

    /**
     * Creates a semantic element of the given {@code type} in the given {@code parentElement}, with the given
     * {@code name}.
     * <p>
     * This method operates at the semantic level. It doesn't create a graphical element. The created element is
     * contained in {@code parentElement} through the {@code containmentReference} reference.
     * </p>
     * <p>
     * <b>Note:</b> this method doesn't set the name of the element if {@code type} isn't a sub-type of
     * {@link NamedElement}.
     * </p>
     * <p>
     * This method is typically used to create semantic elements to drop.
     * </p>
     *
     * @param parentElement
     *            the semantic element containing the created element
     * @param containmentReference
     *            the reference containing the created element
     * @param type
     *            the type of the created element
     * @param name
     *            the name of the created element
     * @return the created element
     */
    protected EObject createSemanticElement(EObject parentElement, EReference containmentReference, EClass type, String name) {
        String parentElementId = this.getObjectService().getId(parentElement);
        int numberOfChildren = ((List<?>) parentElement.eGet(containmentReference)).size();
        this.applyCreateChildTool(parentElementId, containmentReference, type);
        IEditingContext editingContext = this.getEditingContext();
        EObject updatedParentElement = (EObject) this.getObjectService().getObject(editingContext, parentElementId).get();
        EObject createdObject = (EObject) ((List<?>) updatedParentElement.eGet(containmentReference)).get(numberOfChildren);
        if (createdObject instanceof NamedElement namedElement) {
            namedElement.setName(name);
            this.persistenceService.persist(editingContext);
        }
        return createdObject;
    }
}
