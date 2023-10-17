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
package org.eclipse.papyrus.web.application.tools.checker;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.view.diagram.NodeDescription;

/**
 * Utility class to check that a graphical node has been created in the diagram.
 * <p>
 * This checker validates that the expected graphical node has been created at the right location in the diagram (in the
 * expected graphical parent). It also validates that the diagram contains the appropriate number of elements after the
 * creation.
 * </p>
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class NodeCreationGraphicalChecker extends CreationGraphicalChecker {

    private Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes;

    /**
     * Initializes the checker with the provided parameters.
     * <p>
     * The provided {@code graphicalOwnerSupplier} can be set to {@code null} to check that the element has been created
     * on the diagram.
     * </p>
     *
     * @param diagramSupplier
     *            a supplier to access and reload the diagram
     * @param graphicalOwnerSupplier
     *            a supplier to access and reload the expected graphical owner of the checked node
     * @param mappingType
     *            the expected mapping type of the checked node
     * @param convertedNodes
     *            the diagram-to-description mappings for the nodes of the current diagram
     */
    public NodeCreationGraphicalChecker(Supplier<Diagram> diagramSupplier, Supplier<IDiagramElement> graphicalOwnerSupplier, String mappingType,
            Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes) {
        super(diagramSupplier, graphicalOwnerSupplier, mappingType);
        this.convertedNodes = convertedNodes;
    }

    @Override
    protected void checkCreatedElementInstanceOf(IDiagramElement element) {
        assertThat(element).isInstanceOf(Node.class);
    }

    @Override
    protected void checkCreatedElementMapping(IDiagramElement element) {
        String descriptionId = element.getDescriptionId();
        NodeDescription nodeDescription = this.convertedNodes.entrySet().stream()//
                .filter(entry -> entry.getValue().getId().equals(descriptionId))//
                .findFirst()//
                .get()//
                .getKey();
        assertThat(nodeDescription.getName()).isEqualTo(this.mappingType);
    }
}
