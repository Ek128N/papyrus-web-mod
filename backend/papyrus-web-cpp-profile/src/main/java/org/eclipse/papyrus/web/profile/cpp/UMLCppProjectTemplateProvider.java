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
package org.eclipse.papyrus.web.profile.cpp;

import java.util.List;

import org.eclipse.sirius.web.services.api.projects.IProjectTemplateProvider;
import org.eclipse.sirius.web.services.api.projects.ProjectTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * Provides generic UML project templates.
 *
 * @author Arthur Daussy
 */
@Configuration
public class UMLCppProjectTemplateProvider implements IProjectTemplateProvider {

    public static final String UML_CPP_TEMPLATE_ID = "UMLCppProject";

    public static final String UML_CPP_SM_TEMPLATE_ID = "UMLCppSMProject";

    @Override
    public List<ProjectTemplate> getProjectTemplates() {
        // @formatter:off
        var cppTemplate = ProjectTemplate.newProjectTemplate(UML_CPP_TEMPLATE_ID)
                .label("C++")
                .imageURL("/images/CppTemplate.png")
                .natures(List.of())
                .build();
        var cppSMProjectTeamplte = ProjectTemplate.newProjectTemplate(UML_CPP_SM_TEMPLATE_ID)
                .label("C++ SM")
                .imageURL("/images/CppSMTemplate.png")
                .natures(List.of())
                .build();
        // @formatter:on
        return List.of(cppTemplate, cppSMProjectTeamplte);
    }

}
