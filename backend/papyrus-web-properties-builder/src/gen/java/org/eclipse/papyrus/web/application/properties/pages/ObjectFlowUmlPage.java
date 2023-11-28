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

public class ObjectFlowUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ObjectFlowUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createObjectFlowUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("objectFlow_uml_page", "uml::ObjectFlow", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createObjectFlowUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("objectFlow_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsMulticast(group);
        addIsMultireceive(group);
        addVisibility(group);
        addGuard(group);
        addWeight(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsMulticast(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isMulticast", "Is multicast", "feature:isMulticast", "aql:self.set('isMulticast',newValue)",
                "aql:self.getFeatureDescription('isMulticast')", "aql:self.eClass().getEStructuralFeature('isMulticast').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsMultireceive(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isMultireceive", "Is multireceive", "feature:isMultireceive", "aql:self.set('isMultireceive',newValue)",
                "aql:self.getFeatureDescription('isMultireceive')", "aql:self.eClass().getEStructuralFeature('isMultireceive').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addGuard(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("guard") //
                .label("aql:'Guard'") //
                .help("aql:self.getFeatureDescription('guard')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('guard').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('guard')") //
                .isMany(false) //
                .value("feature:guard") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'guard'))");
        group.getChildren().add(builder.build());
    }

    protected void addWeight(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("weight") //
                .label("aql:'Weight'") //
                .help("aql:self.getFeatureDescription('weight')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('weight').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('weight')") //
                .isMany(false) //
                .value("feature:weight") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'weight'))");
        group.getChildren().add(builder.build());
    }

}
