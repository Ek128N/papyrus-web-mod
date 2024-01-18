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
package org.eclipse.papyrus.web.services.editingcontext.handlers;

import java.util.Objects;

import org.eclipse.papyrus.web.services.Monitoring;
import org.eclipse.papyrus.web.services.api.dto.IsProfileDiagramInput;
import org.eclipse.papyrus.web.services.api.dto.IsProfileDiagramSuccessPayload;
import org.eclipse.papyrus.web.services.aqlservices.profile.ProfileDiagramService;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler that returns whether a diagram is a profile diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@Service
public class IsProfileDiagramEventHandler implements IEditingContextEventHandler {

    private final ProfileDiagramService profileDiagramService;

    private final IObjectService objectService;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public IsProfileDiagramEventHandler(ICollaborativeMessageService messageService, MeterRegistry meterRegistry, ProfileDiagramService profileDiagramService, IObjectService objectService) {
        this.messageService = Objects.requireNonNull(messageService);
        this.profileDiagramService = profileDiagramService;
        this.objectService = objectService;

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof IsProfileDiagramInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), IsProfileDiagramInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        if (input instanceof IsProfileDiagramInput isProfileDiagramInput && editingContext != null) {
            String representationId = isProfileDiagramInput.representationId();

            Boolean result = this.profileDiagramService.isProfileModel(editingContext, representationId);
            payload = new IsProfileDiagramSuccessPayload(input.id(), result);
        }
        payloadSink.tryEmitValue(payload);
    }
}
