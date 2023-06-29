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
package org.eclipse.papyrus.web.application.configuration;

import java.util.List;

import org.eclipse.sirius.web.services.api.projects.IProjectTemplateProvider;
import org.eclipse.sirius.web.services.api.projects.Nature;
import org.eclipse.sirius.web.services.api.projects.ProjectTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * Provides Studio-specific project templates.
 *
 * @author pcdavid
 */
@Configuration
public class StudioProjectTemplatesProvider implements IProjectTemplateProvider {

    public static final String STUDIO_NATURE = "siriusComponents://nature?kind=studio";

    public static final String PAPYRUS_STUDIO_TEMPLATE_ID = "papyrus-studio-template";

    public static final String STUDIO_TEMPLATE_ID = "studio-template";

    @Override
    public List<ProjectTemplate> getProjectTemplates() {
        var studioTemplate = ProjectTemplate.newProjectTemplate(STUDIO_TEMPLATE_ID)//
                .label("Studio")//
                .imageURL("/images/Studio-Template.png")//
                .natures(List.of(new Nature(STUDIO_NATURE))).build();

        var papyrusStudioTemplate = ProjectTemplate.newProjectTemplate(PAPYRUS_STUDIO_TEMPLATE_ID)//
                .label("Papyrus Studio")//
                .imageURL("/images/papyrus-studio.png")//
                .natures(List.of(new Nature(STUDIO_NATURE))).build();
        return List.of(studioTemplate, papyrusStudioTemplate);
    }

}
