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
import static org.junit.Assert.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.dto.GetPaletteInput;
import org.springframework.stereotype.Service;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import net.minidev.json.JSONArray;

/**
 * Service used to retrieve a palette tool on a given element.
 * <p>
 * This class instantiates and runs the {@code getPalette} query. The content of the palette is then processed to
 * retrieve the desired tool.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@Service
public class GetPaletteToolQueryRunner {

    private static String query = """
            query getPalette($editingContextId: ID!, $diagramId: ID!, $diagramElementId: ID!) {
                  viewer {
                    editingContext(editingContextId: $editingContextId) {
                      representation(representationId: $diagramId) {
                        description {
                          ... on DiagramDescription {
                            palette(diagramElementId: $diagramElementId) {
                              id
                              tools {
                                __typename
                                id
                                label
                                iconURL
                                ... on SingleClickOnDiagramElementTool {
                                  targetDescriptions {
                                    id
                                    __typename
                                  }
                                  appliesToDiagramRoot
                                  selectionDescriptionId
                                  __typename
                                }
                                ... on SingleClickOnTwoDiagramElementsTool {
                                  candidates {
                                    sources {
                                      id
                                      __typename
                                    }
                                    targets {
                                      id
                                      __typename
                                    }
                                    __typename
                                  }
                                  __typename
                                }
                              }
                              toolSections {
                                id
                                label
                                iconURL
                                tools {
                                  __typename
                                  id
                                  label
                                  iconURL
                                  ... on SingleClickOnDiagramElementTool {
                                    targetDescriptions {
                                      id
                                      __typename
                                    }
                                    appliesToDiagramRoot
                                    selectionDescriptionId
                                    __typename
                                  }
                                  ... on SingleClickOnTwoDiagramElementsTool {
                                    candidates {
                                      sources {
                                        id
                                        __typename
                                      }
                                      targets {
                                        id
                                        __typename
                                      }
                                      __typename
                                    }
                                    __typename
                                  }
                                }
                                __typename
                              }
                              __typename
                            }
                            __typename
                          }
                          __typename
                        }
                        __typename
                      }
                      __typename
                    }
                    __typename
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
    public GetPaletteToolQueryRunner(GraphQL graphQL, ObjectMapper objectMapper) {
        this.graphQL = graphQL;
        this.objectMapper = objectMapper;
    }

    /**
     * Returns the raw JSON of the palette associated to the {@code diagramElementId} element.
     * <p>
     * This method produces a test failure if the underlying GraphQL query returns an error.
     * </p>
     *
     * @param projectId
     *            the project containing the element to retrieve the palette from
     * @param representationId
     *            the representation containing the element
     * @param diagramElementId
     *            the graphical identifier of the element to retrieve the palette from
     * @return the raw JSON of the palette
     */
    private String getPalette(String projectId, String representationId, String diagramElementId) {
        GetPaletteInput getPaletteInput = new GetPaletteInput(UUID.randomUUID(), projectId, representationId, diagramElementId);
        ExecutionInput executionInput = ExecutionInput.newExecutionInput().query(query)
                // We don't have to wrap the GetPaletteInput object into an "input" variable because getPalette is a
                // GraphQL query and not a mutation.
                .variables(Map.of("editingContextId", getPaletteInput.editingContextId(), "diagramId", getPaletteInput.representationId(), "diagramElementId", getPaletteInput.diagramElementId()))
                .build();

        ExecutionResult executionResult = this.graphQL.execute(executionInput);
        assertThat(executionResult.getErrors()).isEmpty();

        String result = null;
        try {
            result = this.objectMapper.writeValueAsString(executionResult.toSpecification());
        } catch (JsonProcessingException | IllegalArgumentException exception) {
            fail(exception.getMessage());
        }
        return result;
    }

    /**
     * Returns the identifier of the {@code toolName} tool in the {@code diagramElementId} element's palette.
     * <p>
     * This method produces a test failure if the underlying GraphQL query returns an error.
     * </p>
     *
     * @param projectId
     *            the project containing the element to retrieve the palette tool from
     * @param representationId
     *            the representation containing the element
     * @param diagramElementId
     *            the graphical identifier of the element to retrieve the palette tool from
     * @param toolSectionName
     *            the name of the tool section containing the tool
     * @param toolName
     *            the name of the tool
     * @return the identifier of the tool if it exists, or an {@code empty} {@link Optional}
     */
    public Optional<String> getTool(String projectId, String representationId, String diagramElementId, String toolSectionName, String toolName) {
        String rawPalette = this.getPalette(projectId, representationId, diagramElementId);
        Object palette = JsonPath.read(rawPalette,
                "$.data.viewer.editingContext.representation.description.palette.toolSections[?(@.label=='" + toolSectionName + "')].tools[?(@.label=='" + toolName + "')]");
        assertThat(palette).isInstanceOf(JSONArray.class);
        Optional<String> result = Optional.empty();
        JSONArray array = (JSONArray) palette;
        if (!array.isEmpty()) {
            assertThat(array).hasSize(1);
            Object toolObject = array.get(0);
            assertThat(toolObject).isInstanceOf(Map.class);
            Map<String, Object> toolMap = (Map<String, Object>) toolObject;
            Object toolIdObject = toolMap.get("id");
            assertThat(toolIdObject).isInstanceOf(String.class);
            result = Optional.of((String) toolIdObject);
        }
        return result;
    }
}
