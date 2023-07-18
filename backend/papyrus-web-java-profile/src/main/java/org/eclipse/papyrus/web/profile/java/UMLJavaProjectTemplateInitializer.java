/*******************************************************************************
 * Copyright (c) 2022 CEA, Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.profile.java;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.web.application.representations.uml.CDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.services.aqlservices.utils.GenericDiagramService;
import org.eclipse.papyrus.web.services.representations.PapyrusRepresentationDescriptionRegistry;
import org.eclipse.papyrus.web.services.template.TemplateInitializer;
import org.eclipse.papyrus.web.sirius.contributions.DiagramNavigator;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramBuilderService;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramNavigationService;
import org.eclipse.papyrus.web.sirius.contributions.query.NodeMatcher;
import org.eclipse.papyrus.web.sirius.contributions.query.NodeMatcher.BorderNodeStatus;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.web.services.api.projects.IProjectTemplateInitializer;
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
    private static final String UML_MODEL_TITLE = "JavaTemplate.uml"; //$NON-NLS-1$

    private final Logger logger = LoggerFactory.getLogger(UMLJavaProjectTemplateInitializer.class);

    private TemplateInitializer initializerHelper;

    private IDiagramBuilderService diagramBuilderService;

    private GenericDiagramService classDiagramService;

    private PapyrusRepresentationDescriptionRegistry papyrusRepresentationRegistry;

    private IDiagramNavigationService diagramNavigationService;

    private IRepresentationPersistenceService representationPersistenceService;

    public UMLJavaProjectTemplateInitializer(TemplateInitializer initializerHelper, //
            IDiagramBuilderService diagramBuilderService, //
            IDiagramNavigationService diagramNavigationService, //
            PapyrusRepresentationDescriptionRegistry papyrusRepresentationRegistry, //
            GenericDiagramService classDiagramService, //
            IRepresentationPersistenceService representationPersistenceService) {
        this.initializerHelper = Objects.requireNonNull(initializerHelper);
        this.diagramBuilderService = Objects.requireNonNull(diagramBuilderService);
        this.diagramNavigationService = Objects.requireNonNull(diagramNavigationService);
        this.papyrusRepresentationRegistry = Objects.requireNonNull(papyrusRepresentationRegistry);
        this.classDiagramService = Objects.requireNonNull(classDiagramService);
        this.representationPersistenceService = representationPersistenceService;
    }

    @Override
    public boolean canHandle(String templateId) {
        return List.of(UMLJavaTemplateProvider.UML_JAVA_TEMPLATE_ID).contains(templateId);
    }

    @Override
    public Optional<RepresentationMetadata> handle(String templateId, IEditingContext editingContext) {
        Optional<RepresentationMetadata> result = Optional.empty();
        if (UMLJavaTemplateProvider.UML_JAVA_TEMPLATE_ID.equals(templateId)) {
            result = this.initializeUMLJavaProjectContents(editingContext);
        }
        return result;
    }

    private Optional<RepresentationMetadata> initializeUMLJavaProjectContents(IEditingContext editingContext) {
        try {
            Optional<Resource> resource = this.initializerHelper.initializeResourceFromClasspathFile(editingContext, UML_MODEL_TITLE, "JavaTemplate.uml"); //$NON-NLS-1$
            return resource.flatMap(r -> this.createMainClassDiagram(editingContext, r))//
                    .map(diagram -> new RepresentationMetadata(diagram.getId(), diagram.getKind(), diagram.getLabel(), diagram.getDescriptionId()));
        } catch (IOException e) {
            this.logger.error("Error while creating template", e); //$NON-NLS-1$
        }
        return Optional.empty();
    }

    private Optional<Diagram> createMainClassDiagram(IEditingContext editingContext, Resource r) {
        Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes = this.papyrusRepresentationRegistry
                .getConvertedNode(CDDiagramDescriptionBuilder.CD_REP_NAME);
        Model model = (Model) r.getContents().get(0);
        return this.diagramBuilderService.createDiagram(editingContext, diagramDescription -> CDDiagramDescriptionBuilder.CD_REP_NAME.equals(diagramDescription.getLabel()), model, "Main") //$NON-NLS-1$
                .flatMap(diagram -> this.dropClassAndComment(editingContext, convertedNodes, model, diagram))//
                .flatMap(diagram -> this.diagramBuilderService.layoutDiagram(diagram, editingContext))//
                .flatMap(diagram -> {
                    this.representationPersistenceService.save(editingContext, diagram);
                    return Optional.of(diagram);
                });
    }

    private Optional<? extends Diagram> dropClassAndComment(IEditingContext editingContext, Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes,
            Model model, Diagram diagram) {
        org.eclipse.uml2.uml.Class mainClass = (Class) model.getOwnedMembers().stream().filter(m -> m instanceof Class && "Main".equals(m.getName())).findFirst().get(); //$NON-NLS-1$
        return this.diagramBuilderService.updateDiagram(diagram, editingContext, diagramContext -> {
            // Get the linked comment
            Comment classComment = model.getOwnedComments().stream().filter(c -> c.getAnnotatedElements().contains(mainClass)).findFirst().get();
            this.classDiagramService.drop(mainClass, null, editingContext, diagramContext, convertedNodes);
            this.classDiagramService.drop(classComment, null, editingContext, diagramContext, convertedNodes);
        }).flatMap(diag -> this.dropOperationsOnClass(editingContext, convertedNodes, mainClass, diag));
    }

    private Optional<Diagram> dropOperationsOnClass(IEditingContext editingContext, Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes,
            org.eclipse.uml2.uml.Class mainClass, Diagram diag) {
        return this.diagramBuilderService.updateDiagram(diag, editingContext, diagramContext -> {
            for (Operation operation : mainClass.getOwnedOperations()) {
                NodeMatcher mainClassNodeMatcher = this.createOperationCompartmentNodeMatcher(mainClass, diag, convertedNodes);
                Node operationCompartement = this.diagramNavigationService.getMatchingNodes(diag, editingContext, mainClassNodeMatcher).get(0);
                this.classDiagramService.drop(operation, operationCompartement, editingContext, diagramContext, convertedNodes);
            }
        });
    }

    private NodeMatcher createOperationCompartmentNodeMatcher(org.eclipse.uml2.uml.Class mainClass, Diagram diagram,
            Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes) {
        DiagramNavigator diagramNav = new DiagramNavigator(this.diagramNavigationService, diagram, convertedNodes);
        return NodeMatcher.buildSemanticAndNodeMatcher(BorderNodeStatus.BASIC_NODE, o -> o == mainClass, v -> this.filter(diagramNav, v));
    }

    private boolean filter(DiagramNavigator diagramNav, Node v) {
        return "CD_Class_Operations_CompartmentNode".equals(diagramNav.getDescription(v).get().getName()); //$NON-NLS-1$
    }

}
