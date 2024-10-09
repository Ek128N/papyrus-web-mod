/*****************************************************************************
 * Copyright (c) 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.web.application.explorer.builder;

import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.tree.FetchTreeItemContextMenuEntryBuilder;
import org.eclipse.sirius.components.view.builder.generated.tree.TreeBuilders;
import org.eclipse.sirius.components.view.builder.generated.tree.TreeDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntryKind;
import org.eclipse.sirius.components.view.tree.TreeDescription;
import org.eclipse.sirius.components.view.tree.TreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.TreeItemLabelDescription;
import org.eclipse.sirius.emfjson.resource.JsonResource;

/**
 * Builder of the {@link TreeDescription} to be used in a UML editing context.
 *
 * @author Jerome Gout
 */
public class UMLDemoTreeDescriptionBuilder {

    /**
     * Name of the Tree.
     */
    public static final String UML_DEMO_EXPLORER = "UML Demo Explorer";

    public View createView() {

        var umlDemoTreeDescription = this.build();

        var umlDefaultTreeView = new ViewBuilders()
                .newView()
                .descriptions(umlDemoTreeDescription)
                .build();

        umlDefaultTreeView.eAllContents().forEachRemaining(eObject -> {
            var id = UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes());
            eObject.eAdapters().add(new IDAdapter(id));
        });

        UUID resourceId = UUID.nameUUIDFromBytes(UMLDemoTreeDescriptionBuilder.UML_DEMO_EXPLORER.getBytes());
        String resourcePath = resourceId.toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter(UMLDemoTreeDescriptionBuilder.UML_DEMO_EXPLORER));
        resource.getContents().add(umlDefaultTreeView);

        return umlDefaultTreeView;
    }

    private TreeDescription build() {

        TreeDescription description = new TreeDescriptionBuilder()
                .name(UML_DEMO_EXPLORER)
                .childrenExpression("aql:self.getChildrenItems(editingContext,expanded, " + TreeRenderer.ANCESTOR_IDS + "," + TreeRenderer.INDEX + ")")
                .deletableExpression("aql:self.canBeDeleted()")
                .editableExpression("aql:self.canBeRenamed()")
                .elementsExpression("aql:editingContext.getRootElements(activeFilterIds)")
                .hasChildrenExpression("aql:self.hasChildren(" + TreeRenderer.ANCESTOR_IDS + "," + TreeRenderer.INDEX + ")")
                .iconURLExpression("aql:self.getIconURLs()")
                .kindExpression("aql:self.getItemKind()")
                .parentExpression("aql:self.getParentItem(id,editingContext)")
                .preconditionExpression("aql:false")
                .selectableExpression("aql:true")
                .titleExpression(UML_DEMO_EXPLORER)
                .treeItemIdExpression("aql:self.getItemId()")
                .treeItemObjectExpression("aql:id.toObject(editingContext)")
                .treeItemLabelDescriptions(this.createDefaultStyle())
                .contextMenuEntries(
                        this.getCapitalizeContextMenuEntry(),
                        this.getGenerateJavaCodeContextMenuEntry(),
                        this.getGenerateCPlusPlusCodeContextMenuEntry(),
                        this.getGeneratePythonCodeContextMenuEntry())
                .build();

        return description;
    }

    private TreeItemLabelDescription createDefaultStyle() {
        return new TreeBuilders()
                .newTreeItemLabelDescription()
                .name("Default style")
                .preconditionExpression("aql:true")
                .children(
                        new TreeBuilders().newTreeItemLabelFragmentDescription()
                                .labelExpression("aql:self.getItemLabel()")
                                .build())
                .build();
    }

    private TreeItemContextMenuEntry getCapitalizeContextMenuEntry() {
        var callService = new ViewBuilders().newChangeContext()
                .expression("aql:self.capitalizeNamedElement()");

        return new TreeBuilders().newSingleClickTreeItemContextMenuEntry()
                .labelExpression("Capitalize")
                .iconURLExpression("/images/capitalize-action.svg")
                .preconditionExpression("aql:self.oclIsKindOf(uml::NamedElement)")
                .body(callService.build())
                .build();
    }

    private TreeItemContextMenuEntry getGenerateJavaCodeContextMenuEntry() {
        return this.getGenerateCodeFetchBuilder("Generate Java")
                .urlExression("aql:self.getGenerateJavaCodeURL(editingContext)")
                .build();
    }

    private TreeItemContextMenuEntry getGenerateCPlusPlusCodeContextMenuEntry() {
        return this.getGenerateCodeFetchBuilder("Generate C++")
                .urlExression("aql:self.getGenerateCPlusPlusCodeURL(editingContext)")
                .build();
    }

    private TreeItemContextMenuEntry getGeneratePythonCodeContextMenuEntry() {
        return this.getGenerateCodeFetchBuilder("Generate Python")
                .urlExression("aql:self.getGeneratePythonCodeURL(editingContext)")
                .build();
    }

    private FetchTreeItemContextMenuEntryBuilder getGenerateCodeFetchBuilder(String label) {
        return new TreeBuilders().newFetchTreeItemContextMenuEntry()
                .labelExpression(label)
                .iconURLExpression("/images/generate-code-action.svg")
                .preconditionExpression("aql:self.oclIsKindOf(uml::Model)")
                .kind(FetchTreeItemContextMenuEntryKind.DOWNLOAD);
    }
}
