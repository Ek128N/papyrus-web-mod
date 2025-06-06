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

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;

/**
 * The cuboid node style.
 * Code duplicated from <a href="https://github.com/eclipse-sirius/sirius-web">Sirius Web</a> (sirius-web-sample-application\src\main\java\org\eclipse\sirius\web\sample\nodes\EllipseNodeStyle.java).
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@Immutable
public final class CuboidNodeStyle implements INodeStyle {

    private String background;

    private String borderColor;

    private int borderSize;

    private LineStyle borderStyle;

    private CuboidNodeStyle() {
        // Prevent instantiation
    }

    public static Builder newCuboidNodeStyle() {
        return new Builder();
    }

    public String getBackground() {
        return this.background;
    }

    public String getBorderColor() {
        return this.borderColor;
    }

    public int getBorderSize() {
        return this.borderSize;
    }

    public LineStyle getBorderStyle() {
        return this.borderStyle;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'color: {1}, border: '{' background: {2}, size: {3}, style: {4}'}''}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.background, this.borderColor, this.borderSize, this.borderStyle);
    }

    /**
     * The builder used to create the cuboid node style.
     *
     * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String background;

        private String borderColor;

        private int borderSize;

        private LineStyle borderStyle;

        private Builder() {
            // Prevent instantiation
        }

        public Builder background(String background) {
            this.background = Objects.requireNonNull(background);
            return this;
        }

        public Builder borderColor(String borderColor) {
            this.borderColor = Objects.requireNonNull(borderColor);
            return this;
        }

        public Builder borderSize(int borderSize) {
            this.borderSize = borderSize;
            return this;
        }

        public Builder borderStyle(LineStyle borderStyle) {
            this.borderStyle = Objects.requireNonNull(borderStyle);
            return this;
        }

        public CuboidNodeStyle build() {
            CuboidNodeStyle nodeStyleDescription = new CuboidNodeStyle();
            nodeStyleDescription.background = Objects.requireNonNull(this.background);
            nodeStyleDescription.borderColor = Objects.requireNonNull(this.borderColor);
            nodeStyleDescription.borderSize = this.borderSize;
            nodeStyleDescription.borderStyle = Objects.requireNonNull(this.borderStyle);
            return nodeStyleDescription;
        }
    }
}
