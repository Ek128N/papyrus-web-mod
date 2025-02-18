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
package org.eclipse.papyrus.web.utils.mutations;

import com.jayway.jsonpath.JsonPath;

import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.graphql.tests.CreateRepresentationMutationRunner;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.springframework.stereotype.Service;

import net.minidev.json.JSONArray;

/**
 * Service use to create a representation.
 *
 * @author Arthur Daussy
 */
@Service
public class PapyrusCreateRepresentationMutationRunner {

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

    private CreateRepresentationMutationRunner runner;

    private IGraphQLRequestor graphQLRequestor;

    public PapyrusCreateRepresentationMutationRunner(CreateRepresentationMutationRunner runner, IGraphQLRequestor graphQLRequestor) {
        super();
        this.runner = runner;
        this.graphQLRequestor = graphQLRequestor;
    }

    public String createRepresentation(String editingContextId, String targetObject, String representationDescriptionName, String representationName) {

        Map<String, Object> parameters = Map.of("editingContextId", editingContextId, "objectId", targetObject);
        String jsonResult0 = this.graphQLRequestor.execute(representationQuery, parameters);

        var matches = (JSONArray) JsonPath.read(jsonResult0, "$.data.viewer.editingContext.representationDescriptions.edges[?(@.node.label=='" + representationDescriptionName + "')].node.id");
        String representationDescriptionId = (String) matches.get(0);

        var input = new CreateRepresentationInput(UUID.randomUUID(), editingContextId, representationDescriptionId, targetObject, representationName);

        var jsonResult = this.runner.run(input);
        String representationId = JsonPath.read(jsonResult, "$.data.createRepresentation.representation.id");

        return representationId;
    }

}
