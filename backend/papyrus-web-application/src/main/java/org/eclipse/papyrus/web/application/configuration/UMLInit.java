/*******************************************************************************
 * Copyright (c) 2022, 2024 CEA LIST, Obeo.
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
 *******************************************************************************/
package org.eclipse.papyrus.web.application.configuration;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.UMLPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * Mock the start of the plugin <i>org.eclipse.uml2.uml.UMLPlugin</i>.
 *
 * This initialization is required in order to be able to use
 * <i>org.eclipse.uml2.uml.resources.util.UMLResourcesUtil.init(ResourceSet)</i>
 *
 * @author Arthur Daussy
 */
@Component
public class UMLInit {

    private static final Logger LOGGER = LoggerFactory.getLogger(UMLInit.class);

    @PostConstruct
    public void init() {
        LOGGER.info("Initializing EPackage Registry");
        // Those mapping are normally done using extension point in Eclipse platform
        Map<String, URI> ePackageNsURIToProfileLocationMap = UMLPlugin.getEPackageNsURIToProfileLocationMap();

        ePackageNsURIToProfileLocationMap.put("http://www.eclipse.org/Papyrus/2014/profile/profileExternalization",
                URI.createURI("pathmap://PAPYRUS_PROFILEEXT/ProfileExternalization.profile.uml#_Mzzc0EWjEeSNXJj2G3jVCw"));
        ePackageNsURIToProfileLocationMap.put("http://www.eclipse.org/papyrus/documentation",
                URI.createURI("pathmap://PAPYRUS_DOCUMENTATION/Papyrus.profile.uml#_H9068AEYEeCIz8iAxBJnfA"));
        ePackageNsURIToProfileLocationMap.put("http://www.eclipse.org/uml2/5.0.0/UML/Profile/Standard", //
                URI.createURI("pathmap://UML_PROFILES/Standard.profile.uml#_0"));
        ePackageNsURIToProfileLocationMap.put("http://www.eclipse.org/Papyrus/2014/common/filters",
                URI.createURI("pathmap://PAPYRUS_FILTERS/filters.uml#_u1APUG86EeSumdlFUM6GVw"));
        ePackageNsURIToProfileLocationMap.put("http://www.eclipse.org/Papyrus/2014/diagram/assistant",
                URI.createURI("pathmap://PAPYRUS_MODELING_ASSISTANTS/assistant.merged.uml#_0"));
        ePackageNsURIToProfileLocationMap.put("http://www.eclipse.org/ocl/2015/OCLforUML",
                URI.createURI("pathmap://OCL_PROFILES/OCLforUML.profile.uml#_0"));
        ePackageNsURIToProfileLocationMap.put("http://www.eclipse.org/uml2/schemas/Ecore/5",
                URI.createURI("pathmap://UML_PROFILES/Ecore.profile.uml#_0"));
    }
}
