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

public class ElementCommentsPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ElementCommentsPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createElementCommentsGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("element_comments_page", "uml::Element", "aql:'Comments'", "aql:self", "aql:not(selection->size()>1)");
    }

    protected void createElementCommentsGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("element_comments_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addOwnedComment(group);

    }

    protected void addOwnedComment(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("ownedComment") //
                .label("aql:'Owned comments'") //
                .help("aql:self.getFeatureDescription('ownedComment')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('ownedComment').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('ownedComment')") //
                .isMany(true) //
                .value("feature:ownedComment") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'ownedComment'))") //
                .reorderOperation("aql:self.moveReferenceElement('ownedComment', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

}
