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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramEventInput;
import org.springframework.stereotype.Service;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;

/**
 * Service used to create a subscription on a given diagram.
 * <p>
 * Creating a subscription allows to perform graphical-level operations via GraphQL mutations. Tests that require such
 * graphical operation need to create the subscription before performing any graphical-level operation (see
 * {@link #createSubscription(String, String)}).
 * </p>
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@Service
public class DiagramEventSubscriptionRunner {

    private static String subscription = """
              subscription diagramEvent($input: DiagramEventInput!) {
                diagramEvent(input: $input) {
                  __typename
                  ... on ErrorPayload {
                    id
                    message
                  }
                  ... on SubscribersUpdatedEventPayload {
                    id
                    subscribers {
                      username
                    }
                  }
                  ... on DiagramRefreshedEventPayload {
                    id
                    diagram {
                      id
                      metadata {
                        kind
                        label
                      }
                      targetObjectId
                      position {
                        x
                        y
                      }
                      size {
                        width
                        height
                      }
                      nodes {
                        ...nodeFields
                        borderNodes {
                          ...nodeFields
                          borderNodes {
                            ...nodeFields
                          }
                          childNodes {
                            ...nodeFields
                            borderNodes {
                              ...nodeFields
                            }
                            childNodes {
                              ...nodeFields
                              borderNodes {
                                ...nodeFields
                              }
                            }
                          }
                        }
                        childNodes {
                          ...nodeFields
                          borderNodes {
                            ...nodeFields
                          }
                          childNodes {
                            ...nodeFields
                            borderNodes {
                              ...nodeFields
                            }
                            childNodes {
                              ...nodeFields
                              borderNodes {
                                ...nodeFields
                              }
                              childNodes {
                                ...nodeFields
                                borderNodes {
                                  ...nodeFields
                                }
                                childNodes {
                                  ...nodeFields
                                }
                              }
                            }
                          }
                        }
                      }
                      edges {
                        ...edgeFields
                      }
                    }
                  }
                }
              }

              fragment nodeFields on Node {
                id
                type
                targetObjectId
                targetObjectKind
                targetObjectLabel
                descriptionId
                state
                insideLabel {
                  ...insideLabelFields
                }
                style {
                  ... on RectangularNodeStyle {
                    color
                    borderColor
                    borderStyle
                    borderSize
                    borderRadius
                  }
                  ... on ImageNodeStyle {
                    imageURL
                    borderColor
                    borderStyle
                    borderSize
                    borderRadius
                  }
                  ... on ParametricSVGNodeStyle {
                    svgURL
                    backgroundColor
                    borderColor
                    borderRadius
                    borderSize
                    borderStyle
                  }
                  ... on IconLabelNodeStyle {
                    backgroundColor
                  }
                }
                position {
                  x
                  y
                }
                size {
                  width
                  height
                }
                userResizable
              }

              fragment edgeFields on Edge {
                id
                type
                targetObjectId
                targetObjectKind
                targetObjectLabel
                descriptionId
                sourceId
                targetId
                state
                beginLabel {
                  ...labelFields
                }
                centerLabel {
                  ...labelFields
                }
                endLabel {
                  ...labelFields
                }
                style {
                  size
                  lineStyle
                  sourceArrow
                  targetArrow
                  color
                }
                routingPoints {
                  x
                  y
                }
                sourceAnchorRelativePosition {
                  x
                  y
                }
                targetAnchorRelativePosition {
                  x
                  y
                }
              }

              fragment labelFields on Label {
                id
                type
                text
                style {
                  color
                  fontSize
                  bold
                  italic
                  underline
                  strikeThrough
                  iconURL
                }
                position {
                  x
                  y
                }
                size {
                  width
                  height
                }
                alignment {
                  x
                  y
                }
              }

              fragment insideLabelFields on InsideLabel {
                id
                type
                text
                style {
                  color
                  fontSize
                  bold
                  italic
                  underline
                  strikeThrough
                  iconURL
                }
                position {
                  x
                  y
                }
                size {
                  width
                  height
                }
                alignment {
                  x
                  y
                }
                isHeader
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
    public DiagramEventSubscriptionRunner(GraphQL graphQL, ObjectMapper objectMapper) {
        this.graphQL = graphQL;
        this.objectMapper = objectMapper;
    }

    /**
     * Creates a subscription for the provided {@code diagramId} diagram.
     * <p>
     * This method produces a test failure if the underlying GraphQL query returns an error.
     * </p>
     *
     * @param projectId
     *            the project containing the diagram on which to open a subscription
     * @param diagramId
     *            the identifier of the diagram to open a subscription from
     */
    public void createSubscription(String projectId, String diagramId) {
        DiagramEventInput diagramEventInput = new DiagramEventInput(UUID.randomUUID(), projectId, diagramId);
        ExecutionInput subscriptionInput = ExecutionInput.newExecutionInput(subscription) //
                .variables(Map.of("input", this.objectMapper.convertValue(diagramEventInput, new TypeReference<Map<String, Object>>() { /**/ }))) //
                .build();
        ExecutionResult subscriptionResult = this.graphQL.execute(subscriptionInput);
        assertThat(subscriptionResult.getErrors()).isEmpty();
    }

}
