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

import org.eclipse.papyrus.web.application.workspace.dto.RevokeProjectInput;
import org.eclipse.papyrus.web.application.workspace.services.api.IProjectApplicationRevokeService;
import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import com.fasterxml.jackson.databind.ObjectMapper;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Mutation#revokeProject.
 * 
 * @author Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr>
 */
@MutationDataFetcher(type = "Mutation", field = "revokeProject")
public class MutationRevokeProjectDataFetcher implements IDataFetcherWithFieldCoordinates<IPayload> {

    private static final String INPUT_ARGUMENT = "input";

    private final ObjectMapper objectMapper;
    private final IProjectApplicationRevokeService revokeProjectService;

    public MutationRevokeProjectDataFetcher(ObjectMapper objectMapper, IProjectApplicationRevokeService revokeProjectService) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.revokeProjectService = Objects.requireNonNull(revokeProjectService);
    }

    @Override
    public IPayload get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, RevokeProjectInput.class);
        return this.revokeProjectService.revokeProject(input);
    }
}
