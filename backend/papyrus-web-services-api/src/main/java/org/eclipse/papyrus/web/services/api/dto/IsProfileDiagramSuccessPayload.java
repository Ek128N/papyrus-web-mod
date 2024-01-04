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
package org.eclipse.papyrus.web.services.api.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * The payload of the {@code isProfileDiagram} query.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public final class IsProfileDiagramSuccessPayload implements IPayload {

    private final UUID id;

    private final Boolean isProfileDiagram;

    public IsProfileDiagramSuccessPayload(UUID id, Boolean isProfileDiagram) {
        this.id = Objects.requireNonNull(id);
        this.isProfileDiagram = isProfileDiagram;
    }

    @Override
    public UUID id() {
        return this.id;
    }

    public Boolean getIsProfileDiagram() {
        return this.isProfileDiagram;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, isProfileDiagram: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.isProfileDiagram);
    }

}
