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
package org.eclipse.papyrus.web.services.aqlservices.utils;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.domain.services.properties.ILogger;
import org.eclipse.papyrus.web.services.aqlservices.IWebInternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.web.sirius.contributions.DiagramNavigator;
import org.eclipse.sirius.components.diagrams.Node;

/**
 * Default implementation of a {@link IWebInternalSourceToRepresentationDropBehaviorProvider} that created a view of the
 * dropped element if the Diagram Description model allows it.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class GenericWebInternalDropBehaviorProvider implements IWebInternalSourceToRepresentationDropBehaviorProvider {

    /**
     * The helper used to create element on a diagram.
     */
    private final IViewHelper viewHelper;

    /**
     * The helper used to navigate inside a diagram and/or to its description.
     */
    private DiagramNavigator diagramNavigator;

    /**
     * Logger used to report errors and warnings to the user.
     */
    private ILogger logger;

    /**
     * Constructor.
     *
     * @param viewHelper
     *            the helper used to create element on a diagram
     * @param diagramNavigator
     *            the helper used to navigate inside a diagram and/or to its description
     * @param logger
     *            Logger used to report errors and warnings to the user
     */
    public GenericWebInternalDropBehaviorProvider(IViewHelper viewHelper, DiagramNavigator diagramNavigator, ILogger logger) {
        this.diagramNavigator = diagramNavigator;
        this.viewHelper = Objects.requireNonNull(viewHelper);
        this.logger = logger;
    }

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
    @Override
    public void handleGraphicalDrop(EObject droppedElement, EObject targetElement, Node droppedNode, Node targetNode) {
        Optional<Node> optionalTargetNode = Optional.ofNullable(targetNode);
        Optional<EObject> optionalOldSemanticContainer = Optional.ofNullable(droppedElement.eContainer());
        Optional<EObject> optionalNewSemanticContainer = Optional.ofNullable(targetElement);
        new GraphicalDropSwitch(optionalTargetNode, optionalOldSemanticContainer, optionalNewSemanticContainer, this.viewHelper, this.diagramNavigator, droppedNode, this.logger)
                .doSwitch(droppedElement);
    }

}
