/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.papyrus.web.application.representations;

import static org.eclipse.papyrus.uml.domain.services.EMFUtils.allContainedObjectOfType;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.uml.domain.services.EMFUtils;
import org.eclipse.papyrus.web.application.representations.uml.AbstractRepresentationDescriptionBuilder;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.emf.IViewConverter;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registry that keeps track of all {@link IRepresentationDescription}s used in Papyrus application.
 *
 * @author Arthur Daussy
 */
public class PapyrusRepresentationDescriptionRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(PapyrusRepresentationDescriptionRegistry.class);

    private IDiagramIdProvider idProvider;

    private List<Match> diagrams = new ArrayList<>();

    private Map<String, DiagramDescription> viewDiagramDescriptionById = new HashMap<>();

    private Map<String, org.eclipse.sirius.components.diagrams.description.DiagramDescription> apiDiagramDescriptionById = new HashMap<>();

    private Map<String, DiagramElementDescription> viewDiagramElementDescriptionById = new HashMap<>();

    private Map<String, IDiagramElementDescription> apiDiagramElementDescriptionById = new HashMap<>();

    private Map<FormDescription, org.eclipse.sirius.components.forms.description.FormDescription> formsConvertion = new HashMap<>();

    private ResourceSetImpl viewResourceSet;

    private Registry ePackagesRegistry;

    private IViewConverter viewConverter;

    public PapyrusRepresentationDescriptionRegistry(IDiagramIdProvider idProvider, EPackage.Registry ePackagesRegistry, IViewConverter viewConverter) {
        this.idProvider = idProvider;
        this.ePackagesRegistry = ePackagesRegistry;
        this.viewConverter = viewConverter;
        this.viewResourceSet = new ResourceSetImpl();
        this.viewResourceSet.eAdapters().add(new ECrossReferenceAdapter());
    }

    /**
     * Register a new diagram in this registry.
     *
     * <p>
     * This method should be called during initialization phase. For example during a @PostConstruct in a service class.
     * </p>
     *
     * @param diagramBuilder
     *            a diagram builder.
     */
    public void registerDiagram(AbstractRepresentationDescriptionBuilder diagramBuilder) {
        List<EPackage> staticEPackages = this.ePackagesRegistry.values().stream()
                .filter(EPackage.class::isInstance)
                .map(EPackage.class::cast)
                .collect(Collectors.toList());

        View view = this.createView(diagramBuilder.getRepresentationName());

        DiagramDescription diagramDescription = diagramBuilder.createDiagramDescription(view);

        Resource eResource = view.eResource();

        this.generateStaticIds(diagramDescription, eResource);

        List<IRepresentationDescription> representationDescriptions = this.viewConverter.convert(Collections.singletonList(view), staticEPackages);

        // Workaround https://github.com/eclipse-sirius/sirius-components/issues/1345
        for (var description : representationDescriptions) {
            if (description instanceof org.eclipse.sirius.components.diagrams.description.DiagramDescription) {
                this.add(diagramDescription, (org.eclipse.sirius.components.diagrams.description.DiagramDescription) description);
                LOGGER.info(MessageFormat.format("Contributing representation {0} with id {1}", description.getLabel(), description.getId()));
            }
        }

        view.eAdapters().add(new UnmodifableModel());

    }

    /**
     * Register a new form in this registry.
     *
     * <p>
     * This method should be called during initialization phase. For example during a @PostConstruct in a service class.
     * </p>
     *
     * @param viewFormDescription
     *            the view form
     * @param formDescription
     *            the converted form
     */
    // This can be improved by relocated the conversion from View to API inside this class
    public void registerForm(FormDescription viewFormDescription, org.eclipse.sirius.components.forms.description.FormDescription formDescription) {
        this.formsConvertion.put(viewFormDescription, formDescription);
        viewFormDescription.eAdapters().add(new UnmodifableModel());
    }

    private View createView(String representatioName) {
        // Required to have a unique URIs - workaround https://github.com/eclipse-sirius/sirius-components/issues/1345
        View view = ViewFactory.eINSTANCE.createView();
        JSONResourceFactory jsonResourceFactory = new JSONResourceFactory();
        var uri = jsonResourceFactory.createResourceURI(UUID.nameUUIDFromBytes(representatioName.getBytes()).toString());

        JsonResource impl = jsonResourceFactory.createResource(uri);

        impl.getContents().add(view);
        this.viewResourceSet.getResources().add(impl);

        return view;
    }

    private void generateStaticIds(RepresentationDescription diagramDescription, Resource eResource) {
        TreeIterator<EObject> contentIterator = eResource.getAllContents();
        Set<String> uniqueDescriptionIds = new HashSet<>();
        while (contentIterator.hasNext()) {
            EObject next = contentIterator.next();
            final String id;
            if (next instanceof DiagramElementDescription desc) {
                id = this.getUniqueIdentifier(diagramDescription, uniqueDescriptionIds, desc);
            } else {
                id = EcoreUtil.getURI(next).toString();
            }
            ((JsonResource) eResource).setID(next, UUID.nameUUIDFromBytes(id.getBytes()).toString());
        }
    }

    private String getUniqueIdentifier(RepresentationDescription diagramDescription, Set<String> uniqueDescriptionIds, DiagramElementDescription desc) {
        String name = desc.getName();

        if (name == null || name.isBlank()) {
            throw new IllegalStateException("Description with no name are forbidden " + desc);
        } else if (uniqueDescriptionIds.contains(name)) {
            throw new IllegalStateException("Duplicated description name " + name + " in representation " + diagramDescription.getName());
        } else {
            return name;
        }
    }

    private void add(DiagramDescription description, org.eclipse.sirius.components.diagrams.description.DiagramDescription converted) {
        String viewId = this.idProvider.getId(description);
        String apiId = converted.getId();

        if (!Objects.equals(apiId, viewId)) {
            throw new IllegalStateException("Invalid diagram ids. View id =" + viewId + " API id=" + apiId);
        }
        this.viewDiagramDescriptionById.put(viewId, description);
        this.apiDiagramDescriptionById.put(apiId, converted);

        EMFUtils.allContainedObjectOfType(description, DiagramElementDescription.class).forEach(de -> this.registerViewDiagramElement(de));
        Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> convertedNodes = this.buildConvertedNodeMap(description, converted);
        Map<org.eclipse.sirius.components.view.diagram.EdgeDescription, EdgeDescription> convertedEdges = this.buildConvertedEdgeMap(description, converted);

        this.diagrams.add(new Match(viewId, description, converted, convertedNodes, convertedEdges));

    }

    private DiagramElementDescription registerViewDiagramElement(DiagramElementDescription de) {
        return this.viewDiagramElementDescriptionById.put(this.idProvider.getId(de), de);
    }

    private Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> buildConvertedNodeMap(DiagramDescription diagramDescription,
            org.eclipse.sirius.components.diagrams.description.DiagramDescription converted) {

        Map<String, NodeDescription> nodeIdToDescriptions = new HashMap<>();
        for (NodeDescription node : converted.getNodeDescriptions()) {
            this.collectNote(node, nodeIdToDescriptions);
        }

        this.apiDiagramElementDescriptionById.putAll(nodeIdToDescriptions);

        Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> converterNodes = new HashMap<>();
        allContainedObjectOfType(diagramDescription, org.eclipse.sirius.components.view.diagram.NodeDescription.class).forEach(n -> {
            converterNodes.put(n, nodeIdToDescriptions.get(this.idProvider.getId(n)));
        });

        return converterNodes;
    }

    private Map<org.eclipse.sirius.components.view.diagram.EdgeDescription, EdgeDescription> buildConvertedEdgeMap(DiagramDescription diagramDescription,
            org.eclipse.sirius.components.diagrams.description.DiagramDescription converted) {

        Map<String, EdgeDescription> edgeIdToDescriptions = new HashMap<>();
        for (EdgeDescription edge : converted.getEdgeDescriptions()) {
            edgeIdToDescriptions.put(edge.getId(), edge);
        }

        this.apiDiagramElementDescriptionById.putAll(edgeIdToDescriptions);

        Map<org.eclipse.sirius.components.view.diagram.EdgeDescription, EdgeDescription> convertedEdges = new HashMap<>();
        allContainedObjectOfType(diagramDescription, org.eclipse.sirius.components.view.diagram.EdgeDescription.class).forEach(e -> {
            convertedEdges.put(e, edgeIdToDescriptions.get(this.idProvider.getId(e)));
        });
        return convertedEdges;
    }

    public Optional<org.eclipse.sirius.components.view.diagram.NodeDescription> getViewNodeDescriptionById(String id) {
        return Optional.ofNullable((org.eclipse.sirius.components.view.diagram.NodeDescription) this.viewDiagramElementDescriptionById.get(id));
    }

    public Optional<org.eclipse.sirius.components.view.diagram.EdgeDescription> getViewEdgeDescriptionById(String id) {
        return Optional.ofNullable((org.eclipse.sirius.components.view.diagram.EdgeDescription) this.viewDiagramElementDescriptionById.get(id));
    }

    public Optional<DiagramDescription> getViewDiagramDescriptionById(String id) {
        return this.diagrams.stream().filter(m -> m.getId().equals(id)).map(Match::getViewDiagramDescription).findFirst();
    }

    public Optional<IRepresentationDescription> getApiDiagramDescriptionById(String id) {
        return this.diagrams.stream().filter(m -> m.getId().equals(id)).map(Match::getApiDiagramDescription).findFirst();
    }

    private void collectNote(NodeDescription node, Map<String, NodeDescription> nodeIdToDescriptions) {
        if (node != null) {
            nodeIdToDescriptions.put(node.getId(), node);

            for (NodeDescription child : node.getChildNodeDescriptions()) {
                this.collectNote(child, nodeIdToDescriptions);
            }
            for (NodeDescription child : node.getBorderNodeDescriptions()) {
                this.collectNote(child, nodeIdToDescriptions);
            }
        }

    }

    public Optional<DiagramDescription> getViewDiagramDescriptionByName(String diagramName) {
        return this.diagrams.stream().filter(m -> Objects.equals(diagramName, m.getViewDiagramDescription().getName())).map(Match::getViewDiagramDescription).findFirst();
    }

    public Optional<IRepresentationDescription> getApiDiagramDescriptionByName(String diagramName) {
        return this.diagrams.stream().filter(m -> Objects.equals(diagramName, m.getViewDiagramDescription().getName())).map(Match::getApiDiagramDescription).findFirst();
    }

    public Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> getConvertedNode(String descriptionName) {
        return this.diagrams.stream().filter(m -> Objects.equals(descriptionName, m.getViewDiagramDescription().getName())).map(Match::getConvertedNodes).findFirst().orElse(Collections.emptyMap());
    }

    public Map<org.eclipse.sirius.components.view.diagram.EdgeDescription, EdgeDescription> getConvertedEdges(String descriptionName) {
        return this.diagrams.stream().filter(m -> Objects.equals(descriptionName, m.getViewDiagramDescription().getName())).map(Match::getConvertedEdges).findFirst().orElse(Collections.emptyMap());
    }

    public List<DiagramDescription> getViewDiagrams() {
        return this.diagrams.stream().map(Match::getViewDiagramDescription).toList();
    }

    public List<IRepresentationDescription> getApiDiagrams() {
        return this.diagrams.stream().map(Match::getApiDiagramDescription).toList();
    }

    public Collection<FormDescription> getForms() {
        return this.formsConvertion.keySet();
    }

    public Collection<org.eclipse.sirius.components.forms.description.FormDescription> getConvertedForms() {
        return this.formsConvertion.values();
    }

    /**
     * Represents a match between an API description and a View Description
     *
     * @author Arthur Daussy
     */
    private final class Match {

        private final DiagramDescription viewDiagramDescription;

        private final org.eclipse.sirius.components.diagrams.description.DiagramDescription apiDiagramDescription;

        private final Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> convertedNodes;

        private Map<org.eclipse.sirius.components.view.diagram.EdgeDescription, EdgeDescription> convertedEdges;

        private final String id;

        private Match(String id, DiagramDescription viewDiagramDescription, org.eclipse.sirius.components.diagrams.description.DiagramDescription apiDiagramDescription,
                Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> convertedNodes,
                Map<org.eclipse.sirius.components.view.diagram.EdgeDescription, EdgeDescription> convertedEdges) {
            super();
            this.id = Objects.requireNonNull(id);
            this.viewDiagramDescription = Objects.requireNonNull(viewDiagramDescription);
            this.apiDiagramDescription = Objects.requireNonNull(apiDiagramDescription);
            this.convertedNodes = Collections.unmodifiableMap(Objects.requireNonNull(convertedNodes));
            this.convertedEdges = convertedEdges;
        }

        public DiagramDescription getViewDiagramDescription() {
            return this.viewDiagramDescription;
        }

        public String getId() {
            return this.id;
        }

        public IRepresentationDescription getApiDiagramDescription() {
            return this.apiDiagramDescription;
        }

        public Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> getConvertedNodes() {
            return this.convertedNodes;
        }

        public Map<org.eclipse.sirius.components.view.diagram.EdgeDescription, EdgeDescription> getConvertedEdges() {
            return this.convertedEdges;
        }
    }

    /**
     * Notifier that checks the view models are not modified after registration
     *
     * @author Arthur Daussy
     */
    private static final class UnmodifableModel extends EContentAdapter {

        @Override
        public void notifyChanged(Notification notification) {
            if (!notification.isTouch()) {
                LOGGER.error("This papyrus view model should not be modified after registration. Notifier " + notification.getNotifier());
            }
        }
    }

}
