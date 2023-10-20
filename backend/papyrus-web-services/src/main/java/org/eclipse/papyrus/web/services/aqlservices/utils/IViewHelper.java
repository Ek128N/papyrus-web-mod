/*****************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.services.aqlservices.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.view.diagram.NodeDescription;

/**
 * Helper used to manage element on a diagram (creation, deletion...).
 *
 * @author Arthur Daussy
 */
public interface IViewHelper {

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
     * Creates a child {@code mappingName} view representing {@code self} in the provided {@code selectedNode}.
     *
     * @param self
     *            the semantic element to represent
     * @param selectedNode
     *            the selected node
     * @param mappingName
     *            the name of the mapping to use to create the view
     * @return {@code true} if a creation request has been made, {@code false} otherwise
     */
    boolean createChildView(EObject self, org.eclipse.sirius.components.diagrams.Node selectedNode, String mappingName);

    /**
     * Creates a view on root of the diagram.
     *
     * @param self
     *            the semantic of the new child
     * @return <code>true</code> if a creation request has been made, <code>false</code> otherwise
     */
    boolean createRootView(EObject self);

    /**
     * Creates a {@code mappingName} view on the root of the diagram.
     *
     * @param self
     *            the semantic element to represent
     * @param mappingName
     *            the name of the mapping to use to create the view
     * @return {@code true} if a creation request has been made, {@code false} otherwise
     */
    boolean createRootView(EObject self, String mappingName);

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
     * Creates a fake node to simulate a node representing a given {@code semanticElement} that will be created in the
     * future.
     * <p>
     * This method is mainly used to add the ability to create multiple nested views for new unsynchronized node
     * descriptions. The fake node is created with the identifier of the future unsynchronized node to create. Despite
     * of createView method call, a created request is created instead of the creation of the node itself. In this way,
     * user can create views in a new one. The specifier should not try to do anything else.
     * </p>
     *
     * @param semanticElement
     *            the semantic of the new child
     * @param selectedNode
     *            the selected node (future parent)
     * @return the fake node represented the future created node
     */
    Node createFakeNode(EObject semanticElement, Node selectedNode);

    /**
     * Deletes the view of given {@link Node}.
     *
     * @param node
     *            the graphical node to delete
     * @return <code>true</code> if a deletion request has been made, <code>false</code> otherwise
     */
    boolean deleteView(Node node);

    /**
     * NoOp implementation.
     *
     * @author Arthur Daussy
     */
    class NoOp implements IViewHelper {

        @Override
        public boolean createChildView(EObject self, Node selectedNode) {
            return false;
        }

        @Override
        public boolean createChildView(EObject self, Node selectedNode, String mappingName) {
            return false;
        }

        @Override
        public boolean createRootView(EObject self) {
            return false;
        }

        @Override
        public boolean createRootView(EObject self, String mappingName) {
            return false;
        }

        @Override
        public boolean createView(EObject semanticElement, Node selectedNode, NodeDescription newViewDescription) {
            return false;
        }

        @Override
        public boolean deleteView(Node node) {
            return false;
        }

        @Override
        public Node createFakeNode(EObject semanticElement, Node optionalParentNode) {
            return null;
        }

    }

}
