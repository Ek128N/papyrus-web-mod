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
package org.eclipse.papyrus.web.custom.widgets.languageexpression;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.representations.VariableManager;


/**
 * Language Expression widget description.
 *
 * @author Jerome Gout
 */
@Immutable
public final class LanguageExpressionDescription extends AbstractWidgetDescription {
    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, List<String>> iconURLProvider;

    private LanguageExpressionDescription() {
        // Prevent instantiation
    }

    public static Builder newLanguageExpressionDescription(String id) {
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

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId());
    }

    /**
     * Builder used to create the LanguageExpressionWidgetDescription.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, List<String>> iconURLProvider = variableManager -> List.of();

        private Function<VariableManager, Boolean> isReadOnlyProvider = variableManager -> false;

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

        public Builder helpTextProvider(Function<VariableManager, String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
            return this;
        }

        public LanguageExpressionDescription build() {
            LanguageExpressionDescription languageExpressionDescription = new LanguageExpressionDescription();
            languageExpressionDescription.id = Objects.requireNonNull(this.id);
            languageExpressionDescription.idProvider = Objects.requireNonNull(this.idProvider);
            languageExpressionDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            languageExpressionDescription.iconURLProvider = Objects.requireNonNull(this.iconURLProvider);
            languageExpressionDescription.isReadOnlyProvider = Objects.requireNonNull(this.isReadOnlyProvider);
            languageExpressionDescription.helpTextProvider = this.helpTextProvider; // Optional on purpose
            languageExpressionDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            return languageExpressionDescription;
        }
    }
}
