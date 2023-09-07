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

public class CommunicationPathUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public CommunicationPathUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createCommunicationPathUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("communicationPath_uml_pageFrom", "uml::CommunicationPath", "aql:'UML'", "");
    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("communicationPath_uml_page", "uml::CommunicationPath", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createCommunicationPathUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("communicationPath_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addIsAbstract(group);
        addIsDerived(group);
        addVisibility(group);
        addMemberEnd(group);
        addNavigableOwnedEnd(group);
        addOwnedEnd(group);
        addUseCase(group);

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

    protected void addIsDerived(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isDerived", "aql:'Is derived'", "feature:isDerived", "aql:self.set('isDerived',newValue)",
                "aql:self.getFeatureDescription('isDerived')", "aql:self.eClass().getEStructuralFeature('isDerived').changeable");
        group.getWidgets().add(widget);
    }

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getWidgets().add(widget);
    }

    protected void addMemberEnd(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("memberEnd", "aql:'Member end'", "aql:self.getFeatureDescription('memberEnd')",
                "aql:self.eClass().getEStructuralFeature('memberEnd').changeable", "aql:'memberEnd'", "");
        group.getWidgets().add(widget);
    }

    protected void addNavigableOwnedEnd(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("navigableOwnedEnd", "aql:'Navigable owned end'", "aql:self.getFeatureDescription('navigableOwnedEnd')",
                "aql:self.eClass().getEStructuralFeature('navigableOwnedEnd').changeable", "aql:'navigableOwnedEnd'", "");
        group.getWidgets().add(widget);
    }

    protected void addOwnedEnd(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("ownedEnd", "aql:'Owned end'", "aql:self.getFeatureDescription('ownedEnd')",
                "aql:self.eClass().getEStructuralFeature('ownedEnd').changeable", "aql:'ownedEnd'", "");
        group.getWidgets().add(widget);
    }

    protected void addUseCase(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("useCase", "aql:'Use case'", "aql:self.getFeatureDescription('useCase')",
                "aql:self.eClass().getEStructuralFeature('useCase').changeable", "aql:'useCase'", "");
        group.getWidgets().add(widget);
    }

}
