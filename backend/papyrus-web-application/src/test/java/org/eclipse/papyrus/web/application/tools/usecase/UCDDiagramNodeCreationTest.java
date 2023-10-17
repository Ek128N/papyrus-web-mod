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
package org.eclipse.papyrus.web.application.tools.usecase;

import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.web.application.representations.uml.UCDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.tools.checker.CombinedChecker;
import org.eclipse.papyrus.web.application.tools.checker.NodeCreationGraphicalChecker;
import org.eclipse.papyrus.web.application.tools.checker.NodeCreationSemanticChecker;
import org.eclipse.papyrus.web.application.tools.test.NodeCreationTest;
import org.eclipse.papyrus.web.application.tools.usecase.utils.UCDCreationTool;
import org.eclipse.papyrus.web.application.tools.usecase.utils.UCDMappingTypes;
import org.eclipse.papyrus.web.application.tools.usecase.utils.UCDToolSections;
import org.eclipse.papyrus.web.application.tools.utils.CreationTool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests node creation tools at the root of the diagram in the Use Case Diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class UCDDiagramNodeCreationTest extends NodeCreationTest {

    private static final EReference PACKAGED_ELEMENT = UML.getPackage_PackagedElement();

    public UCDDiagramNodeCreationTest() {
        super(DEFAULT_DOCUMENT, UCDDiagramDescriptionBuilder.UCD_REP_NAME, UML.getModel());
    }

    private static Stream<Arguments> parameterProvider() {
        return Stream.of(//
                Arguments.of(new UCDCreationTool(UCDToolSections.SUBJECT, UML.getActivity()), UCDMappingTypes.getMappingType(UML.getActivity()), UML.getActivity(), PACKAGED_ELEMENT), //
                Arguments.of(new UCDCreationTool(UCDToolSections.SUBJECT, UML.getClass_()), UCDMappingTypes.getMappingType(UML.getClass_()), UML.getClass_(), PACKAGED_ELEMENT), //
                Arguments.of(new UCDCreationTool(UCDToolSections.SUBJECT, UML.getComponent()), UCDMappingTypes.getMappingType(UML.getComponent()), UML.getComponent(), PACKAGED_ELEMENT), //
                Arguments.of(new UCDCreationTool(UCDToolSections.SUBJECT, UML.getInteraction()), UCDMappingTypes.getMappingType(UML.getInteraction()), UML.getInteraction(), PACKAGED_ELEMENT), //
                Arguments.of(new UCDCreationTool(UCDToolSections.SUBJECT, UML.getStateMachine()), UCDMappingTypes.getMappingType(UML.getStateMachine()), UML.getStateMachine(), PACKAGED_ELEMENT) //
        );
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
    @MethodSource("parameterProvider")
    public void testCreateNode(CreationTool nodeCreationTool, String mappingType, EClass expectedType, EReference containmentReference) {
        NodeCreationGraphicalChecker graphicalChecker = new NodeCreationGraphicalChecker(() -> this.getDiagram(), null, mappingType, this.getCapturedNodes());
        NodeCreationSemanticChecker semanticChecker = new NodeCreationSemanticChecker(this.getObjectService(), () -> this.getEditingContext(), expectedType, () -> this.getRootSemanticElement(),
                containmentReference);
        this.createNodeOnDiagram(nodeCreationTool, new CombinedChecker(graphicalChecker, semanticChecker));
    }

}
