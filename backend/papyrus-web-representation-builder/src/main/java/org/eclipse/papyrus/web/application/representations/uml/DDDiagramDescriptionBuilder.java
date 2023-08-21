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

import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.CONVERTED_NODES;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.DIAGRAM_CONTEXT;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.SELECTED_NODE;

import java.util.List;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.web.application.representations.view.aql.CallQuery;
import org.eclipse.papyrus.web.application.representations.view.aql.Services;
import org.eclipse.papyrus.web.application.representations.view.builders.CallbackAdapter;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DropNodeTool;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeStyle;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.CommunicationPath;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Deployment;
import org.eclipse.uml2.uml.DeploymentSpecification;
import org.eclipse.uml2.uml.Device;
import org.eclipse.uml2.uml.ExecutionEnvironment;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Manifestation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Builder of the "Deployment" diagram representation.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public final class DDDiagramDescriptionBuilder extends AbstractRepresentationDescriptionBuilder {

    /**
     * The name of the representation handled by this builder.
     */
    public static final String DD_REP_NAME = "Deployment Diagram";

    /**
     * The prefix of the representation handled by this builder.
     */
    public static final String DD_PREFIX = "DD_";

    /**
     * Factory used to create UML elements.
     */
    private final UMLPackage umlPackage = UMLPackage.eINSTANCE;

    /**
     * The <i>shared</i> {@link NodeDescription} for the diagram.
     */
    private NodeDescription ddSharedDescription;

    public DDDiagramDescriptionBuilder() {
        super(DD_PREFIX, DD_REP_NAME, UMLPackage.eINSTANCE.getPackage());
    }

    @Override
    protected void fillDescription(DiagramDescription diagramDescription) {

        // create diagram tool sections
        this.createDefaultToolSectionInDiagramDescription(diagramDescription);
        diagramDescription.setPreconditionExpression(CallQuery.queryServiceOnSelf(Services.IS_NOT_PROFILE_MODEL));

        // create node descriptions with their tools
        this.createDiagramArtifactDescription(diagramDescription);
        this.createDiagramCommentDescription(diagramDescription, NODES);
        this.createDiagramConstraintDescription(diagramDescription, NODES);
        this.createDiagramDeploymentSpecificationDescription(diagramDescription);
        this.createDiagramDeviceDescription(diagramDescription);
        this.createDiagramExecutionEnvironmentDescription(diagramDescription);
        this.createDiagramModelDescription(diagramDescription);
        this.createDiagramNodeDescription(diagramDescription);
        this.createDiagramPackageDescription(diagramDescription);

        // create shared node descriptions with their tools
        this.ddSharedDescription = this.createSharedDescription(diagramDescription);
        this.createSharedArtifactDescription(diagramDescription);
        this.createCommentDescriptionInNodeDescription(diagramDescription, this.ddSharedDescription, NODES,
                this.getIdBuilder().getSpecializedDomainNodeName(this.umlPackage.getComment(), SHARED_SUFFIX), List.of(this.umlPackage.getPackage()));
        this.createConstraintDescriptionInNodeDescription(diagramDescription, this.ddSharedDescription, NODES,
                this.getIdBuilder().getSpecializedDomainNodeName(this.umlPackage.getConstraint(), SHARED_SUFFIX), List.of(this.umlPackage.getPackage()));
        this.createSharedDeploymentSpecificationDescription(diagramDescription);
        this.createSharedDeviceDescription(diagramDescription);
        this.createSharedExecutionEnvironmentDescription(diagramDescription);
        this.createSharedModelDescription(diagramDescription);
        this.createSharedNodeDescription(diagramDescription);
        this.createSharedPackageDescription(diagramDescription);

        // edge descriptions
        this.createCommunicationPathDescription(diagramDescription);
        this.createDependencyDescription(diagramDescription);
        this.createDeploymentDescription(diagramDescription);
        this.createGeneralizationDescription(diagramDescription);
        this.createManifestationDescription(diagramDescription);

        diagramDescription.getPalette().setDropTool(this.getViewBuilder().createGenericSemanticDropTool(this.getIdBuilder().getDiagramSemanticDropToolName()));

        // Add dropped tool on diagram
        DropNodeTool ddGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getDiagramGraphicalDropToolName());
        List<EClass> children = List.of(this.umlPackage.getArtifact(), this.umlPackage.getComment(), this.umlPackage.getConstraint(), this.umlPackage.getNode(), this.umlPackage.getPackage());
        this.registerCallback(diagramDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            ddGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        diagramDescription.getPalette().setDropNodeTool(ddGraphicalDropTool);
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Artifact} on the Diagram.
     *
     * @param diagramDescription
     *            the Deployment {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramArtifactDescription(DiagramDescription diagramDescription) {
        EClass artifactEClass = this.umlPackage.getArtifact();
        NodeDescription ddDiagramArtifactDescription = this.newNodeBuilder(artifactEClass, this.getViewBuilder().createRectangularNodeStyle(true, true))//
                .name(this.getIdBuilder().getDomainNodeName(artifactEClass)) //
                .semanticCandidateExpression(this.getQueryBuilder().queryAllReachableExactType(artifactEClass))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(artifactEClass.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(artifactEClass.getName())) //
                .build();
        diagramDescription.getNodeDescriptions().add(ddDiagramArtifactDescription);

        this.createDefaultToolSectionsInNodeDescription(ddDiagramArtifactDescription);

        // create tool
        NodeTool ddDiagramArtifactCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), artifactEClass);
        this.addDiagramToolInToolSection(diagramDescription, ddDiagramArtifactCreationTool, NODES);

        // Add dropped tool on Diagram Artifact container
        DropNodeTool ddDiagramArtifactGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(ddDiagramArtifactDescription));
        List<EClass> children = List.of(this.umlPackage.getArtifact());
        this.registerCallback(ddDiagramArtifactDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            ddDiagramArtifactGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        ddDiagramArtifactDescription.getPalette().setDropNodeTool(ddDiagramArtifactGraphicalDropTool);
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link DeploymentSpecification} on the Diagram.
     *
     * @param diagramDescription
     *            the Deployment {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramDeploymentSpecificationDescription(DiagramDescription diagramDescription) {
        EClass deploymentSpecificationEClass = this.umlPackage.getDeploymentSpecification();
        NodeDescription ddDiagramDeploymentSpecificationDescription = this.newNodeBuilder(deploymentSpecificationEClass, this.getViewBuilder().createRectangularNodeStyle(true, false))//
                .name(this.getIdBuilder().getDomainNodeName(deploymentSpecificationEClass)) //
                .semanticCandidateExpression(this.getQueryBuilder().queryAllReachableExactType(deploymentSpecificationEClass))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(deploymentSpecificationEClass.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(deploymentSpecificationEClass.getName())) //
                .build();
        diagramDescription.getNodeDescriptions().add(ddDiagramDeploymentSpecificationDescription);

        this.createDefaultToolSectionsInNodeDescription(ddDiagramDeploymentSpecificationDescription);

        // create tool
        NodeTool ddDiagramDeploymentSpecificationCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), deploymentSpecificationEClass);
        this.addDiagramToolInToolSection(diagramDescription, ddDiagramDeploymentSpecificationCreationTool, NODES);

        // No graphical Drag&Drop tool on DeploymentSpecification node
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Device} on the Diagram.
     *
     * @param diagramDescription
     *            the Deployment {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramDeviceDescription(DiagramDescription diagramDescription) {
        NodeDescription ddDiagramDeviceDescription = this.createDiagram3DBoxDescription(diagramDescription, this.umlPackage.getDevice());

        // Add dropped tool on Diagram Device container
        DropNodeTool ddDiagramDeviceGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(ddDiagramDeviceDescription));
        List<EClass> children = List.of(this.umlPackage.getDeploymentSpecification(), this.umlPackage.getNode());
        this.registerCallback(ddDiagramDeviceDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            ddDiagramDeviceGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        ddDiagramDeviceDescription.getPalette().setDropNodeTool(ddDiagramDeviceGraphicalDropTool);
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link ExecutionEnvironment} on the Diagram.
     *
     * @param diagramDescription
     *            the Deployment {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramExecutionEnvironmentDescription(DiagramDescription diagramDescription) {
        NodeDescription ddDiagramExecutionEnvironmentDescription = this.createDiagram3DBoxDescription(diagramDescription, this.umlPackage.getExecutionEnvironment());

        // Add dropped tool on Diagram ExecutionEnvironment container
        DropNodeTool ddDiagramExecutionEnvironmentGraphicalDropTool = this.getViewBuilder()
                .createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(ddDiagramExecutionEnvironmentDescription));
        List<EClass> children = List.of(this.umlPackage.getArtifact(), this.umlPackage.getExecutionEnvironment());
        this.registerCallback(ddDiagramExecutionEnvironmentDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            ddDiagramExecutionEnvironmentGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        ddDiagramExecutionEnvironmentDescription.getPalette().setDropNodeTool(ddDiagramExecutionEnvironmentGraphicalDropTool);
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Model} on the Diagram.
     *
     * @param diagramDescription
     *            the Deployment {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramModelDescription(DiagramDescription diagramDescription) {
        EClass modelEClass = this.umlPackage.getModel();
        NodeDescription ddDiagramModelDescription = this.getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(modelEClass, this.getQueryBuilder().queryAllReachableExactType(modelEClass));
        diagramDescription.getNodeDescriptions().add(ddDiagramModelDescription);

        this.createDefaultToolSectionsInNodeDescription(ddDiagramModelDescription);

        NodeTool ddDiagramModelCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), modelEClass);
        this.addDiagramToolInToolSection(diagramDescription, ddDiagramModelCreationTool, NODES);

        // Add dropped tool on Diagram Model container
        DropNodeTool ddDiagramModelGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(ddDiagramModelDescription));
        List<EClass> children = List.of(this.umlPackage.getArtifact(), this.umlPackage.getComment(), this.umlPackage.getConstraint(), this.umlPackage.getNode(), this.umlPackage.getPackage());
        this.registerCallback(ddDiagramModelDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            ddDiagramModelGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        ddDiagramModelDescription.getPalette().setDropNodeTool(ddDiagramModelGraphicalDropTool);
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Node} on the Diagram.
     *
     * @param diagramDescription
     *            the Deployment {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramNodeDescription(DiagramDescription diagramDescription) {
        NodeDescription ddDiagramNodeDescription = this.createDiagram3DBoxDescription(diagramDescription, this.umlPackage.getNode());

        // Add dropped tool on Diagram Node container
        DropNodeTool ddDiagramNodeGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(ddDiagramNodeDescription));
        List<EClass> children = List.of(this.umlPackage.getArtifact(), this.umlPackage.getNode());
        this.registerCallback(ddDiagramNodeDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            ddDiagramNodeGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        ddDiagramNodeDescription.getPalette().setDropNodeTool(ddDiagramNodeGraphicalDropTool);

    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Package} on the Diagram.
     *
     * @param diagramDescription
     *            the Deployment {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createDiagramPackageDescription(DiagramDescription diagramDescription) {
        EClass packageEClass = this.umlPackage.getPackage();
        NodeDescription ddDiagramPackageDescription = this.getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(packageEClass,
                this.getQueryBuilder().queryAllReachableExactType(this.umlPackage.getPackage()));
        diagramDescription.getNodeDescriptions().add(ddDiagramPackageDescription);

        ddDiagramPackageDescription.setStyle(this.getViewBuilder().createPackageNodeStyle());

        // create Package tool sections
        this.createDefaultToolSectionsInNodeDescription(ddDiagramPackageDescription);

        NodeTool ddDiagramPackageCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), packageEClass);
        this.addDiagramToolInToolSection(diagramDescription, ddDiagramPackageCreationTool, NODES);

        // Add dropped tool on Diagram Package container
        DropNodeTool ddDiagramPackageGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(ddDiagramPackageDescription));
        List<EClass> children = List.of(this.umlPackage.getArtifact(), this.umlPackage.getComment(), this.umlPackage.getConstraint(), this.umlPackage.getNode(), this.umlPackage.getPackage());
        this.registerCallback(ddDiagramPackageDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            ddDiagramPackageGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        ddDiagramPackageDescription.getPalette().setDropNodeTool(ddDiagramPackageGraphicalDropTool);
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Classifier} on Diagram with 3D box node.
     *
     * @param diagramDescription
     *            the Deployment {@link DiagramDescription} containing the created {@link NodeDescription}
     * @param type
     *            type of the {@link Classifier} to create
     * @return the created {@link} NodeDescription.
     */
    private NodeDescription createDiagram3DBoxDescription(DiagramDescription diagramDescription, EClass type) {
        NodeDescription ddClassifierDescription = this.newNodeBuilder(type, this.getViewBuilder().createRectangularNodeStyle(true, true))//
                .name(this.getIdBuilder().getDomainNodeName(type)) //
                .semanticCandidateExpression(this.getQueryBuilder().queryAllReachableExactType(type))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(type.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(type.getName())) //
                .build();
        diagramDescription.getNodeDescriptions().add(ddClassifierDescription);

        this.createDefaultToolSectionsInNodeDescription(ddClassifierDescription);

        // create tools
        NodeTool ddClassifierCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), type);
        this.addDiagramToolInToolSection(diagramDescription, ddClassifierCreationTool, NODES);

        return ddClassifierDescription;
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link Artifact}.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     *
     * @param diagramDescription
     *            the Deployment {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedArtifactDescription(DiagramDescription diagramDescription) {
        EClass artifactEClass = this.umlPackage.getArtifact();
        NodeDescription ddSharedArtifactDescription = this.newNodeBuilder(artifactEClass, this.getViewBuilder().createRectangularNodeStyle(true, true))//
                .name(this.getIdBuilder().getSpecializedDomainNodeName(artifactEClass, SHARED_SUFFIX)) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(DeploymentDiagramServices.GET_ARTIFACT_NODE_CANDIDATES))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(artifactEClass.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(artifactEClass.getName())) //
                .build();
        this.ddSharedDescription.getChildrenDescriptions().add(ddSharedArtifactDescription);

        this.createDefaultToolSectionsInNodeDescription(ddSharedArtifactDescription);

        NodeTool ddSharedArtifactCreationTool = this.getViewBuilder().createCreationTool(this.getIdBuilder().getCreationToolId(artifactEClass), DeploymentDiagramServices.CREATE_ARTIFACT,
                List.of(this.getQueryBuilder().aqlString(artifactEClass.getName()), SELECTED_NODE, DIAGRAM_CONTEXT, CONVERTED_NODES));
        List<EClass> owners = List.of(this.umlPackage.getArtifact(), //
                this.umlPackage.getNode(), //
                this.umlPackage.getPackage());
        this.reuseNodeAndCreateTool(ddSharedArtifactDescription, diagramDescription, ddSharedArtifactCreationTool, NODES, owners,
                List.of(this.umlPackage.getDevice(), this.umlPackage.getDeploymentSpecification()));

        // Add dropped tool on Shared Artifact container
        DropNodeTool ddSharedArtifactGraphicalDropTool = this.getViewBuilder()
                .createGraphicalDropTool(this.getIdBuilder().getSpecializedNodeGraphicalDropToolName(ddSharedArtifactDescription, SHARED_SUFFIX));
        List<EClass> children = List.of(this.umlPackage.getArtifact());
        this.registerCallback(ddSharedArtifactDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            ddSharedArtifactGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        ddSharedArtifactDescription.getPalette().setDropNodeTool(ddSharedArtifactGraphicalDropTool);

    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link DeploymentSpecification}.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     *
     * @param diagramDescription
     *            the Deployment {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedDeploymentSpecificationDescription(DiagramDescription diagramDescription) {
        EClass deploymentSepecificationEClass = this.umlPackage.getDeploymentSpecification();
        NodeDescription ddSharedDeploymentSpecificationDescription = this.newNodeBuilder(deploymentSepecificationEClass, this.getViewBuilder().createRectangularNodeStyle(true, false))//
                .name(this.getIdBuilder().getSpecializedDomainNodeName(deploymentSepecificationEClass, SHARED_SUFFIX)) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(DeploymentDiagramServices.GET_DEPLOYMENT_SPECIFICATION_NODE_CANDIDATES))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(deploymentSepecificationEClass.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(deploymentSepecificationEClass.getName())) //
                .build();
        this.ddSharedDescription.getChildrenDescriptions().add(ddSharedDeploymentSpecificationDescription);

        this.createDefaultToolSectionsInNodeDescription(ddSharedDeploymentSpecificationDescription);

        // create tools
        NodeTool ddSharedDeploymentSpecificationCreationTool = this.getViewBuilder().createCreationTool(this.getIdBuilder().getCreationToolId(deploymentSepecificationEClass),
                DeploymentDiagramServices.CREATE_ARTIFACT,
                List.of(this.getQueryBuilder().aqlString(deploymentSepecificationEClass.getName()), SELECTED_NODE, DIAGRAM_CONTEXT, CONVERTED_NODES));
        List<EClass> owners = List.of(this.umlPackage.getArtifact(), //
                this.umlPackage.getNode(), //
                this.umlPackage.getPackage());
        this.reuseNodeAndCreateTool(ddSharedDeploymentSpecificationDescription, diagramDescription, ddSharedDeploymentSpecificationCreationTool, NODES, owners,
                List.of(this.umlPackage.getDeploymentSpecification()));

        // No Graphical Drag&Drop tool
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link Device}.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     *
     * @param diagramDescription
     *            the Deployment {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedDeviceDescription(DiagramDescription diagramDescription) {
        EClass deviceEClass = this.umlPackage.getDevice();
        NodeDescription ddSharedDeviceDescription = this.newNodeBuilder(deviceEClass, this.getViewBuilder().createRectangularNodeStyle(true, true))//
                .name(this.getIdBuilder().getSpecializedDomainNodeName(deviceEClass, SHARED_SUFFIX)) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(DeploymentDiagramServices.GET_DEVICE_NODE_CANDIDATES))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(deviceEClass.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(deviceEClass.getName())) //
                .build();
        this.ddSharedDescription.getChildrenDescriptions().add(ddSharedDeviceDescription);

        this.createDefaultToolSectionsInNodeDescription(ddSharedDeviceDescription);

        // create tools
        NodeTool ddSharedDeviceCreationTool = this.getViewBuilder().createCreationTool(this.getIdBuilder().getCreationToolId(deviceEClass), DeploymentDiagramServices.CREATE_NODE,
                List.of(this.getQueryBuilder().aqlString(deviceEClass.getName()), SELECTED_NODE, DIAGRAM_CONTEXT, CONVERTED_NODES));
        List<EClass> owners = List.of(this.umlPackage.getNode(), //
                this.umlPackage.getPackage());
        this.reuseNodeAndCreateTool(ddSharedDeviceDescription, diagramDescription, ddSharedDeviceCreationTool, NODES, owners, List.of(this.umlPackage.getExecutionEnvironment()));

        // Add dropped tool on Shared Device container
        DropNodeTool ddSharedDeviceGraphicalDropTool = this.getViewBuilder()
                .createGraphicalDropTool(this.getIdBuilder().getSpecializedNodeGraphicalDropToolName(ddSharedDeviceDescription, SHARED_SUFFIX));
        List<EClass> children = List.of(this.umlPackage.getDeploymentSpecification(), this.umlPackage.getNode());
        this.registerCallback(ddSharedDeviceDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            ddSharedDeviceGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        ddSharedDeviceDescription.getPalette().setDropNodeTool(ddSharedDeviceGraphicalDropTool);

    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link ExecutionEnvironment}.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     *
     * @param diagramDescription
     *            the Deployment {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedExecutionEnvironmentDescription(DiagramDescription diagramDescription) {
        EClass executionEnvironmentEClass = this.umlPackage.getExecutionEnvironment();
        NodeDescription ddSharedExecutionEnvironmentDescription = this.newNodeBuilder(executionEnvironmentEClass, this.getViewBuilder().createRectangularNodeStyle(true, true))//
                .name(this.getIdBuilder().getSpecializedDomainNodeName(executionEnvironmentEClass, SHARED_SUFFIX)) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(DeploymentDiagramServices.GET_EXECUTION_ENVIRONMENT_NODE_CANDIDATES))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(executionEnvironmentEClass.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(executionEnvironmentEClass.getName())) //
                .build();
        this.ddSharedDescription.getChildrenDescriptions().add(ddSharedExecutionEnvironmentDescription);

        this.createDefaultToolSectionsInNodeDescription(ddSharedExecutionEnvironmentDescription);

        // create tools
        NodeTool ddSharedExecutionEnvironmentCreationTool = this.getViewBuilder().createCreationTool(this.getIdBuilder().getCreationToolId(executionEnvironmentEClass),
                DeploymentDiagramServices.CREATE_NODE,
                List.of(this.getQueryBuilder().aqlString(executionEnvironmentEClass.getName()), SELECTED_NODE, DIAGRAM_CONTEXT, CONVERTED_NODES));
        List<EClass> owners = List.of(this.umlPackage.getNode(), //
                this.umlPackage.getPackage());
        this.reuseNodeAndCreateTool(ddSharedExecutionEnvironmentDescription, diagramDescription, ddSharedExecutionEnvironmentCreationTool, NODES, owners, List.of());

        // Add dropped tool on Shared ExecutionEnvironment container
        DropNodeTool ddSharedExecutionEnvironmentGraphicalDropTool = this.getViewBuilder()
                .createGraphicalDropTool(this.getIdBuilder().getSpecializedNodeGraphicalDropToolName(ddSharedExecutionEnvironmentDescription, SHARED_SUFFIX));
        List<EClass> children = List.of(this.umlPackage.getArtifact(), this.umlPackage.getExecutionEnvironment());
        this.registerCallback(ddSharedExecutionEnvironmentDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            ddSharedExecutionEnvironmentGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        ddSharedExecutionEnvironmentDescription.getPalette().setDropNodeTool(ddSharedExecutionEnvironmentGraphicalDropTool);
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link Node}.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     *
     * @param diagramDescription
     *            the Deployment {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedNodeDescription(DiagramDescription diagramDescription) {
        EClass nodeEClass = this.umlPackage.getNode();
        NodeDescription ddSharedNodeDescription = this.newNodeBuilder(nodeEClass, this.getViewBuilder().createRectangularNodeStyle(true, true))//
                .name(this.getIdBuilder().getSpecializedDomainNodeName(nodeEClass, SHARED_SUFFIX)) //
                .semanticCandidateExpression(CallQuery.queryServiceOnSelf(DeploymentDiagramServices.GET_NODE_NODE_CANDIDATES))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(nodeEClass.getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(nodeEClass.getName())) //
                .build();
        this.ddSharedDescription.getChildrenDescriptions().add(ddSharedNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(ddSharedNodeDescription);

        // create tools
        NodeTool ddSharedNodeCreationTool = this.getViewBuilder().createCreationTool(this.getIdBuilder().getCreationToolId(nodeEClass), DeploymentDiagramServices.CREATE_NODE,
                List.of(this.getQueryBuilder().aqlString(nodeEClass.getName()), SELECTED_NODE, DIAGRAM_CONTEXT, CONVERTED_NODES));
        List<EClass> owners = List.of(this.umlPackage.getNode(), //
                this.umlPackage.getPackage());
        this.reuseNodeAndCreateTool(ddSharedNodeDescription, diagramDescription, ddSharedNodeCreationTool, NODES, owners, List.of(this.umlPackage.getExecutionEnvironment()));

        // Add dropped tool on Shared Node container
        DropNodeTool ddSharedNodeGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getSpecializedNodeGraphicalDropToolName(ddSharedNodeDescription, SHARED_SUFFIX));
        List<EClass> children = List.of(this.umlPackage.getArtifact(), this.umlPackage.getNode());
        this.registerCallback(ddSharedNodeDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            ddSharedNodeGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        ddSharedNodeDescription.getPalette().setDropNodeTool(ddSharedNodeGraphicalDropTool);

    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link Package}.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     *
     * @param diagramDescription
     *            the Deployment {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedPackageDescription(DiagramDescription diagramDescription) {
        EClass packageEClass = this.umlPackage.getPackage();
        NodeDescription ddSharedPackageDescription = this.getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(packageEClass,
                CallQuery.queryAttributeOnSelf(this.umlPackage.getPackage_PackagedElement()));
        ddSharedPackageDescription.setName(this.getIdBuilder().getSpecializedDomainNodeName(packageEClass, SHARED_SUFFIX));
        ddSharedPackageDescription.setStyle(this.getViewBuilder().createPackageNodeStyle());

        this.createDefaultToolSectionsInNodeDescription(ddSharedPackageDescription);

        this.ddSharedDescription.getChildrenDescriptions().add(ddSharedPackageDescription);

        NodeTool ddSharedPackageCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), packageEClass);
        List<EClass> owners = List.of(this.umlPackage.getPackage());
        this.reuseNodeAndCreateTool(ddSharedPackageDescription, diagramDescription, ddSharedPackageCreationTool, NODES, owners.toArray(EClass[]::new));

        // Add dropped tool on Shared Package container
        DropNodeTool ddSharedPackageGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(ddSharedPackageDescription));
        List<EClass> children = List.of(this.umlPackage.getArtifact(), this.umlPackage.getComment(), this.umlPackage.getConstraint(), this.umlPackage.getNode(), this.umlPackage.getPackage());
        this.registerCallback(ddSharedPackageDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            ddSharedPackageGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        ddSharedPackageDescription.getPalette().setDropNodeTool(ddSharedPackageGraphicalDropTool);
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link Model}.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     *
     * @param diagramDescription
     *            the Deployment {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createSharedModelDescription(DiagramDescription diagramDescription) {
        EClass modelEClass = this.umlPackage.getModel();
        NodeDescription ddSharedModelDescription = this.getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(modelEClass,
                CallQuery.queryAttributeOnSelf(this.umlPackage.getPackage_PackagedElement()));
        ddSharedModelDescription.setName(this.getIdBuilder().getSpecializedDomainNodeName(modelEClass, SHARED_SUFFIX));
        ddSharedModelDescription.setStyle(this.getViewBuilder().createPackageNodeStyle());

        this.createDefaultToolSectionsInNodeDescription(ddSharedModelDescription);

        this.ddSharedDescription.getChildrenDescriptions().add(ddSharedModelDescription);

        NodeTool ddSharedModelCreationTool = this.getViewBuilder().createCreationTool(this.umlPackage.getPackage_PackagedElement(), modelEClass);
        List<EClass> owners = List.of(this.umlPackage.getPackage());
        this.reuseNodeAndCreateTool(ddSharedModelDescription, diagramDescription, ddSharedModelCreationTool, NODES, owners.toArray(EClass[]::new));

        // Add dropped tool on Shared Package container
        DropNodeTool ddSharedModelGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(ddSharedModelDescription));
        List<EClass> children = List.of(this.umlPackage.getArtifact(), this.umlPackage.getComment(), this.umlPackage.getConstraint(), this.umlPackage.getNode(), this.umlPackage.getPackage());
        this.registerCallback(ddSharedModelDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilter(diagramDescription, children, List.of());
            ddSharedModelGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        ddSharedModelDescription.getPalette().setDropNodeTool(ddSharedModelGraphicalDropTool);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link CommunicationPath}.
     *
     * @param diagramDescription
     *            the Deployment {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createCommunicationPathDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> nodeSourcesAndTargets = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getNode());
        EClass communicationPath = this.umlPackage.getCommunicationPath();
        EdgeDescription ddCommunicationPathDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(communicationPath,
                this.getQueryBuilder().queryAllReachableExactType(communicationPath), nodeSourcesAndTargets, nodeSourcesAndTargets);

        ddCommunicationPathDescription.setBeginLabelExpression(this.getQueryBuilder().createDomainBaseEdgeSourceLabelExpression());
        ddCommunicationPathDescription.setEndLabelExpression(this.getQueryBuilder().createDomainBaseEdgeTargetLabelExpression());

        ddCommunicationPathDescription.getStyle().setLineStyle(LineStyle.SOLID);
        ddCommunicationPathDescription.getStyle().setTargetArrowStyle(ArrowStyle.NONE);
        EdgeTool ddCommunicationPathCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(ddCommunicationPathDescription, this.umlPackage.getPackage_PackagedElement());
        this.registerCallback(ddCommunicationPathDescription, () -> {
            this.addEdgeToolInEdgesToolSection(nodeSourcesAndTargets.get(), ddCommunicationPathCreationTool);
        });

        diagramDescription.getEdgeDescriptions().add(ddCommunicationPathDescription);

        this.getViewBuilder().addDefaultReconnectionTools(ddCommunicationPathDescription);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Dependency}.
     *
     * @param diagramDescription
     *            the Deployment {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createDependencyDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> namedElementCollector = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getNamedElement());
        EdgeDescription ddDependencyDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.umlPackage.getDependency(),
                this.getQueryBuilder().queryAllReachableExactType(this.umlPackage.getDependency()),
                namedElementCollector, namedElementCollector);
        EdgeStyle style = ddDependencyDescription.getStyle();
        style.setLineStyle(LineStyle.DASH);
        style.setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        diagramDescription.getEdgeDescriptions().add(ddDependencyDescription);
        EdgeTool ddDependencyCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(ddDependencyDescription, this.umlPackage.getPackage_PackagedElement());
        this.registerCallback(ddDependencyDescription, () -> {
            this.addEdgeToolInEdgesToolSection(namedElementCollector.get(), ddDependencyCreationTool);
        });
        this.getViewBuilder().addDefaultReconnectionTools(ddDependencyDescription);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Deployment}.
     *
     * @param diagramDescription
     *            the Deployment {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createDeploymentDescription(DiagramDescription diagramDescription) {

        Supplier<List<NodeDescription>> sourceDeployedArtifact = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getDeployedArtifact());
        Supplier<List<NodeDescription>> targetDeploymentTarget = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getDeploymentTarget());

        EClass deployment = this.umlPackage.getDeployment();
        EdgeDescription ddDeploymentDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(deployment, this.getQueryBuilder().queryAllReachableExactType(deployment),
                sourceDeployedArtifact, targetDeploymentTarget);
        ddDeploymentDescription.getStyle().setLineStyle(LineStyle.DASH);
        ddDeploymentDescription.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        EdgeTool dddDeploymentCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(ddDeploymentDescription, this.umlPackage.getDeploymentTarget_Deployment());
        this.registerCallback(ddDeploymentDescription, () -> {
            this.addEdgeToolInEdgesToolSection(sourceDeployedArtifact.get(), dddDeploymentCreationTool);
        });

        diagramDescription.getEdgeDescriptions().add(ddDeploymentDescription);

        this.getViewBuilder().addDefaultReconnectionTools(ddDeploymentDescription);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Generalization}.
     *
     * @param diagramDescription
     *            the Deployment {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createGeneralizationDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> sourceAndTargetDescriptionsSupplier = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getClassifier());

        EClass generalization = this.umlPackage.getGeneralization();
        EdgeDescription ddGeneralizationDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(generalization,
                this.getQueryBuilder().queryAllReachableExactType(generalization), sourceAndTargetDescriptionsSupplier, sourceAndTargetDescriptionsSupplier, false);
        ddGeneralizationDescription.getStyle().setLineStyle(LineStyle.SOLID);
        ddGeneralizationDescription.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);
        EdgeTool ddGeneralizationCreationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(ddGeneralizationDescription, this.umlPackage.getClassifier_Generalization());
        this.registerCallback(ddGeneralizationDescription, () -> {
            this.addEdgeToolInEdgesToolSection(sourceAndTargetDescriptionsSupplier.get(), ddGeneralizationCreationTool);
        });

        diagramDescription.getEdgeDescriptions().add(ddGeneralizationDescription);

        this.getViewBuilder().addDefaultReconnectionTools(ddGeneralizationDescription);
    }

    /**
     * Create the {@link EdgeDescription} representing an UML {@link Manifestation}.
     *
     * @param diagramDescription
     *            the Deployment {@link DiagramDescription} containing the created {@link EdgeDescription}
     */
    private void createManifestationDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> packageableELementTargetCollector = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getPackageableElement());
        Supplier<List<NodeDescription>> namedElementSourceCollector = () -> this.collectNodesWithDomain(diagramDescription, this.umlPackage.getNamedElement());

        EClass manifestation = this.umlPackage.getManifestation();
        EdgeDescription ddManifestationDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(manifestation,
                this.getQueryBuilder().queryAllReachableExactType(manifestation), namedElementSourceCollector, packageableELementTargetCollector, true);
        EdgeStyle style = ddManifestationDescription.getStyle();
        style.setLineStyle(LineStyle.DASH);
        style.setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        diagramDescription.getEdgeDescriptions().add(ddManifestationDescription);

        EdgeTool ddManifestationCreationTool = this.getViewBuilder().createDomainBasedEdgeToolWithService("New Manifestation", DeploymentDiagramServices.CREATE_MANIFESTATION);

        ddManifestationDescription.eAdapters().add(new CallbackAdapter(() -> {
            List<NodeDescription> targetNodeDescriptions = ddManifestationDescription.getTargetNodeDescriptions();
            ddManifestationCreationTool.getTargetElementDescriptions().addAll(targetNodeDescriptions);
        }));

        this.registerCallback(ddManifestationDescription, () -> {
            this.addEdgeToolInEdgesToolSection(namedElementSourceCollector.get(), ddManifestationCreationTool);
        });

        diagramDescription.getEdgeDescriptions().add(ddManifestationDescription);

        this.getViewBuilder().addDefaultReconnectionTools(ddManifestationDescription);
    }

}
