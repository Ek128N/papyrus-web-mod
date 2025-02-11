/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.papyrus.web.application.uml;

import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.eclipse.papyrus.uml.domain.services.destroy.DestroyerStatus;
import org.eclipse.papyrus.uml.domain.services.destroy.ElementDestroyer;
import org.eclipse.papyrus.uml.domain.services.destroy.IDestroyer;
import org.eclipse.papyrus.uml.domain.services.properties.ILogger;
import org.eclipse.papyrus.uml.domain.services.status.State;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditServiceDelegate;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.DefaultEditService;
import org.eclipse.sirius.components.emf.services.ISuggestedRootObjectTypesProvider;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.eclipse.sirius.components.emf.services.messages.IEMFMessageService;
import org.eclipse.uml2.uml.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * This class allows to override {@link IEditService} behavior.
 *
 * @author Laurent Fasani
 */
@Service
public class UMLEditServiceDelegate extends DefaultEditService implements IEditServiceDelegate {
    private static final String ITEM_SEP = ",";

    private static final Logger LOGGER = LoggerFactory.getLogger(UMLEditServiceDelegate.class);

    private final ILogger logger;

    private final IEditableChecker editableChecker;

    @SuppressWarnings("checkstyle:ParameterNumber")
    public UMLEditServiceDelegate(IEMFKindService emfKindService, ComposedAdapterFactory composedAdapterFactory, Optional<ISuggestedRootObjectTypesProvider> optionalSuggestedRootObjectsProvider,
            IObjectService objectService, IFeedbackMessageService feedbackMessageService, IEMFMessageService messageService, ILogger logger, IEditableChecker editableChecker) {
        super(emfKindService, composedAdapterFactory, optionalSuggestedRootObjectsProvider, objectService, feedbackMessageService, messageService);
        this.logger = logger;
        this.editableChecker = editableChecker;
    }

    @Override
    public boolean canHandle(Object object) {
        return object instanceof Element;
    }

    @Override
    public boolean canHandle(IEditingContext editingContext) {
        return true;
    }

    @Override
    public void delete(Object semanticElement) {
        if (semanticElement instanceof EObject semanticEObject) {
            ECrossReferenceAdapter adapter = this.getECrossReferenceAdapter(semanticEObject);
            DestroyerStatus destroyerStatus = this.buildDestroyer(adapter).destroy(semanticEObject);

            if (State.FAILED.equals(destroyerStatus.getState())) {
                String elements = destroyerStatus.getElements().stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(ITEM_SEP));
                String errorMessage = destroyerStatus.getMessage() + ": " + elements;
                this.logWarnMessage(errorMessage);
            }
        }
    }

    /**
     * Log a given message in developer logger and user interface.
     *
     * @param message
     *         the message to display
     */
    private void logWarnMessage(String message) {
        LOGGER.warn(message);
        this.logger.log(message, ILogger.ILogLevel.WARNING);
    }

    private ECrossReferenceAdapter getECrossReferenceAdapter(EObject source) {
        return source.eResource().getResourceSet().eAdapters().stream()
                .filter(a -> a instanceof ECrossReferenceAdapter)
                .map(a -> (ECrossReferenceAdapter) a)
                .findFirst().orElse(null);
    }

    private IDestroyer buildDestroyer(ECrossReferenceAdapter adapter) {
        return ElementDestroyer.buildDefault(adapter, this.editableChecker);
    }
}
