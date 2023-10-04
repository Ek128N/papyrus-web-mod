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
import java.util.function.Supplier;

import org.eclipse.papyrus.web.custom.widgets.primitivelist.dto.PrimitiveListItem;
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
        String iconURL = listDescription.getIconURLProvider().apply(variableManager);
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

            PrimitiveListItem item = PrimitiveListItem.newPrimitiveListItem(itemId)//
                    .label(itemLabel)//
                    .kind(itemKind)//
                    .imageURL("")//
                    .deletable(isItemDeletable)//
                    .deleteHandler(deleteHandler)//
                    .build();

            items.add(item);
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

        if (readOnly != null) {
            listElementPropsBuilder.readOnly(readOnly);
        }

        return new Element(PrimitiveListWidgetElementProps.TYPE, listElementPropsBuilder.build());
    }

}
