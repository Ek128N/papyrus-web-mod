/*******************************************************************************
 * Copyright (c) 2022, 2023 CEA, Obeo.
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
package org.eclipse.papyrus.web.services.aqlservices.statemachine;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.eclipse.papyrus.uml.domain.services.labels.ElementDefaultNameProvider;
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
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.springframework.stereotype.Service;

/**
 * AQL Services dedicated to the "State Machine Diagram".
 *
 * @author Laurent Fasani
 */
@Service
public class StateMachineDiagramService extends AbstractDiagramService {

    public StateMachineDiagramService(IObjectService objectService, IDiagramNavigationService diagramNavigationService, IDiagramOperationsService diagramOperationsService,
            IEditableChecker editableChecker, IViewDiagramDescriptionService viewDiagramService) {
        super(objectService, diagramNavigationService, diagramOperationsService, editableChecker, viewDiagramService);
    }

    @Override
    protected IWebExternalSourceToRepresentationDropBehaviorProvider buildDropBehaviorProvider(EObject semanticDroppedElement, IEditingContext editionContext, IDiagramContext diagramContext,
            Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> capturedNodeDescriptions) {
        IViewCreationHelper createViewHelper = CreationViewHelper.create(this.getObjectService(), this.getViewDiagramService(), this.getDiagramOperationsService(), diagramContext,
                capturedNodeDescriptions);
        return new StateMachineDiagramDropBehaviorProvider(createViewHelper, new DiagramNavigator(this.getDiagramNavigationService(), diagramContext.getDiagram(), capturedNodeDescriptions));
    }

    /**
     * Create the EObject instance as documented in {@code super.create} method and in addition set Pseudostate.kind
     * feature and update the name.
     *
     * @return an instance of Pseudostate.
     */
    public Pseudostate createPseudoState(EObject parent, String type, String referenceName, Node targetView, IDiagramContext diagramContext,
            Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> capturedNodeDescriptions, String pseudoStateKind) {
        EObject result = super.create(parent, type, referenceName, targetView, diagramContext, capturedNodeDescriptions);
        if (result instanceof Pseudostate pseudostate) {
            pseudostate.setKind(PseudostateKind.get(pseudoStateKind));
            this.resetDefaultName(pseudostate);
            return (Pseudostate) result;
        }
        return null;
    }

    /**
     * Service that reset the default name of an object.
     *
     * @param self
     *            the eObject to set
     * @return self
     */
    public NamedElement resetDefaultName(NamedElement self) {
        if (self == null) {
            return self;
        }
        self.setName(null);
        self.setName(new ElementDefaultNameProvider().getDefaultName(self, self.eContainer()));
        return self;
    }
}
