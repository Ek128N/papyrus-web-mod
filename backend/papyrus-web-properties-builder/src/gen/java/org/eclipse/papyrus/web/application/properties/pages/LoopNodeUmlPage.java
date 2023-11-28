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
import org.eclipse.papyrus.web.application.properties.ContainmentReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.MonoReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.MultiReferenceWidgetBuilder;
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

    protected void addBodyPart(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("bodyPart") //
                .label("aql:'Body part'") //
                .help("aql:self.getFeatureDescription('bodyPart')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('bodyPart').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('bodyPart')") //
                .value("feature:bodyPart") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('bodyPart')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'bodyPart')") //
                .removeOperation("aql:item.delete(self, 'bodyPart'))") //
                .reorderOperation("aql:self.moveReferenceElement('bodyPart', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('bodyPart')"); //
        group.getChildren().add(builder.build());
    }

    protected void addLocalPostcondition(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("localPostcondition") //
                .label("aql:'Local postcondition'") //
                .help("aql:self.getFeatureDescription('localPostcondition')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('localPostcondition').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('localPostcondition')") //
                .isMany(true) //
                .value("feature:localPostcondition") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'localPostcondition'))") //
                .reorderOperation("aql:self.moveReferenceElement('localPostcondition', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

    protected void addLocalPrecondition(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("localPrecondition") //
                .label("aql:'Local precondition'") //
                .help("aql:self.getFeatureDescription('localPrecondition')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('localPrecondition').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('localPrecondition')") //
                .isMany(true) //
                .value("feature:localPrecondition") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'localPrecondition'))") //
                .reorderOperation("aql:self.moveReferenceElement('localPrecondition', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

    protected void addLoopVariable(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("loopVariable") //
                .label("aql:'Loop variable'") //
                .help("aql:self.getFeatureDescription('loopVariable')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('loopVariable').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('loopVariable')") //
                .isMany(true) //
                .value("feature:loopVariable") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'loopVariable'))") //
                .reorderOperation("aql:self.moveReferenceElement('loopVariable', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

    protected void addLoopVariableInput(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("loopVariableInput") //
                .label("aql:'Loop variable input'") //
                .help("aql:self.getFeatureDescription('loopVariableInput')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('loopVariableInput').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('loopVariableInput')") //
                .isMany(true) //
                .value("feature:loopVariableInput") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'loopVariableInput'))") //
                .reorderOperation("aql:self.moveReferenceElement('loopVariableInput', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

    protected void addResult(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("result") //
                .label("aql:'Result'") //
                .help("aql:self.getFeatureDescription('result')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('result').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('result')") //
                .isMany(true) //
                .value("feature:result") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'result'))") //
                .reorderOperation("aql:self.moveReferenceElement('result', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

    protected void addSetupPart(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("setupPart") //
                .label("aql:'Setup part'") //
                .help("aql:self.getFeatureDescription('setupPart')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('setupPart').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('setupPart')") //
                .value("feature:setupPart") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('setupPart')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'setupPart')") //
                .removeOperation("aql:item.delete(self, 'setupPart'))") //
                .reorderOperation("aql:self.moveReferenceElement('setupPart', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('setupPart')"); //
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
