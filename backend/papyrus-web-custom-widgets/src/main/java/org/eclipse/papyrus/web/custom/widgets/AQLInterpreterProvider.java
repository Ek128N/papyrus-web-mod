/*****************************************************************************
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
 *****************************************************************************/
package org.eclipse.papyrus.web.custom.widgets;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IJavaServiceProvider;
import org.eclipse.sirius.web.services.editingcontext.EditingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Service to retrieve a fresh AQL interpreter.
 *
 * @author Jerome Gout
 */
@Service
public class AQLInterpreterProvider implements IAQLInterpreterProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(AQLInterpreterProvider.class);

    private final List<IJavaServiceProvider> javaServiceProviders;

    private final ApplicationContext applicationContext;

    public AQLInterpreterProvider(List<IJavaServiceProvider> javaServiceProviders, ApplicationContext applicationContext) {
        this.javaServiceProviders = Objects.requireNonNull(javaServiceProviders);
        this.applicationContext = Objects.requireNonNull(applicationContext);
    }

    private List<EPackage> getAccessibleEPackages(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext) {
            Registry packageRegistry = ((EditingContext) editingContext).getDomain().getResourceSet().getPackageRegistry();
            return packageRegistry.values().stream().filter(EPackage.class::isInstance).map(EPackage.class::cast).toList();
        } else {
            return List.of();
        }
    }

    @Override
    public AQLInterpreter createInterpreter(View view, IEditingContext editingContext) {
        List<EPackage> visibleEPackages = this.getAccessibleEPackages(editingContext);
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
}
