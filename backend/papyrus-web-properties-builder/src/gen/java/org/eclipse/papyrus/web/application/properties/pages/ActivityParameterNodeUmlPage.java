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

public class ActivityParameterNodeUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    public ActivityParameterNodeUmlPage(ViewElementsFactory viewElementFactory) {
        super();
        this.viewElementFactory = viewElementFactory;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createActivityParameterNodeUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("activityParameterNode_uml_pageFrom", "uml::ActivityParameterNode", "aql:'UML'", "${formPreconditionExpression}");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("activityParameterNode_uml_page", "uml::ActivityParameterNode", "aql:'UML'", "aql:self",
                "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createActivityParameterNodeUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("activityParameterNode_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsControlType(group);
        addOrdering(group);
        addVisibility(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)");
        group.getWidgets().add(widget);
    }

    protected void addIsControlType(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isControlType", "aql:'Is control type'", "feature:isControlType", "aql:self.set('isControlType',newValue)");
        group.getWidgets().add(widget);
    }

    protected void addOrdering(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("ordering", "aql:'Ordering'",
                "aql:self.eClass().getEStructuralFeature('ordering').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.ordering.toString())", "aql:self.set('ordering',newValue.instance)",
                "aql:self.eClass().getEStructuralFeature('ordering').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name");
        group.getWidgets().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name");
        group.getWidgets().add(widget);
    }

}
