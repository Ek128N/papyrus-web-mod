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
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;

public class SlotUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public SlotUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createSlotUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("slot_uml_page", "uml::Slot", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createSlotUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("slot_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addDefiningFeature(group);
        addValue(group);

    }

    protected void addDefiningFeature(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("definingFeature") //
                .label("aql:'Defining feature'") //
                .help("aql:self.getFeatureDescription('definingFeature')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('definingFeature').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('definingFeature')") //
                .value("feature:definingFeature") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('definingFeature')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'definingFeature')") //
                .unsetOperation("aql:item.delete(self, 'definingFeature'))") //
                .clearOperation("aql:self.clearReference('definingFeature')"); //
        group.getChildren().add(builder.build());
    }

    protected void addValue(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("value") //
                .label("aql:'Value'") //
                .help("aql:self.getFeatureDescription('value')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('value').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('value')") //
                .isMany(true) //
                .value("feature:value") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'value'))") //
                .reorderOperation("aql:self.moveReferenceElement('value', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

}
