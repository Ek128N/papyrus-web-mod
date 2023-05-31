/*******************************************************************************
 * Copyright (c) 2022 CEA, Obeo.
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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.view.ConditionalEdgeStyle;
import org.eclipse.sirius.components.view.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.DiagramElementDescription;
import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.EdgeStyle;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.NodeStyleDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * Builder of label conditional style.
 * 
 * @author Arthur Daussy
 */
public class LabelConditionalStyleBuilder {

    private final DiagramElementDescription element;

    private final String conditionalExpression;

    private LabelStyle labelStyle;

    public LabelConditionalStyleBuilder(DiagramElementDescription element, String conditionalExpression) {
        super();
        this.element = element;
        this.conditionalExpression = conditionalExpression;
    }

    public LabelConditionalStyleBuilder fromExistingStyle() {
        if (element instanceof NodeDescription) {
            NodeDescription nodeDescription = (NodeDescription) element;
            NodeStyleDescription style = nodeDescription.getStyle();
            ConditionalNodeStyle conditionalStyle = ViewFactory.eINSTANCE.createConditionalNodeStyle();
            conditionalStyle.setCondition(conditionalExpression);
            nodeDescription.getConditionalStyles().add(conditionalStyle);
            NodeStyleDescription copiedStyle = EcoreUtil.copy(style);
            labelStyle = copiedStyle;
            conditionalStyle.setStyle(copiedStyle);
        } else if (element instanceof EdgeDescription) {
            EdgeDescription edgeDescription = (EdgeDescription) element;
            EdgeStyle style = edgeDescription.getStyle();
            ConditionalEdgeStyle conditionalStyle = ViewFactory.eINSTANCE.createConditionalEdgeStyle();
            conditionalStyle.setCondition(conditionalExpression);
            edgeDescription.getConditionalStyles().add(conditionalStyle);
            labelStyle = conditionalStyle;

            // Copy all common attributes
            for (EAttribute eAttribute : ViewPackage.eINSTANCE.getEdgeStyle().getEAllAttributes()) {
                labelStyle.eSet(eAttribute, style.eGet(eAttribute));
            }
        }
        return this;
    }

    public LabelConditionalStyleBuilder setItalic(boolean italic) {
        labelStyle.setItalic(italic);
        return this;
    }

    public LabelConditionalStyleBuilder setUnderline(boolean underline) {
        labelStyle.setUnderline(underline);
        return this;
    }

}
