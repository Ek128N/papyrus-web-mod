/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.papyrus.web.tests.utils;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.domain.services.properties.ILogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * This class is used to mock the behavior of {@link ILogger}.
 *
 * @author Jerome Gout
 *
 */
public class MockLogger implements ILogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockLogger.class);

    @Override
    public void log(String message, ILogLevel level) {
        if (level == ILogger.ILogLevel.DEBUG) {
            LOGGER.debug(message);
        } else if (level == ILogger.ILogLevel.ERROR) {
            LOGGER.error(message);
        } else if (level == ILogger.ILogLevel.WARNING) {
            LOGGER.warn(message);
        } else if (level == ILogger.ILogLevel.INFO) {
            LOGGER.info(message);
        } else {
            LOGGER.trace(message);
        }
    }

    @Override
    public String getLabelForLog(EObject object) {
        if (object instanceof ENamedElement) {
            return ((ENamedElement) object).getName();
        } else {
            return object.toString();
        }
    }

}
