/*****************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.custom.widgets.primitivelist;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.forms.validation.Diagnostic;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Component;

/**
 * Widget descriptor for the primitive list widget.
 *
 * @author Arthur Daussy
 */
@Component
public class PrimitiveListWidgetDescriptor implements IWidgetDescriptor {

    public static final String TYPE = "PrimitiveListWidget";

    @Override
    public List<String> getWidgetTypes() {
        return List.of(PrimitiveListWidgetElementProps.TYPE);
    }

    @Override
    public Optional<Boolean> validateComponentProps(Class<?> componentType, IProps props) {
        if (PrimitiveListWidgetComponent.class.equals(componentType)) {
            return Optional.of(props instanceof PrimitiveListWidgetComponentProps);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Boolean> validateInstanceProps(String type, IProps props) {
        Optional<Boolean> result = Optional.empty();
        if (Objects.equals(type, PrimitiveListWidgetElementProps.TYPE)) {
            result = Optional.of(props instanceof PrimitiveListWidgetElementProps);
        }
        return result;
    }

    @Override
    public Optional<Object> instanciate(String type, IProps elementProps, List<Object> children) {
        Optional<Object> result = Optional.empty();
        if (Objects.equals(type, PrimitiveListWidgetElementProps.TYPE) && elementProps instanceof PrimitiveListWidgetElementProps props) {
            List<Diagnostic> diagnostics = this.getDiagnosticsFromChildren(children);
            PrimitiveListWidget.Builder builder = PrimitiveListWidget.newPrimitiveList(props.getId())//
                    .label(props.getLabel())//
                    .iconURL(props.getIconURL())//
                    .diagnostics(diagnostics)//
                    .items(props.getItems())//
                    .candidatesProvider(props.getCandidatesProvider())
                    .readOnly(props.isReadOnly());

            if (props.getIconURL() != null) {
                builder.iconURL(props.getIconURL());
            }

            if (props.getHelpTextProvider() != null) {
                builder.helpTextProvider(props.getHelpTextProvider());
            }
            if (props.getStyle() != null) {
                builder.style(props.getStyle());
            }

            if (props.getNewValueHandler() != null) {
                builder.newValueHandler(props.getNewValueHandler());
            }
            if (props.getReorderHandler() != null) {
                builder.reorderHandler(props.getReorderHandler());
            }
            result = Optional.of(builder.build());
        }
        return result;
    }

    private List<Diagnostic> getDiagnosticsFromChildren(List<Object> children) {
        return children.stream()
                .filter(Diagnostic.class::isInstance)
                .map(Diagnostic.class::cast)
                .toList();
    }

    @Override
    public Optional<Element> createElement(VariableManager variableManager, AbstractWidgetDescription widgetDescription) {
        if (widgetDescription instanceof PrimitiveListWidgetDescription primitiveListWidgetDescription) {
            PrimitiveListWidgetComponentProps primListComponentProps = new PrimitiveListWidgetComponentProps(variableManager, primitiveListWidgetDescription);
            return Optional.of(new Element(PrimitiveListWidgetComponent.class, primListComponentProps));
        } else {
            return Optional.empty();
        }
    }
}
