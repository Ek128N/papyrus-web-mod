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
package org.eclipse.papyrus.web.application.representations.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.eclipse.papyrus.web.application.representations.dto.CreateMetaclassImportInput;
import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.uml2.uml.ElementImport;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to create an {@link ElementImport} for a given UML metaclass.
 * <p>
 * It will be used to handle the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Mutation {
 *   createMetaclassImport(input: CreateMetaclassImportInput): CreateMetaclassImportPayload
 * }
 * </pre>
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@MutationDataFetcher(type = "Mutation", field = MutationCreateMetaclassImportDataFetcher.CREATE_METACLASS_IMPORT_FIELD)
public class MutationCreateMetaclassImportDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<IPayload>> {

    public static final String CREATE_METACLASS_IMPORT_FIELD = "createMetaclassImport";

    private final ObjectMapper objectMapper;

    private IEditingContextDispatcher editingContextDispatcher;

    public MutationCreateMetaclassImportDataFetcher(ObjectMapper objectMapper, IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public CompletableFuture<IPayload> get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument("input");
        var input = this.objectMapper.convertValue(argument, CreateMetaclassImportInput.class);

        return this.editingContextDispatcher.dispatchMutation(input.editingContextId(), input)
                .defaultIfEmpty(new ErrorPayload(input.id(), "Unexpected error"))
                .toFuture();
    }

}
