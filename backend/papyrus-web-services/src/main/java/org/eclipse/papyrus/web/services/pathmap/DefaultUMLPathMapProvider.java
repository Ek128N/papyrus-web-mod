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
package org.eclipse.papyrus.web.services.pathmap;

import java.util.List;

import org.eclipse.papyrus.web.services.api.pathmap.IPathMapProvider;
import org.eclipse.papyrus.web.services.api.pathmap.PathMapMetadata;
import org.springframework.stereotype.Service;

/**
 * Default pathmap for UML.
 *
 * @author Arthur Daussy
 */
@Service
public class DefaultUMLPathMapProvider implements IPathMapProvider {

    @Override
    public List<PathMapMetadata> getPathmaps() {
        return List.of(new PathMapMetadata("UML_LIBRARIES/EcorePrimitiveTypes.library.uml", "libraries/EcorePrimitiveTypes.library.uml"),
                new PathMapMetadata("UML_LIBRARIES/JavaPrimitiveTypes.library.uml", "libraries/JavaPrimitiveTypes.library.uml"),
                new PathMapMetadata("UML_LIBRARIES/UMLPrimitiveTypes.library.uml", "libraries/UMLPrimitiveTypes.library.uml"),
                new PathMapMetadata("UML_LIBRARIES/XMLPrimitiveTypes.library.uml", "libraries/XMLPrimitiveTypes.library.uml"),
                new PathMapMetadata("UML_METAMODELS/Ecore.metamodel.uml", "metamodels/Ecore.metamodel.uml"),
                new PathMapMetadata("UML_METAMODELS/UML.metamodel.uml", "metamodels/UML.metamodel.uml"),
                new PathMapMetadata("UML_PROFILES/Ecore.profile.uml", "profiles/Ecore.profile.uml"),
                new PathMapMetadata("UML_PROFILES/Standard.profile.uml", "profiles/Standard.profile.uml"),
                new PathMapMetadata("UML_PROFILES/UML2.profile.uml", "profiles/UML2.profile.uml"));

    }

}
