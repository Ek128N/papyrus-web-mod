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
 * Custom implementation {@link GeneralizationSetUmlPage}.
 * 
 * @author Arthur Daussy
 */
public class GeneralizationSetUmlPageCustomImpl extends GeneralizationSetUmlPage {

    public GeneralizationSetUmlPageCustomImpl(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super(viewElementFactory, colorRegistry);
    }

    @Override
    protected void addPowerType(GroupDescription group) {
        var builder = new MonoReferenceWidgetBuilder() //
                .name("powerType") //
                .label("aql:'Powertype'") //
                .help("aql:self.getFeatureDescription('powertype')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('powertype').changeable") //
                .owner("") //
                .type("aql:self.getFeatureTypeQualifiedName('powertype')") //
                // Typo in VSM powerType -> powertype
                .value("feature:powertype") //
                .searchScope("aql:self.getAllReachableRootElements()") //
                .dropdownOptions("aql:self.getAllReachableElements('powertype')") //
                .createOperation("aql:parent.create(kind, feature)") //
                .setOperation("aql:self.updateReference(newValue,'powertype')") //
                .unsetOperation("aql:item.delete(self, 'powertype'))") //
                .clearOperation("aql:self.clearReference('powertype')"); //
        group.getChildren().add(builder.build());
    }

}
