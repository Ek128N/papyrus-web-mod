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
package org.eclipse.papyrus.web.application.representations.view.builders;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.uml.domain.services.EMFUtils;
import org.eclipse.papyrus.web.application.representations.uml.AbstractRepresentationDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.UMLMetamodelHelper;
import org.eclipse.papyrus.web.application.representations.view.IDomainHelper;
import org.eclipse.papyrus.web.application.representations.view.IdBuilder;
import org.eclipse.papyrus.web.application.representations.view.StyleProvider;
import org.eclipse.papyrus.web.application.representations.view.aql.QueryHelper;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;
import org.eclipse.sirius.components.view.diagram.provider.DefaultToolsFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the behavior of {@link NoteStyleDescriptionBuilder}.
 * <p>
 * This class initializes the {@link NoteStyleDescriptionBuilder} with dummy diagram parameters. It is not designed to
 * test the rendering of the elements, just the creation of the {@link NodeDescription}, {@link EdgeDescription}, and
 * their corresponding tools.
 * </p>
 * This class tests the existence of tools associated to descriptions, but it doesn't check that the tools are properly
 * defined. These tests can be defined as standalone tests and don't need to be reproduced here.
 * 
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class NoteStyleDescriptionBuilderTest {

    private static final String TEST_DIAGRAM_PREFIX = "Test";

    private static final String TEST_REPRESENTATION_NAME = "Test";

    private static final EClass TEST_REPRESENTATION_DOMAIN_CLASS = UMLPackage.eINSTANCE.getPackage();

    private NoteStyleDescriptionBuilder noteStyleDescriptionBuilder;

    private StyleProvider styleProvider;

    private DiagramDescription diagramDescription;

    @BeforeEach
    public void setUp() {
        IDomainHelper umlMetaModelHelper = new UMLMetamodelHelper();
        IdBuilder idBuilder = new IdBuilder(TEST_DIAGRAM_PREFIX, umlMetaModelHelper);
        QueryHelper queryBuilder = new QueryHelper(umlMetaModelHelper);

        View stubView = ViewFactory.eINSTANCE.createView();

        this.styleProvider = new StyleProvider(stubView, "");
        ViewBuilder viewBuilder = new ViewBuilder(queryBuilder, this.styleProvider, idBuilder, umlMetaModelHelper);

        this.noteStyleDescriptionBuilder = new NoteStyleDescriptionBuilder(idBuilder, viewBuilder, queryBuilder);
        this.diagramDescription = viewBuilder.buildDiagramDescription(TEST_REPRESENTATION_NAME, TEST_REPRESENTATION_DOMAIN_CLASS);
    }

    @Test
    public void testBuildInDiagramDescriptionNoParameter() {
        assertThrows(NullPointerException.class, () -> this.noteStyleDescriptionBuilder.buildIn(this.diagramDescription));
    }

    @Test
    public void testBuildInDiagramDescriptionAllParameters() {
        this.noteStyleDescriptionBuilder //
                .withDomainType(UMLPackage.eINSTANCE.getComment()) //
                .withAnnotedDomainType(UMLPackage.eINSTANCE.getElement()) //
                .withReconnectSourceService("reconnectSourceService") //
                .withReconnectTargetService("reconnectTargetService") //
                .withContainmentReference(UMLPackage.eINSTANCE.getElement_OwnedComment()) //
                .withNoteToElementReference(UMLPackage.eINSTANCE.getComment_AnnotatedElement()) //
                .withColor(this.styleProvider.getNoteColor()) //
                .buildIn(this.diagramDescription);
        // Make sure all the defined call backs have been executed.
        this.triggerCallbacks(this.diagramDescription);
        assertEquals(1, this.diagramDescription.getNodeDescriptions().size());
        NodeDescription nodeDescription = this.diagramDescription.getNodeDescriptions().get(0);
        assertEquals(this.styleProvider.getNoteColor(), nodeDescription.getStyle().getColor());
        assertEquals("200", nodeDescription.getDefaultWidthExpression());
        assertEquals("100", nodeDescription.getDefaultHeightExpression());
        assertTrue(nodeDescription.getStyle().isShowIcon());

        // The builder does not create creation tools
        assertTrue(this.diagramDescription.getPalette().getNodeTools().isEmpty());

        assertEquals(1, this.diagramDescription.getEdgeDescriptions().size());
        EdgeDescription edgeDescription = this.diagramDescription.getEdgeDescriptions().get(0);
        // Note edges don't have an associated domain type
        assertNull(edgeDescription.getDomainType());
        assertEquals(ArrowStyle.NONE, edgeDescription.getStyle().getTargetArrowStyle());
        assertEquals(LineStyle.DASH, edgeDescription.getStyle().getLineStyle());
        assertNotNull(edgeDescription.getPalette().getDeleteTool());
        // Reconnect tool for source and target reconnection
        assertEquals(2, edgeDescription.getPalette().getEdgeReconnectionTools().size());

        assertEquals(2, nodeDescription.getPalette().getToolSections().size());
        assertEquals(1, nodeDescription.getPalette().getToolSections().get(0).getEdgeTools().size() + nodeDescription.getPalette().getToolSections().get(1).getEdgeTools().size());
        NodeToolSection edgeToolSection = this.getEdgeToolSection(nodeDescription);
        assertNotNull(edgeToolSection);
        EdgeTool edgeTool = edgeToolSection.getEdgeTools().get(0);
        assertEquals("New Link", edgeTool.getName());
    }

    @Test
    public void testBuildInNodeDescriptionNoParameter() {
        // create nodeDescription inside DiagramDescription
        NodeDescription nodeDescription = DiagramFactory.eINSTANCE.createNodeDescription();
        NodePalette nodePalette = new DefaultToolsFactory().createDefaultNodePalette();
        nodeDescription.setPalette(nodePalette);
        this.diagramDescription.getNodeDescriptions().add(nodeDescription);

        assertThrows(NullPointerException.class, () -> this.noteStyleDescriptionBuilder.buildIn(this.diagramDescription, nodeDescription));
    }

    @Test
    public void testBuildInNodeDescriptionAllParameters() {
        // create nodeDescription inside DiagramDescription
        NodeDescription nodeDescription = DiagramFactory.eINSTANCE.createNodeDescription();
        NodePalette nodePalette = new DefaultToolsFactory().createDefaultNodePalette();
        nodeDescription.setPalette(nodePalette);
        this.diagramDescription.getNodeDescriptions().add(nodeDescription);

        this.noteStyleDescriptionBuilder //
                .withDomainType(UMLPackage.eINSTANCE.getComment()) //
                .withAnnotedDomainType(UMLPackage.eINSTANCE.getElement()) //
                .withReconnectSourceService("reconnectSourceService") //
                .withReconnectTargetService("reconnectTargetService") //
                .withContainmentReference(UMLPackage.eINSTANCE.getElement_OwnedComment()) //
                .withNoteToElementReference(UMLPackage.eINSTANCE.getComment_AnnotatedElement()) //
                .withColor(this.styleProvider.getNoteColor()) //
                .buildIn(this.diagramDescription, nodeDescription);
        // Make sure all the defined call backs have been executed.
        this.triggerCallbacks(this.diagramDescription);
        assertEquals(1, this.diagramDescription.getNodeDescriptions().size());
        NodeDescription diagChildNodeDescription = this.diagramDescription.getNodeDescriptions().get(0);
        assertEquals(nodeDescription, diagChildNodeDescription);

        assertEquals(1, this.diagramDescription.getNodeDescriptions().size());
        NodeDescription childNodeDescription = diagChildNodeDescription.getChildrenDescriptions().get(0);
        assertEquals(this.styleProvider.getNoteColor(), childNodeDescription.getStyle().getColor());
        assertEquals("200", childNodeDescription.getDefaultWidthExpression());
        assertEquals("100", childNodeDescription.getDefaultHeightExpression());
        assertTrue(childNodeDescription.getStyle().isShowIcon());

        assertTrue(diagChildNodeDescription.getPalette().getNodeTools().isEmpty());

        assertEquals(1, this.diagramDescription.getEdgeDescriptions().size());
        EdgeDescription edgeDescription = this.diagramDescription.getEdgeDescriptions().get(0);
        // Note edges don't have an associated domain type
        assertNull(edgeDescription.getDomainType());
        assertEquals(ArrowStyle.NONE, edgeDescription.getStyle().getTargetArrowStyle());
        assertEquals(LineStyle.DASH, edgeDescription.getStyle().getLineStyle());
        assertNotNull(edgeDescription.getPalette().getDeleteTool());
        // Reconnect tool for source and target reconnection
        assertEquals(2, edgeDescription.getPalette().getEdgeReconnectionTools().size());

        assertEquals(2, childNodeDescription.getPalette().getToolSections().size());
        assertEquals(1, childNodeDescription.getPalette().getToolSections().get(0).getEdgeTools().size() + childNodeDescription.getPalette().getToolSections().get(1).getEdgeTools().size());
        NodeToolSection edgeToolSection = this.getEdgeToolSection(childNodeDescription);
        assertNotNull(edgeToolSection);
        EdgeTool edgeTool = edgeToolSection.getEdgeTools().get(0);
        assertEquals("New Link", edgeTool.getName());
    }

    private void triggerCallbacks(DiagramDescription description) {
        EMFUtils.eAllContentStreamWithSelf(description).forEach(e -> {
            List<CallbackAdapter> callacks = e.eAdapters().stream()//
                    .filter(adapter -> adapter instanceof CallbackAdapter)//
                    .map(adapter -> (CallbackAdapter) adapter)//
                    .collect(toList());

            for (var callback : callacks) {
                callback.run();
                e.eAdapters().remove(callback);
            }
        });
    }

    private NodeToolSection getEdgeToolSection(NodeDescription nodeDescription) {
        return nodeDescription.getPalette().getToolSections().stream().filter(toolSection -> AbstractRepresentationDescriptionBuilder.EDGES.equals(toolSection.getName())).findFirst().orElse(null);
    }

}
