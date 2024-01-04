/*******************************************************************************
 * Copyright (c) 2022, 2024 CEA LIST, Obeo.
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
 *******************************************************************************/
package org.eclipse.papyrus.web.application.properties;

import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.uml.domain.services.EMFUtils;
import org.eclipse.papyrus.uml.domain.services.UMLHelper;
import org.eclipse.papyrus.web.application.properties.builder.UMLDetailViewBuilderCustomImpl;
import org.eclipse.papyrus.web.application.properties.pages.MemberEndGroupDescriptionBuilder;
import org.eclipse.papyrus.web.application.properties.utils.PageDescriptionValidator;
import org.eclipse.papyrus.web.tests.utils.Severity;
import org.eclipse.papyrus.web.tests.utils.Status;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.jupiter.api.Test;

/**
 * Test that validates all {@link PageDescription}.
 *
 * @author Arthur Daussy
 */
public class PageDescriptionTests {

    private PageDescriptionValidator validator = new PageDescriptionValidator();

    @Test
    public void validateDetailView() {
        List<Status> statuses = new ArrayList<>();
        ColorRegistry colorRegistry = new ColorRegistry();
        colorRegistry.registerColor(MemberEndGroupDescriptionBuilder.MEMBER_END_BORDER_COLOR_NAME, "#c2c2c2");
        List<PageDescription> pages = new UMLDetailViewBuilderCustomImpl(colorRegistry).createPages();
        for (PageDescription page : pages) {
            statuses.addAll(validator.validate(page));
        }
        statuses.addAll(validateUniqueName(pages));

        List<Status> errorStatus = statuses.stream().filter(e -> e.getSeverity() == Severity.ERROR).toList();
        assertTrue(errorStatus.isEmpty(), errorStatus.stream().map(Status::getMessage).collect(joining("\n")));
    }

    /**
     * Check that all non abstract UML Concepts have a matching UML page.
     */
    @Test
    public void validateUMLCompleteness() {
        Set<EClass> umlConcepts = EMFUtils.allContainedObjectOfType(UMLPackage.eINSTANCE, EClass.class).filter(e -> !e.isAbstract() && !e.isInterface()).collect(Collectors.toSet());

        // Remove the following concepts because by specification they do not need a custom page
        umlConcepts.remove(UMLPackage.eINSTANCE.getDestructionOccurrenceSpecification());
        umlConcepts.remove(UMLPackage.eINSTANCE.getTemplateSignature());
        umlConcepts.remove(UMLPackage.eINSTANCE.getProtocolConformance());

        ColorRegistry colorRegistry = new ColorRegistry();
        colorRegistry.registerColor(MemberEndGroupDescriptionBuilder.MEMBER_END_BORDER_COLOR_NAME, "#c2c2c2");
        List<PageDescription> allPages = new UMLDetailViewBuilderCustomImpl(colorRegistry).createPages();

        for (PageDescription page : allPages) {
            String domainType = page.getDomainType();
            assertTrue(domainType != null && !domainType.isBlank(), "Invalid domain type for page" + page.getName());
            EClass domain = UMLHelper.toEClass(domainType);
            if (domain != null) {
                umlConcepts.remove(domain);
            }
        }

        assertTrue(umlConcepts.isEmpty(), "Missing page for concepts " + umlConcepts.stream().map(EClass::getName).collect(joining(",")));

    }

    private List<Status> validateUniqueName(List<PageDescription> pageDescriptions) {
        Set<String> pageName = new HashSet<>();
        List<Status> result = new ArrayList<>();

        pageDescriptions.forEach(d -> {
            String name = d.getName();
            if (name == null || name.isBlank()) {
                result.add(Status.error("Missing name on" + d));
            } else if (pageName.contains(name)) {
                result.add(Status.error("Duplicated name" + d.getName()));
            } else {
                pageName.add(name);
            }

        });

        return result;

    }

}
