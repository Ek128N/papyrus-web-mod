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
package org.eclipse.papyrus.web.application.representations;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.diagrams.Node;

/**
 * Object in charge of handling semantic drop on a web diagram.
 *
 * @author Arthur Daussy
 */
public interface IWebExternalSourceToRepresentationDropBehaviorProvider {

    /**
     * Handles a semantic drop event.
     *
     * @param droppedElement
     *            the dropped element
     * @param targetNode
     *            the target node or <code>null</code> if the drop occurred on the diagram
     */
    void handleSemanticDrop(EObject droppedElement, Node targetNode);

}
