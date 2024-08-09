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
package org.eclipse.papyrus.web.application.stereotypes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.migration.MigrationService;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.application.document.dto.DocumentDTO;
import org.eclipse.sirius.web.application.document.services.api.IStereotypeHandler;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLFactory;
import org.springframework.stereotype.Service;

/**
 * Stereotype handler for Papyrus Web.
 *
 * @author Arthur Daussy
 */
@Service
public class UMLStereotypeHandler implements IStereotypeHandler {

    private final List<IMigrationParticipant> migrationParticipants;

    public UMLStereotypeHandler(List<IMigrationParticipant> migrationParticipants) {
        super();
        this.migrationParticipants = migrationParticipants;
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String stereotypeId) {
        return List.of(
                UMLStereotypeProvider.EMPTY_UML,
                UMLStereotypeProvider.MODEL_UML,
                UMLStereotypeProvider.PROFILE_UML).contains(stereotypeId);
    }

    @Override
    public Optional<DocumentDTO> handle(IEditingContext editingContext, String stereotypeId, String name) {
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            return switch (stereotypeId) {
                case UMLStereotypeProvider.EMPTY_UML -> this.createEmptyUMLDocument(emfEditingContext, name);
                case UMLStereotypeProvider.MODEL_UML -> this.createModelDocument(emfEditingContext, name);
                case UMLStereotypeProvider.PROFILE_UML -> this.createProfileDocument(emfEditingContext, name);
                default -> Optional.empty();
            };
        }
        return Optional.empty();
    }

    private Optional<DocumentDTO> createModelDocument(IEMFEditingContext emfEditingContext, String name) {
        var documentId = UUID.randomUUID();

        Model model = UMLFactory.eINSTANCE.createModel();
        model.setName("Model");
        this.createResource(emfEditingContext, name, documentId).getContents().add(model);

        return Optional.of(new DocumentDTO(documentId, name, ExplorerDescriptionProvider.DOCUMENT_KIND));
    }

    private Optional<DocumentDTO> createProfileDocument(IEMFEditingContext emfEditingContext, String name) {
        var documentId = UUID.randomUUID();

        Profile profile = UMLFactory.eINSTANCE.createProfile();
        profile.setName("Profile");
        this.createResource(emfEditingContext, name, documentId).getContents().add(profile);

        return Optional.of(new DocumentDTO(documentId, name, ExplorerDescriptionProvider.DOCUMENT_KIND));
    }

    private Optional<DocumentDTO> createEmptyUMLDocument(IEMFEditingContext emfEditingContext, String name) {
        var documentId = UUID.randomUUID();

        this.createResource(emfEditingContext, name, documentId);

        return Optional.of(new DocumentDTO(documentId, name, ExplorerDescriptionProvider.DOCUMENT_KIND));

    }

    private Resource createResource(IEMFEditingContext emfEditingContext, String name, UUID uuid) {

        var resource = new JSONResourceFactory().createResourceFromPath(uuid.toString());

        var resourceMetadataAdapter = new ResourceMetadataAdapter(name);
        var migrationService = new MigrationService(this.migrationParticipants);

        resourceMetadataAdapter.setMigrationData(migrationService.getMostRecentParticipantMigrationData());

        resource.eAdapters().add(resourceMetadataAdapter);
        emfEditingContext.getDomain().getResourceSet().getResources().add(resource);

        return resource;

    }

}
