/*******************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.representations.view;

import java.util.List;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;

/**
 * Tooling for creation tools creation.
 * 
 * @author Jerome Gout
 */
public class CreationToolsUtil {

    /**
     * Adds the given creation {@link EdgeTool} inside all given {@link NodeDescription}.
     * 
     * @param owners
     *            a Supplier of tool owners. The nodes that contain this creation tool.
     * @param tool
     *            an edge creation tool
     */
    public static void addEdgeCreationTool(Supplier<List<NodeDescription>> owners, EdgeTool tool) {
        owners.get().forEach(owner -> {
            owner.getPalette().getEdgeTools().add(EcoreUtil.copy(tool));
        });
    }

    /**
     * Adds the given creation {@link NodeTool} inside all given {@link NodeDescription}.
     * 
     * @param owners
     *            a Supplier of tool owners. The nodes that contain this creation tool.
     * @param tool
     *            a node creation tool. This tool will be cloned before being inserted in palette.
     */
    public static void addNodeCreationTool(Supplier<List<NodeDescription>> owners, NodeTool tool) {
        owners.get().forEach(owner -> {
            owner.getPalette().getNodeTools().add(EcoreUtil.copy(tool));
        });
    }
}
