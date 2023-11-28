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
import org.eclipse.papyrus.web.application.properties.MonoReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class ElementImportUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ElementImportUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createElementImportUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("elementImport_uml_page", "uml::ElementImport", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createElementImportUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("elementImport_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addAlias(group);
        addVisibility(group);
        addImportedElement(group);

    }

    protected void addAlias(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("alias", "aql:'Alias'", "feature:alias", "aql:self.set('alias',newValue)", "aql:self.getFeatureDescription('alias')",
                "aql:self.eClass().getEStructuralFeature('alias').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addImportedElement(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("importedElement") //
                .label("aql:'Imported element'") //
                .help("aql:self.getFeatureDescription('importedElement')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('importedElement').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('importedElement')") //
                .value("feature:importedElement") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('importedElement')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'importedElement')") //
                .unsetOperation("aql:item.delete(self, 'importedElement'))") //
                .clearOperation("aql:self.clearReference('importedElement')"); //
        group.getChildren().add(builder.build());
    }

}
