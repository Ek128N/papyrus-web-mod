/*******************************************************************************
 * Copyright (c) 2022, 2023 CEA, Obeo.
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
package org.eclipse.papyrus.web.application.properties;

import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.papyrus.web.application.properties.utils.PageDescriptionValidator;
import org.eclipse.papyrus.web.tests.utils.Severity;
import org.eclipse.papyrus.web.tests.utils.Status;
import org.eclipse.sirius.components.view.PageDescription;
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
        List<PageDescription> pages = new UMLDetailViewBuilder().createPages();
        for (PageDescription page : pages) {
            statuses.addAll(validator.validate(page));
        }
        statuses.addAll(validateUniqueName(pages));

        List<Status> errorStatus = statuses.stream().filter(e -> e.getSeverity() == Severity.ERROR).toList();
        assertTrue(errorStatus.isEmpty(), errorStatus.stream().map(Status::getMessage).collect(joining("\n")));
    }

    private List<Status> validateUniqueName(List<PageDescription> pageDescriptions) {
        Set<String> pageName = new HashSet<>();
        List<Status> result = new ArrayList<>();

        pageDescriptions.forEach(d -> {
            String name = d.getName();
            if (name == null || name.isBlank()) {
                result.add(Status.error("Missing name on" + d)); //$NON-NLS-1$
            } else if (pageName.contains(name)) {
                result.add(Status.error("Dupplicated name" + d.getName())); //$NON-NLS-1$
            } else {
                pageName.add(name);
            }

        });

        return result;

    }

}
