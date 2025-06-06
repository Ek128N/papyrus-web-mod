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
package org.eclipse.papyrus.web.custom.widgets.containmentreference.dto;

import java.util.UUID;

import org.eclipse.sirius.components.collaborative.forms.api.IFormInput;
import org.eclipse.sirius.components.forms.ClickEventKind;

/**
 * Input object for the mutation to handle the click of a containment reference item.
 *
 * @author Jerome Gout
 */
public record ClickContainmentReferenceItemInput(UUID id, String editingContextId, String representationId, String referenceWidgetId, String referenceItemId,
                                       ClickEventKind clickEventKind) implements IFormInput {

}
