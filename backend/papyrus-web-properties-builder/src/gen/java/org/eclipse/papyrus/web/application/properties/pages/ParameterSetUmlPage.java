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
import org.eclipse.papyrus.web.application.properties.ContainmentReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.MultiReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class ParameterSetUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ParameterSetUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createParameterSetUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("parameterSet_uml_page", "uml::ParameterSet", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createParameterSetUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("parameterSet_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addVisibility(group);
        addCondition(group);
        addParameter(group);

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

    protected void addCondition(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("condition") //
                .label("aql:'Condition'") //
                .help("aql:self.getFeatureDescription('condition')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('condition').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('condition')") //
                .isMany(true) //
                .value("feature:condition") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'condition'))") //
                .reorderOperation("aql:self.moveReferenceElement('condition', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

    protected void addParameter(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("parameter") //
                .label("aql:'Parameter'") //
                .help("aql:self.getFeatureDescription('parameter')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('parameter').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('parameter')") //
                .value("feature:parameter") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('parameter')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'parameter')") //
                .removeOperation("aql:item.delete(self, 'parameter'))") //
                .reorderOperation("aql:self.moveReferenceElement('parameter', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('parameter')"); //
        group.getChildren().add(builder.build());
    }

}
