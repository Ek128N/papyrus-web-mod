/*****************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.templates.service;

import java.util.Objects;

import org.eclipse.papyrus.web.application.templates.projects.PapyrusUMLNatures;
import org.eclipse.papyrus.web.application.templates.service.api.IUMLProjectCheckerService;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IUMLProjectCheckerService}.
 *
 * @author Arthur Daussy
 */
@Service
public class UMLProjectCheckerService implements IUMLProjectCheckerService {

    private final IProjectSearchService projectSearchService;

    private final IProjectSemanticDataSearchService projectSemanticDataSearchService;

    public UMLProjectCheckerService(IProjectSearchService projectSeachService, IProjectSemanticDataSearchService projectSemanticDataSearchService) {
        super();
        this.projectSearchService = Objects.requireNonNull(projectSeachService);
        this.projectSemanticDataSearchService = Objects.requireNonNull(projectSemanticDataSearchService);
    }

    @Override
    public boolean isPapyrusProject(String editingContextId) {
        try {
            return new UUIDParser().parse(editingContextId)
                    .flatMap(semanticDataId -> this.projectSemanticDataSearchService.findBySemanticDataId(AggregateReference.to(semanticDataId)))
                    .map(ProjectSemanticData::getProject)
                    .map(AggregateReference::getId)
                    .flatMap(this.projectSearchService::findById)
                    .map(this::hasUMLNature)
                    .orElse(false);

        } catch (IllegalStateException e) {
            return false;
        }
    }

    private boolean hasUMLNature(Project p) {
        return p.getNatures().stream()
                .anyMatch(n -> PapyrusUMLNatures.UML.equals(n.name()));
    }

}
