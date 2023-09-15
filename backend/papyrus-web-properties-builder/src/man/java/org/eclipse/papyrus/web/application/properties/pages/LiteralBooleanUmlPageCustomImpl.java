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
import org.eclipse.sirius.components.view.form.FlexDirection;
import org.eclipse.sirius.components.view.form.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.form.FormFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

/**
 * Custom implementation of LiteralBooleanUmlPage.
 * 
 * @author Jerome Gout
 */
public class LiteralBooleanUmlPageCustomImpl extends LiteralBooleanUmlPage {

    public LiteralBooleanUmlPageCustomImpl(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super(viewElementFactory, colorRegistry);
    }

    @Override
    protected void addValue(GroupDescription group) {
        FlexboxContainerDescription widget = FormFactory.eINSTANCE.createFlexboxContainerDescription();
        widget.setFlexDirection(FlexDirection.ROW);
        widget.setLabelExpression("aql:'Value'");
        widget.setHelpExpression("aql:self.getFeatureDescription('value')");
        widget.setName("value");
        WidgetDescription trueCheck = viewElementFactory.createCheckboxDescription("isTrue", "aql:'True'", "feature:value", "aql:self.set('value',newValue)", "",
                "aql:self.eClass().getEStructuralFeature('value').changeable");
        WidgetDescription falseCheck = viewElementFactory.createCheckboxDescription("isFalse", "aql:'False'", "aql:not self.value", "aql:self.set('value',not newValue)", "",
                "aql:self.eClass().getEStructuralFeature('value').changeable");
        widget.getChildren().add(trueCheck);
        widget.getChildren().add(falseCheck);
        group.getChildren().add(widget);
    }
}
