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
package org.eclipse.papyrus.web.application.templates;

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
public class UMLProjectTemplateProvider implements IProjectTemplateProvider {

    public static final String EMPTY_UML_TEMPLATE = "EmptyUMLTemplate";

    public static final String UML_WITH_PRIMITIVES_TEMPLATE_ID = "DefaultUMLWithPrimitive";

    @Override
    public List<ProjectTemplate> getProjectTemplates() {
        var umlWithPrimitivesTemplate = new ProjectTemplate(UML_WITH_PRIMITIVES_TEMPLATE_ID, "UML", "/images/UML.svg", List.of(new ProjectTemplateNature(PapyrusUMLNatures.UML)));
        var emptyUmlWithPrimitivesTemplate = new ProjectTemplate(EMPTY_UML_TEMPLATE, "UML Empty", "/images/UML.svg", List.of(new ProjectTemplateNature(PapyrusUMLNatures.UML)));
        return List.of(umlWithPrimitivesTemplate, emptyUmlWithPrimitivesTemplate);
    }

}
