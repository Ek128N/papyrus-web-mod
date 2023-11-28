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
import org.eclipse.papyrus.web.application.properties.MonoReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.MultiReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;

public class ExceptionHandlerUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ExceptionHandlerUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createExceptionHandlerUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("exceptionHandler_uml_page", "uml::ExceptionHandler", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createExceptionHandlerUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("exceptionHandler_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addExceptionInput(group);
        addHandlerBody(group);
        addExceptionType(group);

    }

    protected void addExceptionInput(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("exceptionInput") //
                .label("aql:'Exception Input'") //
                .help("aql:self.getFeatureDescription('exceptionInput')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('exceptionInput').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('exceptionInput')") //
                .value("feature:exceptionInput") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('exceptionInput')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'exceptionInput')") //
                .unsetOperation("aql:item.delete(self, 'exceptionInput'))") //
                .clearOperation("aql:self.clearReference('exceptionInput')"); //
        group.getChildren().add(builder.build());
    }

    protected void addHandlerBody(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("handlerBody") //
                .label("aql:'Handler body'") //
                .help("aql:self.getFeatureDescription('handlerBody')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('handlerBody').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('handlerBody')") //
                .value("feature:handlerBody") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('handlerBody')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'handlerBody')") //
                .unsetOperation("aql:item.delete(self, 'handlerBody'))") //
                .clearOperation("aql:self.clearReference('handlerBody')"); //
        group.getChildren().add(builder.build());
    }

    protected void addExceptionType(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("exceptionType") //
                .label("aql:'Exception type'") //
                .help("aql:self.getFeatureDescription('exceptionType')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('exceptionType').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('exceptionType')") //
                .value("feature:exceptionType") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('exceptionType')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'exceptionType')") //
                .removeOperation("aql:item.delete(self, 'exceptionType'))") //
                .reorderOperation("aql:self.moveReferenceElement('exceptionType', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('exceptionType')"); //
        group.getChildren().add(builder.build());
    }

}
