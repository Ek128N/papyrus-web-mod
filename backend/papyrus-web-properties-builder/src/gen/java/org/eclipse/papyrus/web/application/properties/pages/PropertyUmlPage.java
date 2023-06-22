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
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.GroupDescription;
import org.eclipse.sirius.components.view.GroupDisplayMode;
import org.eclipse.sirius.components.view.PageDescription;
import org.eclipse.sirius.components.view.WidgetDescription;

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
        return viewElementFactory.createFormDescription("property_uml_pageFrom", "uml::Property", "aql:'UML'", "${formPreconditionExpression}");
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
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')");
        group.getWidgets().add(widget);
    }

    protected void addIsDerived(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isDerived", "Is derived", "feature:isDerived", "aql:self.set('isDerived',newValue)",
                "aql:self.getFeatureDescription('isDerived')");
        group.getWidgets().add(widget);
    }

    protected void addIsDerivedUnion(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isDerivedUnion", "Is derived union", "feature:isDerivedUnion", "aql:self.set('isDerivedUnion',newValue)",
                "aql:self.getFeatureDescription('isDerivedUnion')");
        group.getWidgets().add(widget);
    }

    protected void addIsOrdered(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isOrdered", "Is ordered", "feature:isOrdered", "aql:self.set('isOrdered',newValue)",
                "aql:self.getFeatureDescription('isOrdered')");
        group.getWidgets().add(widget);
    }

    protected void addIsReadOnly(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isReadOnly", "aql:'Is read only'", "feature:isReadOnly", "aql:self.set('isReadOnly',newValue)",
                "aql:self.getFeatureDescription('isReadOnly')");
        group.getWidgets().add(widget);
    }

    protected void addIsStatic(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isStatic", "aql:'Is static'", "feature:isStatic", "aql:self.set('isStatic',newValue)",
                "aql:self.getFeatureDescription('isStatic')");
        group.getWidgets().add(widget);
    }

    protected void addIsUnique(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isUnique", "aql:'Is unique'", "feature:isUnique", "aql:self.set('isUnique',newValue)",
                "aql:self.getFeatureDescription('isUnique')");
        group.getWidgets().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')");
        group.getWidgets().add(widget);
    }

    protected void addAggregation(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("aggregation", "aql:'Aggregation'",
                "aql:self.eClass().getEStructuralFeature('aggregation').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.aggregation.toString())",
                "aql:self.set('aggregation',newValue.instance)", "aql:self.eClass().getEStructuralFeature('aggregation').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('aggregation')");
        group.getWidgets().add(widget);
    }

    protected void addMultiplicity(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("multiplicity", "aql:'Multiplicity'", "aql:self.getMultiplicity()",
                "aql:self.oclAsType(uml::MultiplicityElement).setMultiplicity(newValue)", "aql:self.getMultiplicityHelpContent()");
        group.getWidgets().add(widget);
    }

}
