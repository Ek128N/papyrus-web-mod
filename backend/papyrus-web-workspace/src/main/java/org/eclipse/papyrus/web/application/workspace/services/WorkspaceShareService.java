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

import org.eclipse.papyrus.web.application.workspace.dto.ShareProjectInput;
import org.eclipse.papyrus.web.application.workspace.dto.ShareProjectSuccessPayload;
import org.eclipse.papyrus.web.application.workspace.services.api.IProjectApplicationShareService;
import org.eclipse.papyrus.web.domain.boundedcontext.workspace.services.api.IProjectShareService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.Success;
import org.springframework.stereotype.Service;

/**
 * Service used to share a project between users.
 * 
 * @author Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr>
 */
@Service
public class WorkspaceShareService implements IProjectApplicationShareService {
    private final IProjectShareService projectShareService;

    public WorkspaceShareService(IProjectShareService projectShareService) {
        this.projectShareService = Objects.requireNonNull(projectShareService);
    }

    @Override
    public IPayload shareProject(ShareProjectInput input) {
        var result = this.projectShareService.shareProject(input, input.projectId(), input.userName());
        IPayload payload = null;
        if (result instanceof Failure<Void> failure) {
            payload = new ErrorPayload(input.id(), failure.message());
        } else if (result instanceof Success<Void>) {
            payload = new ShareProjectSuccessPayload(input.id());
        }
        return payload;
    }
}
