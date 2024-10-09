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

import org.eclipse.papyrus.web.application.explorer.builder.UMLDemoTreeDescriptionBuilder;
import org.eclipse.papyrus.web.application.templates.service.api.IUMLProjectCheckerService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.springframework.stereotype.Service;

/**
 * Install the UML demo tree description inside the UML EditingContexts.
 *
 * @author Jerome Gout
 */
@Service
public class UMLDemoTreeExplorerInstaller implements IEditingContextProcessor {
    private final IUMLProjectCheckerService umlChecker;

    private final View view;

    public UMLDemoTreeExplorerInstaller(IUMLProjectCheckerService umlChecker) {
        this.umlChecker = Objects.requireNonNull(umlChecker);
        this.view = new UMLDemoTreeDescriptionBuilder().createView();
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext && this.umlChecker.isPapyrusProject(editingContext.getId())) {
            siriusWebEditingContext.getViews().add(this.view);
        }
    }

}
