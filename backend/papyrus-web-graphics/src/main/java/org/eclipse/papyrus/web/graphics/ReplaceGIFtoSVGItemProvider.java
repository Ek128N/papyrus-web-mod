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

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.net.URL;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;

/**
 * Customized ItemProviderAdapter which replace any GIF image URL into an SVG URL.
 *
 * @author Cedric Brun <cedric.brun@obeo.fr
 */
public class ReplaceGIFtoSVGItemProvider extends ItemProviderAdapter implements IItemLabelProvider {

    private IItemLabelProvider delegate;

    public ReplaceGIFtoSVGItemProvider(AdapterFactory adapterFactory, IItemLabelProvider delegate) {
        super(adapterFactory);
        this.delegate = delegate;
    }

    @Override
    public Object getImage(Object object) {
        Object fromUMLEdit = this.delegate.getImage(object);
        List<String> imgs = Lists.newArrayList();
        if (fromUMLEdit instanceof ComposedImage) {
            for (URL composed : Iterables.filter(((ComposedImage) fromUMLEdit).getImages(), URL.class)) {
                String url = composed.toString();
                if (url.indexOf('!') != -1) {
                    imgs.add(url.substring(url.indexOf('!')).replace("gif", "svg")); //$NON-NLS-1$//$NON-NLS-2$
                }
            }
        }
        if (imgs.iterator().hasNext()) {
            // workaround for a current limitation of Sirius Components which won't display a list of URIs, to
            // get the SVG displayed we currently have no choice than to return a single one.
            // see https://github.com/eclipse-sirius/sirius-components/issues/116
            return URI.createURI(imgs.iterator().next());
        }
        return fromUMLEdit;
    }

    @Override
    public Object getImage(String key) {
        return this.delegate.getImage(key);
    }

    @Override
    public String getText(Object object) {
        return this.delegate.getText(object);
    }

}
