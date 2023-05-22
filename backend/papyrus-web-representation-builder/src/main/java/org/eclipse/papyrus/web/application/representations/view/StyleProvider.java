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
import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.LineStyle;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;

/**
 * Object in charge of providing styles.
 *
 * @author Arthur Daussy
 */
public class StyleProvider {

    private int fontSize = 14;

    private int portSize = 25;

    private LineStyle edgeStyle = LineStyle.SOLID;

    private ArrowStyle sourceArrowStyle = ArrowStyle.NONE;

    private ArrowStyle targetArrowStyle = ArrowStyle.NONE;

    private int edgeWidth = 1;

    private UserColor edgeColor;

    private UserColor nodeColor;

    private UserColor borderNodeColor;

    private int nodeBorderRadius;

    private UserColor nodeLabelColor;

    private final UserColor noteColor;

    private final UserColor modelColor;

    private ColorPalette colorPalette;

    public StyleProvider(View view) {
        colorPalette = ViewFactory.eINSTANCE.createColorPalette();
        view.getColorPalettes().add(colorPalette);
        nodeColor = createFixedColor("Default Node Background", "#fefefe");
        borderNodeColor = createFixedColor("Default Node", "#0b006b");
        nodeLabelColor = createFixedColor("Default Label", "#0b006b");
        noteColor = createFixedColor("Comment", "#fffff0");
        modelColor = createFixedColor("Model", "#f1f8fe");
        edgeColor = borderNodeColor;
    }

    private FixedColor createFixedColor(String name, String value) {
        var fixedColor = ViewFactory.eINSTANCE.createFixedColor();
        fixedColor.setName(name);
        fixedColor.setValue(value);
        colorPalette.getColors().add(fixedColor);
        return fixedColor;
    }

    public UserColor getNoteColor() {
        return noteColor;
    }

    public UserColor getModelColor() {
        return modelColor;
    }

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

    public UserColor getEdgeColor() {
        return edgeColor;
    }

    public StyleProvider setEdgeColor(UserColor anEdgeColor) {
        this.edgeColor = anEdgeColor;
        return this;
    }

    public UserColor getNodeColor() {
        return nodeColor;
    }

    public StyleProvider setNodeColor(UserColor aNodeColor) {
        this.nodeColor = aNodeColor;
        return this;
    }

    public UserColor getBorderNodeColor() {
        return borderNodeColor;
    }

    public StyleProvider setBorderNodeColor(UserColor aBorderNodeColor) {
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

    public UserColor getNodeLabelColor() {
        return nodeLabelColor;
    }

    public StyleProvider setNodeLabelColor(UserColor aNodeLabelColor) {
        this.nodeLabelColor = aNodeLabelColor;
        return this;
    }

}
