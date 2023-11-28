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

public class ActivityUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ActivityUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createActivityUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("activity_uml_page", "uml::Activity", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createActivityUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("activity_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsAbstract(group);
        addIsActive(group);
        addIsReadOnly(group);
        addIsReentrant(group);
        addIsSingleExecution(group);
        addVisibility(group);
        addSpecification(group);
        addPrecondition(group);
        addPostcondition(group);
        addOwnedParameter(group);
        addVariable(group);

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

    protected void addIsActive(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isActive", "aql:'Is active'", "feature:isActive", "aql:self.set('isActive',newValue)",
                "aql:self.getFeatureDescription('isActive')", "aql:self.eClass().getEStructuralFeature('isActive').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsReadOnly(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isReadOnly", "aql:'Is read only'", "feature:isReadOnly", "aql:self.set('isReadOnly',newValue)",
                "aql:self.getFeatureDescription('isReadOnly')", "aql:self.eClass().getEStructuralFeature('isReadOnly').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsReentrant(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isReentrant", "aql:'Is reentrant'", "feature:isReentrant", "aql:self.set('isReentrant',newValue)",
                "aql:self.getFeatureDescription('isReentrant')", "aql:self.eClass().getEStructuralFeature('isReentrant').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsSingleExecution(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isSingleExecution", "aql:'Is single execution'", "feature:isSingleExecution",
                "aql:self.set('isSingleExecution',newValue)", "aql:self.getFeatureDescription('isSingleExecution')", "aql:self.eClass().getEStructuralFeature('isSingleExecution').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addSpecification(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("specification") //
                .label("aql:'Specification'") //
                .help("aql:self.getFeatureDescription('specification')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('specification').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('specification')") //
                .value("feature:specification") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('specification')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'specification')") //
                .unsetOperation("aql:item.delete(self, 'specification'))") //
                .clearOperation("aql:self.clearReference('specification')"); //
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

    protected void addVariable(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("variable") //
                .label("aql:'Variable'") //
                .help("aql:self.getFeatureDescription('variable')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('variable').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('variable')") //
                .isMany(true) //
                .value("feature:variable") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'variable'))") //
                .reorderOperation("aql:self.moveReferenceElement('variable', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

}
