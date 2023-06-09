/*******************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo.
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.papyrus.web.services.api.id.IDParser;
import org.eclipse.papyrus.web.services.documents.DocumentMetadataAdapter;
import org.eclipse.papyrus.web.services.editingcontext.api.IDynamicRepresentationDescriptionService;
import org.eclipse.papyrus.web.services.representations.RepresentationDescriptionRegistry;
import org.eclipse.papyrus.web.sirius.contributions.UnloadingEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.configuration.IRepresentationDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

/**
 * Service used to find and retrieve editing contexts.
 *
 * @author sbegaudeau
 */
@Service
public class EditingContextSearchService implements IEditingContextSearchService {

    private static final String TIMER_NAME = "papyrusweb_editingcontext_load";

    private final Logger logger = LoggerFactory.getLogger(EditingContextSearchService.class);

    private final IProjectRepository projectRepository;

    private final IDocumentRepository documentRepository;

    private final EditingDomainFactoryService editingDomainFactoryService;

    private final List<IRepresentationDescriptionRegistryConfigurer> configurers;

    private final IDynamicRepresentationDescriptionService dynamicRepresentationDescriptionService;

    private final Timer timer;

    public EditingContextSearchService(IProjectRepository projectRepository, IDocumentRepository documentRepository, EditingDomainFactoryService editingDomainFactoryService,
            List<IRepresentationDescriptionRegistryConfigurer> configurers, IDynamicRepresentationDescriptionService dynamicRepresentationDescriptionService, MeterRegistry meterRegistry) {
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.documentRepository = Objects.requireNonNull(documentRepository);
        this.editingDomainFactoryService = Objects.requireNonNull(editingDomainFactoryService);
        this.configurers = Objects.requireNonNull(configurers);
        this.dynamicRepresentationDescriptionService = Objects.requireNonNull(dynamicRepresentationDescriptionService);

        this.timer = Timer.builder(TIMER_NAME).register(meterRegistry);
    }

    @Override
    public boolean existsById(String editingContextId) {
        return new IDParser().parse(editingContextId).map(editingContextUUID -> this.projectRepository.existsById(editingContextUUID)).orElse(false);
    }

    @Override
    public Optional<IEditingContext> findById(String editingContextId) {
        long start = System.currentTimeMillis();

        this.logger.debug("Loading the editing context {}", editingContextId);

        AdapterFactoryEditingDomain editingDomain = this.editingDomainFactoryService.createEditingDomain(editingContextId);
        ResourceSet resourceSet = editingDomain.getResourceSet();
        resourceSet.getLoadOptions().put(JsonResource.OPTION_EXTENDED_META_DATA, new BasicExtendedMetaData(resourceSet.getPackageRegistry()));
        resourceSet.getLoadOptions().put(JsonResource.OPTION_SCHEMA_LOCATION, true);

        List<DocumentEntity> documentEntities = new IDParser().parse(editingContextId).map(this.documentRepository::findAllByProjectId).orElseGet(List::of);
        for (DocumentEntity documentEntity : documentEntities) {
            Resource resource = new JSONResourceFactory().createResourceFromPath(documentEntity.getId().toString());
            try (var inputStream = new ByteArrayInputStream(documentEntity.getContent().getBytes())) {
                resourceSet.getResources().add(resource);
                resource.load(inputStream, resourceSet.getLoadOptions());

                resource.eAdapters().add(new DocumentMetadataAdapter(documentEntity.getName()));
            } catch (IOException | IllegalArgumentException exception) {
                this.logger.warn("An error occured while loading document {}: {}.", documentEntity.getId(), exception.getMessage());
                resourceSet.getResources().remove(resource);
            }
        }
        // DO NOT add the default EditingContextCrossReferenceAdapter for UML resource and UML Element.
        // Everything should be done within the UML Domain Service using the CacheAdapter
        // The ECrossReferenceAdapter must be set after the resource loading because it needs to resolve proxies in case
        // of inter-resources references
        resourceSet.eAdapters().add(new NonUMLEditingContextCrossReferenceAdapter());

        this.logger.debug("{} documents loaded for the editing context {}", resourceSet.getResources().size(), editingContextId);

        Map<String, IRepresentationDescription> representationDescriptions = new LinkedHashMap<>();
        var registry = new RepresentationDescriptionRegistry();
        this.configurers.forEach(configurer -> configurer.addRepresentationDescriptions(registry));
        registry.getRepresentationDescriptions().forEach(representationDescription -> representationDescriptions.put(representationDescription.getId(), representationDescription));
        this.dynamicRepresentationDescriptionService.findDynamicRepresentationDescriptions(editingContextId, editingDomain)
                .forEach(representationDescription -> representationDescriptions.put(representationDescription.getId(), representationDescription));

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);

        // Need this customization to be able to unload the UML resources and so clear the CacheAdapter
        return Optional.of(new UnloadingEditingContext(editingContextId, editingDomain, representationDescriptions));
    }

}
