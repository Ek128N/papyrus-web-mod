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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.components.collaborative.dto.CreateChildInput;
import org.eclipse.sirius.web.tests.graphql.CreateChildMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Service used to create a semantic element inside a given container.
 * <p>
 * This class instantiates and runs the {@code createChild} mutation. Note that this mutation only performs semantic
 * operations, and doesn't perform any graphical operation.
 * </p>
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@Service
public class PapyrusCreateChildMutationRunner {

    private static final Pattern WORD_FINDER = Pattern.compile("(([A-Z]?[a-z]+)|([A-Z]))");

    private CreateChildMutationRunner runner;

    /**
     * Initializes the runner with the provided {@code graphQL} and {@code objectMapper}.
     *
     * @param graphQL
     *            the GraphQL execution engine
     * @param objectMapper
     *            the object mapper
     */
    public PapyrusCreateChildMutationRunner(CreateChildMutationRunner runner) {
        this.runner = runner;
    }

    /**
     * Creates a {@code childType} element in {@code parentElementId} with the provided {@code containmentReference}.
     * <p>
     * This method produces a test failure if the underlying GraphQL query returns an error.
     * </p>
     *
     * @param projectId
     *            the project containing the element the create
     * @param parentElementId
     *            the identifier of the semantic parent of the element to create
     * @param containmentReference
     *            the containment reference of the element to create
     * @param childType
     *            the type of the element to create
     */
    public void createChild(String projectId, String parentElementId, EReference containmentReference, EClass childType) {
        // Build the childCreationDescriptionId with the following pattern: referenceName - type. This pattern is
        // required by the backend.
        String childCreationDescriptionId = containmentReference.getName() + "-" + childType.getName();
        CreateChildInput createChildInput = new CreateChildInput(UUID.randomUUID(), projectId, parentElementId, childCreationDescriptionId);

        String jsonResult = this.runner.run(createChildInput);
        String responseTypeName = JsonPath.read(jsonResult, "$.data.createChild.__typename");
        assertThat(responseTypeName).isEqualTo("CreateChildSuccessPayload");
    }

    private List<String> findWordsInMixedCase(String text) {
        Matcher matcher = WORD_FINDER.matcher(text);
        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            words.add(matcher.group(0));
        }
        return words;
    }

}
