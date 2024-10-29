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
package org.eclipse.papyrus.web.application.workspace.services.api;

import org.eclipse.papyrus.web.application.workspace.dto.ShareProjectInput;
import org.eclipse.sirius.components.core.api.IPayload;

/**
 * API interface for sharing project.
 * 
 * @author Pascal Bannerot (CEA LIST) <pascal.bannerot@cea.fr>
 */
public interface IProjectApplicationShareService {
    IPayload shareProject(ShareProjectInput input);
}
