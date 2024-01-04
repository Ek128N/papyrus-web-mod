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

import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.papyrus.web.persistence.repositories.IProfileRepository;
import org.eclipse.papyrus.web.services.pathmap.IStaticPathmapResourceRegistry;
import org.eclipse.papyrus.web.services.uml.profile.UMLProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * This resource factory is used for the scheme "pathmap".
 *
 * @author Laurent Fasani
 */
public class PathmapResourceFactory implements Factory {

    private final IStaticPathmapResourceRegistry pathmapResourceRegistry;

    private final IProfileRepository profileRepository;

    private final Logger logger = LoggerFactory.getLogger(PathmapResourceFactory.class);

    private Registry factoryRegistryDelegate;

    public PathmapResourceFactory(IStaticPathmapResourceRegistry pathmapResourceRegistry, Registry delegate, IProfileRepository profileRepository) {
        this.pathmapResourceRegistry = pathmapResourceRegistry;
        this.factoryRegistryDelegate = delegate;
        this.profileRepository = profileRepository;
    }

    @Override
    public Resource createResource(URI resourceUri) {
        Optional<Resource> resourceOpt = Optional.empty();

        if (resourceUri.toString().startsWith(UMLProfileService.WEB_DYNAMIC_PROFILE_RESOURCE_PREFIX)) {
            resourceOpt = this.handleProfileURI(resourceUri);
        } else {
            Optional<ClassPathResource> classPathResource = Optional.ofNullable(this.pathmapResourceRegistry.getClassPathResource(resourceUri));

            if (classPathResource.isPresent()) {
                String fileName = classPathResource.get().getFilename();

                String fileExtension = this.getFileExtension(fileName);
                if (fileExtension != null && !fileExtension.isBlank()) {
                    resourceOpt = this.createResource(resourceUri, fileExtension);
                }
            }
        }
        return resourceOpt.orElse(null);
    }

    private Optional<Resource> handleProfileURI(URI resourceUri) {
        try {
            UUID uuid = UUID.fromString(resourceUri.lastSegment());
            boolean exists = this.profileRepository.existsById(uuid);
            if (exists) {
                return this.createResource(resourceUri, "uml");
            } else {
                this.logger.warn("The static or dynamic resource {} does not exists : {}", resourceUri);
            }
        } catch (IllegalArgumentException e) {
            this.logger.warn("Invalid URI format {}", resourceUri);
        }
        return Optional.empty();
    }

    private String getFileExtension(String fileName) {
        String[] parts = fileName.split("\\.");
        if (parts.length > 0) {
            return parts[parts.length - 1];
        }
        return null;
    }

    private Optional<Resource> createResource(URI resourceUri, String fileExtension) {
        Resource.Factory factory = (Factory) this.factoryRegistryDelegate.getExtensionToFactoryMap().get(fileExtension);
        Resource resource = factory.createResource(resourceUri);
        return Optional.of(resource);
    }
}
