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

public class OutputPinUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public OutputPinUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createOutputPinUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("outputPin_uml_page", "uml::OutputPin", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createOutputPinUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("outputPin_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsControl(group);
        addIsControlType(group);
        addIsOrdered(group);
        addIsUnique(group);
        addOrdering(group);
        addVisibility(group);
        addSelection(group);
        addType(group);
        addMultiplicity(group);
        addUpperBound(group);
        addInState(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsControl(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isControl", "aql:'Is control'", "feature:isControl", "aql:self.set('isControl',newValue)",
                "aql:self.getFeatureDescription('isControl')", "aql:self.eClass().getEStructuralFeature('isControl').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsControlType(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isControlType", "aql:'Is control type'", "feature:isControlType", "aql:self.set('isControlType',newValue)",
                "aql:self.getFeatureDescription('isControlType')", "aql:self.eClass().getEStructuralFeature('isControlType').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsOrdered(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isOrdered", "aql:'Is ordered'", "feature:isOrdered", "aql:self.set('isOrdered',newValue)",
                "aql:self.getFeatureDescription('isOrdered')", "aql:self.eClass().getEStructuralFeature('isOrdered').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsUnique(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isUnique", "aql:'Is unique'", "feature:isUnique", "aql:self.set('isUnique',newValue)",
                "aql:self.getFeatureDescription('isUnique')", "aql:self.eClass().getEStructuralFeature('isUnique').changeable");
        group.getChildren().add(widget);
    }

    protected void addOrdering(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("ordering", "aql:'Ordering'",
                "aql:self.eClass().getEStructuralFeature('ordering').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.ordering.toString())", "aql:self.set('ordering',newValue.instance)",
                "aql:self.eClass().getEStructuralFeature('ordering').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name", "aql:self.getFeatureDescription('ordering')",
                "aql:self.eClass().getEStructuralFeature('ordering').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addSelection(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("selection") //
                .label("aql:'Selection'") //
                .help("aql:self.getFeatureDescription('selection')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('selection').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('selection')") //
                .value("feature:selection") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('selection')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'selection')") //
                .unsetOperation("aql:item.delete(self, 'selection'))") //
                .clearOperation("aql:self.clearReference('selection')"); //
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

    protected void addMultiplicity(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("multiplicity", "aql:'Multiplicity'", "aql:self.getMultiplicity()",
                "aql:self.oclAsType(uml::MultiplicityElement).setMultiplicity(newValue)", "aql:self.getMultiplicityHelpContent()",
                "aql:self.eClass().getEStructuralFeature('lowerValue').changeable and self.eClass().getEStructuralFeature('upperValue').changeable");
        group.getChildren().add(widget);
    }

    protected void addUpperBound(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("upperBound") //
                .label("aql:'Upper bound'") //
                .help("aql:self.getFeatureDescription('upperBound')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('upperBound').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('upperBound')") //
                .isMany(false) //
                .value("feature:upperBound") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'upperBound'))");
        group.getChildren().add(builder.build());
    }

    protected void addInState(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("inState") //
                .label("aql:'In state'") //
                .help("aql:self.getFeatureDescription('inState')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('inState').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('inState')") //
                .value("feature:inState") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('inState')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'inState')") //
                .removeOperation("aql:item.delete(self, 'inState'))") //
                .reorderOperation("aql:self.moveReferenceElement('inState', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('inState')"); //
        group.getChildren().add(builder.build());
    }

}
