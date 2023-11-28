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

public class TemplateParameterSubstitutionUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public TemplateParameterSubstitutionUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createTemplateParameterSubstitutionUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("templateParameterSubstitution_uml_page", "uml::TemplateParameterSubstitution", "aql:'UML'", "aql:self",
                "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createTemplateParameterSubstitutionUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("templateParameterSubstitution_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addFormal(group);
        addActual(group);
        addOwnedActual(group);

    }

    protected void addFormal(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("formal") //
                .label("aql:'Formal'") //
                .help("aql:self.getFeatureDescription('formal')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('formal').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('formal')") //
                .value("feature:formal") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('formal')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'formal')") //
                .unsetOperation("aql:item.delete(self, 'formal'))") //
                .clearOperation("aql:self.clearReference('formal')"); //
        group.getChildren().add(builder.build());
    }

    protected void addActual(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("actual") //
                .label("aql:'Actual'") //
                .help("aql:self.getFeatureDescription('actual')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('actual').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('actual')") //
                .value("feature:actual") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('actual')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'actual')") //
                .unsetOperation("aql:item.delete(self, 'actual'))") //
                .clearOperation("aql:self.clearReference('actual')"); //
        group.getChildren().add(builder.build());
    }

    protected void addOwnedActual(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("ownedActual") //
                .label("aql:'Owned actual'") //
                .help("aql:self.getFeatureDescription('ownedActual')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('ownedActual').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('ownedActual')") //
                .isMany(false) //
                .value("feature:ownedActual") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'ownedActual'))");
        group.getChildren().add(builder.build());
    }

}
