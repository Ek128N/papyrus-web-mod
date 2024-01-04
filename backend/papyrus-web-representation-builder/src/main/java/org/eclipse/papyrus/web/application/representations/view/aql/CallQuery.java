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
package org.eclipse.papyrus.web.application.representations.view.aql;

import static java.util.stream.Collectors.joining;
import static org.eclipse.papyrus.web.application.representations.view.aql.QueryHelper.AQL_PREFIX;
import static org.eclipse.papyrus.web.application.representations.view.aql.QueryHelper.trimAqlPrefix;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.SELF;

import java.util.stream.Stream;

import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Builds AQL query to access metamodel features.
 *
 * @author Arthur Daussy
 */
public class CallQuery {

    private static final String CALL_SEP = ".";

    private final String variable;

    public CallQuery(String variable) {
        super();
        this.variable = variable;
    }

    public String callOperation(EOperation operation) {
        return AQL_PREFIX + trimAqlPrefix(variable) + CALL_SEP + operation.getName() + "()";
    }

    public String accessFeature(EStructuralFeature feature) {
        return AQL_PREFIX + trimAqlPrefix(variable) + CALL_SEP + feature.getName();
    }

    public String callService(String serviceName, String... parameters) {
        return AQL_PREFIX + trimAqlPrefix(variable) + CALL_SEP + serviceName + "(" + Stream.of(parameters).collect(joining(",")) + ")";
    }

    public static String queryAttributeOnSelf(EStructuralFeature feature) {
        return new CallQuery(SELF).accessFeature(feature);
    }

    public static String queryOperationOnSelf(EOperation operation) {
        return new CallQuery(SELF).callOperation(operation);
    }

    public static String queryServiceOnSelf(String serviceName, String... parameters) {
        return new CallQuery(SELF).callService(serviceName, parameters);
    }

}
