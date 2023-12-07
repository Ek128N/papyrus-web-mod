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
import org.eclipse.sirius.components.forms.ListStyle;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * The properties for the primitive list widget element.
 *
 * @author Arthur Daussy
 */
@Immutable
public final class PrimitiveListWidgetElementProps implements IProps {

    public static final String TYPE = PrimitiveListWidget.class.getSimpleName();

    private String id;

    private String label;

    private List<String> iconURL;

    private Supplier<String> helpTextProvider;

    private boolean readOnly;

    private ListStyle style;

    private List<PrimitiveListItem> items;

    private List<Element> children;

    private Function<String, IStatus> newValueHandler;

    private Supplier<List<PrimitiveListCandidate>> candidatesProvider;

    private Function<ReorderPrimitiveListHandlerParameters, IStatus> reorderHandler;

    private PrimitiveListWidgetElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public Function<String, IStatus> getNewValueHandler() {
        return this.newValueHandler;
    }

    public String getLabel() {
        return this.label;
    }

    public Supplier<List<PrimitiveListCandidate>> getCandidatesProvider() {
        return this.candidatesProvider;
    }

    public Function<ReorderPrimitiveListHandlerParameters, IStatus> getReorderHandler() {
        return this.reorderHandler;
    }

    public List<String> getIconURL() {
        return this.iconURL;
    }

    public Supplier<String> getHelpTextProvider() {
        return this.helpTextProvider;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public ListStyle getStyle() {
        return this.style;
    }

    public List<PrimitiveListItem> getItems() {
        return this.items;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newListElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, items: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.items);
    }

    /**
     * The builder of the list element props.
     *
     * @author Arthur Daussy
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private String label;

        private List<String> iconURL;

        private Supplier<String> helpTextProvider;

        private boolean readOnly;

        private ListStyle style;

        private List<PrimitiveListItem> items;

        private List<Element> children;

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

        public Builder readOnly(boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        public Builder style(ListStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public Builder items(List<PrimitiveListItem> items) {
            this.items = Objects.requireNonNull(items);
            return this;
        }

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public Builder helpTextProvider(Supplier<String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public Builder newValueHandler(Function<String, IStatus> newValueHandler) {
            this.newValueHandler = newValueHandler;
            return this;
        }

        public Builder candidatesProvider(Supplier<List<PrimitiveListCandidate>> candidatesProvider) {
            this.candidatesProvider = Objects.requireNonNull(candidatesProvider);
            return this;
        }

        public Builder reorderHandler(Function<ReorderPrimitiveListHandlerParameters, IStatus> reorderHandler) {
            this.reorderHandler = reorderHandler;
            return this;
        }

        public PrimitiveListWidgetElementProps build() {
            PrimitiveListWidgetElementProps primListElementProps = new PrimitiveListWidgetElementProps();
            primListElementProps.id = Objects.requireNonNull(this.id);
            primListElementProps.label = Objects.requireNonNull(this.label);
            primListElementProps.iconURL = this.iconURL;
            primListElementProps.readOnly = this.readOnly;
            primListElementProps.style = this.style; // Optional on purpose
            primListElementProps.items = Objects.requireNonNull(this.items);
            primListElementProps.children = Objects.requireNonNull(this.children);
            primListElementProps.helpTextProvider = this.helpTextProvider; // Optional on purpose
            primListElementProps.newValueHandler = this.newValueHandler; // Optional on purpose
            primListElementProps.candidatesProvider = this.candidatesProvider; // Optional on purpose
            primListElementProps.reorderHandler = this.reorderHandler; // Optional on purpose
            return primListElementProps;
        }

    }
}
