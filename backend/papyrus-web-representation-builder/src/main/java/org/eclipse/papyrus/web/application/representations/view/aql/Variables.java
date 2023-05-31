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
package org.eclipse.papyrus.web.application.representations.view.aql;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.handlers.DeleteFromDiagramEventHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Lists of variables that can be used in expressions.
 *
 * @author Arthur Daussy
 */
public class Variables {
    public static final String EDGE_SOURCE = org.eclipse.sirius.components.diagrams.description.EdgeDescription.EDGE_SOURCE;

    public static final String EDGE_TARGET = org.eclipse.sirius.components.diagrams.description.EdgeDescription.EDGE_TARGET;

    public static final String SEMANTIC_EDGE_SOURCE = org.eclipse.sirius.components.diagrams.description.EdgeDescription.SEMANTIC_EDGE_SOURCE;

    public static final String SEMANTIC_EDGE_TARGET = org.eclipse.sirius.components.diagrams.description.EdgeDescription.SEMANTIC_EDGE_TARGET;

    public static final String DIAGRAM_CONTEXT = IDiagramContext.DIAGRAM_CONTEXT;

    public static final String DIAGRAM = "diagram"; //$NON-NLS-1$

    public static final String SELECTED_NODE = Node.SELECTED_NODE;

    public static final String SELECTED_EDGE = Edge.SELECTED_EDGE;

    public static final String EDITING_CONTEXT = IEditingContext.EDITING_CONTEXT;

    public static final String DELETION_POLICY = DeleteFromDiagramEventHandler.DELETION_POLICY;

    public static final String SELF = VariableManager.SELF;

    // Not externalize yet in
    // org.eclipse.sirius.components.view.emf.diagram.ViewDiagramDescriptionConverter.CONVERTED_NODES_VARIABLE
    public static final String CONVERTED_NODES = "convertedNodes"; //$NON-NLS-1$

    // Not externalize yet in
    // org.eclipse.sirius.components.view.emf.diagram.ViewReconnectionToolsExecutor.createVariableManager(ReconnectionToolInterpreterData)
    // Workaround of https://github.com/eclipse-sirius/sirius-components/issues/1479
    public static final String SEMANTIC_RECONNECTION_SOURCE = "semanticReconnectionSource"; //$NON-NLS-1$

    // Not externalize yet in
    // org.eclipse.sirius.components.view.emf.diagram.ViewReconnectionToolsExecutor.createVariableManager(ReconnectionToolInterpreterData)
    // Workaround of https://github.com/eclipse-sirius/sirius-components/issues/1479
    public static final String SEMANTIC_RECONNECTION_TARGET = "semanticReconnectionTarget"; //$NON-NLS-1$

    // Not externalize yet in
    // org.eclipse.sirius.components.view.emf.diagram.ViewReconnectionToolsExecutor.createVariableManager(ReconnectionToolInterpreterData)
    // Workaround of https://github.com/eclipse-sirius/sirius-components/issues/1479
    public static final String EDGE_SEMANTIC_ELEMENT = "edgeSemanticElement"; //$NON-NLS-1$

    // Not externalize yet in
    // org.eclipse.sirius.components.view.emf.diagram.ViewReconnectionToolsExecutor.createVariableManager(ReconnectionToolInterpreterData)
    // Workaround of https://github.com/eclipse-sirius/sirius-components/issues/1479
    public static final String SEMANTIC_OTHER_END = "semanticOtherEnd"; //$NON-NLS-1$

    // Not externalize yet in
    // org.eclipse.sirius.components.view.emf.diagram.ViewReconnectionToolsExecutor.createVariableManager(ReconnectionToolInterpreterData)
    // Workaround of https://github.com/eclipse-sirius/sirius-components/issues/1479
    public static final String RECONNECTION_SOURCE_VIEW = "reconnectionSourceView"; //$NON-NLS-1$

    // Not externalize yet in
    // org.eclipse.sirius.components.view.emf.diagram.ViewReconnectionToolsExecutor.createVariableManager(ReconnectionToolInterpreterData)
    // Workaround of https://github.com/eclipse-sirius/sirius-components/issues/1479
    public static final String RECONNECTION_TARGET_VIEW = "reconnectionTargetView"; //$NON-NLS-1$

    // Not externalize yet in
    // org.eclipse.sirius.components.view.emf.diagram.ViewDiagramDescriptionConverter.createLabelEditHandler(DiagramElementDescription,
    // ViewDiagramDescriptionConverterContext)
    // Workaround of https://github.com/eclipse-sirius/sirius-components/issues/1479
    public static final String ARG0 = "arg0"; //$NON-NLS-1$

    public static final String GRAPHICAL_EDGE_SOURCE = EdgeDescription.GRAPHICAL_EDGE_SOURCE;

    public static final String GRAPHICAL_EDGE_TARGET = EdgeDescription.GRAPHICAL_EDGE_TARGET;

    public static final String CACHE = DiagramDescription.CACHE;
}
