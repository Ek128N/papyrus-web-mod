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
package org.eclipse.papyrus.web.application.templates.projects;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.papyrus.web.application.explorer.builder.UMLDefaultTreeDescriptionBuilder;
import org.eclipse.papyrus.web.application.properties.UMLDetailViewFromBuilder;
import org.eclipse.papyrus.web.application.properties.UMLPropertiesConfigurer;
import org.eclipse.papyrus.web.application.representations.PapyrusRepresentationDescriptionRegistry;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateInitializer;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

/**
 * Provides Studio-specific project templates initializers.
 *
 * @author Arthur Daussy
 */
@Configuration
public class PapyrusStudioProjectTemplatesInitializer implements IProjectTemplateInitializer {

    private final PapyrusRepresentationDescriptionRegistry papyrusRepresentationRegistry;

    public PapyrusStudioProjectTemplatesInitializer(
            PapyrusRepresentationDescriptionRegistry papyrusRepresentationRegistry) {
        this.papyrusRepresentationRegistry = papyrusRepresentationRegistry;
    }

    @Override
    public boolean canHandle(String templateId) {
        return List.of(PapyrusStudioProjectTemplatesProvider.PAPYRUS_STUDIO_TEMPLATE_ID).contains(templateId);
    }

    @Override
    public Optional<RepresentationMetadata> handle(ICause cause, String templateId, IEditingContext editingContext) {
        final Optional<RepresentationMetadata> representationMetadata;
        if (PapyrusStudioProjectTemplatesProvider.PAPYRUS_STUDIO_TEMPLATE_ID.equals(templateId)) {
            representationMetadata = this.initializePapyrusStudioProject(editingContext);
        } else {
            representationMetadata = Optional.empty();
        }
        return representationMetadata;
    }

    private Optional<RepresentationMetadata> initializePapyrusStudioProject(IEditingContext editingContext) {

        return new UUIDParser().parse(editingContext.getId())
                .map(AggregateReference::<Project, UUID>to)
                .flatMap(project -> this.getResourceSet(editingContext).flatMap(resourceSet -> {

                    String formName = UMLPropertiesConfigurer.UML_DETAIL_VIEW_NAME + " Studio";
                    View view = new UMLDetailViewFromBuilder(formName).build();

                    this.addToResouce(resourceSet, view, formName);

                    for (org.eclipse.sirius.components.view.diagram.DiagramDescription diagram : this.papyrusRepresentationRegistry.getViewDiagrams()) {
                        View copiedView = (View) EcoreUtil.copy(diagram.eContainer());
                        RepresentationDescription copiedDiagram = copiedView.getDescriptions().get(0);
                        String name = copiedDiagram.getName() + " Studio";
                        copiedDiagram.setName(name);

                        this.addToResouce(resourceSet, copiedView, name);

                    }

                    resourceSet.getResources().add(new UMLDefaultTreeDescriptionBuilder().createView().eResource());

                    return Optional.empty();
                }));

    }

    private Optional<ResourceSet> getResourceSet(IEditingContext editingContext) {
        return Optional.of(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain)
                .map(AdapterFactoryEditingDomain::getResourceSet);
    }

    public void addToResouce(ResourceSet resourceSet, EObject root, String name) {
        JSONResourceFactory jsonResourceFactory = new JSONResourceFactory();
        JsonResource resource = jsonResourceFactory.createResourceFromPath(UUID.randomUUID().toString());
        resource.eAdapters().add(new ResourceMetadataAdapter(name));
        resource.getContents().add(root);
        resource.getAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });
        resourceSet.getResources().add(resource);
    }

}
