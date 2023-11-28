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
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class StereotypeUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public StereotypeUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createStereotypeUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("stereotype_uml_page", "uml::Stereotype", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createStereotypeUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("stereotype_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsAbstract(group);
        addIcon(group);
        addOwnedAttribute(group);
        addOwnedOperation(group);

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

    protected void addIcon(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("icon") //
                .label("aql:'Icon'") //
                .help("aql:self.getFeatureDescription('icon')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('icon').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('icon')") //
                .isMany(true) //
                .value("feature:icon") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'icon'))") //
                .reorderOperation("aql:self.moveReferenceElement('icon', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

    protected void addOwnedAttribute(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("ownedAttribute") //
                .label("aql:'Owned attribute'") //
                .help("aql:self.getFeatureDescription('ownedAttribute')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('ownedAttribute').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('ownedAttribute')") //
                .isMany(true) //
                .value("feature:ownedAttribute") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'ownedAttribute'))") //
                .reorderOperation("aql:self.moveReferenceElement('ownedAttribute', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

    protected void addOwnedOperation(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("ownedOperation") //
                .label("aql:'Owned operation'") //
                .help("aql:self.getFeatureDescription('ownedOperation')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('ownedOperation').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('ownedOperation')") //
                .isMany(true) //
                .value("feature:ownedOperation") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'ownedOperation'))") //
                .reorderOperation("aql:self.moveReferenceElement('ownedOperation', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

}
