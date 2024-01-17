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
package org.eclipse.papyrus.web.application.tools.activity.checker;

import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.papyrus.web.application.tools.checker.EdgeCreationGraphicalChecker;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.uml2.uml.ObjectFlow;
import org.eclipse.uml2.uml.OpaqueAction;

/**
 * A specific graphical checker for {@link ObjectFlow} creation on source/target {@link OpaqueAction}s.
 * <p>
 * This checker verifies that the appropriate number of additional objects (pins) have been created along the created
 * {@link ObjectFlow}. The number of expected pins is provided in the constructor of this checker.
 * </p>
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class ADObjectFlowOnOpaqueActionCreationGraphicalChecker extends EdgeCreationGraphicalChecker {

    private int expectedPincount;

    public ADObjectFlowOnOpaqueActionCreationGraphicalChecker(Supplier<Diagram> diagramSupplier, Supplier<IDiagramElement> graphicalOwnerSupplier, String mappingType,
            Map<EdgeDescription, org.eclipse.sirius.components.diagrams.description.EdgeDescription> convertedEdges, int expectedPinCount) {
        super(diagramSupplier, graphicalOwnerSupplier, mappingType, convertedEdges);
        this.expectedPincount = expectedPinCount;
    }

    @Override
    protected int getExpectedNumberOfCreatedElements() {
        // The number of pins + the created ObjectFlow
        return this.expectedPincount + 1;
    }

    @Override
    protected int getExpectedNumberOfGraphicalOwnerDirectChildren() {
        // Pins created along the ObjectFlow aren't direct children of the ObjectFlow's graphical owner.
        return 1;
    }

}
