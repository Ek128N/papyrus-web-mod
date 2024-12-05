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
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.web.application.readonly.services;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.papyrus.web.application.readonly.ReadOnlyAdapter;
import org.eclipse.papyrus.web.application.readonly.services.api.IPapyrusReadOnlyChecker;
import org.springframework.stereotype.Service;

/**
 * Checks if an element is read only.
 *
 * @author Arthur Daussy
 */
@Service
public class PapyrusReadOnlyChecker implements IPapyrusReadOnlyChecker {

    @Override
    public boolean isReadOnly(Object object) {
        if (object instanceof Notifier notifier) {
            return notifier.eAdapters().stream().anyMatch(ReadOnlyAdapter.class::isInstance);

        }
        return false;
    }

}
