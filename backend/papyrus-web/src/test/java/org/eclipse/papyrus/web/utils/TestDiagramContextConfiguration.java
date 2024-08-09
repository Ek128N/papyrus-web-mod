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
package org.eclipse.papyrus.web.utils;

import org.eclipse.papyrus.web.tools.configuration.ProjectInitializerEventHandler;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Configuration used for the test on diagrams. It provider event handler to give the capacity to initialize the content
 * of a projet.
 *
 * @author Arthur Daussy
 */
@TestConfiguration
public class TestDiagramContextConfiguration {

    @Bean
    public IEditingContextEventHandler testContextEventHandler() {
        return new ProjectInitializerEventHandler();
    }

}
