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
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.papyrus.web.custom.widgets.PapyrusWebCustomWidgetsEditPlugin;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListItemActionOperation;
import org.eclipse.sirius.components.view.ViewFactory;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListItemActionOperation} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class PrimitiveListItemActionOperationItemProvider extends ItemProviderAdapter
        implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public PrimitiveListItemActionOperationItemProvider(AdapterFactory adapterFactory) {
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

            this.addIconURLExpressionPropertyDescriptor(object);
            this.addPreconditionExpressionPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Icon URL Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addIconURLExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
                this.getResourceLocator(),
                this.getString("_UI_PrimitiveListItemActionOperation_iconURLExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_PrimitiveListItemActionOperation_iconURLExpression_feature", "_UI_PrimitiveListItemActionOperation_type"),
                PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__ICON_URL_EXPRESSION,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Precondition Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addPreconditionExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
                this.getResourceLocator(),
                this.getString("_UI_PrimitiveListItemActionOperation_preconditionExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_PrimitiveListItemActionOperation_preconditionExpression_feature", "_UI_PrimitiveListItemActionOperation_type"),
                PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__PRECONDITION_EXPRESSION,
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
            this.childrenFeatures.add(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__BODY);
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
     * This returns PrimitiveListItemActionOperation.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/PrimitiveListItemActionOperation"));
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
        String label = ((PrimitiveListItemActionOperation) object).getIconURLExpression();
        return label == null || label.length() == 0 ? this.getString("_UI_PrimitiveListItemActionOperation_type") : this.getString("_UI_PrimitiveListItemActionOperation_type") + " " + label;
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

        switch (notification.getFeatureID(PrimitiveListItemActionOperation.class)) {
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__ICON_URL_EXPRESSION:
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__PRECONDITION_EXPRESSION:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__BODY:
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

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__BODY,
                ViewFactory.eINSTANCE.createChangeContext()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__BODY,
                ViewFactory.eINSTANCE.createCreateInstance()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__BODY,
                ViewFactory.eINSTANCE.createSetValue()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__BODY,
                ViewFactory.eINSTANCE.createUnsetValue()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__BODY,
                ViewFactory.eINSTANCE.createDeleteElement()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__BODY,
                ViewFactory.eINSTANCE.createLet()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_ITEM_ACTION_OPERATION__BODY,
                ViewFactory.eINSTANCE.createIf()));
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
