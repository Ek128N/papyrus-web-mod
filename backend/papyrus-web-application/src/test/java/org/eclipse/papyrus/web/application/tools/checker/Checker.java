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
 * A checker to validate an {@link IDiagramElement}.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public interface Checker {

    /**
     * Validates the provided {@code element}.
     *
     * @param element
     *            the {@link IDiagramElement} to validate
     */
    void validateRepresentationElement(IDiagramElement element);

    /**
     * A default {@link Checker} that always validates the provided {@code element}.
     *
     * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
     */
    final class NoOp implements Checker {

        @Override
        public void validateRepresentationElement(IDiagramElement element) {
            // Nothing to do
        }
    }
}
