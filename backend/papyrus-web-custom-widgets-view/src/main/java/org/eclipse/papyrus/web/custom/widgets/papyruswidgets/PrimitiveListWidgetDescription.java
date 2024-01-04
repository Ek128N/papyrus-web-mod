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
import org.eclipse.sirius.components.view.form.ConditionalListDescriptionStyle;
import org.eclipse.sirius.components.view.form.ListDescriptionStyle;
import org.eclipse.sirius.components.view.form.WidgetDescription;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Primitive List Widget Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getValueExpression
 * <em>Value Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getDisplayExpression
 * <em>Display Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getCandidatesExpression
 * <em>Candidates Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getStyle
 * <em>Style</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getConditionalStyles
 * <em>Conditional Styles</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getIsEnabledExpression
 * <em>Is Enabled Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getDeleteOperation
 * <em>Delete Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getAddOperation
 * <em>Add Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getReorderOperation
 * <em>Reorder Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getItemActionOperation
 * <em>Item Action Operation</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getPrimitiveListWidgetDescription()
 * @model
 * @generated
 */
public interface PrimitiveListWidgetDescription extends WidgetDescription {
    /**
     * Returns the value of the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Value Expression</em>' attribute.
     * @see #setValueExpression(String)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getPrimitiveListWidgetDescription_ValueExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getValueExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getValueExpression
     * <em>Value Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Value Expression</em>' attribute.
     * @see #getValueExpression()
     * @generated
     */
    void setValueExpression(String value);

    /**
     * Returns the value of the '<em><b>Display Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Display Expression</em>' attribute.
     * @see #setDisplayExpression(String)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getPrimitiveListWidgetDescription_DisplayExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getDisplayExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getDisplayExpression
     * <em>Display Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Display Expression</em>' attribute.
     * @see #getDisplayExpression()
     * @generated
     */
    void setDisplayExpression(String value);

    /**
     * Returns the value of the '<em><b>Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Candidates Expression</em>' attribute.
     * @see #setCandidatesExpression(String)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getPrimitiveListWidgetDescription_CandidatesExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getCandidatesExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getCandidatesExpression
     * <em>Candidates Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Candidates Expression</em>' attribute.
     * @see #getCandidatesExpression()
     * @generated
     */
    void setCandidatesExpression(String value);

    /**
     * Returns the value of the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Style</em>' containment reference.
     * @see #setStyle(ListDescriptionStyle)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getPrimitiveListWidgetDescription_Style()
     * @model containment="true"
     * @generated
     */
    ListDescriptionStyle getStyle();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getStyle
     * <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Style</em>' containment reference.
     * @see #getStyle()
     * @generated
     */
    void setStyle(ListDescriptionStyle value);

    /**
     * Returns the value of the '<em><b>Conditional Styles</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.form.ConditionalListDescriptionStyle}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Conditional Styles</em>' containment reference list.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getPrimitiveListWidgetDescription_ConditionalStyles()
     * @model containment="true"
     * @generated
     */
    EList<ConditionalListDescriptionStyle> getConditionalStyles();

    /**
     * Returns the value of the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Is Enabled Expression</em>' attribute.
     * @see #setIsEnabledExpression(String)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getPrimitiveListWidgetDescription_IsEnabledExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getIsEnabledExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getIsEnabledExpression
     * <em>Is Enabled Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Is Enabled Expression</em>' attribute.
     * @see #getIsEnabledExpression()
     * @generated
     */
    void setIsEnabledExpression(String value);

    /**
     * Returns the value of the '<em><b>Delete Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Delete Operation</em>' containment reference.
     * @see #setDeleteOperation(PrimitiveListDeleteOperation)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getPrimitiveListWidgetDescription_DeleteOperation()
     * @model containment="true"
     * @generated
     */
    PrimitiveListDeleteOperation getDeleteOperation();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getDeleteOperation
     * <em>Delete Operation</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Delete Operation</em>' containment reference.
     * @see #getDeleteOperation()
     * @generated
     */
    void setDeleteOperation(PrimitiveListDeleteOperation value);

    /**
     * Returns the value of the '<em><b>Add Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Add Operation</em>' containment reference.
     * @see #setAddOperation(PrimitiveListAddOperation)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getPrimitiveListWidgetDescription_AddOperation()
     * @model containment="true"
     * @generated
     */
    PrimitiveListAddOperation getAddOperation();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getAddOperation
     * <em>Add Operation</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Add Operation</em>' containment reference.
     * @see #getAddOperation()
     * @generated
     */
    void setAddOperation(PrimitiveListAddOperation value);

    /**
     * Returns the value of the '<em><b>Reorder Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Reorder Operation</em>' containment reference.
     * @see #setReorderOperation(PrimitiveListReorderOperation)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getPrimitiveListWidgetDescription_ReorderOperation()
     * @model containment="true"
     * @generated
     */
    PrimitiveListReorderOperation getReorderOperation();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getReorderOperation
     * <em>Reorder Operation</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Reorder Operation</em>' containment reference.
     * @see #getReorderOperation()
     * @generated
     */
    void setReorderOperation(PrimitiveListReorderOperation value);

    /**
     * Returns the value of the '<em><b>Item Action Operation</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Item Action Operation</em>' containment reference.
     * @see #setItemActionOperation(PrimitiveListItemActionOperation)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getPrimitiveListWidgetDescription_ItemActionOperation()
     * @model containment="true"
     * @generated
     */
    PrimitiveListItemActionOperation getItemActionOperation();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getItemActionOperation
     * <em>Item Action Operation</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Item Action Operation</em>' containment reference.
     * @see #getItemActionOperation()
     * @generated
     */
    void setItemActionOperation(PrimitiveListItemActionOperation value);

} // PrimitiveListWidgetDescription
