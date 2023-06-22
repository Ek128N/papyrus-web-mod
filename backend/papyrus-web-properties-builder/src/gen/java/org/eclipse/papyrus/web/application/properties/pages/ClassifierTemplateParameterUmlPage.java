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

public class ClassifierTemplateParameterUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    public ClassifierTemplateParameterUmlPage(ViewElementsFactory viewElementFactory) {
        super();
        this.viewElementFactory = viewElementFactory;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createClassifierTemplateParameterUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("classifierTemplateParameter_uml_pageFrom", "uml::ClassifierTemplateParameter", "aql:'UML'", "${formPreconditionExpression}");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("classifierTemplateParameter_uml_page", "uml::ClassifierTemplateParameter", "aql:'UML'", "aql:self",
                "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createClassifierTemplateParameterUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("classifierTemplateParameter_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addAllowSubstitutable(group);

    }

    protected void addAllowSubstitutable(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("allowSubstitutable", "aql:'Allow substitutable'", "feature:allowSubstitutable",
                "aql:self.set('allowSubstitutable',newValue)", "aql:self.getFeatureDescription('allowSubstitutable')");
        group.getWidgets().add(widget);
    }

}
