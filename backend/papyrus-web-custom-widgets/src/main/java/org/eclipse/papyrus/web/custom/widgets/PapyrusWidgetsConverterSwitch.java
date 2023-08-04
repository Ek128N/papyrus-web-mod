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
package org.eclipse.papyrus.web.custom.widgets;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.web.custom.widgets.languageexpression.LanguageExpressionDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.LanguageExpressionWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.util.PapyrusWidgetsSwitch;
import org.eclipse.sirius.components.compatibility.forms.WidgetIdProvider;
import org.eclipse.sirius.components.compatibility.utils.StringValueProvider;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Converts all  view-based Papyrus widget description into its API equivalent.<br>
 * Each custom widget has it own method caseXXX
 *
 * @author Jerome Gout
 */
public class PapyrusWidgetsConverterSwitch extends PapyrusWidgetsSwitch<AbstractWidgetDescription> {

    private final AQLInterpreter interpreter;

    public PapyrusWidgetsConverterSwitch(AQLInterpreter interpreter) {
        this.interpreter = Objects.requireNonNull(interpreter);
    }

    @Override
    public AbstractWidgetDescription caseLanguageExpressionWidgetDescription(LanguageExpressionWidgetDescription languageExpressionDescription) {
        String descriptionId = this.getDescriptionId(languageExpressionDescription);

        var builder = LanguageExpressionDescription.newLanguageExpressionDescription(descriptionId)
                .idProvider(new WidgetIdProvider())
                .labelProvider(variableManager -> this.getLanguageExpressionLabel(languageExpressionDescription, variableManager))
                .iconURLProvider(variableManager -> "")
                .isReadOnlyProvider(this.getReadOnlyValueProvider(languageExpressionDescription.getIsEnabledExpression()));

        if (languageExpressionDescription.getHelpExpression() != null && !languageExpressionDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(languageExpressionDescription.getHelpExpression()));
        }

        return builder.build();
    }

    private String getDescriptionId(EObject description) {
        String descriptionURI = EcoreUtil.getURI(description).toString();
        return UUID.nameUUIDFromBytes(descriptionURI.getBytes()).toString();
    }

    private String getLanguageExpressionLabel(LanguageExpressionWidgetDescription languageExpressionDescription, VariableManager variableManager) {
        return new StringValueProvider(this.interpreter, languageExpressionDescription.getLabelExpression()).apply(variableManager);
    }

    private Function<VariableManager, Boolean> getReadOnlyValueProvider(String expression) {
        return variableManager -> {
            if (expression != null && !expression.isBlank()) {
                Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), expression);
                return result.asBoolean().map(value -> !value).orElse(Boolean.FALSE);
            }
            return Boolean.FALSE;
        };
    }

    private StringValueProvider getStringValueProvider(String valueExpression) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse("");
        return new StringValueProvider(this.interpreter, safeValueExpression);
    }

}
