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
package org.eclipse.papyrus.web.services.aqlservices.scope;

import java.util.List;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.uml.domain.services.EMFUtils;
import org.eclipse.papyrus.uml.domain.services.scope.ElementRootCandidateSeachProvider;

/**
 * Services used to retrieve reachable elements.
 *
 * @author Jerome Gout
 */

public class ReachableElementsServices {

    /**
     * Retrieve all elements present reachable from the given self element compatible with the type of the reference
     * given by name.
     *
     * @param self
     *            the current selected element owning the reference
     * @param referenceName
     *            the name of the reference
     * @return the list of reachable elements.
     */
    public <T extends EObject> List<T> getAllReachableElements(EObject self, String referenceName) {
        EReference ref = (EReference) self.eClass().getEStructuralFeature(referenceName);
        return this.getAllReachableElements(self, ref.getEReferenceType());
    }

    /**
     * Retrieve all reachable elements from a given self which are compatible with the give type.
     *
     * @param self
     *            the current selected element owning the reference
     * @param typeClass
     *            the type of the referenced element
     * @return the list of reachable elements.
     */
    public <T extends EObject> List<T> getAllReachableElements(EObject self, EClass typeClass) {
        @SuppressWarnings("unchecked")
        Class<T> type = (Class<T>) typeClass.getInstanceClass();
        List<Notifier> roots = new ElementRootCandidateSeachProvider().getReachableRoots(self);
        return roots.stream().flatMap(r -> EMFUtils.allContainedObjectOfType(r, type)).toList();
    }

    /**
     * Return all root elements from a given element. This service is used in UI when setting a reference value (mono or
     * multi-valued).
     *
     * @param self
     *            the current selected element owning the reference
     * @return the list of root elements
     */
    public List<Notifier> getAllReachableRootElements(EObject self) {
        return new ElementRootCandidateSeachProvider().getReachableRoots(self);
    }

}
