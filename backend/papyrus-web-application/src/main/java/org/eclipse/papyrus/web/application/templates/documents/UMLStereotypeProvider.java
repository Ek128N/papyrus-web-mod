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
package org.eclipse.papyrus.web.application.templates.documents;

import java.util.List;
import java.util.Objects;

import org.eclipse.papyrus.web.application.templates.projects.PapyrusUMLNatures;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.document.dto.Stereotype;
import org.eclipse.sirius.web.application.document.services.api.IStereotypeProvider;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Nature;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Provides stereotypes for Papyrus Web.
 *
 * @author Arthur Daussy
 */
@Service
public class UMLStereotypeProvider implements IStereotypeProvider {

    public static final String EMPTY_UML = "empty_uml";

    public static final String MODEL_UML = "model_UML";

    public static final String PROFILE_UML = "profile_UML";

    private final IProjectSearchService projectSearchService;

    private final IProjectSemanticDataSearchService projectSemanticDataSearchService;

    public UMLStereotypeProvider(IProjectSearchService projectSearchService, IProjectSemanticDataSearchService projectSemanticDataSearchService) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
        this.projectSemanticDataSearchService = Objects.requireNonNull(projectSemanticDataSearchService);
    }

    @Override
    public List<Stereotype> getStereotypes(IEditingContext editingContext) {
        if (this.isUMLProject(editingContext.getId())) {
            return List.of(
                    new Stereotype(EMPTY_UML, "Empty UML"),
                    new Stereotype(MODEL_UML, "Model UML"),
                    new Stereotype(PROFILE_UML, "Profile UML"));
        }
        return List.of();
    }

    private boolean isUMLProject(String editingContextId) {
        return new UUIDParser().parse(editingContextId)
                .flatMap(semanticDataId -> this.projectSemanticDataSearchService.findBySemanticDataId(AggregateReference.to(semanticDataId)))
                .map(ProjectSemanticData::getProject)
                .map(AggregateReference::getId)
                .flatMap(this.projectSearchService::findById)
                .filter(project -> project.getNatures().stream()
                        .map(Nature::name)
                        .anyMatch(PapyrusUMLNatures.UML::equals))
                .isPresent();
    }

}
