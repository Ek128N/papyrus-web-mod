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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.diagram.ConditionalEdgeStyle;
import org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;

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
            ConditionalNodeStyle conditionalStyle = DiagramFactory.eINSTANCE.createConditionalNodeStyle();
            conditionalStyle.setCondition(conditionalExpression);
            nodeDescription.getConditionalStyles().add(conditionalStyle);
            NodeStyleDescription copiedStyle = EcoreUtil.copy(style);
            labelStyle = copiedStyle;
            conditionalStyle.setStyle(copiedStyle);
        } else if (element instanceof EdgeDescription) {
            EdgeDescription edgeDescription = (EdgeDescription) element;
            EdgeStyle style = edgeDescription.getStyle();
            ConditionalEdgeStyle conditionalStyle = DiagramFactory.eINSTANCE.createConditionalEdgeStyle();
            conditionalStyle.setCondition(conditionalExpression);
            edgeDescription.getConditionalStyles().add(conditionalStyle);
            labelStyle = conditionalStyle;

            // Copy all common attributes
            for (EAttribute eAttribute : DiagramPackage.eINSTANCE.getEdgeStyle().getEAllAttributes()) {
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
