/*****************************************************************************
 * Copyright (c) 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.web.application.readonly;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.web.application.templates.service.api.IUMLProjectCheckerService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.springframework.stereotype.Service;

/**
 * Set all required adapters to install the {@link ReadOnlyAdapter} on all read-only notifiers.
 *
 * @author Arthur Daussy
 */
@Service
public class ReadOnlyAdapterInstaller implements IEditingContextProcessor {

    private final IUMLProjectCheckerService umlChecker;

    public ReadOnlyAdapterInstaller(IUMLProjectCheckerService umlChecker) {
        super();
        this.umlChecker = umlChecker;
    }

    @Override
    public void postProcess(IEditingContext editingContext) {
        if (editingContext instanceof IEMFEditingContext emfEditingContext && this.umlChecker.isPapyrusProject(editingContext.getId())) {
            ResourceSet resourceSet = emfEditingContext.getDomain().getResourceSet();
            ReadOnlyAdapter roAdapter = new ReadOnlyAdapter();
            resourceSet.getResources().stream().filter(ReadOnlyAdapterInstaller::isPathmapResource).forEach(r -> r.eAdapters().add(roAdapter));
            resourceSet.eAdapters().add(new ReadOnlyAdapterResourceSetInstaller(roAdapter));

        }
    }

    private static boolean isPathmapResource(Resource r) {
        return r.getURI().toString().startsWith("pathmap://");
    }

    /**
     * Adapter in charge of adding {@link ReadOnlyAdapter} on Read only resources.
     */
    private static final class ReadOnlyAdapterResourceSetInstaller extends AdapterImpl {

        private final ReadOnlyAdapter adapter;

        /**
         * Simple constructor.
         *
         * @param adapter
         *            the adapter to install on read only resource
         */
        private ReadOnlyAdapterResourceSetInstaller(ReadOnlyAdapter adapter) {
            super();
            this.adapter = adapter;
        }

        @Override
        public void notifyChanged(Notification msg) {
            if (msg.getFeatureID(ResourceSet.class) == ResourceSet.RESOURCE_SET__RESOURCES && msg.getNewValue() instanceof Resource newResource
                    && ReadOnlyAdapterInstaller.isPathmapResource(newResource)) {
                newResource.eAdapters().add(this.adapter);
            }
        }
    }

}
