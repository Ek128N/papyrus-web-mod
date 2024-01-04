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

/**
 * Builder of query using operation (and, not, or...).
 * 
 * @author obeo
 */
public class OperatorQuery {

    private String expression1;

    public OperatorQuery(String expression1) {
        super();
        this.expression1 = trimAqlPrefix(expression1);
    }

    public static String and(String expression1, String expression2) {
        return new OperatorQuery(expression1).and(expression2).toString();
    }

    public static String not(String expression1) {
        return new OperatorQuery(expression1).not().toString();
    }

    public OperatorQuery and(String expression2) {
        this.expression1 = expression1 + " and " + trimAqlPrefix(expression2);
        return this;
    }

    public OperatorQuery or(String expression2) {
        this.expression1 = expression1 + " or " + trimAqlPrefix(expression2);
        return this;
    }

    public OperatorQuery not() {
        this.expression1 = "not " + expression1;
        return this;
    }

    @Override
    public String toString() {
        return AQL_PREFIX + expression1;
    }

}
