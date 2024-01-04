/*******************************************************************************
 * Copyright (c) 2022, 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.services.api.profile;

import java.util.List;

import org.eclipse.papyrus.web.services.api.uml.profile.UMLProfileMetadata;

/**
 * API used to provide static UML Profile metadata.
 *
 * @author lfasani
 */
public interface IUMLProfileProvider {
    /**
     * Returns a non null list of {@link UMLProfileMetadata}.
     */
    List<UMLProfileMetadata> getUMLProfiles();
}
