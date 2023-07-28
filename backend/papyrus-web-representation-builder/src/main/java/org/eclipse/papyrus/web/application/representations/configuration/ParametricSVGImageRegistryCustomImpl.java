/*****************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo.
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
    public static final UUID PARAMETRIC_CLASS_IMAGE_ID = UUID.nameUUIDFromBytes("Class".getBytes()); //$NON-NLS-1$

    public static final UUID PARAMETRIC_USE_CASE_IMAGE_ID = UUID.nameUUIDFromBytes("UseCase".getBytes()); //$NON-NLS-1$

    public static final UUID PARAMETRIC_PACKAGE_IMAGE_ID = UUID.nameUUIDFromBytes("Package".getBytes()); //$NON-NLS-1$

    public static final UUID PARAMETRIC_NOTE_IMAGE_ID = UUID.nameUUIDFromBytes("Note".getBytes()); //$NON-NLS-1$

    public static final UUID PARAMETRIC_FORK_IMAGE_ID = UUID.nameUUIDFromBytes("Fork.svg".getBytes()); //$NON-NLS-1$

    public static final UUID PARAMETRIC_JOIN_IMAGE_ID = UUID.nameUUIDFromBytes("Join.svg".getBytes()); //$NON-NLS-1$

    public static final UUID PARAMETRIC_CHOICE_IMAGE_ID = UUID.nameUUIDFromBytes("Choice.svg".getBytes()); //$NON-NLS-1$

    @Override
    public List<ParametricSVGImage> getImages() {
        return List.of(new ParametricSVGImage(PARAMETRIC_PACKAGE_IMAGE_ID, "Package", "parametricSVGs/package.svg"), //$NON-NLS-1$//$NON-NLS-2$
                new ParametricSVGImage(PARAMETRIC_CLASS_IMAGE_ID, "Class", "parametricSVGs/class.svg"), new ParametricSVGImage(PARAMETRIC_NOTE_IMAGE_ID, "Note", "parametricSVGs/note.svg"), //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
                new ParametricSVGImage(PARAMETRIC_USE_CASE_IMAGE_ID, "UseCase", "parametricSVGs/UseCase.svg"), //$NON-NLS-1$//$NON-NLS-2$
                new ParametricSVGImage(PARAMETRIC_FORK_IMAGE_ID, "Fork", "parametricSVGs/Fork.svg"), new ParametricSVGImage(PARAMETRIC_JOIN_IMAGE_ID, "Join", "parametricSVGs/Fork.svg"), //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
                new ParametricSVGImage(PARAMETRIC_CHOICE_IMAGE_ID, "Choice", "parametricSVGs/Choice.svg")); //$NON-NLS-1$//$NON-NLS-2$
    }
}
