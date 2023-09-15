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
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

/**
 * Custom implementation of LiteralUnlimitedNaturalValueUmlPage.
 * 
 * @author Jerome Gout
 */
public class LiteralUnlimitedNaturalUmlPageCustomImpl extends LiteralUnlimitedNaturalUmlPage {
    public LiteralUnlimitedNaturalUmlPageCustomImpl(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super(viewElementFactory, colorRegistry);
    }

    // This override is because original valueExpression and contextExpression found in VSM contain the Input parameter
    // which is not needed by services.
    @Override
    protected void addValue(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createTextfieldDescription("value", "aql:'Value'", "aql:self.getLiteralUnlimitedNaturalValue()",
                "aql:self.oclAsType(uml::LiteralUnlimitedNatural).setLiteralUnlimitedNaturalValue(newValue)", "aql:self.getFeatureDescription('value')",
                "aql:self.eClass().getEStructuralFeature('value').changeable");
        group.getChildren().add(widget);
    }
}
