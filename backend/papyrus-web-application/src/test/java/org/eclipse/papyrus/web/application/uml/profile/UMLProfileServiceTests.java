/*******************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.uml.profile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.web.application.utils.AbstractWebUMLTest;
import org.eclipse.papyrus.web.persistence.entities.ProfileResourceEntity;
import org.eclipse.papyrus.web.persistence.repositories.IProfileRepository;
import org.eclipse.papyrus.web.services.api.dto.ApplyProfileInput;
import org.eclipse.papyrus.web.services.api.dto.ApplyProfileSuccessPayload;
import org.eclipse.papyrus.web.services.api.uml.profile.IUMLProfileService;
import org.eclipse.papyrus.web.services.api.uml.profile.PublishProfileInput;
import org.eclipse.papyrus.web.services.api.uml.profile.PublishProfileSuccessPayload;
import org.eclipse.papyrus.web.services.api.uml.profile.UMLProfileVersion;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tests the services in charge of using UML Profiles.
 *
 * @author Arthur Daussy
 */
@SpringBootTest
@WebAppConfiguration
@Transactional
public class UMLProfileServiceTests extends AbstractWebUMLTest {

    @Autowired
    private IUMLProfileService profileService;

    @Autowired
    private IProfileRepository profileRepository;

    /**
     * Test the application of a static package profile (<i>Standard Profile</i>).
     */
    @Test
    public void testApplyStaticProfile() {
        Model model = this.create(Model.class);

        Class aClass = this.create(Class.class);
        model.getPackagedElements().add(aClass);

        Resource umlResource = this.createResource("r1"); //$NON-NLS-1$
        umlResource.getContents().add(model);

        this.editingDomain.getResourceSet().getResource(URI.createURI("pathmap://UML_PROFILES/Standard.profile.uml"), true); //$NON-NLS-1$
        IPayload payload = this.profileService.applyProfile(this.getEditingContext(), new ApplyProfileInput(UUID.randomUUID(), this.getEditingContext().getId(), this.getObjectService().getId(model),
                URI.createURI("pathmap://UML_PROFILES/Standard.profile.uml#_0").toString())); //$NON-NLS-1$
        assertTrue(payload instanceof ApplyProfileSuccessPayload);

        EList<ProfileApplication> appliedProfiles = model.getProfileApplications();
        assertEquals(1, appliedProfiles.size());

        ProfileApplication appliedProfile = appliedProfiles.get(0);
        assertEquals("StandardProfile", appliedProfile.getAppliedProfile().getName()); //$NON-NLS-1$
    }

    /**
     * Test loading a model with a Profile and Stereotypes from a static profile.
     *
     * @throws IOException
     */
    @Test
    public void testLoadModelWithStaticProfile() throws IOException {

        Resource modelWithProfileResource = this.createResource("modelWithStandardProfile"); //$NON-NLS-1$

        try (var input = new ClassPathResource("profile/UMLModelWithStandardProfile.json").getInputStream()) { //$NON-NLS-1$
            modelWithProfileResource.load(input, Collections.emptyMap());
        }

        Model model = (Model) modelWithProfileResource.getContents().get(0);

        EList<Profile> appliedProfiles = model.getAllAppliedProfiles();
        assertEquals(1, appliedProfiles.size());

        PackageableElement classOneStereotype = model.getPackagedElement("ClassOneStereotype"); //$NON-NLS-1$
        EList<Stereotype> c1Stereotypes = classOneStereotype.getAppliedStereotypes();
        assertEquals(1, c1Stereotypes.size());
        assertEquals("Utility", c1Stereotypes.get(0).getName()); //$NON-NLS-1$

        PackageableElement classTwoStereotypes = model.getPackagedElement("ClassTwoStereotypes"); //$NON-NLS-1$
        EList<Stereotype> c2Stereotypes = classTwoStereotypes.getAppliedStereotypes();
        assertEquals(2, c2Stereotypes.size());
        assertEquals("Auxiliary", c2Stereotypes.get(0).getName()); //$NON-NLS-1$
        assertEquals("Focus", c2Stereotypes.get(1).getName()); //$NON-NLS-1$

    }

    /**
     * Check the behavior while giving an invalid profile URI resource. => Expected Profile application fails
     */
    @Test
    public void invalidProfileResourceContentInvalidURI() {
        Model model = this.create(Model.class);

        Resource umlResource = this.createResource("r2"); //$NON-NLS-1$
        umlResource.getContents().add(model);

        IPayload payload = this.profileService.applyProfile(this.getEditingContext(),
                new ApplyProfileInput(UUID.randomUUID(), this.getEditingContext().getId(), this.getObjectService().getId(model), URI.createURI("fake:/tot").toString())); //$NON-NLS-1$
        assertTrue(payload instanceof ErrorPayload);

        payload = this.profileService.applyProfile(this.getEditingContext(),
                new ApplyProfileInput(UUID.randomUUID(), this.getEditingContext().getId(), this.getObjectService().getId(model), URI.createURI("fake/test").toString())); //$NON-NLS-1$
        assertTrue(payload instanceof ErrorPayload);
    }

    /**
     * Test profile publication.
     *
     * @throws IOException
     */
    @Test
    public void testProfilePublication() throws IOException {
        Resource modelWithProfileResource = this.createResource("profileResource"); //$NON-NLS-1$

        try (var input = new ClassPathResource("profile/profile.json").getInputStream()) { //$NON-NLS-1$
            modelWithProfileResource.load(input, Collections.emptyMap());
        }

        String profileId = "7420affe-b576-4013-b5d2-02cb0f3c48b1"; //$NON-NLS-1$
        String version1 = "1.0.1"; //$NON-NLS-1$
        String version2 = "2.0.1"; //$NON-NLS-1$
        String comment = "comment"; //$NON-NLS-1$
        String copyright = "copyright"; //$NON-NLS-1$
        String date = "date"; //$NON-NLS-1$
        String author = "author"; //$NON-NLS-1$

        IPayload payload = this.profileService.publishProfile(this.getEditingContext(),
                new PublishProfileInput(UUID.randomUUID(), this.getEditingContext().getId(), profileId, version1, comment, copyright, date, author, true));

        assertTrue(payload instanceof PublishProfileSuccessPayload);

        Optional<ProfileResourceEntity> profileResourceEntity = this.profileRepository.findById(UUID.nameUUIDFromBytes(modelWithProfileResource.getURI().lastSegment().getBytes()));
        assertTrue(profileResourceEntity.isPresent());
        profileResourceEntity.get().getContent().contains(profileId);

        Optional<UMLProfileVersion> profileLastVersion = this.profileService.getProfileLastVersion(this.getEditingContext(), profileId);
        assertEquals(new UMLProfileVersion(1, 0, 1), profileLastVersion.get());

        // publish a second time
        payload = this.profileService.publishProfile(this.getEditingContext(),
                new PublishProfileInput(UUID.randomUUID(), this.getEditingContext().getId(), profileId, version2, comment, copyright, date, author, true));

        assertTrue(payload instanceof PublishProfileSuccessPayload);

        profileResourceEntity = this.profileRepository.findById(UUID.nameUUIDFromBytes(modelWithProfileResource.getURI().lastSegment().getBytes()));
        assertTrue(profileResourceEntity.isPresent());
        profileResourceEntity.get().getContent().contains(profileId);

        profileLastVersion = this.profileService.getProfileLastVersion(this.getEditingContext(), profileId);
        assertEquals(new UMLProfileVersion(2, 0, 1), profileLastVersion.get());
    }
}
