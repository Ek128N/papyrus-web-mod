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

public class StructuredActivityNodeUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    public StructuredActivityNodeUmlPage(ViewElementsFactory viewElementFactory) {
        super();
        this.viewElementFactory = viewElementFactory;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createStructuredActivityNodeUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("structuredActivityNode_uml_pageFrom", "uml::StructuredActivityNode", "aql:'UML'", "${formPreconditionExpression}");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("structuredActivityNode_uml_page", "uml::StructuredActivityNode", "aql:'UML'", "aql:self",
                "aql:not(self.oclIsKindOf(uml::SequenceNode)) and not(self.oclIsKindOf(uml::ConditionalNode)) and not(self.oclIsKindOf(uml::ExpansionRegion)) and not(self.oclIsKindOf(uml::LoopNode)) and not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createStructuredActivityNodeUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("structuredActivityNode_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addMustIsolate(group);
        addVisibility(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)");
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
