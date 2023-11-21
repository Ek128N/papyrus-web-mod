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
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceSetOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceUnsetOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsFactory;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.ViewFactory;

@SuppressWarnings("checkstyle:HiddenField")
public class MonoReferenceWidgetBuilder {
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

    private String setOperation;

    private String unsetOperation;

    public MonoReferenceWidgetBuilder name(String name) {
        this.name = name;
        return this;
    }

    public MonoReferenceWidgetBuilder label(String label) {
        this.label = label;
        return this;
    }

    public MonoReferenceWidgetBuilder isEnable(String isEnable) {
        this.isEnable = isEnable;
        return this;
    }

    public MonoReferenceWidgetBuilder help(String help) {
        this.help = help;
        return this;
    }

    public MonoReferenceWidgetBuilder owner(String owner) {
        this.owner = owner;
        return this;
    }

    public MonoReferenceWidgetBuilder type(String type) {
        this.type = type;
        return this;
    }

    public MonoReferenceWidgetBuilder value(String value) {
        this.value = value;
        return this;
    }

    public MonoReferenceWidgetBuilder searchScope(String searchScope) {
        this.searchScope = searchScope;
        return this;
    }

    public MonoReferenceWidgetBuilder dropdownOptions(String dropdownOptions) {
        this.dropdownOptions = dropdownOptions;
        return this;
    }

    public MonoReferenceWidgetBuilder clearOperation(String clearOperation) {
        this.clearOperation = clearOperation;
        return this;
    }

    public MonoReferenceWidgetBuilder createOperation(String createOperation) {
        this.createOperation = createOperation;
        return this;
    }

    public MonoReferenceWidgetBuilder setOperation(String setOperation) {
        this.setOperation = setOperation;
        return this;
    }

    public MonoReferenceWidgetBuilder unsetOperation(String unsetOperation) {
        this.unsetOperation = unsetOperation;
        return this;
    }

    public MonoReferenceWidgetDescription build() {
        MonoReferenceWidgetDescription description = PapyrusWidgetsFactory.eINSTANCE.createMonoReferenceWidgetDescription();
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

        MonoReferenceSetOperation setOperation = PapyrusWidgetsFactory.eINSTANCE.createMonoReferenceSetOperation();
        setOperation.getBody().add(createChangeContext(this.setOperation));
        description.setSetOperation(setOperation);

        MonoReferenceUnsetOperation unsetOperation = PapyrusWidgetsFactory.eINSTANCE.createMonoReferenceUnsetOperation();
        unsetOperation.getBody().add(createChangeContext(this.unsetOperation));
        description.setUnsetOperation(unsetOperation);

        return description;
    }

    private ChangeContext createChangeContext(String contextExp) {
        ChangeContext changeCtxt = ViewFactory.eINSTANCE.createChangeContext();
        changeCtxt.setExpression(contextExp);
        return changeCtxt;
    }
}
