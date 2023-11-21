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

import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ClearReferenceOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CreateElementInReferenceOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceAddOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceRemoveOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceReorderOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsFactory;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.ViewFactory;

@SuppressWarnings("checkstyle:HiddenField")
public class MultiReferenceWidgetBuilder {
    private String name;

    private String label;

    private String isEnable;

    private String help;

    private String owner;

    private String type;

    private String value;

    private String searchScope;

    private String dropdownOptions;

    private String clearOperation;

    private String createOperation;

    private String addOperation;

    private String removeOperation;

    private String reorderOperation;

    public MultiReferenceWidgetBuilder name(String name) {
        this.name = name;
        return this;
    }

    public MultiReferenceWidgetBuilder label(String label) {
        this.label = label;
        return this;
    }

    public MultiReferenceWidgetBuilder isEnable(String isEnable) {
        this.isEnable = isEnable;
        return this;
    }

    public MultiReferenceWidgetBuilder help(String help) {
        this.help = help;
        return this;
    }

    public MultiReferenceWidgetBuilder owner(String owner) {
        this.owner = owner;
        return this;
    }

    public MultiReferenceWidgetBuilder type(String type) {
        this.type = type;
        return this;
    }

    public MultiReferenceWidgetBuilder value(String value) {
        this.value = value;
        return this;
    }

    public MultiReferenceWidgetBuilder searchScope(String searchScope) {
        this.searchScope = searchScope;
        return this;
    }

    public MultiReferenceWidgetBuilder dropdownOptions(String dropdownOptions) {
        this.dropdownOptions = dropdownOptions;
        return this;
    }

    public MultiReferenceWidgetBuilder clearOperation(String clearOperation) {
        this.clearOperation = clearOperation;
        return this;
    }

    public MultiReferenceWidgetBuilder createOperation(String createOperation) {
        this.createOperation = createOperation;
        return this;
    }

    public MultiReferenceWidgetBuilder addOperation(String addOperation) {
        this.addOperation = addOperation;
        return this;
    }

    public MultiReferenceWidgetBuilder removeOperation(String removeOperation) {
        this.removeOperation = removeOperation;
        return this;
    }

    public MultiReferenceWidgetBuilder reorderOperation(String reorderOperation) {
        this.reorderOperation = reorderOperation;
        return this;
    }

    public MultiReferenceWidgetDescription build() {
        MultiReferenceWidgetDescription description = PapyrusWidgetsFactory.eINSTANCE.createMultiReferenceWidgetDescription();
        description.setName(this.name);
        description.setLabelExpression(this.label);
        description.setHelpExpression(this.help);
        description.setIsEnabledExpression(this.isEnable);
        description.setOwnerExpression(this.owner);
        description.setType(this.type);
        description.setValueExpression(this.value);
        description.setCandidatesSearchScopeExpression(this.searchScope);
        description.setDropdownOptionsExpression(this.dropdownOptions);

        ClearReferenceOperation clearOperation = PapyrusWidgetsFactory.eINSTANCE.createClearReferenceOperation();
        clearOperation.getBody().add(createChangeContext(this.clearOperation));
        description.setClearOperation(clearOperation);

        CreateElementInReferenceOperation createOperation = PapyrusWidgetsFactory.eINSTANCE.createCreateElementInReferenceOperation();
        createOperation.getBody().add(createChangeContext(this.createOperation));
        description.setCreateElementOperation(createOperation);

        MultiReferenceAddOperation addOperation = PapyrusWidgetsFactory.eINSTANCE.createMultiReferenceAddOperation();
        addOperation.getBody().add(createChangeContext(this.addOperation));
        description.setAddOperation(addOperation);

        MultiReferenceRemoveOperation removeOperation = PapyrusWidgetsFactory.eINSTANCE.createMultiReferenceRemoveOperation();
        removeOperation.getBody().add(createChangeContext(this.removeOperation));
        description.setRemoveOperation(removeOperation);

        MultiReferenceReorderOperation reorderOperation = PapyrusWidgetsFactory.eINSTANCE.createMultiReferenceReorderOperation();
        reorderOperation.getBody().add(createChangeContext(this.reorderOperation));
        description.setReorderOperation(reorderOperation);

        return description;
    }

    private ChangeContext createChangeContext(String contextExp) {
        ChangeContext changeCtxt = ViewFactory.eINSTANCE.createChangeContext();
        changeCtxt.setExpression(contextExp);
        return changeCtxt;
    }
}
