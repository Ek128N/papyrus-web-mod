/**
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.view.Operation;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Click Reference Value Operation</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ClickReferenceValueOperation#getBody
 * <em>Body</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getClickReferenceValueOperation()
 * @model
 * @generated
 */
public interface ClickReferenceValueOperation extends EObject {
    /**
     * Returns the value of the '<em><b>Body</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.Operation}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Body</em>' containment reference list.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getClickReferenceValueOperation_Body()
     * @model containment="true"
     * @generated
     */
    EList<Operation> getBody();

} // ClickReferenceValueOperation
