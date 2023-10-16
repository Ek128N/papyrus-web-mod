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
 * Custom implementation for {@link ElementImportUmlPage}.
 * 
 * @author Arthur Daussy
 */
public class ElementImportUmlPageCustomImpl extends ElementImportUmlPage {

    public ElementImportUmlPageCustomImpl(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super(viewElementFactory, colorRegistry);
    }

    @Override
    protected void addImportedElement(GroupDescription group) {
        WidgetDescription widget = viewElementFactory.createReferenceDescription("importedElement", "aql:'Imported element'", "aql:self.getFeatureDescription('importedElement')",
                "aql:false" /* Same behavior as Papyrus Legacy */, "aql:'importedElement'", "");
        group.getChildren().add(widget);
    }
}
