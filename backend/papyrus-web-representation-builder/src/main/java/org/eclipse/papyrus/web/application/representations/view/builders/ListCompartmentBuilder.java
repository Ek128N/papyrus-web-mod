/*******************************************************************************
 * Copyright (c) 2023 CEA, Obeo.
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

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.web.application.representations.view.IDomainHelper;
import org.eclipse.papyrus.web.application.representations.view.IdBuilder;
import org.eclipse.papyrus.web.application.representations.view.aql.CallQuery;
import org.eclipse.papyrus.web.application.representations.view.aql.QueryHelper;
import org.eclipse.papyrus.web.application.representations.view.aql.Services;
import org.eclipse.papyrus.web.application.representations.view.aql.Variables;
import org.eclipse.sirius.components.view.LabelEditTool;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.SynchronizationPolicy;
import org.eclipse.sirius.components.view.ViewFactory;
import org.springframework.data.util.Pair;

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
        NodeDescription attributesCompartement = addCompartementNode(parent, compartmentNameSuffix); // $NON-NLS-1$
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
                .layoutStrategyDescription(ViewFactory.eINSTANCE.createListLayoutStrategyDescription())//
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
                .name(idBuilder.getListItemDomainNodeName(childrenType, metamodelHelper.toEClass(parent.getDomainType()))) // $NON-NLS-1$
                .semanticCandidateExpression(semanticCandidateExpression)//
                .labelExpression(CallQuery.queryServiceOnSelf(Services.RENDER_LABEL_ONE_LINE, "false", "true")) // //$NON-NLS-1$ //$NON-NLS-2$
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .labelEditTool(createDirectEditTool())//
                .deleteTool(viewBuider.createNodeDeleteTool(childrenType.getName())).createTools(creationTools.stream().map(p -> createCreationTool(p.getFirst(), p.getSecond())).collect(toList()))
                .build();

        // Workaround for https://github.com/PapyrusSirius/papyrus-web/issues/164
        NodeDescription fakeNode = new NodeDescriptionBuilder(idBuilder, queryBuilder, childrenType, viewBuider.createIconAndlabelStyle(true), metamodelHelper)//
                .name(idBuilder.getFakeChildNodeId(description)) // $NON-NLS-1$
                .semanticCandidateExpression("aql:Sequence{}") //$NON-NLS-1$
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .createTools(creationTools.stream().map(p -> createSiblingCreationTool(p.getFirst(), p.getSecond())).collect(toList())).build();

        description.getChildrenDescriptions().add(fakeNode);

        return description;
    }

    private LabelEditTool createDirectEditTool() {
        LabelEditTool directEditTool = ViewFactory.eINSTANCE.createLabelEditTool();
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
        return viewBuider.createSiblingCreationTool(idBuilder.getSiblingCreationToolId(newType), Variables.SELF, containementRef, newType); // $NON-NLS-1$
    }

}
