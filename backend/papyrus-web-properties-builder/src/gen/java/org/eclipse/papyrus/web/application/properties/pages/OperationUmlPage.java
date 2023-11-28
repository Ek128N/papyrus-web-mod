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

public class OperationUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public OperationUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createOperationUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("operation_uml_page", "uml::Operation", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createOperationUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("operation_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsAbstract(group);
        addIsQuery(group);
        addIsStatic(group);
        addVisibility(group);
        addConcurrency(group);
        addBodyCondition(group);
        addMethod(group);
        addOwnedParameter(group);
        addPrecondition(group);
        addPostcondition(group);
        addRaisedException(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsAbstract(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isAbstract", "Is abstract", "feature:isAbstract", "aql:self.set('isAbstract',newValue)",
                "aql:self.getFeatureDescription('isAbstract')", "aql:self.eClass().getEStructuralFeature('isAbstract').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsQuery(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isQuery", "aql:'Is query'", "feature:isQuery", "aql:self.set('isQuery',newValue)",
                "aql:self.getFeatureDescription('isQuery')", "aql:self.eClass().getEStructuralFeature('isQuery').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsStatic(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isStatic", "aql:'Is static'", "feature:isStatic", "aql:self.set('isStatic',newValue)",
                "aql:self.getFeatureDescription('isStatic')", "aql:self.eClass().getEStructuralFeature('isStatic').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addConcurrency(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("concurrency", "aql:'Concurrency'",
                "aql:self.eClass().getEStructuralFeature('concurrency').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.concurrency.toString())",
                "aql:self.set('concurrency',newValue.instance)", "aql:self.eClass().getEStructuralFeature('concurrency').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('concurrency')", "aql:self.eClass().getEStructuralFeature('concurrency').changeable");
        group.getChildren().add(widget);
    }

    protected void addBodyCondition(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("bodyCondition") //
                .label("aql:'Body condition'") //
                .help("aql:self.getFeatureDescription('bodyCondition')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('bodyCondition').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('bodyCondition')") //
                .value("feature:bodyCondition") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('bodyCondition')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'bodyCondition')") //
                .unsetOperation("aql:item.delete(self, 'bodyCondition'))") //
                .clearOperation("aql:self.clearReference('bodyCondition')"); //
        group.getChildren().add(builder.build());
    }

    protected void addMethod(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("method") //
                .label("aql:'Method'") //
                .help("aql:self.getFeatureDescription('method')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('method').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('method')") //
                .value("feature:method") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('method')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'method')") //
                .removeOperation("aql:item.delete(self, 'method'))") //
                .reorderOperation("aql:self.moveReferenceElement('method', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('method')"); //
        group.getChildren().add(builder.build());
    }

    protected void addOwnedParameter(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("ownedParameter") //
                .label("aql:'Owned parameter'") //
                .help("aql:self.getFeatureDescription('ownedParameter')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('ownedParameter').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('ownedParameter')") //
                .isMany(true) //
                .value("feature:ownedParameter") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'ownedParameter'))") //
                .reorderOperation("aql:self.moveReferenceElement('ownedParameter', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

    protected void addPrecondition(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("precondition") //
                .label("aql:'Precondition'") //
                .help("aql:self.getFeatureDescription('precondition')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('precondition').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('precondition')") //
                .value("feature:precondition") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('precondition')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'precondition')") //
                .removeOperation("aql:item.delete(self, 'precondition'))") //
                .reorderOperation("aql:self.moveReferenceElement('precondition', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('precondition')"); //
        group.getChildren().add(builder.build());
    }

    protected void addPostcondition(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("postcondition") //
                .label("aql:'Postcondition'") //
                .help("aql:self.getFeatureDescription('postcondition')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('postcondition').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('postcondition')") //
                .value("feature:postcondition") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('postcondition')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'postcondition')") //
                .removeOperation("aql:item.delete(self, 'postcondition'))") //
                .reorderOperation("aql:self.moveReferenceElement('postcondition', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('postcondition')"); //
        group.getChildren().add(builder.build());
    }

    protected void addRaisedException(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("raisedException") //
                .label("aql:'Raised Exception'") //
                .help("aql:self.getFeatureDescription('raisedException')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('raisedException').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('raisedException')") //
                .value("feature:raisedException") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('raisedException')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'raisedException')") //
                .removeOperation("aql:item.delete(self, 'raisedException'))") //
                .reorderOperation("aql:self.moveReferenceElement('raisedException', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('raisedException')"); //
        group.getChildren().add(builder.build());
    }

}
