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
package org.eclipse.papyrus.web.application.tools.deployment;

import java.util.List;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.web.application.representations.uml.DDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.tools.checker.NodeCreationGraphicalChecker;
import org.eclipse.papyrus.web.application.tools.deployment.utils.DDMappingTypes;
import org.eclipse.papyrus.web.application.tools.test.SemanticDropTest;
import org.eclipse.papyrus.web.application.tools.utils.CreationTool;
import org.eclipse.papyrus.web.application.tools.utils.ToolSections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests semantic drop tools in the Deployment Diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class DDSemanticDropTest extends SemanticDropTest {

    private static final EReference PACKAGED_ELEMENT = UML.getPackage_PackagedElement();

    private static final String ARTIFACT_CONTAINER = "ArtifactContainer";

    private static final String DEVICE_CONTAINER = "DeviceContainer";

    private static final String EXECUTION_ENVIRONMENT_CONTAINER = "ExecutionEnvironmentContainer";

    private static final String MODEL_CONTAINER = "ModelContainer";

    private static final String NODE_CONTAINER = "NodeContainer";

    private static final String PACKAGE_CONTAINER = "PackageContainer";

    private static final String DROP_SUFFIX = "Drop";

    public DDSemanticDropTest() {
        super(DEFAULT_DOCUMENT, DDDiagramDescriptionBuilder.DD_REP_NAME, UML.getModel());
    }

    private static Stream<Arguments> dropOnArtifactParameters() {
        return Stream.of(//
                Arguments.of(UML.getArtifact_NestedArtifact(), UML.getArtifact()),
                Arguments.of(UML.getArtifact_NestedArtifact(), UML.getDeploymentSpecification()));
    }

    private static Stream<Arguments> dropOnDeviceParameters() {
        return Stream.of(//
                Arguments.of(UML.getClass_NestedClassifier(), UML.getDeploymentSpecification()),
                Arguments.of(UML.getNode_NestedNode(), UML.getDevice()),
                Arguments.of(UML.getNode_NestedNode(), UML.getExecutionEnvironment()),
                Arguments.of(UML.getNode_NestedNode(), UML.getNode()));
    }

    private static Stream<Arguments> dropOnDiagramAndModelAndPackageParameters() {
        return Stream.of(//
                Arguments.of(PACKAGED_ELEMENT, UML.getArtifact()),
                Arguments.of(UML.getElement_OwnedComment(), UML.getComment()),
                Arguments.of(UML.getNamespace_OwnedRule(), UML.getConstraint()),
                Arguments.of(PACKAGED_ELEMENT, UML.getDeploymentSpecification()),
                Arguments.of(PACKAGED_ELEMENT, UML.getDevice()),
                Arguments.of(PACKAGED_ELEMENT, UML.getExecutionEnvironment()),
                Arguments.of(PACKAGED_ELEMENT, UML.getModel()),
                Arguments.of(PACKAGED_ELEMENT, UML.getNode()),
                Arguments.of(PACKAGED_ELEMENT, UML.getPackage()));
    }

    private static Stream<Arguments> dropOnExecutionEnvironmentParameters() {
        return Stream.of(//
                Arguments.of(UML.getClass_NestedClassifier(), UML.getArtifact()),
                Arguments.of(UML.getClass_NestedClassifier(), UML.getDeploymentSpecification()),
                Arguments.of(UML.getNode_NestedNode(), UML.getExecutionEnvironment()));
    }

    private static Stream<Arguments> dropOnNodeParameters() {
        return Stream.of(//
                Arguments.of(UML.getClass_NestedClassifier(), UML.getArtifact()),
                Arguments.of(UML.getClass_NestedClassifier(), UML.getDeploymentSpecification()),
                Arguments.of(UML.getNode_NestedNode(), UML.getDevice()),
                Arguments.of(UML.getNode_NestedNode(), UML.getExecutionEnvironment()),
                Arguments.of(UML.getNode_NestedNode(), UML.getNode()));
    }

    private static Stream<Arguments> dropCommunicationPathParameters() {
        List<CreationTool> sources = List.of(new CreationTool(ToolSections.NODES, UML.getDevice()));
        List<CreationTool> targets = List.of(
                new CreationTool(ToolSections.NODES, UML.getDevice()),
                new CreationTool(ToolSections.NODES, UML.getExecutionEnvironment()),
                new CreationTool(ToolSections.NODES, UML.getNode()));
        return cartesianProduct(sources, targets);
    }

    private static Stream<Arguments> dropDependencyAndManifestationParameters() {
        List<CreationTool> sources = List.of(new CreationTool(ToolSections.NODES, UML.getArtifact()));
        List<CreationTool> targets = List.of(
                new CreationTool(ToolSections.NODES, UML.getArtifact()),
                new CreationTool(ToolSections.NODES, UML.getConstraint()),
                new CreationTool(ToolSections.NODES, UML.getDeploymentSpecification()),
                new CreationTool(ToolSections.NODES, UML.getDevice()),
                new CreationTool(ToolSections.NODES, UML.getExecutionEnvironment()),
                new CreationTool(ToolSections.NODES, UML.getModel()),
                new CreationTool(ToolSections.NODES, UML.getNode()),
                new CreationTool(ToolSections.NODES, UML.getPackage()));
        return cartesianProduct(sources, targets);
    }

    private static Stream<Arguments> dropDeploymentParameters() {
        List<CreationTool> sources = List.of(
                new CreationTool(ToolSections.NODES, UML.getArtifact()),
                new CreationTool(ToolSections.NODES, UML.getDeploymentSpecification()));
        List<CreationTool> targets = List.of(
                new CreationTool(ToolSections.NODES, UML.getDevice()),
                new CreationTool(ToolSections.NODES, UML.getExecutionEnvironment()),
                new CreationTool(ToolSections.NODES, UML.getNode()));
        return cartesianProduct(sources, targets);
    }

    private static Stream<Arguments> dropGeneralizationParameters() {
        List<CreationTool> sources = List.of(new CreationTool(ToolSections.NODES, UML.getArtifact()));
        List<CreationTool> targets = List.of(
                new CreationTool(ToolSections.NODES, UML.getDeploymentSpecification()),
                new CreationTool(ToolSections.NODES, UML.getDevice()),
                new CreationTool(ToolSections.NODES, UML.getExecutionEnvironment()),
                new CreationTool(ToolSections.NODES, UML.getNode()));
        return cartesianProduct(sources, targets);
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Override
    @AfterEach
    public void tearDown() {
        super.tearDown();
    }

    @ParameterizedTest
    @MethodSource("dropOnDiagramAndModelAndPackageParameters")
    public void testSemanticDropOnDiagram(EReference containmentReference, EClass elementType) {
        EObject elementToDrop = this.createSemanticElement(this.getRootSemanticElement(), containmentReference, elementType, elementType.getName() + DROP_SUFFIX);
        NodeCreationGraphicalChecker graphicalChecker = new NodeCreationGraphicalChecker(this::getDiagram, null, DDMappingTypes.getMappingType(elementType), this.getCapturedNodes());
        this.semanticDropOnDiagram(this.getObjectService().getId(elementToDrop), graphicalChecker);
    }

    @ParameterizedTest
    @MethodSource("dropOnArtifactParameters")
    public void testSemanticDropOnArtifact(EReference containmentReference, EClass elementType) {
        this.createNodeWithLabel(this.representationId, new CreationTool(ToolSections.NODES, UML.getArtifact()), ARTIFACT_CONTAINER);

        EObject parentElement = this.findSemanticElementByName(ARTIFACT_CONTAINER);
        EObject elementToDrop = this.createSemanticElement(parentElement, containmentReference, elementType, elementType.getName() + DROP_SUFFIX);
        NodeCreationGraphicalChecker graphicalChecker = new NodeCreationGraphicalChecker(this::getDiagram, () -> this.findGraphicalElementByLabel(ARTIFACT_CONTAINER),
                DDMappingTypes.getMappingTypeAsSubNode(elementType),
                this.getCapturedNodes());
        this.semanticDropOnContainer(ARTIFACT_CONTAINER, this.getObjectService().getId(elementToDrop), graphicalChecker);
    }

    @ParameterizedTest
    @MethodSource("dropOnDeviceParameters")
    public void testSemanticDropOnDevice(EReference containmentReference, EClass elementType) {
        this.createNodeWithLabel(this.representationId, new CreationTool(ToolSections.NODES, UML.getDevice()), DEVICE_CONTAINER);

        EObject parentElement = this.findSemanticElementByName(DEVICE_CONTAINER);
        EObject elementToDrop = this.createSemanticElement(parentElement, containmentReference, elementType, elementType.getName() + DROP_SUFFIX);
        NodeCreationGraphicalChecker graphicalChecker = new NodeCreationGraphicalChecker(this::getDiagram, () -> this.findGraphicalElementByLabel(DEVICE_CONTAINER),
                DDMappingTypes.getMappingTypeAsSubNode(elementType),
                this.getCapturedNodes());
        this.semanticDropOnContainer(DEVICE_CONTAINER, this.getObjectService().getId(elementToDrop), graphicalChecker);
    }

    @ParameterizedTest
    @MethodSource("dropOnExecutionEnvironmentParameters")
    public void testSemanticDropOnExecutionEnvironment(EReference containmentReference, EClass elementType) {
        this.createNodeWithLabel(this.representationId, new CreationTool(ToolSections.NODES, UML.getExecutionEnvironment()), EXECUTION_ENVIRONMENT_CONTAINER);

        EObject parentElement = this.findSemanticElementByName(EXECUTION_ENVIRONMENT_CONTAINER);
        EObject elementToDrop = this.createSemanticElement(parentElement, containmentReference, elementType, elementType.getName() + DROP_SUFFIX);
        NodeCreationGraphicalChecker graphicalChecker = new NodeCreationGraphicalChecker(this::getDiagram, () -> this.findGraphicalElementByLabel(EXECUTION_ENVIRONMENT_CONTAINER),
                DDMappingTypes.getMappingTypeAsSubNode(elementType),
                this.getCapturedNodes());
        this.semanticDropOnContainer(EXECUTION_ENVIRONMENT_CONTAINER, this.getObjectService().getId(elementToDrop), graphicalChecker);
    }

    @ParameterizedTest
    @MethodSource("dropOnDiagramAndModelAndPackageParameters")
    public void testSemanticDropOnModel(EReference containmentReference, EClass elementType) {
        this.createNodeWithLabel(this.representationId, new CreationTool(ToolSections.NODES, UML.getModel()), MODEL_CONTAINER);

        EObject parentElement = this.findSemanticElementByName(MODEL_CONTAINER);
        EObject elementToDrop = this.createSemanticElement(parentElement, containmentReference, elementType, elementType.getName() + DROP_SUFFIX);
        NodeCreationGraphicalChecker graphicalChecker = new NodeCreationGraphicalChecker(this::getDiagram, () -> this.findGraphicalElementByLabel(MODEL_CONTAINER),
                DDMappingTypes.getMappingTypeAsSubNode(elementType),
                this.getCapturedNodes());
        this.semanticDropOnContainer(MODEL_CONTAINER, this.getObjectService().getId(elementToDrop), graphicalChecker);
    }

    @ParameterizedTest
    @MethodSource("dropOnNodeParameters")
    public void testSemanticDropOnNode(EReference containmentReference, EClass elementType) {
        this.createNodeWithLabel(this.representationId, new CreationTool(ToolSections.NODES, UML.getNode()), NODE_CONTAINER);

        EObject parentElement = this.findSemanticElementByName(NODE_CONTAINER);
        EObject elementToDrop = this.createSemanticElement(parentElement, containmentReference, elementType, elementType.getName() + DROP_SUFFIX);
        NodeCreationGraphicalChecker graphicalChecker = new NodeCreationGraphicalChecker(this::getDiagram, () -> this.findGraphicalElementByLabel(NODE_CONTAINER),
                DDMappingTypes.getMappingTypeAsSubNode(elementType),
                this.getCapturedNodes());
        this.semanticDropOnContainer(NODE_CONTAINER, this.getObjectService().getId(elementToDrop), graphicalChecker);
    }

    @ParameterizedTest
    @MethodSource("dropOnDiagramAndModelAndPackageParameters")
    public void testSemanticDropOnPackage(EReference containmentReference, EClass elementType) {
        this.createNodeWithLabel(this.representationId, new CreationTool(ToolSections.NODES, UML.getPackage()), PACKAGE_CONTAINER);

        EObject parentElement = this.findSemanticElementByName(PACKAGE_CONTAINER);
        EObject elementToDrop = this.createSemanticElement(parentElement, containmentReference, elementType, elementType.getName() + DROP_SUFFIX);
        NodeCreationGraphicalChecker graphicalChecker = new NodeCreationGraphicalChecker(this::getDiagram, () -> this.findGraphicalElementByLabel(PACKAGE_CONTAINER),
                DDMappingTypes.getMappingTypeAsSubNode(elementType),
                this.getCapturedNodes());
        this.semanticDropOnContainer(PACKAGE_CONTAINER, this.getObjectService().getId(elementToDrop), graphicalChecker);
    }

    @ParameterizedTest
    @MethodSource("dropCommunicationPathParameters")
    public void testSemanticDropCommunicationPath(CreationTool sourceCreationTool, CreationTool targetCreationTool) {
        this.edgeSemanticDropOnDiagram(sourceCreationTool, targetCreationTool, new CreationTool(ToolSections.EDGES, UML.getCommunicationPath()),
                DDMappingTypes.getMappingType(UML.getCommunicationPath()));
    }

    @ParameterizedTest
    @MethodSource("dropDependencyAndManifestationParameters")
    public void testSemanticDropDependency(CreationTool sourceCreationTool, CreationTool targetCreationTool) {
        this.edgeSemanticDropOnDiagram(sourceCreationTool, targetCreationTool, new CreationTool(ToolSections.EDGES, UML.getDependency()), DDMappingTypes.getMappingType(UML.getDependency()));
    }

    @ParameterizedTest
    @MethodSource("dropDeploymentParameters")
    public void testSemanticDropDeployment(CreationTool sourceCreationTool, CreationTool targetCreationTool) {
        this.edgeSemanticDropOnDiagram(sourceCreationTool, targetCreationTool, new CreationTool(ToolSections.EDGES, UML.getDeployment()), DDMappingTypes.getMappingType(UML.getDeployment()));
    }

    @ParameterizedTest
    @MethodSource("dropGeneralizationParameters")
    public void testSemanticDropGeneralization(CreationTool sourceCreationTool, CreationTool targetCreationTool) {
        this.edgeSemanticDropOnDiagram(sourceCreationTool, targetCreationTool, new CreationTool(ToolSections.EDGES, UML.getGeneralization()), DDMappingTypes.getMappingType(UML.getGeneralization()));
    }

    @ParameterizedTest
    @MethodSource("dropDependencyAndManifestationParameters")
    public void testSemanticDropManifestation(CreationTool sourceCreationTool, CreationTool targetCreationTool) {
        this.edgeSemanticDropOnDiagram(sourceCreationTool, targetCreationTool, new CreationTool(ToolSections.EDGES, UML.getManifestation()), DDMappingTypes.getMappingType(UML.getManifestation()));
    }

}
