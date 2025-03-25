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

import org.eclipse.sirius.components.core.api.IIdentityServiceDelegate;
import org.springframework.stereotype.Service;

/**
 * Service to handle identity services for virtual rows of the PackageTableTree table.
 *
 * @author Jerome Gout 
 */
@Service
public class PackageTableTreeIdentityServiceDelegate implements IIdentityServiceDelegate {

    @Override
    public boolean canHandle(Object object) {
        return object instanceof String id && id.startsWith(TableService.VIRTUAL_ROW_PREFIX);
    }

    @Override
    public String getId(Object object) {
        return (String) object;
    }

    @Override
    public String getKind(Object object) {
        return "";
    }
}
