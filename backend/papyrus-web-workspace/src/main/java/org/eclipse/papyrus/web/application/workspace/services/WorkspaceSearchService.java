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
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.papyrus.web.domain.boundedcontext.authenticateduser.services.api.CustomAuthenticatedUserDetails;
import org.eclipse.papyrus.web.domain.boundedcontext.workspace.entities.Workspace;
import org.eclipse.papyrus.web.domain.boundedcontext.workspace.repository.IWorkspaceRepository;
import org.eclipse.papyrus.web.sirius.contributions.ServiceOverride;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.repositories.IProjectRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Override of ProjectSearchService.
 * 
 * @author Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr>
 */
@ServiceOverride(org.eclipse.sirius.web.domain.boundedcontexts.project.services.ProjectSearchService.class)
public class WorkspaceSearchService implements IProjectSearchService {
    private final IProjectRepository projectRepository;
    
    @Autowired
    private IWorkspaceRepository workspaceRepository;
    
    public WorkspaceSearchService(IProjectRepository projectRepository) {
        super();
        this.projectRepository = projectRepository;
    }

    @Override
    public boolean existsById(UUID projectId) {
        return this.projectRepository.existsById(projectId);
    }

    @Override
    public Optional<Project> findById(UUID projectId) {
        return this.projectRepository.findById(projectId);
    }

    @Override
    public Page<Project> findAll(Pageable pageable) {
        Page<Project> pageProject = this.projectRepository.findAll(pageable);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomAuthenticatedUserDetails) {
            CustomAuthenticatedUserDetails userDetails = (CustomAuthenticatedUserDetails) authentication.getPrincipal();
            List<Workspace> wre = workspaceRepository.findAllByUserId(userDetails.getId());
            Set<UUID> wsId = wre.stream().map(workspace -> workspace.getProjectId()).collect(Collectors.toSet());
            List<Project> filteredProjectsPage = this.projectRepository.findAll(pageable).get()
                    .filter(pr -> wsId.contains(pr.getId()))
                    .collect(Collectors.toList());
            return new PageImpl<>(filteredProjectsPage, pageable, filteredProjectsPage.size());
        }
        return pageProject;
    }
}
