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
package org.eclipse.papyrus.web.persistence.repositories;

import java.util.UUID;

import org.eclipse.papyrus.web.persistence.entities.ProfileResourceEntity;
import org.eclipse.sirius.components.annotations.Audited;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Used to persist and retrieve profiles.
 *
 * @author sbegaudeau
 */
@Repository
public interface IProfileRepository extends PagingAndSortingRepository<ProfileResourceEntity, UUID>, ListCrudRepository<ProfileResourceEntity, UUID> {

    @Override
    @Audited
    boolean existsById(UUID id);
}
