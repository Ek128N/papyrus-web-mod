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
package org.eclipse.papyrus.web.application.representations.uml;

import static java.util.stream.Collectors.toList;
import static org.eclipse.papyrus.web.application.representations.view.aql.CallQuery.queryAttributeOnSelf;
import static org.eclipse.papyrus.web.application.representations.view.aql.OperatorQuery.and;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.SEMANTIC_OTHER_END;

import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.uml.domain.services.EMFUtils;
import org.eclipse.papyrus.uml.domain.services.UMLHelper;
import org.eclipse.papyrus.web.application.representations.view.IDomainHelper;
import org.eclipse.papyrus.web.application.representations.view.IdBuilder;
import org.eclipse.papyrus.web.application.representations.view.StyleProvider;
import org.eclipse.papyrus.web.application.representations.view.aql.CallQuery;
import org.eclipse.papyrus.web.application.representations.view.aql.QueryHelper;
import org.eclipse.papyrus.web.application.representations.view.aql.Services;
import org.eclipse.papyrus.web.application.representations.view.aql.Variables;
import org.eclipse.papyrus.web.application.representations.view.builders.CallbackAdapter;
import org.eclipse.papyrus.web.application.representations.view.builders.LabelConditionalStyleBuilder;
import org.eclipse.papyrus.web.application.representations.view.builders.ListCompartmentBuilder;
import org.eclipse.papyrus.web.application.representations.view.builders.NodeDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.view.builders.NodeSemanticCandidateExpressionTransformer;
import org.eclipse.papyrus.web.application.representations.view.builders.NoteStyleDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.view.builders.ViewBuilder;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DeleteTool;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Abstract implementation of a representation builder.
 * 
 * @author Arthur Daussy
 */
public abstract class AbstractRepresentationDescriptionBuilder {

    /**
     * Prefix used to identify children of packages and model.
     */
    public static final String PACKAGE_CHILD = "inPackage";

    private static final Predicate<NodeDescription> PACKAGE_CHILDREN_FILTER = n -> n.getName().endsWith(PACKAGE_CHILD);

    protected StyleProvider styleProvider;

    private final UMLPackage pack = UMLPackage.eINSTANCE;

    private ViewBuilder viewBuilder;

    private final QueryHelper queryBuilder;

    private final IdBuilder idBuilder;

    private final String representationName;

    private final EClass representationDomainClass;

    private final IDomainHelper umlMetaModelHelper = new UMLMetamodelHelper();

    public AbstractRepresentationDescriptionBuilder(String diagramPrefix, String representationName, EClass domainClass) {
        super();
        this.representationName = representationName;
        this.representationDomainClass = domainClass;
        this.queryBuilder = new QueryHelper(umlMetaModelHelper);
        this.idBuilder = new IdBuilder(diagramPrefix, umlMetaModelHelper);

    }

    protected NodeDescriptionBuilder newNodeBuilder(EClass semanticDomain, NodeStyleDescription style) {
        return new NodeDescriptionBuilder(idBuilder, queryBuilder, semanticDomain, style, umlMetaModelHelper);
    }

    protected ListCompartmentBuilder newListCompartmentBuilder() {
        return new ListCompartmentBuilder(getIdBuilder(), getViewBuilder(), getQueryBuilder(), getUmlMetaModelHelper());
    }

    protected IDomainHelper getUmlMetaModelHelper() {
        return umlMetaModelHelper;
    }

    protected void registerCallback(EObject owner, Runnable r) {
        owner.eAdapters().add(new CallbackAdapter(r));
    }

    public DiagramDescription createDiagramDescription(View view) {

        this.styleProvider = new StyleProvider(view);
        this.viewBuilder = new ViewBuilder(queryBuilder, styleProvider, idBuilder, umlMetaModelHelper);

        DiagramDescription diagramDescription = getViewBuilder().buildDiagramDescription(representationName, representationDomainClass);

        diagramDescription.setTitleExpression(MessageFormat.format("aql:''{0}''", representationName)); //$NON-NLS-1$

        fillDescription(diagramDescription);

        EMFUtils.allContainedObjectOfType(diagramDescription, DiagramElementDescription.class).forEach(this::addConditionalLabelStyle);

        runCallbacks(diagramDescription);

        sortPaletteTools(diagramDescription);

        view.getDescriptions().add(diagramDescription);

        return diagramDescription;
    }

    private boolean mayHaveLabelConditionalLabelStyle(DiagramElementDescription description) {
        final boolean result;
        if (description instanceof EdgeDescription) {
            result = ((EdgeDescription) description).isIsDomainBasedEdge();
        } else if (description instanceof NodeDescription) {
            NodeDescription nodeDescription = (NodeDescription) description;
            result = !IdBuilder.isCompartmentNode(nodeDescription);
        } else {
            result = false;
        }
        return result;

    }

    private void addConditionalLabelStyle(DiagramElementDescription description) {
        if (mayHaveLabelConditionalLabelStyle(description)) {
            EClass domainType = UMLHelper.toEClass(description.getDomainType());

            if (domainType != null) {
                // We use the feature name here to retrieve feature since the style customization can match more than
                // one feature
                // with the same name. For example:
                // * UMLPackage.eINSTANCE.getClassifier_IsAbstract() and
                // * UMLPackage.eINSTANCE.getBehavioralFeature_IsAbstract()

                // Abstract
                EStructuralFeature abstractFeature = domainType.getEStructuralFeature("isAbstract"); //$NON-NLS-1$
                boolean canBeAbstract = abstractFeature != null;

                // Static
                EStructuralFeature staticFeature = domainType.getEStructuralFeature("isStatic"); //$NON-NLS-1$
                boolean canBeStatic = staticFeature != null;

                if (canBeAbstract && canBeStatic) {

                    new LabelConditionalStyleBuilder(description, and(queryAttributeOnSelf(abstractFeature), queryAttributeOnSelf(staticFeature)).toString())//
                            .fromExistingStyle()//
                            .setItalic(true)//
                            .setUnderline(true);

                }

                if (canBeAbstract) {
                    new LabelConditionalStyleBuilder(description, queryAttributeOnSelf(abstractFeature))//
                            .fromExistingStyle()//
                            .setItalic(true);

                }

                if (canBeStatic) {
                    new LabelConditionalStyleBuilder(description, queryAttributeOnSelf(staticFeature))//
                            .fromExistingStyle()//
                            .setUnderline(true);
                }
            }
        }

    }

    private void runCallbacks(DiagramDescription diagramDescription) {

        EMFUtils.eAllContentStreamWithSelf(diagramDescription).forEach(e -> {
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

    protected abstract void fillDescription(DiagramDescription diagramDescription);

    protected ViewBuilder getViewBuilder() {
        return viewBuilder;
    }

    protected QueryHelper getQueryBuilder() {
        return queryBuilder;
    }

    protected IdBuilder getIdBuilder() {
        return idBuilder;
    }

    /**
     * Collects all {@link NodeDescription} matching the given domain (<b>By default the compartment nodes and the list
     * item are excluded).
     * 
     * @param description
     *            the diagram description
     * @param domains
     *            the list of matching domain
     * @return a list of matching {@link NodeDescription}
     * @see IdBuilder#isCompartmentNode(NodeDescription)
     */
    protected List<NodeDescription> collectNodesWithDomain(DiagramDescription description, EClass... domains) {
        return collectNodesWithDomain(description, false, false, domains);
    }

    /**
     * Collects all {@link NodeDescription} matching the given domain.
     * 
     * @param description
     *            the diagram description
     * @param includeCompartment
     *            holds <code>true</code> if the compartment should be included in the result
     * @param domains
     *            the list of matching domain
     * @return a list of matching {@link NodeDescription}
     * @see IdBuilder#isCompartmentNode(NodeDescription)
     */
    protected List<NodeDescription> collectNodesWithDomain(DiagramDescription description, boolean includeCompartment, boolean includeListItem, EClass... domains) {
        return EMFUtils.allContainedObjectOfType(description, NodeDescription.class).filter(node -> isValidNodeDescription(node, includeCompartment, includeListItem, domains)).collect(toList());
    }

    private boolean isCompartmentChildren(NodeDescription n) {
        EObject parent = n.eContainer();
        if (parent instanceof NodeDescription) {
            return IdBuilder.isCompartmentNode((NodeDescription) parent);
        }
        return false;
    }

    protected boolean isValidNodeDescription(NodeDescription node, boolean includeCompartment, boolean includeListItem, EClass... domains) {
        final boolean result;
        if (!includeCompartment && IdBuilder.isCompartmentNode(node)) {
            result = false;
        } else if (!includeCompartment && isCompartmentChildren(node)) {
            result = false;
        } else {
            EClass targetDomain = UMLHelper.toEClass(node.getDomainType());
            if (targetDomain != null) {
                result = Stream.of(domains).anyMatch(d -> d.isSuperTypeOf(targetDomain));
            } else {
                result = false;
            }
        }
        return result && !IdBuilder.isFakeChildNode(node);
    }

    /**
     * Creates a {@link NodeDescription} representing a {@link Comment} in the provided {@code diagramDescription}.
     * <p>
     * This method is <b>deprecated</b>. Use {@link NoteStyleDescriptionBuilder} to create note-style
     * {@link NodeDescription}.
     * </p>
     * 
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the {@link NodeDescription}
     */
    @Deprecated
    protected void createCommentDescription(DiagramDescription diagramDescription) {
        NodeDescription commentDescription = getViewBuilder().createNoteStyleUnsynchonizedNodeDescription(pack.getComment(), getQueryBuilder().queryAllReachable(pack.getComment()));
        commentDescription.getStyle().setWidthComputationExpression("200");
        commentDescription.getStyle().setHeightComputationExpression("100");

        ImageNodeStyleDescription style = (ImageNodeStyleDescription) commentDescription.getStyle();
        style.setShowIcon(false);
        // TODO uncomment next line when setColor is restored possibly in 2023.10.0
        // style.setColor(styleProvider.getNoteColor());
        diagramDescription.getNodeDescriptions().add(commentDescription);
        diagramDescription.getPalette().getNodeTools().add(getViewBuilder().createCreationTool(pack.getElement_OwnedComment(), pack.getComment()));

        EdgeDescription annotedElementEdge = getViewBuilder().createFeatureEdgeDescription(//
                getIdBuilder().getFeatureBaseEdgeId(pack.getComment_AnnotatedElement()), //
                getQueryBuilder().emptyString(), //
                queryAttributeOnSelf(pack.getComment_AnnotatedElement()), //
                () -> collectNodesWithDomain(diagramDescription, pack.getComment()), //
                () -> collectNodesWithDomain(diagramDescription, pack.getElement()));

        DeleteTool deleteTool = DiagramFactory.eINSTANCE.createDeleteTool();
        deleteTool.setName("Remove annotated element"); //$NON-NLS-1$
        ChangeContext createElement = ViewFactory.eINSTANCE.createChangeContext();
        createElement
                .setExpression(CallQuery.queryServiceOnSelf(Services.REMOVE_VALUE_FROM, getQueryBuilder().aqlString(pack.getComment_AnnotatedElement().getName()), Variables.SEMANTIC_EDGE_TARGET));
        deleteTool.getBody().add(createElement);

        annotedElementEdge.getPalette().setDeleteTool(deleteTool);

        addAnnotatedElementReconnectionTools(annotedElementEdge);

        annotedElementEdge.getStyle().setTargetArrowStyle(ArrowStyle.NONE);
        annotedElementEdge.getStyle().setLineStyle(LineStyle.DASH);
        diagramDescription.getEdgeDescriptions().add(annotedElementEdge);

        EdgeTool creationTool = getViewBuilder().createFeatureBasedEdgeTool("Link", //$NON-NLS-1$
                getQueryBuilder().queryAddValueTo(Variables.SEMANTIC_EDGE_SOURCE, pack.getComment_AnnotatedElement(), Variables.SEMANTIC_EDGE_TARGET), //
                collectNodesWithDomain(diagramDescription, pack.getElement()));
        commentDescription.getPalette().getEdgeTools().add(creationTool);

    }

    private void addAnnotatedElementReconnectionTools(EdgeDescription annotedElementEdge) {
        ChangeContext sourceReconnectionOperation = getViewBuilder().createChangeContextOperation(new CallQuery(SEMANTIC_OTHER_END)
                .callService(Services.RECONNECT_COMMENT_ANNOTATED_ELEMENT_EDGE_SOURCE_SERVICE, Variables.SEMANTIC_RECONNECTION_SOURCE, Variables.SEMANTIC_RECONNECTION_TARGET));
        annotedElementEdge.getPalette().getEdgeReconnectionTools().add(getViewBuilder().createSourceReconnectionTool(annotedElementEdge, //
                getIdBuilder().getSourceReconnectionToolId(annotedElementEdge), //
                List.of(sourceReconnectionOperation)));

        ChangeContext targetReconnectionOperation = getViewBuilder().createChangeContextOperation(//
                new CallQuery(Variables.EDGE_SEMANTIC_ELEMENT).callService(Services.RECONNECT_COMMENT_ANNOTATED_ELEMENT_EDGE_TARGET_SERVICE, Variables.SEMANTIC_RECONNECTION_SOURCE, // $NON-NLS-1$
                        Variables.SEMANTIC_RECONNECTION_TARGET));
        annotedElementEdge.getPalette().getEdgeReconnectionTools().add(getViewBuilder().createTargetReconnectionTool(annotedElementEdge, //
                getIdBuilder().getTargetReconnectionToolId(annotedElementEdge), //
                List.of(targetReconnectionOperation)));
    }

    /**
     * Configures the provided {@code node} as a {@link Comment} owner.
     * <p>
     * This method ensures that the provided {@code node}'s child descriptions contain the {@link NodeDescription}
     * representing a {@link Comment}, and that the palette allows the creation of {@link Comment} elements inside
     * {@code node}.
     * </p>
     * 
     * @param node
     *            the node to configure as a comment owner
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the node
     */
    protected void registerNodeAsCommentOwner(NodeDescription node, DiagramDescription diagramDescription) {
        registerCallback(node, () -> {
            node.getReusedChildNodeDescriptions().addAll(collectNodesWithDomain(diagramDescription, pack.getComment()));
            node.getPalette().getNodeTools().add(getViewBuilder().createCreationTool(pack.getElement_OwnedComment(), pack.getComment()));
        });
    }

    /**
     * Configures the provided {@code node} as a {@link Constraint} owner.
     * <p>
     * This method ensures that the provided {@code node}'s child descriptions contain the {@link NodeDescription}
     * representing a {@link Constraint}, and that the palette allows the creation of {@link Constraint} elements inside
     * {@code node}.
     * 
     * @param node
     *            the node to configure as a constraint owner
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the node
     */
    protected void registerNodeAsConstraintOwner(NodeDescription node, DiagramDescription diagramDescription) {
        registerCallback(node, () -> {
            node.getReusedChildNodeDescriptions().addAll(collectNodesWithDomain(diagramDescription, pack.getConstraint()));
            node.getPalette().getNodeTools().add(getViewBuilder().createCreationTool(pack.getNamespace_OwnedRule(), pack.getConstraint()));
        });
    }

    /**
     * Collects all {@link NodeDescriptions} with the given type and matching the given predicate. Then add them to the
     * reused children or reused border children feature.
     * 
     * @param parent
     *            the parent node
     * @param type
     *            the domain of the {@link NodeDescription} to collect
     * @param diagramDescription
     *            the root container of the candidates {@link NodeDescription}s
     * @param filter
     *            a extra filter to select the candidates
     */
    protected void collectAndReusedChildNodes(NodeDescription parent, EClass type, DiagramDescription diagramDescription, Predicate<NodeDescription> filter) {
        registerCallback(parent, () -> {
            List<NodeDescription> childrenCandidates = collectNodesWithDomain(diagramDescription, type).stream().filter(filter).collect(toList());
            for (var candidate : childrenCandidates) {
                if (candidate.eContainingFeature() == DiagramPackage.eINSTANCE.getNodeDescription_BorderNodesDescriptions()) {
                    parent.getReusedBorderNodeDescriptions().addAll(childrenCandidates);
                } else if (candidate.eContainingFeature() == DiagramPackage.eINSTANCE.getNodeDescription_ChildrenDescriptions()) {
                    parent.getReusedChildNodeDescriptions().addAll(childrenCandidates);
                }
            }
        });
    }

    protected void createModelDescription(DiagramDescription diagramDescription) {
        NodeDescription padModel = getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(pack.getModel(), getQueryBuilder().queryAllReachable(pack.getModel()));
        diagramDescription.getNodeDescriptions().add(padModel);
        diagramDescription.getPalette().getNodeTools().add(getViewBuilder().createCreationTool(pack.getPackage_PackagedElement(), pack.getModel()));

        // TODO uncomment next line when setColor is restored possibly in 2023.10.0
        // padModel.getStyle().setColor(styleProvider.getModelColor());
        collectAndReusedChildNodes(padModel, pack.getPackageableElement(), diagramDescription, PACKAGE_CHILDREN_FILTER);

        registerNodeAsCommentOwner(padModel, diagramDescription);
    }

    protected void createPackageDescription(DiagramDescription diagramDescription) {
        NodeDescription padPackage = getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(pack.getPackage(), getQueryBuilder().queryAllReachable(pack.getPackage()));
        diagramDescription.getNodeDescriptions().add(padPackage);

        diagramDescription.getPalette().getNodeTools().add(getViewBuilder().createCreationTool(pack.getPackage_PackagedElement(), pack.getPackage()));

        registerCallback(padPackage, () -> {
            List<NodeDescription> packages = collectNodesWithDomain(diagramDescription, pack.getPackage());
            packages.forEach(p -> {
                p.getPalette().getNodeTools().add(getViewBuilder().createCreationTool(pack.getPackage_PackagedElement(), pack.getPackage()));
                p.getPalette().getNodeTools().add(getViewBuilder().createCreationTool(pack.getPackage_PackagedElement(), pack.getModel()));
            });
            String childrenCandidateExpression = CallQuery.queryAttributeOnSelf(UMLPackage.eINSTANCE.getPackage_PackagedElement());
            List<NodeDescription> copiedClassifier = diagramDescription.getNodeDescriptions().stream()
                    // Filter out NodeDescription representing a constraint. They can be contained by the
                    // Package.packagedElement reference (they are PackageableElements), but we don't want to support
                    // that, we want to support Namespace.ownedRule as the sole containment reference for constraints.
                    .filter(n -> isValidNodeDescription(n, false, false, pack.getPackageableElement()) && !isValidNodeDescription(n, false, false, pack.getConstraint()))
                    .map(n -> transformIntoPackageChildNode(n, childrenCandidateExpression, diagramDescription)).toList();
            padPackage.getChildrenDescriptions().addAll(copiedClassifier);
        });

        registerNodeAsCommentOwner(padPackage, diagramDescription);
        // Do not use registerNodeAsConstraintOwner here, this would have an impact on CDDiagramDescriptionBuilder,
        // CSDDiagramDescriptionBuilder, PADDiagramDescriptionBuilder, and SMDDigramDescriptionBuilder.
    }

    private NodeDescription transformIntoPackageChildNode(NodeDescription input, String semanticCandidateExpression, DiagramDescription diagramDescription) {
        EClass eClass = UMLHelper.toEClass(input.getDomainType());
        String id = getIdBuilder().getSpecializedDomainNodeName(eClass, PACKAGE_CHILD);
        NodeDescription n = new NodeSemanticCandidateExpressionTransformer().intoNewCanidateExpression(id, input, semanticCandidateExpression);

        if (UMLPackage.eINSTANCE.getPackage().isSuperTypeOf(eClass)) {
            collectAndReusedChildNodes(n, pack.getPackageableElement(), diagramDescription, PACKAGE_CHILDREN_FILTER);
            registerNodeAsCommentOwner(n, diagramDescription);
            // Do not use registerNodeAsConstraintOwner here, this would have an impact on CDDiagramDescriptionBuilder,
            // CSDDiagramDescriptionBuilder, PADDiagramDescriptionBuilder, and SMDDigramDescriptionBuilder.
        }
        return n;
    }

    /**
     * Comparator of {@link Tool} based of alphabetic order of the name of the tool.
     * 
     * @author Arthur Daussy
     */
    class ToolComparator implements Comparator<Tool> {
        @Override
        public int compare(Tool obj1, Tool obj2) {
            int res;
            if (obj1 == obj2) {
                res = 0;
            } else if (obj1 == null) {
                res = -1;
            } else if (obj2 == null) {
                res = 1;
            } else {
                res = obj1.getName().compareTo(obj2.getName());
            }
            return res;
        }
    }

    private void sortPaletteTools(DiagramDescription diagramDescription) {
        ToolComparator comparator = new ToolComparator();
        // diagram palette first
        ECollections.sort(diagramDescription.getPalette().getNodeTools(), comparator);
        diagramDescription.getNodeDescriptions().forEach(node -> sortPaletteTools(node, comparator));
        diagramDescription.getEdgeDescriptions().forEach(edge -> {
            ECollections.sort(edge.getPalette().getNodeTools(), comparator);
        });
    }

    private void sortPaletteTools(NodeDescription nodeDescription, ToolComparator comparator) {

        ECollections.sort(nodeDescription.getPalette().getNodeTools(), comparator);
        ECollections.sort(nodeDescription.getPalette().getEdgeTools(), comparator);
        nodeDescription.getChildrenDescriptions().forEach(node -> sortPaletteTools(node, comparator));
        nodeDescription.getBorderNodesDescriptions().forEach(node -> sortPaletteTools(node, comparator));
    }

}
