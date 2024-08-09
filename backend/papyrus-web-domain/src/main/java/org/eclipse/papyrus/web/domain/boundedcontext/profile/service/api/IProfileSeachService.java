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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.papyrus.web.domain.boundedcontext.profile.ProfileResourceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Search profile service.
 *
 * @author Arthur Daussy
 */
public interface IProfileSeachService {

    /**
     * Checks if the profile identified by the given id exists.
     *
     * @param profileId
     *            an id
     * @return <code>true</code> if exist.
     */
    boolean existsById(UUID profileId);

    /**
     * Finds a profile using its ID.
     *
     * @param projectId
     *            an id
     * @return a profile if found, optional empty otherwise
     */
    Optional<ProfileResourceEntity> findById(UUID projectId);

    /**
     * Finds all profiles.
     *
     * @param pageable
     *            a {@link Pageable}
     * @return a page of {@link ProfileResourceEntity}
     */
    Page<ProfileResourceEntity> findAll(Pageable pageable);

    /**
     * Finds all profiles.
     *
     * @return all profiles
     */
    List<ProfileResourceEntity> findAll();
}
