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
package org.eclipse.papyrus.web.application.workspace.services;

import java.util.Objects;

import org.eclipse.papyrus.web.application.workspace.dto.RevokeProjectInput;
import org.eclipse.papyrus.web.application.workspace.dto.RevokeProjectSuccessPayload;
import org.eclipse.papyrus.web.application.workspace.services.api.IProjectApplicationRevokeService;
import org.eclipse.papyrus.web.domain.boundedcontext.workspace.services.api.IProjectRevokeService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.Success;
import org.springframework.stereotype.Service;

/**
 * Service used to revoke share project access.
 * @author Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr>
 */
@Service
public class WorkspaceRevokeService implements IProjectApplicationRevokeService {
    private final IProjectRevokeService projectRevokeService;

    public WorkspaceRevokeService(IProjectRevokeService projectRevokeService) {
        this.projectRevokeService = Objects.requireNonNull(projectRevokeService);
    }
    
    @Override
    public IPayload revokeProject(RevokeProjectInput input) {
        var result = this.projectRevokeService.revokeProject(input, input.projectId(), input.userName());
        IPayload payload = null;
        if (result instanceof Failure<Void> failure) {
            payload = new ErrorPayload(input.id(), failure.message());
        } else if (result instanceof Success<Void>) {
            payload = new RevokeProjectSuccessPayload(input.id());
        }
        return payload;
    }




}
