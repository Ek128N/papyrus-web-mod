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
package org.eclipse.papyrus.web.custom.widgets;

import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.web.custom.widgets.containmentreference.ContainmentReferenceWidgetDescriptor;
import org.eclipse.papyrus.web.custom.widgets.languageexpression.LanguageExpressionDescriptor;
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage;
import org.eclipse.papyrus.web.custom.widgets.primitivelist.PrimitiveListWidgetDescriptor;
import org.eclipse.papyrus.web.custom.widgets.primitiveradio.PrimitiveRadioDescriptor;
import org.eclipse.sirius.components.formdescriptioneditors.IWidgetDescriptionProvider;
import org.springframework.stereotype.Service;

import graphql.com.google.common.base.Objects;

/**
 * The IWidgetDescriptionProvider for the Primitive List widget.
 *
 * @author Arthur Daussy
 */
@Service
public class PapyrusWidgetsDescriptionProvider implements IWidgetDescriptionProvider {

    @Override
    public Optional<EClass> getWidgetDescriptionType(String widgetKind) {
        Optional<EClass> res = Optional.empty();
        if (Objects.equal(widgetKind, PrimitiveListWidgetDescriptor.TYPE)) {
            res = Optional.of(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_WIDGET_DESCRIPTION);
        } else if (Objects.equal(widgetKind, LanguageExpressionDescriptor.TYPE)) {
            res = Optional.of(PapyrusWidgetsPackage.Literals.LANGUAGE_EXPRESSION_WIDGET_DESCRIPTION);
        } else if (Objects.equal(widgetKind, PrimitiveRadioDescriptor.TYPE)) {
            res = Optional.of(PapyrusWidgetsPackage.Literals.PRIMITIVE_RADIO_WIDGET_DESCRIPTION);
        } else if (Objects.equal(widgetKind, ContainmentReferenceWidgetDescriptor.TYPE)) {
            res = Optional.of(PapyrusWidgetsPackage.Literals.CONTAINMENT_REFERENCE_WIDGET_DESCRIPTION);
        }
        return res;
    }

}
