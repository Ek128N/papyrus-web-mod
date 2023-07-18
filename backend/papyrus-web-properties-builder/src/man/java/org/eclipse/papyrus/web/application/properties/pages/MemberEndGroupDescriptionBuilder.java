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

import java.util.Optional;

import org.eclipse.papyrus.web.application.properties.ColorRegistry;
import org.eclipse.papyrus.web.application.properties.ViewElementsFactory;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.form.ContainerBorderLineStyle;
import org.eclipse.sirius.components.view.form.ContainerBorderStyle;
import org.eclipse.sirius.components.view.form.FlexDirection;
import org.eclipse.sirius.components.view.form.FormFactory;
import org.eclipse.sirius.components.view.form.WidgetDescription;

/**
 * Builder class to share member end group description creation. See {@link AssociationUmlPageCustomImpl} and
 * {@link AssociationClassUmlPageCustomImpl}
 * 
 * @author Jerome Gout
 */
public final class MemberEndGroupDescriptionBuilder {

    /**
     * Color name used for border style of Member end container.
     */
    public static final String MEMBER_END_BORDER_COLOR_NAME = "memberEnd.border";

    private ViewElementsFactory viewElementFactory;

    private ColorRegistry colorRegistry;

    public MemberEndGroupDescriptionBuilder(ViewElementsFactory viewElementFactory, ColorRegistry colorRegistry) {
        this.viewElementFactory = viewElementFactory;
        this.colorRegistry = colorRegistry;
    }

    public WidgetDescription build() {
        var container = FormFactory.eINSTANCE.createFlexboxContainerDescription();
        container.setFlexDirection(FlexDirection.COLUMN);
        container.setBorderStyle(createBorderStyle());
        container.setName("aql:'memberEnd");
        container.setLabelExpression("aql:'Member End'");
        var nameWidget = viewElementFactory.createTextfieldDescription("name", "aql:'Name'", //
                /* valueExpression */"aql:self.name", //
                /* contextExpression */"aql:self.set('name', newValue)", //
                /* helpExpression */"aql:self.getFeatureDescription('name')", //
                /* isEnabledExpression */"aql:self.isMemberEndPropertyEditable('name')");

        // TODO Restore when Primitive Select will be available:
        // https://gitlab.eclipse.org/eclipse/papyrus/org.eclipse.papyrus-web/-/issues/20
        // var ownerWidget = viewElementFactory.createSelectDescription("owner", "aql:'Owner'", //
        // /* valueExpression */"aql:self.getOwner()", //
        // /* contextExpression */"aql:self.setOwner(newValue)", //
        // /* candidatesExpression */"aql:self.getOwnerEnumerations()", //
        // /* candidateLabelExpression */"aql:candidate.toString()", //
        // /* helpExpression */"aql:'The owner of the association.'", //
        // /* isEnabledExpression */"aql:self.isMemberEndPropertyEditable('owner')");

        var isNavigableWidget = viewElementFactory.createCheckboxDescription("isNavigable", "Navigable", //
                /* valueExpression */"aql:self.isNavigable()", //
                /* contextExpression */"aql:self.setNavigable(newValue)", //
                /* helpExpression */"aql:'Indicates whether it is possible to navigate across the property.'", //
                /* isEnabledExpression */"aql:self.isMemberEndPropertyEditable('isNavigable')");

        var aggregationWidget = viewElementFactory.createSelectDescription("aggregation", "aql:'Aggregation'", //
                /* valueExpression */"aql:self.eClass().getEStructuralFeature('aggregation').eType.oclAsType(ecore::EEnum).getEEnumLiteralByLiteral(self.aggregation.toString())", //
                /* contextExpression */"aql:self.oclAsType(uml::Property).set('aggregation', newValue.instance)", //
                /* candidatesExpression */"aql:self.eClass().getEStructuralFeature('aggregation').eType.oclAsType(ecore::EEnum).eLiterals", //
                /* candidateLabelExpression */"aql:candidate.name", //
                /* helpExpression */"aql:self.getFeatureDescription('aggregation')", //
                /* isEnabledExpression */"aql:self.isMemberEndPropertyEditable('aggregation')");

        var multiplicityWidget = viewElementFactory.createTextfieldDescription("multiplicity", "aql:'Multiplicity'", //
                /* valueExpression */"aql:self.getMultiplicity()", //
                /* contextExpression */"aql:self.oclAsType(uml::MultiplicityElement).setMultiplicity(newValue)", //
                /* helpExpression */"aql:self.getMultiplicityHelpContent()", //
                /* isEnabledExpression */"aql:self.isMemberEndPropertyEditable('lowerValue') and self.isMemberEndPropertyEditable('upperValue')");

        container.getChildren().add(nameWidget);
        // container.getChildren().add(ownerWidget);
        container.getChildren().add(isNavigableWidget);
        container.getChildren().add(aggregationWidget);
        container.getChildren().add(multiplicityWidget);
        return container;
    }

    private ContainerBorderStyle createBorderStyle() {
        ContainerBorderStyle style = FormFactory.eINSTANCE.createContainerBorderStyle();
        Optional<UserColor> color = colorRegistry.getColorByName(MEMBER_END_BORDER_COLOR_NAME);
        if (color.isPresent()) {
            style.setBorderColor(color.get());
        }
        style.setBorderLineStyle(ContainerBorderLineStyle.SOLID);
        style.setBorderRadius(0);
        return style;
    }

}
