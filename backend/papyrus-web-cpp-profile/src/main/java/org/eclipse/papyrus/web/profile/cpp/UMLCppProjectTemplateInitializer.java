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
package org.eclipse.papyrus.web.profile.cpp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.uml.domain.services.EMFUtils;
import org.eclipse.papyrus.web.application.representations.uml.CDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.SMDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.services.aqlservices.statemachine.StateMachineDiagramService;
import org.eclipse.papyrus.web.services.aqlservices.utils.GenericDiagramService;
import org.eclipse.papyrus.web.services.representations.PapyrusRepresentationDescriptionRegistry;
import org.eclipse.papyrus.web.services.template.TemplateInitializer;
import org.eclipse.papyrus.web.sirius.contributions.DiagramNavigator;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramBuilderService;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramNavigationService;
import org.eclipse.papyrus.web.sirius.contributions.query.NodeMatcher;
import org.eclipse.papyrus.web.sirius.contributions.query.NodeMatcher.BorderNodeStatus;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
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
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Initializes the contents of projects created from a UML project template.
 *
 * @author Arthur Daussy
 */
@Configuration
public class UMLCppProjectTemplateInitializer implements IProjectTemplateInitializer {

    private static final List<String> HANDLED_DIAGRAMS = List.of(UMLCppProjectTemplateProvider.UML_CPP_TEMPLATE_ID, UMLCppProjectTemplateProvider.UML_CPP_SM_TEMPLATE_ID);

    private static final String CPP_TEMPLATE_FILE = "CppTemplate.uml";

    private static final String CPP_SM_TEMPLATE_FILE = "SimpleSM.uml";

    private final Logger logger = LoggerFactory.getLogger(UMLCppProjectTemplateInitializer.class);

    private TemplateInitializer initializerHelper;

    private PapyrusRepresentationDescriptionRegistry papyrusRepresentationRegistry;

    private StateMachineDiagramService stateMachineDiagramService;

    private IDiagramBuilderService diagramBuilderService;

    private IDiagramNavigationService diagramNavigationService;

    private GenericDiagramService classDiagramService;

    private IRepresentationPersistenceService representationPersistenceService;

    public UMLCppProjectTemplateInitializer(TemplateInitializer templateInitializer, //
            StateMachineDiagramService stateMachineDiagramService, //
            GenericDiagramService packageDiagramService, IDiagramBuilderService diagramBuilderService, //
            IDiagramNavigationService diagramNavigationService, //
            PapyrusRepresentationDescriptionRegistry papyrusRepresentationRegistry, //
            IRepresentationPersistenceService representationPersistenceService) {
        this.stateMachineDiagramService = Objects.requireNonNull(stateMachineDiagramService);
        this.classDiagramService = Objects.requireNonNull(packageDiagramService);
        this.diagramBuilderService = Objects.requireNonNull(diagramBuilderService);
        this.diagramNavigationService = Objects.requireNonNull(diagramNavigationService);
        this.papyrusRepresentationRegistry = Objects.requireNonNull(papyrusRepresentationRegistry);
        this.initializerHelper = Objects.requireNonNull(templateInitializer);
        this.representationPersistenceService = representationPersistenceService;
    }

    @Override
    public boolean canHandle(String templateId) {
        return HANDLED_DIAGRAMS.contains(templateId);
    }

    @Override
    public Optional<RepresentationMetadata> handle(String templateId, IEditingContext editingContext) {
        Optional<RepresentationMetadata> result = Optional.empty();
        if (UMLCppProjectTemplateProvider.UML_CPP_TEMPLATE_ID.equals(templateId)) {
            result = this.initializeCppProjectContents(editingContext);
        } else if (UMLCppProjectTemplateProvider.UML_CPP_SM_TEMPLATE_ID.equals(templateId)) {
            result = this.initializeCppSMProjectContents(editingContext);
        }
        return result;
    }

    private Optional<RepresentationMetadata> initializeCppProjectContents(IEditingContext editingContext) {
        try {
            Optional<Resource> resource = this.initializerHelper.initializeResourceFromClasspathFile(editingContext, CPP_TEMPLATE_FILE, CPP_TEMPLATE_FILE);
            return resource.flatMap(r -> this.createMainCppClassDiagram(editingContext, r))//
                    .map(diagram -> new RepresentationMetadata(diagram.getId(), diagram.getKind(), diagram.getLabel(), diagram.getDescriptionId()));
        } catch (IOException e) {
            this.logger.error("Error while creating template", e);
        }
        return Optional.empty();
    }

    private Optional<Diagram> createMainCppClassDiagram(IEditingContext editingContext, Resource r) {
        Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes = this.papyrusRepresentationRegistry
                .getConvertedNode(CDDiagramDescriptionBuilder.CD_REP_NAME);
        return this.diagramBuilderService
                .createDiagram(editingContext, diagramDescription -> CDDiagramDescriptionBuilder.CD_REP_NAME.equals(diagramDescription.getLabel()), r.getContents().get(0), "Main")
                .flatMap(diagram -> this.semanticDropMainClassAndComment(editingContext, r, convertedNodes, diagram))//
                .flatMap(diagram -> {
                    this.representationPersistenceService.save(editingContext, diagram);
                    return Optional.of(diagram);
                });

    }

    private Optional<? extends Diagram> semanticDropMainClassAndComment(IEditingContext editingContext, Resource r,
            Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes, Diagram diagram) {
        // Drop the main class and its comment
        Model model = (Model) r.getContents().get(0);
        org.eclipse.uml2.uml.Class mainClass = (Class) model.getOwnedMembers().stream().filter(m -> m instanceof Class && "Main".equals(m.getName())).findFirst().get();
        Comment comment = model.getOwnedComments().stream().filter(c -> c.getAnnotatedElements().contains(mainClass)).findFirst().get();
        return this.diagramBuilderService.updateDiagram(diagram, editingContext, diagramContext -> {
            this.classDiagramService.semanticDrop(mainClass, null, editingContext, diagramContext, convertedNodes);
            this.classDiagramService.semanticDrop(comment, null, editingContext, diagramContext, convertedNodes);
        }).flatMap(diag -> this.semanticDropOperationsOnClass(editingContext, convertedNodes, mainClass, diag));
    }

    private Optional<Diagram> semanticDropOperationsOnClass(IEditingContext editingContext, Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes,
            org.eclipse.uml2.uml.Class mainClass, Diagram diag) {
        return this.diagramBuilderService.updateDiagram(diag, editingContext, diagramContext -> {
            for (Operation operation : mainClass.getOwnedOperations()) {
                NodeMatcher mainClassNodeMatcher = this.createOperationCompartmentNodeMatcher(mainClass, diag, convertedNodes);
                Node operationCompartement = this.diagramNavigationService.getMatchingNodes(diag, editingContext, mainClassNodeMatcher).get(0);
                this.classDiagramService.semanticDrop(operation, operationCompartement, editingContext, diagramContext, convertedNodes);
            }
        });
    }

    private NodeMatcher createOperationCompartmentNodeMatcher(org.eclipse.uml2.uml.Class mainClass, Diagram diagram,
            Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes) {
        DiagramNavigator diagramNav = new DiagramNavigator(this.diagramNavigationService, diagram, convertedNodes);
        return NodeMatcher.buildSemanticAndNodeMatcher(BorderNodeStatus.BASIC_NODE, o -> o == mainClass, v -> this.filter(diagramNav, v));
    }

    private boolean filter(DiagramNavigator diagramNav, Node v) {
        return "CD_Class_Operations_CompartmentNode".equals(diagramNav.getDescription(v).get().getName());
    }

    private Optional<RepresentationMetadata> initializeCppSMProjectContents(IEditingContext editingContext) {
        List<Diagram> diagrams = new ArrayList<>();
        try {
            Optional<Resource> resource = this.initializerHelper.initializeResourceFromClasspathFile(editingContext, CPP_SM_TEMPLATE_FILE, CPP_SM_TEMPLATE_FILE);

            resource.ifPresent(r -> {
                this.createMainCppSMClassDiagram(editingContext, r).ifPresent(diagrams::add);
                EMFUtils.allContainedObjectOfType(r, StateMachine.class).forEach(stateMachine -> this.createStateMachineDiagram(stateMachine, editingContext).ifPresent(diagrams::add));

            });
        } catch (IOException e) {
            this.logger.error("Error while creating template", e);
        }
        if (diagrams.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(this.toMetadata(diagrams.get(0)));
        }
    }

    private Optional<Diagram> createMainCppSMClassDiagram(IEditingContext editingContext, Resource r) {

        Optional<Diagram> optDiagram = this.diagramBuilderService.createDiagram(editingContext, diagramDescription -> CDDiagramDescriptionBuilder.CD_REP_NAME.equals(diagramDescription.getLabel()),
                r.getContents().get(0), "SimpleSM_");

        Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes = this.papyrusRepresentationRegistry
                .getConvertedNode(CDDiagramDescriptionBuilder.CD_REP_NAME);
        Model model = (Model) r.getContents().get(0);

        return optDiagram.flatMap(diagram -> this.dropModelAndComment(editingContext, convertedNodes, model, diagram))
                .flatMap(diagram -> this.dropMainClassAndComment(editingContext, convertedNodes, model, diagram))//
                .flatMap(diagram -> {
                    this.representationPersistenceService.save(editingContext, diagram);
                    return Optional.of(diagram);
                });

    }

    private Optional<? extends Diagram> dropMainClassAndComment(IEditingContext editingContext, Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes,
            Model model, Diagram diagram) {
        org.eclipse.uml2.uml.Class mainClass = (Class) model.getOwnedMembers().stream().filter(m -> m instanceof Class && "SimpleSM".equals(m.getName())).findFirst().get();
        return this.diagramBuilderService.updateDiagram(diagram, editingContext, diagramContext -> {

            // Get the linked comment
            Comment classComment = model.getOwnedComments().stream().filter(c -> c.getAnnotatedElements().contains(mainClass)).findFirst().get();
            this.diagramNavigationService.getMatchingNodes(diagram, editingContext, NodeMatcher.buildSemanticMatcher(BorderNodeStatus.BASIC_NODE, sem -> sem == model)).forEach(packNode -> {
                this.classDiagramService.semanticDrop(mainClass, packNode, editingContext, diagramContext, convertedNodes);
                this.classDiagramService.semanticDrop(classComment, packNode, editingContext, diagramContext, convertedNodes);
            });
        }).flatMap(diag -> this.semanticDropOperationsOnClass(editingContext, convertedNodes, mainClass, diag));
    }

    private Optional<? extends Diagram> dropModelAndComment(IEditingContext editingContext, Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes,
            Model model, Diagram diagram) {
        // Get the linked comment
        Comment comment = model.getOwnedComments().stream().filter(c -> c.getAnnotatedElements().contains(model)).findFirst().get();
        return this.diagramBuilderService.updateDiagram(diagram, editingContext, diagramContext -> {
            this.classDiagramService.semanticDrop(model, null, editingContext, diagramContext, convertedNodes);
            this.classDiagramService.semanticDrop(comment, null, editingContext, diagramContext, convertedNodes);
        });
    }

    private Optional<Diagram> createStateMachineDiagram(StateMachine stateMachine, IEditingContext editingContext) {
        Optional<Diagram> diag = this.diagramBuilderService.createDiagram(editingContext, diagramDescription -> SMDDiagramDescriptionBuilder.SMD_REP_NAME.equals(diagramDescription.getLabel()),
                stateMachine, "SM Diagram");

        return diag.flatMap(diagram -> this.diagramBuilderService.refreshDiagram(diagram, editingContext))// Display
                                                                                                          // synchronized
                                                                                                          // elements
                .flatMap(diagram -> this.diagramBuilderService.updateDiagram(diagram, editingContext,
                        diagramContext -> this.fillStateMachineDiagram(stateMachine, editingContext, diagram, diagramContext)))
                .flatMap(diagram -> {
                    this.representationPersistenceService.save(editingContext, diagram);
                    return Optional.of(diagram);
                });
    }

    private void fillStateMachineDiagram(StateMachine stateMachine, IEditingContext editingContext, Diagram diagram, IDiagramContext diagramContext) {
        Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes = this.papyrusRepresentationRegistry
                .getConvertedNode(SMDDiagramDescriptionBuilder.SMD_REP_NAME);
        // Display all state in each region
        for (Region region : stateMachine.getRegions()) {
            NodeMatcher regionNodeMatcher = NodeMatcher.buildSemanticMatcher(BorderNodeStatus.BASIC_NODE, o -> o == region);
            List<Node> regionReps = this.diagramNavigationService.getMatchingNodes(diagram, editingContext, regionNodeMatcher);
            for (Node regionNode : regionReps) {
                // Drop all states
                EList<Vertex> vertices = region.getSubvertices();
                for (var vertex : vertices) {
                    this.stateMachineDiagramService.semanticDrop(vertex, regionNode, editingContext, diagramContext, convertedNodes);
                }
                // Drop all comments
                for (var comment : region.getOwnedComments()) {
                    this.stateMachineDiagramService.semanticDrop(comment, regionNode, editingContext, diagramContext, convertedNodes);
                }

            }
        }
    }

    private RepresentationMetadata toMetadata(Diagram diagram) {
        return new RepresentationMetadata(diagram.getId(), diagram.getKind(), diagram.getLabel(), diagram.getDescriptionId());
    }

}
