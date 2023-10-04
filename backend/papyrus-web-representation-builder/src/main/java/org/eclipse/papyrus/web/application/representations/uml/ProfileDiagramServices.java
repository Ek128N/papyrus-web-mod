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
 * Services available for the "Profile" Diagram.
 * 
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class ProfileDiagramServices extends Services {

    /**
     * The name of the service that retrieves Metaclass candidates.
     */
    public static final String GET_METACLASS_CANDIDATES = "getMetaclassPRD";

    /**
     * The name of the service that check is the root model is a Profile model.
     */
    public static final String IS_PROFILE_MODEL = "isProfileModel";

}
