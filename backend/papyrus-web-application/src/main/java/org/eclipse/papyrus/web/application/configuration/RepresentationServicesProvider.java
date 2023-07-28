/*****************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo.
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.papyrus.uml.domain.services.internal.helpers.UMLService;
import org.eclipse.papyrus.web.application.representations.uml.CDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.CSDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.PADDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.SMDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.UCDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.services.aqlservices.DebugService;
import org.eclipse.papyrus.web.services.aqlservices.clazz.ClassDiagramService;
import org.eclipse.papyrus.web.services.aqlservices.composite.CompositeStructureDiagramService;
import org.eclipse.papyrus.web.services.aqlservices.statemachine.StateMachineDiagramService;
import org.eclipse.papyrus.web.services.aqlservices.useCase.UseCaseDiagramService;
import org.eclipse.papyrus.web.services.aqlservices.utils.GenericDiagramService;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.emf.IJavaServiceProvider;
import org.springframework.context.annotation.Configuration;

/**
 * Registers all the Representation Service classes for Papyrus Web representations.
 *
 * @author Arthur Duassy
 */
@Configuration
public class RepresentationServicesProvider implements IJavaServiceProvider {

    @Override
    public List<Class<?>> getServiceClasses(View view) {
        // @formatter:off
        return view.getDescriptions().stream()
                .flatMap(representationDescription -> this.getRepresentationServicesClass(representationDescription).stream())
                .collect(Collectors.toList());
        // @formatter:on
    }

    private List<Class<?>> getRepresentationServicesClass(RepresentationDescription representationDescription) {
        if (representationDescription instanceof DiagramDescription) {
            String name = representationDescription.getName();
            if (name != null) {
                List<Class<?>> services = new ArrayList<>();

                // Generic services
                services.add(UMLService.class);
                services.add(DebugService.class);

                String repName = representationDescription.getName();
                // Handle both in memory and serialized version
                if (repName != null) {
                    if (repName.startsWith(CSDDiagramDescriptionBuilder.CSD_REP_NAME)) {
                        services.add(CompositeStructureDiagramService.class);
                    } else if (repName.startsWith(SMDDiagramDescriptionBuilder.SMD_REP_NAME)) {
                        services.add(StateMachineDiagramService.class);
                    } else if (repName.startsWith(PADDiagramDescriptionBuilder.PD_REP_NAME)) {
                        services.add(GenericDiagramService.class);
                    } else if (repName.startsWith(CDDiagramDescriptionBuilder.CD_REP_NAME)) {
                        services.add(ClassDiagramService.class);
                    } else if (repName.startsWith(UCDDiagramDescriptionBuilder.UCD_REP_NAME)) {
                        services.add(UseCaseDiagramService.class);
                    }
                }

                return services;
            }
        }
        return List.of();
    }
}
