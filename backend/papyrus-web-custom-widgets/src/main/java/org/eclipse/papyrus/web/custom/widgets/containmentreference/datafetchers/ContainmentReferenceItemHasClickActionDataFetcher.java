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
package org.eclipse.papyrus.web.custom.widgets.containmentreference.datafetchers;

import org.eclipse.papyrus.web.custom.widgets.containmentreference.ContainmentReferenceItem;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to populate ContainmentReferenceItem's hasClickAction field.
 * @author Jerome Gout
 */
@QueryDataFetcher(type = "ContainmentReferenceItem", field = "hasClickAction")
public class ContainmentReferenceItemHasClickActionDataFetcher implements IDataFetcherWithFieldCoordinates<Boolean> {

    @Override
    public Boolean get(DataFetchingEnvironment environment) throws Exception {
        ContainmentReferenceItem item = environment.getSource();
        return item.getClickHandler() != null;
    }
}
