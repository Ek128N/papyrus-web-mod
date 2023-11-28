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
import org.eclipse.papyrus.web.application.properties.MultiReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class ConstraintUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ConstraintUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createConstraintUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("constraint_uml_page", "uml::Constraint", "aql:'UML'", "aql:self",
                "aql:not(self.oclIsKindOf(uml::IntervalConstraint)) and not(self.oclIsKindOf(uml::InteractionConstraint)) and not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createConstraintUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("constraint_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addVisibility(group);
        addContext(group);
        addConstrainedElement(group);
        addSpecification(group);

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

    protected void addContext(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("context") //
                .label("aql:'Context'") //
                .help("aql:self.getFeatureDescription('context')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('context').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('context')") //
                .value("feature:context") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('context')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'context')") //
                .unsetOperation("aql:item.delete(self, 'context'))") //
                .clearOperation("aql:self.clearReference('context')"); //
        group.getChildren().add(builder.build());
    }

    protected void addConstrainedElement(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("constrainedElement") //
                .label("aql:'Constrained element'") //
                .help("aql:self.getFeatureDescription('constrainedElement')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('constrainedElement').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('constrainedElement')") //
                .value("feature:constrainedElement") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('constrainedElement')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'constrainedElement')") //
                .removeOperation("aql:item.delete(self, 'constrainedElement'))") //
                .reorderOperation("aql:self.moveReferenceElement('constrainedElement', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('constrainedElement')"); //
        group.getChildren().add(builder.build());
    }

    protected void addSpecification(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("specification") //
                .label("aql:'Specification'") //
                .help("aql:self.getFeatureDescription('specification')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('specification').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('specification')") //
                .isMany(false) //
                .value("feature:specification") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'specification'))");
        group.getChildren().add(builder.build());
    }

}
