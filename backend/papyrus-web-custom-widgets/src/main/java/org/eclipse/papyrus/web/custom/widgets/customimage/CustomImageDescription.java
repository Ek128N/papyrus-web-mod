/*****************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo, Artal Technologies.
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
 *  Titouan BOUETE-GIRAUD (Artal Technologies) - Issue 210
 *****************************************************************************/
package org.eclipse.papyrus.web.custom.widgets.customimage;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Custom Image widget description.
 *
 * @author tiboue
 */
public final class CustomImageDescription extends AbstractWidgetDescription {
    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, List<String>> iconURLProvider;

    private Function<VariableManager, String> currentUuidProvider;

    private Function<VariableManager, IStatus> newUuidHandler;

    private Function<VariableManager, IStatus> removeUuidHandler;

    private CustomImageDescription() {
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

    public Function<VariableManager, String> getCurrentUuidProvider() {
        return this.currentUuidProvider;
    }

    public Function<VariableManager, IStatus> getNewUuidHandler() {
        return this.newUuidHandler;
    }

    public Function<VariableManager, IStatus> getRemoveUuidHandler() {
        return this.removeUuidHandler;
    }

    public static Builder newCustomImageDescription(String id) {
        return new Builder(id);
    }

    /**
     * Builder used to create the CustomImageDescription.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, List<String>> iconURLProvider = variableManager -> List.of();

        private Function<VariableManager, Boolean> isReadOnlyProvider = variableManager -> false;

        private Function<VariableManager, String> currentUuidProvider;

        private Function<VariableManager, IStatus> newUuidHandler;

        private Function<VariableManager, IStatus> removeUuidHandler;

        private Function<VariableManager, String> helpTextProvider;

        private Function<VariableManager, String> targetObjectIdProvider;

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

        public Builder currentUuidProvider(Function<VariableManager, String> currentUuidProvider) {
            this.currentUuidProvider = Objects.requireNonNull(currentUuidProvider);
            return this;
        }

        public Builder newUuidHandler(Function<VariableManager, IStatus> newUuidHandler) {
            this.newUuidHandler = Objects.requireNonNull(newUuidHandler);
            return this;
        }

        public Builder removeUuidHandler(Function<VariableManager, IStatus> removeUuidHandler) {
            this.removeUuidHandler = Objects.requireNonNull(removeUuidHandler);
            return this;
        }

        public Builder helpTextProvider(Function<VariableManager, String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
            return this;
        }

        public CustomImageDescription build() {
            CustomImageDescription customImageDescription = new CustomImageDescription();
            customImageDescription.id = Objects.requireNonNull(this.id);
            customImageDescription.idProvider = Objects.requireNonNull(this.idProvider);
            customImageDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            customImageDescription.iconURLProvider = this.iconURLProvider; // Optional on purpose
            customImageDescription.isReadOnlyProvider = Objects.requireNonNull(this.isReadOnlyProvider);
            customImageDescription.helpTextProvider = this.helpTextProvider; // Optional on purpose
            customImageDescription.currentUuidProvider = Objects.requireNonNull(this.currentUuidProvider);
            customImageDescription.newUuidHandler = Objects.requireNonNull(this.newUuidHandler);
            customImageDescription.removeUuidHandler = Objects.requireNonNull(this.removeUuidHandler);
            customImageDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            return customImageDescription;
        }
    }

}
