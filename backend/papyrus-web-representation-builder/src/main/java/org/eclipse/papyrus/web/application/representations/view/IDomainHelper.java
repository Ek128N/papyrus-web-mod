/*******************************************************************************
 * Copyright (c) 2023 CEA, Obeo.
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
package org.eclipse.papyrus.web.application.representations.view;

import org.eclipse.emf.ecore.EClass;

/**
 * Helper used to navigate between the metamodel and its string representations.
 * 
 * @author Arthur Daussy
 */
public interface IDomainHelper {

    /**
     * Gets the domain string representation of the given EClass.
     * 
     * @param eClass
     *            an EClass
     * @return a String
     */
    String getDomain(EClass eClass);

    /**
     * Transforms a domain string representation to an EClass.
     * 
     * @param domain
     *            a domain string representation
     * @return an {@link EClass} or <code>null</code>
     * @see #getDomain(EClass)
     */
    EClass toEClass(String domain);

}
