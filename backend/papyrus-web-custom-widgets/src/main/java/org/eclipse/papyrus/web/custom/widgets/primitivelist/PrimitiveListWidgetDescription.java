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
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.ListStyle;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The description of the list widget.
 *
 * @author Arthur Daussy
 */
@Immutable
public final class PrimitiveListWidgetDescription extends AbstractWidgetDescription {

    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, List<String>> iconURLProvider;

    private Function<VariableManager, List<?>> itemsProvider;

    private Function<VariableManager, String> itemIdProvider;

    private Function<VariableManager, String> itemLabelProvider;

    private Function<VariableManager, String> itemKindProvider;

    private Function<VariableManager, Boolean> itemDeletableProvider;

    private Function<VariableManager, IStatus> itemDeleteHandlerProvider;

    private Function<VariableManager, ListStyle> styleProvider;

    private BiFunction<VariableManager, String, IStatus> newValueHandler;

    private Function<VariableManager, List<PrimitiveListCandidate>> candidatesProvider;

    private Function<VariableManager, IStatus> reorderHandlerProvider;

    private Function<VariableManager, IStatus> itemActionHandlerProvider;

    private Function<VariableManager, String> itemActionIconURLProvider;

    private Function<VariableManager, Boolean> itemActionPreconditionHandler;

    private PrimitiveListWidgetDescription() {
        // Prevent instantiation
    }

    public Function<VariableManager, String> getIdProvider() {
        return this.idProvider;
    }


    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, List<String>> getIconURLProvider() {
        return this.iconURLProvider;
    }

    public Function<VariableManager, List<?>> getItemsProvider() {
        return this.itemsProvider;
    }

    public BiFunction<VariableManager, String, IStatus> getNewValueHandler() {
        return this.newValueHandler;
    }

    public Function<VariableManager, List<PrimitiveListCandidate>> getCandidatesProvider() {
        return this.candidatesProvider;
    }

    public Function<VariableManager, IStatus> getReorderHandlerProvider() {
        return this.reorderHandlerProvider;
    }

    public Function<VariableManager, String> getItemIdProvider() {
        return this.itemIdProvider;
    }

    public Function<VariableManager, String> getItemLabelProvider() {
        return this.itemLabelProvider;
    }

    public Function<VariableManager, String> getItemKindProvider() {
        return this.itemKindProvider;
    }

    public Function<VariableManager, Boolean> getItemDeletableProvider() {
        return this.itemDeletableProvider;
    }

    public Function<VariableManager, IStatus> getItemDeleteHandlerProvider() {
        return this.itemDeleteHandlerProvider;
    }

    public Function<VariableManager, ListStyle> getStyleProvider() {
        return this.styleProvider;
    }

    public Function<VariableManager, IStatus> getItemActionHandlerProvider() {
        return this.itemActionHandlerProvider;
    }

    public Function<VariableManager, String> getItemActionIconURLProvider() {
        return this.itemActionIconURLProvider;
    }

    public Function<VariableManager, Boolean> getItemActionPreconditionHandler() {
        return this.itemActionPreconditionHandler;
    }

    public static Builder newPrimitiveListDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId());
    }

    /**
     * Builder used to create the list description.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, List<String>> iconURLProvider = variableManager -> List.of();

        private Function<VariableManager, Boolean> isReadOnlyProvider = variableManager -> false;

        private Function<VariableManager, List<?>> itemsProvider;

        private Function<VariableManager, String> itemIdProvider;

        private Function<VariableManager, String> itemLabelProvider;

        private Function<VariableManager, String> itemKindProvider;

        private Function<VariableManager, Boolean> itemDeletableProvider;

        private Function<VariableManager, IStatus> itemDeleteHandlerProvider;

        private Function<VariableManager, ListStyle> styleProvider;

        private Function<VariableManager, List<?>> diagnosticsProvider;

        private Function<Object, String> kindProvider;

        private Function<Object, String> messageProvider;

        private Function<VariableManager, String> helpTextProvider;

        private BiFunction<VariableManager, String, IStatus> newValueHandler;

        private Function<VariableManager, List<PrimitiveListCandidate>> candidatesProvider;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, IStatus> reorderHandlerProvider;

        private Function<VariableManager, IStatus> itemActionHandlerProvider;

        private Function<VariableManager, String> itemActionIconURLProvider;

        private Function<VariableManager, Boolean> itemActionPreconditionHandler;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder idProvider(Function<VariableManager, String> idProvider) {
            this.idProvider = Objects.requireNonNull(idProvider);
            return this;
        }

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder iconURLProvider(Function<VariableManager, List<String>> iconURLProvider) {
            this.iconURLProvider = Objects.requireNonNull(iconURLProvider);
            return this;
        }

        public Builder isReadOnlyProvider(Function<VariableManager, Boolean> isReadOnlyProvider) {
            this.isReadOnlyProvider = Objects.requireNonNull(isReadOnlyProvider);
            return this;
        }

        public Builder itemsProvider(Function<VariableManager, List<?>> itemsProvider) {
            this.itemsProvider = Objects.requireNonNull(itemsProvider);
            return this;
        }

        public Builder itemIdProvider(Function<VariableManager, String> itemIdProvider) {
            this.itemIdProvider = Objects.requireNonNull(itemIdProvider);
            return this;
        }

        public Builder itemLabelProvider(Function<VariableManager, String> itemLabelProvider) {
            this.itemLabelProvider = Objects.requireNonNull(itemLabelProvider);
            return this;
        }

        public Builder itemKindProvider(Function<VariableManager, String> itemKindProvider) {
            this.itemKindProvider = Objects.requireNonNull(itemKindProvider);
            return this;
        }

        public Builder itemDeletableProvider(Function<VariableManager, Boolean> itemDeletableProvider) {
            this.itemDeletableProvider = Objects.requireNonNull(itemDeletableProvider);
            return this;
        }

        public Builder itemDeleteHandlerProvider(Function<VariableManager, IStatus> itemDeleteHandlerProvider) {
            this.itemDeleteHandlerProvider = Objects.requireNonNull(itemDeleteHandlerProvider);
            return this;
        }

        public Builder styleProvider(Function<VariableManager, ListStyle> styleProvider) {
            this.styleProvider = Objects.requireNonNull(styleProvider);
            return this;
        }

        public Builder diagnosticsProvider(Function<VariableManager, List<?>> diagnosticsProvider) {
            this.diagnosticsProvider = Objects.requireNonNull(diagnosticsProvider);
            return this;
        }

        public Builder kindProvider(Function<Object, String> kindProvider) {
            this.kindProvider = Objects.requireNonNull(kindProvider);
            return this;
        }

        public Builder messageProvider(Function<Object, String> messageProvider) {
            this.messageProvider = Objects.requireNonNull(messageProvider);
            return this;
        }

        public Builder helpTextProvider(Function<VariableManager, String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public Builder newValueHandler(BiFunction<VariableManager, String, IStatus> newValueHandler) {
            this.newValueHandler = Objects.requireNonNull(newValueHandler);
            return this;
        }

        public Builder candidatesProvider(Function<VariableManager, List<PrimitiveListCandidate>> candidatesProvider) {
            this.candidatesProvider = Objects.requireNonNull(candidatesProvider);
            return this;
        }

        public Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = targetObjectIdProvider;
            return this;
        }

        public Builder reorderHandlerProvider(Function<VariableManager, IStatus> reorderHandlerProvider) {
            this.reorderHandlerProvider = reorderHandlerProvider;
            return this;
        }

        public Builder itemActionHandlerProvider(Function<VariableManager, IStatus> itemActionHandlerProvider) {
            this.itemActionHandlerProvider = itemActionHandlerProvider;
            return this;
        }

        public Builder itemActionIconURLProvider(Function<VariableManager, String> itemActionIconURLProvider) {
            this.itemActionIconURLProvider = itemActionIconURLProvider;
            return this;
        }

        public Builder itemActionPreconditionHandler(Function<VariableManager, Boolean> itemActionPreconditionHandler) {
            this.itemActionPreconditionHandler = itemActionPreconditionHandler;
            return this;
        }

        public PrimitiveListWidgetDescription build() {
            PrimitiveListWidgetDescription listDescription = new PrimitiveListWidgetDescription();
            listDescription.id = Objects.requireNonNull(this.id);
            listDescription.idProvider = Objects.requireNonNull(this.idProvider);
            listDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            listDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            listDescription.iconURLProvider = Objects.requireNonNull(this.iconURLProvider);
            listDescription.isReadOnlyProvider = Objects.requireNonNull(this.isReadOnlyProvider);
            listDescription.itemsProvider = Objects.requireNonNull(this.itemsProvider);
            listDescription.itemIdProvider = Objects.requireNonNull(this.itemIdProvider);
            listDescription.itemLabelProvider = Objects.requireNonNull(this.itemLabelProvider);
            listDescription.itemKindProvider = Objects.requireNonNull(this.itemKindProvider);
            listDescription.itemDeletableProvider = Objects.requireNonNull(this.itemDeletableProvider);
            listDescription.itemDeleteHandlerProvider = Objects.requireNonNull(this.itemDeleteHandlerProvider);
            listDescription.styleProvider = Objects.requireNonNull(this.styleProvider);
            listDescription.diagnosticsProvider = Objects.requireNonNull(this.diagnosticsProvider);
            listDescription.kindProvider = Objects.requireNonNull(this.kindProvider);
            listDescription.messageProvider = Objects.requireNonNull(this.messageProvider);
            listDescription.helpTextProvider = this.helpTextProvider; // Optional on purpose
            listDescription.newValueHandler = this.newValueHandler; // Optional on purpose
            listDescription.candidatesProvider = this.candidatesProvider; // Optional on purpose
            listDescription.reorderHandlerProvider = this.reorderHandlerProvider; // Optional on purpose
            listDescription.itemActionHandlerProvider = this.itemActionHandlerProvider; // Optional on purpose
            listDescription.itemActionIconURLProvider = this.itemActionIconURLProvider; // Optional on purpose
            listDescription.itemActionPreconditionHandler = this.itemActionPreconditionHandler; // Optional on purpose
            return listDescription;
        }

    }
}
