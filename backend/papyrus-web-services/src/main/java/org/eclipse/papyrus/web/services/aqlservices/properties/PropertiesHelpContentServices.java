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

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.web.services.properties.UMLDocumentationService;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This service class includes all services used to get the Help Content for UML model elements.
 *
 * @author Jerome Gout
 */
public class PropertiesHelpContentServices {

    private UMLDocumentationService documentationService;

    public PropertiesHelpContentServices(UMLDocumentationService docService) {
        this.documentationService = docService;
    }

    /**
     * Get help content for a given feature.
     *
     * @param obj
     *            the object which contains the feature
     * @param featureName
     *            the name of the feature with the help to display
     * @return help content for a given feature.
     */
    public String getFeatureDescription(EObject obj, String featureName) {
        Objects.requireNonNull(obj);
        String entryKey = this.documentationService.getDocumentationEntryKey(obj.eClass().getName(), featureName);
        return this.documentationService.getDocumentation(entryKey);
    }

    /**
     * Get the help content for the MultiplicityElement type.
     *
     * @param target
     *            the target that should be a MultiplicityElement
     * @return the help content for the MultiplicityElement type.
     */
    public String getMultiplicityHelpContent(EObject target) {
        String multiplicityElementTypeName = UMLPackage.eINSTANCE.getMultiplicityElement().getName();
        String entryKey = this.documentationService.getDocumentationEntryKey(multiplicityElementTypeName, "");
        return this.documentationService.getDocumentation(entryKey);
    }
}
