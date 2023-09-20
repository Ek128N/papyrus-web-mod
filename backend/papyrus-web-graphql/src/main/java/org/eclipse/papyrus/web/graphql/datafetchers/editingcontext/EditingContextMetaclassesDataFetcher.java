/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.graphql.datafetchers.editingcontext;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.papyrus.web.services.api.dto.GetMetaclassMetadatasInput;
import org.eclipse.papyrus.web.services.api.dto.GetMetaclassMetadatasSuccessPayload;
import org.eclipse.papyrus.web.services.api.uml.profile.UMLMetaclassMetadata;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve the UML metaclass metadatas.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type EditingContext {
 *   metaclassMetadatas: [UMLMetaclassMetadata]
 * }
 * </pre>
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@QueryDataFetcher(type = "EditingContext", field = "metaclassMetadatas")
public class EditingContextMetaclassesDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<UMLMetaclassMetadata>>> {

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public EditingContextMetaclassesDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<List<UMLMetaclassMetadata>> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        GetMetaclassMetadatasInput input = new GetMetaclassMetadatasInput(UUID.randomUUID());

        return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, input)//
                .filter(GetMetaclassMetadatasSuccessPayload.class::isInstance)//
                .map(GetMetaclassMetadatasSuccessPayload.class::cast)//
                .map(GetMetaclassMetadatasSuccessPayload::getMetaclassMetadatas)//
                .toFuture();
    }
}
