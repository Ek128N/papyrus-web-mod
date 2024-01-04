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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage
 * @generated
 */
public interface PapyrusWidgetsFactory extends EFactory {
    /**
     * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    PapyrusWidgetsFactory eINSTANCE = org.eclipse.papyrus.web.custom.widgets.papyruswidgets.impl.PapyrusWidgetsFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Language Expression Widget Description</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Language Expression Widget Description</em>'.
     * @generated
     */
    LanguageExpressionWidgetDescription createLanguageExpressionWidgetDescription();

    /**
     * Returns a new object of class '<em>Primitive Radio Widget Description</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Primitive Radio Widget Description</em>'.
     * @generated
     */
    PrimitiveRadioWidgetDescription createPrimitiveRadioWidgetDescription();

    /**
     * Returns a new object of class '<em>Primitive List Widget Description</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Primitive List Widget Description</em>'.
     * @generated
     */
    PrimitiveListWidgetDescription createPrimitiveListWidgetDescription();

    /**
     * Returns a new object of class '<em>Primitive List Delete Operation</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Primitive List Delete Operation</em>'.
     * @generated
     */
    PrimitiveListDeleteOperation createPrimitiveListDeleteOperation();

    /**
     * Returns a new object of class '<em>Primitive List Add Operation</em>'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return a new object of class '<em>Primitive List Add Operation</em>'.
     * @generated
     */
    PrimitiveListAddOperation createPrimitiveListAddOperation();

    /**
     * Returns a new object of class '<em>Primitive List Reorder Operation</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Primitive List Reorder Operation</em>'.
     * @generated
     */
    PrimitiveListReorderOperation createPrimitiveListReorderOperation();

    /**
     * Returns a new object of class '<em>Primitive List Item Action Operation</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Primitive List Item Action Operation</em>'.
     * @generated
     */
    PrimitiveListItemActionOperation createPrimitiveListItemActionOperation();

    /**
     * Returns a new object of class '<em>Mono Reference Widget Description</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Mono Reference Widget Description</em>'.
     * @generated
     */
    MonoReferenceWidgetDescription createMonoReferenceWidgetDescription();

    /**
     * Returns a new object of class '<em>Multi Reference Widget Description</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Multi Reference Widget Description</em>'.
     * @generated
     */
    MultiReferenceWidgetDescription createMultiReferenceWidgetDescription();

    /**
     * Returns a new object of class '<em>Mono Reference Set Operation</em>'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return a new object of class '<em>Mono Reference Set Operation</em>'.
     * @generated
     */
    MonoReferenceSetOperation createMonoReferenceSetOperation();

    /**
     * Returns a new object of class '<em>Mono Reference Unset Operation</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Mono Reference Unset Operation</em>'.
     * @generated
     */
    MonoReferenceUnsetOperation createMonoReferenceUnsetOperation();

    /**
     * Returns a new object of class '<em>Create Element In Reference Operation</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Create Element In Reference Operation</em>'.
     * @generated
     */
    CreateElementInReferenceOperation createCreateElementInReferenceOperation();

    /**
     * Returns a new object of class '<em>Click Reference Value Operation</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Click Reference Value Operation</em>'.
     * @generated
     */
    ClickReferenceValueOperation createClickReferenceValueOperation();

    /**
     * Returns a new object of class '<em>Multi Reference Add Operation</em>'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return a new object of class '<em>Multi Reference Add Operation</em>'.
     * @generated
     */
    MultiReferenceAddOperation createMultiReferenceAddOperation();

    /**
     * Returns a new object of class '<em>Multi Reference Remove Operation</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Multi Reference Remove Operation</em>'.
     * @generated
     */
    MultiReferenceRemoveOperation createMultiReferenceRemoveOperation();

    /**
     * Returns a new object of class '<em>Clear Reference Operation</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Clear Reference Operation</em>'.
     * @generated
     */
    ClearReferenceOperation createClearReferenceOperation();

    /**
     * Returns a new object of class '<em>Multi Reference Reorder Operation</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Multi Reference Reorder Operation</em>'.
     * @generated
     */
    MultiReferenceReorderOperation createMultiReferenceReorderOperation();

    /**
     * Returns a new object of class '<em>Containment Reference Widget Description</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Containment Reference Widget Description</em>'.
     * @generated
     */
    ContainmentReferenceWidgetDescription createContainmentReferenceWidgetDescription();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the package supported by this factory.
     * @generated
     */
    PapyrusWidgetsPackage getPapyrusWidgetsPackage();

} // PapyrusWidgetsFactory
