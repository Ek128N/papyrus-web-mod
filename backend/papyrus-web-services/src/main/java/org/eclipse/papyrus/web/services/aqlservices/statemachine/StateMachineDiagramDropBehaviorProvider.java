/*******************************************************************************
 * Copyright (c) 2022 CEA, Obeo
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
package org.eclipse.papyrus.web.services.aqlservices.statemachine;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.web.services.aqlservices.IWebExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.web.services.aqlservices.utils.IViewCreationHelper;
import org.eclipse.papyrus.web.services.aqlservices.utils.SemanticDropSwitch;
import org.eclipse.papyrus.web.sirius.contributions.DiagramNavigator;

/**
 * Provides the behavior on a drop event in the "StateMachine Diagram".
 *
 * @author Laurent Fasani
 */
public class StateMachineDiagramDropBehaviorProvider implements IWebExternalSourceToRepresentationDropBehaviorProvider {

    private final IViewCreationHelper viewHelper;

    private DiagramNavigator diagramNavigator;

    public StateMachineDiagramDropBehaviorProvider(IViewCreationHelper viewHelper, DiagramNavigator diagramNavigator) {
        this.diagramNavigator = Objects.requireNonNull(diagramNavigator);
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
            new SemanticDropSwitch(Optional.of(targetNode), this.viewHelper, this.diagramNavigator).doSwitch(droppedElement);
        } else {
            // nothing is something is dropped in the diagram
        }
    }

}
