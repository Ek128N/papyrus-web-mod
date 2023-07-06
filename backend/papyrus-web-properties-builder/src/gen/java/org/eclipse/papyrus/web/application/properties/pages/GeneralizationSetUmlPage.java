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
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class GeneralizationSetUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    public GeneralizationSetUmlPage(ViewElementsFactory viewElementFactory) {
        super();
        this.viewElementFactory = viewElementFactory;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createGeneralizationSetUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("generalizationSet_uml_pageFrom", "uml::GeneralizationSet", "aql:'UML'", "");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("generalizationSet_uml_page", "uml::GeneralizationSet", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createGeneralizationSetUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("generalizationSet_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsCovering(group);
        addIsDisjoint(group);
        addVisibility(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getWidgets().add(widget);
    }

    protected void addIsCovering(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isCovering", "Is covering", "feature:isCovering", "aql:self.set('isCovering',newValue)",
                "aql:self.getFeatureDescription('isCovering')", "aql:self.eClass().getEStructuralFeature('isCovering').changeable");
        group.getWidgets().add(widget);
    }

    protected void addIsDisjoint(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isDisjoint", "Is disjoint", "feature:isDisjoint", "aql:self.set('isDisjoint',newValue)",
                "aql:self.getFeatureDescription('isDisjoint')", "aql:self.eClass().getEStructuralFeature('isDisjoint').changeable");
        group.getWidgets().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getWidgets().add(widget);
    }

}
