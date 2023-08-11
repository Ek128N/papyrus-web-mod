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
 * Widget descriptor for the primitive radio widget.
 *
 * @author Jerome Gout
 */
@Component
public class PrimitiveRadioDescriptor implements IWidgetDescriptor {

    public static final String TYPE = PrimitiveRadio.class.getSimpleName();

    @Override
    public List<String> getWidgetTypes() {
        return List.of(PrimitiveRadioDescriptor.TYPE);
    }

    @Override
    public Optional<Boolean> validateComponentProps(Class<?> componentType, IProps props) {
        if (PrimitiveRadioComponent.class.equals(componentType)) {
            return Optional.of(props instanceof PrimitiveRadioComponentProps);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Boolean> validateInstanceProps(String type, IProps props) {
        Optional<Boolean> result = Optional.empty();
        if (Objects.equals(type, PrimitiveRadioDescriptor.TYPE)) {
            result = Optional.of(props instanceof PrimitiveRadioElementProps);
        }
        return result;
    }

    @Override
    public Optional<Object> instanciate(String type, IProps elementProps, List<Object> children) {
        Optional<Object> result = Optional.empty();
        if (Objects.equals(type, PrimitiveRadioElementProps.TYPE) && elementProps instanceof PrimitiveRadioElementProps props) {
            var builder = PrimitiveRadio.newPrimitiveRadio(props.getId())
                    .label(props.getLabel())
                    .iconURL(props.getIconURL())
                    .diagnostics(props.getDiagnostics())
                    .readOnly(props.isReadOnly())
                    .candidateList(props.getCandidateList())
                    .candidateValue(props.getCandidateValue())
                    .newValueHandler(props.getNewValueHandler());
            if (props.getHelpTextProvider() != null) {
                builder.helpTextProvider(props.getHelpTextProvider());
            }
            result = Optional.of(builder.build());
        }
        return result;
    }

    @Override
    public Optional<Element> createElement(VariableManager variableManager, AbstractWidgetDescription widgetDescription) {
        if (widgetDescription instanceof PrimitiveRadioDescription primitiveRadioDescription) {
            PrimitiveRadioComponentProps primitiveRadioComponentProps = new PrimitiveRadioComponentProps(variableManager, primitiveRadioDescription);
            return Optional.of(new Element(PrimitiveRadioComponent.class, primitiveRadioComponentProps));
        } else {
            return Optional.empty();
        }
    }

}
