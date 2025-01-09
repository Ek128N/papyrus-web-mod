/*****************************************************************************
 * Copyright (c) 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  EESHVARAN Dilan - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.web.application.popup.controllers;

import org.eclipse.papyrus.web.application.popup.service.PopupStateService;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * Query Data Fetcher that returns the current popup state as a string value.
 *
 * @author Eeshvaran Dilan
 */
@QueryDataFetcher(type = "Query", field = "showPopup")
public class ShowPopupDataFetcher implements IDataFetcherWithFieldCoordinates<String> {

    private final PopupStateService popupStateService;

    public ShowPopupDataFetcher(PopupStateService popupStateService) {
        this.popupStateService = popupStateService;
    }

    @Override
    public String get(DataFetchingEnvironment environment) {
        if (this.popupStateService.shouldShow()) {
            return "Show Popup";
        } else {
            return "Do Not Show";
        }
    }
}