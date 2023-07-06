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

import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class LinkEndCreationDataUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    public LinkEndCreationDataUmlPage(ViewElementsFactory viewElementFactory) {
        super();
        this.viewElementFactory = viewElementFactory;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createLinkEndCreationDataUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("linkEndCreationData_uml_pageFrom", "uml::LinkEndCreationData", "aql:'UML'", "${formPreconditionExpression}");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("linkEndCreationData_uml_page", "uml::LinkEndCreationData", "aql:'UML'", "aql:self",
                "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createLinkEndCreationDataUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("linkEndCreationData_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addIsReplaceAll(group);

    }

    protected void addIsReplaceAll(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isReplaceAll", "aql:'Is replace all'", "feature:isReplaceAll", "aql:self.set('isReplaceAll',newValue)",
                "aql:self.getFeatureDescription('isReplaceAll')");
        group.getWidgets().add(widget);
    }

}
