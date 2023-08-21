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
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.CommunicationPath;
import org.eclipse.uml2.uml.DeploymentSpecification;
import org.eclipse.uml2.uml.Device;
import org.eclipse.uml2.uml.ExecutionEnvironment;
import org.eclipse.uml2.uml.Manifestation;

/**
 * Services available for the "Deployment" Diagram.
 * 
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class DeploymentDiagramServices extends Services {

    /**
     * The name of the service that creates an {@link Artifact}.
     */
    public static final String CREATE_ARTIFACT = "createArtifactDD";

    /**
     * The name of the service that creates a {@link Manifestation}.
     */
    public static final String CREATE_MANIFESTATION = "createManifestationDD";

    /**
     * The name of the service that creates a {@link Node}.
     */
    public static final String CREATE_NODE = "createNodeDD";

    /**
     * The name of the service that retrieves {@link Artifact} candidates.
     */
    public static final String GET_ARTIFACT_NODE_CANDIDATES = "getArtifactCandidatesDD";

    /**
     * The name of the service that retrieves {@link CommunicationPath} candidates.
     */
    public static final String GET_COMMUNICATION_PATH_EDGE_CANDIDATES = "getCommunicationPathCandidatesDD";

    /**
     * The name of the service that retrieves {@link DeploymentSpecification} candidates.
     */
    public static final String GET_DEPLOYMENT_SPECIFICATION_NODE_CANDIDATES = "getDeploymentSpecificationCandidatesDD";

    /**
     * The name of the service that retrieves {@link Device} candidates.
     */
    public static final String GET_DEVICE_NODE_CANDIDATES = "getDeviceCandidatesDD";

    /**
     * The name of the service that retrieves {@link ExecutionEnvironment} candidates.
     */
    public static final String GET_EXECUTION_ENVIRONMENT_NODE_CANDIDATES = "getExecutionEnvironmentCandidatesDD";

    /**
     * The name of the service that retrieves {@link Node} candidates.
     */
    public static final String GET_NODE_NODE_CANDIDATES = "getNodeCandidatesDD";

}
