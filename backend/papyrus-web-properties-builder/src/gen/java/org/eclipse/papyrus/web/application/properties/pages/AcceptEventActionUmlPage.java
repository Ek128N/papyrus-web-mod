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
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
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

    protected void addResult(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("result") //
                .label("aql:'Result'") //
                .help("aql:self.getFeatureDescription('result')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('result').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('result')") //
                .isMany(true) //
                .value("feature:result") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'result'))") //
                .reorderOperation("aql:self.moveReferenceElement('result', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

    protected void addTrigger(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("trigger") //
                .label("aql:'Trigger'") //
                .help("aql:self.getFeatureDescription('trigger')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('trigger').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('trigger')") //
                .isMany(true) //
                .value("feature:trigger") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'trigger'))") //
                .reorderOperation("aql:self.moveReferenceElement('trigger', item, fromIndex, toIndex)");
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
