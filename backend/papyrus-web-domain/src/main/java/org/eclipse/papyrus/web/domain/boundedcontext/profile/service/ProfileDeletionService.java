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

import java.util.UUID;

import org.eclipse.papyrus.web.domain.boundedcontext.profile.repositories.IProfileRepository;
import org.eclipse.papyrus.web.domain.boundedcontext.profile.service.api.IProfileDeletionService;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.springframework.stereotype.Service;

/**
 * Profile Deletion Service.
 *
 * @author Arthur Daussy
 */
@Service
public class ProfileDeletionService implements IProfileDeletionService {
    private final IProfileRepository profileRepository;

    public ProfileDeletionService(IProfileRepository profileRepository) {
        super();
        this.profileRepository = profileRepository;
    }

    @Override
    public IResult<Void> deleteProfile(UUID profileId) {
        this.profileRepository.deleteById(profileId);
        return new Success<>(null);
    }

}
