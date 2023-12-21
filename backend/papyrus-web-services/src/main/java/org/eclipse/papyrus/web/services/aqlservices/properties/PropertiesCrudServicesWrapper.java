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
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.eclipse.papyrus.uml.domain.services.modify.ElementFeatureModifier;
import org.eclipse.papyrus.uml.domain.services.modify.IFeatureModifier;
import org.eclipse.papyrus.uml.domain.services.profile.StereotypeUtil;
import org.eclipse.papyrus.uml.domain.services.properties.ILogger;
import org.eclipse.papyrus.uml.domain.services.properties.ILogger.ILogLevel;
import org.eclipse.papyrus.uml.domain.services.properties.PropertiesCrudServices;
import org.eclipse.papyrus.uml.domain.services.status.State;
import org.eclipse.papyrus.uml.domain.services.status.Status;
import org.eclipse.uml2.uml.Element;

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
        try {
            return this.delegate.create(target, typeName, refName);
        } catch (IllegalArgumentException e) {
            this.logger.log(MessageFormat.format("Unable to create ''{0}'' in reference ''{1}'': ''{2}''", typeName, refName, e.getMessage()), ILogLevel.WARNING);
            return null;
        }
    }

    /**
     * Replacement of {@link PropertiesCrudServices#delete(Object, EObject, String)}.
     */
    public boolean delete(Object selectedObject, EObject target, String refName) {
        EStructuralFeature feature = target.eClass().getEStructuralFeature(refName);
        if (feature instanceof EReference ref && ref.isContainer() && selectedObject instanceof EObject selectedEObject) {
            this.logger.log(MessageFormat.format("Removing ''{0}'' from ''{1}''  would destroy ''{2}'' by making it an orphan in the containment tree.", this.logger.getLabelForLog(selectedEObject),
                    refName, this.logger.getLabelForLog(target)), ILogLevel.WARNING);
            return false;
        }
        return this.delegate.delete(selectedObject, target, refName);
    }

    /**
     * Removes from the stereotype application of the given base Element from the EReference of the
     * ownerStereotypeApplication.
     *
     * @param baseElementToRemove
     *            The base element linked to the stereotype application to remove
     * @param ownerStereotypeApplication
     *            the owner of EReference to modify
     * @param refName
     *            the name of the EReference to be modified
     * @param stereotypeApplicationtype
     *            the type of the stereotype applied on baseElementToRemove to be removed
     * @return <code>true</code> if the element is removed, <code>false</code> otherwise.
     */
    public boolean removeStereotypeApplicationFromBase(Element baseElementToRemove, EObject ownerStereotypeApplication, String refName, EClass stereotypeApplicationtype) {
        Optional<EObject> stereotypeApplicationToRemove = baseElementToRemove.getStereotypeApplications().stream().filter(st -> stereotypeApplicationtype.isSuperTypeOf(st.eClass())).findFirst();
        return stereotypeApplicationToRemove.map(st -> this.delegate.delete(st, ownerStereotypeApplication, refName)).orElse(false);
    }

    /**
     * Moves the stereotype application (defined by a base element and a type) inside the reference value list. The
     * stereotype application is moved from the given {@code from} index to the given {@code to} index. Nothing is done
     * if the element at the {@code from} position is not the given element to move.
     *
     * @param ownerStereotypeApplication
     *            the owner of the reference
     * @param refName
     *            the reference name
     * @param baseElementToMove
     *            the element to move
     * @param stereotypeApplicationtype
     *            the type of the stereotype application to move
     * @param from
     *            the starting index
     * @param to
     *            the destination index
     * @return target object
     */
    public EObject moveReferenceStereotypeApplicationFromBase(EObject ownerStereotypeApplication, String refName, Element baseElementToMove, EClass stereotypeApplicationtype, int from, int to) {
        EObject stereotypeApplication = StereotypeUtil.getStereotypeApplication(baseElementToMove, stereotypeApplicationtype);
        if (stereotypeApplication != null) {
            this.moveReferenceElement(ownerStereotypeApplication, refName, stereotypeApplication, from, to);
        }
        return ownerStereotypeApplication;
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
        try {
            return this.delegate.updateReference(target, objectToSet, refName);
        } catch (IllegalArgumentException e) {
            this.logger.log(MessageFormat.format("Unable to update reference ''{0}'': ''{1}''", refName, e.getMessage()), ILogLevel.WARNING);
            return false;
        }
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
     * Adds a list of elements (defined by their base element and a stereotype type) to the multi-valued reference. An
     * stereotype application is not added if it is already in the reference value list.
     *
     * @param target
     *            the owner of the reference
     * @param newElements
     *            the list of element to add to the reference
     * @param refName
     *            the name of the reference to update
     * @param stereotypeApplicationtype
     *            the type of the stereotype application to move
     * @return <code>true</code> if <strong>all</strong> elements have been properly added, and <code>false</code>
     *         otherwise.
     */
    public boolean addReferenceStereotypeApplicationfromBase(EObject target, List<Element> newElements, String refName, EClass stereotypeApplicationtype) {

        List<EObject> newStereotypeApplication = newElements.stream()//
                .map(element -> StereotypeUtil.getStereotypeApplication(element, stereotypeApplicationtype))//
                .filter(e -> e != null)//
                .toList();

        return this.addReferenceElement(target, newStereotypeApplication, refName);
    }

    /**
     * Adds a stereotype application(s) (define by a baseElement and a type) to the reference of another stereotype
     * application.
     *
     * @param ownerStereotypeApplication
     *            the stereotype application about to be modified
     * @param baseElementToAdd
     *            the base element to add. It could either be a list of {@link Element} or a unique {@link Element}
     * @param refName
     *            the name of the EReference about to be modified
     * @param stereotypeApplicationtype
     *            the type of the stereotype application extracted from the base Element(s)
     * @return <code>true</code> if success, <code>false</code> otherwise
     */
    public boolean addStereotypeApplicationFromBase(EObject ownerStereotypeApplication, List<Element> baseElementsToAdd, String refName, EClass stereotypeApplicationtype) {

        List<EObject> newStereotypeApplication = baseElementsToAdd.stream()//
                .map(element -> StereotypeUtil.getStereotypeApplication(element, stereotypeApplicationtype))//
                .filter(e -> e != null)//
                .toList();

        return this.delegate.updateReference(ownerStereotypeApplication, newStereotypeApplication, refName);
    }

    public boolean addStereotypeApplicationFromBase(EObject ownerStereotypeApplication, Element baseElementToAdd, String refName, EClass stereotypeApplicationtype) {

        EObject stereotypeApplication = StereotypeUtil.getStereotypeApplication(baseElementToAdd, stereotypeApplicationtype);
        if (stereotypeApplication != null) {

            return this.delegate.updateReference(ownerStereotypeApplication, stereotypeApplication, refName);
        }

        return false;
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
