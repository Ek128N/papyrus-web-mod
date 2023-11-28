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

public class InteractionConstraintUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public InteractionConstraintUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createInteractionConstraintUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("interactionConstraint_uml_page", "uml::InteractionConstraint", "aql:'UML'", "aql:self",
                "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createInteractionConstraintUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("interactionConstraint_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addVisibility(group);
        addContext(group);
        addMaxint(group);
        addMinint(group);
        addSpecification(group);
        addConstrainedElement(group);

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

    protected void addMaxint(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("maxint") //
                .label("aql:'Maxint'") //
                .help("aql:self.getFeatureDescription('maxint')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('maxint').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('maxint')") //
                .isMany(false) //
                .value("feature:maxint") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'maxint'))");
        group.getChildren().add(builder.build());
    }

    protected void addMinint(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("minint") //
                .label("aql:'Minint'") //
                .help("aql:self.getFeatureDescription('minint')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('minint').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('minint')") //
                .isMany(false) //
                .value("feature:minint") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'minint'))");
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

}
