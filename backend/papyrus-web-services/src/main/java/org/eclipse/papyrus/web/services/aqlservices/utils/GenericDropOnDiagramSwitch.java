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

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.eclipse.papyrus.uml.domain.services.drop.DnDStatus;
import org.eclipse.papyrus.uml.domain.services.drop.IExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.IExternalSourceToRepresentationDropChecker;
import org.eclipse.papyrus.uml.domain.services.status.State;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * Default switch used to drop a node on a Diagram.
 *
 * @author Arthur Daussy
 */
public final class GenericDropOnDiagramSwitch extends UMLSwitch<Void> {

    /**
     * The helper used to create element on a diagram.
     */
    private final IViewCreationHelper viewHelper;

    /**
     * Checker in charge of checking if a semantic D&D is possible.
     */
    private IExternalSourceToRepresentationDropChecker dropChecker;

    /**
     * Provider of behavior when dropping semantic elements (from Explorer) to a diagram element.
     */
    private IExternalSourceToRepresentationDropBehaviorProvider dropProvider;

    /**
     * Object that check if an element can be edited.
     */
    private IEditableChecker editableChecker;

    /**
     * An adapter used to get inverse references
     */
    private ECrossReferenceAdapter crossRef;

    /**
     * The semantic root Object represented by the diagram.
     */
    private EObject semanticRootDiagram;

    public GenericDropOnDiagramSwitch(IViewCreationHelper viewHelper) {
        super();
        this.viewHelper = viewHelper;
    }

    /**
     * Sets the drop checker used to check if the DragAndDrop is authorized.
     *
     * @param theDropChecker
     *            the dropChecker used to check if the DragAndDrop is authorized
     * @return this builder
     */
    public GenericDropOnDiagramSwitch withDropChecker(IExternalSourceToRepresentationDropChecker theDropChecker) {
        this.dropChecker = theDropChecker;
        return this;
    }

    /**
     * Sets the drop provider used DragAndDrop an element.
     *
     * @param theDropProvider
     *            the drop provider used DragAndDrop an element
     * @return the GenericDropOnDiagram Swicth
     */
    public GenericDropOnDiagramSwitch withDropProvider(IExternalSourceToRepresentationDropBehaviorProvider theDropProvider) {
        this.dropProvider = theDropProvider;
        return this;
    }

    /**
     * Sets the cross referencer.
     * <p>
     * This parameter is <b>mandatory</b> if the drop checker and drop provider are non null.
     * </p>
     *
     * @param theCrossRef
     *            the cross referencer
     * @return this GenericDropOnDiagram Switch
     */
    public GenericDropOnDiagramSwitch withCrossRef(ECrossReferenceAdapter theCrossRef) {
        this.crossRef = theCrossRef;
        return this;
    }

    /**
     * Sets the editable checker used to check if an element can be edited..
     * <p>
     * This parameter is <b>mandatory</b> if the drop checker and drop provider are non null.
     * </p>
     *
     * @param theEditableChecker
     *            the editable checker
     * @return this GenericDropOnDiagram Switch
     */
    public GenericDropOnDiagramSwitch withEditableChecker(IEditableChecker theEditableChecker) {
        this.editableChecker = theEditableChecker;
        return this;
    }

    /**
     * Sets the semantic root Object represented by the diagram.
     * <p>
     * This parameter is <b>mandatory</b> if the drop checker and drop provider are non null.
     * </p>
     *
     * @param theSemanticRootDiagram
     *            The semantic root Object represented by the diagram.
     * @return this GenericDropOnDiagram Switch
     */
    public GenericDropOnDiagramSwitch withSemanticRootDiagram(EObject theSemanticRootDiagram) {
        this.semanticRootDiagram = theSemanticRootDiagram;
        return this;
    }

    @Override
    public Void defaultCase(EObject object) {
        if (this.dropChecker != null && this.dropProvider != null) {
            Objects.requireNonNull(this.crossRef);
            Objects.requireNonNull(this.editableChecker);
            Objects.requireNonNull(this.semanticRootDiagram);
            if (this.dropChecker.canDragAndDrop(object, this.semanticRootDiagram).isValid()) {
                DnDStatus status = this.dropProvider.drop(object, this.semanticRootDiagram, this.crossRef, this.editableChecker);
                if (status.getState() != State.FAILED) {
                    for (EObject eObjectToDisplay : status.getElementsToDisplay()) {
                        this.viewHelper.createRootView(eObjectToDisplay);
                    }
                }
            }
        } else {
            this.viewHelper.createRootView(object);
        }
        return super.defaultCase(object);
    }

}
