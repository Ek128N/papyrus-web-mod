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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.Supplier;

import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.IDiagramElement;

/**
 * Utility class to check the graphical source of a graphical edge.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class EdgeSourceGraphicalChecker implements Checker {

    private Supplier<IDiagramElement> sourceSupplier;

    /**
     * Initializes the checker with the provided {@code sourceSupplier}.
     *
     * @param sourceSupplier
     *            a supplier to access and reload the edge source to check
     */
    public EdgeSourceGraphicalChecker(Supplier<IDiagramElement> sourceSupplier) {
        Objects.requireNonNull(sourceSupplier);
        this.sourceSupplier = sourceSupplier;
    }

    @Override
    public void validateRepresentationElement(IDiagramElement element) {
        assertThat(element).isInstanceOf(Edge.class);
        Edge edge = (Edge) element;
        assertThat(edge.getSourceId()).isEqualTo(this.sourceSupplier.get().getId());
    }

}
