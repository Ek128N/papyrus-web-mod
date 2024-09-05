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
package org.eclipse.papyrus.web.application.uml;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.uml2.uml.util.UMLUtil.ProfileApplicationHelper;
import org.eclipse.uml2.uml.util.UMLUtil.StereotypeApplicationHelper;
import org.springframework.stereotype.Service;

/**
 * Processor that configure the {@link ResourceSet} to properly handle UML resources.
 *
 * @author Arthur Daussy
 */
@Service
public class UMLEditingContextConfigurer implements IEditingContextProcessor {

    private final Resource.Factory.Registry factoryRegistry;

    public UMLEditingContextConfigurer(Registry factoryRegistry) {
        super();
        this.factoryRegistry = factoryRegistry;
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            ResourceSet resourceSet = emfEditingContext.getDomain().getResourceSet();

            Registry ressourceFactoryRegistry = resourceSet.getResourceFactoryRegistry();
            ressourceFactoryRegistry.getContentTypeToFactoryMap().putAll(this.factoryRegistry.getContentTypeToFactoryMap());
            ressourceFactoryRegistry.getExtensionToFactoryMap().putAll(this.factoryRegistry.getExtensionToFactoryMap());
            ressourceFactoryRegistry.getProtocolToFactoryMap().putAll(this.factoryRegistry.getProtocolToFactoryMap());

            ProfileApplicationHelper.setInstance(resourceSet, new ProfileApplicationHelper());
            StereotypeApplicationHelper.setInstance(resourceSet, new StereotypeApplicationHelper());
        }

    }

}
