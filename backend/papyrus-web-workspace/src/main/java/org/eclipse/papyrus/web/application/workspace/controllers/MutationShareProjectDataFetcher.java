/*****************************************************************************
 * Copyright (c) 2024 CEA LIST
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.web.application.workspace.controllers;

import java.util.Objects;

import org.eclipse.papyrus.web.application.workspace.dto.ShareProjectInput;
import org.eclipse.papyrus.web.application.workspace.services.api.IProjectApplicationShareService;
import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import com.fasterxml.jackson.databind.ObjectMapper;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Mutation#shareProject.
 * 
 * @author Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr>
 */
@MutationDataFetcher(type = "Mutation", field = "shareProject")
public class MutationShareProjectDataFetcher implements IDataFetcherWithFieldCoordinates<IPayload> {

    private static final String INPUT_ARGUMENT = "input";

    private final ObjectMapper objectMapper;
    private final IProjectApplicationShareService shareProjectService;

    public MutationShareProjectDataFetcher(ObjectMapper objectMapper, IProjectApplicationShareService shareProjectService) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.shareProjectService = Objects.requireNonNull(shareProjectService);
    }

    @Override
    public IPayload get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, ShareProjectInput.class);
        return this.shareProjectService.shareProject(input);
    }
}
