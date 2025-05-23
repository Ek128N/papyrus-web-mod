/*****************************************************************************
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
 *****************************************************************************/
package org.eclipse.papyrus.web.properties;

import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.papyrus.web.utils.FileViewRepresentationLoader;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.view.emf.IViewConverter;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.springframework.context.annotation.Configuration;

/**
 * Test-specific configuration which loads and registers a View which defines a Form with Primitive List widgets.
 *
 * @author Arthur Daussy
 */
@Configuration
public class PrimitiveListViewRepresentationDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {

    private final IViewConverter viewConverter;

    private final EPackage.Registry ePackagesRegistry;

    public PrimitiveListViewRepresentationDescriptionProvider(IViewConverter viewConverter, Registry ePackagesRegistry) {
        this.viewConverter = viewConverter;
        this.ePackagesRegistry = ePackagesRegistry;
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusEditingContext) {

            FileViewRepresentationLoader fileViewRepresentationLoader = new FileViewRepresentationLoader("properties/PrimitiveListe.view", this.viewConverter, this.ePackagesRegistry);
            List<IRepresentationDescription> representations = fileViewRepresentationLoader.loadRepresentations();
            for (IRepresentationDescription rep : representations) {
                siriusEditingContext.getRepresentationDescriptions().put(rep.getId(), rep);
            }

            ((EditingContext) editingContext).getViews().addAll(fileViewRepresentationLoader.getViews());
            return representations;
        }
        return List.of();
    }

}
