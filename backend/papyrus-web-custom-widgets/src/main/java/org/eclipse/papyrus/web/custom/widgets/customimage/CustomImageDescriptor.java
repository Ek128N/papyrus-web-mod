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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Component;

/**
 * Widget descriptor for the custom image widget.
 *
 * @author tiboue
 */
@Component
public class CustomImageDescriptor implements IWidgetDescriptor {

    public static final String TYPE = CustomImageWidget.class.getSimpleName();

    @Override
    public List<String> getWidgetTypes() {
        return List.of(CustomImageDescriptor.TYPE);
    }

    @Override
    public Optional<Boolean> validateComponentProps(Class<?> componentType, IProps props) {
        if (CustomImageComponent.class.equals(componentType)) {
            return Optional.of(props instanceof CustomImageComponentProps);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Boolean> validateInstanceProps(String type, IProps props) {
        Optional<Boolean> result = Optional.empty();
        if (Objects.equals(type, CustomImageDescriptor.TYPE)) {
            result = Optional.of(props instanceof CustomImageElementProps);
        }
        return result;
    }

    @Override
    public Optional<Object> instanciate(String type, IProps elementProps, List<Object> children) {
        Optional<Object> result = Optional.empty();
        if (Objects.equals(type, CustomImageElementProps.TYPE) && elementProps instanceof CustomImageElementProps props) {
            var builder = CustomImageWidget.newCustomImage(props.getId())
                    .label(props.getLabel())
                    .iconURL(props.getIconURL())
                    .diagnostics(props.getDiagnostics())
                    .readOnly(props.isReadOnly())
                    .currentUuid(props.getCurrentUuid())
                    .newUuidHandler(props.getNewUuidHandler())
                    .removeUuidHandler(props.getRemoveUuidHandler());
            if (props.getHelpTextProvider() != null) {
                builder.helpTextProvider(props.getHelpTextProvider());
            }
            result = Optional.of(builder.build());
        }
        return result;
    }

    @Override
    public Optional<Element> createElement(VariableManager variableManager, AbstractWidgetDescription widgetDescription) {
        if (widgetDescription instanceof CustomImageDescription customImageDescription) {
            CustomImageComponentProps customImageComponentProps = new CustomImageComponentProps(variableManager, customImageDescription);
            return Optional.of(new Element(CustomImageComponent.class, customImageComponentProps));
        } else {
            return Optional.empty();
        }
    }

}
