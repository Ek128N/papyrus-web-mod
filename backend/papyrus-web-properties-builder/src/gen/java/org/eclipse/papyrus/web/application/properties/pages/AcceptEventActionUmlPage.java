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

public class AcceptEventActionUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public AcceptEventActionUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createAcceptEventActionUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("acceptEventAction_uml_pageFrom", "uml::AcceptEventAction", "aql:'UML'", "");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("acceptEventAction_uml_page", "uml::AcceptEventAction", "aql:'UML'", "aql:self",
                "aql:not(self.oclIsKindOf(uml::AcceptCallAction)) and not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createAcceptEventActionUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("acceptEventAction_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsUnmarshall(group);
        addVisibility(group);
        addLocalPostcondition(group);
        addLocalPrecondition(group);
        addResult(group);
        addTrigger(group);
        addHandler(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsUnmarshall(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isUnmarshall", "aql:'Is unmarshall'", "feature:isUnmarshall", "aql:self.set('isUnmarshall',newValue)",
                "aql:self.getFeatureDescription('isUnmarshall')", "aql:self.eClass().getEStructuralFeature('isUnmarshall').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addLocalPostcondition(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("localPostcondition", "aql:'Local postcondition'", "aql:self.getFeatureDescription('localPostcondition')",
                "aql:self.eClass().getEStructuralFeature('localPostcondition').changeable", "aql:'localPostcondition'", "");
        group.getChildren().add(widget);
    }

    protected void addLocalPrecondition(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("localPrecondition", "aql:'Local precondition'", "aql:self.getFeatureDescription('localPrecondition')",
                "aql:self.eClass().getEStructuralFeature('localPrecondition').changeable", "aql:'localPrecondition'", "");
        group.getChildren().add(widget);
    }

    protected void addResult(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("result", "aql:'Result'", "aql:self.getFeatureDescription('result')",
                "aql:self.eClass().getEStructuralFeature('result').changeable", "aql:'result'", "");
        group.getChildren().add(widget);
    }

    protected void addTrigger(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("trigger", "aql:'Trigger'", "aql:self.getFeatureDescription('trigger')",
                "aql:self.eClass().getEStructuralFeature('trigger').changeable", "aql:'trigger'", "");
        group.getChildren().add(widget);
    }

    protected void addHandler(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("handler", "aql:'Handler'", "aql:self.getFeatureDescription('handler')",
                "aql:self.eClass().getEStructuralFeature('handler').changeable", "aql:'handler'", "");
        group.getChildren().add(widget);
    }

}
