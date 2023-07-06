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

public class TransitionUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    public TransitionUmlPage(ViewElementsFactory viewElementFactory) {
        super();
        this.viewElementFactory = viewElementFactory;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createTransitionUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("transition_uml_pageFrom", "uml::Transition", "aql:'UML'", "${formPreconditionExpression}");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("transition_uml_page", "uml::Transition", "aql:'UML'", "aql:self",
                "aql:not(self.oclIsKindOf(uml::ProtocolTransition)) and not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createTransitionUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("transition_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addKind(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')");
        group.getWidgets().add(widget);
    }

    protected void addKind(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("kind", "aql:'Kind'",
                "aql:self.eClass().getEStructuralFeature('kind').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.kind.toString())", "aql:self.set('kind',newValue.instance)",
                "aql:self.eClass().getEStructuralFeature('kind').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name", "aql:self.getFeatureDescription('kind')");
        group.getWidgets().add(widget);
    }

}
