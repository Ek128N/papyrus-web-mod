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
        changeContextOp.setExpression(this.queryBuilder.queryDrop());
        return dropTool;
    }

    public DiagramDescription buildDiagramDescription(String diagramName, EClass modelType) {
        DiagramDescription diargamDescription = this.createDiagram(diagramName);
        diargamDescription.setDomainType(this.metamodelHelper.getDomain(modelType));
        return diargamDescription;
    }

    private DiagramDescription createDiagram(String diagramName) {
        DiagramDescription diagramDescription = DiagramFactory.eINSTANCE.createDiagramDescription();
        diagramDescription.setName(diagramName);
        diagramDescription.setPalette(DiagramFactory.eINSTANCE.createDiagramPalette());
        return diagramDescription;
    }

    private SourceEdgeEndReconnectionTool createDomainBaseEdgeSourceReconnectionTool(EdgeDescription description, String name) {
        return this.createSourceReconnectionTool(description, name, List.of(this.createChangeContextOperation(this.queryBuilder.queryDomainBasedSourceReconnection())));
    }

    public SourceEdgeEndReconnectionTool createSourceReconnectionTool(EdgeDescription description, String name, List<Operation> operations) {
        SourceEdgeEndReconnectionTool sourceReconnectionTool = DiagramFactory.eINSTANCE.createSourceEdgeEndReconnectionTool();
        sourceReconnectionTool.setName(name);
        sourceReconnectionTool.getBody().addAll(operations);
        return sourceReconnectionTool;
    }

    private TargetEdgeEndReconnectionTool createDomainBaseEdgeTargetReconnectionTool(EdgeDescription description, String name) {
        return this.createTargetReconnectionTool(description, name, List.of(this.createChangeContextOperation(this.queryBuilder.queryDomainBasedTargetReconnection())));
    }

    public TargetEdgeEndReconnectionTool createTargetReconnectionTool(EdgeDescription description, String name, List<Operation> operations) {
        TargetEdgeEndReconnectionTool targetReconnectionTool = DiagramFactory.eINSTANCE.createTargetEdgeEndReconnectionTool();
        targetReconnectionTool.setName(name);
        targetReconnectionTool.getBody().addAll(operations);
        return targetReconnectionTool;
    }

    private NodeDescription createNodeDescription(String id, EClass domainType, String semanticCandidateExpression, NodeStyleDescription style, SynchronizationPolicy synchronizationPolicy) {
        NodeDescription node = this.createNode(id);
        node.setSemanticCandidatesExpression(semanticCandidateExpression);
        node.setDomainType(this.metamodelHelper.getDomain(domainType));
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
        edgeDescription.setDomainType(this.metamodelHelper.getDomain(domainType));
        edgeDescription.setSynchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED);
        edgeDescription.setLabelExpression(this.queryBuilder.queryRenderLabel());
        edgeDescription.setSemanticCandidatesExpression(semanticCandidateExpression);
        edgeDescription.setPalette(DiagramFactory.eINSTANCE.createEdgePalette());

        edgeDescription.eAdapters().add(new CallbackAdapter(() -> {
            edgeDescription.getSourceNodeDescriptions().addAll(sources.get());
            edgeDescription.getTargetNodeDescriptions().addAll(targets.get());
        }));

        edgeDescription.setSourceNodesExpression(this.queryBuilder.aqlDomainBaseGetSourceQuery());
        edgeDescription.setTargetNodesExpression(this.queryBuilder.aqlDomainBaseGetTargetsQuery());

        edgeDescription.setStyle(this.createDefaultEdgeStyle());

        return edgeDescription;
    }

    private EdgeStyle createDefaultEdgeStyle() {
        EdgeStyle edgeStyle = DiagramFactory.eINSTANCE.createEdgeStyle();
        edgeStyle.setColor(this.styleProvider.getEdgeColor());
        edgeStyle.setFontSize(this.styleProvider.getFontSize());
        edgeStyle.setLineStyle(this.styleProvider.getEdgeStyle());
        edgeStyle.setSourceArrowStyle(this.styleProvider.getSourceArrowStyle());
        edgeStyle.setTargetArrowStyle(this.styleProvider.getTargetArrowStyle());
        edgeStyle.setEdgeWidth(this.styleProvider.getEdgeWidth());
        return edgeStyle;
    }

    private EdgeTool createDomainBasedEdgeTool(String id, EdgeDescription description, EReference containmentReference) {
        EdgeTool tool = DiagramFactory.eINSTANCE.createEdgeTool();
        tool.setName(id);
        ChangeContext changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression(this.queryBuilder.queryCreateDomainBaseEdge(description, containmentReference));
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
        NodeDescription borderNodeDescription = this.createNodeDescription(id, domainType, semanticCandidateExpression, this.createDefaultRectangularNodeStyle(), SynchronizationPolicy.UNSYNCHRONIZED);
        borderNodeDescription.getStyle().setWidthComputationExpression(String.valueOf(this.styleProvider.getPortSize()));
        borderNodeDescription.getStyle().setHeightComputationExpression(String.valueOf(this.styleProvider.getPortSize()));
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
        return this.createCreationTool(name, Variables.SELF, containementRef, newType);
    }

    public NodeTool createCreationTool(String name, String selfValue, EReference containementRef, EClass newType) {
        return this.createCreationTool(name, selfValue, containementRef, this.metamodelHelper.getDomain(newType));
    }

    private NodeTool createCreationTool(String name, String selfValue, EReference containementRef, String newType) {
        NodeTool nodeTool = DiagramFactory.eINSTANCE.createNodeTool();
        nodeTool.setName(name);

        // Create instance and init
        ChangeContext createElement = ViewFactory.eINSTANCE.createChangeContext();
        createElement.setExpression(this.queryBuilder.createNodeQuery(newType, selfValue, containementRef));
        nodeTool.getBody().add(createElement);

        return nodeTool;
    }

    /**
     * Creates a creation {@link NodeTool} that delegates to the provided {@code serviceName}.
     * <p>
     * This method is used to create creation tools that rely on diagram-specific creation services. See
     * {@link ViewBuilder#createCreationTool(EReference, EClass)} to create a creation {@link NodeTool} that relies on
     * the default creation mechanism.
     * </p>
     * 
     * @param toolName
     *            the name of the tool to create
     * @param serviceName
     *            the name of the service to call
     * @param serviceParameters
     *            the parameters provided to the service
     * @return the created {@link NodeTool}
     */
    public NodeTool createCreationTool(String toolName, String serviceName, List<String> serviceParameters) {
        NodeTool nodeTool = DiagramFactory.eINSTANCE.createNodeTool();
        nodeTool.setName(toolName);
        ChangeContext createElement = ViewFactory.eINSTANCE.createChangeContext();
        createElement.setExpression(CallQuery.queryServiceOnSelf(serviceName, serviceParameters.toArray(String[]::new)));
        nodeTool.getBody().add(createElement);
        return nodeTool;
    }

    public NodeTool createSiblingCreationTool(String name, String selfValue, EReference containementRef, EClass newType) {
        NodeTool nodeTool = DiagramFactory.eINSTANCE.createNodeTool();
        nodeTool.setName(name);

        // Create instance and init
        ChangeContext createElement = ViewFactory.eINSTANCE.createChangeContext();
        createElement.setExpression(this.queryBuilder.createSiblingNodeQuery(this.metamodelHelper.getDomain(newType), selfValue, containementRef));
        nodeTool.getBody().add(createElement);

        return nodeTool;
    }

    public DeleteTool createNodeDeleteTool(String conceptName) {
        DeleteTool deleteTool = DiagramFactory.eINSTANCE.createDeleteTool();

        deleteTool.setName("Delete " + conceptName); //$NON-NLS-1$

        ChangeContext createElement = ViewFactory.eINSTANCE.createChangeContext();
        createElement.setExpression(this.queryBuilder.queryDestroyNode());
        deleteTool.getBody().add(createElement);

        return deleteTool;
    }

    public DeleteTool createEdgeDeleteTool(String conceptName) {
        DeleteTool deleteTool = DiagramFactory.eINSTANCE.createDeleteTool();

        deleteTool.setName("Delete " + conceptName); //$NON-NLS-1$

        ChangeContext createElement = ViewFactory.eINSTANCE.createChangeContext();
        createElement.setExpression(this.queryBuilder.queryDestroyEdge());
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
        this.initStyle(nodeStyle);
        nodeStyle.setShowIcon(showIcon);
        nodeStyle.setWithHeader(showHeader);
        return nodeStyle;
    }

    private ImageNodeStyleDescription createClassNodeStyle(boolean showIcon) {
        return this.createImageNodeStyle(ParametricSVGImageRegistryCustomImpl.PARAMETRIC_CLASS_IMAGE_ID.toString(), showIcon);
    }

    private ImageNodeStyleDescription createNoteNodeStyle() {
        return this.createImageNodeStyle(ParametricSVGImageRegistryCustomImpl.PARAMETRIC_NOTE_IMAGE_ID.toString(), true);
    }

    public ImageNodeStyleDescription createPackageNodeStyle() {
        ImageNodeStyleDescription packageNodeStyle = this.createImageNodeStyle(ParametricSVGImageRegistryCustomImpl.PARAMETRIC_PACKAGE_IMAGE_ID.toString(), true);
        packageNodeStyle.setWidthComputationExpression("300");
        packageNodeStyle.setHeightComputationExpression("150");
        return packageNodeStyle;
    }

    public ImageNodeStyleDescription createImageNodeStyle(String imageId, boolean showIcon) {
        ImageNodeStyleDescription nodeStyle = DiagramFactory.eINSTANCE.createImageNodeStyleDescription();
        this.initStyle(nodeStyle);
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
        edgeDescription.setStyle(this.createDefaultEdgeStyle());
        edgeDescription.setPalette(DiagramFactory.eINSTANCE.createEdgePalette());

        edgeDescription.eAdapters().add(new CallbackAdapter(() -> {
            edgeDescription.getSourceNodeDescriptions().addAll(sourcesProvider.get());
            edgeDescription.getTargetNodeDescriptions().addAll(targetsProvider.get());
        }));

        return edgeDescription;
    }

    private void initStyle(NodeStyleDescription nodeStyle) {
        nodeStyle.setColor(styleProvider.getNodeColor());
        nodeStyle.setBorderColor(this.styleProvider.getBorderNodeColor());
        nodeStyle.setBorderRadius(this.styleProvider.getNodeBorderRadius());
        nodeStyle.setLabelColor(this.styleProvider.getNodeLabelColor());
    }

    private RectangularNodeStyleDescription createDefaultRectangularNodeStyle() {
        RectangularNodeStyleDescription nodeStyle = DiagramFactory.eINSTANCE.createRectangularNodeStyleDescription();
        this.initStyle(nodeStyle);
        nodeStyle.setShowIcon(false);
        return nodeStyle;
    }

    private NodeDescription createNode(String name) {
        NodeDescription node = DiagramFactory.eINSTANCE.createNodeDescription();
        node.setName(name);
        node.setLabelExpression(this.queryBuilder.queryRenderLabel());
        node.setPalette(DiagramFactory.eINSTANCE.createNodePalette());
        return node;
    }

    public ChangeContext createChangeContextOperation(String string) {
        ChangeContext changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression(string);
        return changeContext;
    }

    public LabelEditTool createDirectEditTool() {
        return this.createDirectEditTool(Variables.SELF);
    }

    public LabelEditTool createDirectEditTool(String selfExpression) {
        LabelEditTool directEditTool = DiagramFactory.eINSTANCE.createLabelEditTool();
        directEditTool.setInitialDirectEditLabelExpression(new CallQuery(selfExpression).callService(Services.GET_DIRECT_EDIT_INPUT_VALUE_SERVICE));
        directEditTool.getBody().add(this.createChangeContextOperation(new CallQuery(selfExpression).callService(Services.CONSUME_DIRECT_EDIT_VALUE_SERVICE, Variables.ARG0)));
        return directEditTool;
    }

    public EdgeDescription createDefaultSynchonizedDomainBaseEdgeDescription(EClass eClass, String semanticCandidateExpression, Supplier<List<NodeDescription>> source,
            Supplier<List<NodeDescription>> targets) {
        EdgeDescription result = this.createSynchonizedDomainBaseEdgeDescription(this.idBuilder.getDomainBaseEdgeId(eClass), eClass, semanticCandidateExpression, source, targets);
        this.addDefaultDeleteTool(result);
        this.addDirectEditTool(result);
        return result;
    }

    public void addDefaultDeleteTool(NodeDescription nodeDescription) {
        nodeDescription.getPalette().setDeleteTool(this.createNodeDeleteTool(this.metamodelHelper.toEClass(nodeDescription.getDomainType()).getName()));
    }

    public void addDefaultDeleteTool(EdgeDescription edgeDescription) {
        if (edgeDescription.isIsDomainBasedEdge()) {
            edgeDescription.getPalette().setDeleteTool(this.createEdgeDeleteTool(this.metamodelHelper.toEClass(edgeDescription.getDomainType()).getName()));
        }
    }

    public void addDirectEditTool(NodeDescription description) {
        description.getPalette().setLabelEditTool(this.createDirectEditTool());
    }

    public void addDirectEditTool(EdgeDescription description) {
        description.getPalette().setCenterLabelEditTool(this.createDirectEditTool());
    }

    public NodeDescription createSpecializedUnsynchonizedNodeDescription(EClass domain, String semanticCandidateExpression, String specialization) {
        NodeDescription result = this.createNodeDescription(this.idBuilder.getSpecializedDomainNodeName(domain, specialization), domain, semanticCandidateExpression, this.createClassNodeStyle(true),
                SynchronizationPolicy.UNSYNCHRONIZED);
        this.addDefaultDeleteTool(result);
        this.addDirectEditTool(result);
        return result;
    }

    public NodeDescription createClassStyleNodeDescription(EClass domain, String semanticCandidateExpression, boolean showIcon, SynchronizationPolicy synchronizationPolicy) {
        NodeDescription result = this.createNodeDescription(this.idBuilder.getDomainNodeName(domain), domain, semanticCandidateExpression, this.createClassNodeStyle(showIcon), synchronizationPolicy);
        this.addDefaultDeleteTool(result);
        this.addDirectEditTool(result);
        return result;
    }

    public NodeDescription createNoteStyleUnsynchonizedNodeDescription(EClass domain, String semanticCandidateExpression) {
        NodeDescription result = this.createNodeDescription(this.idBuilder.getDomainNodeName(domain), domain, semanticCandidateExpression, this.createNoteNodeStyle(),
                SynchronizationPolicy.UNSYNCHRONIZED);
        this.addDefaultDeleteTool(result);
        this.addDirectEditTool(result);
        return result;
    }

    public NodeDescription createPackageStyleUnsynchonizedNodeDescription(EClass domain, String semanticCandidateExpression) {
        NodeDescription result = this.createNodeDescription(this.idBuilder.getDomainNodeName(domain), domain, semanticCandidateExpression, this.createPackageNodeStyle(),
                SynchronizationPolicy.UNSYNCHRONIZED);
        result.setCollapsible(true);
        this.addDefaultDeleteTool(result);
        this.addDirectEditTool(result);
        return result;
    }

    public NodeDescription createSpecializedPortUnsynchonizedNodeDescription(String suffixId, EClass domain, String semanticCandidateExpression) {
        NodeDescription result = this.createUnsynchonizedPortDescription(this.idBuilder.getSpecializedDomainNodeName(domain, suffixId), domain, semanticCandidateExpression);
        this.addDefaultDeleteTool(result);
        this.addDirectEditTool(result);
        return result;
    }

    public EdgeTool createDefaultDomainBasedEdgeTool(EdgeDescription description, EReference contaimentRef) {
        if (!description.isIsDomainBasedEdge()) {
            throw new IllegalArgumentException("Expecting a domain based edge but got " + description); //$NON-NLS-1$
        }
        return this.createDomainBasedEdgeTool(this.idBuilder.getCreationToolId(description), description, contaimentRef);
    }

    public NodeTool createCreationTool(EReference containementRef, EClass newType) {
        return this.createCreationTool(this.idBuilder.getCreationToolId(newType), containementRef, newType);
    }

    public NodeTool createCreationTool(NodeDescription node, EReference containementRef) {
        EClass newElementType = this.metamodelHelper.toEClass(node.getDomainType());
        return this.createCreationTool(this.idBuilder.getCreationToolId(newElementType), containementRef, newElementType);
    }

    public void addDefaultReconnectionTools(EdgeDescription edge) {
        edge.getPalette().getEdgeReconnectionTools().add(this.createDomainBaseEdgeSourceReconnectionTool(edge, this.idBuilder.getSourceReconnectionToolId(edge)));
        edge.getPalette().getEdgeReconnectionTools().add(this.createDomainBaseEdgeTargetReconnectionTool(edge, this.idBuilder.getTargetReconnectionToolId(edge)));
    }

    public NodeStyleDescription createIconAndlabelStyle(boolean showIcon) {
        IconLabelNodeStyleDescription style = DiagramFactory.eINSTANCE.createIconLabelNodeStyleDescription();

        style.setColor(this.styleProvider.getNodeColor());
        style.setLabelColor(this.styleProvider.getNodeLabelColor());
        style.setShowIcon(showIcon);
        return style;
    }
}
