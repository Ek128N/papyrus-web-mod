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
package org.eclipse.papyrus.web.application.tools.component.checker;

import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.papyrus.web.application.tools.checker.NodeCreationGraphicalChecker;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.uml2.uml.Interface;

/**
 * A specific graphical checker for {@link Interface} in the Component diagram.
 * <p>
 * Interface have 3 compartment, this changes the expected number of created elements in the diagram.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class CPDInterfaceCreationGraphicalChecker extends NodeCreationGraphicalChecker {

    public CPDInterfaceCreationGraphicalChecker(Supplier<Diagram> diagramSupplier, Supplier<IDiagramElement> graphicalOwnerSupplier, String mappingType,
            Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes) {
        super(diagramSupplier, graphicalOwnerSupplier, mappingType, convertedNodes);
    }

    @Override
    protected int getExpectedNumberOfCreatedElements() {
        // Interface have 3 compartment contained in their main view
        return 4;
    }

    @Override
    protected int getExpectedNumberOfGraphicalOwnerDirectChildren() {
        // Interface compartments aren't direct children of the interface's graphical owner
        return 1;
    }
}
