/*****************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo, Artal Technologies.
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
 *  Titouan BOUETE-GIRAUD (Artal Technologies) - Issue 210
 *****************************************************************************/
package org.eclipse.papyrus.web.custom.widgets.papyruswidgets;

import org.eclipse.sirius.components.view.form.WidgetDescription;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Custom Image Widget Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CustomImageWidgetDescription#getAddImageOperation
 * <em>Add Image Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CustomImageWidgetDescription#getSelectImageOperation
 * <em>Select Image Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CustomImageWidgetDescription#getRemoveImageOperation
 * <em>Remove Image Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CustomImageWidgetDescription#getUuidExpression
 * <em>Uuid Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getCustomImageWidgetDescription()
 * @model
 * @generated
 */
public interface CustomImageWidgetDescription extends WidgetDescription {
    /**
     * Returns the value of the '<em><b>Add Image Operation</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Add Image Operation</em>' containment reference.
     * @see #setAddImageOperation(AddImageOperation)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getCustomImageWidgetDescription_AddImageOperation()
     * @model containment="true" required="true"
     * @generated
     */
    AddImageOperation getAddImageOperation();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CustomImageWidgetDescription#getAddImageOperation
     * <em>Add Image Operation</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Add Image Operation</em>' containment reference.
     * @see #getAddImageOperation()
     * @generated
     */
    void setAddImageOperation(AddImageOperation value);

    /**
     * Returns the value of the '<em><b>Select Image Operation</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Select Image Operation</em>' containment reference.
     * @see #setSelectImageOperation(SelectImageOperation)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getCustomImageWidgetDescription_SelectImageOperation()
     * @model containment="true" required="true"
     * @generated
     */
    SelectImageOperation getSelectImageOperation();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CustomImageWidgetDescription#getSelectImageOperation
     * <em>Select Image Operation</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Select Image Operation</em>' containment reference.
     * @see #getSelectImageOperation()
     * @generated
     */
    void setSelectImageOperation(SelectImageOperation value);

    /**
     * Returns the value of the '<em><b>Remove Image Operation</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Remove Image Operation</em>' containment reference.
     * @see #setRemoveImageOperation(RemoveImageOperation)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getCustomImageWidgetDescription_RemoveImageOperation()
     * @model containment="true" required="true"
     * @generated
     */
    RemoveImageOperation getRemoveImageOperation();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CustomImageWidgetDescription#getRemoveImageOperation
     * <em>Remove Image Operation</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Remove Image Operation</em>' containment reference.
     * @see #getRemoveImageOperation()
     * @generated
     */
    void setRemoveImageOperation(RemoveImageOperation value);

    /**
     * Returns the value of the '<em><b>Uuid Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Uuid Expression</em>' attribute.
     * @see #setUuidExpression(String)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getCustomImageWidgetDescription_UuidExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getUuidExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CustomImageWidgetDescription#getUuidExpression
     * <em>Uuid Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Uuid Expression</em>' attribute.
     * @see #getUuidExpression()
     * @generated
     */
    void setUuidExpression(String value);

} // CustomImageWidgetDescription
