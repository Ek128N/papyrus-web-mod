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
package org.eclipse.papyrus.web.graphics;

import java.util.Map;
import java.util.Optional;

import org.eclipse.papyrus.web.services.api.IImageOverrideService;

/**
 * Service in charge of scanning icon to override base EMF icon.
 *
 * @author Arthur Daussy
 */
public class SvgIconOverrideService implements IImageOverrideService {

    private final Map<String, String> iconMap;

    public SvgIconOverrideService(Map<String, String> iconMap) {
        super();
        this.iconMap = iconMap;
    }

    @Override
    public Optional<String> getOverrideImage(String baseImage) {
        if (baseImage != null && !baseImage.endsWith("svg")) {
            String[] parts = baseImage.split("\\.");
            if (parts.length > 1) {
                return Optional.ofNullable(this.iconMap.get(parts[parts.length - 2]));
            }
        }
        return Optional.empty();
    }

}
