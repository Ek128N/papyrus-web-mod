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

public class ParameterUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ParameterUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createParameterUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("parameter_uml_page", "uml::Parameter", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createParameterUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("parameter_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsException(group);
        addIsOrdered(group);
        addIsStream(group);
        addIsUnique(group);
        addDirection(group);
        addEffect(group);
        addVisibility(group);
        addType(group);
        addMultiplicity(group);
        addDefaultValue(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsException(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isException", "Is exception", "feature:isException", "aql:self.set('isException',newValue)",
                "aql:self.getFeatureDescription('isException')", "aql:self.eClass().getEStructuralFeature('isException').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsOrdered(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isOrdered", "aql:'Is ordered'", "feature:isOrdered", "aql:self.set('isOrdered',newValue)",
                "aql:self.getFeatureDescription('isOrdered')", "aql:self.eClass().getEStructuralFeature('isOrdered').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsStream(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isStream", "aql:'Is stream'", "feature:isStream", "aql:self.set('isStream',newValue)",
                "aql:self.getFeatureDescription('isStream')", "aql:self.eClass().getEStructuralFeature('isStream').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsUnique(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isUnique", "aql:'Is unique'", "feature:isUnique", "aql:self.set('isUnique',newValue)",
                "aql:self.getFeatureDescription('isUnique')", "aql:self.eClass().getEStructuralFeature('isUnique').changeable");
        group.getChildren().add(widget);
    }

    protected void addDirection(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("direction", "aql:'Direction'",
                "aql:self.eClass().getEStructuralFeature('direction').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.direction.toString())", "aql:self.set('direction',newValue.instance)",
                "aql:self.eClass().getEStructuralFeature('direction').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name", "aql:self.getFeatureDescription('direction')",
                "aql:self.eClass().getEStructuralFeature('direction').changeable");
        group.getChildren().add(widget);
    }

    protected void addEffect(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("effect", "aql:'Effect'",
                "aql:self.eClass().getEStructuralFeature('effect').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.effect.toString())", "aql:self.set('effect',newValue.instance)",
                "aql:self.eClass().getEStructuralFeature('effect').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name", "aql:self.getFeatureDescription('effect')",
                "aql:self.eClass().getEStructuralFeature('effect').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
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

    protected void addMultiplicity(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("multiplicity", "aql:'Multiplicity'", "aql:self.getMultiplicity()",
                "aql:self.oclAsType(uml::MultiplicityElement).setMultiplicity(newValue)", "aql:self.getMultiplicityHelpContent()",
                "aql:self.eClass().getEStructuralFeature('lowerValue').changeable and self.eClass().getEStructuralFeature('upperValue').changeable");
        group.getChildren().add(widget);
    }

    protected void addDefaultValue(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("defaultValue") //
                .label("aql:'Default value'") //
                .help("aql:self.getFeatureDescription('defaultValue')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('defaultValue').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('defaultValue')") //
                .isMany(false) //
                .value("feature:defaultValue") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'defaultValue'))");
        group.getChildren().add(builder.build());
    }

}
