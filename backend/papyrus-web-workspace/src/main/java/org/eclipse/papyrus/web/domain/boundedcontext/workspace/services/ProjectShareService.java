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
package org.eclipse.papyrus.web.domain.boundedcontext.workspace.services;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.papyrus.web.domain.boundedcontext.authenticateduser.entities.AuthenticatedUser;
import org.eclipse.papyrus.web.domain.boundedcontext.authenticateduser.repositories.IAuthenticatedUserRepository;
import org.eclipse.papyrus.web.domain.boundedcontext.workspace.entities.Workspace;
import org.eclipse.papyrus.web.domain.boundedcontext.workspace.repository.IWorkspaceRepository;
import org.eclipse.papyrus.web.domain.boundedcontext.workspace.services.api.IProjectShareService;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.repositories.IProjectRepository;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service used to share projects.
 * 
 * @author Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr>
 */
@Service
public class ProjectShareService implements IProjectShareService {
    @Autowired
    private IWorkspaceRepository workspaceRepository;
    
    @Autowired
    private IAuthenticatedUserRepository authenticatedUserRepository;
    
    private final IProjectRepository projectRepository;
    private final IMessageService messageService;
    

    public ProjectShareService(IProjectRepository projectRepository, IMessageService messageService) {
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IResult<Void> shareProject(ICause cause, UUID projectId, String userName) {
        IResult<Void> result = null;

        Optional<Project> optionalProject = this.projectRepository.findById(projectId);
        Optional<AuthenticatedUser> optionalAuthentificatedUser = authenticatedUserRepository.findByName(userName);
        
        if (optionalProject.isEmpty() || optionalAuthentificatedUser.isEmpty()) {
            result = new Failure<>(this.messageService.notFound());
        } else {
            Project project = optionalProject.get();
            AuthenticatedUser authenticatedUser = optionalAuthentificatedUser.get();
           
            if (workspaceRepository.findAllByProjectIdAndUserId(projectId, authenticatedUser.getId()).isEmpty()) {
                Workspace workspace = Workspace.newWorkspace().userId(authenticatedUser.getId())
                        .projectId(project.getId()).owner(false).build();
                workspace = workspaceRepository.save(workspace);
            }
            
            result = new Success<>(null);
        }

        return result;
    }

}
