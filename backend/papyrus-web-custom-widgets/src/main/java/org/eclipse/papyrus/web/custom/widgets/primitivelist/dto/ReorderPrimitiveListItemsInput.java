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
package org.eclipse.papyrus.web.custom.widgets.primitivelist.dto;

import java.util.UUID;

import org.eclipse.sirius.components.collaborative.forms.api.IFormInput;

/**
 * Input of the reorder items mutation of the Primitive List.
 *
 * @author Jerome Gout
 */
public record ReorderPrimitiveListItemsInput(UUID id, String editingContextId, String representationId, String listId, String itemId, int fromIndex, int toIndex)  implements IFormInput {

}

