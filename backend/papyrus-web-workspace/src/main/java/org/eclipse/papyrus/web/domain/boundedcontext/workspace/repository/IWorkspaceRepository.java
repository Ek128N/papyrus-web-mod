/*****************************************************************************
 * Copyright (c) 2024 CEA LIST
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.web.domain.boundedcontext.workspace.repository;

import java.util.List;
import java.util.UUID;

import org.eclipse.papyrus.web.domain.boundedcontext.workspace.entities.Workspace;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Workspace repository methods.
 * 
 * @author Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr>
 */
@Repository
public interface IWorkspaceRepository
        extends PagingAndSortingRepository<Workspace, UUID>, ListCrudRepository<Workspace, UUID> {

    List<Workspace> findAllByUserId(UUID id);
    List<Workspace> findAllByProjectId(UUID id);
    List<Workspace> findAllByProjectIdAndUserId(UUID projectId, UUID userId);
    List<Workspace> findAllByProjectIdAndUserIdAndOwner(UUID projectId, UUID userId, boolean isOwner);
}
