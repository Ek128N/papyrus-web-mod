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

public class PortUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public PortUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createPortUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("port_uml_page", "uml::Port", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createPortUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("port_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsBehavior(group);
        addIsDerived(group);
        addIsDerivedUnion(group);
        addIsOrdered(group);
        addIsService(group);
        addIsConjugated(group);
        addVisibility(group);
        addType(group);
        addMultiplicity(group);
        addDefaultValue(group);
        addProvided(group);
        addRequired(group);
        addSubsettedProperty(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsBehavior(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isBehavior", "Is behavior", "feature:isBehavior", "aql:self.set('isBehavior',newValue)",
                "aql:self.getFeatureDescription('isBehavior')", "aql:self.eClass().getEStructuralFeature('isBehavior').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsDerived(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isDerived", "aql:'Is derived'", "feature:isDerived", "aql:self.set('isDerived',newValue)",
                "aql:self.getFeatureDescription('isDerived')", "aql:self.eClass().getEStructuralFeature('isDerived').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsDerivedUnion(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isDerivedUnion", "aql:'Is derived union'", "feature:isDerivedUnion", "aql:self.set('isDerivedUnion',newValue)",
                "aql:self.getFeatureDescription('isDerivedUnion')", "aql:self.eClass().getEStructuralFeature('isDerivedUnion').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsOrdered(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isOrdered", "aql:'Is ordered'", "feature:isOrdered", "aql:self.set('isOrdered',newValue)",
                "aql:self.getFeatureDescription('isOrdered')", "aql:self.eClass().getEStructuralFeature('isOrdered').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsService(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isService", "aql:'Is service'", "feature:isService", "aql:self.set('isService',newValue)",
                "aql:self.getFeatureDescription('isService')", "aql:self.eClass().getEStructuralFeature('isService').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsConjugated(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isConjugated", "aql:'Is conjugated'", "feature:isConjugated", "aql:self.set('isConjugated',newValue)",
                "aql:self.getFeatureDescription('isConjugated')", "aql:self.eClass().getEStructuralFeature('isConjugated').changeable");
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

    protected void addProvided(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("provided") //
                .label("aql:'Provided'") //
                .help("aql:self.getFeatureDescription('provided')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('provided').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('provided')") //
                .value("feature:provided") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('provided')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'provided')") //
                .removeOperation("aql:item.delete(self, 'provided'))") //
                .reorderOperation("aql:self.moveReferenceElement('provided', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('provided')"); //
        group.getChildren().add(builder.build());
    }

    protected void addRequired(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("required") //
                .label("aql:'Required'") //
                .help("aql:self.getFeatureDescription('required')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('required').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('required')") //
                .value("feature:required") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('required')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'required')") //
                .removeOperation("aql:item.delete(self, 'required'))") //
                .reorderOperation("aql:self.moveReferenceElement('required', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('required')"); //
        group.getChildren().add(builder.build());
    }

    protected void addSubsettedProperty(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("subsettedProperty") //
                .label("aql:'Subsetted property'") //
                .help("aql:self.getFeatureDescription('subsettedProperty')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('subsettedProperty').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('subsettedProperty')") //
                .value("feature:subsettedProperty") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('subsettedProperty')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'subsettedProperty')") //
                .removeOperation("aql:item.delete(self, 'subsettedProperty'))") //
                .reorderOperation("aql:self.moveReferenceElement('subsettedProperty', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('subsettedProperty')"); //
        group.getChildren().add(builder.build());
    }

}
