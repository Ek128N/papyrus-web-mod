/*******************************************************************************
 * Copyright (c) 2023 CEA, Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.eclipse.papyrus.uml.domain.services.properties.PropertiesCrudServices;
import org.eclipse.papyrus.uml.domain.services.properties.PropertiesMultiplicityServices;
import org.eclipse.papyrus.uml.domain.services.properties.PropertiesProfileDefinitionServices;
import org.eclipse.papyrus.uml.domain.services.properties.PropertiesUMLServices;
import org.eclipse.papyrus.uml.domain.services.properties.PropertiesValueSpecificationServices;
import org.eclipse.papyrus.web.application.properties.AdvancedPropertiesDescriptionProvider;
import org.eclipse.papyrus.web.application.properties.UMLDetailViewBuilder;
import org.eclipse.papyrus.web.application.utils.ViewSerializer;
import org.eclipse.papyrus.web.services.aqlservices.ServiceLogger;
import org.eclipse.papyrus.web.services.aqlservices.properties.PropertiesImageServicesWrapper;
import org.eclipse.papyrus.web.services.aqlservices.properties.PropertiesMemberEndServicesWrapper;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration in charge of contributing the UML details view.
 *
 * @author Arthur Daussy
 */
@Configuration
public class UMLPropertiesConfigurer implements IPropertiesDescriptionRegistryConfigurer {

    private final ViewFormDescriptionConverter converter;

    private boolean saveViewModel;

    private Registry globalEPackageRegistry;

    private IEditableChecker checker;

    private ServiceLogger serviceLogger;

    private AdvancedPropertiesDescriptionProvider defaultPropertyViewProvider;

    public UMLPropertiesConfigurer(ViewFormDescriptionConverter converter, EPackage.Registry globalEPackageRegistry, AdvancedPropertiesDescriptionProvider defaultPropertyViewProvider,
            @Value("${org.eclipse.papyrus.web.application.configuration.save.view.model:false}") boolean saveViewModel, IEditableChecker checker, ServiceLogger aqlLogger) {
        this.defaultPropertyViewProvider = Objects.requireNonNull(defaultPropertyViewProvider);
        this.serviceLogger = Objects.requireNonNull(aqlLogger);
        this.checker = Objects.requireNonNull(checker);
        this.globalEPackageRegistry = Objects.requireNonNull(globalEPackageRegistry);
        this.saveViewModel = saveViewModel;
        this.converter = Objects.requireNonNull(converter);
    }

    @Override
    public void addPropertiesDescriptions(IPropertiesDescriptionRegistry registry) {
        // Build the actual FormDescription

        // The FormDescription must be part of View inside a proper EMF Resource to be correctly handled
        URI uri = URI.createURI(EditingContext.RESOURCE_SCHEME + ":///" + UUID.nameUUIDFromBytes(UMLPropertiesConfigurer.class.getCanonicalName().getBytes()));
        Resource resource = new XMIResourceImpl(uri);
        View view = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createView();
        resource.getContents().add(view);

        FormDescription form = this.createFormDescription();
        view.getDescriptions().add(form);

        form.getPages().addAll(new UMLDetailViewBuilder().createPages());

        if (this.saveViewModel) {
            new ViewSerializer().printAndSaveViewModel(view);
        }

        List<Object> services = List.of(new PropertiesCrudServices(this.serviceLogger, this.checker), //
                new PropertiesImageServicesWrapper(), //
                new PropertiesMemberEndServicesWrapper(this.serviceLogger, this.checker), //
                new PropertiesMultiplicityServices(this.serviceLogger, this.checker), //
                new PropertiesProfileDefinitionServices(this.serviceLogger), //
                new PropertiesUMLServices(this.serviceLogger), //
                new PropertiesValueSpecificationServices(this.serviceLogger, this.checker));

        List<EPackage> allEPackages = this.findGlobalEPackages();
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), services, allEPackages);

        // Convert the View-based FormDescription and register the result into the system
        view.getDescriptions().stream()//
                .filter(d -> d instanceof FormDescription)//
                .map(d -> (FormDescription) d)//
                .forEach(d -> this.register(d, interpreter, registry));

        // Register the "Advance Property View"
        this.defaultPropertyViewProvider.getFormDescription().getPageDescriptions().forEach(registry::add);
    }

    private FormDescription createFormDescription() {
        FormDescription form = ViewFactory.eINSTANCE.createFormDescription();
        form.setName("UML Detail View");
        // Not really used
        // The form is only here to own PageDescriptions
        form.setDomainType("uml::Element");
        return form;

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
