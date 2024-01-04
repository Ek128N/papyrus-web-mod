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

import java.util.Map;
import java.util.Optional;

import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;

/**
 * Service used to navigate in {@link DiagramDescription}.
 *
 * @author Arthur Daussy
 */
public interface IViewDiagramDescriptionService {

    /**
     * Gets the {@link org.eclipse.sirius.components.view.NodeDescription} from its name.
     *
     * @param diagramDescription
     *            the diagram description
     * @param nodeName
     *            the searched name
     * @return an optional node
     */
    Optional<org.eclipse.sirius.components.view.diagram.NodeDescription> getNodeDescriptionByName(DiagramDescription diagramDescription, String nodeName);

    /**
     * Gets the {@link DiagramDescription} from the converted node map.
     *
     * @param capturedNodeDescriptions
     *            map of converted nodes
     * @return an optional diagrams
     */
    Optional<DiagramDescription> getDiagramDescription(Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> capturedNodeDescriptions);

}
