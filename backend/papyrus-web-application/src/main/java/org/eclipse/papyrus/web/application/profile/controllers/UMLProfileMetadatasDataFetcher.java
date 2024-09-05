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
package org.eclipse.papyrus.web.application.profile.controllers;

import java.util.List;
import java.util.Objects;

import org.eclipse.papyrus.web.application.profile.dto.UMLProfileMetadata;
import org.eclipse.papyrus.web.application.profile.services.api.IUMLProfileService;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve all the UML Profiles.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Viewer {
 *   profileMetadatas: [UMLProfileMetadata!]!
 * }
 * </pre>
 *
 * @author lfasani
 */
@QueryDataFetcher(type = "Viewer", field = "profileMetadatas")
public class UMLProfileMetadatasDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<List<UMLProfileMetadata>>> {

    private final IUMLProfileService umlProfileService;

    public UMLProfileMetadatasDataFetcher(IUMLProfileService umlProfileService) {
        this.umlProfileService = Objects.requireNonNull(umlProfileService);
    }

    @Override
    public DataFetcherResult<List<UMLProfileMetadata>> get(DataFetchingEnvironment environment) throws Exception {
        List<UMLProfileMetadata> profileMetadatas = this.umlProfileService.getAllUMLProfiles();

        return DataFetcherResult.<List<UMLProfileMetadata>>newResult()
                .data(profileMetadatas)
                .build();
    }
}
