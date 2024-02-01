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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.eclipse.papyrus.web.application.representations.uml.UMLMetamodelHelper;
import org.eclipse.papyrus.web.sirius.contributions.DiagramNavigator;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.uml2.uml.util.UMLSwitch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract switch used for semantic and graphical drop.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class AbstractDropSwitch extends UMLSwitch<Boolean> {

    // Duplicated from org.eclipse.papyrus.web.application.representations.IdBuilder
    // Keep in sync
    // Workaround https://github.com/PapyrusSirius/papyrus-web/issues/165
    protected static final String COMPARTMENT_NODE_SUFFIX = "_CompartmentNode";

    /**
     * Logger used to log error when Drag and Drop fails.
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractDropSwitch.class);

    /**
     * The helper used to create element on a diagram.
     */
    protected IViewHelper viewHelper;

    /**
     * Object that check if an element can be edited.
     */
    protected IEditableChecker editableChecker;

    /**
     * An adapter used to get inverse references.
     */
    protected ECrossReferenceAdapter crossRef;

    /**
     * EObject resolver used to retrieve the semantic target from the selected node.
     */
    protected Function<String, Object> eObjectResolver;

    /**
     * The target node where element should be dropped.
     */
    protected Node targetNode;

    /**
     * The helper used to navigate inside a diagram and/or to its description.
     */
    protected DiagramNavigator diagramNavigator;

    /**
     * Create default view for a given element.
     *
     * @param object
     *            the object to represent with a view
     * @return {@code true} if the view has been created, {@code false} otherwise
     */
    protected Boolean createDefaultView(EObject object) {
        Boolean isDragAndDropValid;
        if (this.targetNode != null) {
            isDragAndDropValid = this.createChildView(object);
        } else {
            isDragAndDropValid = this.viewHelper.createRootView(object);
        }
        return isDragAndDropValid;
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
    protected boolean createChildView(EObject eObjectToDisplay) {
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
    protected boolean createChildView(EObject eObjectToDisplay, String mappingName) {
        boolean success = this.viewHelper.createChildView(eObjectToDisplay, this.targetNode, mappingName);
        if (!success) {
            // Workaround https://github.com/PapyrusSirius/papyrus-web/issues/165
            // If DnD on a container containing compartments then DnD the element in the appropriate compartment instead
            for (Node childNode : this.targetNode.getChildNodes()) {
                NodeDescription compartmentNodeDescription = this.getCompartmentNodeDescription(childNode);
                String domainType = (new UMLMetamodelHelper()).getDomain(eObjectToDisplay.eClass());
                if (compartmentNodeDescription != null && this.canDomainTypeBeAChildNodeDescription(domainType, compartmentNodeDescription)) {
                    success = this.viewHelper.createChildView(eObjectToDisplay, childNode, mappingName);
                }
            }
            if (!success) {
                // Workaround https://github.com/PapyrusSirius/papyrus-web/issues/165
                // If DnD on an icon label element contained by a compartment then DnD the element in the
                // compartment instead
                this.createViewInCompartmentFromSibling(eObjectToDisplay, mappingName);
            }
        }
        return success;
    }

    /**
     * Creates a {@code mappingName} view in the selected node for the provided {@code eObjectToDisplay}.
     * <p>
     * This method only computes the view in the compartment of the sibling element used as target node.
     * </p>
     *
     * @param eObjectToDisplay
     *            the semantic Object to represent on the selected node
     * @param mappingName
     *            the name of the mapping to use to create the view
     */
    @Deprecated
    private void createViewInCompartmentFromSibling(EObject eObjectToDisplay, String mappingName) {
        Optional<Node> parentNode = this.diagramNavigator.getParentNode(this.targetNode);
        parentNode//
                .flatMap(this.diagramNavigator::getDescription)//
                .filter(desc -> desc.getName().endsWith(COMPARTMENT_NODE_SUFFIX)).ifPresent(parentDescription -> {
                    this.viewHelper.createChildView(eObjectToDisplay, parentNode.get(), mappingName);
                });
    }

    /**
     * Check if a given {@code domainType} can be represented with children node description from a given parent
     * {@code compartmentNodeDescription}.
     *
     * @param domainType
     *            the domain type to represent
     * @param parentNodeDescription
     *            the parent node description which contain children node description to represent the domain type
     * @return {@code true} if a given {@code domainType} can be represented with children node description,
     *         {@code false } otherwise.
     */
    private boolean canDomainTypeBeAChildNodeDescription(String domainType, NodeDescription parentNodeDescription) {
        return this.hasReusedChildrenDescriptionWithDomainType(domainType, parentNodeDescription) || this.hasChildrenDescriptionWithDomainType(domainType, parentNodeDescription);
    }

    /**
     * Check if a given {@code domainType} can be represented with direct children node description from a given parent
     * {@code compartmentNodeDescription}.
     *
     * @param domainType
     *            the domain type to represent
     * @param parentNodeDescription
     *            the parent node description which contain children node description to represent the domain type
     * @return {@code true} if a given {@code domainType} can be represented with direct children node description,
     *         {@code false } otherwise.
     */
    private boolean hasReusedChildrenDescriptionWithDomainType(String domainType, NodeDescription parentNodeDescription) {
        for (NodeDescription nodeDescription : parentNodeDescription.getReusedChildNodeDescriptions()) {
            if (domainType.equals(nodeDescription.getDomainType())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a given {@code domainType} can be represented with reused children node description from a given parent
     * {@code compartmentNodeDescription}.
     *
     * @param domainType
     *            the domain type to represent
     * @param parentNodeDescription
     *            the parent node description which contain children node description to represent the domain type
     * @return {@code true} if a given {@code domainType} can be represented with reused children node description,
     *         {@code false } otherwise.
     */
    private boolean hasChildrenDescriptionWithDomainType(String domainType, NodeDescription parentNodeDescription) {
        for (NodeDescription nodeDescription : parentNodeDescription.getChildrenDescriptions()) {
            if (domainType.equals(nodeDescription.getDomainType())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the node description used to represent a given {@link Node} if this node description is a compartment
     * description.
     *
     * @param node
     *            the node with the the description to recover,
     * @return the compartment node description if it exists, {@code null} if the node is not represented with a
     *         compartment node description.
     */
    private NodeDescription getCompartmentNodeDescription(Node node) {
        NodeDescription compartmentDescription = null;
        Optional<NodeDescription> optDescription = this.diagramNavigator.getDescription(node);
        if (optDescription.isPresent()) {
            NodeDescription nodeDescription = optDescription.get();
            if (nodeDescription.getName().endsWith(COMPARTMENT_NODE_SUFFIX)) {
                compartmentDescription = nodeDescription;
            }
        }
        return compartmentDescription;
    }

    /**
     * Get the semantic target represented by the given node.
     *
     * @param node
     *            the node which represents the semantic target to recover
     *
     * @return the semantic target represented by the given node
     */
    protected EObject getSemanticNode(Node node) {
        return this.getSemanticElementFromId(node.getTargetObjectId());
    }

    /**
     * Returns the semantic element matching the provided {@code objectId}.
     * <p>
     * The {@code objectId} is typically retrieved from {@link Node}/{@link Edge} element using
     * {@code getTargetObjectId()}.
     * </p>
     *
     * @param objectId
     *            the identifier of the semantic element
     * @return the semantic element matching the provided {@code objectId}
     */
    private EObject getSemanticElementFromId(String objectId) {
        return (EObject) this.eObjectResolver.apply(objectId);
    }

    /**
     * Get the semantic target represented by the diagram.
     *
     * @return the semantic target represented by the diagram
     */
    protected EObject getSemanticDiagram() {
        Diagram diagram = this.diagramNavigator.getDiagram();
        return (EObject) this.eObjectResolver.apply(diagram.getTargetObjectId());
    }

    /**
     * Returns the {@link Edge} from the {@link Diagram} that represents the given {@code semanticEdge}.
     *
     * @param semanticEdge
     *            the semantic edge to retrieve the {@link Edge} from
     * @return the {@link Edge} from the diagram that represents the given {@code semanticEdge}
     */
    protected Optional<Edge> getEdgeFromDiagram(EObject semanticEdge) {
        return this.diagramNavigator.getDiagram().getEdges().stream()
                .filter(edge -> Objects.equals(semanticEdge, this.getSemanticElementFromId(edge.getTargetObjectId())))
                .findFirst();
    }

    /**
     * Get the {@link Node} from the diagram and its children nodes that represents the given {@code semanticElement}.
     *
     * @param semanticElement
     *            the semantic element to retrieve the {@link Node} from
     *
     * @return the {@link Node} from the diagram and its children nodes that represents the given semantic element
     * @see #getNodeFromParentNodeAndItsChildren(Node, EObject)
     */
    protected Node getNodeFromDiagramAndItsChildren(EObject semanticElement) {
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
    protected Node getNodeFromParentNodeAndItsChildren(Node parentNode, EObject semanticElement) {
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
