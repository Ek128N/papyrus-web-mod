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
package org.eclipse.papyrus.web.domain.boundedcontext.authenticateduser.services.api;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.eclipse.papyrus.web.domain.boundedcontext.authenticateduser.entities.AuthenticatedUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Mapping authenticated user with org.springframework.security.core.userdetails.UserDetails.
 * 
 * @author Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr>
 */
@SuppressWarnings("serial")
public class CustomAuthenticatedUserDetails implements UserDetails {

    private UUID id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    
    public CustomAuthenticatedUserDetails(UUID id, String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public CustomAuthenticatedUserDetails(AuthenticatedUser authenticatedUser) {
        super();
        this.id = authenticatedUser.getId();
        this.username = authenticatedUser.getName();
        this.password = authenticatedUser.getPass();
        this.authorities = List.of(new SimpleGrantedAuthority("USER"));
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


}
