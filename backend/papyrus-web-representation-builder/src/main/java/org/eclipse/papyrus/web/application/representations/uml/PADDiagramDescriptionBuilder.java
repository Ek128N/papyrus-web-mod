/*****************************************************************************
 * Copyright (c) 2022, 2024 CEA LIST, Obeo, Artal Technologies.
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
 *  Aurelien Didier (Artal Technologies) - Issue 190, 229
 *  Titouan BOUETE-GIRAUD (Artal Technologies) - titouan.bouete-giraud@artal.fr - Issues 219, 227
 *****************************************************************************/
package org.eclipse.papyrus.web.application.representations.uml;

import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.SEMANTIC_EDGE_SOURCE;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.SEMANTIC_EDGE_TARGET;

import java.util.List;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.web.application.representations.view.CreationToolsUtil;
import org.eclipse.papyrus.web.application.representations.view.aql.CallQuery;
import org.eclipse.papyrus.web.application.representations.view.aql.Services;
import org.eclipse.papyrus.web.application.representations.view.aql.Variables;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.DiagramToolSection;
import org.eclipse.sirius.components.view.diagram.DropNodeTool;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Builder of the "Package Diagram" diagram representation.
 *
 * @author Arthur Daussy
 */
public class PADDiagramDescriptionBuilder extends AbstractRepresentationDescriptionBuilder {

    public static final String PD_REP_NAME = "Package Diagram";

    public static final String PAD_PREFIX = "PAD_";

    public static final String CONTAINMENT_LINK_EDGE_ID = PAD_PREFIX + "_ContainmentLink_FeatureEdge";

    public static final String SYMBOLS_COMPARTMENT_SUFFIX = "Symbols";

    public static final String SHOW_HIDE = "SHOW_HIDE";

    /**
     * The <i>shared</i> {@link NodeDescription} for the diagram.
     */
    private NodeDescription padSharedDescription;

    private final UMLPackage pack = UMLPackage.eINSTANCE;

    public PADDiagramDescriptionBuilder() {
        super(PAD_PREFIX, PD_REP_NAME, UMLPackage.eINSTANCE.getPackage());
    }

    @Override
    protected void fillDescription(DiagramDescription diagramDescription) {

        this.createDefaultToolSectionInDiagramDescription(diagramDescription);

        this.createModelTopNodeDescription(diagramDescription);
        this.createPackageTopNodeDescription(diagramDescription);
        this.createCommentTopNodeDescription(diagramDescription, NODES);
        this.createConstraintTopNodeDescription(diagramDescription, NODES);

        // create shared node descriptions with their tools
        this.padSharedDescription = this.createSharedDescription(diagramDescription);

        this.createPackageSharedNodeDescription(diagramDescription);
        this.createModelSharedNodeDescription(diagramDescription);
        this.createCommentSubNodeDescription(diagramDescription, this.padSharedDescription, NODES,
                this.getIdBuilder().getSpecializedDomainNodeName(this.pack.getComment(), SHARED_SUFFIX), List.of(this.pack.getPackage()));
        this.createConstraintSubNodeDescription(diagramDescription, this.padSharedDescription, NODES,
                this.getIdBuilder().getSpecializedDomainNodeName(this.pack.getConstraint(), SHARED_SUFFIX), List.of(this.pack.getPackage()));

        DiagramToolSection showHideToolSection = this.getViewBuilder().createDiagramToolSection(SHOW_HIDE);
        diagramDescription.getPalette().getToolSections().add(showHideToolSection);
        this.createHideSymbolTool(diagramDescription,
                SHOW_HIDE);
        this.createShowSymbolTool(diagramDescription, SHOW_HIDE);
        this.createHideAllNonSymbolCompartmentTool(diagramDescription, SHOW_HIDE);
        this.createShowAllNonSymbolCompartmentTool(diagramDescription, SHOW_HIDE);

        this.createPackageMergeDescription(diagramDescription);
        this.createPackageImportDescription(diagramDescription);
        this.createAbstractionDescription(diagramDescription);
        this.createDependencyDescription(diagramDescription);
        this.createContainmentLink(diagramDescription);

        DropNodeTool padGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getDiagramGraphicalDropToolName());
        List<EClass> children = List.of(this.pack.getModel(), this.pack.getPackage(), this.pack.getComment(), this.pack.getConstraint());
        this.registerCallback(diagramDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilterWithoutContent(diagramDescription, children, List.of());
            padGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        diagramDescription.getPalette().setDropNodeTool(padGraphicalDropTool);

        List<EClass> symbolOwners = List.of(
                this.pack.getPackage());
        this.createSymbolSharedNodeDescription(diagramDescription, this.padSharedDescription, symbolOwners, List.of(), SYMBOLS_COMPARTMENT_SUFFIX);

        diagramDescription.getPalette().setDropTool(this.getViewBuilder().createGenericSemanticDropTool(this.getIdBuilder().getDiagramSemanticDropToolName()));
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link Model}.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     *
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createModelSharedNodeDescription(DiagramDescription diagramDescription) {
        EClass modelEClass = this.pack.getModel();
        ListLayoutStrategyDescription llsd = this.createListLayoutStrategy();
        NodeDescription padModelHolderSharedNodeDescription = this.getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(modelEClass,
                CallQuery.queryAttributeOnSelf(this.pack.getPackage_PackagedElement()));
        padModelHolderSharedNodeDescription.setName(this.getIdBuilder().getSpecializedDomainNodeName(modelEClass, SHARED_SUFFIX + UNDERSCORE + HOLDER_SUFFIX));
        padModelHolderSharedNodeDescription.setStyle(this.getViewBuilder().createPackageNodeStyle());
        padModelHolderSharedNodeDescription.setInsideLabel(this.getViewBuilder().createDefaultInsideLabelDescription(true, true));

        NodeDescription padModelContentSharedNodeDescription = this.createContentNodeDescription(modelEClass, true);
        this.addContent(modelEClass, true, padModelHolderSharedNodeDescription, padModelContentSharedNodeDescription);
        this.copyDimension(padModelHolderSharedNodeDescription, padModelContentSharedNodeDescription);

        this.padSharedDescription.getChildrenDescriptions().add(padModelHolderSharedNodeDescription);
        padModelHolderSharedNodeDescription.getChildrenDescriptions().add(padModelContentSharedNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(padModelContentSharedNodeDescription);

        NodeTool padModelSharedNodeCreationTool = this.getViewBuilder().createCreationTool(this.pack.getPackage_PackagedElement(), modelEClass);
        List<EClass> owners = List.of(this.pack.getPackage(), this.pack.getModel());
        this.reuseNodeAndCreateTool(padModelHolderSharedNodeDescription, diagramDescription,
                padModelSharedNodeCreationTool, NODES, owners, List.of());

        llsd.getGrowableNodes().add(padModelContentSharedNodeDescription);
        padModelHolderSharedNodeDescription.setChildrenLayoutStrategy(llsd);

        // Add dropped tool on Shared Package container
        DropNodeTool padModelGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(padModelContentSharedNodeDescription));
        List<EClass> children = List.of(this.pack.getPackage(), this.pack.getModel(), this.pack.getComment(), this.pack.getConstraint());
        this.registerCallback(padModelContentSharedNodeDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilterWithoutContent(diagramDescription, children, List.of());
            padModelGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        padModelContentSharedNodeDescription.getPalette().setDropNodeTool(padModelGraphicalDropTool);
    }

    /**
     * Creates the shared {@link NodeDescription} representing an UML {@link Package}.
     * <p>
     * The created {@link NodeDescription} is added to the <i>shared</i> {@link NodeDescription} of the diagram.
     * </p>
     *
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createPackageSharedNodeDescription(DiagramDescription diagramDescription) {
        EClass packageEClass = this.pack.getPackage();
        ListLayoutStrategyDescription llsd = this.createListLayoutStrategy();
        NodeDescription padPackageHolderSharedNodeDescription = this.getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(packageEClass,
                CallQuery.queryAttributeOnSelf(this.pack.getPackage_PackagedElement()));
        padPackageHolderSharedNodeDescription.setName(this.getIdBuilder().getSpecializedDomainNodeName(packageEClass, SHARED_SUFFIX + UNDERSCORE + HOLDER_SUFFIX));
        padPackageHolderSharedNodeDescription.setStyle(this.getViewBuilder().createPackageNodeStyle());
        padPackageHolderSharedNodeDescription.setInsideLabel(this.getViewBuilder().createDefaultInsideLabelDescription(true, true));

        NodeDescription padPackageContentSharedNodeDescription = this.createContentNodeDescription(packageEClass, true);
        this.addContent(packageEClass, true, padPackageHolderSharedNodeDescription, padPackageContentSharedNodeDescription);
        this.copyDimension(padPackageHolderSharedNodeDescription, padPackageContentSharedNodeDescription);
        this.padSharedDescription.getChildrenDescriptions().add(padPackageHolderSharedNodeDescription);

        this.createDefaultToolSectionsInNodeDescription(padPackageContentSharedNodeDescription);

        NodeTool padPackageSharedNodeCreationTool = this.getViewBuilder().createCreationTool(this.pack.getPackage_PackagedElement(), packageEClass);
        List<EClass> owners = List.of(this.pack.getPackage(), this.pack.getModel());
        this.reuseNodeAndCreateTool(padPackageHolderSharedNodeDescription, diagramDescription,
                padPackageSharedNodeCreationTool, NODES, owners, List.of());

        llsd.getGrowableNodes().add(padPackageContentSharedNodeDescription);
        padPackageHolderSharedNodeDescription.setChildrenLayoutStrategy(llsd);

        // Add dropped tool on Shared Package container
        DropNodeTool padPackageGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(padPackageContentSharedNodeDescription));
        List<EClass> children = List.of(this.pack.getPackage(), this.pack.getModel(), this.pack.getComment(), this.pack.getConstraint());
        this.registerCallback(padPackageContentSharedNodeDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilterWithoutContent(diagramDescription, children, List.of());
            padPackageGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        padPackageContentSharedNodeDescription.getPalette().setDropNodeTool(padPackageGraphicalDropTool);
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Package} on the Diagram.
     *
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createPackageTopNodeDescription(DiagramDescription diagramDescription) {
        ListLayoutStrategyDescription llsd = this.createListLayoutStrategy();
        EClass packageEClass = this.pack.getPackage();
        NodeDescription padPackageHolderTopNodeDescription = this.getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(packageEClass,
                this.getQueryBuilder().queryAllReachableExactType(packageEClass));
        padPackageHolderTopNodeDescription.setName(this.getIdBuilder().getSpecializedDomainNodeName(packageEClass, HOLDER_SUFFIX));
        padPackageHolderTopNodeDescription.setInsideLabel(this.getViewBuilder().createDefaultInsideLabelDescription(true, true));

        padPackageHolderTopNodeDescription.setStyle(this.getViewBuilder().createPackageNodeStyle());

        NodeDescription padPackageContentTopNodeDescription = this.createContentNodeDescription(packageEClass, false);
        this.copyDimension(padPackageHolderTopNodeDescription, padPackageContentTopNodeDescription);
        this.addContent(packageEClass, false, padPackageHolderTopNodeDescription, padPackageContentTopNodeDescription);
        diagramDescription.getNodeDescriptions().add(padPackageHolderTopNodeDescription);

        // create Package tool sections
        this.createDefaultToolSectionsInNodeDescription(padPackageHolderTopNodeDescription);

        NodeTool padPackageTopNodeCreationTool = this.getViewBuilder().createCreationTool(this.pack.getPackage_PackagedElement(), packageEClass);
        this.addDiagramToolInToolSection(diagramDescription, padPackageTopNodeCreationTool, NODES);

        // No direct children for Package: the NodeDescriptions it can contain are all defined as shared descriptions.

        // Add dropped tool on Package container
        DropNodeTool padPackageGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(padPackageHolderTopNodeDescription));
        List<EClass> children = List.of(this.pack.getPackage(), this.pack.getModel(), this.pack.getComment(), this.pack.getConstraint());
        this.registerCallback(padPackageContentTopNodeDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilterWithoutContent(diagramDescription, children, List.of());
            padPackageGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        padPackageContentTopNodeDescription.getPalette().setDropNodeTool(padPackageGraphicalDropTool);
    }

    /**
     * Creates the {@link NodeDescription} representing an UML {@link Model} on the Diagram.
     *
     * @param diagramDescription
     *            the {@link DiagramDescription} containing the created {@link NodeDescription}
     */
    private void createModelTopNodeDescription(DiagramDescription diagramDescription) {
        EClass modelEClass = this.pack.getModel();
        ListLayoutStrategyDescription llsd = this.createListLayoutStrategy();
        NodeDescription padModelHolderTopNodeDescription = this.getViewBuilder().createPackageStyleUnsynchonizedNodeDescription(modelEClass,
                this.getQueryBuilder().queryAllReachableExactType(modelEClass));
        padModelHolderTopNodeDescription.setName(this.getIdBuilder().getSpecializedDomainNodeName(modelEClass, HOLDER_SUFFIX));
        padModelHolderTopNodeDescription.setInsideLabel(this.getViewBuilder().createDefaultInsideLabelDescription(true, true));
        padModelHolderTopNodeDescription.setStyle(this.getViewBuilder().createPackageNodeStyle());

        NodeDescription padModelContentTopNodeDescription = this.createContentNodeDescription(modelEClass, false);
        this.addContent(modelEClass, false, padModelHolderTopNodeDescription, padModelContentTopNodeDescription);
        this.copyDimension(padModelHolderTopNodeDescription, padModelContentTopNodeDescription);

        diagramDescription.getNodeDescriptions().add(padModelHolderTopNodeDescription);

        // create tool
        this.createDefaultToolSectionsInNodeDescription(padModelHolderTopNodeDescription);

        NodeTool padProfileTopNodeCreationTool = this.getViewBuilder().createCreationTool(this.pack.getPackage_PackagedElement(), modelEClass);
        this.addDiagramToolInToolSection(diagramDescription, padProfileTopNodeCreationTool, NODES);

        // Add dropped tool on Profile container
        DropNodeTool padModelGraphicalDropTool = this.getViewBuilder().createGraphicalDropTool(this.getIdBuilder().getNodeGraphicalDropToolName(padModelHolderTopNodeDescription));
        List<EClass> children = List.of(this.pack.getPackage(), this.pack.getModel(), this.pack.getComment(), this.pack.getConstraint());
        this.registerCallback(padModelContentTopNodeDescription, () -> {
            List<NodeDescription> droppedNodeDescriptions = this.collectNodesWithDomainAndFilterWithoutContent(diagramDescription, children, List.of());
            padModelGraphicalDropTool.getAcceptedNodeTypes().addAll(droppedNodeDescriptions);
        });
        padModelContentTopNodeDescription.getPalette().setDropNodeTool(padModelGraphicalDropTool);
    }

    private void createContainmentLink(DiagramDescription diagramDescription) {

        Supplier<List<NodeDescription>> sourceAndTargetProvider = () -> this.collectNodesWithDomainAndWithoutContent(diagramDescription, this.pack.getPackage());

        EdgeDescription containmentLinkEdge = this.getViewBuilder().createFeatureEdgeDescription(//
                CONTAINMENT_LINK_EDGE_ID, //
                this.getQueryBuilder().emptyString(), //
                CallQuery.queryAttributeOnSelf(this.pack.getPackage_NestedPackage()), //
                sourceAndTargetProvider, //
                sourceAndTargetProvider);

        containmentLinkEdge.setPreconditionExpression(new CallQuery(Variables.GRAPHICAL_EDGE_SOURCE).callService(Services.IS_NOT_VISUAL_DESCENDANT, Variables.GRAPHICAL_EDGE_TARGET, Variables.CACHE));

        containmentLinkEdge.getStyle().setSourceArrowStyle(ArrowStyle.CROSSED_CIRCLE);

        diagramDescription.getEdgeDescriptions().add(containmentLinkEdge);
        this.registerCallback(containmentLinkEdge, () -> {
            // Create containment Link tool
            String toolQuery = new CallQuery(SEMANTIC_EDGE_TARGET).callService(Services.MOVE_IN, SEMANTIC_EDGE_SOURCE);
            EdgeTool tool = this.getViewBuilder().createFeatureBasedEdgeTool("New Containment Link", toolQuery,
                    this.collectNodesWithDomainAndWithoutContent(diagramDescription, this.pack.getPackage()));
            CreationToolsUtil.addEdgeCreationTool(sourceAndTargetProvider, tool);
        });
    }

    private void addCreationToolOnNamedElement(DiagramDescription diagramDescription, DiagramElementDescription desc, EdgeTool tool) {
        Supplier<List<NodeDescription>> namedElementDescriptions = () -> this.collectNodesWithDomainAndWithoutContent(diagramDescription, this.pack.getNamedElement());
        this.registerCallback(desc, () -> {
            CreationToolsUtil.addEdgeCreationTool(namedElementDescriptions, tool);
        });
    }

    private void addCreationToolOnPackage(DiagramDescription diagramDescription, DiagramElementDescription desc, EdgeTool tool) {
        Supplier<List<NodeDescription>> packageDescriptions = () -> this.collectNodesWithDomainAndWithoutContent(diagramDescription, this.pack.getPackage());
        this.registerCallback(desc, () -> {
            CreationToolsUtil.addEdgeCreationTool(packageDescriptions, tool);
        });
    }

    private void createDependencyDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> namedElementDescriptions = () -> this.collectNodesWithDomainAndWithoutContent(diagramDescription, this.pack.getNamedElement());
        EdgeDescription padDependency = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.pack.getDependency(),
                this.getQueryBuilder().queryAllReachableExactType(this.pack.getDependency()), namedElementDescriptions, namedElementDescriptions);
        padDependency.getStyle().setLineStyle(LineStyle.DASH);
        padDependency.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        this.addCreationToolOnNamedElement(diagramDescription, padDependency, this.getViewBuilder().createDefaultDomainBasedEdgeTool(padDependency, this.pack.getPackage_PackagedElement()));

        diagramDescription.getEdgeDescriptions().add(padDependency);

        this.getViewBuilder().addDefaultReconnectionTools(padDependency);
    }

    private void createAbstractionDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> namedElementDescriptions = () -> this.collectNodesWithDomainAndWithoutContent(diagramDescription, this.pack.getNamedElement());
        EdgeDescription padAbstraction = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.pack.getAbstraction(),
                this.getQueryBuilder().queryAllReachable(this.pack.getAbstraction()), namedElementDescriptions, namedElementDescriptions);
        padAbstraction.getStyle().setLineStyle(LineStyle.DASH);
        padAbstraction.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        this.addCreationToolOnNamedElement(diagramDescription, padAbstraction, this.getViewBuilder().createDefaultDomainBasedEdgeTool(padAbstraction, this.pack.getPackage_PackagedElement()));
        diagramDescription.getEdgeDescriptions().add(padAbstraction);

        this.getViewBuilder().addDefaultReconnectionTools(padAbstraction);
    }

    private void createPackageMergeDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> packageDescriptions = () -> this.collectNodesWithDomainAndWithoutContent(diagramDescription, this.pack.getPackage());
        EdgeDescription padPackageMerge = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.pack.getPackageMerge(),
                this.getQueryBuilder().queryAllReachable(this.pack.getPackageMerge()), packageDescriptions, packageDescriptions);
        padPackageMerge.getStyle().setLineStyle(LineStyle.DASH);
        padPackageMerge.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        this.addCreationToolOnPackage(diagramDescription, padPackageMerge, this.getViewBuilder().createDefaultDomainBasedEdgeTool(padPackageMerge, this.pack.getPackage_PackageMerge()));

        diagramDescription.getEdgeDescriptions().add(padPackageMerge);
        this.getViewBuilder().addDefaultReconnectionTools(padPackageMerge);
    }

    private void createPackageImportDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> packageDescriptions = () -> this.collectNodesWithDomainAndWithoutContent(diagramDescription, this.pack.getPackage());
        EdgeDescription padPackageImport = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.pack.getPackageImport(),
                this.getQueryBuilder().queryAllReachable(this.pack.getPackageImport()), packageDescriptions, packageDescriptions);
        padPackageImport.getStyle().setLineStyle(LineStyle.DASH);
        padPackageImport.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);

        this.addCreationToolOnPackage(diagramDescription, padPackageImport, this.getViewBuilder().createDefaultDomainBasedEdgeTool(padPackageImport, this.pack.getNamespace_PackageImport()));
        diagramDescription.getEdgeDescriptions().add(padPackageImport);
        this.getViewBuilder().addDefaultReconnectionTools(padPackageImport);

    }

}
