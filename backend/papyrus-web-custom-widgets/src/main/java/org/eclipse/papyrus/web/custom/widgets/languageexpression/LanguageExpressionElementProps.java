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
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.papyrus.web.custom.widgets.languageexpression.dto.MoveLanguageDirection;
import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.validation.Diagnostic;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * The properties for the language expression widget element.
 *
 * @author Jerome Gout
 */
@Immutable
public final class LanguageExpressionElementProps implements IProps {

    public static final String TYPE = LanguageExpression.class.getSimpleName();

    private String id;

    private String label;

    private List<String> iconURL;

    private List<Diagnostic> diagnostics;

    private Supplier<String> helpTextProvider;

    private boolean readOnly;

    private List<LanguageElement> languages;

    private List<String> predefinedLanguages;

    private Function<String, IStatus> addLanguageHandler;

    private Function<String, IStatus> deleteLanguageHandler;

    private BiFunction<String, String, IStatus> editLanguageBodyHandler;

    private BiFunction<String, MoveLanguageDirection, IStatus> moveLanguageHandler;

    private LanguageExpressionElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public List<String> getIconURL() {
        return this.iconURL;
    }

    public List<Diagnostic> getDiagnostics() {
        return this.diagnostics;
    }

    public Supplier<String> getHelpTextProvider() {
        return this.helpTextProvider;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public List<LanguageElement> getLanguages() {
        return this.languages;
    }

    public List<String> getPredefinedLanguages() {
        return this.predefinedLanguages;
    }

    public Function<String, IStatus> getAddLanguageHandler() {
        return this.addLanguageHandler;
    }

    public Function<String, IStatus> getDeleteLanguageHandler() {
        return this.deleteLanguageHandler;
    }

    public BiFunction<String, String, IStatus> getEditLanguageBodyHandler() {
        return this.editLanguageBodyHandler;
    }

    public BiFunction<String, MoveLanguageDirection, IStatus> getMoveLanguageHandler() {
        return this.moveLanguageHandler;
    }

    public static Builder newLanguageExpressionElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, languages: {3}, predefinedLanguage: {4}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.languages, this.predefinedLanguages);
    }

    /**
     * The builder of the language expression element props.
     *
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String label;

        private List<String> iconURL;

        private boolean readOnly;

        private Supplier<String> helpTextProvider;

        private List<Diagnostic> diagnostics;

        private List<LanguageElement> languages;

        private List<String> predefinedLanguages;

        private Function<String, IStatus> addLanguageHandler;

        private Function<String, IStatus> deleteLanguageHandler;

        private BiFunction<String, String, IStatus> editLanguageBodyHandler;

        private BiFunction<String, MoveLanguageDirection, IStatus> moveLanguageHandler;

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

        public Builder languages(List<LanguageElement> languages) {
            this.languages = Objects.requireNonNull(languages);
            return this;
        }

        public Builder predefinedLanguages(List<String> predefinedLanguages) {
            this.predefinedLanguages = Objects.requireNonNull(predefinedLanguages);
            return this;
        }

        public Builder addLanguageHandler(Function<String, IStatus> addLanguageHandler) {
            this.addLanguageHandler = Objects.requireNonNull(addLanguageHandler);
            return this;
        }

        public Builder deleteLanguageHandler(Function<String, IStatus> deleteLanguageHandler) {
            this.deleteLanguageHandler = Objects.requireNonNull(deleteLanguageHandler);
            return this;
        }

        public Builder editLanguageBodyHandler(BiFunction<String, String, IStatus> editLanguageBodyHandler) {
            this.editLanguageBodyHandler = Objects.requireNonNull(editLanguageBodyHandler);
            return this;
        }

        public Builder moveLanguageHandler(BiFunction<String, MoveLanguageDirection, IStatus> moveLanguageHandler) {
            this.moveLanguageHandler = Objects.requireNonNull(moveLanguageHandler);
            return this;
        }

        public LanguageExpressionElementProps build() {
            LanguageExpressionElementProps leer = new LanguageExpressionElementProps();
            leer.id = Objects.requireNonNull(this.id);
            leer.label = Objects.requireNonNull(this.label);
            leer.iconURL = this.iconURL;
            leer.diagnostics = this.diagnostics;
            leer.readOnly = this.readOnly;
            leer.helpTextProvider = this.helpTextProvider; // Optional on purpose
            leer.languages = this.languages;
            leer.predefinedLanguages = this.predefinedLanguages;
            leer.addLanguageHandler = this.addLanguageHandler;
            leer.deleteLanguageHandler = this.deleteLanguageHandler;
            leer.editLanguageBodyHandler = this.editLanguageBodyHandler;
            leer.moveLanguageHandler = this.moveLanguageHandler;
            return leer;
        }
    }
}
