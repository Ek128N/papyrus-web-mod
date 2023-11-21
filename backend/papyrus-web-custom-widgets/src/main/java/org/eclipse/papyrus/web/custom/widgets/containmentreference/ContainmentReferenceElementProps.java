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
package org.eclipse.papyrus.web.custom.widgets.containmentreference;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.papyrus.web.custom.widgets.containmentreference.dto.CreateElementInContainmentReferenceHandlerParameters;
import org.eclipse.papyrus.web.custom.widgets.containmentreference.dto.MoveContainmentReferenceItemHandlerParamaters;
import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.widget.reference.ReferenceWidgetStyle;

/**
 * The properties for containment reference widget.
 *
 * @author Jerome Gout
 */
@Immutable
public final class ContainmentReferenceElementProps implements IProps {

    public static final String TYPE = ContainmentReferenceWidget.class.getSimpleName();

    private String id;

    private String label;

    private List<String> iconURL;

    private Supplier<String> helpTextProvider;

    private boolean readOnly;

    private List<ContainmentReferenceItem> values;

    private String ownerKind;

    private String referenceKind;

    private String descriptionId;

    private boolean many;

    private ReferenceWidgetStyle style;

    private String ownerId;

    private List<Element> children;

    private Function<CreateElementInContainmentReferenceHandlerParameters, Object> createElementHandler;

    private Function<MoveContainmentReferenceItemHandlerParamaters, IStatus> moveHandler;

    private ContainmentReferenceElementProps() {
        // Prevent instantiation
    }

    public static Builder newContainmentReferenceElementProps(String id) {
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

    public Supplier<String> getHelpTextProvider() {
        return this.helpTextProvider;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public List<ContainmentReferenceItem> getValues() {
        return this.values;
    }

    public String getOwnerKind() {
        return this.ownerKind;
    }

    public String getReferenceKind() {
        return this.referenceKind;
    }

    public boolean isMany() {
        return this.many;
    }

    public String getDescriptionId() {
        return this.descriptionId;
    }

    public ReferenceWidgetStyle getStyle() {
        return this.style;
    }

    public String getOwnerId() {
        return this.ownerId;
    }

    public Function<CreateElementInContainmentReferenceHandlerParameters, Object> getCreateElementHandler() {
        return this.createElementHandler;
    }

    public Function<MoveContainmentReferenceItemHandlerParamaters, IStatus> getMoveHandler() {
        return this.moveHandler;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }

    /**
     * The builder of the containment reference element props.
     *
     * @author Jerome Gout
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String label;

        private List<String> iconURL;

        private boolean readOnly;

        private Supplier<String> helpTextProvider;

        private List<ContainmentReferenceItem> values;

        private String ownerKind;

        private String referenceKind;

        private boolean many;

        private ReferenceWidgetStyle style;

        private String ownerId;

        private List<Element> children;

        private Function<CreateElementInContainmentReferenceHandlerParameters, Object> createElementHandler;

        private Function<MoveContainmentReferenceItemHandlerParamaters, IStatus> moveHandler;

        private String descriptionId;

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
            this.helpTextProvider = helpTextProvider;
            return this;
        }

        public Builder readOnly(boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        public Builder values(List<ContainmentReferenceItem> values) {
            this.values = Objects.requireNonNull(values);
            return this;
        }

        public Builder ownerKind(String ownerKind) {
            this.ownerKind = Objects.requireNonNull(ownerKind);
            return this;
        }

        public Builder referenceKind(String referenceKind) {
            this.referenceKind = Objects.requireNonNull(referenceKind);
            return this;
        }

        public Builder many(boolean many) {
            this.many = many;
            return this;
        }

        public Builder style(ReferenceWidgetStyle style) {
            this.style = style;
            return this;
        }

        public Builder ownerId(String ownerId) {
            this.ownerId = Objects.requireNonNull(ownerId);
            return this;
        }

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public Builder createElementHandler(Function<CreateElementInContainmentReferenceHandlerParameters, Object> createElementHandler) {
            this.createElementHandler = createElementHandler;
            return this;
        }

        public Builder moveHandler(Function<MoveContainmentReferenceItemHandlerParamaters, IStatus> moveHandler) {
            this.moveHandler = moveHandler;
            return this;
        }

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public ContainmentReferenceElementProps build() {
            ContainmentReferenceElementProps props = new ContainmentReferenceElementProps();
            props.id = Objects.requireNonNull(this.id);
            props.descriptionId = Objects.requireNonNull(this.descriptionId);
            props.label = Objects.requireNonNull(this.label);
            props.iconURL = this.iconURL;
            props.readOnly = this.readOnly;
            props.values = Objects.requireNonNull(this.values);
            props.ownerKind = Objects.requireNonNull(this.ownerKind);
            props.referenceKind = Objects.requireNonNull(this.referenceKind);
            props.many = this.many;
            props.helpTextProvider = this.helpTextProvider; // Optional on purpose
            props.style = this.style; // Optional on purpose
            props.ownerId = Objects.requireNonNull(this.ownerId);
            props.children = Objects.requireNonNull(this.children);
            props.createElementHandler = this.createElementHandler;  // Optional on purpose
            props.moveHandler = this.moveHandler;  // Optional on purpose
            return props;
        }
    }
}
