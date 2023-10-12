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

public class ClassUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ClassUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createClassUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("class_uml_page", "uml::Class", "aql:'UML'", "aql:self",
                "aql:not(self.oclIsKindOf(uml::AssociationClass)) and not(self.oclIsKindOf(uml::Behavior)) and not(self.oclIsKindOf(uml::Component)) and not(self.oclIsKindOf(uml::Node)) and not(self.oclIsKindOf(uml::Stereotype)) and not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createClassUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("class_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addQualifiedName(group);
        addIsAbstract(group);
        addIsActive(group);
        addVisibility(group);
        addOwnedAttribute(group);
        addOwnedOperation(group);
        addOwnedReception(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addQualifiedName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("qualifiedName", "aql:'Qualified name'", "aql:self.getQualifiedName()", "var:self",
                "A name which allows the NamedElement to be identified within a hierarchy of nested Namespaces. It is constructed from the names of the containing namespaces starting at the root of the hierarchy and ending with the name of the NamedElement itself.",
                "aql:false");
        group.getChildren().add(widget);
    }

    protected void addIsAbstract(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isAbstract", "Is abstract", "feature:isAbstract", "aql:self.set('isAbstract',newValue)",
                "aql:self.getFeatureDescription('isAbstract')", "aql:self.eClass().getEStructuralFeature('isAbstract').changeable");
        group.getChildren().add(widget);
    }

    protected void addIsActive(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isActive", "aql:'Is active'", "feature:isActive", "aql:self.set('isActive',newValue)",
                "aql:self.getFeatureDescription('isActive')", "aql:self.eClass().getEStructuralFeature('isActive').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
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

    protected void addOwnedReception(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("ownedReception", "aql:'Owned reception'", "aql:self.getFeatureDescription('ownedReception')",
                "aql:self.eClass().getEStructuralFeature('ownedReception').changeable", "aql:'ownedReception'", "");
        group.getChildren().add(widget);
    }

}
