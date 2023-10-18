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
import java.util.Objects;
import java.util.Optional;

import org.eclipse.papyrus.web.services.api.IImageOverrideService;
import org.eclipse.papyrus.web.sirius.contributions.ServiceOverride;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;

/**
 * Specialized version of {@link IObjectService} for Papyrus application.
 *
 * @author Arthur Daussy
 */
@ServiceOverride(org.eclipse.sirius.components.emf.services.ObjectService.class)
public class PapyrusObjectService implements IObjectService {

    private org.eclipse.sirius.components.emf.services.ObjectService delegate;

    private List<IImageOverrideService> imageOverriders;

    public PapyrusObjectService(org.eclipse.sirius.components.emf.services.ObjectService delegate, List<IImageOverrideService> imageOverriders) {
        this.delegate = Objects.requireNonNull(delegate);
        this.imageOverriders = imageOverriders;

    }

    @Override
    public String getId(Object object) {
        return this.delegate.getId(object);
    }

    @Override
    public String getKind(Object object) {
        return this.delegate.getKind(object);
    }

    @Override
    public String getLabel(Object object) {
        return this.delegate.getLabel(object);
    }

    @Override
    public String getFullLabel(Object object) {
        return this.delegate.getFullLabel(object);
    }

    @Override
    public String getImagePath(Object object) {
        String image = this.delegate.getImagePath(object);

        return this.imageOverriders.stream().map(imgOverrider -> imgOverrider.getOverrideImage(image)) //
                .filter(img -> img.isPresent())//
                .map(img -> img.get())//
                .findFirst() //
                .orElse(image);
    }

    @Override
    public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
        return this.delegate.getObject(editingContext, objectId);
    }

    @Override
    public List<Object> getContents(IEditingContext editingContext, String objectId) {
        return this.delegate.getContents(editingContext, objectId);
    }

    @Override
    public Optional<String> getLabelField(Object object) {
        return this.delegate.getLabelField(object);
    }

    @Override
    public boolean isLabelEditable(Object object) {
        return this.delegate.isLabelEditable(object);
    }

}
