/*******************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.services.editingcontext;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.papyrus.web.persistence.repositories.IProfileRepository;
import org.eclipse.papyrus.web.services.pathmap.IStaticPathmapResourceRegistry;
import org.eclipse.papyrus.web.services.projects.api.IEditingContextMetadataProvider;
import org.eclipse.sirius.components.emf.services.IEditingContextEPackageService;
import org.eclipse.uml2.uml.UMLPlugin;
import org.eclipse.uml2.uml.util.UMLUtil.ProfileApplicationHelper;
import org.eclipse.uml2.uml.util.UMLUtil.StereotypeApplicationHelper;
import org.springframework.stereotype.Service;

/**
 * This class is used to create the editing domain used as editing context.</br>
 * It instantiates the ResourceSet with the right configuration.
 *
 * @author lfasani
 */
@Service
public class EditingDomainFactoryService implements IEditingDomainFactoryService {

    private final IEditingContextEPackageService editingContextEPackageService;

    private final ComposedAdapterFactory composedAdapterFactory;

    private final EPackage.Registry globalEPackageRegistry;

    private final Optional<Registry> resourceFactoryRegistryOpt;

    private final Optional<Registry> optionalResourceFactoryRegistry;

    private IStaticPathmapResourceRegistry pathMapRegistry;

    private final IProfileRepository profileRepository;

    private IEditingContextMetadataProvider editingContextMetadataProvider;

    // CHECKSTYLE:OFF For now
    public EditingDomainFactoryService(IEditingContextEPackageService editingContextEPackageService, IEditingContextMetadataProvider editingContextMetadataProvider,
            ComposedAdapterFactory composedAdapterFactory, EPackage.Registry globalEPackageRegistry, Optional<Resource.Factory.Registry> resourceFactoryRegistryOpt,
            IStaticPathmapResourceRegistry pathMapRegistry, IProfileRepository profileRepository, Optional<Resource.Factory.Registry> optionalResourceFactoryRegistry) {
        this.pathMapRegistry = Objects.requireNonNull(pathMapRegistry);
        this.profileRepository = Objects.requireNonNull(profileRepository);
        this.editingContextEPackageService = Objects.requireNonNull(editingContextEPackageService);
        this.editingContextMetadataProvider = Objects.requireNonNull(editingContextMetadataProvider);
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.globalEPackageRegistry = Objects.requireNonNull(globalEPackageRegistry);
        this.resourceFactoryRegistryOpt = resourceFactoryRegistryOpt;
        this.optionalResourceFactoryRegistry = optionalResourceFactoryRegistry;
    }
    // CHECKSTYLE:ON For now

    @Override
    public AdapterFactoryEditingDomain createEditingDomain(String editingContextId) {
        AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(this.composedAdapterFactory, new BasicCommandStack());
        ResourceSet resourceSet = editingDomain.getResourceSet();
        // Remove for now since we need to visualize the view model.
        // For final product this could be reintegrated
        // var isStudioProjectNature =
        // this.editingContextMetadataProvider.getMetadata(editingContextId).natures().stream().map(Nature::natureId)
        // .anyMatch("siriusComponents://nature?kind=studio"::equals);

        EPackageRegistryImpl ePackageRegistry = new EPackageRegistryImpl();
        List<EPackage> additionalEPackages = this.editingContextEPackageService.getEPackages(editingContextId);
        Stream.concat(this.findGlobalEPackages(), additionalEPackages.stream())
                // .filter(ePackage -> isStudioProjectNature || !List.of(DomainPackage.eNS_URI,
                // ViewPackage.eNS_URI).contains(ePackage.getNsURI()))
                .forEach(ePackage -> ePackageRegistry.put(ePackage.getNsURI(), ePackage));

        resourceSet.setPackageRegistry(ePackageRegistry);

        // Plug special URIHandler that handle pathmap:// uris
        resourceSet.getURIConverter().getURIHandlers().add(0, new PathmapURIHandler(this.pathMapRegistry, this.profileRepository));

        // Initialize the ResourceSet in a way UML expected it to be (see
        // org.eclipse.uml2.uml.resources.util.UMLResourcesUtil.init(resourceSet))
        // We do not use org.eclipse.uml2.uml.resources.util.UMLResourcesUtil.initURIConverterURIMap(Map<URI, URI>)
        // since is use platform uri scheme

        org.eclipse.uml2.uml.resources.util.UMLResourcesUtil.initPackageRegistry(EPackage.Registry.INSTANCE);
        org.eclipse.uml2.uml.resources.util.UMLResourcesUtil.initEPackageNsURIToProfileLocationMap(UMLPlugin.getEPackageNsURIToProfileLocationMap());
        org.eclipse.uml2.uml.resources.util.UMLResourcesUtil.initContentHandlerRegistry(ContentHandler.Registry.INSTANCE);
        org.eclipse.uml2.uml.resources.util.UMLResourcesUtil.initResourceFactoryRegistry(Resource.Factory.Registry.INSTANCE);

        ProfileApplicationHelper.setInstance(resourceSet, new ProfileApplicationHelper());
        StereotypeApplicationHelper.setInstance(resourceSet, new StereotypeApplicationHelper());

        if (this.resourceFactoryRegistryOpt.isPresent()) {
            resourceSet.setResourceFactoryRegistry(this.resourceFactoryRegistryOpt.get());
        }

        ProfileApplicationHelper.setInstance(resourceSet, new ProfileApplicationHelper());
        StereotypeApplicationHelper.setInstance(resourceSet, new StereotypeApplicationHelper());

        this.optionalResourceFactoryRegistry.ifPresent(resourceSet::setResourceFactoryRegistry);

        return editingDomain;
    }

    private Stream<EPackage> findGlobalEPackages() {
        return this.globalEPackageRegistry.values().stream().filter(EPackage.class::isInstance).map(EPackage.class::cast);
    }
}
