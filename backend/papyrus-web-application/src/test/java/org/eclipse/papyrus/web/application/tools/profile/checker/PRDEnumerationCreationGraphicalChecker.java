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
package org.eclipse.papyrus.web.application.tools.profile.checker;

import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.papyrus.web.application.tools.checker.NodeCreationGraphicalChecker;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.view.diagram.NodeDescription;

/**
 * A specific graphical checker for enumerations in the profile diagram.
 * <p>
 * Enumerations have 1 compartment, this changes the expected number of created elements in the diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class PRDEnumerationCreationGraphicalChecker extends NodeCreationGraphicalChecker {

    public PRDEnumerationCreationGraphicalChecker(Supplier<Diagram> diagramSupplier, Supplier<IDiagramElement> graphicalOwnerSupplier, String mappingType,
            Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes) {
        super(diagramSupplier, graphicalOwnerSupplier, mappingType, convertedNodes);
    }

    @Override
    protected int getExpectedNumberOfCreatedElements() {
        // Enumerations have 1 compartment contained in their main view
        return 2;
    }

    @Override
    protected int getExpectedNumberOfGraphicalOwnerDirectChildren() {
        // Enumerations compartments aren't direct children of the enumeration's graphical owner
        return 1;
    }
}
