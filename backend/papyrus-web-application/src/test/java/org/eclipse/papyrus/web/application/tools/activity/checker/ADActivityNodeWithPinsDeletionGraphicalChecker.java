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

import java.util.function.Supplier;

import org.eclipse.papyrus.web.application.tools.checker.DeletionGraphicalChecker;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.uml2.uml.ActivityNode;

/**
 * A specific graphical checker for {@link ActivityNode}s deletion with pins in the Activity diagram.
 * <p>
 * {@link ActivityNode}s can have up to 3 pins. This changes the expected number of deleted element in the diagram. The
 * number of expected pins is provided in the constructor of this checker.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class ADActivityNodeWithPinsDeletionGraphicalChecker extends DeletionGraphicalChecker {

    private int expectedPincount;

    public ADActivityNodeWithPinsDeletionGraphicalChecker(Supplier<Diagram> diagramSupplier, Supplier<IDiagramElement> graphicalOwnerSupplier, int expectedPinCount) {
        super(diagramSupplier, graphicalOwnerSupplier);
        this.expectedPincount = expectedPinCount;
    }

    @Override
    protected int getExpectedNumerOfDeletedElements() {
        // The number of pins + the created ActivityNode
        return this.expectedPincount + 1;
    }

    @Override
    protected int getExpectedNumberOfDeletedGraphicalOwnerDirectChildren() {
        // ActivityNode's pins aren't direct children of the ActivityNode's graphical owner
        return 1;
    }

}
