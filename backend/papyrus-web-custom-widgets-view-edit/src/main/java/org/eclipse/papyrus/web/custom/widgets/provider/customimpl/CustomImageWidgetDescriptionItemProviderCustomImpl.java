/*****************************************************************************
 * Copyright (c) 2024 CEA LIST, Obeo, Artal Technologies.
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
 *  Titouan BOUETE-GIRAUD (Artal Technologies) - Issue 210
 *****************************************************************************/
package org.eclipse.papyrus.web.custom.widgets.provider.customimpl;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.provider.CustomImageWidgetDescriptionItemProvider;

/**
 * @author tiboue
 */
public class CustomImageWidgetDescriptionItemProviderCustomImpl extends CustomImageWidgetDescriptionItemProvider {

    public CustomImageWidgetDescriptionItemProviderCustomImpl(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/primitiveradio.svg"));
    }
}
