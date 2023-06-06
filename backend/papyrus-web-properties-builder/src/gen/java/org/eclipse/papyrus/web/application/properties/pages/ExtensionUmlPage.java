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
import org.eclipse.sirius.components.view.WidgetDescription;

public class ExtensionUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    public ExtensionUmlPage(ViewElementsFactory viewElementFactory) {
        super();
        this.viewElementFactory = viewElementFactory;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createExtensionUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("extension_uml_pageFrom", "uml::Extension", "aql:'UML'", "${formPreconditionExpression}");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("extension_uml_page", "uml::Extension", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createExtensionUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("extension_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsRequired(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)");
        group.getWidgets().add(widget);
    }

    protected void addIsRequired(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isRequired", "Is required", "feature:isRequired", "aql:self.set('isRequired',newValue)");
        group.getWidgets().add(widget);
    }

}
