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
package org.eclipse.papyrus.web.profile.cpp;

import java.util.List;

import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateProvider;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplate;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplateNature;
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
        ProjectTemplateNature umlNature = new ProjectTemplateNature("papyrusweb://nature?kind=uml");
        var cppTemplate = new ProjectTemplate(UML_CPP_TEMPLATE_ID, "C++", "/images/CppTemplate.png", List.of(umlNature));
        var cppSMProjectTemplate = new ProjectTemplate(UML_CPP_SM_TEMPLATE_ID, "C++ SM", "/images/CppSMTemplate.png", List.of(umlNature));
        return List.of(cppTemplate, cppSMProjectTemplate);
    }

}
