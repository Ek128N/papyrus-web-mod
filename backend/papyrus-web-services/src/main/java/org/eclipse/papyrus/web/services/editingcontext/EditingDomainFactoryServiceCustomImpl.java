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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
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
import org.eclipse.papyrus.web.sirius.contributions.ServiceOverride;
import org.eclipse.sirius.components.domain.DomainPackage;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IEditingContextEPackageService;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.adapters.DiagramColorAdapter;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.adapters.FormColorAdapter;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.services.api.projects.Nature;
import org.eclipse.sirius.web.services.editingcontext.api.IEditingDomainFactoryService;
import org.eclipse.sirius.web.services.projects.api.IEditingContextMetadataProvider;
import org.eclipse.uml2.uml.UMLPlugin;
import org.eclipse.uml2.uml.util.UMLUtil.ProfileApplicationHelper;
import org.eclipse.uml2.uml.util.UMLUtil.StereotypeApplicationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * This class is used to create the editing domain used as editing context.</br>
 * It instantiates the ResourceSet with the right configuration.
 *
 * @author lfasani
 */
@ServiceOverride(org.eclipse.sirius.web.services.editingcontext.EditingDomainFactoryService.class)
public class EditingDomainFactoryServiceCustomImpl implements IEditingDomainFactoryService {

    private final Logger logger = LoggerFactory.getLogger(EditingDomainFactoryServiceCustomImpl.class);

    private final IEditingContextEPackageService editingContextEPackageService;

    private final IEditingContextMetadataProvider editingContextMetadataProvider;

    private final ComposedAdapterFactory composedAdapterFactory;

    private final EPackage.Registry globalEPackageRegistry;

    private final Optional<Registry> resourceFactoryRegistryOpt;

    private final Optional<Registry> optionalResourceFactoryRegistry;

    private IStaticPathmapResourceRegistry pathMapRegistry;

    private final IProfileRepository profileRepository;

    // CHECKSTYLE:OFF
    public EditingDomainFactoryServiceCustomImpl(IEditingContextEPackageService editingContextEPackageService, IEditingContextMetadataProvider editingContextMetadataProvider,
            ComposedAdapterFactory composedAdapterFactory, EPackage.Registry globalEPackageRegistry, Optional<Resource.Factory.Registry> resourceFactoryRegistryOpt,
            IStaticPathmapResourceRegistry pathMapRegistry, IProfileRepository profileRepository, Optional<Resource.Factory.Registry> optionalResourceFactoryRegistry) {
        this.editingContextMetadataProvider = editingContextMetadataProvider;
        this.pathMapRegistry = Objects.requireNonNull(pathMapRegistry);
        this.profileRepository = Objects.requireNonNull(profileRepository);
        this.editingContextEPackageService = Objects.requireNonNull(editingContextEPackageService);
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.globalEPackageRegistry = Objects.requireNonNull(globalEPackageRegistry);
        this.resourceFactoryRegistryOpt = resourceFactoryRegistryOpt;
        this.optionalResourceFactoryRegistry = optionalResourceFactoryRegistry;
    }
    // CHECKSTYLE:ON

    @Override
    public AdapterFactoryEditingDomain createEditingDomain(String editingContextId) {
        AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(this.composedAdapterFactory, new BasicCommandStack());
        ResourceSet resourceSet = editingDomain.getResourceSet();

        // Workaround regarding the regression on EMFJSon 2.3.6 that now uses the option
        // JsonResource.OPTION_EXTENDED_META_DATA for metamodel migration
        // See commit https://github.com/eclipse-sirius/sirius-emf-json/commit/25da059df31534ce8cdbebf3ced7243951bb9a1f
        // resourceSet.getLoadOptions().put(JsonResource.OPTION_EXTENDED_META_DATA, new
        // BasicExtendedMetaData(resourceSet.getPackageRegistry()));
        resourceSet.getLoadOptions().put(JsonResource.OPTION_SCHEMA_LOCATION, true);

        var isStudioProjectNature = this.editingContextMetadataProvider.getMetadata(editingContextId).natures().stream().map(Nature::natureId)
                .anyMatch("siriusComponents://nature?kind=studio"::equals);

        EPackageRegistryImpl ePackageRegistry = new EPackageRegistryImpl();
        List<EPackage> additionalEPackages = this.editingContextEPackageService.getEPackages(editingContextId);
        Stream.concat(this.findGlobalEPackages(), additionalEPackages.stream())
                .filter(ePackage -> isStudioProjectNature || !List.of(DomainPackage.eNS_URI, ViewPackage.eNS_URI, DiagramPackage.eNS_URI, FormPackage.eNS_URI).contains(ePackage.getNsURI()))
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

        if (isStudioProjectNature) {
            Optional<View> optionalView = this.loadStudioColorPalettes(resourceSet);
            if (optionalView.isPresent()) {
                var colorPalettesView = optionalView.get();
                resourceSet.eAdapters().add(new DiagramColorAdapter(colorPalettesView));
                resourceSet.eAdapters().add(new FormColorAdapter(colorPalettesView));
            }
        }

        this.optionalResourceFactoryRegistry.ifPresent(resourceSet::setResourceFactoryRegistry);

        return editingDomain;
    }

    private Stream<EPackage> findGlobalEPackages() {
        return this.globalEPackageRegistry.values().stream().filter(EPackage.class::isInstance).map(EPackage.class::cast);
    }

    private Optional<View> loadStudioColorPalettes(ResourceSet resourceSet) {
        ClassPathResource classPathResource = new ClassPathResource("studioColorPalettes.json");
        URI uri = URI.createURI(IEMFEditingContext.RESOURCE_SCHEME + ":///" + UUID.nameUUIDFromBytes(classPathResource.getPath().getBytes()));
        Resource resource = null;
        Optional<Resource> existingResource = resourceSet.getResources().stream().filter(r -> uri.equals(r.getURI())).findFirst();
        if (existingResource.isEmpty()) {
            resource = new JSONResourceFactory().createResource(uri);
            try (var inputStream = new ByteArrayInputStream(classPathResource.getContentAsByteArray())) {
                resourceSet.getResources().add(resource);
                resource.load(inputStream, null);
                resource.eAdapters().add(new ResourceMetadataAdapter("studioColorPalettes"));
            } catch (IOException exception) {
                this.logger.warn("An error occured while loading document studioColorPalettes.json: {}.", exception.getMessage());
                resourceSet.getResources().remove(resource);
            }
        } else {
            resource = existingResource.get();
        }
        return resource.getContents().stream().filter(View.class::isInstance).map(View.class::cast).findFirst();
    }
}
