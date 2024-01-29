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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
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
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.widgets.reference.ReferencePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class PapyrusWidgetsPackageImpl extends EPackageImpl implements PapyrusWidgetsPackage {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass languageExpressionWidgetDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass primitiveRadioWidgetDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass primitiveListWidgetDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass primitiveListDeleteOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass primitiveListAddOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass primitiveListReorderOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass primitiveListItemActionOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass monoReferenceWidgetDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass multiReferenceWidgetDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass monoReferenceSetOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass monoReferenceUnsetOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass createElementInReferenceOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass clickReferenceValueOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass multiReferenceAddOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass multiReferenceRemoveOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass clearReferenceOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass multiReferenceReorderOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass containmentReferenceWidgetDescriptionEClass = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
     * EPackage.Registry} by the package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
     * performs initialization of the package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private PapyrusWidgetsPackageImpl() {
        super(eNS_URI, PapyrusWidgetsFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     *
     * <p>
     * This method is used to initialize {@link PapyrusWidgetsPackage#eINSTANCE} when that field is accessed. Clients
     * should not invoke it directly. Instead, they should simply access that field to obtain the package. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static PapyrusWidgetsPackage init() {
        if (isInited)
            return (PapyrusWidgetsPackage) EPackage.Registry.INSTANCE.getEPackage(PapyrusWidgetsPackage.eNS_URI);

        // Obtain or create and register package
        Object registeredPapyrusWidgetsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
        PapyrusWidgetsPackageImpl thePapyrusWidgetsPackage = registeredPapyrusWidgetsPackage instanceof PapyrusWidgetsPackageImpl ? (PapyrusWidgetsPackageImpl) registeredPapyrusWidgetsPackage
                : new PapyrusWidgetsPackageImpl();

        isInited = true;

        // Initialize simple dependencies
        FormPackage.eINSTANCE.eClass();
        ReferencePackage.eINSTANCE.eClass();
        ViewPackage.eINSTANCE.eClass();

        // Create package meta-data objects
        thePapyrusWidgetsPackage.createPackageContents();

        // Initialize created meta-data
        thePapyrusWidgetsPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        thePapyrusWidgetsPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(PapyrusWidgetsPackage.eNS_URI, thePapyrusWidgetsPackage);
        return thePapyrusWidgetsPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getLanguageExpressionWidgetDescription() {
        return this.languageExpressionWidgetDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLanguageExpressionWidgetDescription_IsEnabledExpression() {
        return (EAttribute) this.languageExpressionWidgetDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getPrimitiveRadioWidgetDescription() {
        return this.primitiveRadioWidgetDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPrimitiveRadioWidgetDescription_IsEnabledExpression() {
        return (EAttribute) this.primitiveRadioWidgetDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPrimitiveRadioWidgetDescription_CandidatesExpression() {
        return (EAttribute) this.primitiveRadioWidgetDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPrimitiveRadioWidgetDescription_ValueExpression() {
        return (EAttribute) this.primitiveRadioWidgetDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPrimitiveRadioWidgetDescription_Body() {
        return (EReference) this.primitiveRadioWidgetDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getPrimitiveListWidgetDescription() {
        return this.primitiveListWidgetDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPrimitiveListWidgetDescription_ValueExpression() {
        return (EAttribute) this.primitiveListWidgetDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPrimitiveListWidgetDescription_DisplayExpression() {
        return (EAttribute) this.primitiveListWidgetDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPrimitiveListWidgetDescription_CandidatesExpression() {
        return (EAttribute) this.primitiveListWidgetDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPrimitiveListWidgetDescription_Style() {
        return (EReference) this.primitiveListWidgetDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPrimitiveListWidgetDescription_ConditionalStyles() {
        return (EReference) this.primitiveListWidgetDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPrimitiveListWidgetDescription_IsEnabledExpression() {
        return (EAttribute) this.primitiveListWidgetDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPrimitiveListWidgetDescription_DeleteOperation() {
        return (EReference) this.primitiveListWidgetDescriptionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPrimitiveListWidgetDescription_AddOperation() {
        return (EReference) this.primitiveListWidgetDescriptionEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPrimitiveListWidgetDescription_ReorderOperation() {
        return (EReference) this.primitiveListWidgetDescriptionEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPrimitiveListWidgetDescription_ItemActionOperation() {
        return (EReference) this.primitiveListWidgetDescriptionEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getPrimitiveListDeleteOperation() {
        return this.primitiveListDeleteOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPrimitiveListDeleteOperation_Body() {
        return (EReference) this.primitiveListDeleteOperationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getPrimitiveListAddOperation() {
        return this.primitiveListAddOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPrimitiveListAddOperation_Body() {
        return (EReference) this.primitiveListAddOperationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getPrimitiveListReorderOperation() {
        return this.primitiveListReorderOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPrimitiveListReorderOperation_Body() {
        return (EReference) this.primitiveListReorderOperationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getPrimitiveListItemActionOperation() {
        return this.primitiveListItemActionOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPrimitiveListItemActionOperation_Body() {
        return (EReference) this.primitiveListItemActionOperationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPrimitiveListItemActionOperation_IconURLExpression() {
        return (EAttribute) this.primitiveListItemActionOperationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPrimitiveListItemActionOperation_PreconditionExpression() {
        return (EAttribute) this.primitiveListItemActionOperationEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getMonoReferenceWidgetDescription() {
        return this.monoReferenceWidgetDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getMonoReferenceWidgetDescription_IsEnabledExpression() {
        return (EAttribute) this.monoReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getMonoReferenceWidgetDescription_OwnerExpression() {
        return (EAttribute) this.monoReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getMonoReferenceWidgetDescription_Type() {
        return (EAttribute) this.monoReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getMonoReferenceWidgetDescription_ValueExpression() {
        return (EAttribute) this.monoReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getMonoReferenceWidgetDescription_CandidatesSearchScopeExpression() {
        return (EAttribute) this.monoReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getMonoReferenceWidgetDescription_DropdownOptionsExpression() {
        return (EAttribute) this.monoReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMonoReferenceWidgetDescription_CreateElementOperation() {
        return (EReference) this.monoReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMonoReferenceWidgetDescription_SetOperation() {
        return (EReference) this.monoReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMonoReferenceWidgetDescription_UnsetOperation() {
        return (EReference) this.monoReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMonoReferenceWidgetDescription_ClearOperation() {
        return (EReference) this.monoReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMonoReferenceWidgetDescription_Style() {
        return (EReference) this.monoReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMonoReferenceWidgetDescription_ConditionalStyles() {
        return (EReference) this.monoReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(11);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getMultiReferenceWidgetDescription() {
        return this.multiReferenceWidgetDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getMultiReferenceWidgetDescription_IsEnabledExpression() {
        return (EAttribute) this.multiReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getMultiReferenceWidgetDescription_OwnerExpression() {
        return (EAttribute) this.multiReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getMultiReferenceWidgetDescription_Type() {
        return (EAttribute) this.multiReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getMultiReferenceWidgetDescription_ValueExpression() {
        return (EAttribute) this.multiReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getMultiReferenceWidgetDescription_CandidatesSearchScopeExpression() {
        return (EAttribute) this.multiReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getMultiReferenceWidgetDescription_DropdownOptionsExpression() {
        return (EAttribute) this.multiReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMultiReferenceWidgetDescription_CreateElementOperation() {
        return (EReference) this.multiReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMultiReferenceWidgetDescription_AddOperation() {
        return (EReference) this.multiReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMultiReferenceWidgetDescription_RemoveOperation() {
        return (EReference) this.multiReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMultiReferenceWidgetDescription_ClearOperation() {
        return (EReference) this.multiReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMultiReferenceWidgetDescription_ReorderOperation() {
        return (EReference) this.multiReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMultiReferenceWidgetDescription_Style() {
        return (EReference) this.multiReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(11);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMultiReferenceWidgetDescription_ConditionalStyles() {
        return (EReference) this.multiReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(12);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getMonoReferenceSetOperation() {
        return this.monoReferenceSetOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMonoReferenceSetOperation_Body() {
        return (EReference) this.monoReferenceSetOperationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getMonoReferenceUnsetOperation() {
        return this.monoReferenceUnsetOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMonoReferenceUnsetOperation_Body() {
        return (EReference) this.monoReferenceUnsetOperationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getCreateElementInReferenceOperation() {
        return this.createElementInReferenceOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getCreateElementInReferenceOperation_Body() {
        return (EReference) this.createElementInReferenceOperationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getClickReferenceValueOperation() {
        return this.clickReferenceValueOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getClickReferenceValueOperation_Body() {
        return (EReference) this.clickReferenceValueOperationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getMultiReferenceAddOperation() {
        return this.multiReferenceAddOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMultiReferenceAddOperation_Body() {
        return (EReference) this.multiReferenceAddOperationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getMultiReferenceRemoveOperation() {
        return this.multiReferenceRemoveOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMultiReferenceRemoveOperation_Body() {
        return (EReference) this.multiReferenceRemoveOperationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getClearReferenceOperation() {
        return this.clearReferenceOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getClearReferenceOperation_Body() {
        return (EReference) this.clearReferenceOperationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getMultiReferenceReorderOperation() {
        return this.multiReferenceReorderOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMultiReferenceReorderOperation_Body() {
        return (EReference) this.multiReferenceReorderOperationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getContainmentReferenceWidgetDescription() {
        return this.containmentReferenceWidgetDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getContainmentReferenceWidgetDescription_IsEnabledExpression() {
        return (EAttribute) this.containmentReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getContainmentReferenceWidgetDescription_Many() {
        return (EAttribute) this.containmentReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getContainmentReferenceWidgetDescription_Type() {
        return (EAttribute) this.containmentReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getContainmentReferenceWidgetDescription_OwnerExpression() {
        return (EAttribute) this.containmentReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getContainmentReferenceWidgetDescription_ValueExpression() {
        return (EAttribute) this.containmentReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getContainmentReferenceWidgetDescription_CreateElementOperation() {
        return (EReference) this.containmentReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getContainmentReferenceWidgetDescription_RemoveOperation() {
        return (EReference) this.containmentReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getContainmentReferenceWidgetDescription_ReorderOperation() {
        return (EReference) this.containmentReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getContainmentReferenceWidgetDescription_ClickOperation() {
        return (EReference) this.containmentReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getContainmentReferenceWidgetDescription_Style() {
        return (EReference) this.containmentReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getContainmentReferenceWidgetDescription_ConditionalStyles() {
        return (EReference) this.containmentReferenceWidgetDescriptionEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PapyrusWidgetsFactory getPapyrusWidgetsFactory() {
        return (PapyrusWidgetsFactory) this.getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package. This method is guarded to have no affect on any invocation but
     * its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void createPackageContents() {
        if (this.isCreated)
            return;
        this.isCreated = true;

        // Create classes and their features
        this.languageExpressionWidgetDescriptionEClass = this.createEClass(LANGUAGE_EXPRESSION_WIDGET_DESCRIPTION);
        this.createEAttribute(this.languageExpressionWidgetDescriptionEClass, LANGUAGE_EXPRESSION_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION);

        this.primitiveRadioWidgetDescriptionEClass = this.createEClass(PRIMITIVE_RADIO_WIDGET_DESCRIPTION);
        this.createEAttribute(this.primitiveRadioWidgetDescriptionEClass, PRIMITIVE_RADIO_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION);
        this.createEAttribute(this.primitiveRadioWidgetDescriptionEClass, PRIMITIVE_RADIO_WIDGET_DESCRIPTION__CANDIDATES_EXPRESSION);
        this.createEAttribute(this.primitiveRadioWidgetDescriptionEClass, PRIMITIVE_RADIO_WIDGET_DESCRIPTION__VALUE_EXPRESSION);
        this.createEReference(this.primitiveRadioWidgetDescriptionEClass, PRIMITIVE_RADIO_WIDGET_DESCRIPTION__BODY);

        this.primitiveListWidgetDescriptionEClass = this.createEClass(PRIMITIVE_LIST_WIDGET_DESCRIPTION);
        this.createEAttribute(this.primitiveListWidgetDescriptionEClass, PRIMITIVE_LIST_WIDGET_DESCRIPTION__VALUE_EXPRESSION);
        this.createEAttribute(this.primitiveListWidgetDescriptionEClass, PRIMITIVE_LIST_WIDGET_DESCRIPTION__DISPLAY_EXPRESSION);
        this.createEAttribute(this.primitiveListWidgetDescriptionEClass, PRIMITIVE_LIST_WIDGET_DESCRIPTION__CANDIDATES_EXPRESSION);
        this.createEReference(this.primitiveListWidgetDescriptionEClass, PRIMITIVE_LIST_WIDGET_DESCRIPTION__STYLE);
        this.createEReference(this.primitiveListWidgetDescriptionEClass, PRIMITIVE_LIST_WIDGET_DESCRIPTION__CONDITIONAL_STYLES);
        this.createEAttribute(this.primitiveListWidgetDescriptionEClass, PRIMITIVE_LIST_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION);
        this.createEReference(this.primitiveListWidgetDescriptionEClass, PRIMITIVE_LIST_WIDGET_DESCRIPTION__DELETE_OPERATION);
        this.createEReference(this.primitiveListWidgetDescriptionEClass, PRIMITIVE_LIST_WIDGET_DESCRIPTION__ADD_OPERATION);
        this.createEReference(this.primitiveListWidgetDescriptionEClass, PRIMITIVE_LIST_WIDGET_DESCRIPTION__REORDER_OPERATION);
        this.createEReference(this.primitiveListWidgetDescriptionEClass, PRIMITIVE_LIST_WIDGET_DESCRIPTION__ITEM_ACTION_OPERATION);

        this.primitiveListDeleteOperationEClass = this.createEClass(PRIMITIVE_LIST_DELETE_OPERATION);
        this.createEReference(this.primitiveListDeleteOperationEClass, PRIMITIVE_LIST_DELETE_OPERATION__BODY);

        this.primitiveListAddOperationEClass = this.createEClass(PRIMITIVE_LIST_ADD_OPERATION);
        this.createEReference(this.primitiveListAddOperationEClass, PRIMITIVE_LIST_ADD_OPERATION__BODY);

        this.primitiveListReorderOperationEClass = this.createEClass(PRIMITIVE_LIST_REORDER_OPERATION);
        this.createEReference(this.primitiveListReorderOperationEClass, PRIMITIVE_LIST_REORDER_OPERATION__BODY);

        this.primitiveListItemActionOperationEClass = this.createEClass(PRIMITIVE_LIST_ITEM_ACTION_OPERATION);
        this.createEReference(this.primitiveListItemActionOperationEClass, PRIMITIVE_LIST_ITEM_ACTION_OPERATION__BODY);
        this.createEAttribute(this.primitiveListItemActionOperationEClass, PRIMITIVE_LIST_ITEM_ACTION_OPERATION__ICON_URL_EXPRESSION);
        this.createEAttribute(this.primitiveListItemActionOperationEClass, PRIMITIVE_LIST_ITEM_ACTION_OPERATION__PRECONDITION_EXPRESSION);

        this.monoReferenceWidgetDescriptionEClass = this.createEClass(MONO_REFERENCE_WIDGET_DESCRIPTION);
        this.createEAttribute(this.monoReferenceWidgetDescriptionEClass, MONO_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION);
        this.createEAttribute(this.monoReferenceWidgetDescriptionEClass, MONO_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION);
        this.createEAttribute(this.monoReferenceWidgetDescriptionEClass, MONO_REFERENCE_WIDGET_DESCRIPTION__TYPE);
        this.createEAttribute(this.monoReferenceWidgetDescriptionEClass, MONO_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION);
        this.createEAttribute(this.monoReferenceWidgetDescriptionEClass, MONO_REFERENCE_WIDGET_DESCRIPTION__CANDIDATES_SEARCH_SCOPE_EXPRESSION);
        this.createEAttribute(this.monoReferenceWidgetDescriptionEClass, MONO_REFERENCE_WIDGET_DESCRIPTION__DROPDOWN_OPTIONS_EXPRESSION);
        this.createEReference(this.monoReferenceWidgetDescriptionEClass, MONO_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION);
        this.createEReference(this.monoReferenceWidgetDescriptionEClass, MONO_REFERENCE_WIDGET_DESCRIPTION__SET_OPERATION);
        this.createEReference(this.monoReferenceWidgetDescriptionEClass, MONO_REFERENCE_WIDGET_DESCRIPTION__UNSET_OPERATION);
        this.createEReference(this.monoReferenceWidgetDescriptionEClass, MONO_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION);
        this.createEReference(this.monoReferenceWidgetDescriptionEClass, MONO_REFERENCE_WIDGET_DESCRIPTION__STYLE);
        this.createEReference(this.monoReferenceWidgetDescriptionEClass, MONO_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES);

        this.multiReferenceWidgetDescriptionEClass = this.createEClass(MULTI_REFERENCE_WIDGET_DESCRIPTION);
        this.createEAttribute(this.multiReferenceWidgetDescriptionEClass, MULTI_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION);
        this.createEAttribute(this.multiReferenceWidgetDescriptionEClass, MULTI_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION);
        this.createEAttribute(this.multiReferenceWidgetDescriptionEClass, MULTI_REFERENCE_WIDGET_DESCRIPTION__TYPE);
        this.createEAttribute(this.multiReferenceWidgetDescriptionEClass, MULTI_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION);
        this.createEAttribute(this.multiReferenceWidgetDescriptionEClass, MULTI_REFERENCE_WIDGET_DESCRIPTION__CANDIDATES_SEARCH_SCOPE_EXPRESSION);
        this.createEAttribute(this.multiReferenceWidgetDescriptionEClass, MULTI_REFERENCE_WIDGET_DESCRIPTION__DROPDOWN_OPTIONS_EXPRESSION);
        this.createEReference(this.multiReferenceWidgetDescriptionEClass, MULTI_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION);
        this.createEReference(this.multiReferenceWidgetDescriptionEClass, MULTI_REFERENCE_WIDGET_DESCRIPTION__ADD_OPERATION);
        this.createEReference(this.multiReferenceWidgetDescriptionEClass, MULTI_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION);
        this.createEReference(this.multiReferenceWidgetDescriptionEClass, MULTI_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION);
        this.createEReference(this.multiReferenceWidgetDescriptionEClass, MULTI_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION);
        this.createEReference(this.multiReferenceWidgetDescriptionEClass, MULTI_REFERENCE_WIDGET_DESCRIPTION__STYLE);
        this.createEReference(this.multiReferenceWidgetDescriptionEClass, MULTI_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES);

        this.monoReferenceSetOperationEClass = this.createEClass(MONO_REFERENCE_SET_OPERATION);
        this.createEReference(this.monoReferenceSetOperationEClass, MONO_REFERENCE_SET_OPERATION__BODY);

        this.monoReferenceUnsetOperationEClass = this.createEClass(MONO_REFERENCE_UNSET_OPERATION);
        this.createEReference(this.monoReferenceUnsetOperationEClass, MONO_REFERENCE_UNSET_OPERATION__BODY);

        this.createElementInReferenceOperationEClass = this.createEClass(CREATE_ELEMENT_IN_REFERENCE_OPERATION);
        this.createEReference(this.createElementInReferenceOperationEClass, CREATE_ELEMENT_IN_REFERENCE_OPERATION__BODY);

        this.clickReferenceValueOperationEClass = this.createEClass(CLICK_REFERENCE_VALUE_OPERATION);
        this.createEReference(this.clickReferenceValueOperationEClass, CLICK_REFERENCE_VALUE_OPERATION__BODY);

        this.multiReferenceAddOperationEClass = this.createEClass(MULTI_REFERENCE_ADD_OPERATION);
        this.createEReference(this.multiReferenceAddOperationEClass, MULTI_REFERENCE_ADD_OPERATION__BODY);

        this.multiReferenceRemoveOperationEClass = this.createEClass(MULTI_REFERENCE_REMOVE_OPERATION);
        this.createEReference(this.multiReferenceRemoveOperationEClass, MULTI_REFERENCE_REMOVE_OPERATION__BODY);

        this.clearReferenceOperationEClass = this.createEClass(CLEAR_REFERENCE_OPERATION);
        this.createEReference(this.clearReferenceOperationEClass, CLEAR_REFERENCE_OPERATION__BODY);

        this.multiReferenceReorderOperationEClass = this.createEClass(MULTI_REFERENCE_REORDER_OPERATION);
        this.createEReference(this.multiReferenceReorderOperationEClass, MULTI_REFERENCE_REORDER_OPERATION__BODY);

        this.containmentReferenceWidgetDescriptionEClass = this.createEClass(CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION);
        this.createEAttribute(this.containmentReferenceWidgetDescriptionEClass, CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION);
        this.createEAttribute(this.containmentReferenceWidgetDescriptionEClass, CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__MANY);
        this.createEAttribute(this.containmentReferenceWidgetDescriptionEClass, CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__TYPE);
        this.createEAttribute(this.containmentReferenceWidgetDescriptionEClass, CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION);
        this.createEAttribute(this.containmentReferenceWidgetDescriptionEClass, CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION);
        this.createEReference(this.containmentReferenceWidgetDescriptionEClass, CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION);
        this.createEReference(this.containmentReferenceWidgetDescriptionEClass, CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION);
        this.createEReference(this.containmentReferenceWidgetDescriptionEClass, CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION);
        this.createEReference(this.containmentReferenceWidgetDescriptionEClass, CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CLICK_OPERATION);
        this.createEReference(this.containmentReferenceWidgetDescriptionEClass, CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__STYLE);
        this.createEReference(this.containmentReferenceWidgetDescriptionEClass, CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model. This method is guarded to have no affect on any
     * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void initializePackageContents() {
        if (this.isInitialized)
            return;
        this.isInitialized = true;

        // Initialize package
        this.setName(eNAME);
        this.setNsPrefix(eNS_PREFIX);
        this.setNsURI(eNS_URI);

        // Obtain other dependent packages
        FormPackage theFormPackage = (FormPackage) EPackage.Registry.INSTANCE.getEPackage(FormPackage.eNS_URI);
        ViewPackage theViewPackage = (ViewPackage) EPackage.Registry.INSTANCE.getEPackage(ViewPackage.eNS_URI);
        ReferencePackage theReferencePackage = (ReferencePackage) EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        this.languageExpressionWidgetDescriptionEClass.getESuperTypes().add(theFormPackage.getWidgetDescription());
        this.primitiveRadioWidgetDescriptionEClass.getESuperTypes().add(theFormPackage.getWidgetDescription());
        this.primitiveListWidgetDescriptionEClass.getESuperTypes().add(theFormPackage.getWidgetDescription());
        this.monoReferenceWidgetDescriptionEClass.getESuperTypes().add(theFormPackage.getWidgetDescription());
        this.multiReferenceWidgetDescriptionEClass.getESuperTypes().add(theFormPackage.getWidgetDescription());
        this.containmentReferenceWidgetDescriptionEClass.getESuperTypes().add(theFormPackage.getWidgetDescription());

        // Initialize classes, features, and operations; add parameters
        this.initEClass(this.languageExpressionWidgetDescriptionEClass, LanguageExpressionWidgetDescription.class, "LanguageExpressionWidgetDescription", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getLanguageExpressionWidgetDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "isEnabledExpression", null, 0, 1,
                LanguageExpressionWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.primitiveRadioWidgetDescriptionEClass, PrimitiveRadioWidgetDescription.class, "PrimitiveRadioWidgetDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getPrimitiveRadioWidgetDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "isEnabledExpression", null, 0, 1,
                PrimitiveRadioWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPrimitiveRadioWidgetDescription_CandidatesExpression(), theViewPackage.getInterpretedExpression(), "candidatesExpression", null, 0, 1,
                PrimitiveRadioWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPrimitiveRadioWidgetDescription_ValueExpression(), theViewPackage.getInterpretedExpression(), "valueExpression", null, 0, 1, PrimitiveRadioWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPrimitiveRadioWidgetDescription_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, PrimitiveRadioWidgetDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.primitiveListWidgetDescriptionEClass, PrimitiveListWidgetDescription.class, "PrimitiveListWidgetDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getPrimitiveListWidgetDescription_ValueExpression(), theViewPackage.getInterpretedExpression(), "valueExpression", null, 0, 1, PrimitiveListWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPrimitiveListWidgetDescription_DisplayExpression(), theViewPackage.getInterpretedExpression(), "displayExpression", null, 0, 1,
                PrimitiveListWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPrimitiveListWidgetDescription_CandidatesExpression(), theViewPackage.getInterpretedExpression(), "candidatesExpression", null, 0, 1,
                PrimitiveListWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPrimitiveListWidgetDescription_Style(), theFormPackage.getListDescriptionStyle(), null, "style", null, 0, 1, PrimitiveListWidgetDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPrimitiveListWidgetDescription_ConditionalStyles(), theFormPackage.getConditionalListDescriptionStyle(), null, "conditionalStyles", null, 0, -1,
                PrimitiveListWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPrimitiveListWidgetDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "isEnabledExpression", null, 0, 1,
                PrimitiveListWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPrimitiveListWidgetDescription_DeleteOperation(), this.getPrimitiveListDeleteOperation(), null, "deleteOperation", null, 0, 1, PrimitiveListWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPrimitiveListWidgetDescription_AddOperation(), this.getPrimitiveListAddOperation(), null, "addOperation", null, 0, 1, PrimitiveListWidgetDescription.class,
                !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPrimitiveListWidgetDescription_ReorderOperation(), this.getPrimitiveListReorderOperation(), null, "reorderOperation", null, 0, 1,
                PrimitiveListWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPrimitiveListWidgetDescription_ItemActionOperation(), this.getPrimitiveListItemActionOperation(), null, "itemActionOperation", null, 0, 1,
                PrimitiveListWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.primitiveListDeleteOperationEClass, PrimitiveListDeleteOperation.class, "PrimitiveListDeleteOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getPrimitiveListDeleteOperation_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, PrimitiveListDeleteOperation.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.primitiveListAddOperationEClass, PrimitiveListAddOperation.class, "PrimitiveListAddOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getPrimitiveListAddOperation_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, PrimitiveListAddOperation.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.primitiveListReorderOperationEClass, PrimitiveListReorderOperation.class, "PrimitiveListReorderOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getPrimitiveListReorderOperation_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, PrimitiveListReorderOperation.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.primitiveListItemActionOperationEClass, PrimitiveListItemActionOperation.class, "PrimitiveListItemActionOperation", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getPrimitiveListItemActionOperation_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, PrimitiveListItemActionOperation.class, !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPrimitiveListItemActionOperation_IconURLExpression(), theViewPackage.getInterpretedExpression(), "iconURLExpression", null, 0, 1,
                PrimitiveListItemActionOperation.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPrimitiveListItemActionOperation_PreconditionExpression(), theViewPackage.getInterpretedExpression(), "preconditionExpression", null, 0, 1,
                PrimitiveListItemActionOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.monoReferenceWidgetDescriptionEClass, MonoReferenceWidgetDescription.class, "MonoReferenceWidgetDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getMonoReferenceWidgetDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "isEnabledExpression", null, 0, 1,
                MonoReferenceWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getMonoReferenceWidgetDescription_OwnerExpression(), theViewPackage.getInterpretedExpression(), "ownerExpression", null, 0, 1, MonoReferenceWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getMonoReferenceWidgetDescription_Type(), theViewPackage.getInterpretedExpression(), "type", null, 0, 1, MonoReferenceWidgetDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getMonoReferenceWidgetDescription_ValueExpression(), theViewPackage.getInterpretedExpression(), "valueExpression", null, 0, 1, MonoReferenceWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getMonoReferenceWidgetDescription_CandidatesSearchScopeExpression(), theViewPackage.getInterpretedExpression(), "candidatesSearchScopeExpression", null, 0, 1,
                MonoReferenceWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getMonoReferenceWidgetDescription_DropdownOptionsExpression(), theViewPackage.getInterpretedExpression(), "dropdownOptionsExpression", null, 0, 1,
                MonoReferenceWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getMonoReferenceWidgetDescription_CreateElementOperation(), this.getCreateElementInReferenceOperation(), null, "createElementOperation", null, 1, 1,
                MonoReferenceWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getMonoReferenceWidgetDescription_SetOperation(), this.getMonoReferenceSetOperation(), null, "setOperation", null, 1, 1, MonoReferenceWidgetDescription.class,
                !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getMonoReferenceWidgetDescription_UnsetOperation(), this.getMonoReferenceUnsetOperation(), null, "unsetOperation", null, 1, 1, MonoReferenceWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getMonoReferenceWidgetDescription_ClearOperation(), this.getClearReferenceOperation(), null, "clearOperation", null, 1, 1, MonoReferenceWidgetDescription.class,
                !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getMonoReferenceWidgetDescription_Style(), theReferencePackage.getReferenceWidgetDescriptionStyle(), null, "style", null, 0, 1, MonoReferenceWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getMonoReferenceWidgetDescription_ConditionalStyles(), theReferencePackage.getConditionalReferenceWidgetDescriptionStyle(), null, "conditionalStyles", null, 0, -1,
                MonoReferenceWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.multiReferenceWidgetDescriptionEClass, MultiReferenceWidgetDescription.class, "MultiReferenceWidgetDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getMultiReferenceWidgetDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "isEnabledExpression", null, 0, 1,
                MultiReferenceWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getMultiReferenceWidgetDescription_OwnerExpression(), theViewPackage.getInterpretedExpression(), "ownerExpression", null, 0, 1, MultiReferenceWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getMultiReferenceWidgetDescription_Type(), theViewPackage.getInterpretedExpression(), "type", null, 0, 1, MultiReferenceWidgetDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getMultiReferenceWidgetDescription_ValueExpression(), theViewPackage.getInterpretedExpression(), "valueExpression", null, 0, 1, MultiReferenceWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getMultiReferenceWidgetDescription_CandidatesSearchScopeExpression(), theViewPackage.getInterpretedExpression(), "candidatesSearchScopeExpression", null, 0, 1,
                MultiReferenceWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getMultiReferenceWidgetDescription_DropdownOptionsExpression(), theViewPackage.getInterpretedExpression(), "dropdownOptionsExpression", null, 0, 1,
                MultiReferenceWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getMultiReferenceWidgetDescription_CreateElementOperation(), this.getCreateElementInReferenceOperation(), null, "createElementOperation", null, 1, 1,
                MultiReferenceWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getMultiReferenceWidgetDescription_AddOperation(), this.getMultiReferenceAddOperation(), null, "addOperation", null, 1, 1, MultiReferenceWidgetDescription.class,
                !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getMultiReferenceWidgetDescription_RemoveOperation(), this.getMultiReferenceRemoveOperation(), null, "removeOperation", null, 1, 1,
                MultiReferenceWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getMultiReferenceWidgetDescription_ClearOperation(), this.getClearReferenceOperation(), null, "clearOperation", null, 1, 1, MultiReferenceWidgetDescription.class,
                !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getMultiReferenceWidgetDescription_ReorderOperation(), this.getMultiReferenceReorderOperation(), null, "reorderOperation", null, 0, 1,
                MultiReferenceWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getMultiReferenceWidgetDescription_Style(), theReferencePackage.getReferenceWidgetDescriptionStyle(), null, "style", null, 0, 1, MultiReferenceWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getMultiReferenceWidgetDescription_ConditionalStyles(), theReferencePackage.getConditionalReferenceWidgetDescriptionStyle(), null, "conditionalStyles", null, 0, -1,
                MultiReferenceWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.monoReferenceSetOperationEClass, MonoReferenceSetOperation.class, "MonoReferenceSetOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getMonoReferenceSetOperation_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, MonoReferenceSetOperation.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.monoReferenceUnsetOperationEClass, MonoReferenceUnsetOperation.class, "MonoReferenceUnsetOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getMonoReferenceUnsetOperation_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, MonoReferenceUnsetOperation.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.createElementInReferenceOperationEClass, CreateElementInReferenceOperation.class, "CreateElementInReferenceOperation", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getCreateElementInReferenceOperation_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, CreateElementInReferenceOperation.class, !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.clickReferenceValueOperationEClass, ClickReferenceValueOperation.class, "ClickReferenceValueOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getClickReferenceValueOperation_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, ClickReferenceValueOperation.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.multiReferenceAddOperationEClass, MultiReferenceAddOperation.class, "MultiReferenceAddOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getMultiReferenceAddOperation_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, MultiReferenceAddOperation.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.multiReferenceRemoveOperationEClass, MultiReferenceRemoveOperation.class, "MultiReferenceRemoveOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getMultiReferenceRemoveOperation_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, MultiReferenceRemoveOperation.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.clearReferenceOperationEClass, ClearReferenceOperation.class, "ClearReferenceOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getClearReferenceOperation_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, ClearReferenceOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.multiReferenceReorderOperationEClass, MultiReferenceReorderOperation.class, "MultiReferenceReorderOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getMultiReferenceReorderOperation_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, MultiReferenceReorderOperation.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.containmentReferenceWidgetDescriptionEClass, ContainmentReferenceWidgetDescription.class, "ContainmentReferenceWidgetDescription", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getContainmentReferenceWidgetDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "isEnabledExpression", null, 0, 1,
                ContainmentReferenceWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getContainmentReferenceWidgetDescription_Many(), this.ecorePackage.getEBoolean(), "many", null, 0, 1, ContainmentReferenceWidgetDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getContainmentReferenceWidgetDescription_Type(), theViewPackage.getInterpretedExpression(), "type", null, 0, 1, ContainmentReferenceWidgetDescription.class,
                !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getContainmentReferenceWidgetDescription_OwnerExpression(), theViewPackage.getInterpretedExpression(), "ownerExpression", null, 0, 1,
                ContainmentReferenceWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getContainmentReferenceWidgetDescription_ValueExpression(), theViewPackage.getInterpretedExpression(), "valueExpression", null, 0, 1,
                ContainmentReferenceWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getContainmentReferenceWidgetDescription_CreateElementOperation(), this.getCreateElementInReferenceOperation(), null, "createElementOperation", null, 1, 1,
                ContainmentReferenceWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getContainmentReferenceWidgetDescription_RemoveOperation(), this.getMultiReferenceRemoveOperation(), null, "removeOperation", null, 1, 1,
                ContainmentReferenceWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getContainmentReferenceWidgetDescription_ReorderOperation(), this.getMultiReferenceReorderOperation(), null, "reorderOperation", null, 0, 1,
                ContainmentReferenceWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getContainmentReferenceWidgetDescription_ClickOperation(), this.getClickReferenceValueOperation(), null, "clickOperation", null, 0, 1,
                ContainmentReferenceWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getContainmentReferenceWidgetDescription_Style(), theReferencePackage.getReferenceWidgetDescriptionStyle(), null, "style", null, 0, 1,
                ContainmentReferenceWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getContainmentReferenceWidgetDescription_ConditionalStyles(), theReferencePackage.getConditionalReferenceWidgetDescriptionStyle(), null, "conditionalStyles", null, 0,
                -1,
                ContainmentReferenceWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Create resource
        this.createResource(eNS_URI);
    }

} // PapyrusWidgetsPackageImpl
