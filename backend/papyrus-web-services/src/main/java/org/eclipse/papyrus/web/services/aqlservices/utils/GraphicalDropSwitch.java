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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.eclipse.papyrus.uml.domain.services.drop.IInternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.IInternalSourceToRepresentationDropChecker;
import org.eclipse.papyrus.uml.domain.services.modify.ElementFeatureModifier;
import org.eclipse.papyrus.uml.domain.services.modify.IFeatureModifier;
import org.eclipse.papyrus.uml.domain.services.properties.ILogger;
import org.eclipse.papyrus.uml.domain.services.properties.ILogger.ILogLevel;
import org.eclipse.papyrus.uml.domain.services.status.CheckStatus;
import org.eclipse.papyrus.uml.domain.services.status.State;
import org.eclipse.papyrus.uml.domain.services.status.Status;
import org.eclipse.papyrus.web.application.representations.uml.PRDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.sirius.contributions.DiagramNavigator;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Profile;

/**
 * Default switch used for graphical drop :
 * <ul>
 * <li>a node on a Diagram or on another node.</li>
 * </ul>
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public final class GraphicalDropSwitch extends AbstractDropSwitch {

    /**
     * Message to display when graphical Drag&Drop failed.
     */
    private static final String GRAPHICAL_DRAG_DROP_FAILED = "Graphical Drag&Drop failed";

    /**
     * Checker in charge of checking if a graphical D&D is possible.
     */
    private IInternalSourceToRepresentationDropChecker dropChecker;

    /**
     * Provider of behavior when dropping graphical elements to a diagram element or on the diagram.
     */
    private IInternalSourceToRepresentationDropBehaviorProvider dropProvider;

    /**
     * The old semantic container of the dropped element.
     */
    private EObject oldSemanticContainer;

    /**
     * The new semantic container of the dropped element.
     */
    private EObject newSemanticContainer;

    /**
     * The graphical node to drop.
     */
    private Node droppedNode;

    /**
     * Logger used to report errors and warnings to the user.
     */
    private ILogger logger;

    /**
     * Constructor.
     *
     * @param optionalTargetNode
     *            the target node where element should be dropped, empty optional if the element should be dropped on
     *            diagram
     * @param optionalOldSemanticContainer
     *            the old semantic container of the dropped element
     * @param optionalNewSemanticContainer
     *            the new semantic container of the dropped element
     * @param viewHelper
     *            the helper used to create element on a diagram
     * @param diagramNavigator
     *            the helper used to navigate inside a diagram and/or its description
     * @param droppedNode
     *            the node dropped
     * @param logger
     *            Logger used to report errors and warnings to the user
     */
    public GraphicalDropSwitch(Optional<Node> optionalTargetNode, Optional<EObject> optionalOldSemanticContainer, Optional<EObject> optionalNewSemanticContainer, IViewHelper viewHelper,
            DiagramNavigator diagramNavigator, Node droppedNode, ILogger logger) {
        if (optionalTargetNode.isPresent()) {
            // case DnD on node
            this.targetNode = Objects.requireNonNull(optionalTargetNode.get());
        } else {
            // case DnD on Diagram
            this.targetNode = null;
        }
        this.viewHelper = viewHelper;
        this.diagramNavigator = diagramNavigator;
        this.oldSemanticContainer = Objects.requireNonNull(optionalOldSemanticContainer.get());
        this.newSemanticContainer = Objects.requireNonNull(optionalNewSemanticContainer.get());
        this.droppedNode = droppedNode;
        this.logger = logger;
    }

    /**
     * Sets the drop checker used to check if the drag & drop is authorized.
     *
     * @param theDropChecker
     *            the dropChecker used to check if the drag & drop is authorized
     * @return this GraphicalDropSwitch
     */
    public GraphicalDropSwitch withDropChecker(IInternalSourceToRepresentationDropChecker theDropChecker) {
        this.dropChecker = theDropChecker;
        return this;
    }

    /**
     * Sets the drop provider used to drag & drop an element.
     *
     * @param theDropProvider
     *            the drop provider used to drag & drop an element
     * @return this GraphicalDropSwitch
     */
    public GraphicalDropSwitch withDropProvider(IInternalSourceToRepresentationDropBehaviorProvider theDropProvider) {
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
     * @return this GraphicalDropSwitch
     */
    public GraphicalDropSwitch withCrossRef(ECrossReferenceAdapter theCrossRef) {
        this.crossRef = theCrossRef;
        return this;
    }

    /**
     * Sets the editable checker used to check if an element can be edited.
     * <p>
     * This parameter is <b>mandatory</b> if the drop checker and drop provider are non null.
     * </p>
     *
     * @param theEditableChecker
     *            the editable checker
     * @return this GraphicalDropSwitch
     */
    public GraphicalDropSwitch withEditableChecker(IEditableChecker theEditableChecker) {
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
     * @return this GraphicalDropSwitch
     */
    public GraphicalDropSwitch withEObjectResolver(Function<String, Object> theEObjectResolver) {
        this.eObjectResolver = theEObjectResolver;
        return this;
    }

    @Override
    public Boolean caseClass(Class umlClass) {
        Boolean isDragAndDropValid = Boolean.FALSE;
        String errorMessage = GRAPHICAL_DRAG_DROP_FAILED;
        if (umlClass.isMetaclass()) {
            EObject elementImportTargetParent = null;
            if (this.targetNode == null) {
                // diagram container case
                elementImportTargetParent = this.getSemanticDiagram();
            } else {
                // node container case
                elementImportTargetParent = this.getSemanticNode(this.targetNode);
            }
            EObject existingElementImportInTarget = this.getElementImportReferencingMetaclass(umlClass, elementImportTargetParent);
            if (existingElementImportInTarget != null) {
                String targetLabel = null;
                if (this.targetNode != null) {
                    targetLabel = this.targetNode.getTargetObjectLabel();
                } else {
                    targetLabel = "diagram root";
                }
                errorMessage = MessageFormat.format("Cannot drag and drop Metaclass {0} in {1}, a Metaclass with the same identifier already exists.",
                        this.droppedNode.getTargetObjectLabel(), targetLabel);
                this.logger.log(errorMessage, ILogLevel.WARNING);
            } else {
                EObject elementImportSourceParent = null;
                Optional<Node> parentMetaclassNode = this.diagramNavigator.getParentNode(this.droppedNode);
                if (parentMetaclassNode.isEmpty()) {
                    elementImportSourceParent = this.getSemanticDiagram();
                } else {
                    elementImportSourceParent = this.getSemanticNode(parentMetaclassNode.get());
                }
                EObject droppedElement = this.getElementImportReferencingMetaclass(umlClass, elementImportSourceParent);
                isDragAndDropValid = this.defaultCase(droppedElement);
            }
        } else {
            isDragAndDropValid = this.defaultCase(umlClass);
        }
        if (!isDragAndDropValid) {
            throw new InvalidDropException(errorMessage);
        }
        return isDragAndDropValid;
    }

    @Override
    public Boolean defaultCase(EObject object) {
        Boolean isDragAndDropValid = Boolean.FALSE;
        Status dropStatus = null;
        if (this.dropChecker != null && this.dropProvider != null) {
            Objects.requireNonNull(this.crossRef);
            Objects.requireNonNull(this.editableChecker);
            Objects.requireNonNull(this.eObjectResolver);
            dropStatus = this.graphicalDragAndDrop(object);
        } else {
            // default case when no dropChecker neither dropProvider are defined
            // ex. : org.eclipse.papyrus.web.services.aqlservices.utils.GenericWebInternalDropBehaviorProvider
            dropStatus = this.defaultGraphicalDragAndDrop(object);
        }
        isDragAndDropValid = this.createDragAndDropView(dropStatus, object);
        if (!isDragAndDropValid) {
            throw new InvalidDropException(GRAPHICAL_DRAG_DROP_FAILED);
        }
        return isDragAndDropValid;
    }

    /**
     * Creates the views for the {@code droppedElement} and its children.
     *
     * @param status
     *            status of the executed drag & drop
     * @param droppedElement
     *            the element to represent on the diagram
     * @return {@code true} if view has been created, {@code false} otherwise
     */
    private Boolean createDragAndDropView(Status status, EObject droppedElement) {
        Boolean isDragAndDropValid = Boolean.TRUE;
        if (status != null && status.getState() != State.FAILED) {
            // create the main view in the target node
            if (droppedElement instanceof ElementImport) {
                isDragAndDropValid = this.createElementImportView((ElementImport) droppedElement, this.targetNode);
            } else {
                isDragAndDropValid = isDragAndDropValid && this.createDefaultView(droppedElement);

                // create views for children of the dropped node
                isDragAndDropValid = isDragAndDropValid && this.createChildViews(droppedElement, this.targetNode, this.droppedNode);
            }

            // Delete view from the old container node
            isDragAndDropValid = isDragAndDropValid && this.viewHelper.deleteView(this.droppedNode);
        } else {
            isDragAndDropValid = Boolean.FALSE;
        }
        return isDragAndDropValid;
    }

    /**
     * Clones the child views of {@code elementNode} into {@code newParentNode}.
     *
     * @param semanticElement
     *            the semantic element which contain children element to represent on diagram
     * @param newParentNode
     *            the target node where child views are created
     * @param elementNode
     *            the element node containing the child nodes to clone
     */
    private boolean createChildViews(EObject semanticElement, Node newParentNode, Node elementNode) {
        Boolean isDragAndDropValid = Boolean.TRUE;
        Node fakeParentNode = this.viewHelper.createFakeNode(semanticElement, newParentNode);
        List<Node> matchingChildNodes = new ArrayList<>(elementNode.getChildNodes());
        matchingChildNodes.addAll(elementNode.getBorderNodes());
        for (Node matchingChildNode : matchingChildNodes) {
            EObject semanticNode = this.getSemanticNode(matchingChildNode);
            if (semanticNode instanceof org.eclipse.uml2.uml.Class umlClass && umlClass.isMetaclass()) {
                isDragAndDropValid = this.createElementImportView((ElementImport) this.getElementImportReferencingMetaclass(umlClass, semanticElement), fakeParentNode);
            } else {
                isDragAndDropValid = isDragAndDropValid && this.viewHelper.createChildView(semanticNode, fakeParentNode, null);
            }
            isDragAndDropValid = isDragAndDropValid && this.createChildViews(this.getSemanticNode(matchingChildNode), fakeParentNode, matchingChildNode);
        }
        return isDragAndDropValid;
    }

    /**
     * Creates the view for the provided {@code elementImport}.
     *
     * @param elementImport
     *            the {@link ElementImport} to represent on the diagram
     * @param parentNode
     *            the parent node which contain the new {@link ElementImport} view
     * @return {@code true} if the view has been created, {@code false} otherwise
     */
    private Boolean createElementImportView(ElementImport elementImport, Node parentNode) {
        Boolean isViewCreated = Boolean.TRUE;
        PackageableElement importedElement = elementImport.getImportedElement();
        if (importedElement == null) {
            String errorMessage = "Only ElementImport with imported element can be drag and dropped";
            LOGGER.warn(errorMessage);
            this.logger.log(errorMessage, ILogLevel.WARNING);
        } else if (parentNode != null) {
            // case DnD on Node
            isViewCreated = this.viewHelper.createChildView(importedElement, parentNode, PRDDiagramDescriptionBuilder.PRD_SHARED_METACLASS);
        } else {
            // case DnD on Diagram
            isViewCreated = this.viewHelper.createRootView(importedElement, PRDDiagramDescriptionBuilder.PRD_METACLASS);
        }
        return isViewCreated;
    }

    /**
     * Graphical drag & drop of a given object on the selected node or diagram.
     *
     * @param object
     *            the object to drag & drop on the selected node or diagram
     * @return the status of the drag & drop
     */
    private Status graphicalDragAndDrop(EObject object) {
        EObject semanticDiagram = this.getSemanticDiagram();
        EObject semanticTarget = null;
        Status status = null;
        if (this.targetNode != null) {
            // case DnD on Node
            semanticTarget = this.getSemanticNode(this.targetNode);
        } else {
            // case DnD on Diagram
            semanticTarget = semanticDiagram;
        }
        CheckStatus canDragAndDrop = this.dropChecker.canDragAndDrop(object, semanticTarget);
        if (canDragAndDrop.isValid()) {
            status = this.dropProvider.drop(object, this.oldSemanticContainer, this.newSemanticContainer, this.crossRef, this.editableChecker);
        } else {
            status = Status.createFailingStatus(canDragAndDrop.getMessage());
        }
        if (status.getState() == State.FAILED) {
            LOGGER.warn(status.getMessage());
            this.logger.log(status.getMessage(), ILogLevel.WARNING);
        }
        return status;
    }

    /**
     * Default graphical drag & drop of a given object on the selected node or diagram.
     * <p>
     * Only the container of the dropped element changes.
     * </p>
     *
     * @param object
     *            the object to drag & drop on the selected node or diagram
     * @return the status of the drag & drop
     */
    private Status defaultGraphicalDragAndDrop(EObject object) {
        Status dropStatus = null;
        IFeatureModifier modifier = new ElementFeatureModifier(this.crossRef, this.editableChecker);
        if (this.oldSemanticContainer != this.newSemanticContainer) {
            String refName = object.eContainmentFeature().getName();
            if (this.oldSemanticContainer.eClass().getEStructuralFeature(refName) != null && this.newSemanticContainer.eClass().getEStructuralFeature(refName) != null) {
                dropStatus = modifier.removeValue(this.oldSemanticContainer, refName, object);
                if (State.DONE == dropStatus.getState()) {
                    dropStatus = modifier.addValue(this.newSemanticContainer, refName, object);
                }
            }
        }
        return dropStatus;
    }

    /**
     * Get the {@link ElementImport} referencing the given Metaclass {@code umlClass}.
     *
     * @param metaClass
     *            the metaClass referenced by the {@link ElementImport}
     * @param elementImportParent
     *            the semantic parent of the {@link ElementImport}
     * @return the {@link ElementImport} referencing the given metaClass
     */
    private EObject getElementImportReferencingMetaclass(Class metaClass, EObject elementImportParent) {
        EObject droppedElement = null;
        Optional<ElementImport> optionalElementImport = Optional.of(elementImportParent).filter(Profile.class::isInstance).map(Profile.class::cast).map(profile -> profile.getElementImport(metaClass));
        if (optionalElementImport.isPresent()) {
            droppedElement = optionalElementImport.get();
        }
        return droppedElement;
    }
}
