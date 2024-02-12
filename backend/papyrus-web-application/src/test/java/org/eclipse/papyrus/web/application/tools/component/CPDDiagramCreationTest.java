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
package org.eclipse.papyrus.web.application.tools.component;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.web.application.representations.uml.CPDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.tools.test.DiagramCreationTest;
import org.junit.jupiter.api.Test;

/**
 * Tests diagram creation for the Component Diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class CPDDiagramCreationTest extends DiagramCreationTest {

    private static final EReference PARENT_CONTAINER = UML.getPackage_PackagedElement();

    public CPDDiagramCreationTest() {
        super(DEFAULT_DOCUMENT, CPDDiagramDescriptionBuilder.CPD_REP_NAME, UML.getModel());
    }

    @Test
    public void testCreateOnModel() {
        this.testDiagramCreationOnParentElement(UML.getModel(), PARENT_CONTAINER);
    }

    @Test
    public void testCreateOnPackage() {
        this.testDiagramCreationOnParentElement(UML.getPackage(), PARENT_CONTAINER);
    }
}
