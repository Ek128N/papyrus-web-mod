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

public class CommentUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public CommentUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createCommentUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("comment_uml_page", "uml::Comment", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createCommentUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("comment_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addBody(group);
        addAnnotatedElement(group);

    }

    protected void addBody(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextAreaDescription("body", "aql:'Body'", "feature:body", "aql:self.set('body',newValue)", "aql:self.getFeatureDescription('body')",
                "aql:self.eClass().getEStructuralFeature('body').changeable");
        group.getChildren().add(widget);
    }

    protected void addAnnotatedElement(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("annotatedElement") //
                .label("aql:'Annotated element'") //
                .help("aql:self.getFeatureDescription('annotatedElement')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('annotatedElement').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('annotatedElement')") //
                .value("feature:annotatedElement") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('annotatedElement')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'annotatedElement')") //
                .removeOperation("aql:item.delete(self, 'annotatedElement'))") //
                .reorderOperation("aql:self.moveReferenceElement('annotatedElement', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('annotatedElement')"); //
        group.getChildren().add(builder.build());
    }

}
