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

public class InformationFlowUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public InformationFlowUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createInformationFlowUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("informationFlow_uml_page", "uml::InformationFlow", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createInformationFlowUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("informationFlow_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addVisibility(group);
        addConveyed(group);
        addRealization(group);
        addRealizingActivityEdge(group);
        addRealizingConnector(group);
        addRealizingMessage(group);

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

    protected void addConveyed(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("conveyed", "aql:'Conveyed'", "aql:self.getFeatureDescription('conveyed')",
                "aql:self.eClass().getEStructuralFeature('conveyed').changeable", "aql:'conveyed'", "");
        group.getChildren().add(widget);
    }

    protected void addRealization(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("realization", "aql:'Realization'", "aql:self.getFeatureDescription('realization')",
                "aql:self.eClass().getEStructuralFeature('realization').changeable", "aql:'realization'", "");
        group.getChildren().add(widget);
    }

    protected void addRealizingActivityEdge(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("realizingActivityEdge", "aql:'Realizing activity edge'", "aql:self.getFeatureDescription('realizingActivityEdge')",
                "aql:self.eClass().getEStructuralFeature('realizingActivityEdge').changeable", "aql:'realizingActivityEdge'", "");
        group.getChildren().add(widget);
    }

    protected void addRealizingConnector(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("realizingConnector", "aql:'Realizing connector'", "aql:self.getFeatureDescription('realizingConnector')",
                "aql:self.eClass().getEStructuralFeature('realizingConnector').changeable", "aql:'realizingConnector'", "");
        group.getChildren().add(widget);
    }

    protected void addRealizingMessage(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("realizingMessage", "aql:'Realizing message'", "aql:self.getFeatureDescription('realizingMessage')",
                "aql:self.eClass().getEStructuralFeature('realizingMessage').changeable", "aql:'realizingMessage'", "");
        group.getChildren().add(widget);
    }

}
