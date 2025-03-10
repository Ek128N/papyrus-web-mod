/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
 *
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

package org.eclipse.papyrus.web.application.representations.aqlservices.tables;

import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchServiceDelegate;
import org.springframework.stereotype.Service;

/**
 * Service to handle semantic object associated to a virtual row of the PackageTableTree table.
 *
 * @author Jerome Gout
 */
@Service
public class PackageTableTreeObjectSearchServiceDelegate implements IObjectSearchServiceDelegate {

    @Override
    public boolean canHandle(IEditingContext editingContext, String objectId) {
        if (objectId != null) {
            // only handle virtual row ids
            return objectId.startsWith(TableService.VIRTUAL_ROW_PREFIX);
        }
        return false;
    }

    @Override
    public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
        // As sirius web ObjectService is relied on EObject, semantic objects are always metamodel elements.
        // The semantic object associated to virtual rows are just a string that holds the parent id.
        // We need to handle it here, to avoid ObjectService to return null.
        return Optional.of(objectId);
    }
}
