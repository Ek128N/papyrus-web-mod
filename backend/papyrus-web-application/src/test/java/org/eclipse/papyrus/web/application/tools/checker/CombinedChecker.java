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
package org.eclipse.papyrus.web.application.tools.checker;

import org.eclipse.sirius.components.diagrams.IDiagramElement;

/**
 * A {@link Checker} combining multiple sub-{@link Checker}s.
 * <p>
 * The sub-{@link Checker}s validation are done sequentially when
 * {@link #validateRepresentationElement(IDiagramElement)} is called.
 * </p>
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public final class CombinedChecker implements Checker {

    private Checker[] checkers;

    /**
     * Initializes the {@link CombinedChecker} with the provided sub-{@code checkers}.
     *
     * @param checkers
     *            the {@link Checker}s to combine
     */
    public CombinedChecker(Checker... checkers) {
        this.checkers = checkers;
    }

    @Override
    public void validateRepresentationElement(IDiagramElement element) {
        for (Checker checker : this.checkers) {
            checker.validateRepresentationElement(element);
        }
    }

}
