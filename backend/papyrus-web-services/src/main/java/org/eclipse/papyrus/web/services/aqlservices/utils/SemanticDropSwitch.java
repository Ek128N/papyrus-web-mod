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

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.eclipse.papyrus.uml.domain.services.drop.DnDStatus;
import org.eclipse.papyrus.uml.domain.services.drop.IExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.IExternalSourceToRepresentationDropChecker;
import org.eclipse.papyrus.uml.domain.services.edges.ElementDomainBasedEdgeSourceProvider;
import org.eclipse.papyrus.uml.domain.services.edges.ElementDomainBasedEdgeTargetsProvider;
import org.eclipse.papyrus.uml.domain.services.status.CheckStatus;
import org.eclipse.papyrus.uml.domain.services.status.State;
import org.eclipse.papyrus.web.application.representations.uml.PRDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.sirius.contributions.DiagramNavigator;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ActivityPartition;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.InterruptibleActivityRegion;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.Relationship;
import org.eclipse.uml2.uml.util.UMLSwitch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default switch used to drop :
 * <ul>
 * <li>a node on a Diagram or on another node,</li>
 * <li>an edge on the diagram or on a node.</li>
 * </ul>
 *
 * @author Arthur Daussy
 */
public final class SemanticDropSwitch extends UMLSwitch<Boolean> {

    // Duplicated from org.eclipse.papyrus.web.application.representations.IdBuilder
    // Keep in sync
    // Workaround https://github.com/PapyrusSirius/papyrus-web/issues/165
    private static final String COMPARTMENT_NODE_SUFFIX = "_CompartmentNode"; //$NON-NLS-1$

    /**
     * Logger used to log error when Drag and Drop fails.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SemanticDropSwitch.class);

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
     * EObject resolver used to retrieve the semantic target from the selected node.
     */
    private Function<String, Object> eObjectResolver;

    /**
     * The selected node where element should be dropped.
     */
    private final Node selectedNode;

    /**
     * The helper used to navigate inside a diagram and/or to its description.
     */
    private DiagramNavigator diagramNavigator;

    /**
     * Constructor.
     *
     * @param optionalSelectedNode
     *            the selected node where element should be dropped, empty optional if the element should be dropped on
     *            diagram
     * @param viewHelper
     *            the helper used to create element on a diagram
     * @param diagramNavigator
     *            the helper used to navigate inside a diagram and/or to its description
     */
    public SemanticDropSwitch(Optional<Node> optionalSelectedNode, IViewCreationHelper viewHelper, DiagramNavigator diagramNavigator) {
        if (optionalSelectedNode.isPresent()) {
            // case DnD on node
            this.selectedNode = Objects.requireNonNull(optionalSelectedNode.get());
        } else {
            // case DnD on Diagram
            this.selectedNode = null;
        }
        this.viewHelper = viewHelper;
        this.diagramNavigator = diagramNavigator;
    }

    /**
     * Sets the drop checker used to check if the DragAndDrop is authorized.
     *
     * @param theDropChecker
     *            the dropChecker used to check if the DragAndDrop is authorized
     * @return this SemanticDropSwitch
     */
    public SemanticDropSwitch withDropChecker(IExternalSourceToRepresentationDropChecker theDropChecker) {
        this.dropChecker = theDropChecker;
        return this;
    }

    /**
     * Sets the drop provider used to DragAndDrop an element.
     *
     * @param theDropProvider
     *            the drop provider used to DragAndDrop an element
     * @return this SemanticDropSwitch
     */
    public SemanticDropSwitch withDropProvider(IExternalSourceToRepresentationDropBehaviorProvider theDropProvider) {
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
     * @return this SemanticDropSwitch
     */
    public SemanticDropSwitch withCrossRef(ECrossReferenceAdapter theCrossRef) {
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
     * @return this SemanticDropSwitch
     */
    public SemanticDropSwitch withEditableChecker(IEditableChecker theEditableChecker) {
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
     * @return this SemanticDropSwitch
     */
    public SemanticDropSwitch withEObjectResolver(Function<String, Object> theEObjectResolver) {
        this.eObjectResolver = theEObjectResolver;
        return this;
    }

    @Override
    public Boolean caseRelationship(Relationship relationship) {
        return this.createDnDEdgeView(relationship);
    }

    @Override
    public Boolean caseActivityEdge(ActivityEdge activityEdge) {
        return this.createDnDEdgeView(activityEdge);
    }

    @Override
    public Boolean caseConnector(Connector connector) {
        return this.createDnDEdgeView(connector);
    }

    @Override
    public Boolean caseMessage(Message message) {
        return this.createDnDEdgeView(message);
    }

    // This case is mandatory because ElementImport is a Relationship so it will
    // work with caseRelationship instead of Default Case
    @Override
    public Boolean caseElementImport(ElementImport elementImport) {
        Boolean isDragAndDropValid = Boolean.FALSE;
        DnDStatus status = null;
        if (this.dropChecker != null && this.dropProvider != null) {
            status = this.semanticDragAndDrop(elementImport);
            isDragAndDropValid = this.createElementImportDragAndDropView(status);
        } else {
            // default case when no dropChecker neither dropProvider are defined
            // ex. : org.eclipse.papyrus.web.services.aqlservices.utils.GenericWebExternalDropBehaviorProvider
            isDragAndDropValid = this.createDefaultView(elementImport);
        }
        return isDragAndDropValid;
    }

    @Override
    public Boolean defaultCase(EObject object) {
        Boolean isDragAndDropValid = Boolean.FALSE;
        DnDStatus status = null;
        if (this.dropChecker != null && this.dropProvider != null) {
            Objects.requireNonNull(this.crossRef);
            Objects.requireNonNull(this.editableChecker);
            Objects.requireNonNull(this.eObjectResolver);
            status = this.semanticDragAndDrop(object);
            isDragAndDropValid = this.createDragAndDropView(status);
        } else {
            // default case when no dropChecker neither dropProvider are defined
            // ex. : org.eclipse.papyrus.web.services.aqlservices.utils.GenericWebExternalDropBehaviorProvider
            isDragAndDropValid = this.createDefaultView(object);
        }
        return isDragAndDropValid;
    }

    /**
     * Create views for elements to display after a DragAndDrop.
     *
     * @param status
     *            status of the previous DragAndDrop which contains elements to display
     * @return {@code true} if all views have been created, {@code false} otherwise
     */
    private Boolean createDragAndDropView(DnDStatus status) {
        Boolean isDragAndDropValid = Boolean.TRUE;
        if (status.getState() != State.FAILED) {
            isDragAndDropValid = Boolean.TRUE;
            for (EObject eObjectToDisplay : status.getElementsToDisplay()) {
                if (this.selectedNode != null) {
                    // case DnD on Node
                    isDragAndDropValid = isDragAndDropValid && this.createChildView(eObjectToDisplay);
                } else {
                    // case DnD on Diagram
                    isDragAndDropValid = isDragAndDropValid && this.viewHelper.createRootView(eObjectToDisplay);
                }
            }
        } else {
            isDragAndDropValid = Boolean.FALSE;
        }
        return isDragAndDropValid;
    }

    /**
     * Create views for elements to display after an {@link ElementImport} DragAndDrop.
     *
     * @param status
     *            status of the previous {@link ElementImport} DragAndDrop which contains elements to display
     * @return <code>true</code> if all views have been created, <code>false</code> otherwise
     */
    private Boolean createElementImportDragAndDropView(DnDStatus status) {
        Boolean isDragAndDropValid = Boolean.FALSE;
        if (status != null && status.getState() != State.FAILED) {
            isDragAndDropValid = Boolean.TRUE;
            for (EObject eObjectToDisplay : status.getElementsToDisplay()) {
                if (this.selectedNode != null) {
                    // case DnD on Node
                    isDragAndDropValid = isDragAndDropValid && this.createChildView(((ElementImport) eObjectToDisplay).getImportedElement(), PRDDiagramDescriptionBuilder.PRD_METACLASS);
                } else {
                    // case DnD on Diagram
                    isDragAndDropValid = isDragAndDropValid && this.viewHelper.createRootView(((ElementImport) eObjectToDisplay).getImportedElement(), PRDDiagramDescriptionBuilder.PRD_METACLASS);
                }
            }
        }
        return isDragAndDropValid;
    }

    /**
     * Create default view for a given element.
     *
     * @param object
     *            the object to represent with a view
     * @return {@code true} if the view has been created, {@code false} otherwise
     */
    private Boolean createDefaultView(EObject object) {
        Boolean isDragAndDropValid;
        if (this.selectedNode != null) {
            isDragAndDropValid = this.createChildView(object);
        } else {
            isDragAndDropValid = this.viewHelper.createRootView(object);
        }
        return isDragAndDropValid;
    }

    /**
     * Semantic Drag and drop of a given object on selected node or diagram.
     *
     * @param object
     *            the object to Drag and drop on selected node or diagram
     * @return the status of DragAndDrop
     */
    private DnDStatus semanticDragAndDrop(EObject object) {
        EObject semanticDiagram = this.getSemanticDiagram();
        EObject semanticTarget = null;
        DnDStatus status = null;
        if (this.selectedNode != null) {
            // case DnD on Node
            semanticTarget = this.getSemanticNode(this.selectedNode);
        } else {
            // case DnD on Diagram
            semanticTarget = semanticDiagram;
        }
        CheckStatus canDragAndDrop = this.dropChecker.canDragAndDrop(object, semanticTarget);
        if (canDragAndDrop.isValid()) {
            status = this.dropProvider.drop(object, semanticTarget, this.crossRef, this.editableChecker);
        } else {
            status = DnDStatus.createFailingStatus(canDragAndDrop.getMessage(), Collections.emptySet());
        }
        if (status.getState() == State.FAILED) {
            LOGGER.error(status.getMessage());
        }
        return status;
    }

    /**
     * Creates a view in the selected node for the provided {@code eObjectToDisplay}.
     * <p>
     * This method computes the best possible mapping for the provided {@code eObjectToDisplay}. See
     * {@link #createChildView(EObject, String)} to specify which mapping to use to create the view.
     * </p>
     *
     * @param eObjectToDisplay
     *            the semantic Object to represent on the selected node
     * @return {@code true} if the view has been created, {@code false} otherwise
     *
     * @see #createChildView(EObject, String)
     */
    private boolean createChildView(EObject eObjectToDisplay) {
        return this.createChildView(eObjectToDisplay, null);
    }

    /**
     * Creates a {@code mappingName} view in the selected node for the provided {@code eObjectToDisplay}.
     * <p>
     * This method computes the best possible mapping if the provided {@code mappingName} is {@code null}. See
     * {@link #createChildView(EObject)} for more information.
     * </p>
     *
     * @param eObjectToDisplay
     *            the semantic Object to represent on the selected node
     * @param mappingName
     *            the name of the mapping to use to create the view
     * @return {@code true} if the view has been created, {@code false} otherwise
     *
     * @see #createChildView(EObject)
     */
    private boolean createChildView(EObject eObjectToDisplay, String mappingName) {
        boolean success = this.viewHelper.createChildView(eObjectToDisplay, this.selectedNode, mappingName);
        if (!success) {
            // Workaround https://github.com/PapyrusSirius/papyrus-web/issues/165
            // If DnD on an icon label element contained by a compartment then DnD the element in the
            // compartment
            // instead
            Optional<Node> parentNode = this.diagramNavigator.getParentNode(this.selectedNode);
            parentNode//
                    .flatMap(this.diagramNavigator::getDescription)//
                    .filter(desc -> desc.getName().endsWith(COMPARTMENT_NODE_SUFFIX)).ifPresent(parentDescription -> {
                        this.viewHelper.createChildView(eObjectToDisplay, parentNode.get(), mappingName);
                    });
        }
        return success;
    }

    /**
     * Get the semantic target represented by the given node.
     *
     * @param node
     *            the node which represents the semantic target to recover
     *
     * @return the semantic target represented by the given node
     */
    private EObject getSemanticNode(Node node) {
        return (EObject) this.eObjectResolver.apply(node.getTargetObjectId());
    }

    /**
     * Get the semantic target represented by the diagram.
     *
     * @return the semantic target represented by the diagram
     */
    private EObject getSemanticDiagram() {
        Diagram diagram = this.diagramNavigator.getDiagram();
        return (EObject) this.eObjectResolver.apply(diagram.getTargetObjectId());
    }

    /**
     * Create Edge view, its source view and its target view if needed after DnD of Edge.
     *
     * @param semanticElementEdge
     *            the semantic element on which the domain based edge is based on
     *
     * @return {@code true} if source or target view of edge have been created, {@code false} otherwise
     */
    private Boolean createDnDEdgeView(final EObject semanticElementEdge) {
        // edge are synchronized : no view need to be created
        // only target and source view can be created if they are not already represented on the diagram
        return this.createSourceAndTargetView(semanticElementEdge);
    }

    /**
     * Creates the source and target view needed to represent the domain based edge.
     *
     * @param semanticElementEdge
     *            the semantic element on which the domain based edge is based on
     *
     * @return {@code true} if source or target view have been created, {@code false} otherwise
     */
    private Boolean createSourceAndTargetView(EObject semanticElementEdge) {
        Boolean success = Boolean.FALSE;
        Optional<EObject> optionalSemanticSource = Optional.ofNullable(new ElementDomainBasedEdgeSourceProvider().getSource(semanticElementEdge));
        Optional<? extends EObject> optionalSemanticTarget = new ElementDomainBasedEdgeTargetsProvider().getTargets(semanticElementEdge).stream().findFirst();

        if (optionalSemanticSource.isPresent() && optionalSemanticTarget.isPresent()) {
            EObject semanticSource = optionalSemanticSource.get();
            EObject semanticTarget = optionalSemanticTarget.get();
            Node sourceNode = this.getNodeFromDiagramAndItsChildren(semanticSource);
            Node targetNode = this.getNodeFromDiagramAndItsChildren(semanticTarget);
            if (sourceNode == null) {
                success = this.createEndView(semanticSource);
            }
            if (targetNode == null) {
                success = this.createEndView(semanticTarget);
            }
        }
        return success;
    }

    /**
     * Create semantic element view in its graphical container.
     *
     * @param semanticEnd
     *            the semantic element to represent by a view
     *
     * @return {@code true} if the semantic element view has been created, {@code false} otherwise
     */
    private Boolean createEndView(EObject semanticEnd) {
        Boolean success = Boolean.FALSE;
        EObject semanticEndContainer = semanticEnd.eContainer();
        if (semanticEnd instanceof ActivityNode acitivityNode) {
            EList<ActivityPartition> inPartitions = acitivityNode.getInPartitions();
            if (!inPartitions.isEmpty()) {
                // graphical container is not the semantic container
                semanticEndContainer = inPartitions.get(0);
            }
            EList<InterruptibleActivityRegion> inInterruptibleRegions = acitivityNode.getInInterruptibleRegions();
            if (!inInterruptibleRegions.isEmpty()) {
                // graphical container is not the semantic container
                semanticEndContainer = inInterruptibleRegions.get(0);

            }
        }
        if (semanticEndContainer != null) {
            EObject semanticDiagram = this.getSemanticDiagram();
            if (semanticEndContainer.equals(semanticDiagram)) {
                success = this.viewHelper.createRootView(semanticEnd);
            } else {
                Node node = this.getNodeFromDiagramAndItsChildren(semanticEndContainer);
                success = this.viewHelper.createChildView(semanticEnd, node);
            }
        }
        return success;
    }

    /**
     * Get the node from the diagram and its children nodes that represents the given semantic element.
     *
     * @param semanticElement
     *            the semantic element to retrieve the node from
     *
     * @return the node from the diagram and its children nodes that represents the given semantic element
     * @see #getNodeFromParentNodeAndItsChildren(Node, EObject)
     */
    private Node getNodeFromDiagramAndItsChildren(EObject semanticElement) {
        List<Node> nodes = this.diagramNavigator.getDiagram().getNodes();
        int i = 0;
        boolean isFound = false;
        Node semanticNodeFound = null;
        while (!isFound && i < nodes.size()) {
            Node node = nodes.get(i);
            EObject semanticNode = this.getSemanticNode(node);
            if (semanticElement.equals(semanticNode)) {
                isFound = true;
                semanticNodeFound = node;
            } else {
                semanticNodeFound = this.getNodeFromParentNodeAndItsChildren(node, semanticElement);
                if (semanticNodeFound != null) {
                    isFound = true;
                }
            }
            i++;
        }
        return semanticNodeFound;
    }

    /**
     * Get the node from the parent node and its children nodes that represents the given semantic element.
     *
     * @param parentNode
     *            the parent node to look in
     * @param semanticElement
     *            the semantic element to retrieve the node from
     *
     * @return the node from the parent node and its children nodes that represents the given semantic element
     */
    private Node getNodeFromParentNodeAndItsChildren(Node parentNode, EObject semanticElement) {
        List<Node> nodes = parentNode.getChildNodes();
        int i = 0;
        boolean isFound = false;
        Node semanticNodeFound = null;
        while (!isFound && i < nodes.size()) {
            Node node = nodes.get(i);
            EObject semanticNode = this.getSemanticNode(node);
            if (semanticElement.equals(semanticNode)) {
                isFound = true;
                semanticNodeFound = node;
            } else {
                semanticNodeFound = this.getNodeFromParentNodeAndItsChildren(node, semanticElement);
                if (semanticNodeFound != null) {
                    isFound = true;
                }
            }
            i++;
        }
        return semanticNodeFound;
    }
}
