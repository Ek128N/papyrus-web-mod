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

public class TemplateParameterSubstitutionUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public TemplateParameterSubstitutionUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createTemplateParameterSubstitutionUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("templateParameterSubstitution_uml_pageFrom", "uml::TemplateParameterSubstitution", "aql:'UML'", "");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("templateParameterSubstitution_uml_page", "uml::TemplateParameterSubstitution", "aql:'UML'", "aql:self",
                "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createTemplateParameterSubstitutionUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("templateParameterSubstitution_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addFormal(group);
        addActual(group);
        addOwnedActual(group);

    }

    protected void addFormal(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("formal", "aql:'Formal'", "aql:self.getFeatureDescription('formal')",
                "aql:self.eClass().getEStructuralFeature('formal').changeable", "aql:'formal'", "");
        group.getChildren().add(widget);
    }

    protected void addActual(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("actual", "aql:'Actual'", "aql:self.getFeatureDescription('actual')",
                "aql:self.eClass().getEStructuralFeature('actual').changeable", "aql:'actual'", "");
        group.getChildren().add(widget);
    }

    protected void addOwnedActual(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("ownedActual", "aql:'Owned actual'", "aql:self.getFeatureDescription('ownedActual')",
                "aql:self.eClass().getEStructuralFeature('ownedActual').changeable", "aql:'ownedActual'", "");
        group.getChildren().add(widget);
    }

}
