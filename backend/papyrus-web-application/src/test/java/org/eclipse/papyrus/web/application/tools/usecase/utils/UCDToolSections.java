/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.tools.usecase.utils;

import org.eclipse.papyrus.web.application.tools.utils.ToolSections;

/**
 * The tool sections for the Use Case Diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public final class UCDToolSections extends ToolSections {

    /**
     * The tool section to create classifiers as subjects.
     */
    public static final String SUBJECT = "Subject";

    private UCDToolSections() {
        // private to prevent instantiation.
    }

}
