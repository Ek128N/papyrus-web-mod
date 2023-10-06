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
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.web.custom.widgets.languageexpression.LanguageExpressionDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.LanguageExpressionWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListAddOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListDeleteOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveRadioWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.util.PapyrusWidgetsSwitch;
import org.eclipse.papyrus.web.custom.widgets.primitivelist.PrimitiveListWidgetComponent;
import org.eclipse.papyrus.web.custom.widgets.primitivelist.PrimitiveListWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.primitiveradio.PrimitiveRadioDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.compatibility.forms.WidgetIdProvider;
import org.eclipse.sirius.components.compatibility.utils.StringValueProvider;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.ListStyle;
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
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.ListStyleProvider;
import org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter;
import org.eclipse.sirius.components.view.form.ListDescriptionStyle;

/**
 * Converts all view-based Papyrus widget description into its API equivalent.<br>
 * Each custom widget has it own method caseXXX
 *
 * @author Jerome Gout
 */
public class PapyrusWidgetsConverterSwitch extends PapyrusWidgetsSwitch<AbstractWidgetDescription> {

    private final AQLInterpreter interpreter;

    private IFeedbackMessageService feedbackMessageService;

    private IEditService editService;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    private final IFormIdProvider widgetIdProvider;

    public PapyrusWidgetsConverterSwitch(AQLInterpreter interpreter, IEditService editService, IObjectService objectService, IFeedbackMessageService feedbackMessageService, IFormIdProvider widgetIdProvider) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.editService = Objects.requireNonNull(editService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.semanticTargetIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(objectService::getId).orElse(null);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);

    }

    @Override
    public AbstractWidgetDescription caseLanguageExpressionWidgetDescription(LanguageExpressionWidgetDescription languageExpressionDescription) {
        String descriptionId = this.getDescriptionId(languageExpressionDescription);

        var builder = LanguageExpressionDescription.newLanguageExpressionDescription(descriptionId)
                .idProvider(new WidgetIdProvider())
                .labelProvider(variableManager -> this.getLanguageExpressionLabel(languageExpressionDescription, variableManager))
                .iconURLProvider(variableManager -> "")
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .isReadOnlyProvider(this.getReadOnlyValueProvider(languageExpressionDescription.getIsEnabledExpression()));

        if (languageExpressionDescription.getHelpExpression() != null && !languageExpressionDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(languageExpressionDescription.getHelpExpression()));
        }

        return builder.build();
    }

    private String getDescriptionId(EObject description) {
        return this.widgetIdProvider.getFormElementDescriptionId(description);
    }

    private String getLanguageExpressionLabel(LanguageExpressionWidgetDescription languageExpressionDescription, VariableManager variableManager) {
        return new StringValueProvider(this.interpreter, languageExpressionDescription.getLabelExpression()).apply(variableManager);
    }

    @Override
    public AbstractWidgetDescription casePrimitiveRadioWidgetDescription(PrimitiveRadioWidgetDescription primitiveRadioDescription) {
        String descriptionId = this.getDescriptionId(primitiveRadioDescription);

        var builder = PrimitiveRadioDescription.newPrimitiveRadioDescription(descriptionId)
                .idProvider(new WidgetIdProvider())
                .targetObjectIdProvider(this.semanticTargetIdProvider)//
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

    private String getPrimitiveListItemId(VariableManager variableManager) {
        String indexKey = variableManager.get(PrimitiveListWidgetComponent.CANDIDATE_INDEX_VARIABLE, Integer.class)//
                .map(Object::toString).orElse("");
        String valueKey = variableManager.get(PrimitiveListWidgetComponent.CANDIDATE_VARIABLE, Object.class)//
                .map(Object::toString).orElse("");
        return UUID.nameUUIDFromBytes((indexKey + "-" + valueKey).getBytes()).toString();
    }

    @Override
    public AbstractWidgetDescription casePrimitiveListWidgetDescription(org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription viewListDescription) {
        String descriptionId = this.getDescriptionId(viewListDescription);
        StringValueProvider labelProvider = this.getStringValueProvider(viewListDescription.getLabelExpression());
        Function<VariableManager, Boolean> isReadOnlyProvider = this.getReadOnlyValueProvider(viewListDescription.getIsEnabledExpression());
        Function<VariableManager, List<?>> valueProvider = this.getValuesProvider(viewListDescription.getValueExpression());
        Function<VariableManager, String> displayProvider = this.getItemLabelProvider(viewListDescription.getDisplayExpression());
        Function<VariableManager, Boolean> isDeletableProvider = varMan -> viewListDescription.getDeleteOperation() != null;
        Function<VariableManager, String> itemIdProvider = this::getPrimitiveListItemId;
        Function<VariableManager, String> itemKindProvider = variableManger -> "unknown";
        Function<VariableManager, IStatus> itemDeleteHandlerProvider = this.handlePrimitiveListDeleteOperation(viewListDescription.getDeleteOperation());
        BiFunction<VariableManager, String, IStatus> newValueHandlerProvider = this.getNewValueHandler(viewListDescription.getAddOperation());

        Function<VariableManager, ListStyle> styleProvider = variableManager -> {
            var effectiveStyle = viewListDescription.getConditionalStyles().stream()//
                    .filter(style -> this.matches(style.getCondition(), variableManager))//
                    .map(ListDescriptionStyle.class::cast).findFirst().orElseGet(viewListDescription::getStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new ListStyleProvider(effectiveStyle).apply(variableManager);
        };

        PrimitiveListWidgetDescription.Builder builder = PrimitiveListWidgetDescription.newPrimitiveListDescription(descriptionId)//
                .idProvider(new WidgetIdProvider())//
                .labelProvider(labelProvider)//
                .iconURLProvider(variableManager -> "")//
                .isReadOnlyProvider(isReadOnlyProvider)//
                .itemsProvider(valueProvider)//
                .itemKindProvider(itemKindProvider)//
                .itemDeletableProvider(isDeletableProvider)//
                .itemDeleteHandlerProvider(itemDeleteHandlerProvider)//
                .itemIdProvider(itemIdProvider)//
                .itemLabelProvider(displayProvider)//
                .targetObjectIdProvider(this.semanticTargetIdProvider) //
                .styleProvider(styleProvider)//
                .diagnosticsProvider(variableManager -> List.of())//
                .kindProvider(object -> "")//
                .messageProvider(object -> "");

        if (viewListDescription.getHelpExpression() != null && !viewListDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(viewListDescription.getHelpExpression()));
        }

        if (newValueHandlerProvider != null) {
            builder.newValueHandler(newValueHandlerProvider); //
        }
        return builder.build();
    }

    private Function<VariableManager, IStatus> handlePrimitiveListDeleteOperation(PrimitiveListDeleteOperation deleteOperation) {
        if (deleteOperation == null || deleteOperation.getBody().isEmpty()) {
            return varMan -> new Success();
        }
        return this.handleOperation(deleteOperation.getBody(), "Something went wrong while handling item deletion.");
    }

    private boolean matches(String condition, VariableManager variableManager) {
        return this.interpreter.evaluateExpression(variableManager.getVariables(), condition).asBoolean().orElse(Boolean.FALSE);
    }

    private <T> BiFunction<VariableManager, T, IStatus> getNewValueHandler(PrimitiveListAddOperation addOperation) {
        if (addOperation == null || addOperation.getBody().isEmpty()) {
            return null;
        }
        return (variableManager, newValue) -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(ViewFormDescriptionConverter.NEW_VALUE, newValue);
            OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
            Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(addOperation.getBody(), childVariableManager);
            if (optionalVariableManager.isEmpty()) {
                return this.buildFailureWithFeedbackMessages("Something went wrong while handling the widget new value.");
            } else {
                return this.buildSuccessWithSemanticChangeAndFeedbackMessages();
            }
        };
    }

    private Success buildSuccessWithSemanticChangeAndFeedbackMessages() {
        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
    }

    private Failure buildFailureWithFeedbackMessages(String technicalMessage) {
        List<Message> errorMessages = new ArrayList<>();
        errorMessages.add(new Message(technicalMessage, MessageLevel.ERROR));
        errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
        return new Failure(errorMessages);
    }

    private Function<VariableManager, IStatus> handleOperation(List<Operation> operations, String errorMessage) {
        return (variableManager) -> {
            VariableManager childVariableManager = variableManager.createChild();
            OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
            Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, childVariableManager);
            if (optionalVariableManager.isEmpty()) {
                return this.buildFailureWithFeedbackMessages(errorMessage);
            } else {
                return this.buildSuccessWithSemanticChangeAndFeedbackMessages();
            }
        };
    }

    private Function<VariableManager, String> getItemLabelProvider(String valueExpression) {
        if (valueExpression != null) {
            return this.getStringValueProvider(valueExpression);
        } else {
            return varMan -> {
                return varMan.get(PrimitiveListWidgetComponent.CANDIDATE_VARIABLE, Object.class).map(Object::toString).orElse("");
            };
        }
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

    private Function<VariableManager, List<?>> getValuesProvider(String expression) {
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
