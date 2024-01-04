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
package org.eclipse.papyrus.web.profile.cpp;

import java.util.List;

import org.eclipse.papyrus.web.services.api.profile.IUMLProfileProvider;
import org.eclipse.papyrus.web.services.api.uml.profile.UMLProfileMetadata;
import org.springframework.stereotype.Service;

/**
 * Provider of the CPP profile.
 *
 * @author Arthur Daussy
 */
@Service
public class CppProfileProvider implements IUMLProfileProvider {

    @Override
    public List<UMLProfileMetadata> getUMLProfiles() {
        return List.of(new UMLProfileMetadata("C and C++", "pathmap://PapyrusC_Cpp_PROFILES/C_Cpp.profile.uml#_j9REUByGEduN1bTiWJ0lyw", ""));
    }

}
