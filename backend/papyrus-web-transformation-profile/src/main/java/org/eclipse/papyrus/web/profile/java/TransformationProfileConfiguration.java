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
package org.eclipse.papyrus.web.profile.java;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.papyrus.designer.transformation.profile.Transformation.TransformationPackage;
import org.eclipse.uml2.uml.UMLPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * Configuration of the Java profile.
 *
 * @author Arthur Daussy
 */
@Configuration
public class TransformationProfileConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransformationProfileConfiguration.class);

    @PostConstruct
    public void init() {
        LOGGER.info("Initializing EPackage Registry for Transformation profile");
        Map<String, URI> ePackageNsURIToProfileLocationMap = UMLPlugin.getEPackageNsURIToProfileLocationMap();
        ePackageNsURIToProfileLocationMap.put("http://www.eclipse.org/papyrus/Transformation/1",
                URI.createURI("pathmap://TRAFO_PROFILE/Transformation.profile.uml#_fPDsIBa-EearhdjjJ6cVzQ"));
    }

    @Bean
    public EPackage transformationProfile() {
        return TransformationPackage.eINSTANCE;
    }

}
