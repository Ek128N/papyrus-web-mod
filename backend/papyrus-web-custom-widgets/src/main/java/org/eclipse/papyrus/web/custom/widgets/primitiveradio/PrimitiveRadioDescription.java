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
package org.eclipse.papyrus.web.custom.widgets.primitiveradio;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Primitive radio widget description.
 *
 * @author Jerome Gout
 */
@Immutable
public final class PrimitiveRadioDescription extends AbstractWidgetDescription {
    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, List<String>> iconURLProvider;

    private Function<VariableManager, List<?>> candidateListProvider;

    private Function<VariableManager, String> candidateValueProvider;

    private Function<VariableManager, IStatus> newValueHandler;

    private PrimitiveRadioDescription() {
        // Prevent instantiation
    }

    public static Builder newPrimitiveRadioDescription(String id) {
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

    public Function<VariableManager, List<?>> getCandidateListProvider() {
        return this.candidateListProvider;
    }

    public Function<VariableManager, String> getCandidateValueProvider() {
        return this.candidateValueProvider;
    }

    public Function<VariableManager, IStatus> getNewValueHandler() {
        return this.newValueHandler;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId());
    }

    /**
     * Builder used to create the PrimitiveRadioDescription.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, List<String>> iconURLProvider = variableManager -> List.of();

        private Function<VariableManager, Boolean> isReadOnlyProvider = variableManager -> false;

        private Function<VariableManager, List<?>> candidateListProvider;

        private Function<VariableManager, String> candidateValueProvider;

        private Function<VariableManager, String> helpTextProvider;

        private Function<VariableManager, IStatus> newValueHandler;

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

        public Builder candidateListProvider(Function<VariableManager, List<?>> optionsProvider) {
            this.candidateListProvider = Objects.requireNonNull(optionsProvider);
            return this;
        }

        public Builder candidateValueProvider(Function<VariableManager, String> valueProvider) {
            this.candidateValueProvider = valueProvider;
            return this;
        }

        public Builder newValueHandler(Function<VariableManager, IStatus> newValueHandler) {
            this.newValueHandler = newValueHandler;
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

        public PrimitiveRadioDescription build() {
            PrimitiveRadioDescription primitiveRadioDescription = new PrimitiveRadioDescription();
            primitiveRadioDescription.id = Objects.requireNonNull(this.id);
            primitiveRadioDescription.idProvider = Objects.requireNonNull(this.idProvider);
            primitiveRadioDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            primitiveRadioDescription.iconURLProvider = Objects.requireNonNull(this.iconURLProvider);
            primitiveRadioDescription.isReadOnlyProvider = Objects.requireNonNull(this.isReadOnlyProvider);
            primitiveRadioDescription.candidateListProvider = Objects.requireNonNull(this.candidateListProvider);
            primitiveRadioDescription.candidateValueProvider = Objects.requireNonNull(this.candidateValueProvider);
            primitiveRadioDescription.newValueHandler = Objects.requireNonNull(this.newValueHandler);
            primitiveRadioDescription.helpTextProvider = this.helpTextProvider; // Optional on purpose
            primitiveRadioDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            return primitiveRadioDescription;
        }
    }
}
