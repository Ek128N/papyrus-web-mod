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
package org.eclipse.papyrus.web.application.representations.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.papyrus.uml.domain.services.EMFUtils;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.DiagramElementDescription;
import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.NodeDescription;

/**
 * A class gathering validation rules for {@link DiagramDescription}.
 *
 * @author Arthur Daussy
 */
public class DiagramDescriptionDescriptionValidator {

    private Predicate<DiagramElementDescription> excludedFromDeleteToolValidation = p -> true;

    private Predicate<DiagramElementDescription> excludedFromDirectEditValidation = p -> true;

    public List<Status> validate(DiagramDescription description) {

        List<Status> result = new ArrayList<>();

        result.addAll(validateUniqueName(description));
        result.addAll(validateDeleteTool(description));
        result.addAll(validateDirectEditTool(description));

        if (result.isEmpty()) {
            result.add(Status.OK_SATUS);
        }
        return result;
    }

    public DiagramDescriptionDescriptionValidator excludeFromDirectEditValidation(Predicate<DiagramElementDescription> filter) {
        excludedFromDirectEditValidation = excludedFromDirectEditValidation.and(filter);
        return this;
    }

    public DiagramDescriptionDescriptionValidator excludeFromDeleteToolValidation(Predicate<DiagramElementDescription> filter) {
        excludedFromDeleteToolValidation = excludedFromDeleteToolValidation.and(filter);
        return this;
    }

    /**
     * Check that all Node and domain based edge have a direct edit tool associated.
     * 
     * @param description
     *            a description
     * @return a list of error status
     */
    private Collection<? extends Status> validateDirectEditTool(DiagramDescription description) {
        List<Status> result = new ArrayList<>();

        EMFUtils.allContainedObjectOfType(description, DiagramElementDescription.class)//
                .filter(excludedFromDirectEditValidation)//
                .filter(d -> d.getLabelEditTool() == null).forEach(d -> {
                    if (d instanceof NodeDescription || isDomainBasedEdge(d)) {
                        result.add(Status.error("Missing direct edit tool on " + d.getName())); //$NON-NLS-1$
                    }
                });
        return result;
    }

    private boolean isDomainBasedEdge(DiagramElementDescription description) {
        return description instanceof EdgeDescription && ((EdgeDescription) description).isIsDomainBasedEdge();
    }

    /**
     * All {@link NodeDescription}s should have a proper delete tool.
     *
     * @param description
     *            a diagram description
     */
    private List<Status> validateDeleteTool(DiagramDescription description) {
        List<Status> result = new ArrayList<>();

        EMFUtils.allContainedObjectOfType(description, NodeDescription.class)//
                .filter(excludedFromDeleteToolValidation).filter(d -> d.getDeleteTool() == null).forEach(d -> {
                    result.add(Status.error("Missing deletion tool on " + d.getName())); //$NON-NLS-1$
                });
        return result;
    }

    private List<Status> validateUniqueName(DiagramDescription description) {
        Set<String> names = new HashSet<>();
        List<Status> result = new ArrayList<>();

        EMFUtils.allContainedObjectOfType(description, DiagramElementDescription.class).forEach(d -> {
            String name = d.getName();
            if (name == null || name.isBlank()) {
                result.add(Status.error("Missing name on" + d)); //$NON-NLS-1$
            } else if (names.contains(name)) {
                result.add(Status.error("Dupplicated name" + d.getName())); //$NON-NLS-1$
            } else {
                names.add(name);
            }

        });

        return result;

    }

}
