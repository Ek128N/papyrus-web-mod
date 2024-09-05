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
package org.eclipse.papyrus.web.application.templates.projects;

import java.util.List;

import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateProvider;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplate;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplateNature;
import org.eclipse.sirius.web.application.studio.services.StudioProjectTemplateProvider;
import org.springframework.context.annotation.Configuration;

/**
 * Provides Studio-specific project templates.
 *
 * @author Arthur Daussy
 */
@Configuration
public class PapyrusStudioProjectTemplatesProvider implements IProjectTemplateProvider {

    public static final String PAPYRUS_STUDIO_TEMPLATE_ID = "papyrus-studio-template";

    @Override
    public List<ProjectTemplate> getProjectTemplates() {

        var papyrusStudioTemplate = new ProjectTemplate(PAPYRUS_STUDIO_TEMPLATE_ID, "Papyrus Studio", "/images/papyrus-studio.png",
                List.of(new ProjectTemplateNature(StudioProjectTemplateProvider.STUDIO_NATURE)));
        return List.of(papyrusStudioTemplate);
    }

}
