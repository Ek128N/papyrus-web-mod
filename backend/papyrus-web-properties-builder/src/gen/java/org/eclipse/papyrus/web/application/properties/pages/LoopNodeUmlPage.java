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
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class LoopNodeUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public LoopNodeUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createLoopNodeUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("loopNode_uml_page", "uml::LoopNode", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createLoopNodeUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("loopNode_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsTestedFirst(group);
        addMustIsolate(group);
        addVisibility(group);
        addDecider(group);
        addBodyOutput(group);
        addBodyPart(group);
        addLocalPostcondition(group);
        addLocalPrecondition(group);
        addLoopVariable(group);
        addLoopVariableInput(group);
        addResult(group);
        addSetupPart(group);
        addTest(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsTestedFirst(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isTestedFirst", "aql:'Is tested first'", "feature:isTestedFirst", "aql:self.set('isTestedFirst',newValue)",
                "aql:self.getFeatureDescription('isTestedFirst')", "aql:self.eClass().getEStructuralFeature('isTestedFirst').changeable");
        group.getChildren().add(widget);
    }

    protected void addMustIsolate(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("mustIsolate", "aql:'Must isolate'", "feature:mustIsolate", "aql:self.set('mustIsolate',newValue)",
                "aql:self.getFeatureDescription('mustIsolate')", "aql:self.eClass().getEStructuralFeature('mustIsolate').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addDecider(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("decider", "aql:'Decider'", "aql:self.getFeatureDescription('decider')",
                "aql:self.eClass().getEStructuralFeature('decider').changeable", "aql:'decider'", "");
        group.getChildren().add(widget);
    }

    protected void addBodyOutput(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("bodyOutput", "aql:'Body output'", "aql:self.getFeatureDescription('bodyOutput')",
                "aql:self.eClass().getEStructuralFeature('bodyOutput').changeable", "aql:'bodyOutput'", "");
        group.getChildren().add(widget);
    }

    protected void addBodyPart(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("bodyPart", "aql:'Body part'", "aql:self.getFeatureDescription('bodyPart')",
                "aql:self.eClass().getEStructuralFeature('bodyPart').changeable", "aql:'bodyPart'", "");
        group.getChildren().add(widget);
    }

    protected void addLocalPostcondition(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("localPostcondition", "aql:'Local postcondition'", "aql:self.getFeatureDescription('localPostcondition')",
                "aql:self.eClass().getEStructuralFeature('localPostcondition').changeable", "aql:'localPostcondition'", "");
        group.getChildren().add(widget);
    }

    protected void addLocalPrecondition(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("localPrecondition", "aql:'Local precondition'", "aql:self.getFeatureDescription('localPrecondition')",
                "aql:self.eClass().getEStructuralFeature('localPrecondition').changeable", "aql:'localPrecondition'", "");
        group.getChildren().add(widget);
    }

    protected void addLoopVariable(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("loopVariable", "aql:'Loop variable'", "aql:self.getFeatureDescription('loopVariable')",
                "aql:self.eClass().getEStructuralFeature('loopVariable').changeable", "aql:'loopVariable'", "");
        group.getChildren().add(widget);
    }

    protected void addLoopVariableInput(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("loopVariableInput", "aql:'Loop variable input'", "aql:self.getFeatureDescription('loopVariableInput')",
                "aql:self.eClass().getEStructuralFeature('loopVariableInput').changeable", "aql:'loopVariableInput'", "");
        group.getChildren().add(widget);
    }

    protected void addResult(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("result", "aql:'Result'", "aql:self.getFeatureDescription('result')",
                "aql:self.eClass().getEStructuralFeature('result').changeable", "aql:'result'", "");
        group.getChildren().add(widget);
    }

    protected void addSetupPart(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("setupPart", "aql:'Setup part'", "aql:self.getFeatureDescription('setupPart')",
                "aql:self.eClass().getEStructuralFeature('setupPart').changeable", "aql:'setupPart'", "");
        group.getChildren().add(widget);
    }

    protected void addTest(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("test", "aql:'Test'", "aql:self.getFeatureDescription('test')",
                "aql:self.eClass().getEStructuralFeature('test').changeable", "aql:'test'", "");
        group.getChildren().add(widget);
    }

}
