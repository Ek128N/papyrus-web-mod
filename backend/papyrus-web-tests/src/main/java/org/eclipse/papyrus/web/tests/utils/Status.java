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
package org.eclipse.papyrus.web.tests.utils;

import java.util.Objects;

/**
 * Bean used to report a status.
 *
 * @author Arthur Daussy
 */
public class Status {

    public static final Status OK_SATUS = new Status(Severity.OK, "");

    private final Severity severity;

    private final String message;

    public Status(Severity severity, String message) {
        super();
        this.severity = severity;
        this.message = message;
    }

    public static Status error(String msg) {
        Objects.requireNonNull(msg);
        return new Status(Severity.ERROR, msg);
    }

    public String getMessage() {
        return this.message;
    }

    public Severity getSeverity() {
        return this.severity;
    }

    public boolean isValid() {
        return this.severity == Severity.OK || this.severity == Severity.INFO;
    }

}
