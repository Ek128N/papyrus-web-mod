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
package org.eclipse.papyrus.web.application.readonly.services.api;

/**
 * Checks if an object is read only.
 *
 * @author Arthur Daussy
 */
public interface IPapyrusReadOnlyChecker {

    /**
     * Check if the given object is read only.
     *
     * @return <code>true</code> if the given object is read only
     */
    boolean isReadOnly(Object object);

}
