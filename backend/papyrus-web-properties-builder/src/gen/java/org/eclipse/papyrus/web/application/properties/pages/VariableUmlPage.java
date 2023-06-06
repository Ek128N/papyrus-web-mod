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

public class VariableUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    public VariableUmlPage(ViewElementsFactory viewElementFactory) {
        super();
        this.viewElementFactory = viewElementFactory;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createVariableUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("variable_uml_pageFrom", "uml::Variable", "aql:'UML'", "${formPreconditionExpression}");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("variable_uml_page", "uml::Variable", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createVariableUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("variable_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsOrdered(group);
        addIsUnique(group);
        addVisibility(group);
        addMultiplicity(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)");
        group.getWidgets().add(widget);
    }

    protected void addIsOrdered(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isOrdered", "aql:'Is ordered'", "feature:isOrdered", "aql:self.set('isOrdered',newValue)");
        group.getWidgets().add(widget);
    }

    protected void addIsUnique(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isUnique", "aql:'Is unique'", "feature:isUnique", "aql:self.set('isUnique',newValue)");
        group.getWidgets().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name");
        group.getWidgets().add(widget);
    }

    protected void addMultiplicity(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("multiplicity", "aql:'Multiplicity'", "aql:self.getMultiplicity()",
                "aql:self.oclAsType(uml::MultiplicityElement).setMultiplicity(newValue)");
        group.getWidgets().add(widget);
    }

}
