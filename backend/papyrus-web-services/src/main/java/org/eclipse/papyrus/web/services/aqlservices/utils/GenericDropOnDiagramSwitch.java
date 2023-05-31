/*******************************************************************************
 * Copyright (c) 2022 CEA, Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.services.aqlservices.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.util.UMLSwitch;

public final class GenericDropOnDiagramSwitch extends UMLSwitch<Void> {

    private final IViewCreationHelper viewHelper;

    public GenericDropOnDiagramSwitch(IViewCreationHelper viewHelper) {
        super();
        this.viewHelper = viewHelper;
    }

    @Override
    public Void defaultCase(EObject object) {
        this.viewHelper.createRootView(object);
        return super.defaultCase(object);
    }

}