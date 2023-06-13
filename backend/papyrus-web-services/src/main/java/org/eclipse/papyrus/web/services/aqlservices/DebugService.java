/*******************************************************************************
 * Copyright (c) 2022 CEA, Obeo
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.services.aqlservices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service used for debugging.
 *
 * @author Arthur Daussy
 */
public class DebugService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DebugService.class);

    public Object logInConsole(Object eObject, String message) {
        LOGGER.info(message);
        return eObject;
    }

    public void logInConsole(String message) {
        LOGGER.info(message);
    }
}
