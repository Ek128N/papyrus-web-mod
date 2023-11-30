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
package org.eclipse.papyrus.web.application.configuration;

import org.eclipse.papyrus.web.application.properties.ColorRegistry;
import org.eclipse.papyrus.web.application.properties.builder.UMLDetailViewBuilderCustomImpl;
import org.eclipse.papyrus.web.application.properties.pages.MemberEndGroupDescriptionBuilder;
import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.FormFactory;

/**
 * Build of the full UML Detail view form.
 *
 * @author Arthur Daussy
 */
public class UMLDetailViewFromBuilder {

    private final String formName;

    public UMLDetailViewFromBuilder(String formName) {
        super();
        this.formName = formName;
    }

    public View build() {
        View view = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createView();

        ColorRegistry colorRegistry = new ColorRegistry();

        this.defineGlobalColors(colorRegistry);

        FormDescription form = this.createFormDescription();
        view.getDescriptions().add(form);

        form.getPages().addAll(new UMLDetailViewBuilderCustomImpl(colorRegistry).createPages());

        ColorPalette colorPalette = ViewFactory.eINSTANCE.createColorPalette();
        view.getColorPalettes().add(colorPalette);
        colorPalette.getColors().addAll(colorRegistry.getAllColors());
        return view;
    }

    private void defineGlobalColors(ColorRegistry registry) {
        registry.registerColor(MemberEndGroupDescriptionBuilder.MEMBER_END_BORDER_COLOR_NAME, "#c2c2c2");
    }

    private FormDescription createFormDescription() {
        FormDescription form = FormFactory.eINSTANCE.createFormDescription();
        form.setName(this.formName);
        form.setDomainType("uml::Element");
        form.setTitleExpression("aql:'Details Form'");
        return form;

    }

}
