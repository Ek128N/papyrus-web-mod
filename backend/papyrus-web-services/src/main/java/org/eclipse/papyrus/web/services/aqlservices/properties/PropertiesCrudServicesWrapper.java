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

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.eclipse.papyrus.uml.domain.services.modify.ElementFeatureModifier;
import org.eclipse.papyrus.uml.domain.services.modify.IFeatureModifier;
import org.eclipse.papyrus.uml.domain.services.properties.ILogger;
import org.eclipse.papyrus.uml.domain.services.properties.ILogger.ILogLevel;
import org.eclipse.papyrus.uml.domain.services.properties.PropertiesCrudServices;
import org.eclipse.papyrus.uml.domain.services.status.State;
import org.eclipse.papyrus.uml.domain.services.status.Status;

/**
 * Papyrus web UML Domain CRUD services wrapper class. This class wraps all services defined in
 * {@see PropertiesCrudServices} augmented with special services used in the Web context.
 *
 * @author Jerome Gout
 */
public class PropertiesCrudServicesWrapper {

    private final ILogger logger;

    private final IEditableChecker checker;

    private PropertiesCrudServices delegate;

    public PropertiesCrudServicesWrapper(ILogger logger, IEditableChecker checker) {
        this.logger = logger;
        this.checker = checker;
        this.delegate = new PropertiesCrudServices(logger, checker);
    }

    /**
     * Replacement of {@link PropertiesCrudServices#addToAttribute(EObject, String, String)}.
     */
    public EObject addToAttribute(EObject self, String featureName, String value) {
        return this.delegate.addToAttribute(self, featureName, value);
    }

    /**
     * Replacement of {@link PropertiesCrudServices#create(EObject, String, String)}.
     */
    public EObject create(EObject target, String typeName, String refName) {
        return this.delegate.create(target, typeName, refName);
    }

    /**
     * Replacement of {@link PropertiesCrudServices#delete(Object, EObject, String)}.
     */
    public boolean delete(Object selectedObject, EObject target, String refName) {
        return this.delegate.delete(selectedObject, target, refName);
    }

    /**
     * Replacement of {@link PropertiesCrudServices#removeFromUsingIndex(EObject, String, Integer)}.
     */
    public EObject removeFromUsingIndex(EObject self, String featureName, Integer index) {
        return this.delegate.removeFromUsingIndex(self, featureName, index);
    }

    /**
     * Replacement of {@link PropertiesCrudServices#set(EObject, String, Object)}.
     */
    public boolean set(EObject target, String refName, Object valueToSet) {
        return this.delegate.set(target, refName, valueToSet);
    }

    /**
     * Replacement of {@link PropertiesCrudServices#updateReference(EObject, Object, String)}.
     */
    public boolean updateReference(EObject target, Object objectToSet, String refName) {
        return this.delegate.updateReference(target, objectToSet, refName);
    }

    /**
     * Get the reference from a target {@link EObject} by using its name.
     *
     * @param target
     *            the owner of the reference
     * @param refName
     *            the name of the reference to retrieve
     * @return the reference from a target {@link EObject} by using its name.
     */
    private EReference getReference(EObject target, String refName) {
        EReference eReference = null;
        if (target != null && refName != null && !refName.isBlank()) {
            EStructuralFeature eStructuralFeature = target.eClass().getEStructuralFeature(refName);
            if (eStructuralFeature instanceof EReference) {
                eReference = (EReference) eStructuralFeature;
            }
        }
        return eReference;
    }

    private boolean deleteReferenceValues(EObject target, EReference reference) {
        @SuppressWarnings("unchecked")
        List<Object> values = ((List<Object>) target.eGet(reference)).stream().toList();
        boolean deleteCompleted = true;
        for (Object value : values) {
            deleteCompleted = deleteCompleted && this.delegate.delete(value, target, reference.getName());
        }
        return deleteCompleted;
    }

    /**
     * Clear the reference.
     *
     * @param target
     *            the owner of the reference
     * @param refName
     *            the name of the reference to update
     * @return <code>true</code> if the element has been properly set, <code>false</code> otherwise.
     */
    public boolean clearReference(EObject target, String refName) {
        boolean isDeleted = false;
        EReference reference = this.getReference(target, refName);
        if (reference.isMany()) {
            isDeleted = this.deleteReferenceValues(target, reference);
        } else {
            isDeleted = this.delete(target.eGet(reference), target, refName);
        }
        return isDeleted;
    }

    /**
     * Add a list of elements to the multi-valued reference. An element is not added if it is already in the reference
     * value list.
     *
     * @param target
     *            the owner of the reference
     * @param newElements
     *            the list of element to add to the reference
     * @param refName
     *            the name of the reference to update
     * @return <code>true</code> if <strong>all</strong> elements have been properly added, and <code>false</code>
     *         otherwise.
     */
    public boolean addReferenceElement(EObject target, List<EObject> newElements, String refName) {
        boolean added = true;
        EReference eReference = this.getReference(target, refName);
        if (eReference.isMany()) {
            ECrossReferenceAdapter crossReferenceAdapter = new ECrossReferenceAdapter();
            IFeatureModifier modifier = new ElementFeatureModifier(crossReferenceAdapter, this.checker);
            @SuppressWarnings("unchecked")
            List<EObject> values = (List<EObject>) target.eGet(eReference);
            for (EObject newElement : newElements) {
                if (!values.contains(newElement)) {
                    Status status = modifier.addValue(target, refName, newElement);
                    State state = status.getState();
                    if (state == State.FAILED) {
                        this.logger.log(status.getMessage(), ILogLevel.ERROR);
                    }
                    added = added && state == State.DONE;
                } // silently end because the given value is already in the reference value list
            }
        } else {
            this.logger.log(MessageFormat.format("Feature {0} of {1} is not a multi-valued reference.", refName, target.eClass().getName()), ILogLevel.ERROR);
            added = false;
        }
        return added;
    }

    /**
     * Move an element inside the reference value list. The given element is moved from the given {@code from} index to
     * the given {@code to} index. Nothing is done if the element at the {@code from} position is no the given element
     * to move.
     *
     * @param target
     *            the owner of the reference
     * @param refName
     *            the reference name
     * @param element
     *            the element to move
     * @param from
     *            the starting index
     * @param to
     *            the destination index
     * @return target object
     */
    public EObject moveReferenceElement(EObject target, String refName, EObject element, int from, int to) {
        if (target == null || element == null || from < 0 || to < 0) {
            return target;
        }
        if (target.eClass().getEStructuralFeature(refName) instanceof EReference reference) {
            if (reference.isMany()) {
                @SuppressWarnings("unchecked")
                List<Object> values = (List<Object>) target.eGet(reference);
                if (from < values.size() && to < values.size()) {
                    var valueItem = values.get(from);
                    if (valueItem != null && valueItem.equals(element) && (values instanceof EList<Object> eValues)) {
                        eValues.move(to, from);
                    }
                }
            } else {
                this.logger.log("Only values of multiple-valued references can be reordered.", ILogLevel.ERROR);
            }
        }
        return target;
    }
}
