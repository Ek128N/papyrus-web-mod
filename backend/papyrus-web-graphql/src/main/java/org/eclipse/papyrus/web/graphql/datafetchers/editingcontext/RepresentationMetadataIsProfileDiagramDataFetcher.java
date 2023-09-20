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

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.papyrus.web.services.api.dto.IsProfileDiagramInput;
import org.eclipse.papyrus.web.services.api.dto.IsProfileDiagramSuccessPayload;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve the {@code isProfileDiagram} field.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type EditingContext {
 *   isProfileDiagram: Boolean!
 * }
 * </pre>
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@QueryDataFetcher(type = "RepresentationMetadata", field = "isProfileDiagram")
public class RepresentationMetadataIsProfileDiagramDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<Boolean>> {

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public RepresentationMetadataIsProfileDiagramDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<Boolean> get(DataFetchingEnvironment environment) throws Exception {
        RepresentationMetadata representationMetadata = environment.getSource();
        IsProfileDiagramInput input = new IsProfileDiagramInput(UUID.randomUUID(), representationMetadata.getId());

        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);

        return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, input)//
                .filter(IsProfileDiagramSuccessPayload.class::isInstance)//
                .map(IsProfileDiagramSuccessPayload.class::cast)//
                .map(IsProfileDiagramSuccessPayload::getIsProfileDiagram)//
                .toFuture();
    }
}
