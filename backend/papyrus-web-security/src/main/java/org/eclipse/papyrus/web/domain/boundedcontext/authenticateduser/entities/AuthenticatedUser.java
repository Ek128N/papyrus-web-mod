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
package org.eclipse.papyrus.web.domain.boundedcontext.authenticateduser.entities;

import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Definition of jdbc table AuthenticatedUser.
 * 
 * @author Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr>
 */
@Table(name = "authenticateduser")
public class AuthenticatedUser implements Persistable<UUID> {
    @Transient
    private final Logger logger = LoggerFactory.getLogger(AuthenticatedUser.class);
    
    @Transient
    private boolean isNew;
            
    @Id
    private UUID id;
    
    private String name;
    private String pass;
    private String email;
    
    public String getName() {
        return name;
    }
    public String getPass() {
        return pass;
    }
    public String getEmail() {
        return email;
    }
    
    @Override
    public UUID getId() {
        return this.id;
    }
    @Override
    public boolean isNew() {
        return this.isNew;
    }
    
    public static Builder newUser() {
        return new Builder();
    }
    
    /**
     * Builder of AuthenticatedUser.
     * @author Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr>
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String name;
        private String pass;
        private String email;
        
        private Builder() {
        }

        public Builder name(String name) {
            this.name = Objects.requireNonNull(name);
            return this;
        }
        public Builder pass(String pass) {
            this.pass = Objects.requireNonNull(pass);
            return this;
        }
        public Builder email(String email) {
            this.email = name + "@papyrus.com"; // Objects.requireNonNull(email);
            return this;
        }
        
        public AuthenticatedUser build() {
            AuthenticatedUser user = new AuthenticatedUser();
            user.isNew = true;
            user.id = UUID.randomUUID();
            user.name = Objects.requireNonNull(this.name);
            user.pass = Objects.requireNonNull(this.pass);
            user.email = Objects.requireNonNull(this.email);
            
            return user;
        }
        
    }
}
