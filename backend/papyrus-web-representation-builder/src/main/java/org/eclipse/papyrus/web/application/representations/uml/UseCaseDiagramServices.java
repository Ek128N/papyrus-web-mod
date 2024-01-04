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
package org.eclipse.papyrus.web.application.representations.uml;

import org.eclipse.papyrus.web.application.representations.view.aql.Services;
import org.eclipse.uml2.uml.UseCase;

/**
 * Services available for the "Use Case" Diagram.
 * 
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class UseCaseDiagramServices extends Services {

    /**
     * The name of the service that creates a {@link UseCase}.
     */
    public static final String CREATE_USECASE = "createUseCaseUCD";

    /**
     * The name of the service that retrieves {@link UseCase} candidates.
     */
    public static final String GET_USECASE_NODE_CANDIDATES = "getUseCaseCandidatesUCD";
}
