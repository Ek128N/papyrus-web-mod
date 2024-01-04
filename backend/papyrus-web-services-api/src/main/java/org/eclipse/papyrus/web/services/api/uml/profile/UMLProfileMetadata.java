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
 * The basic information of an UML profile.
 *
 * @author lfasani
 */
public class UMLProfileMetadata {

    private final String label;

    /**
     * The path of the UML profile file in the project.
     */
    private final String uriPath;

    /**
     * The last version of the UML profile file in the project.
     */
    private String version;

    public UMLProfileMetadata(String label, String uriPath, String version) {
        this.label = Objects.requireNonNull(label);
        this.uriPath = Objects.requireNonNull(uriPath);
        this.version = Objects.requireNonNull(version);
    }

    public String getLabel() {
        return this.label;
    }

    public String getUriPath() {
        return this.uriPath;
    }

    public String getVersion() {
        return this.version;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'label: {1}, uriPath: {2}, version: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.label, this.uriPath, this.version);
    }
}
