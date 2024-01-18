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
package org.eclipse.papyrus.web.services.documents;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.papyrus.web.sirius.contributions.ServiceOverride;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.services.api.document.Document;
import org.eclipse.sirius.web.services.api.document.IDocumentService;
import org.eclipse.sirius.web.services.api.id.IDParser;
import org.eclipse.sirius.web.services.documents.DocumentMapper;
import org.eclipse.sirius.web.services.editingcontext.api.IEditingDomainFactoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service used to manipulate documents.
 *
 * @author sbegaudeau
 */
@ServiceOverride(org.eclipse.sirius.web.services.documents.DocumentService.class)
public class DocumentServiceCustomImpl implements IDocumentService {

    private final IDocumentRepository documentRepository;

    private final IProjectRepository projectRepository;

    private final Logger logger = LoggerFactory.getLogger(DocumentServiceCustomImpl.class);

    private final IEditingDomainFactoryService editingDomainFactoryService;

    public DocumentServiceCustomImpl(IProjectRepository projectRepository, IDocumentRepository documentRepository, IEditingDomainFactoryService editingDomainFactoryService) {
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.documentRepository = Objects.requireNonNull(documentRepository);
        this.editingDomainFactoryService = editingDomainFactoryService;
    }

    @Override
    public Optional<Document> createDocument(String projectId, String name, String content) {
        return new IDParser().parse(projectId)
                .flatMap(this.projectRepository::findById)
                .map(projectEntity -> {
                    DocumentEntity documentEntity = new DocumentEntity();
                    documentEntity.setProject(projectEntity);
                    documentEntity.setName(name);
                    documentEntity.setContent(content);

                    documentEntity = this.documentRepository.save(documentEntity);

                    Document document = new DocumentMapper().toDTO(documentEntity);
                    return document;
                });
    }

    @Override
    public Optional<Document> getDocument(UUID documentId) {
        return this.documentRepository.findById(documentId).map(new DocumentMapper()::toDTO);
    }

    @Override
    public Optional<Document> getDocument(String projectId, UUID documentId) {
        return new IDParser().parse(projectId)
                .flatMap(projectUUID -> this.documentRepository.findByProjectIdAndId(projectUUID, documentId))
                .map(new DocumentMapper()::toDTO);
    }

    @Override
    public List<Document> getDocuments(String projectId) {
        return new IDParser().parse(projectId)
                .map(this.documentRepository::findAllByProjectId)
                .orElseGet(List::of)
                .stream()
                .map(new DocumentMapper()::toDTO)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void delete(UUID documentId) {
        this.documentRepository.deleteById(documentId);
    }

    /**
     * Returns the byte array of the serialization of the given document. The document can be serialized with a
     * {@link JsonResource} or an {@link XMIResource}.
     *
     *
     * @param document
     *            The document to serialize
     * @param resourceKind
     *            The resource kind used to determine which {@link Resource} will be used to serialize the document
     * @return The byte array to the serialized document
     */
    @Override
    public Optional<byte[]> getBytes(Document document, String resourceKind) {
        Optional<byte[]> optionalBytes = Optional.empty();

        if (RESOURCE_KIND_JSON.equals(resourceKind)) {
            optionalBytes = Optional.of(document.getContent().getBytes());
        } else if (RESOURCE_KIND_XMI.equals(resourceKind)) {
            // Custo: Need fully configure editing domain to resolve pathmap
            AdapterFactoryEditingDomain editingDomain = this.editingDomainFactoryService.createEditingDomain(document.getProject().getId().toString());
            ResourceSet resourceSet = editingDomain.getResourceSet();

            Resource outputResource = resourceSet.createResource(URI.createURI(document.getName()));
            if (outputResource == null) {
                // In case of no factory has been added on the Resource.Factory.Registry of the resrouceSet
                outputResource = new XMIResourceImpl(URI.createURI(document.getName()));
            }

            JsonResource resource = new JSONResourceFactory().createResourceFromPath(document.getName());
            resourceSet.getResources().add(resource);
            resourceSet.getResources().add(outputResource);

            try (var inputStream = new ByteArrayInputStream(document.getContent().getBytes())) {
                resource.load(inputStream, new HashMap<>());

                outputResource.getContents().addAll(resource.getContents());
                try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
                    Map<String, Object> options = new HashMap<>();
                    options.put(XMIResource.OPTION_ENCODING, JsonResource.ENCODING_UTF_8);
                    options.put(XMIResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
                    options.put(XMIResource.OPTION_USE_XMI_TYPE, Boolean.TRUE);
                    // Custo:Save type information
                    options.put(XMLResource.OPTION_SAVE_TYPE_INFORMATION, Boolean.TRUE);

                    outputResource.save(outputStream, options);
                    optionalBytes = Optional.of(outputStream.toByteArray());
                }
            } catch (IOException exception) {
                this.logger.warn(exception.getMessage(), exception);
            }
        }

        return optionalBytes;
    }

    @Override
    public Optional<Document> rename(UUID documentId, String newName) {
        Optional<DocumentEntity> optionalDocumentEntity = this.documentRepository.findById(documentId);
        if (optionalDocumentEntity.isPresent()) {
            DocumentEntity documentEntity = optionalDocumentEntity.get();
            documentEntity.setName(newName);
            return Optional.of(this.documentRepository.save(documentEntity)).map(new DocumentMapper()::toDTO);
        }
        return Optional.empty();
    }
}
