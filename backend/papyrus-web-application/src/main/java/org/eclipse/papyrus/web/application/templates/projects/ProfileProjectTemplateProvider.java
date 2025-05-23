/*****************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
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
import org.springframework.context.annotation.Configuration;

/**
 * Provides generic Profile project templates.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@Configuration
public class ProfileProjectTemplateProvider implements IProjectTemplateProvider {

    /**
     * ID used to identify Profile project creation.
     */
    public static final String PROFILE_WITH_PRIMITIVES_AND_UML_TEMPLATE_ID = "DefaultProfileWithPrimitiveAndUml";

    @Override
    public List<ProjectTemplate> getProjectTemplates() {
        var profileWithPrimitivesTemplate = new ProjectTemplate(PROFILE_WITH_PRIMITIVES_AND_UML_TEMPLATE_ID, "Profile", "/images/Profile.svg",
                List.of(new ProjectTemplateNature(PapyrusUMLNatures.UML)));
        return List.of(profileWithPrimitivesTemplate);
    }

}
