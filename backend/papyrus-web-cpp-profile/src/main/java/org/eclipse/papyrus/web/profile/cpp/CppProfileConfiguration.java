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
package org.eclipse.papyrus.web.profile.cpp;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.papyrus.designer.languages.cpp.profile.C_Cpp.C_CppPackage;
import org.eclipse.uml2.uml.UMLPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * Configuration of the Cpp profile.
 *
 * @author Arthur Daussy
 */
@Configuration
public class CppProfileConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(CppProfileConfiguration.class);

    @PostConstruct
    public void init() {
        LOGGER.info("Initializing EPackage Registry for CPP profile");
        Map<String, URI> ePackageNsURIToProfileLocationMap = UMLPlugin.getEPackageNsURIToProfileLocationMap();
        ePackageNsURIToProfileLocationMap.put("http://www.eclipse.org/papyrus/C_Cpp/1",
                URI.createURI("pathmap://PapyrusC_Cpp_PROFILES/C_Cpp.profile.uml#_j9REUByGEduN1bTiWJ0lyw"));
    }

    @Bean
    public EPackage cppProfile() {
        return C_CppPackage.eINSTANCE;
    }

}
