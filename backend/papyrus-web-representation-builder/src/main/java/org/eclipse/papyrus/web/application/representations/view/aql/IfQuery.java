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
 * An AQL if query builder.
 *
 * @author Arthur Daussy
 */
public class IfQuery {

    private final String condition;

    private String ifTrue;

    private String orElse;

    public IfQuery(String condition) {
        super();
        this.condition = condition;
    }

    public IfQuery then(String expression) {
        this.ifTrue = expression;
        return this;
    }

    public IfQuery orElse(String expression) {
        this.orElse = expression;
        return this;
    }

    public static IfQuery ifExpression(String condition) {
        return new IfQuery(condition);
    }

    public String toQuery() {
        String base = AQL_PREFIX + "if " + trimAqlPrefix(condition) + " then " + trimAqlPrefix(ifTrue);

        if (orElse != null) {
            base += " else " + trimAqlPrefix(orElse);
        }

        base += " endif";
        return base;
    }
}
