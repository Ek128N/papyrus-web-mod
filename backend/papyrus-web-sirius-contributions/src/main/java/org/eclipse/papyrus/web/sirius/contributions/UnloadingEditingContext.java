/*******************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
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
 *******************************************************************************/
package org.eclipse.papyrus.web.sirius.contributions;

import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.representations.IRepresentationDescription;

/**
 * Special EditingContext that unload the Resources on dispose.
 *
 * @author Arthur Daussy
 */
public class UnloadingEditingContext extends EditingContext {

    public UnloadingEditingContext(String id, AdapterFactoryEditingDomain editingDomain, Map<String, IRepresentationDescription> representationDescriptions) {
        super(id, editingDomain, representationDescriptions);
    }

    @Override
    public void dispose() {
        this.getDomain().getResourceSet().getResources().forEach(Resource::unload);
    }

}
