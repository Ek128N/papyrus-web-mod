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
package org.eclipse.papyrus.web.application.representations.uml;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.uml.domain.services.EMFUtils;
import org.eclipse.papyrus.uml.domain.services.UMLHelper;
import org.eclipse.papyrus.web.application.representations.view.IDomainHelper;

/**
 * A {@link IDomainHelper} for UML.
 * 
 * @author Arthur Daussy
 */
public class UMLMetamodelHelper implements IDomainHelper {
    @Override
    public String getDomain(EClass eClass) {
        return EMFUtils.buildQualifiedName(eClass);
    }

    @Override
    public EClass toEClass(String domain) {
        return UMLHelper.toEClass(domain);
    }
}
