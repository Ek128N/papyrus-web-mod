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
package org.eclipse.papyrus.web.services.api.uml.profile;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * The basic information of an UML stereotype.
 *
 * @author lfasani
 */
public class UMLStereotypeMetadata {

    private final String id;

    private final String label;

    public UMLStereotypeMetadata(String label, String uriPath) {
        this.id = Objects.requireNonNull(uriPath);
        this.label = Objects.requireNonNull(label);
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }
}
