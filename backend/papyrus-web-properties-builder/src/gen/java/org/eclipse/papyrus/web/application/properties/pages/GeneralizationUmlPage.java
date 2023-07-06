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

public class GeneralizationUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    public GeneralizationUmlPage(ViewElementsFactory viewElementFactory) {
        super();
        this.viewElementFactory = viewElementFactory;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createGeneralizationUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("generalization_uml_pageFrom", "uml::Generalization", "aql:'UML'", "");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("generalization_uml_page", "uml::Generalization", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createGeneralizationUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("generalization_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addIsSubstitutable(group);

    }

    protected void addIsSubstitutable(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isSubstitutable", "Is substitutable", "feature:isSubstitutable", "aql:self.set('isSubstitutable',newValue)",
                "aql:self.getFeatureDescription('isSubstitutable')", "aql:self.eClass().getEStructuralFeature('isSubstitutable').changeable");
        group.getWidgets().add(widget);
    }

}
