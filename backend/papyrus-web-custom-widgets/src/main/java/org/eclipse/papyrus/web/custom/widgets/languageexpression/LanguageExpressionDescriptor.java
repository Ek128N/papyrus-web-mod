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
 * Widget descriptor for the language expression widget.
 *
 * @author Jerome Gout
 */
@Component
public class LanguageExpressionDescriptor implements IWidgetDescriptor {

    public static final String TYPE = LanguageExpression.class.getSimpleName();

    @Override
    public List<String> getWidgetTypes() {
        return List.of(LanguageExpressionDescriptor.TYPE);
    }

    @Override
    public Optional<Boolean> validateComponentProps(Class<?> componentType, IProps props) {
        if (LanguageExpressionComponent.class.equals(componentType)) {
            return Optional.of(props instanceof LanguageExpressionComponentProps);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Boolean> validateInstanceProps(String type, IProps props) {
        Optional<Boolean> result = Optional.empty();
        if (Objects.equals(type, LanguageExpressionDescriptor.TYPE)) {
            result = Optional.of(props instanceof LanguageExpressionElementProps);
        }
        return result;
    }

    @Override
    public Optional<Object> instanciate(String type, IProps elementProps, List<Object> children) {
        Optional<Object> result = Optional.empty();
        if (Objects.equals(type, LanguageExpressionElementProps.TYPE) && elementProps instanceof LanguageExpressionElementProps props) {
            var builder = LanguageExpression.newLanguageExpression(props.getId())
                    .label(props.getLabel())
                    .iconURL(props.getIconURL())
                    .diagnostics(props.getDiagnostics())
                    .readOnly(props.isReadOnly())
                    .languages(props.getLanguages())
                    .addLanguageHandler(props.getAddLanguageHandler())
                    .predefinedLanguages(props.getPredefinedLanguages())
                    .deleteLanguageHandler(props.getDeleteLanguageHandler())
                    .editLanguageBodyHandler(props.getEditLanguageBodyHandler())
                    .moveLanguageHandler(props.getMoveLanguageHandler());
            if (props.getHelpTextProvider() != null) {
                builder.helpTextProvider(props.getHelpTextProvider());
            }
            result = Optional.of(builder.build());
        }
        return result;
    }

    @Override
    public Optional<Element> createElement(VariableManager variableManager, AbstractWidgetDescription widgetDescription) {
        if (widgetDescription instanceof LanguageExpressionDescription languageExpressionDescription) {
            LanguageExpressionComponentProps languageExpressionComponentProps = new LanguageExpressionComponentProps(variableManager, languageExpressionDescription);
            return Optional.of(new Element(LanguageExpressionComponent.class, languageExpressionComponentProps));
        } else {
            return Optional.empty();
        }
    }

}
