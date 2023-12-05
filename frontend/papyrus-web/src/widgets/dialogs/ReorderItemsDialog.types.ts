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
 ***************************************************************************/

export interface ReorderItemsDialogProps {
  items: ReorderingItem[];
  onClose: () => void;
  moveElement: (itemId: string, fromIndex: number, toIndex: number) => void;
}
export interface ReorderItemsDialogState {
  draggingItemId: string | undefined;
  draggingStartIndex: number;
  draggingIndex: number;
}

export interface ReorderingItem {
  label: string;
  id: string;
  iconURL: string[];
}
