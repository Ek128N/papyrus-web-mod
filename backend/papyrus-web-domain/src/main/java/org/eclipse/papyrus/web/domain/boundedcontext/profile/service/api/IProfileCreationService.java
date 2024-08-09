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

import org.eclipse.papyrus.web.domain.boundedcontext.profile.ProfileResourceEntity;
import org.eclipse.sirius.web.domain.services.IResult;

/**
 * Use to create projects.
 *
 * @author Arthur Daussy
 */
public interface IProfileCreationService {

    /**
     * Create a new profile.
     *
     * <b> Be aware to set the flag {@link ProfileResourceEntity#isNew()} to <code>true</code> to create a new profile
     * or to <code>false</code> to update an existing one</b>
     *
     * @param profile
     *            a profile
     * @return the result
     */
    IResult<ProfileResourceEntity> createProfile(ProfileResourceEntity profile);
}
