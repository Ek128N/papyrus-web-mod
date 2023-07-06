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

public class PackageUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    public PackageUmlPage(ViewElementsFactory viewElementFactory) {
        super();
        this.viewElementFactory = viewElementFactory;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createPackageUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("package_uml_pageFrom", "uml::Package", "aql:'UML'", "");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("package_uml_page", "uml::Package", "aql:'UML'", "aql:self",
                "aql:not(self.oclIsKindOf(uml::Model)) and not(self.oclIsKindOf(uml::Profile)) and not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createPackageUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("package_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addUri(group);
        addVisibility(group);
        addLocation(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getWidgets().add(widget);
    }

    protected void addUri(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("uri", "aql:'URI'", "aql:self.URI", "aql:self.set('URI',newValue)", "aql:self.getFeatureDescription('URI')",
                "aql:self.eClass().getEStructuralFeature('URI').changeable");
        group.getWidgets().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getWidgets().add(widget);
    }

    protected void addLocation(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("location", "aql:'Location'", "aql:self.getLocation()", "var:self", "aql:'The location of imported package'",
                "aql:false");
        group.getWidgets().add(widget);
    }

}
