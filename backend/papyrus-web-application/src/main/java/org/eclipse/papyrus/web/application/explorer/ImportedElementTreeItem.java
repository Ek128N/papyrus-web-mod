/*****************************************************************************
 * Copyright (c) 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.web.application.explorer;

import org.eclipse.uml2.uml.Element;

/**
 * Object that represents an imported element.
 *
 * @param importedElement
 *            the semantic element imported
 * @param parentElementId
 *            the id of the parent element (either a semantic element or the itemId of another ImportedElementTreeItem)
 * @param itemId
 *            unique id for this element
 *
 * @author Arthur Daussy
 */
public record ImportedElementTreeItem(Element importedElement, String parentElementId, String itemId) {
}
