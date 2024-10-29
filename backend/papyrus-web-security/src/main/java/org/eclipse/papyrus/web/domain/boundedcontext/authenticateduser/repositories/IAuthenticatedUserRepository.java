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
package org.eclipse.papyrus.web.domain.boundedcontext.authenticateduser.repositories;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.papyrus.web.domain.boundedcontext.authenticateduser.entities.AuthenticatedUser;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * AuthenticatedUser repository methods.
 * 
 * @author Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr>
 */

@Repository
public interface IAuthenticatedUserRepository extends ListPagingAndSortingRepository<AuthenticatedUser, UUID>, ListCrudRepository<AuthenticatedUser, UUID> {
    Optional<AuthenticatedUser> findByNameAndPass(String name, String pass);
    Optional<AuthenticatedUser> findByName(String name);
    Optional<AuthenticatedUser> findByPass(String pass);
    Optional<AuthenticatedUser> findByEmail(String email);
}
