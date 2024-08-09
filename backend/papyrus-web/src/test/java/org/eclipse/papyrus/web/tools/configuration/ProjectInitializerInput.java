/*****************************************************************************
 * Copyright (c) 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.tools.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;

/**
 * Input used to initialize the initialize a project. The project should already be created and contain an existing
 * resource. It creates a root element and a diagram under the root element. Then is fill the project using a given
 * {@link BiConsumer}.
 *
 * @author Arthur Daussy
 */
public class ProjectInitializerInput implements IInput {

    private UUID id;

    private Map<String, IEditingContext> editingContexts = new HashMap<>();

    public ProjectInitializerInput(UUID id) {
        this.id = id;
    }

    public IPayload cache(IEditingContext editingContext) {
        this.editingContexts.put(editingContext.getId(), editingContext);
        return new SuccessPayload(this.id);
    }

    public Map<String, IEditingContext> getEditingContextCache() {
        return this.editingContexts;
    }

    @Override
    public UUID id() {
        return this.id;
    }

}
