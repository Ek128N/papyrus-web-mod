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
package org.eclipse.papyrus.web.application.configuration;

import java.util.Map;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.papyrus.web.persistence.repositories.IProfileRepository;
import org.eclipse.papyrus.web.services.editingcontext.PathmapResourceFactory;
import org.eclipse.papyrus.web.services.pathmap.IStaticPathmapResourceRegistry;
import org.eclipse.uml2.types.TypesPackage;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.edit.providers.UMLItemProviderAdapterFactory;
import org.eclipse.uml2.uml.internal.resource.UMLResourceFactoryImpl;
import org.eclipse.uml2.uml.profile.standard.StandardPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of the EMF support for Papyrus Web.
 *
 * @author lfasani
 */
@Configuration
public class UMLEMFConfiguration {

    private static final String PROTOCOL_PATHMAP = "pathmap";

    @Bean
    public AdapterFactory umlAdapterFactory() {
        return new UMLItemProviderAdapterFactory();
    }

    @Bean
    public AdapterFactory ecoreAdapterFactory() {
        return new EcoreItemProviderAdapterFactory();
    }

    @Bean
    public EPackage ecoreEPackage() {
        return EcorePackage.eINSTANCE;
    }

    @Bean
    public EPackage umlEPackage() {
        return UMLPackage.eINSTANCE;
    }

    @Bean
    public EPackage standardEPackage() {
        return StandardPackage.eINSTANCE;
    }

    @Bean
    public EPackage typesEPackage() {
        return TypesPackage.eINSTANCE;
    }

    @Bean
    public Resource.Factory.Registry factoryRegistry(IStaticPathmapResourceRegistry pathmapResourceRegistry, IProfileRepository profileRepository) {
        Registry globalFactoryRegistryInstance = Resource.Factory.Registry.INSTANCE;

        // initialize the registry from the global
        Registry factoryRegistry = new ResourceFactoryRegistryImpl();
        Map<String, Object> protocolToFactoryMap = factoryRegistry.getProtocolToFactoryMap();
        globalFactoryRegistryInstance.getProtocolToFactoryMap().forEach((key, value) -> protocolToFactoryMap.put(key, value));
        Map<String, Object> extensionToFactoryMap = factoryRegistry.getExtensionToFactoryMap();
        globalFactoryRegistryInstance.getExtensionToFactoryMap().forEach((key, value) -> extensionToFactoryMap.put(key, value));
        Map<String, Object> contentTypeToFactoryMap = factoryRegistry.getContentTypeToFactoryMap();
        globalFactoryRegistryInstance.getContentTypeToFactoryMap().forEach((key, value) -> contentTypeToFactoryMap.put(key, value));

        // Add pathmap scheme factory
        Resource.Factory pathmapFactory = new PathmapResourceFactory(pathmapResourceRegistry, globalFactoryRegistryInstance, profileRepository);
        factoryRegistry.getProtocolToFactoryMap().put(PROTOCOL_PATHMAP, pathmapFactory);

        // Add factory associated to uml extension
        factoryRegistry.getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, new UMLResourceFactoryImpl());

        return factoryRegistry;
    }
}
