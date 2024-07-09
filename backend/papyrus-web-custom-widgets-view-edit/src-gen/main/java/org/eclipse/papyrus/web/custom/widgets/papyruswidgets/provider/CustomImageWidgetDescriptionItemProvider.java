/*****************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo, Artal Technologies.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *  Titouan BOUETE-GIRAUD (Artal Technologies) - Issue 210
 *****************************************************************************/
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
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CustomImageWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsFactory;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage;
import org.eclipse.sirius.components.view.form.provider.WidgetDescriptionItemProvider;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.CustomImageWidgetDescription} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class CustomImageWidgetDescriptionItemProvider extends WidgetDescriptionItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public CustomImageWidgetDescriptionItemProvider(AdapterFactory adapterFactory) {
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

            this.addUuidExpressionPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Uuid Expression feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addUuidExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
                this.getResourceLocator(),
                this.getString("_UI_CustomImageWidgetDescription_uuidExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_CustomImageWidgetDescription_uuidExpression_feature", "_UI_CustomImageWidgetDescription_type"),
                PapyrusWidgetsPackage.Literals.CUSTOM_IMAGE_WIDGET_DESCRIPTION__UUID_EXPRESSION,
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
            this.childrenFeatures.add(PapyrusWidgetsPackage.Literals.CUSTOM_IMAGE_WIDGET_DESCRIPTION__ADD_IMAGE_OPERATION);
            this.childrenFeatures.add(PapyrusWidgetsPackage.Literals.CUSTOM_IMAGE_WIDGET_DESCRIPTION__SELECT_IMAGE_OPERATION);
            this.childrenFeatures.add(PapyrusWidgetsPackage.Literals.CUSTOM_IMAGE_WIDGET_DESCRIPTION__REMOVE_IMAGE_OPERATION);
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
     * This returns CustomImageWidgetDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/CustomImageWidgetDescription"));
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
        String label = ((CustomImageWidgetDescription) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_CustomImageWidgetDescription_type") : this.getString("_UI_CustomImageWidgetDescription_type") + " " + label;
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

        switch (notification.getFeatureID(CustomImageWidgetDescription.class)) {
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__UUID_EXPRESSION:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__ADD_IMAGE_OPERATION:
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__SELECT_IMAGE_OPERATION:
            case PapyrusWidgetsPackage.CUSTOM_IMAGE_WIDGET_DESCRIPTION__REMOVE_IMAGE_OPERATION:
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

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.CUSTOM_IMAGE_WIDGET_DESCRIPTION__ADD_IMAGE_OPERATION,
                PapyrusWidgetsFactory.eINSTANCE.createAddImageOperation()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.CUSTOM_IMAGE_WIDGET_DESCRIPTION__SELECT_IMAGE_OPERATION,
                PapyrusWidgetsFactory.eINSTANCE.createSelectImageOperation()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.CUSTOM_IMAGE_WIDGET_DESCRIPTION__REMOVE_IMAGE_OPERATION,
                PapyrusWidgetsFactory.eINSTANCE.createRemoveImageOperation()));
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
