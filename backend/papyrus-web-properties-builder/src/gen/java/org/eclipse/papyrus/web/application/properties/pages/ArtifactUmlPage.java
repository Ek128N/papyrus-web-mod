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
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

public class ArtifactUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    public ArtifactUmlPage(ViewElementsFactory viewElementFactory) {
        super();
        this.viewElementFactory = viewElementFactory;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createArtifactUmlGroup(page);

        return page;

    }

    protected FormDescription createFrom() {
        return viewElementFactory.createFormDescription("artifact_uml_pageFrom", "uml::Artifact", "aql:'UML'", "");
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

    }

    protected void addFileName(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("fileName", "aql:'File name'", "feature:fileName", "aql:self.set('fileName',newValue)",
                "aql:self.getFeatureDescription('fileName')", "aql:self.eClass().getEStructuralFeature('isAbstract').changeable");
        group.getWidgets().add(widget);
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

    protected void addVisibility(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createSelectDescription("visibility", "aql:'Visibility'",
                "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.visibility.toString())",
                "aql:self.set('visibility',newValue.instance)", "aql:self.eClass().getEStructuralFeature('visibility').eType.oclAsType(ecore::EEnum).eLiterals", "aql:candidate.name",
                "aql:self.getFeatureDescription('visibility')", "aql:self.eClass().getEStructuralFeature('visibility').changeable");
        group.getWidgets().add(widget);
    }

}
