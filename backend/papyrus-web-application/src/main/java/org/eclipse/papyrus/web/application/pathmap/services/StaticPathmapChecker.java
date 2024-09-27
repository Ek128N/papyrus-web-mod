/*****************************************************************************
 * Copyright (c) 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.pathmap.services;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.web.application.pathmap.services.api.IStaticPathmapChecker;
import org.eclipse.papyrus.web.application.pathmap.services.api.IStaticPathmapResourceRegistry;
import org.springframework.stereotype.Service;

/**
 * Default implementation for {@link IStaticPathmapChecker}.
 *
 * @author Arthur Daussy
 */
@Service
public class StaticPathmapChecker implements IStaticPathmapChecker {

    @Override
    public boolean isPathmapResource(Resource r) {
        if (r != null) {
            URI uri = r.getURI();
            return uri != null && uri.toString().startsWith(IStaticPathmapResourceRegistry.PREFIX);
        }
        return false;
    }

}
