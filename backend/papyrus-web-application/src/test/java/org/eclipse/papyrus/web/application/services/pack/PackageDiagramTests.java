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
package org.eclipse.papyrus.web.application.services.pack;

import static org.eclipse.papyrus.web.application.representations.uml.PADDiagramDescriptionBuilder.CONTAINMENT_LINK_EDGE_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.web.application.representations.uml.AbstractRepresentationDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.PADDiagramDescriptionBuilder;
import org.eclipse.papyrus.web.application.representations.uml.UMLMetamodelHelper;
import org.eclipse.papyrus.web.application.representations.view.IdBuilder;
import org.eclipse.papyrus.web.application.services.AbstractDiagramTest;
import org.eclipse.papyrus.web.application.utils.ElementMatcher;
import org.eclipse.papyrus.web.services.aqlservices.AbstractDiagramService;
import org.eclipse.papyrus.web.services.aqlservices.pakage.PackageDiagramService;
import org.eclipse.papyrus.web.tests.utils.MockLogger;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.uml2.uml.Abstraction;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.PackageMerge;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Test class gathering integration test regarding creation in the Package Diagram.
 *
 * @author Arthur Daussy
 */
@SpringBootTest
@WebAppConfiguration
public class PackageDiagramTests extends AbstractDiagramTest {

    private static final IdBuilder ID_BUILDER = new IdBuilder(PADDiagramDescriptionBuilder.PAD_PREFIX, new UMLMetamodelHelper());

    private static final String PAD_ABSTRACTION = ID_BUILDER.getDomainBaseEdgeId(UML.getAbstraction());

    private static final String PAD_COMMENT = ID_BUILDER.getDomainNodeName(UML.getComment());

    private static final String PAD_PACKAGE = ID_BUILDER.getDomainNodeName(UML.getPackage());

    private static final String PAD_PACKAGE_CHILD = ID_BUILDER.getSpecializedDomainNodeName(UML.getPackage(), AbstractRepresentationDescriptionBuilder.PACKAGE_CHILD);

    private static final String PAD_MODEL = ID_BUILDER.getDomainNodeName(UML.getModel());

    private static final String PAD_MODEL_CHILD = ID_BUILDER.getSpecializedDomainNodeName(UML.getModel(), AbstractRepresentationDescriptionBuilder.PACKAGE_CHILD);

    @Test
    public void checkRootPackageCreation() {
        this.init();
        this.getServiceTester().assertRootCreation(UML.getPackage(), UML.getPackage_PackagedElement(), PAD_PACKAGE);
    }

    @Test
    public void checkRootModelCreation() {
        this.init();
        this.getServiceTester().assertRootCreation(UML.getModel(), UML.getPackage_PackagedElement(), PAD_MODEL);
    }

    @Test
    public void checkRootCommentCreation() {
        this.init();
        this.getServiceTester().assertRootCreation(UML.getComment(), UML.getElement_OwnedComment(), PAD_COMMENT);
    }

    @Test
    public void checkPackageImportEdge() {
        Package pack = this.init();

        // From package
        this.checkPackageImportEdge(this.createIn(Package.class, pack), this.createIn(Package.class, pack));
        this.checkPackageImportEdge(this.createIn(Package.class, pack), this.createIn(Model.class, pack));

        // From model
        this.checkPackageImportEdge(this.createIn(Model.class, pack), this.createIn(Package.class, pack));
        this.checkPackageImportEdge(this.createIn(Model.class, pack), this.createIn(Model.class, pack));

    }

    /**
     * Checks that the containment links are only displayed when package are displayed as siblings.
     */
    @Test
    public void checkContainmentLinkPredicontionCondition() {
        Package parentPackage = this.init();
        Package subPack = this.createIn(Package.class, parentPackage);

        Node parentNode = this.getServiceTester().assertSemanticDrop(parentPackage, null, PAD_PACKAGE);

        Node childNode = this.getServiceTester().assertSemanticDrop(subPack, parentNode, PAD_PACKAGE_CHILD);
        Node siblingNode = this.getServiceTester().assertSemanticDrop(subPack, null, PAD_PACKAGE);

        this.getDiagramHelper().assertGetUniqueFeatureBasedEdge(CONTAINMENT_LINK_EDGE_ID, parentNode, siblingNode);
        this.getDiagramHelper().assertNoFeatureBasedEdge(CONTAINMENT_LINK_EDGE_ID, parentNode, childNode);
    }

    private void checkPackageImportEdge(Namespace source, Package target) {

        var edge = this.createIn(PackageImport.class, source);
        edge.setImportingNamespace(source);
        edge.setImportedPackage(target);
        this.getServiceTester().checkDisplayedDomainBasedEdge(source, target, edge, ID_BUILDER);
    }

    @Test
    public void checkPackageMergeEdge() {
        Package pack = this.init();

        // From package
        this.checkPackageMergeEdge(this.createIn(Package.class, pack), this.createIn(Package.class, pack));
        this.checkPackageMergeEdge(this.createIn(Package.class, pack), this.createIn(Model.class, pack));

        // From model
        this.checkPackageMergeEdge(this.createIn(Model.class, pack), this.createIn(Package.class, pack));
        this.checkPackageMergeEdge(this.createIn(Model.class, pack), this.createIn(Model.class, pack));

    }

    private void checkPackageMergeEdge(Package source, Package target) {
        var edge = this.createIn(PackageMerge.class, source);
        edge.setReceivingPackage(source);
        edge.setMergedPackage(target);

        this.getServiceTester().checkDisplayedDomainBasedEdge(source, target, edge, ID_BUILDER);
    }

    /**
     * Checks the creation of Dependency between 2 packages.
     */
    @Test
    public void checkDependencyEdgeCreation() {
        Package pack = this.init();

        Package packSource = this.createIn(Package.class, pack);
        Package packTarget = this.createIn(Package.class, pack);

        Edge edge = this.getServiceTester().buildSynchronizedDomainBasedEdgeCreationTestHelper(ID_BUILDER)//
                .withSource(packSource)//
                .withTarget(packTarget)//
                .withExpectedContainementRef(UML.getPackage_PackagedElement())//
                .withExpectedOwner(packSource)//
                .withType(UML.getDependency())//
                .build()//
                .emulateCreationTool()//
                .assertEdgeCreation();

        assertEquals("", edge.getCenterLabel().getText());

    }

    /**
     * Checks the creation of Abstraction between 2 packages.
     */
    @Test
    public void checkAbstractionEdgeCreation() {
        Package pack = this.init();

        Package packSource = this.createIn(Package.class, pack);
        Package packTarget = this.createIn(Package.class, pack);

        this.getServiceTester().buildSynchronizedDomainBasedEdgeCreationTestHelper(ID_BUILDER)//
                .withSource(packSource)//
                .withTarget(packTarget)//
                .withExpectedContainementRef(UML.getPackage_PackagedElement())//
                .withExpectedOwner(packSource)//
                .withType(UML.getAbstraction())//
                .build()//
                .emulateCreationTool()//
                .assertEdgeCreation();
    }

    /**
     * Checks the creation of PackageMerge between 2 packages.
     */
    @Test
    public void checkPackageMergeEdgeCreation() {
        Package pack = this.init();

        Package packSource = this.createIn(Package.class, pack);
        Package packTarget = this.createIn(Package.class, pack);

        this.getServiceTester().buildSynchronizedDomainBasedEdgeCreationTestHelper(ID_BUILDER)//
                .withSource(packSource)//
                .withTarget(packTarget)//
                .withType(UML.getPackageMerge())//
                .withExpectedContainementRef(UML.getPackage_PackageMerge())//
                .withExpectedOwner(packSource)//
                .build()//
                .emulateCreationTool()//
                .assertEdgeCreation();

    }

    /**
     * Check the default source and target reconnection service for PackageMerge.
     */
    @Test
    public void checkPackageMergeReconnection() {
        Package pack = this.init();

        Package packSource = this.createIn(Package.class, pack);
        Package packSource2 = this.createIn(Package.class, pack);
        Package packTarget = this.createIn(Package.class, pack);
        PackageMerge packageMerge = this.createIn(PackageMerge.class, packSource);
        packageMerge.setReceivingPackage(packSource);
        packageMerge.setMergedPackage(packTarget);

        // Create the 3 required nodes
        Node sourceNode = this.getDiagramHelper().createNodeInDiagram(PAD_PACKAGE, packSource);
        Node targetNode = this.getDiagramHelper().createNodeInDiagram(PAD_PACKAGE, packTarget);
        this.getDiagramHelper().createNodeInDiagram(PAD_PACKAGE, packSource2);

        // Check that the edge is created
        this.getDiagramHelper().refresh();
        Edge matchingEdge = this.getDiagramHelper()//
                .getMatchingEdge(//
                        Optional.of(ID_BUILDER.getDomainBaseEdgeId(UML.getPackageMerge())), //
                        Optional.of(this.getObjectService().getId(packageMerge)), //
                        Optional.of(sourceNode.getId()), Optional.of(targetNode.getId()));

        assertNotNull(matchingEdge);

        // Check source reconnection
        this.getServiceTester().assertSourceReconnection(new ElementMatcher(packageMerge, ID_BUILDER.getDomainBaseEdgeId(UML.getPackageMerge())), //
                new ElementMatcher(packSource, PAD_PACKAGE), //
                new ElementMatcher(packSource2, PAD_PACKAGE), //
                new ElementMatcher(packTarget, PAD_PACKAGE));

        assertTrue(packSource2.getPackageMerges().contains(packageMerge));
        assertEquals(packSource2, packageMerge.getReceivingPackage());
        assertEquals(packTarget, packageMerge.getMergedPackage());

        // Check target reconnection
        this.getServiceTester().assertTargetReconnection(new ElementMatcher(packageMerge, ID_BUILDER.getDomainBaseEdgeId(UML.getPackageMerge())), //
                new ElementMatcher(packTarget, PAD_PACKAGE), //
                new ElementMatcher(packSource, PAD_PACKAGE), //
                new ElementMatcher(packSource2, PAD_PACKAGE));

        assertTrue(packSource2.getPackageMerges().contains(packageMerge));
        assertEquals(packSource2, packageMerge.getReceivingPackage());
        assertEquals(packSource, packageMerge.getMergedPackage());
    }

    /**
     * Check the default source and target reconnection service for Dependency.
     */
    @Test
    public void checkDependencyReconnection() {
        Package pack = this.init();

        Package packSource = this.createIn(Package.class, pack);
        Package packSource2 = this.createIn(Package.class, pack);
        Package packTarget = this.createIn(Package.class, pack);
        Dependency dependency = this.createIn(Dependency.class, packSource);

        packSource.getPackagedElements().add(dependency);
        dependency.getClients().add(packSource);
        dependency.getSuppliers().add(packTarget);

        // Create the 3 required nodes
        Node sourceNode = this.getDiagramHelper().createNodeInDiagram(PAD_PACKAGE, packSource);
        Node targetNode = this.getDiagramHelper().createNodeInDiagram(PAD_PACKAGE, packTarget);
        this.getDiagramHelper().createNodeInDiagram(PAD_PACKAGE, packSource2);

        // Check that the edge is created
        this.getDiagramHelper().refresh();
        Edge matchingEdge = this.getDiagramHelper()//
                .getMatchingEdge(//
                        Optional.of(ID_BUILDER.getDomainBaseEdgeId(UML.getDependency())), //
                        Optional.of(this.getObjectService().getId(dependency)), //
                        Optional.of(sourceNode.getId()), Optional.of(targetNode.getId()));

        assertNotNull(matchingEdge);

        // Check source reconnection
        this.getServiceTester().assertSourceReconnection(new ElementMatcher(dependency, ID_BUILDER.getDomainBaseEdgeId(UML.getDependency())), //
                new ElementMatcher(packSource, PAD_PACKAGE), //
                new ElementMatcher(packSource2, PAD_PACKAGE), //
                new ElementMatcher(packTarget, PAD_PACKAGE));

        assertTrue(dependency.getClients().contains(packSource2));
        assertFalse(dependency.getClients().contains(packSource));
        assertTrue(dependency.getSuppliers().contains(packTarget));

        // Check target reconnection
        this.getServiceTester().assertTargetReconnection(new ElementMatcher(dependency, ID_BUILDER.getDomainBaseEdgeId(UML.getDependency())), //
                new ElementMatcher(packTarget, PAD_PACKAGE), //
                new ElementMatcher(packSource, PAD_PACKAGE), //
                new ElementMatcher(packSource2, PAD_PACKAGE));

        assertTrue(dependency.getClients().contains(packSource2));
        assertTrue(dependency.getSuppliers().contains(packSource));
        assertFalse(dependency.getSuppliers().contains(packTarget));
    }

    /**
     * Check the default source and target reconnection service for Abstraction.
     */
    @Test
    public void checkAbstractionReconnection() {
        Package pack = this.init();

        Package packSource = this.createIn(Package.class, pack);
        Package packSource2 = this.createIn(Package.class, pack);
        Package packTarget = this.createIn(Package.class, pack);
        Abstraction abstraction = this.createIn(Abstraction.class, packSource);

        packSource.getPackagedElements().add(abstraction);
        abstraction.getClients().add(packSource);
        abstraction.getSuppliers().add(packTarget);

        // Create the 3 required nodes
        Node sourceNode = this.getDiagramHelper().createNodeInDiagram(PAD_PACKAGE, packSource);
        Node targetNode = this.getDiagramHelper().createNodeInDiagram(PAD_PACKAGE, packTarget);
        this.getDiagramHelper().createNodeInDiagram(PAD_PACKAGE, packSource2);

        // Check that the edge is created
        this.getDiagramHelper().refresh();
        Edge matchingEdge = this.getDiagramHelper()//
                .getMatchingEdge(//
                        Optional.of(PAD_ABSTRACTION), //
                        Optional.of(this.getObjectService().getId(abstraction)), //
                        Optional.of(sourceNode.getId()), Optional.of(targetNode.getId()));

        assertNotNull(matchingEdge);

        // Check source reconnection
        this.getServiceTester().assertSourceReconnection(new ElementMatcher(abstraction, PAD_ABSTRACTION), //
                new ElementMatcher(packSource, PAD_PACKAGE), //
                new ElementMatcher(packSource2, PAD_PACKAGE), //
                new ElementMatcher(packTarget, PAD_PACKAGE));

        assertTrue(abstraction.getClients().contains(packSource2));
        assertFalse(abstraction.getClients().contains(packSource));
        assertTrue(abstraction.getSuppliers().contains(packTarget));

        // Check target reconnection
        this.getServiceTester().assertTargetReconnection(new ElementMatcher(abstraction, PAD_ABSTRACTION), //
                new ElementMatcher(packTarget, PAD_PACKAGE), //
                new ElementMatcher(packSource, PAD_PACKAGE), //
                new ElementMatcher(packSource2, PAD_PACKAGE));

        assertTrue(abstraction.getClients().contains(packSource2));
        assertTrue(abstraction.getSuppliers().contains(packSource));
        assertFalse(abstraction.getSuppliers().contains(packTarget));
    }

    /**
     * Check the default source and target reconnection service for PackageImport.
     */
    @Test
    public void checkPackageImportReconnection() {
        Package pack = this.init();

        Package packSource = this.createIn(Package.class, pack);
        Package packSource2 = this.createIn(Package.class, pack);
        Package packTarget = this.createIn(Package.class, pack);
        PackageImport packageImport = this.createIn(PackageImport.class, packSource);
        packageImport.setImportingNamespace(packSource);
        packageImport.setImportedPackage(packTarget);

        // Create the 3 required nodes
        Node sourceNode = this.getDiagramHelper().createNodeInDiagram(PAD_PACKAGE, packSource);
        Node targetNode = this.getDiagramHelper().createNodeInDiagram(PAD_PACKAGE, packTarget);
        this.getDiagramHelper().createNodeInDiagram(PAD_PACKAGE, packSource2);

        // Check that the edge is created
        this.getDiagramHelper().refresh();
        Edge matchingEdge = this.getDiagramHelper()//
                .getMatchingEdge(//
                        Optional.of(ID_BUILDER.getDomainBaseEdgeId(UML.getPackageImport())), //
                        Optional.of(this.getObjectService().getId(packageImport)), //
                        Optional.of(sourceNode.getId()), Optional.of(targetNode.getId()));

        assertNotNull(matchingEdge);

        // Check source reconnection
        this.getServiceTester().assertSourceReconnection(new ElementMatcher(packageImport, ID_BUILDER.getDomainBaseEdgeId(UML.getPackageImport())), //
                new ElementMatcher(packSource, PAD_PACKAGE), //
                new ElementMatcher(packSource2, PAD_PACKAGE), //
                new ElementMatcher(packTarget, PAD_PACKAGE));

        assertTrue(packSource2.getPackageImports().contains(packageImport));
        assertEquals(packSource2, packageImport.getImportingNamespace());
        assertEquals(packTarget, packageImport.getImportedPackage());

        // Check target reconnection
        this.getServiceTester().assertTargetReconnection(new ElementMatcher(packageImport, ID_BUILDER.getDomainBaseEdgeId(UML.getPackageImport())), //
                new ElementMatcher(packTarget, PAD_PACKAGE), //
                new ElementMatcher(packSource, PAD_PACKAGE), //
                new ElementMatcher(packSource2, PAD_PACKAGE));

        assertTrue(packSource2.getPackageImports().contains(packageImport));
        assertEquals(packSource2, packageImport.getImportingNamespace());
        assertEquals(packSource, packageImport.getImportedPackage());
    }

    /**
     * Checks all the possible owner of comments.
     */
    @Test
    public void checkCommentParent() {
        Package pack = this.init();

        Package pack1 = this.createIn(Package.class, pack);

        // In Package
        Node packageNode = this.getDiagramHelper().createNodeInDiagram(PAD_PACKAGE, pack1);
        this.getServiceTester().assertChildCreation(packageNode, UML.getComment(), UML.getElement_OwnedComment(), PAD_COMMENT);

        // In Model
        Model model1 = this.createIn(Model.class, pack);
        Node modelNodeNode = this.getDiagramHelper().createNodeInDiagram(PAD_MODEL, model1);
        this.getServiceTester().assertChildCreation(modelNodeNode, UML.getComment(), UML.getElement_OwnedComment(), PAD_COMMENT);

    }

    /**
     * Check that a ContainmentLink is always created between directly contained Packages.
     */
    @Test
    public void checkContainmentLinkSynchronization() {
        Package pack = this.init();

        Package packSource = this.createIn(Package.class, pack);
        Package packTarget = this.createIn(Package.class, packSource);
        Package otherPackage = this.createIn(Package.class, pack);

        Node sourceNode = this.getDiagramHelper().createNodeInDiagram(PAD_PACKAGE, packSource);
        Node targetNode = this.getDiagramHelper().createNodeInDiagram(PAD_PACKAGE, packTarget);
        Node otherNode = this.getDiagramHelper().createNodeInDiagram(PAD_PACKAGE, otherPackage);

        this.getDiagramHelper().refresh();

        this.getDiagramHelper().assertGetUniqueFeatureBasedEdge(PADDiagramDescriptionBuilder.CONTAINMENT_LINK_EDGE_ID, sourceNode, targetNode);
        this.getDiagramHelper().assertNoFeatureEdgeStartingFrom(PADDiagramDescriptionBuilder.CONTAINMENT_LINK_EDGE_ID, otherNode);
    }

    private Package init() {
        Resource resource = this.createResource();
        Package pack = this.createInResource(Package.class, resource);

        this.getDiagramHelper().init(pack, PADDiagramDescriptionBuilder.PD_REP_NAME);

        return pack;
    }

    @Override
    protected AbstractDiagramService buildService() {
        return new PackageDiagramService(this.getObjectService(), this.getDiagramNavigationService(), this.getDiagramOperationsService(), e -> true, this.getViewDiagramDescriptionService(),
                new MockLogger());
    }

    @Test
    public void checkModelChildren() {
        Package pack = this.init();

        Model parent = this.createIn(Model.class, pack);
        Node parentNode = this.getDiagramHelper().createNodeInDiagram(PAD_MODEL, parent);

        this.checkPackageChildren(parentNode, true);
    }

    private void checkPackageChildren(Node parentNode, boolean recurse) {
        // Model In Model
        Node droppedModelNode = this.getServiceTester().assertChildCreationAndDrop(parentNode, Model.class, UML.getPackage_PackagedElement(), PAD_MODEL_CHILD);
        if (recurse) {
            this.checkPackageChildren(droppedModelNode, false);
        }

        // Package in Model
        Node droppedPackageNode = this.getServiceTester().assertChildCreationAndDrop(parentNode, Package.class, UML.getPackage_PackagedElement(), PAD_PACKAGE_CHILD);
        if (recurse) {
            this.checkPackageChildren(droppedPackageNode, false);
        }

        // Comment in Model
        this.getServiceTester().assertChildCreationAndDrop(parentNode, Comment.class, UML.getElement_OwnedComment(), PAD_COMMENT);
    }

    @Test
    public void checkPackageChildren() {
        Package pack = this.init();

        Package parent = this.createIn(Package.class, pack);
        Node parentNode = this.getDiagramHelper().createNodeInDiagram(PAD_PACKAGE, parent);

        this.checkPackageChildren(parentNode, true);
    }

}
