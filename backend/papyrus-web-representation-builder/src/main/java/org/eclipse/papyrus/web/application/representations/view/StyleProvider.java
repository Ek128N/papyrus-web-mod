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
package org.eclipse.papyrus.web.application.representations.view;

import org.eclipse.sirius.components.view.ArrowStyle;
import org.eclipse.sirius.components.view.LineStyle;

/**
 * Object in charge of providing styles.
 *
 * @author Arthur Daussy
 */
public class StyleProvider {

    private static final String BLACK_COLOR = "#000000";

    private int fontSize = 14;

    private int portSize = 25;

    private LineStyle edgeStyle = LineStyle.SOLID;

    private ArrowStyle sourceArrowStyle = ArrowStyle.NONE;

    private ArrowStyle targetArrowStyle = ArrowStyle.NONE;

    private int edgeWidth = 1;

    private String edgeColor = BLACK_COLOR;

    private String nodeColor = BLACK_COLOR;

    private String borderNodeColor = BLACK_COLOR;

    private int nodeBorderRadius;

    private String nodeLabelColor = BLACK_COLOR;

    public LineStyle getEdgeStyle() {
        return edgeStyle;
    }

    public StyleProvider setEdgeStyle(LineStyle aEdgeStyle) {
        this.edgeStyle = aEdgeStyle;
        return this;
    }

    public ArrowStyle getSourceArrowStyle() {
        return sourceArrowStyle;
    }

    public StyleProvider setSourceArrowStyle(ArrowStyle aSourceArrowStyle) {
        this.sourceArrowStyle = aSourceArrowStyle;
        return this;
    }

    public ArrowStyle getTargetArrowStyle() {
        return targetArrowStyle;
    }

    public StyleProvider setTargetArrowStyle(ArrowStyle aTargetArrowStyle) {
        this.targetArrowStyle = aTargetArrowStyle;
        return this;
    }

    public int getEdgeWidth() {
        return edgeWidth;
    }

    public int getFontSize() {
        return fontSize;
    }

    public StyleProvider setFontSize(int aFontSize) {
        this.fontSize = aFontSize;
        return this;
    }

    public int getPortSize() {
        return portSize;
    }

    public StyleProvider setPortSize(int aPortSize) {
        this.portSize = aPortSize;
        return this;
    }

    public StyleProvider setEdgeWidth(int anEdgeWidth) {
        this.edgeWidth = anEdgeWidth;
        return this;
    }

    public String getEdgeColor() {
        return edgeColor;
    }

    public StyleProvider setEdgeColor(String anEdgeColor) {
        this.edgeColor = anEdgeColor;
        return this;
    }

    public String getNodeColor() {
        return nodeColor;
    }

    public StyleProvider setNodeColor(String aNodeColor) {
        this.nodeColor = aNodeColor;
        return this;
    }

    public String getBorderNodeColor() {
        return borderNodeColor;
    }

    public StyleProvider setBorderNodeColor(String aBorderNodeColor) {
        this.borderNodeColor = aBorderNodeColor;
        return this;
    }

    public int getNodeBorderRadius() {
        return nodeBorderRadius;
    }

    public StyleProvider setNodeBorderRadius(int aNodeBorderRadius) {
        this.nodeBorderRadius = aNodeBorderRadius;
        return this;
    }

    public String getNodeLabelColor() {
        return nodeLabelColor;
    }

    public StyleProvider setNodeLabelColor(String aNodeLabelColor) {
        this.nodeLabelColor = aNodeLabelColor;
        return this;
    }

}
