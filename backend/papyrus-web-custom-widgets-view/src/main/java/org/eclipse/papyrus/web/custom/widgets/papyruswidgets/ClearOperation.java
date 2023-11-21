/**
 * Copyright (c) 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under
 * the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Obeo - Initial API and implementation
 */
package org.eclipse.papyrus.web.custom.widgets.papyruswidgets;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.view.Operation;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Clear Operation</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ClearOperation#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getClearOperation()
 * @model
 * @generated
 */
public interface ClearOperation extends EObject {
    /**
     * Returns the value of the '<em><b>Body</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Body</em>' reference.
     * @see #setBody(Operation)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getClearOperation_Body()
     * @model
     * @generated
     */
    Operation getBody();

    /**
     * Sets the value of the '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ClearOperation#getBody
     * <em>Body</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Body</em>' reference.
     * @see #getBody()
     * @generated
     */
    void setBody(Operation value);

} // ClearOperation
