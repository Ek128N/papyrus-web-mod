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

import java.util.Objects;

import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the custom image widget component.
 *
 * @author tiboue
 */
public class CustomImageComponentProps implements IProps {
    private final VariableManager variableManager;

    private final CustomImageDescription customImageDescription;

    public CustomImageComponentProps(VariableManager variableManager, CustomImageDescription customImageDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.customImageDescription = Objects.requireNonNull(customImageDescription);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public CustomImageDescription getCustomImageDescription() {
        return this.customImageDescription;
    }

}
