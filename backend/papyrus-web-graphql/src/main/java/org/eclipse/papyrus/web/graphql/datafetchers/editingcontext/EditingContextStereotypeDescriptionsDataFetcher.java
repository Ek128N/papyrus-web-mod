/*******************************************************************************
 * Copyright (c) 2022 CEA, Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.graphql.datafetchers.editingcontext;

import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.papyrus.web.graphql.schema.EditingContextTypeProvider;
import org.eclipse.papyrus.web.services.api.stereotypes.IStereotypeDescriptionService;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.configuration.StereotypeDescription;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.relay.Connection;
import graphql.relay.ConnectionCursor;
import graphql.relay.DefaultConnection;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.DefaultPageInfo;
import graphql.relay.Edge;
import graphql.relay.PageInfo;
import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve the stereotypes accessible in an editing context.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type EditingContext {
 *   stereotypeDescriptions: EditingContextStereotypeDescriptionConnection!
 * }
 * </pre>
 *
 * @author hmarchadour
 */
@QueryDataFetcher(type = EditingContextTypeProvider.TYPE, field = EditingContextTypeProvider.STEREOTYPE_DESCRIPTIONS_FIELD)
public class EditingContextStereotypeDescriptionsDataFetcher implements IDataFetcherWithFieldCoordinates<Connection<StereotypeDescription>> {

    private final IStereotypeDescriptionService stereotypeDescriptionService;

    public EditingContextStereotypeDescriptionsDataFetcher(IStereotypeDescriptionService stereotypeDescriptionService) {
        this.stereotypeDescriptionService = Objects.requireNonNull(stereotypeDescriptionService);
    }

    @Override
    public Connection<StereotypeDescription> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        var stereotypeDescriptions = this.stereotypeDescriptionService.getStereotypeDescriptions(editingContextId)//
                .stream()//
                .sorted(Comparator.comparing(StereotypeDescription::getLabel));

        // @formatter:off
        List<Edge<StereotypeDescription>> stereotypeDescriptionEdges = stereotypeDescriptions
                .map(stereotypeDescription -> {
                    String value = Base64.getEncoder().encodeToString(stereotypeDescription.getId().toString().getBytes());
                    ConnectionCursor cursor = new DefaultConnectionCursor(value);
                    return new DefaultEdge<>(stereotypeDescription, cursor);
                })
                .collect(Collectors.toList());
        // @formatter:on

        ConnectionCursor startCursor = stereotypeDescriptionEdges.stream().findFirst().map(Edge::getCursor).orElse(null);
        ConnectionCursor endCursor = null;
        if (!stereotypeDescriptionEdges.isEmpty()) {
            endCursor = stereotypeDescriptionEdges.get(stereotypeDescriptionEdges.size() - 1).getCursor();
        }
        PageInfo pageInfo = new DefaultPageInfo(startCursor, endCursor, false, false);
        return new DefaultConnection<>(stereotypeDescriptionEdges, pageInfo);
    }
}
