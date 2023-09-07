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

public class StateUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public StateUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createStateUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("state_uml_pageFrom", "uml::State", "aql:'UML'", "");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("state_uml_page", "uml::State", "aql:'UML'", "aql:self",
                "aql:not(self.oclIsKindOf(uml::FinalState)) and not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createStateUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("state_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addStateInvariant(group);
        addEntry(group);
        addDoActivity(group);
        addExit(group);
        addSubmachine(group);
        addDeferrableTrigger(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getWidgets().add(widget);
    }

    protected void addStateInvariant(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("stateInvariant", "aql:'State invariant'", "aql:self.getFeatureDescription('stateInvariant')",
                "aql:self.eClass().getEStructuralFeature('stateInvariant').changeable", "aql:'stateInvariant'", "");
        group.getWidgets().add(widget);
    }

    protected void addEntry(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("entry", "aql:'Entry'", "aql:self.getFeatureDescription('entry')",
                "aql:self.eClass().getEStructuralFeature('entry').changeable", "aql:'entry'", "");
        group.getWidgets().add(widget);
    }

    protected void addDoActivity(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("doActivity", "aql:'Do activity'", "aql:self.getFeatureDescription('doActivity')",
                "aql:self.eClass().getEStructuralFeature('doActivity').changeable", "aql:'doActivity'", "");
        group.getWidgets().add(widget);
    }

    protected void addExit(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("exit", "aql:'Exit'", "aql:self.getFeatureDescription('exit')",
                "aql:self.eClass().getEStructuralFeature('exit').changeable", "aql:'exit'", "");
        group.getWidgets().add(widget);
    }

    protected void addSubmachine(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("submachine", "aql:'Submachine'", "aql:self.getFeatureDescription('submachine')",
                "aql:self.eClass().getEStructuralFeature('submachine').changeable", "aql:'submachine'", "");
        group.getWidgets().add(widget);
    }

    protected void addDeferrableTrigger(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("deferrableTrigger", "aql:'Deferrable trigger'", "aql:self.getFeatureDescription('deferrableTrigger')",
                "aql:self.eClass().getEStructuralFeature('deferrableTrigger').changeable", "aql:'deferrableTrigger'", "");
        group.getWidgets().add(widget);
    }

}
