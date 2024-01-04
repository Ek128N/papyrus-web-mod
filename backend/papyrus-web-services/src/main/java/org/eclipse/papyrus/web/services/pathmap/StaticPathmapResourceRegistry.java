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

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.springframework.core.io.ClassPathResource;

/**
 * Registry used to associate an URI, which scheme is "pathmap", to a mean to load the resource.
 *
 * @author lfasani
 */
public class StaticPathmapResourceRegistry implements IStaticPathmapResourceRegistry {
    private static final String PROTOCOL_PATHMAP = "pathmap";

    private static final String PREFIX = PROTOCOL_PATHMAP + "://";

    private final Map<URI, ClassPathResource> resourceUriToClassPath = new LinkedHashMap<>();

    @Override
    public ClassPathResource getClassPathResource(URI resourceURI) {
        return this.resourceUriToClassPath.get(resourceURI);
    }

    public void add(String uriOpaquePart, String localPath) {
        this.resourceUriToClassPath.put(URI.createURI(PREFIX + uriOpaquePart), new ClassPathResource(localPath));
    }
}
