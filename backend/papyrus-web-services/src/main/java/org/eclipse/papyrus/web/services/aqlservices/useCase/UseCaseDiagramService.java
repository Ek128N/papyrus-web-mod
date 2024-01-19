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
package org.eclipse.papyrus.web.services.aqlservices.useCase;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.eclipse.papyrus.uml.domain.services.properties.ILogger;
import org.eclipse.papyrus.web.services.aqlservices.AbstractDiagramService;
import org.eclipse.papyrus.web.services.aqlservices.IWebExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.web.services.aqlservices.IWebInternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.web.services.aqlservices.utils.IViewHelper;
import org.eclipse.papyrus.web.services.aqlservices.utils.ViewHelper;
import org.eclipse.papyrus.web.sirius.contributions.DiagramNavigator;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramNavigationService;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramOperationsService;
import org.eclipse.papyrus.web.sirius.contributions.IViewDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.UseCase;
import org.springframework.stereotype.Service;

/**
 * Service for the "Use Case" diagram.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@Service
public class UseCaseDiagramService extends AbstractDiagramService {

    /**
     * Logger used to report errors and warnings to the user.
     */
    private ILogger logger;

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
     * @param logger
     *            Logger used to report errors and warnings to the user
     */
    public UseCaseDiagramService(IObjectService objectService, IDiagramNavigationService diagramNavigationService, IDiagramOperationsService diagramOperationsService, IEditableChecker editableChecker,
            IViewDiagramDescriptionService viewDiagramService, ILogger logger) {
        super(objectService, diagramNavigationService, diagramOperationsService, editableChecker, viewDiagramService, logger);
        this.logger = logger;
    }

    @Override
    protected IWebExternalSourceToRepresentationDropBehaviorProvider buildSemanticDropBehaviorProvider(EObject semanticDroppedElement, IEditingContext editionContext, IDiagramContext diagramContext,
            Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> capturedNodeDescriptions) {
        IViewHelper createViewHelper = ViewHelper.create(this.getObjectService(), this.getViewDiagramService(), this.getDiagramOperationsService(), diagramContext, capturedNodeDescriptions);
        IWebExternalSourceToRepresentationDropBehaviorProvider dropProvider = new UseCaseSemanticDropBehaviorProvider(editionContext, createViewHelper, this.getObjectService(),
                this.getECrossReferenceAdapter(semanticDroppedElement), this.getEditableChecker(),
                new DiagramNavigator(this.getDiagramNavigationService(), diagramContext.getDiagram(), capturedNodeDescriptions), this.logger);
        return dropProvider;
    }

    @Override
    protected IWebInternalSourceToRepresentationDropBehaviorProvider buildGraphicalDropBehaviorProvider(EObject semanticDroppedElement, IEditingContext editionContext, IDiagramContext diagramContext,
            Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> capturedNodeDescriptions) {
        IViewHelper createViewHelper = ViewHelper.create(this.getObjectService(), this.getViewDiagramService(), this.getDiagramOperationsService(), diagramContext, capturedNodeDescriptions);
        IWebInternalSourceToRepresentationDropBehaviorProvider dropProvider = new UseCaseGraphicalDropBehaviorProvider(editionContext, createViewHelper, this.getObjectService(),
                this.getECrossReferenceAdapter(semanticDroppedElement), this.getEditableChecker(),
                new DiagramNavigator(this.getDiagramNavigationService(), diagramContext.getDiagram(), capturedNodeDescriptions), this.logger);
        return dropProvider;
    }

    /**
     * A service to create a {@link UseCase} in the given {@code container}.
     *
     * @param container
     *            the future container.
     * @param targetView
     *            the selected Graphical container.
     * @param diagramContext
     *            the diagram context
     * @param capturedNodeDescription
     *            the {@link NodeDescription}s
     * @return the created {@link UseCase}.
     */
    public EObject createUseCaseUCD(Element container, org.eclipse.sirius.components.diagrams.Node targetView, IDiagramContext diagramContext,
            Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> capturedNodeDescriptions) {
        EObject newUseCase = null;
        String useCaseType = UMLPackage.eINSTANCE.getUseCase().getName();
        if (container instanceof Package) {
            newUseCase = this.create(container, useCaseType, UMLPackage.eINSTANCE.getPackage_PackagedElement().getName(), targetView, diagramContext, capturedNodeDescriptions);
        } else if (container instanceof Classifier) {
            newUseCase = this.create(container, useCaseType, UMLPackage.eINSTANCE.getClassifier_OwnedUseCase().getName(), targetView, diagramContext, capturedNodeDescriptions);
        }
        return newUseCase;
    }

    /**
     * Provides {@link UseCase} candidates.
     *
     * @param container
     *            the current container in which looking for the UseCases.
     * @return the {@link UseCase} list.
     */
    public List<? extends UseCase> getUseCaseCandidatesUCD(Element container) {
        List<? extends UseCase> useCaseCandidates = List.of();
        if (container instanceof Package packageContainer) {
            useCaseCandidates = packageContainer.getPackagedElements().stream().filter(UseCase.class::isInstance).map(UseCase.class::cast).toList();
        } else if (container instanceof Classifier classifierContainer) {
            useCaseCandidates = classifierContainer.getOwnedUseCases();
        }
        return useCaseCandidates;
    }
}
