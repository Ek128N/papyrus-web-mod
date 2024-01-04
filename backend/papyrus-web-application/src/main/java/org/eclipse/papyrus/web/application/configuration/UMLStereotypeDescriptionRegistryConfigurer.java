/*****************************************************************************
 * Copyright (c) 2019, 2023 CEA LIST, Obeo.
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

import java.util.Collections;
import java.util.UUID;

import org.eclipse.papyrus.web.sirius.contributions.StereotypeBuilder;
import org.eclipse.sirius.components.core.configuration.IStereotypeDescriptionRegistry;
import org.eclipse.sirius.components.core.configuration.IStereotypeDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.core.configuration.StereotypeDescription;
import org.eclipse.uml2.uml.UMLFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Configuration used to register new stereotype descriptions.
 *
 * @author sbegaudeau
 */
@Configuration
public class UMLStereotypeDescriptionRegistryConfigurer implements IStereotypeDescriptionRegistryConfigurer {

    public static final UUID EMPTY_ID = UUID.nameUUIDFromBytes("empty".getBytes());

    public static final String EMPTY_LABEL = "Others...";

    public static final UUID EMPTY_UML_ID = UUID.nameUUIDFromBytes("empty_uml".getBytes());

    public static final String EMPTY_UML_LABEL = "UML.uml";

    public static final UUID EMPTY_PROFILE_ID = UUID.nameUUIDFromBytes("empty_profile".getBytes());

    public static final String EMPTY_PROFILE_LABEL = "Profile.profile.uml";

    public static final UUID SAMPLE_UML_ID_SELF_CONTAINED = UUID.nameUUIDFromBytes("sample_uml_self_contained".getBytes());

    public static final String SAMPLE_UML_LABEL_SELF_CONTAINED = "UML sample self contained.uml";

    private static final String SAMPLE_UML_WITHOUT_EXTERNAL_LINK = "SampleModel_WithoutExternalLink.uml";

    private static final String TIMER_NAME = "papyrusweb_stereotype_load";

    private final StereotypeBuilder stereotypeBuilder;

    public UMLStereotypeDescriptionRegistryConfigurer(MeterRegistry meterRegistry) {
        this.stereotypeBuilder = new StereotypeBuilder(TIMER_NAME, meterRegistry);
    }

    @Override
    public void addStereotypeDescriptions(IStereotypeDescriptionRegistry registry) {
        registry.add(new StereotypeDescription(EMPTY_UML_ID, EMPTY_UML_LABEL, this::getEmptyUMLContent));
        registry.add(new StereotypeDescription(SAMPLE_UML_ID_SELF_CONTAINED, SAMPLE_UML_LABEL_SELF_CONTAINED, this::getSampleUMLSelfContained));
        registry.add(new StereotypeDescription(EMPTY_PROFILE_ID, EMPTY_PROFILE_LABEL, this::getEmptyProfileContent));
        registry.add(new StereotypeDescription(EMPTY_ID, EMPTY_LABEL, "New", this::getEmptyContent));
    }

    private String getEmptyContent() {
        return this.stereotypeBuilder.getStereotypeBody(Collections.emptyList());
    }

    private String getEmptyUMLContent() {
        return this.stereotypeBuilder.getStereotypeBody(Collections.singletonList(UMLFactory.eINSTANCE.createModel()));
    }

    private String getEmptyProfileContent() {
        return this.stereotypeBuilder.getStereotypeBody(Collections.singletonList(UMLFactory.eINSTANCE.createProfile()));
    }

    private String getSampleUMLSelfContained() {
        return this.stereotypeBuilder.getStereotypeBody(new ClassPathResource(SAMPLE_UML_WITHOUT_EXTERNAL_LINK));
    }
}
