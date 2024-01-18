/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.graphql.datafetchers.editingcontext;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.papyrus.web.services.api.dto.GetStereotypesInput;
import org.eclipse.papyrus.web.services.api.dto.GetStereotypesSuccessPayload;
import org.eclipse.papyrus.web.services.api.uml.profile.UMLStereotypeMetadata;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve the applicable Stereotype for an UML Element.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type EditingContext {
 *   stereotypeMetatadas(elementId: ID!): [UMLStereotypeMetadata!]!
 * }
 * </pre>
 *
 * @author lfasani
 */
@QueryDataFetcher(type = "EditingContext", field = "stereotypeMetatadas")
public class EditingContextStereotypesDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<UMLStereotypeMetadata>>> {

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public EditingContextStereotypesDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<List<UMLStereotypeMetadata>> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        String elementId = environment.getArgument("elementId");

        GetStereotypesInput input = new GetStereotypesInput(UUID.randomUUID(), elementId);
        return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, input)
                .filter(GetStereotypesSuccessPayload.class::isInstance)
                .map(GetStereotypesSuccessPayload.class::cast)
                .map(GetStereotypesSuccessPayload::getStereotypes)
                .toFuture();
    }
}
