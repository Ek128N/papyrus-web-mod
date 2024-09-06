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

import java.util.Iterator;
import java.util.Objects;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.uml.domain.services.EMFUtils;
import org.eclipse.papyrus.web.application.templates.service.api.IUMLProjectCheckerService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.emf.services.EditingContextCrossReferenceAdapter;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.springframework.stereotype.Service;

/**
 * Process in charge of unpluging the default {@link EditingContextCrossReferenceAdapter} used in Sirius Web and use
 * custom one specially designed for Papyrus. In Papyrus web we want to completely handle the deletion of an element. We
 * dot not want the method
 * org.eclipse.sirius.components.emf.services.EditingContextCrossReferenceAdapter.clearReferencesTo(EObject)
 *
 * @author Arthur Daussy
 */
@Service
public class UMLCrossReferenceAdapterEditingContextConfigurer implements IEditingContextProcessor {
    private final IUMLProjectCheckerService umlChecker;

    public UMLCrossReferenceAdapterEditingContextConfigurer(IUMLProjectCheckerService umlChecker) {
        super();
        this.umlChecker = Objects.requireNonNull(umlChecker);
    }

    @Override
    public void postProcess(IEditingContext editingContext) {
        // DO NOT add the default EditingContextCrossReferenceAdapter for UML resource and UML Element.
        // Everything should be done within the UML Domain Service using the CacheAdapter
        // The ECrossReferenceAdapter must be set after the resource loading because it needs to resolve proxies in case
        // of inter-resources references
        if (editingContext instanceof IEMFEditingContext emfEditingContext && this.umlChecker.isPapyrusProject(editingContext.getId())) {
            ResourceSet resourceSet = emfEditingContext.getDomain().getResourceSet();
            // Remove existing cross referencer since
            EMFUtils.eAllContentSteamWithSelf(resourceSet)
                    .forEach(this::removeDefaultEditingContextAdapter);
            // Add our cross ref
            EList<Adapter> eAdapters = resourceSet.eAdapters();
            if (eAdapters.stream().noneMatch(a -> a instanceof NonUMLEditingContextCrossReferenceAdapter)) {
                eAdapters.add(new NonUMLEditingContextCrossReferenceAdapter());
            }
        }
    }

    private void removeDefaultEditingContextAdapter(Notifier o) {
        Iterator<Adapter> adapterIterator = o.eAdapters().iterator();
        while (adapterIterator.hasNext()) {
            Adapter next = adapterIterator.next();
            if (next.getClass() == EditingContextCrossReferenceAdapter.class) {
                adapterIterator.remove();
            }
        }
    }
}
