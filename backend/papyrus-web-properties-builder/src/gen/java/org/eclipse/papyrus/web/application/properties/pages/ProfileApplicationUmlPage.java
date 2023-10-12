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

public class ProfileApplicationUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ProfileApplicationUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createProfileApplicationUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("profileApplication_uml_page", "uml::ProfileApplication", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createProfileApplicationUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("profileApplication_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addIsStrict(group);

    }

    protected void addIsStrict(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isStrict", "aql:'Is strict'", "feature:isStrict", "aql:self.set('isStrict',newValue)",
                "aql:self.getFeatureDescription('isStrict')", "aql:self.eClass().getEStructuralFeature('isStrict').changeable");
        group.getChildren().add(widget);
    }

}
