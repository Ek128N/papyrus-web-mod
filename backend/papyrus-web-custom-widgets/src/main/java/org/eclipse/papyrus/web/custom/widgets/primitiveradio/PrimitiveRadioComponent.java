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
import java.util.function.Function;

import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component to render a primitive radio widget.
 *
 * @author Jerome Gout
 */
public class PrimitiveRadioComponent implements IComponent {

    private final PrimitiveRadioComponentProps props;

    public PrimitiveRadioComponent(PrimitiveRadioComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        PrimitiveRadioDescription primitiveRadio = this.props.getPrimitiveRadioDescription();

        String label = primitiveRadio.getLabelProvider().apply(variableManager);
        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, primitiveRadio.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, primitiveRadio.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);
        String id = primitiveRadio.getIdProvider().apply(idVariableManager);

        List<String> iconURL = primitiveRadio.getIconURLProvider().apply(variableManager);
        Boolean readOnly = primitiveRadio.getIsReadOnlyProvider().apply(variableManager);
        String value = primitiveRadio.getCandidateValueProvider().apply(variableManager);
        List<?> options = primitiveRadio.getCandidateListProvider().apply(variableManager);

        Function<String, IStatus> newValueHandler = (newValue) -> {
            var childVariableManager = variableManager.createChild();
            childVariableManager.put("newValue", newValue);
            return primitiveRadio.getNewValueHandler().apply(childVariableManager);
        };

        var builder = PrimitiveRadioElementProps.newPrimitiveRadioElementProps(id)//
                .label(label)//
                .iconURL(iconURL)//
                .diagnostics(List.of())//
                .candidateValue(value)//
                .candidateList(options)//
                .newValueHandler(newValueHandler);
        if (primitiveRadio.getHelpTextProvider() != null) {
            builder.helpTextProvider(() -> primitiveRadio.getHelpTextProvider().apply(variableManager));
        }
        if (readOnly != null) {
            builder.readOnly(readOnly);
        }

        return new Element(PrimitiveRadioElementProps.TYPE, builder.build());
    }

}
