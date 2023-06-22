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
import org.eclipse.sirius.components.view.FlexDirection;
import org.eclipse.sirius.components.view.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.GroupDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.WidgetDescription;

/**
 * Custom implementation of LiteralBooleanUmlPage.
 * 
 * @author Jerome Gout
 */
public class LiteralBooleanUmlPageCustomImpl extends LiteralBooleanUmlPage {

    public LiteralBooleanUmlPageCustomImpl(ViewElementsFactory viewElementFactory) {
        super(viewElementFactory);
    }

    @Override
    protected void addValue(GroupDescription group) {
        FlexboxContainerDescription widget = ViewFactory.eINSTANCE.createFlexboxContainerDescription();
        widget.setFlexDirection(FlexDirection.ROW);
        widget.setLabelExpression("aql:'Value'");
        // widget.setHelpExpression("aql:self.getFeatureDescription('value')");
        widget.setName("value");
        WidgetDescription trueCheck = viewElementFactory.createCheckboxDescription("isTrue", "aql:'True'", "feature:value", "aql:self.set('value',newValue)",
                "aql:self.getFeatureDescription('value')");
        WidgetDescription falseCheck = viewElementFactory.createCheckboxDescription("isFalse", "aql:'False'", "aql:not self.value", "aql:self.set('value',not newValue)",
                "aql:self.getFeatureDescription('value')");
        widget.getChildren().add(trueCheck);
        widget.getChildren().add(falseCheck);
        group.getWidgets().add(widget);
    }
}
