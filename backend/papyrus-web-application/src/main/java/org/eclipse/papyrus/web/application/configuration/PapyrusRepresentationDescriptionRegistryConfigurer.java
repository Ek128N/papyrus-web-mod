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
import org.eclipse.papyrus.web.application.representations.uml.CDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.CSDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.PADDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.SMDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.UCDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.utils.ViewSerializer;
import org.eclipse.papyrus.web.services.representations.PapyrusRepresentationDescriptionRegistry;
import org.eclipse.sirius.components.core.configuration.IRepresentationDescriptionRegistry;
import org.eclipse.sirius.components.core.configuration.IRepresentationDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.emf.IViewConverter;
import org.eclipse.sirius.emfjson.resource.JsonResourceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * Registers a diagram definition from a statically loaded View model.
 *
 * @author Arthur Daussy
 */
@Configuration
public class PapyrusRepresentationDescriptionRegistryConfigurer implements IRepresentationDescriptionRegistryConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PapyrusRepresentationDescriptionRegistryConfigurer.class);

    private final IViewConverter viewConverter;

    private final EPackage.Registry ePackagesRegistry;

    /**
     * Holds true to save and print the view model during start.
     */
    private final boolean saveViewModel;

    private PapyrusRepresentationDescriptionRegistry viewRegistry;

    public PapyrusRepresentationDescriptionRegistryConfigurer(EPackage.Registry ePackagesRegistry, IViewConverter viewConverter, PapyrusRepresentationDescriptionRegistry viewRegistry,
            @Value("${org.eclipse.papyrus.web.application.configuration.save.view.model:false}") boolean saveViewModel) {
        this.viewRegistry = viewRegistry;
        this.saveViewModel = saveViewModel;
        this.viewConverter = Objects.requireNonNull(viewConverter);
        this.ePackagesRegistry = Objects.requireNonNull(ePackagesRegistry);
    }

    @PostConstruct
    public void buildPapyrusDescription() {
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.eAdapters().add(new ECrossReferenceAdapter());

        // @formatter:off
        List<EPackage> staticEPackages = this.ePackagesRegistry.values().stream()
                .filter(EPackage.class::isInstance)
                .map(EPackage.class::cast)
                .collect(Collectors.toList());
        // @formatter:on
        this.register(resourceSet, staticEPackages, new CSDDiagramDescriptionBuilder().createDiagramDescription(this.createView(resourceSet, CSDDiagramDescriptionBuilder.CSD_REP_NAME)));
        this.register(resourceSet, staticEPackages, new PADDiagramDescriptionBuilder().createDiagramDescription(this.createView(resourceSet, PADDiagramDescriptionBuilder.PD_REP_NAME)));
        this.register(resourceSet, staticEPackages, new SMDDiagramDescriptionBuilder().createDiagramDescription(this.createView(resourceSet, SMDDiagramDescriptionBuilder.SMD_REP_NAME)));
        this.register(resourceSet, staticEPackages, new CDDiagramDescriptionBuilder().createDiagramDescription(this.createView(resourceSet, CDDiagramDescriptionBuilder.CD_REP_NAME)));
        this.register(resourceSet, staticEPackages, new UCDDiagramDescriptionBuilder().createDiagramDescription(this.createView(resourceSet, UCDDiagramDescriptionBuilder.UCD_REP_NAME)));
    }

    @Override
    public void addRepresentationDescriptions(IRepresentationDescriptionRegistry registry) {
        this.viewRegistry.getApiDiagrams().forEach(registry::add);
    }

    private View createView(ResourceSet resourceSet, String representatioName) {
        // Required to have a unique URIs - workaround https://github.com/eclipse-sirius/sirius-components/issues/1345
        View view = ViewFactory.eINSTANCE.createView();
        JsonResourceImpl impl = new JsonResourceImpl(URI.createURI("papyrus-rep:///papyrus-web-" + URI.encodeOpaquePart(representatioName, false)), this.ePackagesRegistry); //$NON-NLS-1$
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
                LOGGER.info(MessageFormat.format("Contributing representation {0} with id{1}", description.getLabel(), description.getId())); //$NON-NLS-1$
            }
        }

        if (this.saveViewModel) {
            new ViewSerializer().printAndSaveViewModel(view);
        }

    }

}
