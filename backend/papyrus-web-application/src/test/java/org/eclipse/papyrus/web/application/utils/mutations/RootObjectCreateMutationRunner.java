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

import org.eclipse.sirius.components.collaborative.dto.CreateRootObjectInput;
import org.springframework.stereotype.Service;

import graphql.ExecutionInput;
import graphql.GraphQL;

/**
 * Service use to create a root object inside a project using a GraphQL mutation.
 *
 * @author Arthur Daussy
 */
@Service
public class RootObjectCreateMutationRunner {

    private static String query = """
            mutation createRootObject($input: CreateRootObjectInput!) {
              createRootObject(input: $input) {
                __typename
                ... on CreateRootObjectSuccessPayload {
                  object {
                    id
                  }
                }
              }
            }
            """;

    private GraphQL graphQL;

    private ObjectMapper objectMapper;

    public RootObjectCreateMutationRunner(GraphQL graphQL, ObjectMapper objectMapper) {
        super();
        this.graphQL = graphQL;
        this.objectMapper = objectMapper;
    }

    public String createRootObject(String ePackageNsURI, String type, String documentId, String projectId) {

        var createRootObjectInput = new CreateRootObjectInput(UUID.randomUUID(), projectId, UUID.fromString(documentId), ePackageNsURI, type);

        var createRootObjectExecutionInput = ExecutionInput.newExecutionInput().query(query)
                .variables(Map.of("input", this.objectMapper.convertValue(createRootObjectInput, new TypeReference<Map<String, Object>>() {

                }))).build();
        var createRootObjectExecutionResult = this.graphQL.execute(createRootObjectExecutionInput);
        assertThat(createRootObjectExecutionResult.getErrors()).isEmpty();

        String rootObjectId = null;
        try {
            var jsonResult = this.objectMapper.writeValueAsString(createRootObjectExecutionResult.toSpecification());
            String responseTypeName = JsonPath.read(jsonResult, "$.data.createRootObject.__typename");
            assertThat(responseTypeName).isEqualTo("CreateRootObjectSuccessPayload");

            rootObjectId = JsonPath.read(jsonResult, "$.data.createRootObject.object.id");
        } catch (JsonProcessingException exception) {
            fail(exception.getMessage());
        }
        return rootObjectId;
    }

}
