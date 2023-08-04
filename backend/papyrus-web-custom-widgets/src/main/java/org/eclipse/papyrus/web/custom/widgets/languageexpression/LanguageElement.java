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
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * Represents the single element of Language expressions.
 *
 * @author Jerome Gout
 */
@Immutable
public final class LanguageElement {
    private String id;

    private String label;

    private String body;

    private LanguageElement() {
        // Prevent instantiation
    }

    public static Builder newLanguageElement(String id) {
        return new Builder(id);
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getBody() {
        return this.body;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, body: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.body);
    }

    /**
     * Builder used to create the LanguageElement.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String label;

        private String body;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder body(String body) {
            this.body = Objects.requireNonNull(body);
            return this;
        }

        public LanguageElement build() {
            LanguageElement languageElement = new LanguageElement();
            languageElement.id = Objects.requireNonNull(this.id);
            languageElement.label = Objects.requireNonNull(this.label);
            languageElement.body = Objects.requireNonNull(this.body);
            return languageElement;
        }
    }
}
