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
package org.eclipse.papyrus.web.services.api.dto;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.papyrus.web.services.api.uml.profile.UMLStereotypeMetadata;
import org.eclipse.sirius.components.core.api.IPayload;

/**
 * The payload of the GetStereotypes query.
 *
 * @author lfasani
 */
public final class GetStereotypesSuccessPayload implements IPayload {

    private final UUID id;

    private final List<UMLStereotypeMetadata> stereotypeMetadatas;

    public GetStereotypesSuccessPayload(UUID id, List<UMLStereotypeMetadata> stereotypeMetadatas) {
        this.id = Objects.requireNonNull(id);
        this.stereotypeMetadatas = new ArrayList<>(Objects.requireNonNull(stereotypeMetadatas));
    }

    @Override
    public UUID id() {
        return this.id;
    }

    public List<UMLStereotypeMetadata> getStereotypes() {
        return this.stereotypeMetadatas;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }
}
