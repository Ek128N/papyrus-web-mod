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
import org.eclipse.sirius.components.view.diagram.LabelEditTool;
import org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
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

    private String labelExpression;

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

    public NodeDescriptionBuilder labelExpression(String labelExpresion) {
        this.labelExpression = labelExpresion;
        return this;
    }

    public NodeDescription build() {
        NodeDescription node = DiagramFactory.eINSTANCE.createNodeDescription();
        if (name != null) {
            node.setName(name);
        } else {
            node.setName(idBuilder.getDomainNodeName(domainEntity));
        }
        if (labelExpression == null) {
            node.setLabelExpression(queryHelper.queryRenderLabel());
        } else {
            node.setLabelExpression(labelExpression);

        }
        node.setSemanticCandidatesExpression(semanticCandidateExpression);
        if (domainType == null) {
            node.setDomainType(metamodelHelper.getDomain(domainEntity));
        } else {
            node.setDomainType(domainType);
        }
        node.setSynchronizationPolicy(synchronizationPolicy);
        node.setStyle(style);
        node.setChildrenLayoutStrategy(layoutStrategyDescription);
        node.getConditionalStyles().addAll(conditionalNodeStyles);
        node.getReusedChildNodeDescriptions().addAll(reusedNodeDescriptions);
        nodePalette.setDeleteTool(deleteTool);
        nodePalette.setLabelEditTool(labelEditTool);
        node.setPalette(nodePalette);
        node.setCollapsible(collapsible);

        return node;
    }

    public NodeDescriptionBuilder collapsible(boolean collapsible) {
        this.collapsible = collapsible;
        return this;
    }

}
