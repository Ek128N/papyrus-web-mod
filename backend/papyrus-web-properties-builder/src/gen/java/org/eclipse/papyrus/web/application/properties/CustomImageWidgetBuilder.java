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
package org.eclipse.papyrus.web.application.properties;

import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.AddImageOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CustomImageWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsFactory;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.RemoveImageOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.SelectImageOperation;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.ViewFactory;

/**
 * @author tiboue
 */
public class CustomImageWidgetBuilder {
    private String name;

    private String label;

    private String help;

    private String uuidExpression;

    private String addOperation;

    private String selectOperation;

    private String removeOperation;

    public CustomImageWidgetBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CustomImageWidgetBuilder label(String label) {
        this.label = label;
        return this;
    }

    public CustomImageWidgetBuilder help(String help) {
        this.help = help;
        return this;
    }

    public CustomImageWidgetBuilder uuidExpression(String uuidExpression) {
        this.uuidExpression = uuidExpression;
        return this;
    }

    public CustomImageWidgetBuilder addOperation(String addOperation) {
        this.addOperation = addOperation;
        return this;
    }

    public CustomImageWidgetBuilder selectOperation(String selectOperation) {
        this.selectOperation = selectOperation;
        return this;
    }

    public CustomImageWidgetBuilder removeOperation(String removeOperation) {
        this.removeOperation = removeOperation;
        return this;
    }

    public CustomImageWidgetDescription build() {
        CustomImageWidgetDescription description = PapyrusWidgetsFactory.eINSTANCE.createCustomImageWidgetDescription();
        description.setName(this.name);
        description.setLabelExpression(this.label);
        description.setHelpExpression(this.help);
        description.setUuidExpression(this.uuidExpression);

        AddImageOperation addOperation = PapyrusWidgetsFactory.eINSTANCE.createAddImageOperation();
        addOperation.getBody().add(this.createChangeContext(this.addOperation));
        description.setAddImageOperation(addOperation);

        SelectImageOperation selectOperation = PapyrusWidgetsFactory.eINSTANCE.createSelectImageOperation();
        selectOperation.getBody().add(this.createChangeContext(this.selectOperation));
        description.setSelectImageOperation(selectOperation);

        RemoveImageOperation removeOperation = PapyrusWidgetsFactory.eINSTANCE.createRemoveImageOperation();
        removeOperation.getBody().add(this.createChangeContext(this.removeOperation));
        description.setRemoveImageOperation(removeOperation);

        return description;
    }

    private ChangeContext createChangeContext(String contextExp) {
        ChangeContext changeCtxt = ViewFactory.eINSTANCE.createChangeContext();
        changeCtxt.setExpression(contextExp);
        return changeCtxt;
    }
}
