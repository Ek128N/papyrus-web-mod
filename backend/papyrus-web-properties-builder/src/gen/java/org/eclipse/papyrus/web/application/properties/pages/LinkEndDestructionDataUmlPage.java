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

public class LinkEndDestructionDataUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public LinkEndDestructionDataUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createLinkEndDestructionDataUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("linkEndDestructionData_uml_pageFrom", "uml::LinkEndDestructionData", "aql:'UML'", "");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("linkEndDestructionData_uml_page", "uml::LinkEndDestructionData", "aql:'UML'", "aql:self",
                "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createLinkEndDestructionDataUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("linkEndDestructionData_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addIsDestroyDuplicates(group);
        addDestroyAt(group);
        addEnd(group);
        addValue(group);
        addQualifier(group);

    }

    protected void addIsDestroyDuplicates(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isDestroyDuplicates", "aql:'Is destroy duplicates'", "feature:isDestroyDuplicates",
                "aql:self.set('isDestroyDuplicates',newValue)", "aql:self.getFeatureDescription('isDestroyDuplicates')", "aql:self.eClass().getEStructuralFeature('isDestroyDuplicates').changeable");
        group.getChildren().add(widget);
    }

    protected void addDestroyAt(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("destroyAt", "aql:'Destroy at'", "aql:self.getFeatureDescription('destroyAt')",
                "aql:self.eClass().getEStructuralFeature('destroyAt').changeable", "aql:'destroyAt'", "");
        group.getChildren().add(widget);
    }

    protected void addEnd(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("end", "aql:'End'", "aql:self.getFeatureDescription('end')",
                "aql:self.eClass().getEStructuralFeature('end').changeable", "aql:'end'", "");
        group.getChildren().add(widget);
    }

    protected void addValue(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("value", "aql:'Value'", "aql:self.getFeatureDescription('value')",
                "aql:self.eClass().getEStructuralFeature('value').changeable", "aql:'value'", "");
        group.getChildren().add(widget);
    }

    protected void addQualifier(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("qualifier", "aql:'Qualifier'", "aql:self.getFeatureDescription('qualifier')",
                "aql:self.eClass().getEStructuralFeature('qualifier').changeable", "aql:'qualifier'", "");
        group.getChildren().add(widget);
    }

}
