/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.graphics;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.uml2.uml.edit.providers.UMLItemProviderAdapterFactory;

/**
 * AdapterFactory customized to provide specific ItemProvider for UML2 metamodel.
 *
 * @author Cedric Brun <cedric.brun@obeo.fr>
 */
public class CustomUMLItemProviderAdapterFactory extends UMLItemProviderAdapterFactory {

    @Override
    public Adapter adapt(Notifier target, Object type) {
        for (Adapter adapter : target.eAdapters()) {
            if (adapter.isAdapterForType(type)) {
                return adapter;
            }
        }
        if (type == IItemLabelProvider.class) {
            final IItemLabelProvider delegate = (IItemLabelProvider) this.createAdapter(target, type);
            ItemProviderAdapter wrapper = new ReplaceGIFtoSVGItemProvider(this, delegate);
            this.associate(wrapper, target);
            return wrapper;
        }
        return this.adaptNew(target, type);
    }

}
