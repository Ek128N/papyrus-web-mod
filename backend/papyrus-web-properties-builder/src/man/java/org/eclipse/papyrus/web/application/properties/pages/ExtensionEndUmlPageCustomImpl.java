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
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

/**
 * Customization of {@link ExtensionEndUmlPage}.
 * 
 * @author Arthur Daussy
 */
public class ExtensionEndUmlPageCustomImpl extends ExtensionEndUmlPage {

    public ExtensionEndUmlPageCustomImpl(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super(viewElementFactory, colorRegistry);
    }

    /**
     * AQL has trouble with the following services 'select' | 'reject' | 'collect' | 'any' | 'exists' | 'forAll' |
     * 'isUnique' | 'one' | 'sortedBy' | 'closure', due to the use lambda as first parameter. To force AQL to use the
     * feature instead of the service we need to add a extra '_' </br>
     * Matching: feature:isUnique -> aql:self._isUnique
     */
    @Override
    protected void addIsUnique(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createCheckboxDescription("isUnique", "aql:'Is unique'", "aql:self._isUnique", "aql:self.set('isUnique',newValue)",
                "aql:self.getFeatureDescription('isUnique')", "aql:self.eClass().getEStructuralFeature('isUnique').changeable");
        group.getChildren().add(widget);
    }

}
