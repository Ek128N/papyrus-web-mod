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
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class DecisionNodeUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public DecisionNodeUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createDecisionNodeUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("decisionNode_uml_page", "uml::DecisionNode", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createDecisionNodeUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("decisionNode_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addVisibility(group);
        addDecisionInput(group);
        addDecisionInputFlow(group);

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

    protected void addDecisionInput(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("decisionInput") //
                .label("aql:'Decision input'") //
                .help("aql:self.getFeatureDescription('decisionInput')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('decisionInput').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('decisionInput')") //
                .value("feature:decisionInput") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('decisionInput')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'decisionInput')") //
                .unsetOperation("aql:item.delete(self, 'decisionInput'))") //
                .clearOperation("aql:self.clearReference('decisionInput')"); //
        group.getChildren().add(builder.build());
    }

    protected void addDecisionInputFlow(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("decisionInputFlow") //
                .label("aql:'Decision input flow'") //
                .help("aql:self.getFeatureDescription('decisionInputFlow')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('decisionInputFlow').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('decisionInputFlow')") //
                .value("feature:decisionInputFlow") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('decisionInputFlow')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'decisionInputFlow')") //
                .unsetOperation("aql:item.delete(self, 'decisionInputFlow'))") //
                .clearOperation("aql:self.clearReference('decisionInputFlow')"); //
        group.getChildren().add(builder.build());
    }

}
