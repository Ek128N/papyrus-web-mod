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
package org.eclipse.papyrus.web.application.tables;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.papyrus.web.application.tables.comment.UMLCommentTableRepresentationDescriptionBuilder;
import org.eclipse.papyrus.web.application.templates.service.api.IUMLProjectCheckerService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.tree.ITreeIdProvider;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.springframework.stereotype.Service;

/**
 * Install the all UML related table view descriptions inside the UML EditingContexts.
 *
 * @author Jerome Gout
 */
@Service
public class UMLTableViewDescriptionsInstaller implements IEditingContextProcessor {
    private final IUMLProjectCheckerService umlChecker;

    private final List<View> views;

    public UMLTableViewDescriptionsInstaller(IUMLProjectCheckerService umlChecker, ITreeIdProvider treeIdProvider) {
        this.umlChecker = Objects.requireNonNull(umlChecker);
        this.views = new ArrayList<>();
        this.views.add(new UMLCommentTableRepresentationDescriptionBuilder().createView());
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext && this.umlChecker.isPapyrusProject(editingContext.getId())) {
            siriusWebEditingContext.getViews().addAll(this.views);
        }
    }
}
