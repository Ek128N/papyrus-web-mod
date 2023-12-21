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

/**
 * Customization of {@link PackageImportUmlPage}.
 * 
 * @author Jerome Gout
 */
public class PackageImportUmlPageCustomImpl extends PackageImportUmlPage {

    public PackageImportUmlPageCustomImpl(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super(viewElementFactory, colorRegistry);
    }
    
    @Override
    protected void addImportedPackage(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("importedPackage") //
                .label("aql:'Imported package'") //
                .help("aql:self.getFeatureDescription('importedPackage')") //
                // We need this feature to be editable since it is the main entry point to compute the candidates for
                // all references
                .isEnable("aql:true") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('importedPackage')") //
                .value("feature:importedPackage") //
                .searchScope("aql:self.getAllRootPackages()") //
                .dropdownOptions("aql:self.getAllUMLPackages()") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'importedPackage')") //
                .unsetOperation("aql:item.delete(self, 'importedPackage'))") //
                .clearOperation("aql:self.clearReference('importedPackage')"); //
        group.getChildren().add(builder.build());
    }
}
