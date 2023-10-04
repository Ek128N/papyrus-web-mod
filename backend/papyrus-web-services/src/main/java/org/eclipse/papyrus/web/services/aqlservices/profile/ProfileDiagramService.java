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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.eclipse.papyrus.web.services.aqlservices.AbstractDiagramService;
import org.eclipse.papyrus.web.services.aqlservices.IWebExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.web.services.aqlservices.utils.CreationViewHelper;
import org.eclipse.papyrus.web.services.aqlservices.utils.IViewCreationHelper;
import org.eclipse.papyrus.web.sirius.contributions.DiagramNavigator;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramNavigationService;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramOperationsService;
import org.eclipse.papyrus.web.sirius.contributions.IViewDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Namespace;
import org.springframework.stereotype.Service;

/**
 * Service for the "Profile" diagram.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@Service
public class ProfileDiagramService extends AbstractDiagramService {

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
     */
    public ProfileDiagramService(IObjectService objectService, IDiagramNavigationService diagramNavigationService, IDiagramOperationsService diagramOperationsService, IEditableChecker editableChecker,
            IViewDiagramDescriptionService viewDiagramService) {
        super(objectService, diagramNavigationService, diagramOperationsService, editableChecker, viewDiagramService);
    }

    @Override
    protected IWebExternalSourceToRepresentationDropBehaviorProvider buildDropBehaviorProvider(EObject semanticDroppedElement, IEditingContext editionContext, IDiagramContext diagramContext,
            Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> capturedNodeDescriptions) {
        IViewCreationHelper createViewHelper = CreationViewHelper.create(this.getObjectService(), this.getViewDiagramService(), this.getDiagramOperationsService(), diagramContext,
                capturedNodeDescriptions);
        IWebExternalSourceToRepresentationDropBehaviorProvider dropProvider = new ProfileDropBehaviorProvider(editionContext, createViewHelper, this.getObjectService(),
                this.getECrossReferenceAdapter(semanticDroppedElement), this.getEditableChecker(),
                new DiagramNavigator(this.getDiagramNavigationService(), diagramContext.getDiagram(), capturedNodeDescriptions));
        return dropProvider;
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

}
