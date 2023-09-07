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

public class TemplateParameterUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public TemplateParameterUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createTemplateParameterUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("templateParameter_uml_pageFrom", "uml::TemplateParameter", "aql:'UML'", "");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("templateParameter_uml_page", "uml::TemplateParameter", "aql:'UML'", "aql:self",
                "aql:not(self.oclIsKindOf(uml::ClassifierTemplateParameter)) and not(self.oclIsKindOf(uml::OperationTemplateParameter)) and not(self.oclIsKindOf(uml::ConnectableElementTemplateParameter)) and not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createTemplateParameterUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("templateParameter_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addDefault(group);
        addOwnedDefault(group);
        addOwnedParameteredElement(group);
        addParameteredElement(group);

    }

    protected void addDefault(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("default", "aql:'Default'", "aql:self.getFeatureDescription('default')",
                "aql:self.eClass().getEStructuralFeature('default').changeable", "aql:'default'", "");
        group.getWidgets().add(widget);
    }

    protected void addOwnedDefault(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("ownedDefault", "aql:'Owned default'", "aql:self.getFeatureDescription('ownedDefault')",
                "aql:self.eClass().getEStructuralFeature('ownedDefault').changeable", "aql:'ownedDefault'", "");
        group.getWidgets().add(widget);
    }

    protected void addOwnedParameteredElement(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("ownedParameteredElement", "aql:'Owned parametered element'",
                "aql:self.getFeatureDescription('ownedParameteredElement')", "aql:self.eClass().getEStructuralFeature('ownedParameteredElement').changeable", "aql:'ownedParameteredElement'", "");
        group.getWidgets().add(widget);
    }

    protected void addParameteredElement(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("parameteredElement", "aql:'Parametered element'", "aql:self.getFeatureDescription('parameteredElement')",
                "aql:self.eClass().getEStructuralFeature('parameteredElement').changeable", "aql:'parameteredElement'", "");
        group.getWidgets().add(widget);
    }

}
