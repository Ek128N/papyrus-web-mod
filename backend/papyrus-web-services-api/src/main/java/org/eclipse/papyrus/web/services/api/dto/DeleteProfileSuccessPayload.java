/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.web.services.api.dto;

import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * Success payload for deleting profile.
 *
 * @author Arthur Daussy
 */
public record DeleteProfileSuccessPayload(UUID id) implements IPayload {

}
