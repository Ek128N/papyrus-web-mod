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
package org.eclipse.papyrus.web.sirius.contributions;

import java.util.function.Consumer;

import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;

/**
 * Visitor for {@link DiagramDescription}.
 *
 * @author Arthur Daussy
 */
public class DiagramDescriptionVisitor {

    private final DiagramDescription diagramDescription;

    public DiagramDescriptionVisitor(DiagramDescription diagramDescription) {
        super();
        this.diagramDescription = diagramDescription;
    }

    public void visitEdges(Consumer<EdgeDescription> edgeConsumer) {
        for (org.eclipse.sirius.components.diagrams.description.EdgeDescription edge : this.diagramDescription.getEdgeDescriptions()) {
            edgeConsumer.accept(edge);
        }
    }

    public void visitNodes(Consumer<org.eclipse.sirius.components.diagrams.description.NodeDescription> nodeConsumer) {
        for (org.eclipse.sirius.components.diagrams.description.NodeDescription node : this.diagramDescription.getNodeDescriptions()) {
            this.collectNode(node, nodeConsumer);
        }
    }

    private void collectNode(org.eclipse.sirius.components.diagrams.description.NodeDescription node, Consumer<org.eclipse.sirius.components.diagrams.description.NodeDescription> nodeConsumer) {
        if (node != null) {
            nodeConsumer.accept(node);

            for (org.eclipse.sirius.components.diagrams.description.NodeDescription child : node.getChildNodeDescriptions()) {
                this.collectNode(child, nodeConsumer);
            }
            for (org.eclipse.sirius.components.diagrams.description.NodeDescription child : node.getBorderNodeDescriptions()) {
                this.collectNode(child, nodeConsumer);
            }
        }

    }
}
