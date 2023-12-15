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
package org.eclipse.papyrus.web.custom.widgets.primitivelist;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.papyrus.web.custom.widgets.primitivelist.dto.PrimitiveListItem;
import org.eclipse.papyrus.web.custom.widgets.primitivelist.dto.ReorderPrimitiveListHandlerParameters;
import org.eclipse.sirius.components.forms.ListStyle;
import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to create the primitive list widget and its items.
 *
 * @author Arthur Daussy
 */
public class PrimitiveListWidgetComponent implements IComponent {

    public static final String CANDIDATE_INDEX_VARIABLE = "candidateIndex";

    public static final String CANDIDATE_VARIABLE = "candidate";

    public static final String ITEM_VARIABLE = "item";

    public static final String ITEM_ID_VARIABLE = "itemId";

    public static final String MOVE_FROM_VARIABLE = "fromIndex";

    public static final String MOVE_TO_VARIABLE = "toIndex";

    private PrimitiveListWidgetComponentProps props;

    public PrimitiveListWidgetComponent(PrimitiveListWidgetComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        PrimitiveListWidgetDescription listDescription = this.props.getPrimitiveWidgetDescription();

        String label = listDescription.getLabelProvider().apply(variableManager);

        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, listDescription.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, listDescription.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);

        String id = listDescription.getIdProvider().apply(idVariableManager);
        List<String> iconURL = listDescription.getIconURLProvider().apply(variableManager);
        Boolean readOnly = listDescription.getIsReadOnlyProvider().apply(variableManager);
        List<?> itemCandidates = listDescription.getItemsProvider().apply(variableManager);
        ListStyle style = listDescription.getStyleProvider().apply(variableManager);

        List<Element> children = List.of(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(listDescription, variableManager)));

        List<PrimitiveListItem> items = new ArrayList<>(itemCandidates.size());
        int index = 0;
        for (Object itemCandidate : itemCandidates) {
            VariableManager itemVariableManager = variableManager.createChild();
            itemVariableManager.put(CANDIDATE_VARIABLE, itemCandidate);
            itemVariableManager.put(CANDIDATE_INDEX_VARIABLE, index++);

            String itemId = listDescription.getItemIdProvider().apply(itemVariableManager);
            String itemLabel = listDescription.getItemLabelProvider().apply(itemVariableManager);
            String itemKind = listDescription.getItemKindProvider().apply(itemVariableManager);
            boolean isItemDeletable = listDescription.getItemDeletableProvider().apply(itemVariableManager);
            Supplier<IStatus> deleteHandler = () -> listDescription.getItemDeleteHandlerProvider().apply(itemVariableManager);

            PrimitiveListItem.Builder itemBuilder = PrimitiveListItem.newPrimitiveListItem(itemId)//
                    .label(itemLabel)//
                    .kind(itemKind)//
                    .iconURL(List.of())//
                    .deletable(isItemDeletable)//
                    .deleteHandler(deleteHandler);
            if (listDescription.getItemActionHandlerProvider() != null) {
                itemBuilder.actionHandler(() -> listDescription.getItemActionHandlerProvider().apply(itemVariableManager));
            }
            Function<VariableManager, Boolean> itemActionPreconditionHandler = listDescription.getItemActionPreconditionHandler();
            if (itemActionPreconditionHandler != null) {
                itemBuilder.actionPreconditionHandler(() -> {
                    return itemActionPreconditionHandler.apply(itemVariableManager);
                });
            }
            if (listDescription.getItemActionIconURLProvider() != null) {
                itemBuilder.actionIconURL(listDescription.getItemActionIconURLProvider().apply(itemVariableManager));
            }

            items.add(itemBuilder.build());
        }

        PrimitiveListWidgetElementProps.Builder listElementPropsBuilder = PrimitiveListWidgetElementProps.newListElementProps(id)
                .label(label)
                .items(items)
                .children(children);
        if (iconURL != null) {
            listElementPropsBuilder.iconURL(iconURL);
        }
        if (style != null) {
            listElementPropsBuilder.style(style);
        }
        if (listDescription.getHelpTextProvider() != null) {
            listElementPropsBuilder.helpTextProvider(() -> listDescription.getHelpTextProvider().apply(variableManager));
        }
        if (listDescription.getNewValueHandler() != null) {
            listElementPropsBuilder.newValueHandler(newValue -> listDescription.getNewValueHandler().apply(variableManager, newValue));
        }
        if (listDescription.getCandidatesProvider() != null) {
            listElementPropsBuilder.candidatesProvider(() -> listDescription.getCandidatesProvider().apply(variableManager));
        }
        if (listDescription.getReorderHandlerProvider() != null) {
            Function<ReorderPrimitiveListHandlerParameters, IStatus> reorderHandler = input -> {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(ITEM_ID_VARIABLE, input.itemId());
                childVariableManager.put(MOVE_FROM_VARIABLE, input.fromIndex());
                childVariableManager.put(MOVE_TO_VARIABLE, input.toIndex());
                return listDescription.getReorderHandlerProvider().apply(childVariableManager);
            };
            listElementPropsBuilder.reorderHandler(reorderHandler);
        }
        if (readOnly != null) {
            listElementPropsBuilder.readOnly(readOnly);
        }

        return new Element(PrimitiveListWidgetElementProps.TYPE, listElementPropsBuilder.build());
    }

}
