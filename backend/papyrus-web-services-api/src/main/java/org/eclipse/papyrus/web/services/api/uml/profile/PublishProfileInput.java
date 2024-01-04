/*******************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
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
 *******************************************************************************/
package org.eclipse.papyrus.web.services.api.uml.profile;

import java.util.UUID;

import org.eclipse.sirius.components.core.api.IInput;

/**
 * Used to publish the profile.
 *
 * @author lfasani
 */
public record PublishProfileInput(UUID id, String editingContextId, String objectId, String version, String comment, String copyright, String date, String author, boolean saveOCLConstraint)
        implements IInput {
}
