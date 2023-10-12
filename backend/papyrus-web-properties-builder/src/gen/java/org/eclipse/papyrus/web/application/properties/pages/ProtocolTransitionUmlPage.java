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

public class ProtocolTransitionUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ProtocolTransitionUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createProtocolTransitionUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("protocolTransition_uml_page", "uml::ProtocolTransition", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createProtocolTransitionUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("protocolTransition_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addKind(group);
        addVisibility(group);
        addEffect(group);
        addGuard(group);
        addPostCondition(group);
        addPreCondition(group);
        addTrigger(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addKind(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("kind", "aql:'Kind'",
                "aql:self.eClass().getEStructuralFeature('kind').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.kind.toString())", "aql:self.set('kind',newValue.instance)",
                "aql:self.eClass().getEStructuralFeature('kind').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name", "aql:self.getFeatureDescription('kind')",
                "aql:self.eClass().getEStructuralFeature('kind').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addEffect(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("effect", "aql:'Effect'", "aql:self.getFeatureDescription('effect')",
                "aql:self.eClass().getEStructuralFeature('effect').changeable", "aql:'effect'", "");
        group.getChildren().add(widget);
    }

    protected void addGuard(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("guard", "aql:'Guard'", "aql:self.getFeatureDescription('guard')",
                "aql:self.eClass().getEStructuralFeature('guard').changeable", "aql:'guard'", "");
        group.getChildren().add(widget);
    }

    protected void addPostCondition(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("postCondition", "aql:'Post condition'", "aql:self.getFeatureDescription('postCondition')",
                "aql:self.eClass().getEStructuralFeature('postCondition').changeable", "aql:'postCondition'", "");
        group.getChildren().add(widget);
    }

    protected void addPreCondition(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("preCondition", "aql:'Pre condition'", "aql:self.getFeatureDescription('preCondition')",
                "aql:self.eClass().getEStructuralFeature('preCondition').changeable", "aql:'preCondition'", "");
        group.getChildren().add(widget);
    }

    protected void addTrigger(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("trigger", "aql:'Trigger'", "aql:self.getFeatureDescription('trigger')",
                "aql:self.eClass().getEStructuralFeature('trigger').changeable", "aql:'trigger'", "");
        group.getChildren().add(widget);
    }

}
