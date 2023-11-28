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
import org.eclipse.papyrus.web.application.properties.MultiReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class CombinedFragmentUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public CombinedFragmentUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createCombinedFragmentUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("combinedFragment_uml_page", "uml::CombinedFragment", "aql:'UML'", "aql:self",
                "aql:not(self.oclIsKindOf(uml::ConsiderIgnoreFragment)) and not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createCombinedFragmentUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("combinedFragment_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addInteractionOperator(group);
        addVisibility(group);
        addCovered(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addInteractionOperator(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("interactionOperator", "aql:'Interaction operator'",
                "aql:self.eClass().getEStructuralFeature('interactionOperator').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.interactionOperator.toString())",
                "aql:self.set('interactionOperator',newValue.instance)", "aql:self.eClass().getEStructuralFeature('interactionOperator').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('interactionOperator')", "aql:self.eClass().getEStructuralFeature('interactionOperator').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addCovered(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("covered") //
                .label("aql:'Covered'") //
                .help("aql:self.getFeatureDescription('covered')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('covered').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('covered')") //
                .value("feature:covered") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('covered')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'covered')") //
                .removeOperation("aql:item.delete(self, 'covered'))") //
                .reorderOperation("aql:self.moveReferenceElement('covered', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('covered')"); //
        group.getChildren().add(builder.build());
    }

}
