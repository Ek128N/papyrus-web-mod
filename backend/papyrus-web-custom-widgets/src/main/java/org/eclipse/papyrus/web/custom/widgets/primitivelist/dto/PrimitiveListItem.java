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
package org.eclipse.papyrus.web.custom.widgets.primitivelist.dto;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * The primitive list item.
 *
 * @author Arthur Daussy
 */
@Immutable
public final class PrimitiveListItem {
    private String id;

    private String label;

    private String kind;

    private List<String> iconURL;

    private boolean deletable;

    private Supplier<IStatus> deleteHandler;

    private Supplier<IStatus> actionHandler;

    private String actionIconURL;

    private BooleanSupplier actionPreconditionHandler;

    private PrimitiveListItem() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getKind() {
        return this.kind;
    }

    public List<String> getIconURL() {
        return this.iconURL;
    }

    public boolean isDeletable() {
        return this.deletable;
    }

    public Supplier<IStatus> getDeleteHandler() {
        return this.deleteHandler;
    }

    public boolean hasAction() {
        return this.actionHandler != null && (this.actionPreconditionHandler == null || this.actionPreconditionHandler.getAsBoolean());
    }

    public Supplier<IStatus> getActionHandler() {
        return this.actionHandler;
    }

    public String getActionIconURL() {
        return this.actionIconURL;
    }

    public static Builder newPrimitiveListItem(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, kind: {3}, deletable: {4}}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.kind, this.deletable);
    }

    /**
     * The builder used to create the list item.
     *
     * @author Arthur Daussy
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private String kind;

        private List<String> iconURL;

        private boolean deletable;

        private Supplier<IStatus> deleteHandler;

        private Supplier<IStatus> actionHandler;

        private String actionIconURL;

        private BooleanSupplier actionPreconditionHandler;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder kind(String kind) {
            this.kind = Objects.requireNonNull(kind);
            return this;
        }

        public Builder iconURL(List<String> iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public Builder deletable(boolean deletable) {
            this.deletable = deletable;
            return this;
        }

        public Builder deleteHandler(Supplier<IStatus> deleteHandler) {
            this.deleteHandler = Objects.requireNonNull(deleteHandler);
            return this;
        }

        public Builder actionHandler(Supplier<IStatus> actionHandler) {
            this.actionHandler = actionHandler;
            return this;
        }

        public Builder actionPreconditionHandler(BooleanSupplier actionPreconditionHandler) {
            this.actionPreconditionHandler = actionPreconditionHandler;
            return this;
        }

        public Builder actionIconURL(String actionIconURL) {
            this.actionIconURL = actionIconURL;
            return this;
        }

        public PrimitiveListItem build() {
            PrimitiveListItem listItem = new PrimitiveListItem();
            listItem.id = Objects.requireNonNull(this.id);
            listItem.label = Objects.requireNonNull(this.label);
            listItem.kind = Objects.requireNonNull(this.kind);
            listItem.deletable = this.deletable;
            listItem.deleteHandler = Objects.requireNonNull(this.deleteHandler);
            listItem.iconURL = Objects.requireNonNull(this.iconURL);
            listItem.actionHandler = this.actionHandler; // Optional on purpose
            listItem.actionIconURL = this.actionIconURL; // Optional on purpose
            listItem.actionPreconditionHandler = this.actionPreconditionHandler; // Optional on purpose
            return listItem;
        }
    }
}
