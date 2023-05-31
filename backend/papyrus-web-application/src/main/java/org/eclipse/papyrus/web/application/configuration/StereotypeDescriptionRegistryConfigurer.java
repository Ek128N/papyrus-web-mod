/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo and CEA.
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

import java.util.UUID;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.web.sirius.contributions.StereotypeBuilder;
import org.eclipse.sirius.components.core.configuration.IStereotypeDescriptionRegistry;
import org.eclipse.sirius.components.core.configuration.IStereotypeDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.core.configuration.StereotypeDescription;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.uml2.uml.UMLFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Configuration used to register new stereotype descriptions.
 *
 * @author sbegaudeau
 */
@Configuration
public class StereotypeDescriptionRegistryConfigurer implements IStereotypeDescriptionRegistryConfigurer {

    public static final UUID EMPTY_ID = UUID.nameUUIDFromBytes("empty".getBytes()); //$NON-NLS-1$

    public static final String EMPTY_LABEL = "Others..."; //$NON-NLS-1$

    public static final UUID EMPTY_UML_ID = UUID.nameUUIDFromBytes("empty_uml".getBytes()); //$NON-NLS-1$

    public static final String EMPTY_UML_LABEL = "UML.uml"; //$NON-NLS-1$

    public static final UUID SAMPLE_UML_ID_SELF_CONTAINED = UUID.nameUUIDFromBytes("sample_uml_self_contained".getBytes()); //$NON-NLS-1$

    public static final String SAMPLE_UML_LABEL_SELF_CONTAINED = "UML sample self contained.uml"; //$NON-NLS-1$

    private static final UUID EMPTY_VIEW_ID = UUID.nameUUIDFromBytes("empty_view".getBytes()); //$NON-NLS-1$

    private static final String EMPTY_VIEW_LABEL = "View"; //$NON-NLS-1$

    private static final String TIMER_NAME = "papyrusweb_stereotype_load"; //$NON-NLS-1$

    private final StereotypeBuilder stereotypeBuilder;

    private final boolean studiosEnabled;

    public StereotypeDescriptionRegistryConfigurer(MeterRegistry meterRegistry, @Value("${org.eclipse.papyrus.web.features.studioDefinition:false}") boolean studiosEnabled) {
        this.stereotypeBuilder = new StereotypeBuilder(TIMER_NAME, meterRegistry);
        this.studiosEnabled = studiosEnabled;
    }

    @Override
    public void addStereotypeDescriptions(IStereotypeDescriptionRegistry registry) {
        registry.add(new StereotypeDescription(EMPTY_UML_ID, EMPTY_UML_LABEL, this::getEmptyUMLContent));
        registry.add(new StereotypeDescription(SAMPLE_UML_ID_SELF_CONTAINED, SAMPLE_UML_LABEL_SELF_CONTAINED, this::getUMLSelfContainedSample));
        if (this.studiosEnabled) {
            registry.add(new StereotypeDescription(EMPTY_VIEW_ID, EMPTY_VIEW_LABEL, this::getEmptyViewContent));
        }
        registry.add(new StereotypeDescription(EMPTY_ID, EMPTY_LABEL, "New", this::getEmptyContent)); //$NON-NLS-1$
    }

    private String getEmptyViewContent() {
        View newView = ViewFactory.eINSTANCE.createView();
        DiagramDescription diagramDescription = ViewFactory.eINSTANCE.createDiagramDescription();
        diagramDescription.setName("New Diagram Description"); //$NON-NLS-1$
        newView.getDescriptions().add(diagramDescription);
        return this.stereotypeBuilder.getStereotypeBody(newView);
    }

    private String getEmptyContent() {
        return this.stereotypeBuilder.getStereotypeBody((EObject) null);
    }

    private String getEmptyUMLContent() {
        return this.stereotypeBuilder.getStereotypeBody(UMLFactory.eINSTANCE.createModel());
    }

    private String getUMLSelfContainedSample() {
        return this.stereotypeBuilder.getStereotypeBody(new ClassPathResource("SampleModel_WithoutExternalLink.uml")); //$NON-NLS-1$
    }
}
