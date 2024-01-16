/*******************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
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
import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.web.application.properties.AdvancedPropertiesDescriptionProvider;
import org.eclipse.papyrus.web.services.properties.UMLDocumentationService;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IJavaServiceProvider;
import org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.web.services.api.representations.IInMemoryViewRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration in charge of contributing the UML details view.
 *
 * @author Arthur Daussy
 */
@Configuration
public class UMLPropertiesConfigurer implements IPropertiesDescriptionRegistryConfigurer {

    public static final String UML_DETAIL_VIEW_NAME = "UML Detail View";

    private static final UUID NAME_UUID_FROM_BYTES = UUID.nameUUIDFromBytes(UMLPropertiesConfigurer.class.getCanonicalName().getBytes());

    private static final Logger LOGGER = LoggerFactory.getLogger(UMLPropertiesConfigurer.class);

    private final ViewFormDescriptionConverter converter;

    private Registry globalEPackageRegistry;

    private AdvancedPropertiesDescriptionProvider defaultPropertyViewProvider;

    private final List<IJavaServiceProvider> javaServiceProviders;

    private final ApplicationContext applicationContext;

    private final IInMemoryViewRegistry viewRegistry;

    public UMLPropertiesConfigurer(ViewFormDescriptionConverter converter, EPackage.Registry globalEPackageRegistry, AdvancedPropertiesDescriptionProvider defaultPropertyViewProvider,
            UMLDocumentationService docService, ApplicationContext applicationContext, List<IJavaServiceProvider> javaServiceProviders, IInMemoryViewRegistry viewRegistry) {
        this.defaultPropertyViewProvider = Objects.requireNonNull(defaultPropertyViewProvider);
        this.globalEPackageRegistry = Objects.requireNonNull(globalEPackageRegistry);
        this.converter = Objects.requireNonNull(converter);
        this.javaServiceProviders = javaServiceProviders;
        this.applicationContext = applicationContext;
        this.viewRegistry = Objects.requireNonNull(viewRegistry);
    }

    @Override
    public void addPropertiesDescriptions(IPropertiesDescriptionRegistry registry) {
        // Build the actual FormDescription

        // The FormDescription must be part of View inside a proper EMF Resource to be correctly handled
        URI uri = URI.createURI(IEMFEditingContext.RESOURCE_SCHEME + ":///" + NAME_UUID_FROM_BYTES);
        Resource resource = new JSONResourceFactory().createResource(uri);
        View view = new UMLDetailViewFromBuilder(UML_DETAIL_VIEW_NAME).build();
        resource.getContents().add(view);

        List<EPackage> allEPackages = this.findGlobalEPackages();
        AQLInterpreter interpreter = this.createInterpreter(view, allEPackages);

        // Convert the View-based FormDescription and register the result into the system
        view.getDescriptions().stream()//
                .filter(d -> d instanceof FormDescription)//
                .map(d -> (FormDescription) d)//
                .forEach(d -> this.register(d, interpreter, registry));

        // Register the "Advance Property View"
        this.defaultPropertyViewProvider.getFormDescription().getPageDescriptions().forEach(registry::add);

        this.viewRegistry.register(view);
    }

    private AQLInterpreter createInterpreter(View view, List<EPackage> visibleEPackages) {
        AutowireCapableBeanFactory beanFactory = this.applicationContext.getAutowireCapableBeanFactory();
        List<Object> serviceInstances = this.javaServiceProviders.stream()
                .flatMap(provider -> provider.getServiceClasses(view).stream())
                .map(serviceClass -> {
                    try {
                        return beanFactory.createBean(serviceClass);
                    } catch (BeansException beansException) {
                        LOGGER.warn("Error while trying to instantiate Java service class " + serviceClass.getName(), beansException);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .map(Object.class::cast)
                .toList();
        return new AQLInterpreter(List.of(), serviceInstances, visibleEPackages);
    }

    private List<EPackage> findGlobalEPackages() {
        return this.globalEPackageRegistry.values().stream().filter(EPackage.class::isInstance).map(EPackage.class::cast).toList();
    }

    private void register(FormDescription viewFormDescription, AQLInterpreter interpreter, IPropertiesDescriptionRegistry registry) {

        IRepresentationDescription converted = this.converter.convert(viewFormDescription, List.of(), interpreter);
        if (converted instanceof org.eclipse.sirius.components.forms.description.FormDescription formDescription) {
            formDescription.getPageDescriptions().forEach(registry::add);
        }
    }

}
