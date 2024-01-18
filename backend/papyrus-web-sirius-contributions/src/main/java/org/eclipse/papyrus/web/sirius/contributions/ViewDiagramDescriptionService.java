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

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.springframework.stereotype.Service;

/**
 * An implementation of {@link IViewDiagramDescriptionService}.
 *
 * @author Arthur Daussy
 */
@Service
public class ViewDiagramDescriptionService implements IViewDiagramDescriptionService {

    private final IEMFNavigationService emfNavigationService;

    public ViewDiagramDescriptionService(IEMFNavigationService emfNavigationService) {
        super();
        this.emfNavigationService = emfNavigationService;
    }

    @Override
    public Optional<DiagramDescription> getDiagramDescription(Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> capturedNodeDescriptions) {
        return capturedNodeDescriptions.keySet().stream()
                .map(nd -> this.emfNavigationService.getAncestor(DiagramDescription.class, nd))
                .filter(Objects::nonNull)
                .findFirst();
    }

    @Override
    public Optional<NodeDescription> getNodeDescriptionByName(DiagramDescription diagramDescription, String name) {
        List<NodeDescription> matchingElements = this.emfNavigationService.allContainedObjectOfType(diagramDescription, NodeDescription.class)//
                .filter(e -> name.equals(e.getName())).collect(toList());

        if (matchingElements.size() == 1) {
            return Optional.of(matchingElements.get(0));
        } else {
            return Optional.empty();
        }
    }

}
