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
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class LinkEndDataUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public LinkEndDataUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createLinkEndDataUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("linkEndData_uml_page", "uml::LinkEndData", "aql:'UML'", "aql:self",
                "aql:not(self.oclIsKindOf(uml::LinkEndCreationData)) and not(self.oclIsKindOf(uml::LinkEndDestructionData)) and not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createLinkEndDataUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("linkEndData_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addEnd(group);
        addValue(group);
        addQualifier(group);

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
