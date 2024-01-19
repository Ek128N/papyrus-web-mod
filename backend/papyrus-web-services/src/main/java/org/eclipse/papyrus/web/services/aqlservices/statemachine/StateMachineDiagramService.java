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
package org.eclipse.papyrus.web.services.aqlservices.statemachine;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.eclipse.papyrus.uml.domain.services.labels.ElementDefaultNameProvider;
import org.eclipse.papyrus.uml.domain.services.properties.ILogger;
import org.eclipse.papyrus.web.services.aqlservices.AbstractDiagramService;
import org.eclipse.papyrus.web.services.aqlservices.IWebExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.web.services.aqlservices.utils.IViewHelper;
import org.eclipse.papyrus.web.services.aqlservices.utils.ViewHelper;
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

    public static final String ROUND_ICON_NODE_DEFAULT_DIAMETER = "30";

    public static final String FORK_NODE_DEFAULT_WIDTH = "50";

    public static final String FORK_NODE_DEFAULT_HEIGHT = "150";

    /**
     * Logger used to report errors and warnings to the user.
     */
    private ILogger logger;

    public StateMachineDiagramService(IObjectService objectService, IDiagramNavigationService diagramNavigationService, IDiagramOperationsService diagramOperationsService,
            IEditableChecker editableChecker, IViewDiagramDescriptionService viewDiagramService, ILogger logger) {
        super(objectService, diagramNavigationService, diagramOperationsService, editableChecker, viewDiagramService, logger);
        this.logger = logger;
    }

    @Override
    protected IWebExternalSourceToRepresentationDropBehaviorProvider buildSemanticDropBehaviorProvider(EObject semanticDroppedElement, IEditingContext editionContext, IDiagramContext diagramContext,
            Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> capturedNodeDescriptions) {
        IViewHelper createViewHelper = ViewHelper.create(this.getObjectService(), this.getViewDiagramService(), this.getDiagramOperationsService(), diagramContext, capturedNodeDescriptions);
        return new StateMachineSemanticDiagramDropBehaviorProvider(editionContext, createViewHelper, this.getObjectService(),
                new DiagramNavigator(this.getDiagramNavigationService(), diagramContext.getDiagram(), capturedNodeDescriptions), this.logger);
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
     * Compute width expression of node representing a given {@link Pseudostate}.
     *
     * @param pseudostate
     *            the {@link Pseudostate} represented on the diagram
     * @return the width expression of node representing a given {@link Pseudostate}
     */
    public String computePseudoStateWidthExpression(Pseudostate pseudostate) {
        String widthComputationExpression = ROUND_ICON_NODE_DEFAULT_DIAMETER;
        PseudostateKind pseudostateKind = pseudostate.getKind();
        if (pseudostateKind.equals(PseudostateKind.FORK_LITERAL) || pseudostateKind.equals(PseudostateKind.JOIN_LITERAL)) {
            widthComputationExpression = FORK_NODE_DEFAULT_WIDTH;
        }
        return widthComputationExpression;
    }

    /**
     * Compute height expression of node representing a given {@link Pseudostate}.
     *
     * @param pseudostate
     *            the {@link Pseudostate} represented on the diagram
     * @return the height expression of node representing a given {@link Pseudostate}
     */
    public String computePseudoStateHeightExpression(Pseudostate pseudostate) {
        String widthComputationExpression = ROUND_ICON_NODE_DEFAULT_DIAMETER;
        PseudostateKind pseudostateKind = pseudostate.getKind();
        if (pseudostateKind.equals(PseudostateKind.FORK_LITERAL) || pseudostateKind.equals(PseudostateKind.JOIN_LITERAL)) {
            widthComputationExpression = FORK_NODE_DEFAULT_HEIGHT;
        }
        return widthComputationExpression;
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
