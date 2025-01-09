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
package org.eclipse.papyrus.web.application.popup.service;

import org.springframework.stereotype.Service;

/**
 * PopupState Service that sends the current Popupstate to the subscribers.
 *
 * @author Eeshvaran Dilan
 */
@Service
public class PopupStateService {

    private boolean shouldShow;

    public boolean shouldShow() {
        return this.shouldShow;
    }

    public void updateShouldShow(boolean shouldShowValue) {
        this.shouldShow = shouldShowValue;
    }
}