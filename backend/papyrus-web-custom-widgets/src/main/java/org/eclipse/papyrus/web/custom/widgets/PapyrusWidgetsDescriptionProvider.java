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
import org.eclipse.papyrus.web.custom.widgets.papyruswidgets.PapyrusWidgetsPackage;
import org.eclipse.papyrus.web.custom.widgets.primitivelist.PrimitiveListWidgetDescriptor;
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

        if (Objects.equal(widgetKind, PrimitiveListWidgetDescriptor.TYPE)) {
            return Optional.of(PapyrusWidgetsPackage.Literals.PRIMITIVE_LIST_WIDGET_DESCRIPTION);
        } else {
            return Optional.empty();
        }
    }

}
