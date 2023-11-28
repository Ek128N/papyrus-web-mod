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
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class LinkEndCreationDataUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public LinkEndCreationDataUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createLinkEndCreationDataUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("linkEndCreationData_uml_page", "uml::LinkEndCreationData", "aql:'UML'", "aql:self",
                "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createLinkEndCreationDataUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("linkEndCreationData_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addIsReplaceAll(group);
        addEnd(group);
        addInsertAt(group);
        addValue(group);
        addQualifier(group);

    }

    protected void addIsReplaceAll(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isReplaceAll", "aql:'Is replace all'", "feature:isReplaceAll", "aql:self.set('isReplaceAll',newValue)",
                "aql:self.getFeatureDescription('isReplaceAll')", "aql:self.eClass().getEStructuralFeature('isReplaceAll').changeable");
        group.getChildren().add(widget);
    }

    protected void addEnd(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("end") //
                .label("aql:'End'") //
                .help("aql:self.getFeatureDescription('end')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('end').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('end')") //
                .value("feature:end") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('end')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'end')") //
                .unsetOperation("aql:item.delete(self, 'end'))") //
                .clearOperation("aql:self.clearReference('end')"); //
        group.getChildren().add(builder.build());
    }

    protected void addInsertAt(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("insertAt") //
                .label("aql:'Insert at'") //
                .help("aql:self.getFeatureDescription('insertAt')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('insertAt').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('insertAt')") //
                .value("feature:insertAt") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('insertAt')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'insertAt')") //
                .unsetOperation("aql:item.delete(self, 'insertAt'))") //
                .clearOperation("aql:self.clearReference('insertAt')"); //
        group.getChildren().add(builder.build());
    }

    protected void addValue(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("value") //
                .label("aql:'Value'") //
                .help("aql:self.getFeatureDescription('value')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('value').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('value')") //
                .value("feature:value") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('value')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'value')") //
                .unsetOperation("aql:item.delete(self, 'value'))") //
                .clearOperation("aql:self.clearReference('value')"); //
        group.getChildren().add(builder.build());
    }

    protected void addQualifier(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("qualifier") //
                .label("aql:'Qualifier'") //
                .help("aql:self.getFeatureDescription('qualifier')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('qualifier').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('qualifier')") //
                .isMany(true) //
                .value("feature:qualifier") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'qualifier'))") //
                .reorderOperation("aql:self.moveReferenceElement('qualifier', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

}
