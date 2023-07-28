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
package org.eclipse.papyrus.web.services.aqlservices.useCase;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.UseCaseExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.UseCaseExternalSourceToRepresentationDropChecker;
import org.eclipse.papyrus.web.services.aqlservices.IWebExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.web.services.aqlservices.utils.GenericDropOnDiagramSwitch;
import org.eclipse.papyrus.web.services.aqlservices.utils.GenericDropOnNodeSwitch;
import org.eclipse.papyrus.web.services.aqlservices.utils.IViewCreationHelper;
import org.eclipse.papyrus.web.sirius.contributions.DiagramNavigator;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;

/**
 * Provides the behavior on a drop event in the "Use Case" Diagram.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class UseCaseDropBehaviorProvider implements IWebExternalSourceToRepresentationDropBehaviorProvider {

    private final IEditingContext editionContext;

    private final IViewCreationHelper viewHelper;

    private final IObjectService objectService;

    private final ECrossReferenceAdapter crossRef;

    private final IEditableChecker editableChecker;

    private final DiagramNavigator diagramNavigator;

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
     * @return
     */
    public UseCaseDropBehaviorProvider(IEditingContext editionContext, IViewCreationHelper viewHelper, IObjectService objectService, ECrossReferenceAdapter crossRef,
            IEditableChecker editableChecker, DiagramNavigator diagramNavigator) {
        this.diagramNavigator = Objects.requireNonNull(diagramNavigator);
        this.crossRef = Objects.requireNonNull(crossRef);
        this.editableChecker = Objects.requireNonNull(editableChecker);
        this.editionContext = Objects.requireNonNull(editionContext);
        this.viewHelper = Objects.requireNonNull(viewHelper);
        this.objectService = Objects.requireNonNull(objectService);
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
            new GenericDropOnNodeSwitch(targetNode, this.viewHelper, this.diagramNavigator) //
                    .withDropChecker(new UseCaseExternalSourceToRepresentationDropChecker()) //
                    .withDropProvider(new UseCaseExternalSourceToRepresentationDropBehaviorProvider()) //
                    .withCrossRef(this.crossRef) //
                    .withEditableChecker(this.editableChecker) //
                    .withEObjectResolver(this::getSemanticObject) //
                    .doSwitch(droppedElement);
        } else {
            String rootDiagramId = this.diagramNavigator.getDiagram().getTargetObjectId();
            EObject semanticRootDiagram = (EObject) this.getSemanticObject(rootDiagramId);
            new GenericDropOnDiagramSwitch(this.viewHelper) //
                    .withDropChecker(new UseCaseExternalSourceToRepresentationDropChecker()) //
                    .withDropProvider(new UseCaseExternalSourceToRepresentationDropBehaviorProvider()) //
                    .withCrossRef(this.crossRef) //
                    .withEditableChecker(this.editableChecker) //
                    .withSemanticRootDiagram(semanticRootDiagram) //
                    .doSwitch(droppedElement);
        }
    }

    private Object getSemanticObject(String id) {
        return this.objectService.getObject(this.editionContext, id).orElse(null);

    }

}
