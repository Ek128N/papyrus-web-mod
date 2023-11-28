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
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;

public class PackageMergeUmlPage {

    protected final ViewElementsFactory viewElementFactory;

    protected final ColorRegistry colorRegistry;

    public PackageMergeUmlPage(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super();
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public PageDescription create() {

        PageDescription page = createPage();

        createPackageMergeUmlGroup(page);

        return page;

    }

    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("packageMerge_uml_page", "uml::PackageMerge", "aql:'UML'", "aql:self", "aql:not(selection->size()>1) and not(self.isMetaclass())");
    }

    protected void createPackageMergeUmlGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("packageMerge_uml_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        addMergedPackage(group);

    }

    protected void addMergedPackage(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("mergedPackage") //
                .label("aql:'Merged package'") //
                .help("aql:self.getFeatureDescription('mergedPackage')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('mergedPackage').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('mergedPackage')") //
                .value("feature:mergedPackage") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('mergedPackage')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'mergedPackage')") //
                .unsetOperation("aql:item.delete(self, 'mergedPackage'))") //
                .clearOperation("aql:self.clearReference('mergedPackage')"); //
        group.getChildren().add(builder.build());
    }

}
