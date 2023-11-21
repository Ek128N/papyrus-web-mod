/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
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
package org.eclipse.papyrus.web.application.properties.pages;

import org.eclipse.papyrus.web.application.properties.ColorRegistry;
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.form.FlexDirection;
import org.eclipse.sirius.components.view.form.FormFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;

/**
 * Customization of {@link ProfileDefinitionPage}.
 * @author Jerome Gout
 */
public class ProfileDefinitionPageCustomImpl extends ProfileDefinitionPage {

    private static final String AQL_FALSE = "aql:false";

    public ProfileDefinitionPageCustomImpl(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super(viewElementFactory, colorRegistry);
    }

    @Override
    protected void createProfileDefinitionGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("profile_definition_group", "aql:self.name + '.' + self.getProfileDefinitionVersion()", "aql:self.oclAsType(uml::Profile).getDefinitions()", GroupDisplayMode.LIST);
        page.getGroups().add(group);
        
        var buttonWidget = FormFactory.eINSTANCE.createButtonDescription();
        buttonWidget.setImageExpression("aql:'/icons/remove-profile-trash.svg'");
        buttonWidget.setHelpExpression("aql:'Remove this profile definition from the model'");
        ChangeContext changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:self.removeProfileDefinition(self.eContainer().eContainer())");
        buttonWidget.getBody().add(changeContext);
        group.getToolbarActions().add(buttonWidget);
        
        var col1 = FormFactory.eINSTANCE.createFlexboxContainerDescription();
        col1.setFlexDirection(FlexDirection.COLUMN);
        var versionWidget = viewElementFactory.createTextfieldDescription("version", "aql:'Version'", //
                /* valueExpression */"aql:self.getProfileDefinitionVersion()", //
                /* contextExpression */"", //
                /* helpExpression */"", //
                /* isEnabledExpression */AQL_FALSE);
        var dateWidget = viewElementFactory.createTextfieldDescription("date", "aql:'Date'", //
                /* valueExpression */"aql:self.getProfileDefinitionDate()", //
                /* contextExpression */"", //
                /* helpExpression */"", //
                /* isEnabledExpression */AQL_FALSE);
        col1.getChildren().add(versionWidget);
        col1.getChildren().add(dateWidget);
        
        var col2 = FormFactory.eINSTANCE.createFlexboxContainerDescription();
        col2.setFlexDirection(FlexDirection.COLUMN);
        var authorWidget = viewElementFactory.createTextfieldDescription("author", "aql:'Author'", //
                /* valueExpression */"aql:self.getProfileDefinitionAuthor()", //
                /* contextExpression */"", //
                /* helpExpression */"", //
                /* isEnabledExpression */AQL_FALSE);
        var copyrightWidget = viewElementFactory.createTextfieldDescription("copyright", "aql:'Copyright'", //
                /* valueExpression */"aql:self.getProfileDefinitionCopyright()", //
                /* contextExpression */"", //
                /* helpExpression */"", //
                /* isEnabledExpression */AQL_FALSE);
        col2.getChildren().add(authorWidget);
        col2.getChildren().add(copyrightWidget);
        var container = FormFactory.eINSTANCE.createFlexboxContainerDescription();
        container.setFlexDirection(FlexDirection.ROW);
        container.getChildren().add(col1);
        container.getChildren().add(col2);
        group.getChildren().add(container);

        var commentWidget = viewElementFactory.createTextAreaDescription("comment", "aql:'Comment'", //
                /* valueExpression */"aql:self.getProfileDefinitionComment()", //
                /* contextExpression */"", //
                /* helpExpression */"", //
                /* isEnabledExpression */AQL_FALSE);
        group.getChildren().add(commentWidget);
    }
}
