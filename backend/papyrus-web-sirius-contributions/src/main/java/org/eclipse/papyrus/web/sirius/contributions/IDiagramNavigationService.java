/*******************************************************************************
 * Copyright (c) 2022 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.sirius.contributions;

import java.util.List;
import java.util.Optional;

import org.eclipse.papyrus.web.sirius.contributions.query.NodeMatcher;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.view.emf.IJavaServiceProvider;

/**
 * Provides useful APIs for studios which need tools that must introspect the current diagram's structure and its
 * description. It can be injected into a Java service class returned from a {@link IJavaServiceProvider}. All the
 * arguments needed to invoke the methods in this service must be available from the variables available to a studio's
 * render expressions and tools.
 *
 * @author pcdavid
 */
public interface IDiagramNavigationService {

    Optional<Object> getParent(Diagram diagram, Node node);

    List<Node> getAncestorNodes(Diagram diagram, Node node);

    List<Node> getMatchingNodes(Diagram diagram, IEditingContext editingContext, NodeMatcher matcher);

    List<Node> getMatchingNodesIn(Node parentNode, Diagram diagram, IEditingContext editingContext, NodeMatcher matcher);

    /**
     * Empty implementation that can be used or extended for testing.
     *
     * @author pcdavid
     */
    class NoOp implements IDiagramNavigationService {

        @Override
        public Optional<Object> getParent(Diagram diagram, Node node) {
            return null;
        }

        @Override
        public List<Node> getAncestorNodes(Diagram diagram, Node node) {
            return List.of();
        }

        @Override
        public List<Node> getMatchingNodes(Diagram diagram, IEditingContext editingContext, NodeMatcher matcher) {
            return List.of();
        }

        @Override
        public List<Node> getMatchingNodesIn(Node parentNode, Diagram diagram, IEditingContext editingContext, NodeMatcher matcher) {
            return List.of();
        }

    }

}
