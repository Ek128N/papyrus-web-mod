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

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.eclipse.emf.common.notify.Notifier;
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
     * given by name. The returned list does not contain elements that the reference is already referring to.
     *
     * @param self
     *            the current selected element owning the reference
     * @param referenceName
     *            the name of the reference
     * @return the list of new reachable elements.
     */
    public <T extends EObject> List<T> getAllReachableElements(EObject self, String referenceName) {
        EReference ref = (EReference) self.eClass().getEStructuralFeature(referenceName);

        final Predicate<T> filter = this.getExistingElementsFilter(self, ref);

        @SuppressWarnings("unchecked")
        Class<T> type = (Class<T>) ref.getEReferenceType().getInstanceClass();
        List<Notifier> roots = new ElementRootCandidateSeachProvider().getReachableRoots(self);
        return roots.stream().flatMap(r -> EMFUtils.allContainedObjectOfType(r, type)).filter(filter).toList();
    }

    private <T extends EObject> Predicate<T> getExistingElementsFilter(EObject self, EReference ref) {
        final Predicate<T> filter;
        if (ref.isUnique()) {
            if (ref.isMany()) {
                @SuppressWarnings("unchecked")
                Collection<T> values = (Collection<T>) self.eGet(ref);
                filter = element -> !values.contains(element);
            } else {
                Object value = self.eGet(ref);
                filter = element -> element != value;
            }
        } else {
            filter = element -> true;
        }
        return filter;
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
