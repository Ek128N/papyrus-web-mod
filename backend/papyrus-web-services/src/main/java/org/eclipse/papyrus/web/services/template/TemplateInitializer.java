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
package org.eclipse.papyrus.web.services.template;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.papyrus.web.sirius.contributions.StereotypeBuilder;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.services.api.id.IDParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Helper to create Project templates.
 *
 * @author Arthur Daussy
 */
@Service
public class TemplateInitializer {

    private final IProjectRepository projectRepository;

    private final IDocumentRepository documentRepository;

    private StereotypeBuilder stereotypeBuilder;

    public TemplateInitializer(IProjectRepository projectRepository, IDocumentRepository documentRepository, MeterRegistry meterRegistry) {
        super();
        this.projectRepository = projectRepository;
        this.documentRepository = documentRepository;
        this.stereotypeBuilder = new StereotypeBuilder("classpath-model-loader", meterRegistry);
    }

    public Optional<Resource> initializeResourceFromClasspathFile(IEditingContext editingContext, String newResourceName, String filePath) throws IOException {
        Optional<AdapterFactoryEditingDomain> optionalEditingDomain = Optional.of(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain);
        Optional<UUID> optionalEditingContextUUID = new IDParser().parse(editingContext.getId());
        if (optionalEditingDomain.isPresent() && optionalEditingContextUUID.isPresent()) {
            UUID editingContextUUID = optionalEditingContextUUID.get();
            var optionalDocumentEntity = this.createDocument(editingContextUUID, newResourceName, this.loadModelAsJSON(filePath));
            if (optionalDocumentEntity.isPresent()) {
                DocumentEntity documentEntity = optionalDocumentEntity.get();

                AdapterFactoryEditingDomain adapterFactoryEditingDomain = optionalEditingDomain.get();
                ResourceSet resourceSet = adapterFactoryEditingDomain.getResourceSet();
                JsonResource resource = new JSONResourceFactory().createResourceFromPath(documentEntity.getId().toString());
                resourceSet.getResources().add(resource);
                resource.eAdapters().add(new ResourceMetadataAdapter(newResourceName));
                try (var inputStream = new ByteArrayInputStream(documentEntity.getContent().getBytes())) {
                    resource.load(inputStream, null);
                    return Optional.of(resource);
                }
            }
        }
        return Optional.empty();
    }

    private String loadModelAsJSON(String resourcePath) {
        return this.stereotypeBuilder.getStereotypeBody(new ClassPathResource(resourcePath));
    }

    private Optional<DocumentEntity> createDocument(UUID editingContextUUID, String title, String jsonContents) {
        return this.projectRepository.findById(editingContextUUID).map(projectEntity -> {
            DocumentEntity documentEntity = new DocumentEntity();
            documentEntity.setProject(projectEntity);
            documentEntity.setName(title);
            documentEntity.setContent(jsonContents);
            documentEntity = this.documentRepository.save(documentEntity);
            return documentEntity;
        });
    }

}
