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
package org.eclipse.papyrus.web.application.profile;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.papyrus.web.application.profile.services.UMLProfileService;
import org.eclipse.papyrus.web.application.profile.services.api.IUMLProfileProvider;
import org.eclipse.papyrus.web.domain.boundedcontext.profile.service.api.IProfileCreationService;
import org.eclipse.papyrus.web.domain.boundedcontext.profile.service.api.IProfileDeletionService;
import org.eclipse.papyrus.web.domain.boundedcontext.profile.service.api.IProfileSearchService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The configuration used to provide the services related to the UML profiles.
 *
 * @author lfasani
 */
@Configuration
public class UMLProfileConfiguration {
    @Bean
    public UMLProfileService profileDescriptionService(List<IUMLProfileProvider> umlProfileDescriptionProviders, IObjectService objectService, IProfileSearchService profilesearchService,
            IProfileDeletionService profileDeletionService,
            IProfileCreationService profileCreationService, Registry factoryRegistry) {
        UMLProfileMetadataRegistry registry = new UMLProfileMetadataRegistry();
        umlProfileDescriptionProviders.stream().flatMap(uMLProfileProvider -> {
            return uMLProfileProvider.getUMLProfiles().stream();
        }).forEach(umlProfile -> {
            registry.add(umlProfile);
        });
        return new UMLProfileService(registry, objectService, profilesearchService, profileDeletionService, profileCreationService, factoryRegistry);
    }
}
