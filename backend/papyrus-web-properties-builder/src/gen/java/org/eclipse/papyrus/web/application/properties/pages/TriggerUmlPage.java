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
import org.eclipse.papyrus.web.application.properties.MonoReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.MultiReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class TriggerUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public TriggerUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createTriggerUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("trigger_uml_page", "uml::Trigger", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createTriggerUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("trigger_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addName(group);
        addVisibility(group);
        addEvent(group);
        addPort(group);

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

    protected void addEvent(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("event") //
                .label("aql:'Event'") //
                .help("aql:self.getFeatureDescription('event')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('event').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('event')") //
                .value("feature:event") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('event')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'event')") //
                .unsetOperation("aql:item.delete(self, 'event'))") //
                .clearOperation("aql:self.clearReference('event')"); //
        group.getChildren().add(builder.build());
    }

    protected void addPort(GroupDescription group) {
        var builder = new MultiReferenceWidgetBuilder() //
                .name("port") //
                .label("aql:'Port'") //
                .help("aql:self.getFeatureDescription('port')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('port').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('port')") //
                .value("feature:port") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('port')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .addOperation("aql:self.addReferenceElement(newValue, 'port')") //
                .removeOperation("aql:item.delete(self, 'port'))") //
                .reorderOperation("aql:self.moveReferenceElement('port', item, fromIndex, toIndex)") //
                .clearOperation("aql:self.clearReference('port')"); //
        group.getChildren().add(builder.build());
    }

}
