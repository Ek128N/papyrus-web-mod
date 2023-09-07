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
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class TransitionUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public TransitionUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createTransitionUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("transition_uml_pageFrom", "uml::Transition", "aql:'UML'", "");
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
        addTrigger(group);
        addGuard(group);
        addEffect(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getWidgets().add(widget);
    }

    protected void addKind(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("kind", "aql:'Kind'",
                "aql:self.eClass().getEStructuralFeature('kind').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.kind.toString())", "aql:self.set('kind',newValue.instance)",
                "aql:self.eClass().getEStructuralFeature('kind').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name", "aql:self.getFeatureDescription('kind')",
                "aql:self.eClass().getEStructuralFeature('kind').changeable");
        group.getWidgets().add(widget);
    }

    protected void addTrigger(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("trigger", "aql:'Trigger'", "aql:self.getFeatureDescription('trigger')",
                "aql:self.eClass().getEStructuralFeature('trigger').changeable", "aql:'trigger'", "");
        group.getWidgets().add(widget);
    }

    protected void addGuard(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("guard", "aql:'Guard'", "aql:self.getFeatureDescription('guard')",
                "aql:self.eClass().getEStructuralFeature('guard').changeable", "aql:'guard'", "");
        group.getWidgets().add(widget);
    }

    protected void addEffect(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("effect", "aql:'Effect'", "aql:self.getFeatureDescription('effect')",
                "aql:self.eClass().getEStructuralFeature('effect').changeable", "aql:'effect'", "");
        group.getWidgets().add(widget);
    }

}
