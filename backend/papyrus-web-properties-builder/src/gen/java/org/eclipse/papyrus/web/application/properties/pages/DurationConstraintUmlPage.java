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
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class DurationConstraintUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public DurationConstraintUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createDurationConstraintUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("durationConstraint_uml_pageFrom", "uml::DurationConstraint", "aql:'UML'", "");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("durationConstraint_uml_page", "uml::DurationConstraint", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createDurationConstraintUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("durationConstraint_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addVisibility(group);
        addContext(group);
        addFirstEvent(group);
        addConstrainedElement(group);
        addSpecification(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getWidgets().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getWidgets().add(widget);
    }

    protected void addContext(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("context", "aql:'Context'", "aql:self.getFeatureDescription('context')",
                "aql:self.eClass().getEStructuralFeature('context').changeable", "aql:'context'", "");
        group.getWidgets().add(widget);
    }

    protected void addFirstEvent(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createListDescription("firstEvent", "First event", "feature:firstEvent", "", "aql:false", "aql:self.getFeatureDescription('firstEvent')",
                "aql:self.eClass().getEStructuralFeature('firstEvent').changeable");
        group.getWidgets().add(widget);
    }

    protected void addConstrainedElement(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("constrainedElement", "aql:'Constrained element'", "aql:self.getFeatureDescription('constrainedElement')",
                "aql:self.eClass().getEStructuralFeature('constrainedElement').changeable", "aql:'constrainedElement'", "");
        group.getWidgets().add(widget);
    }

    protected void addSpecification(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("specification", "aql:'Specification'", "aql:self.getFeatureDescription('specification')",
                "aql:self.eClass().getEStructuralFeature('specification').changeable", "aql:'specification'", "");
        group.getWidgets().add(widget);
    }

}
