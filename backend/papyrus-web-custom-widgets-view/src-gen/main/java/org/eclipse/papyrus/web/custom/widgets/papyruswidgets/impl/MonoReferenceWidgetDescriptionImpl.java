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
package org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ClearReferenceOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CreateElementInReferenceOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceSetOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceUnsetOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage;
import org.eclipse.sirius.components.view.form.impl.WidgetDescriptionImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Mono Reference Widget Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceWidgetDescriptionImpl#getIsEnabledExpression
 * <em>Is Enabled Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceWidgetDescriptionImpl#getOwnerExpression
 * <em>Owner Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceWidgetDescriptionImpl#getType
 * <em>Type</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceWidgetDescriptionImpl#getValueExpression
 * <em>Value Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceWidgetDescriptionImpl#getCandidatesSearchScopeExpression
 * <em>Candidates Search Scope Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceWidgetDescriptionImpl#getDropdownOptionsExpression
 * <em>Dropdown Options Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceWidgetDescriptionImpl#getCreateElementOperation
 * <em>Create Element Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceWidgetDescriptionImpl#getSetOperation
 * <em>Set Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceWidgetDescriptionImpl#getUnsetOperation
 * <em>Unset Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceWidgetDescriptionImpl#getClearOperation
 * <em>Clear Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceWidgetDescriptionImpl#getStyle
 * <em>Style</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceWidgetDescriptionImpl#getConditionalStyles
 * <em>Conditional Styles</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MonoReferenceWidgetDescriptionImpl extends WidgetDescriptionImpl implements MonoReferenceWidgetDescription {
    /**
     * The default value of the '{@link #getIsEnabledExpression() <em>Is Enabled Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsEnabledExpression()
     * @generated
     * @ordered
     */
    protected static final String IS_ENABLED_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIsEnabledExpression() <em>Is Enabled Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsEnabledExpression()
     * @generated
     * @ordered
     */
    protected String isEnabledExpression = IS_ENABLED_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getOwnerExpression() <em>Owner Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getOwnerExpression()
     * @generated
     * @ordered
     */
    protected static final String OWNER_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOwnerExpression() <em>Owner Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getOwnerExpression()
     * @generated
     * @ordered
     */
    protected String ownerExpression = OWNER_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getType() <em>Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getType()
     * @generated
     * @ordered
     */
    protected static final String TYPE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getType() <em>Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getType()
     * @generated
     * @ordered
     */
    protected String type = TYPE_EDEFAULT;

    /**
     * The default value of the '{@link #getValueExpression() <em>Value Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getValueExpression()
     * @generated
     * @ordered
     */
    protected static final String VALUE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getValueExpression() <em>Value Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getValueExpression()
     * @generated
     * @ordered
     */
    protected String valueExpression = VALUE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getCandidatesSearchScopeExpression() <em>Candidates Search Scope
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getCandidatesSearchScopeExpression()
     * @generated
     * @ordered
     */
    protected static final String CANDIDATES_SEARCH_SCOPE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getCandidatesSearchScopeExpression() <em>Candidates Search Scope
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getCandidatesSearchScopeExpression()
     * @generated
     * @ordered
     */
    protected String candidatesSearchScopeExpression = CANDIDATES_SEARCH_SCOPE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getDropdownOptionsExpression() <em>Dropdown Options Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDropdownOptionsExpression()
     * @generated
     * @ordered
     */
    protected static final String DROPDOWN_OPTIONS_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDropdownOptionsExpression() <em>Dropdown Options Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDropdownOptionsExpression()
     * @generated
     * @ordered
     */
    protected String dropdownOptionsExpression = DROPDOWN_OPTIONS_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getCreateElementOperation() <em>Create Element Operation</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getCreateElementOperation()
     * @generated
     * @ordered
     */
    protected CreateElementInReferenceOperation createElementOperation;

    /**
     * The cached value of the '{@link #getSetOperation() <em>Set Operation</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSetOperation()
     * @generated
     * @ordered
     */
    protected MonoReferenceSetOperation setOperation;

    /**
     * The cached value of the '{@link #getUnsetOperation() <em>Unset Operation</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getUnsetOperation()
     * @generated
     * @ordered
     */
    protected MonoReferenceUnsetOperation unsetOperation;

    /**
     * The cached value of the '{@link #getClearOperation() <em>Clear Operation</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getClearOperation()
     * @generated
     * @ordered
     */
    protected ClearReferenceOperation clearOperation;

    /**
     * The cached value of the '{@link #getStyle() <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getStyle()
     * @generated
     * @ordered
     */
    protected org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescriptionStyle style;

    /**
     * The cached value of the '{@link #getConditionalStyles() <em>Conditional Styles</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getConditionalStyles()
     * @generated
     * @ordered
     */
    protected EList<org.eclipse.sirius.components.view.widget.reference.ConditionalReferenceWidgetDescriptionStyle> conditionalStyles;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected MonoReferenceWidgetDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapyrusWidgetsPackage.Literals.MONO_REFERENCE_WIDGET_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getIsEnabledExpression() {
        return this.isEnabledExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIsEnabledExpression(String newIsEnabledExpression) {
        String oldIsEnabledExpression = this.isEnabledExpression;
        this.isEnabledExpression = newIsEnabledExpression;
        if (this.eNotificationRequired())
            this.eNotify(
                    new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION, oldIsEnabledExpression, this.isEnabledExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getOwnerExpression() {
        return this.ownerExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setOwnerExpression(String newOwnerExpression) {
        String oldOwnerExpression = this.ownerExpression;
        this.ownerExpression = newOwnerExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION, oldOwnerExpression, this.ownerExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getType() {
        return this.type;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setType(String newType) {
        String oldType = this.type;
        this.type = newType;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__TYPE, oldType, this.type));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getValueExpression() {
        return this.valueExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setValueExpression(String newValueExpression) {
        String oldValueExpression = this.valueExpression;
        this.valueExpression = newValueExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION, oldValueExpression, this.valueExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getCandidatesSearchScopeExpression() {
        return this.candidatesSearchScopeExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setCandidatesSearchScopeExpression(String newCandidatesSearchScopeExpression) {
        String oldCandidatesSearchScopeExpression = this.candidatesSearchScopeExpression;
        this.candidatesSearchScopeExpression = newCandidatesSearchScopeExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CANDIDATES_SEARCH_SCOPE_EXPRESSION, oldCandidatesSearchScopeExpression,
                    this.candidatesSearchScopeExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getDropdownOptionsExpression() {
        return this.dropdownOptionsExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDropdownOptionsExpression(String newDropdownOptionsExpression) {
        String oldDropdownOptionsExpression = this.dropdownOptionsExpression;
        this.dropdownOptionsExpression = newDropdownOptionsExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__DROPDOWN_OPTIONS_EXPRESSION, oldDropdownOptionsExpression,
                    this.dropdownOptionsExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CreateElementInReferenceOperation getCreateElementOperation() {
        return this.createElementOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetCreateElementOperation(CreateElementInReferenceOperation newCreateElementOperation, NotificationChain msgs) {
        CreateElementInReferenceOperation oldCreateElementOperation = this.createElementOperation;
        this.createElementOperation = newCreateElementOperation;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION, oldCreateElementOperation,
                    newCreateElementOperation);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setCreateElementOperation(CreateElementInReferenceOperation newCreateElementOperation) {
        if (newCreateElementOperation != this.createElementOperation) {
            NotificationChain msgs = null;
            if (this.createElementOperation != null)
                msgs = ((InternalEObject) this.createElementOperation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION,
                        null,
                        msgs);
            if (newCreateElementOperation != null)
                msgs = ((InternalEObject) newCreateElementOperation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION, null,
                        msgs);
            msgs = this.basicSetCreateElementOperation(newCreateElementOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION, newCreateElementOperation,
                    newCreateElementOperation));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public MonoReferenceSetOperation getSetOperation() {
        return this.setOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetSetOperation(MonoReferenceSetOperation newSetOperation, NotificationChain msgs) {
        MonoReferenceSetOperation oldSetOperation = this.setOperation;
        this.setOperation = newSetOperation;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__SET_OPERATION, oldSetOperation, newSetOperation);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSetOperation(MonoReferenceSetOperation newSetOperation) {
        if (newSetOperation != this.setOperation) {
            NotificationChain msgs = null;
            if (this.setOperation != null)
                msgs = ((InternalEObject) this.setOperation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__SET_OPERATION, null, msgs);
            if (newSetOperation != null)
                msgs = ((InternalEObject) newSetOperation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__SET_OPERATION, null, msgs);
            msgs = this.basicSetSetOperation(newSetOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__SET_OPERATION, newSetOperation, newSetOperation));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public MonoReferenceUnsetOperation getUnsetOperation() {
        return this.unsetOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetUnsetOperation(MonoReferenceUnsetOperation newUnsetOperation, NotificationChain msgs) {
        MonoReferenceUnsetOperation oldUnsetOperation = this.unsetOperation;
        this.unsetOperation = newUnsetOperation;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__UNSET_OPERATION, oldUnsetOperation,
                    newUnsetOperation);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setUnsetOperation(MonoReferenceUnsetOperation newUnsetOperation) {
        if (newUnsetOperation != this.unsetOperation) {
            NotificationChain msgs = null;
            if (this.unsetOperation != null)
                msgs = ((InternalEObject) this.unsetOperation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__UNSET_OPERATION, null, msgs);
            if (newUnsetOperation != null)
                msgs = ((InternalEObject) newUnsetOperation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__UNSET_OPERATION, null, msgs);
            msgs = this.basicSetUnsetOperation(newUnsetOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__UNSET_OPERATION, newUnsetOperation, newUnsetOperation));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ClearReferenceOperation getClearOperation() {
        return this.clearOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetClearOperation(ClearReferenceOperation newClearOperation, NotificationChain msgs) {
        ClearReferenceOperation oldClearOperation = this.clearOperation;
        this.clearOperation = newClearOperation;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION, oldClearOperation,
                    newClearOperation);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setClearOperation(ClearReferenceOperation newClearOperation) {
        if (newClearOperation != this.clearOperation) {
            NotificationChain msgs = null;
            if (this.clearOperation != null)
                msgs = ((InternalEObject) this.clearOperation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION, null, msgs);
            if (newClearOperation != null)
                msgs = ((InternalEObject) newClearOperation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION, null, msgs);
            msgs = this.basicSetClearOperation(newClearOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION, newClearOperation, newClearOperation));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescriptionStyle getStyle() {
        return this.style;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetStyle(org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescriptionStyle newStyle, NotificationChain msgs) {
        org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescriptionStyle oldStyle = this.style;
        this.style = newStyle;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__STYLE, oldStyle, newStyle);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setStyle(org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescriptionStyle newStyle) {
        if (newStyle != this.style) {
            NotificationChain msgs = null;
            if (this.style != null)
                msgs = ((InternalEObject) this.style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__STYLE, null, msgs);
            if (newStyle != null)
                msgs = ((InternalEObject) newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__STYLE, null, msgs);
            msgs = this.basicSetStyle(newStyle, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__STYLE, newStyle, newStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<org.eclipse.sirius.components.view.widget.reference.ConditionalReferenceWidgetDescriptionStyle> getConditionalStyles() {
        if (this.conditionalStyles == null) {
            this.conditionalStyles = new EObjectContainmentEList<org.eclipse.sirius.components.view.widget.reference.ConditionalReferenceWidgetDescriptionStyle>(
                    org.eclipse.sirius.components.view.widget.reference.ConditionalReferenceWidgetDescriptionStyle.class, this,
                    PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES);
        }
        return this.conditionalStyles;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION:
                return this.basicSetCreateElementOperation(null, msgs);
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__SET_OPERATION:
                return this.basicSetSetOperation(null, msgs);
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__UNSET_OPERATION:
                return this.basicSetUnsetOperation(null, msgs);
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION:
                return this.basicSetClearOperation(null, msgs);
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__STYLE:
                return this.basicSetStyle(null, msgs);
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
                return ((InternalEList<?>) this.getConditionalStyles()).basicRemove(otherEnd, msgs);
            default:
                return super.eInverseRemove(otherEnd, featureID, msgs);
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                return this.getIsEnabledExpression();
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION:
                return this.getOwnerExpression();
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__TYPE:
                return this.getType();
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION:
                return this.getValueExpression();
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CANDIDATES_SEARCH_SCOPE_EXPRESSION:
                return this.getCandidatesSearchScopeExpression();
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__DROPDOWN_OPTIONS_EXPRESSION:
                return this.getDropdownOptionsExpression();
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION:
                return this.getCreateElementOperation();
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__SET_OPERATION:
                return this.getSetOperation();
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__UNSET_OPERATION:
                return this.getUnsetOperation();
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION:
                return this.getClearOperation();
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__STYLE:
                return this.getStyle();
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
                return this.getConditionalStyles();
            default:
                return super.eGet(featureID, resolve, coreType);
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.setIsEnabledExpression((String) newValue);
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION:
                this.setOwnerExpression((String) newValue);
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__TYPE:
                this.setType((String) newValue);
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION:
                this.setValueExpression((String) newValue);
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CANDIDATES_SEARCH_SCOPE_EXPRESSION:
                this.setCandidatesSearchScopeExpression((String) newValue);
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__DROPDOWN_OPTIONS_EXPRESSION:
                this.setDropdownOptionsExpression((String) newValue);
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION:
                this.setCreateElementOperation((CreateElementInReferenceOperation) newValue);
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__SET_OPERATION:
                this.setSetOperation((MonoReferenceSetOperation) newValue);
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__UNSET_OPERATION:
                this.setUnsetOperation((MonoReferenceUnsetOperation) newValue);
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION:
                this.setClearOperation((ClearReferenceOperation) newValue);
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__STYLE:
                this.setStyle((org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescriptionStyle) newValue);
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
                this.getConditionalStyles().clear();
                this.getConditionalStyles().addAll((Collection<? extends org.eclipse.sirius.components.view.widget.reference.ConditionalReferenceWidgetDescriptionStyle>) newValue);
                return;
            default:
                super.eSet(featureID, newValue);
                return;
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.setIsEnabledExpression(IS_ENABLED_EXPRESSION_EDEFAULT);
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION:
                this.setOwnerExpression(OWNER_EXPRESSION_EDEFAULT);
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__TYPE:
                this.setType(TYPE_EDEFAULT);
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION:
                this.setValueExpression(VALUE_EXPRESSION_EDEFAULT);
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CANDIDATES_SEARCH_SCOPE_EXPRESSION:
                this.setCandidatesSearchScopeExpression(CANDIDATES_SEARCH_SCOPE_EXPRESSION_EDEFAULT);
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__DROPDOWN_OPTIONS_EXPRESSION:
                this.setDropdownOptionsExpression(DROPDOWN_OPTIONS_EXPRESSION_EDEFAULT);
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION:
                this.setCreateElementOperation((CreateElementInReferenceOperation) null);
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__SET_OPERATION:
                this.setSetOperation((MonoReferenceSetOperation) null);
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__UNSET_OPERATION:
                this.setUnsetOperation((MonoReferenceUnsetOperation) null);
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION:
                this.setClearOperation((ClearReferenceOperation) null);
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__STYLE:
                this.setStyle((org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescriptionStyle) null);
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
                this.getConditionalStyles().clear();
                return;
            default:
                super.eUnset(featureID);
                return;
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                return IS_ENABLED_EXPRESSION_EDEFAULT == null ? this.isEnabledExpression != null : !IS_ENABLED_EXPRESSION_EDEFAULT.equals(this.isEnabledExpression);
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION:
                return OWNER_EXPRESSION_EDEFAULT == null ? this.ownerExpression != null : !OWNER_EXPRESSION_EDEFAULT.equals(this.ownerExpression);
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__TYPE:
                return TYPE_EDEFAULT == null ? this.type != null : !TYPE_EDEFAULT.equals(this.type);
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION:
                return VALUE_EXPRESSION_EDEFAULT == null ? this.valueExpression != null : !VALUE_EXPRESSION_EDEFAULT.equals(this.valueExpression);
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CANDIDATES_SEARCH_SCOPE_EXPRESSION:
                return CANDIDATES_SEARCH_SCOPE_EXPRESSION_EDEFAULT == null ? this.candidatesSearchScopeExpression != null
                        : !CANDIDATES_SEARCH_SCOPE_EXPRESSION_EDEFAULT.equals(this.candidatesSearchScopeExpression);
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__DROPDOWN_OPTIONS_EXPRESSION:
                return DROPDOWN_OPTIONS_EXPRESSION_EDEFAULT == null ? this.dropdownOptionsExpression != null : !DROPDOWN_OPTIONS_EXPRESSION_EDEFAULT.equals(this.dropdownOptionsExpression);
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION:
                return this.createElementOperation != null;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__SET_OPERATION:
                return this.setOperation != null;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__UNSET_OPERATION:
                return this.unsetOperation != null;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION:
                return this.clearOperation != null;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__STYLE:
                return this.style != null;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
                return this.conditionalStyles != null && !this.conditionalStyles.isEmpty();
            default:
                return super.eIsSet(featureID);
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        if (this.eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (isEnabledExpression: ");
        result.append(this.isEnabledExpression);
        result.append(", ownerExpression: ");
        result.append(this.ownerExpression);
        result.append(", type: ");
        result.append(this.type);
        result.append(", valueExpression: ");
        result.append(this.valueExpression);
        result.append(", candidatesSearchScopeExpression: ");
        result.append(this.candidatesSearchScopeExpression);
        result.append(", dropdownOptionsExpression: ");
        result.append(this.dropdownOptionsExpression);
        result.append(')');
        return result.toString();
    }

} // MonoReferenceWidgetDescriptionImpl
