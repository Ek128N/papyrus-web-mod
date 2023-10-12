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
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class ArtifactUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ArtifactUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createArtifactUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("artifact_uml_page", "uml::Artifact", "aql:'UML'", "aql:self",
                "aql:not(self.oclIsKindOf(uml::DeploymentSpecification)) and not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createArtifactUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("artifact_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addFileName(group);
        addName(group);
        addIsAbstract(group);
        addVisibility(group);
        addManifestation(group);
        addOwnedAttribute(group);
        addOwnedOperation(group);
        addUseCase(group);

    }

    protected void addFileName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("fileName", "aql:'File name'", "feature:fileName", "aql:self.set('fileName',newValue)",
                "aql:self.getFeatureDescription('fileName')", "aql:self.eClass().getEStructuralFeature('isAbstract').changeable");
        group.getChildren().add(widget);
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

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addManifestation(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("manifestation", "aql:'Manifestation'", "aql:self.getFeatureDescription('manifestation')",
                "aql:self.eClass().getEStructuralFeature('manifestation').changeable", "aql:'manifestation'", "");
        group.getChildren().add(widget);
    }

    protected void addOwnedAttribute(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("ownedAttribute", "aql:'Owned attribute'", "aql:self.getFeatureDescription('ownedAttribute')",
                "aql:self.eClass().getEStructuralFeature('ownedAttribute').changeable", "aql:'ownedAttribute'", "");
        group.getChildren().add(widget);
    }

    protected void addOwnedOperation(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("ownedOperation", "aql:'Owned operation'", "aql:self.getFeatureDescription('ownedOperation')",
                "aql:self.eClass().getEStructuralFeature('ownedOperation').changeable", "aql:'ownedOperation'", "");
        group.getChildren().add(widget);
    }

    protected void addUseCase(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("useCase", "aql:'Use case'", "aql:self.getFeatureDescription('useCase')",
                "aql:self.eClass().getEStructuralFeature('useCase').changeable", "aql:'useCase'", "");
        group.getChildren().add(widget);
    }

}
