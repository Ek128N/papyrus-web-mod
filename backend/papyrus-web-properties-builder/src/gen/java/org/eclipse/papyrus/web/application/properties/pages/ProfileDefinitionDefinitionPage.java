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

public class ProfileDefinitionDefinitionPage {

    protected final ViewElementsFactory viewElementFactory;

    public ProfileDefinitionDefinitionPage(ViewElementsFactory viewElementFactory) {
        super();
        this.viewElementFactory = viewElementFactory;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createProfileDefinitionDefinitionGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("profileDefinition_definition_pageFrom", "ecore::EPackage", "aql:'Definition'", "${formPreconditionExpression}");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("profileDefinition_definition_page", "ecore::EPackage", "aql:'Definition'", "aql:self", "aql:not(selection->size()>1)");
    }

    protected void createProfileDefinitionDefinitionGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("profileDefinition_definition_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addVersion(group);
        addDate(group);
        addAuthor(group);
        addCopyright(group);
        addComment(group);

    }

    protected void addVersion(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("version", "aql:'Version'", "aql:self.getProfileDefinitionVersion()", "var:self", "aql:'Version'");
        group.getWidgets().add(widget);
    }

    protected void addDate(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("date", "aql:'Date'", "aql:self.getProfileDefinitionDate()", "var:self", "aql:'Date'");
        group.getWidgets().add(widget);
    }

    protected void addAuthor(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("author", "aql:'Author'", "aql:self.getProfileDefinitionAuthor()", "var:self", "aql:'Author'");
        group.getWidgets().add(widget);
    }

    protected void addCopyright(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextAreaDescription("copyright", "aql:'Copyright'", "aql:self.getProfileDefinitionCopyright()", "var:self", "aql:'Copyright'");
        group.getWidgets().add(widget);
    }

    protected void addComment(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextAreaDescription("comment", "aql:'Comment'", "aql:self.getProfileDefinitionComment()", "var:self", "aql:'Comment'");
        group.getWidgets().add(widget);
    }

}
