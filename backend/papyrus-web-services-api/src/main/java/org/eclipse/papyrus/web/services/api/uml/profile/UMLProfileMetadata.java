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

    public UMLProfileMetadata(String label, String uriPath) {
        this.label = Objects.requireNonNull(label);
        this.uriPath = Objects.requireNonNull(uriPath);
    }

    public String getLabel() {
        return this.label;
    }

    public String getUriPath() {
        return this.uriPath;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'label: {1}, uriPath: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.label, this.uriPath);
    }
}
