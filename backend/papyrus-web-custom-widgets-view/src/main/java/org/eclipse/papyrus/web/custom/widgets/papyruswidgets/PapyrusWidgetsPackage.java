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
package org.eclipse.papyrus.web.custom.widgets.papyruswidgets;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.components.view.form.FormPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsFactory
 * @model kind="package"
 * @generated
 */
public interface PapyrusWidgetsPackage extends EPackage {
    /**
     * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNAME = "papyruswidgets";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "https://www.eclipse.org/papyrus/widgets/";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "papyruswidgets";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    PapyrusWidgetsPackage eINSTANCE = org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl.init();

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.LanguageExpressionWidgetDescriptionImpl
     * <em>Language Expression Widget Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.LanguageExpressionWidgetDescriptionImpl
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getLanguageExpressionWidgetDescription()
     * @generated
     */
    int LANGUAGE_EXPRESSION_WIDGET_DESCRIPTION = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LANGUAGE_EXPRESSION_WIDGET_DESCRIPTION__NAME = FormPackage.WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LANGUAGE_EXPRESSION_WIDGET_DESCRIPTION__LABEL_EXPRESSION = FormPackage.WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LANGUAGE_EXPRESSION_WIDGET_DESCRIPTION__HELP_EXPRESSION = FormPackage.WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LANGUAGE_EXPRESSION_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Language Expression Widget Description</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LANGUAGE_EXPRESSION_WIDGET_DESCRIPTION_FEATURE_COUNT = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Language Expression Widget Description</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LANGUAGE_EXPRESSION_WIDGET_DESCRIPTION_OPERATION_COUNT = FormPackage.WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveRadioWidgetDescriptionImpl
     * <em>Primitive Radio Widget Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveRadioWidgetDescriptionImpl
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getPrimitiveRadioWidgetDescription()
     * @generated
     */
    int PRIMITIVE_RADIO_WIDGET_DESCRIPTION = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_RADIO_WIDGET_DESCRIPTION__NAME = FormPackage.WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_RADIO_WIDGET_DESCRIPTION__LABEL_EXPRESSION = FormPackage.WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_RADIO_WIDGET_DESCRIPTION__HELP_EXPRESSION = FormPackage.WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_RADIO_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_RADIO_WIDGET_DESCRIPTION__CANDIDATES_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_RADIO_WIDGET_DESCRIPTION__VALUE_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_RADIO_WIDGET_DESCRIPTION__BODY = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Primitive Radio Widget Description</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_RADIO_WIDGET_DESCRIPTION_FEATURE_COUNT = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The number of operations of the '<em>Primitive Radio Widget Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_RADIO_WIDGET_DESCRIPTION_OPERATION_COUNT = FormPackage.WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.LanguageExpressionWidgetDescription <em>Language
     * Expression Widget Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Language Expression Widget Description</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.LanguageExpressionWidgetDescription
     * @generated
     */
    EClass getLanguageExpressionWidgetDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.LanguageExpressionWidgetDescription#getIsEnabledExpression
     * <em>Is Enabled Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Enabled Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.LanguageExpressionWidgetDescription#getIsEnabledExpression()
     * @see #getLanguageExpressionWidgetDescription()
     * @generated
     */
    EAttribute getLanguageExpressionWidgetDescription_IsEnabledExpression();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveRadioWidgetDescription <em>Primitive Radio
     * Widget Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Primitive Radio Widget Description</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveRadioWidgetDescription
     * @generated
     */
    EClass getPrimitiveRadioWidgetDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveRadioWidgetDescription#getIsEnabledExpression
     * <em>Is Enabled Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Enabled Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveRadioWidgetDescription#getIsEnabledExpression()
     * @see #getPrimitiveRadioWidgetDescription()
     * @generated
     */
    EAttribute getPrimitiveRadioWidgetDescription_IsEnabledExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveRadioWidgetDescription#getCandidatesExpression
     * <em>Candidates Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Candidates Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveRadioWidgetDescription#getCandidatesExpression()
     * @see #getPrimitiveRadioWidgetDescription()
     * @generated
     */
    EAttribute getPrimitiveRadioWidgetDescription_CandidatesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveRadioWidgetDescription#getValueExpression
     * <em>Value Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveRadioWidgetDescription#getValueExpression()
     * @see #getPrimitiveRadioWidgetDescription()
     * @generated
     */
    EAttribute getPrimitiveRadioWidgetDescription_ValueExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveRadioWidgetDescription#getBody
     * <em>Body</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveRadioWidgetDescription#getBody()
     * @see #getPrimitiveRadioWidgetDescription()
     * @generated
     */
    EReference getPrimitiveRadioWidgetDescription_Body();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    PapyrusWidgetsFactory getPapyrusWidgetsFactory();

    /**
     * <!-- begin-user-doc --> Defines literals for the meta objects that represent
     * <ul>
     * <li>each class,</li>
     * <li>each feature of each class,</li>
     * <li>each operation of each class,</li>
     * <li>each enum,</li>
     * <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     *
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.LanguageExpressionWidgetDescriptionImpl
         * <em>Language Expression Widget Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.LanguageExpressionWidgetDescriptionImpl
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getLanguageExpressionWidgetDescription()
         * @generated
         */
        EClass LANGUAGE_EXPRESSION_WIDGET_DESCRIPTION = eINSTANCE.getLanguageExpressionWidgetDescription();

        /**
         * The meta object literal for the '<em><b>Is Enabled Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LANGUAGE_EXPRESSION_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION = eINSTANCE.getLanguageExpressionWidgetDescription_IsEnabledExpression();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveRadioWidgetDescriptionImpl
         * <em>Primitive Radio Widget Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveRadioWidgetDescriptionImpl
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getPrimitiveRadioWidgetDescription()
         * @generated
         */
        EClass PRIMITIVE_RADIO_WIDGET_DESCRIPTION = eINSTANCE.getPrimitiveRadioWidgetDescription();

        /**
         * The meta object literal for the '<em><b>Is Enabled Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute PRIMITIVE_RADIO_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION = eINSTANCE.getPrimitiveRadioWidgetDescription_IsEnabledExpression();

        /**
         * The meta object literal for the '<em><b>Candidates Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute PRIMITIVE_RADIO_WIDGET_DESCRIPTION__CANDIDATES_EXPRESSION = eINSTANCE.getPrimitiveRadioWidgetDescription_CandidatesExpression();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute PRIMITIVE_RADIO_WIDGET_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getPrimitiveRadioWidgetDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PRIMITIVE_RADIO_WIDGET_DESCRIPTION__BODY = eINSTANCE.getPrimitiveRadioWidgetDescription_Body();

    }

} // PapyrusWidgetsPackage
