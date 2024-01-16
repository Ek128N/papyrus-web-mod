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
 *******************************************************************************/
package org.eclipse.papyrus.web.application.utils;

import java.util.UUID;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.papyrus.web.application.configuration.JPAConfiguration;
import org.eclipse.papyrus.web.tests.utils.UMLTestHelper;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Abstract test class for testing web UML features.
 *
 * @author Arthur Daussy
 */
@SpringJUnitConfig(classes = { IntegrationTestConfiguration.class, JPAConfiguration.class })
public class AbstractWebUMLTest {

    // Emulate a POSTGRESQL database.
    public static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER;

    protected static final UMLPackage UML = UMLPackage.eINSTANCE;

    protected AdapterFactoryEditingDomain editingDomain;

    private UMLTestHelper umlHelper = new UMLTestHelper();

    @Autowired
    private IObjectService objectService;

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    private IEditingContext editingContext;

    static {
        POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:latest").withReuse(true);
        POSTGRESQL_CONTAINER.start();
    }

    @BeforeEach
    public void before() {
        UUID editingContextId = UUID.randomUUID();
        this.editingContext = this.editingContextSearchService.findById(editingContextId.toString()).get();
        this.editingDomain = ((IEMFEditingContext) this.editingContext).getDomain();

        this.registerClasspathURIHandler();

    }

    // Plug emulated database in application
    @DynamicPropertySource
    public static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
    }

    protected IObjectService getObjectService() {
        return this.objectService;
    }

    public IEditingContext getEditingContext() {
        return this.editingContext;
    }

    /**
     * Plugs a special {@link URIHandler} and {@link Factory.Registry} to be able to handle "classpath://$pathToFile"
     * URIs.
     */
    private void registerClasspathURIHandler() {
        this.editingDomain.getResourceSet().getURIConverter().getURIHandlers().add(0, new ClassPathResourceURIHandler());
        this.editingDomain.getResourceSet().getResourceFactoryRegistry().getProtocolToFactoryMap().put(ClassPathResourceURIHandler.CLASSPATH,
                new ClassPathResourceFactory(this.editingDomain.getResourceSet().getResourceFactoryRegistry()));
    }

    public AdapterFactoryEditingDomain getEditingDomain() {
        return this.editingDomain;
    }

    public ResourceSet getResourceSet() {
        return this.editingDomain.getResourceSet();
    }

    /**
     * Creates an element with the given type in the given parent. The containment reference is automatically computed
     * by finding the feature containment {@link EReference} that can contains the given object.
     *
     * @param <T>
     *            the expected type of the given element
     * @param type
     *            the expected type of the given element
     * @param parent
     *            the container
     * @return a new element
     */
    protected <T extends EObject> T createIn(java.lang.Class<T> type, EObject parent) {
        return this.umlHelper.createIn(type, parent);
    }

    protected <T extends EObject> T createInResource(java.lang.Class<T> type, Resource resource) {
        return this.umlHelper.createInResource(type, resource);
    }

    protected <T extends EObject> T createIn(java.lang.Class<T> type, EObject parent, String containmentRefName) {
        return this.umlHelper.createIn(type, parent, containmentRefName);
    }

    protected <T extends EObject> T create(java.lang.Class<T> type) {
        return this.umlHelper.create(type);
    }

    protected <T extends EObject> EClass getEClass(java.lang.Class<T> type) {
        return (EClass) UML.getEClassifier(type.getSimpleName());
    }

    protected Resource createResource(String resourceId) {
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourceId);
        this.editingDomain.getResourceSet().getResources().add(resource);
        return resource;
    }

    protected Resource createResource() {
        return this.createResource(UUID.randomUUID().toString());
    }

}
