/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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

package org.eclipse.papyrus.web.application.tables.comment;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.table.TableBuilders;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.table.CellDescription;
import org.eclipse.sirius.components.view.table.ColumnDescription;
import org.eclipse.sirius.components.view.table.RowDescription;
import org.eclipse.sirius.components.view.table.TableDescription;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Builder of the {@link org.eclipse.sirius.components.view.table.TableDescription} to be used in an UML element.
 *
 * @author Jerome Gout
 */
public class UMLCommentTableRepresentationDescriptionBuilder {

    public static final String AQL_TRUE = "aql:true";
    /**
     * Name of the table.
     */
    public static final String UML_COMMENT_TABLE = "UML Comment Table";

    public View createView() {
        var umlCommentTableRepresentationDescription = this.build();

        var umlCommentTableView = new ViewBuilders()
                .newView()
                .descriptions(umlCommentTableRepresentationDescription)
                .build();

        UUID resourceId = UUID.nameUUIDFromBytes(UML_COMMENT_TABLE.getBytes());
        String resourcePath = resourceId.toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter(UML_COMMENT_TABLE));
        resource.getContents().add(umlCommentTableView);

        return umlCommentTableView;
    }

    private TableDescription build() {

        var rowDescription = this.buildRowDescription();

        var columnDescriptions = this.buildColumnDescriptions();

        var cellDescriptions = this.buildCellDescriptions();

        return new TableBuilders().newTableDescription()
                .name(UML_COMMENT_TABLE)
                .titleExpression(UML_COMMENT_TABLE)
                .domainType("uml::Element")
                .useStripedRowsExpression(AQL_TRUE)
                .rowDescription(rowDescription)
                .columnDescriptions(columnDescriptions.toArray(new ColumnDescription[0]))
                .cellDescriptions(cellDescriptions.toArray(new CellDescription[0]))
                .build();

    }

    private RowDescription buildRowDescription() {
        return new TableBuilders().newRowDescription()
                .name("Comment")
                .domainType("uml::Comment")
                .semanticCandidatesExpression("aql:self.getSemanticObjectsFromFeatureName('" + UMLPackage.eINSTANCE.getElement_OwnedComment().getName() + "', globalFilterData, columnFilters)")
                .initialHeightExpression("aql:53")
                .isResizableExpression(AQL_TRUE)
                .headerIconExpression("aql:self.getElementIconPath()")
                .headerIndexLabelExpression("aql:rowIndex + 1")
                .build();
    }

    private List<ColumnDescription> buildColumnDescriptions() {
        var comment = new TableBuilders().newColumnDescription()
                .name("comment features")
                .isResizableExpression(AQL_TRUE)
                .initialWidthExpression("aql:200")
                .headerLabelExpression("aql:self.getCommentColumnLabel()")
                .headerIndexLabelExpression("aql:columnIndex.alphabetic()")
                .semanticCandidatesExpression("aql:self.getCommentColumns()")
                .filterWidgetExpression("text")
                .build();
        return List.of(comment);
    }

    private List<CellDescription> buildCellDescriptions() {
        return List.of(new TableBuilders().newCellDescription()
                .valueExpression("aql:self.getCommentCellValue(columTargetObject)")
                .cellWidgetDescription(new TableBuilders().newCellLabelWidgetDescription().build())
                .build()
                );
    }
}
