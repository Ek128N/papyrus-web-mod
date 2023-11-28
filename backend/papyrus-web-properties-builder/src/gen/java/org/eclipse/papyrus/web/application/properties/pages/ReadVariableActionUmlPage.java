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
import org.eclipse.papyrus.web.application.properties.MonoReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class ReadVariableActionUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ReadVariableActionUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createReadVariableActionUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("readVariableAction_uml_page", "uml::ReadVariableAction", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createReadVariableActionUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("readVariableAction_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addVisibility(group);
        addResult(group);
        addVariable(group);
        addLocalPostcondition(group);
        addLocalPrecondition(group);
        addHandler(group);

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

    protected void addResult(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("result") //
                .label("aql:'Result'") //
                .help("aql:self.getFeatureDescription('result')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('result').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('result')") //
                .isMany(false) //
                .value("feature:result") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'result'))");
        group.getChildren().add(builder.build());
    }

    protected void addVariable(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("variable") //
                .label("aql:'Variable'") //
                .help("aql:self.getFeatureDescription('variable')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('variable').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('variable')") //
                .value("feature:variable") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('variable')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'variable')") //
                .unsetOperation("aql:item.delete(self, 'variable'))") //
                .clearOperation("aql:self.clearReference('variable')"); //
        group.getChildren().add(builder.build());
    }

    protected void addLocalPostcondition(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("localPostcondition") //
                .label("aql:'Local postcondition'") //
                .help("aql:self.getFeatureDescription('localPostcondition')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('localPostcondition').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('localPostcondition')") //
                .isMany(true) //
                .value("feature:localPostcondition") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'localPostcondition'))") //
                .reorderOperation("aql:self.moveReferenceElement('localPostcondition', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

    protected void addLocalPrecondition(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("localPrecondition") //
                .label("aql:'Local precondition'") //
                .help("aql:self.getFeatureDescription('localPrecondition')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('localPrecondition').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('localPrecondition')") //
                .isMany(true) //
                .value("feature:localPrecondition") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'localPrecondition'))") //
                .reorderOperation("aql:self.moveReferenceElement('localPrecondition', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

    protected void addHandler(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("handler") //
                .label("aql:'Handler'") //
                .help("aql:self.getFeatureDescription('handler')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('handler').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('handler')") //
                .isMany(true) //
                .value("feature:handler") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'handler'))") //
                .reorderOperation("aql:self.moveReferenceElement('handler', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

}
