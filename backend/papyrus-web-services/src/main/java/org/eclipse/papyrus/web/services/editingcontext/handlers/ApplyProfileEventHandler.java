/*******************************************************************************
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
package org.eclipse.papyrus.web.services.editingcontext.handlers;

import java.util.Objects;

import org.eclipse.papyrus.web.services.Monitoring;
import org.eclipse.papyrus.web.services.api.dto.ApplyProfileInput;
import org.eclipse.papyrus.web.services.api.dto.ApplyProfileSuccessPayload;
import org.eclipse.papyrus.web.services.api.uml.profile.IUMLProfileService;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
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
 * Handler used to apply an UML profile.
 *
 * @author lfasani
 */
@Service
public class ApplyProfileEventHandler implements IEditingContextEventHandler {

    private final IUMLProfileService profileService;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public ApplyProfileEventHandler(ICollaborativeMessageService messageService, IUMLProfileService profileService, MeterRegistry meterRegistry) {
        this.profileService = Objects.requireNonNull(profileService);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof ApplyProfileInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
        IPayload payload = null;

        final String message;
        if (input instanceof ApplyProfileInput) {
            payload = this.profileService.applyProfile(editingContext, (ApplyProfileInput) input);

            if (payload instanceof ApplyProfileSuccessPayload) {
                payload = new ApplyProfileSuccessPayload(input.id());
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input);
                message = null;
            } else {
                message = "The profile application failed";
            }
        } else {
            message = this.messageService.invalidInput(input.getClass().getSimpleName(), ApplyProfileInput.class.getSimpleName());
        }

        if (payload == null) {
            payload = new ErrorPayload(input.id(), message);
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
