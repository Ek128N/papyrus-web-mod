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

import org.eclipse.papyrus.web.domain.boundedcontext.profile.ProfileResourceEntity;
import org.eclipse.papyrus.web.domain.boundedcontext.profile.repositories.IProfileRepository;
import org.eclipse.papyrus.web.domain.boundedcontext.profile.service.api.IProfileCreationService;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.springframework.stereotype.Service;

/**
 *
 * Profile Creation Service.
 *
 * @author Arthur Daussy
 */
@Service
public class ProfileCreationService implements IProfileCreationService {
    private final IProfileRepository profileRepository;

    public ProfileCreationService(IProfileRepository profileRepository) {
        super();
        this.profileRepository = profileRepository;
    }

    @Override
    public IResult<ProfileResourceEntity> createProfile(ProfileResourceEntity profile) {
        profile.setNew(this.profileRepository.findById(profile.getId()).isEmpty());

        this.profileRepository.save(profile);

        return new Success<>(profile);

    }

}
