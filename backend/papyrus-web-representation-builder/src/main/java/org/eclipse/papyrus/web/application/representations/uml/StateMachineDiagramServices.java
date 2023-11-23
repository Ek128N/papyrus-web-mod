/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
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

/**
 * Services available for the "State Machine" Diagram.
 * 
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class StateMachineDiagramServices extends Services {

    /**
     * The name of the service that retrieves PseudoState height computation expression.
     */
    public static final String COMPUTE_PSEUDO_STATE_HEIGHT = "computePseudoStateHeightExpression";

    /**
     * The name of the service that retrieves PseudoState width computation expression.
     */
    public static final String COMPUTE_PSEUDO_STATE_WITDTH = "computePseudoStateWidthExpression";

    /**
     * The name of the service that create PseudoState.
     */
    public static final String CREATE_PSEUDO_STATE = "createPseudoState";

}
