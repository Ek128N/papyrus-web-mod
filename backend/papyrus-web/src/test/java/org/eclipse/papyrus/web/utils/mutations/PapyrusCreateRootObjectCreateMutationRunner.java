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

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.UUID;

import org.eclipse.sirius.components.collaborative.dto.CreateRootObjectInput;
import org.eclipse.sirius.web.tests.graphql.CreateRootObjectMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Service use to create a root object inside a project using a GraphQL mutation.
 *
 * @author Arthur Daussy
 */
@Service
public class PapyrusCreateRootObjectCreateMutationRunner {

    private CreateRootObjectMutationRunner createRootObjectMutationRunner;

    public PapyrusCreateRootObjectCreateMutationRunner(CreateRootObjectMutationRunner createRootObjectMutationRunner) {
        super();
        this.createRootObjectMutationRunner = createRootObjectMutationRunner;
    }

    public String createRootObject(String ePackageNsURI, String type, String documentId, String projectId) {

        var createRootObjectInput = new CreateRootObjectInput(UUID.randomUUID(), projectId, UUID.fromString(documentId), ePackageNsURI, type);
        String rootObjectId = null;
        var jsonResult = this.createRootObjectMutationRunner.run(createRootObjectInput);
        String responseTypeName = JsonPath.read(jsonResult, "$.data.createRootObject.__typename");
        assertThat(responseTypeName).isEqualTo("CreateRootObjectSuccessPayload");

        rootObjectId = JsonPath.read(jsonResult, "$.data.createRootObject.object.id");
        return rootObjectId;
    }

}
