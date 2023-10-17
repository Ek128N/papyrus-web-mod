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
package org.eclipse.papyrus.web.application.utils.mutations;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.dto.ReconnectEdgeInput;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeKind;
import org.springframework.stereotype.Service;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;

/**
 * Service used to reconnect the source or target of an edge.
 * <p>
 * This class instantiates and runs the {@code reconnectEdge} mutation. Node that this mutation performs both graphical
 * and semantic operations.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@Service
public class ReconnectEdgeMutationRunner {

    private static String query = """
                        mutation reconnectEdge($input: ReconnectEdgeInput!) {
              reconnectEdge(input: $input) {
                __typename
                ... on ErrorPayload {
                  message
                  __typename
                }
              }
            }
                        """;

    private GraphQL graphQL;

    private ObjectMapper objectMapper;

    /**
     * Initializes the runner with the provided {@code graphQL} and {@code objectMapper}.
     *
     * @param graphQL
     *            the GraphQL execution engine
     * @param objectMapper
     *            the object mapper
     */
    public ReconnectEdgeMutationRunner(GraphQL graphQL, ObjectMapper objectMapper) {
        this.graphQL = graphQL;
        this.objectMapper = objectMapper;
    }

    /**
     * Reconnects the source of the {@code edgeId} edge to {@code newEdgeEndId}.
     * <p>
     * This method produces a test failure if the underlying GraphQL query returns an error.
     * </p>
     *
     * @param projectId
     *            the project containing the elements
     * @param representationId
     *            the representation containing the elements
     * @param edgeId
     *            the graphical identifier of the edge to reconnect the source from
     * @param newEdgeEndId
     *            the graphical identifier of the new source of the edge
     */
    public void reconnectEdgeSource(String projectId, String representationId, String edgeId, String newEdgeEndId) {
        this.reconnectEdge(projectId, representationId, edgeId, newEdgeEndId, ReconnectEdgeKind.SOURCE);
    }

    /**
     * Reconnects the target of the {@code edgeId} edge to {@code newEdgeEndId}.
     * <p>
     * This method produces a test failure if the underlying GraphQL query returns an error.
     * </p>
     *
     * @param projectId
     *            the project containing the elements
     * @param representationId
     *            the representation containing the elements
     * @param edgeId
     *            the graphical identifier of the edge to reconnect the target from
     * @param newEdgeEndId
     *            the graphical identifier of the new target of the edge
     */
    public void reconnectEdgeTarget(String projectId, String representationId, String edgeId, String newEdgeEndId) {
        this.reconnectEdge(projectId, representationId, edgeId, newEdgeEndId, ReconnectEdgeKind.TARGET);
    }

    public void reconnectEdge(String editingContext, String representationId, String edgeId, String newEdgeEndId, ReconnectEdgeKind reconnectEdgeKind) {
        ReconnectEdgeInput reconnectEdgeInput = new ReconnectEdgeInput(UUID.randomUUID(), editingContext.toString(), representationId.toString(), edgeId.toString(), newEdgeEndId.toString(),
                reconnectEdgeKind, Position.at(0, 0));
        ExecutionInput executionInput = ExecutionInput.newExecutionInput(query)//
                .variables(Map.of("input", this.objectMapper.convertValue(reconnectEdgeInput, new TypeReference<Map<String, Object>>() { /**/ }))) //
                .build();
        ExecutionResult executionResult = this.graphQL.execute(executionInput);
        assertThat(executionResult.getErrors()).isEmpty();
    }

}
