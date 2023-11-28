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
import org.eclipse.papyrus.web.application.properties.MultiReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class InteractionOperandUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public InteractionOperandUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createInteractionOperandUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("interactionOperand_uml_page", "uml::InteractionOperand", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createInteractionOperandUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("interactionOperand_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addVisibility(group);
        addGuard(group);
        addCovered(group);
        addFragment(group);

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

    protected void addGuard(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("guard") //
                .label("aql:'Guard'") //
                .help("aql:self.getFeatureDescription('guard')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('guard').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('guard')") //
                .isMany(false) //
                .value("feature:guard") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'guard'))");
        group.getChildren().add(builder.build());
    }

    protected void addCovered(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("covered") //
                .label("aql:'Covered'") //
                .help("aql:self.getFeatureDescription('covered')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('covered').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('covered')") //
                .value("feature:covered") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('covered')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'covered')") //
                .removeOperation("aql:item.delete(self, 'covered'))") //
                .reorderOperation("aql:self.moveReferenceElement('covered', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('covered')"); //
        group.getChildren().add(builder.build());
    }

    protected void addFragment(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("fragment") //
                .label("aql:'Fragment'") //
                .help("aql:self.getFeatureDescription('fragment')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('fragment').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('fragment')") //
                .isMany(true) //
                .value("feature:fragment") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'fragment'))") //
                .reorderOperation("aql:self.moveReferenceElement('fragment', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

}
