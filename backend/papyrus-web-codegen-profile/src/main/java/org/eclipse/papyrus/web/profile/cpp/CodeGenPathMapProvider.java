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

import org.eclipse.papyrus.web.services.api.pathmap.IPathMapProvider;
import org.eclipse.papyrus.web.services.api.pathmap.PathMapMetadata;
import org.springframework.stereotype.Service;

/**
 * Pathmap provider used for CodeGen profile.
 *
 * @author Arthur Daussy
 */
@Service
public class CodeGenPathMapProvider implements IPathMapProvider {

    @Override
    public List<PathMapMetadata> getPathmaps() {
        return List.of(new PathMapMetadata("Codegen_PROFILES/Codegen.profile.uml", "profiles/Codegen.profile.uml"),
                new PathMapMetadata("Codegen_PROFILES/languages.uml", "profiles/languages.uml"));
    }

}
