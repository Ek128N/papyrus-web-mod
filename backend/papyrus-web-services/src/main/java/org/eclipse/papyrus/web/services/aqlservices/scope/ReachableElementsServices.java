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
import java.util.function.Predicate;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.uml.domain.services.EMFUtils;
import org.eclipse.papyrus.uml.domain.services.profile.StereotypeUtil;
import org.eclipse.papyrus.uml.domain.services.scope.ElementRootCandidateSeachProvider;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;

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

    /**
     * Gets all {@link Element}s on which a stereotype is applied that is compliant with the given reference.
     *
     * @param self
     *            a source EObject
     * @param referenceName
     *            the name of EReference of this object that targets a Stereotype Application
     * @return a list of elements
     */
    public List<Element> getAllReachableStereotypeApplicationsBaseElements(EObject self, String referenceName) {
        EReference ref = (EReference) self.eClass().getEStructuralFeature(referenceName);
        return this.getAllReachableStereotypeApplications(self, ref.getEReferenceType()).stream()//
                .map(StereotypeUtil::getBaseElement)//
                .filter(e -> e != null)//
                .toList();
    }

    private List<EObject> getAllReachableStereotypeApplications(EObject self, EClass typeClass) {
        Class<?> type = typeClass.getInstanceClass();
        Predicate<EObject> filter;
        if (type != null) {
            // Used for static profile (profile with generated java code)
            filter = type::isInstance;
        } else {
            // Used for dynamic profile (profile with no generated java code)
            filter = e -> typeClass.isSuperTypeOf(e.eClass());
        }
        List<Notifier> roots = new ElementRootCandidateSeachProvider().getReachableRoots(self);
        return roots.stream().flatMap(r -> EMFUtils.allContainedObjectOfType(r, Element.class))//
                .flatMap(e -> e.getStereotypeApplications().stream())//
                .filter(filter)//
                .toList();
    }

    /**
     * Return all root {@link Package} elements found in the resource set of the given element.
     *
     * @param self
     *            the current selected element owning the reference
     * @return the list of root {@link Package} elements.
     */
    public List<Package> getAllRootPackages(EObject self) {
        return self.eResource().getResourceSet().getResources().stream()//
                .flatMap(r -> r.getContents().stream())//
                .filter(Package.class::isInstance)//
                .map(Package.class::cast)//
                .toList();
    }

    /**
     * Return all {@link Package} elements reachable by the given element.
     *
     * @param self
     *            the current selected element owning the reference
     * @return the list of {@link Package} elements.
     */
    public List<Package> getAllUMLPackages(EObject self) {
        var roots = this.getAllRootPackages(self);
        return roots.stream().flatMap(e -> EMFUtils.allContainedObjectOfType(e, Package.class)).toList();
    }

}
