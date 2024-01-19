/*****************************************************************************
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
 *****************************************************************************/
package org.eclipse.papyrus.web.services.aqlservices.statemachine;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.domain.services.properties.ILogger;
import org.eclipse.papyrus.web.services.aqlservices.IWebExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.web.services.aqlservices.utils.IViewHelper;
import org.eclipse.papyrus.web.services.aqlservices.utils.SemanticDropSwitch;
import org.eclipse.papyrus.web.sirius.contributions.DiagramNavigator;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;

/**
 * Provides the behavior on a semantic drop event in the "StateMachine Diagram".
 *
 * @author Laurent Fasani
 */
public class StateMachineSemanticDiagramDropBehaviorProvider implements IWebExternalSourceToRepresentationDropBehaviorProvider {

    private final IViewHelper viewHelper;

    private DiagramNavigator diagramNavigator;

    private IEditingContext editionContext;

    private IObjectService objectService;

    /**
     * Logger used to report errors and warnings to the user.
     */
    private ILogger logger;

    /**
     * Constructor.
     *
     * @param editionContext
     *            editing context used to retrieve semantic target
     * @param viewHelper
     *            the helper used to create element on a diagram
     * @param objectService
     *            service used to retrieve semantic target according to node id
     * @param diagramNavigator
     *            the helper used to navigate inside a diagram and/or to its description
     * @param logger
     *            Logger used to report errors and warnings to the user
     */
    public StateMachineSemanticDiagramDropBehaviorProvider(IEditingContext editionContext, IViewHelper viewHelper, IObjectService objectService, DiagramNavigator diagramNavigator, ILogger logger) {
        this.diagramNavigator = Objects.requireNonNull(diagramNavigator);
        this.editionContext = Objects.requireNonNull(editionContext);
        this.viewHelper = Objects.requireNonNull(viewHelper);
        this.objectService = Objects.requireNonNull(objectService);
        this.logger = logger;
    }

    /**
     * Handles a semantic drop event.
     *
     * @param droppedElement
     *            the dropped element
     * @param targetNode
     *            the target node or <code>null</code> if the drop occurred on the diagram
     */
    @Override
    public void handleSemanticDrop(EObject droppedElement, org.eclipse.sirius.components.diagrams.Node targetNode) {
        if (targetNode != null) {
            new SemanticDropSwitch(Optional.of(targetNode), this.viewHelper, this.diagramNavigator, this.logger)//
                    .withEObjectResolver(this::getSemanticObject) //
                    .doSwitch(droppedElement);
        } else {
            // nothing is something is dropped in the diagram
        }
    }

    private Object getSemanticObject(String id) {
        return this.objectService.getObject(this.editionContext, id).orElse(null);
    }

}
