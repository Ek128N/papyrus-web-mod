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

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.validation.Diagnostic;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * The properties for the Custom Image element.
 *
 * @author tiboue
 */
@Immutable
public final class CustomImageElementProps implements IProps {

    public static final String TYPE = CustomImageWidget.class.getSimpleName();

    private String id;

    private String label;

    private List<String> iconURL;

    private List<Diagnostic> diagnostics;

    private Supplier<String> helpTextProvider;

    private boolean readOnly;

    private String currentUuid;

    private Function<String, IStatus> newUuidHandler;

    private Function<String, IStatus> removeUuidHandler;

    private CustomImageElementProps() {
        // Prevent instantiation
    }

    public static Builder newCustomImageElementProps(String id) {
        return new Builder(id);
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
        String pattern = "{0} '{'id: {1}, label: {2}, currentUuid: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.currentUuid);
    }

    /**
     * The builder of the custom image element props.
     *
     * @author tiboue
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String label;

        private List<String> iconURL;

        private Supplier<String> helpTextProvider;

        private List<Diagnostic> diagnostics;

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

        public Builder readOnly(boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        public Builder helpTextProvider(Supplier<String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public Builder diagnostics(List<Diagnostic> diagnostics) {
            this.diagnostics = Objects.requireNonNull(diagnostics);
            return this;
        }

        public Builder currentUuid(String currentUuid) {
            this.currentUuid = currentUuid;
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

        public CustomImageElementProps build() {
            CustomImageElementProps customImageElementProps = new CustomImageElementProps();
            customImageElementProps.id = Objects.requireNonNull(this.id);
            customImageElementProps.label = Objects.requireNonNull(this.label);
            customImageElementProps.iconURL = this.iconURL;
            customImageElementProps.diagnostics = this.diagnostics;
            customImageElementProps.readOnly = this.readOnly;
            customImageElementProps.currentUuid = this.currentUuid;
            customImageElementProps.newUuidHandler = this.newUuidHandler;
            customImageElementProps.removeUuidHandler = this.removeUuidHandler;
            customImageElementProps.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return customImageElementProps;
        }
    }

}
