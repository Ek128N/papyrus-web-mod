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
package org.eclipse.papyrus.web.services.api.uml.profile;

import java.text.MessageFormat;

/**
 * The metadatas of an UML metaclass.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class UMLMetaclassMetadata {

    private final String id;

    private final String name;

    private final String imagePath;

    public UMLMetaclassMetadata(String id, String name, String imagePath) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, name:{2}, imagePath:{3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.name, this.imagePath);
    }

}
