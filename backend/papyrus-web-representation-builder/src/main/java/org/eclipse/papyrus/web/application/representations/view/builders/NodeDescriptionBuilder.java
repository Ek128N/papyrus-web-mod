/*******************************************************************************
 * Copyright (c) 2022, 2024 CEA LIST, Obeo.
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
 *******************************************************************************/
package org.eclipse.papyrus.web.application.representations.view.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.web.application.representations.view.IDomainHelper;
import org.eclipse.papyrus.web.application.representations.view.IdBuilder;
import org.eclipse.papyrus.web.application.representations.view.aql.QueryHelper;
import org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.diagram.DeleteTool;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.InsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.InsideLabelStyle;
import org.eclipse.sirius.components.view.diagram.LabelEditTool;
import org.eclipse.sirius.components.view.diagram.LabelTextAlign;
import org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.OutsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.components.view.diagram.provider.DefaultToolsFactory;

/**
 * The builder used to create a NodeDescription.
 *
 * @author lfasani
 */
@SuppressWarnings("checkstyle:HiddenField")
public final class NodeDescriptionBuilder {
    private IdBuilder idBuilder;

    private QueryHelper queryHelper;

    private EClass domainEntity;

    private String semanticCandidateExpression;

    private NodeStyleDescription style;

    private SynchronizationPolicy synchronizationPolicy;

    private LayoutStrategyDescription layoutStrategyDescription;

    private DeleteTool deleteTool;

    private List<ConditionalNodeStyle> conditionalNodeStyles = new ArrayList<>();

    private List<NodeDescription> reusedNodeDescriptions = new ArrayList<>();

    private LabelEditTool labelEditTool;

    private String name;

    private InsideLabelDescription insideLabelDescription;

    private List<OutsideLabelDescription> outsideLabelDescriptions = new ArrayList<>();

    private String domainType;

    private boolean collapsible;

    private IDomainHelper metamodelHelper;

    private NodePalette nodePalette;

    public NodeDescriptionBuilder(IdBuilder idBuilder, QueryHelper queryHelper, EClass domainEntity, NodeStyleDescription style, IDomainHelper metamodelHelper) {
        this.idBuilder = idBuilder;
        this.queryHelper = queryHelper;
        this.domainEntity = domainEntity;
        this.style = style;
        this.metamodelHelper = metamodelHelper;
        this.nodePalette = new DefaultToolsFactory().createDefaultNodePalette();
    }

    public NodeDescriptionBuilder name(String name) {
        this.name = Objects.requireNonNull(name);
        return this;
    }

    public NodeDescriptionBuilder domainType(String domainType) {
        this.domainType = Objects.requireNonNull(domainType);
        return this;
    }

    public NodeDescriptionBuilder semanticCandidateExpression(String semanticCandidateExpression) {
        this.semanticCandidateExpression = Objects.requireNonNull(semanticCandidateExpression);
        return this;
    }

    public NodeDescriptionBuilder synchronizationPolicy(SynchronizationPolicy synchronizationPolicy) {
        this.synchronizationPolicy = Objects.requireNonNull(synchronizationPolicy);
        return this;
    }

    public NodeDescriptionBuilder layoutStrategyDescription(LayoutStrategyDescription layoutStrategyDescription) {
        this.layoutStrategyDescription = Objects.requireNonNull(layoutStrategyDescription);
        return this;
    }

    public NodeDescriptionBuilder deleteTool(DeleteTool deleteTool) {
        this.deleteTool = Objects.requireNonNull(deleteTool);
        return this;
    }

    public NodeDescriptionBuilder conditionalStyles(List<ConditionalNodeStyle> conditionalNodeStyles) {
        this.conditionalNodeStyles = Objects.requireNonNull(conditionalNodeStyles);
        return this;
    }

    public NodeDescriptionBuilder reusedNodeDescriptions(List<NodeDescription> reusedNodeDescriptions) {
        this.reusedNodeDescriptions = Objects.requireNonNull(reusedNodeDescriptions);
        return this;
    }

    public NodeDescriptionBuilder labelEditTool(LabelEditTool labelEditTool) {
        this.labelEditTool = Objects.requireNonNull(labelEditTool);
        return this;
    }

    public NodeDescriptionBuilder insideLabelDescription(InsideLabelDescription labelDescription) {
        this.insideLabelDescription = labelDescription;
        return this;
    }

    public NodeDescriptionBuilder insideLabelDescription(String expression, InsideLabelStyle insideLabelStyle) {
        this.insideLabelDescription = DiagramFactory.eINSTANCE.createInsideLabelDescription();
        this.insideLabelDescription.setTextAlign(LabelTextAlign.CENTER);
        this.insideLabelDescription.setLabelExpression(expression);
        this.insideLabelDescription.setStyle(insideLabelStyle);
        return this;
    }

    public NodeDescriptionBuilder addOutsideLabelDescription(OutsideLabelDescription labelDescription) {
        this.outsideLabelDescriptions.add(labelDescription);
        return this;
    }

    public NodeDescription build() {
        NodeDescription node = DiagramFactory.eINSTANCE.createNodeDescription();
        if (this.name != null) {
            node.setName(this.name);
        } else {
            node.setName(this.idBuilder.getDomainNodeName(this.domainEntity));
        }
        if (this.insideLabelDescription != null) {
            node.setInsideLabel(this.insideLabelDescription);
        }

        for (OutsideLabelDescription outsideLabelDescription : this.outsideLabelDescriptions) {
            node.getOutsideLabels().add(outsideLabelDescription);
        }

        node.setSemanticCandidatesExpression(this.semanticCandidateExpression);
        if (this.domainType == null) {
            node.setDomainType(this.metamodelHelper.getDomain(this.domainEntity));
        } else {
            node.setDomainType(this.domainType);
        }
        node.setSynchronizationPolicy(this.synchronizationPolicy);
        node.setStyle(this.style);
        node.setChildrenLayoutStrategy(this.layoutStrategyDescription);
        node.getConditionalStyles().addAll(this.conditionalNodeStyles);
        node.getReusedChildNodeDescriptions().addAll(this.reusedNodeDescriptions);
        this.nodePalette.setDeleteTool(this.deleteTool);
        this.nodePalette.setLabelEditTool(this.labelEditTool);
        node.setPalette(this.nodePalette);
        node.setCollapsible(this.collapsible);

        return node;
    }

    public NodeDescriptionBuilder collapsible(boolean collapsible) {
        this.collapsible = collapsible;
        return this;
    }

}
