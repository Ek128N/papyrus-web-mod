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

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnTwoDiagramElementsToolInput;
import org.eclipse.sirius.components.diagrams.tests.graphql.InvokeSingleClickOnTwoDiagramElementsToolMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Service used to invoke a tool on two elements.
 * <p>
 * This class instantiates and runs the {@code invokeSingleClickOnTwoDiagramElementsTool}. The invoked tool can be
 * retrieved via {@link PapyrusPaletteToolQueryRunner#getTool(UUID, UUID, UUID, String, String)}.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@Service
public class PapyrusInvokeSingleClickOnTwoDiagramElementsToolRunner {

    private final InvokeSingleClickOnTwoDiagramElementsToolMutationRunner runner;

    /**
     * Initializes the runner with the provided {@code graphQL} and {@code objectMapper}.
     *
     * @param graphQL
     *            the GraphQL execution engine
     * @param objectMapper
     *            the object mapper
     */
    public PapyrusInvokeSingleClickOnTwoDiagramElementsToolRunner(InvokeSingleClickOnTwoDiagramElementsToolMutationRunner runner) {
        this.runner = runner;
    }

    /**
     * Invokes the {@code toolId} tool on {@code diagramSourceElementId} and {@code diagramTargetElementId} elements.
     * <p>
     * This method invokes the given tool on two elements, see {@link #invokeTool(String, String, String, String, String)} ()} to invoke a
     * tool on a single element.
     * </p>
     * <p>
     * This method produces a test failure if the underlying GraphQL query returns an error.
     * </p>
     *
     * @param projectId
     *            the project containing the element on which the tool is invoked
     * @param representationId
     *            the representation containing the elements
     * @param diagramSourceElementId
     *            the graphical identifier of the source element on which the tool is invoked
     * @param diagramTargetElementId
     *            the graphical identifier of the target element on which the tool is invoked
     * @param toolId
     *            the identifier of the tool to invoke
     */
    public void invokeTool(String projectId, String representationId, String diagramSourceElementId, String diagramTargetElementId, String toolId) {
        // Source and target positions aren't relevant when invoking the tool manually, so we set them to 0
        InvokeSingleClickOnTwoDiagramElementsToolInput invokeSingleClickOnTwoDiagramElementsToolInput = new InvokeSingleClickOnTwoDiagramElementsToolInput(UUID.randomUUID(), projectId,
                representationId, diagramSourceElementId, diagramTargetElementId, 0, 0, 0, 0, toolId, List.of());

        String jsonResult = this.runner.run(invokeSingleClickOnTwoDiagramElementsToolInput);

        String responseTypeName = JsonPath.read(jsonResult, "$.data.invokeSingleClickOnTwoDiagramElementsTool.__typename");
        assertThat(responseTypeName).isEqualTo("InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload");
    }

}
