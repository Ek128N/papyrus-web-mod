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
package org.eclipse.papyrus.web.services.editingcontext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.URIHandlerImpl;
import org.eclipse.papyrus.web.persistence.entities.ProfileResourceEntity;
import org.eclipse.papyrus.web.persistence.repositories.IProfileRepository;
import org.eclipse.papyrus.web.services.pathmap.IStaticPathmapResourceRegistry;
import org.eclipse.papyrus.web.services.uml.profile.UMLProfileService;
import org.springframework.core.io.ClassPathResource;

/**
 * URIHandler in charge of handling URIs of type "pathmap://..".
 *
 * @author Arthur Daussy
 */
public class PathmapURIHandler extends URIHandlerImpl {

    private static final Pattern UUID_REGEX = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    private final IStaticPathmapResourceRegistry registry;

    private final IProfileRepository profileRepository;

    public PathmapURIHandler(IStaticPathmapResourceRegistry registry, IProfileRepository profileRepository) {
        super();
        this.registry = Objects.requireNonNull(registry);
        this.profileRepository = Objects.requireNonNull(profileRepository);
    }

    @Override
    public boolean canHandle(URI uri) {
        return this.exists(uri, null);
    }

    @Override
    public InputStream createInputStream(URI uri, Map<?, ?> options) throws IOException {
        InputStream inputStream = null;
        ClassPathResource classPathResource = this.registry.getClassPathResource(uri);
        if (classPathResource != null) {
            inputStream = classPathResource.getInputStream();
        } else {
            try {
                ProfileResourceEntity profileResourceEntity = this.profileRepository.findById(UUID.fromString(uri.lastSegment())).get();
                inputStream = new ByteArrayInputStream(profileResourceEntity.getContent().getBytes());
            } catch (NoSuchElementException exception) {
                throw new Resource.IOWrappedException(exception);
            }
        }
        return inputStream;
    }

    @Override
    public OutputStream createOutputStream(URI uri, Map<?, ?> options) throws IOException {
        return new OutputStream() {

            @Override
            public void write(int b) throws IOException {
                // Do nothing this resource can be edite
            }
        };
    }

    @Override
    public void delete(URI uri, Map<?, ?> options) throws IOException {
        // Do nothing
    }

    @Override
    public boolean exists(URI uri, Map<?, ?> options) {
        boolean exists = false;
        if (uri.toString().startsWith(UMLProfileService.WEB_DYNAMIC_PROFILE_RESOURCE_PREFIX)) {
            exists = this.profileResourceExist(uri);
        } else {
            exists = this.registry.getClassPathResource(uri) != null;
        }
        return exists;
    }

    private boolean profileResourceExist(URI uri) {
        boolean exists;
        try {
            // In order to get the internationalized label, UML searches for a properties file located next to the
            // targeted resource
            // Consequently it is necessary to catch the case where it is not a valid UUID.
            String lastSegment = uri.lastSegment();
            if (UUID_REGEX.matcher(lastSegment).matches()) {
                exists = this.profileRepository.existsById(UUID.fromString(uri.lastSegment()));
            } else {
                exists = false;
            }
        } catch (IllegalArgumentException e) {
            exists = false;
        }
        return exists;
    }

}
