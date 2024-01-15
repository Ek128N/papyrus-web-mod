/*****************************************************************************
 * Copyright (c) 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.representations.uml;

import org.eclipse.papyrus.web.application.representations.view.aql.Services;
import org.eclipse.uml2.uml.ActionInputPin;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ActivityPartition;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.ExpansionNode;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.InterruptibleActivityRegion;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.ValuePin;

/**
 * Services available for the "Activity" Diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class ActivityDiagramServices extends Services {

    /**
     * The name of the service that checks if the diagram can be created.
     */
    public static final String CAN_CREATE_DIAGRAM = "canCreateDiagramAD";

    /**
     * The name of the service that checks if an element of a given type can be created in a given parent.
     */
    public static final String CAN_CREATE_INTO_PARENT = "canCreateIntoParentAD";

    /**
     * The name of the service that retrieves AcceptEventAction height computation expression.
     */
    public static final String COMPUTE_ACCEPT_EVENT_ACTION_HEIGHT = "computeAcceptEventActionHeightExpressionAD";

    /**
     * The name of the service that retrieves AcceptEventAction width computation expression.
     */
    public static final String COMPUTE_ACCEPT_EVENT_ACTION_WIDTH = "computeAcceptEventActionWidthExpressionAD";

    /**
     * The name of the service that creates an {@link ActionInputPin}.
     */
    public static final String CREATE_ACTION_INPUT_PIN = "createActionInputPinAD";

    /**
     * The name of the service that creates an {@link ActivityNode}.
     */
    public static final String CREATE_ACTIVITY_NODE = "createActivityNodeAD";

    /**
     * The name of the service that creates an {@link ExpansionNode}.
     */
    public static final String CREATE_EXPANSION_NODE = "createExpansionNodeAD";

    /**
     * The name of the service that creates an {@link InputPin}.
     */
    public static final String CREATE_INPUT_PIN = "createInputPinAD";

    /**
     * The name of the service that creates an {@link OutputPin}.
     */
    public static final String CREATE_OUTPUT_PIN = "createOutputPinAD";

    /**
     * The name of the service that creates a {@link ValuePin}.
     */
    public static final String CREATE_VALUE_PIN = "createValuePinAD";

    /**
     * The name of the service that retrieves {@link ActionInputPin} candidates.
     */
    public static final String GET_ACTION_INPUT_PIN_CANDIDATES = "getActionInputPinCandidatesAD";

    /**
     * The name of the service that retrieves {@link ActivityNode} candidates.
     */
    public static final String GET_ACTIVITY_NODE_CANDIDATES = "getActivityNodeCandidatesAD";

    /**
     * The name of the service that retrieves {@link ActivityPartition} candidates.
     */
    public static final String GET_ACTIVITY_PARTITION_CANDIDATES = "getActivityPartitionCandidatesAD";

    /**
     * The name of the service that computes {@link DecisionNode}'s label.
     */
    public static final String GET_DECISION_INPUT_NOTE_LABEL = "getDecisionInputNoteLabel";

    /**
     * The name of the service that retrieves {@link ExpansionNode} candidates.
     */
    public static final String GET_EXPANSION_NODE_CANDIDATES = "getExpansionNodesCandidatesAD";

    /**
     * The name of the service that retrieves {@link InputPin} candidates.
     */
    public static final String GET_INPUT_PIN_CANDIDATES = "getInputPinCandidatesAD";

    /**
     * The name of the service that retrieves {@link InterruptibleActivityRegion} candidates.
     */
    public static final String GET_INTERRUPTIBLE_ACTIVITY_REGION_CANDIDATES = "getInterruptibleActivityRegionCandidatesAD";

    /**
     * The name of the service that retrieves {@link OutputPin} candidates.
     */
    public static final String GET_OUTPUT_PIN_CANDIDATES = "getOutputPinCandidatesAD";

    /**
     * The name of the service that retrieves {@link ValuePin} candidates.
     */
    public static final String GET_VALUE_PIN_CANDIDATES = "getValuePinCandidatesAD";

    /**
     * The name of the service that checks whether a {@link DecisionNode}'s note should be displayed.
     */
    public static final String SHOW_DECISION_NODE_NOTE = "showDecisionNodeNote";
}
