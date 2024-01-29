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
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.eclipse.sirius.components.widgets.reference.ConditionalReferenceWidgetDescriptionStyle;
import org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescriptionStyle;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Multi Reference Widget Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getIsEnabledExpression
 * <em>Is Enabled Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getOwnerExpression
 * <em>Owner Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getType
 * <em>Type</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getValueExpression
 * <em>Value Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getCandidatesSearchScopeExpression
 * <em>Candidates Search Scope Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getDropdownOptionsExpression
 * <em>Dropdown Options Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getCreateElementOperation
 * <em>Create Element Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getAddOperation
 * <em>Add Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getRemoveOperation
 * <em>Remove Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getClearOperation
 * <em>Clear Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getReorderOperation
 * <em>Reorder Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getStyle
 * <em>Style</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getConditionalStyles
 * <em>Conditional Styles</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMultiReferenceWidgetDescription()
 * @model
 * @generated
 */
public interface MultiReferenceWidgetDescription extends WidgetDescription {
    /**
     * Returns the value of the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Is Enabled Expression</em>' attribute.
     * @see #setIsEnabledExpression(String)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMultiReferenceWidgetDescription_IsEnabledExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getIsEnabledExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getIsEnabledExpression
     * <em>Is Enabled Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Is Enabled Expression</em>' attribute.
     * @see #getIsEnabledExpression()
     * @generated
     */
    void setIsEnabledExpression(String value);

    /**
     * Returns the value of the '<em><b>Owner Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Owner Expression</em>' attribute.
     * @see #setOwnerExpression(String)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMultiReferenceWidgetDescription_OwnerExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getOwnerExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getOwnerExpression
     * <em>Owner Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Owner Expression</em>' attribute.
     * @see #getOwnerExpression()
     * @generated
     */
    void setOwnerExpression(String value);

    /**
     * Returns the value of the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Type</em>' attribute.
     * @see #setType(String)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMultiReferenceWidgetDescription_Type()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getType();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getType
     * <em>Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Type</em>' attribute.
     * @see #getType()
     * @generated
     */
    void setType(String value);

    /**
     * Returns the value of the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Value Expression</em>' attribute.
     * @see #setValueExpression(String)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMultiReferenceWidgetDescription_ValueExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getValueExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getValueExpression
     * <em>Value Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Value Expression</em>' attribute.
     * @see #getValueExpression()
     * @generated
     */
    void setValueExpression(String value);

    /**
     * Returns the value of the '<em><b>Candidates Search Scope Expression</b></em>' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Candidates Search Scope Expression</em>' attribute.
     * @see #setCandidatesSearchScopeExpression(String)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMultiReferenceWidgetDescription_CandidatesSearchScopeExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getCandidatesSearchScopeExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getCandidatesSearchScopeExpression
     * <em>Candidates Search Scope Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Candidates Search Scope Expression</em>' attribute.
     * @see #getCandidatesSearchScopeExpression()
     * @generated
     */
    void setCandidatesSearchScopeExpression(String value);

    /**
     * Returns the value of the '<em><b>Dropdown Options Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Dropdown Options Expression</em>' attribute.
     * @see #setDropdownOptionsExpression(String)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMultiReferenceWidgetDescription_DropdownOptionsExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getDropdownOptionsExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getDropdownOptionsExpression
     * <em>Dropdown Options Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Dropdown Options Expression</em>' attribute.
     * @see #getDropdownOptionsExpression()
     * @generated
     */
    void setDropdownOptionsExpression(String value);

    /**
     * Returns the value of the '<em><b>Create Element Operation</b></em>' containment reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Create Element Operation</em>' containment reference.
     * @see #setCreateElementOperation(CreateElementInReferenceOperation)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMultiReferenceWidgetDescription_CreateElementOperation()
     * @model containment="true" required="true"
     * @generated
     */
    CreateElementInReferenceOperation getCreateElementOperation();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getCreateElementOperation
     * <em>Create Element Operation</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Create Element Operation</em>' containment reference.
     * @see #getCreateElementOperation()
     * @generated
     */
    void setCreateElementOperation(CreateElementInReferenceOperation value);

    /**
     * Returns the value of the '<em><b>Add Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Add Operation</em>' containment reference.
     * @see #setAddOperation(MultiReferenceAddOperation)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMultiReferenceWidgetDescription_AddOperation()
     * @model containment="true" required="true"
     * @generated
     */
    MultiReferenceAddOperation getAddOperation();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getAddOperation
     * <em>Add Operation</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Add Operation</em>' containment reference.
     * @see #getAddOperation()
     * @generated
     */
    void setAddOperation(MultiReferenceAddOperation value);

    /**
     * Returns the value of the '<em><b>Remove Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Remove Operation</em>' containment reference.
     * @see #setRemoveOperation(MultiReferenceRemoveOperation)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMultiReferenceWidgetDescription_RemoveOperation()
     * @model containment="true" required="true"
     * @generated
     */
    MultiReferenceRemoveOperation getRemoveOperation();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getRemoveOperation
     * <em>Remove Operation</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Remove Operation</em>' containment reference.
     * @see #getRemoveOperation()
     * @generated
     */
    void setRemoveOperation(MultiReferenceRemoveOperation value);

    /**
     * Returns the value of the '<em><b>Clear Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Clear Operation</em>' containment reference.
     * @see #setClearOperation(ClearReferenceOperation)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMultiReferenceWidgetDescription_ClearOperation()
     * @model containment="true" required="true"
     * @generated
     */
    ClearReferenceOperation getClearOperation();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getClearOperation
     * <em>Clear Operation</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Clear Operation</em>' containment reference.
     * @see #getClearOperation()
     * @generated
     */
    void setClearOperation(ClearReferenceOperation value);

    /**
     * Returns the value of the '<em><b>Reorder Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Reorder Operation</em>' containment reference.
     * @see #setReorderOperation(MultiReferenceReorderOperation)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMultiReferenceWidgetDescription_ReorderOperation()
     * @model containment="true"
     * @generated
     */
    MultiReferenceReorderOperation getReorderOperation();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getReorderOperation
     * <em>Reorder Operation</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Reorder Operation</em>' containment reference.
     * @see #getReorderOperation()
     * @generated
     */
    void setReorderOperation(MultiReferenceReorderOperation value);

    /**
     * Returns the value of the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Style</em>' containment reference.
     * @see #setStyle(ReferenceWidgetDescriptionStyle)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMultiReferenceWidgetDescription_Style()
     * @model containment="true"
     * @generated
     */
    ReferenceWidgetDescriptionStyle getStyle();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getStyle
     * <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Style</em>' containment reference.
     * @see #getStyle()
     * @generated
     */
    void setStyle(ReferenceWidgetDescriptionStyle value);

    /**
     * Returns the value of the '<em><b>Conditional Styles</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.widgets.reference.ConditionalReferenceWidgetDescriptionStyle}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Conditional Styles</em>' containment reference list.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMultiReferenceWidgetDescription_ConditionalStyles()
     * @model containment="true"
     * @generated
     */
    EList<ConditionalReferenceWidgetDescriptionStyle> getConditionalStyles();

} // MultiReferenceWidgetDescription
