/*****************************************************************************
 * Copyright (c) 2024 CEA LIST, Obeo.
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
 *****************************************************************************/
package org.eclipse.papyrus.web.application.configuration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.web.sirius.contributions.ServiceOverride;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.migration.MigrationService;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.emf.services.EObjectIDManager;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.editingcontext.services.DocumentData;
import org.eclipse.sirius.web.application.editingcontext.services.JsonResourceSerializationListener;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceToDocumentService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.Document;
import org.eclipse.uml2.uml.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom implementation used as a workaround for bug https://github.com/eclipse-sirius/sirius-web/issues/3806.
 *
 * This implementation remove the MigrationService since it forces the used of BasicExtendedMetaData which does not work
 * properly together with EMFJSon and UML.
 *
 * @author Arthur Daussy
 */
@ServiceOverride(org.eclipse.sirius.web.application.editingcontext.services.ResourceToDocumentService.class)
public class PapyrusResourceToDocumentService implements IResourceToDocumentService {

    private final Logger logger = LoggerFactory.getLogger(PapyrusResourceToDocumentService.class);

    private List<IMigrationParticipant> migrationParticipants;

    public PapyrusResourceToDocumentService(List<IMigrationParticipant> migrationParticipants) {
        this.migrationParticipants = migrationParticipants;
    }

    @Override
    public Optional<DocumentData> toDocument(Resource resource) {
        var serializationListener = new JsonResourceSerializationListener();

        var migrationService = new MigrationService(this.migrationParticipants);

        HashMap<Object, Object> options = new HashMap<>();
        options.put(JsonResource.OPTION_ID_MANAGER, new EObjectIDManager());
        options.put(JsonResource.OPTION_SCHEMA_LOCATION, true);
        options.put(JsonResource.OPTION_SERIALIZATION_LISTENER, serializationListener);
        if (resource.getContents().stream().noneMatch(e -> e instanceof Element)) {
            // Workaround of bug https://github.com/eclipse-sirius/sirius-web/issues/3806
            // We do not want ExtendedMetada for UML resource as long as this problem is not solved
            options.put(JsonResource.OPTION_JSON_RESSOURCE_PROCESSOR, migrationService);
            options.put(JsonResource.OPTION_EXTENDED_META_DATA, migrationService);
        }

        Optional<DocumentData> optionalDocumentData = Optional.empty();
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            resource.save(outputStream, options);

            for (Resource.Diagnostic warning : resource.getWarnings()) {
                this.logger.warn(warning.getMessage());
            }
            for (Resource.Diagnostic error : resource.getErrors()) {
                this.logger.warn(error.getMessage());
            }

            var name = resource.eAdapters().stream()
                    .filter(ResourceMetadataAdapter.class::isInstance)
                    .map(ResourceMetadataAdapter.class::cast)
                    .findFirst()
                    .map(ResourceMetadataAdapter::getName)
                    .orElse("");
            var content = outputStream.toString();

            var resourceId = resource.getURI().path().substring(1);
            var documentId = new UUIDParser().parse(resourceId).orElse(UUID.randomUUID());

            var document = Document.newDocument(documentId)
                    .name(name)
                    .content(content)
                    .build();
            var documentData = new DocumentData(document, serializationListener.getePackageEntries());
            optionalDocumentData = Optional.of(documentData);
        } catch (IllegalArgumentException | IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return optionalDocumentData;
    }

    public Optional<ResourceMetadataAdapter> getOptionalResourceMetadataAdapter(Resource resource) {
        return resource.eAdapters().stream()
                .filter(ResourceMetadataAdapter.class::isInstance)
                .map(ResourceMetadataAdapter.class::cast)
                .findFirst();
    }

}
