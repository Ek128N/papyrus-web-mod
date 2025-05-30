/*******************************************************************************
 * Copyright (c) 2022, 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.utils;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Base configuration to be used to start Papyrs Web application for integration tests.
 *
 * @author Arthur Daussy
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@SpringBootApplication
// Keep in sync with org.eclipse.papyrus.web.PapyrusApplication
@ComponentScan(basePackages = { "org.eclipse.papyrus.web", "org.eclipse.sirius.components", "org.eclipse.sirius.web" })
public class IntegrationTestConfiguration {

}
