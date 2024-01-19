/*****************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.services.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.papyrus.web.application.utils.AbstractWebUMLTest;
import org.eclipse.papyrus.web.services.properties.UMLDocumentationService;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Unit tests of the UML documentation service.
 *
 * @author Jerome Gout
 */
@SpringBootTest
@WebAppConfiguration
public class UMLDocumentationServiceTests extends AbstractWebUMLTest {

    @Autowired
    private UMLDocumentationService documentationService;

    @Test
    public void validateUMLDocumentationLoad() {
        String classElementName = UMLPackage.eINSTANCE.getClass_().getName();
        String entryKey = this.documentationService.getDocumentationEntryKey(classElementName, "name");
        assertEquals("The name of the NamedElement.\n", this.documentationService.getDocumentation(entryKey));
    }

    @Test
    public void testRedefinedDocumentation() {
        // this test covers the issue described at
        // https://gitlab.eclipse.org/eclipse/papyrus/org.eclipse.papyrus-web/-/issues/54
        String className = UMLPackage.eINSTANCE.getDurationConstraint().getName();
        String entryKey = this.documentationService.getDocumentationEntryKey(className, "specification");
        assertEquals("The DurationInterval constraining the duration.\n", this.documentationService.getDocumentation(entryKey));
    }
}
