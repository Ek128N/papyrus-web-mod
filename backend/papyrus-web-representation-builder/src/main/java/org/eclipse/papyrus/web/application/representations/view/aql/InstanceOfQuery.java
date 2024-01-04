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

import static org.eclipse.papyrus.web.application.representations.view.aql.QueryHelper.AQL_PREFIX;
import static org.eclipse.papyrus.web.application.representations.view.aql.QueryHelper.trimAqlPrefix;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.web.application.representations.view.IDomainHelper;

/**
 * Instance query builder.
 *
 * @author Arthur Daussy
 */
public class InstanceOfQuery {

    private final String variable;

    private IDomainHelper metamodelHelper;

    public InstanceOfQuery(String variable, IDomainHelper metamodelHelper) {
        super();
        this.variable = variable;
        this.metamodelHelper = metamodelHelper;
    }

    public static String instanceOf(String variable, EClass classifier, IDomainHelper metamodelHelper) {
        return new InstanceOfQuery(variable, metamodelHelper).instanceOf(classifier);
    }

    public String instanceOf(EClass classifier) {
        return AQL_PREFIX + trimAqlPrefix(variable) + ".oclIsKindOf(" + metamodelHelper.getDomain(classifier) + ")";
    }
}
