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

import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.components.formdescriptioneditors.IWidgetPreviewConverterProvider;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * Converter used to convert Papyrus widgets from view to API for preview editor.
 *
 * @author Arthur Daussy
 */
@Service
public class PapyrusWidgetsPreviewConverterProvider implements IWidgetPreviewConverterProvider {

    @Override
    public Switch<AbstractWidgetDescription> getWidgetConverter(FormDescriptionEditorDescription formDescriptionEditorDescription, VariableManager variableManager) {
        return new PapyrusWidgetsPreviewConverter(variableManager, formDescriptionEditorDescription);
    }

}
