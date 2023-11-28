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

public class DeploymentSpecificationUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public DeploymentSpecificationUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createDeploymentSpecificationUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("deploymentSpecification_uml_page", "uml::DeploymentSpecification", "aql:'UML'", "aql:self",
                "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createDeploymentSpecificationUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("deploymentSpecification_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addDeploymentLocation(group);
        addExecutionLocation(group);
        addFileName(group);
        addIsAbstract(group);
        addVisibility(group);
        addManifestation(group);
        addOwnedAttribute(group);
        addOwnedOperation(group);
        addUseCase(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addDeploymentLocation(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("deploymentLocation", "aql:'Deployment location'", "feature:deploymentLocation",
                "aql:self.set('deploymentLocation',newValue)", "aql:self.getFeatureDescription('deploymentLocation')", "aql:self.eClass().getEStructuralFeature('deploymentLocation').changeable");
        group.getChildren().add(widget);
    }

    protected void addExecutionLocation(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("executionLocation", "aql:'Execution location'", "feature:executionLocation",
                "aql:self.set('executionLocation',newValue)", "aql:self.getFeatureDescription('executionLocation')", "aql:self.eClass().getEStructuralFeature('executionLocation').changeable");
        group.getChildren().add(widget);
    }

    protected void addFileName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("fileName", "aql:'File name'", "feature:fileName", "aql:self.set('fileName',newValue)",
                "aql:self.getFeatureDescription('fileName')", "aql:self.eClass().getEStructuralFeature('fileName').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsAbstract(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isAbstract", "Is abstract", "feature:isAbstract", "aql:self.set('isAbstract',newValue)",
                "aql:self.getFeatureDescription('isAbstract')", "aql:self.eClass().getEStructuralFeature('isAbstract').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addManifestation(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("manifestation") //
                .label("aql:'Manifestation'") //
                .help("aql:self.getFeatureDescription('manifestation')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('manifestation').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('manifestation')") //
                .isMany(true) //
                .value("feature:manifestation") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'manifestation'))") //
                .reorderOperation("aql:self.moveReferenceElement('manifestation', item, fromIndex, toIndex)");
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

    protected void addUseCase(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("useCase") //
                .label("aql:'Use case'") //
                .help("aql:self.getFeatureDescription('useCase')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('useCase').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('useCase')") //
                .value("feature:useCase") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('useCase')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'useCase')") //
                .removeOperation("aql:item.delete(self, 'useCase'))") //
                .reorderOperation("aql:self.moveReferenceElement('useCase', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('useCase')"); //
        group.getChildren().add(builder.build());
    }

}
