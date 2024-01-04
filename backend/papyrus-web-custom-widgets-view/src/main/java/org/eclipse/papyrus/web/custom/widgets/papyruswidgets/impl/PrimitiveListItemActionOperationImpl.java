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
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListItemActionOperation;
import org.eclipse.sirius.components.view.Operation;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Primitive List Item Action Operation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListItemActionOperationImpl#getBody
 * <em>Body</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListItemActionOperationImpl#getIconURLExpression
 * <em>Icon URL Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListItemActionOperationImpl#getPreconditionExpression
 * <em>Precondition Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PrimitiveListItemActionOperationImpl extends MinimalEObjectImpl.Container implements PrimitiveListItemActionOperation {
    /**
     * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getBody()
     * @generated
     * @ordered
     */
    protected EList<Operation> body;

    /**
     * The default value of the '{@link #getIconURLExpression() <em>Icon URL Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIconURLExpression()
     * @generated
     * @ordered
     */
    protected static final String ICON_URL_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIconURLExpression() <em>Icon URL Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIconURLExpression()
     * @generated
     * @ordered
     */
    protected String iconURLExpression = ICON_URL_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getPreconditionExpression() <em>Precondition Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getPreconditionExpression()
     * @generated
     * @ordered
     */
    protected static final String PRECONDITION_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPreconditionExpression() <em>Precondition Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getPreconditionExpression()
     * @generated
     * @ordered
     */
    protected String preconditionExpression = PRECONDITION_EXPRESSION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected PrimitiveListItemActionOperationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_ITEM_ACTION_OPERATION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Operation> getBody() {
        if (this.body == null) {
            this.body = new EObjectContainmentEList<>(Operation.class, this, PapyrusWidgetsPackage.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__BODY);
        }
        return this.body;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getIconURLExpression() {
        return this.iconURLExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIconURLExpression(String newIconURLExpression) {
        String oldIconURLExpression = this.iconURLExpression;
        this.iconURLExpression = newIconURLExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__ICON_URL_EXPRESSION, oldIconURLExpression, this.iconURLExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getPreconditionExpression() {
        return this.preconditionExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setPreconditionExpression(String newPreconditionExpression) {
        String oldPreconditionExpression = this.preconditionExpression;
        this.preconditionExpression = newPreconditionExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__PRECONDITION_EXPRESSION, oldPreconditionExpression,
                    this.preconditionExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__BODY:
                return ((InternalEList<?>) this.getBody()).basicRemove(otherEnd, msgs);
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
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__BODY:
                return this.getBody();
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__ICON_URL_EXPRESSION:
                return this.getIconURLExpression();
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__PRECONDITION_EXPRESSION:
                return this.getPreconditionExpression();
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
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__BODY:
                this.getBody().clear();
                this.getBody().addAll((Collection<? extends Operation>) newValue);
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__ICON_URL_EXPRESSION:
                this.setIconURLExpression((String) newValue);
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__PRECONDITION_EXPRESSION:
                this.setPreconditionExpression((String) newValue);
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
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__BODY:
                this.getBody().clear();
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__ICON_URL_EXPRESSION:
                this.setIconURLExpression(ICON_URL_EXPRESSION_EDEFAULT);
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__PRECONDITION_EXPRESSION:
                this.setPreconditionExpression(PRECONDITION_EXPRESSION_EDEFAULT);
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
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__BODY:
                return this.body != null && !this.body.isEmpty();
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__ICON_URL_EXPRESSION:
                return ICON_URL_EXPRESSION_EDEFAULT == null ? this.iconURLExpression != null : !ICON_URL_EXPRESSION_EDEFAULT.equals(this.iconURLExpression);
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__PRECONDITION_EXPRESSION:
                return PRECONDITION_EXPRESSION_EDEFAULT == null ? this.preconditionExpression != null : !PRECONDITION_EXPRESSION_EDEFAULT.equals(this.preconditionExpression);
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
        result.append(" (iconURLExpression: ");
        result.append(this.iconURLExpression);
        result.append(", preconditionExpression: ");
        result.append(this.preconditionExpression);
        result.append(')');
        return result.toString();
    }

} // PrimitiveListItemActionOperationImpl
