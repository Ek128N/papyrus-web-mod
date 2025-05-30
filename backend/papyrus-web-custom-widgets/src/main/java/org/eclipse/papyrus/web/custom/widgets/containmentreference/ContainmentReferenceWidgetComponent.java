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
package org.eclipse.papyrus.web.custom.widgets.containmentreference;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.papyrus.web.custom.widgets.containmentreference.dto.CreateElementInContainmentReferenceHandlerParameters;
import org.eclipse.papyrus.web.custom.widgets.containmentreference.dto.MoveContainmentReferenceItemHandlerParamaters;
import org.eclipse.sirius.components.forms.ClickEventKind;
import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.widget.reference.ReferenceWidgetStyle;

/**
 * The component used to create the containment reference widget and its items.
 *
 * @author Jerome Gout
 */
public class ContainmentReferenceWidgetComponent implements IComponent {

    public static final String ITEM_VARIABLE = "item";

    public static final String MOVE_FROM_VARIABLE = "fromIndex";

    public static final String MOVE_TO_VARIABLE = "toIndex";

    public static final String CREATION_DESCRIPTION_ID_VARIABLE = "creationDescriptionId";

    public static final String CLICK_EVENT_KIND_VARIABLE = "onClickEventKind";

    private final ContainmentReferenceWidgetComponentProps props;

    public ContainmentReferenceWidgetComponent(ContainmentReferenceWidgetComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }
    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        ContainmentReferenceWidgetDescription referenceDescription = this.props.getContainmentReferenceWidgetDescription();

        String label = referenceDescription.getLabelProvider().apply(variableManager);
        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, referenceDescription.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, referenceDescription.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);
        String id = referenceDescription.getIdProvider().apply(idVariableManager);

        List<String> iconURL = referenceDescription.getIconURLProvider().apply(variableManager);
        Boolean readOnly = referenceDescription.getIsReadOnlyProvider().apply(variableManager);
        String ownerId = referenceDescription.getOwnerIdProvider().apply(variableManager);

        String ownerKind = referenceDescription.getOwnerKindProvider().apply(variableManager);
        String referenceKind = referenceDescription.getReferenceKindProvider().apply(variableManager);
        boolean isMany = referenceDescription.getIsManyProvider().apply(variableManager);
        ReferenceWidgetStyle style = referenceDescription.getStyleProvider().apply(variableManager);

        List<ContainmentReferenceItem> items = this.getItems(variableManager, referenceDescription);

        List<Element> children = List.of(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(referenceDescription, variableManager)));

        var builder = ContainmentReferenceElementProps.newContainmentReferenceElementProps(id)
                .label(label)
                .iconURL(iconURL)
                .descriptionId(referenceDescription.getId())
                .values(items)
                .ownerKind(ownerKind)
                .referenceKind(referenceKind)
                .many(isMany)
                .ownerId(ownerId)
                .children(children);
        if (referenceDescription.getHelpTextProvider() != null) {
            builder.helpTextProvider(() -> referenceDescription.getHelpTextProvider().apply(variableManager));
        }
        if (readOnly != null) {
            builder.readOnly(readOnly);
        }
        if (style != null) {
            builder.style(style);
        }
        if (referenceDescription.getMoveHandlerProvider() != null) {
            Function<MoveContainmentReferenceItemHandlerParamaters, IStatus> moveHandler = input -> {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(ITEM_VARIABLE, input.value());
                childVariableManager.put(MOVE_FROM_VARIABLE, input.fromIndex());
                childVariableManager.put(MOVE_TO_VARIABLE, input.toIndex());
                return referenceDescription.getMoveHandlerProvider().apply(childVariableManager);
            };
            builder.moveHandler(moveHandler);
        }
        if (referenceDescription.getCreateElementHandlerProvider() != null) {
            Function<CreateElementInContainmentReferenceHandlerParameters, Object> createElementHandler = input -> {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(CREATION_DESCRIPTION_ID_VARIABLE, input.creationDescriptionId());
                return referenceDescription.getCreateElementHandlerProvider().apply(childVariableManager);
            };
            builder.createElementHandler(createElementHandler);
        }
        return new Element(ContainmentReferenceElementProps.TYPE, builder.build());
    }

    private List<ContainmentReferenceItem> getItems(VariableManager variableManager, ContainmentReferenceWidgetDescription referenceDescription) {
        List<?> rawValue = referenceDescription.getItemsProvider().apply(variableManager);
        List<ContainmentReferenceItem> items = rawValue.stream()
                .map(object -> {
                    VariableManager childVariables = variableManager.createChild();
                    childVariables.put(ContainmentReferenceWidgetComponent.ITEM_VARIABLE, object);
                    String itemId = referenceDescription.getItemIdProvider().apply(childVariables);
                    String itemLabel = referenceDescription.getItemLabelProvider().apply(childVariables);
                    List<String> itemImageURL = referenceDescription.getItemImageURLProvider().apply(childVariables);
                    String itemKind = referenceDescription.getItemKindProvider().apply(childVariables);
                    Function<VariableManager, IStatus> clickHandlerProvider = referenceDescription.getItemClickHandlerProvider();
                    Function<VariableManager, IStatus> removeHandlerProvider = referenceDescription.getItemRemoveHandlerProvider();

                    var referenceValueBuilder = ContainmentReferenceItem.newReferenceItem(itemId)
                            .label(itemLabel)
                            .iconURL(itemImageURL)
                            .kind(itemKind);
                    if (clickHandlerProvider != null) {
                        Function<ClickEventKind, IStatus> clickHandler = (clickEventKind) -> {
                            VariableManager clickHandlerVariableManager = childVariables.createChild();
                            clickHandlerVariableManager.put(CLICK_EVENT_KIND_VARIABLE, clickEventKind.toString());
                            return clickHandlerProvider.apply(clickHandlerVariableManager);
                        };
                        referenceValueBuilder.clickHandler(clickHandler);
                    }
                    if (removeHandlerProvider != null) {
                        Supplier<IStatus> removeHandler = () -> {
                            return removeHandlerProvider.apply(childVariables);
                        };
                        referenceValueBuilder.removeHandler(removeHandler);
                    }
                    return referenceValueBuilder.build();
                })
                .toList();
        return items;
    }

}
