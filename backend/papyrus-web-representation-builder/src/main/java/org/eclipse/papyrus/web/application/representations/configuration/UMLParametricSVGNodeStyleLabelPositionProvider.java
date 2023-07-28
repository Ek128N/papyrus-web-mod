/*****************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.representations.configuration;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.ParametricSVGNodeStyle;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ICustomNodeLabelPositionProvider;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * Customize the label position for parametric SVG node styled nodes.
 *
 * @author lfasani
 */
@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UMLParametricSVGNodeStyleLabelPositionProvider implements ICustomNodeLabelPositionProvider {

    @Override
    public Optional<Position> getLabelPosition(ISiriusWebLayoutConfigurator layoutConfigurator, Size initialLabelSize, Size nodeSize, String nodeType, INodeStyle nodeStyle) {
        Optional<Position> positionOpt = Optional.empty();
        if (nodeStyle instanceof ParametricSVGNodeStyle) {
            String svgURL = ((ParametricSVGNodeStyle) nodeStyle).getSvgURL();
            if (svgURL.contains(ParametricSVGImageRegistryCustomImpl.PARAMETRIC_CLASS_IMAGE_ID.toString())) {
                // horizontally centered
                ElkPadding labelPadding = layoutConfigurator.configureByType(nodeType).getProperty(CoreOptions.NODE_LABELS_PADDING);
                positionOpt = Optional.of(Position.at(nodeSize.getWidth() / 2 - initialLabelSize.getWidth() / 2, labelPadding.getTop()));
            } else if (svgURL.contains(ParametricSVGImageRegistryCustomImpl.PARAMETRIC_USE_CASE_IMAGE_ID.toString())) {
                // horizontally and vertically centered
                positionOpt = Optional.of(Position.at(nodeSize.getWidth() / 2 - initialLabelSize.getWidth() / 2, nodeSize.getHeight() / 2 - initialLabelSize.getHeight() / 2));
            } else if (svgURL.contains(ParametricSVGImageRegistryCustomImpl.PARAMETRIC_JOIN_IMAGE_ID.toString()) || svgURL.contains(UUID.nameUUIDFromBytes("Fork.svg".getBytes()).toString()) //$NON-NLS-1$
                    || svgURL.contains(ParametricSVGImageRegistryCustomImpl.PARAMETRIC_CHOICE_IMAGE_ID.toString())) {
                // horizontally centered and above the node
                ElkPadding labelPadding = layoutConfigurator.configureByType(nodeType).getProperty(CoreOptions.NODE_LABELS_PADDING);
                positionOpt = Optional.of(Position.at(nodeSize.getWidth() / 2 - initialLabelSize.getWidth() / 2, -initialLabelSize.getHeight() - labelPadding.getTop()));
            } else {
                // horizontally left
                ElkPadding labelPadding = layoutConfigurator.configureByType(nodeType).getProperty(CoreOptions.NODE_LABELS_PADDING);
                positionOpt = Optional.of(Position.at(labelPadding.getLeft(), labelPadding.getTop()));
            }
        }
        return positionOpt;
    }
}
