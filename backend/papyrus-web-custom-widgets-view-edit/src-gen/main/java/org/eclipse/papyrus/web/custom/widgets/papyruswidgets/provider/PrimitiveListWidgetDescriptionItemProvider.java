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
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsFactory;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription;
import org.eclipse.sirius.components.view.form.FormFactory;
import org.eclipse.sirius.components.view.form.provider.WidgetDescriptionItemProvider;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class PrimitiveListWidgetDescriptionItemProvider extends WidgetDescriptionItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public PrimitiveListWidgetDescriptionItemProvider(AdapterFactory adapterFactory) {
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

            this.addValueExpressionPropertyDescriptor(object);
            this.addDisplayExpressionPropertyDescriptor(object);
            this.addCandidatesExpressionPropertyDescriptor(object);
            this.addIsEnabledExpressionPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Value Expression feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addValueExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
                this.getResourceLocator(),
                this.getString("_UI_PrimitiveListWidgetDescription_valueExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_PrimitiveListWidgetDescription_valueExpression_feature", "_UI_PrimitiveListWidgetDescription_type"),
                PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_WIDGET_DESCRIPTION__VALUE_EXPRESSION,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Display Expression feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addDisplayExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
                this.getResourceLocator(),
                this.getString("_UI_PrimitiveListWidgetDescription_displayExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_PrimitiveListWidgetDescription_displayExpression_feature", "_UI_PrimitiveListWidgetDescription_type"),
                PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_WIDGET_DESCRIPTION__DISPLAY_EXPRESSION,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Candidates Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addCandidatesExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
                this.getResourceLocator(),
                this.getString("_UI_PrimitiveListWidgetDescription_candidatesExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_PrimitiveListWidgetDescription_candidatesExpression_feature", "_UI_PrimitiveListWidgetDescription_type"),
                PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_WIDGET_DESCRIPTION__CANDIDATES_EXPRESSION,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
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
                this.getString("_UI_PrimitiveListWidgetDescription_isEnabledExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_PrimitiveListWidgetDescription_isEnabledExpression_feature", "_UI_PrimitiveListWidgetDescription_type"),
                PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION,
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
            this.childrenFeatures.add(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_WIDGET_DESCRIPTION__STYLE);
            this.childrenFeatures.add(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_WIDGET_DESCRIPTION__CONDITIONAL_STYLES);
            this.childrenFeatures.add(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_WIDGET_DESCRIPTION__DELETE_OPERATION);
            this.childrenFeatures.add(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ADD_OPERATION);
            this.childrenFeatures.add(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_WIDGET_DESCRIPTION__REORDER_OPERATION);
            this.childrenFeatures.add(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ITEM_ACTION_OPERATION);
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
     * This returns PrimitiveListWidgetDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/PrimitiveListWidgetDescription"));
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
        String label = ((PrimitiveListWidgetDescription) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_PrimitiveListWidgetDescription_type") : this.getString("_UI_PrimitiveListWidgetDescription_type") + " " + label;
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

        switch (notification.getFeatureID(PrimitiveListWidgetDescription.class)) {
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__VALUE_EXPRESSION:
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__DISPLAY_EXPRESSION:
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__CANDIDATES_EXPRESSION:
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__STYLE:
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__DELETE_OPERATION:
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ADD_OPERATION:
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__REORDER_OPERATION:
            case PapyrusWidgetsPackage.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ITEM_ACTION_OPERATION:
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

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_WIDGET_DESCRIPTION__STYLE,
                FormFactory.eINSTANCE.createListDescriptionStyle()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_WIDGET_DESCRIPTION__STYLE,
                FormFactory.eINSTANCE.createConditionalListDescriptionStyle()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_WIDGET_DESCRIPTION__CONDITIONAL_STYLES,
                FormFactory.eINSTANCE.createConditionalListDescriptionStyle()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_WIDGET_DESCRIPTION__DELETE_OPERATION,
                PapyrusWidgetsFactory.eINSTANCE.createPrimitiveListDeleteOperation()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ADD_OPERATION,
                PapyrusWidgetsFactory.eINSTANCE.createPrimitiveListAddOperation()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_WIDGET_DESCRIPTION__REORDER_OPERATION,
                PapyrusWidgetsFactory.eINSTANCE.createPrimitiveListReorderOperation()));

        newChildDescriptors.add(this.createChildParameter(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_WIDGET_DESCRIPTION__ITEM_ACTION_OPERATION,
                PapyrusWidgetsFactory.eINSTANCE.createPrimitiveListItemActionOperation()));
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

        boolean qualify = childFeature == PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_WIDGET_DESCRIPTION__STYLE ||
                childFeature == PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_WIDGET_DESCRIPTION__CONDITIONAL_STYLES;

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
