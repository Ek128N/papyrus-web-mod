/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.templates;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.web.application.representations.uml.PRDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.services.template.TemplateInitializer;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramBuilderService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.web.services.api.projects.IProjectTemplateInitializer;
import org.eclipse.uml2.uml.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Initializes the contents of projects created from a Profile project template.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@Configuration
public class ProfileProjectTemplateInitializer implements IProjectTemplateInitializer {

    /**
     * Name of the new resource.
     */
    private static final String PROFILE_MODEL_TITLE = "Profile.profile.uml";

    /**
     * Logger used to log error when model creation fails.
     */
    private final Logger logger = LoggerFactory.getLogger(ProfileProjectTemplateInitializer.class);

    /**
     * Helper to create Project templates.
     */
    private TemplateInitializer initializerHelper;

    /**
     * Service used to create diagram programmatically.
     */
    private IDiagramBuilderService diagramBuilderService;

    /**
     * Service used to save new representations.
     */
    private IRepresentationPersistenceService representationPersistenceService;

    /**
     * Constructor.
     *
     * @param initializerHelper
     *            Helper to create Project templates
     * @param diagramBuilderService
     *            Service used to create diagram programmatically
     * @param representationPersistenceService
     *            Service used to save new representations
     */
    public ProfileProjectTemplateInitializer(TemplateInitializer initializerHelper, //
            IDiagramBuilderService diagramBuilderService, //
            IRepresentationPersistenceService representationPersistenceService) {
        this.initializerHelper = initializerHelper;
        this.diagramBuilderService = diagramBuilderService;
        this.representationPersistenceService = representationPersistenceService;
    }

    @Override
    public boolean canHandle(String templateId) {
        return List.of(ProfileProjectTemplateProvider.PROFILE_WITH_PRIMITIVES_AND_UML_TEMPLATE_ID).contains(templateId);
    }

    @Override
    public Optional<RepresentationMetadata> handle(String templateId, IEditingContext editingContext) {
        Optional<RepresentationMetadata> result = Optional.empty();
        if (ProfileProjectTemplateProvider.PROFILE_WITH_PRIMITIVES_AND_UML_TEMPLATE_ID.equals(templateId)) {
            result = this.initializeProfileWithPrimitivesAndUmlProjectContents(editingContext);
        }
        return result;
    }

    private Optional<RepresentationMetadata> initializeProfileWithPrimitivesAndUmlProjectContents(IEditingContext editingContext) {
        try {
            Optional<Resource> resource = this.initializerHelper.initializeResourceFromClasspathFile(editingContext, PROFILE_MODEL_TITLE, "DefaultProfileWithPrimitiveAndUml.uml");
            return resource.flatMap(r -> this.createProfileDiagram(editingContext, r))//
                    .map(diagram -> new RepresentationMetadata(diagram.getId(), diagram.getKind(), diagram.getLabel(), diagram.getDescriptionId()));
        } catch (IOException e) {
            this.logger.error("Error while creating template", e);
        }
        return Optional.empty();
    }

    private Optional<? extends Diagram> createProfileDiagram(IEditingContext editingContext, Resource r) {
        Profile profile = (Profile) r.getContents().get(0);

        return this.diagramBuilderService
                .createDiagram(editingContext, diagramDescription -> PRDDiagramDescriptionBuilder.PRD_REP_NAME.equals(diagramDescription.getLabel()), profile, "Root Profile Diagram")
                .flatMap(diagram -> {
                    this.representationPersistenceService.save(editingContext, diagram);
                    return Optional.of(diagram);
                });

    }

}
