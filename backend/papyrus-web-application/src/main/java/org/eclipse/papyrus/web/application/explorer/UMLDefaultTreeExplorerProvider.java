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
import java.util.Objects;
import java.util.Optional;

import org.eclipse.papyrus.web.application.explorer.builder.UMLDefaultTreeDescriptionBuilder;
import org.eclipse.papyrus.web.application.templates.service.api.IUMLProjectCheckerService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.tree.ITreeIdProvider;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerTreeDescriptionProvider;
import org.springframework.stereotype.Service;

/**
 * Explorer to be used inside an Papyrus UML Editing context.
 *
 * @author Arthur Daussy
 */
@Service
public class UMLDefaultTreeExplorerProvider implements IExplorerTreeDescriptionProvider {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final IUMLProjectCheckerService umlChecker;

    public UMLDefaultTreeExplorerProvider(IRepresentationDescriptionSearchService representationDescriptionSearchService,
            IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService, IUMLProjectCheckerService umlChecker) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
        this.umlChecker = umlChecker;
    }

    @Override
    public List<TreeDescription> getDescriptions(IEditingContext editingContext) {
        if (this.umlChecker.isPapyrusProject(editingContext.getId())) {
            var optionalUMLExplorerDescription = this.getUMLExplorerTreeDescription(editingContext);

            if (optionalUMLExplorerDescription.isPresent()) {
                return List.of(optionalUMLExplorerDescription.get());
            }
        }
        return List.of();
    }

    private Optional<TreeDescription> getUMLExplorerTreeDescription(IEditingContext editingContext) {
        return this.representationDescriptionSearchService
                .findAll(editingContext).values().stream()
                .filter(TreeDescription.class::isInstance)
                .map(TreeDescription.class::cast)
                .filter(td -> this.isUMLExplorerViewTreeDescription(td, editingContext))
                .findFirst();
    }

    private boolean isUMLExplorerViewTreeDescription(TreeDescription treeDescription, IEditingContext editingContext) {
        if (treeDescription.getId().startsWith(ITreeIdProvider.TREE_DESCRIPTION_KIND)) {
            // this tree description comes from a tree DSL
            var optionalViewTreeDescription = this.viewRepresentationDescriptionSearchService.findById(editingContext, treeDescription.getId());
            if (optionalViewTreeDescription.isPresent()) {
                return optionalViewTreeDescription.get().getName().equals(UMLDefaultTreeDescriptionBuilder.UML_EXPLORER);
            }
        }
        return false;
    }

}
