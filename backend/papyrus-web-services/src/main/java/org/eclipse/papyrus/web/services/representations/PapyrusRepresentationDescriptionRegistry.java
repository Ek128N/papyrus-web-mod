/*******************************************************************************
 * Copyright (c) 2022 CEA, Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.services.representations;

import static org.eclipse.papyrus.uml.domain.services.EMFUtils.allContainedObjectOfType;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.DiagramElementDescription;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registry that keeps track of all {@link DiagramDescription}s used in Papyrus application.
 *
 * @author Arthur Daussy
 */
public class PapyrusRepresentationDescriptionRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(PapyrusRepresentationDescriptionRegistry.class);

    // Copied from org.eclipse.sirius.components.view.emf.diagram.ViewDiagramDescriptionConverter.idProvider
    private static final Function<DiagramElementDescription, UUID> ID_PROVIDER = (diagramElementDescription) -> {
        // DiagramElementDescription should have a proper id.
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(diagramElementDescription).toString().getBytes());
    };

    private Map<String, RepresentationDescription> registryByName = new HashMap<>();

    private Map<String, IRepresentationDescription> registryById = new HashMap<>();

    private Map<RepresentationDescription, IRepresentationDescription> viewToConverted = new HashMap<>();

    private Map<String, Map<org.eclipse.sirius.components.view.NodeDescription, NodeDescription>> convertedNodeByDiagram = new HashMap<>();

    public void add(RepresentationDescription description, IRepresentationDescription converted) {
        this.registryByName.put(description.getName(), description);
        this.registryById.put(converted.getId(), converted);
        this.viewToConverted.put(description, converted);
        if (description instanceof DiagramDescription && converted instanceof org.eclipse.sirius.components.diagrams.description.DiagramDescription) {
            this.convertedNodeByDiagram.put(description.getName(),
                    this.buildConvertedNodeMap((DiagramDescription) description, (org.eclipse.sirius.components.diagrams.description.DiagramDescription) converted));
        }
    }

    private Map<org.eclipse.sirius.components.view.NodeDescription, NodeDescription> buildConvertedNodeMap(DiagramDescription diagramDescription,
            org.eclipse.sirius.components.diagrams.description.DiagramDescription converted) {

        Map<UUID, NodeDescription> nodeIdToDescriptions = new HashMap<>();
        for (NodeDescription node : converted.getNodeDescriptions()) {
            this.collectNote(node, nodeIdToDescriptions);
        }

        Map<org.eclipse.sirius.components.view.NodeDescription, NodeDescription> converterNodes = new HashMap<>();
        allContainedObjectOfType(diagramDescription, org.eclipse.sirius.components.view.NodeDescription.class).forEach(n -> {
            converterNodes.put(n, nodeIdToDescriptions.get(ID_PROVIDER.apply(n)));
        });

        return converterNodes;
    }

    private void collectNote(NodeDescription node, Map<UUID, NodeDescription> nodeIdToDescriptions) {
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

    public IRepresentationDescription getRepresentationDescriptionById(String id) {
        return this.registryById.get(id);
    }

    public DiagramDescription getViewDiagramDescriptionByName(String diagramName) {
        RepresentationDescription repDescription = this.registryByName.get(diagramName);
        if (repDescription instanceof DiagramDescription) {
            return (DiagramDescription) repDescription;
        }
        return null;
    }

    public IRepresentationDescription getRepresentationDescriptionByName(String diagramName) {
        RepresentationDescription repDescription = this.registryByName.get(diagramName);
        if (repDescription != null) {
            return this.viewToConverted.get(repDescription);
        }
        return null;
    }

    public Map<org.eclipse.sirius.components.view.NodeDescription, NodeDescription> getConvertedNode(String descriptionName) {
        Map<org.eclipse.sirius.components.view.NodeDescription, NodeDescription> convertedNode = this.convertedNodeByDiagram.get(descriptionName);
        if (convertedNode != null) {
            return convertedNode;
        } else {
            LOGGER.warn(MessageFormat.format("Unkown description {0}", descriptionName)); //$NON-NLS-1$
            return Collections.emptyMap();
        }
    }

    public List<RepresentationDescription> getAll() {
        return new ArrayList<>(this.registryByName.values());
    }

}
