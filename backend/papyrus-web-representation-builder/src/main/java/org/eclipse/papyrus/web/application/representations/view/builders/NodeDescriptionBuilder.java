/*******************************************************************************
 * Copyright (c) 2022 CEA, Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.application.representations.view.builders;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.web.application.representations.view.IDomainHelper;
import org.eclipse.papyrus.web.application.representations.view.IdBuilder;
import org.eclipse.papyrus.web.application.representations.view.aql.QueryHelper;
import org.eclipse.sirius.components.view.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.DeleteTool;
import org.eclipse.sirius.components.view.LabelEditTool;
import org.eclipse.sirius.components.view.LayoutStrategyDescription;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.NodeStyleDescription;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.SynchronizationPolicy;
import org.eclipse.sirius.components.view.ViewFactory;

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

    private List<NodeTool> createTools = new ArrayList<>();

    private LabelEditTool labelEditTool;

    private String name;

    private String labelExpression;

    private String domainType;

    private boolean collapsible;

    private IDomainHelper metamodelHelper;

    public NodeDescriptionBuilder(IdBuilder idBuilder, QueryHelper queryHelper, EClass domainEntity, NodeStyleDescription style, IDomainHelper metamodelHelper) {
        this.idBuilder = idBuilder;
        this.queryHelper = queryHelper;
        this.domainEntity = domainEntity;
        this.style = style;
        this.metamodelHelper = metamodelHelper;
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

    public NodeDescriptionBuilder createTools(List<NodeTool> createTools) {
        this.createTools = Objects.requireNonNull(createTools);
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
        NodeDescription node = ViewFactory.eINSTANCE.createNodeDescription();
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
        node.setDeleteTool(deleteTool);
        node.getConditionalStyles().addAll(conditionalNodeStyles);
        node.getReusedChildNodeDescriptions().addAll(reusedNodeDescriptions);
        node.getNodeTools().addAll(createTools);
        node.setLabelEditTool(labelEditTool);
        node.setCollapsible(collapsible);

        return node;
    }

    public NodeDescriptionBuilder collapsible(boolean collapsible) {
        this.collapsible = collapsible;
        return this;
    }

}
