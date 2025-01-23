/*****************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo, Artal Technologies.
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
 *  Titouan BOUETE-GIRAUD (Artal Technologies) - Issue 210
 *****************************************************************************/
package org.eclipse.papyrus.web.custom.widgets.customimage;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component to render a custom image widget.
 *
 * @author tiboue
 */
public class CustomImageComponent implements IComponent {

    private final CustomImageComponentProps props;

    public CustomImageComponent(CustomImageComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        CustomImageDescription customImageDescription = this.props.getCustomImageDescription();

        String label = customImageDescription.getLabelProvider().apply(variableManager);
        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, customImageDescription.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, customImageDescription.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);
        String id = customImageDescription.getIdProvider().apply(idVariableManager);

        List<String> iconURL = customImageDescription.getIconURLProvider().apply(variableManager);
        Boolean readOnly = customImageDescription.getIsReadOnlyProvider().apply(variableManager);
        String currentUuid = customImageDescription.getCurrentUuidProvider().apply(variableManager);

        Function<String, IStatus> specializedNewUUidHandler = (newUuid) -> {
            var childVariableManager = variableManager.createChild();
            childVariableManager.put("newUuid", newUuid);
            return customImageDescription.getNewUuidHandler().apply(childVariableManager);
        };

        Function<String, IStatus> specializedRemoveUUidHandler = (removeUuid) -> {
            var childVariableManager = variableManager.createChild();
            childVariableManager.put("removeUuid", removeUuid);
            return customImageDescription.getRemoveUuidHandler().apply(childVariableManager);
        };

        var cutomImageWidgetElementPropsBuilder = CustomImageElementProps.newCustomImageElementProps(id)
                .label(label)
                .iconURL(iconURL)//
                .diagnostics(List.of())//
                .currentUuid(currentUuid)
                .newUuidHandler(specializedNewUUidHandler)
                .removeUuidHandler(specializedRemoveUUidHandler);

        if (iconURL != null) {
            cutomImageWidgetElementPropsBuilder.iconURL(iconURL);
        }
        if (customImageDescription.getHelpTextProvider() != null) {
            cutomImageWidgetElementPropsBuilder.helpTextProvider(() -> customImageDescription.getHelpTextProvider().apply(variableManager));
        }
        if (readOnly != null) {
            cutomImageWidgetElementPropsBuilder.readOnly(readOnly);
        }

        return new Element(CustomImageElementProps.TYPE, cutomImageWidgetElementPropsBuilder.build());

    }

}
