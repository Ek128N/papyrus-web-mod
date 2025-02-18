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

import org.eclipse.sirius.web.application.document.dto.CreateDocumentInput;
import org.eclipse.sirius.web.tests.graphql.CreateDocumentMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Service used to create document inside a project.
 *
 * @author Arthur Daussy
 */
@Service
public class PapyrusCreateDocumentMutationRunner {

    private final CreateDocumentMutationRunner siriusDocumentMutationRunner;

    public PapyrusCreateDocumentMutationRunner(
            CreateDocumentMutationRunner siriusDocumentMutationRunner) {
        super();
        this.siriusDocumentMutationRunner = siriusDocumentMutationRunner;
    }

    public String createDocument(String editingContextId, String documentName, String stereotypeId) {

        var createDocumentInput = new CreateDocumentInput(UUID.randomUUID(), editingContextId, stereotypeId, documentName);

        var jsonResult = this.siriusDocumentMutationRunner.run(createDocumentInput);
        String responseTypeName = JsonPath.read(jsonResult, "$.data.createDocument.__typename");
        assertThat(responseTypeName).isEqualTo("CreateDocumentSuccessPayload");

        return JsonPath.read(jsonResult, "$.data.createDocument.document.id");
    }

}
