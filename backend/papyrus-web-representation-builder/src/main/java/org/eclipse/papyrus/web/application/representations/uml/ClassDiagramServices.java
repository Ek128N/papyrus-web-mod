/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.papyrus.web.application.representations.uml;

import org.eclipse.papyrus.web.application.representations.view.aql.Services;

/**
 * Services available for the Class Diagram.
 * 
 * @author Arthur Daussy
 */
public class ClassDiagramServices extends Services {

    public static final String IS_ASSOCIATION_SOURCE_NAVIGABLE = "isAssociationSourceNavigable";

    public static final String IS_ASSOCIATION_TARGET_NAVIGABLE = "isAssociationTargetNavigable";

    public static final String IS_ASSOCIATION_TARGET_COMPOSITE = "isAssociationTargetComposite";

    public static final String IS_ASSOCIATION_SOURCE_COMPOSITE = "isAssociationSourceComposite";

    public static final String IS_ASSOCIATION_TARGET_SHARED = "isAssociationTargetShared";

    public static final String IS_ASSOCIATION_SOURCE_SHARED = "isAssociationSourceShared";

    public static final String GET_ASSOCIATION_SOURCE = "getAssociationSource";

    public static final String GET_ASSOCIATION_TARGET = "getAssociationTarget";

    public static final String CREATION_COMPOSITE_ASSOCIATION = "createCompositeAssociation";

    public static final String CREATION_SHARED_ASSOCIATION = "createSharedAssociation";

}
