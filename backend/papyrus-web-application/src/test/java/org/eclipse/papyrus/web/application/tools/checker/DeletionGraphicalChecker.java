/*****************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.tools.checker;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.Node;

/**
 * Utility class to check that a graphical element has been removed from the diagram.
 * <p>
 * It also validates that the diagram contains the appropriate number of elements after the creation.
 * </p>
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class DeletionGraphicalChecker implements Checker {

    protected Supplier<Diagram> diagramSupplier;

    protected Supplier<IDiagramElement> graphicalOwnerSupplier;

    protected String mappingType;

    protected int diagramAllChildCount;

    protected int diagramDirectChildCount;

    protected int graphicalOwnerChildCount;

    /**
     * Initializes the checker with the provided parameters.
     *
     * @param diagramSupplier
     *            the diagram which contain the owner of the node to remove
     * @param graphicalOwnerSupplier
     *            owner node of the element to remove
     */
    public DeletionGraphicalChecker(Supplier<Diagram> diagramSupplier, Supplier<IDiagramElement> graphicalOwnerSupplier) {
        this.diagramSupplier = diagramSupplier;
        this.graphicalOwnerSupplier = graphicalOwnerSupplier;
        Diagram diagram = this.diagramSupplier.get();
        this.diagramAllChildCount = this.getDiagramSize(diagram);
        this.diagramDirectChildCount = diagram.getNodes().size() + diagram.getEdges().size();
        this.graphicalOwnerChildCount = this.getGraphicalElementChildCount(graphicalOwnerSupplier).orElse(this.diagramDirectChildCount);
    }

    @Override
    public void validateRepresentationElement(IDiagramElement elementToRemove) {

        // 1. check element has been removed from the diagram
        this.checkElementHasBeenRemovedFromDiagram(elementToRemove);

        // 2. check the number of element on diagram
        this.checkNumberOfRemovedElement();

    }

    protected void checkElementHasBeenRemovedFromDiagram(IDiagramElement element) {
        List<IDiagramElement> removedElement = null;
        if (this.graphicalOwnerSupplier != null && this.graphicalOwnerSupplier.get() instanceof Node graphicalParentNode) {
            removedElement = this.findGraphicalElementFromContainer(graphicalParentNode, element);
        } else {
            removedElement = this.findGraphicalElementFromDiagram(element);
        }
        assertThat(removedElement.isEmpty());
    }

    protected void checkNumberOfRemovedElement() {
        Diagram diagram = this.diagramSupplier.get();
        int newDiagramAllNodesCount = this.getDiagramSize(diagram);

        assertThat(newDiagramAllNodesCount).as("The diagram should contain " + this.getExpectedNumerOfDeletedElements() + " less element")
                .isEqualTo(this.diagramAllChildCount - this.getExpectedNumerOfDeletedElements());

        int newGraphicalOwnerChildCount = this.getGraphicalElementChildCount(this.graphicalOwnerSupplier)
                .orElse(this.diagramSupplier.get().getNodes().size() + this.diagramSupplier.get().getEdges().size());
        assertThat(newGraphicalOwnerChildCount).as("The graphical container should contain " + this.getExpectedNumberOfDeletedGraphicalOwnerDirectChildren() + " less element")
                .isEqualTo(this.graphicalOwnerChildCount - this.getExpectedNumberOfDeletedGraphicalOwnerDirectChildren());
    }

    /**
     * The expected number of deleted elements in the diagram.
     * <p>
     * This method is used by {@link #checkNumberOfRemovedElement()} to ensure that the correct number of elements have
     * been deleted in the diagram. The default implementation of this method returns {@code 1}.
     * </p>
     *
     * @return the expected number of deleted elements in the diagram
     */
    protected int getExpectedNumerOfDeletedElements() {
        return 1;
    }

    /**
     * The expected number of deleted element in the checked graphical owner.
     * <p>
     * This method is used by {@link #checkNumberOfRemovedElement()} to ensure that the correct number of elements have
     * been deleted in the checked graphical parent. The default implementation of this method returns {@code 1}.
     * </p>
     * <p>
     * This method may return a number smaller than {@link #getExpectedNumerOfDeletedElements()} if some elements aren't
     * deleted in the graphical owner.
     * </p>
     *
     * @return the expected number of deleted element in the checked graphical parent
     */
    protected int getExpectedNumberOfDeletedGraphicalOwnerDirectChildren() {
        return 1;
    }

    private int getDiagramSize(Diagram diagram) {
        int result = diagram.getEdges().size();
        for (Node node : diagram.getNodes()) {
            result += this.getDiagramSize(node);
        }
        return result;
    }

    private int getDiagramSize(Node node) {
        int result = 1;
        for (Node subNode : node.getChildNodes()) {
            result += this.getDiagramSize(subNode);
        }
        for (Node borderNode : node.getBorderNodes()) {
            result += this.getDiagramSize(borderNode);
        }
        return result;
    }

    private Optional<Integer> getGraphicalElementChildCount(Supplier<IDiagramElement> elementSupplier) {
        Optional<Integer> result = Optional.empty();
        if (elementSupplier != null && elementSupplier.get() instanceof Node graphicalElement) {
            result = Optional.of(graphicalElement.getChildNodes().size() + graphicalElement.getBorderNodes().size());
        }
        return result;
    }

    private List<IDiagramElement> findGraphicalElementFromDiagram(IDiagramElement removedElement) {
        Diagram diagram = this.diagramSupplier.get();
        List<IDiagramElement> result = new ArrayList<>();
        for (Node node : diagram.getNodes()) {
            result.addAll(this.findGraphicalElementFromContainer(node, removedElement));
        }
        for (Edge edge : diagram.getEdges()) {
            // Compare IDs instead of objects, the edge may have been reloaded and can be a different instance.
            if (Objects.equals(edge.getId(), removedElement.getId())) {
                result.add(edge);
            }
        }
        assertThat(result).as("The graphical element  should be removed.").hasSize(0);
        return result;

    }

    private List<IDiagramElement> findGraphicalElementFromContainer(Node node, IDiagramElement removedElement) {
        List<IDiagramElement> result = new ArrayList<>();
        // Compare IDs instead of objects, the node may have been reloaded and can be a different instance.
        if (Objects.equals(node.getId(), removedElement.getId())) {
            result.add(node);
        }
        for (Node childNode : node.getChildNodes()) {
            result.addAll(this.findGraphicalElementFromContainer(childNode, removedElement));
        }
        return result;
    }

}
