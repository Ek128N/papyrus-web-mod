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

import java.util.function.Supplier;

import org.eclipse.papyrus.web.application.tools.checker.DeletionGraphicalChecker;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.IDiagramElement;

/**
 * A specific graphical checker for classifiers in the profile diagram.
 * <p>
 * Classifiers have 2 compartments, this changes the expected number of deleted elements in the diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class PRDClassifierDeletionGraphicalChecker extends DeletionGraphicalChecker {

    public PRDClassifierDeletionGraphicalChecker(Supplier<Diagram> diagramSupplier, Supplier<IDiagramElement> graphicalOwnerSupplier) {
        super(diagramSupplier, graphicalOwnerSupplier);
    }

    @Override
    protected int getExpectedNumerOfDeletedElements() {
        // Classifiers have 2 compartments contained in their main view
        return 3;
    }

    @Override
    protected int getExpectedNumberOfDeletedGraphicalOwnerDirectChildren() {
        // Classifiers compartments aren't direct children of the classifier's graphical owner
        return 1;
    }

}
