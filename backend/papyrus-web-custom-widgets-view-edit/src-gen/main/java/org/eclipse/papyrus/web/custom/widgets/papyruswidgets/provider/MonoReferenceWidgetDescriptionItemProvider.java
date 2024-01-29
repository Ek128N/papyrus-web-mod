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
package org.eclipse.papyrus.web.custom.widgets.papyruswidgets.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.papyrus.web.custom.widgets.PapyrusWebCustomWidgetsEditPlugin;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsFactory;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage;
import org.eclipse.sirius.components.view.form.provider.WidgetDescriptionItemProvider;
import org.eclipse.sirius.components.widgets.reference.ReferenceFactory;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.MonoReferenceWidgetDescription} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class MonoReferenceWidgetDescriptionItemProvider extends WidgetDescriptionItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public MonoReferenceWidgetDescriptionItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        if (this.itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            this.addIsEnabledExpressionPropertyDescriptor(object);
            this.addOwnerExpressionPropertyDescriptor(object);
            this.addTypePropertyDescriptor(object);
            this.addValueExpressionPropertyDescriptor(object);
            this.addCandidatesSearchScopeExpressionPropertyDescriptor(object);
            this.addDropdownOptionsExpressionPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Is Enabled Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addIsEnabledExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
                this.getResourceLocator(),
                this.getString("_UI_MonoReferenceWidgetDescription_isEnabledExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_MonoReferenceWidgetDescription_isEnabledExpression_feature", "_UI_MonoReferenceWidgetDescription_type"),
                PapyrusWidgetsPackage.Literals.MONO_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Owner Expression feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addOwnerExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
                this.getResourceLocator(),
                this.getString("_UI_MonoReferenceWidgetDescription_ownerExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_MonoReferenceWidgetDescription_ownerExpression_feature", "_UI_MonoReferenceWidgetDescription_type"),
                PapyrusWidgetsPackage.Literals.MONO_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Type feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addTypePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
                this.getResourceLocator(),
                this.getString("_UI_MonoReferenceWidgetDescription_type_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_MonoReferenceWidgetDescription_type_feature", "_UI_MonoReferenceWidgetDescription_type"),
                PapyrusWidgetsPackage.Literals.MONO_REFERENCE_WIDGET_DESCRIPTION__TYPE,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Value Expression feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addValueExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
                this.getResourceLocator(),
                this.getString("_UI_MonoReferenceWidgetDescription_valueExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_MonoReferenceWidgetDescription_valueExpression_feature", "_UI_MonoReferenceWidgetDescription_type"),
                PapyrusWidgetsPackage.Literals.MONO_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Candidates Search Scope Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addCandidatesSearchScopeExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
                this.getResourceLocator(),
                this.getString("_UI_MonoReferenceWidgetDescription_candidatesSearchScopeExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_MonoReferenceWidgetDescription_candidatesSearchScopeExpression_feature", "_UI_MonoReferenceWidgetDescription_type"),
                PapyrusWidgetsPackage.Literals.MONO_REFERENCE_WIDGET_DESCRIPTION__CANDIDATES_SEARCH_SCOPE_EXPRESSION,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Dropdown Options Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addDropdownOptionsExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
                this.getResourceLocator(),
                this.getString("_UI_MonoReferenceWidgetDescription_dropdownOptionsExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_MonoReferenceWidgetDescription_dropdownOptionsExpression_feature", "_UI_MonoReferenceWidgetDescription_type"),
                PapyrusWidgetsPackage.Literals.MONO_REFERENCE_WIDGET_DESCRIPTION__DROPDOWN_OPTIONS_EXPRESSION,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
     * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
     * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
        if (this.childrenFeatures == null) {
            super.getChildrenFeatures(object);
            this.childrenFeatures.add(PapyrusWidgetsPackage.Literals.MONO_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION);
            this.childrenFeatures.add(PapyrusWidgetsPackage.Literals.MONO_REFERENCE_WIDGET_DESCRIPTION__SET_OPERATION);
            this.childrenFeatures.add(PapyrusWidgetsPackage.Literals.MONO_REFERENCE_WIDGET_DESCRIPTION__UNSET_OPERATION);
            this.childrenFeatures.add(PapyrusWidgetsPackage.Literals.MONO_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION);
            this.childrenFeatures.add(PapyrusWidgetsPackage.Literals.MONO_REFERENCE_WIDGET_DESCRIPTION__STYLE);
            this.childrenFeatures.add(PapyrusWidgetsPackage.Literals.MONO_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES);
        }
        return this.childrenFeatures;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EStructuralFeature getChildFeature(Object object, Object child) {
        // Check the type of the specified child object and return the proper feature to use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature(object, child);
    }

    /**
     * This returns MonoReferenceWidgetDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/MonoReferenceWidgetDescription"));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected boolean shouldComposeCreationImage() {
        return true;
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((MonoReferenceWidgetDescription) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_MonoReferenceWidgetDescription_type") : this.getString("_UI_MonoReferenceWidgetDescription_type") + " " + label;
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to update any cached children and by creating
     * a viewer notification, which it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    @Override
    public void notifyChanged(Notification notification) {
        this.updateChildren(notification);

        switch (notification.getFeatureID(MonoReferenceWidgetDescription.class)) {
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION:
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__TYPE:
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION:
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CANDIDATES_SEARCH_SCOPE_EXPRESSION:
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__DROPDOWN_OPTIONS_EXPRESSION:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION:
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__SET_OPERATION:
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__UNSET_OPERATION:
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION:
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__STYLE:
            case PapyrusWidgetsPackage.MONO_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
                return;
            default:
                super.notifyChanged(notification);
                return;
        }
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children that can be created
     * under this object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.MONO_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION,
                PapyrusWidgetsFactory.eINSTANCE.createCreateElementInReferenceOperation()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.MONO_REFERENCE_WIDGET_DESCRIPTION__SET_OPERATION,
                PapyrusWidgetsFactory.eINSTANCE.createMonoReferenceSetOperation()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.MONO_REFERENCE_WIDGET_DESCRIPTION__UNSET_OPERATION,
                PapyrusWidgetsFactory.eINSTANCE.createMonoReferenceUnsetOperation()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.MONO_REFERENCE_WIDGET_DESCRIPTION__CLEAR_OPERATION,
                PapyrusWidgetsFactory.eINSTANCE.createClearReferenceOperation()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.MONO_REFERENCE_WIDGET_DESCRIPTION__STYLE,
                ReferenceFactory.eINSTANCE.createReferenceWidgetDescriptionStyle()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.MONO_REFERENCE_WIDGET_DESCRIPTION__STYLE,
                ReferenceFactory.eINSTANCE.createConditionalReferenceWidgetDescriptionStyle()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.MONO_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES,
                ReferenceFactory.eINSTANCE.createConditionalReferenceWidgetDescriptionStyle()));
    }

    /**
     * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
        Object childFeature = feature;
        Object childObject = child;

        boolean qualify = childFeature == PapyrusWidgetsPackage.Literals.MONO_REFERENCE_WIDGET_DESCRIPTION__STYLE ||
                childFeature == PapyrusWidgetsPackage.Literals.MONO_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES;

        if (qualify) {
            return this.getString("_UI_CreateChild_text2",
                    new Object[] { this.getTypeText(childObject), this.getFeatureText(childFeature), this.getTypeText(owner) });
        }
        return super.getCreateChildText(owner, feature, child, selection);
    }

    /**
     * Return the resource locator for this item provider's resources. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return PapyrusWebCustomWidgetsEditPlugin.INSTANCE;
    }

}
