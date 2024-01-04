/*******************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
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
import java.util.Objects;
import java.util.UUID;

import org.eclipse.papyrus.web.services.api.uml.profile.UMLProfileVersion;
import org.eclipse.sirius.components.core.api.IPayload;

/**
 * The payload of the GetProfileLastVersion query.
 *
 * @author lfasani
 */
public final class GetProfileLastVersionSuccessPayload implements IPayload {

    private final UUID id;

    private final UMLProfileVersion profileLastVersion;

    public GetProfileLastVersionSuccessPayload(UUID id, UMLProfileVersion profileLastVersion) {
        this.id = Objects.requireNonNull(id);
        this.profileLastVersion = profileLastVersion;
    }

    @Override
    public UUID id() {
        return this.id;
    }

    public UMLProfileVersion getProfileLastVersion() {
        return this.profileLastVersion;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, profileLastVersion: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.profileLastVersion);
    }

}
