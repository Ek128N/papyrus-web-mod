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
package org.eclipse.papyrus.web.application.representations.view;

import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.LineStyle;

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

    private final UserColor constraintColor;

    private final UserColor modelColor;

    private ColorPalette colorPalette;

    public StyleProvider(View view, String colorPrefix) {
        this.colorPalette = ViewFactory.eINSTANCE.createColorPalette();
        view.getColorPalettes().add(this.colorPalette);
        this.colorPalette.setName(colorPrefix + "ColorPalette");
        this.nodeColor = this.createFixedColor(colorPrefix + "Default Node Background", "#fefefe");
        this.borderNodeColor = this.createFixedColor(colorPrefix + "Default Node", "#0b006b");
        this.nodeLabelColor = this.createFixedColor(colorPrefix + "Default Label", "#0b006b");
        this.noteColor = this.createFixedColor(colorPrefix + "Comment", "#fffff0");
        this.constraintColor = this.createFixedColor(colorPrefix + "Constraint", "#c8ffe6");
        this.modelColor = this.createFixedColor(colorPrefix + "Model", "#f1f8fe");
        this.edgeColor = this.borderNodeColor;
    }

    private FixedColor createFixedColor(String name, String value) {
        var fixedColor = ViewFactory.eINSTANCE.createFixedColor();
        fixedColor.setName(name);
        fixedColor.setValue(value);
        this.colorPalette.getColors().add(fixedColor);
        return fixedColor;
    }

    public UserColor getNoteColor() {
        return this.noteColor;
    }

    public UserColor getConstraintColor() {
        return this.constraintColor;
    }

    public UserColor getModelColor() {
        return this.modelColor;
    }

    public LineStyle getEdgeStyle() {
        return this.edgeStyle;
    }

    public StyleProvider setEdgeStyle(LineStyle aEdgeStyle) {
        this.edgeStyle = aEdgeStyle;
        return this;
    }

    public ArrowStyle getSourceArrowStyle() {
        return this.sourceArrowStyle;
    }

    public StyleProvider setSourceArrowStyle(ArrowStyle aSourceArrowStyle) {
        this.sourceArrowStyle = aSourceArrowStyle;
        return this;
    }

    public ArrowStyle getTargetArrowStyle() {
        return this.targetArrowStyle;
    }

    public StyleProvider setTargetArrowStyle(ArrowStyle aTargetArrowStyle) {
        this.targetArrowStyle = aTargetArrowStyle;
        return this;
    }

    public int getEdgeWidth() {
        return this.edgeWidth;
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public StyleProvider setFontSize(int aFontSize) {
        this.fontSize = aFontSize;
        return this;
    }

    public int getPortSize() {
        return this.portSize;
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
        return this.edgeColor;
    }

    public StyleProvider setEdgeColor(UserColor anEdgeColor) {
        this.edgeColor = anEdgeColor;
        return this;
    }

    public UserColor getNodeColor() {
        return this.nodeColor;
    }

    public StyleProvider setNodeColor(UserColor aNodeColor) {
        this.nodeColor = aNodeColor;
        return this;
    }

    public UserColor getBorderNodeColor() {
        return this.borderNodeColor;
    }

    public StyleProvider setBorderNodeColor(UserColor aBorderNodeColor) {
        this.borderNodeColor = aBorderNodeColor;
        return this;
    }

    public int getNodeBorderRadius() {
        return this.nodeBorderRadius;
    }

    public StyleProvider setNodeBorderRadius(int aNodeBorderRadius) {
        this.nodeBorderRadius = aNodeBorderRadius;
        return this;
    }

    public UserColor getNodeLabelColor() {
        return this.nodeLabelColor;
    }

    public StyleProvider setNodeLabelColor(UserColor aNodeLabelColor) {
        this.nodeLabelColor = aNodeLabelColor;
        return this;
    }

}
