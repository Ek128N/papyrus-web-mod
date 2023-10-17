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
import static org.assertj.core.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.services.api.document.CreateDocumentInput;
import org.springframework.stereotype.Service;

import graphql.ExecutionInput;
import graphql.GraphQL;

/**
 * Service used to create document inside a project.
 *
 * @author Arthur Daussy
 */
@Service
public class CreateDocumentMutationRunner {

    private static String query = """
              mutation createDocument($input: CreateDocumentInput!) {
              createDocument(input: $input) {
                __typename
                ... on CreateDocumentSuccessPayload {
                  document {
                    id
                  }
                }
              }
            }
            """;

    private GraphQL graphQL;

    private ObjectMapper objectMapper;

    private IProjectRepository projectRepository;

    public CreateDocumentMutationRunner(GraphQL graphQL, ObjectMapper objectMapper, IProjectRepository projectRepository) {
        super();
        this.graphQL = graphQL;
        this.objectMapper = objectMapper;
        this.projectRepository = projectRepository;
    }

    public String createDocument(String projectId, String documentName, UUID stereotypeId) {

        assertThat(this.projectRepository.existsById(UUID.fromString(projectId))).isTrue();

        var createDocumentInput = new CreateDocumentInput(UUID.randomUUID(), projectId, documentName, stereotypeId);

        var createDocumentExecutionInput = ExecutionInput.newExecutionInput().query(query)
                .variables(Map.of("input", this.objectMapper.convertValue(createDocumentInput, new TypeReference<Map<String, Object>>() {

                }))).build();
        var createDocumentExecutionResult = this.graphQL.execute(createDocumentExecutionInput);
        assertThat(createDocumentExecutionResult.getErrors()).isEmpty();

        String documentId = null;
        try {
            var jsonResult = this.objectMapper.writeValueAsString(createDocumentExecutionResult.toSpecification());
            String responseTypeName = JsonPath.read(jsonResult, "$.data.createDocument.__typename");
            assertThat(responseTypeName).isEqualTo("CreateDocumentSuccessPayload");

            documentId = JsonPath.read(jsonResult, "$.data.createDocument.document.id");
        } catch (JsonProcessingException | IllegalArgumentException exception) {
            fail(exception.getMessage());
        }

        return documentId;
    }

}
