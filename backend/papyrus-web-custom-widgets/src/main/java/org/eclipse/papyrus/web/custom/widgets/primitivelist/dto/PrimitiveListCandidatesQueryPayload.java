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
package org.eclipse.papyrus.web.custom.widgets.primitivelist.dto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.papyrus.web.custom.widgets.primitivelist.PrimitiveListCandidate;
import org.eclipse.sirius.components.core.api.IPayload;

/**
 * The payload object for the query primitiveListCandidates.
 *
 * @author Jerome Gout
 */
public record PrimitiveListCandidatesQueryPayload(UUID id, List<PrimitiveListCandidate> candidates) implements IPayload {

    public PrimitiveListCandidatesQueryPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(candidates);
    }
}
