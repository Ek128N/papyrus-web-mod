/*****************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo.
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
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.web.application.representations.uml.PADDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.services.aqlservices.utils.GenericDiagramService;
import org.eclipse.papyrus.web.services.representations.PapyrusRepresentationDescriptionRegistry;
import org.eclipse.papyrus.web.services.template.TemplateInitializer;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramBuilderService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.web.services.api.projects.IProjectTemplateInitializer;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Initializes the contents of projects created from a UML project template.
 *
 * @author pcdavid
 */
@Configuration
public class UMLProjectTemplateInitializer implements IProjectTemplateInitializer {
    private static final String UML_MODEL_TITLE = "Model.uml";

    private final Logger logger = LoggerFactory.getLogger(UMLProjectTemplateInitializer.class);

    private TemplateInitializer initializerHelper;

    private IDiagramBuilderService diagramBuilderService;

    private GenericDiagramService packageDiagramService;

    private PapyrusRepresentationDescriptionRegistry papyrusRepresentationRegistry;

    private IRepresentationPersistenceService representationPersistenceService;

    public UMLProjectTemplateInitializer(TemplateInitializer initializerHelper, //
            IDiagramBuilderService diagramBuilderService, //
            PapyrusRepresentationDescriptionRegistry papyrusRepresentationRegistry, //
            GenericDiagramService packageDiagramService, IRepresentationPersistenceService representationPersistenceService) {
        this.initializerHelper = initializerHelper;
        this.diagramBuilderService = diagramBuilderService;
        this.papyrusRepresentationRegistry = papyrusRepresentationRegistry;
        this.packageDiagramService = packageDiagramService;
        this.representationPersistenceService = representationPersistenceService;
    }

    @Override
    public boolean canHandle(String templateId) {
        return List.of(UMLProjectTemplateProvider.UML_WITH_PRIMITIVES_TEMPLATE_ID).contains(templateId);
    }

    @Override
    public Optional<RepresentationMetadata> handle(String templateId, IEditingContext editingContext) {
        Optional<RepresentationMetadata> result = Optional.empty();
        if (UMLProjectTemplateProvider.UML_WITH_PRIMITIVES_TEMPLATE_ID.equals(templateId)) {
            result = this.initializeUMLWithPrimitivesProjectContents(editingContext);
        }
        return result;
    }

    private Optional<RepresentationMetadata> initializeUMLWithPrimitivesProjectContents(IEditingContext editingContext) {
        try {
            Optional<Resource> resource = this.initializerHelper.initializeResourceFromClasspathFile(editingContext, UML_MODEL_TITLE, "DefaultUMLWithPrimitive.uml");
            return resource.flatMap(r -> this.createPackageDiagram(editingContext, r))//
                    .map(diagram -> new RepresentationMetadata(diagram.getId(), diagram.getKind(), diagram.getLabel(), diagram.getDescriptionId()));
        } catch (IOException e) {
            this.logger.error("Error while creating template", e);
        }
        return Optional.empty();
    }

    private Optional<? extends Diagram> createPackageDiagram(IEditingContext editingContext, Resource r) {
        Model model = (Model) r.getContents().get(0);
        Package primitiveTypePackage = model.getImportedPackages().get(0);

        Map<org.eclipse.sirius.components.view.diagram.NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes = this.papyrusRepresentationRegistry
                .getConvertedNode(PADDiagramDescriptionBuilder.PD_REP_NAME);

        return this.diagramBuilderService
                .createDiagram(editingContext, diagramDescription -> PADDiagramDescriptionBuilder.PD_REP_NAME.equals(diagramDescription.getLabel()), model, "Root Package Diagram")
                .flatMap(diagram -> {
                    return this.diagramBuilderService.updateDiagram(diagram, editingContext, diagramContext -> {
                        this.packageDiagramService.semanticDrop(model, null, editingContext, diagramContext, convertedNodes);
                        this.packageDiagramService.semanticDrop(primitiveTypePackage, null, editingContext, diagramContext, convertedNodes);
                    });
                })//
                .flatMap(diagram -> {
                    this.representationPersistenceService.save(editingContext, diagram);
                    return Optional.of(diagram);
                });

    }

}
