/*******************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
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
 *******************************************************************************/
package org.eclipse.papyrus.web.tests.utils;

import static org.junit.jupiter.api.Assertions.fail;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.UMLFactory;

/**
 * Helper class used to ease the creation of tests with UML elements.
 *
 * @author Arthur Daussy
 * @author Jerome Gout
 */
public class UMLTestHelper {

    /**
     * Creates an element with the given type in the given parent. The containment reference is automatically computed
     * by finding the feature containment {@link EReference} that can contains the given object.
     *
     * @param <T>
     *            the expected type of the given element
     * @param type
     *            the expected type of the given element
     * @param parent
     *            the container
     * @return a new element
     */
    public <T extends EObject> T createIn(java.lang.Class<T> type, EObject parent) {
        Optional<EReference> defaultContainementRef = parent.eClass().getEAllContainments().stream().filter(ref -> ref.getEType().getInstanceClass().isAssignableFrom(type)).findFirst();

        if (defaultContainementRef.isPresent()) {
            return this.createIn(type, parent, defaultContainementRef.get().getName());
        } else {
            fail(MessageFormat.format("Unable to find a containement reference for {0} in {1}", type.getSimpleName(), parent));
            return null;
        }
    }

    public <T extends EObject> T createInResource(java.lang.Class<T> type, Resource resource) {
        T element = this.create(type);
        resource.getContents().add(element);
        return element;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T extends EObject> T createIn(java.lang.Class<T> type, EObject parent, String containmentRefName) {
        T newElement = this.create(type);

        EStructuralFeature ref = parent.eClass().getEStructuralFeature(containmentRefName);
        if (ref == null || !(ref instanceof EReference)) {
            fail("Invalid reference name");
        } else {
            if (ref.isDerived()) {
                fail(ref.getName() + " is a derived feature.");
            }
            EReference eRef = (EReference) ref;
            if (!eRef.getEType().isInstance(newElement)) {
                fail(MessageFormat.format("Invalid reference {0} for element{1}", eRef.getName(), newElement));
            }
            if (ref.isMany()) {
                ((List) parent.eGet(ref)).add(newElement);
            } else {
                parent.eSet(ref, newElement);
            }
        }

        return newElement;
    }

    @SuppressWarnings("unchecked")
    public <T extends EObject> T create(java.lang.Class<T> type) {

        EObject newElement = UMLFactory.eINSTANCE.create(this.getEClass(type));

        return (T) newElement;
    }

    public <T extends EObject> EClass getEClass(java.lang.Class<T> type) {
        return org.eclipse.papyrus.uml.domain.services.UMLHelper.toEClass(type.getSimpleName());
    }

}
