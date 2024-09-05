/*****************************************************************************
 * Copyright (c) 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.uml;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.UMLPlugin;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * Bean used to fill the global UML registries.
 *
 * @author Arthur Daussy
 */
@Service
public class UMLRegistriesConfigurer implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        org.eclipse.uml2.uml.resources.util.UMLResourcesUtil.initPackageRegistry(EPackage.Registry.INSTANCE);
        org.eclipse.uml2.uml.resources.util.UMLResourcesUtil.initEPackageNsURIToProfileLocationMap(UMLPlugin.getEPackageNsURIToProfileLocationMap());
        org.eclipse.uml2.uml.resources.util.UMLResourcesUtil.initContentHandlerRegistry(ContentHandler.Registry.INSTANCE);
        org.eclipse.uml2.uml.resources.util.UMLResourcesUtil.initResourceFactoryRegistry(Resource.Factory.Registry.INSTANCE);
    }
}
