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
import java.util.function.Supplier;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.validation.Diagnostic;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * Primitive radio custom widget.
 *
 * @author Jerome Gout
 */
@Immutable
public final class PrimitiveRadio extends AbstractWidget {

    private List<?> candidateList;

    private String candidateValue;

    private Function<String, IStatus> newValueHandler;

    private PrimitiveRadio() {
        // Prevent instantiation
    }

    public List<?> getCandidateList() {
        return this.candidateList;
    }

    public String getCandidateValue() {
        return this.candidateValue;
    }

    public Function<String, IStatus> getNewValueHandler() {
        return this.newValueHandler;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'options: {1} value: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getCandidateList(), this.getCandidateValue());
    }

    public static Builder newPrimitiveRadio(String id) {
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

        private List<?> candidateList;

        private String candidateValue;

        private Function<String, IStatus> newValueHandler;

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

        public Builder candidateList(List<?> options) {
            this.candidateList = Objects.requireNonNull(options);
            return this;
        }

        public Builder candidateValue(String value) {
            this.candidateValue = Objects.requireNonNull(value);
            return this;
        }

        public Builder newValueHandler(Function<String, IStatus> newValueHandler) {
            this.newValueHandler = Objects.requireNonNull(newValueHandler);
            return this;
        }

        public PrimitiveRadio build() {
            PrimitiveRadio primitiveRadio = new PrimitiveRadio();
            primitiveRadio.id = Objects.requireNonNull(this.id);
            primitiveRadio.label = Objects.requireNonNull(this.label);
            primitiveRadio.iconURL = this.iconURL;
            primitiveRadio.readOnly = this.readOnly;
            primitiveRadio.diagnostics = Objects.requireNonNull(this.diagnostics);
            primitiveRadio.helpTextProvider = this.helpTextProvider; // Optional on purpose
            primitiveRadio.candidateList = Objects.requireNonNull(this.candidateList);
            primitiveRadio.candidateValue = Objects.requireNonNull(this.candidateValue);
            primitiveRadio.newValueHandler = Objects.requireNonNull(this.newValueHandler);
            return primitiveRadio;
        }
    }
}
