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
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ClickReferenceValueOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CreateElementInReferenceOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceRemoveOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceReorderOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage;
import org.eclipse.sirius.components.view.form.impl.WidgetDescriptionImpl;
import org.eclipse.sirius.components.widgets.reference.ConditionalReferenceWidgetDescriptionStyle;
import org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescriptionStyle;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Containment Reference Widget
 * Description</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ContainmentReferenceWidgetDescriptionImpl#getIsEnabledExpression
 * <em>Is Enabled Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ContainmentReferenceWidgetDescriptionImpl#isMany
 * <em>Many</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ContainmentReferenceWidgetDescriptionImpl#getType
 * <em>Type</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ContainmentReferenceWidgetDescriptionImpl#getOwnerExpression
 * <em>Owner Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ContainmentReferenceWidgetDescriptionImpl#getValueExpression
 * <em>Value Expression</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ContainmentReferenceWidgetDescriptionImpl#getCreateElementOperation
 * <em>Create Element Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ContainmentReferenceWidgetDescriptionImpl#getRemoveOperation
 * <em>Remove Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ContainmentReferenceWidgetDescriptionImpl#getReorderOperation
 * <em>Reorder Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ContainmentReferenceWidgetDescriptionImpl#getClickOperation
 * <em>Click Operation</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ContainmentReferenceWidgetDescriptionImpl#getStyle
 * <em>Style</em>}</li>
 * <li>{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ContainmentReferenceWidgetDescriptionImpl#getConditionalStyles
 * <em>Conditional Styles</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContainmentReferenceWidgetDescriptionImpl extends WidgetDescriptionImpl implements ContainmentReferenceWidgetDescription {
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
     * The default value of the '{@link #isMany() <em>Many</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #isMany()
     * @generated
     * @ordered
     */
    protected static final boolean MANY_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isMany() <em>Many</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #isMany()
     * @generated
     * @ordered
     */
    protected boolean many = MANY_EDEFAULT;

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
     * The cached value of the '{@link #getCreateElementOperation() <em>Create Element Operation</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getCreateElementOperation()
     * @generated
     * @ordered
     */
    protected CreateElementInReferenceOperation createElementOperation;

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
     * The cached value of the '{@link #getReorderOperation() <em>Reorder Operation</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getReorderOperation()
     * @generated
     * @ordered
     */
    protected MultiReferenceReorderOperation reorderOperation;

    /**
     * The cached value of the '{@link #getClickOperation() <em>Click Operation</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getClickOperation()
     * @generated
     * @ordered
     */
    protected ClickReferenceValueOperation clickOperation;

    /**
     * The cached value of the '{@link #getStyle() <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getStyle()
     * @generated
     * @ordered
     */
    protected ReferenceWidgetDescriptionStyle style;

    /**
     * The cached value of the '{@link #getConditionalStyles() <em>Conditional Styles</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getConditionalStyles()
     * @generated
     * @ordered
     */
    protected EList<ConditionalReferenceWidgetDescriptionStyle> conditionalStyles;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ContainmentReferenceWidgetDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapyrusWidgetsPackage.Literals.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION, oldIsEnabledExpression,
                    this.isEnabledExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isMany() {
        return this.many;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setMany(boolean newMany) {
        boolean oldMany = this.many;
        this.many = newMany;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__MANY, oldMany, this.many));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__TYPE, oldType, this.type));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION, oldOwnerExpression, this.ownerExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION, oldValueExpression, this.valueExpression));
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION,
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
                msgs = ((InternalEObject) this.createElementOperation).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION, null, msgs);
            if (newCreateElementOperation != null)
                msgs = ((InternalEObject) newCreateElementOperation).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION, null, msgs);
            msgs = this.basicSetCreateElementOperation(newCreateElementOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION, newCreateElementOperation,
                    newCreateElementOperation));
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION, oldRemoveOperation,
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
                msgs = ((InternalEObject) this.removeOperation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION, null,
                        msgs);
            if (newRemoveOperation != null)
                msgs = ((InternalEObject) newRemoveOperation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION, null, msgs);
            msgs = this.basicSetRemoveOperation(newRemoveOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION, newRemoveOperation, newRemoveOperation));
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION, oldReorderOperation,
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
                msgs = ((InternalEObject) this.reorderOperation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION, null,
                        msgs);
            if (newReorderOperation != null)
                msgs = ((InternalEObject) newReorderOperation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION, null,
                        msgs);
            msgs = this.basicSetReorderOperation(newReorderOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION, newReorderOperation, newReorderOperation));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ClickReferenceValueOperation getClickOperation() {
        return this.clickOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetClickOperation(ClickReferenceValueOperation newClickOperation, NotificationChain msgs) {
        ClickReferenceValueOperation oldClickOperation = this.clickOperation;
        this.clickOperation = newClickOperation;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CLICK_OPERATION, oldClickOperation,
                    newClickOperation);
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
    public void setClickOperation(ClickReferenceValueOperation newClickOperation) {
        if (newClickOperation != this.clickOperation) {
            NotificationChain msgs = null;
            if (this.clickOperation != null)
                msgs = ((InternalEObject) this.clickOperation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CLICK_OPERATION, null,
                        msgs);
            if (newClickOperation != null)
                msgs = ((InternalEObject) newClickOperation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CLICK_OPERATION, null, msgs);
            msgs = this.basicSetClickOperation(newClickOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CLICK_OPERATION, newClickOperation, newClickOperation));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ReferenceWidgetDescriptionStyle getStyle() {
        return this.style;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetStyle(ReferenceWidgetDescriptionStyle newStyle, NotificationChain msgs) {
        ReferenceWidgetDescriptionStyle oldStyle = this.style;
        this.style = newStyle;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__STYLE, oldStyle, newStyle);
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
    public void setStyle(ReferenceWidgetDescriptionStyle newStyle) {
        if (newStyle != this.style) {
            NotificationChain msgs = null;
            if (this.style != null)
                msgs = ((InternalEObject) this.style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__STYLE, null, msgs);
            if (newStyle != null)
                msgs = ((InternalEObject) newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__STYLE, null, msgs);
            msgs = this.basicSetStyle(newStyle, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__STYLE, newStyle, newStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ConditionalReferenceWidgetDescriptionStyle> getConditionalStyles() {
        if (this.conditionalStyles == null) {
            this.conditionalStyles = new EObjectContainmentEList<>(ConditionalReferenceWidgetDescriptionStyle.class, this,
                    PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES);
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
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION:
                return this.basicSetCreateElementOperation(null, msgs);
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION:
                return this.basicSetRemoveOperation(null, msgs);
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION:
                return this.basicSetReorderOperation(null, msgs);
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CLICK_OPERATION:
                return this.basicSetClickOperation(null, msgs);
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__STYLE:
                return this.basicSetStyle(null, msgs);
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
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
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                return this.getIsEnabledExpression();
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__MANY:
                return this.isMany();
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__TYPE:
                return this.getType();
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION:
                return this.getOwnerExpression();
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION:
                return this.getValueExpression();
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION:
                return this.getCreateElementOperation();
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION:
                return this.getRemoveOperation();
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION:
                return this.getReorderOperation();
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CLICK_OPERATION:
                return this.getClickOperation();
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__STYLE:
                return this.getStyle();
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
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
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.setIsEnabledExpression((String) newValue);
                return;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__MANY:
                this.setMany((Boolean) newValue);
                return;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__TYPE:
                this.setType((String) newValue);
                return;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION:
                this.setOwnerExpression((String) newValue);
                return;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION:
                this.setValueExpression((String) newValue);
                return;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION:
                this.setCreateElementOperation((CreateElementInReferenceOperation) newValue);
                return;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION:
                this.setRemoveOperation((MultiReferenceRemoveOperation) newValue);
                return;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION:
                this.setReorderOperation((MultiReferenceReorderOperation) newValue);
                return;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CLICK_OPERATION:
                this.setClickOperation((ClickReferenceValueOperation) newValue);
                return;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__STYLE:
                this.setStyle((ReferenceWidgetDescriptionStyle) newValue);
                return;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
                this.getConditionalStyles().clear();
                this.getConditionalStyles().addAll((Collection<? extends ConditionalReferenceWidgetDescriptionStyle>) newValue);
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
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.setIsEnabledExpression(IS_ENABLED_EXPRESSION_EDEFAULT);
                return;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__MANY:
                this.setMany(MANY_EDEFAULT);
                return;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__TYPE:
                this.setType(TYPE_EDEFAULT);
                return;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION:
                this.setOwnerExpression(OWNER_EXPRESSION_EDEFAULT);
                return;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION:
                this.setValueExpression(VALUE_EXPRESSION_EDEFAULT);
                return;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION:
                this.setCreateElementOperation((CreateElementInReferenceOperation) null);
                return;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION:
                this.setRemoveOperation((MultiReferenceRemoveOperation) null);
                return;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION:
                this.setReorderOperation((MultiReferenceReorderOperation) null);
                return;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CLICK_OPERATION:
                this.setClickOperation((ClickReferenceValueOperation) null);
                return;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__STYLE:
                this.setStyle((ReferenceWidgetDescriptionStyle) null);
                return;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
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
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                return IS_ENABLED_EXPRESSION_EDEFAULT == null ? this.isEnabledExpression != null : !IS_ENABLED_EXPRESSION_EDEFAULT.equals(this.isEnabledExpression);
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__MANY:
                return this.many != MANY_EDEFAULT;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__TYPE:
                return TYPE_EDEFAULT == null ? this.type != null : !TYPE_EDEFAULT.equals(this.type);
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION:
                return OWNER_EXPRESSION_EDEFAULT == null ? this.ownerExpression != null : !OWNER_EXPRESSION_EDEFAULT.equals(this.ownerExpression);
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION:
                return VALUE_EXPRESSION_EDEFAULT == null ? this.valueExpression != null : !VALUE_EXPRESSION_EDEFAULT.equals(this.valueExpression);
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION:
                return this.createElementOperation != null;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION:
                return this.removeOperation != null;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION:
                return this.reorderOperation != null;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CLICK_OPERATION:
                return this.clickOperation != null;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__STYLE:
                return this.style != null;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
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
        result.append(", many: ");
        result.append(this.many);
        result.append(", type: ");
        result.append(this.type);
        result.append(", ownerExpression: ");
        result.append(this.ownerExpression);
        result.append(", valueExpression: ");
        result.append(this.valueExpression);
        result.append(')');
        return result.toString();
    }

} // ContainmentReferenceWidgetDescriptionImpl
