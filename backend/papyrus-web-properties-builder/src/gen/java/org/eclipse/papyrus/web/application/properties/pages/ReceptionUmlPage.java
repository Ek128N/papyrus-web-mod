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

public class ReceptionUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public ReceptionUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createReceptionUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("reception_uml_pageFrom", "uml::Reception", "aql:'UML'", "");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("reception_uml_page", "uml::Reception", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createReceptionUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("reception_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsAbstract(group);
        addIsStatic(group);
        addConcurrency(group);
        addVisibility(group);
        addSignal(group);
        addMethod(group);
        addOwnedParameter(group);
        addRaisedException(group);

    }

    protected void addName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", "feature:name", "aql:self.set('name',newValue)", "aql:self.getFeatureDescription('name')",
                "aql:self.eClass().getEStructuralFeature('name').changeable");
        group.getWidgets().add(widget);
    }

    protected void addIsAbstract(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isAbstract", "Is abstract", "feature:isAbstract", "aql:self.set('isAbstract',newValue)",
                "aql:self.getFeatureDescription('isAbstract')", "aql:self.eClass().getEStructuralFeature('isAbstract').changeable");
        group.getWidgets().add(widget);
    }

    protected void addIsStatic(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isStatic", "aql:'Is static'", "feature:isStatic", "aql:self.set('isStatic',newValue)",
                "aql:self.getFeatureDescription('isStatic')", "");
        group.getWidgets().add(widget);
    }

    protected void addConcurrency(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("concurrency", "aql:'Concurrency'",
                "aql:self.eClass().getEStructuralFeature('concurrency').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.concurrency.toString())",
                "aql:self.set('concurrency',newValue.instance)", "aql:self.eClass().getEStructuralFeature('concurrency').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('concurrency')", "aql:self.eClass().getEStructuralFeature('concurrency').changeable");
        group.getWidgets().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getWidgets().add(widget);
    }

    protected void addSignal(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("signal", "aql:'Signal'", "aql:self.getFeatureDescription('signal')",
                "aql:self.eClass().getEStructuralFeature('signal').changeable", "aql:'signal'", "");
        group.getWidgets().add(widget);
    }

    protected void addMethod(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("method", "aql:'Method'", "aql:self.getFeatureDescription('method')",
                "aql:self.eClass().getEStructuralFeature('method').changeable", "aql:'method'", "");
        group.getWidgets().add(widget);
    }

    protected void addOwnedParameter(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("ownedParameter", "aql:'Owned parameter'", "aql:self.getFeatureDescription('ownedParameter')",
                "aql:self.eClass().getEStructuralFeature('ownedParameter').changeable", "aql:'ownedParameter'", "");
        group.getWidgets().add(widget);
    }

    protected void addRaisedException(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("raisedException", "aql:'Raised Exception'", "aql:self.getFeatureDescription('raisedException')",
                "aql:self.eClass().getEStructuralFeature('raisedException').changeable", "aql:'raisedException'", "");
        group.getWidgets().add(widget);
    }

}
