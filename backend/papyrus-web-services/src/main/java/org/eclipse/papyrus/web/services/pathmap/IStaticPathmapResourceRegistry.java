/*******************************************************************************
 * Copyright (c) 2022, 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.services.pathmap;

import org.eclipse.emf.common.util.URI;
import org.springframework.core.io.ClassPathResource;

/**
 * Registry used to associate an URI, which scheme is "pathmap", to a mean to load the resource.
 *
 * @author Laurent Fasani
 */
public interface IStaticPathmapResourceRegistry {

    /**
     * Returns a {@link ClassPathResource} that allows to retrieve the resource of the given {@link URI}.
     */
    ClassPathResource getClassPathResource(URI resourceURI);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author Arthur Daussy
     */
    class NoOp implements IStaticPathmapResourceRegistry {

        @Override
        public ClassPathResource getClassPathResource(URI resourceURI) {
            return null;
        }
    }
}
