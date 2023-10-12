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

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EPackage;

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
}
