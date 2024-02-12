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
package org.eclipse.papyrus.web.application.tools.activity;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.web.application.representations.uml.ADDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.tools.test.DiagramCreationTest;
import org.junit.jupiter.api.Test;

/**
 * Tests diagram creation for the Activity Diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class ADDiagramCreationTest extends DiagramCreationTest {

    private static final EReference PARENT_CONTAINER = UML.getPackage_PackagedElement();

    private static final EClass INTERMEDIATE_ACTIVITY = UML.getActivity();

    public ADDiagramCreationTest() {
        super(DEFAULT_DOCUMENT, ADDiagramDescriptionBuilder.AD_REP_NAME, UML.getModel());
    }

    @Test
    public void testCreateOnModel() {
        this.testDiagramCreationOnParentElementWithIntermediateElementCreation(UML.getModel(), PARENT_CONTAINER, INTERMEDIATE_ACTIVITY, UML.getPackage_PackagedElement());
    }

    @Test
    public void testCreateOnPackage() {
        this.testDiagramCreationOnParentElementWithIntermediateElementCreation(UML.getPackage(), PARENT_CONTAINER, INTERMEDIATE_ACTIVITY, UML.getPackage_PackagedElement());
    }

    @Test
    public void testCreateOnInteraction() {
        this.testDiagramCreationOnParentElementWithIntermediateElementCreation(UML.getInteraction(), PARENT_CONTAINER, INTERMEDIATE_ACTIVITY,
                UML.getBehavioredClassifier_OwnedBehavior());
    }

    @Test
    public void testCreateOnActivity() {
        this.testDiagramCreationOnParentElement(UML.getActivity(), PARENT_CONTAINER);
    }
}
