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

public class ActivityPartitionUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ActivityPartitionUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createActivityPartitionUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("activityPartition_uml_pageFrom", "uml::ActivityPartition", "aql:'UML'", "");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("activityPartition_uml_page", "uml::ActivityPartition", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createActivityPartitionUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("activityPartition_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsDimension(group);
        addIsExternal(group);
        addVisibility(group);
        addRepresents(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsDimension(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isDimension", "Is dimension", "feature:isDimension", "aql:self.set('isDimension',newValue)",
                "aql:self.getFeatureDescription('isDimension')", "aql:self.eClass().getEStructuralFeature('isDimension').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsExternal(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isExternal", "aql:'Is external'", "feature:isExternal", "aql:self.set('isExternal',newValue)",
                "aql:self.getFeatureDescription('isExternal')", "aql:self.eClass().getEStructuralFeature('isExternal').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addRepresents(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("represents", "aql:'Represents'", "aql:self.getFeatureDescription('represents')",
                "aql:self.eClass().getEStructuralFeature('represents').changeable", "aql:'represents'", "");
        group.getChildren().add(widget);
    }

}
