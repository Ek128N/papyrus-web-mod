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
package org.eclipse.papyrus.web.application.tools.usecase.utils;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * The mapping types for the Use Case Diagram.
 * <p>
 * This class can be used to retrieve both node mapping types and edge mapping types.
 * </p>
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public final class UCDMappingTypes {

    private static final String UCD_PREFIX = "UCD_";

    private UCDMappingTypes() {
        // private to prevent instantiation.
    }

    public static String getMappingType(EClass eClass) {
        String result = UCD_PREFIX + eClass.getName();
        if (UMLPackage.eINSTANCE.getRelationship().isSuperTypeOf(eClass)) {
            result = UCD_PREFIX + eClass.getName() + "_DomainEdge";
        }
        return result;
    }

    public static String getMappingTypeAsSubNode(EClass eClass) {
        String result = UCD_PREFIX + eClass.getName() + "_SHARED";
        if (UMLPackage.eINSTANCE.getPackage().isSuperTypeOf(eClass)) {
            result = UCD_PREFIX + eClass.getName() + "_inPackage";
        }
        return result;
    }

}
