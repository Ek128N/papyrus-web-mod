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

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.validation.Diagnostic;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * A custom widget to handle custom image.
 *
 * @author tiboue
 */
public final class CustomImageWidget extends AbstractWidget {

    private String currentUuid;

    private Function<String, IStatus> newUuidHandler;

    private Function<String, IStatus> removeUuidHandler;

    private CustomImageWidget() {
        // Prevent instantiation
    }

    public String getCurrentUuid() {
        return this.currentUuid;
    }

    public Function<String, IStatus> getNewUuidHandler() {
        return this.newUuidHandler;
    }

    public Function<String, IStatus> getRemoveUuidHandler() {
        return this.removeUuidHandler;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'currentUuid: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getCurrentUuid());
    }

    public static Builder newCustomImage(String id) {
        return new Builder(id);
    }

    /**
     * Builder used to create the ContainmentReferenceWidget.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String label;

        private List<String> iconURL;

        private List<Diagnostic> diagnostics;

        private Supplier<String> helpTextProvider;

        private boolean readOnly;

        private String currentUuid;

        private Function<String, IStatus> newUuidHandler;

        private Function<String, IStatus> removeUuidHandler;

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

        public Builder helpTextProvider(Supplier<String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public Builder readOnly(boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        public Builder currentUuid(String currentUuid) {
            this.currentUuid = Objects.requireNonNull(currentUuid);
            return this;
        }

        public Builder newUuidHandler(Function<String, IStatus> newUuidHandler) {
            this.newUuidHandler = Objects.requireNonNull(newUuidHandler);
            return this;
        }

        public Builder removeUuidHandler(Function<String, IStatus> removeUuidHandler) {
            this.removeUuidHandler = Objects.requireNonNull(removeUuidHandler);
            return this;
        }

        public CustomImageWidget build() {
            CustomImageWidget widget = new CustomImageWidget();
            widget.id = Objects.requireNonNull(this.id);
            widget.label = Objects.requireNonNull(this.label);
            widget.iconURL = this.iconURL;
            widget.readOnly = this.readOnly;
            widget.diagnostics = Objects.requireNonNull(this.diagnostics);
            widget.currentUuid = Objects.requireNonNull(this.currentUuid);
            widget.newUuidHandler = Objects.requireNonNull(this.newUuidHandler);
            widget.removeUuidHandler = Objects.requireNonNull(this.removeUuidHandler);
            widget.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return widget;
        }

    }

}
