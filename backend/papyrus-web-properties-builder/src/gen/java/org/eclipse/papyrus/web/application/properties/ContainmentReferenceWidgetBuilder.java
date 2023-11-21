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

package org.eclipse.papyrus.web.application.properties;

import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CreateElementInReferenceOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceRemoveOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceReorderOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsFactory;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.ViewFactory;

@SuppressWarnings("checkstyle:HiddenField")
public class ContainmentReferenceWidgetBuilder {
    private String name;

    private String label;

    private String isEnable;

    private String help;

    private String owner;

    private String type;

    private String value;

    private String createOperation;

    private String removeOperation;

    private String reorderOperation;

    private boolean isMany;

    public ContainmentReferenceWidgetBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ContainmentReferenceWidgetBuilder label(String label) {
        this.label = label;
        return this;
    }

    public ContainmentReferenceWidgetBuilder isEnable(String isEnable) {
        this.isEnable = isEnable;
        return this;
    }

    public ContainmentReferenceWidgetBuilder help(String help) {
        this.help = help;
        return this;
    }

    public ContainmentReferenceWidgetBuilder owner(String owner) {
        this.owner = owner;
        return this;
    }

    public ContainmentReferenceWidgetBuilder type(String type) {
        this.type = type;
        return this;
    }

    public ContainmentReferenceWidgetBuilder value(String value) {
        this.value = value;
        return this;
    }

    public ContainmentReferenceWidgetBuilder createOperation(String createOperation) {
        this.createOperation = createOperation;
        return this;
    }

    public ContainmentReferenceWidgetBuilder removeOperation(String removeOperation) {
        this.removeOperation = removeOperation;
        return this;
    }

    public ContainmentReferenceWidgetBuilder reorderOperation(String reorderOperation) {
        this.reorderOperation = reorderOperation;
        return this;
    }

    public ContainmentReferenceWidgetBuilder isMany(boolean isMany) {
        this.isMany = isMany;
        return this;
    }

    public ContainmentReferenceWidgetDescription build() {
        ContainmentReferenceWidgetDescription description = PapyrusWidgetsFactory.eINSTANCE.createContainmentReferenceWidgetDescription();
        description.setName(this.name);
        description.setLabelExpression(this.label);
        description.setHelpExpression(this.help);
        description.setIsEnabledExpression(this.isEnable);
        description.setOwnerExpression(this.owner);
        description.setType(this.type);
        description.setMany(this.isMany);
        description.setValueExpression(this.value);

        CreateElementInReferenceOperation createOperation = PapyrusWidgetsFactory.eINSTANCE.createCreateElementInReferenceOperation();
        createOperation.getBody().add(createChangeContext(this.createOperation));
        description.setCreateElementOperation(createOperation);

        MultiReferenceRemoveOperation removeOperation = PapyrusWidgetsFactory.eINSTANCE.createMultiReferenceRemoveOperation();
        removeOperation.getBody().add(createChangeContext(this.removeOperation));
        description.setRemoveOperation(removeOperation);

        if (this.reorderOperation != null) {
            MultiReferenceReorderOperation reorderOperation = PapyrusWidgetsFactory.eINSTANCE.createMultiReferenceReorderOperation();
            reorderOperation.getBody().add(createChangeContext(this.reorderOperation));
            description.setReorderOperation(reorderOperation);
        }

        return description;
    }

    private ChangeContext createChangeContext(String contextExp) {
        ChangeContext changeCtxt = ViewFactory.eINSTANCE.createChangeContext();
        changeCtxt.setExpression(contextExp);
        return changeCtxt;
    }
}
