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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Mono Reference Widget Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getIsEnabledExpression
 * <em>Is Enabled Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getOwnerExpression
 * <em>Owner Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getType
 * <em>Type</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getValueExpression
 * <em>Value Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getCandidatesSearchScopeExpression
 * <em>Candidates Search Scope Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getDropdownOptionsExpression
 * <em>Dropdown Options Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getCreateElementOperation
 * <em>Create Element Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getSetOperation
 * <em>Set Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getUnsetOperation
 * <em>Unset Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getClearOperation
 * <em>Clear Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getStyle
 * <em>Style</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getConditionalStyles
 * <em>Conditional Styles</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMonoReferenceWidgetDescription()
 * @model
 * @generated
 */
public interface MonoReferenceWidgetDescription extends WidgetDescription {
    /**
     * Returns the value of the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Is Enabled Expression</em>' attribute.
     * @see #setIsEnabledExpression(String)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMonoReferenceWidgetDescription_IsEnabledExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getIsEnabledExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getIsEnabledExpression
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
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMonoReferenceWidgetDescription_OwnerExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getOwnerExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getOwnerExpression
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
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMonoReferenceWidgetDescription_Type()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getType();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getType
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
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMonoReferenceWidgetDescription_ValueExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getValueExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getValueExpression
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
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMonoReferenceWidgetDescription_CandidatesSearchScopeExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getCandidatesSearchScopeExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getCandidatesSearchScopeExpression
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
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMonoReferenceWidgetDescription_DropdownOptionsExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getDropdownOptionsExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getDropdownOptionsExpression
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
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMonoReferenceWidgetDescription_CreateElementOperation()
     * @model containment="true" required="true"
     * @generated
     */
    CreateElementInReferenceOperation getCreateElementOperation();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getCreateElementOperation
     * <em>Create Element Operation</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Create Element Operation</em>' containment reference.
     * @see #getCreateElementOperation()
     * @generated
     */
    void setCreateElementOperation(CreateElementInReferenceOperation value);

    /**
     * Returns the value of the '<em><b>Set Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Set Operation</em>' containment reference.
     * @see #setSetOperation(MonoReferenceSetOperation)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMonoReferenceWidgetDescription_SetOperation()
     * @model containment="true" required="true"
     * @generated
     */
    MonoReferenceSetOperation getSetOperation();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getSetOperation
     * <em>Set Operation</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Set Operation</em>' containment reference.
     * @see #getSetOperation()
     * @generated
     */
    void setSetOperation(MonoReferenceSetOperation value);

    /**
     * Returns the value of the '<em><b>Unset Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Unset Operation</em>' containment reference.
     * @see #setUnsetOperation(MonoReferenceUnsetOperation)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMonoReferenceWidgetDescription_UnsetOperation()
     * @model containment="true" required="true"
     * @generated
     */
    MonoReferenceUnsetOperation getUnsetOperation();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getUnsetOperation
     * <em>Unset Operation</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Unset Operation</em>' containment reference.
     * @see #getUnsetOperation()
     * @generated
     */
    void setUnsetOperation(MonoReferenceUnsetOperation value);

    /**
     * Returns the value of the '<em><b>Clear Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Clear Operation</em>' containment reference.
     * @see #setClearOperation(ClearReferenceOperation)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMonoReferenceWidgetDescription_ClearOperation()
     * @model containment="true" required="true"
     * @generated
     */
    ClearReferenceOperation getClearOperation();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getClearOperation
     * <em>Clear Operation</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Clear Operation</em>' containment reference.
     * @see #getClearOperation()
     * @generated
     */
    void setClearOperation(ClearReferenceOperation value);

    /**
     * Returns the value of the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Style</em>' containment reference.
     * @see #setStyle(ReferenceWidgetDescriptionStyle)
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMonoReferenceWidgetDescription_Style()
     * @model containment="true"
     * @generated
     */
    ReferenceWidgetDescriptionStyle getStyle();

    /**
     * Sets the value of the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getStyle
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
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#getMonoReferenceWidgetDescription_ConditionalStyles()
     * @model containment="true"
     * @generated
     */
    EList<ConditionalReferenceWidgetDescriptionStyle> getConditionalStyles();

} // MonoReferenceWidgetDescription
