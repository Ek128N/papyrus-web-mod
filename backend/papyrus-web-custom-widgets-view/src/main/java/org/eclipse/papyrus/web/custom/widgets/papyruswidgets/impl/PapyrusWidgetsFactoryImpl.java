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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ClearReferenceOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ClickReferenceValueOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CreateElementInReferenceOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.LanguageExpressionWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceSetOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceUnsetOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceAddOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceRemoveOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceReorderOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsFactory;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListAddOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListDeleteOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListItemActionOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListReorderOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveRadioWidgetDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class PapyrusWidgetsFactoryImpl extends EFactoryImpl implements PapyrusWidgetsFactory {
    /**
     * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static PapyrusWidgetsFactory init() {
        try {
            PapyrusWidgetsFactory thePapyrusWidgetsFactory = (PapyrusWidgetsFactory) EPackage.Registry.INSTANCE.getEFactory(PapyrusWidgetsPackage.eNS_URI);
            if (thePapyrusWidgetsFactory != null) {
                return thePapyrusWidgetsFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new PapyrusWidgetsFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public PapyrusWidgetsFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case PapyrusWidgetsPackage.LANGUAGE_EXPRESSION_WIDGET_DESCRIPTION:
                return this.createLanguageExpressionWidgetDescription();
            case PapyrusWidgetsPackage.PRIMITIVE_RADIO_WIDGET_DESCRIPTION:
                return this.createPrimitiveRadioWidgetDescription();
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION:
                return this.createPrimitiveListWidgetDescription();
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_DELETE_OPERATION:
                return this.createPrimitiveListDeleteOperation();
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_ADD_OPERATION:
                return this.createPrimitiveListAddOperation();
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_REORDER_OPERATION:
                return this.createPrimitiveListReorderOperation();
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_ITEM_ACTION_OPERATION:
                return this.createPrimitiveListItemActionOperation();
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION:
                return this.createMonoReferenceWidgetDescription();
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION:
                return this.createMultiReferenceWidgetDescription();
            case PapyrusWidgetsPackage.MONO_REFERENCE_SET_OPERATION:
                return this.createMonoReferenceSetOperation();
            case PapyrusWidgetsPackage.MONO_REFERENCE_UNSET_OPERATION:
                return this.createMonoReferenceUnsetOperation();
            case PapyrusWidgetsPackage.CREATE_ELEMENT_IN_REFERENCE_OPERATION:
                return this.createCreateElementInReferenceOperation();
            case PapyrusWidgetsPackage.CLICK_REFERENCE_VALUE_OPERATION:
                return this.createClickReferenceValueOperation();
            case PapyrusWidgetsPackage.MULTI_REFERENCE_ADD_OPERATION:
                return this.createMultiReferenceAddOperation();
            case PapyrusWidgetsPackage.MULTI_REFERENCE_REMOVE_OPERATION:
                return this.createMultiReferenceRemoveOperation();
            case PapyrusWidgetsPackage.CLEAR_REFERENCE_OPERATION:
                return this.createClearReferenceOperation();
            case PapyrusWidgetsPackage.MULTI_REFERENCE_REORDER_OPERATION:
                return this.createMultiReferenceReorderOperation();
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION:
                return this.createContainmentReferenceWidgetDescription();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LanguageExpressionWidgetDescription createLanguageExpressionWidgetDescription() {
        LanguageExpressionWidgetDescriptionImpl languageExpressionWidgetDescription = new LanguageExpressionWidgetDescriptionImpl();
        return languageExpressionWidgetDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PrimitiveRadioWidgetDescription createPrimitiveRadioWidgetDescription() {
        PrimitiveRadioWidgetDescriptionImpl primitiveRadioWidgetDescription = new PrimitiveRadioWidgetDescriptionImpl();
        return primitiveRadioWidgetDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PrimitiveListWidgetDescription createPrimitiveListWidgetDescription() {
        PrimitiveListWidgetDescriptionImpl primitiveListWidgetDescription = new PrimitiveListWidgetDescriptionImpl();
        return primitiveListWidgetDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PrimitiveListDeleteOperation createPrimitiveListDeleteOperation() {
        PrimitiveListDeleteOperationImpl primitiveListDeleteOperation = new PrimitiveListDeleteOperationImpl();
        return primitiveListDeleteOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PrimitiveListAddOperation createPrimitiveListAddOperation() {
        PrimitiveListAddOperationImpl primitiveListAddOperation = new PrimitiveListAddOperationImpl();
        return primitiveListAddOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PrimitiveListReorderOperation createPrimitiveListReorderOperation() {
        PrimitiveListReorderOperationImpl primitiveListReorderOperation = new PrimitiveListReorderOperationImpl();
        return primitiveListReorderOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PrimitiveListItemActionOperation createPrimitiveListItemActionOperation() {
        PrimitiveListItemActionOperationImpl primitiveListItemActionOperation = new PrimitiveListItemActionOperationImpl();
        return primitiveListItemActionOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public MonoReferenceWidgetDescription createMonoReferenceWidgetDescription() {
        MonoReferenceWidgetDescriptionImpl monoReferenceWidgetDescription = new MonoReferenceWidgetDescriptionImpl();
        return monoReferenceWidgetDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public MultiReferenceWidgetDescription createMultiReferenceWidgetDescription() {
        MultiReferenceWidgetDescriptionImpl multiReferenceWidgetDescription = new MultiReferenceWidgetDescriptionImpl();
        return multiReferenceWidgetDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public MonoReferenceSetOperation createMonoReferenceSetOperation() {
        MonoReferenceSetOperationImpl monoReferenceSetOperation = new MonoReferenceSetOperationImpl();
        return monoReferenceSetOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public MonoReferenceUnsetOperation createMonoReferenceUnsetOperation() {
        MonoReferenceUnsetOperationImpl monoReferenceUnsetOperation = new MonoReferenceUnsetOperationImpl();
        return monoReferenceUnsetOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CreateElementInReferenceOperation createCreateElementInReferenceOperation() {
        CreateElementInReferenceOperationImpl createElementInReferenceOperation = new CreateElementInReferenceOperationImpl();
        return createElementInReferenceOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ClickReferenceValueOperation createClickReferenceValueOperation() {
        ClickReferenceValueOperationImpl clickReferenceValueOperation = new ClickReferenceValueOperationImpl();
        return clickReferenceValueOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public MultiReferenceAddOperation createMultiReferenceAddOperation() {
        MultiReferenceAddOperationImpl multiReferenceAddOperation = new MultiReferenceAddOperationImpl();
        return multiReferenceAddOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public MultiReferenceRemoveOperation createMultiReferenceRemoveOperation() {
        MultiReferenceRemoveOperationImpl multiReferenceRemoveOperation = new MultiReferenceRemoveOperationImpl();
        return multiReferenceRemoveOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ClearReferenceOperation createClearReferenceOperation() {
        ClearReferenceOperationImpl clearReferenceOperation = new ClearReferenceOperationImpl();
        return clearReferenceOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public MultiReferenceReorderOperation createMultiReferenceReorderOperation() {
        MultiReferenceReorderOperationImpl multiReferenceReorderOperation = new MultiReferenceReorderOperationImpl();
        return multiReferenceReorderOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ContainmentReferenceWidgetDescription createContainmentReferenceWidgetDescription() {
        ContainmentReferenceWidgetDescriptionImpl containmentReferenceWidgetDescription = new ContainmentReferenceWidgetDescriptionImpl();
        return containmentReferenceWidgetDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PapyrusWidgetsPackage getPapyrusWidgetsPackage() {
        return (PapyrusWidgetsPackage) this.getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @deprecated
     * @generated
     */
    @Deprecated
    public static PapyrusWidgetsPackage getPackage() {
        return PapyrusWidgetsPackage.eINSTANCE;
    }

} // PapyrusWidgetsFactoryImpl
