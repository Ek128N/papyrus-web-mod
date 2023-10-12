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

public class IntervalUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public IntervalUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createIntervalUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("interval_uml_page", "uml::Interval", "aql:'UML'", "aql:self",
                "aql:not(self.oclIsKindOf(uml::DurationInterval)) and not(self.oclIsKindOf(uml::TimeInterval)) and not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createIntervalUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("interval_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addVisibility(group);
        addMin(group);
        addMax(group);
        addType(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addMin(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("min", "aql:'Min'", "aql:self.getFeatureDescription('min')",
                "aql:self.eClass().getEStructuralFeature('min').changeable", "aql:'min'", "");
        group.getChildren().add(widget);
    }

    protected void addMax(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("max", "aql:'Max'", "aql:self.getFeatureDescription('max')",
                "aql:self.eClass().getEStructuralFeature('max').changeable", "aql:'max'", "");
        group.getChildren().add(widget);
    }

    protected void addType(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("type", "aql:'Type'", "aql:self.getFeatureDescription('type')",
                "aql:self.eClass().getEStructuralFeature('type').changeable", "aql:'type'", "");
        group.getChildren().add(widget);
    }

}
