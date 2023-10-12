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
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

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
        WidgetDescription widget = viewElementFactory.createReferenceDescription("exceptionInput", "aql:'Exception Input'", "aql:self.getFeatureDescription('exceptionInput')",
                "aql:self.eClass().getEStructuralFeature('exceptionInput').changeable", "aql:'exceptionInput'", "");
        group.getChildren().add(widget);
    }

    protected void addHandlerBody(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("handlerBody", "aql:'Handler body'", "aql:self.getFeatureDescription('handlerBody')",
                "aql:self.eClass().getEStructuralFeature('handlerBody').changeable", "aql:'handlerBody'", "");
        group.getChildren().add(widget);
    }

    protected void addExceptionType(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("exceptionType", "aql:'Exception type'", "aql:self.getFeatureDescription('exceptionType')",
                "aql:self.eClass().getEStructuralFeature('exceptionType').changeable", "aql:'exceptionType'", "");
        group.getChildren().add(widget);
    }

}
