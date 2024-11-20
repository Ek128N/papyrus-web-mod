/*****************************************************************************
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
 *****************************************************************************/
package org.eclipse.papyrus.web.profile.java;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.web.application.representations.PapyrusRepresentationDescriptionRegistry;
import org.eclipse.papyrus.web.application.representations.aqlservices.utils.GenericDiagramService;
import org.eclipse.papyrus.web.application.representations.uml.CDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.templates.projects.PapyrusProjectTemplateInitializerParameters;
import org.eclipse.papyrus.web.application.templates.projects.TemplateInitializer;
import org.eclipse.papyrus.web.sirius.contributions.DiagramNavigator;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramBuilderService;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramNavigationService;
import org.eclipse.papyrus.web.sirius.contributions.query.NodeMatcher;
import org.eclipse.papyrus.web.sirius.contributions.query.NodeMatcher.BorderNodeStatus;
import org.eclipse.sirius.components.collaborative.api.IRepresentationMetadataPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateInitializer;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Initializes the contents of projects created from a UML project template.
 *
 * @author pcdavid
 */
@Configuration
public class UMLJavaProjectTemplateInitializer implements IProjectTemplateInitializer {
    private static final String UML_MODEL_TITLE = "JavaTemplate.uml";

    private final Logger logger = LoggerFactory.getLogger(UMLJavaProjectTemplateInitializer.class);

    private final TemplateInitializer initializerHelper;

    private final IDiagramBuilderService diagramBuilderService;

    private final GenericDiagramService classDiagramService;

    private final PapyrusRepresentationDescriptionRegistry papyrusRepresentationRegistry;

    private final IDiagramNavigationService diagramNavigationService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationMetadataPersistenceService representationMetadataPersistenceService;

    public UMLJavaProjectTemplateInitializer(TemplateInitializer initializerHelper, //
            IDiagramBuilderService diagramBuilderService, //
            IDiagramNavigationService diagramNavigationService, //
            PapyrusRepresentationDescriptionRegistry papyrusRepresentationRegistry, //
            GenericDiagramService classDiagramService, //
            PapyrusProjectTemplateInitializerParameters papyrusProjectTemplateInitializerParameters) {
        this.initializerHelper = Objects.requireNonNull(initializerHelper);
        this.diagramBuilderService = Objects.requireNonNull(diagramBuilderService);
        this.diagramNavigationService = Objects.requireNonNull(diagramNavigationService);
        this.papyrusRepresentationRegistry = Objects.requireNonNull(papyrusRepresentationRegistry);
        this.classDiagramService = Objects.requireNonNull(classDiagramService);
        this.representationPersistenceService = papyrusProjectTemplateInitializerParameters.representationPersistenceService();
        this.representationDescriptionSearchService = papyrusProjectTemplateInitializerParameters.representationDescriptionSearchService();
        this.representationMetadataPersistenceService = papyrusProjectTemplateInitializerParameters.representationMetadataPersistenceService();
    }

    @Override
    public boolean canHandle(String templateId) {
        return List.of(UMLJavaTemplateProvider.UML_JAVA_TEMPLATE_ID).contains(templateId);
    }

    @Override
    public Optional<RepresentationMetadata> handle(ICause cause, String templateId, IEditingContext editingContext) {
        Optional<RepresentationMetadata> result = Optional.empty();
        if (UMLJavaTemplateProvider.UML_JAVA_TEMPLATE_ID.equals(templateId)) {
            result = this.initializeUMLJavaProjectContents(editingContext, cause);
        }
        return result;
    }

    private Optional<RepresentationMetadata> initializeUMLJavaProjectContents(IEditingContext editingContext, ICause cause) {
        try {
            Optional<Resource> resource = this.initializerHelper.initializeResourceFromClasspathFile(editingContext, UML_MODEL_TITLE, "JavaTemplate.uml", cause);
            var optionalDiagram = resource.flatMap(r -> this.createMainClassDiagram(editingContext, r, cause));
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

    private Optional<Diagram> createMainClassDiagram(IEditingContext editingContext, Resource r, ICause cause) {
        Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes = this.papyrusRepresentationRegistry
                .getConvertedNode(CDDiagramDescriptionBuilder.CD_REP_NAME);
        Model model = (Model) r.getContents().get(0);
        return this.diagramBuilderService.createDiagram(editingContext, diagramDescription -> CDDiagramDescriptionBuilder.CD_REP_NAME.equals(diagramDescription.getLabel()), model, "Main")
                .flatMap(diagram -> this.semanticDropClassAndComment(editingContext, convertedNodes, model, diagram));
    }

    private Optional<Diagram> semanticDropClassAndComment(IEditingContext editingContext,
            Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes, Model model, Diagram diagram) {
        Class mainClass = (Class) model.getOwnedMembers().stream().filter(m -> m instanceof Class && "Main".equals(m.getName())).findFirst().orElse(null);
        Comment classComment = model.getOwnedComments().stream().filter(c -> c.getAnnotatedElements().contains(mainClass)).findFirst().orElse(null);
        return this.diagramBuilderService.updateDiagram(diagram, editingContext, diagramContext -> {
            // Get the linked comment
            this.classDiagramService.semanticDrop(mainClass, null, editingContext, diagramContext, convertedNodes);
            this.classDiagramService.semanticDrop(classComment, null, editingContext, diagramContext, convertedNodes);
        }).flatMap(diag -> this.semanticDropOperationsOnClass(editingContext, convertedNodes, mainClass, diag));
    }

    private Optional<Diagram> semanticDropOperationsOnClass(IEditingContext editingContext, Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes,
            Class mainClass, Diagram diag) {
        return this.diagramBuilderService.updateDiagram(diag, editingContext, diagramContext -> {
            for (Operation operation : mainClass.getOwnedOperations()) {
                NodeMatcher mainClassNodeMatcher = this.createOperationCompartmentNodeMatcher(mainClass, diag, convertedNodes);
                Node operationCompartment = this.diagramNavigationService.getMatchingNodes(diag, editingContext, mainClassNodeMatcher).get(0);
                this.classDiagramService.semanticDrop(operation, operationCompartment, editingContext, diagramContext, convertedNodes);
            }
        });
    }

    private NodeMatcher createOperationCompartmentNodeMatcher(Class mainClass, Diagram diagram,
            Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes) {
        DiagramNavigator diagramNav = new DiagramNavigator(this.diagramNavigationService, diagram, convertedNodes);
        return NodeMatcher.buildSemanticAndNodeMatcher(BorderNodeStatus.BASIC_NODE, o -> o == mainClass, v -> this.filter(diagramNav, v));
    }

    private boolean filter(DiagramNavigator diagramNav, Node v) {
        return "CD_Class_Operations_SHARED_CompartmentNode".equals(diagramNav.getDescription(v).get().getName());
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
                    return RepresentationMetadata.newRepresentationMetadata(diagram.getId())
                            .kind(diagram.getKind())
                            .label(label)
                            .descriptionId(diagram.getDescriptionId())
                            .build();
                });
    }
}
