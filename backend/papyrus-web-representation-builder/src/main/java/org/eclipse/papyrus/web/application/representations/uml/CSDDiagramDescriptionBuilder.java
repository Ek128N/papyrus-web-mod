/*******************************************************************************
 * Copyright (c) 2022 CEA, Obeo
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.application.representations.uml;

import static org.eclipse.papyrus.web.application.representations.view.aql.CallQuery.queryAttributeOnSelf;
import static org.eclipse.papyrus.web.application.representations.view.aql.CallQuery.queryOperationOnSelf;
import static org.eclipse.papyrus.web.application.representations.view.aql.InstanceOfQuery.instanceOf;

import java.util.List;
import java.util.function.Supplier;

import org.eclipse.papyrus.web.application.representations.view.CreationToolsUtil;
import org.eclipse.papyrus.web.application.representations.view.aql.CallQuery;
import org.eclipse.papyrus.web.application.representations.view.aql.IfQuery;
import org.eclipse.papyrus.web.application.representations.view.aql.Variables;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeStyle;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Builder of the "Composite Structure Diagram" diagram representation.
 *
 * @author Arthur Daussy
 */
public class CSDDiagramDescriptionBuilder extends AbstractRepresentationDescriptionBuilder {

    public static final String CSD_PREFIX = "CSD_"; //$NON-NLS-1$

    public static final String CSD_REP_NAME = "Composite Structure Diagram"; //$NON-NLS-1$

    public static final String IN_PROPERTY = "_InProperty"; //$NON-NLS-1$

    public static final String IN_CLASSIFIER = "_InClassifier"; //$NON-NLS-1$

    private final UMLPackage pack = UMLPackage.eINSTANCE;

    private NodeDescription csdPortOnClassifier;

    private NodeDescription csdPortOnProperty;

    private NodeDescription csdPropertyOnClassifier;

    private NodeDescription csdPropertyOnProperty;

    private NodeDescription csdClassifier;

    public CSDDiagramDescriptionBuilder() {
        super(CSD_PREFIX, CSD_REP_NAME, UMLPackage.eINSTANCE.getPackage());
    }

    @Override
    protected void fillDescription(DiagramDescription diagramDescription) {

        createClassifierAndChildrenDescription(diagramDescription);

        createConnectorDescription(diagramDescription);
        createUsageDescription(diagramDescription);
        createGeneralizationDescription(diagramDescription);

        createCommentDescription(diagramDescription);

        diagramDescription.getPalette().setDropTool(getViewBuilder().createGenericDropTool(getIdBuilder().getDropToolId()));
    }

    private void createGeneralizationDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> namedElementCollector = () -> collectNodesWithDomain(diagramDescription, pack.getClassifier());
        EdgeDescription connectorDescription = getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(pack.getGeneralization(),
                getQueryBuilder().queryAllReachable(pack.getGeneralization()),
                namedElementCollector, namedElementCollector);
        EdgeStyle style = connectorDescription.getStyle();
        style.setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);
        diagramDescription.getEdgeDescriptions().add(connectorDescription);

        EdgeTool creationTool = getViewBuilder().createDefaultDomainBasedEdgeTool(connectorDescription, pack.getClassifier_Generalization());
        registerCallback(connectorDescription, () -> {
            CreationToolsUtil.addEdgeCreationTool(namedElementCollector, creationTool);
        });
    }

    private void createConnectorDescription(DiagramDescription diagramDescription) {

        Supplier<List<NodeDescription>> sourceAndTargets = () -> collectNodesWithDomain(diagramDescription, pack.getPort(), pack.getProperty());

        EdgeDescription connectorDescription = getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(pack.getConnector(), getQueryBuilder().queryAllReachable(pack.getConnector()),
                sourceAndTargets,
                sourceAndTargets);
        connectorDescription.setBeginLabelExpression(getQueryBuilder().createDomainBaseEdgeSourceLabelExpression());
        connectorDescription.setEndLabelExpression(getQueryBuilder().createDomainBaseEdgeTargetLabelExpression());
        // Use ConnectorEnd#partWithPort to handle complex Connector edges
        connectorDescription.setPreconditionExpression(new CallQuery(Variables.SELF)//
                .callService("shouldDisplayConnector", //$NON-NLS-1$
                        Variables.SEMANTIC_EDGE_SOURCE, //
                        Variables.SEMANTIC_EDGE_TARGET, //
                        Variables.GRAPHICAL_EDGE_SOURCE, //
                        Variables.GRAPHICAL_EDGE_TARGET, //
                        Variables.CACHE, //
                        Variables.EDITING_CONTEXT));
        diagramDescription.getEdgeDescriptions().add(connectorDescription);
        EdgeTool creationTool = getViewBuilder().createDefaultDomainBasedEdgeTool(connectorDescription, pack.getStructuredClassifier_OwnedConnector());
        registerCallback(connectorDescription, () -> {
            CreationToolsUtil.addEdgeCreationTool(sourceAndTargets, creationTool);
        });
    }

    private void createUsageDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> classifierCollector = () -> collectNodesWithDomain(diagramDescription, pack.getNamedElement());
        EdgeDescription connectorDescription = getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(pack.getUsage(), getQueryBuilder().queryAllReachable(pack.getUsage()),
                classifierCollector,
                classifierCollector);
        EdgeStyle style = connectorDescription.getStyle();
        style.setLineStyle(LineStyle.DASH);
        style.setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        diagramDescription.getEdgeDescriptions().add(connectorDescription);

        EdgeTool creationTool = getViewBuilder().createDefaultDomainBasedEdgeTool(connectorDescription, pack.getPackage_PackagedElement());
        registerCallback(connectorDescription, () -> {
            CreationToolsUtil.addEdgeCreationTool(classifierCollector, creationTool);
        });
    }

    private void createClassifierAndChildrenDescription(DiagramDescription diagramDescription) {
        csdClassifier = newNodeBuilder(pack.getClassifier(), getViewBuilder().createRectangularNodeStyle(true, true))//
                .name(getIdBuilder().getDomainNodeName(pack.getClassifier())) //
                .semanticCandidateExpression(getQueryBuilder().queryAllReachable(pack.getClassifier()))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .labelEditTool(getViewBuilder().createDirectEditTool())//
                .deleteTool(getViewBuilder().createNodeDeleteTool(pack.getClassifier().getName())) //
                .build();
        diagramDescription.getNodeDescriptions().add(csdClassifier);
        diagramDescription.getPalette().getNodeTools().add(getViewBuilder().createCreationTool(pack.getPackage_PackagedElement(), pack.getClass_()));

        createPropertyAndChildrenDescription(CallQuery.queryOperationOnSelf(pack.getClassifier__AllAttributes()), diagramDescription);
        csdPortOnClassifier = createPortDescriptionOnClassifier();
        csdClassifier.getPalette().getNodeTools().add(getViewBuilder().createCreationTool(csdPortOnClassifier, pack.getStructuredClassifier_OwnedAttribute()));
        
        createNestedClassifierDescription(csdClassifier, diagramDescription);

        csdClassifier.getBorderNodesDescriptions().add(csdPortOnClassifier);

        registerNodeAsCommentOwner(csdClassifier, diagramDescription);

        registerCallback(csdClassifier, () -> {
            CreationToolsUtil.addNodeCreationTool(() -> collectNodesWithDomain(diagramDescription, pack.getClassifier()),
                    getViewBuilder().createCreationTool(pack.getClass_NestedClassifier(), pack.getClass_()));
        });
    }

    private void createNestedClassifierDescription(NodeDescription parent, DiagramDescription diagramDescription) {
        NodeDescription csdNestedClass = newNodeBuilder(pack.getClassifier(), getViewBuilder().createRectangularNodeStyle(true, true))//
                .name(getIdBuilder().getSpecializedDomainNodeName(pack.getClassifier(), IN_CLASSIFIER)) //
                .semanticCandidateExpression(queryAttributeOnSelf(pack.getClass_NestedClassifier()))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .labelEditTool(getViewBuilder().createDirectEditTool())//
                .deleteTool(getViewBuilder().createNodeDeleteTool(pack.getClassifier().getName())) //
                .build();

        parent.getChildrenDescriptions().add(csdNestedClass);
        getViewBuilder().addDefaultDeleteTool(csdNestedClass);

        registerNodeAsCommentOwner(csdNestedClass, diagramDescription);

        csdNestedClass.getReusedBorderNodeDescriptions().add(csdPortOnClassifier);
        csdNestedClass.getReusedChildNodeDescriptions().add(csdPropertyOnClassifier);
        csdNestedClass.getReusedChildNodeDescriptions().add(csdNestedClass);
    }

    private NodeDescription createPortDescriptionOnClassifier() {
        NodeDescription port = getViewBuilder().createSpecializedPortUnsynchonizedNodeDescription(IN_CLASSIFIER, pack.getPort(), queryOperationOnSelf(pack.getClassifier__AllAttributes()));
        return port;
    }

    private void createPropertyAndChildrenDescription(String semanticCandidateExpression, DiagramDescription diagramDescription) {

        this.csdPropertyOnClassifier = getViewBuilder().createSpecializedUnsynchonizedNodeDescription(pack.getProperty(), semanticCandidateExpression, IN_CLASSIFIER);
        csdClassifier.getChildrenDescriptions().add(csdPropertyOnClassifier);
        csdClassifier.getPalette().getNodeTools().add(getViewBuilder().createCreationTool(csdPropertyOnClassifier, pack.getStructuredClassifier_OwnedAttribute()));

        registerNodeAsCommentOwner(csdPropertyOnClassifier, diagramDescription);

        csdPortOnProperty = createPortDescriptionOnProperty();
        NodeTool creationTool = getViewBuilder().createCreationTool(getIdBuilder().getCreationToolId(pack.getPort()), //
                queryAttributeOnSelf(pack.getTypedElement_Type()), //
                pack.getStructuredClassifier_OwnedAttribute(), //
                pack.getPort());
        csdPropertyOnClassifier.getPalette().getNodeTools().add(creationTool);
        csdPropertyOnClassifier.getPalette().getNodeTools().add(getViewBuilder().createCreationTool(csdPropertyOnClassifier, pack.getStructuredClassifier_OwnedAttribute()));
        csdPropertyOnClassifier.getBorderNodesDescriptions().add(csdPortOnProperty);

        // Create property children
        String typeVariable = queryAttributeOnSelf(pack.getTypedElement_Type());
        String childrenSemanticCandidateExpression = IfQuery.ifExpression(instanceOf(typeVariable, pack.getClassifier(), getUmlMetaModelHelper())) // $NON-NLS-1$
                .then(new CallQuery(typeVariable).callOperation(pack.getClassifier__AllAttributes())) // $NON-NLS-1$
                .orElse("Sequence{}").toQuery(); //$NON-NLS-1$

        csdPropertyOnProperty = getViewBuilder().createSpecializedUnsynchonizedNodeDescription(pack.getProperty(), childrenSemanticCandidateExpression, IN_PROPERTY);

        registerNodeAsCommentOwner(csdPropertyOnProperty, diagramDescription);

        csdPropertyOnClassifier.getChildrenDescriptions().add(csdPropertyOnProperty);
        csdPropertyOnProperty.getPalette().getNodeTools().add(getViewBuilder().createCreationTool(csdPropertyOnProperty, pack.getStructuredClassifier_OwnedAttribute()));
        csdPropertyOnProperty.getReusedBorderNodeDescriptions().add(csdPortOnProperty);
        csdPropertyOnProperty.getReusedChildNodeDescriptions().add(csdPropertyOnProperty);

    }

    private NodeDescription createPortDescriptionOnProperty() {
        String typeVariable = queryAttributeOnSelf(pack.getTypedElement_Type());
        String semanticCandidateExpression = IfQuery.ifExpression(instanceOf(typeVariable, pack.getClassifier(), getUmlMetaModelHelper()))//
                .then(//
                        new CallQuery(typeVariable)//
                                .callOperation(pack.getClassifier__AllAttributes()))
                .orElse("Sequence{}").toQuery(); //$NON-NLS-1$

        NodeDescription port = getViewBuilder().createSpecializedPortUnsynchonizedNodeDescription(IN_PROPERTY, pack.getPort(), semanticCandidateExpression);

        return port;
    }

}
