/*******************************************************************************
 * Copyright (c) 2022, 2024 CEA LIST, Obeo.
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
 *******************************************************************************/
package org.eclipse.papyrus.web.application.representations.view.builders;

import org.eclipse.emf.ecore.EStructuralFeature;
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
        EdgeStyle style = this.element.getStyle();
        this.newStyle = DiagramFactory.eINSTANCE.createConditionalEdgeStyle();
        this.newStyle.setCondition(this.conditionalExpression);
        this.element.getConditionalStyles().add(this.newStyle);

        // Copy all common attributes
        for (EStructuralFeature eStructuralFeature : DiagramPackage.eINSTANCE.getEdgeStyle().getEAllStructuralFeatures()) {
            this.newStyle.eSet(eStructuralFeature, style.eGet(eStructuralFeature));
        }
        return this;
    }

    public EdgeConditionalStyleBuilder setColor(UserColor color) {
        this.newStyle.setColor(color);
        return this;
    }

    public EdgeConditionalStyleBuilder setFontSize(int value) {
        this.newStyle.setFontSize(value);
        return this;
    }

    public EdgeConditionalStyleBuilder setLineStyle(LineStyle value) {
        this.newStyle.setLineStyle(value);
        return this;
    }

    public EdgeConditionalStyleBuilder setItalic(boolean value) {
        this.newStyle.setItalic(value);
        return this;
    }

    public EdgeConditionalStyleBuilder setSourceArrowStyle(ArrowStyle value) {
        this.newStyle.setSourceArrowStyle(value);
        return this;
    }

    public EdgeConditionalStyleBuilder setBold(boolean value) {
        this.newStyle.setBold(value);
        return this;
    }

    public EdgeConditionalStyleBuilder setTargetArrowStyle(ArrowStyle value) {
        this.newStyle.setTargetArrowStyle(value);
        return this;
    }

    public EdgeConditionalStyleBuilder setUnderline(boolean value) {
        this.newStyle.setUnderline(value);
        return this;
    }

    public EdgeConditionalStyleBuilder setStrikeThrough(boolean value) {
        this.newStyle.setStrikeThrough(value);
        return this;
    }

    public EdgeConditionalStyleBuilder setEdgeWidth(int value) {
        this.newStyle.setEdgeWidth(value);
        return this;
    }

    public EdgeConditionalStyleBuilder setShowIcon(boolean value) {
        this.newStyle.setShowIcon(value);
        return this;
    }

}
