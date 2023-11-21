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

import org.eclipse.papyrus.web.custom.widgets.containmentreference.ContainmentReference;
import org.eclipse.papyrus.web.custom.widgets.containmentreference.ContainmentReferenceWidget;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for ContainmentReferenceWidget.containmentReference.
 *
 * @author Jerome Gout
 */
@QueryDataFetcher(type = "ContainmentReferenceWidget", field = "containmentReference")
public class ContainmentReferenceWidgetDataFetcher implements IDataFetcherWithFieldCoordinates<ContainmentReference> {

    @Override
    public ContainmentReference get(DataFetchingEnvironment environment) throws Exception {
        ContainmentReferenceWidget widget = environment.getSource();
        return new ContainmentReference(widget.getOwnerKind(),
                widget.getReferenceKind(),
                widget.isMany(), widget.canMove());
    }

}
