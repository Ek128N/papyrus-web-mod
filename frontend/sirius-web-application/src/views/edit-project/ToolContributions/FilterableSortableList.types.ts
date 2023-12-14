/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *     CEA LIST - copied from FilterableSortableList.types.ts at 10f4e6386bb9eeb3839071e9cdd4d1ceb8efcb39. Adapted to contain metaclasses.
 *******************************************************************************/
import { ItemWithIcon } from './TransferModal.types';

export interface FilterableSortableListProps {
  items: ItemWithIcon[];
  setItems: (items: ItemWithIcon[]) => void;
  onDoubleClick: () => void;
  onClick: (event: React.MouseEvent<Element, MouseEvent>, item: ItemWithIcon) => void;
  selectedItems: ItemWithIcon[];
  onFocusFilter: boolean;
}

export interface FilterableSortableListState {
  filterBarText: string;
  hoveringItemId: string | undefined;
  draggingItemId: string | undefined;
}

export interface HighlightedLabelProps {
  label: string;
  textToHighlight: string;
}
