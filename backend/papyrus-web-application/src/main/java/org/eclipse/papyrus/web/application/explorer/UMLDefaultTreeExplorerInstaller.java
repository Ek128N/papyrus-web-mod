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

import java.util.Objects;

import org.eclipse.papyrus.web.application.explorer.builder.UMLDefaultTreeDescriptionBuilder;
import org.eclipse.papyrus.web.application.templates.service.api.IUMLProjectCheckerService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.tree.ITreeIdProvider;
import org.eclipse.sirius.components.view.tree.TreeDescription;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.springframework.stereotype.Service;

/**
 * Install the UML default tree description inside the UML EditingContexts.
 *
 * @author Arthur Daussy
 */
@Service
public class UMLDefaultTreeExplorerInstaller implements IEditingContextProcessor {
    private final IUMLProjectCheckerService umlChecker;

    private final View view;

    private String descriptionId;

    public UMLDefaultTreeExplorerInstaller(IUMLProjectCheckerService umlChecker, ITreeIdProvider treeIdProvider) {
        this.umlChecker = Objects.requireNonNull(umlChecker);
        this.view = new UMLDefaultTreeDescriptionBuilder().createView();
        this.descriptionId = treeIdProvider.getId((TreeDescription) this.view.getDescriptions().get(0));
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext && this.umlChecker.isPapyrusProject(editingContext.getId())) {
            siriusWebEditingContext.getViews().add(this.view);
        }
    }

    public String getDescriptionId() {
        return this.descriptionId;
    }

}
