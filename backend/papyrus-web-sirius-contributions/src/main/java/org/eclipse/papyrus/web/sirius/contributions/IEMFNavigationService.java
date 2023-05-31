/*******************************************************************************
 * Copyright (c) 2022 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.sirius.contributions;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * Provide high-level methods to navigate inside EMF models.
 *
 * @author pcdavid
 */
public interface IEMFNavigationService {

    <T extends EObject> T getAncestor(Class<T> type, EObject object);

    /**
     * Gets the fist common ancestor from both given objects with the expected type.
     *
     * @param <T>
     *            the expected ancestor type
     * @param expectedType
     *            the expected ancestor type
     * @param e1
     *            the first {@link EObject}
     * @param e2
     *            the last {@link EObject}
     * @return an ancestor or <code>null</code>
     */
    <T extends EObject> T getLeastCommonContainer(Class<T> expectedType, EObject e1, EObject e2);

    /**
     * Gets all objects contained in the given notifier with the given type.
     * <p>
     * <i>If self if of the expected type then it belong to the returned stream </i>
     * </p>
     *
     * @param self
     *            a {@link Notifier} (EObject, Resource or ResourceSet)
     * @param type
     *            the type of the element in the returned stream
     * @return a stream
     * @param <T>
     *            type of element in the returned stream
     */
    <T extends EObject> Stream<T> allContainedObjectOfType(Notifier self, Class<T> type);

    /**
     * Gets a stream composed from the object itself and all its content.
     *
     * @param rs
     *            a resource set
     * @return a stream
     */
    Stream<Notifier> eAllContentSteamWithSelf(ResourceSet rs);

    /**
     * Gets a stream composed from the object itself and all its content.
     *
     * @param r
     *            a resource
     * @return a stream
     */
    Stream<Notifier> eAllContentSteamWithSelf(Resource r);

    /**
     * Gets a stream composed from the object itself and all its content.
     *
     * @param o
     *            an {@link EObject}
     * @return a stream
     */
    Stream<EObject> eAllContentStreamWithSelf(EObject o);

    /**
     * Gets the first ancestor from the given object which match the predicated and has the expected type.
     *
     * @param <T>
     *            the expected type
     * @param type
     *            the expected type
     * @param object
     *            the source object
     * @return an ancestor or <code>null</code>
     */
    <T extends EObject> List<T> getAncestors(Class<T> type, EObject object);

    /**
     * Gets the first ancestor from the given object which match the predicated and has the expected type.
     *
     * @param <T>
     *            the expected type
     * @param type
     *            the expected type
     * @param object
     *            the source object
     * @param ancestorPredicate
     *            an optional {@link Predicate}
     * @return an ancestor or <code>null</code>
     */
    <T extends EObject> List<T> getAncestors(Class<T> type, EObject object, Predicate<EObject> ancestorPredicate);

}
