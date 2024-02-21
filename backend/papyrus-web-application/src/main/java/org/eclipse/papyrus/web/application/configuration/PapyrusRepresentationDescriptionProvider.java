/*****************************************************************************
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
 *****************************************************************************/
package org.eclipse.papyrus.web.application.configuration;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
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
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.emf.IViewConverter;
import org.eclipse.sirius.emfjson.resource.JsonResourceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * Registers a diagram definition from a statically loaded View model.
 *
 * @author Arthur Daussy
 */
@Configuration
public class PapyrusRepresentationDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(PapyrusRepresentationDescriptionProvider.class);

    private final IViewConverter viewConverter;

    private final EPackage.Registry ePackagesRegistry;

    private PapyrusRepresentationDescriptionRegistry viewRegistry;

    public PapyrusRepresentationDescriptionProvider(EPackage.Registry ePackagesRegistry, IViewConverter viewConverter, PapyrusRepresentationDescriptionRegistry viewRegistry) {
        this.viewRegistry = viewRegistry;
        this.viewConverter = Objects.requireNonNull(viewConverter);
        this.ePackagesRegistry = Objects.requireNonNull(ePackagesRegistry);
    }

    @PostConstruct
    public void buildPapyrusDescription() {
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.eAdapters().add(new ECrossReferenceAdapter());

        List<EPackage> staticEPackages = this.ePackagesRegistry.values().stream()
                .filter(EPackage.class::isInstance)
                .map(EPackage.class::cast)
                .collect(Collectors.toList());

        this.register(resourceSet, staticEPackages, new CSDDiagramDescriptionBuilder().createDiagramDescription(this.createView(resourceSet, CSDDiagramDescriptionBuilder.CSD_REP_NAME)));
        this.register(resourceSet, staticEPackages, new PADDiagramDescriptionBuilder().createDiagramDescription(this.createView(resourceSet, PADDiagramDescriptionBuilder.PD_REP_NAME)));
        this.register(resourceSet, staticEPackages, new SMDDiagramDescriptionBuilder().createDiagramDescription(this.createView(resourceSet, SMDDiagramDescriptionBuilder.SMD_REP_NAME)));
        this.register(resourceSet, staticEPackages, new CDDiagramDescriptionBuilder().createDiagramDescription(this.createView(resourceSet, CDDiagramDescriptionBuilder.CD_REP_NAME)));
        this.register(resourceSet, staticEPackages, new UCDDiagramDescriptionBuilder().createDiagramDescription(this.createView(resourceSet, UCDDiagramDescriptionBuilder.UCD_REP_NAME)));
        this.register(resourceSet, staticEPackages, new PRDDiagramDescriptionBuilder().createDiagramDescription(this.createView(resourceSet, PRDDiagramDescriptionBuilder.PRD_REP_NAME)));
        this.register(resourceSet, staticEPackages, new CODDiagramDescriptionBuilder().createDiagramDescription(this.createView(resourceSet, CODDiagramDescriptionBuilder.COD_REP_NAME)));
        this.register(resourceSet, staticEPackages, new ADDiagramDescriptionBuilder().createDiagramDescription(this.createView(resourceSet, ADDiagramDescriptionBuilder.AD_REP_NAME)));
        this.register(resourceSet, staticEPackages, new CPDDiagramDescriptionBuilder().createDiagramDescription(this.createView(resourceSet, CPDDiagramDescriptionBuilder.CPD_REP_NAME)));
        this.register(resourceSet, staticEPackages, new DDDiagramDescriptionBuilder().createDiagramDescription(this.createView(resourceSet, DDDiagramDescriptionBuilder.DD_REP_NAME)));
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {
        return this.viewRegistry.getApiDiagrams();
    }

    private View createView(ResourceSet resourceSet, String representatioName) {
        // Required to have a unique URIs - workaround https://github.com/eclipse-sirius/sirius-components/issues/1345
        View view = ViewFactory.eINSTANCE.createView();
        JsonResourceImpl impl = new JsonResourceImpl(URI.createURI("papyrus-rep:///papyrus-web-" + URI.encodeOpaquePart(representatioName, false)), this.ePackagesRegistry);
        resourceSet.getResources().add(impl);
        impl.getContents().add(view);

        return view;
    }

    private void register(ResourceSet resourceSet, List<EPackage> staticEPackages, DiagramDescription diagramDescription) {

        View view = (View) diagramDescription.eContainer();
        List<IRepresentationDescription> representationDescriptions = this.viewConverter.convert(Collections.singletonList(view), staticEPackages);

        // Workaround https://github.com/eclipse-sirius/sirius-components/issues/1345
        for (var description : representationDescriptions) {
            if (description instanceof org.eclipse.sirius.components.diagrams.description.DiagramDescription) {
                this.viewRegistry.add(diagramDescription, (org.eclipse.sirius.components.diagrams.description.DiagramDescription) description);
                LOGGER.info(MessageFormat.format("Contributing representation {0} with id{1}", description.getLabel(), description.getId()));
            }
        }
    }

}
