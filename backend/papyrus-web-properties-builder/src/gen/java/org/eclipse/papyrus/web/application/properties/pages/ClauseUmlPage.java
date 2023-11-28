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
import org.eclipse.papyrus.web.application.properties.MonoReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.MultiReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;

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
        var builder = new MonoReferenceWidgetBuilder() //
                .name("decider") //
                .label("aql:'Decider'") //
                .help("aql:self.getFeatureDescription('decider')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('decider').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('decider')") //
                .value("feature:decider") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('decider')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'decider')") //
                .unsetOperation("aql:item.delete(self, 'decider'))") //
                .clearOperation("aql:self.clearReference('decider')"); //
        group.getChildren().add(builder.build());
    }

    protected void addBody(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("body") //
                .label("aql:'Body'") //
                .help("aql:self.getFeatureDescription('body')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('body').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('body')") //
                .value("feature:body") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('body')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'body')") //
                .removeOperation("aql:item.delete(self, 'body'))") //
                .reorderOperation("aql:self.moveReferenceElement('body', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('body')"); //
        group.getChildren().add(builder.build());
    }

    protected void addBodyOutput(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("bodyOutput") //
                .label("aql:'Body output'") //
                .help("aql:self.getFeatureDescription('bodyOutput')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('bodyOutput').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('bodyOutput')") //
                .value("feature:bodyOutput") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('bodyOutput')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'bodyOutput')") //
                .removeOperation("aql:item.delete(self, 'bodyOutput'))") //
                .reorderOperation("aql:self.moveReferenceElement('bodyOutput', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('bodyOutput')"); //
        group.getChildren().add(builder.build());
    }

    protected void addPredecessorClause(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("predecessorClause") //
                .label("aql:'Predecessor clause'") //
                .help("aql:self.getFeatureDescription('predecessorClause')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('predecessorClause').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('predecessorClause')") //
                .value("feature:predecessorClause") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('predecessorClause')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'predecessorClause')") //
                .removeOperation("aql:item.delete(self, 'predecessorClause'))") //
                .reorderOperation("aql:self.moveReferenceElement('predecessorClause', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('predecessorClause')"); //
        group.getChildren().add(builder.build());
    }

    protected void addSuccessorClause(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("successorClause") //
                .label("aql:'Successor clause'") //
                .help("aql:self.getFeatureDescription('successorClause')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('successorClause').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('successorClause')") //
                .value("feature:successorClause") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('successorClause')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'successorClause')") //
                .removeOperation("aql:item.delete(self, 'successorClause'))") //
                .reorderOperation("aql:self.moveReferenceElement('successorClause', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('successorClause')"); //
        group.getChildren().add(builder.build());
    }

    protected void addTest(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("test") //
                .label("aql:'Test'") //
                .help("aql:self.getFeatureDescription('test')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('test').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('test')") //
                .value("feature:test") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('test')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'test')") //
                .removeOperation("aql:item.delete(self, 'test'))") //
                .reorderOperation("aql:self.moveReferenceElement('test', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('test')"); //
        group.getChildren().add(builder.build());
    }

}
