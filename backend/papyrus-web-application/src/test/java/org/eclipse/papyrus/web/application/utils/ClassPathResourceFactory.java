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
package org.eclipse.papyrus.web.application.utils;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;

/**
 * Resource Factory to be used for {@link ClassPathResourceURIHandler} URIs.
 *
 * @author Arthur Daussy
 */
public class ClassPathResourceFactory implements Factory {

    private final Resource.Factory.Registry delegate;

    public ClassPathResourceFactory(Resource.Factory.Registry delegate) {
        super();
        this.delegate = delegate;
    }

    @Override
    public Resource createResource(URI uri) {
        String opaquePart = uri.opaquePart();
        if (opaquePart != null) {
            String fileExtension = this.getFileExtension(opaquePart);
            return this.createResource(uri, fileExtension);
        }
        return null;
    }

    private Resource createResource(URI uri, String fileExtension) {
        final Resource resource;
        if ("json".equals(fileExtension)) {
            resource = new JSONResourceFactory().createResource(uri);
        } else {
            Object factory = this.delegate.getExtensionToFactoryMap().get(fileExtension);
            if (factory instanceof Factory) {
                resource = ((Factory) factory).createResource(uri);
            } else {
                resource = null;
            }
        }
        return resource;
    }

    private String getFileExtension(String fileName) {
        String[] parts = fileName.split("\\.");
        if (parts.length > 0) {
            return parts[parts.length - 1];
        }
        return null;
    }
}
