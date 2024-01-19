/*******************************************************************************
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.uml.domain.services.EMFUtils;
import org.eclipse.papyrus.uml.domain.services.properties.PropertiesStereotypeApplicationServices;
import org.eclipse.papyrus.web.services.aqlservices.properties.PropertiesCrudServicesWrapper;
import org.eclipse.papyrus.web.tests.utils.MockEditableChecker;
import org.eclipse.papyrus.web.tests.utils.MockLogger;
import org.eclipse.papyrus.web.tests.utils.UMLTestHelper;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Actor;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Duration;
import org.eclipse.uml2.uml.DurationInterval;
import org.eclipse.uml2.uml.Expression;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Variable;
import org.eclipse.uml2.uml.internal.resource.UMLResourceImpl;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link PropertiesCrudServicesWrapper} service class.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 * @author Jerome Gout
 */
public class PropertiesCrudServicesWrapperTest {

    /**
     * Name reference name.
     */
    public static final String NAME = "name"; //$NON-NLS-1$

    /**
     * Unknown reference name.
     */
    public static final String FAKE = "fake"; //$NON-NLS-1$

    /**
     * the name of the multi-valued reference to stereotype2
     */
    private static final String MULTI_REFERENCE_TO_STEREOTYPE = "testReftoStereotype2";

    /**
     * the name of the mono-valued reference to stereotype2
     */
    private static final String MONO_REFERENCE_TO_STEREOTYPE = "testUnaryReftoStereotype2";

    /**
     * context reference name (inside Constraint for instance)
     */
    private static final String CONTEXT = "context";

    /**
     * min reference name.
     */
    private static final String MIN = "min";

    /**
     * ConstrainedElement reference name.
     */
    private static final String CONSTRAINED_ELEMENT = "constrainedElement"; //$NON-NLS-1$

    /**
     * OwnedAttribute reference name.
     */
    private static final String OWNED_ATTRIBUTE = "ownedAttribute"; //$NON-NLS-1$

    /**
     * PackagedElement reference name.
     */
    private static final String PACKAGED_ELEMENT = "packagedElement"; //$NON-NLS-1$

    /**
     * Type reference name.
     */
    private static final String TYPE = "type"; //$NON-NLS-1$

    /**
     * New name to set.
     */
    private static final String UPDATE_NAME = "updateName"; //$NON-NLS-1$

    /**
     * The instance of PropertiesServices being tested.
     */
    private PropertiesCrudServicesWrapper propertiesService = new PropertiesCrudServicesWrapper(new MockLogger(), new MockEditableChecker());

    /**
     * Helper to create UML elements
     */
    private final UMLTestHelper umlHelper = new UMLTestHelper();

    private final PropertiesStereotypeApplicationServices stereotypeApplicationServices = new PropertiesStereotypeApplicationServices(new MockLogger());

    @Test
    public void testDeleteNullObj() {
        Component component = this.umlHelper.create(Component.class);
        assertFalse(this.propertiesService.delete(null, component, PACKAGED_ELEMENT));
    }

    /**
     * reference is null or unknown: EcoreUtil.remove is called by default.
     */
    @Test
    public void testDeleteUnknownRef() {
        Component component = this.umlHelper.create(Component.class);
        org.eclipse.uml2.uml.Class clazz = this.umlHelper.create(org.eclipse.uml2.uml.Class.class);
        component.getPackagedElements().add(clazz);
        assertTrue(this.propertiesService.delete(clazz, component, null));
        assertTrue(component.getPackagedElements().isEmpty());
    }

    /**
     * Test of deletion of the containment reference value.
     */
    @Test
    public void testDeleteKnownContainmentRef() {
        Component component = this.umlHelper.create(Component.class);
        org.eclipse.uml2.uml.Class clazz = this.umlHelper.create(org.eclipse.uml2.uml.Class.class);
        component.getPackagedElements().add(clazz);
        assertTrue(this.propertiesService.delete(clazz, component, PACKAGED_ELEMENT));
        assertTrue(component.getPackagedElements().isEmpty());
    }

    /**
     * Test of deletion of the non containment reference value.
     */
    @Test
    public void testDeleteKnownNonContainmentRef() {
        Constraint constraint = this.umlHelper.create(Constraint.class);
        org.eclipse.uml2.uml.Class clazz = this.umlHelper.create(org.eclipse.uml2.uml.Class.class);
        constraint.getConstrainedElements().add(clazz);
        assertTrue(this.propertiesService.delete(clazz, constraint, CONSTRAINED_ELEMENT));
        assertTrue(constraint.getConstrainedElements().isEmpty());
    }

    /**
     * Test of creation of an element as a value of a containment reference.
     */
    @Test
    public void testCreateContainmentRef() {
        Component component = this.umlHelper.create(Component.class);
        EObject createdObject = this.propertiesService.create(component, Actor.class.getSimpleName(), PACKAGED_ELEMENT);
        assertEquals(component, createdObject.eContainer());
        EStructuralFeature ref = component.eClass().getEStructuralFeature(PACKAGED_ELEMENT);
        Object eGet = component.eGet(ref);
        List<?> list = (List<?>) eGet;
        assertTrue(list.contains(createdObject));
    }

    /**
     * Test of creation of an element as a value of a non containment reference.
     */
    @Test
    public void testCreateNonContainmentRef() {
        Property property = this.umlHelper.create(Property.class);
        EObject createdObject = this.propertiesService.create(property, Actor.class.getSimpleName(), TYPE);
        EStructuralFeature ref = property.eClass().getEStructuralFeature(TYPE);
        assertEquals(createdObject, property.eGet(ref));
    }

    /**
     * Try to set the reference of null.
     */
    @Test
    public void testSetNullTarget() {
        assertFalse(this.propertiesService.set(null, NAME, UPDATE_NAME));
    }

    /**
     * Try to set an unknown reference.
     */
    @Test
    public void testSetUknownFeatureName() {
        org.eclipse.uml2.uml.Class clazz = this.umlHelper.create(org.eclipse.uml2.uml.Class.class);
        assertFalse(this.propertiesService.set(clazz, FAKE, UPDATE_NAME));
    }

    /**
     * Test of setting the mono reference value.
     */
    @Test
    public void testSetUnaryFeature() {
        org.eclipse.uml2.uml.Class clazz = this.umlHelper.create(org.eclipse.uml2.uml.Class.class);
        clazz.setName("name1"); //$NON-NLS-1$
        assertTrue(this.propertiesService.set(clazz, NAME, UPDATE_NAME));
        assertEquals(UPDATE_NAME, clazz.getName());
    }

    /**
     * Test of setting a multi reference value.
     */
    @Test
    public void testSetNaryFeature() {
        org.eclipse.uml2.uml.Class clazz = this.umlHelper.create(org.eclipse.uml2.uml.Class.class);
        Property property1 = this.umlHelper.create(Property.class);
        Property property2 = this.umlHelper.create(Property.class);
        clazz.getOwnedAttributes().add(property1);
        assertTrue(this.propertiesService.set(clazz, OWNED_ATTRIBUTE, Arrays.asList(property2)));
        assertEquals(Arrays.asList(property2), clazz.getOwnedAttributes());
    }

    /**
     * Try to update a reference on null.
     */
    @Test
    public void testUpdateReferenceNullTarget() {
        Property property2 = this.umlHelper.create(Property.class);
        assertFalse(this.propertiesService.updateReference(null, property2, OWNED_ATTRIBUTE));
    }

    /**
     * Try to update an unknown reference.
     */
    @Test
    public void testUpdateReferenceUnknownFeature() {
        org.eclipse.uml2.uml.Class clazz = this.umlHelper.create(org.eclipse.uml2.uml.Class.class);
        Property property2 = this.umlHelper.create(Property.class);
        assertFalse(this.propertiesService.updateReference(clazz, property2, FAKE));
    }

    /**
     * Test of updating a containment reference.
     */
    @Test
    public void testUpdateReferenceContainmentFeature() {
        org.eclipse.uml2.uml.Class clazz = this.umlHelper.create(org.eclipse.uml2.uml.Class.class);
        Property property1 = this.umlHelper.create(Property.class);
        Property property2 = this.umlHelper.create(Property.class);
        clazz.getOwnedAttributes().add(property1);
        assertFalse(this.propertiesService.updateReference(clazz, property2, OWNED_ATTRIBUTE));
    }

    /**
     * Test of updating a non contiainment reference.
     */
    @Test
    public void testUpdateReferenceNonContainmentFeatureWothElement() {
        Constraint constraint = this.umlHelper.create(Constraint.class);
        org.eclipse.uml2.uml.Class clazz1 = this.umlHelper.create(org.eclipse.uml2.uml.Class.class);
        constraint.getConstrainedElements().add(clazz1);
        org.eclipse.uml2.uml.Class clazz2 = this.umlHelper.create(org.eclipse.uml2.uml.Class.class);
        assertTrue(this.propertiesService.updateReference(constraint, clazz2, CONSTRAINED_ELEMENT));
        assertEquals(Arrays.asList(clazz1, clazz2), constraint.getConstrainedElements());
    }

    /**
     * Test of updating a multi-valued non containment reference.
     */
    @Test
    public void testUpdateReferenceNonContainmentFeatureWithList() {
        Constraint constraint = this.umlHelper.create(Constraint.class);
        org.eclipse.uml2.uml.Class clazz1 = this.umlHelper.create(org.eclipse.uml2.uml.Class.class);
        constraint.getConstrainedElements().add(clazz1);
        org.eclipse.uml2.uml.Class clazz2 = this.umlHelper.create(org.eclipse.uml2.uml.Class.class);
        assertTrue(this.propertiesService.updateReference(constraint, Arrays.asList(clazz2), CONSTRAINED_ELEMENT));
        assertEquals(Arrays.asList(clazz2), constraint.getConstrainedElements());
    }

    /**
     * Try to create a not supported element in a reference (type redefinition case).
     */
    @Test
    public void testCreateInvalidReferenceRedefined() {
        DurationInterval interval = this.umlHelper.create(DurationInterval.class);
        EObject createdObject = this.propertiesService.create(interval, Expression.class.getSimpleName(), MIN);
        assertNull(createdObject);
    }

    /**
     * Test of creating a valid element inside a reference with type redefinition.
     */
    @Test
    public void testCreateValidReferenceRedefined() {
        DurationInterval interval = this.umlHelper.create(DurationInterval.class);
        EObject createdObject = this.propertiesService.create(interval, Duration.class.getSimpleName(), MIN);
        assertNotNull(createdObject);
    }

    /**
     * Try to delete the value of a container reference.
     */
    @Test
    public void testDeleteContainerReference() {
        Constraint constraint = this.umlHelper.create(Constraint.class);
        Activity activity = this.umlHelper.create(Activity.class);
        constraint.setContext(activity);
        boolean isDeleted = this.propertiesService.delete(activity, constraint, CONTEXT);
        assertFalse(isDeleted);
        assertEquals(activity, constraint.getContext());
    }

    /**
     * Try to update a not supported element in a reference (type redefinition case).
     */
    @Test
    public void testUpdateReferenceInvalidRedefined() {
        DurationInterval interval = this.umlHelper.create(DurationInterval.class);
        Duration duration = this.umlHelper.create(Duration.class);
        interval.setMin(duration);
        Expression expr = this.umlHelper.create(Expression.class);
        boolean isUpdated = this.propertiesService.updateReference(interval, expr, MIN);
        assertFalse(isUpdated);
    }

    /**
     * Test of clearing mono and multi references value.
     */
    @Test
    public void testClearReference() {
        // Mono ref
        Property property = this.umlHelper.create(Property.class);
        Actor actor = this.umlHelper.create(Actor.class);
        property.setType(actor);
        assertTrue(this.propertiesService.clearReference(property, TYPE));
        // Multi ref
        org.eclipse.uml2.uml.Class clazz = this.umlHelper.create(org.eclipse.uml2.uml.Class.class);
        Property property1 = this.umlHelper.create(Property.class);
        Property property2 = this.umlHelper.create(Property.class);
        clazz.getOwnedAttributes().addAll(List.of(property1, property2));
        assertTrue(this.propertiesService.clearReference(clazz, OWNED_ATTRIBUTE));
    }

    /**
     * Test of adding multi values to a multi-valued reference.
     */
    @Test
    public void testAddReferenceElement() {
        org.eclipse.uml2.uml.Class clazz = this.umlHelper.create(org.eclipse.uml2.uml.Class.class);
        Property property1 = this.umlHelper.create(Property.class);
        Property property2 = this.umlHelper.create(Property.class);
        assertTrue(this.propertiesService.addReferenceElement(clazz, List.of(property1, property2), OWNED_ATTRIBUTE));
    }

    /**
     * Try to add new element in mono reference. Actor.type is a mono reference. Add element can only be applied on a
     * multi reference. Check that service returns {@code false}
     */
    @Test
    public void testAddReferenceElementInvalid() {
        Property property = this.umlHelper.create(Property.class);
        Actor actor = this.umlHelper.create(Actor.class);
        assertFalse(this.propertiesService.addReferenceElement(property, List.of(actor), TYPE));
    }

    /**
     * Move Variable reference values of an Activity. Move the first var to become the second. Check before and after
     * moving.
     */
    @Test
    public void testMoveReferenceElement() {
        Activity activity = this.umlHelper.create(Activity.class);
        Variable var1 = this.umlHelper.create(Variable.class);
        Variable var2 = this.umlHelper.create(Variable.class);
        activity.getVariables().addAll(List.of(var1, var2));
        assertEquals(var1, activity.getVariables().get(0));
        this.propertiesService.moveReferenceElement(activity, "variable", var1, 0, 1);
        assertEquals(var2, activity.getVariables().get(0));
    }

    /**
     * Move mono reference. Nothing should append.
     */
    @Test
    public void testMoveReferenceElementInvalidMonoRef() {
        Property property = this.umlHelper.create(Property.class);
        Actor actor = this.umlHelper.create(Actor.class);
        property.setType(actor);
        this.propertiesService.moveReferenceElement(property, TYPE, actor, 0, 1);
        assertEquals(actor, property.getType());
    }

    private Optional<EClass> loadAndRetrieveStereotype2Class(ResourceSet rs) throws IOException {
        Optional<EClass> stereotype2 = Optional.empty();
        URL url = this.getClass().getClassLoader().getResource("test_import/DynamicProfileTypeTests.profile.uml");
        try (var stream = url.openStream()) {
            UMLResource profileResource = new UMLResourceImpl(URI.createURI("fake:/profile"));
            rs.getResources().add(profileResource);
            profileResource.load(stream, Map.of());
            stereotype2 = EMFUtils.allContainedObjectOfType(profileResource, EClass.class).filter(c -> "Stereotype2".equals(c.getName())).findFirst();
        }
        return stereotype2;
    }

    /**
     * Record used to return all data necessary from the instance model.
     *
     * @author Jerome Gout
     */
    private record InstanceData(Class class1Stereotype2, Class class2Stereotype2, EObject self) {
    }

    private Optional<InstanceData> loadAndRetrieveInstanceData(ResourceSet rs) throws IOException {
        Optional<InstanceData> result = Optional.empty();
        URL url2 = this.getClass().getClassLoader().getResource("test_import/InstanceDynProfile.uml");
        try (var stream2 = url2.openStream()) {
            UMLResource instanceResource = new UMLResourceImpl(URI.createURI("fake:/instance"));
            rs.getResources().add(instanceResource);
            instanceResource.load(stream2, Map.of());
            List<Class> classes = EMFUtils.allContainedObjectOfType(instanceResource, Class.class).toList();
            EObject self = EMFUtils.allContainedObjectOfType(instanceResource, DynamicEObjectImpl.class).toList().get(0);
            result = Optional.of(new InstanceData(classes.get(1), classes.get(2), self));
        }
        return result;
    }

    /**
     * Tests of a Multi Reference to Stereotype. This includes tests of:<br>
     * <ul>
     * <li>addReferenceStereotypeApplicationfromBase
     * <li>addStereotypeApplicationFromBase
     * <li>moveReferenceStereotypeApplicationFromBase
     * <li>removeStereotypeApplicationFromBase
     * </ul>
     */
    @Test
    public void testMultiReferenceToStereotype() throws IOException {
        ResourceSet rs = new ResourceSetImpl();
        UMLResourcesUtil.init(rs);
        Optional<EClass> stereotype2Opt = this.loadAndRetrieveStereotype2Class(rs);
        this.loadAndRetrieveInstanceData(rs).ifPresent(id -> {
            var stereotype2 = stereotype2Opt.get();
            var class1Stereotype2 = id.class1Stereotype2;
            var class2Stereotype2 = id.class2Stereotype2;
            var self = id.self;
            // add class1Stereotype2 and class2Stereotype2 to the reference
            assertTrue(this.propertiesService.addReferenceStereotypeApplicationfromBase(self, List.of(class1Stereotype2, class2Stereotype2), MULTI_REFERENCE_TO_STEREOTYPE, stereotype2));
            // check that the class1Stereotype2 is the first value of the reference
            EReference reference = (EReference) self.eClass().getEStructuralFeature(MULTI_REFERENCE_TO_STEREOTYPE);
            Object value = this.stereotypeApplicationServices.getStereotypeFeatureBaseElementValue(self, reference);
            assertEquals(2, ((List<?>) value).size());
            assertEquals(class1Stereotype2, ((List<?>) value).get(0));
            // update reference value list with class1Stereotype2 and class2Stereotype2
            assertTrue(this.propertiesService.addStereotypeApplicationFromBase(self, List.of(class1Stereotype2, class2Stereotype2), MULTI_REFERENCE_TO_STEREOTYPE, stereotype2));
            // check that the class1Stereotype2 is the first value of the reference
            value = this.stereotypeApplicationServices.getStereotypeFeatureBaseElementValue(self, reference);
            assertEquals(2, ((List<?>) value).size());
            assertEquals(class1Stereotype2, ((List<?>) value).get(0));
            // move first reference values to second place
            this.propertiesService.moveReferenceStereotypeApplicationFromBase(self, MULTI_REFERENCE_TO_STEREOTYPE, class1Stereotype2, stereotype2, 0, 1);
            // check that the class1Stereotype2 is now the second value of the reference
            value = this.stereotypeApplicationServices.getStereotypeFeatureBaseElementValue(self, reference);
            assertEquals(class1Stereotype2, ((List<?>) value).get(1));
            // remove the first element (class2Stereotype2) of the reference value list
            assertTrue(this.propertiesService.removeStereotypeApplicationFromBase(class2Stereotype2, self, MULTI_REFERENCE_TO_STEREOTYPE, stereotype2));
            // check that the class1Stereotype2 is the first value of the reference
            value = this.stereotypeApplicationServices.getStereotypeFeatureBaseElementValue(self, reference);
            assertEquals(class1Stereotype2, ((List<?>) value).get(0));
        });
    }

    /**
     * Tests of a Mono Reference to Stereotype. This includes tests of:<br>
     * <ul>
     * <li>addStereotypeApplicationFromBase
     * <li>removeStereotypeApplicationFromBase
     * </ul>
     */
    @Test
    public void testMonoReferenceToStereotype() throws IOException {
        ResourceSet rs = new ResourceSetImpl();
        UMLResourcesUtil.init(rs);
        Optional<EClass> stereotype2Opt = this.loadAndRetrieveStereotype2Class(rs);
        this.loadAndRetrieveInstanceData(rs).ifPresent(id -> {
            var stereotype2 = stereotype2Opt.get();
            var class1Stereotype2 = id.class1Stereotype2;
            var class2Stereotype2 = id.class2Stereotype2;
            var self = id.self;
            // set the reference to class1Stereotype2
            assertTrue(this.propertiesService.addStereotypeApplicationFromBase(self, class1Stereotype2, MONO_REFERENCE_TO_STEREOTYPE, stereotype2));
            // check that the class1Stereotype2 is the value of the reference
            EReference reference = (EReference) self.eClass().getEStructuralFeature(MONO_REFERENCE_TO_STEREOTYPE);
            Object value = this.stereotypeApplicationServices.getStereotypeFeatureBaseElementValue(self, reference);
            assertEquals(class1Stereotype2, value);
            // update the value of the reference to be class2Stereotype2
            assertTrue(this.propertiesService.addStereotypeApplicationFromBase(self, class2Stereotype2, MONO_REFERENCE_TO_STEREOTYPE, stereotype2));
            // check that the class2Stereotype2 is now the value of the reference
            value = this.stereotypeApplicationServices.getStereotypeFeatureBaseElementValue(self, reference);
            assertEquals(class2Stereotype2, value);
            // remove the reference value (class2Stereotype2)
            assertTrue(this.propertiesService.removeStereotypeApplicationFromBase(class2Stereotype2, self, MONO_REFERENCE_TO_STEREOTYPE, stereotype2));
            // check that the class1Stereotype2 is the first value of the reference
            value = this.stereotypeApplicationServices.getStereotypeFeatureBaseElementValue(self, reference);
            assertEquals(null, value);
        });
    }
}
