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

public class GeneralOrderingUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public GeneralOrderingUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createGeneralOrderingUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("generalOrdering_uml_page", "uml::GeneralOrdering", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createGeneralOrderingUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("generalOrdering_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addVisibility(group);
        addBefore(group);
        addAfter(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addBefore(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("before") //
                .label("aql:'Before'") //
                .help("aql:self.getFeatureDescription('before')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('before').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('before')") //
                .value("feature:before") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('before')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'before')") //
                .unsetOperation("aql:item.delete(self, 'before'))") //
                .clearOperation("aql:self.clearReference('before')"); //
        group.getChildren().add(builder.build());
    }

    protected void addAfter(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("after") //
                .label("aql:'After'") //
                .help("aql:self.getFeatureDescription('after')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('after').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('after')") //
                .value("feature:after") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('after')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'after')") //
                .unsetOperation("aql:item.delete(self, 'after'))") //
                .clearOperation("aql:self.clearReference('after')"); //
        group.getChildren().add(builder.build());
    }

}
