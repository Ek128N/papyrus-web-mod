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
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Tests semantic drop tools in the Use Case Diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class UCDSemanticDropTest extends SemanticDropTest {

    private static final String PACKAGE_CONTAINER = "PackageContainer";

    private static final String CLASS_CONTAINER = "ClassContainer";

    @Autowired
    private IEditingContextPersistenceService persistenceService;

    public UCDSemanticDropTest() {
        super(DEFAULT_DOCUMENT, UCDDiagramDescriptionBuilder.UCD_REP_NAME, UML.getModel());
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

    @Test
    public void testSemanticDropOnDiagram() {
        EObject elementToDrop = this.createSemanticElement(this.rootObjectId, UML.getPackage_PackagedElement(), UML.getUseCase(), "UseCase1");
        NodeCreationGraphicalChecker graphicalChecker = new NodeCreationGraphicalChecker(this::getDiagram, null, UCDMappingTypes.getMappingType(UML.getUseCase()), this.getCapturedNodes());
        this.semanticDropOnDiagram(this.getObjectService().getId(elementToDrop), graphicalChecker);
    }

    @Test
    public void testSemanticDropOnPackage() {
        this.applyNodeCreationTool(this.representationId, new UCDCreationTool(UCDToolSections.NODES, UML.getPackage()));
        Node parentNode = (Node) this.findGraphicalElementByLabel("Package1");
        this.applyEditLabelTool(parentNode.getInsideLabel().getId(), PACKAGE_CONTAINER);

        EObject parentElement = this.findSemanticElementByName(PACKAGE_CONTAINER);
        String parentElementId = this.getObjectService().getId(parentElement);
        EObject elementToDrop = this.createSemanticElement(parentElementId, UML.getPackage_PackagedElement(), UML.getUseCase(), "UseCase1");
        NodeCreationGraphicalChecker graphicalChecker = new NodeCreationGraphicalChecker(this::getDiagram, () -> this.findGraphicalElementByLabel(PACKAGE_CONTAINER),
                UCDMappingTypes.getSharedMappingType(UML.getUseCase()), this.getCapturedNodes());
        this.semanticDropOnContainer(PACKAGE_CONTAINER, this.getObjectService().getId(elementToDrop), graphicalChecker);
    }

    private EObject createSemanticElement(String parentElementId, EReference containmentReference, EClass type, String name) {
        EObject parentElement = (EObject) this.getObjectService().getObject(this.getEditingContext(), parentElementId).get();
        int numberOfChildren = ((List<?>) parentElement.eGet(containmentReference)).size();
        this.applyCreateChildTool(parentElementId, containmentReference, type);
        IEditingContext editingContext = this.getEditingContext();
        parentElement = (EObject) this.getObjectService().getObject(editingContext, parentElementId).get();
        NamedElement namedElement = (NamedElement) ((List<?>) parentElement.eGet(containmentReference)).get(numberOfChildren);
        namedElement.setName(name);
        this.persistenceService.persist(editingContext);
        return namedElement;

    }

}
