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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.papyrus.web.domain.boundedcontext.authenticateduser.entities.AuthenticatedUser;
import org.eclipse.papyrus.web.domain.boundedcontext.authenticateduser.repositories.IAuthenticatedUserRepository;
import org.eclipse.papyrus.web.domain.boundedcontext.authenticateduser.services.api.CustomAuthenticatedUserDetails;
import org.eclipse.papyrus.web.domain.boundedcontext.workspace.entities.Workspace;
import org.eclipse.papyrus.web.domain.boundedcontext.workspace.repository.IWorkspaceRepository;
import org.eclipse.papyrus.web.domain.boundedcontext.workspace.services.api.IProjectRevokeService;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.repositories.IProjectRepository;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service used to revoke access projects.
 * 
 * @author Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr>
 */
@Service
public class ProjectRevokeService implements IProjectRevokeService {
    @Autowired
    private IWorkspaceRepository workspaceRepository;
    
    @Autowired
    private IAuthenticatedUserRepository authenticatedUserRepository;
    
    private final IProjectRepository projectRepository;
    private final IMessageService messageService;
    

    public ProjectRevokeService(IProjectRepository projectRepository, IMessageService messageService) {
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IResult<Void> revokeProject(ICause cause, UUID projectId, String userName) {
        IResult<Void> result = null;
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomAuthenticatedUserDetails) {
            CustomAuthenticatedUserDetails userDetails = (CustomAuthenticatedUserDetails) authentication.getPrincipal();
            Optional<AuthenticatedUser> authentificatedUser = authenticatedUserRepository
                    .findByName(userDetails.getUsername());
            if (authentificatedUser.isPresent() && authentificatedUser.get().getName().equalsIgnoreCase(userName)) {
                return new Failure<>("Cannot revoke access on self");
            }
        }

        Optional<Project> optionalProject = this.projectRepository.findById(projectId);
        Optional<AuthenticatedUser> optionalAuthentificatedUser = authenticatedUserRepository.findByName(userName);
        
        if (optionalProject.isEmpty() || optionalAuthentificatedUser.isEmpty()) {
            result = new Failure<>(this.messageService.notFound());
        } else {
            Project project = optionalProject.get();
            AuthenticatedUser authenticatedUser = optionalAuthentificatedUser.get();
           
            List<Workspace> workspaces = workspaceRepository.findAllByProjectIdAndUserIdAndOwner(
                    project.getId(), authenticatedUser.getId(), false);
            workspaces.forEach(w -> workspaceRepository.delete(w));
            
            result = new Success<>(null);
        }

        return result;
    }

}
