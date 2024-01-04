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
package org.eclipse.papyrus.web.services.uml.profile;

import java.util.Objects;

import org.eclipse.papyrus.web.services.api.uml.profile.IUMLProfileService;
import org.eclipse.papyrus.web.services.api.uml.profile.PublishProfileInput;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Used to publish a profile.
 *
 * @author lfasani
 */
@Service
public class PublishProfileEventHandler implements IEditingContextEventHandler {

    private final IUMLProfileService profileService;

    public PublishProfileEventHandler(IUMLProfileService profileService) {
        this.profileService = Objects.requireNonNull(profileService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof PublishProfileInput;
    }

    @Override
    @Transactional
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        var publishProfileInput = (PublishProfileInput) input;

        IPayload payload = this.profileService.publishProfile(editingContext, publishProfileInput);

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input));
    }

}
