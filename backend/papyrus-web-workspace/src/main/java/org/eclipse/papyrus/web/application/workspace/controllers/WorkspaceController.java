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
package org.eclipse.papyrus.web.application.workspace.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.papyrus.web.domain.boundedcontext.authenticateduser.services.api.CustomAuthenticatedUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create a /isConnected REST endpoint.
 * 
 * @author Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr>
 */

@RestController
public class WorkspaceController {
    @GetMapping(value = "/isConnected", produces = "application/json")
    @ResponseBody
    ResponseEntity<Map<String, String>> isUserConnected() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomAuthenticatedUserDetails) {
            Map<String, String> response = new HashMap<>();
            response.put("url", "/logout");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(Collections.emptyMap(), HttpStatus.FORBIDDEN);
    }
}
