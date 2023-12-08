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
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsFactory;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListAddOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListDeleteOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListReorderOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;

/**
 * Custom implementation of ElementProfilePage.
 * 
 * @author Arthur Daussy
 */
public class ElementProfilePageCustomImpl extends ElementProfilePage {

    public ElementProfilePageCustomImpl(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super(viewElementFactory, colorRegistry);
    }

    @Override
    protected void createElementProfileGroup(PageDescription page) {
        GroupDescription group = viewElementFactory.createGroupDescription("element_profile_group", "", "var:self", GroupDisplayMode.LIST);
        page.getGroups().add(group);

        PrimitiveListWidgetDescription appliedStereotypeWidget = PapyrusWidgetsFactory.eINSTANCE.createPrimitiveListWidgetDescription();
        appliedStereotypeWidget.setName("appliedStereotypes");
        appliedStereotypeWidget.setLabelExpression("aql:'Applied stereotypes'");
        appliedStereotypeWidget.setValueExpression("aql:self.getAppliedStereotypes()");
        appliedStereotypeWidget.setCandidatesExpression("aql:self.getNonAppliedApplicableStereotypes().qualifiedName");
        appliedStereotypeWidget.setDisplayExpression("aql:candidate.name+ ' (from '+candidate.profile.name+')'");

        PrimitiveListDeleteOperation deleteOperation = PapyrusWidgetsFactory.eINSTANCE.createPrimitiveListDeleteOperation();
        deleteOperation.getBody().add(createChangeContext("aql:self.unapplyStereotype(candidate)"));
        appliedStereotypeWidget.setDeleteOperation(deleteOperation);

        PrimitiveListAddOperation addOperation = PapyrusWidgetsFactory.eINSTANCE.createPrimitiveListAddOperation();
        addOperation.getBody().add(createChangeContext("aql:self.applyStereotype(newValue)"));
        appliedStereotypeWidget.setAddOperation(addOperation);

        appliedStereotypeWidget.setHelpExpression("aql:'The List of Applied Stereotypes'");
        appliedStereotypeWidget.setIsEnabledExpression("aql:true");

        PrimitiveListReorderOperation reorderOperation = PapyrusWidgetsFactory.eINSTANCE.createPrimitiveListReorderOperation();
        reorderOperation.getBody().add(createChangeContext("aql:self.reorderStereotypes(fromIndex,toIndex)"));
        appliedStereotypeWidget.setReorderOperation(reorderOperation);

        group.getChildren().add(appliedStereotypeWidget);
    }

    private ChangeContext createChangeContext(String contextExp) {
        ChangeContext changeCtxt = ViewFactory.eINSTANCE.createChangeContext();
        changeCtxt.setExpression(contextExp);
        return changeCtxt;
    }

}
