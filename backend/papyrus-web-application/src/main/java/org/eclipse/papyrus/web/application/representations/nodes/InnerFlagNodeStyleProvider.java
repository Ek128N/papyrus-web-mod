/*****************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.papyrus.web.application.representations.nodes;

import java.util.Optional;

import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.InnerFlagNodeStyleDescription;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.emf.diagram.INodeStyleProvider;
import org.springframework.stereotype.Service;

/**
 * This class provides style information for the custom inner node flag.<br>
 * Code duplicated from <a href="https://github.com/eclipse-sirius/sirius-web">Sirius Web</a>
 * (sirius-web-sample-application\src\main\java\org\eclipse\sirius\web\sample\nodes\EllipseNodeStyleProvider.java).
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@Service
public class InnerFlagNodeStyleProvider implements INodeStyleProvider {

    public static final String NODE_INNER_FLAG = "customnode:innerFlag";

    @Override
    public Optional<String> getNodeType(NodeStyleDescription nodeStyle) {
        if (nodeStyle instanceof InnerFlagNodeStyleDescription) {
            return Optional.of(NODE_INNER_FLAG);
        }
        return Optional.empty();
    }

    @Override
    public Optional<INodeStyle> createNodeStyle(NodeStyleDescription nodeStyle, Optional<String> optionalEditingContextId) {
        Optional<INodeStyle> iNodeStyle = Optional.empty();
        Optional<String> nodeType = this.getNodeType(nodeStyle);
        if (nodeType.isPresent() && nodeStyle instanceof InnerFlagNodeStyleDescription flagStyle) {
            return Optional.of(InnerFlagNodeStyle.newInnerFlagNodeStyle()
                    .background(Optional.ofNullable(((InnerFlagNodeStyleDescription) nodeStyle).getBackground())
                            .filter(FixedColor.class::isInstance)
                            .map(FixedColor.class::cast)
                            .map(FixedColor::getValue)
                            .orElse("transparent"))
                    .borderColor(Optional.ofNullable(nodeStyle.getBorderColor())
                            .filter(FixedColor.class::isInstance)
                            .map(FixedColor.class::cast)
                            .map(FixedColor::getValue)
                            .orElse("black"))
                    .borderSize(nodeStyle.getBorderSize())
                    .borderStyle(LineStyle.valueOf(nodeStyle.getBorderLineStyle().getLiteral()))
                    .build());
        }

        return iNodeStyle;
    }
}
