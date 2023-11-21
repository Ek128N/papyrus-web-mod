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

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.widget.reference.ReferenceWidgetStyle;

/**
 * Containment Reference widget description.
 *
 * @author Jerome Gout
 */
@Immutable
public final class ContainmentReferenceWidgetDescription extends AbstractWidgetDescription {
    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, List<String>> iconURLProvider;

    private Function<VariableManager, List<?>> itemsProvider;

    private Function<VariableManager, List<?>> optionsProvider;

    private Function<VariableManager, String> itemIdProvider;

    private Function<VariableManager, String> itemLabelProvider;

    private Function<VariableManager, String> itemKindProvider;

    private Function<VariableManager, List<String>> itemIconURLProvider;

    private Function<VariableManager, String> ownerKindProvider;

    private Function<VariableManager, String> referenceKindProvider;

    private Function<VariableManager, Boolean> isContainmentProvider;

    private Function<VariableManager, Boolean> isManyProvider;

    private Function<VariableManager, IStatus> itemClickHandlerProvider;

    private Function<VariableManager, ReferenceWidgetStyle> styleProvider;

    private Function<VariableManager, String> ownerIdProvider;

    private Function<VariableManager, IStatus> clearHandlerProvider;

    private Function<VariableManager, IStatus> itemRemoveHandlerProvider;

    private Function<VariableManager, IStatus> setHandlerProvider;

    private Function<VariableManager, IStatus> addHandlerProvider;

    private Function<VariableManager, Object> createElementHandlerProvider;

    private Function<VariableManager, IStatus> moveHandlerProvider;

    private ContainmentReferenceWidgetDescription() {
        // Prevent instantiation
    }

    public static Builder newContainmentReferenceWidgetDescription(String id) {
        return new Builder(id);
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

    public Function<VariableManager, List<?>> getOptionsProvider() {
        return this.optionsProvider;
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

    public Function<VariableManager, List<String>> getItemImageURLProvider() {
        return this.itemIconURLProvider;
    }

    public Function<VariableManager, String> getOwnerKindProvider() {
        return this.ownerKindProvider;
    }

    public Function<VariableManager, String> getReferenceKindProvider() {
        return this.referenceKindProvider;
    }

    public Function<VariableManager, Boolean> getIsContainmentProvider() {
        return this.isContainmentProvider;
    }

    public Function<VariableManager, Boolean> getIsManyProvider() {
        return this.isManyProvider;
    }

    public Function<VariableManager, IStatus> getItemClickHandlerProvider() {
        return this.itemClickHandlerProvider;
    }

    public Function<VariableManager, ReferenceWidgetStyle> getStyleProvider() {
        return this.styleProvider;
    }

    public Function<VariableManager, String> getOwnerIdProvider() {
        return this.ownerIdProvider;
    }

    public Function<VariableManager, IStatus> getClearHandlerProvider() {
        return this.clearHandlerProvider;
    }

    public Function<VariableManager, IStatus> getItemRemoveHandlerProvider() {
        return this.itemRemoveHandlerProvider;
    }

    public Function<VariableManager, IStatus> getSetHandlerProvider() {
        return this.setHandlerProvider;
    }

    public Function<VariableManager, IStatus> getAddHandlerProvider() {
        return this.addHandlerProvider;
    }

    public Function<VariableManager, Object> getCreateElementHandlerProvider() {
        return this.createElementHandlerProvider;
    }

    public Function<VariableManager, IStatus> getMoveHandlerProvider() {
        return this.moveHandlerProvider;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId());
    }

    /**
     * Builder used to create the ContainmentReferenceWidgetDescription.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, List<String>> iconURLProvider = variableManager -> null;

        private Function<VariableManager, Boolean> isReadOnlyProvider = variableManager -> false;

        private Function<VariableManager, List<?>> itemsProvider;

        private Function<VariableManager, String> itemIdProvider;

        private Function<VariableManager, String> itemLabelProvider;

        private Function<VariableManager, String> itemKindProvider;

        private Function<VariableManager, List<String>> itemIconURLProvider;

        private Function<VariableManager, String> ownerKindProvider;

        private Function<VariableManager, String> referenceKindProvider;

        private Function<VariableManager, Boolean> isManyProvider;

        private Function<VariableManager, String> helpTextProvider;

        private Function<VariableManager, ReferenceWidgetStyle> styleProvider;

        private Function<VariableManager, String> ownerIdProvider;

        private Function<VariableManager, List<?>> diagnosticsProvider;

        private Function<Object, String> kindProvider;

        private Function<Object, String> messageProvider;

        private Function<VariableManager, IStatus> itemRemoveHandlerProvider;

        private Function<VariableManager, IStatus> itemClickHandlerProvider;

        private Function<VariableManager, Object> createElementHandlerProvider;

        private Function<VariableManager, IStatus> moveHandlerProvider;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder idProvider(Function<VariableManager, String> idProvider) {
            this.idProvider = Objects.requireNonNull(idProvider);
            return this;
        }

        public Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
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

        public Builder itemIconURLProvider(Function<VariableManager, List<String>> itemImageURLProvider) {
            this.itemIconURLProvider = Objects.requireNonNull(itemImageURLProvider);
            return this;
        }

        public Builder ownerKindProvider(Function<VariableManager, String> ownerKindProvider) {
            this.ownerKindProvider = Objects.requireNonNull(ownerKindProvider);
            return this;
        }

        public Builder referenceKindProvider(Function<VariableManager, String> referenceKindProvider) {
            this.referenceKindProvider = Objects.requireNonNull(referenceKindProvider);
            return this;
        }

        public Builder isManyProvider(Function<VariableManager, Boolean> isManyProvider) {
            this.isManyProvider = Objects.requireNonNull(isManyProvider);
            return this;
        }

        public Builder helpTextProvider(Function<VariableManager, String> helpTextProvider) {
            this.helpTextProvider = helpTextProvider;
            return this;
        }

        public Builder styleProvider(Function<VariableManager, ReferenceWidgetStyle> styleProvider) {
            this.styleProvider = Objects.requireNonNull(styleProvider);
            return this;
        }

        public Builder ownerIdProvider(Function<VariableManager, String> ownerIdProvider) {
            this.ownerIdProvider = Objects.requireNonNull(ownerIdProvider);
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

        public Builder itemRemoveHandlerProvider(Function<VariableManager, IStatus> itemRemoveHandlerProvider) {
            this.itemRemoveHandlerProvider = itemRemoveHandlerProvider;
            return this;
        }

        public Builder itemClickHandlerProvider(Function<VariableManager, IStatus> itemClickHandlerProvider) {
            this.itemClickHandlerProvider = itemClickHandlerProvider;
            return this;
        }

        public Builder createElementHandlerProvider(Function<VariableManager, Object> createElementHandlerProvider) {
            this.createElementHandlerProvider = createElementHandlerProvider;
            return this;
        }

        public Builder moveHandlerProvider(Function<VariableManager, IStatus> moveHandlerProvider) {
            this.moveHandlerProvider = moveHandlerProvider;
            return this;
        }

        public ContainmentReferenceWidgetDescription build() {
            ContainmentReferenceWidgetDescription description = new ContainmentReferenceWidgetDescription();
            description.id = Objects.requireNonNull(this.id);
            description.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            description.idProvider = Objects.requireNonNull(this.idProvider);
            description.labelProvider = Objects.requireNonNull(this.labelProvider);
            description.iconURLProvider = Objects.requireNonNull(this.iconURLProvider);
            description.isReadOnlyProvider = Objects.requireNonNull(this.isReadOnlyProvider);
            description.itemsProvider = Objects.requireNonNull(this.itemsProvider);
            description.itemIdProvider = Objects.requireNonNull(this.itemIdProvider);
            description.itemLabelProvider = Objects.requireNonNull(this.itemLabelProvider);
            description.itemKindProvider = Objects.requireNonNull(this.itemKindProvider);
            description.itemIconURLProvider = Objects.requireNonNull(this.itemIconURLProvider);
            description.ownerKindProvider = Objects.requireNonNull(this.ownerKindProvider);
            description.referenceKindProvider = Objects.requireNonNull(this.referenceKindProvider);
            description.isManyProvider = Objects.requireNonNull(this.isManyProvider);
            description.helpTextProvider = this.helpTextProvider; // Optional on purpose
            description.styleProvider = Objects.requireNonNull(this.styleProvider);
            description.ownerIdProvider = Objects.requireNonNull(this.ownerIdProvider);
            description.diagnosticsProvider = Objects.requireNonNull(this.diagnosticsProvider);
            description.kindProvider = Objects.requireNonNull(this.kindProvider);
            description.messageProvider = Objects.requireNonNull(this.messageProvider);
            description.itemRemoveHandlerProvider = this.itemRemoveHandlerProvider; // Optional on purpose
            description.itemClickHandlerProvider = this.itemClickHandlerProvider; // Optional on purpose
            description.createElementHandlerProvider = this.createElementHandlerProvider;  // Optional on purpose
            description.moveHandlerProvider = this.moveHandlerProvider;  // Optional on purpose
            return description;
        }
    }
}
