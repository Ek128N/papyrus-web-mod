/*******************************************************************************
 * Copyright (c) 2019, 2024 CEA LIST, Obeo.
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

import java.util.List;

import org.eclipse.sirius.components.compatibility.services.api.ISiriusConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of the Sirius compatibility layer.
 *
 * @author sbegaudeau
 */
@Configuration
public class SiriusConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "org.eclipse.sirius.web.features", name = "studioDefinition")
    public ISiriusConfiguration domainModelerDefinition() {
        return () -> List.of("description/domain.odesign");
    }
}
