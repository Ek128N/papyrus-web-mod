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
package org.eclipse.papyrus.web.application.representations.uml;

import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.CACHE;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.GRAPHICAL_EDGE_SOURCE;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.GRAPHICAL_EDGE_TARGET;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.SEMANTIC_EDGE_SOURCE;
import static org.eclipse.papyrus.web.application.representations.view.aql.Variables.SEMANTIC_EDGE_TARGET;

import java.util.List;
import java.util.function.Supplier;

import org.eclipse.papyrus.web.application.representations.view.aql.CallQuery;
import org.eclipse.papyrus.web.application.representations.view.aql.Services;
import org.eclipse.sirius.components.view.ArrowStyle;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.EdgeTool;
import org.eclipse.sirius.components.view.LineStyle;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Builder of the "Package Diagram" diagram representation.
 *
 * @author Arthur Daussy
 */
public class PADDiagramDescriptionBuilder extends AbstractRepresentationDescriptionBuilder {

    public static final String PD_REP_NAME = "Package Diagram"; //$NON-NLS-1$

    public static final String PAD_PREFIX = "PAD_"; //$NON-NLS-1$

    public static final String CONTAINMENT_LINK_EDGE_ID = PAD_PREFIX + "_ContainmentLink_FeatureEdge"; //$NON-NLS-1$

    private final UMLPackage pack = UMLPackage.eINSTANCE;

    public PADDiagramDescriptionBuilder() {
        super(PAD_PREFIX, PD_REP_NAME, UMLPackage.eINSTANCE.getPackage()); // $NON-NLS-1$
    }

    @Override
    protected void fillDescription(DiagramDescription diagramDescription) {

        createPackageDescription(diagramDescription);
        createModelDescription(diagramDescription);

        createPackageMergeDescription(diagramDescription);
        createPackageImportDescription(diagramDescription);
        createAbstractionDescription(diagramDescription);
        createDependencyDescription(diagramDescription);
        createContainmentLink(diagramDescription);

        createCommentDescription(diagramDescription);

        diagramDescription.setOnDrop(getViewBuilder().createGenericDropTool(getIdBuilder().getDropToolId()));
    }

    private void createContainmentLink(DiagramDescription diagramDescription) {

        Supplier<List<NodeDescription>> sourceAndTargetProvider = () -> collectNodesWithDomain(diagramDescription, pack.getPackage());

        EdgeDescription containmentLinkEdge = getViewBuilder().createFeatureEdgeDescription(//
                CONTAINMENT_LINK_EDGE_ID, // $NON-NLS-1$
                getQueryBuilder().emptyString(), //
                CallQuery.queryAttributeOnSelf(pack.getPackage_NestedPackage()), //
                sourceAndTargetProvider, //
                sourceAndTargetProvider);

        containmentLinkEdge.setPreconditionExpression(new CallQuery(GRAPHICAL_EDGE_SOURCE).callService(Services.IS_NOT_VISUAL_DESCENDANT, GRAPHICAL_EDGE_TARGET, CACHE)); // $NON-NLS-1$

        containmentLinkEdge.getStyle().setSourceArrowStyle(ArrowStyle.CROSSED_CIRCLE);

        diagramDescription.getEdgeDescriptions().add(containmentLinkEdge);

        // Create containment Link tool

        EdgeTool tool = ViewFactory.eINSTANCE.createEdgeTool();
        tool.setName("New Containment Link"); //$NON-NLS-1$

        String toolQuery = new CallQuery(SEMANTIC_EDGE_TARGET).callService(Services.MOVE_IN, SEMANTIC_EDGE_SOURCE); // $NON-NLS-1$

        ChangeContext changeContext = getViewBuilder().createChangeContextOperation(toolQuery);
        tool.getBody().add(changeContext);
        containmentLinkEdge.getEdgeTools().add(tool);
    }

    private void createDependencyDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> namedElementDescriptions = () -> collectNodesWithDomain(diagramDescription, pack.getNamedElement());
        EdgeDescription padDependency = getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(pack.getDependency(), getQueryBuilder().queryAllReachableExactType(pack.getDependency()),
                namedElementDescriptions, namedElementDescriptions);
        padDependency.getStyle().setLineStyle(LineStyle.DASH);
        padDependency.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        padDependency.getEdgeTools().add(getViewBuilder().createDefaultDomainBasedEdgeTool(padDependency, pack.getPackage_PackagedElement()));

        diagramDescription.getEdgeDescriptions().add(padDependency);

        getViewBuilder().addDefaultReconnectionTools(padDependency);
    }

    private void createAbstractionDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> namedElementDescriptions = () -> collectNodesWithDomain(diagramDescription, pack.getNamedElement());
        EdgeDescription padAbstraction = getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(pack.getAbstraction(), getQueryBuilder().queryAllReachable(pack.getAbstraction()),
                namedElementDescriptions, namedElementDescriptions);
        padAbstraction.getStyle().setLineStyle(LineStyle.DASH);
        padAbstraction.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        padAbstraction.getEdgeTools().add(getViewBuilder().createDefaultDomainBasedEdgeTool(padAbstraction, pack.getPackage_PackagedElement()));

        diagramDescription.getEdgeDescriptions().add(padAbstraction);

        getViewBuilder().addDefaultReconnectionTools(padAbstraction);
    }

    private void createPackageMergeDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> packageDescriptions = () -> collectNodesWithDomain(diagramDescription, pack.getPackage());
        EdgeDescription padPackageMerge = getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(pack.getPackageMerge(), getQueryBuilder().queryAllReachable(pack.getPackageMerge()),
                packageDescriptions, packageDescriptions);
        padPackageMerge.getStyle().setLineStyle(LineStyle.DASH);
        padPackageMerge.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        padPackageMerge.getEdgeTools().add(getViewBuilder().createDefaultDomainBasedEdgeTool(padPackageMerge, pack.getPackage_PackageMerge()));

        diagramDescription.getEdgeDescriptions().add(padPackageMerge);
        getViewBuilder().addDefaultReconnectionTools(padPackageMerge);
    }

    private void createPackageImportDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> packageDescriptions = () -> collectNodesWithDomain(diagramDescription, pack.getPackage());
        EdgeDescription padPackageImport = getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(pack.getPackageImport(), getQueryBuilder().queryAllReachable(pack.getPackageImport()),
                packageDescriptions, packageDescriptions);
        padPackageImport.getStyle().setLineStyle(LineStyle.DASH);
        padPackageImport.getStyle().setTargetArrowStyle(ArrowStyle.INPUT_ARROW);

        padPackageImport.getEdgeTools().add(getViewBuilder().createDefaultDomainBasedEdgeTool(padPackageImport, pack.getNamespace_PackageImport()));
        diagramDescription.getEdgeDescriptions().add(padPackageImport);
        getViewBuilder().addDefaultReconnectionTools(padPackageImport);

    }

}
