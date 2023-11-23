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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.IDiagramElement;

/**
 * Utility class to check that a semantic node has not been removed in the semantic model after graphical deletion.
 * <p>
 * It also validates that the semantic model contains the appropriate number of elements after the creation.
 * </p>
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class NodeGraphicalDeletionSemanticChecker extends DeletionSemanticChecker {

    /**
     * Initializes the checker with the provided parameters.
     *
     * @param objectService
     *            the object service used to retrieve and compute identifiers
     * @param editingContextSupplier
     *            a supplier to access and reload the editing context
     * @param oldOwnerSupplier
     *            a supplier to access and reload the previous owner of the checked element
     * @param containmentFeature
     *            the expected containment feature of the checked element
     */
    public NodeGraphicalDeletionSemanticChecker(IObjectService objectService, Supplier<IEditingContext> editingContextSupplier, Supplier<EObject> oldOwnerSupplier, EReference containmentFeature) {
        super(objectService, editingContextSupplier, oldOwnerSupplier, containmentFeature);
    }

    @Override
    public void validateRepresentationElement(IDiagramElement element) {
        EObject semanticElement = this.getSemanticElement(element);
        assertThat(this.getContainmentFeatureValue().isEmpty());
        EObject expectedOwner = this.oldOwnerSupplier.get();
        // Check that the semantic element is still in the model in the expected owner
        // Need to compare IDs because EObject instances are different
        assertThat(this.getContainmentFeatureValue().stream().map(this.objectService::getId)).anyMatch(id -> Objects.equals(id, this.objectService.getId(semanticElement)));
        assertThat(this.objectService.getId(semanticElement.eContainer())).as("The created element is not contained by the expected owner").isEqualTo(this.objectService.getId(expectedOwner));
    }
}
