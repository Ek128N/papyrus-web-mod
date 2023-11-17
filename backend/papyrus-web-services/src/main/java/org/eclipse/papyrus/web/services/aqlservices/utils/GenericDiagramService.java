/*****************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.services.aqlservices.utils;

import org.eclipse.papyrus.uml.domain.services.IEditableChecker;
import org.eclipse.papyrus.web.services.aqlservices.AbstractDiagramService;
import org.eclipse.papyrus.web.services.aqlservices.ServiceLogger;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramNavigationService;
import org.eclipse.papyrus.web.sirius.contributions.IDiagramOperationsService;
import org.eclipse.papyrus.web.sirius.contributions.IViewDiagramDescriptionService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.springframework.stereotype.Service;

/**
 * Generic service used to provide generic service for a diagram with.
 *
 * @author Arthur Daussy
 */
@Service
public class GenericDiagramService extends AbstractDiagramService {

    public GenericDiagramService(IObjectService objectService, IDiagramNavigationService diagramNavigationService, IDiagramOperationsService diagramOperationsService, IEditableChecker editableChecker,
            IViewDiagramDescriptionService viewDiagramService, ServiceLogger logger) {
        super(objectService, diagramNavigationService, diagramOperationsService, editableChecker, viewDiagramService, logger);
    }

}
