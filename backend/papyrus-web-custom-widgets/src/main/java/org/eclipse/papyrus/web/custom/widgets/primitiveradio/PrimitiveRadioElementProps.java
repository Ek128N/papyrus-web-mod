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
import org.eclipse.sirius.components.forms.validation.Diagnostic;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * The properties for the primitive radio widget element.
 *
 * @author Jerome Gout
 */
@Immutable
public final class PrimitiveRadioElementProps implements IProps {

    public static final String TYPE = PrimitiveRadio.class.getSimpleName();

    private String id;

    private String label;

    private List<String> iconURL;

    private List<Diagnostic> diagnostics;

    private Supplier<String> helpTextProvider;

    private boolean readOnly;

    private List<?> candidateList;

    private String candidateValue;

    private Function<String, IStatus> newValueHandler;

    private PrimitiveRadioElementProps() {
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

    public List<?> getCandidateList() {
        return this.candidateList;
    }

    public String getCandidateValue() {
        return this.candidateValue;
    }

    public Function<String, IStatus> getNewValueHandler() {
        return this.newValueHandler;
    }

    public static Builder newPrimitiveRadioElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, options: {3}, value: {4}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.candidateList, this.candidateValue);
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

        public PrimitiveRadioElementProps build() {
            PrimitiveRadioElementProps prep = new PrimitiveRadioElementProps();
            prep.id = Objects.requireNonNull(this.id);
            prep.label = Objects.requireNonNull(this.label);
            prep.iconURL = this.iconURL;
            prep.diagnostics = this.diagnostics;
            prep.readOnly = this.readOnly;
            prep.helpTextProvider = this.helpTextProvider; // Optional on purpose
            prep.candidateList = this.candidateList;
            prep.candidateValue = this.candidateValue;
            prep.newValueHandler = this.newValueHandler;
            return prep;
        }
    }
}
