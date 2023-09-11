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
package org.eclipse.papyrus.web.services.aqlservices.clazz;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.web.services.aqlservices.IWebExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.web.services.aqlservices.utils.IViewCreationHelper;
import org.eclipse.papyrus.web.services.aqlservices.utils.SemanticDropSwitch;
import org.eclipse.papyrus.web.sirius.contributions.DiagramNavigator;
import org.eclipse.sirius.components.diagrams.Node;

/**
 * Provides the behavior on a drop event in the "Class" Diagram.
 *
 * @author Laurent Fasani
 */
public class ClassDropBehaviorProvider implements IWebExternalSourceToRepresentationDropBehaviorProvider {

    private final IViewCreationHelper viewHelper;

    private DiagramNavigator diagramNavigator;

    public ClassDropBehaviorProvider(IViewCreationHelper viewHelper, DiagramNavigator diagramNavigator) {
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
        Optional<Node> optionalTargetNode = Optional.ofNullable(targetNode);
        new SemanticDropSwitch(optionalTargetNode, this.viewHelper, this.diagramNavigator).doSwitch(droppedElement);
    }

}
