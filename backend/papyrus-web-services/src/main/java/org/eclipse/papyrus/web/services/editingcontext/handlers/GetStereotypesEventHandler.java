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

import java.util.List;
import java.util.Objects;

import org.eclipse.papyrus.web.services.Monitoring;
import org.eclipse.papyrus.web.services.api.dto.GetStereotypesInput;
import org.eclipse.papyrus.web.services.api.dto.GetStereotypesSuccessPayload;
import org.eclipse.papyrus.web.services.api.uml.profile.IUMLStereotypeService;
import org.eclipse.papyrus.web.services.api.uml.profile.UMLStereotypeMetadata;
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
 * Handler used to get the applicable stereotypes for an UML Element.
 *
 * @author lfasani
 */
@Service
public class GetStereotypesEventHandler implements IEditingContextEventHandler {

    private final IUMLStereotypeService stereotypeService;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public GetStereotypesEventHandler(ICollaborativeMessageService messageService, MeterRegistry meterRegistry, IUMLStereotypeService stereotypeService) {
        this.messageService = Objects.requireNonNull(messageService);
        this.stereotypeService = Objects.requireNonNull(stereotypeService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof GetStereotypesInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), GetStereotypesInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        if (input instanceof GetStereotypesInput && editingContext != null) {
            List<UMLStereotypeMetadata> stereotypeMetadatas = this.stereotypeService.getApplicableStereotypeOn(editingContext, ((GetStereotypesInput) input).elementId());
            payload = new GetStereotypesSuccessPayload(input.id(), stereotypeMetadatas);
        }
        payloadSink.tryEmitValue(payload);

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
