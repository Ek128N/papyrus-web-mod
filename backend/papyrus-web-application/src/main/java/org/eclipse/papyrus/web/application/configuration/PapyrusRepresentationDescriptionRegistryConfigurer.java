/*******************************************************************************
 * Copyright (c) 2022 CEA, Obeo
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.application.configuration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.papyrus.web.application.representations.uml.CDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.CSDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.PADDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.SMDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.services.representations.PapyrusRepresentationDescriptionRegistry;
import org.eclipse.sirius.components.core.configuration.IRepresentationDescriptionRegistry;
import org.eclipse.sirius.components.core.configuration.IRepresentationDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.emf.IViewConverter;
import org.eclipse.sirius.emfjson.resource.JsonResourceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

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

    @Override
    public void addRepresentationDescriptions(IRepresentationDescriptionRegistry registry) {
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.eAdapters().add(new ECrossReferenceAdapter());

        // @formatter:off
        List<EPackage> staticEPackages = this.ePackagesRegistry.values().stream()
                .filter(EPackage.class::isInstance)
                .map(EPackage.class::cast)
                .collect(Collectors.toList());
        // @formatter:on

        this.register(resourceSet, staticEPackages, new CSDDiagramDescriptionBuilder().createDiagramDescription(), registry);
        this.register(resourceSet, staticEPackages, new PADDiagramDescriptionBuilder().createDiagramDescription(), registry);
        this.register(resourceSet, staticEPackages, new SMDDiagramDescriptionBuilder().createDiagramDescription(), registry);
        this.register(resourceSet, staticEPackages, new CDDiagramDescriptionBuilder().createDiagramDescription(), registry);
    }

    private void register(ResourceSet resourceSet, List<EPackage> staticEPackages, DiagramDescription diagramDescription, IRepresentationDescriptionRegistry registry) {

        // Required to have a unique URIs - workaround https://github.com/eclipse-sirius/sirius-components/issues/1345
        View view = ViewFactory.eINSTANCE.createView();
        JsonResourceImpl impl = new JsonResourceImpl(URI.createURI("papyrus-rep:///papyrus-web-" + diagramDescription.getName()), this.ePackagesRegistry); //$NON-NLS-1$
        resourceSet.getResources().add(impl);
        impl.getContents().add(view);

        view.getDescriptions().add(diagramDescription);

        var representationDescriptions = this.viewConverter.convert(view, staticEPackages);

        // Workaround https://github.com/eclipse-sirius/sirius-components/issues/1345
        for (var description : representationDescriptions) {
            this.viewRegistry.add(diagramDescription, description);
            LOGGER.info(MessageFormat.format("Contributing representation {0} with id{1}", description.getLabel(), description.getId())); //$NON-NLS-1$
        }
        representationDescriptions.forEach(registry::add);

        if (this.saveViewModel) {
            this.printAndSaveViewModel(view);
        }

    }

    private void printAndSaveViewModel(View view) {
        XMIResourceImpl xmiResourceImpl = new XMIResourceImpl();
        View viewCopy = EcoreUtil.copy(view);
        viewCopy.getDescriptions().forEach(d -> d.setName(d.getName() + " serialized")); //$NON-NLS-1$
        xmiResourceImpl.getContents().add(viewCopy);

        try (var fileOutputStream = new ByteArrayOutputStream()) {
            xmiResourceImpl.save(fileOutputStream, Collections.emptyMap());

            byte[] content = fileOutputStream.toByteArray();
            String contentString = new String(content, "UTF-8"); //$NON-NLS-1$
            LOGGER.info(contentString);

            String userHome = System.getProperty("user.home"); //$NON-NLS-1$
            if (userHome != null) {
                String[] parts = view.eResource().getURI().toString().split("/"); //$NON-NLS-1$
                String fileName = parts[parts.length - 1];
                Path targetFile = Path.of(userHome + "/papyrus-web/" + fileName + ".view"); //$NON-NLS-1$ //$NON-NLS-2$

                if (!targetFile.getParent().toFile().exists()) {
                    targetFile.getParent().toFile().mkdirs();
                }

                Files.write(targetFile, content);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
