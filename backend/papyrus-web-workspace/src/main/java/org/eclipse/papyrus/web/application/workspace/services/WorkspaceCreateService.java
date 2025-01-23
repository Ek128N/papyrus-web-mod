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

import org.eclipse.papyrus.web.domain.boundedcontext.authenticateduser.entities.AuthenticatedUser;
import org.eclipse.papyrus.web.domain.boundedcontext.authenticateduser.repositories.IAuthenticatedUserRepository;
import org.eclipse.papyrus.web.domain.boundedcontext.authenticateduser.services.api.CustomAuthenticatedUserDetails;
import org.eclipse.papyrus.web.domain.boundedcontext.workspace.entities.Workspace;
import org.eclipse.papyrus.web.domain.boundedcontext.workspace.repository.IWorkspaceRepository;
import org.eclipse.papyrus.web.sirius.contributions.ServiceOverride;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.repositories.IProjectRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectCreationService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectNameValidator;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Override of ProjectCreationService.
 * 
 * @author Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr>
 */
@ServiceOverride(org.eclipse.sirius.web.domain.boundedcontexts.project.services.ProjectCreationService.class)
public class WorkspaceCreateService implements IProjectCreationService {
    @Autowired
    private IAuthenticatedUserRepository authenticatedUserRepository;
    
    @Autowired
    private IWorkspaceRepository workspaceRepository;
    
    private final IProjectRepository projectRepository;

    private final IProjectNameValidator projectNameValidator;

    private final IMessageService messageService;

    public WorkspaceCreateService(IProjectRepository projectRepository, IProjectNameValidator projectNameValidator, IMessageService messageService) {
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.projectNameValidator = Objects.requireNonNull(projectNameValidator);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IResult<Project> createProject(ICause cause, String name, List<String> natures) {
        IResult<Project> result = null;

        var projectName = this.projectNameValidator.sanitize(name);
        if (!this.projectNameValidator.isValid(projectName)) {
            result = new Failure<>(this.messageService.invalidName());
        } else {
            Project project = Project.newProject()
                    .name(name)
                    .natures(natures)
                    .build(cause);
            project = this.projectRepository.save(project);

            result = new Success<>(project);
            
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof CustomAuthenticatedUserDetails) {
                CustomAuthenticatedUserDetails userDetails = (CustomAuthenticatedUserDetails) authentication.getPrincipal();
                Optional<AuthenticatedUser> authentificatedUser = authenticatedUserRepository.findByName(userDetails.getUsername());
                Workspace workspace = Workspace.newWorkspace().userId(authentificatedUser.get().getId()).projectId(project.getId()).owner(true).build();
                workspaceRepository.save(workspace);
            }
        }

        return result;
    }

}
