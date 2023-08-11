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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.web.custom.widgets.languageexpression.LanguageExpressionDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.LanguageExpressionWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveRadioWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.util.PapyrusWidgetsSwitch;
import org.eclipse.papyrus.web.custom.widgets.primitiveradio.PrimitiveRadioDescription;
import org.eclipse.sirius.components.compatibility.forms.WidgetIdProvider;
import org.eclipse.sirius.components.compatibility.utils.StringValueProvider;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.emf.OperationInterpreter;

/**
 * Converts all  view-based Papyrus widget description into its API equivalent.<br>
 * Each custom widget has it own method caseXXX
 *
 * @author Jerome Gout
 */
public class PapyrusWidgetsConverterSwitch extends PapyrusWidgetsSwitch<AbstractWidgetDescription> {

    private final AQLInterpreter interpreter;
    private IFeedbackMessageService feedbackMessageService;
    private IEditService editService;

    public PapyrusWidgetsConverterSwitch(AQLInterpreter interpreter, IEditService editService, IFeedbackMessageService feedbackMessageService) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.editService = Objects.requireNonNull(editService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
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

    @Override
    public AbstractWidgetDescription casePrimitiveRadioWidgetDescription(PrimitiveRadioWidgetDescription primitiveRadioDescription) {
        String descriptionId = this.getDescriptionId(primitiveRadioDescription);

        var builder = PrimitiveRadioDescription.newPrimitiveRadioDescription(descriptionId)
                .idProvider(new WidgetIdProvider())
                .labelProvider(variableManager -> this.getPrimitiveRadioLabel(primitiveRadioDescription, variableManager))
                .iconURLProvider(variableManager -> "")
                .isReadOnlyProvider(this.getReadOnlyValueProvider(primitiveRadioDescription.getIsEnabledExpression()))
                .candidateValueProvider(this.getValueProvider(primitiveRadioDescription.getValueExpression()))
                .candidateListProvider(this.getOptionsProvider(primitiveRadioDescription.getCandidatesExpression()))
                .newValueHandler(this.getOperationsHandler(primitiveRadioDescription.getBody()));

        if (primitiveRadioDescription.getHelpExpression() != null && !primitiveRadioDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(primitiveRadioDescription.getHelpExpression()));
        }

        return builder.build();
    }

    private String getPrimitiveRadioLabel(PrimitiveRadioWidgetDescription primitiveRadioDescription, VariableManager variableManager) {
        return new StringValueProvider(this.interpreter, primitiveRadioDescription.getLabelExpression()).apply(variableManager);
    }

    private Function<VariableManager, List<?>> getOptionsProvider(String expression) {
        String safeExpression = Optional.ofNullable(expression).orElse("");
        return variableManager -> {
            if (safeExpression.isBlank()) {
                return List.of();
            } else {
                return this.interpreter.evaluateExpression(variableManager.getVariables(), safeExpression).asObjects().orElse(List.of());
            }
        };
    }

    private Function<VariableManager, String> getValueProvider(String valueExpression) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse("");
        return variableManager -> {
            if (!safeValueExpression.isBlank()) {
                Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), safeValueExpression);
                return result.asString().orElse("");
            }
            return "";
        };
    }


    private Function<VariableManager, IStatus> getOperationsHandler(List<Operation> operations) {
        return variableManager -> {
            OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
            Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, variableManager);
            if (optionalVariableManager.isEmpty()) {
                List<Message> errorMessages = new ArrayList<>();
                errorMessages.add(new Message("Something went wrong while handling the widget operations execution.", MessageLevel.ERROR));
                errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
                return new Failure(errorMessages);
            } else {
                return new Success(this.feedbackMessageService.getFeedbackMessages());
            }
        };
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
