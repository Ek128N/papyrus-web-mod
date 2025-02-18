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

import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramEventInput;
import org.eclipse.sirius.components.diagrams.tests.graphql.DiagramEventSubscriptionRunner;
import org.springframework.stereotype.Service;

/**
 * Service used to create a subscription on a given diagram.
 * <p>
 * Creating a subscription allows to perform graphical-level operations via GraphQL mutations. Tests that require such graphical operation need to create the subscription before performing any
 * graphical-level operation (see {@link #createSubscription(String, String)}).
 * </p>
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@Service
public class PapyrusDiagramEventSubscriptionRunner {

    private DiagramEventSubscriptionRunner runner;

    /**
     * Initializes the runner with the provided {@code graphQL} and {@code objectMapper}.
     *
     * @param graphQL
     *         the GraphQL execution engine
     * @param objectMapper
     *         the object mapper
     */
    public PapyrusDiagramEventSubscriptionRunner(DiagramEventSubscriptionRunner runner) {
        this.runner = runner;
    }

    /**
     * Creates a subscription for the provided {@code diagramId} diagram.
     * <p>
     * This method produces a test failure if the underlying GraphQL query returns an error.
     * </p>
     *
     * @param editingContextId
     *         the project containing the diagram on which to open a subscription
     * @param diagramId
     *         the identifier of the diagram to open a subscription from
     */
    public void createSubscription(String editingContextId, String diagramId) {
        DiagramEventInput diagramEventInput = new DiagramEventInput(UUID.randomUUID(), editingContextId, diagramId);

        this.runner.run(diagramEventInput);
    }

}
