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
import org.eclipse.papyrus.web.application.properties.ContainmentReferenceWidgetBuilder;
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;

/**
 * Custom implementation of {@link OperationTemplateParameterUmlPage}.
 * 
 * @author Arthur Daussy
 */
public class OperationTemplateParameterUmlPageCustomImpl extends OperationTemplateParameterUmlPage {

    public OperationTemplateParameterUmlPageCustomImpl(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super(viewElementFactory, colorRegistry);
    }

    @Override
    protected void addOwnedParameteredElement(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("ownedParameteredElement") //
                .label("aql:'Owned parametered element'") //
                .help("aql:self.getFeatureDescription('ownedParameteredElement')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('ownedParameteredElement').changeable") //
                .owner("") //
                // Only operation can be used here
                .type("aql:'uml::Operation'") //
                .isMany(false) //
                .value("feature:ownedParameteredElement") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'ownedParameteredElement'))");
        group.getChildren().add(builder.build());
    }

}
