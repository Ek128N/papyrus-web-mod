/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.papyrus.web.services.representations;

import static org.eclipse.papyrus.uml.domain.services.EMFUtils.allContainedObjectOfType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.papyrus.uml.domain.services.EMFUtils;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;

/**
 * Registry that keeps track of all {@link DiagramDescription}s used in Papyrus application.
 *
 * @author Arthur Daussy
 */
public class PapyrusRepresentationDescriptionRegistry {

    private IDiagramIdProvider idProvider;

    private List<Match> diagrams = new ArrayList<>();

    private Map<String, DiagramDescription> viewDiagramDescriptionById = new HashMap<>();

    private Map<String, org.eclipse.sirius.components.diagrams.description.DiagramDescription> apiDiagramDescriptionById = new HashMap<>();

    private Map<String, DiagramElementDescription> viewDiagramElementDescriptionById = new HashMap<>();

    private Map<String, IDiagramElementDescription> apiDiagramElementDescriptionById = new HashMap<>();

    public PapyrusRepresentationDescriptionRegistry(IDiagramIdProvider idProvider) {
        this.idProvider = idProvider;
    }

    public void add(DiagramDescription description, org.eclipse.sirius.components.diagrams.description.DiagramDescription converted) {
        String viewId = this.idProvider.getId(description);
        String apiId = converted.getId();

        if (!Objects.equals(apiId, viewId)) {
            throw new IllegalStateException("Invalid diagram ids. View id =" + viewId + " API id=" + apiId);
        }
        this.viewDiagramDescriptionById.put(viewId, description);
        this.apiDiagramDescriptionById.put(apiId, converted);

        EMFUtils.allContainedObjectOfType(description, DiagramElementDescription.class).forEach(de -> this.registerViewDiagramElement(de));
        Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> convertedNodes = this.buildConvertedNodeMap(description, converted);
        Map<org.eclipse.sirius.components.view.diagram.EdgeDescription, EdgeDescription> convertedEdges = this.buildConvertedEdgeMap(description, converted);

        this.diagrams.add(new Match(viewId, description, converted, convertedNodes, convertedEdges));

    }

    private DiagramElementDescription registerViewDiagramElement(DiagramElementDescription de) {
        return this.viewDiagramElementDescriptionById.put(this.idProvider.getId(de), de);
    }

    private Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> buildConvertedNodeMap(DiagramDescription diagramDescription,
            org.eclipse.sirius.components.diagrams.description.DiagramDescription converted) {

        Map<String, NodeDescription> nodeIdToDescriptions = new HashMap<>();
        for (NodeDescription node : converted.getNodeDescriptions()) {
            this.collectNote(node, nodeIdToDescriptions);
        }

        this.apiDiagramElementDescriptionById.putAll(nodeIdToDescriptions);

        Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> converterNodes = new HashMap<>();
        allContainedObjectOfType(diagramDescription, org.eclipse.sirius.components.view.diagram.NodeDescription.class).forEach(n -> {
            converterNodes.put(n, nodeIdToDescriptions.get(this.idProvider.getId(n)));
        });

        return converterNodes;
    }

    private Map<org.eclipse.sirius.components.view.diagram.EdgeDescription, EdgeDescription> buildConvertedEdgeMap(DiagramDescription diagramDescription,
            org.eclipse.sirius.components.diagrams.description.DiagramDescription converted) {

        Map<String, EdgeDescription> edgeIdToDescriptions = new HashMap<>();
        for (EdgeDescription edge : converted.getEdgeDescriptions()) {
            edgeIdToDescriptions.put(edge.getId(), edge);
        }

        this.apiDiagramElementDescriptionById.putAll(edgeIdToDescriptions);

        Map<org.eclipse.sirius.components.view.diagram.EdgeDescription, EdgeDescription> convertedEdges = new HashMap<>();
        allContainedObjectOfType(diagramDescription, org.eclipse.sirius.components.view.diagram.EdgeDescription.class).forEach(e -> {
            convertedEdges.put(e, edgeIdToDescriptions.get(this.idProvider.getId(e)));
        });
        return convertedEdges;
    }

    public Optional<org.eclipse.sirius.components.view.diagram.NodeDescription> getViewNodeDescriptionById(String id) {
        return Optional.ofNullable((org.eclipse.sirius.components.view.diagram.NodeDescription) this.viewDiagramElementDescriptionById.get(id));
    }

    public Optional<org.eclipse.sirius.components.view.diagram.EdgeDescription> getViewEdgeDescriptionById(String id) {
        return Optional.ofNullable((org.eclipse.sirius.components.view.diagram.EdgeDescription) this.viewDiagramElementDescriptionById.get(id));
    }

    public Optional<DiagramDescription> getViewDiagramDescriptionById(String id) {
        return this.diagrams.stream().filter(m -> m.getId().equals(id)).map(Match::getViewDiagramDescription).findFirst();
    }

    public Optional<IRepresentationDescription> getApiDiagramDescriptionById(String id) {
        return this.diagrams.stream().filter(m -> m.getId().equals(id)).map(Match::getApiDiagramDescription).findFirst();
    }

    private void collectNote(NodeDescription node, Map<String, NodeDescription> nodeIdToDescriptions) {
        if (node != null) {
            nodeIdToDescriptions.put(node.getId(), node);

            for (NodeDescription child : node.getChildNodeDescriptions()) {
                this.collectNote(child, nodeIdToDescriptions);
            }
            for (NodeDescription child : node.getBorderNodeDescriptions()) {
                this.collectNote(child, nodeIdToDescriptions);
            }
        }

    }

    public Optional<DiagramDescription> getViewDiagramDescriptionByName(String diagramName) {
        return this.diagrams.stream().filter(m -> Objects.equals(diagramName, m.getViewDiagramDescription().getName())).map(Match::getViewDiagramDescription).findFirst();
    }

    public Optional<IRepresentationDescription> getApiDiagramDescriptionByName(String diagramName) {
        return this.diagrams.stream().filter(m -> Objects.equals(diagramName, m.getViewDiagramDescription().getName())).map(Match::getApiDiagramDescription).findFirst();
    }

    public Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> getConvertedNode(String descriptionName) {
        return this.diagrams.stream().filter(m -> Objects.equals(descriptionName, m.getViewDiagramDescription().getName())).map(Match::getConvertedNodes).findFirst().orElse(Collections.emptyMap());
    }

    public Map<org.eclipse.sirius.components.view.diagram.EdgeDescription, EdgeDescription> getConvertedEdges(String descriptionName) {
        return this.diagrams.stream().filter(m -> Objects.equals(descriptionName, m.getViewDiagramDescription().getName())).map(Match::getConvertedEdges).findFirst().orElse(Collections.emptyMap());
    }

    public List<DiagramDescription> getViewDiagrams() {
        return this.diagrams.stream().map(Match::getViewDiagramDescription).toList();
    }

    public List<IRepresentationDescription> getApiDiagrams() {
        return this.diagrams.stream().map(Match::getApiDiagramDescription).toList();
    }

    /**
     * Represents a match between an API description and a View Description
     *
     * @author Arthur Daussy
     */
    private final class Match {

        private final DiagramDescription viewDiagramDescription;

        private final org.eclipse.sirius.components.diagrams.description.DiagramDescription apiDiagramDescription;

        private final Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> convertedNodes;

        private Map<org.eclipse.sirius.components.view.diagram.EdgeDescription, EdgeDescription> convertedEdges;

        private final String id;

        private Match(String id, DiagramDescription viewDiagramDescription, org.eclipse.sirius.components.diagrams.description.DiagramDescription apiDiagramDescription,
                Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> convertedNodes,
                Map<org.eclipse.sirius.components.view.diagram.EdgeDescription, EdgeDescription> convertedEdges) {
            super();
            this.id = Objects.requireNonNull(id);
            this.viewDiagramDescription = Objects.requireNonNull(viewDiagramDescription);
            this.apiDiagramDescription = Objects.requireNonNull(apiDiagramDescription);
            this.convertedNodes = Collections.unmodifiableMap(Objects.requireNonNull(convertedNodes));
            this.convertedEdges = convertedEdges;
        }

        public DiagramDescription getViewDiagramDescription() {
            return this.viewDiagramDescription;
        }

        public String getId() {
            return this.id;
        }

        public IRepresentationDescription getApiDiagramDescription() {
            return this.apiDiagramDescription;
        }

        public Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> getConvertedNodes() {
            return this.convertedNodes;
        }

        public Map<org.eclipse.sirius.components.view.diagram.EdgeDescription, EdgeDescription> getConvertedEdges() {
            return this.convertedEdges;
        }
    }
}
