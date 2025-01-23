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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.papyrus.web.domain.boundedcontext.authenticateduser.entities.AuthenticatedUser;
import org.eclipse.papyrus.web.domain.boundedcontext.authenticateduser.repositories.IAuthenticatedUserRepository;
import org.eclipse.papyrus.web.domain.boundedcontext.authenticateduser.services.api.CustomAuthenticatedUserDetails;
import org.eclipse.papyrus.web.domain.boundedcontext.workspace.entities.Workspace;
import org.eclipse.papyrus.web.domain.boundedcontext.workspace.repository.IWorkspaceRepository;
import org.eclipse.papyrus.web.sirius.contributions.ServiceOverride;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.project.repositories.IProjectRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectDeletionService;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Override of ProjectDeletionService.
 * 
 * @author Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr>
 */
@ServiceOverride(org.eclipse.sirius.web.domain.boundedcontexts.project.services.ProjectDeletionService.class)
public class WorkspaceDeleteService implements IProjectDeletionService {
    @Autowired
    private IWorkspaceRepository workspaceRepository;
    
    @Autowired
    private IAuthenticatedUserRepository authenticatedUserRepository;
    
    private final IProjectRepository projectRepository;

    private final IMessageService messageService;

    public WorkspaceDeleteService(IProjectRepository projectRepository, IMessageService messageService) {
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IResult<Void> deleteProject(ICause cause, UUID projectId) {
        IResult<Void> result = null;
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomAuthenticatedUserDetails) {
            CustomAuthenticatedUserDetails userDetails = (CustomAuthenticatedUserDetails) authentication.getPrincipal();
            Optional<AuthenticatedUser> authentificatedUser = authenticatedUserRepository
                    .findByName(userDetails.getUsername());
            if (authentificatedUser.isPresent()) {
                List<Workspace> wre = workspaceRepository.findAllByProjectIdAndUserIdAndOwner(projectId, authentificatedUser.get().getId(), true);
                if (wre.isEmpty()) {
                    return new Failure<>("Unauthorized deletion");
                }
            }
        }

        var optionalProject = this.projectRepository.findById(projectId);
        if (optionalProject.isPresent()) {
            var project = optionalProject.get();
            project.dispose(cause);

            this.projectRepository.delete(project);
            result = new Success<>(null);
            
            List<Workspace> wre = workspaceRepository.findAllByProjectId(projectId);
            Set<UUID> wsId = wre.stream().map(workspace -> workspace.getId()).collect(Collectors.toSet());
            workspaceRepository.deleteAllById(wsId);
        } else {
            result = new Failure<>(this.messageService.notFound());
        }

        return result;
    }

}
