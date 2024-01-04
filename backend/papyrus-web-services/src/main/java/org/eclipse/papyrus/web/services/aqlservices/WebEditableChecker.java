/*******************************************************************************
 * Copyright (c) 2022, 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.services.aqlservices;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IEditableChecker} for the Web. It used the
 * {@link EditingDomain#isReadOnly(org.eclipse.emf.ecore.resource.Resource)} method to check if an element is editable.
 *
 * @author Arthur Daussy
 */
@Service
public class WebEditableChecker implements IEditableChecker {

    @Override
    public boolean canEdit(EObject eObject) {
        EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(eObject);
        boolean isReadOnly = (eObject.eResource() != null) && (editingDomain.isReadOnly(eObject.eResource()));
        return !isReadOnly;
    }

}
