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

import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.services.api.projects.CreateProjectInput;
import org.springframework.stereotype.Service;

import graphql.ExecutionInput;
import graphql.GraphQL;

/**
 * Service used to create a project.
 *
 * @author Arthur Daussy
 */
@Service
public class CreateProjectMutationRunner {

    private static String query = """
              mutation createProject($input: CreateProjectInput!) {
              createProject(input: $input) {
                __typename
                ... on CreateProjectSuccessPayload {
                  project {
                    id
                  }
                }
              }
            }
            """;

    private GraphQL graphQL;

    private ObjectMapper objectMapper;

    private IProjectRepository projectRepository;

    public CreateProjectMutationRunner(GraphQL graphQL, ObjectMapper objectMapper, IProjectRepository projectRepository) {
        super();
        this.graphQL = graphQL;
        this.objectMapper = objectMapper;
        this.projectRepository = projectRepository;
    }

    public String createProject(String projectName) {

        var input = new CreateProjectInput(UUID.randomUUID(), projectName);

        var executionInput = ExecutionInput.newExecutionInput().query(query).variables(Map.of("input", this.objectMapper.convertValue(input, new TypeReference<Map<String, Object>>() {

        }))).build();

        var executionResult = this.graphQL.execute(executionInput);
        assertThat(executionResult.getErrors()).isEmpty();

        String projectId = null;
        try {
            var jsonResult = this.objectMapper.writeValueAsString(executionResult.toSpecification());
            String responseTypeName = JsonPath.read(jsonResult, "$.data.createProject.__typename");
            assertThat(responseTypeName).isEqualTo("CreateProjectSuccessPayload");

            projectId = JsonPath.read(jsonResult, "$.data.createProject.project.id");
            assertThat(this.projectRepository.existsById(UUID.fromString(projectId))).isTrue();
        } catch (JsonProcessingException | IllegalArgumentException exception) {
            fail(exception.getMessage());
        }
        return projectId;
    }

}
