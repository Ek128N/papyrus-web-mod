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

import java.util.Objects;

import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the primitive list widget component.
 *
 * @author Arthur Daussy
 */
public class PrimitiveListWidgetComponentProps implements IProps {
    private final VariableManager variableManager;

    private final PrimitiveListWidgetDescription widgetDescription;

    public PrimitiveListWidgetComponentProps(VariableManager variableManager, PrimitiveListWidgetDescription widgetDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.widgetDescription = Objects.requireNonNull(widgetDescription);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public PrimitiveListWidgetDescription getPrimitiveWidgetDescription() {
        return this.widgetDescription;
    }
}
