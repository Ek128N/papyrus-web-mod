/*****************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
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

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.PapyrusCustomnodesPackage;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.provider.PapyrusCustomnodesItemProviderAdapterFactory;
import org.eclipse.papyrus.web.customnodes.papyruscustomnodes.provider.customimpl.PapyrusCustomnodesItemProviderAdapterFactoryCustomImpl;
import org.eclipse.sirius.components.emf.configuration.ChildExtenderProvider;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Registers the customnodes DSL extension for the papyrus custom nodes.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@Configuration
public class PapyrusCustomNodeEMFConfiguration {
    @Bean
    public EPackage papyrusCustomNodeEPackage() {
        return PapyrusCustomnodesPackage.eINSTANCE;
    }

    @Bean
    public AdapterFactory papyrusCustomNodeAdapterFactory() {
        return new PapyrusCustomnodesItemProviderAdapterFactoryCustomImpl();
    }

    @Bean
    public ChildExtenderProvider papyrusCustomNodeChildExtenderProvider() {
        return new ChildExtenderProvider(DiagramPackage.eNS_URI, PapyrusCustomnodesItemProviderAdapterFactory.DiagramChildCreationExtender::new);
    }
}
