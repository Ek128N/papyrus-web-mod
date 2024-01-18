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
package org.eclipse.papyrus.web.services.editingcontext.handlers;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.papyrus.web.services.Monitoring;
import org.eclipse.papyrus.web.services.api.dto.GetProfileLastVersionInput;
import org.eclipse.papyrus.web.services.api.dto.GetProfileLastVersionSuccessPayload;
import org.eclipse.papyrus.web.services.api.uml.profile.IUMLProfileService;
import org.eclipse.papyrus.web.services.api.uml.profile.UMLProfileVersion;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to get the last version of a dynamic Profile.
 *
 * @author lfasani
 */
@Service
public class GetProfileLastVersionEventHandler implements IEditingContextEventHandler {

    private final IUMLProfileService profileService;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public GetProfileLastVersionEventHandler(ICollaborativeMessageService messageService, MeterRegistry meterRegistry, IUMLProfileService stereotypeService) {
        this.messageService = Objects.requireNonNull(messageService);
        this.profileService = Objects.requireNonNull(stereotypeService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof GetProfileLastVersionInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), GetProfileLastVersionInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        if (input instanceof GetProfileLastVersionInput && editingContext != null) {
            Optional<UMLProfileVersion> profileLastVersion = this.profileService.getProfileLastVersion(editingContext, ((GetProfileLastVersionInput) input).profileId());

            if (profileLastVersion.isPresent()) {
                payload = new GetProfileLastVersionSuccessPayload(input.id(), profileLastVersion.get());
            } else {
                payload = new ErrorPayload(input.id(), "Failed to retrieve the current version of the profile");
            }
        }
        payloadSink.tryEmitValue(payload);
    }
}
