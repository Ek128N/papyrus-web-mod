/*****************************************************************************
 * Copyright (c) 2024 CEA LIST, Obeo.
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
 *****************************************************************************/
package org.eclipse.papyrus.web.custom.widgets.provider.customimpl;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.provider.PapyrusWidgetsItemProviderAdapterFactory;

/**
 * Custom implementation of {@link PapyrusWidgetsItemProviderAdapterFactory}.
 *
 * @author Jerome Gout
 */
public class PapyrusWidgetsItemProviderAdapterFactoryCustomImpl extends PapyrusWidgetsItemProviderAdapterFactory {

    @Override
    public Adapter createClearReferenceOperationAdapter() {
        if (this.clearReferenceOperationItemProvider == null) {
            this.clearReferenceOperationItemProvider = new ClearReferenceOperationItemProviderCustomImpl(this);
        }

        return this.clearReferenceOperationItemProvider;
    }

    @Override
    public Adapter createClickReferenceValueOperationAdapter() {
        if (this.clickReferenceValueOperationItemProvider == null) {
            this.clickReferenceValueOperationItemProvider = new ClickReferenceValueOperationItemProviderCustomImpl(this);
        }

        return this.clickReferenceValueOperationItemProvider;
    }

    @Override
    public Adapter createContainmentReferenceWidgetDescriptionAdapter() {
        if (this.containmentReferenceWidgetDescriptionItemProvider == null) {
            this.containmentReferenceWidgetDescriptionItemProvider = new ContainmentReferenceWidgetDescriptionItemProviderCustomImpl(this);
        }

        return this.containmentReferenceWidgetDescriptionItemProvider;
    }

    @Override
    public Adapter createCreateElementInReferenceOperationAdapter() {
        if (this.createElementInReferenceOperationItemProvider == null) {
            this.createElementInReferenceOperationItemProvider = new CreateElementInReferenceOperationItemProviderCustomImpl(this);
        }

        return this.createElementInReferenceOperationItemProvider;
    }

    @Override
    public Adapter createLanguageExpressionWidgetDescriptionAdapter() {
        if (this.languageExpressionWidgetDescriptionItemProvider == null) {
            this.languageExpressionWidgetDescriptionItemProvider = new LanguageExpressionWidgetDescriptionItemProviderCustomImpl(this);
        }

        return this.languageExpressionWidgetDescriptionItemProvider;
    }

    @Override
    public Adapter createMonoReferenceSetOperationAdapter() {
        if (this.monoReferenceSetOperationItemProvider == null) {
            this.monoReferenceSetOperationItemProvider = new MonoReferenceSetOperationItemProviderCustomImpl(this);
        }

        return this.monoReferenceSetOperationItemProvider;
    }

    @Override
    public Adapter createMonoReferenceUnsetOperationAdapter() {
        if (this.monoReferenceUnsetOperationItemProvider == null) {
            this.monoReferenceUnsetOperationItemProvider = new MonoReferenceUnsetOperationItemProviderCustomImpl(this);
        }

        return this.monoReferenceUnsetOperationItemProvider;
    }

    @Override
    public Adapter createMonoReferenceWidgetDescriptionAdapter() {
        if (this.monoReferenceWidgetDescriptionItemProvider == null) {
            this.monoReferenceWidgetDescriptionItemProvider = new MonoReferenceWidgetDescriptionItemProviderCustomImpl(this);
        }

        return this.monoReferenceWidgetDescriptionItemProvider;
    }

    @Override
    public Adapter createMultiReferenceAddOperationAdapter() {
        if (this.multiReferenceAddOperationItemProvider == null) {
            this.multiReferenceAddOperationItemProvider = new MultiReferenceAddOperationItemProviderCustomImpl(this);
        }

        return this.multiReferenceAddOperationItemProvider;
    }

    @Override
    public Adapter createMultiReferenceRemoveOperationAdapter() {
        if (this.multiReferenceRemoveOperationItemProvider == null) {
            this.multiReferenceRemoveOperationItemProvider = new MultiReferenceRemoveOperationItemProviderCustomImpl(this);
        }

        return this.multiReferenceRemoveOperationItemProvider;
    }

    @Override
    public Adapter createMultiReferenceReorderOperationAdapter() {
        if (this.multiReferenceReorderOperationItemProvider == null) {
            this.multiReferenceReorderOperationItemProvider = new MultiReferenceReorderOperationItemProviderCustomImpl(this);
        }

        return this.multiReferenceReorderOperationItemProvider;
    }

    @Override
    public Adapter createMultiReferenceWidgetDescriptionAdapter() {
        if (this.multiReferenceWidgetDescriptionItemProvider == null) {
            this.multiReferenceWidgetDescriptionItemProvider = new MultiReferenceWidgetDescriptionItemProviderCustomImpl(this);
        }

        return this.multiReferenceWidgetDescriptionItemProvider;
    }

    @Override
    public Adapter createPrimitiveListAddOperationAdapter() {
        if (this.primitiveListAddOperationItemProvider == null) {
            this.primitiveListAddOperationItemProvider = new PrimitiveListAddOperationItemProviderCustomImpl(this);
        }

        return this.primitiveListAddOperationItemProvider;
    }

    @Override
    public Adapter createPrimitiveListDeleteOperationAdapter() {
        if (this.primitiveListDeleteOperationItemProvider == null) {
            this.primitiveListDeleteOperationItemProvider = new PrimitiveListDeleteOperationItemProviderCustomImpl(this);
        }

        return this.primitiveListDeleteOperationItemProvider;
    }

    @Override
    public Adapter createPrimitiveListWidgetDescriptionAdapter() {
        if (this.primitiveListWidgetDescriptionItemProvider == null) {
            this.primitiveListWidgetDescriptionItemProvider = new PrimitiveListWidgetDescriptionItemProviderCustomImpl(this);
        }

        return this.primitiveListWidgetDescriptionItemProvider;
    }

    @Override
    public Adapter createPrimitiveRadioWidgetDescriptionAdapter() {
        if (this.primitiveRadioWidgetDescriptionItemProvider == null) {
            this.primitiveRadioWidgetDescriptionItemProvider = new PrimitiveRadioWidgetDescriptionItemProviderCustomImpl(this);
        }

        return this.primitiveRadioWidgetDescriptionItemProvider;
    }
}
