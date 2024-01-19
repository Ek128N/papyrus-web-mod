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
package org.eclipse.papyrus.web.services.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.papyrus.web.services.aqlservices.properties.PropertiesHelpContentServices;
import org.eclipse.papyrus.web.tests.utils.UMLTestHelper;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.DurationConstraint;
import org.eclipse.uml2.uml.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link PropertiesHelpContentServices} service class.
 *
 * @author Jerome Gout
 */
class PropertiesHelpContentServicesTest {

    private static final String HELP_CONTENT_MULTIPLICITY = "A multiplicity is a definition of an inclusive interval of non-negative integers beginning with a lower bound and ending with a (possibly infinite) upper bound. A MultiplicityElement embeds this information to specify the allowable cardinalities for an instantiation of the Element.\nExample of valid formats: 1, 0..12, 1..*, *";

    private PropertiesHelpContentServices propertiesService;

    /**
     * Helper to create UML elements
     */
    private final UMLTestHelper umlHelper = new UMLTestHelper();

    @BeforeEach
    public void setUp() {
        this.propertiesService = new PropertiesHelpContentServices(new UMLDocumentationService());
    }

    /**
     * Test of retrieving the documentation associated to a feature of an element.
     */
    @Test
    void testGetFeatureDescription() {
        Activity activity = this.umlHelper.create(Activity.class);
        assertEquals("Top-level Variables defined by the Activity.\n", this.propertiesService.getFeatureDescription(activity, "variable"));
    }

    /**
     * Test of retrieving the redefined documentation associated to a feature of an element.
     */
    @Test
    void testGetFeatureDescriptionRedefined() {
        DurationConstraint durationConstraint = this.umlHelper.create(DurationConstraint.class);
        assertEquals("The DurationInterval constraining the duration.\n", this.propertiesService.getFeatureDescription(durationConstraint, "specification"));
    }

    /**
     * Test of retrieving the documentation of the multiplicity feature.
     */
    @Test
    void testGetMultiplicityHelpContent() {
        Property property = this.umlHelper.create(Property.class);
        assertEquals(HELP_CONTENT_MULTIPLICITY, this.propertiesService.getMultiplicityHelpContent(property));
    }

}
