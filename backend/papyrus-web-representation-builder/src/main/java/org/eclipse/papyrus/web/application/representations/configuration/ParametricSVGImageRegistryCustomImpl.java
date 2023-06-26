/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.papyrus.web.application.representations.configuration;

import java.util.List;
import java.util.UUID;

import org.eclipse.papyrus.web.sirius.contributions.ServiceOverride;
import org.eclipse.sirius.components.collaborative.diagrams.api.IParametricSVGImageRegistry;
import org.eclipse.sirius.components.collaborative.diagrams.api.ParametricSVGImage;

/**
 * Provide a IParametricSVGImageRegistry.
 *
 * @author lfasani
 */
@ServiceOverride(org.eclipse.sirius.web.services.diagram.ParametricSVGImageRegistry.class)
public class ParametricSVGImageRegistryCustomImpl implements IParametricSVGImageRegistry {
    public static final UUID PARAMETRIC_CLASS_IMAGE_ID = UUID.nameUUIDFromBytes("Class".getBytes());

    public static final UUID PARAMETRIC_PACKAGE_IMAGE_ID = UUID.nameUUIDFromBytes("Package".getBytes());

    public static final UUID PARAMETRIC_NOTE_IMAGE_ID = UUID.nameUUIDFromBytes("Note".getBytes());

    public static final UUID PARAMETRIC_FORK_IMAGE_ID = UUID.nameUUIDFromBytes("Fork.svg".getBytes());

    public static final UUID PARAMETRIC_JOIN_IMAGE_ID = UUID.nameUUIDFromBytes("Join.svg".getBytes());

    public static final UUID PARAMETRIC_CHOICE_IMAGE_ID = UUID.nameUUIDFromBytes("Choice.svg".getBytes());

    @Override
    public List<ParametricSVGImage> getImages() {
        return List.of(new ParametricSVGImage(PARAMETRIC_PACKAGE_IMAGE_ID, "Package", "parametricSVGs/package.svg"),
                new ParametricSVGImage(PARAMETRIC_CLASS_IMAGE_ID, "Class", "parametricSVGs/class.svg"), new ParametricSVGImage(PARAMETRIC_NOTE_IMAGE_ID, "Note", "parametricSVGs/note.svg"),
                new ParametricSVGImage(PARAMETRIC_FORK_IMAGE_ID, "Fork", "parametricSVGs/Fork.svg"), new ParametricSVGImage(PARAMETRIC_JOIN_IMAGE_ID, "Join", "parametricSVGs/Fork.svg"),
                new ParametricSVGImage(PARAMETRIC_CHOICE_IMAGE_ID, "Choice", "parametricSVGs/Choice.svg"));
    }
}
