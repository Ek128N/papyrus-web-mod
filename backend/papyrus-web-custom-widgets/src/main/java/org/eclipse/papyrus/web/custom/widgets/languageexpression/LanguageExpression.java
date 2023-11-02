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
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.validation.Diagnostic;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * Language expression custom widget to handle UML "BodyOwner" elements such as OpaqueBehavior.
 *
 * @author Jerome Gout
 */
@Immutable
public final class LanguageExpression extends AbstractWidget {

    private List<LanguageElement> languages;

    private List<String> predefinedLanguages;

    private Function<String, IStatus> addLanguageHandler;

    private Function<String, IStatus> deleteLanguageHandler;

    private BiFunction<String, String, IStatus> editLanguageBodyHandler;

    private BiFunction<String, MoveLanguageDirection, IStatus> moveLanguageHandler;

    private LanguageExpression() {
        // Prevent instantiation
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

    @Override
    public String toString() {
        String pattern = "{0} '{'languages: {1} predefinedLanguages: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getLanguages(), this.predefinedLanguages);
    }

    public static Builder newLanguageExpression(String id) {
        return new Builder(id);
    }

    /**
     * Builder used to create the LanguageExpression.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
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

        public Builder diagnostics(List<Diagnostic> diagnostics) {
            this.diagnostics = Objects.requireNonNull(diagnostics);
            return this;
        }

        public Builder readOnly(boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        public Builder helpTextProvider(Supplier<String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
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

        public LanguageExpression build() {
            LanguageExpression le = new LanguageExpression();
            le.id = Objects.requireNonNull(this.id);
            le.label = Objects.requireNonNull(this.label);
            le.iconURL = this.iconURL;
            le.readOnly = this.readOnly;
            le.diagnostics = Objects.requireNonNull(this.diagnostics);
            le.helpTextProvider = this.helpTextProvider; // Optional on purpose
            le.languages = this.languages;
            le.predefinedLanguages = this.predefinedLanguages;
            le.addLanguageHandler = this.addLanguageHandler;
            le.deleteLanguageHandler = this.deleteLanguageHandler;
            le.editLanguageBodyHandler = this.editLanguageBodyHandler;
            le.moveLanguageHandler = this.moveLanguageHandler;
            return le;
        }
    }
}
