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

import java.util.List;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.web.application.representations.uml.UCDDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.tools.checker.NodeCreationGraphicalChecker;
import org.eclipse.papyrus.web.application.tools.test.SemanticDropTest;
import org.eclipse.papyrus.web.application.tools.usecase.utils.UCDCreationTool;
import org.eclipse.papyrus.web.application.tools.usecase.utils.UCDMappingTypes;
import org.eclipse.papyrus.web.application.tools.usecase.utils.UCDToolSections;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Tests semantic drop tools in the Use Case Diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class UCDSemanticDropTest extends SemanticDropTest {

    private static final String PACKAGE_CONTAINER = "PackageContainer";

    private static final String CLASS_CONTAINER = "ClassContainer";

    private static final String DROP_SUFFIX = "Drop";

    @Autowired
    private IEditingContextPersistenceService persistenceService;

    public UCDSemanticDropTest() {
        super(DEFAULT_DOCUMENT, UCDDiagramDescriptionBuilder.UCD_REP_NAME, UML.getModel());
    }

    private static Stream<Arguments> dropOnDiagramAndPackageParameters() {
        return Stream.of(//
                Arguments.of(UML.getPackage_PackagedElement(), UML.getActor()), //
                Arguments.of(UML.getElement_OwnedComment(), UML.getComment()), //
                Arguments.of(UML.getNamespace_OwnedRule(), UML.getConstraint()), //
                Arguments.of(UML.getPackage_PackagedElement(), UML.getPackage()), //
                Arguments.of(UML.getPackage_PackagedElement(), UML.getActivity()), //
                Arguments.of(UML.getPackage_PackagedElement(), UML.getClass_()), //
                Arguments.of(UML.getPackage_PackagedElement(), UML.getComponent()), //
                Arguments.of(UML.getPackage_PackagedElement(), UML.getInteraction()), //
                Arguments.of(UML.getPackage_PackagedElement(), UML.getStateMachine()), //
                Arguments.of(UML.getPackage_PackagedElement(), UML.getUseCase())//
        );
    }

    private static Stream<Arguments> dropOnClassParameters() {
        return Stream.of(//
                Arguments.of(UML.getElement_OwnedComment(), UML.getComment()), //
                Arguments.of(UML.getNamespace_OwnedRule(), UML.getConstraint()), //
                Arguments.of(UML.getClassifier_OwnedUseCase(), UML.getUseCase())//
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
    @MethodSource("dropOnDiagramAndPackageParameters")
    public void testSemanticDropOnDiagram(EReference containmentReference, EClass elementType) {
        EObject elementToDrop = this.createSemanticElement(this.getRootSemanticElement(), containmentReference, elementType, elementType.getName() + DROP_SUFFIX);
        NodeCreationGraphicalChecker graphicalChecker = new NodeCreationGraphicalChecker(this::getDiagram, null, UCDMappingTypes.getMappingType(elementType), this.getCapturedNodes());
        this.semanticDropOnDiagram(this.getObjectService().getId(elementToDrop), graphicalChecker);
    }

    @ParameterizedTest
    @MethodSource("dropOnDiagramAndPackageParameters")
    public void testSemanticDropOnPackage(EReference containmentReference, EClass elementType) {
        this.createNodeWithLabel(this.representationId, new UCDCreationTool(UCDToolSections.NODES, UML.getPackage()), PACKAGE_CONTAINER);
        EObject parentElement = this.findSemanticElementByName(PACKAGE_CONTAINER);
        EObject elementToDrop = this.createSemanticElement(parentElement, containmentReference, elementType, elementType.getName() + DROP_SUFFIX);
        NodeCreationGraphicalChecker graphicalChecker = new NodeCreationGraphicalChecker(this::getDiagram, () -> this.findGraphicalElementByLabel(PACKAGE_CONTAINER),
                UCDMappingTypes.getMappingTypeAsSubNode(elementType), this.getCapturedNodes());
        this.semanticDropOnContainer(PACKAGE_CONTAINER, this.getObjectService().getId(elementToDrop), graphicalChecker);
    }

    @ParameterizedTest
    @MethodSource("dropOnClassParameters")
    public void testSemanticDropOnClass(EReference containmentReference, EClass elementType) {
        this.createNodeWithLabel(this.representationId, new UCDCreationTool(UCDToolSections.SUBJECT, UML.getClass_()), CLASS_CONTAINER);
        EObject parentElement = this.findSemanticElementByName(CLASS_CONTAINER);
        EObject elementToDrop = this.createSemanticElement(parentElement, containmentReference, elementType, elementType.getName() + DROP_SUFFIX);
        NodeCreationGraphicalChecker graphicalChecker = new NodeCreationGraphicalChecker(this::getDiagram, () -> this.findGraphicalElementByLabel(CLASS_CONTAINER),
                UCDMappingTypes.getMappingTypeAsSubNode(elementType), this.getCapturedNodes());
        this.semanticDropOnContainer(CLASS_CONTAINER, this.getObjectService().getId(elementToDrop), graphicalChecker);
    }

    private EObject createSemanticElement(EObject parentElement, EReference containmentReference, EClass type, String name) {
        String parentElementId = this.getObjectService().getId(parentElement);
        int numberOfChildren = ((List<?>) parentElement.eGet(containmentReference)).size();
        this.applyCreateChildTool(parentElementId, containmentReference, type);
        IEditingContext editingContext = this.getEditingContext();
        EObject updatedParentElement = (EObject) this.getObjectService().getObject(editingContext, parentElementId).get();
        EObject createdObject = (EObject) ((List<?>) updatedParentElement.eGet(containmentReference)).get(numberOfChildren);
        if (createdObject instanceof NamedElement namedElement) {
            namedElement.setName(name);
            this.persistenceService.persist(editingContext);
        }
        return createdObject;
    }

}
