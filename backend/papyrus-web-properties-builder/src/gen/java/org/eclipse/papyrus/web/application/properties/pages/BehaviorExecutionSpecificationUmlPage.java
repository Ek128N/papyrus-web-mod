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

public class BehaviorExecutionSpecificationUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public BehaviorExecutionSpecificationUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createBehaviorExcutionSpecificationUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("behaviorExecutionSpecification_uml_page", "uml::BehaviorExecutionSpecification", "aql:'UML'", "aql:self",
                "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createBehaviorExcutionSpecificationUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("behaviorExcutionSpecification_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addVisibility(group);
        addBehavior(group);
        addFinish(group);
        addStart(group);

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

    protected void addBehavior(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("behavior") //
                .label("aql:'Behavior'") //
                .help("aql:self.getFeatureDescription('behavior')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('behavior').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('behavior')") //
                .value("feature:behavior") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('behavior')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'behavior')") //
                .unsetOperation("aql:item.delete(self, 'behavior'))") //
                .clearOperation("aql:self.clearReference('behavior')"); //
        group.getChildren().add(builder.build());
    }

    protected void addFinish(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("finish") //
                .label("aql:'Finish'") //
                .help("aql:self.getFeatureDescription('finish')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('finish').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('finish')") //
                .value("feature:finish") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('finish')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'finish')") //
                .unsetOperation("aql:item.delete(self, 'finish'))") //
                .clearOperation("aql:self.clearReference('finish')"); //
        group.getChildren().add(builder.build());
    }

    protected void addStart(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("start") //
                .label("aql:'Start'") //
                .help("aql:self.getFeatureDescription('start')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('start').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('start')") //
                .value("feature:start") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('start')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'start')") //
                .unsetOperation("aql:item.delete(self, 'start'))") //
                .clearOperation("aql:self.clearReference('start')"); //
        group.getChildren().add(builder.build());
    }

}
