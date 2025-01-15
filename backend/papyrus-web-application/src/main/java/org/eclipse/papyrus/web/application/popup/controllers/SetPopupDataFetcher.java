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
import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * Mutation data fetcher to update the popup state.
 *
 * @author Eeshvaran Dilan
 */
@MutationDataFetcher(type = "Mutation", field = "setPopup")
public class SetPopupDataFetcher implements IDataFetcherWithFieldCoordinates<String> {

    private final PopupStateService popupStateService;

    public SetPopupDataFetcher(PopupStateService popupStateService) {
        this.popupStateService = popupStateService;
    }

    @Override
    public String get(DataFetchingEnvironment environment) {
        Boolean shouldShow = environment.getArgument("shouldShow");
        this.popupStateService.updateShouldShow(shouldShow);
        return "Popup state updated";
    }
}
