/*****************************************************************************
 * Copyright (c) 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.web.application.templates.projects;

import org.eclipse.sirius.components.collaborative.api.IRepresentationMetadataPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.springframework.stereotype.Service;

/**
 * Bundles the bean dependencies that project template initializers need into a single object for convenience.
 *
 * @author Jerome Gout
 */
@Service
public record PapyrusProjectTemplateInitializerParameters(
        IRepresentationPersistenceService representationPersistenceService,
        IRepresentationDescriptionSearchService representationDescriptionSearchService,
        IRepresentationMetadataPersistenceService representationMetadataPersistenceService) {
}
