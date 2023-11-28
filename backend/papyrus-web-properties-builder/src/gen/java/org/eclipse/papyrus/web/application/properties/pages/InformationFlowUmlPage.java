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
        var builder = new MultiReferenceWidgetBuilder() //
                .name("conveyed") //
                .label("aql:'Conveyed'") //
                .help("aql:self.getFeatureDescription('conveyed')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('conveyed').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('conveyed')") //
                .value("feature:conveyed") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('conveyed')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'conveyed')") //
                .removeOperation("aql:item.delete(self, 'conveyed'))") //
                .reorderOperation("aql:self.moveReferenceElement('conveyed', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('conveyed')"); //
        group.getChildren().add(builder.build());
    }

    protected void addRealization(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("realization") //
                .label("aql:'Realization'") //
                .help("aql:self.getFeatureDescription('realization')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('realization').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('realization')") //
                .value("feature:realization") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('realization')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'realization')") //
                .removeOperation("aql:item.delete(self, 'realization'))") //
                .reorderOperation("aql:self.moveReferenceElement('realization', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('realization')"); //
        group.getChildren().add(builder.build());
    }

    protected void addRealizingActivityEdge(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("realizingActivityEdge") //
                .label("aql:'Realizing activity edge'") //
                .help("aql:self.getFeatureDescription('realizingActivityEdge')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('realizingActivityEdge').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('realizingActivityEdge')") //
                .value("feature:realizingActivityEdge") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('realizingActivityEdge')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'realizingActivityEdge')") //
                .removeOperation("aql:item.delete(self, 'realizingActivityEdge'))") //
                .reorderOperation("aql:self.moveReferenceElement('realizingActivityEdge', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('realizingActivityEdge')"); //
        group.getChildren().add(builder.build());
    }

    protected void addRealizingConnector(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("realizingConnector") //
                .label("aql:'Realizing connector'") //
                .help("aql:self.getFeatureDescription('realizingConnector')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('realizingConnector').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('realizingConnector')") //
                .value("feature:realizingConnector") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('realizingConnector')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'realizingConnector')") //
                .removeOperation("aql:item.delete(self, 'realizingConnector'))") //
                .reorderOperation("aql:self.moveReferenceElement('realizingConnector', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('realizingConnector')"); //
        group.getChildren().add(builder.build());
    }

    protected void addRealizingMessage(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("realizingMessage") //
                .label("aql:'Realizing message'") //
                .help("aql:self.getFeatureDescription('realizingMessage')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('realizingMessage').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('realizingMessage')") //
                .value("feature:realizingMessage") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('realizingMessage')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'realizingMessage')") //
                .removeOperation("aql:item.delete(self, 'realizingMessage'))") //
                .reorderOperation("aql:self.moveReferenceElement('realizingMessage', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('realizingMessage')"); //
        group.getChildren().add(builder.build());
    }

}
