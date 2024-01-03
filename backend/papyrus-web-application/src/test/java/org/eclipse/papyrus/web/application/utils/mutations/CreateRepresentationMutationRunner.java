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
package org.eclipse.papyrus.web.application.utils.mutations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.springframework.stereotype.Service;

import graphql.ExecutionInput;
import graphql.GraphQL;
import net.minidev.json.JSONArray;

/**
 * Service use to create a representation.
 *
 * @author Arthur Daussy
 */
@Service
public class CreateRepresentationMutationRunner {

    private static String representationQuery = """
              query getRepresentationDescriptions($editingContextId: ID!, $objectId: ID!) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  representationDescriptions(objectId: $objectId) {
                    edges {
                      node {
                        id
                        label
                      }
                    }
                  }
                }
              }
            }
            """;

    private static String query = """
            mutation createRepresentation($input: CreateRepresentationInput!) {
              createRepresentation(input: $input) {
                __typename
                ... on CreateRepresentationSuccessPayload {
                  representation {
                    id
                  }
                }
              }
            }
            """;

    private GraphQL graphQL;

    private ObjectMapper objectMapper;

    public CreateRepresentationMutationRunner(GraphQL graphQL, ObjectMapper objectMapper) {
        super();
        this.graphQL = graphQL;
        this.objectMapper = objectMapper;
    }

    public String createRepresentation(String projectId, String targetObject, String representationDescriptionName, String representationName) {

        var getRepresentationDescriptionsExecutionInput = ExecutionInput.newExecutionInput().query(representationQuery).variables(Map.of("editingContextId", projectId, "objectId", targetObject))
                .build();
        var getRepresentationDescriptionsExecutionResult = this.graphQL.execute(getRepresentationDescriptionsExecutionInput);
        assertThat(getRepresentationDescriptionsExecutionResult.getErrors()).isEmpty();

        String representationDescriptionId = null;
        try {
            var jsonResult = this.objectMapper.writeValueAsString(getRepresentationDescriptionsExecutionResult.toSpecification());
            // ??
            var matches = (JSONArray) JsonPath.read(jsonResult, "$.data.viewer.editingContext.representationDescriptions.edges[?(@.node.label=='" + representationDescriptionName + "')].node.id");
            representationDescriptionId = (String) matches.get(0);
        } catch (JsonProcessingException exception) {
            fail(exception.getMessage());
        }

        var input = new CreateRepresentationInput(UUID.randomUUID(), projectId, representationDescriptionId, targetObject, representationName);
        var executionInput = ExecutionInput.newExecutionInput().query(query).variables(Map.of("input", this.objectMapper.convertValue(input, new TypeReference<Map<String, Object>>() { /**/ })))
                .build();
        var executionResult = this.graphQL.execute(executionInput);
        assertThat(executionResult.getErrors()).isEmpty();

        String representationId = null;
        try {
            var jsonResult = this.objectMapper.writeValueAsString(executionResult.toSpecification());
            representationId = JsonPath.read(jsonResult, "$.data.createRepresentation.representation.id");
        } catch (JsonProcessingException | IllegalArgumentException exception) {
            fail(exception.getMessage());
        }

        return representationId;
    }

}
