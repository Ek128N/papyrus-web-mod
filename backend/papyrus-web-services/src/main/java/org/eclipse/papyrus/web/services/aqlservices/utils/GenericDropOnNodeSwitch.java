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
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.eclipse.papyrus.uml.domain.services.drop.DnDStatus;
import org.eclipse.papyrus.uml.domain.services.drop.IExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.IExternalSourceToRepresentationDropChecker;
import org.eclipse.papyrus.uml.domain.services.status.State;
import org.eclipse.papyrus.web.sirius.contributions.DiagramNavigator;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * Default switch used to drop a node on another node.
 *
 * @author Arthur Daussy
 */
public class GenericDropOnNodeSwitch extends UMLSwitch<Boolean> {

    // Duplicated from org.eclipse.papyrus.web.application.representations.IdBuilder
    // Keep in sync
    // Workaround https://github.com/PapyrusSirius/papyrus-web/issues/165
    private static final String COMPARTMENT_NODE_SUFFIX = "_CompartmentNode"; //$NON-NLS-1$

    /**
     * The helper used to create element on a diagram.
     */
    private final IViewCreationHelper viewHelper;

    /**
     * The selected node where element should be dropped.
     */
    private final Node selectedNode;

    /**
     * The helper used to navigate inside a diagram and/or to its description.
     */
    private DiagramNavigator diagramNavigator;

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
     * EObject resolver used to retrieve the semantic target from the selected node.
     */
    private Function<String, Object> eObjectResolver;

    /**
     * Constructor.
     *
     * @param selectedNode
     *            the selected node where element should be dropped
     * @param viewHelper
     *            the helper used to create element on a diagram
     * @param diagramNavigator
     *            the helper used to navigate inside a diagram and/or to its description
     */
    public GenericDropOnNodeSwitch(Node selectedNode, IViewCreationHelper viewHelper, DiagramNavigator diagramNavigator) {
        super();
        this.selectedNode = selectedNode;
        this.viewHelper = viewHelper;
        this.diagramNavigator = diagramNavigator;
    }

    /**
     * Sets the drop checker used to check if the DragAndDrop is authorized.
     *
     * @param theDropChecker
     *            the dropChecker used to check if the DragAndDrop is authorized
     * @return this builder
     */
    public GenericDropOnNodeSwitch withDropChecker(IExternalSourceToRepresentationDropChecker theDropChecker) {
        this.dropChecker = theDropChecker;
        return this;
    }

    /**
     * Sets the drop provider used DragAndDrop an element.
     *
     * @param theDropProvider
     *            the drop provider used DragAndDrop an element
     * @return this GenericDropOnNode Switch
     */
    public GenericDropOnNodeSwitch withDropProvider(IExternalSourceToRepresentationDropBehaviorProvider theDropProvider) {
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
     * @return this GenericDropOnNode Switch
     */
    public GenericDropOnNodeSwitch withCrossRef(ECrossReferenceAdapter theCrossRef) {
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
     * @return this GenericDropOnNode Switch
     */
    public GenericDropOnNodeSwitch withEditableChecker(IEditableChecker theEditableChecker) {
        this.editableChecker = theEditableChecker;
        return this;
    }

    /**
     * Sets the eObject resolver used to retrieve the semantic target from the selected node.
     * <p>
     * This parameter is <b>mandatory</b> if the drop checker and drop provider are non null.
     * </p>
     *
     * @param theEObjectResolver
     *            the eEbject resolver
     * @return this GenericDropOnNode Switch
     */
    public GenericDropOnNodeSwitch withEObjectResolver(Function<String, Object> theEObjectResolver) {
        this.eObjectResolver = theEObjectResolver;
        return this;
    }

    /**
     * Get the selected node where element should be dropped.
     *
     * @return the selected node where element should be dropped.
     */
    public Node getSelectedNode() {
        return this.selectedNode;
    }

    /**
     * Get the helper used to create element on a diagram.
     *
     * @return the helper used to create element on a diagram.
     */
    public IViewCreationHelper getViewHelper() {
        return this.viewHelper;
    }

    /**
     * Get the helper used to navigate inside a diagram and/or to its description.
     *
     * @return the helper used to navigate inside a diagram and/or to its description.
     */
    public DiagramNavigator getDiagramNavigator() {
        return this.diagramNavigator;
    }

    @Override
    public Boolean defaultCase(EObject object) {
        Boolean isDragAndDropValid = Boolean.FALSE;
        if (this.dropChecker != null && this.dropProvider != null) {
            Objects.requireNonNull(this.crossRef);
            Objects.requireNonNull(this.editableChecker);
            Objects.requireNonNull(this.eObjectResolver);
            if (this.dropChecker.canDragAndDrop(object, this.getSemanticTarget()).isValid()) {
                DnDStatus status = this.dropProvider.drop(object, this.getSemanticTarget(), this.crossRef, this.editableChecker);
                if (status.getState() != State.FAILED) {
                    isDragAndDropValid = Boolean.TRUE;
                    for (EObject eObjectToDisplay : status.getElementsToDisplay()) {
                        isDragAndDropValid = isDragAndDropValid && this.createChildView(eObjectToDisplay);
                    }
                }
            }
        } else {
            isDragAndDropValid = this.createChildView(object);
        }
        return isDragAndDropValid;
    }

    /**
     * Create view in the selected Node matching with a given semantic Object.
     *
     * @param eObjectToDisplay
     *            the semantic Object to represent on the selected node
     * @return <code>true</code> if the view has been created, <code>false</code> otherwise.
     */
    private boolean createChildView(EObject eObjectToDisplay) {
        boolean success = this.viewHelper.createChildView(eObjectToDisplay, this.selectedNode);
        if (!success) {
            // Workaround https://github.com/PapyrusSirius/papyrus-web/issues/165
            // If DnD on an icon label element contained by a compartment then DnD the element in the
            // compartment
            // instead
            Optional<Node> parentNode = this.diagramNavigator.getParentNode(this.selectedNode);
            parentNode//
                    .flatMap(this.diagramNavigator::getDescription)//
                    .filter(desc -> desc.getName().endsWith(COMPARTMENT_NODE_SUFFIX)).ifPresent(parentDescription -> {
                        this.viewHelper.createChildView(eObjectToDisplay, parentNode.get());
                    });
        }
        return success;
    }

    /**
     * Get the semantic target represented by the selected node.
     *
     * @return the semantic target represented by the selected node.
     */
    private EObject getSemanticTarget() {
        return (EObject) this.eObjectResolver.apply(this.getSelectedNode().getTargetObjectId());
    }

}
