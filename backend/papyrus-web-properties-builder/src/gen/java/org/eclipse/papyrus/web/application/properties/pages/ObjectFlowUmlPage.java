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

public class ObjectFlowUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    public ObjectFlowUmlPage(ViewElementsFactory viewElementFactory) {
        super();
        this.viewElementFactory = viewElementFactory;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createObjectFlowUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("objectFlow_uml_pageFrom", "uml::ObjectFlow", "aql:'UML'", "${formPreconditionExpression}");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("objectFlow_uml_page", "uml::ObjectFlow", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createObjectFlowUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("objectFlow_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsMulticast(group);
        addIsMultireceive(group);
        addVisibility(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)");
        group.getWidgets().add(widget);
    }

    protected void addIsMulticast(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isMulticast", "Is multicast", "feature:isMulticast", "aql:self.set('isMulticast',newValue)");
        group.getWidgets().add(widget);
    }

    protected void addIsMultireceive(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isMultireceive", "Is multireceive", "feature:isMultireceive", "aql:self.set('isMultireceive',newValue)");
        group.getWidgets().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name");
        group.getWidgets().add(widget);
    }

}
