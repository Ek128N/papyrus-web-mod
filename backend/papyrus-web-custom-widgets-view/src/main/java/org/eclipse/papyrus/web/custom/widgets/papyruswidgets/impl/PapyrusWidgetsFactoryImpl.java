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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.LanguageExpressionWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsFactory;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListAddOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListDeleteOperation;
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
