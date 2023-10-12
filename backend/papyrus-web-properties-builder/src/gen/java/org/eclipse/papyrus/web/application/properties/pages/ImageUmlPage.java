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

public class ImageUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ImageUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createImageUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("image_uml_page", "uml::Image", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createImageUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("image_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addKind(group);
        addExpression(group);
        addLocation(group);
        addFormat(group);
        addContent(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "aql:self.getImageName()", "aql:self.oclAsType(uml::Image).setImageName(newValue)",
                "aql:'The image\'s name. This is a virtual property, used only for label display.'", "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addKind(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("kind", "aql:'Kind'", "aql:self.getImageKind()",
                "aql:self.oclAsType(uml::Image).setImageKind(newValue.oclAsType(ecore::EString))", "aql:self.getImageKindEnumerations()", "aql:candidate.toString()",
                "aql:'The kind of image to be used. If icon is set, the image will be displayed on the stereotyped element. If shape is used, the image will be used as the graphical element. '", "");
        group.getChildren().add(widget);
    }

    protected void addExpression(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextAreaDescription("expression", "aql:'Expression'", "aql:self.getImageExpression()",
                "aql:self.oclAsType(uml::Image).setImageExpression(newValue)", "aql:'A boolean expression to determine under which condition this image should be displayed.'",
                "aql:self.eClass().getEStructuralFeature('expression').changeable");
        group.getChildren().add(widget);
    }

    protected void addLocation(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("location", "aql:'Location'", "feature:location", "aql:self.set('location',newValue)",
                "aql:self.getFeatureDescription('location')", "aql:self.eClass().getEStructuralFeature('location').changeable");
        group.getChildren().add(widget);
    }

    protected void addFormat(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("format", "aql:'Format'", "feature:format", "aql:self.set('format',newValue)",
                "aql:self.getFeatureDescription('format')", "aql:self.eClass().getEStructuralFeature('format').changeable");
        group.getChildren().add(widget);
    }

    protected void addContent(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("content", "aql:'Content'", "feature:content", "aql:self.set('content',newValue)",
                "aql:self.getFeatureDescription('content')", "aql:self.eClass().getEStructuralFeature('content').changeable");
        group.getChildren().add(widget);
    }

}
