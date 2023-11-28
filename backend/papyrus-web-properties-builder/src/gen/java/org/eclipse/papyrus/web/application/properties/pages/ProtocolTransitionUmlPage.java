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
import org.eclipse.papyrus.web.application.properties.ContainmentReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.MonoReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class ProtocolTransitionUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ProtocolTransitionUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createProtocolTransitionUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("protocolTransition_uml_page", "uml::ProtocolTransition", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createProtocolTransitionUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("protocolTransition_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addKind(group);
        addVisibility(group);
        addEffect(group);
        addGuard(group);
        addPostCondition(group);
        addPreCondition(group);
        addTrigger(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addKind(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("kind", "aql:'Kind'",
                "aql:self.eClass().getEStructuralFeature('kind').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.kind.toString())", "aql:self.set('kind',newValue.instance)",
                "aql:self.eClass().getEStructuralFeature('kind').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name", "aql:self.getFeatureDescription('kind')",
                "aql:self.eClass().getEStructuralFeature('kind').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addEffect(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("effect") //
                .label("aql:'Effect'") //
                .help("aql:self.getFeatureDescription('effect')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('effect').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('effect')") //
                .isMany(false) //
                .value("feature:effect") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'effect'))");
        group.getChildren().add(builder.build());
    }

    protected void addGuard(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("guard") //
                .label("aql:'Guard'") //
                .help("aql:self.getFeatureDescription('guard')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('guard').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('guard')") //
                .value("feature:guard") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('guard')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'guard')") //
                .unsetOperation("aql:item.delete(self, 'guard'))") //
                .clearOperation("aql:self.clearReference('guard')"); //
        group.getChildren().add(builder.build());
    }

    protected void addPostCondition(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("postCondition") //
                .label("aql:'Post condition'") //
                .help("aql:self.getFeatureDescription('postCondition')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('postCondition').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('postCondition')") //
                .value("feature:postCondition") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('postCondition')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'postCondition')") //
                .unsetOperation("aql:item.delete(self, 'postCondition'))") //
                .clearOperation("aql:self.clearReference('postCondition')"); //
        group.getChildren().add(builder.build());
    }

    protected void addPreCondition(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("preCondition") //
                .label("aql:'Pre condition'") //
                .help("aql:self.getFeatureDescription('preCondition')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('preCondition').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('preCondition')") //
                .value("feature:preCondition") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('preCondition')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'preCondition')") //
                .unsetOperation("aql:item.delete(self, 'preCondition'))") //
                .clearOperation("aql:self.clearReference('preCondition')"); //
        group.getChildren().add(builder.build());
    }

    protected void addTrigger(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("trigger") //
                .label("aql:'Trigger'") //
                .help("aql:self.getFeatureDescription('trigger')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('trigger').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('trigger')") //
                .isMany(true) //
                .value("feature:trigger") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'trigger'))") //
                .reorderOperation("aql:self.moveReferenceElement('trigger', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

}
