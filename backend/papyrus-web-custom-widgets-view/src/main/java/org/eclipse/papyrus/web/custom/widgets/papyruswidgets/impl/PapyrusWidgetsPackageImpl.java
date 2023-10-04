/**
 * Copyright (c) 2023 CEA LIST, Obeo.
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
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.LanguageExpressionWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsFactory;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListAddOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListDeleteOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveRadioWidgetDescription;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.form.FormPackage;

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
    public EReference getPrimitiveListWidgetDescription_Style() {
        return (EReference) this.primitiveListWidgetDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPrimitiveListWidgetDescription_ConditionalStyles() {
        return (EReference) this.primitiveListWidgetDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPrimitiveListWidgetDescription_IsEnabledExpression() {
        return (EAttribute) this.primitiveListWidgetDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPrimitiveListWidgetDescription_DeleteOperation() {
        return (EReference) this.primitiveListWidgetDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPrimitiveListWidgetDescription_AddOperation() {
        return (EReference) this.primitiveListWidgetDescriptionEClass.getEStructuralFeatures().get(6);
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
        this.createEReference(this.primitiveListWidgetDescriptionEClass, PRIMITIVE_LIST_WIDGET_DESCRIPTION__STYLE);
        this.createEReference(this.primitiveListWidgetDescriptionEClass, PRIMITIVE_LIST_WIDGET_DESCRIPTION__CONDITIONAL_STYLES);
        this.createEAttribute(this.primitiveListWidgetDescriptionEClass, PRIMITIVE_LIST_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION);
        this.createEReference(this.primitiveListWidgetDescriptionEClass, PRIMITIVE_LIST_WIDGET_DESCRIPTION__DELETE_OPERATION);
        this.createEReference(this.primitiveListWidgetDescriptionEClass, PRIMITIVE_LIST_WIDGET_DESCRIPTION__ADD_OPERATION);

        this.primitiveListDeleteOperationEClass = this.createEClass(PRIMITIVE_LIST_DELETE_OPERATION);
        this.createEReference(this.primitiveListDeleteOperationEClass, PRIMITIVE_LIST_DELETE_OPERATION__BODY);

        this.primitiveListAddOperationEClass = this.createEClass(PRIMITIVE_LIST_ADD_OPERATION);
        this.createEReference(this.primitiveListAddOperationEClass, PRIMITIVE_LIST_ADD_OPERATION__BODY);
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

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        this.languageExpressionWidgetDescriptionEClass.getESuperTypes().add(theFormPackage.getWidgetDescription());
        this.primitiveRadioWidgetDescriptionEClass.getESuperTypes().add(theFormPackage.getWidgetDescription());
        this.primitiveListWidgetDescriptionEClass.getESuperTypes().add(theFormPackage.getWidgetDescription());

        // Initialize classes, features, and operations; add parameters
        this.initEClass(this.languageExpressionWidgetDescriptionEClass, LanguageExpressionWidgetDescription.class, "LanguageExpressionWidgetDescription", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getLanguageExpressionWidgetDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "isEnabledExpression", null, 0, 1,
                LanguageExpressionWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.primitiveRadioWidgetDescriptionEClass, PrimitiveRadioWidgetDescription.class, "PrimitiveRadioWidgetDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getPrimitiveRadioWidgetDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "isEnabledExpression", null, 0, 1,
                PrimitiveRadioWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPrimitiveRadioWidgetDescription_CandidatesExpression(), theViewPackage.getInterpretedExpression(), "candidatesExpression", null, 0, 1,
                PrimitiveRadioWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPrimitiveRadioWidgetDescription_ValueExpression(), theViewPackage.getInterpretedExpression(), "valueExpression", null, 0, 1, PrimitiveRadioWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPrimitiveRadioWidgetDescription_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, PrimitiveRadioWidgetDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.primitiveListWidgetDescriptionEClass, PrimitiveListWidgetDescription.class, "PrimitiveListWidgetDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getPrimitiveListWidgetDescription_ValueExpression(), theViewPackage.getInterpretedExpression(), "valueExpression", null, 0, 1, PrimitiveListWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPrimitiveListWidgetDescription_DisplayExpression(), theViewPackage.getInterpretedExpression(), "displayExpression", null, 0, 1,
                PrimitiveListWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPrimitiveListWidgetDescription_Style(), theFormPackage.getListDescriptionStyle(), null, "style", null, 0, 1, PrimitiveListWidgetDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPrimitiveListWidgetDescription_ConditionalStyles(), theFormPackage.getConditionalListDescriptionStyle(), null, "conditionalStyles", null, 0, -1,
                PrimitiveListWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPrimitiveListWidgetDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "isEnabledExpression", null, 0, 1,
                PrimitiveListWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPrimitiveListWidgetDescription_DeleteOperation(), this.getPrimitiveListDeleteOperation(), null, "deleteOperation", null, 0, 1, PrimitiveListWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPrimitiveListWidgetDescription_AddOperation(), this.getPrimitiveListAddOperation(), null, "addOperation", null, 0, 1, PrimitiveListWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.primitiveListDeleteOperationEClass, PrimitiveListDeleteOperation.class, "PrimitiveListDeleteOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getPrimitiveListDeleteOperation_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, PrimitiveListDeleteOperation.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.primitiveListAddOperationEClass, PrimitiveListAddOperation.class, "PrimitiveListAddOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getPrimitiveListAddOperation_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, PrimitiveListAddOperation.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Create resource
        this.createResource(eNS_URI);
    }

} // PapyrusWidgetsPackageImpl
