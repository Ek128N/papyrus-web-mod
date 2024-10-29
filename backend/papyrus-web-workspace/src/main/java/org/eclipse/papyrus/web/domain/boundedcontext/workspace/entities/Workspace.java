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
package org.eclipse.papyrus.web.domain.boundedcontext.workspace.entities;

import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Definition of jdbc table Workspace.
 * 
 * @author Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr>
 */
@Table("workspace")
public class Workspace implements Persistable<UUID> {
    @Transient
    private final Logger logger = LoggerFactory.getLogger(Workspace.class);

    @Transient
    private boolean isNew;
            
    @Id
    private UUID id;
    
    @Column("user_id")
    private UUID userId;
    
    @Column("project_id")
    private UUID projectId;
    

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getProjectId() {
        return projectId;
    }
    
    public static Builder newWorkspace() {
        return new Builder();
    }
    
    /**
     * Builder of Workspace.
     * 
     * @author Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr>
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private UUID userId;
        private UUID projectId;
        
        private Builder() {
        }

        public Builder userId(UUID userId) {
            this.userId = Objects.requireNonNull(userId);
            return this;
        }
        public Builder projectId(UUID projectId) {
            this.projectId = Objects.requireNonNull(projectId);
            return this;
        }
        
        public Workspace build() {
            Workspace workspace = new Workspace();
            workspace.isNew = true;
            workspace.id = UUID.randomUUID();
            workspace.userId = Objects.requireNonNull(this.userId);
            workspace.projectId = Objects.requireNonNull(this.projectId);
            
            return workspace;
        }
        
    }
}
