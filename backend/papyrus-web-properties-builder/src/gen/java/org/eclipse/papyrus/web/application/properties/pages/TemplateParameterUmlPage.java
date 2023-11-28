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

public class TemplateParameterUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public TemplateParameterUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createTemplateParameterUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("templateParameter_uml_page", "uml::TemplateParameter", "aql:'UML'", "aql:self",
                "aql:not(self.oclIsKindOf(uml::ClassifierTemplateParameter)) and not(self.oclIsKindOf(uml::OperationTemplateParameter)) and not(self.oclIsKindOf(uml::ConnectableElementTemplateParameter)) and not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createTemplateParameterUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("templateParameter_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addDefault(group);
        addOwnedDefault(group);
        addOwnedParameteredElement(group);
        addParameteredElement(group);

    }

    protected void addDefault(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("default") //
                .label("aql:'Default'") //
                .help("aql:self.getFeatureDescription('default')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('default').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('default')") //
                .value("feature:default") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('default')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'default')") //
                .unsetOperation("aql:item.delete(self, 'default'))") //
                .clearOperation("aql:self.clearReference('default')"); //
        group.getChildren().add(builder.build());
    }

    protected void addOwnedDefault(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("ownedDefault") //
                .label("aql:'Owned default'") //
                .help("aql:self.getFeatureDescription('ownedDefault')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('ownedDefault').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('ownedDefault')") //
                .isMany(false) //
                .value("feature:ownedDefault") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'ownedDefault'))");
        group.getChildren().add(builder.build());
    }

    protected void addOwnedParameteredElement(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("ownedParameteredElement") //
                .label("aql:'Owned parametered element'") //
                .help("aql:self.getFeatureDescription('ownedParameteredElement')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('ownedParameteredElement').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('ownedParameteredElement')") //
                .isMany(false) //
                .value("feature:ownedParameteredElement") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'ownedParameteredElement'))");
        group.getChildren().add(builder.build());
    }

    protected void addParameteredElement(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("parameteredElement") //
                .label("aql:'Parametered element'") //
                .help("aql:self.getFeatureDescription('parameteredElement')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('parameteredElement').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('parameteredElement')") //
                .value("feature:parameteredElement") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('parameteredElement')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'parameteredElement')") //
                .unsetOperation("aql:item.delete(self, 'parameteredElement'))") //
                .clearOperation("aql:self.clearReference('parameteredElement')"); //
        group.getChildren().add(builder.build());
    }

}
