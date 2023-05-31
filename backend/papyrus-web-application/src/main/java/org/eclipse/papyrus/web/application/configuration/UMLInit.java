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
package org.eclipse.papyrus.web.application.configuration;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.UMLPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
        LOGGER.info("Initializing EPackage Registry"); //$NON-NLS-1$
        // Those mapping are normally done using extension point in Eclipse platform
        Map<String, URI> ePackageNsURIToProfileLocationMap = UMLPlugin.getEPackageNsURIToProfileLocationMap();

        ePackageNsURIToProfileLocationMap.put("http://www.eclipse.org/Papyrus/2014/profile/profileExternalization", //$NON-NLS-1$
                URI.createURI("pathmap://PAPYRUS_PROFILEEXT/ProfileExternalization.profile.uml#_Mzzc0EWjEeSNXJj2G3jVCw")); //$NON-NLS-1$
        ePackageNsURIToProfileLocationMap.put("http://www.eclipse.org/papyrus/documentation", //$NON-NLS-1$
                URI.createURI("pathmap://PAPYRUS_DOCUMENTATION/Papyrus.profile.uml#_H9068AEYEeCIz8iAxBJnfA")); //$NON-NLS-1$
        ePackageNsURIToProfileLocationMap.put("http://www.eclipse.org/uml2/5.0.0/UML/Profile/Standard", // //$NON-NLS-1$
                URI.createURI("pathmap://UML_PROFILES/Standard.profile.uml#_0")); //$NON-NLS-1$
        ePackageNsURIToProfileLocationMap.put("http://www.eclipse.org/Papyrus/2014/common/filters", //$NON-NLS-1$
                URI.createURI("pathmap://PAPYRUS_FILTERS/filters.uml#_u1APUG86EeSumdlFUM6GVw")); //$NON-NLS-1$
        ePackageNsURIToProfileLocationMap.put("http://www.eclipse.org/Papyrus/2014/diagram/assistant", //$NON-NLS-1$
                URI.createURI("pathmap://PAPYRUS_MODELING_ASSISTANTS/assistant.merged.uml#_0")); //$NON-NLS-1$
        ePackageNsURIToProfileLocationMap.put("http://www.eclipse.org/ocl/2015/OCLforUML", //$NON-NLS-1$
                URI.createURI("pathmap://OCL_PROFILES/OCLforUML.profile.uml#_0")); //$NON-NLS-1$
        ePackageNsURIToProfileLocationMap.put("http://www.eclipse.org/uml2/schemas/Ecore/5", //$NON-NLS-1$
                URI.createURI("pathmap://UML_PROFILES/Ecore.profile.uml#_0")); //$NON-NLS-1$
    }
}
