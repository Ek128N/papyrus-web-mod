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
package org.eclipse.papyrus.web.application.tools.activity.utils;

import org.eclipse.papyrus.web.application.tools.utils.ToolSections;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.ActivityGroup;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.CreateObjectAction;
import org.eclipse.uml2.uml.ExecutableNode;
import org.eclipse.uml2.uml.ExpansionRegion;
import org.eclipse.uml2.uml.InvocationAction;
import org.eclipse.uml2.uml.Pin;
import org.eclipse.uml2.uml.StructuralFeature;
import org.eclipse.uml2.uml.StructuredActivityNode;

/**
 * The tool sections for the Activity Diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class ADToolSections extends ToolSections {

    /**
     * The tool section to create {@link ActivityGroup}s.
     */
    public static final String ACTIVITY_GROUP = "Activity Group";

    /**
     * The tool section to create {@link ActivityNode}s.
     */
    public static final String ACTIVITY_NODE = "Activity Node";

    /**
     * The tool section to create {@link ExpansionRegion}s.
     */
    public static final String EXPANSION_REGION = "Expansion Region";

    /**
     * The tool section to create {@link InvocationAction}s.
     */
    public static final String INVOCATION_ACTION = "Invocation Action";

    /**
     * The tool section to create {@link CreateObjectAction}s.
     */
    public static final String CREATE_OBJECT_ACTION = "Create Object Action";

    /**
     * The tool section to create {@link StructuredActivityNode}s.
     */
    public static final String STRUCTURED_ACTIVITY_NODE = "Structured Activity Node";

    /**
     * The tool section to create {@link StructuralFeature}s.
     */
    public static final String STRUCTURAL_FEATURE = "Structural Feature";

    /**
     * The tool section to create {@link ExecutableNode}s.
     */
    public static final String EXECUTABLE_NODE = "Executable Node";

    /**
     * The tool section to create {@link AcceptEventAction}s.
     */
    public static final String ACCEPT_EVENT_ACTION = "Accept Event Action";

    /**
     * The tool section to create {@link Pin}s.
     */
    public static final String PIN = "Pin";

}
