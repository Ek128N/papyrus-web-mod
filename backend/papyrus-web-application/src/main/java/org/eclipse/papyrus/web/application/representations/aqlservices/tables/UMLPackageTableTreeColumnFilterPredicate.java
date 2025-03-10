/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
 *
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

package org.eclipse.papyrus.web.application.representations.aqlservices.tables;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.function.Predicate;

import org.eclipse.sirius.components.tables.ColumnFilter;
import org.eclipse.uml2.uml.NamedElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example of how ColumnFilter could be implemented.
 *
 * @author Jerome Gout
 */
public class UMLPackageTableTreeColumnFilterPredicate implements Predicate<ColumnFilter> {

    private final ObjectMapper objectMapper;

    private final NamedElement element;

    private final Logger logger = LoggerFactory.getLogger(UMLPackageTableTreeColumnFilterPredicate.class);

    public UMLPackageTableTreeColumnFilterPredicate(ObjectMapper objectMapper, NamedElement namedElement) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.element = Objects.requireNonNull(namedElement);
    }

    @Override
    public boolean test(ColumnFilter columnFilter) {
        boolean isValidColumFilterCandidate = true;
        if (columnFilter.id().equals("name")) {
            isValidColumFilterCandidate = this.isValidNameColumnFilterCandidate(columnFilter);
        } else if (columnFilter.id().equals("qualified-name")) {
            isValidColumFilterCandidate = this.isValidQualifiedNameFilterCandidate(columnFilter);
        }
        return isValidColumFilterCandidate;
    }

    private boolean isValidNameColumnFilterCandidate(ColumnFilter columnFilter) {
        var isValid = true;
        try {
            String filterValue = this.objectMapper.readValue(columnFilter.value(), new TypeReference<>() {
            });
            isValid = this.element.getName() != null && this.element.getName().contains(filterValue);
        } catch (JsonProcessingException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return isValid;
    }

    private boolean isValidQualifiedNameFilterCandidate(ColumnFilter columnFilter) {
        var isValid = true;
        try {
            String filterValue = this.objectMapper.readValue(columnFilter.value(), new TypeReference<>() {
            });
            isValid = this.element.getQualifiedName() != null && this.element.getQualifiedName().contains(filterValue);
        } catch (JsonProcessingException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return isValid;
    }
}
