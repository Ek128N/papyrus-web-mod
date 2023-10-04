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
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListWidgetDescriptionImpl
     * <em>Primitive List Widget Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListWidgetDescriptionImpl
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getPrimitiveListWidgetDescription()
     * @generated
     */
    int PRIMITIVE_LIST_WIDGET_DESCRIPTION = 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_WIDGET_DESCRIPTION__NAME = FormPackage.WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_WIDGET_DESCRIPTION__LABEL_EXPRESSION = FormPackage.WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_WIDGET_DESCRIPTION__HELP_EXPRESSION = FormPackage.WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_WIDGET_DESCRIPTION__VALUE_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Display Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_WIDGET_DESCRIPTION__DISPLAY_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_WIDGET_DESCRIPTION__STYLE = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_WIDGET_DESCRIPTION__CONDITIONAL_STYLES = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Delete Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_WIDGET_DESCRIPTION__DELETE_OPERATION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Add Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_WIDGET_DESCRIPTION__ADD_OPERATION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Primitive List Widget Description</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_WIDGET_DESCRIPTION_FEATURE_COUNT = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Primitive List Widget Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_WIDGET_DESCRIPTION_OPERATION_COUNT = FormPackage.WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListDeleteOperationImpl <em>Primitive
     * List Delete Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListDeleteOperationImpl
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getPrimitiveListDeleteOperation()
     * @generated
     */
    int PRIMITIVE_LIST_DELETE_OPERATION = 3;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_DELETE_OPERATION__BODY = 0;

    /**
     * The number of structural features of the '<em>Primitive List Delete Operation</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_DELETE_OPERATION_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Primitive List Delete Operation</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_DELETE_OPERATION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListAddOperationImpl <em>Primitive
     * List Add Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListAddOperationImpl
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getPrimitiveListAddOperation()
     * @generated
     */
    int PRIMITIVE_LIST_ADD_OPERATION = 4;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_ADD_OPERATION__BODY = 0;

    /**
     * The number of structural features of the '<em>Primitive List Add Operation</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_ADD_OPERATION_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Primitive List Add Operation</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_ADD_OPERATION_OPERATION_COUNT = 0;

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
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription <em>Primitive List
     * Widget Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Primitive List Widget Description</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription
     * @generated
     */
    EClass getPrimitiveListWidgetDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getValueExpression
     * <em>Value Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getValueExpression()
     * @see #getPrimitiveListWidgetDescription()
     * @generated
     */
    EAttribute getPrimitiveListWidgetDescription_ValueExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getDisplayExpression
     * <em>Display Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Display Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getDisplayExpression()
     * @see #getPrimitiveListWidgetDescription()
     * @generated
     */
    EAttribute getPrimitiveListWidgetDescription_DisplayExpression();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getStyle
     * <em>Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getStyle()
     * @see #getPrimitiveListWidgetDescription()
     * @generated
     */
    EReference getPrimitiveListWidgetDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getConditionalStyles
     * <em>Conditional Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getConditionalStyles()
     * @see #getPrimitiveListWidgetDescription()
     * @generated
     */
    EReference getPrimitiveListWidgetDescription_ConditionalStyles();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getIsEnabledExpression
     * <em>Is Enabled Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Enabled Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getIsEnabledExpression()
     * @see #getPrimitiveListWidgetDescription()
     * @generated
     */
    EAttribute getPrimitiveListWidgetDescription_IsEnabledExpression();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getDeleteOperation
     * <em>Delete Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Delete Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getDeleteOperation()
     * @see #getPrimitiveListWidgetDescription()
     * @generated
     */
    EReference getPrimitiveListWidgetDescription_DeleteOperation();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getAddOperation
     * <em>Add Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Add Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getAddOperation()
     * @see #getPrimitiveListWidgetDescription()
     * @generated
     */
    EReference getPrimitiveListWidgetDescription_AddOperation();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListDeleteOperation <em>Primitive List
     * Delete Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Primitive List Delete Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListDeleteOperation
     * @generated
     */
    EClass getPrimitiveListDeleteOperation();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListDeleteOperation#getBody
     * <em>Body</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListDeleteOperation#getBody()
     * @see #getPrimitiveListDeleteOperation()
     * @generated
     */
    EReference getPrimitiveListDeleteOperation_Body();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListAddOperation <em>Primitive List Add
     * Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Primitive List Add Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListAddOperation
     * @generated
     */
    EClass getPrimitiveListAddOperation();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListAddOperation#getBody <em>Body</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListAddOperation#getBody()
     * @see #getPrimitiveListAddOperation()
     * @generated
     */
    EReference getPrimitiveListAddOperation_Body();

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

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListWidgetDescriptionImpl
         * <em>Primitive List Widget Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListWidgetDescriptionImpl
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getPrimitiveListWidgetDescription()
         * @generated
         */
        EClass PRIMITIVE_LIST_WIDGET_DESCRIPTION = eINSTANCE.getPrimitiveListWidgetDescription();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute PRIMITIVE_LIST_WIDGET_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getPrimitiveListWidgetDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Display Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute PRIMITIVE_LIST_WIDGET_DESCRIPTION__DISPLAY_EXPRESSION = eINSTANCE.getPrimitiveListWidgetDescription_DisplayExpression();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PRIMITIVE_LIST_WIDGET_DESCRIPTION__STYLE = eINSTANCE.getPrimitiveListWidgetDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PRIMITIVE_LIST_WIDGET_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getPrimitiveListWidgetDescription_ConditionalStyles();

        /**
         * The meta object literal for the '<em><b>Is Enabled Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute PRIMITIVE_LIST_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION = eINSTANCE.getPrimitiveListWidgetDescription_IsEnabledExpression();

        /**
         * The meta object literal for the '<em><b>Delete Operation</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PRIMITIVE_LIST_WIDGET_DESCRIPTION__DELETE_OPERATION = eINSTANCE.getPrimitiveListWidgetDescription_DeleteOperation();

        /**
         * The meta object literal for the '<em><b>Add Operation</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PRIMITIVE_LIST_WIDGET_DESCRIPTION__ADD_OPERATION = eINSTANCE.getPrimitiveListWidgetDescription_AddOperation();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListDeleteOperationImpl
         * <em>Primitive List Delete Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListDeleteOperationImpl
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getPrimitiveListDeleteOperation()
         * @generated
         */
        EClass PRIMITIVE_LIST_DELETE_OPERATION = eINSTANCE.getPrimitiveListDeleteOperation();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PRIMITIVE_LIST_DELETE_OPERATION__BODY = eINSTANCE.getPrimitiveListDeleteOperation_Body();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListAddOperationImpl
         * <em>Primitive List Add Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListAddOperationImpl
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getPrimitiveListAddOperation()
         * @generated
         */
        EClass PRIMITIVE_LIST_ADD_OPERATION = eINSTANCE.getPrimitiveListAddOperation();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PRIMITIVE_LIST_ADD_OPERATION__BODY = eINSTANCE.getPrimitiveListAddOperation_Body();

    }

} // PapyrusWidgetsPackage
