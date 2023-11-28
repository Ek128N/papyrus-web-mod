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
import org.eclipse.papyrus.web.application.properties.MultiReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class InterruptibleActivityRegionUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public InterruptibleActivityRegionUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createInterruptibleActivityRegionUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("interruptibleActivityRegion_uml_page", "uml::InterruptibleActivityRegion", "aql:'UML'", "aql:self",
                "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createInterruptibleActivityRegionUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("interruptibleActivityRegion_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addInterruptingEdge(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addInterruptingEdge(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("interruptingEdge") //
                .label("aql:'Interrupting edge'") //
                .help("aql:self.getFeatureDescription('interruptingEdge')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('interruptingEdge').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('interruptingEdge')") //
                .value("feature:interruptingEdge") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('interruptingEdge')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'interruptingEdge')") //
                .removeOperation("aql:item.delete(self, 'interruptingEdge'))") //
                .reorderOperation("aql:self.moveReferenceElement('interruptingEdge', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('interruptingEdge')"); //
        group.getChildren().add(builder.build());
    }

}
