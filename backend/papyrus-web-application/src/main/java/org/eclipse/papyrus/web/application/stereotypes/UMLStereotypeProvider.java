/*****************************************************************************
 * Copyright (c) 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.stereotypes;

import java.util.List;
import java.util.Objects;

import org.eclipse.papyrus.web.application.templates.PapyrusUMLNatures;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.document.dto.Stereotype;
import org.eclipse.sirius.web.application.document.services.api.IStereotypeProvider;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Nature;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
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

    public UMLStereotypeProvider(IProjectSearchService projectSearchService) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
    }

    @Override
    public List<Stereotype> getStereotypes(IEditingContext editingContext) {
        var isFlowProject = new UUIDParser().parse(editingContext.getId())
                .flatMap(this.projectSearchService::findById)
                .filter(project -> project.getNatures().stream()
                        .map(Nature::name)
                        .anyMatch(PapyrusUMLNatures.UML::equals))
                .isPresent();

        if (isFlowProject) {
            return List.of(
                    new Stereotype(EMPTY_UML, "Empty UML"),
                    new Stereotype(MODEL_UML, "Model UML"),
                    new Stereotype(PROFILE_UML, "Profile UML"));
        }
        return List.of();
    }

}
