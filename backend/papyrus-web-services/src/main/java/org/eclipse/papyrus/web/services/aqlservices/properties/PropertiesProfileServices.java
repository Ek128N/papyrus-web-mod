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
package org.eclipse.papyrus.web.services.aqlservices.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Properties Profile services.
 *
 * @author Jerome Gout
 */
public class PropertiesProfileServices {

    private static final String PROFILE_PARENT_ANNOTATION_SOURCE = "http://www.eclipse.org/uml2/2.0.0/UML";

    /**
     * Check whether the given package instance is an UML profile definition or not.
     *
     * @param pack
     *            an {@code EPackage} instance
     * @return {@code true} if the given package is an UML profile definition element and {@code false} otherwise.
     */
    public boolean isPackageDefiningProfile(EPackage pack) {
        if (pack.eContainer() instanceof EAnnotation parent) {
            return PROFILE_PARENT_ANNOTATION_SOURCE.equals(parent.getSource());
        }
        return false;
    }

    /**
     * Apply a stereotype on a {@link Element} given its qualified name.
     *
     * @param element
     *            an UML element
     * @param qualifiedName
     *            the qualified name of the stereotype
     * @return self
     */
    public EObject applyStereotype(Element element, String qualifiedName) {
        Stereotype stereotype = element.getApplicableStereotype(qualifiedName);
        if (stereotype != null) {
            return element.applyStereotype(stereotype);
        }

        return element;
    }

    /**
     * Gets all applicable stereotypes that are not already applied.
     *
     * @param element
     *            an Element
     * @return a list of stereotypes
     */
    public List<Stereotype> getNonAppliedApplicableStereotypes(Element element) {
        var applicableStereotypes = new ArrayList<>(element.getApplicableStereotypes());
        applicableStereotypes.removeAll(element.getAppliedStereotypes());
        return applicableStereotypes;
    }

    /**
     * Reorder the stereotype application of an element.
     *
     * @param element
     *            the Element targets by the stereotype application
     * @param oldIndex
     *            the old index of the stereotype application
     * @param newIndex
     *            the new index of the stereotype application
     * @return self
     */
    // Copied from org.eclipse.papyrus.uml.tools.commands.ReorderStereotypeApplicationsCommand.prepare()
    public EObject reorderStereotypes(Element element, int oldIndex, int newIndex) {

        EList<Stereotype> stereotypeOrdering = new BasicEList.FastCompare<>(element.getAppliedStereotypes());
        EList<EObject> oldOrdering = new BasicEList.FastCompare<>(element.getStereotypeApplications());

        oldOrdering = new BasicEList.FastCompare<>(element.getStereotypeApplications());
        if (stereotypeOrdering.size() == oldOrdering.size() && oldOrdering.stream().map(EObject::eResource).distinct().count() == 1) {
            EList<EObject> resourceContents = oldOrdering.get(0).eResource().getContents();
            EList<EObject> newOrdering = stereotypeOrdering.stream().map(element::getStereotypeApplication).filter(Objects::nonNull).collect(Collectors.toCollection(BasicEList.FastCompare::new));
            newOrdering.move(newIndex, oldIndex);
            if (newOrdering.size() == oldOrdering.size()) {
                int[] positions = oldOrdering.stream().mapToInt(resourceContents::indexOf).filter(index -> index >= 0).sorted().toArray();
                this.reorderStereotypes(oldOrdering, newOrdering, resourceContents, positions, element);
            }
        }

        return element;
    }

    // Copied from org.eclipse.papyrus.uml.tools.commands.ReorderStereotypeApplicationsCommand.execute()
    private void reorderStereotypes(EList<EObject> oldOrdering, EList<EObject> newOrdering, EList<EObject> resourceContents, int[] positions, Element element) {
        // First, replace all of the stereotype applications with placeholders because when
        // we re-insert them in their new positions, we cannot repeat objects in the list
        EObject[] dummies = IntStream.range(0, oldOrdering.size()).mapToObj(__ -> EcoreFactory.eINSTANCE.createEObject()).toArray(EObject[]::new);
        for (int i = 0; i < dummies.length; i++) {
            resourceContents.set(positions[i], dummies[i]);
        }

        // Remove the stereotype applications from the inverse reference map because otherwise
        // they will be rediscovered in the original order via that map
        oldOrdering.forEach(sa -> org.eclipse.uml2.uml.util.UMLUtil.setBaseElement(sa, null));

        // Then, replace the dummies with the original stereotype applications in their new ordering
        for (int i = 0; i < positions.length; i++) {
            resourceContents.set(positions[i], newOrdering.get(i));

            // Restore the base element reference
            org.eclipse.uml2.uml.util.UMLUtil.setBaseElement(newOrdering.get(i), element);
        }
    }
}
