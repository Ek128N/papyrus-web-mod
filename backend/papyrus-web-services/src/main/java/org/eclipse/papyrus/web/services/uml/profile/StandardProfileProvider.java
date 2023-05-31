/*******************************************************************************
 * Copyright (c) 2022 CEA.
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
package org.eclipse.papyrus.web.services.uml.profile;

import java.util.List;

import org.eclipse.papyrus.web.services.api.profile.IUMLProfileProvider;
import org.eclipse.papyrus.web.services.api.uml.profile.UMLProfileMetadata;
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
        // @formatter:off
        return List.of(new UMLProfileMetadata("Standard", "pathmap://UML_PROFILES/Standard.profile.uml#_0")); //$NON-NLS-1$//$NON-NLS-2$
        // @formatter:on
    }
}
