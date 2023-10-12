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

public class ElementProfilePage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ElementProfilePage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createPackageProfileGroup(page);
        createElementProfileGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("element_profile_page", "uml::Element", "aql:'Profile'", "aql:self", "aql:not(self.oclIsKindOf(uml::Stereotype)) and not(selection->size()>1)");
    }

    protected void createPackageProfileGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("package_profile_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

    }

    protected void createElementProfileGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("element_profile_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

    }

}
