/*****************************************************************************
 * Copyright (c) 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.representations.aqlservices.activity;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.ActivityExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.ActivityExternalSourceToRepresentationDropChecker;
import org.eclipse.papyrus.uml.domain.services.properties.ILogger;
import org.eclipse.papyrus.web.application.representations.IWebExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.web.application.representations.aqlservices.utils.IViewHelper;
import org.eclipse.papyrus.web.application.representations.aqlservices.utils.SemanticDropSwitch;
import org.eclipse.papyrus.web.sirius.contributions.DiagramNavigator;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.Node;

/**
 * Provides the behavior on a drop event in the "Activity" Diagram.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class ActivitySemanticDropBehaviorProvider implements IWebExternalSourceToRepresentationDropBehaviorProvider {

    private final IEditingContext editionContext;

    private final IViewHelper viewHelper;

    private final IObjectService objectService;

    private final ECrossReferenceAdapter crossRef;

    private final IEditableChecker editableChecker;

    private DiagramNavigator diagramNavigator;

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
     * @param crossRef
     *            An adapter used to get inverse references
     * @param editableChecker
     *            Object that check if an element can be edited
     * @param diagramNavigator
     *            the helper used to navigate inside a diagram and/or to its description
     * @param logger
     *            Logger used to report errors and warnings to the user
     */
    public ActivitySemanticDropBehaviorProvider(IEditingContext editionContext, IViewHelper viewHelper, IObjectService objectService, ECrossReferenceAdapter crossRef, IEditableChecker editableChecker,
            DiagramNavigator diagramNavigator, ILogger logger) {
        this.diagramNavigator = Objects.requireNonNull(diagramNavigator);
        this.crossRef = Objects.requireNonNull(crossRef);
        this.editableChecker = Objects.requireNonNull(editableChecker);
        this.editionContext = Objects.requireNonNull(editionContext);
        this.viewHelper = Objects.requireNonNull(viewHelper);
        this.objectService = Objects.requireNonNull(objectService);
        this.logger = logger;
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
    public void handleSemanticDrop(EObject droppedElement, org.eclipse.sirius.components.diagrams.Node targetNode) {
        Optional<Node> optionalTargetNode = Optional.ofNullable(targetNode);
        if (targetNode != null) {
            new SemanticDropSwitch(optionalTargetNode, this.viewHelper, this.diagramNavigator, this.logger) //
                    .withDropChecker(new ActivityExternalSourceToRepresentationDropChecker()) //
                    .withDropProvider(new ActivityExternalSourceToRepresentationDropBehaviorProvider()) //
                    .withCrossRef(this.crossRef) //
                    .withEditableChecker(this.editableChecker) //
                    .withEObjectResolver(this::getSemanticObject) //
                    .doSwitch(droppedElement);
        } else {
            // nothing if something is dropped in the diagram
        }
    }

    private Object getSemanticObject(String id) {
        return this.objectService.getObject(this.editionContext, id).orElse(null);
    }

}
