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
package org.eclipse.papyrus.web.services.editingcontext;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.papyrus.web.persistence.entities.ProfileResourceEntity;
import org.eclipse.papyrus.web.persistence.repositories.IProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Implementation of the profile repository which does nothing.
 *
 * @author lfasani
 */
public class NoOpProfileRepository implements IProfileRepository {

    @Override
    public Iterable<ProfileResourceEntity> findAll(Sort sort) {
        return Collections.emptyList();
    }

    @Override
    public Page<ProfileResourceEntity> findAll(Pageable pageable) {
        return Page.empty();
    }

    @Override
    public <S extends ProfileResourceEntity> S save(S entity) {
        return entity;
    }

    @Override
    public <S extends ProfileResourceEntity> List<S> saveAll(Iterable<S> entities) {
        return Collections.emptyList();
    }

    @Override
    public Optional<ProfileResourceEntity> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID id) {
        return false;
    }

    @Override
    public List<ProfileResourceEntity> findAll() {
        return Collections.emptyList();
    }

    @Override
    public List<ProfileResourceEntity> findAllById(Iterable<UUID> ids) {
        return Collections.emptyList();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID id) {
    }

    @Override
    public void delete(ProfileResourceEntity entity) {
    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> ids) {
    }

    @Override
    public void deleteAll(Iterable<? extends ProfileResourceEntity> entities) {
    }

    @Override
    public void deleteAll() {
    }

}
