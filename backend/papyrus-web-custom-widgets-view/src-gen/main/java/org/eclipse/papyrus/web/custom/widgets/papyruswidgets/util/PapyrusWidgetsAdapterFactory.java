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
package org.eclipse.papyrus.web.custom.widgets.papyruswidgets.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
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
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListAddOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListDeleteOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListItemActionOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListReorderOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveRadioWidgetDescription;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter <code>createXXX</code>
 * method for each class of the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage
 * @generated
 */
public class PapyrusWidgetsAdapterFactory extends AdapterFactoryImpl {
    /**
     * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected static PapyrusWidgetsPackage modelPackage;

    /**
     * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public PapyrusWidgetsAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = PapyrusWidgetsPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object. <!-- begin-user-doc --> This
     * implementation returns <code>true</code> if the object is either the model's package or is an instance object of
     * the model. <!-- end-user-doc -->
     *
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject) object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected PapyrusWidgetsSwitch<Adapter> modelSwitch = new PapyrusWidgetsSwitch<>() {
        @Override
        public Adapter caseLanguageExpressionWidgetDescription(LanguageExpressionWidgetDescription object) {
            return PapyrusWidgetsAdapterFactory.this.createLanguageExpressionWidgetDescriptionAdapter();
        }

        @Override
        public Adapter casePrimitiveRadioWidgetDescription(PrimitiveRadioWidgetDescription object) {
            return PapyrusWidgetsAdapterFactory.this.createPrimitiveRadioWidgetDescriptionAdapter();
        }

        @Override
        public Adapter casePrimitiveListWidgetDescription(PrimitiveListWidgetDescription object) {
            return PapyrusWidgetsAdapterFactory.this.createPrimitiveListWidgetDescriptionAdapter();
        }

        @Override
        public Adapter casePrimitiveListDeleteOperation(PrimitiveListDeleteOperation object) {
            return PapyrusWidgetsAdapterFactory.this.createPrimitiveListDeleteOperationAdapter();
        }

        @Override
        public Adapter casePrimitiveListAddOperation(PrimitiveListAddOperation object) {
            return PapyrusWidgetsAdapterFactory.this.createPrimitiveListAddOperationAdapter();
        }

        @Override
        public Adapter casePrimitiveListReorderOperation(PrimitiveListReorderOperation object) {
            return PapyrusWidgetsAdapterFactory.this.createPrimitiveListReorderOperationAdapter();
        }

        @Override
        public Adapter casePrimitiveListItemActionOperation(PrimitiveListItemActionOperation object) {
            return PapyrusWidgetsAdapterFactory.this.createPrimitiveListItemActionOperationAdapter();
        }

        @Override
        public Adapter caseMonoReferenceWidgetDescription(MonoReferenceWidgetDescription object) {
            return PapyrusWidgetsAdapterFactory.this.createMonoReferenceWidgetDescriptionAdapter();
        }

        @Override
        public Adapter caseMultiReferenceWidgetDescription(MultiReferenceWidgetDescription object) {
            return PapyrusWidgetsAdapterFactory.this.createMultiReferenceWidgetDescriptionAdapter();
        }

        @Override
        public Adapter caseMonoReferenceSetOperation(MonoReferenceSetOperation object) {
            return PapyrusWidgetsAdapterFactory.this.createMonoReferenceSetOperationAdapter();
        }

        @Override
        public Adapter caseMonoReferenceUnsetOperation(MonoReferenceUnsetOperation object) {
            return PapyrusWidgetsAdapterFactory.this.createMonoReferenceUnsetOperationAdapter();
        }

        @Override
        public Adapter caseCreateElementInReferenceOperation(CreateElementInReferenceOperation object) {
            return PapyrusWidgetsAdapterFactory.this.createCreateElementInReferenceOperationAdapter();
        }

        @Override
        public Adapter caseClickReferenceValueOperation(ClickReferenceValueOperation object) {
            return PapyrusWidgetsAdapterFactory.this.createClickReferenceValueOperationAdapter();
        }

        @Override
        public Adapter caseMultiReferenceAddOperation(MultiReferenceAddOperation object) {
            return PapyrusWidgetsAdapterFactory.this.createMultiReferenceAddOperationAdapter();
        }

        @Override
        public Adapter caseMultiReferenceRemoveOperation(MultiReferenceRemoveOperation object) {
            return PapyrusWidgetsAdapterFactory.this.createMultiReferenceRemoveOperationAdapter();
        }

        @Override
        public Adapter caseClearReferenceOperation(ClearReferenceOperation object) {
            return PapyrusWidgetsAdapterFactory.this.createClearReferenceOperationAdapter();
        }

        @Override
        public Adapter caseMultiReferenceReorderOperation(MultiReferenceReorderOperation object) {
            return PapyrusWidgetsAdapterFactory.this.createMultiReferenceReorderOperationAdapter();
        }

        @Override
        public Adapter caseContainmentReferenceWidgetDescription(ContainmentReferenceWidgetDescription object) {
            return PapyrusWidgetsAdapterFactory.this.createContainmentReferenceWidgetDescriptionAdapter();
        }

        @Override
        public Adapter caseFormElementDescription(FormElementDescription object) {
            return PapyrusWidgetsAdapterFactory.this.createFormElementDescriptionAdapter();
        }

        @Override
        public Adapter caseWidgetDescription(WidgetDescription object) {
            return PapyrusWidgetsAdapterFactory.this.createWidgetDescriptionAdapter();
        }

        @Override
        public Adapter defaultCase(EObject object) {
            return PapyrusWidgetsAdapterFactory.this.createEObjectAdapter();
        }
    };

    /**
     * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param target
     *            the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return this.modelSwitch.doSwitch((EObject) target);
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.LanguageExpressionWidgetDescription <em>Language
     * Expression Widget Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.LanguageExpressionWidgetDescription
     * @generated
     */
    public Adapter createLanguageExpressionWidgetDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveRadioWidgetDescription <em>Primitive Radio
     * Widget Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveRadioWidgetDescription
     * @generated
     */
    public Adapter createPrimitiveRadioWidgetDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription <em>Primitive List
     * Widget Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription
     * @generated
     */
    public Adapter createPrimitiveListWidgetDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListDeleteOperation <em>Primitive List
     * Delete Operation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListDeleteOperation
     * @generated
     */
    public Adapter createPrimitiveListDeleteOperationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListAddOperation <em>Primitive List Add
     * Operation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListAddOperation
     * @generated
     */
    public Adapter createPrimitiveListAddOperationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListReorderOperation <em>Primitive List
     * Reorder Operation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListReorderOperation
     * @generated
     */
    public Adapter createPrimitiveListReorderOperationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListItemActionOperation <em>Primitive List
     * Item Action Operation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListItemActionOperation
     * @generated
     */
    public Adapter createPrimitiveListItemActionOperationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription <em>Mono Reference
     * Widget Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription
     * @generated
     */
    public Adapter createMonoReferenceWidgetDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription <em>Multi Reference
     * Widget Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceWidgetDescription
     * @generated
     */
    public Adapter createMultiReferenceWidgetDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceSetOperation <em>Mono Reference Set
     * Operation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceSetOperation
     * @generated
     */
    public Adapter createMonoReferenceSetOperationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceUnsetOperation <em>Mono Reference
     * Unset Operation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceUnsetOperation
     * @generated
     */
    public Adapter createMonoReferenceUnsetOperationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CreateElementInReferenceOperation <em>Create
     * Element In Reference Operation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
     * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CreateElementInReferenceOperation
     * @generated
     */
    public Adapter createCreateElementInReferenceOperationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ClickReferenceValueOperation <em>Click Reference
     * Value Operation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ClickReferenceValueOperation
     * @generated
     */
    public Adapter createClickReferenceValueOperationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceAddOperation <em>Multi Reference Add
     * Operation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceAddOperation
     * @generated
     */
    public Adapter createMultiReferenceAddOperationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceRemoveOperation <em>Multi Reference
     * Remove Operation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceRemoveOperation
     * @generated
     */
    public Adapter createMultiReferenceRemoveOperationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ClearReferenceOperation <em>Clear Reference
     * Operation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ClearReferenceOperation
     * @generated
     */
    public Adapter createClearReferenceOperationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceReorderOperation <em>Multi Reference
     * Reorder Operation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MultiReferenceReorderOperation
     * @generated
     */
    public Adapter createMultiReferenceReorderOperationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription
     * <em>Containment Reference Widget Description</em>}'. <!-- begin-user-doc --> This default implementation returns
     * null so that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases
     * anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription
     * @generated
     */
    public Adapter createContainmentReferenceWidgetDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.FormElementDescription <em>Element Description</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.FormElementDescription
     * @generated
     */
    public Adapter createFormElementDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.WidgetDescription
     * <em>Widget Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.WidgetDescription
     * @generated
     */
    public Adapter createWidgetDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case. <!-- begin-user-doc --> This default implementation returns null.
     * <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} // PapyrusWidgetsAdapterFactory
