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
package org.eclipse.papyrus.web.services.aqlservices.profile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.eclipse.papyrus.web.application.representations.uml.PRDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.services.aqlservices.AbstractDiagramService;
import org.eclipse.papyrus.web.services.aqlservices.IWebExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.web.services.aqlservices.IWebInternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.web.services.aqlservices.ServiceLogger;
import org.eclipse.papyrus.web.services.aqlservices.utils.IViewHelper;
import org.eclipse.papyrus.web.services.aqlservices.utils.ViewHelper;
import org.eclipse.papyrus.web.services.representations.PapyrusRepresentationDescriptionRegistry;
import org.eclipse.papyrus.web.sirius.contributions.DiagramNavigator;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramNavigationService;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramOperationsService;
import org.eclipse.papyrus.web.sirius.contributions.IViewDiagramDescriptionService;
import org.eclipse.papyrus.web.sirius.contributions.query.NodeMatcher;
import org.eclipse.papyrus.web.sirius.contributions.query.NodeMatcher.BorderNodeStatus;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.springframework.stereotype.Service;

/**
 * Service for the "Profile" diagram.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@Service
public class ProfileDiagramService extends AbstractDiagramService {

    private IRepresentationSearchService representationSearchService;

    private PapyrusRepresentationDescriptionRegistry papyrusRepresentationRegistry;

    /**
     * Logger used to report errors and warnings to the user.
     */
    private ServiceLogger logger;

    /**
     * Constructor.
     *
     * @param objectService
     *            service used to retrieve semantic object from a Diagram node
     * @param diagramNavigationService
     *            helper that must introspect the current diagram's structure and its description
     * @param diagramOperationsService
     *            helper that must modify the current diagram, most notably create or delete views for unsynchronized
     *            elements
     * @param editableChecker
     *            Object that check if an element can be edited
     * @param viewDiagramService
     *            Service used to navigate in DiagramDescription
     * @param representationSearchService
     *            helper used to find representation
     * @param papyrusRepresentationRegistry
     *            registry that keeps track of all {@link DiagramDescription}s used in Papyrus application
     * @param logger
     *            Logger used to report errors and warnings to the user
     */
    // CHECKSTYLE:OFF
    public ProfileDiagramService(IObjectService objectService, IDiagramNavigationService diagramNavigationService, IDiagramOperationsService diagramOperationsService, IEditableChecker editableChecker,
            IViewDiagramDescriptionService viewDiagramService, IRepresentationSearchService representationSearchService, PapyrusRepresentationDescriptionRegistry papyrusRepresentationRegistry,
            ServiceLogger logger) {
        // CHECKSTYLE:ON
        super(objectService, diagramNavigationService, diagramOperationsService, editableChecker, viewDiagramService, logger);
        this.representationSearchService = representationSearchService;
        this.papyrusRepresentationRegistry = papyrusRepresentationRegistry;
        this.logger = logger;
    }

    @Override
    protected IWebExternalSourceToRepresentationDropBehaviorProvider buildSemanticDropBehaviorProvider(EObject semanticDroppedElement, IEditingContext editionContext, IDiagramContext diagramContext,
            Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> capturedNodeDescriptions) {
        IViewHelper createViewHelper = ViewHelper.create(this.getObjectService(), this.getViewDiagramService(), this.getDiagramOperationsService(), diagramContext, capturedNodeDescriptions);
        IWebExternalSourceToRepresentationDropBehaviorProvider dropProvider = new ProfileSemanticDropBehaviorProvider(editionContext, createViewHelper, this.getObjectService(),
                this.getECrossReferenceAdapter(semanticDroppedElement), this.getEditableChecker(),
                new DiagramNavigator(this.getDiagramNavigationService(), diagramContext.getDiagram(), capturedNodeDescriptions), this.logger);
        return dropProvider;
    }

    @Override
    protected IWebInternalSourceToRepresentationDropBehaviorProvider buildGraphicalDropBehaviorProvider(EObject semanticDroppedElement, IEditingContext editionContext, IDiagramContext diagramContext,
            Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> capturedNodeDescriptions) {
        IViewHelper createViewHelper = ViewHelper.create(this.getObjectService(), this.getViewDiagramService(), this.getDiagramOperationsService(), diagramContext, capturedNodeDescriptions);
        IWebInternalSourceToRepresentationDropBehaviorProvider dropProvider = new ProfileGraphicalDropBehaviorProvider(editionContext, createViewHelper, this.getObjectService(),
                this.getECrossReferenceAdapter(semanticDroppedElement), this.getEditableChecker(),
                new DiagramNavigator(this.getDiagramNavigationService(), diagramContext.getDiagram(), capturedNodeDescriptions), this.logger);
        return dropProvider;
    }

    /**
     * Checks if the provided {@code representationId} is inside a Profile model.
     *
     * @param editingContext
     *            the current editing context
     * @param representationId
     *            the identifier of the representation to check
     * @return {@code true} if the {@code representationId} is inside a profile model, {@code false} otherwise
     */
    public boolean isProfileModel(IEditingContext editingContext, String representationId) {
        boolean result = false;
        if (representationId != null && !representationId.isEmpty()) {
            Optional<Diagram> optDiagram = this.representationSearchService.findById(editingContext, representationId, Diagram.class);
            if (optDiagram.isPresent()) {
                EObject eObject = (EObject) this.getObjectService().getObject(editingContext, optDiagram.get().getTargetObjectId()).orElse(null);
                result = this.isProfileModel(eObject);
            }
        }
        return result;
    }

    /**
     * Provides Meta{@link Class} candidates.
     *
     * @param container
     *            the current container in which looking for the Meta{@link Class}.
     * @return the Meta{@link Class} list.
     */
    public List<? extends Class> getMetaclassPRD(EObject container) {
        List<? extends Class> importedElementCandidates = List.of();
        if (container instanceof Namespace) {
            importedElementCandidates = ((Namespace) container).getElementImports().stream() //
                    .map(ei -> ei.getImportedElement()) //
                    .filter(Class.class::isInstance) //
                    .map(Class.class::cast)//
                    .filter(c -> c.isMetaclass()) //
                    .toList();
        }
        return importedElementCandidates;
    }

    /**
     * Check if the resource of a given {@link Object} is a Profile model.
     *
     * @param context
     *            context used to create diagram on
     *
     * @return <code>true</code> if the resource is a profile model, <code>false</code> otherwise.
     */
    public boolean isProfileModel(EObject context) {
        return this.isContainedInProfileResource(context);
    }

    /**
     *
     * Returns the metaclasses of the UML metamodel.
     * <p>
     * This method only returns the metaclasses from the {@code this.UML.metamodel.uml} model.The returned list is
     * sorted by alphabetical order.
     * </p>
     *
     * @param editingContext
     *            the editing context to search into
     * @return the list of metaclasses from the UML metamodel
     */
    public List<? extends Class> getMetaclasses(IEditingContext editingContext) {
        EditingContext ed = (EditingContext) editingContext;
        Resource umlMetamodelResource = ed.getDomain().getResourceSet().getResource(URI.createURI(UMLResource.UML_METAMODEL_URI), true);
        Package umlPackage = (Package) EcoreUtil.getObjectByType(umlMetamodelResource.getContents(), UMLPackage.eINSTANCE.getPackage());
        return umlPackage.getOwnedTypes().stream() //
                .filter(Class.class::isInstance) //
                .map(Class.class::cast) //
                .sorted((metaclass1, metaclass2) -> metaclass1.getName().compareTo(metaclass2.getName())) //
                .toList();
    }

    /**
     * Creates an {@link ElementImport} referencing the provided {@code metaclassId}.
     * <p>
     * The provided {@code metaclassId} must be a valid URI of a metaclass from the UML metamodel. Metaclasses stored in
     * other resources cannot be configured with this method.
     * </p>
     *
     * @param editingContext
     *            the editing context used to perform the operation
     * @param representationId
     *            the identifier of the graphical element to use to create the {@link ElementImport} view
     * @param metaclassId
     *            the identifier of the UML metaclass to reference in the created {@link ElementImport}
     * @param diagramContext
     *            the graphical context
     * @return {@code true} if the {@link ElementImport} and its view are successfully created, {@code false} otherwise
     */
    public boolean createMetaclassImport(IEditingContext editingContext, String representationId, String diagramElementId, String metaclassId, IDiagramContext diagramContext) {
        boolean result = false;
        Optional<Diagram> optDiagram = this.representationSearchService.findById(editingContext, representationId, Diagram.class);
        if (optDiagram.isPresent()) {
            Diagram diagram = optDiagram.get();
            Node diagramElement = this.getDiagramElement(editingContext, diagram, diagramElementId).orElse(null);
            Optional<Profile> optProfile;
            if (diagramElement == null) {
                optProfile = this.getProfile(editingContext, diagram.getTargetObjectId());
            } else {
                optProfile = this.getProfile(editingContext, diagramElement.getTargetObjectId());
            }
            if (optProfile.isPresent()) {
                Profile profile = optProfile.get();
                Optional<Class> optMetaclass = this.getObjectService().getObject(editingContext, metaclassId).map(Class.class::cast);
                if (optMetaclass.isPresent() && !this.profileHasImportElementForMetaclass(profile, optMetaclass.get())) {
                    ElementImport createdElementImport = (ElementImport) this.create(profile, UMLPackage.eINSTANCE.getElementImport().getName(),
                            UMLPackage.eINSTANCE.getNamespace_ElementImport().getName(), null, diagramContext, Map.of());
                    createdElementImport.setImportedElement(optMetaclass.get());

                    Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> convertedNodes = this.papyrusRepresentationRegistry
                            .getConvertedNode(PRDDiagramDescriptionBuilder.PRD_REP_NAME);

                    IViewHelper createViewHelper = ViewHelper.create(this.getObjectService(), this.getViewDiagramService(), this.getDiagramOperationsService(), diagramContext, convertedNodes);
                    if (diagramElement == null) {
                        result = createViewHelper.createRootView(createdElementImport.getImportedElement(), PRDDiagramDescriptionBuilder.PRD_METACLASS);
                    } else {
                        result = createViewHelper.createChildView(createdElementImport.getImportedElement(), diagramElement, PRDDiagramDescriptionBuilder.PRD_SHARED_METACLASS);
                    }
                }

            }
        }
        return result;
    }

    /**
     * Returns the semantic {@link Profile} associated to the given {@code objectId}.
     * <p>
     * This method returns an empty {@link Optional} if the provided {@code objectId} element isn't a {@link Profile}.
     * </p>
     *
     * @param editingContext
     *            the editing context
     * @param objectId
     *            the object identifier to retrieve the {@link Profile} from
     * @return the {@link Profile} if it exists, or an empty {@link Optional} otherwise
     * @throws NullPointerException
     *             if {@code editingContext} or {@code objectId} is {@code null}
     */
    private Optional<Profile> getProfile(IEditingContext editingContext, String objectId) {
        Objects.requireNonNull(editingContext);
        Objects.requireNonNull(objectId);
        return this.getObjectService().getObject(editingContext, objectId)//
                .filter(Profile.class::isInstance)//
                .map(Profile.class::cast);
    }

    /**
     * Returns whether the provided {@code profile} contains an {@link ElementImport} for the provided
     * {@code metaclass}.
     *
     * @param profile
     *            the {@link Profile} to check
     * @param metaclass
     *            the metaclass to find in the provided {@link Profile}
     * @return {@code true} if the {@code profile} contains an {@link ElementImport} for the provided {@code metaclass},
     *         {@code false} otherwise
     */
    private boolean profileHasImportElementForMetaclass(Profile profile, Class metaclass) {
        return profile.getElementImports().stream() //
                .anyMatch(elementImport -> Objects.equals(elementImport.getImportedElement(), metaclass));
    }

    private Optional<Node> getDiagramElement(IEditingContext editingContext, Diagram diagram, String diagramElementId) {
        return this.getDiagramNavigationService() //
                .getMatchingNodes(diagram, editingContext, NodeMatcher.buildSemanticAndNodeMatcher(BorderNodeStatus.BASIC_NODE, null, n -> n.getId().equals(diagramElementId)))//
                .stream() //
                .findFirst();
    }
}
