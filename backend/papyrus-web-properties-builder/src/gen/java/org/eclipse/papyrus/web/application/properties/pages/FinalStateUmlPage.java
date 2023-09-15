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

public class FinalStateUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public FinalStateUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createFinalStateUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("finalState_uml_pageFrom", "uml::FinalState", "aql:'UML'", "");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("finalState_uml_page", "uml::FinalState", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createFinalStateUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("finalState_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addVisibility(group);
        addStateInvariant(group);
        addEntry(group);
        addDoActivity(group);
        addExit(group);
        addDeferrableTrigger(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addStateInvariant(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("stateInvariant", "aql:'State invariant'", "aql:self.getFeatureDescription('stateInvariant')",
                "aql:self.eClass().getEStructuralFeature('stateInvariant').changeable", "aql:'stateInvariant'", "");
        group.getChildren().add(widget);
    }

    protected void addEntry(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("entry", "aql:'Entry'", "aql:self.getFeatureDescription('entry')",
                "aql:self.eClass().getEStructuralFeature('entry').changeable", "aql:'entry'", "");
        group.getChildren().add(widget);
    }

    protected void addDoActivity(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("doActivity", "aql:'Do activity'", "aql:self.getFeatureDescription('doActivity')",
                "aql:self.eClass().getEStructuralFeature('doActivity').changeable", "aql:'doActivity'", "");
        group.getChildren().add(widget);
    }

    protected void addExit(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("exit", "aql:'Exit'", "aql:self.getFeatureDescription('exit')",
                "aql:self.eClass().getEStructuralFeature('exit').changeable", "aql:'exit'", "");
        group.getChildren().add(widget);
    }

    protected void addDeferrableTrigger(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("deferrableTrigger", "aql:'Deferrable trigger'", "aql:self.getFeatureDescription('deferrableTrigger')",
                "aql:self.eClass().getEStructuralFeature('deferrableTrigger').changeable", "aql:'deferrableTrigger'", "");
        group.getChildren().add(widget);
    }

}
