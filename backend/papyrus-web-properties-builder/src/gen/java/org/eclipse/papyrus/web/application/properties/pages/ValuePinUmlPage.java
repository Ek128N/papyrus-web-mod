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
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class ValuePinUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ValuePinUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createValuePinUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("valuePin_uml_pageFrom", "uml::ValuePin", "aql:'UML'", "");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("valuePin_uml_page", "uml::ValuePin", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createValuePinUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("valuePin_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsControl(group);
        addIsControlType(group);
        addIsOrdered(group);
        addIsUnique(group);
        addOrdering(group);
        addVisibility(group);
        addMultiplicity(group);
        addSelection(group);
        addType(group);
        addUpperBound(group);
        addValue(group);
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

    protected void addMultiplicity(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("multiplicity", "aql:'Multiplicity'", "aql:self.getMultiplicity()",
                "aql:self.oclAsType(uml::MultiplicityElement).setMultiplicity(newValue)", "aql:self.getMultiplicityHelpContent()",
                "aql:self.eClass().getEStructuralFeature('lowerValue').changeable and self.eClass().getEStructuralFeature('upperValue').changeable");
        group.getChildren().add(widget);
    }

    protected void addSelection(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("selection", "aql:'Selection'", "aql:self.getFeatureDescription('selection')",
                "aql:self.eClass().getEStructuralFeature('selection').changeable", "aql:'selection'", "");
        group.getChildren().add(widget);
    }

    protected void addType(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("type", "aql:'Type'", "aql:self.getFeatureDescription('type')",
                "aql:self.eClass().getEStructuralFeature('type').changeable", "aql:'type'", "");
        group.getChildren().add(widget);
    }

    protected void addUpperBound(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("upperBound", "aql:'Upper bound'", "aql:self.getFeatureDescription('upperBound')",
                "aql:self.eClass().getEStructuralFeature('upperBound').changeable", "aql:'upperBound'", "");
        group.getChildren().add(widget);
    }

    protected void addValue(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("value", "aql:'Value'", "aql:self.getFeatureDescription('value')",
                "aql:self.eClass().getEStructuralFeature('value').changeable", "aql:'value'", "");
        group.getChildren().add(widget);
    }

    protected void addInState(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("inState", "aql:'In state'", "aql:self.getFeatureDescription('inState')",
                "aql:self.eClass().getEStructuralFeature('inState').changeable", "aql:'inState'", "");
        group.getChildren().add(widget);
    }

}
