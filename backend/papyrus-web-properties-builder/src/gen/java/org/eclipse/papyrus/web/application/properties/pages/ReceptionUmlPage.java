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

public class ReceptionUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ReceptionUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createReceptionUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("reception_uml_page", "uml::Reception", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createReceptionUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("reception_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsAbstract(group);
        addIsStatic(group);
        addConcurrency(group);
        addVisibility(group);
        addSignal(group);
        addMethod(group);
        addOwnedParameter(group);
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

    protected void addIsStatic(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isStatic", "aql:'Is static'", "feature:isStatic", "aql:self.set('isStatic',newValue)",
                "aql:self.getFeatureDescription('isStatic')", "");
        group.getChildren().add(widget);
    }

    protected void addConcurrency(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("concurrency", "aql:'Concurrency'",
                "aql:self.eClass().getEStructuralFeature('concurrency').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.concurrency.toString())",
                "aql:self.set('concurrency',newValue.instance)", "aql:self.eClass().getEStructuralFeature('concurrency').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('concurrency')", "aql:self.eClass().getEStructuralFeature('concurrency').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addSignal(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("signal") //
                .label("aql:'Signal'") //
                .help("aql:self.getFeatureDescription('signal')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('signal').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('signal')") //
                .value("feature:signal") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('signal')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'signal')") //
                .unsetOperation("aql:item.delete(self, 'signal'))") //
                .clearOperation("aql:self.clearReference('signal')"); //
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
