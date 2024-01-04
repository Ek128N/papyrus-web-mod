/*******************************************************************************
 * Copyright (c) 2022, 2024 CEA LIST, Obeo.
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
 *******************************************************************************/
package org.eclipse.papyrus.web.sirius.contributions;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.papyrus.web.sirius.contributions.query.NodeMatcher;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.view.diagram.NodeDescription;

/**
 * Helper used to navigate inside a diagram and/or to its description.
 *
 * @author Arthur Daussy
 */
public class DiagramNavigator {

    private final IDiagramNavigationService diagramNavigationService;

    private final Diagram diagram;

    private final Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> capturedNodeDescriptions;

    public DiagramNavigator(IDiagramNavigationService diagramNavigationService, Diagram diagram,
            Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> capturedNodeDescriptions) {
        super();
        this.diagramNavigationService = diagramNavigationService;
        this.diagram = diagram;
        this.capturedNodeDescriptions = capturedNodeDescriptions;
    }

    public Diagram getDiagram() {
        return this.diagram;
    }

    public Optional<Node> getParentNode(Node selectedNode) {
        Optional<Object> parent = this.diagramNavigationService.getParent(this.diagram, selectedNode);
        return parent.filter(p -> p instanceof Node).map(p -> (Node) p);
    }

    public Optional<NodeDescription> getDescription(Node node) {
        return Optional.ofNullable(node.getDescriptionId())//
                .flatMap(id -> this.capturedNodeDescriptions.entrySet().stream()//
                        .filter(e -> id.equals(e.getValue().getId()))//
                        .map(e -> e.getKey())//
                        .findFirst());
    }

    public List<Node> getAncestorNodes(Node node) {
        return this.diagramNavigationService.getAncestorNodes(this.diagram, node);
    }

    public List<Node> getMatchingNodes(IEditingContext editingContext, NodeMatcher matcher) {
        return this.diagramNavigationService.getMatchingNodes(this.diagram, editingContext, matcher);
    }

    public List<Node> getMatchingNodesIn(Node parentNode, IEditingContext editingContext, NodeMatcher matcher) {
        return this.diagramNavigationService.getMatchingNodesIn(parentNode, this.diagram, editingContext, matcher);
    }

}
