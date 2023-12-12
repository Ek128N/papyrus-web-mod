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
package org.eclipse.papyrus.web.services.representations;

import java.util.List;

import org.eclipse.sirius.components.representations.IRepresentationDescription;

/**
 * Interface used to override a IRepresentationDescription.
 *
 * <p>
 * This interface has been created for bug
 * https://gitlab.eclipse.org/eclipse/papyrus/org.eclipse.papyrus-web/-/issues/97. But once
 * https://github.com/eclipse-sirius/sirius-web/issues/2809 is fixed this is no longer needed
 * </p>
 *
 * @author Arthur Daussy
 */
public interface IRepresentationDescriptionOverrider {

    /**
     * Provide a list of new {@link IRepresentationDescription}s that have ids already registered in the representation description registry.
     *
     * @return a list of {@link IRepresentationDescription}
     */
    List<IRepresentationDescription> getOverridedDescriptions();
}
