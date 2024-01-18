/*******************************************************************************
 * Copyright (c) 2022, 2024 CEA LIST, Obeo.
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
 *******************************************************************************/
package org.eclipse.papyrus.web.graphql.datafetchers.mutation;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.eclipse.papyrus.web.services.api.uml.profile.PublishProfileInput;
import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.graphql.messages.IGraphQLMessageService;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to publish profiles.
 *
 * @author sbegaudeau
 */
@MutationDataFetcher(type = "Mutation", field = "publishProfile")
public class MutationPublishProfileDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<IPayload>> {

    private final ObjectMapper objectMapper;

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    private final IGraphQLMessageService messageService;

    public MutationPublishProfileDataFetcher(ObjectMapper objectMapper, IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry, IGraphQLMessageService messageService) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public CompletableFuture<IPayload> get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument("input");
        var input = this.objectMapper.convertValue(argument, PublishProfileInput.class);

        return this.editingContextEventProcessorRegistry.dispatchEvent(input.editingContextId(), input)
                .defaultIfEmpty(new ErrorPayload(input.id(), this.messageService.unexpectedError()))
                .toFuture();
    }

}
