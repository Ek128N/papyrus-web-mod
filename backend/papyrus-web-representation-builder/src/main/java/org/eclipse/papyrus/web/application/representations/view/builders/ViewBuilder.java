/*****************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.web.application.representations.configuration.ParametricSVGImageRegistryCustomImpl;
import org.eclipse.papyrus.web.application.representations.view.IDomainHelper;
import org.eclipse.papyrus.web.application.representations.view.IdBuilder;
import org.eclipse.papyrus.web.application.representations.view.StyleProvider;
import org.eclipse.papyrus.web.application.representations.view.aql.CallQuery;
import org.eclipse.papyrus.web.application.representations.view.aql.QueryHelper;
import org.eclipse.papyrus.web.application.representations.view.aql.Services;
import org.eclipse.papyrus.web.application.representations.view.aql.Variables;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.diagram.DeleteTool;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DropTool;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeStyle;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.LabelEditTool;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.SourceEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.components.view.diagram.TargetEdgeEndReconnectionTool;

/**
 * Builder in charge of creating elements to fill a {@link DiagramDescription}.
 *
 * @author Arthur Daussy
 */
public class ViewBuilder {

    private QueryHelper queryBuilder;

    private StyleProvider styleProvider;

    private IdBuilder idBuilder;

    private IDomainHelper metamodelHelper;

    public ViewBuilder(QueryHelper queryBuilder, StyleProvider styleProvider, IdBuilder idBuilder, IDomainHelper metamodelHelper) {
        this.metamodelHelper = Objects.requireNonNull(metamodelHelper);
        this.idBuilder = Objects.requireNonNull(idBuilder);
        this.queryBuilder = Objects.requireNonNull(queryBuilder);
        this.styleProvider = Objects.requireNonNull(styleProvider);
    }

    public DropTool createGenericDropTool(String dropToolId) {
        DropTool dropTool = DiagramFactory.eINSTANCE.createDropTool();
        dropTool.setName(dropToolId);

        ChangeContext changeContextOp = ViewFactory.eINSTANCE.createChangeContext();
        dropTool.getBody().add(changeContextOp);
        changeContextOp.setExpression(queryBuilder.queryDrop());
        return dropTool;
    }

    public DiagramDescription buildDiagramDescription(String diagramName, EClass modelType) {
        DiagramDescription diargamDescription = createDiagram(diagramName);
        diargamDescription.setDomainType(metamodelHelper.getDomain(modelType));
        return diargamDescription;
    }

    private DiagramDescription createDiagram(String diagramName) {
        DiagramDescription diagramDescription = DiagramFactory.eINSTANCE.createDiagramDescription();
        diagramDescription.setName(diagramName);
        diagramDescription.setPalette(DiagramFactory.eINSTANCE.createDiagramPalette());
        return diagramDescription;
    }

    private SourceEdgeEndReconnectionTool createDomainBaseEdgeSourceReconnectionTool(EdgeDescription description, String name) {
        return createSourceReconnectionTool(description, name, List.of(createChangeContextOperation(queryBuilder.queryDomainBasedSourceReconnection())));
    }

    public SourceEdgeEndReconnectionTool createSourceReconnectionTool(EdgeDescription description, String name, List<Operation> operations) {
        SourceEdgeEndReconnectionTool sourceReconnectionTool = DiagramFactory.eINSTANCE.createSourceEdgeEndReconnectionTool();
        sourceReconnectionTool.setName(name);
        sourceReconnectionTool.getBody().addAll(operations);
        return sourceReconnectionTool;
    }

    private TargetEdgeEndReconnectionTool createDomainBaseEdgeTargetReconnectionTool(EdgeDescription description, String name) {
        return createTargetReconnectionTool(description, name, List.of(createChangeContextOperation(queryBuilder.queryDomainBasedTargetReconnection())));
    }

    public TargetEdgeEndReconnectionTool createTargetReconnectionTool(EdgeDescription description, String name, List<Operation> operations) {
        TargetEdgeEndReconnectionTool targetReconnectionTool = DiagramFactory.eINSTANCE.createTargetEdgeEndReconnectionTool();
        targetReconnectionTool.setName(name);
        targetReconnectionTool.getBody().addAll(operations);
        return targetReconnectionTool;
    }

    private NodeDescription createNodeDescription(String id, EClass domainType, String semanticCandidateExpression, NodeStyleDescription style, SynchronizationPolicy synchronizationPolicy) {
        NodeDescription node = createNode(id);
        node.setSemanticCandidatesExpression(semanticCandidateExpression);
        node.setDomainType(metamodelHelper.getDomain(domainType));
        node.setSynchronizationPolicy(synchronizationPolicy);
        node.setStyle(style);
        node.setChildrenLayoutStrategy(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());
        return node;
    }

    private EdgeDescription createSynchonizedDomainBaseEdgeDescription(String id, EClass domainType, String semanticCandidateExpression, Supplier<List<NodeDescription>> sources,
            Supplier<List<NodeDescription>> targets) {
        EdgeDescription edgeDescription = DiagramFactory.eINSTANCE.createEdgeDescription();
        edgeDescription.setName(id);
        edgeDescription.setIsDomainBasedEdge(true);
        edgeDescription.setDomainType(metamodelHelper.getDomain(domainType));
        edgeDescription.setSynchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED);
        edgeDescription.setLabelExpression(queryBuilder.queryRenderLabel());
        edgeDescription.setSemanticCandidatesExpression(semanticCandidateExpression);
        edgeDescription.setPalette(DiagramFactory.eINSTANCE.createEdgePalette());

        edgeDescription.eAdapters().add(new CallbackAdapter(() -> {
            edgeDescription.getSourceNodeDescriptions().addAll(sources.get());
            edgeDescription.getTargetNodeDescriptions().addAll(targets.get());
        }));

        edgeDescription.setSourceNodesExpression(queryBuilder.aqlDomainBaseGetSourceQuery());
        edgeDescription.setTargetNodesExpression(queryBuilder.aqlDomainBaseGetTargetsQuery());

        edgeDescription.setStyle(createDefaultEdgeStyle());

        return edgeDescription;
    }

    private EdgeStyle createDefaultEdgeStyle() {
        EdgeStyle edgeStyle = DiagramFactory.eINSTANCE.createEdgeStyle();
        edgeStyle.setColor(styleProvider.getEdgeColor());
        edgeStyle.setFontSize(styleProvider.getFontSize());
        edgeStyle.setLineStyle(styleProvider.getEdgeStyle());
        edgeStyle.setSourceArrowStyle(styleProvider.getSourceArrowStyle());
        edgeStyle.setTargetArrowStyle(styleProvider.getTargetArrowStyle());
        edgeStyle.setEdgeWidth(styleProvider.getEdgeWidth());
        return edgeStyle;
    }

    private EdgeTool createDomainBasedEdgeTool(String id, EdgeDescription description, EReference containmentReference) {
        EdgeTool tool = DiagramFactory.eINSTANCE.createEdgeTool();
        tool.setName(id);
        ChangeContext changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression(queryBuilder.queryCreateDomainBaseEdge(description, containmentReference));
        // Configure the tool's target element descriptions once the representation has been fully created. This ensures
        // that description.getTargetNodeDescription has been filled with the descriptions.
        description.eAdapters().add(new CallbackAdapter(() -> {
            List<NodeDescription> targetNodeDescriptions = description.getTargetNodeDescriptions();
            tool.getTargetElementDescriptions().addAll(targetNodeDescriptions);
        }));
        tool.getBody().add(changeContext);
        return tool;
    }

    public EdgeTool createFeatureBasedEdgeTool(String id, String serviceExpression, List<? extends DiagramElementDescription> targets) {
        EdgeTool tool = DiagramFactory.eINSTANCE.createEdgeTool();
        tool.setName(id);
        tool.getTargetElementDescriptions().addAll(targets);
        ChangeContext changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression(serviceExpression);

        tool.getBody().add(changeContext);

        return tool;
    }

    private NodeDescription createUnsynchonizedPortDescription(String id, EClass domainType, String semanticCandidateExpression) {
        NodeDescription borderNodeDescription = createNodeDescription(id, domainType, semanticCandidateExpression, createDefaultRectangularNodeStyle(), SynchronizationPolicy.UNSYNCHRONIZED);
        borderNodeDescription.getStyle().setWidthComputationExpression(String.valueOf(styleProvider.getPortSize()));
        borderNodeDescription.getStyle().setHeightComputationExpression(String.valueOf(styleProvider.getPortSize()));
        return borderNodeDescription;
    }

    /**
     * Create a creation tool to create a unsynchronized {@link NodeDescription}.
     *
     * @param nodeToCreate
     *            the description of the mapping
     * @param containementRef
     *            the containment reference used to contained the new element
     * @return a new {@link NodeTool}
     */
    public NodeTool createCreationTool(String name, EReference containementRef, EClass newType) {
        return createCreationTool(name, Variables.SELF, containementRef, newType);
    }

    public NodeTool createCreationTool(String name, String selfValue, EReference containementRef, EClass newType) {
        return createCreationTool(name, selfValue, containementRef, metamodelHelper.getDomain(newType));
    }

    private NodeTool createCreationTool(String name, String selfValue, EReference containementRef, String newType) {
        NodeTool nodeTool = DiagramFactory.eINSTANCE.createNodeTool();
        nodeTool.setName(name);

        // Create instance and init
        ChangeContext createElement = ViewFactory.eINSTANCE.createChangeContext();
        createElement.setExpression(queryBuilder.createNodeQuery(newType, selfValue, containementRef));
        nodeTool.getBody().add(createElement);

        return nodeTool;
    }

    public NodeTool createSiblingCreationTool(String name, String selfValue, EReference containementRef, EClass newType) {
        NodeTool nodeTool = DiagramFactory.eINSTANCE.createNodeTool();
        nodeTool.setName(name);

        // Create instance and init
        ChangeContext createElement = ViewFactory.eINSTANCE.createChangeContext();
        createElement.setExpression(queryBuilder.createSiblingNodeQuery(metamodelHelper.getDomain(newType), selfValue, containementRef));
        nodeTool.getBody().add(createElement);

        return nodeTool;
    }

    public DeleteTool createNodeDeleteTool(String conceptName) {
        DeleteTool deleteTool = DiagramFactory.eINSTANCE.createDeleteTool();

        deleteTool.setName("Delete " + conceptName); //$NON-NLS-1$

        ChangeContext createElement = ViewFactory.eINSTANCE.createChangeContext();
        createElement.setExpression(queryBuilder.queryDestroyNode());
        deleteTool.getBody().add(createElement);

        return deleteTool;
    }

    public DeleteTool createEdgeDeleteTool(String conceptName) {
        DeleteTool deleteTool = DiagramFactory.eINSTANCE.createDeleteTool();

        deleteTool.setName("Delete " + conceptName); //$NON-NLS-1$

        ChangeContext createElement = ViewFactory.eINSTANCE.createChangeContext();
        createElement.setExpression(queryBuilder.queryDestroyEdge());
        deleteTool.getBody().add(createElement);

        return deleteTool;
    }

    public ConditionalNodeStyle createConditionalNodeStyle(String condition, NodeStyleDescription nodeStyle) {
        ConditionalNodeStyle conditionalNodeStyle = DiagramFactory.eINSTANCE.createConditionalNodeStyle();
        conditionalNodeStyle.setCondition(condition);
        conditionalNodeStyle.setStyle(nodeStyle);
        return conditionalNodeStyle;
    }

    public RectangularNodeStyleDescription createRectangularNodeStyle(boolean showIcon, boolean showHeader) {
        RectangularNodeStyleDescription nodeStyle = DiagramFactory.eINSTANCE.createRectangularNodeStyleDescription();
        initStyle(nodeStyle);
        nodeStyle.setColor(styleProvider.getNodeColor());
        nodeStyle.setShowIcon(showIcon);
        nodeStyle.setWithHeader(showHeader);
        return nodeStyle;
    }

    private ImageNodeStyleDescription createClassNodeStyle(boolean showIcon) {
        return createImageNodeStyle(ParametricSVGImageRegistryCustomImpl.PARAMETRIC_CLASS_IMAGE_ID.toString(), showIcon);
    }

    private ImageNodeStyleDescription createNoteNodeStyle() {
        return createImageNodeStyle(ParametricSVGImageRegistryCustomImpl.PARAMETRIC_NOTE_IMAGE_ID.toString(), true);
    }

    private ImageNodeStyleDescription createPackageNodeStyle() {
        ImageNodeStyleDescription packageNodeStyle = createImageNodeStyle(ParametricSVGImageRegistryCustomImpl.PARAMETRIC_PACKAGE_IMAGE_ID.toString(), true);
        packageNodeStyle.setWidthComputationExpression("300");
        packageNodeStyle.setHeightComputationExpression("150");
        return packageNodeStyle;
    }

    public ImageNodeStyleDescription createImageNodeStyle(String imageId, boolean showIcon) {
        ImageNodeStyleDescription nodeStyle = DiagramFactory.eINSTANCE.createImageNodeStyleDescription();
        initStyle(nodeStyle);
        // TODO uncomment next line when setColor is restored possibly in 2023.10.0
        // nodeStyle.setColor(styleProvider.getNoteColor());
        nodeStyle.setShape(imageId);
        nodeStyle.setShowIcon(showIcon);
        return nodeStyle;
    }

    public EdgeDescription createFeatureEdgeDescription(String id, String labelExpression, String targetNodeExpression, Supplier<List<NodeDescription>> sourcesProvider,
            Supplier<List<NodeDescription>> targetsProvider) {

        EdgeDescription edgeDescription = DiagramFactory.eINSTANCE.createEdgeDescription();
        edgeDescription.setName(id);
        edgeDescription.setLabelExpression(labelExpression);
        edgeDescription.setTargetNodesExpression(targetNodeExpression);
        edgeDescription.setStyle(createDefaultEdgeStyle());
        edgeDescription.setPalette(DiagramFactory.eINSTANCE.createEdgePalette());

        edgeDescription.eAdapters().add(new CallbackAdapter(() -> {
            edgeDescription.getSourceNodeDescriptions().addAll(sourcesProvider.get());
            edgeDescription.getTargetNodeDescriptions().addAll(targetsProvider.get());
        }));

        return edgeDescription;
    }

    private void initStyle(NodeStyleDescription nodeStyle) {
        nodeStyle.setBorderColor(styleProvider.getBorderNodeColor());
        nodeStyle.setBorderRadius(styleProvider.getNodeBorderRadius());
        nodeStyle.setLabelColor(styleProvider.getNodeLabelColor());
    }

    private RectangularNodeStyleDescription createDefaultRectangularNodeStyle() {
        RectangularNodeStyleDescription nodeStyle = DiagramFactory.eINSTANCE.createRectangularNodeStyleDescription();
        initStyle(nodeStyle);
        nodeStyle.setColor(styleProvider.getNodeColor());
        nodeStyle.setShowIcon(false);
        return nodeStyle;
    }

    private NodeDescription createNode(String name) {
        NodeDescription node = DiagramFactory.eINSTANCE.createNodeDescription();
        node.setName(name);
        node.setLabelExpression(queryBuilder.queryRenderLabel());
        node.setPalette(DiagramFactory.eINSTANCE.createNodePalette());
        return node;
    }

    public ChangeContext createChangeContextOperation(String string) {
        ChangeContext changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression(string);
        return changeContext;
    }

    public LabelEditTool createDirectEditTool() {
        return createDirectEditTool(Variables.SELF);
    }

    public LabelEditTool createDirectEditTool(String selfExpression) {
        LabelEditTool directEditTool = DiagramFactory.eINSTANCE.createLabelEditTool();
        directEditTool.setInitialDirectEditLabelExpression(new CallQuery(selfExpression).callService(Services.GET_DIRECT_EDIT_INPUT_VALUE_SERVICE));
        directEditTool.getBody().add(createChangeContextOperation(new CallQuery(selfExpression).callService(Services.CONSUME_DIRECT_EDIT_VALUE_SERVICE, Variables.ARG0)));
        return directEditTool;
    }

    public EdgeDescription createDefaultSynchonizedDomainBaseEdgeDescription(EClass eClass, String semanticCandidateExpression, Supplier<List<NodeDescription>> source,
            Supplier<List<NodeDescription>> targets) {
        EdgeDescription result = createSynchonizedDomainBaseEdgeDescription(idBuilder.getDomainBaseEdgeId(eClass), eClass, semanticCandidateExpression, source, targets);
        addDefaultDeleteTool(result);
        addDirectEditTool(result);
        return result;
    }

    public void addDefaultDeleteTool(NodeDescription nodeDescription) {
        nodeDescription.getPalette().setDeleteTool(createNodeDeleteTool(metamodelHelper.toEClass(nodeDescription.getDomainType()).getName()));
    }

    public void addDefaultDeleteTool(EdgeDescription edgeDescription) {
        if (edgeDescription.isIsDomainBasedEdge()) {
            edgeDescription.getPalette().setDeleteTool(createEdgeDeleteTool(metamodelHelper.toEClass(edgeDescription.getDomainType()).getName()));
        }
    }

    public void addDirectEditTool(NodeDescription description) {
        description.getPalette().setLabelEditTool(createDirectEditTool());
    }

    public void addDirectEditTool(EdgeDescription description) {
        description.getPalette().setCenterLabelEditTool(createDirectEditTool());
    }

    public NodeDescription createSpecializedUnsynchonizedNodeDescription(EClass domain, String semanticCandidateExpression, String specialization) {
        NodeDescription result = createNodeDescription(idBuilder.getSpecializedDomainNodeName(domain, specialization), domain, semanticCandidateExpression, createClassNodeStyle(true),
                SynchronizationPolicy.UNSYNCHRONIZED);
        addDefaultDeleteTool(result);
        addDirectEditTool(result);
        return result;
    }

    public NodeDescription createClassStyleNodeDescription(EClass domain, String semanticCandidateExpression, boolean showIcon, SynchronizationPolicy synchronizationPolicy) {
        NodeDescription result = createNodeDescription(idBuilder.getDomainNodeName(domain), domain, semanticCandidateExpression, createClassNodeStyle(showIcon), synchronizationPolicy);
        addDefaultDeleteTool(result);
        addDirectEditTool(result);
        return result;
    }

    public NodeDescription createNoteStyleUnsynchonizedNodeDescription(EClass domain, String semanticCandidateExpression) {
        NodeDescription result = createNodeDescription(idBuilder.getDomainNodeName(domain), domain, semanticCandidateExpression, createNoteNodeStyle(), SynchronizationPolicy.UNSYNCHRONIZED);
        addDefaultDeleteTool(result);
        addDirectEditTool(result);
        return result;
    }

    public NodeDescription createPackageStyleUnsynchonizedNodeDescription(EClass domain, String semanticCandidateExpression) {
        NodeDescription result = createNodeDescription(idBuilder.getDomainNodeName(domain), domain, semanticCandidateExpression, createPackageNodeStyle(), SynchronizationPolicy.UNSYNCHRONIZED);
        result.setCollapsible(true);
        addDefaultDeleteTool(result);
        addDirectEditTool(result);
        return result;
    }

    public NodeDescription createSpecializedPortUnsynchonizedNodeDescription(String suffixId, EClass domain, String semanticCandidateExpression) {
        NodeDescription result = createUnsynchonizedPortDescription(idBuilder.getSpecializedDomainNodeName(domain, suffixId), domain, semanticCandidateExpression);
        addDefaultDeleteTool(result);
        addDirectEditTool(result);
        return result;
    }

    public EdgeTool createDefaultDomainBasedEdgeTool(EdgeDescription description, EReference contaimentRef) {
        if (!description.isIsDomainBasedEdge()) {
            throw new IllegalArgumentException("Expecting a domain based edge but got " + description); //$NON-NLS-1$
        }
        return createDomainBasedEdgeTool(idBuilder.getCreationToolId(description), description, contaimentRef);
    }

    public NodeTool createCreationTool(EReference containementRef, EClass newType) {
        return createCreationTool(idBuilder.getCreationToolId(newType), containementRef, newType);
    }

    public NodeTool createCreationTool(NodeDescription node, EReference containementRef) {
        EClass newElementType = metamodelHelper.toEClass(node.getDomainType());
        return createCreationTool(idBuilder.getCreationToolId(newElementType), containementRef, newElementType);
    }

    public void addDefaultReconnectionTools(EdgeDescription edge) {
        edge.getPalette().getEdgeReconnectionTools().add(createDomainBaseEdgeSourceReconnectionTool(edge, idBuilder.getSourceReconnectionToolId(edge)));
        edge.getPalette().getEdgeReconnectionTools().add(createDomainBaseEdgeTargetReconnectionTool(edge, idBuilder.getTargetReconnectionToolId(edge)));
    }

    public NodeStyleDescription createIconAndlabelStyle(boolean showIcon) {
        IconLabelNodeStyleDescription style = DiagramFactory.eINSTANCE.createIconLabelNodeStyleDescription();

        style.setColor(styleProvider.getNodeColor());
        style.setLabelColor(styleProvider.getNodeLabelColor());
        style.setShowIcon(showIcon);
        return style;
    }
}
