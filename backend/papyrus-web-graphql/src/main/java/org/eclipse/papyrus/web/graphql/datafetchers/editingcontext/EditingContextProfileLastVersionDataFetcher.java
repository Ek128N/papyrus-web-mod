/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.papyrus.web.graphql.datafetchers.editingcontext;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.papyrus.web.services.api.dto.GetProfileLastVersionInput;
import org.eclipse.papyrus.web.services.api.dto.GetProfileLastVersionSuccessPayload;
import org.eclipse.papyrus.web.services.api.uml.profile.UMLProfileVersion;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve the last version of an UML Profile.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type EditingContext {
 *   profileLastVersion(elementId: ID!): UMLProfileVersion
 * }
 * </pre>
 *
 * @author lfasani
 */
@QueryDataFetcher(type = "EditingContext", field = "profileLastVersion")
public class EditingContextProfileLastVersionDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<UMLProfileVersion>> {

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public EditingContextProfileLastVersionDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<UMLProfileVersion> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        String elementId = environment.getArgument("profileId");

        GetProfileLastVersionInput input = new GetProfileLastVersionInput(UUID.randomUUID(), elementId);
        return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, input)//
                .filter(GetProfileLastVersionSuccessPayload.class::isInstance)//
                .map(GetProfileLastVersionSuccessPayload.class::cast)//
                .map(GetProfileLastVersionSuccessPayload::getProfileLastVersion)//
                .toFuture();
    }
}
