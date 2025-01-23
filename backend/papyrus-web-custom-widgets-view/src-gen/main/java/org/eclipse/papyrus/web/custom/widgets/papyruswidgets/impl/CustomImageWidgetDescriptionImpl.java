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
package org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.AddImageOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CustomImageWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.RemoveImageOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.SelectImageOperation;
import org.eclipse.sirius.components.view.form.impl.WidgetDescriptionImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Custom Image Widget Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.CustomImageWidgetDescriptionImpl#getAddImageOperation
 * <em>Add Image Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.CustomImageWidgetDescriptionImpl#getSelectImageOperation
 * <em>Select Image Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.CustomImageWidgetDescriptionImpl#getRemoveImageOperation
 * <em>Remove Image Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.CustomImageWidgetDescriptionImpl#getUuidExpression
 * <em>Uuid Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CustomImageWidgetDescriptionImpl extends WidgetDescriptionImpl implements CustomImageWidgetDescription {
    /**
     * The cached value of the '{@link #getAddImageOperation() <em>Add Image Operation</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getAddImageOperation()
     * @generated
     * @ordered
     */
    protected AddImageOperation addImageOperation;

    /**
     * The cached value of the '{@link #getSelectImageOperation() <em>Select Image Operation</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSelectImageOperation()
     * @generated
     * @ordered
     */
    protected SelectImageOperation selectImageOperation;

    /**
     * The cached value of the '{@link #getRemoveImageOperation() <em>Remove Image Operation</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getRemoveImageOperation()
     * @generated
     * @ordered
     */
    protected RemoveImageOperation removeImageOperation;

    /**
     * The default value of the '{@link #getUuidExpression() <em>Uuid Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getUuidExpression()
     * @generated
     * @ordered
     */
    protected static final String UUID_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getUuidExpression() <em>Uuid Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getUuidExpression()
     * @generated
     * @ordered
     */
    protected String uuidExpression = UUID_EXPRESSION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected CustomImageWidgetDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapyrusWidgetsPackage.Literals.CUSTOM_IMAGE_WIDGET_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public AddImageOperation getAddImageOperation() {
        return this.addImageOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetAddImageOperation(AddImageOperation newAddImageOperation, NotificationChain msgs) {
        AddImageOperation oldAddImageOperation = this.addImageOperation;
        this.addImageOperation = newAddImageOperation;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__ADD_IMAGE_OPERATION, oldAddImageOperation,
                    newAddImageOperation);
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
    public void setAddImageOperation(AddImageOperation newAddImageOperation) {
        if (newAddImageOperation != this.addImageOperation) {
            NotificationChain msgs = null;
            if (this.addImageOperation != null)
                msgs = ((InternalEObject) this.addImageOperation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__ADD_IMAGE_OPERATION, null, msgs);
            if (newAddImageOperation != null)
                msgs = ((InternalEObject) newAddImageOperation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__ADD_IMAGE_OPERATION, null, msgs);
            msgs = this.basicSetAddImageOperation(newAddImageOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__ADD_IMAGE_OPERATION, newAddImageOperation, newAddImageOperation));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SelectImageOperation getSelectImageOperation() {
        return this.selectImageOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetSelectImageOperation(SelectImageOperation newSelectImageOperation, NotificationChain msgs) {
        SelectImageOperation oldSelectImageOperation = this.selectImageOperation;
        this.selectImageOperation = newSelectImageOperation;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__SELECT_IMAGE_OPERATION, oldSelectImageOperation,
                    newSelectImageOperation);
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
    public void setSelectImageOperation(SelectImageOperation newSelectImageOperation) {
        if (newSelectImageOperation != this.selectImageOperation) {
            NotificationChain msgs = null;
            if (this.selectImageOperation != null)
                msgs = ((InternalEObject) this.selectImageOperation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__SELECT_IMAGE_OPERATION, null,
                        msgs);
            if (newSelectImageOperation != null)
                msgs = ((InternalEObject) newSelectImageOperation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__SELECT_IMAGE_OPERATION, null,
                        msgs);
            msgs = this.basicSetSelectImageOperation(newSelectImageOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(
                    new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__SELECT_IMAGE_OPERATION, newSelectImageOperation, newSelectImageOperation));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public RemoveImageOperation getRemoveImageOperation() {
        return this.removeImageOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetRemoveImageOperation(RemoveImageOperation newRemoveImageOperation, NotificationChain msgs) {
        RemoveImageOperation oldRemoveImageOperation = this.removeImageOperation;
        this.removeImageOperation = newRemoveImageOperation;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__REMOVE_IMAGE_OPERATION, oldRemoveImageOperation,
                    newRemoveImageOperation);
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
    public void setRemoveImageOperation(RemoveImageOperation newRemoveImageOperation) {
        if (newRemoveImageOperation != this.removeImageOperation) {
            NotificationChain msgs = null;
            if (this.removeImageOperation != null)
                msgs = ((InternalEObject) this.removeImageOperation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__REMOVE_IMAGE_OPERATION, null,
                        msgs);
            if (newRemoveImageOperation != null)
                msgs = ((InternalEObject) newRemoveImageOperation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__REMOVE_IMAGE_OPERATION, null,
                        msgs);
            msgs = this.basicSetRemoveImageOperation(newRemoveImageOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(
                    new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__REMOVE_IMAGE_OPERATION, newRemoveImageOperation, newRemoveImageOperation));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getUuidExpression() {
        return this.uuidExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setUuidExpression(String newUuidExpression) {
        String oldUuidExpression = this.uuidExpression;
        this.uuidExpression = newUuidExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__UUID_EXPRESSION, oldUuidExpression, this.uuidExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__ADD_IMAGE_OPERATION:
                return this.basicSetAddImageOperation(null, msgs);
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__SELECT_IMAGE_OPERATION:
                return this.basicSetSelectImageOperation(null, msgs);
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__REMOVE_IMAGE_OPERATION:
                return this.basicSetRemoveImageOperation(null, msgs);
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
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__ADD_IMAGE_OPERATION:
                return this.getAddImageOperation();
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__SELECT_IMAGE_OPERATION:
                return this.getSelectImageOperation();
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__REMOVE_IMAGE_OPERATION:
                return this.getRemoveImageOperation();
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__UUID_EXPRESSION:
                return this.getUuidExpression();
            default:
                return super.eGet(featureID, resolve, coreType);
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__ADD_IMAGE_OPERATION:
                this.setAddImageOperation((AddImageOperation) newValue);
                return;
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__SELECT_IMAGE_OPERATION:
                this.setSelectImageOperation((SelectImageOperation) newValue);
                return;
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__REMOVE_IMAGE_OPERATION:
                this.setRemoveImageOperation((RemoveImageOperation) newValue);
                return;
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__UUID_EXPRESSION:
                this.setUuidExpression((String) newValue);
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
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__ADD_IMAGE_OPERATION:
                this.setAddImageOperation((AddImageOperation) null);
                return;
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__SELECT_IMAGE_OPERATION:
                this.setSelectImageOperation((SelectImageOperation) null);
                return;
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__REMOVE_IMAGE_OPERATION:
                this.setRemoveImageOperation((RemoveImageOperation) null);
                return;
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__UUID_EXPRESSION:
                this.setUuidExpression(UUID_EXPRESSION_EDEFAULT);
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
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__ADD_IMAGE_OPERATION:
                return this.addImageOperation != null;
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__SELECT_IMAGE_OPERATION:
                return this.selectImageOperation != null;
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__REMOVE_IMAGE_OPERATION:
                return this.removeImageOperation != null;
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__UUID_EXPRESSION:
                return UUID_EXPRESSION_EDEFAULT == null ? this.uuidExpression != null : !UUID_EXPRESSION_EDEFAULT.equals(this.uuidExpression);
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
        result.append(" (uuidExpression: ");
        result.append(this.uuidExpression);
        result.append(')');
        return result.toString();
    }

} // CustomImageWidgetDescriptionImpl
