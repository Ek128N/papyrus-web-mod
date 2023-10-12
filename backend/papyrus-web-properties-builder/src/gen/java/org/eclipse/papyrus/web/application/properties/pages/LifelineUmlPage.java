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

public class LifelineUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public LifelineUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createLifelineUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("lifeline_uml_page", "uml::Lifeline", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createLifelineUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("lifeline_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addVisibility(group);
        addDecomposedAs(group);
        addRepresents(group);
        addSelector(group);

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

    protected void addDecomposedAs(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("decomposedAs", "aql:'Decomposed as'", "aql:self.getFeatureDescription('decomposedAs')",
                "aql:self.eClass().getEStructuralFeature('decomposedAs').changeable", "aql:'decomposedAs'", "");
        group.getChildren().add(widget);
    }

    protected void addRepresents(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("represents", "aql:'Represents'", "aql:self.getFeatureDescription('represents')",
                "aql:self.eClass().getEStructuralFeature('represents').changeable", "aql:'represents'", "");
        group.getChildren().add(widget);
    }

    protected void addSelector(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("selector", "aql:'Selector'", "aql:self.getFeatureDescription('selector')",
                "aql:self.eClass().getEStructuralFeature('selector').changeable", "aql:'selector'", "");
        group.getChildren().add(widget);
    }

}
