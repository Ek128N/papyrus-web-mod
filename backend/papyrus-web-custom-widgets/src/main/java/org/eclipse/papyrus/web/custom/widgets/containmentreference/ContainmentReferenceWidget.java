/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.custom.widgets.containmentreference;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.papyrus.web.custom.widgets.containmentreference.dto.CreateElementInContainmentReferenceHandlerParameters;
import org.eclipse.papyrus.web.custom.widgets.containmentreference.dto.MoveContainmentReferenceItemHandlerParamaters;
import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.validation.Diagnostic;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.widget.reference.ReferenceWidgetStyle;

/**
 * A custom widget to handle containment reference.
 *
 * @author Jerome Gout
 */
@Immutable
public final class ContainmentReferenceWidget extends AbstractWidget {

    private String descriptionId;

    private List<ContainmentReferenceItem> referenceValues;

    private String ownerId;

    private String ownerKind;

    private String referenceKind;

    private boolean many;

    private ReferenceWidgetStyle style;

    private boolean canMove;

    private Function<CreateElementInContainmentReferenceHandlerParameters, Object> createElementHandler;

    private Function<MoveContainmentReferenceItemHandlerParamaters, IStatus> moveHandler;

    private ContainmentReferenceWidget() {
        // Prevent instantiation
    }

    public static Builder newContainmentReferenceWidget(String id) {
        return new Builder(id);
    }

    public List<ContainmentReferenceItem> getReferenceValues() {
        return this.referenceValues;
    }

    public String getDescriptionId() {
        return this.descriptionId;
    }

    public String getReferenceKind() {
        return this.referenceKind;
    }

    public String getOwnerKind() {
        return this.ownerKind;
    }

    public boolean isMany() {
        return this.many;
    }

    public ReferenceWidgetStyle getStyle() {
        return this.style;
    }

    public boolean canMove() {
        return this.canMove;
    }

    public String getOwnerId() {
        return this.ownerId;
    }

    public Function<CreateElementInContainmentReferenceHandlerParameters, Object> getCreateElementHandler() {
        return this.createElementHandler;
    }

    public Function<MoveContainmentReferenceItemHandlerParamaters, IStatus> getMoveHandler() {
        return this.moveHandler;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * Builder used to create the ContainmentReferenceWidget.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String label;

        private List<String> iconURL;

        private List<Diagnostic> diagnostics;

        private Supplier<String> helpTextProvider;

        private boolean readOnly;

        private List<ContainmentReferenceItem> referenceValues;

        private String ownerKind;

        private String referenceKind;

        private boolean many;

        private ReferenceWidgetStyle style;

        private boolean canMove;

        private String ownerId;

        private Function<CreateElementInContainmentReferenceHandlerParameters, Object> createElementHandler;

        private Function<MoveContainmentReferenceItemHandlerParamaters, IStatus> moveHandler;

        private String descriptionId;

        public Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder iconURL(List<String> iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public Builder helpTextProvider(Supplier<String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public Builder readOnly(boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        public Builder diagnostics(List<Diagnostic> diagnostics) {
            this.diagnostics = Objects.requireNonNull(diagnostics);
            return this;
        }

        public Builder referenceValues(List<ContainmentReferenceItem> values) {
            this.referenceValues = Objects.requireNonNull(values);
            return this;
        }

        public Builder ownerKind(String ownerKind) {
            this.ownerKind = Objects.requireNonNull(ownerKind);
            return this;
        }

        public Builder referenceKind(String referenceKind) {
            this.referenceKind = Objects.requireNonNull(referenceKind);
            return this;
        }

        public Builder many(boolean many) {
            this.many = many;
            return this;
        }

        public Builder style(ReferenceWidgetStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public Builder canMove(boolean canMove) {
            this.canMove = canMove;
            return this;
        }

        public Builder ownerId(String ownerId) {
            this.ownerId = Objects.requireNonNull(ownerId);
            return this;
        }

        public Builder createElementHandler(Function<CreateElementInContainmentReferenceHandlerParameters, Object> createElementHandler) {
            this.createElementHandler = createElementHandler;
            return this;
        }

        public Builder moveHandler(Function<MoveContainmentReferenceItemHandlerParamaters, IStatus> moveHandler) {
            this.moveHandler = moveHandler;
            return this;
        }

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public ContainmentReferenceWidget build() {
            ContainmentReferenceWidget widget = new ContainmentReferenceWidget();
            widget.id = Objects.requireNonNull(this.id);
            widget.descriptionId = Objects.requireNonNull(this.descriptionId);
            widget.label = Objects.requireNonNull(this.label);
            widget.iconURL = this.iconURL;
            widget.diagnostics = Objects.requireNonNull(this.diagnostics);
            widget.referenceValues = Objects.requireNonNull(this.referenceValues);
            widget.ownerKind = Objects.requireNonNull(this.ownerKind);
            widget.referenceKind = Objects.requireNonNull(this.referenceKind);
            widget.many = this.many;
            widget.helpTextProvider = this.helpTextProvider; // Optional on purpose
            widget.readOnly = this.readOnly;
            widget.canMove = this.canMove;
            widget.style = this.style; // Optional on purpose
            widget.ownerId = Objects.requireNonNull(this.ownerId);
            widget.createElementHandler = this.createElementHandler; // Optional on purpose
            widget.moveHandler = this.moveHandler; // Optional on purpose
            return widget;
        }

    }
}
