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

public class ActivityUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ActivityUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createActivityUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("activity_uml_pageFrom", "uml::Activity", "aql:'UML'", "");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("activity_uml_page", "uml::Activity", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createActivityUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("activity_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsAbstract(group);
        addIsActive(group);
        addIsReadOnly(group);
        addIsReentrant(group);
        addIsSingleExecution(group);
        addVisibility(group);
        addSpecification(group);
        addPrecondition(group);
        addPostcondition(group);
        addOwnedParameter(group);
        addVariable(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getWidgets().add(widget);
    }

    protected void addIsAbstract(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isAbstract", "Is abstract", "feature:isAbstract", "aql:self.set('isAbstract',newValue)",
                "aql:self.getFeatureDescription('isAbstract')", "aql:self.eClass().getEStructuralFeature('isAbstract').changeable");
        group.getWidgets().add(widget);
    }

    protected void addIsActive(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isActive", "aql:'Is active'", "feature:isActive", "aql:self.set('isActive',newValue)",
                "aql:self.getFeatureDescription('isActive')", "aql:self.eClass().getEStructuralFeature('isActive').changeable");
        group.getWidgets().add(widget);
    }

    protected void addIsReadOnly(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isReadOnly", "aql:'Is read only'", "feature:isReadOnly", "aql:self.set('isReadOnly',newValue)",
                "aql:self.getFeatureDescription('isReadOnly')", "aql:self.eClass().getEStructuralFeature('isReadOnly').changeable");
        group.getWidgets().add(widget);
    }

    protected void addIsReentrant(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isReentrant", "aql:'Is reentrant'", "feature:isReentrant", "aql:self.set('isReentrant',newValue)",
                "aql:self.getFeatureDescription('isReentrant')", "aql:self.eClass().getEStructuralFeature('isReentrant').changeable");
        group.getWidgets().add(widget);
    }

    protected void addIsSingleExecution(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isSingleExecution", "aql:'Is single execution'", "feature:isSingleExecution",
                "aql:self.set('isSingleExecution',newValue)", "aql:self.getFeatureDescription('isSingleExecution')", "aql:self.eClass().getEStructuralFeature('isSingleExecution').changeable");
        group.getWidgets().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getWidgets().add(widget);
    }

    protected void addSpecification(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("specification", "aql:'Specification'", "aql:self.getFeatureDescription('specification')",
                "aql:self.eClass().getEStructuralFeature('specification').changeable", "aql:'specification'", "");
        group.getWidgets().add(widget);
    }

    protected void addPrecondition(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("precondition", "aql:'Precondition'", "aql:self.getFeatureDescription('precondition')",
                "aql:self.eClass().getEStructuralFeature('precondition').changeable", "aql:'precondition'", "");
        group.getWidgets().add(widget);
    }

    protected void addPostcondition(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("postcondition", "aql:'Postcondition'", "aql:self.getFeatureDescription('postcondition')",
                "aql:self.eClass().getEStructuralFeature('postcondition').changeable", "aql:'postcondition'", "");
        group.getWidgets().add(widget);
    }

    protected void addOwnedParameter(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("ownedParameter", "aql:'Owned parameter'", "aql:self.getFeatureDescription('ownedParameter')",
                "aql:self.eClass().getEStructuralFeature('ownedParameter').changeable", "aql:'ownedParameter'", "");
        group.getWidgets().add(widget);
    }

    protected void addVariable(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("variable", "aql:'Variable'", "aql:self.getFeatureDescription('variable')",
                "aql:self.eClass().getEStructuralFeature('variable').changeable", "aql:'variable'", "");
        group.getWidgets().add(widget);
    }

}
