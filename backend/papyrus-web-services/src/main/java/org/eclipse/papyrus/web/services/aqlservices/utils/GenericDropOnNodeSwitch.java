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
package org.eclipse.papyrus.web.services.aqlservices.utils;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.web.sirius.contributions.DiagramNavigator;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.uml2.uml.util.UMLSwitch;

public class GenericDropOnNodeSwitch extends UMLSwitch<Boolean> {

    // Dupllicated from org.eclipse.papyrus.web.application.representations.IdBuilder
    // Keep in sync
    // Workaround https://github.com/PapyrusSirius/papyrus-web/issues/165
    private static final String COMPARTMENT_NODE_SUFFIX = "_CompartmentNode"; //$NON-NLS-1$

    private final Node selectedNode;

    private final IViewCreationHelper viewHelper;

    private DiagramNavigator diagramNavigator;

    public GenericDropOnNodeSwitch(Node selectedNode, IViewCreationHelper viewHelper, DiagramNavigator diagramNavigator) {
        super();
        this.selectedNode = selectedNode;
        this.viewHelper = viewHelper;
        this.diagramNavigator = diagramNavigator;
    }

    public Node getSelectedNode() {
        return this.selectedNode;
    }

    public IViewCreationHelper getViewHelper() {
        return this.viewHelper;
    }

    public DiagramNavigator getDiagramNavigator() {
        return this.diagramNavigator;
    }

    @Override
    public Boolean defaultCase(EObject object) {
        boolean success = this.viewHelper.createChildView(object, this.selectedNode);
        if (!success) {
            // Workaround https://github.com/PapyrusSirius/papyrus-web/issues/165
            // If DnD on an icon label element contained by a compartment then DnD the element in the compartment
            // instead
            Optional<Node> parentNode = this.diagramNavigator.getParentNode(this.selectedNode);
            parentNode//
                    .flatMap(this.diagramNavigator::getDescription)//
                    .filter(desc -> desc.getName().endsWith(COMPARTMENT_NODE_SUFFIX)).ifPresent(parentDescription -> {
                        this.viewHelper.createChildView(object, parentNode.get());
                    });

        }
        return Boolean.TRUE;
    }

}
