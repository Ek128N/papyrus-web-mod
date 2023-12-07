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
package org.eclipse.papyrus.web.custom.widgets.primitivelist.datafetchers;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.papyrus.web.custom.widgets.primitivelist.PrimitiveListCandidate;
import org.eclipse.papyrus.web.custom.widgets.primitivelist.dto.PrimitiveListCandidatesQueryInput;
import org.eclipse.papyrus.web.custom.widgets.primitivelist.dto.PrimitiveListCandidatesQueryPayload;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.IExceptionWrapper;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher used to retrieve candidates for a primitive list widget.
 * @author Jerome Gout
 */
@QueryDataFetcher(type = "FormDescription", field = "primitiveListCandidates")
public class FormDescriptionPrimitiveListCandidatesDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<PrimitiveListCandidate>>> {

    private static final String REPRESENTATION_ID = "representationId";

    private static final String PRIMITIVE_LIST_ID = "primitiveListId";

    private final IExceptionWrapper exceptionWrapper;

    private final IEditingContextDispatcher editingContextDispatcher;

    public FormDescriptionPrimitiveListCandidatesDataFetcher(IExceptionWrapper exceptionWrapper, IEditingContextDispatcher editingContextDispatcher) {
        this.exceptionWrapper = Objects.requireNonNull(exceptionWrapper);
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<List<PrimitiveListCandidate>> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);
        String primitiveListId = environment.getArgument(PRIMITIVE_LIST_ID);
        var input = new PrimitiveListCandidatesQueryInput(UUID.randomUUID(), editingContextId, representationId, primitiveListId);

        return this.exceptionWrapper.wrapMono(() -> this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input), input)
                .filter(PrimitiveListCandidatesQueryPayload.class::isInstance)
                .map(PrimitiveListCandidatesQueryPayload.class::cast)
                .map(PrimitiveListCandidatesQueryPayload::candidates)
                .toFuture();
    }

}
