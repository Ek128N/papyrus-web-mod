/*******************************************************************************
 * Copyright (c) 2022 CEA, Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.application.templates;

import java.util.List;

import org.eclipse.sirius.web.services.api.projects.IProjectTemplateProvider;
import org.eclipse.sirius.web.services.api.projects.ProjectTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * Provides generic UML project templates.
 *
 * @author pcdavid
 */
@Configuration
public class UMLProjectTemplateProvider implements IProjectTemplateProvider {

    public static final String UML_WITH_PRIMITIVES_TEMPLATE_ID = "DefaultUMLWithPrimitive";

    @Override
    public List<ProjectTemplate> getProjectTemplates() {
        // @formatter:off
        var umlWithPrimitivesTemplate = ProjectTemplate.newProjectTemplate(UML_WITH_PRIMITIVES_TEMPLATE_ID)
                .label("UML")
                .imageURL("/images/UML.svg")
                .natures(List.of())
                .build();
        // @formatter:on
        return List.of(umlWithPrimitivesTemplate);
    }

}
