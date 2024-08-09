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
package org.eclipse.papyrus.web.application.configuration;

import org.eclipse.papyrus.web.application.representations.uml.ADDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.CDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.CODDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.CPDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.CSDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.DDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.PADDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.PRDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.SMDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.UCDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.services.representations.PapyrusRepresentationDescriptionRegistry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

/**
 * Set up Papyrus representations in the editing context.
 *
 * @author Arthur Daussy
 */
@Service
public class PapyrusRepresentationInstaller implements IEditingContextProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PapyrusRepresentationInstaller.class);

    private final PapyrusRepresentationDescriptionRegistry papyrusRepresentationRegistry;

    public PapyrusRepresentationInstaller(PapyrusRepresentationDescriptionRegistry papyrusRepresentationRegistry) {
        super();
        this.papyrusRepresentationRegistry = papyrusRepresentationRegistry;
    }

    @PostConstruct
    private void fillPapyrusRegistry() {
        // Create the view models only once
        this.papyrusRepresentationRegistry.registerDiagram(new CSDDiagramDescriptionBuilder());
        this.papyrusRepresentationRegistry.registerDiagram(new PADDiagramDescriptionBuilder());
        this.papyrusRepresentationRegistry.registerDiagram(new SMDDiagramDescriptionBuilder());
        this.papyrusRepresentationRegistry.registerDiagram(new CDDiagramDescriptionBuilder());
        this.papyrusRepresentationRegistry.registerDiagram(new UCDDiagramDescriptionBuilder());
        this.papyrusRepresentationRegistry.registerDiagram(new PRDDiagramDescriptionBuilder());
        this.papyrusRepresentationRegistry.registerDiagram(new CODDiagramDescriptionBuilder());
        this.papyrusRepresentationRegistry.registerDiagram(new ADDiagramDescriptionBuilder());
        this.papyrusRepresentationRegistry.registerDiagram(new CPDDiagramDescriptionBuilder());
        this.papyrusRepresentationRegistry.registerDiagram(new DDDiagramDescriptionBuilder());
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            // Add all view models in the editing context
            this.papyrusRepresentationRegistry.getViewDiagrams().stream()
                    .map(e -> (View) e.eContainer())
                    .forEach(v -> {
                        siriusWebEditingContext.getViews().add(v);
                        LOGGER.info("{} description added to editing context {}", v.getDescriptions().get(0).getName(), editingContext.getId());
                    });
            this.papyrusRepresentationRegistry.getForms().stream()
                    .map(e -> (View) e.eContainer())
                    .forEach(v -> {
                        siriusWebEditingContext.getViews().add(v);
                        LOGGER.info("{} form description added to editing context {}", v.getDescriptions().get(0).getName(), editingContext.getId());
                    });
        }
    }

    @Override
    public void postProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            // Add all representation in the editing context
            this.papyrusRepresentationRegistry.getApiDiagrams().stream()
                    .forEach(diag -> siriusWebEditingContext.getRepresentationDescriptions().put(diag.getId(), diag));
        }

    }

}
