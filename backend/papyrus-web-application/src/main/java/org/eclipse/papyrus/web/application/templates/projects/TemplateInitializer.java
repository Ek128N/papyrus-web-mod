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
package org.eclipse.papyrus.web.application.templates.projects;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.papyrus.web.sirius.contributions.StereotypeBuilder;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.Document;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataUpdateService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Helper to create Project templates.
 *
 * @author Arthur Daussy
 */
@Service
public class TemplateInitializer {
    private StereotypeBuilder stereotypeBuilder;

    private ISemanticDataUpdateService semanticDataUpdateService;

    public TemplateInitializer(ISemanticDataUpdateService semanticDataUpdateService, MeterRegistry meterRegistry) {
        super();
        this.semanticDataUpdateService = semanticDataUpdateService;
        this.stereotypeBuilder = new StereotypeBuilder("classpath-model-loader", meterRegistry);
    }

    /**
     * Initialize a {@link Resource} from a file located in the classpath.
     *
     * @param editingContext
     *            the current {@link IEditingContext}
     * @param newResourceName
     *            the name of the resource
     * @param filePath
     *            the path of the file in the jar
     * @return a optional resource.
     * @throws IOException
     *             if something goes wrong while reading the file
     */
    public Optional<Resource> initializeResourceFromClasspathFile(IEditingContext editingContext, String newResourceName, String filePath) throws IOException {
        Optional<AdapterFactoryEditingDomain> optionalEditingDomain = Optional.of(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain);
        Optional<UUID> optionalEditingContextUUID = new UUIDParser().parse(editingContext.getId());
        if (optionalEditingDomain.isPresent() && optionalEditingContextUUID.isPresent()) {
            UUID editingContextUUID = optionalEditingContextUUID.get();

            String jsonContents = this.loadModelAsJSON(filePath);
            UUID documentId = UUID.randomUUID();
            Document document = Document.newDocument(documentId)
                    .name(newResourceName)
                    .content(jsonContents)
                    .build();
            AdapterFactoryEditingDomain adapterFactoryEditingDomain = optionalEditingDomain.get();
            ResourceSet resourceSet = adapterFactoryEditingDomain.getResourceSet();
            JsonResource resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());
            resourceSet.getResources().add(resource);
            resource.eAdapters().add(new ResourceMetadataAdapter(newResourceName));

            resource.getAllContents().forEachRemaining(eObject -> {
                eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
            });
            try (var inputStream = new ByteArrayInputStream(jsonContents.getBytes())) {
                resource.load(inputStream, null);
                this.semanticDataUpdateService.updateDocuments(AggregateReference.to(editingContextUUID), Collections.singleton(document), resourceSet.getPackageRegistry().keySet());
                return Optional.of(resource);
            }

        }
        return Optional.empty();
    }

    private String loadModelAsJSON(String resourcePath) {
        return this.stereotypeBuilder.getStereotypeBody(new ClassPathResource(resourcePath));
    }

}
