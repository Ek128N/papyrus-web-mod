/*****************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.papyrus.web.application.nodes;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;

/**
 * The note node style.
 * Code duplicated from <a href="https://github.com/eclipse-sirius/sirius-web">Sirius Web</a> (sirius-web-sample-application\src\main\java\org\eclipse\sirius\web\sample\nodes\EllipseNodeStyle.java).
 *
 * @author frouene
 */
@Immutable
public final class NoteNodeStyle implements INodeStyle {

    private String color;

    private String borderColor;

    private int borderSize;

    private LineStyle borderStyle;

    private NoteNodeStyle() {
        // Prevent instantiation
    }

    public static Builder newNoteNodeStyle() {
        return new Builder();
    }

    public String getColor() {
        return this.color;
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
        String pattern = "{0} '{'color: {1}, border: '{' color: {2}, size: {3}, style: {4} '}''}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.color, this.borderColor, this.borderSize, this.borderStyle);
    }

    /**
     * The builder used to create the note node style.
     *
     * @author hmarchadour
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String color;

        private String borderColor;

        private int borderSize;

        private LineStyle borderStyle;

        private Builder() {
            // Prevent instantiation
        }

        public Builder color(String color) {
            this.color = Objects.requireNonNull(color);
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

        public NoteNodeStyle build() {
            NoteNodeStyle nodeStyleDescription = new NoteNodeStyle();
            nodeStyleDescription.color = Objects.requireNonNull(this.color);
            nodeStyleDescription.borderColor = Objects.requireNonNull(this.borderColor);
            nodeStyleDescription.borderSize = this.borderSize;
            nodeStyleDescription.borderStyle = Objects.requireNonNull(this.borderStyle);
            return nodeStyleDescription;
        }
    }
}
