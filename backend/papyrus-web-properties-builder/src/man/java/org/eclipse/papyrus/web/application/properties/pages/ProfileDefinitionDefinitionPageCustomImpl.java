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
import org.eclipse.sirius.components.view.form.PageDescription;

/**
 * Customization of {@link ProfileDefinitionDefinitionPage}.
 * @author Jerome Gout
 */
public class ProfileDefinitionDefinitionPageCustomImpl extends ProfileDefinitionDefinitionPage {

    public ProfileDefinitionDefinitionPageCustomImpl(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        super(viewElementFactory, colorRegistry);
    }
    
    @Override
    protected PageDescription createPage() {
        return viewElementFactory.createPageDescription("profileDefinition_definition_page", "ecore::EPackage", "aql:'Definition'", "aql:self", "aql:not(selection->size()>1) and selection->at(1).isPackageDefiningProfile()");
    }
}
