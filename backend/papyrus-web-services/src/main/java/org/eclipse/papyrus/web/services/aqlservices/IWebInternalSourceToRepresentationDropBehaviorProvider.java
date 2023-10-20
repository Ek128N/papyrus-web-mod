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
package org.eclipse.papyrus.web.services.aqlservices;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.diagrams.Node;

/**
 * Object in charge of handling graphical drop on a web diagram.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public interface IWebInternalSourceToRepresentationDropBehaviorProvider {

    /**
     * Handles a graphical drop event.
     *
     * @param droppedElement
     *            the semantic element to drop
     * @param targetElement
     *            the semantic target of the dropped element
     * @param droppedNode
     *            the node to drop
     * @param targetNode
     *            the target node or <code>null</code> if the drop occurred on the diagram
     */
    void handleGraphicalDrop(EObject droppedElement, EObject targetElement, Node droppedNode, Node targetNode);

}
