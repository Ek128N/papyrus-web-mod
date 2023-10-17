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
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;

/**
 * Utility class to check that a graphical edge has been created in the diagram.
 * <p>
 * This checker validates that the expected graphical edge has been created at the right location in the diagram (in the
 * expected graphical parent). It also validates that the diagram contains the appropriate number of elements after the
 * creation.
 * </p>
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class EdgeCreationGraphicalChecker extends CreationGraphicalChecker {

    private Map<org.eclipse.sirius.components.view.diagram.EdgeDescription, EdgeDescription> convertedEdges;

    /**
     * Initializes the checker with the provided parameters.
     *
     * @param diagramSupplier
     *            a supplier to access and reload the diagram
     * @param graphicalOwnerSupplier
     *            a supplier to access and reload the expected graphical owner of the checked edge
     * @param mappingType
     *            the expected mapping type of the checked edge
     * @param convertedEdges
     *            the diagram-to-description mappings for the edges of the current diagram
     */
    public EdgeCreationGraphicalChecker(Supplier<Diagram> diagramSupplier, Supplier<IDiagramElement> graphicalOwnerSupplier, String mappingType,
            Map<org.eclipse.sirius.components.view.diagram.EdgeDescription, EdgeDescription> convertedEdges) {
        super(diagramSupplier, graphicalOwnerSupplier, mappingType);
        this.convertedEdges = convertedEdges;
    }

    @Override
    protected void checkCreatedElementInstanceOf(IDiagramElement element) {
        assertThat(element).isInstanceOf(Edge.class);
    }

    @Override
    protected void checkCreatedElementMapping(IDiagramElement element) {
        String descriptionId = element.getDescriptionId();
        org.eclipse.sirius.components.view.diagram.EdgeDescription edgeDescription = this.convertedEdges.entrySet().stream()//
                .filter(entry -> entry.getValue().getId().equals(descriptionId))//
                .findFirst()//
                .get()//
                .getKey();
        assertThat(edgeDescription.getName()).isEqualTo(this.mappingType);
    }
}
