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
package org.eclipse.papyrus.web.services.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.web.services.aqlservices.scope.ReachableElementsServices;
import org.eclipse.papyrus.web.tests.utils.UMLTestHelper;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Reception;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.internal.resource.UMLResourceImpl;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link ReachableElementsServices} service class.
 *
 * @author Jerome Gout
 */
class ReachableElementsServicesTest {

    private static final String FAKE_1 = "fake://1";

    /**
     * The instance of PropertiesServices being tested.
     */
    private ReachableElementsServices propertiesService = new ReachableElementsServices();

    /**
     * Helper to create UML elements
     */
    private final UMLTestHelper umlHelper = new UMLTestHelper();

    /**
     * Test method for
     * {@link org.eclipse.papyrus.web.services.aqlservices.scope.ReachableElementsServices#getAllReachableElements(org.eclipse.emf.ecore.EObject, java.lang.String)}.
     */
    @Test
    void testGetAllReachableElements() {
        Activity activity = this.umlHelper.create(Activity.class);
        Artifact artifact = this.umlHelper.createIn(Artifact.class, activity);
        Operation operation = this.umlHelper.createIn(Operation.class, artifact);
        Reception reception = this.umlHelper.createIn(Reception.class, activity);
        List<EObject> elements = this.propertiesService.getAllReachableElements(activity, "specification");
        assertEquals(2, elements.size());
        assertEquals(operation, elements.get(0));
        assertEquals(reception, elements.get(1));
    }

    /**
     * Test method for
     * {@link org.eclipse.papyrus.web.services.aqlservices.scope.ReachableElementsServices#getAllReachableRootElements(org.eclipse.emf.ecore.EObject)}.
     */
    @Test
    void testGetAllReachableRootElements() {
        ResourceSet rs = new ResourceSetImpl();
        UMLResource resource = new UMLResourceImpl(URI.createURI(FAKE_1));
        rs.getResources().add(resource);
        Package pack = UMLFactory.eINSTANCE.createPackage();
        resource.getContents().add(pack);
        Activity activity = this.umlHelper.createIn(Activity.class, pack);
        List<Notifier> roots = this.propertiesService.getAllReachableRootElements(activity);
        assertEquals(1, roots.size());
        assertEquals(pack, roots.get(0));
    }

    /**
     * Test method for
     * {@link org.eclipse.papyrus.web.services.aqlservices.scope.ReachableElementsServices#getAllRootPackages(org.eclipse.emf.ecore.EObject)}.
     */
    @Test
    void testGetAllRootPackages() {
        ResourceSet rs = new ResourceSetImpl();
        UMLResource resource = new UMLResourceImpl(URI.createURI(FAKE_1));
        rs.getResources().add(resource);
        Package packRoot = this.umlHelper.create(Package.class);
        resource.getContents().add(packRoot);
        Activity activity = this.umlHelper.createIn(Activity.class, packRoot);
        this.umlHelper.createIn(Package.class, packRoot);
        List<Package> allRootPackages = this.propertiesService.getAllRootPackages(activity);
        assertEquals(1, allRootPackages.size());
        assertEquals(packRoot, allRootPackages.get(0));
    }

    /**
     * Test method for
     * {@link org.eclipse.papyrus.web.services.aqlservices.scope.ReachableElementsServices#getAllUMLPackages(org.eclipse.emf.ecore.EObject)}.
     */
    @Test
    void testGetAllUMLPackages() {
        ResourceSet rs = new ResourceSetImpl();
        UMLResource resource = new UMLResourceImpl(URI.createURI(FAKE_1));
        rs.getResources().add(resource);
        Package packRoot = this.umlHelper.create(Package.class);
        resource.getContents().add(packRoot);
        Activity activity = this.umlHelper.createIn(Activity.class, packRoot);
        Package packNested = this.umlHelper.createIn(Package.class, packRoot);
        List<Package> allUMLPackages = this.propertiesService.getAllUMLPackages(activity);
        assertEquals(2, allUMLPackages.size());
        assertEquals(packRoot, allUMLPackages.get(0));
        assertEquals(packNested, allUMLPackages.get(1));
    }

}
