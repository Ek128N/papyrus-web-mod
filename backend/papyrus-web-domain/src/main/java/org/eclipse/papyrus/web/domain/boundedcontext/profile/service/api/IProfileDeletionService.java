/*******************************************************************************
 * Copyright (c) 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.domain.boundedcontext.profile.service.api;

import java.util.UUID;

import org.eclipse.sirius.web.domain.services.IResult;

/**
 * Used to delete profile.
 *
 * @author Arthur Daussy
 */
public interface IProfileDeletionService {

    /**
     * Deletes the profile identified by the given id.
     *
     * @param profileId
     *            a profile id
     * @return an {@link IResult}
     */
    IResult<Void> deleteProfile(UUID profileId);
}
