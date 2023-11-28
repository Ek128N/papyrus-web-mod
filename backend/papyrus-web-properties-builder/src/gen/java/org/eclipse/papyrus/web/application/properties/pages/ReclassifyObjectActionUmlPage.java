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
import org.eclipse.papyrus.web.application.properties.MultiReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class ReclassifyObjectActionUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ReclassifyObjectActionUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createReclassifyObjectActionUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("reclassifyObjectAction_uml_page", "uml::ReclassifyObjectAction", "aql:'UML'", "aql:self",
                "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createReclassifyObjectActionUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("reclassifyObjectAction_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsReplaceAll(group);
        addVisibility(group);
        addObject(group);
        addNewClassifier(group);
        addOldClassifier(group);
        addLocalPostcondition(group);
        addLocalPrecondition(group);
        addHandler(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsReplaceAll(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isReplaceAll", "aql:'Is replace all'", "feature:isReplaceAll", "aql:self.set('isReplaceAll',newValue)",
                "aql:self.getFeatureDescription('isReplaceAll')", "aql:self.eClass().getEStructuralFeature('isReplaceAll').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addObject(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("object") //
                .label("aql:'Object'") //
                .help("aql:self.getFeatureDescription('object')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('object').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('object')") //
                .isMany(false) //
                .value("feature:object") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'object'))");
        group.getChildren().add(builder.build());
    }

    protected void addNewClassifier(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("newClassifier") //
                .label("aql:'New classifier'") //
                .help("aql:self.getFeatureDescription('newClassifier')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('newClassifier').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('newClassifier')") //
                .value("feature:newClassifier") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('newClassifier')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'newClassifier')") //
                .removeOperation("aql:item.delete(self, 'newClassifier'))") //
                .reorderOperation("aql:self.moveReferenceElement('newClassifier', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('newClassifier')"); //
        group.getChildren().add(builder.build());
    }

    protected void addOldClassifier(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("oldClassifier") //
                .label("aql:'Old classifier'") //
                .help("aql:self.getFeatureDescription('oldClassifier')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('oldClassifier').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('oldClassifier')") //
                .value("feature:oldClassifier") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('oldClassifier')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'oldClassifier')") //
                .removeOperation("aql:item.delete(self, 'oldClassifier'))") //
                .reorderOperation("aql:self.moveReferenceElement('oldClassifier', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('oldClassifier')"); //
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

    protected void addHandler(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("handler") //
                .label("aql:'Handler'") //
                .help("aql:self.getFeatureDescription('handler')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('handler').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('handler')") //
                .isMany(true) //
                .value("feature:handler") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'handler'))") //
                .reorderOperation("aql:self.moveReferenceElement('handler', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

}
