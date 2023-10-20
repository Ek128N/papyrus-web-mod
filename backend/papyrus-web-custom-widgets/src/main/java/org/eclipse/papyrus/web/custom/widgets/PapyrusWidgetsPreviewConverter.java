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

import java.util.List;
import java.util.UUID;

import org.eclipse.papyrus.web.custom.widgets.languageexpression.LanguageExpressionDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.LanguageExpressionWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveRadioWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.util.PapyrusWidgetsSwitch;
import org.eclipse.papyrus.web.custom.widgets.primitiveradio.PrimitiveRadioDescription;
import org.eclipse.sirius.components.compatibility.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.ListStyleProvider;
import org.eclipse.sirius.components.view.form.ListDescriptionStyle;

/**
 * Converter used to create the widget previews.
 *
 * @author Arthur Daussy
 */
public class PapyrusWidgetsPreviewConverter extends PapyrusWidgetsSwitch<AbstractWidgetDescription> {

    private final VariableManager variableManager;

    public PapyrusWidgetsPreviewConverter(VariableManager variableManager) {
        this.variableManager = variableManager;
    }

    @Override
    public AbstractWidgetDescription caseLanguageExpressionWidgetDescription(LanguageExpressionWidgetDescription object) {

        var builder = LanguageExpressionDescription.newLanguageExpressionDescription(UUID.randomUUID().toString()).idProvider(new WidgetIdProvider())
                .labelProvider(varMan -> "")//
                .iconURLProvider(varMan -> "") //
                .targetObjectIdProvider(varMan -> "")//
                .isReadOnlyProvider(varMan -> false);

        builder.helpTextProvider(varMan -> "Help expression");

        return builder.build();
    }

    @Override
    public AbstractWidgetDescription casePrimitiveRadioWidgetDescription(PrimitiveRadioWidgetDescription object) {

        var builder = PrimitiveRadioDescription.newPrimitiveRadioDescription(UUID.randomUUID().toString()).idProvider(new WidgetIdProvider()) //
                .targetObjectIdProvider(varMan -> "")//
                .labelProvider(varMan -> "") //
                .iconURLProvider(varMan -> "") //
                .isReadOnlyProvider(varMan -> false) //
                .candidateValueProvider(varMan -> "") //
                .candidateListProvider(varMan -> List.of("Option1", "Option2", "Option3")) //
                .newValueHandler(varMan -> new Success()) //
                .helpTextProvider(varMan -> "Help text");

        return builder.build();

    }

    @Override
    public AbstractWidgetDescription casePrimitiveListWidgetDescription(PrimitiveListWidgetDescription viewListDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, viewListDescription);

        org.eclipse.papyrus.web.custom.widgets.primitivelist.PrimitiveListWidgetDescription.Builder builder = org.eclipse.papyrus.web.custom.widgets.primitivelist.PrimitiveListWidgetDescription
                .newPrimitiveListDescription(UUID.randomUUID().toString())//
                .idProvider(varMan -> "")//
                .labelProvider(vm -> this.getWidgetLabel(viewListDescription, "Primitive List"))//
                .iconURLProvider(varMan -> "")//
                .isReadOnlyProvider(varMan -> false)//
                .itemsProvider(varMan -> List.of())//
                .itemKindProvider(varMan -> "")//
                .itemDeleteHandlerProvider((vm) -> new Success())//
                .itemIdProvider(varMan -> "")//
                .itemLabelProvider(vm -> "")//
                .targetObjectIdProvider(varMan -> "")//
                .itemDeletableProvider(vm -> true)//
                .styleProvider(varMan -> {
                    ListDescriptionStyle style = viewListDescription.getStyle();
                    if (style == null) {
                        return null;
                    }
                    return new ListStyleProvider(style).apply(this.variableManager);
                })//
                .diagnosticsProvider(varMan -> List.of())//
                .newValueHandler((vm, value) -> new Success())//
                .kindProvider(object -> "")//
                .messageProvider(object -> "");
        if (viewListDescription.getHelpExpression() != null && !viewListDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(vm -> this.getWidgetHelpText(viewListDescription));
        }

        return builder.build();
    }

    private String getWidgetLabel(org.eclipse.sirius.components.view.form.WidgetDescription widgetDescription, String defaultLabel) {
        String widgetLabel = defaultLabel;
        String name = widgetDescription.getName();
        String labelExpression = widgetDescription.getLabelExpression();
        if (labelExpression != null && !labelExpression.isBlank() && !labelExpression.startsWith("aql:")) {
            widgetLabel = labelExpression;
        } else if (name != null && !name.isBlank()) {
            widgetLabel = name;
        }
        return widgetLabel;
    }

    private String getWidgetHelpText(org.eclipse.sirius.components.view.form.WidgetDescription widgetDescription) {
        String helpText = "Help";
        String helpExpression = widgetDescription.getHelpExpression();
        if (helpExpression != null && !helpExpression.isBlank() && !helpExpression.startsWith("aql:")) {
            helpText = helpExpression;
        }
        return helpText;
    }

}
