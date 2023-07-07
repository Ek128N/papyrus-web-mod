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

package org.eclipse.papyrus.web.application.properties;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.ViewFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColorRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(ColorRegistry.class);

    private final Map<String, UserColor> colors;

    public ColorRegistry() {
        colors = new HashMap<>();
    }

    public Optional<UserColor> getColorByName(String name) {
        if (name == null)
            return Optional.empty();
        return Optional.ofNullable(colors.get(name));
    }

    public void registerColor(String name, String value) {
        if (colors.containsKey(name)) {
            LOGGER.warn("Color named " + name + " already exists in color registry");
        }
        colors.put(name, createFixedColor(name, value));
    }

    public Collection<UserColor> getAllColors() {
        return colors.values();
    }

    private UserColor createFixedColor(String name, String value) {
        var color = ViewFactory.eINSTANCE.createFixedColor();
        color.setName(name);
        color.setValue(value);
        return color;
    }
}
