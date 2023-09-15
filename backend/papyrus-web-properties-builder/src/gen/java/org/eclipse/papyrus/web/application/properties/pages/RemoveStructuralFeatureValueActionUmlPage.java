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

public class RemoveStructuralFeatureValueActionUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public RemoveStructuralFeatureValueActionUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createRemoveStructuralFeatureValueActionUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("removeStructuralFeatureValueAction_uml_pageFrom", "uml::RemoveStructuralFeatureValueAction", "aql:'UML'", "");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("removeStructuralFeatureValueAction_uml_page", "uml::RemoveStructuralFeatureValueAction", "aql:'UML'", "aql:self",
                "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createRemoveStructuralFeatureValueActionUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("removeStructuralFeatureValueAction_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsRemoveDuplicates(group);
        addVisibility(group);
        addObject(group);
        addRemoveAt(group);
        addResult(group);
        addStructuralFeature(group);
        addValue(group);
        addLocalPostcondition(group);
        addLocalPrecondition(group);
        addHandler(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsRemoveDuplicates(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isRemoveDuplicates", "aql:'Is remove duplicates'", "feature:isRemoveDuplicates",
                "aql:self.set('isRemoveDuplicates',newValue)", "aql:self.getFeatureDescription('isRemoveDuplicates')", "aql:self.eClass().getEStructuralFeature('isRemoveDuplicates').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addObject(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("object", "aql:'Object'", "aql:self.getFeatureDescription('object')",
                "aql:self.eClass().getEStructuralFeature('object').changeable", "aql:'object'", "");
        group.getChildren().add(widget);
    }

    protected void addRemoveAt(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("removeAt", "aql:'Remove at'", "aql:self.getFeatureDescription('removeAt')",
                "aql:self.eClass().getEStructuralFeature('removeAt').changeable", "aql:'removeAt'", "");
        group.getChildren().add(widget);
    }

    protected void addResult(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("result", "aql:'Result'", "aql:self.getFeatureDescription('result')",
                "aql:self.eClass().getEStructuralFeature('result').changeable", "aql:'result'", "");
        group.getChildren().add(widget);
    }

    protected void addStructuralFeature(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("structuralFeature", "aql:'Structural feature'", "aql:self.getFeatureDescription('structuralFeature')",
                "aql:self.eClass().getEStructuralFeature('structuralFeature').changeable", "aql:'structuralFeature'", "");
        group.getChildren().add(widget);
    }

    protected void addValue(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("value", "aql:'Value'", "aql:self.getFeatureDescription('value')",
                "aql:self.eClass().getEStructuralFeature('value').changeable", "aql:'value'", "");
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
