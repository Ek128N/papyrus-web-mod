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

import org.eclipse.papyrus.web.services.api.dto.ApplyStereotypeInput;
import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.graphql.messages.IGraphQLMessageService;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * The data fetcher used to apply an UML Stereotype on a UML Element.
 * <p>
 * It will be used to handle the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Mutation {
 *   applyStereotype(input: ApplyStereotypeInput!): ApplyStereotypePayload!
 * }
 * </pre>
 *
 * @author lfasani
 */
@MutationDataFetcher(type = "Mutation", field = MutationApplyStereotypeDataFetcher.APPLY_STEREOTYPE_FIELD)
public class MutationApplyStereotypeDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<IPayload>> {

    public static final String APPLY_STEREOTYPE_FIELD = "applyStereotype";

    private final ObjectMapper objectMapper;

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    private final IGraphQLMessageService messageService;

    public MutationApplyStereotypeDataFetcher(ObjectMapper objectMapper, IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry, IGraphQLMessageService messageService) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public CompletableFuture<IPayload> get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument("input");
        var input = this.objectMapper.convertValue(argument, ApplyStereotypeInput.class);

        Mono<IPayload> defaultIfEmpty = this.editingContextEventProcessorRegistry.dispatchEvent(input.editingContextId(), input)
            .defaultIfEmpty(new ErrorPayload(input.id(), this.messageService.unexpectedError()));
        return defaultIfEmpty.log().toFuture();
    }

}
