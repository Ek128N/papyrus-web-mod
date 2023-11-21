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
 * Widget descriptor for the containment reference widget.
 *
 * @author Jerome Gout
 */
@Component
public class ContainmentReferenceWidgetDescriptor implements IWidgetDescriptor {

    public static final String TYPE = "ContainmentReferenceWidget";

    @Override
    public List<String> getWidgetTypes() {
        return List.of(ContainmentReferenceElementProps.TYPE);
    }

    @Override
    public Optional<Boolean> validateComponentProps(Class<?> componentType, IProps props) {
        if (ContainmentReferenceWidgetComponent.class.equals(componentType)) {
            return Optional.of(props instanceof ContainmentReferenceWidgetComponentProps);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Boolean> validateInstanceProps(String type, IProps props) {
        Optional<Boolean> result = Optional.empty();
        if (Objects.equals(type, ContainmentReferenceElementProps.TYPE)) {
            result = Optional.of(props instanceof ContainmentReferenceElementProps);
        }
        return result;
    }

    @Override
    public Optional<Object> instanciate(String type, IProps elementProps, List<Object> children) {
        Optional<Object> result = Optional.empty();

        List<Diagnostic> diagnostics = children.stream()
                .filter(Diagnostic.class::isInstance)
                .map(Diagnostic.class::cast)
                .toList();

        if (Objects.equals(type, ContainmentReferenceElementProps.TYPE) && elementProps instanceof ContainmentReferenceElementProps props) {
            var builder = ContainmentReferenceWidget.newContainmentReferenceWidget(props.getId())
                    .descriptionId(props.getDescriptionId())
                    .label(props.getLabel())
                    .iconURL(props.getIconURL())
                    .diagnostics(diagnostics)
                    .readOnly(props.isReadOnly())
                    .ownerKind(props.getOwnerKind())
                    .referenceKind(props.getReferenceKind())
                    .many(props.isMany())
                    .referenceValues(props.getValues())
                    .ownerId(props.getOwnerId())
                    .canMove(props.isMany() && props.getMoveHandler() != null) // Only multi-valued containment references can reorder items
                    .createElementHandler(props.getCreateElementHandler())
                    .moveHandler(props.getMoveHandler());
            if (props.getHelpTextProvider() != null) {
                builder.helpTextProvider(props.getHelpTextProvider());
            }
            if (props.getStyle() != null) {
                builder.style(props.getStyle());
            }
            result = Optional.of(builder.build());
        }
        return result;
    }

    @Override
    public Optional<Element> createElement(VariableManager variableManager, AbstractWidgetDescription widgetDescription) {
        if (widgetDescription instanceof ContainmentReferenceWidgetDescription referenceWidgetDescription) {
            ContainmentReferenceWidgetComponentProps referenceComponentProps = new ContainmentReferenceWidgetComponentProps(variableManager, referenceWidgetDescription);
            return Optional.of(new Element(ContainmentReferenceWidgetComponent.class, referenceComponentProps));
        } else {
            return Optional.empty();
        }
    }

}
