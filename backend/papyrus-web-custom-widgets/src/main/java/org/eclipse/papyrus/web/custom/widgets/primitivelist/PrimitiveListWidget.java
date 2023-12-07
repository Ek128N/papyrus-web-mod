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
package org.eclipse.papyrus.web.custom.widgets.primitivelist;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.papyrus.web.custom.widgets.primitivelist.dto.PrimitiveListItem;
import org.eclipse.papyrus.web.custom.widgets.primitivelist.dto.ReorderPrimitiveListHandlerParameters;
import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.ListStyle;
import org.eclipse.sirius.components.forms.validation.Diagnostic;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * A widget to view/edit an EMF EAttribute of cardinality n.
 *
 * @author Arthur Daussy
 */
@Immutable
public final class PrimitiveListWidget extends AbstractWidget {

    private java.util.List<PrimitiveListItem> items;

    private ListStyle style;

    private Function<String, IStatus> newValueHandler;

    private Supplier<List<PrimitiveListCandidate>> candidatesProvider;

    private Function<ReorderPrimitiveListHandlerParameters, IStatus> reorderHandler;

    private PrimitiveListWidget() {
        // Prevent instantiation
    }

    public boolean canAdd() {
        return this.newValueHandler != null;
    }

    public boolean canReorder() {
        return this.reorderHandler != null;
    }

    public boolean hasCandidates() {
        return this.candidatesProvider != null;
    }

    public Function<String, IStatus> getNewValueHandler() {
        return this.newValueHandler;
    }

    public java.util.List<PrimitiveListItem> getItems() {
        return this.items;
    }

    public ListStyle getStyle() {
        return this.style;
    }

    public Supplier<List<PrimitiveListCandidate>> getCandidatesProvider() {
        return this.candidatesProvider;
    }

    public Function<ReorderPrimitiveListHandlerParameters, IStatus> getReorderHandler() {
        return this.reorderHandler;
    }

    public static Builder newPrimitiveList(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, items: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId(), this.label, this.items);
    }

    /**
     * The builder used to create the list.
     *
     * @author Arthur Daussy
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private List<String> iconURL;

        private ListStyle style;

        private java.util.List<PrimitiveListItem> items;

        private java.util.List<Diagnostic> diagnostics;

        private Supplier<String> helpTextProvider;

        private boolean readOnly;

        private Function<String, IStatus> newValueHandler;

        private Supplier<List<PrimitiveListCandidate>> candidatesProvider;

        private Function<ReorderPrimitiveListHandlerParameters, IStatus> reorderHandler;

        private Builder(String id) {
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

        public Builder style(ListStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public Builder items(java.util.List<PrimitiveListItem> items) {
            this.items = Objects.requireNonNull(items);
            return this;
        }

        public Builder diagnostics(java.util.List<Diagnostic> diagnostics) {
            this.diagnostics = Objects.requireNonNull(diagnostics);
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

        public Builder newValueHandler(Function<String, IStatus> newValueHandler) {
            this.newValueHandler = Objects.requireNonNull(newValueHandler);
            return this;
        }

        public Builder candidatesProvider(Supplier<List<PrimitiveListCandidate>> candidatesProvider) {
            this.candidatesProvider = candidatesProvider;
            return this;
        }

        public Builder reorderHandler(Function<ReorderPrimitiveListHandlerParameters, IStatus> reorderHandler) {
            this.reorderHandler = reorderHandler;
            return this;
        }

        public PrimitiveListWidget build() {
            PrimitiveListWidget list = new PrimitiveListWidget();
            list.id = Objects.requireNonNull(this.id);
            list.label = Objects.requireNonNull(this.label);
            list.iconURL = this.iconURL; // Optional on purpose
            list.style = this.style; // Optional on purpose
            list.items = Objects.requireNonNull(this.items);
            list.diagnostics = Objects.requireNonNull(this.diagnostics);
            list.helpTextProvider = this.helpTextProvider; // Optional on purpose
            list.readOnly = this.readOnly;
            list.newValueHandler = this.newValueHandler; // Optional on purpose
            list.candidatesProvider = this.candidatesProvider;
            list.reorderHandler = this.reorderHandler; // Optional on purpose
            return list;
        }
    }

}
