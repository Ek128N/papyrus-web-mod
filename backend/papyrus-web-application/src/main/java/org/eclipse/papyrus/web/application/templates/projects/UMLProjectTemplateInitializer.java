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
package org.eclipse.papyrus.web.application.templates.projects;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.web.application.representations.PapyrusRepresentationDescriptionRegistry;
import org.eclipse.papyrus.web.application.representations.aqlservices.utils.GenericDiagramService;
import org.eclipse.papyrus.web.application.representations.uml.PADDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramBuilderService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationMetadataPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateInitializer;
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

    private final TemplateInitializer initializerHelper;

    private final IDiagramBuilderService diagramBuilderService;

    private final GenericDiagramService packageDiagramService;

    private final PapyrusRepresentationDescriptionRegistry papyrusRepresentationRegistry;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationMetadataPersistenceService representationMetadataPersistenceService;

    public UMLProjectTemplateInitializer(TemplateInitializer initializerHelper,
            IDiagramBuilderService diagramBuilderService,
            PapyrusRepresentationDescriptionRegistry papyrusRepresentationRegistry,
            GenericDiagramService packageDiagramService,
            PapyrusProjectTemplateInitializerParameters papyrusProjectTemplateInitializerParameters) {
        this.initializerHelper = initializerHelper;
        this.diagramBuilderService = diagramBuilderService;
        this.papyrusRepresentationRegistry = papyrusRepresentationRegistry;
        this.packageDiagramService = packageDiagramService;
        this.representationPersistenceService = papyrusProjectTemplateInitializerParameters.representationPersistenceService();
        this.representationDescriptionSearchService = papyrusProjectTemplateInitializerParameters.representationDescriptionSearchService();
        this.representationMetadataPersistenceService = papyrusProjectTemplateInitializerParameters.representationMetadataPersistenceService();
    }

    @Override
    public boolean canHandle(String templateId) {
        return List.of(UMLProjectTemplateProvider.UML_WITH_PRIMITIVES_TEMPLATE_ID, UMLProjectTemplateProvider.EMPTY_UML_TEMPLATE).contains(templateId);
    }

    @Override
    public Optional<RepresentationMetadata> handle(ICause cause, String templateId, IEditingContext editingContext) {
        Optional<RepresentationMetadata> result = Optional.empty();
        if (UMLProjectTemplateProvider.UML_WITH_PRIMITIVES_TEMPLATE_ID.equals(templateId)) {
            result = this.initializeUMLWithPrimitivesProjectContents(editingContext, cause);
        } else if (UMLProjectTemplateProvider.EMPTY_UML_TEMPLATE.equals(templateId)) {
            result = Optional.empty();
        }
        return result;
    }

    private Optional<RepresentationMetadata> initializeUMLWithPrimitivesProjectContents(IEditingContext editingContext, ICause cause) {
        try {
            Optional<Resource> resource = this.initializerHelper.initializeResourceFromClasspathFile(editingContext, UML_MODEL_TITLE, "DefaultUMLWithPrimitive.uml", cause);
            var optionalDiagram = resource.flatMap(r -> this.createPackageDiagram(editingContext, r, cause));
            if (optionalDiagram.isPresent()) {
                var diagram = optionalDiagram.get();
                Object semanticTarget = resource.map(r -> r.getContents().get(0)).orElse(null);
                var optionalRepresentationMetadata = this.createRepresentationMetadata(editingContext, diagram, semanticTarget);
                optionalRepresentationMetadata.ifPresent(rm -> {
                    this.representationMetadataPersistenceService.save(cause, editingContext, rm, diagram.getTargetObjectId());
                    this.representationPersistenceService.save(cause, editingContext, diagram);
                });
                return optionalRepresentationMetadata;
            }
        } catch (IOException e) {
            this.logger.error("Error while creating template", e);
        }
        return Optional.empty();
    }

    private Optional<? extends Diagram> createPackageDiagram(IEditingContext editingContext, Resource r, ICause cause) {
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
                });
    }

    private Optional<RepresentationMetadata> createRepresentationMetadata(IEditingContext editingContext, Diagram diagram, Object semanticTarget) {
        return this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .map(diagramDescription -> {
                    var variableManager = new VariableManager();
                    variableManager.put(VariableManager.SELF, semanticTarget);
                    variableManager.put(DiagramDescription.LABEL, diagramDescription.getLabel());
                    String label = diagramDescription.getLabelProvider().apply(variableManager);
                    List<String> iconURLs = diagramDescription.getIconURLsProvider().apply(variableManager);
                    return RepresentationMetadata.newRepresentationMetadata(diagram.getId())
                            .kind(diagram.getKind())
                            .label(label)
                            .descriptionId(diagram.getDescriptionId())
                            .iconURLs(iconURLs)
                            .build();
                });
    }
}
