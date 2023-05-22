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

import org.eclipse.papyrus.web.services.api.projects.IProjectTemplateProvider;
import org.eclipse.papyrus.web.services.api.projects.ProjectTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * Provides generic UML project templates.
 *
 * @author Arthur Daussy
 */
@Configuration
public class UMLCppProjectTemplateProvider implements IProjectTemplateProvider {

    public static final String UML_CPP_TEMPLATE_ID = "UMLCppProject"; //$NON-NLS-1$

    public static final String UML_CPP_SM_TEMPLATE_ID = "UMLCppSMProject"; //$NON-NLS-1$

    @Override
    public List<ProjectTemplate> getProjectTemplates() {
        // @formatter:off
        var cppTemplate = ProjectTemplate.newProjectTemplate(UML_CPP_TEMPLATE_ID)
                .label("C++") //$NON-NLS-1$
                .imageURL("/images/CppTemplate.png") //$NON-NLS-1$
                .natures(List.of())
                .build();
        var cppSMProjectTeamplte = ProjectTemplate.newProjectTemplate(UML_CPP_SM_TEMPLATE_ID)
                .label("C++ SM") //$NON-NLS-1$
                .imageURL("/images/CppSMTemplate.png") //$NON-NLS-1$
                .natures(List.of())
                .build();
        // @formatter:on
        return List.of(cppTemplate, cppSMProjectTeamplte);
    }

}
