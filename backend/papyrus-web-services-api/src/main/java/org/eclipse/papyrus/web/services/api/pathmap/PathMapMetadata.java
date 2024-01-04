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
package org.eclipse.papyrus.web.services.api.pathmap;

/**
 * The basic information used for a Pathmap registration.
 *
 * @author Arthur Daussy
 */
public class PathMapMetadata {

    /**
     * Opaque part of the pathmap URI (ex: pathmap://<opaquePart>#<fragment>)
     */
    private final String resourceURIOpaquePart;

    /**
     * Local path (path in the classpath) of the targetted file
     */
    private final String localPath;

    public PathMapMetadata(String resourceURI, String localPath) {
        super();
        this.resourceURIOpaquePart = resourceURI;
        this.localPath = localPath;
    }

    public String getResourceURIOpaquePart() {
        return this.resourceURIOpaquePart;
    }

    public String getLocalPath() {
        return this.localPath;
    }

}
