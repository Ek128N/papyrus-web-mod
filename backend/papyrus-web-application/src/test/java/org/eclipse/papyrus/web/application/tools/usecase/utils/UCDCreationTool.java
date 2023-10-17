/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.tools.usecase.utils;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A creation tool for Use Case Diagram tests.
 * <p>
 * Instances of this class are built from a {@code toolSection} name (see {@link UCDToolSections}) and the
 * {@code eClass} of the element to create.
 *
 * @see UCDToolSections
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public final class UCDCreationTool extends org.eclipse.papyrus.web.application.tools.utils.CreationTool {

    /**
     * Build the Use Case Diagram creation tool from the provided {@code toolSection} and {@code eClass}.
     *
     * @param toolSection
     *            the name of the tool section containing the creation tool
     * @param eClass
     *            the type of the element to create
     */
    public UCDCreationTool(String toolSection, EClass eClass) {
        super(toolSection, eClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String computeToolName(EClass eClass) {
        String result = super.computeToolName(eClass);
        if (UMLPackage.eINSTANCE.getClass_().isSuperTypeOf(eClass)) {
            result = "New " + this.splitWordsInMixedCase(eClass.getName()) + " as Subject";
        }
        return result;
    }

}
