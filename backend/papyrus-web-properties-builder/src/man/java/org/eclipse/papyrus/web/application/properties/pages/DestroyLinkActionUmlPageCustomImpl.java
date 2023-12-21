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
 * Custom implementation of {@link DestroyLinkActionUmlPage}.
 * 
 * @author Arthur Daussy
 */
public class DestroyLinkActionUmlPageCustomImpl extends DestroyLinkActionUmlPage {

    public DestroyLinkActionUmlPageCustomImpl(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super(viewElementFactory, colorRegistry);
    }

    @Override
    protected void addEndData(GroupDescription group) {
        var builder = new ContainmentReferenceWidgetBuilder() //
                .name("endData") //
                .label("aql:'End data'") //
                .help("aql:self.getFeatureDescription('endData')") //
                .isEnable("aql:self.eClass().getEStructuralFeature('endData').changeable") //
                .owner("") //
                // We need to override the feature type because UML is buggy and throw and ArrayStoreException if
                // another LinkEndData type if given to this feature
                .type("aql:'uml::LinkEndDestructionData'") //
                .isMany(true) //
                .value("feature:endData") //
                .createOperation("aql:parent.create(kind, feature)") //
                .removeOperation("aql:item.delete(self, 'endData'))") //
                .reorderOperation("aql:self.moveReferenceElement('endData', item, fromIndex, toIndex)");
        group.getChildren().add(builder.build());
    }

}
