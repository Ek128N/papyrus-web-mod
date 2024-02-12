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
package org.eclipse.papyrus.web.application.tools.communication;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.web.application.representations.uml.CODDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.tools.test.DiagramCreationTest;
import org.junit.jupiter.api.Test;

/**
 * Tests diagram creation for the Communication Diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class CODDiagramCreationTest extends DiagramCreationTest {

    private static final EReference PARENT_CONTAINER = UML.getPackage_PackagedElement();

    private static final EClass INTERMEDIATE_INTERACTION = UML.getInteraction();

    public CODDiagramCreationTest() {
        super(DEFAULT_DOCUMENT, CODDiagramDescriptionBuilder.COD_REP_NAME, UML.getModel());
    }

    @Test
    public void testCreateOnModel() {
        this.testDiagramCreationOnParentElementWithIntermediateElementCreation(UML.getModel(), PARENT_CONTAINER, INTERMEDIATE_INTERACTION, UML.getPackage_PackagedElement());
    }

    @Test
    public void testCreateOnPackage() {
        this.testDiagramCreationOnParentElementWithIntermediateElementCreation(UML.getPackage(), PARENT_CONTAINER, INTERMEDIATE_INTERACTION, UML.getPackage_PackagedElement());
    }

    @Test
    public void testCreateOnActivity() {
        this.testDiagramCreationOnParentElementWithIntermediateElementCreation(UML.getActivity(), PARENT_CONTAINER, INTERMEDIATE_INTERACTION,
                UML.getBehavioredClassifier_OwnedBehavior());
    }

    @Test
    public void testCreateOnInteraction() {
        this.testDiagramCreationOnParentElement(UML.getInteraction(), PARENT_CONTAINER);
    }
}
