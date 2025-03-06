/*****************************************************************************
 * Copyright (c) 2025 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *  Dilan EESHVARAN (CEA LIST) dilan.eeshvaran@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.web.application.representations.aqlservices.statemachine;

import java.util.Optional;

import org.eclipse.papyrus.uml.domain.services.labels.ElementDefaultNameProvider;
import org.eclipse.papyrus.web.application.representations.uml.SMDDiagramDescriptionBuilder;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IInputPreProcessor;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.ProtocolStateMachine;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.UMLFactory;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks.Many;

/**
 *
 * Adapted from ActivityInputPreProcessor. Service to provide actions before the input processing.
 *
 * Service used to create intermediate semantic StateMachine when creating StateMachine diagram representations.
 * <p>
 * This service allows to create a representation on an element that doesn't directly supports it. In this case this
 * class takes care of creating the intermediate semantic StateMachine required to create the StateMachine diagram
 * representation.
 * </p>
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@Service
public class StateMachineInputPreProcessor implements IInputPreProcessor {

    private IObjectService objectService;

    /**
     * The constructor.
     *
     * @param objectService
     *            service used to retrieve semantic target according to node id
     */
    public StateMachineInputPreProcessor(IObjectService objectService) {
        this.objectService = objectService;
    }

    @Override
    public IInput preProcess(IEditingContext editingContext, IInput input, Many<ChangeDescription> changeDescriptionSink) {
        IInput result = input;

        if (input instanceof CreateRepresentationInput createRepresentationInput) {
            if (SMDDiagramDescriptionBuilder.SMD_REP_NAME.equals(createRepresentationInput.representationName())) {
                Optional<Object> optionalTarget = this.objectService.getObject(editingContext, createRepresentationInput.objectId());

                if (this.shouldCreateIntermediateStateMachine(optionalTarget)) {
                    StateMachine newStateMachine = this.createIntermediateStateMachine(optionalTarget.get());
                    changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input));
                    String stateMachineIdExplorer = this.objectService.getId(newStateMachine);
                    result = new CreateRepresentationInput(createRepresentationInput.id(), createRepresentationInput.editingContextId(),
                            createRepresentationInput.representationDescriptionId(), stateMachineIdExplorer,
                            createRepresentationInput.representationName());
                } else if (optionalTarget.get() instanceof State state) {
                    ElementDefaultNameProvider elementDefaultNameProvider = new ElementDefaultNameProvider();
                    Region region = state.createRegion("MyRegion");
                    region.setName(elementDefaultNameProvider.getDefaultName(region, region));

                    changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input));
                    String stateIdExplorer = this.objectService.getId(state);
                    result = new CreateRepresentationInput(createRepresentationInput.id(), createRepresentationInput.editingContextId(),
                            createRepresentationInput.representationDescriptionId(), stateIdExplorer,
                            createRepresentationInput.representationName());
                }
            }
        }

        return result;
    }

    /**
     * Check if an intermediate {@link StateMachine} should be created before StateMachine diagram creation.
     *
     * @param target
     *            the context used initially to launch StateMachine diagram creation
     *
     * @return {@code true} if an intermediate {@link StateMachine} should be created, {@code false} otherwise
     */
    private boolean shouldCreateIntermediateStateMachine(Optional<Object> target) {
        if (target.isPresent()) {
            Object parent = target.get();
            return parent instanceof Package || (parent instanceof BehavioredClassifier && !(parent instanceof StateMachine)) || parent instanceof Interface;
        }
        return false;
    }

    /**
     * Create an intermediate {@link StateMachine} on the given {@code target}.
     *
     * @param target
     *            the parent of the new {@link StateMachine}
     * @return an intermediate {@link StateMachine} on the given {@code target}
     */
    private StateMachine createIntermediateStateMachine(Object target) {
        StateMachine newStateMachine = UMLFactory.eINSTANCE.createStateMachine();
        ElementDefaultNameProvider elementDefaultNameProvider = new ElementDefaultNameProvider();
        if (target instanceof Package pack) {
            newStateMachine.setName(elementDefaultNameProvider.getDefaultName(newStateMachine, pack));
            pack.getPackagedElements().add(newStateMachine);
        } else if (target instanceof BehavioredClassifier behavioredClassifier) {
            newStateMachine.setName(elementDefaultNameProvider.getDefaultName(newStateMachine, behavioredClassifier));
            behavioredClassifier.getOwnedBehaviors().add(newStateMachine);
            behavioredClassifier.setClassifierBehavior(newStateMachine);
        } else if (target instanceof Interface inter) {
            ProtocolStateMachine protocolStateMachine = UMLFactory.eINSTANCE.createProtocolStateMachine();
            protocolStateMachine.setName(elementDefaultNameProvider.getDefaultName(protocolStateMachine, inter) + "Protocol");
            inter.setProtocol(protocolStateMachine);
            newStateMachine = protocolStateMachine;
        }
        return newStateMachine;
    }

}