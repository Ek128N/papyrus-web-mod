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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
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
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the inheritance hierarchy until a non-null result is
 * returned, which is the result of the switch. <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage
 * @generated
 */
public class PapyrusWidgetsSwitch<T> extends Switch<T> {
    /**
     * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected static PapyrusWidgetsPackage modelPackage;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public PapyrusWidgetsSwitch() {
        if (modelPackage == null) {
            modelPackage = PapyrusWidgetsPackage.eINSTANCE;
        }
    }

    /**
     * Checks whether this is a switch for the given package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param ePackage
     *            the package in question.
     * @return whether this is a switch for the given package.
     * @generated
     */
    @Override
    protected boolean isSwitchFor(EPackage ePackage) {
        return ePackage == modelPackage;
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that
     * result. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    @Override
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
            case PapyrusWidgetsPackage.LANGUAGE_EXPRESSION_WIDGET_DESCRIPTION: {
                LanguageExpressionWidgetDescription languageExpressionWidgetDescription = (LanguageExpressionWidgetDescription) theEObject;
                T result = this.caseLanguageExpressionWidgetDescription(languageExpressionWidgetDescription);
                if (result == null)
                    result = this.caseWidgetDescription(languageExpressionWidgetDescription);
                if (result == null)
                    result = this.caseFormElementDescription(languageExpressionWidgetDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapyrusWidgetsPackage.PRIMITIVE_RADIO_WIDGET_DESCRIPTION: {
                PrimitiveRadioWidgetDescription primitiveRadioWidgetDescription = (PrimitiveRadioWidgetDescription) theEObject;
                T result = this.casePrimitiveRadioWidgetDescription(primitiveRadioWidgetDescription);
                if (result == null)
                    result = this.caseWidgetDescription(primitiveRadioWidgetDescription);
                if (result == null)
                    result = this.caseFormElementDescription(primitiveRadioWidgetDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION: {
                PrimitiveListWidgetDescription primitiveListWidgetDescription = (PrimitiveListWidgetDescription) theEObject;
                T result = this.casePrimitiveListWidgetDescription(primitiveListWidgetDescription);
                if (result == null)
                    result = this.caseWidgetDescription(primitiveListWidgetDescription);
                if (result == null)
                    result = this.caseFormElementDescription(primitiveListWidgetDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_DELETE_OPERATION: {
                PrimitiveListDeleteOperation primitiveListDeleteOperation = (PrimitiveListDeleteOperation) theEObject;
                T result = this.casePrimitiveListDeleteOperation(primitiveListDeleteOperation);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_ADD_OPERATION: {
                PrimitiveListAddOperation primitiveListAddOperation = (PrimitiveListAddOperation) theEObject;
                T result = this.casePrimitiveListAddOperation(primitiveListAddOperation);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_REORDER_OPERATION: {
                PrimitiveListReorderOperation primitiveListReorderOperation = (PrimitiveListReorderOperation) theEObject;
                T result = this.casePrimitiveListReorderOperation(primitiveListReorderOperation);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_ITEM_ACTION_OPERATION: {
                PrimitiveListItemActionOperation primitiveListItemActionOperation = (PrimitiveListItemActionOperation) theEObject;
                T result = this.casePrimitiveListItemActionOperation(primitiveListItemActionOperation);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION: {
                MonoReferenceWidgetDescription monoReferenceWidgetDescription = (MonoReferenceWidgetDescription) theEObject;
                T result = this.caseMonoReferenceWidgetDescription(monoReferenceWidgetDescription);
                if (result == null)
                    result = this.caseWidgetDescription(monoReferenceWidgetDescription);
                if (result == null)
                    result = this.caseFormElementDescription(monoReferenceWidgetDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapyrusWidgetsPackage.MULTI_REFERENCE_WIDGET_DESCRIPTION: {
                MultiReferenceWidgetDescription multiReferenceWidgetDescription = (MultiReferenceWidgetDescription) theEObject;
                T result = this.caseMultiReferenceWidgetDescription(multiReferenceWidgetDescription);
                if (result == null)
                    result = this.caseWidgetDescription(multiReferenceWidgetDescription);
                if (result == null)
                    result = this.caseFormElementDescription(multiReferenceWidgetDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapyrusWidgetsPackage.MONO_REFERENCE_SET_OPERATION: {
                MonoReferenceSetOperation monoReferenceSetOperation = (MonoReferenceSetOperation) theEObject;
                T result = this.caseMonoReferenceSetOperation(monoReferenceSetOperation);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapyrusWidgetsPackage.MONO_REFERENCE_UNSET_OPERATION: {
                MonoReferenceUnsetOperation monoReferenceUnsetOperation = (MonoReferenceUnsetOperation) theEObject;
                T result = this.caseMonoReferenceUnsetOperation(monoReferenceUnsetOperation);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapyrusWidgetsPackage.CREATE_ELEMENT_IN_REFERENCE_OPERATION: {
                CreateElementInReferenceOperation createElementInReferenceOperation = (CreateElementInReferenceOperation) theEObject;
                T result = this.caseCreateElementInReferenceOperation(createElementInReferenceOperation);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapyrusWidgetsPackage.CLICK_REFERENCE_VALUE_OPERATION: {
                ClickReferenceValueOperation clickReferenceValueOperation = (ClickReferenceValueOperation) theEObject;
                T result = this.caseClickReferenceValueOperation(clickReferenceValueOperation);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapyrusWidgetsPackage.MULTI_REFERENCE_ADD_OPERATION: {
                MultiReferenceAddOperation multiReferenceAddOperation = (MultiReferenceAddOperation) theEObject;
                T result = this.caseMultiReferenceAddOperation(multiReferenceAddOperation);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapyrusWidgetsPackage.MULTI_REFERENCE_REMOVE_OPERATION: {
                MultiReferenceRemoveOperation multiReferenceRemoveOperation = (MultiReferenceRemoveOperation) theEObject;
                T result = this.caseMultiReferenceRemoveOperation(multiReferenceRemoveOperation);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapyrusWidgetsPackage.CLEAR_REFERENCE_OPERATION: {
                ClearReferenceOperation clearReferenceOperation = (ClearReferenceOperation) theEObject;
                T result = this.caseClearReferenceOperation(clearReferenceOperation);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapyrusWidgetsPackage.MULTI_REFERENCE_REORDER_OPERATION: {
                MultiReferenceReorderOperation multiReferenceReorderOperation = (MultiReferenceReorderOperation) theEObject;
                T result = this.caseMultiReferenceReorderOperation(multiReferenceReorderOperation);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION: {
                ContainmentReferenceWidgetDescription containmentReferenceWidgetDescription = (ContainmentReferenceWidgetDescription) theEObject;
                T result = this.caseContainmentReferenceWidgetDescription(containmentReferenceWidgetDescription);
                if (result == null)
                    result = this.caseWidgetDescription(containmentReferenceWidgetDescription);
                if (result == null)
                    result = this.caseFormElementDescription(containmentReferenceWidgetDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            default:
                return this.defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Language Expression Widget
     * Description</em>'. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Language Expression Widget
     *         Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLanguageExpressionWidgetDescription(LanguageExpressionWidgetDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Primitive Radio Widget Description</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Primitive Radio Widget Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePrimitiveRadioWidgetDescription(PrimitiveRadioWidgetDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Primitive List Widget Description</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Primitive List Widget Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePrimitiveListWidgetDescription(PrimitiveListWidgetDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Primitive List Delete Operation</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Primitive List Delete Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePrimitiveListDeleteOperation(PrimitiveListDeleteOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Primitive List Add Operation</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Primitive List Add Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePrimitiveListAddOperation(PrimitiveListAddOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Primitive List Reorder Operation</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Primitive List Reorder Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePrimitiveListReorderOperation(PrimitiveListReorderOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Primitive List Item Action Operation</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Primitive List Item Action Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePrimitiveListItemActionOperation(PrimitiveListItemActionOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Mono Reference Widget Description</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Mono Reference Widget Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMonoReferenceWidgetDescription(MonoReferenceWidgetDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Multi Reference Widget Description</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Multi Reference Widget Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMultiReferenceWidgetDescription(MultiReferenceWidgetDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Mono Reference Set Operation</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Mono Reference Set Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMonoReferenceSetOperation(MonoReferenceSetOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Mono Reference Unset Operation</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Mono Reference Unset Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMonoReferenceUnsetOperation(MonoReferenceUnsetOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Create Element In Reference Operation</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Create Element In Reference Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCreateElementInReferenceOperation(CreateElementInReferenceOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Click Reference Value Operation</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Click Reference Value Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseClickReferenceValueOperation(ClickReferenceValueOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Multi Reference Add Operation</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Multi Reference Add Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMultiReferenceAddOperation(MultiReferenceAddOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Multi Reference Remove Operation</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Multi Reference Remove Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMultiReferenceRemoveOperation(MultiReferenceRemoveOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Clear Reference Operation</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Clear Reference Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseClearReferenceOperation(ClearReferenceOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Multi Reference Reorder Operation</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Multi Reference Reorder Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMultiReferenceReorderOperation(MultiReferenceReorderOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Containment Reference Widget
     * Description</em>'. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Containment Reference Widget
     *         Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseContainmentReferenceWidgetDescription(ContainmentReferenceWidgetDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Element Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Element Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFormElementDescription(FormElementDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Widget Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Widget Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWidgetDescription(WidgetDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch, but this is the last case
     * anyway. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    @Override
    public T defaultCase(EObject object) {
        return null;
    }

} // PapyrusWidgetsSwitch
