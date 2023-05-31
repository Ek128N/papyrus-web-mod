/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.papyrus.web.application.services;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.papyrus.web.graphql.datafetchers.IViewerProvider;
import org.eclipse.papyrus.web.services.api.viewer.IViewer;
import org.eclipse.papyrus.web.services.api.viewer.User;
import org.springframework.stereotype.Service;

import graphql.schema.DataFetchingEnvironment;

/**
 * Service used to retrieve the current viewer.
 *
 * @author sbegaudeau
 */
@Service
public class ViewerProvider implements IViewerProvider {
    @Override
    public Optional<IViewer> getViewer(DataFetchingEnvironment environment) {
        return Optional.of(new User(UUID.randomUUID(), "system")); //$NON-NLS-1$
    }

}
