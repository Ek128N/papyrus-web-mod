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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.emf.ecore.resource.impl.URIHandlerImpl;
import org.springframework.core.io.ClassPathResource;

/**
 * {@link URIHandler} that is able to load a Resource from a uri with shape "classpath://$pathToFileInJar".
 *
 * @author Arthur Daussy
 */
public class ClassPathResourceURIHandler extends URIHandlerImpl {

    public static final String CLASSPATH = "classpath";

    public static URI createURI(String path) {
        return URI.createGenericURI(CLASSPATH, path, null);
    }

    @Override
    public boolean canHandle(URI uri) {
        return CLASSPATH.equals(uri.scheme());
    }

    @Override
    public InputStream createInputStream(URI uri, Map<?, ?> options) throws IOException {
        String path = uri.opaquePart();
        ClassPathResource resource = new ClassPathResource(path);
        return resource.getInputStream();
    }

    @Override
    public OutputStream createOutputStream(URI uri, Map<?, ?> options) throws IOException {
        return new OutputStream() {

            @Override
            public void write(int b) throws IOException {
                // Do nothing this resource can be edited
            }
        };
    }

    @Override
    public void delete(URI uri, Map<?, ?> options) throws IOException {
        // Do nothing
    }

    @Override
    public boolean exists(URI uri, Map<?, ?> options) {
        ClassPathResource resource = new ClassPathResource(uri.path());
        return resource.exists();
    }

}
