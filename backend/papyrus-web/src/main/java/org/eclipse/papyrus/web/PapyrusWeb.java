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
package org.eclipse.papyrus.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Entry point of the Sirius Web server.
 *
 * @author Arthur Daussy
 */
@SpringBootApplication
@ComponentScan(basePackages = { "org.eclipse.papyrus.web", "org.eclipse.sirius.components", "org.eclipse.sirius.web" })
public class PapyrusWeb {
    /**
     * The entry point of the server.
     *
     * @param args
     *            The command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(PapyrusWeb.class, args);
    }
}
