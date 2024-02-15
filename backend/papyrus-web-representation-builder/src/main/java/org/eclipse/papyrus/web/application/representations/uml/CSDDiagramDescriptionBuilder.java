/*****************************************************************************
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
 *****************************************************************************/
package org.eclipse.papyrus.web.application.representations.uml;

import static org.eclipse.papyrus.web.application.representations.view.aql.CallQuery.queryAttributeOnSelf;
import static org.eclipse.papyrus.web.application.representations.view.aql.CallQuery.queryOperationOnSelf;
import static org.eclipse.papyrus.web.application.representations.view.aql.InstanceOfQuery.instanceOf;

import java.util.List;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.web.application.representations.view.CreationToolsUtil;
import org.eclipse.papyrus.web.application.representations.view.aql.CallQuery;
import org.eclipse.papyrus.web.application.representations.view.aql.IfQuery;
import org.eclipse.papyrus.web.application.representations.view.aql.Services;
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

    public static final String CSD_PREFIX = "CSD_";

    public static final String CSD_REP_NAME = "Composite Structure Diagram";

    public static final String IN_PROPERTY = "_InProperty";

    public static final String IN_CLASSIFIER = "_InClassifier";

    private static final String AQL_CHECK_TYPED_PROPERTY = "aql:self.oclIsKindOf(uml::Property) and self.type!=null";

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

        diagramDescription.setPreconditionExpression(CallQuery.queryServiceOnSelf(Services.IS_NOT_PROFILE_MODEL));

        this.createClassifierAndChildrenDescription(diagramDescription);

        this.createConnectorDescription(diagramDescription);
        this.createUsageDescription(diagramDescription);
        this.createGeneralizationDescription(diagramDescription);

        this.createCommentDescription(diagramDescription);

        diagramDescription.getPalette().setDropTool(this.getViewBuilder().createGenericSemanticDropTool(this.getIdBuilder().getDiagramSemanticDropToolName()));
    }

    private void createGeneralizationDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> namedElementCollector = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getClassifier());
        EdgeDescription connectorDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.pack.getGeneralization(),
                this.getQueryBuilder().queryAllReachable(this.pack.getGeneralization()), namedElementCollector, namedElementCollector);
        EdgeStyle style = connectorDescription.getStyle();
        style.setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);
        diagramDescription.getEdgeDescriptions().add(connectorDescription);

        EdgeTool creationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(connectorDescription, this.pack.getClassifier_Generalization());
        this.registerCallback(connectorDescription, () -> {
            CreationToolsUtil.addEdgeCreationTool(namedElementCollector, creationTool);
        });
    }

    private void createConnectorDescription(DiagramDescription diagramDescription) {

        Supplier<List<NodeDescription>> sourceAndTargets = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getPort(), this.pack.getProperty());

        EdgeDescription connectorDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.pack.getConnector(),
                this.getQueryBuilder().queryAllReachable(this.pack.getConnector()), sourceAndTargets, sourceAndTargets);
        connectorDescription.setBeginLabelExpression(this.getQueryBuilder().createDomainBaseEdgeSourceLabelExpression());
        connectorDescription.setEndLabelExpression(this.getQueryBuilder().createDomainBaseEdgeTargetLabelExpression());
        // Use ConnectorEnd#partWithPort to handle complex Connector edges
        connectorDescription.setPreconditionExpression(new CallQuery(Variables.SELF)//
                .callService("shouldDisplayConnector", Variables.SEMANTIC_EDGE_SOURCE, //
                        Variables.SEMANTIC_EDGE_TARGET, //
                        Variables.GRAPHICAL_EDGE_SOURCE, //
                        Variables.GRAPHICAL_EDGE_TARGET, //
                        Variables.CACHE, //
                        Variables.EDITING_CONTEXT));
        diagramDescription.getEdgeDescriptions().add(connectorDescription);
        EdgeTool creationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(connectorDescription, this.pack.getStructuredClassifier_OwnedConnector());
        this.registerCallback(connectorDescription, () -> {
            CreationToolsUtil.addEdgeCreationTool(sourceAndTargets, creationTool);
        });
    }

    private void createUsageDescription(DiagramDescription diagramDescription) {
        Supplier<List<NodeDescription>> classifierCollector = () -> this.collectNodesWithDomain(diagramDescription, this.pack.getNamedElement());
        EdgeDescription connectorDescription = this.getViewBuilder().createDefaultSynchonizedDomainBaseEdgeDescription(this.pack.getUsage(),
                this.getQueryBuilder().queryAllReachable(this.pack.getUsage()), classifierCollector, classifierCollector);
        EdgeStyle style = connectorDescription.getStyle();
        style.setLineStyle(LineStyle.DASH);
        style.setTargetArrowStyle(ArrowStyle.INPUT_ARROW);
        diagramDescription.getEdgeDescriptions().add(connectorDescription);

        EdgeTool creationTool = this.getViewBuilder().createDefaultDomainBasedEdgeTool(connectorDescription, this.pack.getPackage_PackagedElement());
        this.registerCallback(connectorDescription, () -> {
            CreationToolsUtil.addEdgeCreationTool(classifierCollector, creationTool);
        });
    }

    private void createClassifierAndChildrenDescription(DiagramDescription diagramDescription) {
        this.csdClassifier = this.newNodeBuilder(this.pack.getClassifier(), this.getViewBuilder().createRectangularNodeStyle(true, true))//
                .name(this.getIdBuilder().getDomainNodeName(this.pack.getClassifier())) //
                .semanticCandidateExpression(this.getQueryBuilder().queryAllReachable(this.pack.getClassifier()))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(this.pack.getClassifier().getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(this.pack.getClassifier().getName())) //
                .build();
        diagramDescription.getNodeDescriptions().add(this.csdClassifier);
        diagramDescription.getPalette().getNodeTools().add(this.getViewBuilder().createCreationTool(this.pack.getPackage_PackagedElement(), this.pack.getClass_()));

        this.createPropertyAndChildrenDescription(CallQuery.queryOperationOnSelf(this.pack.getClassifier__AllAttributes()), diagramDescription);
        this.csdPortOnClassifier = this.createPortDescriptionOnClassifier();
        this.csdClassifier.getPalette().getNodeTools().add(this.getViewBuilder().createCreationTool(this.csdPortOnClassifier, this.pack.getStructuredClassifier_OwnedAttribute()));

        this.createNestedClassifierDescription(this.csdClassifier, diagramDescription);

        this.csdClassifier.getBorderNodesDescriptions().add(this.csdPortOnClassifier);

        this.registerNodeAsCommentOwner(this.csdClassifier, diagramDescription);

        this.registerCallback(this.csdClassifier, () -> {
            CreationToolsUtil.addNodeCreationTool(() -> this.collectNodesWithDomain(diagramDescription, this.pack.getClassifier()),
                    this.getViewBuilder().createCreationTool(this.pack.getClass_NestedClassifier(), this.pack.getClass_()));
        });
    }

    private void createNestedClassifierDescription(NodeDescription parent, DiagramDescription diagramDescription) {
        NodeDescription csdNestedClass = this.newNodeBuilder(this.pack.getClassifier(), this.getViewBuilder().createRectangularNodeStyle(true, true))//
                .name(this.getIdBuilder().getSpecializedDomainNodeName(this.pack.getClassifier(), IN_CLASSIFIER)) //
                .semanticCandidateExpression(queryAttributeOnSelf(this.pack.getClass_NestedClassifier()))//
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)//
                .layoutStrategyDescription(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription())//
                .labelEditTool(this.getViewBuilder().createDirectEditTool(this.pack.getClassifier().getName()))//
                .deleteTool(this.getViewBuilder().createNodeDeleteTool(this.pack.getClassifier().getName())) //
                .build();

        parent.getChildrenDescriptions().add(csdNestedClass);
        this.getViewBuilder().addDefaultDeleteTool(csdNestedClass);

        this.registerNodeAsCommentOwner(csdNestedClass, diagramDescription);

        csdNestedClass.getReusedBorderNodeDescriptions().add(this.csdPortOnClassifier);
        csdNestedClass.getReusedChildNodeDescriptions().add(this.csdPropertyOnClassifier);
        csdNestedClass.getReusedChildNodeDescriptions().add(csdNestedClass);
    }

    private NodeDescription createPortDescriptionOnClassifier() {
        NodeDescription port = this.getViewBuilder().createSpecializedPortUnsynchonizedNodeDescription(IN_CLASSIFIER, this.pack.getPort(),
                queryOperationOnSelf(this.pack.getClassifier__AllAttributes()));
        return port;
    }

    private void createPropertyAndChildrenDescription(String semanticCandidateExpression, DiagramDescription diagramDescription) {

        this.csdPropertyOnClassifier = this.getViewBuilder().createSpecializedUnsynchonizedNodeDescription(this.pack.getProperty(), semanticCandidateExpression, IN_CLASSIFIER);
        this.csdClassifier.getChildrenDescriptions().add(this.csdPropertyOnClassifier);
        this.csdClassifier.getPalette().getNodeTools().add(this.getViewBuilder().createCreationTool(this.csdPropertyOnClassifier, this.pack.getStructuredClassifier_OwnedAttribute()));

        this.registerNodeAsCommentOwner(this.csdPropertyOnClassifier, diagramDescription);

        this.csdPortOnProperty = this.createPortDescriptionOnProperty();
        NodeTool portCreationTool = this.getViewBuilder().createCreationTool(this.getIdBuilder().getCreationToolId(this.pack.getPort()), //
                queryAttributeOnSelf(this.pack.getTypedElement_Type()), //
                this.pack.getStructuredClassifier_OwnedAttribute(), //
                this.pack.getPort());
        portCreationTool
                .setPreconditionExpression(AQL_CHECK_TYPED_PROPERTY);
        this.csdPropertyOnClassifier.getPalette().getNodeTools().add(portCreationTool);

        EClass propertyEClass = this.pack.getProperty();
        String propertyTypeVariable = IfQuery.ifExpression("self.oclIsKindOf(uml::Property) and self.type!=null")
                .then(queryAttributeOnSelf(this.pack.getTypedElement_Type()))
                .orElse(Variables.SELF).toQuery();
        NodeTool propertyInPropertyOnClassifierCreationTool = this.getViewBuilder().createCreationTool(this.getIdBuilder().getCreationToolId(propertyEClass), propertyTypeVariable,
                this.pack.getStructuredClassifier_OwnedAttribute(), propertyEClass);
        propertyInPropertyOnClassifierCreationTool
                .setPreconditionExpression(AQL_CHECK_TYPED_PROPERTY);
        this.csdPropertyOnClassifier.getPalette().getNodeTools().add(propertyInPropertyOnClassifierCreationTool);
        this.csdPropertyOnClassifier.getBorderNodesDescriptions().add(this.csdPortOnProperty);

        // Create property children
        String typeVariable = queryAttributeOnSelf(this.pack.getTypedElement_Type());
        String childrenSemanticCandidateExpression = IfQuery.ifExpression(instanceOf(typeVariable, this.pack.getClassifier(), this.getUmlMetaModelHelper())) //
                .then(new CallQuery(typeVariable).callOperation(this.pack.getClassifier__AllAttributes())) //
                .orElse("Sequence{}").toQuery();

        this.csdPropertyOnProperty = this.getViewBuilder().createSpecializedUnsynchonizedNodeDescription(this.pack.getProperty(), childrenSemanticCandidateExpression, IN_PROPERTY);

        this.registerNodeAsCommentOwner(this.csdPropertyOnProperty, diagramDescription);

        this.csdPropertyOnClassifier.getChildrenDescriptions().add(this.csdPropertyOnProperty);

        NodeTool propertyInPropertyOnPropertyCreationTool = this.getViewBuilder().createCreationTool(this.getIdBuilder().getCreationToolId(propertyEClass), propertyTypeVariable,
                this.pack.getStructuredClassifier_OwnedAttribute(), propertyEClass);
        propertyInPropertyOnPropertyCreationTool
                .setPreconditionExpression(AQL_CHECK_TYPED_PROPERTY);
        this.csdPropertyOnProperty.getPalette().getNodeTools().add(propertyInPropertyOnPropertyCreationTool);
        this.csdPropertyOnProperty.getReusedBorderNodeDescriptions().add(this.csdPortOnProperty);
        this.csdPropertyOnProperty.getReusedChildNodeDescriptions().add(this.csdPropertyOnProperty);

    }

    private NodeDescription createPortDescriptionOnProperty() {
        String typeVariable = queryAttributeOnSelf(this.pack.getTypedElement_Type());
        String semanticCandidateExpression = IfQuery.ifExpression(instanceOf(typeVariable, this.pack.getClassifier(), this.getUmlMetaModelHelper()))//
                .then(//
                        new CallQuery(typeVariable)//
                                .callOperation(this.pack.getClassifier__AllAttributes()))
                .orElse("Sequence{}").toQuery();

        NodeDescription port = this.getViewBuilder().createSpecializedPortUnsynchonizedNodeDescription(IN_PROPERTY, this.pack.getPort(), semanticCandidateExpression);

        return port;
    }

}
