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

public class TransitionUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public TransitionUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createTransitionUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("transition_uml_page", "uml::Transition", "aql:'UML'", "aql:self",
                "aql:not(self.oclIsKindOf(uml::ProtocolTransition)) and not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createTransitionUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("transition_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addKind(group);
        addTrigger(group);
        addGuard(group);
        addEffect(group);

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

}
