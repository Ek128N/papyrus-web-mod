/*****************************************************************************
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
 *****************************************************************************/
package org.eclipse.papyrus.web.domain.boundedcontext.profile.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.papyrus.web.domain.boundedcontext.profile.ProfileResourceEntity;
import org.eclipse.papyrus.web.domain.boundedcontext.profile.repositories.IProfileRepository;
import org.eclipse.papyrus.web.domain.boundedcontext.profile.service.api.IProfileSeachService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Profile Search Service.
 *
 * @author Arthur Daussy
 */
@Service
public class ProfileSearchService implements IProfileSeachService {

    private final IProfileRepository profileRepository;

    public ProfileSearchService(IProfileRepository profileRepository) {
        super();
        this.profileRepository = profileRepository;
    }

    @Override
    public boolean existsById(UUID profileId) {
        return this.profileRepository.existsById(profileId);
    }

    @Override
    public Optional<ProfileResourceEntity> findById(UUID projectId) {
        return this.profileRepository.findById(projectId);
    }

    @Override
    public Page<ProfileResourceEntity> findAll(Pageable pageable) {
        return this.profileRepository.findAll(pageable);
    }

    @Override
    public List<ProfileResourceEntity> findAll() {
        return this.profileRepository.findAll();
    }
}
