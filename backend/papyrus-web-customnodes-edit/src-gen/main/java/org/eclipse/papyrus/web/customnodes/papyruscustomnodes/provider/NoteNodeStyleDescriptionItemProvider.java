/**
 * Copyright (c) 2023, 2024 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 */
package org.eclipse.papyrus.web.customnodes.papyruscustomnodes.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
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
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.NoteNodeStyleDescription;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.PapyrusCustomNodesPackage;
import org.eclipse.papyrus.web.customnodes.provider.CustomNodesEditPlugin;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.papyrus.web.customnodes.papyruscustomnodes.NoteNodeStyleDescription} object. <!-- begin-user-doc
 * --> <!-- end-user-doc -->
 *
 * @generated
 */
public class NoteNodeStyleDescriptionItemProvider extends ItemProviderAdapter
        implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NoteNodeStyleDescriptionItemProvider(AdapterFactory adapterFactory) {
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

            this.addBorderColorPropertyDescriptor(object);
            this.addBorderRadiusPropertyDescriptor(object);
            this.addBorderSizePropertyDescriptor(object);
            this.addBorderLineStylePropertyDescriptor(object);
            this.addBackgroundPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Border Color feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addBorderColorPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
                this.getResourceLocator(),
                this.getString("_UI_BorderStyle_borderColor_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_BorderStyle_borderColor_feature", "_UI_BorderStyle_type"),
                DiagramPackage.Literals.BORDER_STYLE__BORDER_COLOR,
                true,
                false,
                false,
                null,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Border Radius feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addBorderRadiusPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
                this.getResourceLocator(),
                this.getString("_UI_BorderStyle_borderRadius_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_BorderStyle_borderRadius_feature", "_UI_BorderStyle_type"),
                DiagramPackage.Literals.BORDER_STYLE__BORDER_RADIUS,
                true,
                false,
                false,
                ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Border Size feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addBorderSizePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
                this.getResourceLocator(),
                this.getString("_UI_BorderStyle_borderSize_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_BorderStyle_borderSize_feature", "_UI_BorderStyle_type"),
                DiagramPackage.Literals.BORDER_STYLE__BORDER_SIZE,
                true,
                false,
                false,
                ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Border Line Style feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addBorderLineStylePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
                this.getResourceLocator(),
                this.getString("_UI_BorderStyle_borderLineStyle_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_BorderStyle_borderLineStyle_feature", "_UI_BorderStyle_type"),
                DiagramPackage.Literals.BORDER_STYLE__BORDER_LINE_STYLE,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Background feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addBackgroundPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
                this.getResourceLocator(),
                this.getString("_UI_NoteNodeStyleDescription_background_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_NoteNodeStyleDescription_background_feature", "_UI_NoteNodeStyleDescription_type"),
                PapyrusCustomNodesPackage.Literals.NOTE_NODE_STYLE_DESCRIPTION__BACKGROUND,
                true,
                false,
                true,
                null,
                null,
                null));
    }

    /**
     * This returns NoteNodeStyleDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/NoteNodeStyleDescription"));
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
        NoteNodeStyleDescription noteNodeStyleDescription = (NoteNodeStyleDescription) object;
        return this.getString("_UI_NoteNodeStyleDescription_type") + " " + noteNodeStyleDescription.getBorderRadius();
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

        switch (notification.getFeatureID(NoteNodeStyleDescription.class)) {
            case PapyrusCustomNodesPackage.NOTE_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
            case PapyrusCustomNodesPackage.NOTE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
            case PapyrusCustomNodesPackage.NOTE_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
            case PapyrusCustomNodesPackage.NOTE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
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
        return CustomNodesEditPlugin.INSTANCE;
    }

}
