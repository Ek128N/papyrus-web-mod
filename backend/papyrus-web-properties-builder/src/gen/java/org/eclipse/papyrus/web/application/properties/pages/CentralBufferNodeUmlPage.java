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

public class CentralBufferNodeUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public CentralBufferNodeUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createCentralBufferNodeUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("centralBufferNode_uml_pageFrom", "uml::CentralBufferNode", "aql:'UML'", "");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("centralBufferNode_uml_page", "uml::CentralBufferNode", "aql:'UML'", "aql:self",
                "aql:not(self.oclIsKindOf(uml::DataStoreNode)) and not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createCentralBufferNodeUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("centralBufferNode_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsControlType(group);
        addOrdering(group);
        addVisibility(group);
        addSelection(group);
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
