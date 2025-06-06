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
import org.eclipse.papyrus.web.application.properties.MonoReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;

public class QualifierValueUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public QualifierValueUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createQualifierValueUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("qualifierValue_uml_page", "uml::QualifierValue", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createQualifierValueUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("qualifierValue_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addQualifier(group);
        addValue(group);

    }

    protected void addQualifier(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("qualifier") //
                .label("aql:'Qualifier'") //
                .help("aql:self.getFeatureDescription('qualifier')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('qualifier').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('qualifier')") //
                .value("feature:qualifier") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('qualifier')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'qualifier')") //
                .unsetOperation("aql:item.delete(self, 'qualifier'))") //
                .clearOperation("aql:self.clearReference('qualifier')"); //
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

}
