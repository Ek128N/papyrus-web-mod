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
package org.eclipse.papyrus.web.profile.java;

import java.util.List;

import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateProvider;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplate;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplateNature;
import org.springframework.context.annotation.Configuration;

/**
 * Provides generic UML project templates.
 *
 * @author pcdavid
 */
@Configuration
public class UMLJavaTemplateProvider implements IProjectTemplateProvider {

    public static final String UML_JAVA_TEMPLATE_ID = "UMLJavaTemplate";

    @Override
    public List<ProjectTemplate> getProjectTemplates() {
        var umlWithPrimitivesTemplate = new ProjectTemplate(UML_JAVA_TEMPLATE_ID, "Java", "/images/JavaTemplate.png", List.of(new ProjectTemplateNature("papyrusweb://nature?kind=uml")));
        return List.of(umlWithPrimitivesTemplate);
    }

}
