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

public class InteractionConstraintUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public InteractionConstraintUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createInteractionConstraintUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("interactionConstraint_uml_page", "uml::InteractionConstraint", "aql:'UML'", "aql:self",
                "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createInteractionConstraintUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("interactionConstraint_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addVisibility(group);
        addContext(group);
        addMaxint(group);
        addMinint(group);
        addSpecification(group);
        addConstrainedElement(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getChildren().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getChildren().add(widget);
    }

    protected void addContext(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("context", "aql:'Context'", "aql:self.getFeatureDescription('context')",
                "aql:self.eClass().getEStructuralFeature('context').changeable", "aql:'context'", "");
        group.getChildren().add(widget);
    }

    protected void addMaxint(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("maxint", "aql:'Maxint'", "aql:self.getFeatureDescription('maxint')",
                "aql:self.eClass().getEStructuralFeature('maxint').changeable", "aql:'maxint'", "");
        group.getChildren().add(widget);
    }

    protected void addMinint(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("minint", "aql:'Minint'", "aql:self.getFeatureDescription('minint')",
                "aql:self.eClass().getEStructuralFeature('minint').changeable", "aql:'minint'", "");
        group.getChildren().add(widget);
    }

    protected void addSpecification(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("specification", "aql:'Specification'", "aql:self.getFeatureDescription('specification')",
                "aql:self.eClass().getEStructuralFeature('specification').changeable", "aql:'specification'", "");
        group.getChildren().add(widget);
    }

    protected void addConstrainedElement(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("constrainedElement", "aql:'Constrained element'", "aql:self.getFeatureDescription('constrainedElement')",
                "aql:self.eClass().getEStructuralFeature('constrainedElement').changeable", "aql:'constrainedElement'", "");
        group.getChildren().add(widget);
    }

}
