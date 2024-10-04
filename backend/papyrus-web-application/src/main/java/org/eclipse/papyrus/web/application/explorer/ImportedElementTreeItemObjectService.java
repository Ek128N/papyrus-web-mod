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
package org.eclipse.papyrus.web.application.explorer;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.papyrus.web.application.explorer.builder.aqlservices.UMLDefaultTreeServices;
import org.eclipse.sirius.components.core.api.IDefaultObjectSearchService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchServiceDelegate;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * IObjectSearchServiceDelegate that handles {@link ImportedElementTreeItem}.
 *
 * @author Arthur Daussy
 */
@Service
public class ImportedElementTreeItemObjectService implements IObjectSearchServiceDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportedElementTreeItemObjectService.class);

    private final IDefaultObjectSearchService objectService;

    private final IURLParser urlParser;

    public ImportedElementTreeItemObjectService(IDefaultObjectSearchService objectService, IURLParser urlParser) {
        super();
        this.objectService = Objects.requireNonNull(objectService);
        this.urlParser = Objects.requireNonNull(urlParser);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String objectId) {
        return objectId != null && objectId.startsWith(UMLDefaultTreeServices.PAPYRUS_IMPORTED_ELEMENT_PREFIX);
    }

    @Override
    public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
        Optional<Object> result = Optional.empty();
        try {
            Map<String, List<String>> parameters = this.urlParser.getParameterValues(objectId);
            if (parameters != null) {
                List<String> importedElementId = parameters.get(UMLDefaultTreeServices.IMPORTED_ELEMENT_ID);

                if (importedElementId != null && !importedElementId.isEmpty()) {
                    result = this.objectService.getObject(editingContext, importedElementId.get(0));
                }
            }

        } catch (IllegalStateException e) {
            LOGGER.warn("Unparsable id {} : {}", objectId, e.getCause());
        }

        return result;
    }

}
