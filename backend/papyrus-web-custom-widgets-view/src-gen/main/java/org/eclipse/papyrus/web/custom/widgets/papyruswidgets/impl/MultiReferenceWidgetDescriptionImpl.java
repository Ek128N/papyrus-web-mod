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
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceAddOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceRemoveOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceReorderOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage;
import org.eclipse.sirius.components.view.form.impl.WidgetDescriptionImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Multi Reference Widget Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceWidgetDescriptionImpl#getIsEnabledExpression
 * <em>Is Enabled Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceWidgetDescriptionImpl#getOwnerExpression
 * <em>Owner Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceWidgetDescriptionImpl#getType
 * <em>Type</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceWidgetDescriptionImpl#getValueExpression
 * <em>Value Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceWidgetDescriptionImpl#getCandidatesSearchScopeExpression
 * <em>Candidates Search Scope Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceWidgetDescriptionImpl#getDropdownOptionsExpression
 * <em>Dropdown Options Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceWidgetDescriptionImpl#getCreateElementOperation
 * <em>Create Element Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceWidgetDescriptionImpl#getAddOperation
 * <em>Add Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceWidgetDescriptionImpl#getRemoveOperation
 * <em>Remove Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceWidgetDescriptionImpl#getClearOperation
 * <em>Clear Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceWidgetDescriptionImpl#getReorderOperation
 * <em>Reorder Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceWidgetDescriptionImpl#getStyle
 * <em>Style</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceWidgetDescriptionImpl#getConditionalStyles
 * <em>Conditional Styles</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MultiReferenceWidgetDescriptionImpl extends WidgetDescriptionImpl implements MultiReferenceWidgetDescription {
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
     * The cached value of the '{@link #getAddOperation() <em>Add Operation</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getAddOperation()
     * @generated
     * @ordered
     */
    protected MultiReferenceAddOperation addOperation;

    /**
     * The cached value of the '{@link #getRemoveOperation() <em>Remove Operation</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getRemoveOperation()
     * @generated
     * @ordered
     */
    protected MultiReferenceRemoveOperation removeOperation;

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
     * The cached value of the '{@link #getReorderOperation() <em>Reorder Operation</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getReorderOperation()
     * @generated
     * @ordered
     */
    protected MultiReferenceReorderOperation reorderOperation;

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
    protected MultiReferenceWidgetDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapyrusWidgetsPackage.Literals.MULTI_REFERENCE_WIDGET_DESCRIPTION;
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
                    new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION, oldIsEnabledExpression, this.isEnabledExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION, oldOwnerExpression, this.ownerExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__TYPE, oldType, this.type));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION, oldValueExpression, this.valueExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CANDIDATES_SEARCH_SCOPE_EXPRESSION, oldCandidatesSearchScopeExpression,
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__DROPDOWN_OPTIONS_EXPRESSION, oldDropdownOptionsExpression,
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION,
                    oldCreateElementOperation, newCreateElementOperation);
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
                msgs = ((InternalEObject) this.createElementOperation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION,
                        null, msgs);
            if (newCreateElementOperation != null)
                msgs = ((InternalEObject) newCreateElementOperation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION,
                        null, msgs);
            msgs = this.basicSetCreateElementOperation(newCreateElementOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION, newCreateElementOperation,
                    newCreateElementOperation));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public MultiReferenceAddOperation getAddOperation() {
        return this.addOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetAddOperation(MultiReferenceAddOperation newAddOperation, NotificationChain msgs) {
        MultiReferenceAddOperation oldAddOperation = this.addOperation;
        this.addOperation = newAddOperation;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__ADD_OPERATION, oldAddOperation, newAddOperation);
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
    public void setAddOperation(MultiReferenceAddOperation newAddOperation) {
        if (newAddOperation != this.addOperation) {
            NotificationChain msgs = null;
            if (this.addOperation != null)
                msgs = ((InternalEObject) this.addOperation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__ADD_OPERATION, null, msgs);
            if (newAddOperation != null)
                msgs = ((InternalEObject) newAddOperation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__ADD_OPERATION, null, msgs);
            msgs = this.basicSetAddOperation(newAddOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__ADD_OPERATION, newAddOperation, newAddOperation));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public MultiReferenceRemoveOperation getRemoveOperation() {
        return this.removeOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetRemoveOperation(MultiReferenceRemoveOperation newRemoveOperation, NotificationChain msgs) {
        MultiReferenceRemoveOperation oldRemoveOperation = this.removeOperation;
        this.removeOperation = newRemoveOperation;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION, oldRemoveOperation,
                    newRemoveOperation);
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
    public void setRemoveOperation(MultiReferenceRemoveOperation newRemoveOperation) {
        if (newRemoveOperation != this.removeOperation) {
            NotificationChain msgs = null;
            if (this.removeOperation != null)
                msgs = ((InternalEObject) this.removeOperation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION, null, msgs);
            if (newRemoveOperation != null)
                msgs = ((InternalEObject) newRemoveOperation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION, null, msgs);
            msgs = this.basicSetRemoveOperation(newRemoveOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION, newRemoveOperation, newRemoveOperation));
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION, oldClearOperation,
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
                msgs = ((InternalEObject) this.clearOperation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION, null, msgs);
            if (newClearOperation != null)
                msgs = ((InternalEObject) newClearOperation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION, null, msgs);
            msgs = this.basicSetClearOperation(newClearOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION, newClearOperation, newClearOperation));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public MultiReferenceReorderOperation getReorderOperation() {
        return this.reorderOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetReorderOperation(MultiReferenceReorderOperation newReorderOperation, NotificationChain msgs) {
        MultiReferenceReorderOperation oldReorderOperation = this.reorderOperation;
        this.reorderOperation = newReorderOperation;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION, oldReorderOperation,
                    newReorderOperation);
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
    public void setReorderOperation(MultiReferenceReorderOperation newReorderOperation) {
        if (newReorderOperation != this.reorderOperation) {
            NotificationChain msgs = null;
            if (this.reorderOperation != null)
                msgs = ((InternalEObject) this.reorderOperation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION, null, msgs);
            if (newReorderOperation != null)
                msgs = ((InternalEObject) newReorderOperation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION, null, msgs);
            msgs = this.basicSetReorderOperation(newReorderOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION, newReorderOperation, newReorderOperation));
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__STYLE, oldStyle, newStyle);
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
                msgs = ((InternalEObject) this.style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__STYLE, null, msgs);
            if (newStyle != null)
                msgs = ((InternalEObject) newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__STYLE, null, msgs);
            msgs = this.basicSetStyle(newStyle, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__STYLE, newStyle, newStyle));
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
                    PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES);
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
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION:
                return this.basicSetCreateElementOperation(null, msgs);
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__ADD_OPERATION:
                return this.basicSetAddOperation(null, msgs);
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION:
                return this.basicSetRemoveOperation(null, msgs);
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION:
                return this.basicSetClearOperation(null, msgs);
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION:
                return this.basicSetReorderOperation(null, msgs);
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__STYLE:
                return this.basicSetStyle(null, msgs);
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
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
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                return this.getIsEnabledExpression();
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION:
                return this.getOwnerExpression();
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__TYPE:
                return this.getType();
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION:
                return this.getValueExpression();
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CANDIDATES_SEARCH_SCOPE_EXPRESSION:
                return this.getCandidatesSearchScopeExpression();
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__DROPDOWN_OPTIONS_EXPRESSION:
                return this.getDropdownOptionsExpression();
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION:
                return this.getCreateElementOperation();
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__ADD_OPERATION:
                return this.getAddOperation();
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION:
                return this.getRemoveOperation();
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION:
                return this.getClearOperation();
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION:
                return this.getReorderOperation();
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__STYLE:
                return this.getStyle();
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
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
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.setIsEnabledExpression((String) newValue);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION:
                this.setOwnerExpression((String) newValue);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__TYPE:
                this.setType((String) newValue);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION:
                this.setValueExpression((String) newValue);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CANDIDATES_SEARCH_SCOPE_EXPRESSION:
                this.setCandidatesSearchScopeExpression((String) newValue);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__DROPDOWN_OPTIONS_EXPRESSION:
                this.setDropdownOptionsExpression((String) newValue);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION:
                this.setCreateElementOperation((CreateElementInReferenceOperation) newValue);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__ADD_OPERATION:
                this.setAddOperation((MultiReferenceAddOperation) newValue);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION:
                this.setRemoveOperation((MultiReferenceRemoveOperation) newValue);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION:
                this.setClearOperation((ClearReferenceOperation) newValue);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION:
                this.setReorderOperation((MultiReferenceReorderOperation) newValue);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__STYLE:
                this.setStyle((org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescriptionStyle) newValue);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
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
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.setIsEnabledExpression(IS_ENABLED_EXPRESSION_EDEFAULT);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION:
                this.setOwnerExpression(OWNER_EXPRESSION_EDEFAULT);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__TYPE:
                this.setType(TYPE_EDEFAULT);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION:
                this.setValueExpression(VALUE_EXPRESSION_EDEFAULT);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CANDIDATES_SEARCH_SCOPE_EXPRESSION:
                this.setCandidatesSearchScopeExpression(CANDIDATES_SEARCH_SCOPE_EXPRESSION_EDEFAULT);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__DROPDOWN_OPTIONS_EXPRESSION:
                this.setDropdownOptionsExpression(DROPDOWN_OPTIONS_EXPRESSION_EDEFAULT);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION:
                this.setCreateElementOperation((CreateElementInReferenceOperation) null);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__ADD_OPERATION:
                this.setAddOperation((MultiReferenceAddOperation) null);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION:
                this.setRemoveOperation((MultiReferenceRemoveOperation) null);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION:
                this.setClearOperation((ClearReferenceOperation) null);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION:
                this.setReorderOperation((MultiReferenceReorderOperation) null);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__STYLE:
                this.setStyle((org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescriptionStyle) null);
                return;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
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
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                return IS_ENABLED_EXPRESSION_EDEFAULT == null ? this.isEnabledExpression != null : !IS_ENABLED_EXPRESSION_EDEFAULT.equals(this.isEnabledExpression);
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION:
                return OWNER_EXPRESSION_EDEFAULT == null ? this.ownerExpression != null : !OWNER_EXPRESSION_EDEFAULT.equals(this.ownerExpression);
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__TYPE:
                return TYPE_EDEFAULT == null ? this.type != null : !TYPE_EDEFAULT.equals(this.type);
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION:
                return VALUE_EXPRESSION_EDEFAULT == null ? this.valueExpression != null : !VALUE_EXPRESSION_EDEFAULT.equals(this.valueExpression);
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CANDIDATES_SEARCH_SCOPE_EXPRESSION:
                return CANDIDATES_SEARCH_SCOPE_EXPRESSION_EDEFAULT == null ? this.candidatesSearchScopeExpression != null
                        : !CANDIDATES_SEARCH_SCOPE_EXPRESSION_EDEFAULT.equals(this.candidatesSearchScopeExpression);
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__DROPDOWN_OPTIONS_EXPRESSION:
                return DROPDOWN_OPTIONS_EXPRESSION_EDEFAULT == null ? this.dropdownOptionsExpression != null : !DROPDOWN_OPTIONS_EXPRESSION_EDEFAULT.equals(this.dropdownOptionsExpression);
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION:
                return this.createElementOperation != null;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__ADD_OPERATION:
                return this.addOperation != null;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION:
                return this.removeOperation != null;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION:
                return this.clearOperation != null;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION:
                return this.reorderOperation != null;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__STYLE:
                return this.style != null;
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
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

} // MultiReferenceWidgetDescriptionImpl
