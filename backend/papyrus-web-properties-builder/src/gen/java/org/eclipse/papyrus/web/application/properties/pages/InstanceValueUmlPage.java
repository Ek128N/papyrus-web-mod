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

public class InstanceValueUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public InstanceValueUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createInstanceValueUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("instanceValue_uml_page", "uml::InstanceValue", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createInstanceValueUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("instanceValue_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addVisibility(group);
        addInstance(group);
        addType(group);

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

    protected void addInstance(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("instance") //
                .label("aql:'Instance'") //
                .help("aql:self.getFeatureDescription('instance')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('instance').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('instance')") //
                .value("feature:instance") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('instance')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'instance')") //
                .unsetOperation("aql:item.delete(self, 'instance'))") //
                .clearOperation("aql:self.clearReference('instance')"); //
        group.getChildren().add(builder.build());
    }

    protected void addType(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("type") //
                .label("aql:'Type'") //
                .help("aql:self.getFeatureDescription('type')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('type').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('type')") //
                .value("feature:type") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('type')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'type')") //
                .unsetOperation("aql:item.delete(self, 'type'))") //
                .clearOperation("aql:self.clearReference('type')"); //
        group.getChildren().add(builder.build());
    }

}
