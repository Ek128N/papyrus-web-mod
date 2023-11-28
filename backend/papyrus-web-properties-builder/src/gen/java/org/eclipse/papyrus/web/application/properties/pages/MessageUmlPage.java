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

public class MessageUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public MessageUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createMessageUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("message_uml_page", "uml::Message", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createMessageUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("message_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addMessageSort(group);
        addSignature(group);
        addArgument(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addMessageSort(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("messageSort", "aql:'Message sort'",
                "aql:self.eClass().getEStructuralFeature('messageSort').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.messageSort.toString())",
                "aql:self.set('messageSort',newValue.instance)", "aql:self.eClass().getEStructuralFeature('messageSort').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('messageSort')", "aql:self.eClass().getEStructuralFeature('messageSort').changeable");
        group.getChildren().add(widget);
    }

    protected void addSignature(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("signature") //
                .label("aql:'Signature'") //
                .help("aql:self.getFeatureDescription('signature')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('signature').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('signature')") //
                .value("feature:signature") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('signature')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'signature')") //
                .unsetOperation("aql:item.delete(self, 'signature'))") //
                .clearOperation("aql:self.clearReference('signature')"); //
        group.getChildren().add(builder.build());
    }

    protected void addArgument(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("argument") //
                .label("aql:'Argument'") //
                .help("aql:self.getFeatureDescription('argument')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('argument').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('argument')") //
                .isMany(true) //
                .value("feature:argument") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'argument'))") //
                .reorderOperation("aql:self.moveReferenceElement('argument', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

}
