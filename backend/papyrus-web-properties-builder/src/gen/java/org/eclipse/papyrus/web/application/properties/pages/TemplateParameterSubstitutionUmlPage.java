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

package org.eclipse.papyrus.web.application.properties.pages;

import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.GroupDescription;
import org.eclipse.sirius.components.view.GroupDisplayMode;
import org.eclipse.sirius.components.view.PageDescription;

public class TemplateParameterSubstitutionUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    public TemplateParameterSubstitutionUmlPage(ViewElementsFactory viewElementFactory) {
        super();
        this.viewElementFactory = viewElementFactory;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createTemplateParameterSubstitutionUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("templateParameterSubstitution_uml_pageFrom", "uml::TemplateParameterSubstitution", "aql:'UML'", "${formPreconditionExpression}");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("templateParameterSubstitution_uml_page", "uml::TemplateParameterSubstitution", "aql:'UML'", "aql:self",
                "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createTemplateParameterSubstitutionUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("templateParameterSubstitution_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

    }

}
