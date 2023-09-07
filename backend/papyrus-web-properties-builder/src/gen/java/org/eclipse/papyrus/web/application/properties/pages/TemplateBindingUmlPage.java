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

import org.eclipse.papyrus.web.application.properties.ColorRegistry;
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class TemplateBindingUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public TemplateBindingUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createTemplateBindingUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("templateBinding_uml_pageFrom", "uml::TemplateBinding", "aql:'UML'", "");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("templateBinding_uml_page", "uml::TemplateBinding", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createTemplateBindingUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("templateBinding_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addParameterSubstitution(group);

    }

    protected void addParameterSubstitution(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("parameterSubstitution", "aql:'Parameter substitution'", "aql:self.getFeatureDescription('parameterSubstitution')",
                "aql:self.eClass().getEStructuralFeature('parameterSubstitution').changeable", "aql:'parameterSubstitution'", "");
        group.getWidgets().add(widget);
    }

}
