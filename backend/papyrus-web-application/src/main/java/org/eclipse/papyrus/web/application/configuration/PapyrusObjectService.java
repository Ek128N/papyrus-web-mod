/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.configuration;

import java.util.List;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.papyrus.web.services.api.IImageOverrideService;
import org.eclipse.sirius.components.core.api.ILabelServiceDelegate;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.DefaultLabelService;
import org.eclipse.sirius.components.emf.services.LabelFeatureProviderRegistry;
import org.eclipse.uml2.uml.Element;
import org.springframework.stereotype.Service;

/**
 * Specialized version of {@link IObjectService} for Papyrus application.
 *
 * @author Arthur Daussy
 */
@Service
public class PapyrusObjectService extends DefaultLabelService implements ILabelServiceDelegate {

    private List<IImageOverrideService> imageOverriders;

    public PapyrusObjectService(ComposedAdapterFactory composedAdapterFactory, LabelFeatureProviderRegistry labelFeatureProviderRegistry, List<IImageOverrideService> imageOverriders) {
        super(labelFeatureProviderRegistry, composedAdapterFactory, List.of());
        this.imageOverriders = imageOverriders;
    }

    @Override
    public boolean canHandle(Object object) {
        return object instanceof Element;
    }

    @Override
    public List<String> getImagePath(Object object) {
        List<String> images = super.getImagePath(object);

        return images.stream().map(image -> this.imageOverriders.stream().map(imgOverrider -> imgOverrider.getOverrideImage(image)) //
                .filter(img -> img.isPresent())//
                .map(img -> img.get())//
                .findFirst() //
                .orElse(image) //
        ).toList();
    }

}
