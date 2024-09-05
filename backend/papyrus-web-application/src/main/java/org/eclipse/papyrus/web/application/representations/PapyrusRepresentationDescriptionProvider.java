/*****************************************************************************
 * Copyright (c) 2022, 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.representations;

import java.util.List;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.springframework.context.annotation.Configuration;

/**
 * Registers a diagram definition from a statically loaded View model.
 *
 * @author Arthur Daussy
 */
@Configuration
public class PapyrusRepresentationDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {

    private PapyrusRepresentationDescriptionRegistry viewRegistry;

    public PapyrusRepresentationDescriptionProvider(PapyrusRepresentationDescriptionRegistry viewRegistry) {
        this.viewRegistry = viewRegistry;
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {
        return this.viewRegistry.getApiDiagrams();
    }

}
