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

import java.util.List;

import org.eclipse.papyrus.web.application.properties.ColorRegistry;
import org.eclipse.papyrus.web.application.properties.UMLDetailViewBuilder;
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.papyrus.web.application.properties.pages.StereotypeApplicationPage;
import org.eclipse.sirius.components.view.form.PageDescription;

/**
 * Override the View builder {@link UMLDetailViewBuilder} to manage generated pages from the Papyrus EEF VSM and extra pages.
 * 
 * @author Jerome Gout
 */
public class UMLDetailViewBuilderCustomImpl extends UMLDetailViewBuilder {
   
    private ViewElementsFactory factory = new ViewElementsFactory();
   
    private ColorRegistry colorRegistry;
    
    public UMLDetailViewBuilderCustomImpl(ColorRegistry colorRegistry) {
        super(colorRegistry);
        this.colorRegistry = colorRegistry;
    }

    @Override
    public List<PageDescription> createPages() {
        var generatedPages = super.createPages();
        
        // add stereotype application page
        generatedPages.add(new StereotypeApplicationPage(factory, colorRegistry).create());
        
        return generatedPages;
    }
}
