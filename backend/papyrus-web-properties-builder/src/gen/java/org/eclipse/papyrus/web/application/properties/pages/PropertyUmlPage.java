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

import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class PropertyUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    public PropertyUmlPage(ViewElementsFactory viewElementFactory) {
        super();
        this.viewElementFactory = viewElementFactory;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createPropertyUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("property_uml_pageFrom", "uml::Property", "aql:'UML'", "");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("property_uml_page", "uml::Property", "aql:'UML'", "aql:self",
                "aql:not(self.oclIsKindOf(uml::Port)) and not(self.oclIsKindOf(uml::ExtensionEnd)) and not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createPropertyUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("property_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsDerived(group);
        addIsDerivedUnion(group);
        addIsOrdered(group);
        addIsReadOnly(group);
        addIsStatic(group);
        addIsUnique(group);
        addVisibility(group);
        addAggregation(group);
        addMultiplicity(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getWidgets().add(widget);
    }

    protected void addIsDerived(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isDerived", "Is derived", "feature:isDerived", "aql:self.set('isDerived',newValue)",
                "aql:self.getFeatureDescription('isDerived')", "aql:self.eClass().getEStructuralFeature('isDerived').changeable");
        group.getWidgets().add(widget);
    }

    protected void addIsDerivedUnion(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isDerivedUnion", "Is derived union", "feature:isDerivedUnion", "aql:self.set('isDerivedUnion',newValue)",
                "aql:self.getFeatureDescription('isDerivedUnion')", "aql:self.eClass().getEStructuralFeature('isDerivedUnion').changeable");
        group.getWidgets().add(widget);
    }

    protected void addIsOrdered(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isOrdered", "Is ordered", "feature:isOrdered", "aql:self.set('isOrdered',newValue)",
                "aql:self.getFeatureDescription('isOrdered')", "aql:self.eClass().getEStructuralFeature('isOrdered').changeable");
        group.getWidgets().add(widget);
    }

    protected void addIsReadOnly(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isReadOnly", "aql:'Is read only'", "feature:isReadOnly", "aql:self.set('isReadOnly',newValue)",
                "aql:self.getFeatureDescription('isReadOnly')", "aql:self.eClass().getEStructuralFeature('isReadOnly').changeable");
        group.getWidgets().add(widget);
    }

    protected void addIsStatic(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isStatic", "aql:'Is static'", "feature:isStatic", "aql:self.set('isStatic',newValue)",
                "aql:self.getFeatureDescription('isStatic')", "aql:self.eClass().getEStructuralFeature('isStatic').changeable");
        group.getWidgets().add(widget);
    }

    protected void addIsUnique(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isUnique", "aql:'Is unique'", "feature:isUnique", "aql:self.set('isUnique',newValue)",
                "aql:self.getFeatureDescription('isUnique')", "aql:self.eClass().getEStructuralFeature('isUnique').changeable");
        group.getWidgets().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getWidgets().add(widget);
    }

    protected void addAggregation(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("aggregation", "aql:'Aggregation'",
                "aql:self.eClass().getEStructuralFeature('aggregation').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.aggregation.toString())",
                "aql:self.set('aggregation',newValue.instance)", "aql:self.eClass().getEStructuralFeature('aggregation').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('aggregation')", "aql:self.eClass().getEStructuralFeature('aggregation').changeable");
        group.getWidgets().add(widget);
    }

    protected void addMultiplicity(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("multiplicity", "aql:'Multiplicity'", "aql:self.getMultiplicity()",
                "aql:self.oclAsType(uml::MultiplicityElement).setMultiplicity(newValue)", "aql:self.getMultiplicityHelpContent()",
                "aql:self.eClass().getEStructuralFeature('lowerValue').changeable and self.eClass().getEStructuralFeature('upperValue').changeable");
        group.getWidgets().add(widget);
    }

}
