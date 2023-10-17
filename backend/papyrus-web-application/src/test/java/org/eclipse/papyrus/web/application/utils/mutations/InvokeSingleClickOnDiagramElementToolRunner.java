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

import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolInput;
import org.springframework.stereotype.Service;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;

/**
 * Service used to invoke a tool on one element.
 * <p>
 * This class instantiates and runs the {@code invokeSingleClickOnDiagramElementTool}. The invoked tool can be retrieved
 * via {@link GetPaletteToolQueryRunner#getTool(UUID, UUID, UUID, String, String)}.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@Service
public class InvokeSingleClickOnDiagramElementToolRunner {

    private static final String INVOKE_SINGLE_CLICK_ON_DIAGRAM_ELEMENT_TOOL_QUERY = """
            mutation invokeSingleClickOnDiagramElementTool($input: InvokeSingleClickOnDiagramElementToolInput!) {
               invokeSingleClickOnDiagramElementTool(input: $input) {
                 __typename
                 ... on InvokeSingleClickOnDiagramElementToolSuccessPayload {
                   newSelection {
                     entries {
                       id
                       label
                       kind
                       __typename
                     }
                     __typename
                   }
                   messages {
                     body
                     level
                     __typename
                   }
                   __typename
                 }
                 ... on ErrorPayload {
                   messages {
                     body
                     level
                     __typename
                   }
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
    public InvokeSingleClickOnDiagramElementToolRunner(GraphQL graphQL, ObjectMapper objectMapper) {
        this.graphQL = graphQL;
        this.objectMapper = objectMapper;
    }

    /**
     * Invokes the {@code toolId} tool on the {@code diagramElementId} element.
     * <p>
     * This method invokes the given tool on a single element, see {@link #invokeTool(UUID, UUID, UUID, UUID, UUID)} to
     * invoke a tool on two elements.
     * </p>
     * <p>
     * This method produces a test failure if the underlying GraphQL query returns an error.
     * </p>
     *
     * @param projectId
     *            the project containing the element on which the tool is invoked
     * @param representationId
     *            the representation containing the element
     * @param diagramElementId
     *            the graphical identifier of the element on which the tool is invoked
     * @param toolId
     *            the identifier of the tool to invoke
     */
    public void invokeTool(String projectId, String representationId, String diagramElementId, String toolId) {
        // Starting position and selected objects aren't relevant when invoking the tool manually, so we set them to 0
        // and null, respectively
        InvokeSingleClickOnDiagramElementToolInput invokeSingleClickOnDiagramElementToolInput = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), projectId, representationId,
                diagramElementId, toolId, 0, 0, null);
        ExecutionInput executionInput = ExecutionInput.newExecutionInput(INVOKE_SINGLE_CLICK_ON_DIAGRAM_ELEMENT_TOOL_QUERY) //
                .variables(Map.of("input", this.objectMapper.convertValue(invokeSingleClickOnDiagramElementToolInput, new TypeReference<Map<String, Object>>() { /**/ }))) //
                .build();
        ExecutionResult executionResult = this.graphQL.execute(executionInput);
        assertThat(executionResult.getErrors()).isEmpty();
    }

}
