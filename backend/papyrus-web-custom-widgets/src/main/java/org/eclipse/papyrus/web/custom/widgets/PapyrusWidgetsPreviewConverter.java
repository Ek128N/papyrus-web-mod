/*****************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
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
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.LanguageExpressionWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveRadioWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.util.PapyrusWidgetsSwitch;
import org.eclipse.papyrus.web.custom.widgets.primitiveradio.PrimitiveRadioDescription;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.ListStyleProvider;
import org.eclipse.sirius.components.view.form.ListDescriptionStyle;
import org.eclipse.sirius.components.widget.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.components.widget.reference.ReferenceWidgetStyle;
import org.eclipse.sirius.components.widget.reference.ReferenceWidgetStyleProvider;
import org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescriptionStyle;

/**
 * Converter used to create the widget previews.
 *
 * @author Arthur Daussy
 */
public class PapyrusWidgetsPreviewConverter extends PapyrusWidgetsSwitch<AbstractWidgetDescription> {

    private final VariableManager variableManager;

    private final FormDescriptionEditorDescription formDescriptionEditorDescription;

    public PapyrusWidgetsPreviewConverter(VariableManager variableManager, FormDescriptionEditorDescription formDescriptionEditorDescription) {
        this.variableManager = variableManager;
        this.formDescriptionEditorDescription = formDescriptionEditorDescription;
    }

    @Override
    public AbstractWidgetDescription caseLanguageExpressionWidgetDescription(LanguageExpressionWidgetDescription languageExpressionDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, languageExpressionDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);
        var builder = LanguageExpressionDescription.newLanguageExpressionDescription(UUID.randomUUID().toString()).idProvider(varManager -> id)
                .labelProvider(varManager -> this.getWidgetLabel(languageExpressionDescription, "Language Expression"))//
                .iconURLProvider(varManager -> List.of()) //
                .targetObjectIdProvider(varManager -> "")//
                .isReadOnlyProvider(varManager -> false);

        builder.helpTextProvider(varManager -> "Help expression");

        return builder.build();
    }

    @Override
    public AbstractWidgetDescription casePrimitiveRadioWidgetDescription(PrimitiveRadioWidgetDescription primitiveRadioDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, primitiveRadioDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);
        var builder = PrimitiveRadioDescription.newPrimitiveRadioDescription(UUID.randomUUID().toString()).idProvider(varManager -> id) //
                .targetObjectIdProvider(varManager -> "")//
                .labelProvider(varManager -> this.getWidgetLabel(primitiveRadioDescription, "Primitive radio")) //
                .iconURLProvider(varManager -> List.of()) //
                .isReadOnlyProvider(varManager -> false) //
                .candidateValueProvider(varManager -> "") //
                .candidateListProvider(varManager -> List.of("Option1", "Option2", "Option3")) //
                .newValueHandler(varManager -> new Success()) //
                .helpTextProvider(varManager -> "Help text");

        return builder.build();

    }

    @Override
    public AbstractWidgetDescription casePrimitiveListWidgetDescription(PrimitiveListWidgetDescription viewListDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, viewListDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);
        org.eclipse.papyrus.web.custom.widgets.primitivelist.PrimitiveListWidgetDescription.Builder builder = org.eclipse.papyrus.web.custom.widgets.primitivelist.PrimitiveListWidgetDescription
                .newPrimitiveListDescription(UUID.randomUUID().toString())//
                .idProvider(varManager -> id)//
                .labelProvider(varManager -> this.getWidgetLabel(viewListDescription, "Primitive List"))//
                .iconURLProvider(varManager -> List.of())//
                .isReadOnlyProvider(varManager -> false)//
                .itemsProvider(varManager -> List.of())//
                .itemKindProvider(varManager -> "")//
                .itemDeleteHandlerProvider(varManager -> new Success())//
                .itemIdProvider(varManager -> "")//
                .itemLabelProvider(varManager -> "")//
                .targetObjectIdProvider(varManager -> "")//
                .itemDeletableProvider(varManager -> true)//
                .styleProvider(varManager -> {
                    ListDescriptionStyle style = viewListDescription.getStyle();
                    if (style == null) {
                        return null;
                    }
                    return new ListStyleProvider(style).apply(this.variableManager);
                })//
                .diagnosticsProvider(varManager -> List.of())//
                .newValueHandler((varManager, value) -> new Success())//
                .kindProvider(object -> "")//
                .messageProvider(object -> "");
        if (viewListDescription.getHelpExpression() != null && !viewListDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(varManager -> this.getWidgetHelpText(viewListDescription));
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

    private ReferenceWidgetStyle getWidgetStyle(ReferenceWidgetDescriptionStyle style, VariableManager varManager) {
        if (style == null) {
            return null;
        }
        return new ReferenceWidgetStyleProvider(style).apply(varManager);
    }

    @Override
    public AbstractWidgetDescription caseContainmentReferenceWidgetDescription(ContainmentReferenceWidgetDescription description) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, description);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);
        var builder = org.eclipse.papyrus.web.custom.widgets.containmentreference.ContainmentReferenceWidgetDescription.newContainmentReferenceWidgetDescription(UUID.randomUUID().toString()) //
                .targetObjectIdProvider(varManager -> "") //
                .idProvider(varManager -> id) //
                .labelProvider(varManager -> this.getWidgetLabel(description, "Containment Reference")) //
                .isReadOnlyProvider(varManager -> false) //
                .iconURLProvider(varManager -> List.of()) //
                .itemsProvider(varManager -> List.of()) //
                .itemIdProvider(varManager -> "") //
                .itemKindProvider(varManager -> "") //
                .itemLabelProvider(varManager -> "") //
                .itemIconURLProvider(varManager -> List.of()) //
                .ownerKindProvider(varManager -> "") //
                .referenceKindProvider(varManager -> "") //
                .isManyProvider(varManager -> true)
                .styleProvider(varManager -> this.getWidgetStyle(description.getStyle(), varManager)) //
                .ownerIdProvider(varManager -> "") //
                .diagnosticsProvider(varManager -> List.of()) //
                .kindProvider(object -> "") //
                .messageProvider(object -> "") //
                .itemClickHandlerProvider(varManager -> new Success()) //
                .itemRemoveHandlerProvider(varManager -> new Success()) //
                .moveHandlerProvider(varManager -> new Success());
        if (description.getHelpExpression() != null && !description.getHelpExpression().isBlank()) {
            builder.helpTextProvider(varManager -> this.getWidgetHelpText(description));
        }
        return builder.build();
    }

    @Override
    public AbstractWidgetDescription caseMonoReferenceWidgetDescription(MonoReferenceWidgetDescription referenceDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, referenceDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);
        var builder = ReferenceWidgetDescription.newReferenceWidgetDescription(UUID.randomUUID().toString())
                .idProvider(varManager -> id)
                .targetObjectIdProvider(varManager -> "")
                .labelProvider(varManager -> this.getWidgetLabel(referenceDescription, "Mono Reference"))
                .iconURLProvider(varManager -> List.of())
                .isReadOnlyProvider(varManager -> false)
                .itemsProvider(varManager -> List.of())
                .optionsProvider(varManager -> List.of())
                .itemIdProvider(varManager -> "")
                .itemKindProvider(varManager -> "")
                .itemLabelProvider(varManager -> "")
                .itemKindProvider(varManager -> "")
                .itemLabelProvider(varManager -> "")
                .itemIconURLProvider(varManager -> List.of())
                .ownerKindProvider(varManager -> "")
                .referenceKindProvider(varManager -> "")
                .isContainmentProvider(varManager -> false)
                .isManyProvider(varManager -> false)
                .ownerIdProvider(varManager -> "")
                .ownerIdProvider(varManager -> "")
                .clearHandlerProvider(varManager -> new Success())
                .itemRemoveHandlerProvider(varManager -> new Success())
                .setHandlerProvider(varManager -> new Success())
                .addHandlerProvider(varManager -> new Success())
                .moveHandlerProvider(varManager -> new Success())
                .styleProvider(varManager -> this.getWidgetStyle(referenceDescription.getStyle(), this.variableManager))
                .diagnosticsProvider(varManager -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "");
        if (referenceDescription.getHelpExpression() != null && !referenceDescription.getHelpExpression().isBlank()) {
            String helpText = this.getWidgetHelpText(referenceDescription);
            builder.helpTextProvider(varManager -> helpText);
        }
        return builder.build();
    }

    @Override
    public AbstractWidgetDescription caseMultiReferenceWidgetDescription(MultiReferenceWidgetDescription referenceDescription) {
        VariableManager childVariableManager = this.variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, referenceDescription);
        String id = this.formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);
        var builder = ReferenceWidgetDescription.newReferenceWidgetDescription(UUID.randomUUID().toString())
                .idProvider(varManager -> id)
                .targetObjectIdProvider(varManager -> "")
                .labelProvider(varManager -> this.getWidgetLabel(referenceDescription, "Multi Reference"))
                .iconURLProvider(varManager -> List.of())
                .isReadOnlyProvider(varManager -> false)
                .itemsProvider(varManager -> List.of())
                .optionsProvider(varManager -> List.of())
                .itemIdProvider(varManager -> "")
                .itemKindProvider(varManager -> "")
                .itemLabelProvider(varManager -> "")
                .itemKindProvider(varManager -> "")
                .itemLabelProvider(varManager -> "")
                .itemIconURLProvider(varManager -> List.of())
                .ownerKindProvider(varManager -> "")
                .referenceKindProvider(varManager -> "")
                .isContainmentProvider(varManager -> false)
                .isManyProvider(varManager -> true)
                .ownerIdProvider(varManager -> "")
                .ownerIdProvider(varManager -> "")
                .clearHandlerProvider(varManager -> new Success())
                .itemRemoveHandlerProvider(varManager -> new Success())
                .setHandlerProvider(varManager -> new Success())
                .addHandlerProvider(varManager -> new Success())
                .moveHandlerProvider(varManager -> new Success())
                .styleProvider(varManager -> this.getWidgetStyle(referenceDescription.getStyle(), this.variableManager))
                .diagnosticsProvider(varManager -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "");
        if (referenceDescription.getHelpExpression() != null && !referenceDescription.getHelpExpression().isBlank()) {
            String helpText = this.getWidgetHelpText(referenceDescription);
            builder.helpTextProvider(varManager -> helpText);
        }
        return builder.build();
    }
}
