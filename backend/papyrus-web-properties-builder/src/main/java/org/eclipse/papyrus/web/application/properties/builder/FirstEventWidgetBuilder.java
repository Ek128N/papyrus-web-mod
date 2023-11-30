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
package org.eclipse.papyrus.web.application.properties.builder;

import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsFactory;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListAddOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListDeleteOperation;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PrimitiveListWidgetDescription;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.ViewFactory;

/**
 * Builder of widget in charge of handling "firstEvent" feature.
 * 
 * @author Arthur Daussy
 */
public class FirstEventWidgetBuilder {

    public PrimitiveListWidgetDescription build() {
        PrimitiveListWidgetDescription description = PapyrusWidgetsFactory.eINSTANCE.createPrimitiveListWidgetDescription();
        description.setName("firstEvent");
        description.setLabelExpression("First event");
        description.setValueExpression("feature:firstEvent");
        description.setCandidatesExpression("aql:Sequence{'true', 'false'}");
        PrimitiveListDeleteOperation deleteOperation = PapyrusWidgetsFactory.eINSTANCE.createPrimitiveListDeleteOperation();
        deleteOperation.getBody().add(createChangeContext("aql:self.removeFromUsingIndex('firstEvent',candidateIndex)"));
        description.setDeleteOperation(deleteOperation);

        PrimitiveListAddOperation addOperation = PapyrusWidgetsFactory.eINSTANCE.createPrimitiveListAddOperation();
        addOperation.getBody().add(createChangeContext("aql:self.addToAttribute('firstEvent',newValue)"));
        description.setAddOperation(addOperation);

        description.setHelpExpression("aql:self.getFeatureDescription('firstEvent')");
        description.setIsEnabledExpression("aql:true");
        return description;
    }

    private static ChangeContext createChangeContext(String contextExp) {
        ChangeContext changeCtxt = ViewFactory.eINSTANCE.createChangeContext();
        changeCtxt.setExpression(contextExp);
        return changeCtxt;
    }

}
