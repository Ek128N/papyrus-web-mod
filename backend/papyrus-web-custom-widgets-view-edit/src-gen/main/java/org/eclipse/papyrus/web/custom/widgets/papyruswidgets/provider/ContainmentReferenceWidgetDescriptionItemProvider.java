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
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsFactory;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage;
import org.eclipse.sirius.components.view.form.provider.WidgetDescriptionItemProvider;
import org.eclipse.sirius.components.widgets.reference.ReferenceFactory;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.ContainmentReferenceWidgetDescription} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class ContainmentReferenceWidgetDescriptionItemProvider extends WidgetDescriptionItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ContainmentReferenceWidgetDescriptionItemProvider(AdapterFactory adapterFactory) {
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
            this.addManyPropertyDescriptor(object);
            this.addTypePropertyDescriptor(object);
            this.addOwnerExpressionPropertyDescriptor(object);
            this.addValueExpressionPropertyDescriptor(object);
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
                this.getString("_UI_ContainmentReferenceWidgetDescription_isEnabledExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_ContainmentReferenceWidgetDescription_isEnabledExpression_feature", "_UI_ContainmentReferenceWidgetDescription_type"),
                PapyrusWidgetsPackage.Literals.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Many feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addManyPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
                this.getResourceLocator(),
                this.getString("_UI_ContainmentReferenceWidgetDescription_many_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_ContainmentReferenceWidgetDescription_many_feature", "_UI_ContainmentReferenceWidgetDescription_type"),
                PapyrusWidgetsPackage.Literals.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__MANY,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
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
                this.getString("_UI_ContainmentReferenceWidgetDescription_type_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_ContainmentReferenceWidgetDescription_type_feature", "_UI_ContainmentReferenceWidgetDescription_type"),
                PapyrusWidgetsPackage.Literals.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__TYPE,
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
                this.getString("_UI_ContainmentReferenceWidgetDescription_ownerExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_ContainmentReferenceWidgetDescription_ownerExpression_feature", "_UI_ContainmentReferenceWidgetDescription_type"),
                PapyrusWidgetsPackage.Literals.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION,
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
                this.getString("_UI_ContainmentReferenceWidgetDescription_valueExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_ContainmentReferenceWidgetDescription_valueExpression_feature", "_UI_ContainmentReferenceWidgetDescription_type"),
                PapyrusWidgetsPackage.Literals.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION,
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
            this.childrenFeatures.add(PapyrusWidgetsPackage.Literals.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION);
            this.childrenFeatures.add(PapyrusWidgetsPackage.Literals.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION);
            this.childrenFeatures.add(PapyrusWidgetsPackage.Literals.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION);
            this.childrenFeatures.add(PapyrusWidgetsPackage.Literals.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CLICK_OPERATION);
            this.childrenFeatures.add(PapyrusWidgetsPackage.Literals.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__STYLE);
            this.childrenFeatures.add(PapyrusWidgetsPackage.Literals.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES);
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
     * This returns ContainmentReferenceWidgetDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/ContainmentReferenceWidgetDescription"));
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
        String label = ((ContainmentReferenceWidgetDescription) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_ContainmentReferenceWidgetDescription_type") : this.getString("_UI_ContainmentReferenceWidgetDescription_type") + " " + label;
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

        switch (notification.getFeatureID(ContainmentReferenceWidgetDescription.class)) {
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__MANY:
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__TYPE:
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__OWNER_EXPRESSION:
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__VALUE_EXPRESSION:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION:
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION:
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION:
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CLICK_OPERATION:
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__STYLE:
            case PapyrusWidgetsPackage.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
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

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CREATE_ELEMENT_OPERATION,
                PapyrusWidgetsFactory.eINSTANCE.createCreateElementInReferenceOperation()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REMOVE_OPERATION,
                PapyrusWidgetsFactory.eINSTANCE.createMultiReferenceRemoveOperation()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__REORDER_OPERATION,
                PapyrusWidgetsFactory.eINSTANCE.createMultiReferenceReorderOperation()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CLICK_OPERATION,
                PapyrusWidgetsFactory.eINSTANCE.createClickReferenceValueOperation()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__STYLE,
                ReferenceFactory.eINSTANCE.createReferenceWidgetDescriptionStyle()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__STYLE,
                ReferenceFactory.eINSTANCE.createConditionalReferenceWidgetDescriptionStyle()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES,
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

        boolean qualify = childFeature == PapyrusWidgetsPackage.Literals.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__STYLE ||
                childFeature == PapyrusWidgetsPackage.Literals.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES;

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
