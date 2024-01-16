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
package org.eclipse.papyrus.web.services.editingcontext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.web.services.representations.IRepresentationDescriptionOverrider;
import org.eclipse.papyrus.web.sirius.contributions.ServiceOverride;
import org.eclipse.papyrus.web.sirius.contributions.UnloadingEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.configuration.IRepresentationDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IViewConverter;
import org.eclipse.sirius.components.view.util.services.ColorPaletteService;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.services.api.id.IDParser;
import org.eclipse.sirius.web.services.editingcontext.api.IEditingDomainFactoryService;
import org.eclipse.sirius.web.services.editingcontext.api.IViewLoader;
import org.eclipse.sirius.web.services.representations.RepresentationDescriptionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

/**
 * Service used to find and retrieve editing contexts.
 *
 * @author sbegaudeau
 */
@ServiceOverride(org.eclipse.sirius.web.services.editingcontext.EditingContextSearchService.class)
public class EditingContextSearchServiceCustomImpl implements IEditingContextSearchService {

    private static final String TIMER_NAME = "siriusweb_editingcontext_load";

    private final Logger logger = LoggerFactory.getLogger(EditingContextSearchServiceCustomImpl.class);

    private final IProjectRepository projectRepository;

    private final IDocumentRepository documentRepository;

    private final IEditingDomainFactoryService editingDomainFactoryService;

    private final List<IRepresentationDescriptionRegistryConfigurer> configurers;

    private final IViewLoader viewLoader;

    private final IViewConverter viewConverter;

    private final Timer timer;

    private List<IRepresentationDescriptionOverrider> descriptionOverriders;

    // CHECKSTYLE:OFF
    public EditingContextSearchServiceCustomImpl(IProjectRepository projectRepository, IDocumentRepository documentRepository, IEditingDomainFactoryService editingDomainFactoryService,
            List<IRepresentationDescriptionRegistryConfigurer> configurers, IViewLoader viewLoader, IViewConverter viewConverter, List<IRepresentationDescriptionOverrider> descriptionOverriders,
            MeterRegistry meterRegistry) {
        // CHECKSTYLE:ON
        this.descriptionOverriders = descriptionOverriders;
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.documentRepository = Objects.requireNonNull(documentRepository);
        this.editingDomainFactoryService = Objects.requireNonNull(editingDomainFactoryService);
        this.configurers = Objects.requireNonNull(configurers);
        this.viewLoader = Objects.requireNonNull(viewLoader);
        this.viewConverter = Objects.requireNonNull(viewConverter);

        this.timer = Timer.builder(TIMER_NAME).register(meterRegistry);
    }

    @Override
    public boolean existsById(String editingContextId) {
        return new IDParser().parse(editingContextId).map(this.projectRepository::existsById).orElse(false);
    }

    @Override
    public Optional<IEditingContext> findById(String editingContextId) {
        long start = System.currentTimeMillis();

        this.logger.debug("Loading the editing context {}", editingContextId);

        AdapterFactoryEditingDomain editingDomain = this.editingDomainFactoryService.createEditingDomain(editingContextId);

        // Workaround for bug: https://github.com/eclipse-sirius/sirius-web/issues/1863
        editingDomain.getResourceSet().eAdapters().add(new SelfPreResolvingProxyAdapter());
        ResourceSet resourceSet = editingDomain.getResourceSet();

        List<DocumentEntity> documentEntities = new IDParser().parse(editingContextId) //
                .map(this.documentRepository::findAllByProjectId) //
                .orElseGet(List::of) //
                .stream() //
                .filter(doc -> !ColorPaletteService.SIRIUS_STUDIO_COLOR_PALETTES_URI.equals(doc.getId().toString())) //
                .toList();
        for (DocumentEntity documentEntity : documentEntities) {
            Resource resource = new JSONResourceFactory().createResourceFromPath(documentEntity.getId().toString());
            try (var inputStream = new ByteArrayInputStream(documentEntity.getContent().getBytes())) {
                resourceSet.getResources().add(resource);

                resource.load(inputStream, resourceSet.getLoadOptions());

                resource.eAdapters().add(new ResourceMetadataAdapter(documentEntity.getName()));
            } catch (IOException | IllegalArgumentException exception) {
                this.logger.warn("An error occured while loading document {}: {}.", documentEntity.getId(), exception.getMessage());
                resourceSet.getResources().remove(resource);
            }
        }

        // DO NOT add the default EditingContextCrossReferenceAdapter for UML resource and UML Element.
        // Everything should be done within the UML Domain Service using the CacheAdapter
        // The ECrossReferenceAdapter must be set after the resource loading because it needs to resolve proxies in case
        // of inter-resources references
        resourceSet.eAdapters().add(new NonUMLEditingContextCrossReferenceAdapter());

        this.logger.debug("{} documents loaded for the editing context {}", resourceSet.getResources().size(), editingContextId);

        var views = this.viewLoader.load();
        Map<String, IRepresentationDescription> representationDescriptions = this.getRepresentationDescriptions(editingDomain, views);

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);

        return Optional.of(new UnloadingEditingContext(editingContextId, editingDomain, representationDescriptions, views));
    }

    private Map<String, IRepresentationDescription> getRepresentationDescriptions(EditingDomain editingDomain, List<View> views) {

        Map<String, IRepresentationDescription> representationDescriptions = new LinkedHashMap<>();
        var registry = new RepresentationDescriptionRegistry();
        this.configurers.forEach(configurer -> configurer.addRepresentationDescriptions(registry));

        /**
         * <p>
         * This part of the code has been created for bug
         * https://gitlab.eclipse.org/eclipse/papyrus/org.eclipse.papyrus-web/-/issues/97. But once
         * https://github.com/eclipse-sirius/sirius-web/issues/2809 is fixed this is no longer needed
         * </p>
         */
        for (var descriptionOverrider : this.descriptionOverriders) {
            for (var ovDescription : descriptionOverrider.getOverridedDescriptions()) {
                registry.getRepresentationDescription(ovDescription.getId()).ifPresentOrElse(__ -> registry.add(ovDescription), () -> {
                    this.logger.error("There is no exisitng representation with id " + ovDescription.getId());
                });
            }
        }

        registry.getRepresentationDescriptions().forEach(representationDescription -> representationDescriptions.put(representationDescription.getId(), representationDescription));
        List<EPackage> accessibleEPackages = this.getAccessibleEPackages(editingDomain);
        this.viewConverter.convert(views, accessibleEPackages).stream().filter(Objects::nonNull)
                .forEach(representationDescription -> representationDescriptions.put(representationDescription.getId(), representationDescription));

        return representationDescriptions;
    }

    private List<EPackage> getAccessibleEPackages(EditingDomain editingDomain) {
        var packageRegistry = editingDomain.getResourceSet().getPackageRegistry();

        return packageRegistry.values().stream().filter(EPackage.class::isInstance).map(EPackage.class::cast).toList();
    }

    /**
     * Workaround for bug: https://github.com/eclipse-sirius/sirius-web/issues/1863 .
     *
     * @author Arthur Daussy
     */
    private final class SelfPreResolvingProxyAdapter extends EContentAdapter {
        @Override
        public void notifyChanged(Notification notification) {
            super.notifyChanged(notification);
            if (notification.getNotifier() instanceof Resource resource //
                    && notification.getFeatureID(Resource.class) == Resource.RESOURCE__IS_LOADED//
                    && notification.getEventType() == Notification.SET //
                    && notification.getNewBooleanValue() /* Only during loading */) {
                EcoreUtil.resolveAll(resource);
            }
        }

        @Override
        protected void selfAdapt(Notification notification) {

            // Only adapt on ResourceSet and Resources
            Object notifier = notification.getNotifier();
            if (notifier instanceof ResourceSet) {
                if (notification.getFeatureID(ResourceSet.class) == ResourceSet.RESOURCE_SET__RESOURCES) {
                    this.handleContainment(notification);
                }
            }

        }
    }

}
