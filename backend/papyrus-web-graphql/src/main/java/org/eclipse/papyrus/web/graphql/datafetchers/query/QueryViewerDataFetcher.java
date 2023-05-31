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
package org.eclipse.papyrus.web.graphql.datafetchers.query;

import java.util.Objects;

import org.eclipse.papyrus.web.graphql.datafetchers.IViewerProvider;
import org.eclipse.papyrus.web.graphql.schema.QueryTypeProvider;
import org.eclipse.papyrus.web.services.api.viewer.IViewer;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve the viewer of a query.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Query {
 *   viewer: Viewer
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = QueryTypeProvider.TYPE, field = QueryTypeProvider.VIEWER_FIELD)
public class QueryViewerDataFetcher implements IDataFetcherWithFieldCoordinates<IViewer> {

    private final IViewerProvider viewerProvider;

    public QueryViewerDataFetcher(IViewerProvider viewerProvider) {
        this.viewerProvider = Objects.requireNonNull(viewerProvider);
    }

    @Override
    public IViewer get(DataFetchingEnvironment environment) throws Exception {
        return this.viewerProvider.getViewer(environment).orElse(null);
    }
}
