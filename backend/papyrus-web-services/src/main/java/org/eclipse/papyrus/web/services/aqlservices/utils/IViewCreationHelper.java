/*******************************************************************************
 * Copyright (c) 2022, 2023 CEA, Obeo
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.services.aqlservices.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.view.diagram.NodeDescription;

/**
 * Helper used to create element on a diagram.
 *
 * @author Arthur Daussy
 */
public interface IViewCreationHelper {

    /**
     * Creates a child view on the selected node. The type of node is deduced from the representation description.
     *
     * @param self
     *            the semantic of the new child
     * @param selectedNode
     *            the selected node (future parent)
     * @return <code>true</code> if a creation request has been made, <code>false</code> otherwise
     */
    boolean createChildView(EObject self, org.eclipse.sirius.components.diagrams.Node selectedNode);

    /**
     * Creates a view on root of the diagram.
     *
     * @param self
     *            the semantic of the new child
     * @return <code>true</code> if a creation request has been made, <code>false</code> otherwise
     */
    boolean createRootView(EObject self);

    /**
     * Creates a child view on the selected node.
     *
     * @param semanticElement
     *            the semantic of the new child
     * @param selectedNode
     *            the selected node (future parent)
     * @param newViewDescription
     *            the description of the node to create
     * @return <code>true</code> if a creation request has been made, <code>false</code> otherwise
     */
    boolean createView(EObject semanticElement, Node selectedNode, NodeDescription newViewDescription);

    /**
     * NoOp implementation.
     *
     * @author Arthur Daussy
     */
    class NoOp implements IViewCreationHelper {

        @Override
        public boolean createChildView(EObject self, Node selectedNode) {
            return false;
        }

        @Override
        public boolean createRootView(EObject self) {
            return false;
        }

        @Override
        public boolean createView(EObject semanticElement, Node selectedNode, NodeDescription newViewDescription) {
            return false;
        }

    }

}
