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
package org.eclipse.papyrus.web.services.aqlservices.properties;

import java.util.List;

import org.eclipse.papyrus.uml.domain.services.properties.PropertiesImageServices;
import org.eclipse.uml2.uml.Image;

/**
 * At the moment, there is an error if a service returns void (see discussion in
 * {@link https://github.com/eclipse-sirius/sirius-components/issues/1343}), for this reason we wrap all services of
 * {@see PropertiesImageServices} class in order to be sure that services return something.
 *
 * @author Jerome Gout
 */
public class PropertiesImageServicesWrapper {
    private PropertiesImageServices delegate;

    public PropertiesImageServicesWrapper() {
        this.delegate = new PropertiesImageServices();
    }

    /**
     * Replacement of {@link PropertiesImageServices#getImageName(Image)}.
     */
    public String getImageName(Image image) {
        return this.delegate.getImageName(image);
    }

    /**
     * Replacement of {@link PropertiesImageServices#setImageName(Image, String)}.
     */
    public Image setImageName(Image image, String name) {
        this.delegate.setImageName(image, name);
        return image;
    }

    /**
     * Replacement of {@link PropertiesImageServices#getImageKind(Image)}.
     */
    public String getImageKind(Image image) {
        return this.delegate.getImageKind(image);
    }

    /**
     * Replacement of {@link PropertiesImageServices#setImageKind(Image, String)}.
     */
    public Image setImageKind(Image image, String kind) {
        this.delegate.setImageKind(image, kind);
        return image;
    }

    /**
     * Replacement of {@link PropertiesImageServices#getImageKindEnumerations(Image)}.
     */
    public List<String> getImageKindEnumerations(Image obj) {
        return this.delegate.getImageKindEnumerations(obj);
    }

    /**
     * Replacement of {@link PropertiesImageServices#getImageExpression(Image)}.
     */
    public String getImageExpression(Image image) {
        return this.delegate.getImageExpression(image);
    }

    /**
     * Replacement of {@link PropertiesImageServices#setImageExpression(Image, String)}.
     */
    public Image setImageExpression(Image image, String expression) {
        this.delegate.setImageExpression(image, expression);
        return image;
    }

}
