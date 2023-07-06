/*******************************************************************************
 * Copyright (c) 2022, 2023 CEA, Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.application.representations.view.builders;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.ConditionalEdgeStyle;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeStyle;
import org.eclipse.sirius.components.view.diagram.LineStyle;

/**
 * Builder of Edge conditional style.
 * 
 * @author Arthur Daussy
 */
public class EdgeConditionalStyleBuilder {

    private final EdgeDescription element;

    private final String conditionalExpression;

    private ConditionalEdgeStyle newStyle;

    public EdgeConditionalStyleBuilder(EdgeDescription element, String conditionalExpression) {
        super();
        this.element = element;
        this.conditionalExpression = conditionalExpression;
    }

    public EdgeConditionalStyleBuilder fromExistingStyle() {
        EdgeStyle style = element.getStyle();
        newStyle = DiagramFactory.eINSTANCE.createConditionalEdgeStyle();
        newStyle.setCondition(conditionalExpression);
        element.getConditionalStyles().add(newStyle);

        // Copy all common attributes
        for (EAttribute eAttribute : DiagramPackage.eINSTANCE.getEdgeStyle().getEAllAttributes()) {
            newStyle.eSet(eAttribute, style.eGet(eAttribute));
        }
        return this;
    }

    public EdgeConditionalStyleBuilder setColor(UserColor color) {
        newStyle.setColor(color);
        return this;
    }

    public EdgeConditionalStyleBuilder setFontSize(int value) {
        newStyle.setFontSize(value);
        return this;
    }

    public EdgeConditionalStyleBuilder setLineStyle(LineStyle value) {
        newStyle.setLineStyle(value);
        return this;
    }

    public EdgeConditionalStyleBuilder setItalic(boolean value) {
        newStyle.setItalic(value);
        return this;
    }

    public EdgeConditionalStyleBuilder setSourceArrowStyle(ArrowStyle value) {
        newStyle.setSourceArrowStyle(value);
        return this;
    }

    public EdgeConditionalStyleBuilder setBold(boolean value) {
        newStyle.setBold(value);
        return this;
    }

    public EdgeConditionalStyleBuilder setTargetArrowStyle(ArrowStyle value) {
        newStyle.setTargetArrowStyle(value);
        return this;
    }

    public EdgeConditionalStyleBuilder setUnderline(boolean value) {
        newStyle.setUnderline(value);
        return this;
    }

    public EdgeConditionalStyleBuilder setStrikeThrough(boolean value) {
        newStyle.setStrikeThrough(value);
        return this;
    }

    public EdgeConditionalStyleBuilder setEdgeWidth(int value) {
        newStyle.setEdgeWidth(value);
        return this;
    }

    public EdgeConditionalStyleBuilder setShowIcon(boolean value) {
        newStyle.setShowIcon(value);
        return this;
    }


}
