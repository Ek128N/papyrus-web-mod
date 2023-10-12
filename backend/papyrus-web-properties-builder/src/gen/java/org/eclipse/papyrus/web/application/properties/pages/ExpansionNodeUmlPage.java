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
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class ExpansionNodeUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ExpansionNodeUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createExpansionNodeUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("expansionNode_uml_page", "uml::ExpansionNode", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createExpansionNodeUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("expansionNode_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsControlType(group);
        addOrdering(group);
        addVisibility(group);
        addSelection(group);
        addRegionAsInput(group);
        addRegionAsOutput(group);
        addType(group);
        addUpperBound(group);
        addInState(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsControlType(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isControlType", "aql:'Is control type'", "feature:isControlType", "aql:self.set('isControlType',newValue)",
                "aql:self.getFeatureDescription('isControlType')", "aql:self.eClass().getEStructuralFeature('isControlType').changeable");
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
        WidgetDescription widget = viewElementFactory.createReferenceDescription("selection", "aql:'Selection'", "aql:self.getFeatureDescription('selection')",
                "aql:self.eClass().getEStructuralFeature('selection').changeable", "aql:'selection'", "");
        group.getChildren().add(widget);
    }

    protected void addRegionAsInput(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("regionAsInput", "aql:'Region as input'", "aql:self.getFeatureDescription('regionAsInput')",
                "aql:self.eClass().getEStructuralFeature('regionAsInput').changeable", "aql:'regionAsInput'", "");
        group.getChildren().add(widget);
    }

    protected void addRegionAsOutput(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("regionAsOutput", "aql:'Region as output'", "aql:self.getFeatureDescription('regionAsOutput')",
                "aql:self.eClass().getEStructuralFeature('regionAsOutput').changeable", "aql:'regionAsOutput'", "");
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

    protected void addInState(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("inState", "aql:'In state'", "aql:self.getFeatureDescription('inState')",
                "aql:self.eClass().getEStructuralFeature('inState').changeable", "aql:'inState'", "");
        group.getChildren().add(widget);
    }

}
