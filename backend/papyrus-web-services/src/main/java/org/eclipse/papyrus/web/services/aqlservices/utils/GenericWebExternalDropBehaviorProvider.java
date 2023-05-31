/*******************************************************************************
 * Copyright (c) 2022 CEA, Obeo.
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
package org.eclipse.papyrus.web.services.aqlservices.utils;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.web.services.aqlservices.IWebExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.web.sirius.contributions.DiagramNavigator;

/**
 * Default implementation of a {@link IWebExternalSourceToRepresentationDropBehaviorProvider} that created a view of the
 * dropped element if the Diagram Description model allows it.
 *
 * @author Arthur Daussy
 */
public class GenericWebExternalDropBehaviorProvider implements IWebExternalSourceToRepresentationDropBehaviorProvider {

    private final IViewCreationHelper viewHelper;

    private DiagramNavigator diagramNavigator;

    public GenericWebExternalDropBehaviorProvider(IViewCreationHelper viewHelper, DiagramNavigator diagramNavigator) {
        this.diagramNavigator = diagramNavigator;
        this.viewHelper = Objects.requireNonNull(viewHelper);
    }

    /**
     * Handle a drop event.
     *
     * @param droppedElement
     *            the dropped element
     * @param targetNode
     *            the target node or <code>null</code> if the drop occurred on the diagram
     */
    @Override
    public void handleDrop(EObject droppedElement, org.eclipse.sirius.components.diagrams.Node targetNode) {
        if (targetNode != null) {
            new GenericDropOnNodeSwitch(targetNode, this.viewHelper, this.diagramNavigator).doSwitch(droppedElement);
        } else {
            new GenericDropOnDiagramSwitch(this.viewHelper).doSwitch(droppedElement);
        }
    }

}
