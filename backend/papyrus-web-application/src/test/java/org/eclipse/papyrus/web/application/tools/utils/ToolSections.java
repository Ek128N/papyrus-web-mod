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
package org.eclipse.papyrus.web.application.tools.utils;

/**
 * Generic tool section that are common to most Papyrus Web Diagrams.
 * <p>
 * This class can be extended by diagram-specific tool sections.
 * </p>
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class ToolSections {

    /**
     * The default tool section to create nodes.
     */
    public static final String NODES = "Nodes";

    /**
     * The default tool section to create edges.
     */
    public static final String EDGES = "Edges";

    protected ToolSections() {
        // protected to prevent instantiation and allow subclasses.
    }

}
