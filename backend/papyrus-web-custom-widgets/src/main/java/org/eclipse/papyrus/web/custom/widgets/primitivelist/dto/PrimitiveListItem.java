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
import java.util.Objects;
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

    private String imageURL;

    private boolean deletable;

    private Supplier<IStatus> deleteHandler;

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

    public String getImageURL() {
        return this.imageURL;
    }

    public boolean isDeletable() {
        return this.deletable;
    }

    public Supplier<IStatus> getDeleteHandler() {
        return this.deleteHandler;
    }

    public static Builder newPrimitiveListItem(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, kind: {3}, deletable: {4}, imageURL: {5}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.kind, this.deletable, this.imageURL);
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

        private String imageURL;

        private boolean deletable;

        private Supplier<IStatus> deleteHandler;

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

        public Builder imageURL(String imageURL) {
            this.imageURL = Objects.requireNonNull(imageURL);
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

        public PrimitiveListItem build() {
            PrimitiveListItem listItem = new PrimitiveListItem();
            listItem.id = Objects.requireNonNull(this.id);
            listItem.label = Objects.requireNonNull(this.label);
            listItem.kind = Objects.requireNonNull(this.kind);
            listItem.deletable = this.deletable;
            listItem.deleteHandler = Objects.requireNonNull(this.deleteHandler);
            listItem.imageURL = Objects.requireNonNull(this.imageURL);
            return listItem;
        }
    }
}
