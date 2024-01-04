/*******************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.web.application.representations.view.CreationToolsUtil;
import org.eclipse.papyrus.web.application.representations.view.IDomainHelper;
import org.eclipse.papyrus.web.application.representations.view.IdBuilder;
import org.eclipse.papyrus.web.application.representations.view.aql.CallQuery;
import org.eclipse.papyrus.web.application.representations.view.aql.QueryHelper;
import org.eclipse.papyrus.web.application.representations.view.aql.Services;
import org.eclipse.papyrus.web.application.representations.view.aql.Variables;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.LabelEditTool;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.springframework.data.util.Pair;

/**
 * Builder in charge of building a compartment composed of list items.
 * 
 * @author Arthur Daussy
 */
public class ListCompartmentBuilder {

    private String compartmentNameSuffix;

    private EClass childrenType;

    private List<Pair<EReference, EClass>> creationTools = new ArrayList<>();

    private String semanticCandidateExpression;

    private final IdBuilder idBuilder;

    private final ViewBuilder viewBuider;

    private final QueryHelper queryBuilder;

    private IDomainHelper metamodelHelper;

    public ListCompartmentBuilder(IdBuilder idBuilder, ViewBuilder viewBuider, QueryHelper queryBuilder, IDomainHelper metamodelHelper) {
        super();
        this.idBuilder = idBuilder;
        this.viewBuider = viewBuider;
        this.queryBuilder = queryBuilder;
        this.metamodelHelper = metamodelHelper;
    }

    public ListCompartmentBuilder withCompartmentNameSuffix(String aCompartmentNameSuffix) {
        this.compartmentNameSuffix = aCompartmentNameSuffix;
        return this;
    }

    public ListCompartmentBuilder withChildrenType(EClass aChildrenType) {
        this.childrenType = aChildrenType;
        return this;
    }

    public ListCompartmentBuilder withSemanticCandidateExpression(String aSemanticCandidateExpression) {
        this.semanticCandidateExpression = aSemanticCandidateExpression;
        return this;
    }

    public ListCompartmentBuilder addCreationTools(EReference containmentRef, EClass newType) {
        creationTools.add(Pair.of(containmentRef, newType));
        return this;
    }

    public NodeDescription buildIn(NodeDescription parent) {
        Objects.requireNonNull(queryBuilder);
        Objects.requireNonNull(idBuilder);
        Objects.requireNonNull(semanticCandidateExpression);
        Objects.requireNonNull(childrenType);
        Objects.requireNonNull(compartmentNameSuffix);
        NodeDescription attributesCompartement = addCompartementNode(parent, compartmentNameSuffix);
        attributesCompartement.getPalette().getNodeTools().addAll(creationTools.stream().map(pair -> createCreationTool(pair.getFirst(), pair.getSecond())).toList());
        NodeDescription attributeDescription = createLabelIconInsideCompartmentDescription(parent);
        attributesCompartement.getChildrenDescriptions().add(attributeDescription);
        return attributeDescription;
    }

    /**
     * Creates a compartment node. A compartment node is basically a node which target the same element as its parent
     * but is used to store a list of children.
     * 
     * @param parent
     *            the parent description
     * @param compartmentSpecialization
     *            a name suffix for the compartment
     * @return a new {@link NodeDescription}
     */
    private NodeDescription addCompartementNode(NodeDescription parent, String compartmentSpecialization) {
        NodeDescription description = new NodeDescriptionBuilder(idBuilder, queryBuilder, metamodelHelper.toEClass(parent.getDomainType()), viewBuider.createRectangularNodeStyle(false, false),
                metamodelHelper)//
                        .name(idBuilder.getCompartmentDomainNodeName(metamodelHelper.toEClass(parent.getDomainType()), compartmentSpecialization))
                        .layoutStrategyDescription(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription())//
                        .semanticCandidateExpression(queryBuilder.querySelf())//
                        .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)//
                        .labelExpression(queryBuilder.emptyString())//
                        .collapsible(true)//
                        .build();

        parent.getChildrenDescriptions().add(description);
        return description;
    }

    /**
     * Creates an icon and label description to be used inside a compartment node.
     * 
     * @param elementType
     *            the semantic type of the element
     * @param containmentReference
     *            the containment reference to used for a creation
     * @param semanticCandidateExpression
     *            the semantic candidate expression
     * @return a new NodeDescription
     */
    protected NodeDescription createLabelIconInsideCompartmentDescription(NodeDescription parent) {
        NodeDescription description = new NodeDescriptionBuilder(idBuilder, queryBuilder, childrenType, viewBuider.createIconAndlabelStyle(true), metamodelHelper)//
                .name(idBuilder.getListItemDomainNodeName(childrenType, metamodelHelper.toEClass(parent.getDomainType()))) //
                .semanticCandidateExpression(semanticCandidateExpression)//
                .labelExpression(CallQuery.queryServiceOnSelf(Services.RENDER_LABEL_ONE_LINE, "false", "true")) //
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(createDirectEditTool())//
                .deleteTool(viewBuider.createNodeDeleteTool(childrenType.getName()))//
                .build();

        // Workaround for https://github.com/PapyrusSirius/papyrus-web/issues/164
        NodeDescription fakeNode = new NodeDescriptionBuilder(idBuilder, queryBuilder, childrenType, viewBuider.createIconAndlabelStyle(true), metamodelHelper)//
                .name(idBuilder.getFakeChildNodeId(description)) //
                .semanticCandidateExpression("aql:Sequence{}")
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .build();
        registerCallback(fakeNode, () -> {
            creationTools.forEach(p -> CreationToolsUtil.addNodeCreationTool(() -> List.of(parent), createSiblingCreationTool(p.getFirst(), p.getSecond())));
        });
        description.getChildrenDescriptions().add(fakeNode);

        return description;
    }

    private LabelEditTool createDirectEditTool() {
        LabelEditTool directEditTool = DiagramFactory.eINSTANCE.createLabelEditTool();
        directEditTool.setInitialDirectEditLabelExpression(CallQuery.queryServiceOnSelf(Services.GET_DIRECT_EDIT_INPUT_VALUE_SERVICE));
        directEditTool.getBody().add(viewBuider.createChangeContextOperation(CallQuery.queryServiceOnSelf(Services.CONSUME_DIRECT_EDIT_VALUE_SERVICE, Variables.ARG0)));
        return directEditTool;
    }

    private NodeTool createCreationTool(EReference containementRef, EClass newType) {
        return viewBuider.createCreationTool(idBuilder.getCreationToolId(newType), containementRef, newType);
    }

    /**
     * Creates a node tool that creates a sibling node to the targeted name node.
     * 
     * @param containementRef
     *            the containment reference to be used on the semantic parent of the targeted node
     * @param newType
     *            the type to create
     * @return a new NodeTool
     */
    private NodeTool createSiblingCreationTool(EReference containementRef, EClass newType) {
        return viewBuider.createSiblingCreationTool(idBuilder.getSiblingCreationToolId(newType), Variables.SELF, containementRef, newType);
    }

    private void registerCallback(EObject owner, Runnable r) {
        owner.eAdapters().add(new CallbackAdapter(r));
    }

}
