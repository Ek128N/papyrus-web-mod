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

import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.util.PapyrusWidgetsSwitch;
import org.eclipse.sirius.components.formdescriptioneditors.IWidgetPreviewConverterProvider;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.ListStyleProvider;
import org.eclipse.sirius.components.view.form.ListDescriptionStyle;
import org.springframework.stereotype.Service;

/**
 * Converter used to convert Papyrus widgets from view to API for preview editor.
 *
 * @author Arthur Daussy
 */
@Service
public class PapyrusWidgetsPreviewConverterProvider implements IWidgetPreviewConverterProvider {

    @Override
    public Switch<AbstractWidgetDescription> getWidgetConverter(FormDescriptionEditorDescription formDescriptionEditorDescription, VariableManager variableManager) {
        return new PapyrusWidgetsSwitch<>() {
            @Override
            public AbstractWidgetDescription casePrimitiveListWidgetDescription(PrimitiveListWidgetDescription viewListDescription) {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VariableManager.SELF, viewListDescription);
                String id = formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);

                org.eclipse.papyrus.web.custom.widgets.primitivelist.PrimitiveListWidgetDescription.Builder builder = org.eclipse.papyrus.web.custom.widgets.primitivelist.PrimitiveListWidgetDescription
                        .newPrimitiveListDescription(UUID.randomUUID().toString())//
                        .idProvider(vm -> id)//
                        .labelProvider(vm -> PapyrusWidgetsPreviewConverterProvider.this.getWidgetLabel(viewListDescription, "Primitive List"))//
                        .iconURLProvider(variableManager -> "")//
                        .isReadOnlyProvider(variableManager -> false)//
                        .itemsProvider(variableManager -> List.of())//
                        .itemKindProvider(variableManager -> "")//
                        .itemDeleteHandlerProvider((vm) -> new Success())//
                        .itemIdProvider(variableManager -> "")//
                        .itemLabelProvider(vm -> "")//
                        .itemDeletableProvider(vm -> true)//
                        .styleProvider(variableManager -> {
                            ListDescriptionStyle style = viewListDescription.getStyle();
                            if (style == null) {
                                return null;
                            }
                            return new ListStyleProvider(style).apply(variableManager);
                        })//
                        .diagnosticsProvider(variableManager -> List.of())//
                        .newValueHandler((vm, value) -> new Success())//
                        .kindProvider(object -> "")//
                        .messageProvider(object -> "");
                if (viewListDescription.getHelpExpression() != null && !viewListDescription.getHelpExpression().isBlank()) {
                    builder.helpTextProvider(vm -> PapyrusWidgetsPreviewConverterProvider.this.getWidgetHelpText(viewListDescription));
                }

                return builder.build();
            }
        };
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
