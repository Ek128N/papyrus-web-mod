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

public class ReadIsClassifiedObjectActionUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ReadIsClassifiedObjectActionUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createReadIsClassifiedObjectActionUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("readIsClassifiedObjectAction_uml_pageFrom", "uml::ReadIsClassifiedObjectAction", "aql:'UML'", "");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("readIsClassifiedObjectAction_uml_page", "uml::ReadIsClassifiedObjectAction", "aql:'UML'", "aql:self",
                "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createReadIsClassifiedObjectActionUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("readIsClassifiedObjectAction_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsDirect(group);
        addVisibility(group);
        addClassifier(group);
        addObject(group);
        addResult(group);
        addLocalPostcondition(group);
        addLocalPrecondition(group);
        addHandler(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsDirect(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isDirect", "aql:'Is direct'", "feature:isDirect", "aql:self.set('isDirect',newValue)",
                "aql:self.getFeatureDescription('isDirect')", "aql:self.eClass().getEStructuralFeature('isDirect').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addClassifier(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("classifier", "aql:'Classifier'", "aql:self.getFeatureDescription('classifier')",
                "aql:self.eClass().getEStructuralFeature('classifier').changeable", "aql:'classifier'", "");
        group.getChildren().add(widget);
    }

    protected void addObject(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("object", "aql:'Object'", "aql:self.getFeatureDescription('object')",
                "aql:self.eClass().getEStructuralFeature('object').changeable", "aql:'object'", "");
        group.getChildren().add(widget);
    }

    protected void addResult(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("result", "aql:'Result'", "aql:self.getFeatureDescription('result')",
                "aql:self.eClass().getEStructuralFeature('result').changeable", "aql:'result'", "");
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

    protected void addHandler(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("handler", "aql:'Handler'", "aql:self.getFeatureDescription('handler')",
                "aql:self.eClass().getEStructuralFeature('handler').changeable", "aql:'handler'", "");
        group.getChildren().add(widget);
    }

}
