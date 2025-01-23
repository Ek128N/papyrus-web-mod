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
package org.eclipse.papyrus.web.security.configuration;

import java.io.IOException;
import java.util.List;

import org.eclipse.papyrus.web.domain.boundedcontext.authenticateduser.entities.AuthenticatedUser;
import org.eclipse.papyrus.web.domain.boundedcontext.authenticateduser.repositories.IAuthenticatedUserRepository;
import org.eclipse.papyrus.web.domain.boundedcontext.authenticateduser.services.api.CustomAuthenticatedUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Custom implementation of authentication failure.
 * If the user is not found, create a new one.
 * 
 * 
 * @author Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr>
 */
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler { // AuthenticationFailureHandler {
    private final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);
    
    @Autowired
    private IAuthenticatedUserRepository userRepository;

    @Autowired
    private CustomAuthenticatedUserDetailsService customAuthenticatedUserDetailsService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (userRepository.findByNameAndPass(username, password).isEmpty()
                && userRepository.findByName(username).isEmpty()) {
            // Create a new AuthenticatedUser
            AuthenticatedUser authentificatedUser = AuthenticatedUser.newUser().name(username)
                    .pass(new BCryptPasswordEncoder().encode(password)).email(username).build();
            authentificatedUser = userRepository.save(authentificatedUser);

            UserDetails userDetails = customAuthenticatedUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                    List.of(new SimpleGrantedAuthority("USER")));
            
            // Put new user in SecurityContext
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(auth);
            
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            
            // Redirect to home page
            response.sendRedirect("/projects");
        } else {
            // Consider wrong authentication: login OK, but password don't match 
            logger.error(exception.getMessage());
            
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            this.setDefaultFailureUrl("/login?error");
            super.onAuthenticationFailure(request, response, exception);
        }

    }

}
