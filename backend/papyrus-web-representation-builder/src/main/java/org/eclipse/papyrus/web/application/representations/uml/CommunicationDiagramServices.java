/*****************************************************************************
 * Copyright (c) 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.representations.uml;

import org.eclipse.papyrus.web.application.representations.view.aql.Services;
import org.eclipse.uml2.uml.DurationObservation;
import org.eclipse.uml2.uml.TimeObservation;

/**
 * Services available for the Communication Diagram.
 * 
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class CommunicationDiagramServices extends Services {

    /**
     * The name of the service that checks if the diagram can be created.
     */
    public static final String CAN_CREATE_DIAGRAM = "canCreateDiagramCOD";

    /**
     * The name of the service that creates a {@link Message}.
     */
    public static final String CREATE_MESSAGE = "createMessageCOD";

    /**
     * The name of the service that retrieves {@link DurationObservation} candidates.
     */
    public static final String GET_DURATION_OBSERVATION_CANDIDATES = "getDurationObservationCandidatesCOD";

    /**
     * The name of the service that retrieves {@link Package} container candidate.
     */
    public static final String GET_PACKAGE_CONTAINER = "getPackageContainerCOD";

    /**
     * The name of the service that retrieves {@link TimeObservation} candidates.
     */
    public static final String GET_TIME_OBSERVATION_CANDIDATES = "getTimeObservationCandidatesCOD";

}
