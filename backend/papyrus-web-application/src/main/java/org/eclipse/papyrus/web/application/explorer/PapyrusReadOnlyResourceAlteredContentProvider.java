/*****************************************************************************
 * Copyright (c) 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.explorer;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.web.application.pathmap.services.api.IStaticPathmapChecker;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerTreeAlteredContentProvider;
import org.springframework.stereotype.Service;

/**
 * Implementation of the filter that hides the read only resources.
 *
 * @author Arthur Daussy
 */
@Service
public class PapyrusReadOnlyResourceAlteredContentProvider implements IExplorerTreeAlteredContentProvider {

    private final IStaticPathmapChecker pathmapChecker;

    public PapyrusReadOnlyResourceAlteredContentProvider(IStaticPathmapChecker pathmapChecker) {
        super();
        this.pathmapChecker = Objects.requireNonNull(pathmapChecker);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, List<String> activeFilterIds) {
        return activeFilterIds.contains(PapyrusTreeFilterProvider.HIDE_PATHMAP_URI_TREE_ITEM_FILTER_ID);
    }

    @Override
    public List<Object> apply(List<Object> computedElements, VariableManager variableManager) {
        return computedElements.stream()
                .filter(element -> !(element instanceof Resource res && this.pathmapChecker.isPathmapResource(res)))
                .toList();
    }

}
