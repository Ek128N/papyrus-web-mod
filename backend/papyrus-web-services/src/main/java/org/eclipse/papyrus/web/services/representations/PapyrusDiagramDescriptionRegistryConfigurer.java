/*******************************************************************************
 * Copyright (c) 2022 CEA, Obeo.
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
package org.eclipse.papyrus.web.services.representations;

import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a {@link PapyrusRepresentationDescriptionRegistry}.
 *
 * @author Arthur Daussy
 */
@Configuration
public class PapyrusDiagramDescriptionRegistryConfigurer {

    @Bean
    public PapyrusRepresentationDescriptionRegistry viewRegistry(IDiagramIdProvider idProvider) {
        return new PapyrusRepresentationDescriptionRegistry(idProvider);
    }
}
