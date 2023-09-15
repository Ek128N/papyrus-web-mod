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

public class ClauseUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ClauseUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createClauseUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("clause_uml_pageFrom", "uml::Clause", "aql:'UML'", "");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("clause_uml_page", "uml::Clause", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createClauseUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("clause_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addDecider(group);
        addBody(group);
        addBodyOutput(group);
        addPredecessorClause(group);
        addSuccessorClause(group);
        addTest(group);

    }

    protected void addDecider(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("decider", "aql:'Decider'", "aql:self.getFeatureDescription('decider')",
                "aql:self.eClass().getEStructuralFeature('decider').changeable", "aql:'decider'", "");
        group.getChildren().add(widget);
    }

    protected void addBody(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("body", "aql:'Body'", "aql:self.getFeatureDescription('body')",
                "aql:self.eClass().getEStructuralFeature('body').changeable", "aql:'body'", "");
        group.getChildren().add(widget);
    }

    protected void addBodyOutput(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("bodyOutput", "aql:'Body output'", "aql:self.getFeatureDescription('bodyOutput')",
                "aql:self.eClass().getEStructuralFeature('bodyOutput').changeable", "aql:'bodyOutput'", "");
        group.getChildren().add(widget);
    }

    protected void addPredecessorClause(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("predecessorClause", "aql:'Predecessor clause'", "aql:self.getFeatureDescription('predecessorClause')",
                "aql:self.eClass().getEStructuralFeature('predecessorClause').changeable", "aql:'predecessorClause'", "");
        group.getChildren().add(widget);
    }

    protected void addSuccessorClause(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("successorClause", "aql:'Successor clause'", "aql:self.getFeatureDescription('successorClause')",
                "aql:self.eClass().getEStructuralFeature('successorClause').changeable", "aql:'successorClause'", "");
        group.getChildren().add(widget);
    }

    protected void addTest(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("test", "aql:'Test'", "aql:self.getFeatureDescription('test')",
                "aql:self.eClass().getEStructuralFeature('test').changeable", "aql:'test'", "");
        group.getChildren().add(widget);
    }

}
