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

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("message_uml_pageFrom", "uml::Message", "aql:'UML'", "");
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
        WidgetDescription widget = viewElementFactory.createReferenceDescription("signature", "aql:'Signature'", "aql:self.getFeatureDescription('signature')",
                "aql:self.eClass().getEStructuralFeature('signature').changeable", "aql:'signature'", "");
        group.getChildren().add(widget);
    }

    protected void addArgument(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("argument", "aql:'Argument'", "aql:self.getFeatureDescription('argument')",
                "aql:self.eClass().getEStructuralFeature('argument').changeable", "aql:'argument'", "");
        group.getChildren().add(widget);
    }

}
