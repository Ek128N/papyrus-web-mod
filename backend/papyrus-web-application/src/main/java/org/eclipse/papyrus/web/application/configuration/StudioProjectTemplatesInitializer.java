/*****************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.papyrus.web.services.representations.PapyrusRepresentationDescriptionRegistry;
import org.eclipse.papyrus.web.sirius.contributions.StereotypeBuilder;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.domain.Attribute;
import org.eclipse.sirius.components.domain.DataType;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.DomainFactory;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.domain.Relation;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.CreateInstance;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeStyle;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.components.view.diagram.provider.DefaultToolsFactory;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.services.api.id.IDParser;
import org.eclipse.sirius.web.services.api.projects.IProjectTemplateInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Provides Studio-specific project templates initializers.
 *
 * @author Arthur Daussy
 */
@Configuration
public class StudioProjectTemplatesInitializer implements IProjectTemplateInitializer {

    private static final String NAME_ATTRIBUTE = "name";

    private static final String DOMAIN_DOCUMENT_NAME = "DomainNewModel";

    private static final String VIEW_DOCUMENT_NAME = "ViewNewModel";

    private final Logger logger = LoggerFactory.getLogger(StudioProjectTemplatesInitializer.class);

    private final IProjectRepository projectRepository;

    private final IDocumentRepository documentRepository;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramCreationService diagramCreationService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final StereotypeBuilder stereotypeBuilder;

    private PapyrusRepresentationDescriptionRegistry papyrusViewRegistry;

    public StudioProjectTemplatesInitializer(IProjectRepository projectRepository, IDocumentRepository documentRepository,
            IRepresentationDescriptionSearchService representationDescriptionSearchService, IDiagramCreationService diagramCreationService,
            IRepresentationPersistenceService representationPersistenceService, MeterRegistry meterRegistry, PapyrusRepresentationDescriptionRegistry papyrusViewRegistry) {
        this.papyrusViewRegistry = papyrusViewRegistry;
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.documentRepository = Objects.requireNonNull(documentRepository);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramCreationService = Objects.requireNonNull(diagramCreationService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.stereotypeBuilder = new StereotypeBuilder("studio-template-initializer", meterRegistry);
    }

    @Override
    public boolean canHandle(String templateId) {
        return List.of(StudioProjectTemplatesProvider.STUDIO_TEMPLATE_ID, StudioProjectTemplatesProvider.PAPYRUS_STUDIO_TEMPLATE_ID).contains(templateId);
    }

    @Override
    public Optional<RepresentationMetadata> handle(String templateId, IEditingContext editingContext) {
        final Optional<RepresentationMetadata> repsentationMetadata;
        if (StudioProjectTemplatesProvider.STUDIO_TEMPLATE_ID.equals(templateId)) {
            repsentationMetadata = this.initializeStudioProject(editingContext);
        } else if (StudioProjectTemplatesProvider.PAPYRUS_STUDIO_TEMPLATE_ID.equals(templateId)) {
            repsentationMetadata = this.initializePapyrusStudioProject(editingContext);
        } else {
            repsentationMetadata = Optional.empty();
        }
        return repsentationMetadata;
    }

    private Optional<RepresentationMetadata> initializePapyrusStudioProject(IEditingContext editingContext) {
        Optional<RepresentationMetadata> result = Optional.empty();

        Optional<AdapterFactoryEditingDomain> optionalEditingDomain = Optional.of(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain);

        Optional<UUID> editingContextUUID = new IDParser().parse(editingContext.getId());
        if (optionalEditingDomain.isPresent() && editingContextUUID.isPresent()) {
            AdapterFactoryEditingDomain adapterFactoryEditingDomain = optionalEditingDomain.get();
            ResourceSet resourceSet = adapterFactoryEditingDomain.getResourceSet();

            var optionalDomainDocumentEntity = this.createDocument(editingContextUUID.get(), "UML Detail View Builder", this.getDetailViewContent());

            if (optionalDomainDocumentEntity.isPresent()) {
                this.addToResouce(resourceSet, optionalDomainDocumentEntity.get(), DOMAIN_DOCUMENT_NAME);
            }

            for (org.eclipse.sirius.components.view.diagram.DiagramDescription diagram : this.papyrusViewRegistry.getViewDiagrams()) {
                View copiedView = (View) EcoreUtil.copy(diagram.eContainer());
                RepresentationDescription copiedDiagram = copiedView.getDescriptions().get(0);
                String name = copiedDiagram.getName() + " Studio";
                copiedDiagram.setName(name);

                var optionalViewDocumentEntity = this.createDocument(editingContextUUID.get(), name, this.stereotypeBuilder.getStereotypeBody(Collections.singletonList(copiedView)));
                if (optionalViewDocumentEntity.isPresent()) {
                    this.addToResouce(resourceSet, optionalViewDocumentEntity.get(), name);
                }

            }

        }
        return result;
    }

    public void addToResouce(ResourceSet resourceSet, DocumentEntity documentEntity, String name) {

        JsonResource resource = new JSONResourceFactory().createResourceFromPath(documentEntity.getId().toString());
        try (var inputStream = new ByteArrayInputStream(documentEntity.getContent().getBytes())) {
            resource.load(inputStream, null);

        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }

        resource.eAdapters().add(new ResourceMetadataAdapter(name));

        resourceSet.getResources().add(resource);
    }

    private Optional<DocumentEntity> createDocument(UUID editingContextId, String documentName, String content) {
        return this.projectRepository.findById(editingContextId).map(projectEntity -> {
            DocumentEntity documentEntity = new DocumentEntity();
            documentEntity.setProject(projectEntity);
            documentEntity.setName(documentName);
            documentEntity.setContent(content);

            documentEntity = this.documentRepository.save(documentEntity);
            return documentEntity;
        });
    }

    private Optional<RepresentationMetadata> initializeStudioProject(IEditingContext editingContext) {
        Optional<RepresentationMetadata> result = Optional.empty();

        Optional<AdapterFactoryEditingDomain> optionalEditingDomain = Optional.of(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain);

        Optional<UUID> editingContextUUID = new IDParser().parse(editingContext.getId());
        if (optionalEditingDomain.isPresent() && editingContextUUID.isPresent()) {
            AdapterFactoryEditingDomain adapterFactoryEditingDomain = optionalEditingDomain.get();
            ResourceSet resourceSet = adapterFactoryEditingDomain.getResourceSet();

            var optionalDomainDocumentEntity = this.createDocument(editingContextUUID.get(), DOMAIN_DOCUMENT_NAME, this.getDomainContent());

            String domainName = "sampledomain";

            if (optionalDomainDocumentEntity.isPresent()) {
                DocumentEntity documentEntity = optionalDomainDocumentEntity.get();

                JsonResource resource = new JSONResourceFactory().createResourceFromPath(documentEntity.getId().toString());
                try (var inputStream = new ByteArrayInputStream(documentEntity.getContent().getBytes())) {
                    resource.load(inputStream, null);

                    var optionalTopographyDiagram = this.findDiagramDescription(editingContext, "Domain");
                    if (optionalTopographyDiagram.isPresent()) {
                        DiagramDescription topographyDiagram = optionalTopographyDiagram.get();
                        Object semanticTarget = resource.getContents().get(0);
                        domainName = ((Domain) semanticTarget).getName();

                        Diagram diagram = this.diagramCreationService.create(topographyDiagram.getLabel(), semanticTarget, topographyDiagram, editingContext);
                        this.representationPersistenceService.save(editingContext, diagram);

                        result = Optional.of(new RepresentationMetadata(diagram.getId(), diagram.getKind(), diagram.getLabel(), diagram.getDescriptionId()));
                    }
                } catch (IOException exception) {
                    this.logger.warn(exception.getMessage(), exception);
                }

                resource.eAdapters().add(new ResourceMetadataAdapter(DOMAIN_DOCUMENT_NAME));

                resourceSet.getResources().add(resource);
            }

            final String theDomainName = domainName;
            var optionalViewDocumentEntity = this.projectRepository.findById(editingContextUUID.get()).map(projectEntity -> {
                DocumentEntity documentEntity = new DocumentEntity();
                documentEntity.setProject(projectEntity);
                documentEntity.setName(VIEW_DOCUMENT_NAME);
                documentEntity.setContent(this.getViewContent(theDomainName));

                documentEntity = this.documentRepository.save(documentEntity);
                return documentEntity;
            });
            if (optionalViewDocumentEntity.isPresent()) {
                DocumentEntity documentEntity = optionalViewDocumentEntity.get();
                JsonResource resource = new JSONResourceFactory().createResourceFromPath(documentEntity.getId().toString());
                resource.eAdapters().add(new ResourceMetadataAdapter(VIEW_DOCUMENT_NAME));
                try (var inputStream = new ByteArrayInputStream(documentEntity.getContent().getBytes())) {
                    resource.load(inputStream, null);
                } catch (IOException exception) {
                    this.logger.warn(exception.getMessage(), exception);
                }
                resourceSet.getResources().add(resource);
            }
        }
        return result;
    }

    private String getDetailViewContent() {
        View view = new UMLDetailViewFromBuilder(UMLPropertiesConfigurer.UML_DETAIL_VIEW_NAME + " Studio").build();

        return this.stereotypeBuilder.getStereotypeBody(Collections.singletonList(view));
    }

    private String getDomainContent() {
        Domain domain = DomainFactory.eINSTANCE.createDomain();
        domain.setName(new SampleDomainNameProvider().getSampleDomainName());

        Entity root = DomainFactory.eINSTANCE.createEntity();
        root.setName("Root");
        domain.getTypes().add(root);

        Entity entity1 = DomainFactory.eINSTANCE.createEntity();
        entity1.setName("Entity1");
        domain.getTypes().add(entity1);

        Relation entity1s = DomainFactory.eINSTANCE.createRelation();
        entity1s.setName("entity1s");
        entity1s.setContainment(true);
        entity1s.setOptional(true);
        entity1s.setMany(true);
        entity1s.setTargetType(entity1);
        root.getRelations().add(entity1s);

        Entity entity2 = DomainFactory.eINSTANCE.createEntity();
        entity2.setName("Entity2");
        domain.getTypes().add(entity2);

        Relation entity2s = DomainFactory.eINSTANCE.createRelation();
        entity2s.setName("entity2s");
        entity2s.setContainment(true);
        entity2s.setOptional(true);
        entity2s.setMany(true);
        entity2s.setTargetType(entity2);
        root.getRelations().add(entity2s);

        Relation linkedTo = DomainFactory.eINSTANCE.createRelation();
        linkedTo.setName("linkedTo");
        linkedTo.setContainment(false);
        linkedTo.setOptional(true);
        linkedTo.setMany(true);
        linkedTo.setTargetType(entity2);
        entity1.getRelations().add(linkedTo);

        this.addAttribute(entity1, NAME_ATTRIBUTE, DataType.STRING);
        this.addAttribute(entity1, "attribute2", DataType.BOOLEAN);
        this.addAttribute(entity1, "attribute3", DataType.NUMBER);
        this.addAttribute(entity2, NAME_ATTRIBUTE, DataType.STRING);

        return this.stereotypeBuilder.getStereotypeBody(List.of(domain));
    }

    private void addAttribute(Entity entity, String name, DataType type) {
        Attribute attr = DomainFactory.eINSTANCE.createAttribute();
        attr.setName(name);
        attr.setOptional(true);
        attr.setType(type);
        entity.getAttributes().add(attr);
    }

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    private String getViewContent(String domainName) {
        View view = ViewFactory.eINSTANCE.createView();
        DefaultToolsFactory defaultToolsFactory = new DefaultToolsFactory();

        org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription = DiagramFactory.eINSTANCE.createDiagramDescription();
        viewDiagramDescription.setName(domainName + " Diagram Description");
        viewDiagramDescription.setDomainType(domainName + "::Root");
        viewDiagramDescription.setTitleExpression(domainName + " diagram");
        viewDiagramDescription.setPalette(defaultToolsFactory.createDefaultDiagramPalette());
        view.getDescriptions().add(viewDiagramDescription);

        view.getColorPalettes().add(this.createColorPalette());

        NodeDescription entity1Node = DiagramFactory.eINSTANCE.createNodeDescription();
        entity1Node.setName("Entity1 Node");
        entity1Node.setDomainType(domainName + "::Entity1");
        entity1Node.setSemanticCandidatesExpression("aql:self.eContents()");
        entity1Node.setLabelExpression("aql:self.name");
        entity1Node.setSynchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED);
        entity1Node.setStyle(this.createRectangularNodeStyle(view, "color_blue", "border_blue"));
        entity1Node.setPalette(defaultToolsFactory.createDefaultNodePalette());

        viewDiagramDescription.getNodeDescriptions().add(entity1Node);
        viewDiagramDescription.getPalette().getNodeTools().add(this.createNewInstanceTool(domainName + "::Entity1", "entity1s"));

        NodeDescription entity2Node = DiagramFactory.eINSTANCE.createNodeDescription();
        entity2Node.setName("Entity2 Node");
        entity2Node.setDomainType(domainName + "::Entity2");
        entity2Node.setSemanticCandidatesExpression("aql:self.eContents()");
        entity2Node.setLabelExpression("aql:self.name");
        entity2Node.setSynchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED);
        entity2Node.setStyle(this.createRectangularNodeStyle(view, "color_green", "border_green"));
        entity2Node.setPalette(defaultToolsFactory.createDefaultNodePalette());

        viewDiagramDescription.getNodeDescriptions().add(entity2Node);
        viewDiagramDescription.getPalette().getNodeTools().add(this.createNewInstanceTool(domainName + "::Entity2", "entity2s"));

        EdgeTool createLinkTo = DiagramFactory.eINSTANCE.createEdgeTool();
        createLinkTo.setName("Link to");
        createLinkTo.getTargetElementDescriptions().add(entity2Node);
        ChangeContext gotoSemanticSource = ViewFactory.eINSTANCE.createChangeContext();
        gotoSemanticSource.setExpression("aql:semanticEdgeSource");
        createLinkTo.getBody().add(gotoSemanticSource);
        SetValue setLink = ViewFactory.eINSTANCE.createSetValue();
        setLink.setFeatureName("linkedTo");
        setLink.setValueExpression("aql:semanticEdgeTarget");
        gotoSemanticSource.getChildren().add(setLink);
        entity1Node.getPalette().getEdgeTools().add(createLinkTo);

        EdgeDescription linkedToEdge = DiagramFactory.eINSTANCE.createEdgeDescription();
        linkedToEdge.setName("LinkedTo Edge");
        linkedToEdge.setSemanticCandidatesExpression("");
        linkedToEdge.setLabelExpression("");
        linkedToEdge.getSourceNodeDescriptions().add(entity1Node);
        linkedToEdge.setSourceNodesExpression("aql:self");
        linkedToEdge.getTargetNodeDescriptions().add(entity2Node);
        linkedToEdge.setTargetNodesExpression("aql:self.linkedTo");
        linkedToEdge.setPalette(defaultToolsFactory.createDefaultEdgePalette());
        viewDiagramDescription.getEdgeDescriptions().add(linkedToEdge);

        EdgeStyle edgeStyle = DiagramFactory.eINSTANCE.createEdgeStyle();
        edgeStyle.setColor(this.getColorFromPalette(view, "color_dark"));
        linkedToEdge.setStyle(edgeStyle);

        return this.stereotypeBuilder.getStereotypeBody(List.of(view));
    }

    private RectangularNodeStyleDescription createRectangularNodeStyle(View view, String color, String borderColor) {
        RectangularNodeStyleDescription entity2Style = DiagramFactory.eINSTANCE.createRectangularNodeStyleDescription();
        entity2Style.setColor(this.getColorFromPalette(view, color));
        entity2Style.setBorderColor(this.getColorFromPalette(view, borderColor));
        entity2Style.setBorderRadius(3);
        return entity2Style;
    }

    private NodeTool createNewInstanceTool(String typeName, String referenceName) {
        String simpleName = typeName.split("::")[1];
        NodeTool tool = DiagramFactory.eINSTANCE.createNodeTool();
        tool.setName("New " + simpleName);

        CreateInstance createInstance = ViewFactory.eINSTANCE.createCreateInstance();
        createInstance.setReferenceName(referenceName);
        createInstance.setTypeName(typeName);
        createInstance.setVariableName("newInstance");
        tool.getBody().add(createInstance);

        ChangeContext gotoNewInstance = ViewFactory.eINSTANCE.createChangeContext();
        gotoNewInstance.setExpression("aql:newInstance");
        createInstance.getChildren().add(gotoNewInstance);

        SetValue setInitialName = ViewFactory.eINSTANCE.createSetValue();
        setInitialName.setFeatureName(NAME_ATTRIBUTE);
        setInitialName.setValueExpression("New" + simpleName);
        gotoNewInstance.getChildren().add(setInitialName);

        return tool;
    }

    private Optional<DiagramDescription> findDiagramDescription(IEditingContext editingContext, String label) {
        return this.representationDescriptionSearchService.findAll(editingContext).values().stream()
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .filter(diagramDescription -> Objects.equals(label, diagramDescription.getLabel()))
                .findFirst();
    }

    private ColorPalette createColorPalette() {
        var colorPalette = ViewFactory.eINSTANCE.createColorPalette();

        colorPalette.getColors().add(this.createFixedColor("color_dark", "#002639"));
        colorPalette.getColors().add(this.createFixedColor("color_blue", "#E5F5F8"));
        colorPalette.getColors().add(this.createFixedColor("color_green", "#B1D8B7"));
        colorPalette.getColors().add(this.createFixedColor("border_blue", "#33B0C3"));
        colorPalette.getColors().add(this.createFixedColor("border_green", "#76B947"));

        return colorPalette;
    }

    private FixedColor createFixedColor(String name, String value) {
        var fixedColor = ViewFactory.eINSTANCE.createFixedColor();
        fixedColor.setName(name);
        fixedColor.setValue(value);
        return fixedColor;
    }

    private UserColor getColorFromPalette(View view, String colorName) {
        return view.getColorPalettes().stream().findFirst().map(ColorPalette::getColors).stream().flatMap(Collection::stream).filter(userColor -> userColor.getName().equals(colorName)).findFirst()
                .orElse(null);
    }

}
