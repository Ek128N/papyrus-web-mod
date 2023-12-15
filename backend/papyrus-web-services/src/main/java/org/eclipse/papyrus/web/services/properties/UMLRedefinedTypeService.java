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
package org.eclipse.papyrus.web.services.properties;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.papyrus.uml.domain.services.properties.ILogger;
import org.eclipse.papyrus.uml.domain.services.properties.PropertiesUMLReferenceTypeServices;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/**
 * Service that handles redefined types for several UML references.
 *
 * @author Jerome Gout
 */
@Service
public class UMLRedefinedTypeService {

    private final PropertiesUMLReferenceTypeServices typeServices;

    public UMLRedefinedTypeService(ILogger logger) {
        this.typeServices = new PropertiesUMLReferenceTypeServices(logger);
        this.collectRedefinedReferenceTypes();
    }

    private void collectRedefinedReferenceTypes() {
        Resource resource = new XMIResourceImpl();
        try (var inputStream = new ClassPathResource("model/UML.ecore").getInputStream()) {
            resource.load(inputStream, Collections.emptyMap());
        } catch (IOException exception) {
            throw new WrappedException(exception);
        }
        EPackage uml2EPackage = (EPackage) resource.getContents().get(0);
        this.typeServices.initRedefinedTypes(uml2EPackage);
    }

    /**
     * Return the qualified name of the type of the given feature of the given element.
     *
     * @param self
     *            the current selected element owning the feature
     * @param featureName
     *            the name of the feature
     * @return the qualified name of the feature type.
     */
    public String getFeatureTypeQualifiedName(EObject self, String featureName) {
        return this.typeServices.getFeatureTypeQualifiedName(self, featureName);
    }
}
