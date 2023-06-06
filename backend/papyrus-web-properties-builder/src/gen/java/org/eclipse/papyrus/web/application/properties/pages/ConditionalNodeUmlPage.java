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

public class ConditionalNodeUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    public ConditionalNodeUmlPage(ViewElementsFactory viewElementFactory) {
        super();
        this.viewElementFactory = viewElementFactory;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createConditionalNodeUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("conditionalNode_uml_pageFrom", "uml::ConditionalNode", "aql:'UML'", "${formPreconditionExpression}");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("conditionalNode_uml_page", "uml::ConditionalNode", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createConditionalNodeUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("conditionalNode_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsAssured(group);
        addIsDeterminate(group);
        addMustIsolate(group);
        addVisibility(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)");
        group.getWidgets().add(widget);
    }

    protected void addIsAssured(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isAssured", "aql:'Is assured'", "feature:isAssured", "aql:self.set('isAssured',newValue)");
        group.getWidgets().add(widget);
    }

    protected void addIsDeterminate(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isDeterminate", "aql:'Is determinate'", "feature:isDeterminate", "aql:self.set('isDeterminate',newValue)");
        group.getWidgets().add(widget);
    }

    protected void addMustIsolate(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("mustIsolate", "aql:'Must isolate'", "feature:mustIsolate", "aql:self.set('mustIsolate',newValue)");
        group.getWidgets().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name");
        group.getWidgets().add(widget);
    }

}
