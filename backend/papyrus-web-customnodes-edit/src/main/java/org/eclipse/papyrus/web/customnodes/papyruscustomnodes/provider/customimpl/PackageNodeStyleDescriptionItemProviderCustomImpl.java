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
package org.eclipse.papyrus.web.customnodes.papyruscustomnodes.provider.customimpl;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.provider.PackageNodeStyleDescriptionItemProvider;

/**
 * Custom implementation of {@linkplain PackageNodeStyleDescriptionItemProvider} to avoid "@generated NOT".
 *
 * @author Jerome Gout
 */
public class PackageNodeStyleDescriptionItemProviderCustomImpl extends PackageNodeStyleDescriptionItemProvider {

    public PackageNodeStyleDescriptionItemProviderCustomImpl(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/PackageNodeStyle.svg"));
    }

}
