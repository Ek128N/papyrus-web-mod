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

public class PackageImportUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public PackageImportUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createPackageImportUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("packageImport_uml_pageFrom", "uml::PackageImport", "aql:'UML'", "");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("packageImport_uml_page", "uml::PackageImport", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createPackageImportUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("packageImport_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addVisibility(group);
        addImportedPackage(group);

    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addImportedPackage(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("importedPackage", "aql:'Imported package'", "aql:self.getFeatureDescription('importedPackage')", "aql:false",
                "aql:'importedPackage'", "");
        group.getChildren().add(widget);
    }

}
