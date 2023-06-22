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

import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.GroupDescription;
import org.eclipse.sirius.components.view.GroupDisplayMode;
import org.eclipse.sirius.components.view.PageDescription;
import org.eclipse.sirius.components.view.WidgetDescription;

public class PortUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    public PortUmlPage(ViewElementsFactory viewElementFactory) {
        super();
        this.viewElementFactory = viewElementFactory;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createPortUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("port_uml_pageFrom", "uml::Port", "aql:'UML'", "${formPreconditionExpression}");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("port_uml_page", "uml::Port", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createPortUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("port_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsBehavior(group);
        addIsDerived(group);
        addIsDerivedUnion(group);
        addIsOrdered(group);
        addIsService(group);
        addIsConjugated(group);
        addVisibility(group);
        addMultiplicity(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')");
        group.getWidgets().add(widget);
    }

    protected void addIsBehavior(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isBehavior", "Is behavior", "feature:isBehavior", "aql:self.set('isBehavior',newValue)",
                "aql:self.getFeatureDescription('isBehavior')");
        group.getWidgets().add(widget);
    }

    protected void addIsDerived(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isDerived", "aql:'Is derived'", "feature:isDerived", "aql:self.set('isDerived',newValue)",
                "aql:self.getFeatureDescription('isDerived')");
        group.getWidgets().add(widget);
    }

    protected void addIsDerivedUnion(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isDerivedUnion", "aql:'Is derived union'", "feature:isDerivedUnion", "aql:self.set('isDerivedUnion',newValue)",
                "aql:self.getFeatureDescription('isDerivedUnion')");
        group.getWidgets().add(widget);
    }

    protected void addIsOrdered(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isOrdered", "aql:'Is ordered'", "feature:isOrdered", "aql:self.set('isOrdered',newValue)",
                "aql:self.getFeatureDescription('isOrdered')");
        group.getWidgets().add(widget);
    }

    protected void addIsService(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isService", "aql:'Is service'", "feature:isService", "aql:self.set('isService',newValue)",
                "aql:self.getFeatureDescription('isService')");
        group.getWidgets().add(widget);
    }

    protected void addIsConjugated(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isConjugated", "aql:'Is conjugated'", "feature:isConjugated", "aql:self.set('isConjugated',newValue)",
                "aql:self.getFeatureDescription('isConjugated')");
        group.getWidgets().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')");
        group.getWidgets().add(widget);
    }

    protected void addMultiplicity(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("multiplicity", "aql:'Multiplicity'", "aql:self.getMultiplicity()",
                "aql:self.oclAsType(uml::MultiplicityElement).setMultiplicity(newValue)", "aql:self.getMultiplicityHelpContent()");
        group.getWidgets().add(widget);
    }

}
