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
package org.eclipse.papyrus.web.services.editingcontext;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.springframework.stereotype.Service;

/**
 * This class forces the resolution of all contained EProxies just after loading a resource. This a partial workaround
 * of bug: https://github.com/eclipse-sirius/sirius-web/issues/1863
 *
 * @author Arthur Daussy
 */
@Service
public class SelfResolvingAdapterEditingContextConfigurer implements IEditingContextProcessor {

    @Override
    public void preProcess(IEditingContext editingContext) {
        // SelfPreResolvingProxyAdapter
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            // Workaround for bug: https://github.com/eclipse-sirius/sirius-web/issues/1863
            EList<Adapter> eAdapters = emfEditingContext.getDomain().getResourceSet().eAdapters();
            if (eAdapters.stream().noneMatch(a -> a instanceof SelfPreResolvingProxyAdapter)) {
                eAdapters.add(new SelfPreResolvingProxyAdapter());
            }
        }
    }

    /**
     * Workaround for bug: https://github.com/eclipse-sirius/sirius-web/issues/1863 .
     *
     * @author Arthur Daussy
     */
    private final class SelfPreResolvingProxyAdapter extends EContentAdapter {
        @Override
        public void notifyChanged(Notification notification) {
            super.notifyChanged(notification);
            if (notification.getNotifier() instanceof Resource resource //
                    && notification.getFeatureID(Resource.class) == Resource.RESOURCE__IS_LOADED//
                    && notification.getEventType() == Notification.SET //
                    && notification.getNewBooleanValue() /* Only during loading */) {
                EcoreUtil.resolveAll(resource);
            }
        }

        @Override
        protected void selfAdapt(Notification notification) {

            // Only adapt on ResourceSet and Resources
            Object notifier = notification.getNotifier();
            if (notifier instanceof ResourceSet) {
                if (notification.getFeatureID(ResourceSet.class) == ResourceSet.RESOURCE_SET__RESOURCES) {
                    this.handleContainment(notification);
                }
            }

        }
    }
}
