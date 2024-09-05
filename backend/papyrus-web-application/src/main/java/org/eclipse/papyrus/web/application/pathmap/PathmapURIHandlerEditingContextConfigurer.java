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
package org.eclipse.papyrus.web.application.pathmap;

import java.util.Objects;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.web.application.pathmap.services.api.IStaticPathmapResourceRegistry;
import org.eclipse.papyrus.web.domain.boundedcontext.profile.repositories.IProfileRepository;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.springframework.stereotype.Service;

/**
 * Context processor in charge of installing the {@link PathmapURIHandler}.
 *
 * @author Arthur Daussy
 */
@Service
public class PathmapURIHandlerEditingContextConfigurer implements IEditingContextProcessor {
    private final IStaticPathmapResourceRegistry pathMapRegistry;

    private final IProfileRepository profileRepository;

    public PathmapURIHandlerEditingContextConfigurer(IStaticPathmapResourceRegistry pathMapRegistry, IProfileRepository profileRepository) {
        super();
        this.pathMapRegistry = Objects.requireNonNull(pathMapRegistry);
        this.profileRepository = Objects.requireNonNull(profileRepository);
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext swEditingContext) {
            ResourceSet resourceSet = swEditingContext.getDomain().getResourceSet();
            // Plug special URIHandler that handle pathmap:// uris
            resourceSet.getURIConverter().getURIHandlers().add(0, new PathmapURIHandler(this.pathMapRegistry, this.profileRepository));
        }

    }
}
