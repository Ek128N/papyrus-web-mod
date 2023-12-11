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
package org.eclipse.papyrus.web.graphics;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.papyrus.web.services.api.IImageOverrideService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Configuration for IImageOverrideService.
 *
 * @author Arthur Daussy
 */
@Configuration
public class SvgIconProviderConfiguration {

    private static final Pattern PATH_REGEX = Pattern.compile(".*(/icons-override/full/obj16/.*\\.svg)");

    private static final Pattern OVERLAY_PATH_REGEX = Pattern.compile(".*(/icons-override/full/ovr16/.*\\.svg)");

    private static final Logger LOGGER = LoggerFactory.getLogger(SvgIconProviderConfiguration.class);

    @Bean
    IImageOverrideService svgIconProvider(ResourceLoader resourceLoader) {
        HashMap<String, String> iconMap = new HashMap<>();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(resourceLoader);
        try {
            this.computeIconsOverrideMappings(iconMap, resolver, "classpath*:icons-override/full/obj16/*.svg", PATH_REGEX);
            this.computeIconsOverrideMappings(iconMap, resolver, "classpath*:icons-override/full/ovr16/*.svg", OVERLAY_PATH_REGEX);
        } catch (IOException e) {
            LOGGER.error("Error while searching for svg icons", e);
        }
        return new SvgIconOverrideService(iconMap);
    }

    private void computeIconsOverrideMappings(HashMap<String, String> iconMap, PathMatchingResourcePatternResolver resolver, String objt16icons, Pattern pattern) throws IOException {
        Resource[] images = resolver.getResources(objt16icons);
        for (Resource r : images) {

            Matcher matcher = pattern.matcher(r.getURL().toString());
            if (matcher.matches()) {
                String svgPath = matcher.group(1);
                String key = svgPath.replace("/icons-override", "/icons").replace(".svg", "");
                iconMap.put(key, svgPath);
            }
        }
    }

}
