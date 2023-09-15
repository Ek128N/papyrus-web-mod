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

/**
 * Custom implementation of AssociationUmlPage.
 * 
 * @author Jerome Gout
 */
public class AssociationUmlPageCustomImpl extends AssociationUmlPage {

    public AssociationUmlPageCustomImpl(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super(viewElementFactory, colorRegistry);
    }

    @Override
    protected void createMemberEndUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("memberEnd_uml_group", "", "aql:self.memberEnd", GroupDisplayMode.LIST);
        page.getGroups().add(group);
        group.getChildren().add(new MemberEndGroupDescriptionBuilder(viewElementFactory, colorRegistry).build());
    }
}
