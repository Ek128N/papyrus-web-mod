/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.customnodes.papyruscustomnodes.provider.customimpl;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.provider.InnerFlagNodeStyleDescriptionItemProvider;

/**
 * Custom implementation of {@linkplain InnerFlagNodeStyleDescriptionItemProvider} to avoid "@generated NOT".
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class InnerFlagNodeStyleDescriptionItemProviderCustomImpl extends InnerFlagNodeStyleDescriptionItemProvider {

    public InnerFlagNodeStyleDescriptionItemProviderCustomImpl(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/InnerFlagNodeStyle.svg"));
    }

}
