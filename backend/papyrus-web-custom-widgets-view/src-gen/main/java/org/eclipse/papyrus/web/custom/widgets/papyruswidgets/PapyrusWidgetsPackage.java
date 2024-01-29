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
    String eNS_URI = "http://www.eclipse.org/papyrus-web/widgets/";

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
     * The feature id for the '<em><b>Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_WIDGET_DESCRIPTION__CANDIDATES_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_WIDGET_DESCRIPTION__STYLE = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_WIDGET_DESCRIPTION__CONDITIONAL_STYLES = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Delete Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_WIDGET_DESCRIPTION__DELETE_OPERATION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Add Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_WIDGET_DESCRIPTION__ADD_OPERATION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Reorder Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_WIDGET_DESCRIPTION__REORDER_OPERATION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Item Action Operation</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_WIDGET_DESCRIPTION__ITEM_ACTION_OPERATION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 9;

    /**
     * The number of structural features of the '<em>Primitive List Widget Description</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_WIDGET_DESCRIPTION_FEATURE_COUNT = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 10;

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
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListReorderOperationImpl
     * <em>Primitive List Reorder Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListReorderOperationImpl
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getPrimitiveListReorderOperation()
     * @generated
     */
    int PRIMITIVE_LIST_REORDER_OPERATION = 5;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_REORDER_OPERATION__BODY = 0;

    /**
     * The number of structural features of the '<em>Primitive List Reorder Operation</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_REORDER_OPERATION_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Primitive List Reorder Operation</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_REORDER_OPERATION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListItemActionOperationImpl
     * <em>Primitive List Item Action Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListItemActionOperationImpl
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getPrimitiveListItemActionOperation()
     * @generated
     */
    int PRIMITIVE_LIST_ITEM_ACTION_OPERATION = 6;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_ITEM_ACTION_OPERATION__BODY = 0;

    /**
     * The feature id for the '<em><b>Icon URL Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_ITEM_ACTION_OPERATION__ICON_URL_EXPRESSION = 1;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_ITEM_ACTION_OPERATION__PRECONDITION_EXPRESSION = 2;

    /**
     * The number of structural features of the '<em>Primitive List Item Action Operation</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_ITEM_ACTION_OPERATION_FEATURE_COUNT = 3;

    /**
     * The number of operations of the '<em>Primitive List Item Action Operation</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PRIMITIVE_LIST_ITEM_ACTION_OPERATION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceWidgetDescriptionImpl <em>Mono
     * Reference Widget Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceWidgetDescriptionImpl
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getMonoReferenceWidgetDescription()
     * @generated
     */
    int MONO_REFERENCE_WIDGET_DESCRIPTION = 7;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_WIDGET_DESCRIPTION__NAME = FormPackage.WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_WIDGET_DESCRIPTION__LABEL_EXPRESSION = FormPackage.WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_WIDGET_DESCRIPTION__HELP_EXPRESSION = FormPackage.WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Owner Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_WIDGET_DESCRIPTION__TYPE = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Candidates Search Scope Expression</b></em>' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_WIDGET_DESCRIPTION__CANDIDATES_SEARCH_SCOPE_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Dropdown Options Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_WIDGET_DESCRIPTION__DROPDOWN_OPTIONS_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Create Element Operation</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Set Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_WIDGET_DESCRIPTION__SET_OPERATION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Unset Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_WIDGET_DESCRIPTION__UNSET_OPERATION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Clear Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_WIDGET_DESCRIPTION__STYLE = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 10;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 11;

    /**
     * The number of structural features of the '<em>Mono Reference Widget Description</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_WIDGET_DESCRIPTION_FEATURE_COUNT = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 12;

    /**
     * The number of operations of the '<em>Mono Reference Widget Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_WIDGET_DESCRIPTION_OPERATION_COUNT = FormPackage.WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceWidgetDescriptionImpl <em>Multi
     * Reference Widget Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceWidgetDescriptionImpl
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getMultiReferenceWidgetDescription()
     * @generated
     */
    int MULTI_REFERENCE_WIDGET_DESCRIPTION = 8;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_WIDGET_DESCRIPTION__NAME = FormPackage.WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_WIDGET_DESCRIPTION__LABEL_EXPRESSION = FormPackage.WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_WIDGET_DESCRIPTION__HELP_EXPRESSION = FormPackage.WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Owner Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_WIDGET_DESCRIPTION__TYPE = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Candidates Search Scope Expression</b></em>' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_WIDGET_DESCRIPTION__CANDIDATES_SEARCH_SCOPE_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Dropdown Options Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_WIDGET_DESCRIPTION__DROPDOWN_OPTIONS_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Create Element Operation</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Add Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_WIDGET_DESCRIPTION__ADD_OPERATION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Remove Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Clear Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Reorder Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 10;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_WIDGET_DESCRIPTION__STYLE = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 11;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 12;

    /**
     * The number of structural features of the '<em>Multi Reference Widget Description</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_WIDGET_DESCRIPTION_FEATURE_COUNT = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 13;

    /**
     * The number of operations of the '<em>Multi Reference Widget Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_WIDGET_DESCRIPTION_OPERATION_COUNT = FormPackage.WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceSetOperationImpl <em>Mono
     * Reference Set Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceSetOperationImpl
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getMonoReferenceSetOperation()
     * @generated
     */
    int MONO_REFERENCE_SET_OPERATION = 9;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_SET_OPERATION__BODY = 0;

    /**
     * The number of structural features of the '<em>Mono Reference Set Operation</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_SET_OPERATION_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Mono Reference Set Operation</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_SET_OPERATION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceUnsetOperationImpl <em>Mono
     * Reference Unset Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceUnsetOperationImpl
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getMonoReferenceUnsetOperation()
     * @generated
     */
    int MONO_REFERENCE_UNSET_OPERATION = 10;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_UNSET_OPERATION__BODY = 0;

    /**
     * The number of structural features of the '<em>Mono Reference Unset Operation</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_UNSET_OPERATION_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Mono Reference Unset Operation</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MONO_REFERENCE_UNSET_OPERATION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.CreateElementInReferenceOperationImpl
     * <em>Create Element In Reference Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.CreateElementInReferenceOperationImpl
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getCreateElementInReferenceOperation()
     * @generated
     */
    int CREATE_ELEMENT_IN_REFERENCE_OPERATION = 11;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_ELEMENT_IN_REFERENCE_OPERATION__BODY = 0;

    /**
     * The number of structural features of the '<em>Create Element In Reference Operation</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_ELEMENT_IN_REFERENCE_OPERATION_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Create Element In Reference Operation</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_ELEMENT_IN_REFERENCE_OPERATION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ClickReferenceValueOperationImpl <em>Click
     * Reference Value Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ClickReferenceValueOperationImpl
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getClickReferenceValueOperation()
     * @generated
     */
    int CLICK_REFERENCE_VALUE_OPERATION = 12;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CLICK_REFERENCE_VALUE_OPERATION__BODY = 0;

    /**
     * The number of structural features of the '<em>Click Reference Value Operation</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CLICK_REFERENCE_VALUE_OPERATION_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Click Reference Value Operation</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CLICK_REFERENCE_VALUE_OPERATION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceAddOperationImpl <em>Multi
     * Reference Add Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceAddOperationImpl
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getMultiReferenceAddOperation()
     * @generated
     */
    int MULTI_REFERENCE_ADD_OPERATION = 13;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_ADD_OPERATION__BODY = 0;

    /**
     * The number of structural features of the '<em>Multi Reference Add Operation</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_ADD_OPERATION_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Multi Reference Add Operation</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_ADD_OPERATION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceRemoveOperationImpl <em>Multi
     * Reference Remove Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceRemoveOperationImpl
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getMultiReferenceRemoveOperation()
     * @generated
     */
    int MULTI_REFERENCE_REMOVE_OPERATION = 14;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_REMOVE_OPERATION__BODY = 0;

    /**
     * The number of structural features of the '<em>Multi Reference Remove Operation</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_REMOVE_OPERATION_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Multi Reference Remove Operation</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_REMOVE_OPERATION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ClearReferenceOperationImpl <em>Clear
     * Reference Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ClearReferenceOperationImpl
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getClearReferenceOperation()
     * @generated
     */
    int CLEAR_REFERENCE_OPERATION = 15;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CLEAR_REFERENCE_OPERATION__BODY = 0;

    /**
     * The number of structural features of the '<em>Clear Reference Operation</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CLEAR_REFERENCE_OPERATION_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Clear Reference Operation</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CLEAR_REFERENCE_OPERATION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceReorderOperationImpl <em>Multi
     * Reference Reorder Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceReorderOperationImpl
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getMultiReferenceReorderOperation()
     * @generated
     */
    int MULTI_REFERENCE_REORDER_OPERATION = 16;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_REORDER_OPERATION__BODY = 0;

    /**
     * The number of structural features of the '<em>Multi Reference Reorder Operation</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_REORDER_OPERATION_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Multi Reference Reorder Operation</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_REFERENCE_REORDER_OPERATION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ContainmentReferenceWidgetDescriptionImpl
     * <em>Containment Reference Widget Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ContainmentReferenceWidgetDescriptionImpl
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getContainmentReferenceWidgetDescription()
     * @generated
     */
    int CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION = 17;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__NAME = FormPackage.WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__LABEL_EXPRESSION = FormPackage.WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__HELP_EXPRESSION = FormPackage.WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Many</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__MANY = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__TYPE = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Owner Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Create Element Operation</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Remove Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Reorder Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Click Operation</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CLICK_OPERATION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__STYLE = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 10;

    /**
     * The number of structural features of the '<em>Containment Reference Widget Description</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION_FEATURE_COUNT = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 11;

    /**
     * The number of operations of the '<em>Containment Reference Widget Description</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION_OPERATION_COUNT = FormPackage.WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

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
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getCandidatesExpression
     * <em>Candidates Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Candidates Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getCandidatesExpression()
     * @see #getPrimitiveListWidgetDescription()
     * @generated
     */
    EAttribute getPrimitiveListWidgetDescription_CandidatesExpression();

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
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getReorderOperation
     * <em>Reorder Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Reorder Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getReorderOperation()
     * @see #getPrimitiveListWidgetDescription()
     * @generated
     */
    EReference getPrimitiveListWidgetDescription_ReorderOperation();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getItemActionOperation
     * <em>Item Action Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Item Action Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription#getItemActionOperation()
     * @see #getPrimitiveListWidgetDescription()
     * @generated
     */
    EReference getPrimitiveListWidgetDescription_ItemActionOperation();

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
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListReorderOperation <em>Primitive List
     * Reorder Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Primitive List Reorder Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListReorderOperation
     * @generated
     */
    EClass getPrimitiveListReorderOperation();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListReorderOperation#getBody
     * <em>Body</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListReorderOperation#getBody()
     * @see #getPrimitiveListReorderOperation()
     * @generated
     */
    EReference getPrimitiveListReorderOperation_Body();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListItemActionOperation <em>Primitive List
     * Item Action Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Primitive List Item Action Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListItemActionOperation
     * @generated
     */
    EClass getPrimitiveListItemActionOperation();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListItemActionOperation#getBody
     * <em>Body</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListItemActionOperation#getBody()
     * @see #getPrimitiveListItemActionOperation()
     * @generated
     */
    EReference getPrimitiveListItemActionOperation_Body();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListItemActionOperation#getIconURLExpression
     * <em>Icon URL Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Icon URL Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListItemActionOperation#getIconURLExpression()
     * @see #getPrimitiveListItemActionOperation()
     * @generated
     */
    EAttribute getPrimitiveListItemActionOperation_IconURLExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListItemActionOperation#getPreconditionExpression
     * <em>Precondition Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Precondition Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListItemActionOperation#getPreconditionExpression()
     * @see #getPrimitiveListItemActionOperation()
     * @generated
     */
    EAttribute getPrimitiveListItemActionOperation_PreconditionExpression();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription <em>Mono Reference
     * Widget Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Mono Reference Widget Description</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription
     * @generated
     */
    EClass getMonoReferenceWidgetDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getIsEnabledExpression
     * <em>Is Enabled Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Enabled Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getIsEnabledExpression()
     * @see #getMonoReferenceWidgetDescription()
     * @generated
     */
    EAttribute getMonoReferenceWidgetDescription_IsEnabledExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getOwnerExpression
     * <em>Owner Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Owner Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getOwnerExpression()
     * @see #getMonoReferenceWidgetDescription()
     * @generated
     */
    EAttribute getMonoReferenceWidgetDescription_OwnerExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getType
     * <em>Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getType()
     * @see #getMonoReferenceWidgetDescription()
     * @generated
     */
    EAttribute getMonoReferenceWidgetDescription_Type();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getValueExpression
     * <em>Value Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getValueExpression()
     * @see #getMonoReferenceWidgetDescription()
     * @generated
     */
    EAttribute getMonoReferenceWidgetDescription_ValueExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getCandidatesSearchScopeExpression
     * <em>Candidates Search Scope Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Candidates Search Scope Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getCandidatesSearchScopeExpression()
     * @see #getMonoReferenceWidgetDescription()
     * @generated
     */
    EAttribute getMonoReferenceWidgetDescription_CandidatesSearchScopeExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getDropdownOptionsExpression
     * <em>Dropdown Options Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Dropdown Options Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getDropdownOptionsExpression()
     * @see #getMonoReferenceWidgetDescription()
     * @generated
     */
    EAttribute getMonoReferenceWidgetDescription_DropdownOptionsExpression();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getCreateElementOperation
     * <em>Create Element Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Create Element Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getCreateElementOperation()
     * @see #getMonoReferenceWidgetDescription()
     * @generated
     */
    EReference getMonoReferenceWidgetDescription_CreateElementOperation();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getSetOperation
     * <em>Set Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Set Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getSetOperation()
     * @see #getMonoReferenceWidgetDescription()
     * @generated
     */
    EReference getMonoReferenceWidgetDescription_SetOperation();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getUnsetOperation
     * <em>Unset Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Unset Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getUnsetOperation()
     * @see #getMonoReferenceWidgetDescription()
     * @generated
     */
    EReference getMonoReferenceWidgetDescription_UnsetOperation();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getClearOperation
     * <em>Clear Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Clear Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getClearOperation()
     * @see #getMonoReferenceWidgetDescription()
     * @generated
     */
    EReference getMonoReferenceWidgetDescription_ClearOperation();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getStyle
     * <em>Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getStyle()
     * @see #getMonoReferenceWidgetDescription()
     * @generated
     */
    EReference getMonoReferenceWidgetDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getConditionalStyles
     * <em>Conditional Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription#getConditionalStyles()
     * @see #getMonoReferenceWidgetDescription()
     * @generated
     */
    EReference getMonoReferenceWidgetDescription_ConditionalStyles();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription <em>Multi Reference
     * Widget Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Multi Reference Widget Description</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription
     * @generated
     */
    EClass getMultiReferenceWidgetDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getIsEnabledExpression
     * <em>Is Enabled Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Enabled Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getIsEnabledExpression()
     * @see #getMultiReferenceWidgetDescription()
     * @generated
     */
    EAttribute getMultiReferenceWidgetDescription_IsEnabledExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getOwnerExpression
     * <em>Owner Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Owner Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getOwnerExpression()
     * @see #getMultiReferenceWidgetDescription()
     * @generated
     */
    EAttribute getMultiReferenceWidgetDescription_OwnerExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getType
     * <em>Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getType()
     * @see #getMultiReferenceWidgetDescription()
     * @generated
     */
    EAttribute getMultiReferenceWidgetDescription_Type();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getValueExpression
     * <em>Value Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getValueExpression()
     * @see #getMultiReferenceWidgetDescription()
     * @generated
     */
    EAttribute getMultiReferenceWidgetDescription_ValueExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getCandidatesSearchScopeExpression
     * <em>Candidates Search Scope Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Candidates Search Scope Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getCandidatesSearchScopeExpression()
     * @see #getMultiReferenceWidgetDescription()
     * @generated
     */
    EAttribute getMultiReferenceWidgetDescription_CandidatesSearchScopeExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getDropdownOptionsExpression
     * <em>Dropdown Options Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Dropdown Options Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getDropdownOptionsExpression()
     * @see #getMultiReferenceWidgetDescription()
     * @generated
     */
    EAttribute getMultiReferenceWidgetDescription_DropdownOptionsExpression();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getCreateElementOperation
     * <em>Create Element Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Create Element Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getCreateElementOperation()
     * @see #getMultiReferenceWidgetDescription()
     * @generated
     */
    EReference getMultiReferenceWidgetDescription_CreateElementOperation();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getAddOperation
     * <em>Add Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Add Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getAddOperation()
     * @see #getMultiReferenceWidgetDescription()
     * @generated
     */
    EReference getMultiReferenceWidgetDescription_AddOperation();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getRemoveOperation
     * <em>Remove Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Remove Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getRemoveOperation()
     * @see #getMultiReferenceWidgetDescription()
     * @generated
     */
    EReference getMultiReferenceWidgetDescription_RemoveOperation();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getClearOperation
     * <em>Clear Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Clear Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getClearOperation()
     * @see #getMultiReferenceWidgetDescription()
     * @generated
     */
    EReference getMultiReferenceWidgetDescription_ClearOperation();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getReorderOperation
     * <em>Reorder Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Reorder Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getReorderOperation()
     * @see #getMultiReferenceWidgetDescription()
     * @generated
     */
    EReference getMultiReferenceWidgetDescription_ReorderOperation();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getStyle
     * <em>Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getStyle()
     * @see #getMultiReferenceWidgetDescription()
     * @generated
     */
    EReference getMultiReferenceWidgetDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getConditionalStyles
     * <em>Conditional Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription#getConditionalStyles()
     * @see #getMultiReferenceWidgetDescription()
     * @generated
     */
    EReference getMultiReferenceWidgetDescription_ConditionalStyles();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceSetOperation <em>Mono Reference Set
     * Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Mono Reference Set Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceSetOperation
     * @generated
     */
    EClass getMonoReferenceSetOperation();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceSetOperation#getBody <em>Body</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceSetOperation#getBody()
     * @see #getMonoReferenceSetOperation()
     * @generated
     */
    EReference getMonoReferenceSetOperation_Body();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceUnsetOperation <em>Mono Reference
     * Unset Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Mono Reference Unset Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceUnsetOperation
     * @generated
     */
    EClass getMonoReferenceUnsetOperation();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceUnsetOperation#getBody
     * <em>Body</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceUnsetOperation#getBody()
     * @see #getMonoReferenceUnsetOperation()
     * @generated
     */
    EReference getMonoReferenceUnsetOperation_Body();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CreateElementInReferenceOperation <em>Create
     * Element In Reference Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Create Element In Reference Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CreateElementInReferenceOperation
     * @generated
     */
    EClass getCreateElementInReferenceOperation();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CreateElementInReferenceOperation#getBody
     * <em>Body</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CreateElementInReferenceOperation#getBody()
     * @see #getCreateElementInReferenceOperation()
     * @generated
     */
    EReference getCreateElementInReferenceOperation_Body();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ClickReferenceValueOperation <em>Click Reference
     * Value Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Click Reference Value Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ClickReferenceValueOperation
     * @generated
     */
    EClass getClickReferenceValueOperation();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ClickReferenceValueOperation#getBody
     * <em>Body</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ClickReferenceValueOperation#getBody()
     * @see #getClickReferenceValueOperation()
     * @generated
     */
    EReference getClickReferenceValueOperation_Body();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceAddOperation <em>Multi Reference Add
     * Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Multi Reference Add Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceAddOperation
     * @generated
     */
    EClass getMultiReferenceAddOperation();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceAddOperation#getBody <em>Body</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceAddOperation#getBody()
     * @see #getMultiReferenceAddOperation()
     * @generated
     */
    EReference getMultiReferenceAddOperation_Body();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceRemoveOperation <em>Multi Reference
     * Remove Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Multi Reference Remove Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceRemoveOperation
     * @generated
     */
    EClass getMultiReferenceRemoveOperation();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceRemoveOperation#getBody
     * <em>Body</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceRemoveOperation#getBody()
     * @see #getMultiReferenceRemoveOperation()
     * @generated
     */
    EReference getMultiReferenceRemoveOperation_Body();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ClearReferenceOperation <em>Clear Reference
     * Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Clear Reference Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ClearReferenceOperation
     * @generated
     */
    EClass getClearReferenceOperation();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ClearReferenceOperation#getBody <em>Body</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ClearReferenceOperation#getBody()
     * @see #getClearReferenceOperation()
     * @generated
     */
    EReference getClearReferenceOperation_Body();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceReorderOperation <em>Multi Reference
     * Reorder Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Multi Reference Reorder Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceReorderOperation
     * @generated
     */
    EClass getMultiReferenceReorderOperation();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceReorderOperation#getBody
     * <em>Body</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceReorderOperation#getBody()
     * @see #getMultiReferenceReorderOperation()
     * @generated
     */
    EReference getMultiReferenceReorderOperation_Body();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription
     * <em>Containment Reference Widget Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Containment Reference Widget Description</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription
     * @generated
     */
    EClass getContainmentReferenceWidgetDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription#getIsEnabledExpression
     * <em>Is Enabled Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Enabled Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription#getIsEnabledExpression()
     * @see #getContainmentReferenceWidgetDescription()
     * @generated
     */
    EAttribute getContainmentReferenceWidgetDescription_IsEnabledExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription#isMany
     * <em>Many</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Many</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription#isMany()
     * @see #getContainmentReferenceWidgetDescription()
     * @generated
     */
    EAttribute getContainmentReferenceWidgetDescription_Many();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription#getType
     * <em>Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription#getType()
     * @see #getContainmentReferenceWidgetDescription()
     * @generated
     */
    EAttribute getContainmentReferenceWidgetDescription_Type();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription#getOwnerExpression
     * <em>Owner Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Owner Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription#getOwnerExpression()
     * @see #getContainmentReferenceWidgetDescription()
     * @generated
     */
    EAttribute getContainmentReferenceWidgetDescription_OwnerExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription#getValueExpression
     * <em>Value Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription#getValueExpression()
     * @see #getContainmentReferenceWidgetDescription()
     * @generated
     */
    EAttribute getContainmentReferenceWidgetDescription_ValueExpression();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription#getCreateElementOperation
     * <em>Create Element Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Create Element Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription#getCreateElementOperation()
     * @see #getContainmentReferenceWidgetDescription()
     * @generated
     */
    EReference getContainmentReferenceWidgetDescription_CreateElementOperation();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription#getRemoveOperation
     * <em>Remove Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Remove Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription#getRemoveOperation()
     * @see #getContainmentReferenceWidgetDescription()
     * @generated
     */
    EReference getContainmentReferenceWidgetDescription_RemoveOperation();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription#getReorderOperation
     * <em>Reorder Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Reorder Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription#getReorderOperation()
     * @see #getContainmentReferenceWidgetDescription()
     * @generated
     */
    EReference getContainmentReferenceWidgetDescription_ReorderOperation();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription#getClickOperation
     * <em>Click Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Click Operation</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription#getClickOperation()
     * @see #getContainmentReferenceWidgetDescription()
     * @generated
     */
    EReference getContainmentReferenceWidgetDescription_ClickOperation();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription#getStyle
     * <em>Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription#getStyle()
     * @see #getContainmentReferenceWidgetDescription()
     * @generated
     */
    EReference getContainmentReferenceWidgetDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription#getConditionalStyles
     * <em>Conditional Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription#getConditionalStyles()
     * @see #getContainmentReferenceWidgetDescription()
     * @generated
     */
    EReference getContainmentReferenceWidgetDescription_ConditionalStyles();

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
         * The meta object literal for the '<em><b>Candidates Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute PRIMITIVE_LIST_WIDGET_DESCRIPTION__CANDIDATES_EXPRESSION = eINSTANCE.getPrimitiveListWidgetDescription_CandidatesExpression();

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
         * The meta object literal for the '<em><b>Delete Operation</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PRIMITIVE_LIST_WIDGET_DESCRIPTION__DELETE_OPERATION = eINSTANCE.getPrimitiveListWidgetDescription_DeleteOperation();

        /**
         * The meta object literal for the '<em><b>Add Operation</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PRIMITIVE_LIST_WIDGET_DESCRIPTION__ADD_OPERATION = eINSTANCE.getPrimitiveListWidgetDescription_AddOperation();

        /**
         * The meta object literal for the '<em><b>Reorder Operation</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PRIMITIVE_LIST_WIDGET_DESCRIPTION__REORDER_OPERATION = eINSTANCE.getPrimitiveListWidgetDescription_ReorderOperation();

        /**
         * The meta object literal for the '<em><b>Item Action Operation</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PRIMITIVE_LIST_WIDGET_DESCRIPTION__ITEM_ACTION_OPERATION = eINSTANCE.getPrimitiveListWidgetDescription_ItemActionOperation();

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

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListReorderOperationImpl
         * <em>Primitive List Reorder Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListReorderOperationImpl
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getPrimitiveListReorderOperation()
         * @generated
         */
        EClass PRIMITIVE_LIST_REORDER_OPERATION = eINSTANCE.getPrimitiveListReorderOperation();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PRIMITIVE_LIST_REORDER_OPERATION__BODY = eINSTANCE.getPrimitiveListReorderOperation_Body();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListItemActionOperationImpl
         * <em>Primitive List Item Action Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PrimitiveListItemActionOperationImpl
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getPrimitiveListItemActionOperation()
         * @generated
         */
        EClass PRIMITIVE_LIST_ITEM_ACTION_OPERATION = eINSTANCE.getPrimitiveListItemActionOperation();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PRIMITIVE_LIST_ITEM_ACTION_OPERATION__BODY = eINSTANCE.getPrimitiveListItemActionOperation_Body();

        /**
         * The meta object literal for the '<em><b>Icon URL Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute PRIMITIVE_LIST_ITEM_ACTION_OPERATION__ICON_URL_EXPRESSION = eINSTANCE.getPrimitiveListItemActionOperation_IconURLExpression();

        /**
         * The meta object literal for the '<em><b>Precondition Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute PRIMITIVE_LIST_ITEM_ACTION_OPERATION__PRECONDITION_EXPRESSION = eINSTANCE.getPrimitiveListItemActionOperation_PreconditionExpression();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceWidgetDescriptionImpl
         * <em>Mono Reference Widget Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceWidgetDescriptionImpl
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getMonoReferenceWidgetDescription()
         * @generated
         */
        EClass MONO_REFERENCE_WIDGET_DESCRIPTION = eINSTANCE.getMonoReferenceWidgetDescription();

        /**
         * The meta object literal for the '<em><b>Is Enabled Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute MONO_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION = eINSTANCE.getMonoReferenceWidgetDescription_IsEnabledExpression();

        /**
         * The meta object literal for the '<em><b>Owner Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute MONO_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION = eINSTANCE.getMonoReferenceWidgetDescription_OwnerExpression();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute MONO_REFERENCE_WIDGET_DESCRIPTION__TYPE = eINSTANCE.getMonoReferenceWidgetDescription_Type();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute MONO_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getMonoReferenceWidgetDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Candidates Search Scope Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute MONO_REFERENCE_WIDGET_DESCRIPTION__CANDIDATES_SEARCH_SCOPE_EXPRESSION = eINSTANCE.getMonoReferenceWidgetDescription_CandidatesSearchScopeExpression();

        /**
         * The meta object literal for the '<em><b>Dropdown Options Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute MONO_REFERENCE_WIDGET_DESCRIPTION__DROPDOWN_OPTIONS_EXPRESSION = eINSTANCE.getMonoReferenceWidgetDescription_DropdownOptionsExpression();

        /**
         * The meta object literal for the '<em><b>Create Element Operation</b></em>' containment reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MONO_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION = eINSTANCE.getMonoReferenceWidgetDescription_CreateElementOperation();

        /**
         * The meta object literal for the '<em><b>Set Operation</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MONO_REFERENCE_WIDGET_DESCRIPTION__SET_OPERATION = eINSTANCE.getMonoReferenceWidgetDescription_SetOperation();

        /**
         * The meta object literal for the '<em><b>Unset Operation</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MONO_REFERENCE_WIDGET_DESCRIPTION__UNSET_OPERATION = eINSTANCE.getMonoReferenceWidgetDescription_UnsetOperation();

        /**
         * The meta object literal for the '<em><b>Clear Operation</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MONO_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION = eINSTANCE.getMonoReferenceWidgetDescription_ClearOperation();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MONO_REFERENCE_WIDGET_DESCRIPTION__STYLE = eINSTANCE.getMonoReferenceWidgetDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MONO_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getMonoReferenceWidgetDescription_ConditionalStyles();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceWidgetDescriptionImpl
         * <em>Multi Reference Widget Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceWidgetDescriptionImpl
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getMultiReferenceWidgetDescription()
         * @generated
         */
        EClass MULTI_REFERENCE_WIDGET_DESCRIPTION = eINSTANCE.getMultiReferenceWidgetDescription();

        /**
         * The meta object literal for the '<em><b>Is Enabled Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute MULTI_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION = eINSTANCE.getMultiReferenceWidgetDescription_IsEnabledExpression();

        /**
         * The meta object literal for the '<em><b>Owner Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute MULTI_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION = eINSTANCE.getMultiReferenceWidgetDescription_OwnerExpression();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute MULTI_REFERENCE_WIDGET_DESCRIPTION__TYPE = eINSTANCE.getMultiReferenceWidgetDescription_Type();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute MULTI_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getMultiReferenceWidgetDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Candidates Search Scope Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute MULTI_REFERENCE_WIDGET_DESCRIPTION__CANDIDATES_SEARCH_SCOPE_EXPRESSION = eINSTANCE.getMultiReferenceWidgetDescription_CandidatesSearchScopeExpression();

        /**
         * The meta object literal for the '<em><b>Dropdown Options Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute MULTI_REFERENCE_WIDGET_DESCRIPTION__DROPDOWN_OPTIONS_EXPRESSION = eINSTANCE.getMultiReferenceWidgetDescription_DropdownOptionsExpression();

        /**
         * The meta object literal for the '<em><b>Create Element Operation</b></em>' containment reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MULTI_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION = eINSTANCE.getMultiReferenceWidgetDescription_CreateElementOperation();

        /**
         * The meta object literal for the '<em><b>Add Operation</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MULTI_REFERENCE_WIDGET_DESCRIPTION__ADD_OPERATION = eINSTANCE.getMultiReferenceWidgetDescription_AddOperation();

        /**
         * The meta object literal for the '<em><b>Remove Operation</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MULTI_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION = eINSTANCE.getMultiReferenceWidgetDescription_RemoveOperation();

        /**
         * The meta object literal for the '<em><b>Clear Operation</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MULTI_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION = eINSTANCE.getMultiReferenceWidgetDescription_ClearOperation();

        /**
         * The meta object literal for the '<em><b>Reorder Operation</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MULTI_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION = eINSTANCE.getMultiReferenceWidgetDescription_ReorderOperation();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MULTI_REFERENCE_WIDGET_DESCRIPTION__STYLE = eINSTANCE.getMultiReferenceWidgetDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MULTI_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getMultiReferenceWidgetDescription_ConditionalStyles();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceSetOperationImpl <em>Mono
         * Reference Set Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceSetOperationImpl
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getMonoReferenceSetOperation()
         * @generated
         */
        EClass MONO_REFERENCE_SET_OPERATION = eINSTANCE.getMonoReferenceSetOperation();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MONO_REFERENCE_SET_OPERATION__BODY = eINSTANCE.getMonoReferenceSetOperation_Body();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceUnsetOperationImpl <em>Mono
         * Reference Unset Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MonoReferenceUnsetOperationImpl
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getMonoReferenceUnsetOperation()
         * @generated
         */
        EClass MONO_REFERENCE_UNSET_OPERATION = eINSTANCE.getMonoReferenceUnsetOperation();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MONO_REFERENCE_UNSET_OPERATION__BODY = eINSTANCE.getMonoReferenceUnsetOperation_Body();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.CreateElementInReferenceOperationImpl
         * <em>Create Element In Reference Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.CreateElementInReferenceOperationImpl
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getCreateElementInReferenceOperation()
         * @generated
         */
        EClass CREATE_ELEMENT_IN_REFERENCE_OPERATION = eINSTANCE.getCreateElementInReferenceOperation();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CREATE_ELEMENT_IN_REFERENCE_OPERATION__BODY = eINSTANCE.getCreateElementInReferenceOperation_Body();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ClickReferenceValueOperationImpl <em>Click
         * Reference Value Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ClickReferenceValueOperationImpl
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getClickReferenceValueOperation()
         * @generated
         */
        EClass CLICK_REFERENCE_VALUE_OPERATION = eINSTANCE.getClickReferenceValueOperation();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CLICK_REFERENCE_VALUE_OPERATION__BODY = eINSTANCE.getClickReferenceValueOperation_Body();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceAddOperationImpl <em>Multi
         * Reference Add Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceAddOperationImpl
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getMultiReferenceAddOperation()
         * @generated
         */
        EClass MULTI_REFERENCE_ADD_OPERATION = eINSTANCE.getMultiReferenceAddOperation();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MULTI_REFERENCE_ADD_OPERATION__BODY = eINSTANCE.getMultiReferenceAddOperation_Body();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceRemoveOperationImpl
         * <em>Multi Reference Remove Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceRemoveOperationImpl
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getMultiReferenceRemoveOperation()
         * @generated
         */
        EClass MULTI_REFERENCE_REMOVE_OPERATION = eINSTANCE.getMultiReferenceRemoveOperation();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MULTI_REFERENCE_REMOVE_OPERATION__BODY = eINSTANCE.getMultiReferenceRemoveOperation_Body();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ClearReferenceOperationImpl <em>Clear
         * Reference Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ClearReferenceOperationImpl
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getClearReferenceOperation()
         * @generated
         */
        EClass CLEAR_REFERENCE_OPERATION = eINSTANCE.getClearReferenceOperation();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CLEAR_REFERENCE_OPERATION__BODY = eINSTANCE.getClearReferenceOperation_Body();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceReorderOperationImpl
         * <em>Multi Reference Reorder Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.MultiReferenceReorderOperationImpl
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getMultiReferenceReorderOperation()
         * @generated
         */
        EClass MULTI_REFERENCE_REORDER_OPERATION = eINSTANCE.getMultiReferenceReorderOperation();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MULTI_REFERENCE_REORDER_OPERATION__BODY = eINSTANCE.getMultiReferenceReorderOperation_Body();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ContainmentReferenceWidgetDescriptionImpl
         * <em>Containment Reference Widget Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.ContainmentReferenceWidgetDescriptionImpl
         * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsPackageImpl#getContainmentReferenceWidgetDescription()
         * @generated
         */
        EClass CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION = eINSTANCE.getContainmentReferenceWidgetDescription();

        /**
         * The meta object literal for the '<em><b>Is Enabled Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION = eINSTANCE.getContainmentReferenceWidgetDescription_IsEnabledExpression();

        /**
         * The meta object literal for the '<em><b>Many</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__MANY = eINSTANCE.getContainmentReferenceWidgetDescription_Many();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__TYPE = eINSTANCE.getContainmentReferenceWidgetDescription_Type();

        /**
         * The meta object literal for the '<em><b>Owner Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION = eINSTANCE.getContainmentReferenceWidgetDescription_OwnerExpression();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getContainmentReferenceWidgetDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Create Element Operation</b></em>' containment reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION = eINSTANCE.getContainmentReferenceWidgetDescription_CreateElementOperation();

        /**
         * The meta object literal for the '<em><b>Remove Operation</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION = eINSTANCE.getContainmentReferenceWidgetDescription_RemoveOperation();

        /**
         * The meta object literal for the '<em><b>Reorder Operation</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION = eINSTANCE.getContainmentReferenceWidgetDescription_ReorderOperation();

        /**
         * The meta object literal for the '<em><b>Click Operation</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CLICK_OPERATION = eINSTANCE.getContainmentReferenceWidgetDescription_ClickOperation();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__STYLE = eINSTANCE.getContainmentReferenceWidgetDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getContainmentReferenceWidgetDescription_ConditionalStyles();

    }

} // PapyrusWidgetsPackage
