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

import java.util.List;

import org.eclipse.papyrus.web.custom.widgets.containmentreference.ContainmentReferenceItem;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.URLConstants;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for ContainmentReferenceItem.iconURL, to rewrite the relative path of the image into an absolute path on the server.
 * <p>
 * If the <code>TreeItem.imageURL</code> is of the form <code>path/to/image.svg</code>, the rewritten value which will
 * be seen by the frontend will be <code>/api/images/path/to/image.svg</code>.
 *
 * @author Jerome Gout
 */
@QueryDataFetcher(type = "ContainmentReferenceItem", field = "iconURL")
public class ContainmentReferenceItemIconURLDataFetcher implements IDataFetcherWithFieldCoordinates<List<String>> {

    @Override
    public List<String> get(DataFetchingEnvironment environment) throws Exception {
        ContainmentReferenceItem item = environment.getSource();
        return item.getIconURL().stream().map(url -> URLConstants.IMAGE_BASE_PATH + url).toList();
    }
}
