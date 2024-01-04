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
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListAddOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListDeleteOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListItemActionOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListReorderOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription;
import org.eclipse.sirius.components.view.form.ConditionalListDescriptionStyle;
import org.eclipse.sirius.components.view.form.ListDescriptionStyle;
import org.eclipse.sirius.components.view.form.impl.WidgetDescriptionImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Primitive List Widget Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListWidgetDescriptionImpl#getValueExpression
 * <em>Value Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListWidgetDescriptionImpl#getDisplayExpression
 * <em>Display Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListWidgetDescriptionImpl#getCandidatesExpression
 * <em>Candidates Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListWidgetDescriptionImpl#getStyle
 * <em>Style</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListWidgetDescriptionImpl#getConditionalStyles
 * <em>Conditional Styles</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListWidgetDescriptionImpl#getIsEnabledExpression
 * <em>Is Enabled Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListWidgetDescriptionImpl#getDeleteOperation
 * <em>Delete Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListWidgetDescriptionImpl#getAddOperation
 * <em>Add Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListWidgetDescriptionImpl#getReorderOperation
 * <em>Reorder Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListWidgetDescriptionImpl#getItemActionOperation
 * <em>Item Action Operation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PrimitiveListWidgetDescriptionImpl extends WidgetDescriptionImpl implements PrimitiveListWidgetDescription {
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
     * The default value of the '{@link #getDisplayExpression() <em>Display Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDisplayExpression()
     * @generated
     * @ordered
     */
    protected static final String DISPLAY_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDisplayExpression() <em>Display Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDisplayExpression()
     * @generated
     * @ordered
     */
    protected String displayExpression = DISPLAY_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getCandidatesExpression() <em>Candidates Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getCandidatesExpression()
     * @generated
     * @ordered
     */
    protected static final String CANDIDATES_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getCandidatesExpression() <em>Candidates Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getCandidatesExpression()
     * @generated
     * @ordered
     */
    protected String candidatesExpression = CANDIDATES_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getStyle() <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getStyle()
     * @generated
     * @ordered
     */
    protected ListDescriptionStyle style;

    /**
     * The cached value of the '{@link #getConditionalStyles() <em>Conditional Styles</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getConditionalStyles()
     * @generated
     * @ordered
     */
    protected EList<ConditionalListDescriptionStyle> conditionalStyles;

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
     * The cached value of the '{@link #getDeleteOperation() <em>Delete Operation</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDeleteOperation()
     * @generated
     * @ordered
     */
    protected PrimitiveListDeleteOperation deleteOperation;

    /**
     * The cached value of the '{@link #getAddOperation() <em>Add Operation</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getAddOperation()
     * @generated
     * @ordered
     */
    protected PrimitiveListAddOperation addOperation;

    /**
     * The cached value of the '{@link #getReorderOperation() <em>Reorder Operation</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getReorderOperation()
     * @generated
     * @ordered
     */
    protected PrimitiveListReorderOperation reorderOperation;

    /**
     * The cached value of the '{@link #getItemActionOperation() <em>Item Action Operation</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getItemActionOperation()
     * @generated
     * @ordered
     */
    protected PrimitiveListItemActionOperation itemActionOperation;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected PrimitiveListWidgetDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_WIDGET_DESCRIPTION;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__VALUE_EXPRESSION, oldValueExpression, this.valueExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getDisplayExpression() {
        return this.displayExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDisplayExpression(String newDisplayExpression) {
        String oldDisplayExpression = this.displayExpression;
        this.displayExpression = newDisplayExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__DISPLAY_EXPRESSION, oldDisplayExpression, this.displayExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getCandidatesExpression() {
        return this.candidatesExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setCandidatesExpression(String newCandidatesExpression) {
        String oldCandidatesExpression = this.candidatesExpression;
        this.candidatesExpression = newCandidatesExpression;
        if (this.eNotificationRequired())
            this.eNotify(
                    new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__CANDIDATES_EXPRESSION, oldCandidatesExpression, this.candidatesExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ListDescriptionStyle getStyle() {
        return this.style;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetStyle(ListDescriptionStyle newStyle, NotificationChain msgs) {
        ListDescriptionStyle oldStyle = this.style;
        this.style = newStyle;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__STYLE, oldStyle, newStyle);
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
    public void setStyle(ListDescriptionStyle newStyle) {
        if (newStyle != this.style) {
            NotificationChain msgs = null;
            if (this.style != null)
                msgs = ((InternalEObject) this.style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__STYLE, null, msgs);
            if (newStyle != null)
                msgs = ((InternalEObject) newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__STYLE, null, msgs);
            msgs = this.basicSetStyle(newStyle, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__STYLE, newStyle, newStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ConditionalListDescriptionStyle> getConditionalStyles() {
        if (this.conditionalStyles == null) {
            this.conditionalStyles = new EObjectContainmentEList<>(ConditionalListDescriptionStyle.class, this,
                    PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__CONDITIONAL_STYLES);
        }
        return this.conditionalStyles;
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
                    new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION, oldIsEnabledExpression, this.isEnabledExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PrimitiveListDeleteOperation getDeleteOperation() {
        return this.deleteOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetDeleteOperation(PrimitiveListDeleteOperation newDeleteOperation, NotificationChain msgs) {
        PrimitiveListDeleteOperation oldDeleteOperation = this.deleteOperation;
        this.deleteOperation = newDeleteOperation;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__DELETE_OPERATION, oldDeleteOperation,
                    newDeleteOperation);
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
    public void setDeleteOperation(PrimitiveListDeleteOperation newDeleteOperation) {
        if (newDeleteOperation != this.deleteOperation) {
            NotificationChain msgs = null;
            if (this.deleteOperation != null)
                msgs = ((InternalEObject) this.deleteOperation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__DELETE_OPERATION, null, msgs);
            if (newDeleteOperation != null)
                msgs = ((InternalEObject) newDeleteOperation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__DELETE_OPERATION, null, msgs);
            msgs = this.basicSetDeleteOperation(newDeleteOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__DELETE_OPERATION, newDeleteOperation, newDeleteOperation));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PrimitiveListAddOperation getAddOperation() {
        return this.addOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetAddOperation(PrimitiveListAddOperation newAddOperation, NotificationChain msgs) {
        PrimitiveListAddOperation oldAddOperation = this.addOperation;
        this.addOperation = newAddOperation;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ADD_OPERATION, oldAddOperation, newAddOperation);
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
    public void setAddOperation(PrimitiveListAddOperation newAddOperation) {
        if (newAddOperation != this.addOperation) {
            NotificationChain msgs = null;
            if (this.addOperation != null)
                msgs = ((InternalEObject) this.addOperation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ADD_OPERATION, null, msgs);
            if (newAddOperation != null)
                msgs = ((InternalEObject) newAddOperation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ADD_OPERATION, null, msgs);
            msgs = this.basicSetAddOperation(newAddOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ADD_OPERATION, newAddOperation, newAddOperation));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PrimitiveListReorderOperation getReorderOperation() {
        return this.reorderOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetReorderOperation(PrimitiveListReorderOperation newReorderOperation, NotificationChain msgs) {
        PrimitiveListReorderOperation oldReorderOperation = this.reorderOperation;
        this.reorderOperation = newReorderOperation;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__REORDER_OPERATION, oldReorderOperation,
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
    public void setReorderOperation(PrimitiveListReorderOperation newReorderOperation) {
        if (newReorderOperation != this.reorderOperation) {
            NotificationChain msgs = null;
            if (this.reorderOperation != null)
                msgs = ((InternalEObject) this.reorderOperation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__REORDER_OPERATION, null, msgs);
            if (newReorderOperation != null)
                msgs = ((InternalEObject) newReorderOperation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__REORDER_OPERATION, null, msgs);
            msgs = this.basicSetReorderOperation(newReorderOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__REORDER_OPERATION, newReorderOperation, newReorderOperation));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PrimitiveListItemActionOperation getItemActionOperation() {
        return this.itemActionOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetItemActionOperation(PrimitiveListItemActionOperation newItemActionOperation, NotificationChain msgs) {
        PrimitiveListItemActionOperation oldItemActionOperation = this.itemActionOperation;
        this.itemActionOperation = newItemActionOperation;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ITEM_ACTION_OPERATION, oldItemActionOperation,
                    newItemActionOperation);
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
    public void setItemActionOperation(PrimitiveListItemActionOperation newItemActionOperation) {
        if (newItemActionOperation != this.itemActionOperation) {
            NotificationChain msgs = null;
            if (this.itemActionOperation != null)
                msgs = ((InternalEObject) this.itemActionOperation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ITEM_ACTION_OPERATION, null,
                        msgs);
            if (newItemActionOperation != null)
                msgs = ((InternalEObject) newItemActionOperation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ITEM_ACTION_OPERATION, null,
                        msgs);
            msgs = this.basicSetItemActionOperation(newItemActionOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ITEM_ACTION_OPERATION, newItemActionOperation, newItemActionOperation));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__STYLE:
                return this.basicSetStyle(null, msgs);
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
                return ((InternalEList<?>) this.getConditionalStyles()).basicRemove(otherEnd, msgs);
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__DELETE_OPERATION:
                return this.basicSetDeleteOperation(null, msgs);
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ADD_OPERATION:
                return this.basicSetAddOperation(null, msgs);
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__REORDER_OPERATION:
                return this.basicSetReorderOperation(null, msgs);
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ITEM_ACTION_OPERATION:
                return this.basicSetItemActionOperation(null, msgs);
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
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__VALUE_EXPRESSION:
                return this.getValueExpression();
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__DISPLAY_EXPRESSION:
                return this.getDisplayExpression();
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__CANDIDATES_EXPRESSION:
                return this.getCandidatesExpression();
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__STYLE:
                return this.getStyle();
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
                return this.getConditionalStyles();
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                return this.getIsEnabledExpression();
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__DELETE_OPERATION:
                return this.getDeleteOperation();
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ADD_OPERATION:
                return this.getAddOperation();
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__REORDER_OPERATION:
                return this.getReorderOperation();
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ITEM_ACTION_OPERATION:
                return this.getItemActionOperation();
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
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__VALUE_EXPRESSION:
                this.setValueExpression((String) newValue);
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__DISPLAY_EXPRESSION:
                this.setDisplayExpression((String) newValue);
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__CANDIDATES_EXPRESSION:
                this.setCandidatesExpression((String) newValue);
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__STYLE:
                this.setStyle((ListDescriptionStyle) newValue);
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
                this.getConditionalStyles().clear();
                this.getConditionalStyles().addAll((Collection<? extends ConditionalListDescriptionStyle>) newValue);
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.setIsEnabledExpression((String) newValue);
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__DELETE_OPERATION:
                this.setDeleteOperation((PrimitiveListDeleteOperation) newValue);
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ADD_OPERATION:
                this.setAddOperation((PrimitiveListAddOperation) newValue);
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__REORDER_OPERATION:
                this.setReorderOperation((PrimitiveListReorderOperation) newValue);
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ITEM_ACTION_OPERATION:
                this.setItemActionOperation((PrimitiveListItemActionOperation) newValue);
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
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__VALUE_EXPRESSION:
                this.setValueExpression(VALUE_EXPRESSION_EDEFAULT);
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__DISPLAY_EXPRESSION:
                this.setDisplayExpression(DISPLAY_EXPRESSION_EDEFAULT);
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__CANDIDATES_EXPRESSION:
                this.setCandidatesExpression(CANDIDATES_EXPRESSION_EDEFAULT);
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__STYLE:
                this.setStyle((ListDescriptionStyle) null);
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
                this.getConditionalStyles().clear();
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.setIsEnabledExpression(IS_ENABLED_EXPRESSION_EDEFAULT);
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__DELETE_OPERATION:
                this.setDeleteOperation((PrimitiveListDeleteOperation) null);
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ADD_OPERATION:
                this.setAddOperation((PrimitiveListAddOperation) null);
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__REORDER_OPERATION:
                this.setReorderOperation((PrimitiveListReorderOperation) null);
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ITEM_ACTION_OPERATION:
                this.setItemActionOperation((PrimitiveListItemActionOperation) null);
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
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__VALUE_EXPRESSION:
                return VALUE_EXPRESSION_EDEFAULT == null ? this.valueExpression != null : !VALUE_EXPRESSION_EDEFAULT.equals(this.valueExpression);
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__DISPLAY_EXPRESSION:
                return DISPLAY_EXPRESSION_EDEFAULT == null ? this.displayExpression != null : !DISPLAY_EXPRESSION_EDEFAULT.equals(this.displayExpression);
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__CANDIDATES_EXPRESSION:
                return CANDIDATES_EXPRESSION_EDEFAULT == null ? this.candidatesExpression != null : !CANDIDATES_EXPRESSION_EDEFAULT.equals(this.candidatesExpression);
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__STYLE:
                return this.style != null;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
                return this.conditionalStyles != null && !this.conditionalStyles.isEmpty();
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                return IS_ENABLED_EXPRESSION_EDEFAULT == null ? this.isEnabledExpression != null : !IS_ENABLED_EXPRESSION_EDEFAULT.equals(this.isEnabledExpression);
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__DELETE_OPERATION:
                return this.deleteOperation != null;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ADD_OPERATION:
                return this.addOperation != null;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__REORDER_OPERATION:
                return this.reorderOperation != null;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ITEM_ACTION_OPERATION:
                return this.itemActionOperation != null;
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
        result.append(" (valueExpression: ");
        result.append(this.valueExpression);
        result.append(", displayExpression: ");
        result.append(this.displayExpression);
        result.append(", candidatesExpression: ");
        result.append(this.candidatesExpression);
        result.append(", isEnabledExpression: ");
        result.append(this.isEnabledExpression);
        result.append(')');
        return result.toString();
    }

} // PrimitiveListWidgetDescriptionImpl
