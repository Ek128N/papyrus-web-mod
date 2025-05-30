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
package org.eclipse.papyrus.web.application.profile;

import java.util.List;

import org.eclipse.papyrus.web.application.profile.dto.UMLProfileMetadata;
import org.eclipse.papyrus.web.application.profile.services.api.IUMLProfileProvider;
import org.springframework.stereotype.Service;

/**
 * Service to provide some static profiles.
 *
 * @author lfasani
 */
@Service
public class StandardProfileProvider implements IUMLProfileProvider {

    @Override
    public List<UMLProfileMetadata> getUMLProfiles() {
        return List.of(new UMLProfileMetadata("Standard", "pathmap://UML_PROFILES/Standard.profile.uml#_0", ""));
    }
}
