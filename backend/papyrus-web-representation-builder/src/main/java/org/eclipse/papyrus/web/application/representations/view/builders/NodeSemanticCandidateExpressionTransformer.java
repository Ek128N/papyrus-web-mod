/*******************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.view.diagram.NodeDescription;

/**
 * Transforms a node by changing its semantic candidate expression and reusing all its children node.
 * 
 * @author Arthur Daussy
 */
public class NodeSemanticCandidateExpressionTransformer {

    public NodeDescription intoNewCanidateExpression(String newId, NodeDescription input, String semanticCandidateExpression) {
        NodeDescription n = EcoreUtil.copy(input);
        n.setName(newId);
        n.setSemanticCandidatesExpression(semanticCandidateExpression);

        n.getReusedChildNodeDescriptions().addAll(input.getChildrenDescriptions());
        n.getChildrenDescriptions().clear();

        n.getReusedBorderNodeDescriptions().addAll(input.getBorderNodesDescriptions());
        n.getBorderNodesDescriptions().clear();
        return n;
    }

}
