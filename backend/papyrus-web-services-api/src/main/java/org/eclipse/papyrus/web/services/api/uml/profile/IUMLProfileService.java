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
package org.eclipse.papyrus.web.services.api.uml.profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.papyrus.web.services.api.dto.ApplyProfileInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;

/**
 * The UML profile service.
 *
 * @author lfasani
 */
public interface IUMLProfileService {
    List<UMLProfileMetadata> getAllUMLProfiles();

    IPayload applyProfile(IEditingContext editingContext, ApplyProfileInput input);

    /**
     * Search the profile among the dynamic profiles and return the last published version.<br/>
     * If the profile is not published yet the returned version is 0.0.0<br/>
     * If the version is empty something wrong happened.
     */
    Optional<UMLProfileVersion> getProfileLastVersion(IEditingContext editingContext, String profileId);

    /**
     * Update the given profile with the ecore meta model EPackage<br/>
     * . Then publish the given profile to the profile repository.
     */
    IPayload publishProfile(IEditingContext editingContext, PublishProfileInput publishProfileInput);

    /**
     * Deletes all profiles with the given name.
     *
     * @return a payload
     */
    IPayload deletePublishedDynamicProfileByName(String profileName);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author lfasani
     */
    class NoOp implements IUMLProfileService {

        @Override
        public List<UMLProfileMetadata> getAllUMLProfiles() {
            return new ArrayList<>();
        }

        @Override
        public IPayload applyProfile(IEditingContext editingContext, ApplyProfileInput input) {
            return null;
        }

        @Override
        public Optional<UMLProfileVersion> getProfileLastVersion(IEditingContext editingContext, String profileId) {
            return Optional.empty();
        }

        @Override
        public IPayload publishProfile(IEditingContext editingContext, PublishProfileInput publishProfileInput) {
            return null;
        }

        @Override
        public IPayload deletePublishedDynamicProfileByName(String profileName) {
            return null;
        }
    }
}
