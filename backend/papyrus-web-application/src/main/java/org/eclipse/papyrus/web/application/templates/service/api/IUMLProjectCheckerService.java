/*****************************************************************************
 * Copyright (c) 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.templates.service.api;

/**
 * Checks if a editing context is a UML context.
 *
 * @author Arthur Daussy
 */
public interface IUMLProjectCheckerService {

    /**
     * Check if editing context identified by it id is a UML editing context.
     *
     * @param editingContextId
     *            an id
     * @return
     */
    boolean isPapyrusProject(String editingContextId);
}
