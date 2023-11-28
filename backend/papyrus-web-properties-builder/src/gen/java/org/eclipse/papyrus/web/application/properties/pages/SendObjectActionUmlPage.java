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

public class SendObjectActionUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public SendObjectActionUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createSendObjectActionUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("sendObjectAction_uml_page", "uml::SendObjectAction", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createSendObjectActionUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("sendObjectAction_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addVisibility(group);
        addOnPort(group);
        addRequest(group);
        addTarget(group);
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

    protected void addOnPort(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("onPort") //
                .label("aql:'On port'") //
                .help("aql:self.getFeatureDescription('onPort')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('onPort').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('onPort')") //
                .value("feature:onPort") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('onPort')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'onPort')") //
                .unsetOperation("aql:item.delete(self, 'onPort'))") //
                .clearOperation("aql:self.clearReference('onPort')"); //
        group.getChildren().add(builder.build());
    }

    protected void addRequest(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("request") //
                .label("aql:'Request'") //
                .help("aql:self.getFeatureDescription('request')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('request').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('request')") //
                .isMany(false) //
                .value("feature:request") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'request'))");
        group.getChildren().add(builder.build());
    }

    protected void addTarget(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("target") //
                .label("aql:'Target'") //
                .help("aql:self.getFeatureDescription('target')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('target').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('target')") //
                .isMany(false) //
                .value("feature:target") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'target'))");
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
