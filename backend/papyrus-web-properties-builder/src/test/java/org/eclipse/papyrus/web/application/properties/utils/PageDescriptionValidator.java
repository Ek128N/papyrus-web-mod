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
package org.eclipse.papyrus.web.application.properties.utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.uml.domain.services.EMFUtils;
import org.eclipse.papyrus.web.tests.utils.Severity;
import org.eclipse.papyrus.web.tests.utils.Status;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

/**
 * A class gathering validation rules for {@link PageDescription}.
 *
 * @author Arthur Daussy
 */
public class PageDescriptionValidator {

    private static final List<String> FORBIDDEN_SERVICES = List.of("select", "reject", "collect", "any", "exists", "forAll", "isUnique", "one", "sortedBy", "closure");

    private static final List<String> FORBIDDEN_SERVICES_PATTERN = new ArrayList<>();

    static {
        for (String fService : FORBIDDEN_SERVICES) {
            FORBIDDEN_SERVICES_PATTERN.add("feature:" + fService);
            FORBIDDEN_SERVICES_PATTERN.add("." + fService);
        }
    }

    public List<Status> validate(PageDescription pageDescription) {

        List<Status> result = new ArrayList<>();

        result.addAll(validateForbidenServiceExpressionCase(pageDescription));

        if (result.isEmpty()) {
            result.add(Status.OK_SATUS);
        }
        return result;
    }

    /**
     * AQL as trouble with the following services 'select' | 'reject' | 'collect' | 'any' | 'exists' | 'forAll' |
     * 'isUnique' | 'one' | 'sortedBy' | 'closure' because the use lambda as first parameter. To force AQL to use the
     * feature instead of the service we need to add a extra '_'. This test
     */
    private List<Status> validateForbidenServiceExpressionCase(PageDescription pageDescription) {
        List<Status> result = new ArrayList<>();
        EMFUtils.allContainedObjectOfType(pageDescription, WidgetDescription.class).forEach(d -> {
            List<EStructuralFeature> expressionFeatures = getFeatureExpression(d);
            for (var feature : expressionFeatures) {
                String expression = (String) d.eGet(feature);
                checkForbiddenServices(result, d, expression);
            }
        });

        return result;

    }

    private String getQualifiedName(WidgetDescription desc) {
        GroupDescription group = (GroupDescription) desc.eContainer();
        PageDescription page = (PageDescription) group.eContainer();
        return page.getName() + "::" + group.getName() + "::" + desc.getName();
    }

    private void checkForbiddenServices(List<Status> result, WidgetDescription d, String expression) {
        if (expression != null && !expression.isBlank()) {
            for (String pattern : FORBIDDEN_SERVICES_PATTERN) {
                if (pattern.startsWith("feature:")) {
                    if (pattern.equals(expression)) {
                        result.add(new Status(Severity.ERROR, MessageFormat.format("Forbidden expression {0} in {1} matching pattern {2}", expression, getQualifiedName(d), pattern.toString())));
                    }
                } else if (expression.contains(pattern)) {
                    result.add(new Status(Severity.ERROR, MessageFormat.format("Forbidden expression {0} in {1} matching pattern {2}", expression, getQualifiedName(d), pattern.toString())));
                }
            }
        }
    }

    public List<EStructuralFeature> getFeatureExpression(WidgetDescription d) {
        return d.eClass().getEAllStructuralFeatures().stream().filter(f -> f.getName().endsWith("Expression")).toList();
    }

}
