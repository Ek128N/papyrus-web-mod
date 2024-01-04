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
package org.eclipse.papyrus.web.services.uml.profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.papyrus.web.services.api.uml.profile.UMLProfileMetadata;

/**
 * Registry containing all the UML profile metadata.
 *
 * @author lfasani
 */
public class UMLProfileMetadataRegistry {
    private final List<UMLProfileMetadata> profileDescriptions = new ArrayList<>();

    public void add(UMLProfileMetadata uMLProfileDescription) {
        this.profileDescriptions.add(uMLProfileDescription);
    }

    public List<UMLProfileMetadata> getUMLProfileDescriptions() {
        return Collections.unmodifiableList(this.profileDescriptions);
    }
}
