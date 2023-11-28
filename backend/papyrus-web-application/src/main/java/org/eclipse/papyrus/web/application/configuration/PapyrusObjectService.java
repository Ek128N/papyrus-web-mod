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
import java.util.Optional;

import org.eclipse.papyrus.web.services.api.IImageOverrideService;
import org.eclipse.sirius.components.core.api.IDefaultObjectService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IObjectServiceDelegate;
import org.eclipse.uml2.uml.Element;
import org.springframework.stereotype.Service;

/**
 * Specialized version of {@link IObjectService} for Papyrus application.
 *
 * @author Arthur Daussy
 */
@Service
public class PapyrusObjectService implements IObjectServiceDelegate {

    private List<IImageOverrideService> imageOverriders;

    private IDefaultObjectService defaultObjectService;

    public PapyrusObjectService(List<IImageOverrideService> imageOverriders, IDefaultObjectService defaultObjectService) {
        this.imageOverriders = imageOverriders;
        this.defaultObjectService = defaultObjectService;

    }

    @Override
    public String getId(Object object) {
        return this.defaultObjectService.getId(object);
    }

    @Override
    public String getLabel(Object object) {
        return this.defaultObjectService.getLabel(object);
    }

    @Override
    public String getKind(Object object) {
        return this.defaultObjectService.getKind(object);
    }

    @Override
    public String getFullLabel(Object object) {
        return this.defaultObjectService.getFullLabel(object);
    }

    @Override
    public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
        return this.defaultObjectService.getObject(editingContext, objectId);
    }

    @Override
    public List<Object> getContents(IEditingContext editingContext, String objectId) {
        return this.defaultObjectService.getContents(editingContext, objectId);
    }

    @Override
    public Optional<String> getLabelField(Object object) {
        return this.defaultObjectService.getLabelField(object);
    }

    @Override
    public boolean isLabelEditable(Object object) {
        return this.defaultObjectService.isLabelEditable(object);
    }

    @Override
    public boolean canHandle(Object object) {
        return object instanceof Element;
    }

    @Override
    public boolean canHandle(IEditingContext editingContext) {
        // At the time of writing this method is used to get an object from it id and the content of an object from its
        // id. For the moment we only customize the image of an object which mean that the framework use
        // canHandle(Object
        // object) to evaluate the choice of the IObjectServiceDelegate. Be be aware that is you want ot use
        // getContents(IEditingContext editingContext, String objectId) or getObject(IEditingContext editingContext,
        // String objectId) you will need to implement this method.
        return false;
    }

    @Override
    public List<String> getImagePath(Object object) {
        List<String> images = this.defaultObjectService.getImagePath(object);

        return images.stream().map(image -> this.imageOverriders.stream().map(imgOverrider -> imgOverrider.getOverrideImage(image)) //
                .filter(img -> img.isPresent())//
                .map(img -> img.get())//
                .findFirst() //
                .orElse(image) //
        ).toList();
    }

}
