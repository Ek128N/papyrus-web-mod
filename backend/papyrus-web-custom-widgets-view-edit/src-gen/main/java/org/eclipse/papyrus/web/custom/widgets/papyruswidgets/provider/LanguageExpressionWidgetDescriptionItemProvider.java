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
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.papyrus.web.custom.widgets.PapyrusWebCustomWidgetsEditPlugin;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.LanguageExpressionWidgetDescription;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage;
import org.eclipse.sirius.components.view.form.provider.WidgetDescriptionItemProvider;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.LanguageExpressionWidgetDescription} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class LanguageExpressionWidgetDescriptionItemProvider extends WidgetDescriptionItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public LanguageExpressionWidgetDescriptionItemProvider(AdapterFactory adapterFactory) {
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
                this.getString("_UI_LanguageExpressionWidgetDescription_isEnabledExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_LanguageExpressionWidgetDescription_isEnabledExpression_feature", "_UI_LanguageExpressionWidgetDescription_type"),
                PapyrusWidgetsPackage.Literals.LANGUAGE_EXPRESSION_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This returns LanguageExpressionWidgetDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/LanguageExpressionWidgetDescription"));
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
        String label = ((LanguageExpressionWidgetDescription) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_LanguageExpressionWidgetDescription_type") : this.getString("_UI_LanguageExpressionWidgetDescription_type") + " " + label;
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

        switch (notification.getFeatureID(LanguageExpressionWidgetDescription.class)) {
            case PapyrusWidgetsPackage.LANGUAGE_EXPRESSION_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
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
