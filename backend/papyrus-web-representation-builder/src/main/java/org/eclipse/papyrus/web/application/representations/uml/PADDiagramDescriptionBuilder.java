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

import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.SEMANTIC_EDGE_SOURCE;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.SEMANTIC_EDGE_TARGET;

import java.util.List;
import java.util.function.Supplier;

import org.eclipse.papyrus.web.application.representations.view.CreationToolsUtil;
import org.eclipse.papyrus.web.application.representations.view.aql.CallQuery;
import org.eclipse.papyrus.web.application.representations.view.aql.Services;
import org.eclipse.papyrus.web.application.representations.view.aql.Variables;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
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

    private final UMLPackage pack = UMLPackage.eINSTANCE;

    public PADDiagramDescriptionBuilder() {
        super(PAD_PREFIX, PD_REP_NAME, UMLPackage.eINSTANCE.getPackage());
    }

    @Override
    protected void fillDescription(DiagramDescription diagramDescription) {

        this.createPackageDescription(diagramDescription);
        this.createModelDescription(diagramDescription);

        this.createPackageMergeDescription(diagramDescription);
        this.createPackageImportDescription(diagramDescription);
        this.createAbstractionDescription(diagramDescription);
        this.createDependencyDescription(diagramDescription);
        this.createContainmentLink(diagramDescription);

        this.createCommentDescription(diagramDescription);

        diagramDescription.getPalette().setDropTool(this.getViewBuilder().createGenericSemanticDropTool(this.getIdBuilder().getDiagramSemanticDropToolName()));
    }

    private void createContainmentLink(DiagramDescription diagramDescription) {

        Supplier<List<NodeDescription>> sourceAndTargetProvider = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getPackage());

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
            EdgeTool tool = this.getViewBuilder().createFeatureBasedEdgeTool("New Containment Link", toolQuery, this.collectNodesWithDomain(diagramDescription, this.pack.getPackage()));
            CreationToolsUtil.addEdgeCreationTool(sourceAndTargetProvider, tool);
        });
    }

    private void addCreationToolOnNamedElement(DiagramDescription diagramDescription, DiagramElementDescription desc, EdgeTool tool) {
        Supplier<List<NodeDescription>> namedElementDescriptions = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getNamedElement());
        this.registerCallback(desc, () -> {
            CreationToolsUtil.addEdgeCreationTool(namedElementDescriptions, tool);
        });
    }

    private void addCreationToolOnPackage(DiagramDescription diagramDescription, DiagramElementDescription desc, EdgeTool tool) {
        Supplier<List<NodeDescription>> packageDescriptions = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getPackage());
        this.registerCallback(desc, () -> {
            CreationToolsUtil.addEdgeCreationTool(packageDescriptions, tool);
        });
    }

    private void createDependencyDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> namedElementDescriptions = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getNamedElement());
        EdgeDescription padDependency = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.pack.getDependency(),
                this.getQueryBuilder().queryAllReachableExactType(this.pack.getDependency()), namedElementDescriptions, namedElementDescriptions);
        padDependency.getStyle().setLineStyle(LineStyle.DASH);
        padDependency.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        this.addCreationToolOnNamedElement(diagramDescription, padDependency, this.getViewBuilder().createDefaultDomainBasedEdgeTool(padDependency, this.pack.getPackage_PackagedElement()));

        diagramDescription.getEdgeDescriptions().add(padDependency);

        this.getViewBuilder().addDefaultReconnectionTools(padDependency);
    }

    private void createAbstractionDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> namedElementDescriptions = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getNamedElement());
        EdgeDescription padAbstraction = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.pack.getAbstraction(),
                this.getQueryBuilder().queryAllReachable(this.pack.getAbstraction()), namedElementDescriptions, namedElementDescriptions);
        padAbstraction.getStyle().setLineStyle(LineStyle.DASH);
        padAbstraction.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        this.addCreationToolOnNamedElement(diagramDescription, padAbstraction, this.getViewBuilder().createDefaultDomainBasedEdgeTool(padAbstraction, this.pack.getPackage_PackagedElement()));
        diagramDescription.getEdgeDescriptions().add(padAbstraction);

        this.getViewBuilder().addDefaultReconnectionTools(padAbstraction);
    }

    private void createPackageMergeDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> packageDescriptions = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getPackage());
        EdgeDescription padPackageMerge = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.pack.getPackageMerge(),
                this.getQueryBuilder().queryAllReachable(this.pack.getPackageMerge()), packageDescriptions, packageDescriptions);
        padPackageMerge.getStyle().setLineStyle(LineStyle.DASH);
        padPackageMerge.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        this.addCreationToolOnPackage(diagramDescription, padPackageMerge, this.getViewBuilder().createDefaultDomainBasedEdgeTool(padPackageMerge, this.pack.getPackage_PackageMerge()));

        diagramDescription.getEdgeDescriptions().add(padPackageMerge);
        this.getViewBuilder().addDefaultReconnectionTools(padPackageMerge);
    }

    private void createPackageImportDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> packageDescriptions = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getPackage());
        EdgeDescription padPackageImport = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.pack.getPackageImport(),
                this.getQueryBuilder().queryAllReachable(this.pack.getPackageImport()), packageDescriptions, packageDescriptions);
        padPackageImport.getStyle().setLineStyle(LineStyle.DASH);
        padPackageImport.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);

        this.addCreationToolOnPackage(diagramDescription, padPackageImport, this.getViewBuilder().createDefaultDomainBasedEdgeTool(padPackageImport, this.pack.getNamespace_PackageImport()));
        diagramDescription.getEdgeDescriptions().add(padPackageImport);
        this.getViewBuilder().addDefaultReconnectionTools(padPackageImport);

    }

}
